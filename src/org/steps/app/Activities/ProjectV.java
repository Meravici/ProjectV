package org.steps.app.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageButton;
import org.steps.utils.Globals;


public class ProjectV extends Activity{

    private ConstructorAPI constructor;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        //this.constructor = new Constructor(this);
        this.constructor = new ConstructorMoc(this);
        listView = (ListView) findViewById(R.id.GroupListView);

        listView.setEmptyView(findViewById(R.id.first_view));

        constructor.login(Globals.USER_ID);
        constructor.getGroups();

        Globals.GROUPLIST = listView;
        Globals.MAIN_ACTIVITY = this;
        final Activity local = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent GroupActivity = new Intent(local, GroupActivity.class);
                Globals.GROUP = Globals.GROUPS.get(position);
                startActivity(GroupActivity);
                overridePendingTransition(R.animator.anim_right_in ,R.animator.anim_left_out);
            }
        });



    }

    public void errorCallback(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("შეცდომა!");
        builder.setMessage("დაფიქსირდა შეცდომა, გთხოვთ გადატვირთოთ აპლიკაცია").setCancelable(false)
                .setPositiveButton("დიახ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                finish();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.addGroup:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setTitle("ჯგუფის რეგისტრაცია");
                alert.setMessage("გთოხვთ შეიყანოთ სახელი");


// Set an EditText view to get user input

                final EditText input = new EditText(this);
                alert.setView(input);
                alert.setPositiveButton("წინ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        constructor.registerGroup(value);
                    }
                });

                alert.setNegativeButton("უკან", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    public ConstructorAPI getConstructor(){
        return this.constructor;
    }
}

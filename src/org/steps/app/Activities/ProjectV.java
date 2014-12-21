package org.steps.app.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.ProjectV.R;
import org.steps.app.objects.Group;
import org.steps.utils.Globals;

import java.util.ArrayList;

public class ProjectV extends Activity {
    /**
     * Called when the activity is first created.
     */

    private ConstructorAPI constructor;
    private ListView listView;
    private ArrayList<Group> arrayOfGroups;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        this.constructor = new Constructor(this);
//        this.constructor = new ConstructorMoc(this);
        listView = (ListView) findViewById(R.id.GroupListView);

        listView.setEmptyView(findViewById(R.id.first_view));

        constructor.login("000000000000000000000");
        constructor.getGroups();



        // Must add the progress bar to the root of the layout
//        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
//        root.addView(progressBar);
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
        ImageButton btn =(ImageButton) listView.findViewById(R.id.addGroup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    public ConstructorAPI getConstructor(){
        return this.constructor;
    }
}

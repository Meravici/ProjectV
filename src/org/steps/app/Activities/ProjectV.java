package org.steps.app.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.steps.utils.Globals;


public class ProjectV extends Activity{

    private ConstructorAPI constructor;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        this.constructor = new Constructor(this);
//        this.constructor = new ConstructorMoc(this);
        listView = (ListView) findViewById(R.id.GroupListView);

        listView.setEmptyView(findViewById(R.id.first_view));

        constructor.login(Globals.USER_ID);
        constructor.getGroups();


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

    public ConstructorAPI getConstructor(){
        return this.constructor;
    }
}

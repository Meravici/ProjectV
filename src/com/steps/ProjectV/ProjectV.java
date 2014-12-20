package com.steps.ProjectV;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AbsListView.LayoutParams;
import com.example.ProjectV.R;
import com.steps.Facade.FacadeAPI;
import com.steps.Facade.FacadeMain;
import com.steps.Facade.FacadeMock;
import com.steps.Objects.GroupObject;
import com.steps.Objects.UserObject;
import com.steps.Utils.Globals;
import com.steps.Utils.Mediator;
import com.steps.adapters.GroupAdapter;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ProjectV extends Activity {
    /**
     * Called when the activity is first created.
     */
    private ArrayList<GroupObject> arrayOfGroups;
    private FacadeAPI facade;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);




        this.facade = new FacadeMock(this);
        listView = (ListView) findViewById(R.id.GroupListView);
        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        listView.setEmptyView(progressBar);

        try{
            facade.loginUser("000000000000000000000");

        }catch(Exception e){
            System.out.println("error");
        }
        short a = 1;
//        successCallback(new UserObject("000000", "Gio"), new GroupObject[]{new GroupObject(1, "სახელიი", new Timestamp(1l), a)});

        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);
        final Context local = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("clicked bitch");
                Globals.GROUP = arrayOfGroups.get(position);
                Intent groupActivity;
                groupActivity = new Intent(local, GroupActivity.class);
                local.startActivity(groupActivity);
                overridePendingTransition(R.animator.anim_right_in ,R.animator.anim_left_out);
            }
        });

    }

    public void successCallback(UserObject loggedInUser, ArrayList<GroupObject> groups){
        // Construct the data source
        arrayOfGroups= new ArrayList<GroupObject>();
        for(int i=0; i<groups.size(); i++) {
            arrayOfGroups.add(groups.get(i)); //TODO
        }
        // Create the adapter to convert the array to views
        GroupAdapter adapter = new GroupAdapter(this, arrayOfGroups);
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);
        System.out.println(loggedInUser.getGoogleID());
    }

    public void failureCallback(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("CLOSE");
        builder.setMessage("Do You Want to Close the Application").setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                finish();
                            }


                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

package com.steps.ProjectV;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AbsListView.LayoutParams;
import com.example.ProjectV.R;
import com.steps.Facade.FacadeAPI;
import com.steps.Facade.FacadeMain;
import com.steps.Objects.GroupObject;
import com.steps.Objects.UserObject;
import com.steps.Utils.Mediator;
import com.steps.adapters.GroupAdapter;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ProjectV extends Activity {
    /**
     * Called when the activity is first created.
     */

    private FacadeAPI facade;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        this.facade = new FacadeMain(new Mediator(), this);
        listView = (ListView) findViewById(R.id.GroupListView);
        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        listView.setEmptyView(progressBar);

        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.GroupListViewItemLinearLayout);
//                LinearLayout groupRow = (LinearLayout)linearLayout.getChildAt(position);
//                groupRow.setBackgroundResource(R.
//
//                        Android.android:background="?android:attr/activatedBackgroundIndicator");
//            }
//        });

    }

    private void successCallback(UserObject loggedInUser, ArrayList<GroupObject> groups){
        // Construct the data source
        ArrayList<GroupObject> arrayOfGroups= new ArrayList<GroupObject>();
        for(short i=0; i<100; i++) {
            arrayOfGroups.add(new GroupObject(1, "სახელი", new Timestamp(1l), i)); //TODO
        }
        // Create the adapter to convert the array to views
        GroupAdapter adapter = new GroupAdapter(this, arrayOfGroups);
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);
    }

    private void failureCallback(){
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

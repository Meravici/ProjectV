package com.steps.ProjectV;
import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
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
import com.steps.Objects.GroupObject;
import com.steps.adapters.GroupAdapter;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ProjectV extends Activity {
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        ListView listView = (ListView) findViewById(R.id.GroupListView);
        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        listView.setEmptyView(progressBar);

        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);

        // Construct the data source
        ArrayList<GroupObject> arrayOfGroups= new ArrayList<GroupObject>();
        for(short i=0; i<100; i++) {
            arrayOfGroups.add(new GroupObject(1, "name", new Timestamp(1l), i)); //TODO
        }
        // Create the adapter to convert the array to views
        GroupAdapter adapter = new GroupAdapter(this, arrayOfGroups);
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.GroupListViewItemLinearLayout);
                linearLayout.
            }
        });

    }
}

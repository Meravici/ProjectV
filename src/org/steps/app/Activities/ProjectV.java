package org.steps.app.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.example.ProjectV.R;
import org.steps.app.objects.Group;

import java.util.ArrayList;

public class ProjectV extends Activity {
    /**
     * Called when the activity is first created.
     */

    private Constructor constructor;
    private ListView listView;
    private ArrayList<Group> arrayOfGroups;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);



        this.constructor = new Constructor(this);

        listView = (ListView) findViewById(R.id.GroupListView);
        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        listView.setEmptyView(progressBar);

        if(constructor.login("000000000000000000000")){
            constructor.getGroups();
        }else{
            errorCallback();
        }


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

    public Constructor getConstructor(){
        return this.constructor;
    }
}

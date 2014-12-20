package org.steps.app.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.ListView;
import com.example.ProjectV.R;
import org.steps.adapters.GroupAdapter;
import org.steps.app.objects.Group;
import org.steps.app.objects.Task;
import org.steps.app.objects.User;
import org.steps.mediator.Mediator;
import org.steps.storage.MyStorageListener;
import org.steps.storage.StorageListener;
import org.steps.storage.StorageReader;
import org.steps.storage.StorageWriter;
import org.steps.utils.Globals;
import org.steps.utils.ServerErrorException;
import org.steps.utils.startGCM;

import java.lang.reflect.Array;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Xato on 12/20/2014.
 */
public class ConstructorMoc implements ConstructorAPI {
    private String telephoneNumber;
    private Mediator mediator;
    private StorageWriter storageWriter;
    private StorageReader storageReader;
    private StorageListener storageListener;
    private ProjectV activity;

    private ProgressDialog loadingDialog;

    private User[] users = new User[4];
    private Task[] tasks = new Task[4];
    private ArrayList<Group> groups = new ArrayList<Group>();

    public ConstructorMoc(ProjectV activity){
        telephoneNumber = getTelephoneNumber(activity.getApplicationContext());
        this.mediator = new Mediator(storageReader, storageWriter);
        this.storageReader = new StorageReader(storageListener, activity);
        this.storageWriter = new StorageWriter(storageListener, activity);
        this.storageListener = new MyStorageListener(activity.getApplicationContext());
        this.activity = activity;

        startGCM gcm=new startGCM(activity,storageWriter,activity.getApplicationContext());

        this.loadingDialog = new ProgressDialog(activity);
        loadingDialog.setTitle("იტვირთება");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setMessage("გთხოვთ დაიცადოთ");
        loadingDialog.setCancelable(true);


        users[0] = new User("000000000000000000001", "9550000001");
        users[1] = new User("000000000000000000002", "9550000002");
        users[2] = new User("000000000000000000003", "9550000003");
        users[3] = new User("000000000000000000004", "9550000004");

        tasks[0] = new Task(1, "Task1", new Date(0), new Date(0), Task.STATUS_PENDING, users[0], null);
        tasks[1] = new Task(2, "Task2", new Date(0), new Date(0), Task.STATUS_FINISHED, users[1], users[0]);
        tasks[2] = new Task(3, "Task3", new Date(0), new Date(0), Task.STATUS_STARTED, users[1], users[3]);
        tasks[3] = new Task(4, "Task4", new Date(0), new Date(0), Task.STATUS_FAILED, users[3], users[2]);

        groups.add(new Group(1, "Group1", new Timestamp(0), 1));
        groups.get(0).addUser(users[0]);
        groups.get(0).addUser(users[1]);

        groups.add(new Group(2, "Group2", new Timestamp(0), 2));
        groups.get(1).addUser(users[2]);
        groups.get(1).addUser(users[3]);
    }



    private String getTelephoneNumber(Context context) {
        TelephonyManager tMgr =(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return tMgr.getLine1Number();
    }

    public void getGroups(){
        getGroupsCallback(groups);
    }

    public void getGroupsCallback(ArrayList<Group> groups){
        ListView listView = (ListView) activity.findViewById(R.id.GroupListView);
        GroupAdapter adapter = new GroupAdapter(activity, groups);
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);
        stopSpinner();
        Globals.GROUPS = groups;
    }

    public void login(String s){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                storageListener.onUserLoggedIn();
            }
        });
    }




    private void startSpinner(){
        loadingDialog.show();
    }

    private void stopSpinner(){
        loadingDialog.dismiss();
    }

}



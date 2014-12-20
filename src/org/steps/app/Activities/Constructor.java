package org.steps.app.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.telephony.TelephonyManager;
import android.widget.ListView;
import com.example.ProjectV.R;
import org.steps.adapters.GroupAdapter;
import org.steps.app.objects.Group;
import org.steps.mediator.Mediator;
import org.steps.storage.MyStorageListener;
import org.steps.storage.StorageListener;
import org.steps.storage.StorageReader;
import org.steps.storage.StorageWriter;
import org.steps.utils.Globals;
import org.steps.utils.ServerErrorException;
import org.steps.utils.startGCM;

import java.util.ArrayList;

/**
 * Created by Xato on 12/20/2014.
 */
public class Constructor implements ConstructorAPI {
    private String telephoneNumber;
    private Mediator mediator;
    private StorageWriter storageWriter;
    private StorageReader storageReader;
    private StorageListener storageListener;
    private ProjectV activity;

    private ProgressDialog loadingDialog;

    public Constructor(ProjectV activity){
        this.storageListener = new MyStorageListener(activity.getApplicationContext());
        this.activity = activity;
        telephoneNumber = getTelephoneNumber(activity.getApplicationContext());
        this.storageReader = new StorageReader(storageListener, activity);
        this.storageWriter = new StorageWriter(storageListener, activity);
        this.mediator = new Mediator(storageReader, storageWriter);


        this.loadingDialog = new ProgressDialog(activity);
        loadingDialog.setTitle("იტვირთება");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setMessage("გთხოვთ დაიცადოთ");
        loadingDialog.setCancelable(true);
        this.loadingDialog.show();

        startGCM gcm = new startGCM(activity, storageWriter ,activity.getApplicationContext());
    }



    private String getTelephoneNumber(Context context) {
        TelephonyManager tMgr =(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return tMgr.getLine1Number();
    }

    public void getGroups(){
        startSpinner();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<Group> groups = storageReader.getMyGroups();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.getConstructor().getGroupsCallback(groups);
                    }
                });
            }
        }).start();
    }

    public void getGroupsCallback(ArrayList<Group> groups){
        ListView listView = (ListView) activity.findViewById(R.id.GroupListView);
        GroupAdapter adapter = new GroupAdapter(activity, groups);
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);

        stopSpinner();
    }

    public void login(String s){
        final String name = s;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mediator.login(name, telephoneNumber, Globals.USER_REG_ID);
                } catch (ServerErrorException e) {
                    activity.errorCallback();
                }
            }
        }).start();

    }




    private void startSpinner(){
        loadingDialog.show();
    }

    private void stopSpinner(){
        loadingDialog.dismiss();
    }

}



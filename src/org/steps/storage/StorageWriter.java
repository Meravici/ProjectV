package org.steps.storage;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Xato on 12/20/2014.
 */
public class StorageWriter {
    private StorageListener listener;
    private Activity activity;
    public StorageWriter(StorageListener listener, Activity activity){
        this.listener = listener;
        this.activity = activity;
    }

    public void addUserToGroup(){

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.onUserAddedToGroup();
            }
        });

    }
    public void removeUserFromGroup(){
        listener.onUserLeftGroup();
    }

    public void AddTaskToGroup(){
        listener.onTaskAddedToGroup();
    }

    public void SetTaskPendingStatus(){
        listener.onTaskTakenInGroup();
    }

    public void SetTaskFinishedStatus(){
        listener.onTaskFinishedInGroup();
    }



}

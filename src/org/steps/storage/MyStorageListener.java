package org.steps.storage;

import android.content.Context;

/**
 * Created by Xato on 12/20/2014.
 */
public class MyStorageListener extends StorageListener {
    private Context context;
    public MyStorageListener(Context context){
        this.context = context;
    }

    @Override
    public void onUserAddedToGroup() {
        //TODO
    }

    @Override
    public void onUserLeftGroup() {
        //TODO
    }

    @Override
    public void onTaskAddedToGroup() {
        //TODO
    }

    @Override
    public void onTaskTakenInGroup() {
        //TODO
    }

    @Override
    public void onTaskFinishedInGroup() {
        //TODO
    }
}

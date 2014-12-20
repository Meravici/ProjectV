package org.steps.storage;

/**
 * Created by Xato on 12/20/2014.
 */
public abstract class StorageListener {
    public abstract void onUserLoggedIn();
    public abstract void onGroupCreated();
    public abstract void onUserAddedToGroup();
    public abstract void onUserLeftGroup();
    public abstract void onTaskAddedToGroup();
    public abstract void onTaskTakenInGroup();
    public abstract void onTaskFinishedInGroup();
}

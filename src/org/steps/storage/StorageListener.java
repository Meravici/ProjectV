package org.steps.storage;

import org.steps.app.objects.Group;
import org.steps.app.objects.Task;
import org.steps.app.objects.User;

/**
 * Created by Xato on 12/20/2014.
 */
public abstract class StorageListener {
    public abstract void onUserLoggedIn();
    public abstract void onGroupCreated(Group group);
    public abstract void onUserAddedToGroup(Group group, User user);
    public abstract void onUserLeftGroup(Group group, User user);
    public abstract void onTaskAddedToGroup(Group group, Task task);
    public abstract void onTaskTakenInGroup(Group group, Task task, User user);
    public abstract void onTaskFinishedInGroup(Group group, Task task);
}

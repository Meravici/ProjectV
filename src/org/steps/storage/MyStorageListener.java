package org.steps.storage;

import android.content.Context;
import org.steps.adapters.GroupAdapter;
import org.steps.app.objects.Group;
import org.steps.app.objects.Task;
import org.steps.app.objects.User;
import org.steps.utils.Globals;

import java.util.ArrayList;

/**
 * Created by Xato on 12/20/2014.
 */
public class MyStorageListener extends StorageListener {
    private Context context;
    public MyStorageListener(Context context){
        this.context = context;
    }

    @Override
    public void onUserLoggedIn() {
        //TODO
    }

    @Override
    public void onGroupCreated(Group group) {

        Globals.GROUPS.add(group);
        if(Globals.GROUPLIST != null){
            GroupAdapter adapter = new GroupAdapter(Globals.MAIN_ACTIVITY, Globals.GROUPS);
            Globals.GROUPLIST.setAdapter(adapter);
        }
    }

    @Override
    public void onUserAddedToGroup(Group group, User user) {
        for(Group cur : Globals.GROUPS){
            if(cur.getId() == group.getId()){
                cur.addUser(user);
            }
        }
    }

    @Override
    public void onUserLeftGroup(Group group, User user) {
        for(Group cur : Globals.GROUPS){
            if(cur.getId() == group.getId())
                cur.removeUser(user);
        }
    }

    @Override
    public void onTaskAddedToGroup(Group group, Task task) {
        for(Group cur : Globals.GROUPS){
            if(cur.getId() == group.getId())
                cur.addTask(task);
        }
    }

    @Override
    public void onTaskTakenInGroup(Group group, Task task, User user) {
        for(Group cur : Globals.GROUPS){
            if(cur.getId() == group.getId()) {
                ArrayList<Task> tasks = cur.getTasks();
                for(Task ct : tasks) {
                    if(ct.getId() == task.getId()){
                        ct.setVolunteer(user);
                        ct.setStatus(Task.STATUS_STARTED);
                    }
                }
            }
        }
    }

    @Override
    public void onTaskFinishedInGroup(Group group, Task task) {
        for(Group cur : Globals.GROUPS){
            if(cur.getId() == group.getId()) {
                ArrayList<Task> tasks = cur.getTasks();
                for(Task ct : tasks) {
                    if(ct.getId() == task.getId()){
                        ct.setStatus(Task.STATUS_FINISHED);
                    }
                }
            }
        }
    }

}

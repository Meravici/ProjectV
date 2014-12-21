package org.steps.storage;

import android.app.Activity;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import org.steps.app.objects.Group;
import org.steps.app.objects.Task;

/**
 * Created by Xato on 12/20/2014.
 */
public class StorageWriter {
    private StorageListener listener;
    private Activity activity;
    private Gson gson;

    public StorageWriter(StorageListener listener, Activity activity){
        this.listener = listener;
        this.activity = activity;
        this.gson = new Gson();
    }

    public void login(String googleID, String userData) {
        SharedPreferences settings = activity.getSharedPreferences("user" + googleID, 0);
        settings.edit().putString("user" + googleID, userData);
        settings.edit().commit();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.onUserLoggedIn();
            }
        });
    }

    public void createNewGroup(int groupID, final String groupData) {
        SharedPreferences settings = activity.getSharedPreferences("groupArr", 0);
        String oldString = settings.getString("groupArr", "");
        oldString += groupID+",";
        settings.edit().putString("groupArr", oldString);
        settings.edit().commit();

        activity.getSharedPreferences("group" + Integer.toString(groupID), 0);
        settings.edit().putString("group" + Integer.toString(groupID), groupData);
        settings.edit().commit();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.onGroupCreated(gson.fromJson(groupData, Group.class));
            }
        });
    }

    public void addUserToGroup(int groupID, final String groupData){
        SharedPreferences settings = activity.getSharedPreferences("groupArr", 0);
        String oldString = settings.getString("groupArr", "");
        String[] arr = oldString.split(",");
        for(int i=0;i<arr.length;i++){
            if(arr[i].length()>0)
                if (Integer.parseInt(arr[i]) == groupID)
                    return;
        }
        oldString += groupID+",";
        settings.edit().putString("groupArr", oldString);
        settings.edit().commit();

        activity.getSharedPreferences("group" + Integer.toString(groupID), 0);
        settings.edit().putString("group" + Integer.toString(groupID), groupData);
        settings.edit().commit();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Group group = gson.fromJson(groupData, Group.class);
                listener.onUserAddedToGroup(group, group.getLastUser());
            }
        });
    }
    public void removeUserFromGroup(int groupID, String groupData){
        SharedPreferences settings = activity.getSharedPreferences("group" + Integer.toString(groupID), 0);
        settings.edit().putString("group" + Integer.toString(groupID), groupData);
        settings.edit().commit();

        final Group group = gson.fromJson(groupData, Group.class);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.onUserLeftGroup(group, group.getLastUser());
            }
        });
    }

    public void AddTaskToGroup(int groupID, String groupData){
        SharedPreferences settings = activity.getSharedPreferences("group" + Integer.toString(groupID), 0);
        settings.edit().putString("group" + Integer.toString(groupID), groupData);
        settings.edit().commit();

        final Group group = gson.fromJson(groupData, Group.class);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.onTaskAddedToGroup(group, group.getLastTask());
            }
        });

    }

    public void setTaskStartStatus(int groupID, String groupData){
        SharedPreferences settings = activity.getSharedPreferences("group" + Integer.toString(groupID), 0);
        settings.edit().putString("group" + Integer.toString(groupID), groupData);
        settings.edit().commit();

        final Group group = gson.fromJson(groupData, Group.class);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.onTaskTakenInGroup(group, group.getLastTask(), group.getLastTask().getVolunteer());
            }
        });
    }

    public void setTaskFinishedStatus(int groupID, String groupData){
        SharedPreferences settings = activity.getSharedPreferences("group" + Integer.toString(groupID), 0);
        settings.edit().putString("group" + Integer.toString(groupID), groupData);
        settings.edit().commit();
    }
}

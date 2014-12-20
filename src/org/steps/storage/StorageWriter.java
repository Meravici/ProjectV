package org.steps.storage;

import android.app.Activity;
import android.content.SharedPreferences;

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

    public void createNewGroup(int groupID, String groupData) {
        SharedPreferences settings = activity.getSharedPreferences("group" + Integer.toString(groupID), 0);
        settings.edit().putString("group" + Integer.toString(groupID), groupData);
        settings.edit().commit();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.onGroupCreated();
            }
        });
    }

    public void addUserToGroup(int groupID, String groupData){
        SharedPreferences settings = activity.getSharedPreferences("group" + Integer.toString(groupID), 0);
        settings.edit().putString("group" + Integer.toString(groupID), groupData);
        settings.edit().commit();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.onUserAddedToGroup();
            }
        });

    }
    public void removeUserFromGroup(int groupID, String groupData){
        SharedPreferences settings = activity.getSharedPreferences("group" + Integer.toString(groupID), 0);
        settings.edit().putString("group" + Integer.toString(groupID), groupData);
        settings.edit().commit();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.onUserLeftGroup();
            }
        });
    }

    public void AddTaskToGroup(int groupID, String groupData){
        SharedPreferences settings = activity.getSharedPreferences("group" + Integer.toString(groupID), 0);
        settings.edit().putString("group" + Integer.toString(groupID), groupData);
        settings.edit().commit();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.onTaskAddedToGroup();
            }
        });
    }

    public void setTaskStartStatus(int groupID, String groupData){
        SharedPreferences settings = activity.getSharedPreferences("group" + Integer.toString(groupID), 0);
        settings.edit().putString("group" + Integer.toString(groupID), groupData);
        settings.edit().commit();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.onTaskTakenInGroup();
            }
        });
    }

    public void setTaskFinishedStatus(int groupID, String groupData){
        SharedPreferences settings = activity.getSharedPreferences("group" + Integer.toString(groupID), 0);
        settings.edit().putString("group" + Integer.toString(groupID), groupData);
        settings.edit().commit();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.onTaskFinishedInGroup();
            }
        });
    }

}

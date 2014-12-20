package org.steps.storage;


import android.app.Activity;
import android.content.SharedPreferences;
import org.steps.app.objects.Group;

import java.util.ArrayList;

/**
 * Created by Xato on 12/20/2014.
 */
public class StorageReader {
    private StorageListener listener;
    private Activity activity;

    public StorageReader(StorageListener listener, Activity activity) {
        this.listener = listener;
        this.activity = activity;
    }

    public String getGroupData(int groupID) {
        SharedPreferences settings = activity.getSharedPreferences("group" + Integer.toString(groupID), 0);
        return settings.getString("group" + Integer.toString(groupID), "");
    }

    public ArrayList<Group> getMyGroups(){

        //Open storage and get data
        return null;
    }
}

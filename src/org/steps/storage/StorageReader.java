package org.steps.storage;


import android.app.Activity;
import android.content.SharedPreferences;
import com.google.gson.Gson;
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
        ArrayList<Group> output = new ArrayList<Group>();
        SharedPreferences settings = activity.getSharedPreferences("groupArr", 0);
        String oldString = settings.getString("groupArr", "");
        String[] arr = oldString.split(",");

        for(int i=0;i<arr.length;i++){
            if(arr[i].length()>0) {
                settings = activity.getSharedPreferences("group"+arr[i].trim(), 0);
                output.add(new Gson().fromJson(settings.getString("group"+arr[i].trim(),""),Group.class));
            }
        }
        return output;
    }
}

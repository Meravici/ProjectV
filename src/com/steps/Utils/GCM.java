package com.steps.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.steps.Objects.GroupObject;
import com.steps.Objects.TaskObject;
import com.steps.Objects.UserObject;

/**
 * Created by Rati on 20/12/14.
 */


public class GCM extends GCMBaseIntentService {
    private static final String PROJECT_ID = "rich-suprstate-800";

    private static final String TAG = "GCMIntentService";
    public GCM(){
        super(PROJECT_ID);
        Log.d(TAG, "GCMIntentService init");
    }
    @Override
    protected void onMessage(Context context, Intent intent) {
        JsonParser json = new JsonParser();
        com.google.gson.JsonObject tmp =(com.google.gson.JsonObject) json.parse(context.toString());
        JsonElement result = tmp.get("type");
        int typecode = result.getAsInt();
        switch (typecode){
                case 0://ახალი იუზერი დაემატა ჯგუფს
                    updateGroupCache(tmp.get("Group_ID").getAsInt(),new Gson().fromJson(tmp.get("userObject"),UserObject.class));
                break;
            case 1://ახალ ჯგუფში გაგაწევრიანეს
                    updateGroupCache_create(new Gson().fromJson(tmp.get("Group"),GroupObject.class);
                break;
            case 2://ტასკის დამატება
                updateTaskCache_add(new Gson().fromJson(tmp.get("Task"),TaskObject.class));
                break;
            case 3://ტასკის შესრულებაზე ვოლუნთეარი
                updateTaskCache_volunteer(new Gson().fromJson(tmp.get("Task"), TaskObject.class), new Gson().fromJson(tmp.get("Valunteer"), UserObject.class));
                break;
            case 4://
                break;
        }
    }
    final static String groupCache="groupCache";
    final static String taskCache="taskCache";
    final static String mainUserCache="mainUserCache";

    private void updateGroupCache(int groupID,UserObject usr){
        SharedPreferences settings = getSharedPreferences(groupCache, 0);
        GroupObject grp = new Gson().fromJson(settings.getString("" + groupID, "{}"), GroupObject.class);
        grp.addUser(usr);
        settings.edit().putString(""+groupID,new Gson().toJson(grp));
    }
    private void updateGroupCache_create(GroupObject grp){
        SharedPreferences settings = getSharedPreferences(groupCache, 0);
        settings.edit().putString(""+grp.getId(),new Gson().toJson(grp));
    }
    private  void updateTaskCache_add(TaskObject task){
        SharedPreferences settings = getSharedPreferences(taskCache, 0);
        settings.edit().putString(String.valueOf(task.getId()),new Gson().toJson(task));
    }
    private void updateTaskCache_volunteer(TaskObject task, UserObject usr){
        SharedPreferences settings = getSharedPreferences(taskCache,0);
        task.setAuthor(usr);
        settings.edit().putString(String.valueOf(task.getId()),new Gson().toJson(task));
    }
    @Override
    protected void onError(Context context, String s) {

    }

    @Override
    protected void onRegistered(Context context, String s) {

    }

    @Override
    protected void onUnregistered(Context context, String s) {

    }
}

package org.steps.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.steps.app.objects.Group;
import org.steps.storage.StorageWriter;

/**
 * Created by Rati on 20/12/14.
 */
public class GCM extends GCMBaseIntentService {
    private static final String PROJECT_ID = "rich-suprstate-800";

    private static final String TAG = "GCMIntentService";
    StorageWriter storage;

    public GCM(StorageWriter storage){
        super(PROJECT_ID);
        this.storage=storage;
        Log.d(TAG, "GCMIntentService init");
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        JsonParser json = new JsonParser();
        com.google.gson.JsonObject tmp =(com.google.gson.JsonObject) json.parse(message);
        JsonElement result = tmp.get("type");
        switch (result.getAsInt()){
            case 0:
                Group grp=new Gson().fromJson(tmp.get("rest").getAsJsonObject(),Group.class);
                storage.createNewGroup(grp.getId(), tmp.get("rest").getAsString());
                break;
            case 1:
                grp=new Gson().fromJson(tmp.get("rest").getAsJsonObject(),Group.class);
                storage.addUserToGroup(grp.getId(),tmp.get("rest").getAsString());
                break;
            case 2:
                grp=new Gson().fromJson(tmp.get("rest").getAsJsonObject(),Group.class);
                storage.removeUserFromGroup(grp.getId(), tmp.get("rest").getAsString());
                break;
            case 3:
                grp=new Gson().fromJson(tmp.get("rest").getAsJsonObject(),Group.class);
                storage.AddTaskToGroup(grp.getId(),tmp.get("rest").getAsString());
                break;
            case 4:
                grp=new Gson().fromJson(tmp.get("rest").getAsJsonObject(),Group.class);
                storage.setTaskStartStatus(grp.getId(), tmp.get("rest").getAsString());
                break;
            case 5:
                grp=new Gson().fromJson(tmp.get("rest").getAsJsonObject(),Group.class);
                storage.setTaskFinishedStatus(grp.getId(),tmp.get("rest").getAsString());
                break;
        }

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

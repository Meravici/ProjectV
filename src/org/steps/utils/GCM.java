package org.steps.utils;

import android.app.IntentService;
import android.content.Intent;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.steps.app.objects.Group;

/**
 * Created by Rati on 20/12/14.
 */
public class GCM extends IntentService {
    private static final String PROJECT_ID = "rich-suprstate-800";

    public GCM() {
        super("GcmIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String message = intent.getStringExtra("message");
        JsonParser json = new JsonParser();
        com.google.gson.JsonObject tmp =(com.google.gson.JsonObject) json.parse(message);
        JsonElement result = tmp.get("type");
        switch (result.getAsInt()){
            case 0:
                Group grp=new Gson().fromJson(tmp.get("rest").getAsJsonObject(),Group.class);
                startGCM.storage.createNewGroup(grp.getId(), tmp.get("rest").getAsString());
                break;
            case 1:
                grp=new Gson().fromJson(tmp.get("rest").getAsJsonObject(),Group.class);
                startGCM.storage.addUserToGroup(grp.getId(), tmp.get("rest").getAsString());
                break;
            case 2:
                grp=new Gson().fromJson(tmp.get("rest").getAsJsonObject(),Group.class);
                startGCM.storage.removeUserFromGroup(grp.getId(), tmp.get("rest").getAsString());
                break;
            case 3:
                grp=new Gson().fromJson(tmp.get("rest").getAsJsonObject(),Group.class);
                startGCM.storage.AddTaskToGroup(grp.getId(), tmp.get("rest").getAsString());
                break;
            case 4:
                grp=new Gson().fromJson(tmp.get("rest").getAsJsonObject(),Group.class);
                startGCM.storage.setTaskStartStatus(grp.getId(), tmp.get("rest").getAsString());
                break;
            case 5:
                grp=new Gson().fromJson(tmp.get("rest").getAsJsonObject(),Group.class);
                startGCM.storage.setTaskFinishedStatus(grp.getId(), tmp.get("rest").getAsString());
                break;
        }
    }
}

package org.steps.smsClient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.steps.app.objects.Group;
import org.steps.storage.StorageWriter;

/**
 * Created by Rati on 20/12/14.
 */
public class smsModule extends BroadcastReceiver {
    final static int taskStatusPending = 2;
    final static int taskStatusFinished = 3;

    final static String serverPhone = "+995558677895";

    StorageWriter storage;

    public smsModule(StorageWriter storage){
        this.storage=storage;
    }
    public void sendsms(String body){
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(serverPhone,null,body,null,null);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try{
            if(bundle == null)
                return;
            final Object[] pdusObj = (Object[]) bundle.get("pdus");
            SmsMessage[] msgs = new SmsMessage[pdusObj.length];
            for(int i=0;i<msgs.length;i++){
                if(msgs[i].getOriginatingAddress().equals(serverPhone)){
                    String message = msgs[i].getMessageBody();
                    JsonObject messageJson = new JsonParser().parse(message).getAsJsonObject();

                    Group group;
                    switch(messageJson.get("command").getAsInt()){
                        case 0://createNewGroup
                            group = new Gson().fromJson(messageJson.get("group").getAsString(),Group.class);
                            storage.createNewGroup(group.getId(),messageJson.get("group").getAsString());
                            break;
                        case 1://addUserToGroup(int groupID, String groupData)
                            group = new Gson().fromJson(messageJson.get("group").getAsString(),Group.class);
                            storage.addUserToGroup(group.getId(), messageJson.get("group").getAsString());
                            break;
                        case 2://removeUserFromGroup(int groupID, String groupData)
                            group = new Gson().fromJson(messageJson.get("group").getAsString(),Group.class);
                            storage.removeUserFromGroup(group.getId(), messageJson.get("group").getAsString());
                            break;
                        case 3://AddTaskToGroup(int groupID, String groupData)
                            group = new Gson().fromJson(messageJson.get("group").getAsString(),Group.class);
                            storage.AddTaskToGroup(group.getId(), messageJson.get("group").getAsString());
                            break;
                        case 4://setTaskStartStatus(int groupID, String groupData)
                            group = new Gson().fromJson(messageJson.get("group").getAsString(),Group.class);
                            storage.setTaskStartStatus(group.getId(), messageJson.get("group").getAsString());
                            break;
                        case 5://setTaskFinishedStatus(int groupID, String groupData)
                            group = new Gson().fromJson(messageJson.get("group").getAsString(),Group.class);
                            storage.setTaskFinishedStatus(group.getId(), messageJson.get("group").getAsString());
                            break;
                    }
                }
            }
        } catch (Exception e){

        }
    }
}

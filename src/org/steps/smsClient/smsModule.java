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
import org.steps.app.objects.Task;
import org.steps.app.objects.User;
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
                    switch(messageJson.get("command").getAsInt()){
                        case 0://addUserToGroup
                            User user = new Gson().fromJson(messageJson.get("user").getAsString(),User.class);
                            Group group = new Gson().fromJson(messageJson.get("group").getAsString(),Group.class);
                            storage.addUserToGroup(User,Group);
                            break;
                        case 1://removeUserFromGroup
                            User user = new Gson().fromJson(messageJson.get("user").getAsString(),User.class);
                            Group group = new Gson().fromJson(messageJson.get("group").getAsString(),Group.class);
                            storage.removeUserFromGroup(user,group);
                            break;
                        case 2://AddTaskToGroup
                            Task task = new Gson().fromJson(messageJson.get("task").getAsString(),Task.class);
                            Group group = new Gson().fromJson(messageJson.get("group").getAsString(),Group.class);
                            storage.AddTaskToGroup(task,group);
                            break;
                        case 3://SetTaskPendingStatus
                            Task task =  new Gson().fromJson(messageJson.get("task").getAsString(),Task.class);
                            storage.SetTaskPendingStatus(task,Task.STATUS_PENDING);
                            break;
                        case 4://SetTaskFinishedStatus
                            storage.SetTaskFinishedStatus(task,Task.STATUS_FINISHED);
                            break;
                    }
                }
            }
        } catch (Exception e){

        }
    }
}

package org.steps.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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
        String message = intent.getStringExtra("message");
        JsonParser json = new JsonParser();
        com.google.gson.JsonObject tmp =(com.google.gson.JsonObject) json.parse(message);
        JsonElement result = tmp.get("type");
        switch (result.getAsInt()){
            case 0:
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

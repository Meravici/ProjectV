package com.steps.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;

/**
 * Created by Rati on 20/12/14.
 */


public class GCM extends GCMBaseIntentService {
    private static final String PROJECT_ID = "YOUR_PROJECT_ID";

    private static final String TAG = "GCMIntentService";
    public GCM(){
        super(PROJECT_ID);
        Log.d(TAG, "GCMIntentService init");
    }
    @Override
    protected void onMessage(Context context, Intent intent) {


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

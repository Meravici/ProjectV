package org.steps.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import org.steps.app.Activities.ProjectV;
import org.steps.storage.StorageWriter;

import java.io.IOException;

/**
 * Created by Rati on 20/12/14.
 */
public class startGCM {
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static String SENDER_ID = "947695180777";

    private Activity myActivity;
    private Context context;
    public static StorageWriter storage;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    GoogleCloudMessaging gcm;

    public startGCM (Activity myActivity,StorageWriter storage,Context context){
        this.myActivity= myActivity;
        this.storage=storage;
        this.context=context;
        registerClient();
    }
    public void registerClient() {
        if(checkPlayServices()){
            gcm=GoogleCloudMessaging.getInstance(myActivity);
            Globals.USER_REG_ID = getRegistrationId(context);
            if(Globals.USER_REG_ID.isEmpty()){
                registerInBackground();
            }
        }
    }
    private void registerInBackground() {
//        new AsyncTask<Void,Void,Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    Globals.USER_REG_ID = gcm.register(SENDER_ID);
                    storeRegistrationId(context, Globals.USER_REG_ID);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
//                return null;
//            }

//        }.execute(null, null, null);
    }
    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return myActivity.getSharedPreferences(ProjectV.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            return "";
        }
        return registrationId;
    }
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
    private SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return myActivity.getSharedPreferences(ProjectV.class.getSimpleName(),Context.MODE_PRIVATE);
    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, myActivity,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }
}

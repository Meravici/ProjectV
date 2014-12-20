package com.steps.Facade;

/**
 * Created by Rati on 20/12/14.
 */

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.steps.Exceptions.ServerErrorException;
import com.steps.Objects.GroupObject;
import com.steps.Objects.UserObject;
import com.steps.ProjectV.ProjectV;
import com.steps.Utils.Mediator;
import com.steps.Utils.MediatorAPI;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Timer;

public class FacadeMain implements FacadeAPI {

    private MediatorAPI mediator;
    private Activity myActivity;

    public FacadeMain(MediatorAPI mediator,Activity myActivity){
        this.mediator = mediator;
        this.myActivity=myActivity;
    }

    private class login_user_async extends AsyncTask<String,Integer, Void> {
        @Override
        protected Void doInBackground(String ... params) {
            try {
                HttpGet httpRequest = new HttpGet();
                HttpClient httpClient=new DefaultHttpClient();
                httpRequest.setURI(new URI(params[0]));
                httpClient.execute(httpRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Long result){

        }
    }
    private class register_group_async extends AsyncTask<GroupObject,Integer, Void> {
        @Override
        protected Void doInBackground(GroupObject ... params){
            try {
                mediator.insertGroup(params[0]);
            }catch (Exception e){
                somethingWentWrong();
            }
            return null;
        }
        protected void somethingWentWrong(){

        }
        protected void onPostExecute(Long result){

        }
    }
    private class add_to_group_async extends AsyncTask<String,Integer, Void> {
        @Override
        protected Void doInBackground(String ... params) {
            try {
                HttpGet httpRequest = new HttpGet();
                HttpClient httpClient=new DefaultHttpClient();
                httpRequest.setURI(new URI(params[0]));
                httpClient.execute(httpRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Long result){

        }
    }
    public UserObject loginUser(String id) throws ServerErrorException {
        login_user_async tmp = new login_user_async();
        tmp.execute(id);
        return null;
    }
    public void registerGroup(GroupObject group){
        register_group_async tmp = new register_group_async();
        tmp.execute(group);
    }
    public void addToGroup(GroupObject group, UserObject me){

    }
}

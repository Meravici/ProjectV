package com.steps.Facade;

import com.steps.Exceptions.ServerErrorException;
import com.steps.Objects.*;

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
import java.security.acl.Group;
import java.util.List;
import java.util.Timer;

public class FacadeMain implements FacadeAPI {

    private MediatorAPI mediator;
    private ProjectV myActivity;

    public FacadeMain(MediatorAPI mediator,ProjectV myActivity){
        this.mediator = mediator;
        this.myActivity = myActivity;
    }

    private class login_user_async extends AsyncTask<String,Integer, Void> {
        GroupObject[] grps;
        UserObject usr;
        @Override
        protected Void doInBackground(String ... params) {
            try {
                this.usr = mediator.getUser(params[0]);
                System.out.println(this.usr.getGoogleID());
                this.grps = mediator.getGroups(this.usr);
            } catch (ServerErrorException e) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myActivity.failureCallback();
                    }

                });
            }
                return null;
        }
        protected void onPostExecute(Long result){
            final UserObject users = this.usr;
            final GroupObject[] groups = this.grps;
            myActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myActivity.successCallback(users, groups);
                }
            });

        }
    }
    private class register_group_async extends AsyncTask<GroupObject,Integer, Void> {
        @Override
        protected Void doInBackground(GroupObject ... params){
            try {
                mediator.insertGroup(params[0]);
            }catch (Exception e){

            }
            return null;
        }
        protected void onPostExecute(Long result){

        }
    }
    private class add_to_group_async extends AsyncTask<Object,Integer, Void> {
        @Override
        protected Void doInBackground(Object ... params) {
            try {
                mediator.addUserToGroup((GroupObject) params[0],(UserObject) params[1]);
            } catch (Exception e) {

            }
            return null;
        }
        protected void onPostExecute(Long result){

        }
    }
    private class insert_task_async extends AsyncTask<TaskObject,Integer, Void>{
        @Override
        protected  Void doInBackground(TaskObject ... params){
            try{
                mediator.insertTask(params[0]);
            }catch (Exception e){

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
        add_to_group_async tmp = new add_to_group_async();
        tmp.execute(group,me);
    }

    public void insertTask(TaskObject tsk){
        insert_task_async tmp = new insert_task_async();
        tmp.execute(tsk);
    }
}

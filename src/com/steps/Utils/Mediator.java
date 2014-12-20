package com.steps.Utils;

import com.google.gson.Gson;
import com.steps.Exceptions.ServerErrorException;
import com.steps.Objects.GroupObject;
import com.steps.Objects.TaskObject;
import com.steps.Objects.UserObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URI;

/**
 * Created by Alex on 12/20/2014.
 */
public class Mediator implements MediatorAPI {
    private Gson gson;
    private HttpClient httpClient;

    public Mediator(){
        gson = new Gson();
        httpClient = new DefaultHttpClient();
    }

    public void insertUser(UserObject userObject) throws ServerErrorException {
        String jsonUser = gson.toJson(userObject);
        sendData("/android/user/set" + jsonUser);
    }

    public UserObject getUser(String googleID) throws ServerErrorException {
        sendData("/android/user/get" + googleID);
        // TODO get dara from GCM server
        return null;
    }

    public void insertGroup(GroupObject groupObject) throws ServerErrorException {
        String jsonGroup = gson.toJson(groupObject);
        sendData("/android/group/set" + jsonGroup);
    }

    public GroupObject getGroup(int groupID) throws ServerErrorException {
        sendData("/android/group/get" + Integer.toString(groupID));
        // TODO get dara from GCM server
        return null;
    }

    public void insertTask(TaskObject taskObject) throws ServerErrorException {
        String jsonTask = gson.toJson(taskObject);
        sendData("android/task/set" + jsonTask);
    }


    public TaskObject getTask(int taskID) throws ServerErrorException {
        sendData("android/task/get" + Integer.toString(taskID));
        // TODO get dara from GCM server
        return null;
    }

    private void sendData(String jsonString) throws ServerErrorException {
        try {
            HttpGet httpRequest = new HttpGet();
            httpRequest.setURI(new URI("http:/192.168.84.157:8888" + jsonString));
            httpClient.execute(httpRequest);
            // TODO test 404
        } catch (Exception e) {
            throw new ServerErrorException();
        }
    }

}

package com.steps.Utils;

import com.google.gson.Gson;
import com.steps.Exceptions.ServerErrorException;
import com.steps.Objects.GroupObject;
import com.steps.Objects.TaskObject;
import com.steps.Objects.UserObject;
import org.apache.http.HttpConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;

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
        sendData("/android/user/add/" + jsonUser);
    }

    public UserObject getUser(String googleID) throws ServerErrorException {
        String data = sendData("/android/user/get/" + googleID);
        UserObject user = gson.fromJson(data, UserObject.class);
        return user;
    }

    public void addUser(GroupObject groupObject, UserObject userObject) throws ServerErrorException {
        groupObject.addUser(userObject);
        sendData("/android/group/user/" + Integer.toString(groupObject.getId()) + "/" + userObject.getGoogleID());
    }

    public void insertGroup(GroupObject groupObject) throws ServerErrorException {
        String jsonGroup = gson.toJson(groupObject);
        String groupID = sendData("/android/group/add/" + jsonGroup);
        groupObject.setId(Integer.parseInt(groupID));
    }

    public GroupObject getGroup(int groupID) throws ServerErrorException {
        String data = sendData("/android/group/get/" + Integer.toString(groupID));
        GroupObject group = gson.fromJson(data, GroupObject.class);
        return group;
    }

    public void insertTask(TaskObject taskObject) throws ServerErrorException {
        String jsonTask = gson.toJson(taskObject);
        String taskID = sendData("/android/task/add/" + jsonTask);
        taskObject.setId(Integer.parseInt(taskID));
    }


    public TaskObject getTask(int taskID) throws ServerErrorException {
        String data = sendData("/android/task/get/" + Integer.toString(taskID));
        TaskObject task = gson.fromJson(data, TaskObject.class);
        return task;
    }

    public void addTask(GroupObject groupObject, TaskObject taskObject) throws ServerErrorException {
        groupObject.addTask(taskObject);
        sendData("/android/group/task/" + Integer.toString(groupObject.getId()) + "/" + Integer.toString(taskObject.getId()));
    }

    public GroupObject[] getGroups(UserObject usr) throws ServerErrorException {
        String data = sendData("/android/user/groups/" + usr.getGoogleID());
        GroupObject[] groups = gson.fromJson(data, GroupObject[].class);
        return groups;
    }

    private String sendData(String jsonString) throws ServerErrorException {
        try {

            HttpURLConnection cn = (HttpURLConnection) new URL("http://192.168.84.157/:8888" + jsonString).openConnection();
            cn.setRequestMethod("GET");
            int responseCode = cn.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            InputStream response = cn.getInputStream();





//            HttpGet httpRequest = new HttpGet();
//            httpRequest.setURI(new URI("http:/192.168.84.157:8888" + jsonString));
//            HttpResponse response = httpClient.execute(httpRequest);

            if (responseCode == 200) {
                BufferedReader r = new BufferedReader(new InputStreamReader(response));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }
                return total.toString();
            }
            // TODO test 404
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        } catch (Exception e) {
//            throw new ServerErrorException();
//        }
        return null;
    }

}

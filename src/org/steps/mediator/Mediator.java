package org.steps.mediator;

import com.google.gson.Gson;
import org.steps.app.objects.Group;
import org.steps.app.objects.Task;
import org.steps.app.objects.User;
import org.steps.storage.StorageReader;
import org.steps.storage.StorageWriter;
import org.steps.utils.ServerErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Timestamp;
import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Xato on 12/20/2014.
 */
public class Mediator implements MediatorAPI {
    private static final String SERVER = "http://192.168.84.157/:8888";

    private Gson gson;
    private StorageWriter storageWriter;
    private StorageReader storageReader;

    public Mediator(StorageReader storageReader, StorageWriter storageWriter) {
        this.storageWriter = storageWriter;
        this.gson = new Gson();
    }

    @Override
    public void login(String googleID, String phoneNumber) throws ServerErrorException {
        User user = new User(googleID, phoneNumber);
        String userData = gson.toJson(user);
        sendData("/android/user/add/" + userData);

        storageWriter.login(googleID, userData);
    }

    @Override
    public void createNewGroup(String name, int imageID) throws ServerErrorException {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        Group group = new Group(0, name, currentTimestamp, imageID);
        String groupData = gson.toJson(group);
        String response = sendData("/android/group/add/" + groupData);
        group.setId(Integer.parseInt(response));

        storageWriter.createNewGroup(group.getId(), gson.toJson(group));
    }

    @Override
    public void addUserToGroup(String phoneNumber, int groupID) throws ServerErrorException {
        Group group = gson.fromJson(storageReader.getGroupData(groupID), Group.class);
        User user = gson.fromJson(sendData("android/user/getifexists/" + phoneNumber), User.class);

        group.addUser(user);

        sendData("/android/group/user/" + group.getId() + "/" + user.getGoogleID());

        storageWriter.addUserToGroup(groupID, gson.toJson(group));
    }

    @Override
    public void leaveGroup(int groupID, String myID) throws ServerErrorException {

    }



    private void insertTask(Task taskObject) throws ServerErrorException {
        String jsonTask = gson.toJson(taskObject);
        String taskID = sendData("/android/task/add/" + jsonTask);
        taskObject.setId(Integer.parseInt(taskID));
    }


    private void addTask(Group groupObject, Task taskObject) throws ServerErrorException {
        groupObject.addTask(taskObject);
        sendData("/android/group/task/" + Integer.toString(groupObject.getId()) + "/" + Integer.toString(taskObject.getId()));
    }

    @Override
    public void addTaskToGroup(String title, Timestamp dueDate, User author, int group_id) throws ServerErrorException {
        Task task = new Task(0,title,null, Date.valueOf(dueDate.toString()),0,author, null); //TODO

        insertTask(task);

        Group group = gson.fromJson(storageReader.getGroupData(group_id), Group.class);
        addTask(group ,task);
    }

    @Override
    public void takeTaskInGroup() throws ServerErrorException {

    }

    @Override
    public void finishTaskInGroup() throws ServerErrorException {

    }


    private String sendData(String jsonString) throws ServerErrorException {
        try {
            HttpURLConnection cn = (HttpURLConnection) new URL(SERVER + jsonString).openConnection();
            cn.setRequestMethod("GET");
            int responseCode = cn.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            InputStream response = cn.getInputStream();

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
        return null;
    }
}
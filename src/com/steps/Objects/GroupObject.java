package com.steps.Objects;

import com.google.gson.internal.bind.ArrayTypeAdapter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 12/19/2014.
 */
public class GroupObject implements Serializable {

    private int id;
    private String name;
    private Timestamp lastUpdate;
    private short imageID;
    private ArrayList<UserObject> userObjects;
    private ArrayList<TaskObject> taskObjects;

    public GroupObject(int id, String name, Timestamp lastUpdate, short imageID) {
        this.id = id;
        this.name = name;
        this.lastUpdate = lastUpdate;
        this.imageID = imageID;
        this.userObjects = new ArrayList<UserObject>();
        this.taskObjects = new ArrayList<TaskObject>();
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public short getImageID() {
        return imageID;
    }

    public void setImageID(short imageID) {
        this.imageID = imageID;
    }

    public void addUser(UserObject userObject) {
        this.userObjects.add(userObject);
        // TODO sorted insert
    }

    public List<UserObject> getUserObjects() {
        return userObjects;
    }

    public void addTask(TaskObject taskObject) {
        this.taskObjects.add(taskObject);
        // TODO serted insert
    }

    public ArrayList<TaskObject> getTasks() {
        return taskObjects;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", users=" + userObjects +
                ", tasks=" + taskObjects +
                '}';
    }

    public int countPendingTasks() {
        int count = 0;
        for (TaskObject task : taskObjects)
            if (task.getStatus() == TaskObject.STATUS_PENDING)
                count++;

        return count;
    }
}

package com.steps.Objects;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alex on 12/19/2014.
 */
public class GroupObject implements Serializable {
    private int id;
    private String name;
    private Timestamp lastUpdate;
    private Set<UserObject> userObjects;
    private Set<TaskObject> taskObjects;

    public GroupObject(int id, String name, Timestamp lastUpdate) {
        this.id = id;
        this.name = name;
        this.lastUpdate = lastUpdate;
        this.userObjects = new HashSet<UserObject>();
        this.taskObjects = new HashSet<TaskObject>();
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

    public void addUser(UserObject userObject) {
        this.userObjects.add(userObject);
    }

    public void addTask(TaskObject taskObject) {
        this.taskObjects.add(taskObject);
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
}

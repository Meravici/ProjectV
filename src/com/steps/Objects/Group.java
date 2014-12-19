package com.steps.Objects;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alex on 12/19/2014.
 */
public class Group implements Serializable {
    private int id;
    private String name;
    private Timestamp lastUpdate;
    private Set<User> users;
    private Set<Task> tasks;

    public Group(int id, String name, Timestamp lastUpdate) {
        this.id = id;
        this.name = name;
        this.lastUpdate = lastUpdate;
        this.users = new HashSet<User>();
        this.tasks = new HashSet<Task>();
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

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", users=" + users +
                ", tasks=" + tasks +
                '}';
    }
}

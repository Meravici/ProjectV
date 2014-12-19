package com.steps.Objects;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alex on 12/19/2014.
 */
public class Group implements Serializable {
    private int id;
    private String name;
    private Set<User> users;
    private Set<Task> tasks;

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
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

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }
}

package org.steps.app.objects;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Xato on 12/20/2014.
 */
public class Group implements Serializable {
    private int id;
    private String name;
    private Timestamp lastUpdate;
    private int imageID;
    private ArrayList<User> users;
    private ArrayList<Task> tasks;

    public Group(int id, String name, Timestamp lastUpdate, int imageID) {
        this.users = new ArrayList<User>();
        this.tasks = new ArrayList<Task>();
        this.id = id;
        this.name = name;
        this.lastUpdate = lastUpdate;
        this.imageID = imageID;
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

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
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
                ", imageID=" + imageID +
                ", users=" + users +
                ", tasks=" + tasks +
                '}';
    }

    public int getTasksPending() {
        int count = 0;
        for (Task task : this.tasks)
            if (task.getStatus() == Task.STATUS_PENDING)
                count++;

        return count;
    }
}

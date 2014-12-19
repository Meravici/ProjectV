package com.steps.Utils;

import com.google.gson.Gson;
import com.steps.Objects.Group;
import com.steps.Objects.Task;
import com.steps.Objects.User;

/**
 * Created by Alex on 12/20/2014.
 */
public class Mediator implements MediatorAPI {
    private Gson gson;

    public Mediator(){
        gson = new Gson();
    }

    public void insertUser(User user) {
        String jsonUser = gson.toJson(user);
    }

    public User getUser(int googleID) {

    }

    public void insertGroup(Group group) {
        String jsonGroup = gson.toJson(group);
    }

    public Group getGroup(int groupID) {

    }

    public void insertTask(Task task) {
        String jsonTask = gson.toJson(task);
    }


    public Task getTask(int taskID) {

    }
}

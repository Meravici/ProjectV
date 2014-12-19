package com.steps.Utils;

import com.google.gson.Gson;
import com.steps.Objects.GroupObject;
import com.steps.Objects.TaskObject;
import com.steps.Objects.UserObject;

/**
 * Created by Alex on 12/20/2014.
 */
public class Mediator implements MediatorAPI {
    private Gson gson;

    public Mediator(){
        gson = new Gson();
    }

    public void insertUser(UserObject userObject) {
        String jsonUser = gson.toJson(userObject);
    }

    public UserObject getUser(int googleID) {
        return null;
    }

    public void insertGroup(GroupObject groupObject) {
        String jsonGroup = gson.toJson(groupObject);
    }

    public GroupObject getGroup(int groupID) {
        return null;
    }

    public void insertTask(TaskObject taskObject) {
        String jsonTask = gson.toJson(taskObject);
    }


    public TaskObject getTask(int taskID) {
        return null;
    }
}

package com.steps.Utils;

import com.steps.Exceptions.ServerErrorException;
import com.steps.Objects.GroupObject;
import com.steps.Objects.TaskObject;
import com.steps.Objects.UserObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 12/20/2014.
 */
public interface MediatorAPI {

    public void insertUser(UserObject userObject) throws ServerErrorException;

    public UserObject getUser(String googleID) throws ServerErrorException;

    public void addUser(GroupObject groupObject, UserObject userObject) throws ServerErrorException;

    public void insertGroup(GroupObject groupObject) throws ServerErrorException;

    public GroupObject getGroup(int groupID) throws ServerErrorException;

    public void insertTask(TaskObject taskObject) throws ServerErrorException;

    public TaskObject getTask(int taskID) throws ServerErrorException;

    public void addTask(GroupObject groupObject, TaskObject taskObject) throws ServerErrorException;

    public ArrayList<GroupObject> getGroups(UserObject usr) throws ServerErrorException;

    void addUserToGroup(GroupObject param, UserObject param1);
}

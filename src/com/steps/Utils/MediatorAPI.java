package com.steps.Utils;

import com.steps.Exceptions.ServerErrorException;
import com.steps.Objects.GroupObject;
import com.steps.Objects.TaskObject;
import com.steps.Objects.UserObject;

/**
 * Created by Alex on 12/20/2014.
 */
public interface MediatorAPI {

    public void insertUser(UserObject userObject) throws ServerErrorException;

    public UserObject getUser(String googleID);

    public void insertGroup(GroupObject groupObject) throws ServerErrorException;

    public GroupObject getGroup(int groupID);

    public void insertTask(TaskObject taskObject) throws ServerErrorException;

    public TaskObject getTask(int taskID);
}

package com.steps.Utils;

import com.steps.Objects.GroupObject;
import com.steps.Objects.TaskObject;
import com.steps.Objects.UserObject;

/**
 * Created by Alex on 12/20/2014.
 */
public interface MediatorAPI {

    public void insertUser(UserObject userObject);

    public UserObject getUser(int googleID);

    public void insertGroup(GroupObject groupObject);

    public GroupObject getGroup(int groupID);

    public void insertTask(TaskObject taskObject);

    public TaskObject getTask(int taskID);
}

package com.steps.Utils;

import com.steps.Objects.Group;
import com.steps.Objects.Task;
import com.steps.Objects.User;

/**
 * Created by Alex on 12/20/2014.
 */
public interface MediatorAPI {

    public void insertUser(User user);

    public User getUser(int googleID);

    public void insertGroup(Group group);

    public Group getGroup(int groupID);

    public void insertTask(Task task);

    public Task getTask(int taskID);
}

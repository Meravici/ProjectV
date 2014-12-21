package org.steps.mediator;

import org.steps.app.objects.Group;
import org.steps.app.objects.Task;
import org.steps.app.objects.User;
import org.steps.utils.ServerErrorException;

import java.security.Timestamp;

/**
 * Created by Xato on 12/20/2014.
 */
public interface MediatorAPI {
    public void login(String googleID, String phoneNumber, String reg_id) throws ServerErrorException;//Register too
    public void createNewGroup(String name, int imageID) throws ServerErrorException;//afterwards call addUserToGroup add timestamps //TODO
    public void addUserToGroup(String phoneNumber, int groupID) throws ServerErrorException;
    public void leaveGroup(int groupID, User me) throws ServerErrorException;
    public void addTaskToGroup(String title, Timestamp dueDate, User author, int group_id) throws ServerErrorException;
    public void takeTaskInGroup(Task task, Group group) throws ServerErrorException;
    public void finishTaskInGroup(Task task, Group group) throws ServerErrorException;
}

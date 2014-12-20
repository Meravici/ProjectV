package org.steps.mediator;

import org.steps.app.objects.User;
import org.steps.utils.ServerErrorException;

import java.security.Timestamp;

/**
 * Created by Xato on 12/20/2014.
 */
public interface MediatorAPI {
    public void login(String googleID, String phoneNumber) throws ServerErrorException;//Register too
    public void createNewGroup(String name, int imageID) throws ServerErrorException;//afterwards call addUserToGroup add timestamps //TODO
    public void addUserToGroup(String phoneNumber, int groupID) throws ServerErrorException;
    public void leaveGroup(int groupID, String myID) throws ServerErrorException;
    public void addTaskToGroup(String title, Timestamp dueDate, User author, int group_id) throws ServerErrorException;
    public void takeTaskInGroup() throws ServerErrorException;
    public void finishTaskInGroup() throws ServerErrorException;
}

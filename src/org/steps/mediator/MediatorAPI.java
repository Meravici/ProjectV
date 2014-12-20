package org.steps.mediator;

import java.security.Timestamp;

/**
 * Created by Xato on 12/20/2014.
 */
public interface MediatorAPI {
    public void login(String googleID);//Register too
    public void createNewGroup(String name, int imageID);//afterwards call addUserToGroup add timestamps //TODO
    public void addUserToGroup(String userID, int groupID);
    public void leaveGroup(int groupID, String myID);
    public void addTaskToGroup(String title, Timestamp dueDate); 
    public void takeTaskInGroup();
    public void finishTaskInGroup();
}

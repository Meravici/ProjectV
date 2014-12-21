package org.steps.app.Activities;

import org.steps.app.objects.Group;

import java.util.ArrayList;

/**
 * Created by Alex on 12/20/2014.
 */
public interface ConstructorAPI {
    public void getGroups();
    public void getGroupsCallback(ArrayList<Group> groups);
    public void login(String s);
    void registerGroup(String name);
}

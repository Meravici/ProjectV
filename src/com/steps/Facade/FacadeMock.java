package com.steps.Facade;

import android.os.AsyncTask;
import com.steps.Exceptions.ServerErrorException;
import com.steps.Objects.GroupObject;
import com.steps.Objects.TaskObject;
import com.steps.Objects.UserObject;
import com.steps.ProjectV.ProjectV;
import com.steps.Utils.MediatorAPI;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Rati on 20/12/14.
 */

public class FacadeMock implements FacadeAPI {
    private UserObject user;
    private ProjectV context;
    private ArrayList<GroupObject> groups = new ArrayList<GroupObject>();
    private TaskObject[] tasks = new TaskObject[5];
    public FacadeMock(ProjectV context){
        this.context = context;
        user = new UserObject("000000", "Gio");
        groups.add(new GroupObject(1,"ჩემი ჯგუფი", new Timestamp(1l), (short)1));

        for(int i=0; i<tasks.length; i++)
            tasks[i] = new TaskObject(i, "თეთრი პურია მოსაატანი", new Date(1), new Date(2), i, user, user);
    }
    @Override
    public UserObject loginUser(String id) throws ServerErrorException {
        context.successCallback(user, groups);
        return user;
    }

    @Override
    public void registerGroup(GroupObject group) {
        groups.add(group);
    }

    @Override
    public void addToGroup(GroupObject group, UserObject user) throws ServerErrorException {
        group.addUser(user);
    }
}

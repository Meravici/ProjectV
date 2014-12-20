package com.steps.Facade;

import android.accounts.NetworkErrorException;
import com.steps.Exceptions.ServerErrorException;
import com.steps.Objects.GroupObject;
import com.steps.Objects.UserObject;

/**
 * Created by Xato on 12/20/2014.
 */
public interface FacadeAPI {
    /*
     * Tries to login user, if not registered yet registers him and logins
     * Throws ServerErrorException and NotConnectedException
     */
    public UserObject loginUser(String id) throws ServerErrorException; //not connected to internet exception;

    /*
     * Registers new group
     */
    public void registerGroup(GroupObject group);

    /*
     * Adds user to Group
     */
    public void addToGroup(GroupObject group, UserObject user) throws ServerErrorException;
}


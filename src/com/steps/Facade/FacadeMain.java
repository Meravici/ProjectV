package com.steps.Facade;

import com.steps.Exceptions.ServerErrorException;
import com.steps.Objects.GroupObject;
import com.steps.Objects.UserObject;

/**
 * Created by Rati on 20/12/14.
 */
public class FacadeMain implements FacadeAPI {

    @Override
    public UserObject LoginUser(String id) throws ServerErrorException {
        return null;
    }

    @Override
    public void registerGroup(GroupObject group, UserObject user) {

    }

    @Override
    public void addToGroup(GroupObject group, String userId, UserObject me) {

    }
}

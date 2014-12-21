package org.steps.app.objects;

import java.io.Serializable;

/**
 * Created by Xato on 12/20/2014.
 */
public class User implements Serializable {
    private String googleID;

    private String phoneNumber;
    private String registrationID;

    public User(String googleID, String phoneNumber) {
        this.googleID = googleID;
        this.phoneNumber = phoneNumber;
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public String getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "googleID='" + googleID + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}

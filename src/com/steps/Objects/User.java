package com.steps.Objects;

import java.io.Serializable;

/**
 * Created by Alex on 12/19/2014.
 */
public class User implements Serializable {
    private String googleID;
    private String phoneNumber;

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

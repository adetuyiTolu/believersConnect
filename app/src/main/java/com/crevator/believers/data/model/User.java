package com.crevator.believers.data.model;

import java.io.Serializable;
import java.io.StringReader;

/**
 * Created by Slimfit on 1/23/2017.
 */

public class User implements Serializable {
    private int userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String pictureUrl;
    private String authToken;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    @Override
    public String toString() {
        return getFirstName()+"-"+getUserName();
    }
}

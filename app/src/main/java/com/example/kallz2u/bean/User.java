package com.example.kallz2u.bean;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class User {

    public String uID;

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.uID = uID;
    }

}
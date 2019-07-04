package com.urstrulygsw.chat.sampledata;

public class User {

    private int intId;
    private String strUsername;
    private String strImageUrl;


    public User() {
    }

    public User(int intId, String strUsername, String strImageUrl) {
        this.intId = intId;
        this.strUsername = strUsername;
        this.strImageUrl = strImageUrl;
    }

    public int getIntId() {
        return intId;
    }

    public String getStrUsername() {
        return strUsername;
    }

    public String getStrImageUrl() {
        return strImageUrl;
    }
}

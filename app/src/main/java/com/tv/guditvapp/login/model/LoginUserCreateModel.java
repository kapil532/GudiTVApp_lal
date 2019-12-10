package com.tv.guditvapp.login.model;

import com.google.gson.annotations.SerializedName;

public class LoginUserCreateModel {

    /**
     * screenUsername : screen1
     * screenPassword : screen1
     * screenName : This is the first screen
     * screenAddress : Chambenahalli Sarjapur
     */

    @SerializedName("screenUsername")
    private String screenUsername;
    @SerializedName("screenPassword")
    private String screenPassword;
    @SerializedName("screenName")
    private String screenName;
    @SerializedName("screenAddress")
    private String screenAddress;

    public String getScreenUsername() {
        return screenUsername;
    }

    public void setScreenUsername(String screenUsername) {
        this.screenUsername = screenUsername;
    }

    public String getScreenPassword() {
        return screenPassword;
    }

    public void setScreenPassword(String screenPassword) {
        this.screenPassword = screenPassword;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getScreenAddress() {
        return screenAddress;
    }

    public void setScreenAddress(String screenAddress) {
        this.screenAddress = screenAddress;
    }
}

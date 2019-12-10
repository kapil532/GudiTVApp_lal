package com.tv.guditvapp.login.model;

import com.google.gson.annotations.SerializedName;

public class ScreenModel {
    /**
     * screenId : e711338d-c1f6-4528-b7d1-3bd4cfc4ece8
     * version : 0
     * screenUsername : screen2
     * screenName : This is the first screen
     * screenAddress : Chambenahalli Sarjapur
     */

    @SerializedName("screenId")
    private String screenId;
    @SerializedName("version")
    private int version;
    @SerializedName("screenUsername")
    private String screenUsername;
    @SerializedName("screenName")
    private String screenName;
    @SerializedName("screenAddress")
    private String screenAddress;

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getScreenUsername() {
        return screenUsername;
    }

    public void setScreenUsername(String screenUsername) {
        this.screenUsername = screenUsername;
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

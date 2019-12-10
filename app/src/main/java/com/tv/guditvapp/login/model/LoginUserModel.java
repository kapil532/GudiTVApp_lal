package com.tv.guditvapp.login.model;

import com.google.gson.annotations.SerializedName;

public class LoginUserModel {

    /**
     * screen : {"screenId":"e711338d-c1f6-4528-b7d1-3bd4cfc4ece8","version":0,"screenUsername":"screen2",
     * "screenName":"This is the first screen","screenAddress":"Chambenahalli Sarjapur"}
     */

    @SerializedName("screen")
    private ScreenModel screen;

    public ScreenModel getScreen() {
        return screen;
    }

    public void setScreen(ScreenModel screen) {
        this.screen = screen;
    }
}

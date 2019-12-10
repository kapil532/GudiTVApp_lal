package com.tv.guditvapp.dashboard.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SectionThreeModel implements Serializable {
    /**
     * index : 1
     * loop : 1
     * message : This application is develop by lal
     */

    @SerializedName("index")
    private int index;
    @SerializedName("loop")
    private int loop;
    @SerializedName("message")
    private String message;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLoop() {
        return loop;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

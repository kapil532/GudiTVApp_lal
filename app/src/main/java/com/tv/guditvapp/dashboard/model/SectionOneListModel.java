package com.tv.guditvapp.dashboard.model;

import com.tv.guditvapp.dashboard.utils.ScreenListType;

public class SectionOneListModel {

    private String url;
    private ScreenListType type;
    private int second;

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ScreenListType getType() {
        return type;
    }

    public void setType(ScreenListType type) {
        this.type = type;
    }

}

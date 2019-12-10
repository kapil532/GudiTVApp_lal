package com.tv.guditvapp.dashboard.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SectionOneModel implements Serializable {
    /**
     * index : 1
     * loop : 1
     * originalname : IMG-20180729-WA0011.jpg
     * size : 76854
     * filename : 5ec89cd4cc4fdd3e354967e415c701e2
     * ext : .jpg
     * type : image
     * link : http://shambhu.online/api/upload/image/5ec89cd4cc4fdd3e354967e415c701e2.jpg
     */

    @SerializedName("index")
    private int index;
    @SerializedName("loop")
    private int loop;
    @SerializedName("originalname")
    private String originalname;
    @SerializedName("size")
    private int size;
    @SerializedName("filename")
    private String filename;
    @SerializedName("ext")
    private String ext;
    @SerializedName("type")
    private String type;
    @SerializedName("link")
    private String link;

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

    public String getOriginalname() {
        return originalname;
    }

    public void setOriginalname(String originalname) {
        this.originalname = originalname;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

package com.tv.guditvapp.dashboard.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SectionDataModel implements Serializable {
    /**
     * screen_type : 3
     * sectionOne : [{"index":1,"loop":1,"originalname":"IMG-20180729-WA0011.jpg","size":76854,"filename":"5ec89cd4cc4fdd3e354967e415c701e2","ext":".jpg","type":"image","link":"http://shambhu.online/api/upload/image/5ec89cd4cc4fdd3e354967e415c701e2.jpg"},{"index":2,"loop":1,"originalname":"VID-20190918-WA0001.mp4","size":2976232,"filename":"fa17659feb7520f6fb9abc702d8af650","ext":".mp4","type":"video","link":"http://shambhu.online/api/upload/image/fa17659feb7520f6fb9abc702d8af650.mp4"},{"index":3,"loop":1,"originalname":"IMG-20180805-WA0004.jpg","size":76032,"filename":"6956d8dfa82eddc62fe55d7f5f4fdd21","ext":".jpg","type":"image","link":"http://shambhu.online/api/upload/image/6956d8dfa82eddc62fe55d7f5f4fdd21.jpg"},{"index":4,"loop":1,"originalname":"VID-20191106-WA0000.mp4","size":1168534,"filename":"5d3fc9a1ee0c3d0ec2a15efdb8ee3893","ext":".mp4","type":"video","link":"http://shambhu.online/api/upload/image/5d3fc9a1ee0c3d0ec2a15efdb8ee3893.mp4"}]
     * sectionTwo : [{"index":1,"loop":1,"originalname":"IMG-20180805-WA0022.jpg","size":97769,"filename":"3185436c70f3f6ebc3cf6197f37b659a","ext":".jpg","type":"image","link":"http://shambhu.online/api/upload/image/3185436c70f3f6ebc3cf6197f37b659a.jpg"},{"index":2,"loop":1,"originalname":"IMG-20180805-WA0013.jpg","size":179549,"filename":"7654c377dc2491c72dfea689ac52cadb","ext":".jpg","type":"image","link":"http://shambhu.online/api/upload/image/7654c377dc2491c72dfea689ac52cadb.jpg"},{"index":3,"loop":1,"originalname":"VID-20191129-WA0002.mp4","size":3926980,"filename":"9b6bc87af913a55e5bda34c993334f7f","ext":".mp4","type":"video","link":"http://shambhu.online/api/upload/image/9b6bc87af913a55e5bda34c993334f7f.mp4"},{"index":4,"loop":1,"originalname":"VID-20191127-WA0000.mp4","size":2252865,"filename":"c7971286a3ee26f1c69852b73b9117ba","ext":".mp4","type":"video","link":"http://shambhu.online/api/upload/image/c7971286a3ee26f1c69852b73b9117ba.mp4"}]
     * sectionThree : [{"index":1,"loop":1,"message":"This application is develop by lal"},{"index":2,"loop":1,"message":"everything is great"}]
     */

    @SerializedName("screen_type")
    private int screenType;
    @SerializedName("sectionOne")
    private List<SectionOneModel> sectionOne;
    @SerializedName("sectionTwo")
    private List<SectionTwoModel> sectionTwo;
    @SerializedName("sectionThree")
    private List<SectionThreeModel> sectionThree;

    public int getScreenType() {
        return screenType;
    }

    public void setScreenType(int screenType) {
        this.screenType = screenType;
    }

    public List<SectionOneModel> getSectionOne() {
        return sectionOne;
    }

    public void setSectionOne(List<SectionOneModel> sectionOne) {
        this.sectionOne = sectionOne;
    }

    public List<SectionTwoModel> getSectionTwo() {
        return sectionTwo;
    }

    public void setSectionTwo(List<SectionTwoModel> sectionTwo) {
        this.sectionTwo = sectionTwo;
    }

    public List<SectionThreeModel> getSectionThree() {
        return sectionThree;
    }

    public void setSectionThree(List<SectionThreeModel> sectionThree) {
        this.sectionThree = sectionThree;
    }
}

package com.zzg.zcib.entity;

public class Msg {


    private Integer img;
    private String content;
    private String uid;
    private String fid;
    private String time;
    private String type;
    private String fname;
    private String imgurl;
    private String fImg;

    public Msg(Integer img, String content, String uid, String fid, String time, String type,String fname,String imgurl,String fImg) {
        this.img = img;
        this.content = content;
        this.uid = uid;
        this.fid = fid;
        this.time = time;
        this.type = type;
        this.fname=fname;
        this.imgurl=imgurl;
        this.fImg=fImg;
    }

    public String getfImg() {
        return fImg;
    }

    public void setfImg(String fImg) {
        this.fImg = fImg;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "img=" + img +
                ", content='" + content + '\'' +
                ", uid='" + uid + '\'' +
                ", fid='" + fid + '\'' +
                ", time='" + time + '\'' +
                ", type=" + type +
                '}';
    }
}

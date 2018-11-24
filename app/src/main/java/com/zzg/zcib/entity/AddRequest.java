package com.zzg.zcib.entity;

public class AddRequest {
    private String requestid;
    private String uid;
    private String fid;
    private String isok;
    private String time;
    private String fname;
    private String imgurl;


    public AddRequest(String requestid, String uid, String fid, String isok, String time, String fname,String imgurl) {
        this.requestid = requestid;
        this.uid = uid;
        this.fid = fid;
        this.isok = isok;
        this.time = time;
        this.fname = fname;
        this.imgurl=imgurl;
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

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
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

    public String getIsok() {
        return isok;
    }

    public void setIsok(String isok) {
        this.isok = isok;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

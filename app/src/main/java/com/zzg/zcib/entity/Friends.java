package com.zzg.zcib.entity;

public class Friends {
    public static final Integer ONLINE=1;
    public static final Integer OFFLINE=2;

    private Integer fid;
    private String fusername;
    private Integer isonline;
    private String imgurl;

    public Friends(Integer fid, String fusername, Integer isonline,String imgurl) {
        this.fid = fid;
        this.fusername = fusername;
        this.isonline = isonline;
        this.imgurl=imgurl;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFusername() {
        return fusername;
    }

    public void setFusername(String fusername) {
        this.fusername = fusername;
    }

    public Integer getIsonline() {
        return isonline;
    }

    public void setIsonline(Integer isonline) {
        this.isonline = isonline;
    }
}

package com.sinog2c.model.user;

public class UserGrantDetailKey {
    private String resid;

    private String userid;

    private String opid;

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid == null ? null : resid.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid == null ? null : opid.trim();
    }
}
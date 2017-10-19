package com.sinog2c.model.commutationParole;

import java.util.Date;

public class TbxfMergeReference {
    private Integer mergeid;

    private Integer refid;

    private Integer rewardid;

    private Integer sno;

    private Integer eno;

    private String opid;

    private Date optime;

    public Integer getMergeid() {
        return mergeid;
    }

    public void setMergeid(Integer mergeid) {
        this.mergeid = mergeid;
    }

    public Integer getRefid() {
        return refid;
    }

    public void setRefid(Integer refid) {
        this.refid = refid;
    }

    public Integer getRewardid() {
        return rewardid;
    }

    public void setRewardid(Integer rewardid) {
        this.rewardid = rewardid;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public Integer getEno() {
        return eno;
    }

    public void setEno(Integer eno) {
        this.eno = eno;
    }

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid == null ? null : opid.trim();
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }
}
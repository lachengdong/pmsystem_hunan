package com.sinog2c.model.commutationParole;

import java.util.Date;

public class TbxfPrisonerSentence {
    private Integer senid;

    private String departid;

    private String name;

    private Short sop;

    private Short syear;

    private Short eop;

    private Short eyear;

    private Date stime;

    private Date etime;

    private Short isdel;

    private String remark;

    private String opid;

    private Date optime;

    public Integer getSenid() {
        return senid;
    }

    public void setSenid(Integer senid) {
        this.senid = senid;
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getSop() {
        return sop;
    }

    public void setSop(Short sop) {
        this.sop = sop;
    }

    public Short getSyear() {
        return syear;
    }

    public void setSyear(Short syear) {
        this.syear = syear;
    }

    public Short getEop() {
        return eop;
    }

    public void setEop(Short eop) {
        this.eop = eop;
    }

    public Short getEyear() {
        return eyear;
    }

    public void setEyear(Short eyear) {
        this.eyear = eyear;
    }

    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }

    public Short getIsdel() {
        return isdel;
    }

    public void setIsdel(Short isdel) {
        this.isdel = isdel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
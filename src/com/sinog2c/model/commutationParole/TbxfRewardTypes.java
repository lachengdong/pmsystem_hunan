package com.sinog2c.model.commutationParole;

import java.math.BigDecimal;
import java.util.Date;

public class TbxfRewardTypes {
    private BigDecimal rewardid;

    private String departid;

    private String name;

    private Date stime;

    private Date etime;

    private Short isreward;

    private String actioncode;

    private Short isdel;

    private String remark;

    private String opid;

    private Date optime;

    public BigDecimal getRewardid() {
        return rewardid;
    }

    public void setRewardid(BigDecimal rewardid) {
        this.rewardid = rewardid;
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

    public Short getIsreward() {
        return isreward;
    }

    public void setIsreward(Short isreward) {
        this.isreward = isreward;
    }

    public String getActioncode() {
        return actioncode;
    }

    public void setActioncode(String actioncode) {
        this.actioncode = actioncode == null ? null : actioncode.trim();
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
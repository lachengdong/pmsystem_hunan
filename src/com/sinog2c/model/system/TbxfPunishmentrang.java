package com.sinog2c.model.system;

import java.util.Date;

public class TbxfPunishmentrang {
    private Integer punid;

    private Integer typeid;

    private Integer phid;

    private Short syear;

    private Short eyear;

    private Short type;

    private Short gender;

    private Integer senid;

    private Integer originalstart;

    private Integer originalend;

    private Integer sentencestart;

    private Integer sentenceend;

    private Integer startperiod;

    private Integer intervalperiod;

    private String remark;

    private Short delflag;

    private String opid;

    private Date optime;
    
    private Integer sn;

    public Integer getPunid() {
        return punid;
    }

    public void setPunid(Integer punid) {
        this.punid = punid;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Integer getPhid() {
        return phid;
    }

    public void setPhid(Integer phid) {
        this.phid = phid;
    }

    public Short getSyear() {
        return syear;
    }

    public void setSyear(Short syear) {
        this.syear = syear;
    }

    public Short getEyear() {
        return eyear;
    }

    public void setEyear(Short eyear) {
        this.eyear = eyear;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getGender() {
        return gender;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    public Integer getSenid() {
        return senid;
    }

    public void setSenid(Integer senid) {
        this.senid = senid;
    }

    public Integer getOriginalstart() {
        return originalstart;
    }

    public void setOriginalstart(Integer originalstart) {
        this.originalstart = originalstart;
    }

    public Integer getOriginalend() {
        return originalend;
    }

    public void setOriginalend(Integer originalend) {
        this.originalend = originalend;
    }

    public Integer getSentencestart() {
        return sentencestart;
    }

    public void setSentencestart(Integer sentencestart) {
        this.sentencestart = sentencestart;
    }

    public Integer getSentenceend() {
        return sentenceend;
    }

    public void setSentenceend(Integer sentenceend) {
        this.sentenceend = sentenceend;
    }

    public Integer getStartperiod() {
        return startperiod;
    }

    public void setStartperiod(Integer startperiod) {
        this.startperiod = startperiod;
    }

    public Integer getIntervalperiod() {
        return intervalperiod;
    }

    public void setIntervalperiod(Integer intervalperiod) {
        this.intervalperiod = intervalperiod;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Short getDelflag() {
        return delflag;
    }

    public void setDelflag(Short delflag) {
        this.delflag = delflag;
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

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}
    
}
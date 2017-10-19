package com.sinog2c.model.commutationParole;

import java.util.Date;

public class TbxfWideandthinscheme {
    private Integer lssid;

    private Integer punid;

    private Integer schemetype;

    private Integer lssexecutionsentences;

    private Integer lssexecutionsentencee;

    private Integer lssintervalperiod;

    private Integer lssendintervalperiod;

    private Integer lssstartperiod;

    private Integer lssendperiod;

    private Integer rangestart;

    private Integer rangeend;

    private Integer executesentencelimit;

    private Integer int1;

    private Integer int2;

    private String text1;

    private String text2;

    private Date optime;

    private String opid;

    private String remark;

    private Integer criminaltype;

    public Integer getLssid() {
        return lssid;
    }

    public void setLssid(Integer lssid) {
        this.lssid = lssid;
    }

    public Integer getPunid() {
        return punid;
    }

    public void setPunid(Integer punid) {
        this.punid = punid;
    }

    public Integer getSchemetype() {
        return schemetype;
    }

    public void setSchemetype(Integer schemetype) {
        this.schemetype = schemetype;
    }

    public Integer getLssexecutionsentences() {
        return lssexecutionsentences;
    }

    public void setLssexecutionsentences(Integer lssexecutionsentences) {
        this.lssexecutionsentences = lssexecutionsentences;
    }

    public Integer getLssexecutionsentencee() {
        return lssexecutionsentencee;
    }

    public void setLssexecutionsentencee(Integer lssexecutionsentencee) {
        this.lssexecutionsentencee = lssexecutionsentencee;
    }

    public Integer getLssintervalperiod() {
        return lssintervalperiod;
    }

    public void setLssintervalperiod(Integer lssintervalperiod) {
        this.lssintervalperiod = lssintervalperiod;
    }

    public Integer getLssendintervalperiod() {
        return lssendintervalperiod;
    }

    public void setLssendintervalperiod(Integer lssendintervalperiod) {
        this.lssendintervalperiod = lssendintervalperiod;
    }

    public Integer getLssstartperiod() {
        return lssstartperiod;
    }

    public void setLssstartperiod(Integer lssstartperiod) {
        this.lssstartperiod = lssstartperiod;
    }

    public Integer getLssendperiod() {
        return lssendperiod;
    }

    public void setLssendperiod(Integer lssendperiod) {
        this.lssendperiod = lssendperiod;
    }

    public Integer getRangestart() {
        return rangestart;
    }

    public void setRangestart(Integer rangestart) {
        this.rangestart = rangestart;
    }

    public Integer getRangeend() {
        return rangeend;
    }

    public void setRangeend(Integer rangeend) {
        this.rangeend = rangeend;
    }

    public Integer getExecutesentencelimit() {
        return executesentencelimit;
    }

    public void setExecutesentencelimit(Integer executesentencelimit) {
        this.executesentencelimit = executesentencelimit;
    }

    public Integer getInt1() {
        return int1;
    }

    public void setInt1(Integer int1) {
        this.int1 = int1;
    }

    public Integer getInt2() {
        return int2;
    }

    public void setInt2(Integer int2) {
        this.int2 = int2;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1 == null ? null : text1.trim();
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2 == null ? null : text2.trim();
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid == null ? null : opid.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCriminaltype() {
        return criminaltype;
    }

    public void setCriminaltype(Integer criminaltype) {
        this.criminaltype = criminaltype;
    }
}
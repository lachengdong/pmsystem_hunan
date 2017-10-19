package com.sinog2c.model.flow;

import java.util.Date;

public class FlowDeliverHis {
    private String flowdefid;

    private String resid;

    private String snodeid;

    private String dnodeid;

    private String explain;

    private Short state;

    private String text1;

    private String text2;

    private String text3;

    private String remark;

    private Date optime;

    private String opid;

    public String getFlowdefid() {
        return flowdefid;
    }

    public void setFlowdefid(String flowdefid) {
        this.flowdefid = flowdefid == null ? null : flowdefid.trim();
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid == null ? null : resid.trim();
    }

    public String getSnodeid() {
        return snodeid;
    }

    public void setSnodeid(String snodeid) {
        this.snodeid = snodeid == null ? null : snodeid.trim();
    }

    public String getDnodeid() {
        return dnodeid;
    }

    public void setDnodeid(String dnodeid) {
        this.dnodeid = dnodeid == null ? null : dnodeid.trim();
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
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

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3 == null ? null : text3.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}
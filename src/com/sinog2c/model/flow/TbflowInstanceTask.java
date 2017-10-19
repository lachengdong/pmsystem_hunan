package com.sinog2c.model.flow;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;



public class TbflowInstanceTask {
    private String taskid;

    private String flowid;

    private String flowdefid;

    private String assigneer;

    private String assigneername;

    private Date assigneertime;

    private Date startdate;

    private Date enddate;

    private String cnode;

    private String cnodename;

    private String explain;

    private String state;

    private String lnode;

    private String lnodename;

    private String lassigneer;

    private String lassigneername;

    private String feedback;

    private String response;

    private BigDecimal urgeint1;

    private String note;

    private String text1;

    private String text2;

    private String text3;

    private String text4;  

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid == null ? null : taskid.trim();
    }

    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid == null ? null : flowid.trim();
    }

    public String getFlowdefid() {
        return flowdefid;
    }

    public void setFlowdefid(String flowdefid) {
        this.flowdefid = flowdefid == null ? null : flowdefid.trim();
    }

    public String getAssigneer() {
        return assigneer;
    }

    public void setAssigneer(String assigneer) {
        this.assigneer = assigneer == null ? null : assigneer.trim();
    }

    public String getAssigneername() {
        return assigneername;
    }

    public void setAssigneername(String assigneername) {
        this.assigneername = assigneername == null ? null : assigneername.trim();
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getAssigneertime() {
        return assigneertime;
    }

    public void setAssigneertime(Date assigneertime) {
        this.assigneertime = assigneertime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getCnode() {
        return cnode;
    }

    public void setCnode(String cnode) {
        this.cnode = cnode == null ? null : cnode.trim();
    }

    public String getCnodename() {
        return cnodename;
    }

    public void setCnodename(String cnodename) {
        this.cnodename = cnodename == null ? null : cnodename.trim();
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getLnode() {
        return lnode;
    }

    public void setLnode(String lnode) {
        this.lnode = lnode == null ? null : lnode.trim();
    }

    public String getLnodename() {
        return lnodename;
    }

    public void setLnodename(String lnodename) {
        this.lnodename = lnodename == null ? null : lnodename.trim();
    }

    public String getLassigneer() {
        return lassigneer;
    }

    public void setLassigneer(String lassigneer) {
        this.lassigneer = lassigneer == null ? null : lassigneer.trim();
    }

    public String getLassigneername() {
        return lassigneername;
    }

    public void setLassigneername(String lassigneername) {
        this.lassigneername = lassigneername == null ? null : lassigneername.trim();
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback == null ? null : feedback.trim();
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response == null ? null : response.trim();
    }

    public BigDecimal getUrgeint1() {
        return urgeint1;
    }

    public void setUrgeint1(BigDecimal urgeint1) {
        this.urgeint1 = urgeint1;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
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

    public String getText4() {
        return text4;
    }

    public void setText4(String text4) {
        this.text4 = text4 == null ? null : text4.trim();
    }	
}
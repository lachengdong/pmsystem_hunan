package com.sinog2c.model.khjc;

import java.math.BigDecimal;

public class TkhRoutineexamination {
    private Integer routineid;//日常考核id

    private String crimid;//罪犯编号

    private String flowid;//流程正式id

    private String orgpid;//监狱id

    private String orgid;//监区id

    private String orgsid;//分监区id

    private String ruleid;//依据（TKH_REWARDPUNISHRULE表规则ID）

    private String searchcode;//依据（TKH_REWARDPUNISHRULE表快速搜索码）

    private BigDecimal score;//得分

    private String typeid;//奖惩类型ID（TKH_TYPE表）

    private String applytime;//奖惩时间

    private String approvalcomment;//审批意见

    private Short iscancel;//是否废弃，1废弃，0否

    private String cancelreason;//废弃原因

    private String cancetime;//废弃时间

    private String canceid;//废弃操作人id

    private String text1;//备用

    private String text2;//备用

    private String text3;//备用

    private String text4;//备用

    private String text5;//备用

    private String text6;//备用

    private String text7;//备用

    private String text8;//备用

    private Integer int1;//备用

    private String opid;//操作人id

    private String optime;//操作时间

    public Integer getRoutineid() {
        return routineid;
    }

    public void setRoutineid(Integer routineid) {
        this.routineid = routineid;
    }

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid == null ? null : flowid.trim();
    }

    public String getOrgpid() {
        return orgpid;
    }

    public void setOrgpid(String orgpid) {
        this.orgpid = orgpid == null ? null : orgpid.trim();
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

    public String getOrgsid() {
        return orgsid;
    }

    public void setOrgsid(String orgsid) {
        this.orgsid = orgsid == null ? null : orgsid.trim();
    }

    public String getRuleid() {
        return ruleid;
    }

    public void setRuleid(String ruleid) {
        this.ruleid = ruleid == null ? null : ruleid.trim();
    }

    public String getSearchcode() {
        return searchcode;
    }

    public void setSearchcode(String searchcode) {
        this.searchcode = searchcode == null ? null : searchcode.trim();
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid == null ? null : typeid.trim();
    }

    public String getApplytime() {
        return applytime;
    }

    public void setApplytime(String applytime) {
        this.applytime = applytime == null ? null : applytime.trim();
    }

    public String getApprovalcomment() {
        return approvalcomment;
    }

    public void setApprovalcomment(String approvalcomment) {
        this.approvalcomment = approvalcomment == null ? null : approvalcomment.trim();
    }

    public Short getIscancel() {
        return iscancel;
    }

    public void setIscancel(Short iscancel) {
        this.iscancel = iscancel;
    }

    public String getCancelreason() {
        return cancelreason;
    }

    public void setCancelreason(String cancelreason) {
        this.cancelreason = cancelreason == null ? null : cancelreason.trim();
    }

    public String getCancetime() {
        return cancetime;
    }

    public void setCancetime(String cancetime) {
        this.cancetime = cancetime == null ? null : cancetime.trim();
    }

    public String getCanceid() {
        return canceid;
    }

    public void setCanceid(String canceid) {
        this.canceid = canceid == null ? null : canceid.trim();
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

    public String getText5() {
        return text5;
    }

    public void setText5(String text5) {
        this.text5 = text5 == null ? null : text5.trim();
    }

    public String getText6() {
        return text6;
    }

    public void setText6(String text6) {
        this.text6 = text6 == null ? null : text6.trim();
    }

    public String getText7() {
        return text7;
    }

    public void setText7(String text7) {
        this.text7 = text7 == null ? null : text7.trim();
    }

    public String getText8() {
        return text8;
    }

    public void setText8(String text8) {
        this.text8 = text8 == null ? null : text8.trim();
    }

    public Integer getInt1() {
        return int1;
    }

    public void setInt1(Integer int1) {
        this.int1 = int1;
    }

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid == null ? null : opid.trim();
    }

    public String getOptime() {
        return optime;
    }

    public void setOptime(String optime) {
        this.optime = optime == null ? null : optime.trim();
    }
}
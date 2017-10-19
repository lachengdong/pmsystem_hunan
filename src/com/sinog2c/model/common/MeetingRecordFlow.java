package com.sinog2c.model.common;

import java.util.Date;

public class MeetingRecordFlow extends MeetingRecordFlowKey {
    private String tempid;

    private Short grade;

    private Short delflag;

    private String text1;

    private String text2;

    private String text3;

    private String text4;

    private String text5;

    private String text6;

    private Integer int1;

    private Integer int2;

    private Integer int3;

    private Integer int4;

    private Integer int5;

    private Integer int6;

    private String opid;

    private Date optime;

    private String remark;
    
    private String casetype;
    
    private String departid;

    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid == null ? null : tempid.trim();
    }

    public Short getGrade() {
        return grade;
    }

    public void setGrade(Short grade) {
        this.grade = grade;
    }

    public Short getDelflag() {
        return delflag;
    }

    public void setDelflag(Short delflag) {
        this.delflag = delflag;
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

    public Integer getInt3() {
        return int3;
    }

    public void setInt3(Integer int3) {
        this.int3 = int3;
    }

    public Integer getInt4() {
        return int4;
    }

    public void setInt4(Integer int4) {
        this.int4 = int4;
    }

    public Integer getInt5() {
        return int5;
    }

    public void setInt5(Integer int5) {
        this.int5 = int5;
    }

    public Integer getInt6() {
        return int6;
    }

    public void setInt6(Integer int6) {
        this.int6 = int6;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
    public String getCasetype() {
    	return casetype;
    }
    
    public void setCasetype(String casetype) {
    	this.casetype = casetype == null ? null : casetype.trim();
    }
    
    public String getDepartid() {
    	return departid;
    }
    
    public void setDepartid(String departid) {
    	this.departid = departid == null ? null : departid.trim();
    }
}
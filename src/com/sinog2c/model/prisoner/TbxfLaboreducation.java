package com.sinog2c.model.prisoner;

import java.math.BigDecimal;
import java.util.Date;

public class TbxfLaboreducation {
    private String laborid;

    private String crimid;

    private String year;

    private String project;

    private String tempid;

    private BigDecimal int1;

    private String text1;

    private Date optime;

    private String optid;

    private String fraction1;

    private String fraction2;

    private String fraction3;

    private String remark;

    private String otherwise;

    public String getLaborid() {
        return laborid;
    }

    public void setLaborid(String laborid) {
        this.laborid = laborid == null ? null : laborid.trim();
    }

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project == null ? null : project.trim();
    }

    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid == null ? null : tempid.trim();
    }

    public BigDecimal getInt1() {
        return int1;
    }

    public void setInt1(BigDecimal int1) {
        this.int1 = int1;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1 == null ? null : text1.trim();
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public String getOptid() {
        return optid;
    }

    public void setOptid(String optid) {
        this.optid = optid == null ? null : optid.trim();
    }

    public String getFraction1() {
        return fraction1;
    }

    public void setFraction1(String fraction1) {
        this.fraction1 = fraction1 == null ? null : fraction1.trim();
    }

    public String getFraction2() {
        return fraction2;
    }

    public void setFraction2(String fraction2) {
        this.fraction2 = fraction2 == null ? null : fraction2.trim();
    }

    public String getFraction3() {
        return fraction3;
    }

    public void setFraction3(String fraction3) {
        this.fraction3 = fraction3 == null ? null : fraction3.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOtherwise() {
        return otherwise;
    }

    public void setOtherwise(String otherwise) {
        this.otherwise = otherwise == null ? null : otherwise.trim();
    }
}
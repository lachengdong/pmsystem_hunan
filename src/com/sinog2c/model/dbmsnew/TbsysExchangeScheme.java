package com.sinog2c.model.dbmsnew;

import java.math.BigDecimal;
import java.util.Date;

public class TbsysExchangeScheme {
    private Integer excid;

    private String solutionid;

    private String orgid;

    private String name;

    private Integer pexcid;

    private Short delflag;

    private String remark;

    private String opid;

    private Date optime;

    private String text1;

    private String text2;

    private String text3;

    private String text4;

    private BigDecimal int1;

    private BigDecimal int2;

    private BigDecimal int3;

    private BigDecimal int4;

    private Short orderby;

    public Integer getExcid() {
        return excid;
    }

    public void setExcid(Integer excid) {
        this.excid = excid;
    }

    public String getSolutionid() {
        return solutionid;
    }

    public void setSolutionid(String solutionid) {
        this.solutionid = solutionid == null ? null : solutionid.trim();
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPexcid() {
        return pexcid;
    }

    public void setPexcid(Integer pexcid) {
        this.pexcid = pexcid;
    }

    public Short getDelflag() {
        return delflag;
    }

    public void setDelflag(Short delflag) {
        this.delflag = delflag;
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

    public BigDecimal getInt1() {
        return int1;
    }

    public void setInt1(BigDecimal int1) {
        this.int1 = int1;
    }

    public BigDecimal getInt2() {
        return int2;
    }

    public void setInt2(BigDecimal int2) {
        this.int2 = int2;
    }

    public BigDecimal getInt3() {
        return int3;
    }

    public void setInt3(BigDecimal int3) {
        this.int3 = int3;
    }

    public BigDecimal getInt4() {
        return int4;
    }

    public void setInt4(BigDecimal int4) {
        this.int4 = int4;
    }

    public Short getOrderby() {
        return orderby;
    }

    public void setOrderby(Short orderby) {
        this.orderby = orderby;
    }
}
package com.sinog2c.model.solution;

import java.util.Date;

public class FormSolution implements Cloneable{
    private String solutionid;

    private String solutionpid;

    private Integer solutiontype;

    private String solutionname;

    private Integer delflag;

    private String remark;

    private Date createtime;

    private Date updatetime;

    private String createmender;

    private String updatemender;

    private Integer signflag;

    private Integer int1;

    private Integer int2;

    private Integer int3;

    private Integer int4;

    private Integer int5;

    private String text1;

    private String text2;

    private String text3;

    private String text4;

    private String text5;

    public String getSolutionid() {
        return solutionid;
    }

    public void setSolutionid(String solutionid) {
        this.solutionid = solutionid == null ? null : solutionid.trim();
    }

    public String getSolutionpid() {
        return solutionpid;
    }

    public void setSolutionpid(String solutionpid) {
        this.solutionpid = solutionpid == null ? null : solutionpid.trim();
    }

    public Integer getSolutiontype() {
        return solutiontype;
    }

    public void setSolutiontype(Integer solutiontype) {
        this.solutiontype = solutiontype;
    }

    public String getSolutionname() {
        return solutionname;
    }

    public void setSolutionname(String solutionname) {
        this.solutionname = solutionname == null ? null : solutionname.trim();
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreatemender() {
        return createmender;
    }

    public void setCreatemender(String createmender) {
        this.createmender = createmender == null ? null : createmender.trim();
    }

    public String getUpdatemender() {
        return updatemender;
    }

    public void setUpdatemender(String updatemender) {
        this.updatemender = updatemender == null ? null : updatemender.trim();
    }

    public Integer getSignflag() {
        return signflag;
    }

    public void setSignflag(Integer signflag) {
        this.signflag = signflag;
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
    
    @Override
    public FormSolution clone() throws CloneNotSupportedException {
    	return (FormSolution) super.clone();
    }
}
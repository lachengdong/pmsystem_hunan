package com.sinog2c.model.flow;

import java.util.Date;

public class FlowArchivesSetting extends FlowArchivesSettingKey {
    private String tempname;

    private String codeid;

    private Short savetype;

    private Short checkclob;

    private String solutionid;

    private Short isassemble;

    private String filetype;

    private String remark;

    private String delflag;

    private Date optime;

    private String opid;

    private String text1;

    private String text2;

    private String text3;

    private String text4;

    private Integer int1;

    private Integer int2;

    private Integer int3;

    private Integer int4;
    
    private String flowdefid;

    public String getTempname() {
        return tempname;
    }

    public void setTempname(String tempname) {
        this.tempname = tempname == null ? null : tempname.trim();
    }

    public String getCodeid() {
        return codeid;
    }

    public void setCodeid(String codeid) {
        this.codeid = codeid == null ? null : codeid.trim();
    }

    public Short getSavetype() {
        return savetype;
    }

    public void setSavetype(Short savetype) {
        this.savetype = savetype;
    }

    public Short getCheckclob() {
        return checkclob;
    }

    public void setCheckclob(Short checkclob) {
        this.checkclob = checkclob;
    }

    public String getSolutionid() {
        return solutionid;
    }

    public void setSolutionid(String solutionid) {
        this.solutionid = solutionid == null ? null : solutionid.trim();
    }

    public Short getIsassemble() {
        return isassemble;
    }

    public void setIsassemble(Short isassemble) {
        this.isassemble = isassemble;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype == null ? null : filetype.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
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
    
    public String getFlowdefid() {
        return flowdefid;
    }

    public void setFlowdefid(String flowdefid) {
        this.flowdefid = flowdefid == null ? null : flowdefid.trim();
    }
}
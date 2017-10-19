package com.sinog2c.model.flow;

import java.util.Date;

public class FlowArchivesCode extends FlowArchivesCodeKey {
    private String pcodeid;

    private String name;

    private Short ispositive;

    private Integer sn;

    private String scearch;

    private String remark;

    private String delflag;

    private Date optime;

    private String opid;
    
    private String tempid;

    public String getPcodeid() {
        return pcodeid;
    }

    public void setPcodeid(String pcodeid) {
        this.pcodeid = pcodeid == null ? null : pcodeid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getIspositive() {
        return ispositive;
    }

    public void setIspositive(Short ispositive) {
        this.ispositive = ispositive;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getScearch() {
        return scearch;
    }

    public void setScearch(String scearch) {
        this.scearch = scearch == null ? null : scearch.trim();
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

	public String getTempid() {
		return tempid;
	}

	public void setTempid(String tempid) {
		this.tempid = tempid;
	}
    
}
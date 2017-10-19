package com.sinog2c.model.commutationParole;

import java.util.Date;

public class TbxfCommutationReference {
    private Integer refid;

    private Integer punid;

    private String name;

    private Integer containrewardid;

    private Short lowestnum;

    private Short highestnum;

    private Short suggestnum;

    private Short isinterval;

    private Short isdel;

    private String remark;

    private String opid;

    private Date optime;
    
    private Integer reflevel;
    
    private String boquannum;

    public Integer getRefid() {
        return refid;
    }

    public void setRefid(Integer refid) {
        this.refid = refid;
    }

    public Integer getPunid() {
        return punid;
    }

    public void setPunid(Integer punid) {
        this.punid = punid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getContainrewardid() {
        return containrewardid;
    }

    public void setContainrewardid(Integer containrewardid) {
        this.containrewardid = containrewardid;
    }

    public Short getLowestnum() {
        return lowestnum;
    }

    public void setLowestnum(Short lowestnum) {
        this.lowestnum = lowestnum;
    }

    public Short getHighestnum() {
        return highestnum;
    }

    public void setHighestnum(Short highestnum) {
        this.highestnum = highestnum;
    }

    public Short getSuggestnum() {
        return suggestnum;
    }

    public void setSuggestnum(Short suggestnum) {
        this.suggestnum = suggestnum;
    }

    public Short getIsinterval() {
        return isinterval;
    }

    public void setIsinterval(Short isinterval) {
        this.isinterval = isinterval;
    }

    public Short getIsdel() {
        return isdel;
    }

    public void setIsdel(Short isdel) {
        this.isdel = isdel;
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

	public Integer getReflevel() {
		return reflevel;
	}

	public void setReflevel(Integer reflevel) {
		this.reflevel = reflevel;
	}

	public String getBoquannum() {
		return boquannum;
	}

	public void setBoquannum(String boquannum) {
		this.boquannum = boquannum;
	}


	
}
package com.sinog2c.model.system;

import java.util.Date;

public class Tbcourtconnectioninfo {
    private Long cid;

    private String corgid;

    private String torgid;

    private String cname;

    private String cip;

    private String cport;

    private String cusername;

    private String cpassword;

    private String opid;

    private Date optime;

    private String remark;

    private String delflg;
    
    private String orgname;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCorgid() {
        return corgid;
    }

    public void setCorgid(String corgid) {
        this.corgid = corgid == null ? null : corgid.trim();
    }

    public String getTorgid() {
        return torgid;
    }

    public void setTorgid(String torgid) {
        this.torgid = torgid == null ? null : torgid.trim();
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip == null ? null : cip.trim();
    }

    public String getCport() {
        return cport;
    }

    public void setCport(String cport) {
        this.cport = cport == null ? null : cport.trim();
    }

    public String getCusername() {
        return cusername;
    }

    public void setCusername(String cusername) {
        this.cusername = cusername == null ? null : cusername.trim();
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword == null ? null : cpassword.trim();
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

    public String getDelflg() {
        return delflg;
    }

    public void setDelflg(String delflg) {
        this.delflg = delflg == null ? null : delflg.trim();
    }

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
    
    
}
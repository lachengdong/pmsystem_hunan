package com.sinog2c.model.prisoner;

import java.util.Date;

public class Tbxfoldilldisability extends TbxfoldilldisabilityKey {
    private String departid;

    private String oidtype;

    private String oidtypedetail;

    private String description;

    private String cause;

    private String disabilitytype;

    private String remark;

    private String opid;

    private Date optime;
    
    private String tstatus;
    
    private String id;
    
    private String revoke_rs;
    
    private Date revoke_date;

    public String getRevoke_rs() {
		return revoke_rs;
	}

	public void setRevoke_rs(String revoke_rs) {
		this.revoke_rs = revoke_rs;
	}

	public Date getRevoke_date() {
		return revoke_date;
	}

	public void setRevoke_date(Date revoke_date) {
		this.revoke_date = revoke_date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id== null ? null : id.trim();
	}

	public String getTstatus() {
		return tstatus;
	}

	public void setTstatus(String tstatus) {
		this.tstatus = tstatus== null ? null : tstatus.trim();
	}

	public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
    }

    public String getOidtype() {
        return oidtype;
    }

    public void setOidtype(String oidtype) {
        this.oidtype = oidtype == null ? null : oidtype.trim();
    }

    public String getOidtypedetail() {
        return oidtypedetail;
    }

    public void setOidtypedetail(String oidtypedetail) {
        this.oidtypedetail = oidtypedetail == null ? null : oidtypedetail.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause == null ? null : cause.trim();
    }

    public String getDisabilitytype() {
        return disabilitytype;
    }

    public void setDisabilitytype(String disabilitytype) {
        this.disabilitytype = disabilitytype == null ? null : disabilitytype.trim();
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
}
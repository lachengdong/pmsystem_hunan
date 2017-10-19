package com.sinog2c.model.user;

import java.util.Date;

public class UserGrantDetail extends UserGrantDetailKey {
    private String presid;

    private String name;

    private Date distime;

    private Date optime;

    private Integer noticeid;
    public String getPresid() {
        return presid;
    }

    public void setPresid(String presid) {
        this.presid = presid == null ? null : presid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getDistime() {
        return distime;
    }

    public void setDistime(Date distime) {
        this.distime = distime;
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

	public Integer getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(Integer noticeid) {
		this.noticeid = noticeid;
	}
    
}
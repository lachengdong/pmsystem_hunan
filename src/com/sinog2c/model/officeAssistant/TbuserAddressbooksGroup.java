package com.sinog2c.model.officeAssistant;

import java.util.Date;

/**
 * @author kexz
 *通讯簿
 * 2014-7-9
 */
public class TbuserAddressbooksGroup {
	private String ispublic;
	
    private String userid;

    private Integer groupid;

    private Integer pgroupid;

    private String name;

    private Short sn;

    private String remark;

    private Date optime;

    private String opid;
    

    public String getIspublic() {
		return ispublic;
	}

	public void setIspublic(String ispublic) {
		this.ispublic = ispublic==null?null:ispublic.trim();
	}

	public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getPgroupid() {
        return pgroupid;
    }

    public void setPgroupid(Integer pgroupid) {
        this.pgroupid = pgroupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getSn() {
        return sn;
    }

    public void setSn(Short sn) {
        this.sn = sn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}
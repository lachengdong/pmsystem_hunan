package com.sinog2c.model.system;

import java.util.Date;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.sinog2c.model.RequireLog;

public class Tbsys_doctemplate {
	@RequireLog
    private String doctempid;

    private String modulename;

    @RequireLog
    private String tempname;

    private String filetype;

    private String thumfiletype;

    private String type;

    private String remark;

    private String departid;

    private Date optime;

    private String opid;
    
    private String actiontype;

    public String getDoctempid() {
        return doctempid;
    }

    public void setDoctempid(String doctempid) {
        this.doctempid = doctempid == null ? null : doctempid.trim();
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename == null ? null : modulename.trim();
    }

    public String getTempname() {
        return tempname;
    }

    public void setTempname(String tempname) {
        this.tempname = tempname == null ? null : tempname.trim();
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype == null ? null : filetype.trim();
    }

    public String getThumfiletype() {
        return thumfiletype;
    }

    public void setThumfiletype(String thumfiletype) {
        this.thumfiletype = thumfiletype == null ? null : thumfiletype.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}
}
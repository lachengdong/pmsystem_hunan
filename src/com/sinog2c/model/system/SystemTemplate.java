package com.sinog2c.model.system;

import java.util.Date;

public class SystemTemplate {
    private String tempid;

    private String functionname;

    private String tempname;

    private Short delflag;

    private Short type;

    private Integer findid;

    private String remark;

    private String departid;

    private Date optime;

    private String opid;

    private String content;
    
    private String temptype;
    
    private String generaltype;
    
    private String  uneditedfields;

    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid == null ? null : tempid.trim();
    }

    public String getFunctionname() {
        return functionname;
    }

    public void setFunctionname(String functionname) {
        this.functionname = functionname == null ? null : functionname.trim();
    }

    public String getTempname() {
        return tempname;
    }

    public void setTempname(String tempname) {
        this.tempname = tempname == null ? null : tempname.trim();
    }

    public Short getDelflag() {
        return delflag;
    }

    public void setDelflag(Short delflag) {
        this.delflag = delflag;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getFindid() {
        return findid;
    }

    public void setFindid(Integer findid) {
        this.findid = findid;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getTemptype() {
		return temptype;
	}

	public void setTemptype(String temptype) {
		this.temptype = temptype;
	}

	
	public String getGeneraltype() {
		return generaltype;
	}

	public void setGeneraltype(String generaltype) {
		this.generaltype = generaltype;
	}

	public String getUneditedfields() {
		return uneditedfields;
	}

	public void setUneditedfields(String uneditedfields) {
		this.uneditedfields = uneditedfields;
	}
    
    
}
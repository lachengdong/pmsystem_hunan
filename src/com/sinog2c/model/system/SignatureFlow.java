package com.sinog2c.model.system;

import java.util.Date;

public class SignatureFlow {
    private String flowdraftid;

    private String tempid;

    private Integer sealnum;

    private Integer notationnum;

    private Short isable;

    private Short delflag;

    private String text1;

    private String text2;

    private String text3;

    private String text4;

    private Integer int1;

    private Integer int2;

    private Integer int3;

    private Integer int4;

    private String opid;

    private Date optime;

    private String remark;
    
    private String operatetype;
    
    private String currnodeid;
    
    private String lastnodeid;
    
    private Integer lastsealnum;
    
    private Integer requaresealnum;
    
    private String pdfpath;
    
    private String operateip;

    public String getFlowdraftid() {
        return flowdraftid;
    }

    public void setFlowdraftid(String flowdraftid) {
        this.flowdraftid = flowdraftid == null ? null : flowdraftid.trim();
    }

    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid == null ? null : tempid.trim();
    }

    public Integer getSealnum() {
        return sealnum;
    }

    public void setSealnum(Integer sealnum) {
        this.sealnum = sealnum;
    }

    public Integer getNotationnum() {
        return notationnum;
    }

    public void setNotationnum(Integer notationnum) {
        this.notationnum = notationnum;
    }

    public Short getIsable() {
        return isable;
    }

    public void setIsable(Short isable) {
        this.isable = isable;
    }

    public Short getDelflag() {
        return delflag;
    }

    public void setDelflag(Short delflag) {
        this.delflag = delflag;
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

	public String getOperatetype() {
		return operatetype;
	}

	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype == null ? null : operatetype.trim();
	}

	public String getCurrnodeid() {
		return currnodeid;
	}

	public void setCurrnodeid(String currnodeid) {
		this.currnodeid = currnodeid == null ? null : currnodeid.trim();
	}

	public String getLastnodeid() {
		return lastnodeid;
	}

	public void setLastnodeid(String lastnodeid) {
		this.lastnodeid = lastnodeid == null ? null : lastnodeid.trim();
	}

	public Integer getLastsealnum() {
		return lastsealnum;
	}

	public void setLastsealnum(Integer lastsealnum) {
		this.lastsealnum = lastsealnum;
	}

	public Integer getRequaresealnum() {
		return requaresealnum;
	}

	public void setRequaresealnum(Integer requaresealnum) {
		this.requaresealnum = requaresealnum;
	}
	
	public String getPdfpath() {
		return pdfpath;
	}

	public void setPdfpath(String pdfpath) {
		this.pdfpath = pdfpath == null ? null : pdfpath.trim();
	}
	
	public String getOperateip() {
		return operateip;
	}

	public void setOperateip(String operateip) {
		this.operateip = operateip == null ? null : operateip.trim();
	}
    
}
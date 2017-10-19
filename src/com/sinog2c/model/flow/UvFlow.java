package com.sinog2c.model.flow;

import java.math.BigDecimal;
import java.util.Date;

public class UvFlow {
	private String flowdraftid;
	
	private String casetype;
	
    private String flowid;

    private String flowdefid;

    private String applyid;

    private String applyname;
    
    private String applydatetime;

    private Date startdatetime;

    private Date enddatetime;

    private String conent;

    private Short islocked;
    
    private String opid;
    
    private String departid;

    private String nodeid;

    private String orgid;

    private BigDecimal state;

    private String startsummry;

    private String endsummry;

    private String commenttext;
    
    private String optime;
    
    private String opname;
    
    private String ischeck;
    
    private String text12;

	public String getFlowdraftid() {
		return flowdraftid;
	}

	public void setFlowdraftid(String flowdraftid) {
		this.flowdraftid = flowdraftid;
	}

	public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid == null ? null : flowid.trim();
    }

    public String getFlowdefid() {
        return flowdefid;
    }

    public void setFlowdefid(String flowdefid) {
        this.flowdefid = flowdefid == null ? null : flowdefid.trim();
    }

    public String getApplyid() {
        return applyid;
    }

    public void setApplyid(String applyid) {
        this.applyid = applyid == null ? null : applyid.trim();
    }

    public String getApplyname() {
        return applyname;
    }

    public void setApplyname(String applyname) {
        this.applyname = applyname == null ? null : applyname.trim();
    }

    public String getApplydatetime() {
		return applydatetime;
	}

	public void setApplydatetime(String applydatetime) {
		this.applydatetime = applydatetime;
	}

	public Date getStartdatetime() {
        return startdatetime;
    }

    public void setStartdatetime(Date startdatetime) {
        this.startdatetime = startdatetime;
    }

    public Date getEnddatetime() {
        return enddatetime;
    }

    public void setEnddatetime(Date enddatetime) {
        this.enddatetime = enddatetime;
    }

    public String getConent() {
        return conent;
    }

    public void setConent(String conent) {
        this.conent = conent == null ? null : conent.trim();
    }

    public Short getIslocked() {
        return islocked;
    }

    public void setIslocked(Short islocked) {
        this.islocked = islocked;
    }

    public String getOpid() {
		return opid;
	}

	public void setOpid(String opid) {
		this.opid = opid;
	}

	public String getDepartid() {
		return departid;
	}

	public void setDepartid(String departid) {
		this.departid = departid;
	}

	public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid == null ? null : nodeid.trim();
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

    public BigDecimal getState() {
        return state;
    }

    public void setState(BigDecimal state) {
        this.state = state;
    }

    public String getStartsummry() {
        return startsummry;
    }

    public void setStartsummry(String startsummry) {
        this.startsummry = startsummry == null ? null : startsummry.trim();
    }

    public String getEndsummry() {
        return endsummry;
    }

    public void setEndsummry(String endsummry) {
        this.endsummry = endsummry == null ? null : endsummry.trim();
    }

    public String getCommenttext() {
        return commenttext;
    }

    public void setCommenttext(String commenttext) {
        this.commenttext = commenttext == null ? null : commenttext.trim();
    }

	public String getOptime() {
		return optime;
	}

	public void setOptime(String optime) {
		this.optime = optime;
	}

	public String getOpname() {
		return opname;
	}

	public void setOpname(String opname) {
		this.opname = opname;
	}

	public String getCasetype() {
		return casetype;
	}

	public void setCasetype(String casetype) {
		this.casetype = casetype;
	}

	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	
	public String getText12() {
		return text12;
	}
	
	public void setText12(String text12) {
		this.text12 = text12;
	}
    
}
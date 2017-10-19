package com.sinog2c.model.flow;

import java.util.Date;

public class Flow {
	private String flowsn;
	
	private String flowdraftid;
	
    private String flowid;

    private String nodeid;

    private String orgid;

    private Short state;

    private String startsummry;

    private String endsummry;

    private String commenttext;
    
    private String examineid;

    private String remark;

    private String text1;

    private String text2;

    private String text3;

    private String text4;

    private String text5;

    private String text6;
    
    private String casetype;

    private Integer int1;

    private Integer int2;

    private String opname;

    private String opid;

    private Date optime;
    
    private String operateip;
    
    private Integer orderby;
    
    private String text7;
    
    private String prenodeid;
    
    private String operatetype;
    
    private String operateid;

    public String getFlowsn() {
		return flowsn;
	}

	public void setFlowsn(String flowsn) {
		this.flowsn = flowsn;
	}

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

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
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

    public String getExamineid() {
		return examineid;
	}

	public void setExamineid(String examineid) {
		this.examineid = examineid;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getText5() {
        return text5;
    }

    public void setText5(String text5) {
        this.text5 = text5 == null ? null : text5.trim();
    }

    public String getText6() {
        return text6;
    }

    public void setText6(String text6) {
        this.text6 = text6 == null ? null : text6.trim();
    }

    public String getCasetype() {
		return casetype;
	}

	public void setCasetype(String casetype) {
		this.casetype = casetype;
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

    public String getOpname() {
        return opname;
    }

    public void setOpname(String opname) {
        this.opname = opname == null ? null : opname.trim();
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
    
    public String getOperateip() {
        return operateip;
    }

    public void setOperateip(String operateip) {
        this.operateip = operateip == null ? null : operateip.trim();
    }
    
    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }

	public String getText7() {
		return text7;
	}

	public void setText7(String text7) {
		this.text7 = text7;
	}

	public String getPrenodeid() {
		return prenodeid;
	}

	public void setPrenodeid(String prenodeid) {
		this.prenodeid = prenodeid == null ? null : prenodeid.trim();
	}

	public String getOperatetype() {
		return operatetype;
	}

	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype == null ? null : operatetype.trim();
	}

	public String getOperateid() {
		return operateid;
	}

	public void setOperateid(String operateid) {
		this.operateid = operateid == null ? null : operateid.trim();
	}
    
}
package com.sinog2c.model.flow;

import java.util.Date;

public class FlowDeliver {
    private String flowdefid;

    private String resid;

    private String snodeid;

    private String dnodeid;

    private String explain;

    private Short state;

    private String text1;

    private String text2;

    private String text3;

    private String departid;
    
    private String remark;

    private Date optime;

    private String opid;

    private Integer int1;
    
    private String text4;
    
    private Integer int2;
    
    private Integer int3;
    
    private Integer int4;
    
    private String text5;
    
    private String text6;
    
    private String text7;
    
    private String text8;
    
    private String text9;
    
    private String text10;

    private String assigneer;
   	
   	private String strategy;
   	
   	private String flowName;
   	
   	private String filterorg;
   	
   	private String branchtype;
   	
   	private String branchvalue;
   	
   	private String solutionid;

    public String getFlowdefid() {
        return flowdefid;
    }

    public void setFlowdefid(String flowdefid) {
        this.flowdefid = flowdefid == null ? null : flowdefid.trim();
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid == null ? null : resid.trim();
    }

    public String getSnodeid() {
        return snodeid;
    }

    public void setSnodeid(String snodeid) {
        this.snodeid = snodeid == null ? null : snodeid.trim();
    }

    public String getDnodeid() {
        return dnodeid;
    }

    public void setDnodeid(String dnodeid) {
        this.dnodeid = dnodeid == null ? null : dnodeid.trim();
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
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
		this.text4 = text4;
	}

	public Integer getInt1() {
		return int1;
	}

	public void setInt1(Integer int1) {
		this.int1 = int1;
	}

	public String getDepartid() {
		return departid;
	}

	public void setDepartid(String departid) {
		this.departid = departid;
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
    
    //start add by blue_lv 2015-7-19
	public String getAssigneer() {
		return assigneer;
	}

	public void setAssigneer(String assigneer) {
		this.assigneer = assigneer;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
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

	public String getText5() {
		return text5;
	}

	public void setText5(String text5) {
		this.text5 = text5==null?null:text5.trim();
	}

	public String getText6() {
		return text6;
	}

	public void setText6(String text6) {
		this.text6 = text6==null?null:text6.trim();
	}

	public String getText7() {
		return text7;
	}
	
	public void setText7(String text7) {
		this.text7 = text7==null?null:text7.trim();
	}

	public String getText8() {
		return text8;
	}
	
	public void setText8(String text8) {
		this.text8 = text8==null?null:text8.trim();
	}

	public String getText9() {
		return text9;
	}

	public void setText9(String text9) {
		this.text9 = text9==null?null:text9.trim();
	}
	
	public String getText10() {
		return text10;
	}

	public void setText10(String text10) {
		this.text10 = text10==null?null:text10.trim();
	}

	public String getFilterorg() {
		return filterorg;
	}

	public void setFilterorg(String filterorg) {
		this.filterorg = filterorg == null ? null : filterorg.trim();
	}

	public String getBranchtype() {
		return branchtype;
	}

	public void setBranchtype(String branchtype) {
		this.branchtype = branchtype == null ? null : branchtype.trim();
	}

	public String getBranchvalue() {
		return branchvalue;
	}

	public void setBranchvalue(String branchvalue) {
		this.branchvalue = branchvalue == null ? null : branchvalue.trim();
	}

	public String getSolutionid() {
		return solutionid;
	}

	public void setSolutionid(String solutionid) {
		this.solutionid = solutionid == null ? null : solutionid.trim();
	}
	
}
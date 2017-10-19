package com.sinog2c.model.system;

import java.util.Date;

public class SignatureScheme {
	
    private Integer signid;

    private String orgid;
    
    private String resid;

    private String tempid;

    private String signtype;

    private String name;

    private Integer progress;

    private String content;

    private String scheme;

    private Short delflag;

    private String remark;

    private Date optime;
    
    private Integer seal;
    
    private Integer notation; 
    
    private String flowdefid;
    
    private String isnotcheckprogress;
    
    private Integer psignid;
    
    private String solutionid;
    
    private Integer orderby;
    
    private Integer isseal;
    
    private Integer isnotation;
    
    private String protectnode;
    
    private String sealtype;
    
    private String text1;
    
    private String text2;
    
    private String text3;
    
    private String text4;
    
    private String currnodeid;
    
    private Integer requaresealnum;
    
    private Integer flowfilter;
    
    private Integer orgfilter;
    
    private Integer contentfilter;
    
    private Integer processfilter;
    
    public void testSync(String userid){
    	synchronized ("_testSync_"+ userid) {
			// 这样子性能损失是不是很大?
		}
    }

    public Integer getSignid() {
        return signid;
    }

    public void setSignid(Integer signid) {
        this.signid = signid;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }
    
    public String getResid() {
        return resid;
    }

    public void setResid(String Resid) {
        this.resid = resid == null ? null : resid.trim();
    }

    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid == null ? null : tempid.trim();
    }

    public String getSigntype() {
        return signtype;
    }

    public void setSigntype(String signtype) {
        this.signtype = signtype == null ? null : signtype.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme == null ? null : scheme.trim();
    }

    public Short getDelflag() {
        return delflag;
    }

    public void setDelflag(Short delflag) {
        this.delflag = delflag;
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

	public Integer getSeal() {
		return seal;
	}

	public void setSeal(Integer seal) {
		this.seal = seal;
	}

	public Integer getNotation() {
		return notation;
	}

	public void setNotation(Integer notation) {
		this.notation = notation;
	}

	public String getFlowdefid() {
		return flowdefid;
	}

	public void setFlowdefid(String flowdefid) {
		this.flowdefid = flowdefid;
	}

	public String getIsnotcheckprogress() {
		return isnotcheckprogress;
	}

	public void setIsnotcheckprogress(String isnotcheckprogress) {
		this.isnotcheckprogress = isnotcheckprogress;
	}

	public Integer getPsignid() {
		return psignid;
	}

	public void setPsignid(Integer psignid) {
		this.psignid = psignid;
	}

	public String getSolutionid() {
		return solutionid;
	}

	public void setSolutionid(String solutionid) {
		this.solutionid = solutionid;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	public Integer getIsseal() {
		return isseal;
	}

	public void setIsseal(Integer isseal) {
		this.isseal = isseal;
	}

	public Integer getIsnotation() {
		return isnotation;
	}

	public void setIsnotation(Integer isnotation) {
		this.isnotation = isnotation;
	}
	
	public String getProtectnode() {
		return protectnode;
	}

	public void setProtectnode(String protectnode) {
		this.protectnode = protectnode;
	}
	
	public String getSealtype() {
		return sealtype;
	}
	
	public void setSealtype(String sealtype) {
		this.sealtype = sealtype;
	}
	
	public String getText1(){
		return text1;
	}
	
	public void setText1(String text1) {
		this.text1 = text1;
	}
	
	public String getText2(){
		return text2;
	}
	
	public void setText2(String text2) {
		this.text2 = text2;
	}
	
	public String getText3(){
		return text3;
	}
	
	public void setText3(String text3) {
		this.text3 = text3;
	}
	
	public String getText4(){
		return text4;
	}
	
	public void setText4(String text4) {
		this.text4 = text4;
	}

	public String getCurrnodeid() {
		return currnodeid;
	}

	public void setCurrnodeid(String currnodeid) {
		this.currnodeid = currnodeid;
	}

	public Integer getRequaresealnum() {
		return requaresealnum;
	}

	public void setRequaresealnum(Integer requaresealnum) {
		this.requaresealnum = requaresealnum;
	}

	public Integer getFlowfilter() {
		return flowfilter;
	}

	public void setFlowfilter(Integer flowfilter) {
		this.flowfilter = flowfilter;
	}

	public Integer getOrgfilter() {
		return orgfilter;
	}

	public void setOrgfilter(Integer orgfilter) {
		this.orgfilter = orgfilter;
	}

	public Integer getContentfilter() {
		return contentfilter;
	}

	public void setContentfilter(Integer contentfilter) {
		this.contentfilter = contentfilter;
	}

	public Integer getProcessfilter() {
		return processfilter;
	}

	public void setProcessfilter(Integer processfilter) {
		this.processfilter = processfilter;
	}
	
}
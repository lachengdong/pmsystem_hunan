package com.sinog2c.model.commutationParole;

public class UvFlowDoc {
    private String applyid;

    private String applyname;
    
    private String departid;

    private String flowdefid;

    private Integer yearcasenumber;

    private String templetname;

    private String docconent;
    
    private String anjianleixing;

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
    
    public String getDepartid() {
		return departid;
	}

	public void setDepartid(String departid) {
		this.departid = departid;
	}

	public String getFlowdefid() {
        return flowdefid;
    }

    public void setFlowdefid(String flowdefid) {
        this.flowdefid = flowdefid == null ? null : flowdefid.trim();
    }

    public Integer getYearcasenumber() {
        return yearcasenumber;
    }

    public void setYearcasenumber(Integer yearcasenumber) {
        this.yearcasenumber = yearcasenumber;
    }

    public String getTempletname() {
        return templetname;
    }

    public void setTempletname(String templetname) {
        this.templetname = templetname == null ? null : templetname.trim();
    }

    public String getDocconent() {
        return docconent;
    }

    public void setDocconent(String docconent) {
        this.docconent = docconent == null ? null : docconent.trim();
    }

	public String getAnjianleixing() {
		return anjianleixing;
	}

	public void setAnjianleixing(String anjianleixing) {
		this.anjianleixing = anjianleixing;
	}
    
    
}
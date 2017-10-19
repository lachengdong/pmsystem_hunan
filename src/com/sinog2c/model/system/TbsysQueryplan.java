package com.sinog2c.model.system;

import java.util.Date;
/**
 * 查询方案信息表
 * @author liuxin
 * 2014-7-22 14:02:30
 */
public class TbsysQueryplan {
    private Integer planid;

    private Integer pplanid;

    private String name;

    private String resid;

    private String delflag;

    private String datarelation;

    private Date optime;

    private String opid;

    private String reportortemp;
    
    public Integer getPlanid() {
        return planid;
    }

    public void setPlanid(Integer planid) {
        this.planid = planid;
    }

    public Integer getPplanid() {
        return pplanid;
    }

    public void setPplanid(Integer pplanid) {
        this.pplanid = pplanid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid == null ? null : resid.trim();
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

    public String getDatarelation() {
        return datarelation;
    }

    public void setDatarelation(String datarelation) {
        this.datarelation = datarelation == null ? null : datarelation.trim();
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

	public String getReportortemp() {
		return reportortemp;
	}

	public void setReportortemp(String reportortemp) {
		this.reportortemp = reportortemp;
	}
    
    
}
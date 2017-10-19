package com.sinog2c.model.oa;

import java.math.BigDecimal;
import java.util.Date;

import com.sinog2c.util.common.DateUtil;

public class OAyz {
    private String yzid;

    private String bgr;

    private BigDecimal userid;

    private BigDecimal departmentid;

    private String yzmc;

    private String yzym;

    private String qrzt;

    private Date jsrq;

    private Date optime;

    private String opid;

    private String remark;
    
    private String helpStr;

    public String getYzid() {
        return yzid;
    }

    public void setYzid(String yzid) {
        this.yzid = yzid == null ? null : yzid.trim();
    }

    public String getBgr() {
        return bgr;
    }

    public void setBgr(String bgr) {
        this.bgr = bgr == null ? null : bgr.trim();
    }

    public BigDecimal getUserid() {
        return userid;
    }

    public void setUserid(BigDecimal userid) {
        this.userid = userid;
    }

    public BigDecimal getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(BigDecimal departmentid) {
        this.departmentid = departmentid;
    }

    public String getYzmc() {
        return yzmc;
    }

    public void setYzmc(String yzmc) {
        this.yzmc = yzmc == null ? null : yzmc.trim();
    }

    public String getYzym() {
        return yzym;
    }

    public void setYzym(String yzym) {
        this.yzym = yzym == null ? null : yzym.trim();
    }

    public String getQrzt() {
        return qrzt;
    }

    public void setQrzt(String qrzt) {
        this.qrzt = qrzt == null ? null : qrzt.trim();
    }

    public Date getJsrq() {
        return jsrq;
    }

    public void setJsrq(Date jsrq) {
        this.jsrq = jsrq;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getHelpStr() {
		return helpStr;
	}

	public void setHelpStr(String helpStr) {
		this.helpStr = helpStr;
	}
    
}
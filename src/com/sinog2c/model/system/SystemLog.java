package com.sinog2c.model.system;

import java.util.Date;

public class SystemLog {
    private Integer logid;

    private String logtype;

    private String opcontent;

    private String opaction;

    private String loginmac;

    private String loginip;

    private int status;

    private String remark;

    private String orgid;

    private Date optime;

    private String opid;

    private String opname;

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public String getLogtype() {
        return logtype;
    }

    public void setLogtype(String logtype) {
        this.logtype = logtype == null ? null : logtype.trim();
    }

    public String getOpcontent() {
        return opcontent;
    }

    public void setOpcontent(String opcontent) {
        this.opcontent = opcontent == null ? null : opcontent.trim();
    }

    public String getOpaction() {
        return opaction;
    }

    public void setOpaction(String opaction) {
        this.opaction = opaction == null ? null : opaction.trim();
    }

    public String getLoginmac() {
        return loginmac;
    }

    public void setLoginmac(String loginmac) {
        this.loginmac = loginmac == null ? null : loginmac.trim();
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip == null ? null : loginip.trim();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
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

    public String getOpname() {
        return opname;
    }

    public void setOpname(String opname) {
        this.opname = opname == null ? null : opname.trim();
    }
}
package com.sinog2c.model.system;

import java.util.Date;

public class PrintSchemeConfig extends PrintSchemeConfigKey {
    private String ptype;

    private Short pnumber;

    private Short sn;

    private String remark;

    private Date optime;

    private String opid;

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype == null ? null : ptype.trim();
    }

    public Short getPnumber() {
        return pnumber;
    }

    public void setPnumber(Short pnumber) {
        this.pnumber = pnumber;
    }

    public Short getSn() {
        return sn;
    }

    public void setSn(Short sn) {
        this.sn = sn;
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
}
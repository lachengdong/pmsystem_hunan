package com.sinog2c.model.assessAndPunish;

import java.util.Date;

public class TbsflowCaseCriminal {
    private String crimid;

    private String flowdraftid;

    private String flowdefid;

    private String flowid;

    private String departid;

    private String caseno;

    private Short casetype;

    private Short state;

    private Date endtime;

    private Short isdefer;

    private Integer defermonth;

    private Date defertime;

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getFlowdraftid() {
        return flowdraftid;
    }

    public void setFlowdraftid(String flowdraftid) {
        this.flowdraftid = flowdraftid == null ? null : flowdraftid.trim();
    }

    public String getFlowdefid() {
        return flowdefid;
    }

    public void setFlowdefid(String flowdefid) {
        this.flowdefid = flowdefid == null ? null : flowdefid.trim();
    }

    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid == null ? null : flowid.trim();
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
    }

    public String getCaseno() {
        return caseno;
    }

    public void setCaseno(String caseno) {
        this.caseno = caseno == null ? null : caseno.trim();
    }

    public Short getCasetype() {
        return casetype;
    }

    public void setCasetype(Short casetype) {
        this.casetype = casetype;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Short getIsdefer() {
        return isdefer;
    }

    public void setIsdefer(Short isdefer) {
        this.isdefer = isdefer;
    }

    public Integer getDefermonth() {
        return defermonth;
    }

    public void setDefermonth(Integer defermonth) {
        this.defermonth = defermonth;
    }

    public Date getDefertime() {
        return defertime;
    }

    public void setDefertime(Date defertime) {
        this.defertime = defertime;
    }
}
package com.sinog2c.model.khjc;

import java.util.Date;

public class KhjcTbflowDeliver {
    private String flowdefid;

    private String departid;

    private String nodeid;

    private String orderby;

    private String textname;

    private String datename;

    private String signaname;

    private String isseecheck;

    private Date createtime;

    private String remark;

    private String menuid;

    public String getFlowdefid() {
        return flowdefid;
    }

    public void setFlowdefid(String flowdefid) {
        this.flowdefid = flowdefid == null ? null : flowdefid.trim();
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid == null ? null : nodeid.trim();
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby == null ? null : orderby.trim();
    }

    public String getTextname() {
        return textname;
    }

    public void setTextname(String textname) {
        this.textname = textname == null ? null : textname.trim();
    }

    public String getDatename() {
        return datename;
    }

    public void setDatename(String datename) {
        this.datename = datename == null ? null : datename.trim();
    }

    public String getSignaname() {
        return signaname;
    }

    public void setSignaname(String signaname) {
        this.signaname = signaname == null ? null : signaname.trim();
    }

    public String getIsseecheck() {
        return isseecheck;
    }

    public void setIsseecheck(String isseecheck) {
        this.isseecheck = isseecheck == null ? null : isseecheck.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid == null ? null : menuid.trim();
    }
}
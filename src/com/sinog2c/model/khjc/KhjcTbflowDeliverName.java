package com.sinog2c.model.khjc;

import java.math.BigDecimal;
import java.util.Date;

public class KhjcTbflowDeliverName {
    private String snodeid;

    private String explain;

    private String state;

    private String departid;

    private String remark;

    private Date optime;

    private BigDecimal orderby;

    public String getSnodeid() {
        return snodeid;
    }

    public void setSnodeid(String snodeid) {
        this.snodeid = snodeid == null ? null : snodeid.trim();
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
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

    public BigDecimal getOrderby() {
        return orderby;
    }

    public void setOrderby(BigDecimal orderby) {
        this.orderby = orderby;
    }
}
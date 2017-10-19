package com.sinog2c.model.system;

import java.math.BigDecimal;

public class TbViewMaintain {
    private String id;

    private String flowdefid;

    private String provinceid;

    private String nodeid;

    private BigDecimal days;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFlowdefid() {
        return flowdefid;
    }

    public void setFlowdefid(String flowdefid) {
        this.flowdefid = flowdefid == null ? null : flowdefid.trim();
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid == null ? null : provinceid.trim();
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid == null ? null : nodeid.trim();
    }

    public BigDecimal getDays() {
        return days;
    }

    public void setDays(BigDecimal days) {
        this.days = days;
    }
}
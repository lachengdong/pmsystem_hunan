package com.sinog2c.model.flow;

public class TbFlowPersonConfigKey {
    private String departid;

    private String flowdefid;

    private String nodeid;

    private String userid;

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
    }

    public String getFlowdefid() {
        return flowdefid;
    }

    public void setFlowdefid(String flowdefid) {
        this.flowdefid = flowdefid == null ? null : flowdefid.trim();
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid == null ? null : nodeid.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
}
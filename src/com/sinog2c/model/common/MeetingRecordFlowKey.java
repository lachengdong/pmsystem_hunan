package com.sinog2c.model.common;

public class MeetingRecordFlowKey {
    private String flowdraftid;

    private String crimid;

    public String getFlowdraftid() {
        return flowdraftid;
    }

    public void setFlowdraftid(String flowdraftid) {
        this.flowdraftid = flowdraftid == null ? null : flowdraftid.trim();
    }

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }
}
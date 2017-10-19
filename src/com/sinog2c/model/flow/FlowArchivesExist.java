package com.sinog2c.model.flow;

import java.util.Date;

public class FlowArchivesExist {
    private String flowdraftid;

    private Integer state;

    private Date starttime;

    private Date endtime;

    public String getFlowdraftid() {
        return flowdraftid;
    }

    public void setFlowdraftid(String flowdraftid) {
        this.flowdraftid = flowdraftid == null ? null : flowdraftid.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}
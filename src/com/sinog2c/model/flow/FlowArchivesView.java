package com.sinog2c.model.flow;

import java.util.Date;

public class FlowArchivesView {
    private String flowid;

    private String applyid;

    private String personid;

    private String archiveid;

    private Date starttime;

    private Date endtime;

    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid == null ? null : flowid.trim();
    }

    public String getApplyid() {
        return applyid;
    }

    public void setApplyid(String applyid) {
        this.applyid = applyid == null ? null : applyid.trim();
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid == null ? null : personid.trim();
    }

    public String getArchiveid() {
        return archiveid;
    }

    public void setArchiveid(String archiveid) {
        this.archiveid = archiveid;
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
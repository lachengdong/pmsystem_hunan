package com.sinog2c.model.khjc;

public class TkhWorkdayKey {
    private String crimid;

    private String workdate;

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getWorkdate() {
        return workdate;
    }

    public void setWorkdate(String workdate) {
        this.workdate = workdate == null ? null : workdate.trim();
    }
}
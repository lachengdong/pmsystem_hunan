package com.sinog2c.model.courtjoint;

import java.util.Date;

public class TbxfOpenCourt {
    private String crimid;

    private String casenum;

    private String courtyear;

    private String courtshort;

    private String handlecourt;

    private String batchid;

    private Date opencourttime;

    private Date opencourtend;

    private String opencourtsite;

    private Date opencourtdate;

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid;
    }

    public String getCasenum() {
        return casenum;
    }

    public void setCasenum(String casenum) {
        this.casenum = casenum;
    }

    public String getCourtyear() {
        return courtyear;
    }

    public void setCourtyear(String courtyear) {
        this.courtyear = courtyear;
    }

    public String getCourtshort() {
        return courtshort;
    }

    public void setCourtshort(String courtshort) {
        this.courtshort = courtshort;
    }

    public String getHandlecourt() {
        return handlecourt;
    }

    public void setHandlecourt(String handlecourt) {
        this.handlecourt = handlecourt;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public Date getOpencourttime() {
        return opencourttime;
    }

    public void setOpencourttime(Date opencourttime) {
        this.opencourttime = opencourttime;
    }

    public Date getOpencourtend() {
        return opencourtend;
    }

    public void setOpencourtend(Date opencourtend) {
        this.opencourtend = opencourtend;
    }

    public String getOpencourtsite() {
        return opencourtsite;
    }

    public void setOpencourtsite(String opencourtsite) {
        this.opencourtsite = opencourtsite;
    }

    public Date getOpencourtdate() {
        return opencourtdate;
    }

    public void setOpencourtdate(Date opencourtdate) {
        this.opencourtdate = opencourtdate;
    }
}
package com.sinog2c.model.prisoner;

import java.util.Date;

public class TbxfoldilldisabilityKey {
    private String crimid;

    private Date confirmtime;

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public Date getConfirmtime() {
        return confirmtime;
    }

    public void setConfirmtime(Date confirmtime) {
        this.confirmtime = confirmtime;
    }
}
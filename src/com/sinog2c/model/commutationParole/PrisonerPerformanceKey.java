package com.sinog2c.model.commutationParole;

import java.util.Date;

public class PrisonerPerformanceKey {
    private String criid;

    private Date optime;

    public String getCriid() {
        return criid;
    }

    public void setCriid(String criid) {
        this.criid = criid == null ? null : criid.trim();
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }
}
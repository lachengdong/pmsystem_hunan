package com.sinog2c.model.dbmsnew;

public class DbmsDatasTableNewKey {
    private String ddtid;

    private String ddcid;

    public String getDdtid() {
        return ddtid;
    }

    public void setDdtid(String ddtid) {
        this.ddtid = ddtid == null ? null : ddtid.trim();
    }

    public String getDdcid() {
        return ddcid;
    }

    public void setDdcid(String ddcid) {
        this.ddcid = ddcid == null ? null : ddcid.trim();
    }
}
package com.sinog2c.model.dbmsnew;

public class DbmsDatasChemeDetailNewKey {
    private String ddcid;

    private String dcdid;

    public String getDdcid() {
        return ddcid;
    }

    public void setDdcid(String ddcid) {
        this.ddcid = ddcid == null ? null : ddcid.trim();
    }

    public String getDcdid() {
        return dcdid;
    }

    public void setDcdid(String dcdid) {
        this.dcdid = dcdid == null ? null : dcdid.trim();
    }
}
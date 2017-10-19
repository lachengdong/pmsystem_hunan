package com.sinog2c.model.system;

public class SystemDictionaryTemplateKey {
    private String tempid;

    private String ename;

    private String ecolname;

    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid == null ? null : tempid.trim();
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename == null ? null : ename.trim();
    }

    public String getEcolname() {
        return ecolname;
    }

    public void setEcolname(String ecolname) {
        this.ecolname = ecolname == null ? null : ecolname.trim();
    }
}
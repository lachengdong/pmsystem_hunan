package com.sinog2c.model.system;

public class SystemDictionaryKey {
    private String ename;

    private String ecolname;

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
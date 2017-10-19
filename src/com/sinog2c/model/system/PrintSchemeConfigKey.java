package com.sinog2c.model.system;

public class PrintSchemeConfigKey {
    private Integer psid;

    private String confid;

    public Integer getPsid() {
        return psid;
    }

    public void setPsid(Integer psid) {
        this.psid = psid;
    }

    public String getConfid() {
        return confid;
    }

    public void setConfid(String confid) {
        this.confid = confid == null ? null : confid.trim();
    }
}
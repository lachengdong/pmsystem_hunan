package com.sinog2c.model.flow;

public class FlowArchivesSettingKey {
    private String tempid;

    private String departid;

    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid == null ? null : tempid.trim();
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
    }
}
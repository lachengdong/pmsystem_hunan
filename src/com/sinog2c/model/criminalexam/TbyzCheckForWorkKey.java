package com.sinog2c.model.criminalexam;

public class TbyzCheckForWorkKey {
    private String crimid;

    private String yeardate;

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getYeardate() {
        return yeardate;
    }

    public void setYeardate(String yeardate) {
        this.yeardate = yeardate == null ? null : yeardate.trim();
    }
}
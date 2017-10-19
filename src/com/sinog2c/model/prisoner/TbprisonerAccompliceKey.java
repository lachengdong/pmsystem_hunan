package com.sinog2c.model.prisoner;

public class TbprisonerAccompliceKey {
    private String accid;

    private String crimid;

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid == null ? null : accid.trim();
    }

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }
}
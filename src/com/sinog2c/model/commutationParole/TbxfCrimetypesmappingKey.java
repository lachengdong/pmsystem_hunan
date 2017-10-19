package com.sinog2c.model.commutationParole;

public class TbxfCrimetypesmappingKey {
    private String crimid;

    private Integer typeid;

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }
}
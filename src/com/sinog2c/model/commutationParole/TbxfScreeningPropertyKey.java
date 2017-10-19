package com.sinog2c.model.commutationParole;

public class TbxfScreeningPropertyKey {
    private String departid;

    private Short type;

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }
}
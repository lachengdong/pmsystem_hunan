package com.sinog2c.model.commutationParole;

public class TbdataSentchageKey {
    private String crimid;

    private String courtsanction;

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getCourtsanction() {
        return courtsanction;
    }

    public void setCourtsanction(String courtsanction) {
        this.courtsanction = courtsanction == null ? null : courtsanction.trim();
    }
}
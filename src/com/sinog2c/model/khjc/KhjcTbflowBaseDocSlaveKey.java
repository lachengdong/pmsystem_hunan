package com.sinog2c.model.khjc;

public class KhjcTbflowBaseDocSlaveKey {
    private String docid;

    private Short sn;

    private String templetid;

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid == null ? null : docid.trim();
    }

    public Short getSn() {
        return sn;
    }

    public void setSn(Short sn) {
        this.sn = sn;
    }

    public String getTempletid() {
        return templetid;
    }

    public void setTempletid(String templetid) {
        this.templetid = templetid == null ? null : templetid.trim();
    }
}
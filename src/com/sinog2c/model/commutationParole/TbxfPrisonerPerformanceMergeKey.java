package com.sinog2c.model.commutationParole;

public class TbxfPrisonerPerformanceMergeKey {
    private String crimid;

    private Integer batchid;

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public Integer getBatchid() {
        return batchid;
    }

    public void setBatchid(Integer batchid) {
        this.batchid = batchid;
    }
}
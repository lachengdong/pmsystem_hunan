package com.sinog2c.model.system;

public class TbxfPrisonerperformancemergeKey {
    private String criid;

    private Integer batchid;

    public String getCriid() {
        return criid;
    }

    public void setCriid(String criid) {
        this.criid = criid == null ? null : criid.trim();
    }

    public Integer getBatchid() {
        return batchid;
    }

    public void setBatchid(Integer batchid) {
        this.batchid = batchid;
    }
}
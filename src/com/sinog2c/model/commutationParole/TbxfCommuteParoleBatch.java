package com.sinog2c.model.commutationParole;

import java.util.Date;

public class TbxfCommuteParoleBatch {
    private Long batchid;

    private String departid;

    private String curyear;

    private Short batch;

    private Date examineend;

    private Date remainderpunishment;

    private String remark;

    private String opid;

    private Date optime;

    public Long getBatchid() {
        return batchid;
    }

    public void setBatchid(long batchid2) {
        this.batchid = batchid2;
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
    }

    public String getCuryear() {
        return curyear;
    }

    public void setCuryear(String curyear) {
        this.curyear = curyear == null ? null : curyear.trim();
    }

    public Short getBatch() {
        return batch;
    }

    public void setBatch(Short batch) {
        this.batch = batch;
    }

    public Date getExamineend() {
        return examineend;
    }

    public void setExamineend(Date examineend) {
        this.examineend = examineend;
    }

    public Date getRemainderpunishment() {
        return remainderpunishment;
    }

    public void setRemainderpunishment(Date remainderpunishment) {
        this.remainderpunishment = remainderpunishment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid == null ? null : opid.trim();
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }
}
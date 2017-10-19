package com.sinog2c.model.system;

import java.util.Date;

public class TbsysCode {
    private Integer noid;

    private String codetype;

    private String codeid;

    private String pcodeid;

    private String name;

    private Integer sn;

    private String scearch;

    private String remark;

    private String delflag;

    private String orgid;

    private Date optime;

    private String opid;

    public Integer getNoid() {
        return noid;
    }

    public void setNoid(Integer noid) {
        this.noid = noid;
    }

    public String getCodetype() {
        return codetype;
    }

    public void setCodetype(String codetype) {
        this.codetype = codetype == null ? null : codetype.trim();
    }

    public String getCodeid() {
        return codeid;
    }

    public void setCodeid(String codeid) {
        this.codeid = codeid == null ? null : codeid.trim();
    }

    public String getPcodeid() {
        return pcodeid;
    }

    public void setPcodeid(String pcodeid) {
        this.pcodeid = pcodeid == null ? null : pcodeid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getScearch() {
        return scearch;
    }

    public void setScearch(String scearch) {
        this.scearch = scearch == null ? null : scearch.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid == null ? null : opid.trim();
    }
}
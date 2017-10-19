package com.sinog2c.model.system;

public class TbsysLocalcode {
    private String noid;

    private String codetype;

    private String codeid;

    private String pcodeid;

    private String name;

    private String oname;

    private Integer sn;

    private String scearch;

    private Short isdel;

    private String orgid;

    private String orgpid;

    private String orgppid;

    private String remark;

    private String oremark;

    private String opid;

    private String optime;

    public String getNoid() {
        return noid;
    }

    public void setNoid(String noid) {
        this.noid = noid == null ? null : noid.trim();
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

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname == null ? null : oname.trim();
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

    public Short getIsdel() {
        return isdel;
    }

    public void setIsdel(Short isdel) {
        this.isdel = isdel;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

    public String getOrgpid() {
        return orgpid;
    }

    public void setOrgpid(String orgpid) {
        this.orgpid = orgpid == null ? null : orgpid.trim();
    }

    public String getOrgppid() {
        return orgppid;
    }

    public void setOrgppid(String orgppid) {
        this.orgppid = orgppid == null ? null : orgppid.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOremark() {
        return oremark;
    }

    public void setOremark(String oremark) {
        this.oremark = oremark == null ? null : oremark.trim();
    }

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid == null ? null : opid.trim();
    }

    public String getOptime() {
        return optime;
    }

    public void setOptime(String optime) {
        this.optime = optime == null ? null : optime.trim();
    }
}
package com.sinog2c.model.prisoner;

import java.math.BigDecimal;
import java.util.Date;

public class TbprisonerResumHis {
    private Integer resumeid;

    private String crimid;

    private String begindate;

    private String enddate;

    private String orgaddress;

    private String orgname;

    private String postalcode;

    private String vocation;

    private String reterence;

    private String reterencelink;

    private String identity;

    private String clevel;

    private String duty;

    private BigDecimal orgmark;

    private String remark;

    private String delflag;

    private String opid;

    private Date optime;

    public Integer getResumeid() {
        return resumeid;
    }

    public void setResumeid(Integer resumeid) {
        this.resumeid = resumeid;
    }

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate == null ? null : begindate.trim();
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }

    public String getOrgaddress() {
        return orgaddress;
    }

    public void setOrgaddress(String orgaddress) {
        this.orgaddress = orgaddress == null ? null : orgaddress.trim();
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname == null ? null : orgname.trim();
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode == null ? null : postalcode.trim();
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation == null ? null : vocation.trim();
    }

    public String getReterence() {
        return reterence;
    }

    public void setReterence(String reterence) {
        this.reterence = reterence == null ? null : reterence.trim();
    }

    public String getReterencelink() {
        return reterencelink;
    }

    public void setReterencelink(String reterencelink) {
        this.reterencelink = reterencelink == null ? null : reterencelink.trim();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public String getClevel() {
        return clevel;
    }

    public void setClevel(String clevel) {
        this.clevel = clevel == null ? null : clevel.trim();
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty == null ? null : duty.trim();
    }

    public BigDecimal getOrgmark() {
        return orgmark;
    }

    public void setOrgmark(BigDecimal orgmark) {
        this.orgmark = orgmark;
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
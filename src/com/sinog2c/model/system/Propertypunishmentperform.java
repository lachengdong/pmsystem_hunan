package com.sinog2c.model.system;

import java.util.Date;

public class Propertypunishmentperform {
    private int propertyid;

    private String crimid;

    private String name;

    private Long zangkuan;

    private Long fajin;

    private Long moshoucaichan;

    private Long peichang;

    private String lxrlb;

    private String lxrname;

    private String remark1;

    private String remark2;

    private String remark3;

    private Date lxdate;

    public int getPropertyid() {
        return propertyid;
    }

    public void setPropertyid(int propertyid) {
        this.propertyid = propertyid;
    }

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getZangkuan() {
        return zangkuan;
    }

    public void setZangkuan(Long zangkuan) {
        this.zangkuan = zangkuan;
    }

    public Long getFajin() {
        return fajin;
    }

    public void setFajin(Long fajin) {
        this.fajin = fajin;
    }

    public Long getMoshoucaichan() {
        return moshoucaichan;
    }

    public void setMoshoucaichan(Long moshoucaichan) {
        this.moshoucaichan = moshoucaichan;
    }

    public Long getPeichang() {
        return peichang;
    }

    public void setPeichang(Long peichang) {
        this.peichang = peichang;
    }

    public String getLxrlb() {
        return lxrlb;
    }

    public void setLxrlb(String lxrlb) {
        this.lxrlb = lxrlb == null ? null : lxrlb.trim();
    }

    public String getLxrname() {
        return lxrname;
    }

    public void setLxrname(String lxrname) {
        this.lxrname = lxrname == null ? null : lxrname.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3 == null ? null : remark3.trim();
    }

    public Date getLxdate() {
        return lxdate;
    }

    public void setLxdate(Date lxdate) {
        this.lxdate = lxdate;
    }
}
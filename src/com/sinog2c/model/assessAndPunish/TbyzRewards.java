package com.sinog2c.model.assessAndPunish;

import java.util.Date;

public class TbyzRewards {
    private String crimid;

    private Date cbrq;

    private Date pzrq;

    private String jllb;

    private String jlyy;

    private String yyjx;

    private String bz;

    private String gydw;

    private String gymx;

    private String opid;

    private Date optime;

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public Date getCbrq() {
        return cbrq;
    }

    public void setCbrq(Date cbrq) {
        this.cbrq = cbrq;
    }

    public Date getPzrq() {
        return pzrq;
    }

    public void setPzrq(Date pzrq) {
        this.pzrq = pzrq;
    }

    public String getJllb() {
        return jllb;
    }

    public void setJllb(String jllb) {
        this.jllb = jllb == null ? null : jllb.trim();
    }

    public String getJlyy() {
        return jlyy;
    }

    public void setJlyy(String jlyy) {
        this.jlyy = jlyy == null ? null : jlyy.trim();
    }

    public String getYyjx() {
        return yyjx;
    }

    public void setYyjx(String yyjx) {
        this.yyjx = yyjx == null ? null : yyjx.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getGydw() {
        return gydw;
    }

    public void setGydw(String gydw) {
        this.gydw = gydw == null ? null : gydw.trim();
    }

    public String getGymx() {
        return gymx;
    }

    public void setGymx(String gymx) {
        this.gymx = gymx == null ? null : gymx.trim();
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
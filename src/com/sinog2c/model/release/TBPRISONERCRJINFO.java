package com.sinog2c.model.release;

import java.util.Date;

public class TBPRISONERCRJINFO {
    private String crimid;

    private String plentytype;

    private Date inprisondate;

    private Date outprisondate;

    private String ljlb;

    private String qxqh;

    private String qxqx;

    private String dah;

    private String watchhouse;

    private String songfujiguan;

    private Date createtime;

    private String createmender;

    private Date updatetime;

    private String updatemender;

    private String remark;

    private String delflg;

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getPlentytype() {
        return plentytype;
    }

    public void setPlentytype(String plentytype) {
        this.plentytype = plentytype == null ? null : plentytype.trim();
    }

    public Date getInprisondate() {
        return inprisondate;
    }

    public void setInprisondate(Date inprisondate) {
        this.inprisondate = inprisondate;
    }

    public Date getOutprisondate() {
        return outprisondate;
    }

    public void setOutprisondate(Date outprisondate) {
        this.outprisondate = outprisondate;
    }

    public String getLjlb() {
        return ljlb;
    }

    public void setLjlb(String ljlb) {
        this.ljlb = ljlb == null ? null : ljlb.trim();
    }

    public String getQxqh() {
        return qxqh;
    }

    public void setQxqh(String qxqh) {
        this.qxqh = qxqh == null ? null : qxqh.trim();
    }

    public String getQxqx() {
        return qxqx;
    }

    public void setQxqx(String qxqx) {
        this.qxqx = qxqx == null ? null : qxqx.trim();
    }

    public String getDah() {
        return dah;
    }

    public void setDah(String dah) {
        this.dah = dah == null ? null : dah.trim();
    }

    public String getWatchhouse() {
        return watchhouse;
    }

    public void setWatchhouse(String watchhouse) {
        this.watchhouse = watchhouse == null ? null : watchhouse.trim();
    }

    public String getSongfujiguan() {
        return songfujiguan;
    }

    public void setSongfujiguan(String songfujiguan) {
        this.songfujiguan = songfujiguan == null ? null : songfujiguan.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreatemender() {
        return createmender;
    }

    public void setCreatemender(String createmender) {
        this.createmender = createmender == null ? null : createmender.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdatemender() {
        return updatemender;
    }

    public void setUpdatemender(String updatemender) {
        this.updatemender = updatemender == null ? null : updatemender.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDelflg() {
        return delflg;
    }

    public void setDelflg(String delflg) {
        this.delflg = delflg == null ? null : delflg.trim();
    }
}
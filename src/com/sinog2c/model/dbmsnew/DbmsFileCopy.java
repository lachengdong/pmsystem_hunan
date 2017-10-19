package com.sinog2c.model.dbmsnew;

import java.util.Date;

public class DbmsFileCopy {
    private String sdid;

    private String jgxtaddress;

    private String localaddress;

    private Date createtime;

    private String updatemender;

    private Date updatetime;

    private String createmender;

    private String remark;

    private String delflg;

    public String getSdid() {
        return sdid;
    }

    public void setSdid(String sdid) {
        this.sdid = sdid == null ? null : sdid.trim();
    }

    public String getJgxtaddress() {
        return jgxtaddress;
    }

    public void setJgxtaddress(String jgxtaddress) {
        this.jgxtaddress = jgxtaddress == null ? null : jgxtaddress.trim();
    }

    public String getLocaladdress() {
        return localaddress;
    }

    public void setLocaladdress(String localaddress) {
        this.localaddress = localaddress == null ? null : localaddress.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUpdatemender() {
        return updatemender;
    }

    public void setUpdatemender(String updatemender) {
        this.updatemender = updatemender == null ? null : updatemender.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreatemender() {
        return createmender;
    }

    public void setCreatemender(String createmender) {
        this.createmender = createmender == null ? null : createmender.trim();
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
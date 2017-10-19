package com.sinog2c.model.dbmsnew;

import java.util.Date;

public class DbmsCodeTypeNew {
    private String codetypeid;

    private String codetypename;

    private String updatemender;

    private Date updatetime;

    private Date createtime;

    private String createmender;

    private String delflg;

    public String getCodetypeid() {
        return codetypeid;
    }

    public void setCodetypeid(String codetypeid) {
        this.codetypeid = codetypeid == null ? null : codetypeid.trim();
    }

    public String getCodetypename() {
        return codetypename;
    }

    public void setCodetypename(String codetypename) {
        this.codetypename = codetypename == null ? null : codetypename.trim();
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

    public String getDelflg() {
        return delflg;
    }

    public void setDelflg(String delflg) {
        this.delflg = delflg == null ? null : delflg.trim();
    }
}
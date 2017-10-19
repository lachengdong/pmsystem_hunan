package com.sinog2c.model.dbmsnew;

import java.util.Date;

public class DbmsDatabaseNew {
    private String ddid;

    private String ddname;

    private String ddip;

    private String databasename;

    private String ddorg;

    private String databaseuser;

    private String databasepassword;

    private Short port;

    private String databasetype;

    private String updatemender;

    private Date updatetime;

    private Date createtime;

    private String createmender;

    private String delflg;

    private String sdid;

    public String getDdid() {
        return ddid;
    }

    public void setDdid(String ddid) {
        this.ddid = ddid == null ? null : ddid.trim();
    }

    public String getDdname() {
        return ddname;
    }

    public void setDdname(String ddname) {
        this.ddname = ddname == null ? null : ddname.trim();
    }

    public String getDdip() {
        return ddip;
    }

    public void setDdip(String ddip) {
        this.ddip = ddip == null ? null : ddip.trim();
    }

    public String getDatabasename() {
        return databasename;
    }

    public void setDatabasename(String databasename) {
        this.databasename = databasename == null ? null : databasename.trim();
    }

    public String getDdorg() {
        return ddorg;
    }

    public void setDdorg(String ddorg) {
        this.ddorg = ddorg == null ? null : ddorg.trim();
    }

    public String getDatabaseuser() {
        return databaseuser;
    }

    public void setDatabaseuser(String databaseuser) {
        this.databaseuser = databaseuser == null ? null : databaseuser.trim();
    }

    public String getDatabasepassword() {
        return databasepassword;
    }

    public void setDatabasepassword(String databasepassword) {
        this.databasepassword = databasepassword == null ? null : databasepassword.trim();
    }

    public Short getPort() {
        return port;
    }

    public void setPort(Short port) {
        this.port = port;
    }

    public String getDatabasetype() {
        return databasetype;
    }

    public void setDatabasetype(String databasetype) {
        this.databasetype = databasetype == null ? null : databasetype.trim();
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

    public String getSdid() {
        return sdid;
    }

    public void setSdid(String sdid) {
        this.sdid = sdid == null ? null : sdid.trim();
    }
}
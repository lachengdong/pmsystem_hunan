package com.sinog2c.model.dbmsnew;

import java.util.Date;

public class DbmsDatasTableNew extends DbmsDatasTableNewKey {
    private String tablename;

    private String descrition;

    private Integer ddtorder;

    private String updatemender;

    private Date updatetime;

    private String ddtisscreen;

    private String ddtismaintable;

    private String ddtjoinedfields;

    private String shujuguanxi;

    private String daochusql;

    private Date createtime;

    private String createmender;

    private String totable;

    private String delflg;

    private String addcondition;

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition == null ? null : descrition.trim();
    }

    public Integer getDdtorder() {
        return ddtorder;
    }

    public void setDdtorder(Integer ddtorder) {
        this.ddtorder = ddtorder;
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

    public String getDdtisscreen() {
        return ddtisscreen;
    }

    public void setDdtisscreen(String ddtisscreen) {
        this.ddtisscreen = ddtisscreen == null ? null : ddtisscreen.trim();
    }

    public String getDdtismaintable() {
        return ddtismaintable;
    }

    public void setDdtismaintable(String ddtismaintable) {
        this.ddtismaintable = ddtismaintable == null ? null : ddtismaintable.trim();
    }

    public String getDdtjoinedfields() {
        return ddtjoinedfields;
    }

    public void setDdtjoinedfields(String ddtjoinedfields) {
        this.ddtjoinedfields = ddtjoinedfields == null ? null : ddtjoinedfields.trim();
    }

    public String getShujuguanxi() {
        return shujuguanxi;
    }

    public void setShujuguanxi(String shujuguanxi) {
        this.shujuguanxi = shujuguanxi == null ? null : shujuguanxi.trim();
    }

    public String getDaochusql() {
        return daochusql;
    }

    public void setDaochusql(String daochusql) {
        this.daochusql = daochusql == null ? null : daochusql.trim();
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

    public String getTotable() {
        return totable;
    }

    public void setTotable(String totable) {
        this.totable = totable == null ? null : totable.trim();
    }

    public String getDelflg() {
        return delflg;
    }

    public void setDelflg(String delflg) {
        this.delflg = delflg == null ? null : delflg.trim();
    }

    public String getAddcondition() {
        return addcondition;
    }

    public void setAddcondition(String addcondition) {
        this.addcondition = addcondition == null ? null : addcondition.trim();
    }
}
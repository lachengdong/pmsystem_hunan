package com.sinog2c.model.khjc;

import java.util.Date;

public class KhjcTbflowBaseDocSlave extends KhjcTbflowBaseDocSlaveKey {
    private String delflag;

    private Date createtime;

    private String createmender;

    private Date updatetime;

    private String updatemender;

    private String crimid;

    private String docconent;
    
    private String departid;

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
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

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getDocconent() {
        return docconent;
    }

    public void setDocconent(String docconent) {
        this.docconent = docconent == null ? null : docconent.trim();
    }
    
    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
    }
}
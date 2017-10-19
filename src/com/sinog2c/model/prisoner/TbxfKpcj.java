package com.sinog2c.model.prisoner;

import java.util.Date;

public class TbxfKpcj {
    private String propertyid;

    private String crimid;

    private String name;

    private String kpcj;

    private String blrname;

    private String remark1;

    private String remark2;

    private String remark3;

    private String kpdate;

    private Date bldate;

    public String getPropertyid() {
        return propertyid;
    }

    public void setPropertyid(String propertyid) {
        this.propertyid = propertyid == null ? null : propertyid.trim();
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

    public String getKpcj() {
        return kpcj;
    }

    public void setKpcj(String kpcj) {
        this.kpcj = kpcj == null ? null : kpcj.trim();
    }

    public String getBlrname() {
        return blrname;
    }

    public void setBlrname(String blrname) {
        this.blrname = blrname == null ? null : blrname.trim();
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

    public String getKpdate() {
        return kpdate;
    }

    public void setKpdate(String kpdate) {
        this.kpdate = kpdate;
    }

    public Date getBldate() {
        return bldate;
    }

    public void setBldate(Date bldate) {
        this.bldate = bldate;
    }
}
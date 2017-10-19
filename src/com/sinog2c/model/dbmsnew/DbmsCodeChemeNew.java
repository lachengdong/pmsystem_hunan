package com.sinog2c.model.dbmsnew;

import java.util.Date;

public class DbmsCodeChemeNew {

    private String dccid;

    private String codetype;
    
    // code 类型名字,不参与持久化
    private String codetypename;
    
    private String codeid;

    private String codecontent;

    private String targetid;

    private String updatemender;

    private Date updatetime;

    private Date createtime;

    private String createmender;

    private String delflg;
    
    private String orgid;
    
    private String remark;

    public String getDccid() {
        return dccid;
    }

    public void setDccid(String dccid) {
        this.dccid = dccid == null ? null : dccid.trim();
    }

    public String getCodetype() {
        return codetype;
    }

    public void setCodetype(String codetype) {
        this.codetype = codetype == null ? null : codetype.trim();
    }

    public String getCodetypename() {
        return codetypename;
    }

    public void setCodetypename(String codetypename) {
        this.codetypename = codetypename == null ? null : codetypename.trim();
    }

    public String getCodeid() {
        return codeid;
    }

    public void setCodeid(String codeid) {
        this.codeid = codeid == null ? null : codeid.trim();
    }

    public String getCodecontent() {
        return codecontent;
    }

    public void setCodecontent(String codecontent) {
        this.codecontent = codecontent == null ? null : codecontent.trim();
    }

    public String getTargetid() {
        return targetid;
    }

    public void setTargetid(String targetid) {
        this.targetid = targetid == null ? null : targetid.trim();
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
    public String getOrgid() {
		return orgid;
	}
    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
	}
    
    public String getRemark() {
		return remark;
	}
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
	}
}
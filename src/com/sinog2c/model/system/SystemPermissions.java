package com.sinog2c.model.system;

import java.util.Date;

public class SystemPermissions {
	
	/**
	 * 是否选中. 额外设置的参数,不参与持久化
	 */
    private boolean checked;
    
    public boolean isChecked() {
		return checked;
	}
    public void setChecked(boolean checked) {
		this.checked = checked;
	}
    public void checkedIt() {
    	setChecked(true);
	}
    
    private String spid;

    private String spdiscribe;

    private String spparent;

    private String smid;

    private Integer sporderby;

    private Date createtime;

    private String createmender;

    private Date updatetime;

    private String updatemender;

    private String remark;

    private String isshow;

    private String delflg;

    private String rid;

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid == null ? null : spid.trim();
    }

    public String getSpdiscribe() {
        return spdiscribe;
    }

    public void setSpdiscribe(String spdiscribe) {
        this.spdiscribe = spdiscribe == null ? null : spdiscribe.trim();
    }

    public String getSpparent() {
        return spparent;
    }

    public void setSpparent(String spparent) {
        this.spparent = spparent == null ? null : spparent.trim();
    }

    public String getSmid() {
        return smid;
    }

    public void setSmid(String smid) {
        this.smid = smid == null ? null : smid.trim();
    }

    public Integer getSporderby() {
        return sporderby;
    }

    public void setSporderby(Integer sporderby) {
        this.sporderby = sporderby;
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

    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow == null ? null : isshow.trim();
    }

    public String getDelflg() {
        return delflg;
    }

    public void setDelflg(String delflg) {
        this.delflg = delflg == null ? null : delflg.trim();
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid == null ? null : rid.trim();
    }
}
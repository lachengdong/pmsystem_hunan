package com.sinog2c.model.system;

import java.io.Serializable;
import java.util.Date;

public class SystemRole implements Serializable{
	
	private static final long serialVersionUID = -6918125308076488642L;

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
    
    private Integer roleid;

    private String departid;

    private String name;

    private Short sn;

    private String text1;

    private String text2;

    private String text3;

    private String text4;

    private Integer int1;

    private Integer int2;

    private String remark;

    private Date optime;

    private String opid;

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getSn() {
        return sn;
    }

    public void setSn(Short sn) {
        this.sn = sn;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1 == null ? null : text1.trim();
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2 == null ? null : text2.trim();
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3 == null ? null : text3.trim();
    }

    public String getText4() {
        return text4;
    }

    public void setText4(String text4) {
        this.text4 = text4 == null ? null : text4.trim();
    }

    public Integer getInt1() {
        return int1;
    }

    public void setInt1(Integer int1) {
        this.int1 = int1;
    }

    public Integer getInt2() {
        return int2;
    }

    public void setInt2(Integer int2) {
        this.int2 = int2;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid == null ? null : opid.trim();
    }
    
    @Override
    public String toString() {
    	return this.name == null ? "" : this.name.trim();
    }
    @Override
    public boolean equals(Object obj) {
    	if(this == obj){
    		return true;
    	}
    	if(obj instanceof SystemRole){
    		SystemRole other = (SystemRole)obj;
    		Integer id = this.getRoleid();
    		Integer idOther = other.getRoleid();
    		if(null != id && id.equals(idOther)){
    			return true; // 只要ID相等就认为是true
    		}
    	}
    	return super.equals(obj);
    }
    // 覆写 equals时需要覆写 hashCode
    @Override
    public int hashCode() {
    	Integer id = this.getRoleid();
    	if(null == id){
    		return 0;
    	} else {
    		return id; // 直接认为是ID即可,多个对象可以冲突
    	}
    }
}
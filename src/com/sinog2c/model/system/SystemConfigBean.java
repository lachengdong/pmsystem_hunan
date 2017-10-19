package com.sinog2c.model.system;

import java.io.Serializable;
import java.util.Date;

public class SystemConfigBean implements Serializable,Cloneable{
	
	private static final long serialVersionUID = 1L;
	
	public static String CATAGORY_JYCONFIG = "JYCONFIG";
	public static String CATAGORY_SYSTEM = "SYSTEM";
	
    private Integer id;

    private String departid;

    private String catagory;

    private String name;

    private String value;

    private Integer status;

    private Date optime;

    private String opid;

    private String describe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory == null ? null : catagory.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }
    
    @Override
    protected SystemConfigBean clone() throws CloneNotSupportedException {
    	return (SystemConfigBean)super.clone();
    }
}
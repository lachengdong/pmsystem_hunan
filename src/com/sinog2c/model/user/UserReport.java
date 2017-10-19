package com.sinog2c.model.user;

public class UserReport {
    private String rid;

    private String resid;

    private Integer planid;

    private String name;

    private String type;

    private Short sn;

    private String delflag;

    private String remark;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid == null ? null : rid.trim();
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid == null ? null : resid.trim();
    }

    public Integer getPlanid() {
        return planid;
    }

    public void setPlanid(Integer planid) {
        this.planid = planid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Short getSn() {
        return sn;
    }

    public void setSn(Short sn) {
        this.sn = sn;
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
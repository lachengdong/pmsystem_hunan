package com.sinog2c.model.system;

import java.util.Date;
/**
 * 查询方案SQL表
 * @author liuxin
 * 2014-7-22 14:19:28
 */
public class TbsysQueryplansql {
    private Integer sqlid;

    private Integer planid;

    private Short opsign;

    private String sqltext;

    private String delflag;

    private String remark;

    private Date optime;

    private String opid;

    public Integer getSqlid() {
        return sqlid;
    }

    public void setSqlid(Integer sqlid) {
        this.sqlid = sqlid;
    }

    public Integer getPlanid() {
        return planid;
    }

    public void setPlanid(Integer planid) {
        this.planid = planid;
    }

    public Short getOpsign() {
        return opsign;
    }

    public void setOpsign(Short opsign) {
        this.opsign = opsign;
    }

    public String getSqltext() {
        return sqltext;
    }

    public void setSqltext(String sqltext) {
        this.sqltext = sqltext == null ? null : sqltext.trim();
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
}
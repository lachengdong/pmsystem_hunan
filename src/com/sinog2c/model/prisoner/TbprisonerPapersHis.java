package com.sinog2c.model.prisoner;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 证件信息历史表
 * @author liuxin
 * 2014-7-24 ‏‎‏‎11:02:00
 */
public class TbprisonerPapersHis {
    private BigDecimal paperid;

    private String crimid;

    private String type;

    private String code;

    private Date endtime;

    private String delflag;

    private String remark;

    private String opid;

    private Date optime;

    public BigDecimal getPaperid() {
        return paperid;
    }

    public void setPaperid(BigDecimal paperid) {
        this.paperid = paperid;
    }

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
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

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid == null ? null : opid.trim();
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }
}
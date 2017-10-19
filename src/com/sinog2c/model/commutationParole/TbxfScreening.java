package com.sinog2c.model.commutationParole;

import java.util.Date;

public class TbxfScreening {
    private String crimid;

    private String departid;

    private Short status;

    private Short cstatus;

    private Long batchid;

    private Integer punid;

    private Integer refid;

    private Date optime;
    
    private Integer int1;  //建议幅度
    
private String tmp1;  //案件性质id
    
    private String tmp2;  //案件性质name

    
    public String getTmp1() {
		return tmp1;
	}

	public void setTmp1(String tmp1) {
		this.tmp1 = tmp1;
	}

	public String getTmp2() {
		return tmp2;
	}

	public void setTmp2(String tmp2) {
		this.tmp2 = tmp2;
	}



    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getDepartid() {
        return departid;
    }

    public void setDepartid(String departid) {
        this.departid = departid == null ? null : departid.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getCstatus() {
        return cstatus;
    }

    public void setCstatus(Short cstatus) {
        this.cstatus = cstatus;
    }

    public Long getBatchid() {
        return batchid;
    }

    public void setBatchid(Long batchid) {
        this.batchid = batchid;
    }

    public Integer getPunid() {
        return punid;
    }

    public void setPunid(Integer punid) {
        this.punid = punid;
    }

    public Integer getRefid() {
        return refid;
    }

    public void setRefid(Integer refid) {
        this.refid = refid;
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

	public Integer getInt1() {
		return int1;
	}

	public void setInt1(Integer int1) {
		this.int1 = int1;
	}
}
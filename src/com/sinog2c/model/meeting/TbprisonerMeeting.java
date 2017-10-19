package com.sinog2c.model.meeting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TbprisonerMeeting implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sdid;

    private String mkey;

    private BigDecimal mtype;

    private String mname;

    private String mplace;

    private String mpeople;

    private String mcompere;

    private String mrecorder;

    private BigDecimal mshouldcount;

    private BigDecimal mattendcount;

    private String mliexi;

    private String listyear;

    private Date optime;
    
    private String opid;

    private String tempid;

    public String getSdid() {
        return sdid;
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
		this.opid = opid;
	}

	public String getTempid() {
		return tempid;
	}

	public void setTempid(String tempid) {
		this.tempid = tempid;
	}

	public void setSdid(String sdid) {
        this.sdid = sdid == null ? null : sdid.trim();
    }

    public String getMkey() {
        return mkey;
    }

    public void setMkey(String mkey) {
        this.mkey = mkey == null ? null : mkey.trim();
    }

    public BigDecimal getMtype() {
        return mtype;
    }

    public void setMtype(BigDecimal mtype) {
        this.mtype = mtype;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    public String getMplace() {
        return mplace;
    }

    public void setMplace(String mplace) {
        this.mplace = mplace == null ? null : mplace.trim();
    }

    public String getMpeople() {
        return mpeople;
    }

    public void setMpeople(String mpeople) {
        this.mpeople = mpeople == null ? null : mpeople.trim();
    }

    public String getMcompere() {
        return mcompere;
    }

    public void setMcompere(String mcompere) {
        this.mcompere = mcompere == null ? null : mcompere.trim();
    }

    public String getMrecorder() {
        return mrecorder;
    }

    public void setMrecorder(String mrecorder) {
        this.mrecorder = mrecorder == null ? null : mrecorder.trim();
    }

    public BigDecimal getMshouldcount() {
        return mshouldcount;
    }

    public void setMshouldcount(BigDecimal mshouldcount) {
        this.mshouldcount = mshouldcount;
    }

    public BigDecimal getMattendcount() {
        return mattendcount;
    }

    public void setMattendcount(BigDecimal mattendcount) {
        this.mattendcount = mattendcount;
    }

    public String getMliexi() {
        return mliexi;
    }

    public void setMliexi(String mliexi) {
        this.mliexi = mliexi == null ? null : mliexi.trim();
    }

    public String getListyear() {
        return listyear;
    }

    public void setListyear(String listyear) {
        this.listyear = listyear == null ? null : listyear.trim();
    }

  
}
package com.sinog2c.model.system;

import java.io.Serializable;
import java.util.Date;

public class PingYiResource implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sdid;// 部門編號
	private Date srarttime;// 立案開始時間;
	private Date endtime;// 立案結束時間
	private String mtype;//會議記錄類型
	private String sqid;
	private String coverid;
	private String endid;
	private String mname;//會議名稱
	private Date mdate;//會議時間
	private String mplace;//會議地點
	private String mpeople;//、、參會警察
	private String mcompere;//主持人
	private String mrecorder;//記錄員
	private String mtopic;//、、會議議題
	private String mprior;// 大字段會議内容前
	private String mlater;// 大字段會議内容后
	private String mresolution;// 大字段
	private int mshouldcount;//出席人數
	private int mattendcount;//實到人數
	private String mrecord;// 大字段
	private String remark;
	private Date createtime;//、、創建時間
	private String createmender;
	private Date updatetime;
	private String updatemender;
	private String delflg;
	private String mreferenceid;
	private int mxuhao;//序號
	private int listyear;
	private int listtime;
	private String mcriminaltype;//會議記錄類型
	private String mflag;
	private String resid;//資源id
	private String mkey;//唯一標識符
	private String mliexi;
	private String mquexi;

	public String getSdid() {
		return sdid;
	}

	public void setSdid(String sdid) {
		this.sdid = sdid;
	}

	public Date getSrarttime() {
		return srarttime;
	}

	public void setSrarttime(Date srarttime) {
		this.srarttime = srarttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getMtype() {
		return mtype;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	public String getSqid() {
		return sqid;
	}

	public void setSqid(String sqid) {
		this.sqid = sqid;
	}

	public String getCoverid() {
		return coverid;
	}

	public void setCoverid(String coverid) {
		this.coverid = coverid;
	}

	public String getEndid() {
		return endid;
	}

	public void setEndid(String endid) {
		this.endid = endid;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public Date getMdate() {
		return mdate;
	}

	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}

	public String getMplace() {
		return mplace;
	}

	public void setMplace(String mplace) {
		this.mplace = mplace;
	}

	public String getMpeople() {
		return mpeople;
	}

	public void setMpeople(String mpeople) {
		this.mpeople = mpeople;
	}

	public String getMcompere() {
		return mcompere;
	}

	public void setMcompere(String mcompere) {
		this.mcompere = mcompere;
	}

	public String getMrecorder() {
		return mrecorder;
	}

	public void setMrecorder(String mrecorder) {
		this.mrecorder = mrecorder;
	}

	public String getMtopic() {
		return mtopic;
	}

	public void setMtopic(String mtopic) {
		this.mtopic = mtopic;
	}

	public String getMprior() {
		return mprior;
	}

	public void setMprior(String mprior) {
		this.mprior = mprior;
	}

	public String getMlater() {
		return mlater;
	}

	public void setMlater(String mlater) {
		this.mlater = mlater;
	}

	public String getMresolution() {
		return mresolution;
	}

	public void setMresolution(String mresolution) {
		this.mresolution = mresolution;
	}

	public int getMshouldcount() {
		return mshouldcount;
	}

	public void setMshouldcount(int mshouldcount) {
		this.mshouldcount = mshouldcount;
	}

	public int getMattendcount() {
		return mattendcount;
	}

	public void setMattendcount(int mattendcount) {
		this.mattendcount = mattendcount;
	}

	public String getMrecord() {
		return mrecord;
	}

	public void setMrecord(String mrecord) {
		this.mrecord = mrecord;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		this.createmender = createmender;
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
		this.updatemender = updatemender;
	}

	public String getDelflg() {
		return delflg;
	}

	public void setDelflg(String delflg) {
		this.delflg = delflg;
	}

	public String getMreferenceid() {
		return mreferenceid;
	}

	public void setMreferenceid(String mreferenceid) {
		this.mreferenceid = mreferenceid;
	}

	public int getMxuhao() {
		return mxuhao;
	}

	public void setMxuhao(int mxuhao) {
		this.mxuhao = mxuhao;
	}

	public int getListyear() {
		return listyear;
	}

	public void setListyear(int listyear) {
		this.listyear = listyear;
	}

	public int getListtime() {
		return listtime;
	}

	public void setListtime(int listtime) {
		this.listtime = listtime;
	}

	public String getMcriminaltype() {
		return mcriminaltype;
	}

	public void setMcriminaltype(String mcriminaltype) {
		this.mcriminaltype = mcriminaltype;
	}

	public String getMflag() {
		return mflag;
	}

	public void setMflag(String mflag) {
		this.mflag = mflag;
	}

	public String getResid() {
		return resid;
	}

	public void setResid(String resid) {
		this.resid = resid;
	}

	public String getMkey() {
		return mkey;
	}

	public void setMkey(String mkey) {
		this.mkey = mkey;
	}

	public String getMliexi() {
		return mliexi;
	}

	public void setMliexi(String mliexi) {
		this.mliexi = mliexi;
	}

	public String getMquexi() {
		return mquexi;
	}

	public void setMquexi(String mquexi) {
		this.mquexi = mquexi;
	}

}

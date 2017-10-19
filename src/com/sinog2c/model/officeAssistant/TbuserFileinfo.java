package com.sinog2c.model.officeAssistant;

import java.util.Date;

/**
 * 文件管理
 * 
 * @author shily 2014-7-17 15:35:38
 */
public class TbuserFileinfo {
	// 文件id
	private Integer fileid;

	// 文件父id
	private Integer pfileid;

	// 是否共享
	private Short ishared;

	// 是否是文件
	private Short isleaf;

	// 实际名称
	private String realname;

	// 显示名称
	private String virtualname;

	// 文件编号
	private String fileno;

	// 备注
	private String remark;

	// 文件类型
	private String filetype;

	// 备用1
	private String text1;

	// 备用2
	private String text2;

	// 备用3
	private String text3;

	// 操作时间
	private Date optime;

	// 操作员id
	private String opid;

	// 操作员名字
	private String opname;

	public Integer getFileid() {
		return fileid;
	}

	public void setFileid(Integer fileid) {
		this.fileid = fileid;
	}

	public Integer getPfileid() {
		return pfileid;
	}

	public void setPfileid(Integer pfileid) {
		this.pfileid = pfileid;
	}

	public Short getIshared() {
		return ishared;
	}

	public void setIshared(Short ishared) {
		this.ishared = ishared;
	}

	public Short getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(Short isleaf) {
		this.isleaf = isleaf;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname == null ? null : realname.trim();
	}

	public String getVirtualname() {
		return virtualname;
	}

	public void setVirtualname(String virtualname) {
		this.virtualname = virtualname == null ? null : virtualname.trim();
	}

	public String getFileno() {
		return fileno;
	}

	public void setFileno(String fileno) {
		this.fileno = fileno == null ? null : fileno.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype == null ? null : filetype.trim();
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

	public String getOpname() {
		return opname;
	}

	public void setOpname(String opname) {
		this.opname = opname == null ? null : opname.trim();
	}

}
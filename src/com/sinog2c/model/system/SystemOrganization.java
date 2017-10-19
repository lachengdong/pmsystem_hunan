package com.sinog2c.model.system;

import java.io.Serializable;
import java.util.Date;

public class SystemOrganization implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 司法部
	 */
	public static String UNITLEVEL_SIFABU="1";
	/**
	 * 省局
	 */
	public static String UNITLEVEL_PROVINCE="2";
	/**
	 * 单位级别: 监狱
	 */
	public static String UNITLEVEL_PRISON="3";
	/**
	 * 单位级别: 监区
	 */
	public static String UNITLEVEL_P_AREA="4";
	 /**
	  * 单位级别:  其他
	  */
	public static String UNITLEVEL_OTHER="5";
	/**
	 * 高院
	 */
	public static String UNITLEVEL_H_COURT="6";
	/**
	 * 中院
	 */
	public static String UNITLEVEL_M_COURT="7";
	/**
	 * 检察院, 高级检察院
	 */
	public static String UNITLEVEL_H_PROC="8";
	/**
	 * 检察院, 中级检察院
	 */
	public static String UNITLEVEL_M_PROC="9";
	/**
	 * 公安
	 */
	public static String UNITLEVEL_PublicSecurity="10";

	private String orgid;

    private String porgid;

    private String belongorgid;

    private String name;

    private String shortname;

    private String fullname;

    private Integer sn;

    private String exttel;

    private String fax;

    private String extcontacts;

    private String city;

    private String address;

    private String postcode;

    private String email;

    private String description;

    private String busline;

    private String dutyexplain;

    private String benefit;

    private String unitlevel;

    private String intermediatecourt;

    private String highcourt;

    private String procuratorate;

    private String prisontype;

    private String prisonarealevel;

    private String criminalcoderules;

    private String delflag;

    private Integer opflag;

    private String remark;

    private String text1;

    private String text2;

    private String text3;

    private String text4;

    private Integer int1;

    private Integer int2;

    private Date optime;

    private String opid;

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

    public String getPorgid() {
        return porgid;
    }

    public void setPorgid(String porgid) {
        this.porgid = porgid == null ? null : porgid.trim();
    }

    public String getBelongorgid() {
        return belongorgid;
    }

    public void setBelongorgid(String belongorgid) {
        this.belongorgid = belongorgid == null ? null : belongorgid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname == null ? null : shortname.trim();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname == null ? null : fullname.trim();
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getExttel() {
        return exttel;
    }

    public void setExttel(String exttel) {
        this.exttel = exttel == null ? null : exttel.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getExtcontacts() {
        return extcontacts;
    }

    public void setExtcontacts(String extcontacts) {
        this.extcontacts = extcontacts == null ? null : extcontacts.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getBusline() {
        return busline;
    }

    public void setBusline(String busline) {
        this.busline = busline == null ? null : busline.trim();
    }

    public String getDutyexplain() {
        return dutyexplain;
    }

    public void setDutyexplain(String dutyexplain) {
        this.dutyexplain = dutyexplain == null ? null : dutyexplain.trim();
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit == null ? null : benefit.trim();
    }

    public String getUnitlevel() {
        return unitlevel;
    }

    public void setUnitlevel(String unitlevel) {
        this.unitlevel = unitlevel == null ? null : unitlevel.trim();
    }

    public String getIntermediatecourt() {
        return intermediatecourt;
    }

    public void setIntermediatecourt(String intermediatecourt) {
        this.intermediatecourt = intermediatecourt == null ? null : intermediatecourt.trim();
    }

    public String getHighcourt() {
        return highcourt;
    }

    public void setHighcourt(String highcourt) {
        this.highcourt = highcourt == null ? null : highcourt.trim();
    }

    public String getProcuratorate() {
        return procuratorate;
    }

    public void setProcuratorate(String procuratorate) {
        this.procuratorate = procuratorate == null ? null : procuratorate.trim();
    }

    public String getPrisontype() {
        return prisontype;
    }

    public void setPrisontype(String prisontype) {
        this.prisontype = prisontype == null ? null : prisontype.trim();
    }

    public String getPrisonarealevel() {
        return prisonarealevel;
    }

    public void setPrisonarealevel(String prisonarealevel) {
        this.prisonarealevel = prisonarealevel == null ? null : prisonarealevel.trim();
    }

    public String getCriminalcoderules() {
        return criminalcoderules;
    }

    public void setCriminalcoderules(String criminalcoderules) {
        this.criminalcoderules = criminalcoderules == null ? null : criminalcoderules.trim();
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

    public Integer getOpflag() {
        return opflag;
    }

    public void setOpflag(Integer opflag) {
        this.opflag = opflag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
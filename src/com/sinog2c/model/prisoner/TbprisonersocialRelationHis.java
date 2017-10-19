package com.sinog2c.model.prisoner;

import java.math.BigDecimal;
import java.util.Date;

public class TbprisonersocialRelationHis {
    private BigDecimal srid;

    private String crimid;

    private String relationship;

    private String name;

    private String birthday;

    private String gender;

    private String orgaddress;

    private String orgname;

    private String orgpostalcode;

    private String orgtel;

    private String papertype;

    private String paperid;

    private String rduty;

    private String vocation;

    private String political;

    private String homeaddress;

    private String postalcode;

    private String phone;

    private String iscrimhistory;

    private String isprimarycontact;

    private String delflag;

    private String remark;

    private String opid;

    private Date optime;

    public BigDecimal getSrid() {
        return srid;
    }

    public void setSrid(BigDecimal srid) {
        this.srid = srid;
    }

    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship == null ? null : relationship.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getOrgaddress() {
        return orgaddress;
    }

    public void setOrgaddress(String orgaddress) {
        this.orgaddress = orgaddress == null ? null : orgaddress.trim();
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname == null ? null : orgname.trim();
    }

    public String getOrgpostalcode() {
        return orgpostalcode;
    }

    public void setOrgpostalcode(String orgpostalcode) {
        this.orgpostalcode = orgpostalcode == null ? null : orgpostalcode.trim();
    }

    public String getOrgtel() {
        return orgtel;
    }

    public void setOrgtel(String orgtel) {
        this.orgtel = orgtel == null ? null : orgtel.trim();
    }

    public String getPapertype() {
        return papertype;
    }

    public void setPapertype(String papertype) {
        this.papertype = papertype == null ? null : papertype.trim();
    }

    public String getPaperid() {
        return paperid;
    }

    public void setPaperid(String paperid) {
        this.paperid = paperid == null ? null : paperid.trim();
    }

    public String getRduty() {
        return rduty;
    }

    public void setRduty(String rduty) {
        this.rduty = rduty == null ? null : rduty.trim();
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation == null ? null : vocation.trim();
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political == null ? null : political.trim();
    }

    public String getHomeaddress() {
        return homeaddress;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress == null ? null : homeaddress.trim();
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode == null ? null : postalcode.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getIscrimhistory() {
        return iscrimhistory;
    }

    public void setIscrimhistory(String iscrimhistory) {
        this.iscrimhistory = iscrimhistory == null ? null : iscrimhistory.trim();
    }

    public String getIsprimarycontact() {
        return isprimarycontact;
    }

    public void setIsprimarycontact(String isprimarycontact) {
        this.isprimarycontact = isprimarycontact == null ? null : isprimarycontact.trim();
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
package com.sinog2c.model.prisoner;

import java.util.Date;

public class TbprisonerBaseinfo {
    private String crimid;

    private String departid;

    private String name;

    private String usedname;

    private String namepinyinshort;

    private String idnumber;

    private String gender;

    private Date birthday;

    private String nation;

    private String politicalstatus;

    private String education;

    private String maritalstatus;

    private String vocation;

    private String vocationclass;

    private String rank;

    private String titles;

    private String majors;

    private String technicalgrade;

    private String specialist;

    private String religion;

    private String parties;

    private String identity;

    private String grade;

    private String duty;

    private String countryarea;

    private String origin;

    private String originplacearea;

    private String originplaceaddress;

    private String birtharea;

    private String birthaddress;

    private String registerarea;

    private String registeraddress;

    private String registeraddressdetail;

    private String residencetype;

    private String familyaddress;

    private String addresscode;

    private String addressarea;

    private String addressdetail;

    private String postcode;

    private String pprovince;

    private String workaddressdetail;

    private String workpostcode;

    private String orgcontact;

    private String contacttel;

    private String orgmark;

    private String rewardpunish;

    private String isescuage;

    private String language;

    private String writing;

    private String accent;

    private String remark;

    private String opid;

    private Date optime;
    
    private String sanclassstatus;
    
    private String familyjcaddress;
    
    private String origin_code;
    
    private String applyid;
    
    public String getApplyid(){
    	return applyid;
    }
    
    public void setApplyid(String applyid){
    	this.applyid = applyid;
    }
    
    //2015/8/16 添加 by luan
    private String courtchangeto;
    public String getCourtchangeto() {
		return courtchangeto;
	}

	public void setCourtchangeto(String courtchangeto) {
		this.courtchangeto = courtchangeto;
	}

	public String getSentencestime() {
		return sentencestime;
	}

	public void setSentencestime(String sentencestime) {
		this.sentencestime = sentencestime;
	}

	public String getSentenceetime() {
		return sentenceetime;
	}

	public void setSentenceetime(String sentenceetime) {
		this.sentenceetime = sentenceetime;
	}

	public String getCauseaction() {
		return causeaction;
	}

	public void setCauseaction(String causeaction) {
		this.causeaction = causeaction;
	}

	public String getOriginalyear() {
		return originalyear;
	}

	public void setOriginalyear(String originalyear) {
		this.originalyear = originalyear;
	}

	public String getCrimclass() {
		return crimclass;
	}

	public void setCrimclass(String crimclass) {
		this.crimclass = crimclass;
	}

	private String sentencestime;
    private String sentenceetime;
    private String causeaction;
    private String originalyear;
    private String crimclass;
    //2015/8/16 添加 by luan
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUsedname() {
        return usedname;
    }

    public void setUsedname(String usedname) {
        this.usedname = usedname == null ? null : usedname.trim();
    }

    public String getNamepinyinshort() {
        return namepinyinshort;
    }

    public void setNamepinyinshort(String namepinyinshort) {
        this.namepinyinshort = namepinyinshort == null ? null : namepinyinshort.trim();
    }

    public String getIdnumber() {
        return idnumber;
    }
    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber == null ? null : idnumber.trim();
    }
    
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getPoliticalstatus() {
        return politicalstatus;
    }

    public void setPoliticalstatus(String politicalstatus) {
        this.politicalstatus = politicalstatus == null ? null : politicalstatus.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus == null ? null : maritalstatus.trim();
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation == null ? null : vocation.trim();
    }

    public String getVocationclass() {
        return vocationclass;
    }

    public void setVocationclass(String vocationclass) {
        this.vocationclass = vocationclass == null ? null : vocationclass.trim();
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank == null ? null : rank.trim();
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles == null ? null : titles.trim();
    }

    public String getMajors() {
        return majors;
    }

    public void setMajors(String majors) {
        this.majors = majors == null ? null : majors.trim();
    }

    public String getTechnicalgrade() {
        return technicalgrade;
    }

    public void setTechnicalgrade(String technicalgrade) {
        this.technicalgrade = technicalgrade == null ? null : technicalgrade.trim();
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist == null ? null : specialist.trim();
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion == null ? null : religion.trim();
    }

    public String getParties() {
        return parties;
    }

    public void setParties(String parties) {
        this.parties = parties == null ? null : parties.trim();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty == null ? null : duty.trim();
    }

    public String getCountryarea() {
        return countryarea;
    }

    public void setCountryarea(String countryarea) {
        this.countryarea = countryarea == null ? null : countryarea.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getOriginplacearea() {
        return originplacearea;
    }

    public void setOriginplacearea(String originplacearea) {
        this.originplacearea = originplacearea == null ? null : originplacearea.trim();
    }

    public String getOriginplaceaddress() {
        return originplaceaddress;
    }

    public void setOriginplaceaddress(String originplaceaddress) {
        this.originplaceaddress = originplaceaddress == null ? null : originplaceaddress.trim();
    }

    public String getBirtharea() {
        return birtharea;
    }

    public void setBirtharea(String birtharea) {
        this.birtharea = birtharea == null ? null : birtharea.trim();
    }

    public String getBirthaddress() {
        return birthaddress;
    }

    public void setBirthaddress(String birthaddress) {
        this.birthaddress = birthaddress == null ? null : birthaddress.trim();
    }

    public String getRegisterarea() {
        return registerarea;
    }

    public void setRegisterarea(String registerarea) {
        this.registerarea = registerarea == null ? null : registerarea.trim();
    }

    public String getRegisteraddress() {
        return registeraddress;
    }

    public void setRegisteraddress(String registeraddress) {
        this.registeraddress = registeraddress == null ? null : registeraddress.trim();
    }

    public String getRegisteraddressdetail() {
        return registeraddressdetail;
    }

    public void setRegisteraddressdetail(String registeraddressdetail) {
        this.registeraddressdetail = registeraddressdetail == null ? null : registeraddressdetail.trim();
    }

    public String getResidencetype() {
        return residencetype;
    }

    public void setResidencetype(String residencetype) {
        this.residencetype = residencetype == null ? null : residencetype.trim();
    }

    public String getFamilyaddress() {
        return familyaddress;
    }

    public void setFamilyaddress(String familyaddress) {
        this.familyaddress = familyaddress == null ? null : familyaddress.trim();
    }

    public String getAddresscode() {
        return addresscode;
    }

    public void setAddresscode(String addresscode) {
        this.addresscode = addresscode == null ? null : addresscode.trim();
    }

    public String getAddressarea() {
        return addressarea;
    }

    public void setAddressarea(String addressarea) {
        this.addressarea = addressarea == null ? null : addressarea.trim();
    }

    public String getAddressdetail() {
        return addressdetail;
    }

    public void setAddressdetail(String addressdetail) {
        this.addressdetail = addressdetail == null ? null : addressdetail.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getPprovince() {
        return pprovince;
    }

    public void setPprovince(String pprovince) {
        this.pprovince = pprovince == null ? null : pprovince.trim();
    }

    public String getWorkaddressdetail() {
        return workaddressdetail;
    }

    public void setWorkaddressdetail(String workaddressdetail) {
        this.workaddressdetail = workaddressdetail == null ? null : workaddressdetail.trim();
    }

    public String getWorkpostcode() {
        return workpostcode;
    }

    public void setWorkpostcode(String workpostcode) {
        this.workpostcode = workpostcode == null ? null : workpostcode.trim();
    }

    public String getOrgcontact() {
        return orgcontact;
    }

    public void setOrgcontact(String orgcontact) {
        this.orgcontact = orgcontact == null ? null : orgcontact.trim();
    }

    public String getContacttel() {
        return contacttel;
    }

    public void setContacttel(String contacttel) {
        this.contacttel = contacttel == null ? null : contacttel.trim();
    }

    public String getOrgmark() {
        return orgmark;
    }

    public void setOrgmark(String orgmark) {
        this.orgmark = orgmark == null ? null : orgmark.trim();
    }

    public String getRewardpunish() {
        return rewardpunish;
    }

    public void setRewardpunish(String rewardpunish) {
        this.rewardpunish = rewardpunish == null ? null : rewardpunish.trim();
    }

    public String getIsescuage() {
        return isescuage;
    }

    public void setIsescuage(String isescuage) {
        this.isescuage = isescuage == null ? null : isescuage.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getWriting() {
        return writing;
    }

    public void setWriting(String writing) {
        this.writing = writing == null ? null : writing.trim();
    }

    public String getAccent() {
        return accent;
    }

    public void setAccent(String accent) {
        this.accent = accent == null ? null : accent.trim();
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
    
	public String getSanclassstatus() {
		return sanclassstatus;
	}

	public void setSanclassstatus(String sanclassstatus) {
		this.sanclassstatus = sanclassstatus;
	}

	public TbprisonerBaseinfo(String crimid, String departid, String name,
			String usedname, String namepinyinshort, String idnumber,
			String gender, Date birthday, String nation,
			String politicalstatus, String education, String maritalstatus,
			String vocation, String vocationclass, String rank, String titles,
			String majors, String technicalgrade, String specialist,
			String religion, String parties, String identity, String grade,
			String duty, String countryarea, String origin,
			String originplacearea, String originplaceaddress,
			String birtharea, String birthaddress, String registerarea,
			String registeraddress, String registeraddressdetail,
			String residencetype, String familyaddress, String addresscode,
			String addressarea, String addressdetail, String postcode,
			String pprovince, String workaddressdetail, String workpostcode,
			String orgcontact, String contacttel, String orgmark,
			String rewardpunish, String isescuage, String language,
			String writing, String accent, String remark, String opid,
			Date optime,String sanclassstatus) {
		super();
		this.crimid = crimid;
		this.departid = departid;
		this.name = name;
		this.usedname = usedname;
		this.namepinyinshort = namepinyinshort;
		this.idnumber = idnumber;
		this.gender = gender;
		this.birthday = birthday;
		this.nation = nation;
		this.politicalstatus = politicalstatus;
		this.education = education;
		this.maritalstatus = maritalstatus;
		this.vocation = vocation;
		this.vocationclass = vocationclass;
		this.rank = rank;
		this.titles = titles;
		this.majors = majors;
		this.technicalgrade = technicalgrade;
		this.specialist = specialist;
		this.religion = religion;
		this.parties = parties;
		this.identity = identity;
		this.grade = grade;
		this.duty = duty;
		this.countryarea = countryarea;
		this.origin = origin;
		this.originplacearea = originplacearea;
		this.originplaceaddress = originplaceaddress;
		this.birtharea = birtharea;
		this.birthaddress = birthaddress;
		this.registerarea = registerarea;
		this.registeraddress = registeraddress;
		this.registeraddressdetail = registeraddressdetail;
		this.residencetype = residencetype;
		this.familyaddress = familyaddress;
		this.addresscode = addresscode;
		this.addressarea = addressarea;
		this.addressdetail = addressdetail;
		this.postcode = postcode;
		this.pprovince = pprovince;
		this.workaddressdetail = workaddressdetail;
		this.workpostcode = workpostcode;
		this.orgcontact = orgcontact;
		this.contacttel = contacttel;
		this.orgmark = orgmark;
		this.rewardpunish = rewardpunish;
		this.isescuage = isescuage;
		this.language = language;
		this.writing = writing;
		this.accent = accent;
		this.remark = remark;
		this.opid = opid;
		this.optime = optime;
		this.sanclassstatus=sanclassstatus;
	}

	public TbprisonerBaseinfo() {
		super();
	}

	public String getFamilyjcaddress() {
		return familyjcaddress;
	}

	public void setFamilyjcaddress(String familyjcaddress) {
		this.familyjcaddress = familyjcaddress;
	}

	public String getOrigin_code() {
		return origin_code;
	}

	public void setOrigin_code(String origin_code) {
		this.origin_code = origin_code;
	}
    
}
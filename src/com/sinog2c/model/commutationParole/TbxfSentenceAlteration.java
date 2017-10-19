package com.sinog2c.model.commutationParole;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TbxfSentenceAlteration {
	private String crimid;

    private String jailid;

    private String jailname;

    private String areaname;

    private String areaid;

    private String branchareaname;

    private Integer caseno;

    private String name;

    private String gender;

    private Date brithday;

    private Integer typeid;

    private Short isminor;

    private Short isold;

    private Short isill;

    private Short isdisability;

    private String changetype;

    private String courtname;

    private String courttitle;

    private Short courtyear;

    private String courtshort;

    private String courtsn;

    private Date courtsanction;

    private String courtchangeyear;

    private String courtchangemonth;

    private String courtchangeday;

    private Date courtchangefrom;

    private Date courtchangeto;

    private Short courtstatus;

    private String crimclass;

    private Short isdeportation;

    private String punishmentyear;

    private String punishmentmonth;

    private String punishmentday;

    private Short islimit;

    private Short isparole;

    private Date stayexecution;

    private Date noperiod;

    private String exnoperiod;

    private Date sentencestart;

    private Date delivery;

    private String nowpunishmentyear;

    private String nowpunishmentmonth;

    private String nowpunishmentday;

    private Date inpersion;

    private Date fristsubmit;

    private Date commutationdate;

    private Date predate;

    private Date thisdate;

    private String intervalyear;

    private String intervalmonth;

    private String intervalday;

    private Double fine;

    private Double thisfine;

    private Double sumfine;

    private Double recovered;

    private Double sumrecovered;

    private Double thisrecovered;

    private Double expropriation;

    private Double sumexpropriation;

    private Double thisexpropriation;

    private String expropriationinfo;

    private String civiljudgment;

    private Double civil;

    private Double sumcivil;

    private Double thiscivil;

    private Double income;

    private Double pay;

    private Double overplus;

    private Date rewardstart;

    private Date rewardend;

    private Date jailreport;

    private Date execdate;

    private String jailinfo;

    private String administration;

    private String prosecutor;

    private String rewardinfo;

    private String punishinfo;

    private String sentencechageinfo;

    private String leniency;

    private String branchto;

    private String areato;

    private String officesto;

    private String review;

    private String jailto;

    private Date crimetime;

    private Date detaindate;

    private String originalyear;

    private String originalmonth;

    private String originalday;

    private Long batchid;

    private Short status;

    private Short isfilter;

    private String losepoweryear;

    private String losepowermonth;

    private String losepowereday;

    private String remark;

    private String opid;

    private Date optime;

    private String executesentencemonths;
    
    private List<Map<String,Object>> wtList;
    
    private Integer firstcommutation;
    
    private Integer yusinmonths;
    
    private Integer inprisonmonths;
    
    public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getJailid() {
        return jailid;
    }

    public void setJailid(String jailid) {
        this.jailid = jailid == null ? null : jailid.trim();
    }

    public String getJailname() {
        return jailname;
    }

    public void setJailname(String jailname) {
        this.jailname = jailname == null ? null : jailname.trim();
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname == null ? null : areaname.trim();
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid == null ? null : areaid.trim();
    }

    public String getBranchareaname() {
        return branchareaname;
    }

    public void setBranchareaname(String branchareaname) {
        this.branchareaname = branchareaname == null ? null : branchareaname.trim();
    }

    public Integer getCaseno() {
        return caseno;
    }

    public void setCaseno(Integer caseno) {
        this.caseno = caseno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Date getBrithday() {
        return brithday;
    }

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Short getIsminor() {
        return isminor;
    }

    public void setIsminor(Short isminor) {
        this.isminor = isminor;
    }

    public Short getIsold() {
        return isold;
    }

    public void setIsold(Short isold) {
        this.isold = isold;
    }

    public Short getIsill() {
        return isill;
    }

    public void setIsill(Short isill) {
        this.isill = isill;
    }

    public Short getIsdisability() {
        return isdisability;
    }

    public void setIsdisability(Short isdisability) {
        this.isdisability = isdisability;
    }

    public String getChangetype() {
        return changetype;
    }

    public void setChangetype(String changetype) {
        this.changetype = changetype == null ? null : changetype.trim();
    }

    public String getCourtname() {
        return courtname;
    }

    public void setCourtname(String courtname) {
        this.courtname = courtname == null ? null : courtname.trim();
    }

    public String getCourttitle() {
        return courttitle;
    }

    public void setCourttitle(String courttitle) {
        this.courttitle = courttitle == null ? null : courttitle.trim();
    }

    public Short getCourtyear() {
        return courtyear;
    }

    public void setCourtyear(Short courtyear) {
        this.courtyear = courtyear;
    }

    public String getCourtshort() {
        return courtshort;
    }

    public void setCourtshort(String courtshort) {
        this.courtshort = courtshort == null ? null : courtshort.trim();
    }

    public String getCourtsn() {
        return courtsn;
    }

    public void setCourtsn(String courtsn) {
        this.courtsn = courtsn == null ? null : courtsn.trim();
    }

    public Date getCourtsanction() {
        return courtsanction;
    }

    public void setCourtsanction(Date courtsanction) {
        this.courtsanction = courtsanction;
    }

    public String getCourtchangeyear() {
        return courtchangeyear;
    }

    public void setCourtchangeyear(String courtchangeyear) {
        this.courtchangeyear = courtchangeyear == null ? null : courtchangeyear.trim();
    }

    public String getCourtchangemonth() {
        return courtchangemonth;
    }

    public void setCourtchangemonth(String courtchangemonth) {
        this.courtchangemonth = courtchangemonth == null ? null : courtchangemonth.trim();
    }

    public String getCourtchangeday() {
        return courtchangeday;
    }

    public void setCourtchangeday(String courtchangeday) {
        this.courtchangeday = courtchangeday == null ? null : courtchangeday.trim();
    }

    public Date getCourtchangefrom() {
        return courtchangefrom;
    }

    public void setCourtchangefrom(Date courtchangefrom) {
        this.courtchangefrom = courtchangefrom;
    }

    public Date getCourtchangeto() {
        return courtchangeto;
    }

    public void setCourtchangeto(Date courtchangeto) {
        this.courtchangeto = courtchangeto;
    }

    public Short getCourtstatus() {
        return courtstatus;
    }

    public void setCourtstatus(Short courtstatus) {
        this.courtstatus = courtstatus;
    }

    public String getCrimclass() {
        return crimclass;
    }

    public void setCrimclass(String crimclass) {
        this.crimclass = crimclass == null ? null : crimclass.trim();
    }

    public Short getIsdeportation() {
        return isdeportation;
    }

    public void setIsdeportation(Short isdeportation) {
        this.isdeportation = isdeportation;
    }

    public String getPunishmentyear() {
        return punishmentyear;
    }

    public void setPunishmentyear(String punishmentyear) {
        this.punishmentyear = punishmentyear == null ? null : punishmentyear.trim();
    }

    public String getPunishmentmonth() {
        return punishmentmonth;
    }

    public void setPunishmentmonth(String punishmentmonth) {
        this.punishmentmonth = punishmentmonth == null ? null : punishmentmonth.trim();
    }

    public String getPunishmentday() {
        return punishmentday;
    }

    public void setPunishmentday(String punishmentday) {
        this.punishmentday = punishmentday == null ? null : punishmentday.trim();
    }

    public Short getIslimit() {
        return islimit;
    }

    public void setIslimit(Short islimit) {
        this.islimit = islimit;
    }

    public Short getIsparole() {
        return isparole;
    }

    public void setIsparole(Short isparole) {
        this.isparole = isparole;
    }

    public Date getStayexecution() {
        return stayexecution;
    }

    public void setStayexecution(Date stayexecution) {
        this.stayexecution = stayexecution;
    }

    public Date getNoperiod() {
        return noperiod;
    }

    public void setNoperiod(Date noperiod) {
        this.noperiod = noperiod;
    }


    public String getExnoperiod() {
		return exnoperiod;
	}

	public void setExnoperiod(String exnoperiod) {
		this.exnoperiod = exnoperiod;
	}

	public Date getSentencestart() {
        return sentencestart;
    }

    public void setSentencestart(Date sentencestart) {
        this.sentencestart = sentencestart;
    }

    public Date getDelivery() {
        return delivery;
    }

    public void setDelivery(Date delivery) {
        this.delivery = delivery;
    }

    public String getNowpunishmentyear() {
        return nowpunishmentyear;
    }

    public void setNowpunishmentyear(String nowpunishmentyear) {
        this.nowpunishmentyear = nowpunishmentyear == null ? null : nowpunishmentyear.trim();
    }

    public String getNowpunishmentmonth() {
        return nowpunishmentmonth;
    }

    public void setNowpunishmentmonth(String nowpunishmentmonth) {
        this.nowpunishmentmonth = nowpunishmentmonth == null ? null : nowpunishmentmonth.trim();
    }

    public String getNowpunishmentday() {
        return nowpunishmentday;
    }

    public void setNowpunishmentday(String nowpunishmentday) {
        this.nowpunishmentday = nowpunishmentday == null ? null : nowpunishmentday.trim();
    }

    public Date getInpersion() {
        return inpersion;
    }

    public void setInpersion(Date inpersion) {
        this.inpersion = inpersion;
    }

    public Date getFristsubmit() {
        return fristsubmit;
    }

    public void setFristsubmit(Date fristsubmit) {
        this.fristsubmit = fristsubmit;
    }

    public Date getCommutationdate() {
        return commutationdate;
    }

    public void setCommutationdate(Date commutationdate) {
        this.commutationdate = commutationdate;
    }

    public Date getPredate() {
        return predate;
    }

    public void setPredate(Date predate) {
        this.predate = predate;
    }

    public Date getThisdate() {
        return thisdate;
    }

    public void setThisdate(Date thisdate) {
        this.thisdate = thisdate;
    }

    public String getIntervalyear() {
        return intervalyear;
    }

    public void setIntervalyear(String intervalyear) {
        this.intervalyear = intervalyear == null ? null : intervalyear.trim();
    }

    public String getIntervalmonth() {
        return intervalmonth;
    }

    public void setIntervalmonth(String intervalmonth) {
        this.intervalmonth = intervalmonth == null ? null : intervalmonth.trim();
    }

    public String getIntervalday() {
        return intervalday;
    }

    public void setIntervalday(String intervalday) {
        this.intervalday = intervalday == null ? null : intervalday.trim();
    }

    public Double getFine() {
		return fine;
	}

	public void setFine(Double fine) {
		this.fine = fine;
	}

	public Double getThisfine() {
		return thisfine;
	}

	public void setThisfine(Double thisfine) {
		this.thisfine = thisfine;
	}

	public Double getSumfine() {
		return sumfine;
	}

	public void setSumfine(Double sumfine) {
		this.sumfine = sumfine;
	}

	public Double getRecovered() {
		return recovered;
	}

	public void setRecovered(Double recovered) {
		this.recovered = recovered;
	}

	public Double getSumrecovered() {
		return sumrecovered;
	}

	public void setSumrecovered(Double sumrecovered) {
		this.sumrecovered = sumrecovered;
	}

	public Double getThisrecovered() {
		return thisrecovered;
	}

	public void setThisrecovered(Double thisrecovered) {
		this.thisrecovered = thisrecovered;
	}

	public Double getExpropriation() {
		return expropriation;
	}

	public void setExpropriation(Double expropriation) {
		this.expropriation = expropriation;
	}

	public Double getSumexpropriation() {
		return sumexpropriation;
	}

	public void setSumexpropriation(Double sumexpropriation) {
		this.sumexpropriation = sumexpropriation;
	}

	public Double getThisexpropriation() {
		return thisexpropriation;
	}

	public void setThisexpropriation(Double thisexpropriation) {
		this.thisexpropriation = thisexpropriation;
	}

	public void setCivil(Double civil) {
		this.civil = civil;
	}

	public void setSumcivil(Double sumcivil) {
		this.sumcivil = sumcivil;
	}

	public void setThiscivil(Double thiscivil) {
		this.thiscivil = thiscivil;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}

	public void setOverplus(Double overplus) {
		this.overplus = overplus;
	}

	public String getExpropriationinfo() {
        return expropriationinfo;
    }

    public void setExpropriationinfo(String expropriationinfo) {
        this.expropriationinfo = expropriationinfo == null ? null : expropriationinfo.trim();
    }

    public String getCiviljudgment() {
        return civiljudgment;
    }

    public void setCiviljudgment(String civiljudgment) {
        this.civiljudgment = civiljudgment == null ? null : civiljudgment.trim();
    }

   

    public Double getCivil() {
		return civil;
	}

	public Double getSumcivil() {
		return sumcivil;
	}

	public Double getThiscivil() {
		return thiscivil;
	}

	public Double getIncome() {
		return income;
	}

	public Double getPay() {
		return pay;
	}

	public Double getOverplus() {
		return overplus;
	}

	public Date getRewardstart() {
        return rewardstart;
    }

    public void setRewardstart(Date rewardstart) {
        this.rewardstart = rewardstart;
    }

    public Date getRewardend() {
        return rewardend;
    }

    public void setRewardend(Date rewardend) {
        this.rewardend = rewardend;
    }

    public Date getJailreport() {
        return jailreport;
    }

    public void setJailreport(Date jailreport) {
        this.jailreport = jailreport;
    }

    public Date getExecdate() {
        return execdate;
    }

    public void setExecdate(Date execdate) {
        this.execdate = execdate;
    }

    public String getJailinfo() {
        return jailinfo;
    }

    public void setJailinfo(String jailinfo) {
        this.jailinfo = jailinfo == null ? null : jailinfo.trim();
    }

    public String getAdministration() {
        return administration;
    }

    public void setAdministration(String administration) {
        this.administration = administration == null ? null : administration.trim();
    }

    public String getProsecutor() {
        return prosecutor;
    }

    public void setProsecutor(String prosecutor) {
        this.prosecutor = prosecutor == null ? null : prosecutor.trim();
    }

    public String getRewardinfo() {
        return rewardinfo;
    }

    public void setRewardinfo(String rewardinfo) {
        this.rewardinfo = rewardinfo == null ? null : rewardinfo.trim();
    }

    public String getPunishinfo() {
        return punishinfo;
    }

    public void setPunishinfo(String punishinfo) {
        this.punishinfo = punishinfo == null ? null : punishinfo.trim();
    }

    public String getSentencechageinfo() {
        return sentencechageinfo;
    }

    public void setSentencechageinfo(String sentencechageinfo) {
        this.sentencechageinfo = sentencechageinfo == null ? null : sentencechageinfo.trim();
    }

    public String getLeniency() {
        return leniency;
    }

    public void setLeniency(String leniency) {
        this.leniency = leniency == null ? null : leniency.trim();
    }

    public String getBranchto() {
        return branchto;
    }

    public void setBranchto(String branchto) {
        this.branchto = branchto == null ? null : branchto.trim();
    }

    public String getAreato() {
        return areato;
    }

    public void setAreato(String areato) {
        this.areato = areato == null ? null : areato.trim();
    }

    public String getOfficesto() {
        return officesto;
    }

    public void setOfficesto(String officesto) {
        this.officesto = officesto == null ? null : officesto.trim();
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review == null ? null : review.trim();
    }

    public String getJailto() {
        return jailto;
    }

    public void setJailto(String jailto) {
        this.jailto = jailto == null ? null : jailto.trim();
    }

    public Date getCrimetime() {
        return crimetime;
    }

    public void setCrimetime(Date crimetime) {
        this.crimetime = crimetime;
    }

    public Date getDetaindate() {
        return detaindate;
    }

    public void setDetaindate(Date detaindate) {
        this.detaindate = detaindate;
    }

    public String getOriginalyear() {
        return originalyear;
    }

    public void setOriginalyear(String originalyear) {
        this.originalyear = originalyear == null ? null : originalyear.trim();
    }

    public String getOriginalmonth() {
        return originalmonth;
    }

    public void setOriginalmonth(String originalmonth) {
        this.originalmonth = originalmonth == null ? null : originalmonth.trim();
    }

    public String getOriginalday() {
        return originalday;
    }

    public void setOriginalday(String originalday) {
        this.originalday = originalday == null ? null : originalday.trim();
    }

    public Long getBatchid() {
        return batchid;
    }

    public void setBatchid(long batchid) {
        this.batchid = batchid;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getIsfilter() {
        return isfilter;
    }

    public void setIsfilter(Short isfilter) {
        this.isfilter = isfilter;
    }

    public String getLosepoweryear() {
        return losepoweryear;
    }

    public void setLosepoweryear(String losepoweryear) {
        this.losepoweryear = losepoweryear == null ? null : losepoweryear.trim();
    }

    public String getLosepowermonth() {
        return losepowermonth;
    }

    public void setLosepowermonth(String losepowermonth) {
        this.losepowermonth = losepowermonth == null ? null : losepowermonth.trim();
    }

    public String getLosepowereday() {
        return losepowereday;
    }

    public void setLosepowereday(String losepowereday) {
        this.losepowereday = losepowereday == null ? null : losepowereday.trim();
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

	public String getExecutesentencemonths() {
		return executesentencemonths;
	}

	public void setExecutesentencemonths(String executesentencemonths) {
		this.executesentencemonths = executesentencemonths;
	}

	public List<Map<String, Object>> getWtList() {
		return wtList;
	}

	public void setWtList(List<Map<String, Object>> wtList) {
		this.wtList = wtList;
	}

	public Integer getFirstcommutation() {
		return firstcommutation;
	}

	public void setFirstcommutation(Integer firstcommutation) {
		this.firstcommutation = firstcommutation;
	}

	public Integer getYusinmonths() {
		return yusinmonths;
	}

	public void setYusinmonths(Integer yusinmonths) {
		this.yusinmonths = yusinmonths;
	}

	public Integer getInprisonmonths() {
		return inprisonmonths;
	}

	public void setInprisonmonths(Integer inprisonmonths) {
		this.inprisonmonths = inprisonmonths;
	}
}
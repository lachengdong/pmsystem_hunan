package com.sinog2c.model.prisoner;

import java.util.Date;
import java.util.Map;

public class TbprisonerBaseCrime {
    private String crimid;

    private String orgid;

    private String orgname1;

    private String orgname2;

    private String lawcaseno;

    private String crimeface;

    private Date crimedate;

    private String isminor;

    private Date detaindate;

    private String arrestauthority;

    private String arrestauthorityshort;

    private Date arrestdate;

    private Date bailstart;

    private Date bailend;

    private String sueorgan;

    private String sueorganshort;

    private String sueyear;

    private String suecaseno;

    private String sueno;

    private Integer pedigreenum;

    private String jointcrimetype;

    private Integer jointcrimes;

    private Date inprisondate;

    private String firsttrialsum;

    private String appealtype;

    private String appeal;

    private String sentencetype;

    private String judgmentname;

    private String judgmentshort;

    private String caseyear;

    private String caseorgshort;

    private String caseno;

    private String punishmenttype;

    private Integer punishmentyear;

    private Integer punishmentmonth;

    private Integer punishmentday;

    private Date sentencestime;

    private Date sentenceetime;

    private Integer losepoweryear;

    private Integer losepowermonth;

    private Integer losepowereday;

    private String forfeit;

    private String isdeport;

    private String forfeitureproperty;

    private String other;

    private String compensation;

    private Short isremission;

    private Short isparole;

    private String limitedremission;

    private String payment;

    private String criminaltype;

    private Date issueddate;

    private String detainstatus;

    private String fleetype;

    private String isrecidivism;

    private String recidivist;

    private String cation;

    private String marriage;

    private String isruling;

    private String istakeeffect;

    private String courttype;

    private String casetype;

    private String stolenmoney;

    private Date takeeffectdate;

    private String sanctionno;

    private String causeaction;

    private String charge;

    private Date judgedate;

    private String whereto;

    private String watchhouse;

    private String detaintype;

    private String detainprison;

    private String specialcrimetype;

    private String fileno;

    private String chargeclass;

    private String custodytype;

    private String commutation;

    private String drugs;

    private String gun;

    private String underworld;

    private String wickedness;

    private String takedrugs;

    private String escapes;

    private String suicide;

    private String assaultepolice;

    private String othercase;

    private String jobs;

    private String issuingauthority;

    private String returnloot;

    private String fulfilcompensation;

    private String expropriation;

    private Date executiondate;

    private String mediaattention;

    private String focus;

    private String specialcontrol;

    private String opinion;

    private String sanclassstatus;

    private String oldprisoner;

    private String illprisoner;

    private String deformityprisoner;

    private String redrugs;

    private String crimes;

    private String overfourcrimes;

    private String seriousviolations;

    private String combinedpunishment;

    private String majorcriminal;

    private String strictlyplot;

    private String postcrime;

    private String undermine;

    private String wideplots;

    private String sanremark;

    private String principalaccessory;

    private String declarationproperty;

    private String crimtype;

    private String maincase;

    private String entryways;

    private String ischinese;

    private String isforeign;

    private Short isdeath;

    private String orgid1;

    private String orgid2;

    private String remark;
    
    private String aliasno;

    private String opid;

    private Date optime;
    
    private String falungong;
    
    private String qitaxiejiao;
    
    private String casenature;
    
    private String qitareport;
    
    private String importantcriminal;
    
    private String prison;
    
    private String berth;
    
    private String punishmenteight;
    
    private String arrestauthority_code;
 
    private String caseothertype;
    
    public String getFalungong() {
		return falungong;
	}

	public String getPrison() {
		return prison;
	}

	public void setPrison(String prison) {
		this.prison = prison;
	}

	public String getBerth() {
		return berth;
	}

	public void setBerth(String berth) {
		this.berth = berth;
	}

	public void setFalungong(String falungong) {
		this.falungong = falungong == null ? null : falungong.trim();
	}

	public String getQitaxiejiao() {
		return qitaxiejiao;
	}

	public void setQitaxiejiao(String qitaxiejiao) {
		this.qitaxiejiao = qitaxiejiao == null ? null : qitaxiejiao.trim();
	}

	public String getCrimid() {
        return crimid;
    }

    public void setCrimid(String crimid) {
        this.crimid = crimid == null ? null : crimid.trim();
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

    public String getOrgname1() {
        return orgname1;
    }

    public void setOrgname1(String orgname1) {
        this.orgname1 = orgname1 == null ? null : orgname1.trim();
    }

    public String getOrgname2() {
        return orgname2;
    }

    public void setOrgname2(String orgname2) {
        this.orgname2 = orgname2 == null ? null : orgname2.trim();
    }

    public String getLawcaseno() {
        return lawcaseno;
    }

    public void setLawcaseno(String lawcaseno) {
        this.lawcaseno = lawcaseno == null ? null : lawcaseno.trim();
    }

    public String getCrimeface() {
        return crimeface;
    }

    public void setCrimeface(String crimeface) {
        this.crimeface = crimeface == null ? null : crimeface.trim();
    }

    public Date getCrimedate() {
        return crimedate;
    }

    public void setCrimedate(Date crimedate) {
        this.crimedate = crimedate;
    }

    public String getIsminor() {
        return isminor;
    }

    public void setIsminor(String isminor) {
        this.isminor = isminor == null ? null : isminor.trim();
    }

    public Date getDetaindate() {
        return detaindate;
    }

    public void setDetaindate(Date detaindate) {
        this.detaindate = detaindate;
    }

    public String getArrestauthority() {
        return arrestauthority;
    }

    public void setArrestauthority(String arrestauthority) {
        this.arrestauthority = arrestauthority == null ? null : arrestauthority.trim();
    }

    public String getArrestauthorityshort() {
        return arrestauthorityshort;
    }

    public void setArrestauthorityshort(String arrestauthorityshort) {
        this.arrestauthorityshort = arrestauthorityshort == null ? null : arrestauthorityshort.trim();
    }

    public Date getArrestdate() {
        return arrestdate;
    }

    public void setArrestdate(Date arrestdate) {
        this.arrestdate = arrestdate;
    }

    public Date getBailstart() {
        return bailstart;
    }

    public void setBailstart(Date bailstart) {
        this.bailstart = bailstart;
    }

    public Date getBailend() {
        return bailend;
    }

    public void setBailend(Date bailend) {
        this.bailend = bailend;
    }

    public String getSueorgan() {
        return sueorgan;
    }

    public void setSueorgan(String sueorgan) {
        this.sueorgan = sueorgan == null ? null : sueorgan.trim();
    }

    public String getSueorganshort() {
        return sueorganshort;
    }

    public void setSueorganshort(String sueorganshort) {
        this.sueorganshort = sueorganshort == null ? null : sueorganshort.trim();
    }

    public String getSueyear() {
        return sueyear;
    }

    public void setSueyear(String sueyear) {
        this.sueyear = sueyear == null ? null : sueyear.trim();
    }

    public String getSuecaseno() {
        return suecaseno;
    }

    public void setSuecaseno(String suecaseno) {
        this.suecaseno = suecaseno == null ? null : suecaseno.trim();
    }

    public String getSueno() {
        return sueno;
    }

    public void setSueno(String sueno) {
        this.sueno = sueno == null ? null : sueno.trim();
    }

    public Integer getPedigreenum() {
        return pedigreenum;
    }

    public void setPedigreenum(Integer pedigreenum) {
        this.pedigreenum = pedigreenum;
    }

    public String getJointcrimetype() {
        return jointcrimetype;
    }

    public void setJointcrimetype(String jointcrimetype) {
        this.jointcrimetype = jointcrimetype == null ? null : jointcrimetype.trim();
    }

    public Integer getJointcrimes() {
        return jointcrimes;
    }

    public void setJointcrimes(Integer jointcrimes) {
        this.jointcrimes = jointcrimes;
    }

    public Date getInprisondate() {
        return inprisondate;
    }

    public void setInprisondate(Date inprisondate) {
        this.inprisondate = inprisondate;
    }

    public String getFirsttrialsum() {
        return firsttrialsum;
    }

    public void setFirsttrialsum(String firsttrialsum) {
        this.firsttrialsum = firsttrialsum == null ? null : firsttrialsum.trim();
    }

    public String getAppealtype() {
        return appealtype;
    }

    public void setAppealtype(String appealtype) {
        this.appealtype = appealtype == null ? null : appealtype.trim();
    }

    public String getAppeal() {
        return appeal;
    }

    public void setAppeal(String appeal) {
        this.appeal = appeal == null ? null : appeal.trim();
    }

    public String getSentencetype() {
        return sentencetype;
    }

    public void setSentencetype(String sentencetype) {
        this.sentencetype = sentencetype == null ? null : sentencetype.trim();
    }

    public String getJudgmentname() {
        return judgmentname;
    }

    public void setJudgmentname(String judgmentname) {
        this.judgmentname = judgmentname == null ? null : judgmentname.trim();
    }

    public String getJudgmentshort() {
        return judgmentshort;
    }

    public void setJudgmentshort(String judgmentshort) {
        this.judgmentshort = judgmentshort == null ? null : judgmentshort.trim();
    }

    public String getCaseyear() {
        return caseyear;
    }

    public void setCaseyear(String caseyear) {
        this.caseyear = caseyear == null ? null : caseyear.trim();
    }

    public String getCaseorgshort() {
        return caseorgshort;
    }

    public void setCaseorgshort(String caseorgshort) {
        this.caseorgshort = caseorgshort == null ? null : caseorgshort.trim();
    }

    public String getCaseno() {
        return caseno;
    }

    public void setCaseno(String caseno) {
        this.caseno = caseno == null ? null : caseno.trim();
    }

    public String getPunishmenttype() {
        return punishmenttype;
    }

    public void setPunishmenttype(String punishmenttype) {
        this.punishmenttype = punishmenttype == null ? null : punishmenttype.trim();
    }

    public Integer getPunishmentyear() {
        return punishmentyear;
    }

    public void setPunishmentyear(Integer punishmentyear) {
        this.punishmentyear = punishmentyear;
    }

    public Integer getPunishmentmonth() {
        return punishmentmonth;
    }

    public void setPunishmentmonth(Integer punishmentmonth) {
        this.punishmentmonth = punishmentmonth;
    }

    public Integer getPunishmentday() {
        return punishmentday;
    }

    public void setPunishmentday(Integer punishmentday) {
        this.punishmentday = punishmentday;
    }

    public Date getSentencestime() {
        return sentencestime;
    }

    public void setSentencestime(Date sentencestime) {
        this.sentencestime = sentencestime;
    }

    public Date getSentenceetime() {
        return sentenceetime;
    }

    public void setSentenceetime(Date sentenceetime) {
        this.sentenceetime = sentenceetime;
    }

    public Integer getLosepoweryear() {
        return losepoweryear;
    }

    public void setLosepoweryear(Integer losepoweryear) {
        this.losepoweryear = losepoweryear;
    }

    public Integer getLosepowermonth() {
        return losepowermonth;
    }

    public void setLosepowermonth(Integer losepowermonth) {
        this.losepowermonth = losepowermonth;
    }

    public Integer getLosepowereday() {
        return losepowereday;
    }

    public void setLosepowereday(Integer losepowereday) {
        this.losepowereday = losepowereday;
    }

    public String getForfeit() {
        return forfeit;
    }

    public void setForfeit(String forfeit) {
        this.forfeit = forfeit == null ? null : forfeit.trim();
    }

    public String getIsdeport() {
        return isdeport;
    }

    public void setIsdeport(String isdeport) {
        this.isdeport = isdeport == null ? null : isdeport.trim();
    }

    public String getForfeitureproperty() {
        return forfeitureproperty;
    }

    public void setForfeitureproperty(String forfeitureproperty) {
        this.forfeitureproperty = forfeitureproperty == null ? null : forfeitureproperty.trim();
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }

    public String getCompensation() {
        return compensation;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation == null ? null : compensation.trim();
    }

    public Short getIsremission() {
        return isremission;
    }

    public void setIsremission(Short isremission) {
        this.isremission = isremission;
    }

    public Short getIsparole() {
        return isparole;
    }

    public void setIsparole(Short isparole) {
        this.isparole = isparole;
    }

    public String getLimitedremission() {
        return limitedremission;
    }

    public void setLimitedremission(String limitedremission) {
        this.limitedremission = limitedremission == null ? null : limitedremission.trim();
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment == null ? null : payment.trim();
    }

    public String getCriminaltype() {
        return criminaltype;
    }

    public void setCriminaltype(String criminaltype) {
        this.criminaltype = criminaltype == null ? null : criminaltype.trim();
    }

    public Date getIssueddate() {
        return issueddate;
    }

    public void setIssueddate(Date issueddate) {
        this.issueddate = issueddate;
    }

    public String getDetainstatus() {
        return detainstatus;
    }

    public void setDetainstatus(String detainstatus) {
        this.detainstatus = detainstatus == null ? null : detainstatus.trim();
    }

    public String getFleetype() {
        return fleetype;
    }

    public void setFleetype(String fleetype) {
        this.fleetype = fleetype == null ? null : fleetype.trim();
    }

    public String getIsrecidivism() {
        return isrecidivism;
    }

    public void setIsrecidivism(String isrecidivism) {
        this.isrecidivism = isrecidivism == null ? null : isrecidivism.trim();
    }

    public String getRecidivist() {
        return recidivist;
    }

    public void setRecidivist(String recidivist) {
        this.recidivist = recidivist == null ? null : recidivist.trim();
    }

    public String getCation() {
        return cation;
    }

    public void setCation(String cation) {
        this.cation = cation == null ? null : cation.trim();
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage == null ? null : marriage.trim();
    }

    public String getIsruling() {
        return isruling;
    }

    public void setIsruling(String isruling) {
        this.isruling = isruling == null ? null : isruling.trim();
    }

    public String getIstakeeffect() {
        return istakeeffect;
    }

    public void setIstakeeffect(String istakeeffect) {
        this.istakeeffect = istakeeffect == null ? null : istakeeffect.trim();
    }

    public String getCourttype() {
        return courttype;
    }

    public void setCourttype(String courttype) {
        this.courttype = courttype == null ? null : courttype.trim();
    }

    public String getCasetype() {
        return casetype;
    }

    public void setCasetype(String casetype) {
        this.casetype = casetype == null ? null : casetype.trim();
    }

    public String getStolenmoney() {
        return stolenmoney;
    }

    public void setStolenmoney(String stolenmoney) {
        this.stolenmoney = stolenmoney == null ? null : stolenmoney.trim();
    }

    public Date getTakeeffectdate() {
        return takeeffectdate;
    }

    public void setTakeeffectdate(Date takeeffectdate) {
        this.takeeffectdate = takeeffectdate;
    }

    public String getSanctionno() {
        return sanctionno;
    }

    public void setSanctionno(String sanctionno) {
        this.sanctionno = sanctionno == null ? null : sanctionno.trim();
    }

    public String getCauseaction() {
        return causeaction;
    }

    public void setCauseaction(String causeaction) {
        this.causeaction = causeaction == null ? null : causeaction.trim();
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge == null ? null : charge.trim();
    }

    public Date getJudgedate() {
        return judgedate;
    }

    public void setJudgedate(Date judgedate) {
        this.judgedate = judgedate;
    }

    public String getWhereto() {
        return whereto;
    }

    public void setWhereto(String whereto) {
        this.whereto = whereto == null ? null : whereto.trim();
    }

    public String getWatchhouse() {
        return watchhouse;
    }

    public void setWatchhouse(String watchhouse) {
        this.watchhouse = watchhouse == null ? null : watchhouse.trim();
    }

    public String getDetaintype() {
        return detaintype;
    }

    public void setDetaintype(String detaintype) {
        this.detaintype = detaintype == null ? null : detaintype.trim();
    }

    public String getDetainprison() {
        return detainprison;
    }

    public void setDetainprison(String detainprison) {
        this.detainprison = detainprison == null ? null : detainprison.trim();
    }

    public String getSpecialcrimetype() {
        return specialcrimetype;
    }

    public void setSpecialcrimetype(String specialcrimetype) {
        this.specialcrimetype = specialcrimetype == null ? null : specialcrimetype.trim();
    }

    public String getFileno() {
        return fileno;
    }

    public void setFileno(String fileno) {
        this.fileno = fileno == null ? null : fileno.trim();
    }

    public String getChargeclass() {
        return chargeclass;
    }

    public void setChargeclass(String chargeclass) {
        this.chargeclass = chargeclass == null ? null : chargeclass.trim();
    }

    public String getCustodytype() {
        return custodytype;
    }

    public void setCustodytype(String custodytype) {
        this.custodytype = custodytype == null ? null : custodytype.trim();
    }

    public String getCommutation() {
        return commutation;
    }

    public void setCommutation(String commutation) {
        this.commutation = commutation == null ? null : commutation.trim();
    }

    public String getDrugs() {
        return drugs;
    }

    public void setDrugs(String drugs) {
        this.drugs = drugs == null ? null : drugs.trim();
    }

    public String getGun() {
        return gun;
    }

    public void setGun(String gun) {
        this.gun = gun == null ? null : gun.trim();
    }

    public String getUnderworld() {
        return underworld;
    }

    public void setUnderworld(String underworld) {
        this.underworld = underworld == null ? null : underworld.trim();
    }

    public String getWickedness() {
        return wickedness;
    }

    public void setWickedness(String wickedness) {
        this.wickedness = wickedness == null ? null : wickedness.trim();
    }

    public String getTakedrugs() {
        return takedrugs;
    }

    public void setTakedrugs(String takedrugs) {
        this.takedrugs = takedrugs == null ? null : takedrugs.trim();
    }

    public String getEscapes() {
        return escapes;
    }

    public void setEscapes(String escapes) {
        this.escapes = escapes == null ? null : escapes.trim();
    }

    public String getSuicide() {
        return suicide;
    }

    public void setSuicide(String suicide) {
        this.suicide = suicide == null ? null : suicide.trim();
    }

    public String getAssaultepolice() {
        return assaultepolice;
    }

    public void setAssaultepolice(String assaultepolice) {
        this.assaultepolice = assaultepolice == null ? null : assaultepolice.trim();
    }

    public String getOthercase() {
        return othercase;
    }

    public void setOthercase(String othercase) {
        this.othercase = othercase == null ? null : othercase.trim();
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs == null ? null : jobs.trim();
    }

    public String getIssuingauthority() {
        return issuingauthority;
    }

    public void setIssuingauthority(String issuingauthority) {
        this.issuingauthority = issuingauthority == null ? null : issuingauthority.trim();
    }

    public String getReturnloot() {
        return returnloot;
    }

    public void setReturnloot(String returnloot) {
        this.returnloot = returnloot == null ? null : returnloot.trim();
    }

    public String getFulfilcompensation() {
        return fulfilcompensation;
    }

    public void setFulfilcompensation(String fulfilcompensation) {
        this.fulfilcompensation = fulfilcompensation == null ? null : fulfilcompensation.trim();
    }

    public String getExpropriation() {
        return expropriation;
    }

    public void setExpropriation(String expropriation) {
        this.expropriation = expropriation == null ? null : expropriation.trim();
    }

    public Date getExecutiondate() {
        return executiondate;
    }

    public void setExecutiondate(Date executiondate) {
        this.executiondate = executiondate;
    }

    public String getMediaattention() {
        return mediaattention;
    }

    public void setMediaattention(String mediaattention) {
        this.mediaattention = mediaattention == null ? null : mediaattention.trim();
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus == null ? null : focus.trim();
    }

    public String getSpecialcontrol() {
        return specialcontrol;
    }

    public void setSpecialcontrol(String specialcontrol) {
        this.specialcontrol = specialcontrol == null ? null : specialcontrol.trim();
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion == null ? null : opinion.trim();
    }

    public String getSanclassstatus() {
        return sanclassstatus;
    }

    public void setSanclassstatus(String sanclassstatus) {
        this.sanclassstatus = sanclassstatus == null ? null : sanclassstatus.trim();
    }

    public String getOldprisoner() {
        return oldprisoner;
    }

    public void setOldprisoner(String oldprisoner) {
        this.oldprisoner = oldprisoner == null ? null : oldprisoner.trim();
    }

    public String getIllprisoner() {
        return illprisoner;
    }

    public void setIllprisoner(String illprisoner) {
        this.illprisoner = illprisoner == null ? null : illprisoner.trim();
    }

    public String getDeformityprisoner() {
        return deformityprisoner;
    }

    public void setDeformityprisoner(String deformityprisoner) {
        this.deformityprisoner = deformityprisoner == null ? null : deformityprisoner.trim();
    }

    public String getRedrugs() {
        return redrugs;
    }

    public void setRedrugs(String redrugs) {
        this.redrugs = redrugs == null ? null : redrugs.trim();
    }

    public String getCrimes() {
        return crimes;
    }

    public void setCrimes(String crimes) {
        this.crimes = crimes == null ? null : crimes.trim();
    }

    public String getOverfourcrimes() {
        return overfourcrimes;
    }

    public void setOverfourcrimes(String overfourcrimes) {
        this.overfourcrimes = overfourcrimes == null ? null : overfourcrimes.trim();
    }

    public String getSeriousviolations() {
        return seriousviolations;
    }

    public void setSeriousviolations(String seriousviolations) {
        this.seriousviolations = seriousviolations == null ? null : seriousviolations.trim();
    }

    public String getCombinedpunishment() {
        return combinedpunishment;
    }

    public void setCombinedpunishment(String combinedpunishment) {
        this.combinedpunishment = combinedpunishment == null ? null : combinedpunishment.trim();
    }

    public String getMajorcriminal() {
        return majorcriminal;
    }

    public void setMajorcriminal(String majorcriminal) {
        this.majorcriminal = majorcriminal == null ? null : majorcriminal.trim();
    }

    public String getStrictlyplot() {
        return strictlyplot;
    }

    public void setStrictlyplot(String strictlyplot) {
        this.strictlyplot = strictlyplot == null ? null : strictlyplot.trim();
    }

    public String getPostcrime() {
        return postcrime;
    }

    public void setPostcrime(String postcrime) {
        this.postcrime = postcrime == null ? null : postcrime.trim();
    }

    public String getUndermine() {
        return undermine;
    }

    public void setUndermine(String undermine) {
        this.undermine = undermine == null ? null : undermine.trim();
    }

    public String getWideplots() {
        return wideplots;
    }

    public void setWideplots(String wideplots) {
        this.wideplots = wideplots == null ? null : wideplots.trim();
    }

    public String getSanremark() {
        return sanremark;
    }

    public void setSanremark(String sanremark) {
        this.sanremark = sanremark == null ? null : sanremark.trim();
    }

    public String getPrincipalaccessory() {
        return principalaccessory;
    }

    public void setPrincipalaccessory(String principalaccessory) {
        this.principalaccessory = principalaccessory == null ? null : principalaccessory.trim();
    }

    public String getDeclarationproperty() {
        return declarationproperty;
    }

    public void setDeclarationproperty(String declarationproperty) {
        this.declarationproperty = declarationproperty == null ? null : declarationproperty.trim();
    }

    public String getCrimtype() {
        return crimtype;
    }

    public void setCrimtype(String crimtype) {
        this.crimtype = crimtype == null ? null : crimtype.trim();
    }

    public String getMaincase() {
        return maincase;
    }

    public void setMaincase(String maincase) {
        this.maincase = maincase == null ? null : maincase.trim();
    }

    public String getEntryways() {
        return entryways;
    }

    public void setEntryways(String entryways) {
        this.entryways = entryways == null ? null : entryways.trim();
    }

    public String getIschinese() {
        return ischinese;
    }

    public void setIschinese(String ischinese) {
        this.ischinese = ischinese == null ? null : ischinese.trim();
    }

    public String getIsforeign() {
        return isforeign;
    }

    public void setIsforeign(String isforeign) {
        this.isforeign = isforeign == null ? null : isforeign.trim();
    }

    public Short getIsdeath() {
        return isdeath;
    }

    public void setIsdeath(Short isdeath) {
        this.isdeath = isdeath;
    }

    public String getOrgid1() {
        return orgid1;
    }

    public void setOrgid1(String orgid1) {
        this.orgid1 = orgid1 == null ? null : orgid1.trim();
    }

    public String getOrgid2() {
        return orgid2;
    }

    public void setOrgid2(String orgid2) {
        this.orgid2 = orgid2 == null ? null : orgid2.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAliasno() {
		return aliasno;
	}

	public void setAliasno(String aliasno) {
		this.aliasno = aliasno;
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

	public String getCasenature() {
		return casenature;
	}

	public void setCasenature(String casenature) {
		this.casenature = casenature;
	}

	public String getQitareport() {
		return qitareport;
	}

	public void setQitareport(String qitareport) {
		this.qitareport = qitareport;
	}

	public String getImportantcriminal() {
		return importantcriminal;
	}

	public void setImportantcriminal(String importantcriminal) {
		this.importantcriminal = importantcriminal;
	}

	public String getPunishmenteight() {
		return punishmenteight;
	}

	public void setPunishmenteight(String punishmenteight) {
		this.punishmenteight = punishmenteight;
	}

	public String getArrestauthority_code() {
		return arrestauthority_code;
	}

	public void setArrestauthority_code(String arrestauthority_code) {
		this.arrestauthority_code = arrestauthority_code;
	}

	public String getCaseothertype() {
		return caseothertype;
	}

	public void setCaseothertype(String caseothertype) {
		this.caseothertype = caseothertype;
	}
	
	
	
}
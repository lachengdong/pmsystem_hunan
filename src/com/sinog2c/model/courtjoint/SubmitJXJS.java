package com.sinog2c.model.courtjoint;
/*package com.sinog2c.model.ws;

*//**
 * 
 * 法院需要的减刑假释信息
 *//*
public class SubmitJXJS {
	//刑罚变更类型	
	private String AJXXCOMMUTETYPE;
	//新案件来源	
	//主罪名	
	private String AJXXCHARGE;
	//罪名注释
	private String AJXXCHARGEREMARK;
	//减刑/假释类型	
	private String AJXXJXJSTYPE;
	//对应罪犯编号	
	private String AJXXCRIMID;
	//案件编号
	private String AJXXCASENUM;
	//涉三股势力	
	//报请机关	
	private String BQXXTHISSHORTNAME;
	//接收法院	
	private String BQXXTIQINGFAYUANID;
	//报请理由	
	private String BQXXSBRESON;
	//批次	
	private String BQXXBATCH;
	//服刑机关	
	private String BQXXDETAINPRISON;
	//报请日期	
	private String BQXXJAILREPORT;
	//监区	
	private String BQXXAREANAME;
	//变更前主刑刑种	
	private String BQXXNOWSENTENCETYPE;
	//变更前剥夺政治权利终身	
	private String BQXXLOSEPOWERYEA;
	//变更前剥夺政治权利年限（年）	
	private String BQXXLOSEPOWERYEAR;
	//变更前剥夺政治权利年限（月）	
	private String BQXXLOSEPOWERMONTH;
	//变更前剥夺政治权利年限（日）	
	private String BQXXLOSEPOWEREDAY;
	//变更前自由刑刑期（年）	
	private String BQXXCOURTCHANGEYEAR;
	//变更前自由刑刑期（月）	
	private String BQXXCOURTCHANGEMONTH;
	//变更前自由刑刑期（日）	
	private String BQXXCOURTCHANGEDAY;
//	本次申请减刑（年）	
	private String BQXXCOMMUTEYEAR;
//	本次申请减刑（月）	
	private String BQXXCOMMUTEMONTH;
//	本次申请减刑（日）	
	private String BQXXCOMMUTEDAY;
//	变更后主刑刑种	
//	从严情况	
	private String BQXXLENIENT_RANGE;
//	从宽情况	
	private String BQXXSTRICT_RANGE;
//	刑满日期	
	private String BQXXCOURTCHANGETO;
//	假释监管条件	
//	已减刑刑期（年）
	private String BQXXYETREDUCEYEAR;
//	已减刑刑期（月）
	private String BQXXYETREDUCEMONTH;
//	已减刑刑期（日）	
	private String BQXXYETREDUCEDAY;
//	剩余自由刑（年）	
	private String BQXXNOWPUNISHMENTYEAR;
//	剩余自由刑（月）
	private String BQXXNOWPUNISHMENTMONTH;
//	剩余自由刑（日）	
	private String BQXXNOWPUNISHMENTDAY;
	//-----------------------------------------------------------------
//	建议减刑/假释类型	
	private String JYCOMMUTETYPE;
//	是否减余刑	
//	减刑（年）	
	private String JYCOMMUTEYEAR;
//	减刑（月）	
	private String JYCOMMUTEMONTH;
//	减刑（日）
	private String JYCOMMUTEDAY;
//	剥夺政治权利终身
	private String JYBDZZQLLIFE;
//	剥夺年限（年）	
	private String JYBDZZQLYEAR;
//	剥夺年限（月）	
//	剥夺年限（日）	
//	监狱管理局审核日期	
//	建议减刑/假释类型	
	private String JYCCOMMUTETYPE;
//	是否减余刑	
//	减刑（年）
	private String JYCCOMMUTEYEAR;
//	减刑（月）	
	private String JYCCOMMUTEMONTH;
//	减刑（日）	
	private String JYCCOMMUTEDAY;
//	剥夺政治权利终身	
	private String JYCBDZZQLLIFE;
//	剥夺年限（年）	
	private String JYCBDZZQLYEAR;
//	剥夺年限（月）	
//	剥夺年限（日）	
//	建议减刑/假释类型	
//	是否减余刑	
//	减刑（年）	COMMUTEYEAR
//	减刑（月）	COMMUTEMONTH
//	减刑（日）	COMMUTEDAY
//	剥夺政治权利终身	BDZZQLLIFE
//	剥夺年限（年）	BDZZQLYEAR
//	剥夺年限（月）	
//	剥夺年限（日）	
//	监狱是否采纳	ISAGREE
//	未采纳理由	
	//_________________________________________
//	减刑尺度	COMMUTATION
//	是否共同犯罪	ISJOINTCRIME
//	是否团伙犯罪	ISGANGCRIME
//	团伙中地位	GANGSTATUS
//	团伙人数	JOINTNUM
//	犯罪地位	JOINTCRIMES
//	犯罪事实	CRIMEFACE
//	流窜类型	FLEETYPE_CODE
//	案件涉及	CASEINVOLVE
//	四史	SISHI
//	罪犯编号	CRIMINAID
//	姓名	NAME
//	证件类型	
//	证件号码	CREDENTIALSNUM
//	民族	NATION
//	性别	GENDER
//	出生日期	BIRTHDAY
//	犯罪时间	CRIMEDATE
//	犯罪时年龄	ARRESTDATE
//	是否未成年犯罪	ISMINOR
//	婚姻状况	MARRIAGE
//	职业	VOCATION
//	政治面貌	POLITICAL
//	文化程度	CATION
//	国籍	COUNTRYAREA_CODE
//	户籍所在地	DOMICILE
//	家庭住址	HOMEADDRESS
//	籍贯	ORIGIN
//	是否累犯	ISRECIDIVISM
//	是否数罪并罚	COMBINEDPUNISHMENT
//	是否限制减刑	ISLIMIT
//	是否职务犯罪	POSTCRIME
//	是否破坏金融	UNDERMINE
//	是否立功	
//	是否涉黑	UNDERWORLD
//	监狱存款总额	ALLINCOME
//	监狱劳动报酬	REMUNERATION
//	监狱消费	ALLCONSUME
//	特殊情节	SPECIALPLOT
//	入监日期	INPRISONDATE
//	交付执行日期	EXECUTIONDATE
//	行政级别	LEVEL
//	职务级别	CLEVEL
//	分押类别	CUSTODYTYPE_CODE
//	分管等级	CHARGECLASS_CODE
//	是否在监外执行	STATUS
//	联系方式	
//	原单位	PPROVINCE
//	身份	IDENTITY
//	涉及国家秘密	
//	是否终身监禁	
//	是否三无人员	SANCLASSSTATUS
//	是否金融诈骗	UNDERMINE
//	刑罚执行期间又故意犯罪	
//	是否死刑复核	
//	是否惯犯	RECIDIVIST
//	年月	
//	监狱内收入	REMUNERATION
//	监狱外收入	WITHCASH 
//	支出	SHOPPING
//	金额	OVERPLUS
//	老病残类型	OIDTYPE
//	病残程度	
//	老病残情况	
//	医院鉴定意见	
//	鉴定日期	CONFIRMTIME
//	撤销日期	REVOCATIONTIME
//	开始日期	SENTENCE
//	结束日期	STARTTIME
//	前科类别	TYPE
//	罪名	CRIMENAME
//	判决机关	
//	原判刑期（年）	RAWYEAR
//	原判刑期（月）	RAWMONTH
//	原判刑期（日）	RAWDAY
//	判决字号	
//	执行机关	EXECUTINGORGAN
//	开始日期	
//	结束日期	
//	劣迹类别	
//	处罚原因	
//	执行机关	
//	姓名	NAME
//	性别	GENDER
//	称谓	RELATIONSHIP
//	出生日期	BIRTHDAY
//	政治面貌	POLITICAL_CODE
//	家庭住址	HOMEADDRESS
//	职业	VOCATION
//	职务	RDUTY
//	所在单位	ORGNAME
//	是否主联系人	ISPRIMARYCONTACT
//	年龄	
//	联系方式	PHONE
//	开始日期	BEGINDATE
//	结束日期	ENDDATE
//	单位	ORGNAME
//	职业	VOCATION
//	职务	
	private String DUTY;
//	法律积分	
	private String LAWINTEGRAL;
//	当年积分	
//	悔改表现	
	private String REPENTANCE;
//	立功表现	
//	重大立功表现	
	private String HONOUR;
//	立功表现备注	
//	奖励情况	
	private String REWARDINFO;
//	处分情况	
	private String PUNISHINFO;
//	罚金金额	
	private String FINE;
//	已交纳罚金	
	private String SUMFINE;
//	本次交纳金额	
	private String THISFINE;
//	民事赔偿金额	
	private String CIVIL;
//	已赔偿金额	
	private String SUMCIVIL;
//	本次赔偿金额	
	private String THISCIVIL;
//	财产刑	
//	本次执行财产刑	
//	已执行财产刑	
//	判决责令退赔金额	
	private String QUANTUM;
//	未赔偿金额	
	private String UNDONEQUANTUM;
//	判决追缴金额	
	private String RECOVEREDAMOUNT;
//	已追缴金额	
	private String DONERECOVEREDAMOUNT;
//	未追缴金额	
	private String UNDONERECOVEREDAMOUNT;
//	未执行罚金金额	
	private String NONEXECUTIONAMOUNT;
//	判决没收财产金额
	private String CONFISCATEAMOUNT;
//	已履行没收财产金额	
	private String DONECONFISCATEAMOUNT;
//	未履行没收财产金额	
	private String UNDONECONFISCATEAMOUNT;
//	奖励类别	
	private String NAME;
//	奖励原因	
//	奖励日期	
	private String STIME;
//	惩罚类别	
	private String NAME;
//	惩罚原因	
//	惩罚日期	
	private String STIME;

	private String HANDLECOURT;
//	上（抗）诉类型	
//	判决主罪名	
	private String MAINCHARGE;
//	判决主罪名描述	
	private String MAINCHARGEREMARKE;
//	判决其他罪名	
//	判决其他罪名描述	
//	裁判日期	
	private String JUDGEDATE;
//	结案方式	
//	案号	
	private String CASENUM;
//	审判程序	
//	判决主刑	
	private String MAINTORTURE;
//	原判自由刑期（年）	
	private String ORIGINALY;
//	原判自由刑期（月）	
	private String ORIGINALM;
//	原判自由刑期（日）	
	private String ORIGINALD;
//	是否剥夺政治权利终身	
	private String LOSEPOWERLIFE;
//	剥夺政治权利（年）	
	private String LOSEPOWERYEAR;
//	剥夺政治权利（月）	
	private String LOSEPOWERMONTH;
//	剥夺政治权利（日）	
	private String LOSEPOWERDAY;
//	没收个人全部财产	
	private String CONFISCATEALLAMOUNT;
//	没收个人财产	
	private String EXPROPRIATION;
//	罚金	
	private String THISEXPROPRIATION;
//	民事赔偿	
	private String THISCIVIL;
//	原判宣告日期	
	private String DECLARDATE;
//	罪犯羁押开始日期	
	private String INPERSION;
//	原审法院生效日期
	private String EFFECTDATE;
//	刑期起日	
	private String PRISONSTARTTIME;
//	刑期止日	
	private String PRISONENDTIME;
//	是否交付执行	
//	交付执行日期	
	private String EXECUTETIME;
//	报请机关	
	private String APPLYOFFICE;
//	报请理由	
//	服刑地点	
	private String JAILNAME;
//	经办法院	
	private String COURTTITLE;
//	减刑案号	
	private String COMMUTATIONCASENUM;
//	减刑日期	
	private String COMMUTATIONDATE;
//	申请次数	
//	已减刑（年）	
	private String COMMUTATIONYEAR;
//	已减刑（月）	
	private String COMMUTATIONMONTH;
//	已减刑（日）	
	private String COMMUTATIONDAY;
//	已服刑（年）	
	private String EXECUTEDYEAR;
//	已服刑（月）	
	private String EXECUTEDMONTH;
//	已服刑（日）	
	private String EXECUTEDDAY;
//	变更前主刑刑种	
	private String NOWPUNISHMENTMAIN;
//	变更前刑期（年）	
	private String NOWPUNISHMENTYEAR;
//	变更前刑期（月）	
	private String NOWPUNISHMENTMONTH;
//	变更前刑期（日）
	private String NOWPUNISHMENTDAY;
//	变更前剥夺政治权利终身	
	private String LOSEPOWERLIFE;
//	剥夺政治权利（年）	
	private String LOSEPOWERYEAR;
//	剥夺政治权利（月）	
	private String LOSEPOWERMONTH;
//	剥夺政治权利（日）	
	private String LOSEPOWERDAY;
//	本次减刑类型	
	private String COMMUTATIONTYPE;
//	自由刑刑期（年）	
	private String FREEPUNISHMENTYEAR;
//	自由刑刑期（月）	
	private String FREEPUNISHMENTMONT;
//	自由刑刑期（日）
	private String FREEPUNISHMENTDAY;
//	本次减刑（年）	
	private String COURTCHANGEYEAR;
//	本次减刑（月）	
	private String COURTCHANGEMONTH;
//	本次减刑（日）	
	private String COURTCHANGEDAY;
//	本次剥夺政治权利终身	
	private String NOWLOSEPOWERLIFE;
//	本次剥夺政治权利（年）	
	private String NOWLOSEPOWERYEAR;
//	本次剥夺政治权利（月）	
	private String NOWLOSEPOWERMONTH;
//	本次剥夺政治权利（日）	
	private String NOWLOSEPOWERDAY;
//	变更后释放日期	
//	结案方式	
//	结案日期	
//	减免罚金数额	
	private String ANNULPENALTYMONEY;
//	收监执行事由	
	private String COMMITMENTREASON;
//	不计入刑期期间	
//	变更后刑期起日	
	private String COURTCHANGEFROM;
	//变更后刑期止日	
	private String COURTCHANGETO;
	//考验期起始日期	
	private String ORDEALSTARTDATE;
	//考验期届满日期	
	private String ORDEALENDDATE;
	//减刑事由	
	private String REDUCEREASON;
}
*/
package com.sinog2c.model.flow;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sinog2c.model.system.SystemUser;

public class TbflowInstance {
	private String flowid;

	private String flowdefid;

	private String applyid;

	private String applyname;

	private Date applydatetime;

	private Date startdatetime;

	private Date enddatetime;

	private String conent;

	private Short islocked;

	private String text1;

	private String text2;

	private String text3;

	private String text4;

	private String text5;

	private String text6;

	private String text7;

	private String text8;

	private String text9;

	private String text10;

	private String text11;

	private String text12;

	private String text13;

	private String text14;

	private String text15;

	private String text16;

	private String text17;

	private String text18;

	private String text19;

	private String text20;

	private String text21;

	private String text22;

	private String text23;

	private String text24;

	private String text25;

	private Integer int1;

	private Integer int2;

	private Integer int3;

	private Integer int4;

	private Integer int5;

	private String departid;

	private Long sn;

	private String opname;

	private String opid;

	private Date optime;

	private Short int6;

	private String cnode;

	private String cnodename;

	private String cnodestate;

	private String flowstate;

	private String flowtype;

	private String flowname;

	private String hasaccessory;

	private String doccontenfilename;

	private String docaipcontentfilename;

	private String note;

	private String explain;

	private String lnode;

	private String lnodename;

	private String feedback;

	private String response;

	private BigDecimal urgeint1;

	private String taskid;

	private String flowdocid;

	private String doccontent;

	private String templetid;

	private String action;

	private String lassigneername;

	private List<SystemUser> assigners;

	private short needNotice = 1;

	private String menuid;
	
	private String attachjs;
	
	private String className;
	
	private String methodName;
	
	private String jsonParameter;
	
	private String classtype;
	

	public String getFlowid() {
		return flowid;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid == null ? null : flowid.trim();
	}

	public String getFlowdefid() {
		return flowdefid;
	}

	public void setFlowdefid(String flowdefid) {
		this.flowdefid = flowdefid == null ? null : flowdefid.trim();
	}

	public String getApplyid() {
		return applyid;
	}

	public void setApplyid(String applyid) {
		this.applyid = applyid == null ? null : applyid.trim();
	}

	public String getApplyname() {
		return applyname;
	}

	public void setApplyname(String applyname) {
		this.applyname = applyname == null ? null : applyname.trim();
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getApplydatetime() {
		return applydatetime;
	}

	public void setApplydatetime(Date applydatetime) {
		this.applydatetime = applydatetime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getStartdatetime() {
		return startdatetime;
	}

	public void setStartdatetime(Date startdatetime) {
		this.startdatetime = startdatetime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getEnddatetime() {
		return enddatetime;
	}

	public void setEnddatetime(Date enddatetime) {
		this.enddatetime = enddatetime;
	}

	public String getConent() {
		return conent;
	}

	public void setConent(String conent) {
		this.conent = conent == null ? null : conent.trim();
	}

	public Short getIslocked() {
		return islocked;
	}

	public void setIslocked(Short islocked) {
		this.islocked = islocked;
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

	public String getText5() {
		return text5;
	}

	public void setText5(String text5) {
		this.text5 = text5 == null ? null : text5.trim();
	}

	public String getText6() {
		return text6;
	}

	public void setText6(String text6) {
		this.text6 = text6 == null ? null : text6.trim();
	}

	public String getText7() {
		return text7;
	}

	public void setText7(String text7) {
		this.text7 = text7 == null ? null : text7.trim();
	}

	public String getText8() {
		return text8;
	}

	public void setText8(String text8) {
		this.text8 = text8 == null ? null : text8.trim();
	}

	public String getText9() {
		return text9;
	}

	public void setText9(String text9) {
		this.text9 = text9 == null ? null : text9.trim();
	}

	public String getText10() {
		return text10;
	}

	public void setText10(String text10) {
		this.text10 = text10 == null ? null : text10.trim();
	}

	public String getText11() {
		return text11;
	}

	public void setText11(String text11) {
		this.text11 = text11 == null ? null : text11.trim();
	}

	public String getText12() {
		return text12;
	}

	public void setText12(String text12) {
		this.text12 = text12 == null ? null : text12.trim();
	}

	public String getText13() {
		return text13;
	}

	public void setText13(String text13) {
		this.text13 = text13 == null ? null : text13.trim();
	}

	public String getText14() {
		return text14;
	}

	public void setText14(String text14) {
		this.text14 = text14 == null ? null : text14.trim();
	}

	public String getText15() {
		return text15;
	}

	public void setText15(String text15) {
		this.text15 = text15 == null ? null : text15.trim();
	}

	public String getText16() {
		return text16;
	}

	public void setText16(String text16) {
		this.text16 = text16 == null ? null : text16.trim();
	}

	public String getText17() {
		return text17;
	}

	public void setText17(String text17) {
		this.text17 = text17 == null ? null : text17.trim();
	}

	public String getText18() {
		return text18;
	}

	public void setText18(String text18) {
		this.text18 = text18 == null ? null : text18.trim();
	}

	public String getText19() {
		return text19;
	}

	public void setText19(String text19) {
		this.text19 = text19 == null ? null : text19.trim();
	}

	public String getText20() {
		return text20;
	}

	public void setText20(String text20) {
		this.text20 = text20 == null ? null : text20.trim();
	}

	public String getText21() {
		return text21;
	}

	public void setText21(String text21) {
		this.text21 = text21 == null ? null : text21.trim();
	}

	public String getText22() {
		return text22;
	}

	public void setText22(String text22) {
		this.text22 = text22 == null ? null : text22.trim();
	}

	public String getText23() {
		return text23;
	}

	public void setText23(String text23) {
		this.text23 = text23 == null ? null : text23.trim();
	}

	public String getText24() {
		return text24;
	}

	public void setText24(String text24) {
		this.text24 = text24 == null ? null : text24.trim();
	}

	public String getText25() {
		return text25;
	}

	public void setText25(String text25) {
		this.text25 = text25 == null ? null : text25.trim();
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

	public Integer getInt3() {
		return int3;
	}

	public void setInt3(Integer int3) {
		this.int3 = int3;
	}

	public Integer getInt4() {
		return int4;
	}

	public void setInt4(Integer int4) {
		this.int4 = int4;
	}

	public Integer getInt5() {
		return int5;
	}

	public void setInt5(Integer int5) {
		this.int5 = int5;
	}

	public String getDepartid() {
		return departid;
	}

	public void setDepartid(String departid) {
		this.departid = departid == null ? null : departid.trim();
	}

	public Long getSn() {
		return sn;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public String getOpname() {
		return opname;
	}

	public void setOpname(String opname) {
		this.opname = opname == null ? null : opname.trim();
	}

	public String getOpid() {
		return opid;
	}

	public void setOpid(String opid) {
		this.opid = opid == null ? null : opid.trim();
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	public Short getInt6() {
		return int6;
	}

	public void setInt6(Short int6) {
		this.int6 = int6;
	}

	public String getCnode() {
		return cnode;
	}

	public void setCnode(String cnode) {
		this.cnode = cnode == null ? null : cnode.trim();
	}

	public String getCnodename() {
		return cnodename;
	}

	public void setCnodename(String cnodename) {
		this.cnodename = cnodename == null ? null : cnodename.trim();
	}

	public String getCnodestate() {
		return cnodestate;
	}

	public void setCnodestate(String cnodestate) {
		this.cnodestate = cnodestate == null ? null : cnodestate.trim();
	}

	public String getFlowstate() {
		return flowstate;
	}

	public void setFlowstate(String flowstate) {
		this.flowstate = flowstate == null ? null : flowstate.trim();
	}

	public String getFlowtype() {
		return flowtype;
	}

	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype == null ? null : flowtype.trim();
	}

	public String getFlowname() {
		return flowname;
	}

	public void setFlowname(String flowname) {
		this.flowname = flowname == null ? null : flowname.trim();
	}

	public String getHasaccessory() {
		return hasaccessory;
	}

	public void setHasaccessory(String hasaccessory) {
		this.hasaccessory = hasaccessory == null ? null : hasaccessory.trim();
	}

	public String getDoccontenfilename() {
		return doccontenfilename;
	}

	public void setDoccontenfilename(String doccontenfilename) {
		this.doccontenfilename = doccontenfilename == null ? null
				: doccontenfilename.trim();
	}

	public String getDocaipcontentfilename() {
		return docaipcontentfilename;
	}

	public void setDocaipcontentfilename(String docaipcontentfilename) {
		this.docaipcontentfilename = docaipcontentfilename == null ? null
				: docaipcontentfilename.trim();
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getLnode() {
		return lnode;
	}

	public void setLnode(String lnode) {
		this.lnode = lnode;
	}

	public String getLnodename() {
		return lnodename;
	}

	public void setLnodename(String lnodename) {
		this.lnodename = lnodename;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public BigDecimal getUrgeint1() {
		return urgeint1;
	}

	public void setUrgeint1(BigDecimal urgeint1) {
		this.urgeint1 = urgeint1;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getFlowdocid() {
		return flowdocid;
	}

	public void setFlowdocid(String flowdocid) {
		this.flowdocid = flowdocid;
	}

	public String getDoccontent() {
		return doccontent;
	}

	public void setDoccontent(String doccontent) {
		this.doccontent = doccontent;
	}

	public String getTempletid() {
		return templetid;
	}

	public void setTempletid(String templetid) {
		this.templetid = templetid;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getLassigneername() {
		return lassigneername;
	}

	public void setLassigneername(String lassigneername) {
		this.lassigneername = lassigneername;
	}

	public List<SystemUser> getAssigners() {
		return assigners;
	}

	public void setAssigners(List<SystemUser> assigners) {
		this.assigners = assigners;
	}

	public short getNeedNotice() {
		return needNotice;
	}

	public void setNeedNotice(short needNotice) {
		this.needNotice = needNotice;
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getAttachjs() {
		return attachjs;
	}

	public void setAttachjs(String attachjs) {
		this.attachjs = attachjs;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getJsonParameter() {
		return jsonParameter;
	}

	public void setJsonParameter(String jsonParameter) {
		this.jsonParameter = jsonParameter;
	}

	public String getClasstype() {
		return classtype;
	}

	public void setClasstype(String classtype) {
		this.classtype = classtype;
	}

}
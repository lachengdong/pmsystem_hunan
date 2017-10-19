package com.sinog2c.mvc.controller.court;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.report.RMEngine;
/**
 * 法院立案
 * 
 */
@Controller()
public class CourtCaseController extends ControllerBase {
	
	
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private UvFlowService uvFlowService;
	@Autowired
	private SystemCodeService systemCodeService;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	/*@Autowired
	TbCourtFullCourtMapper tbCourtFullCourtMapper;
	@Autowired
	UvCourtBigDataMapper uvCourtBigDataMapper;
	@Autowired
	CourtApprovalService  courtApprovalService;*/
	
//	@RequestMapping(value = "toCourtLiAnCaseListPage")
//	public ModelAndView toCourtLiAnCaseListPage(HttpServletRequest request) {
//		
//		returnResourceMap(request);
//		SystemUser su = getLoginUser(request);
//		String orgid = su.getOrgid();
//		String departid = su.getDepartid();
//		String flowdefid = request.getParameter("flowdefid");
//		String tempid = request.getParameter("tempid");
//		String courttype = request.getParameter("courttype");
//		Map paramMap = new HashMap();
//		paramMap.put("departid", departid);
//		paramMap.put("flowdefid", flowdefid);
//		String curyear = flowBaseService.getCourtMaxCaseYear(paramMap);//案件号的最大年
//		if(StringNumberUtil.isEmpty(curyear)){
//			Date date = new Date();
//			curyear = DateUtil.dateFormat(date, "yyyy");
//		}
//		paramMap.put("curyear", curyear);
//		
//		String casenumber = flowBaseService.getCourtMaxCaseNum(paramMap);//案件号
//		
//		
//		SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
//		String shortname = so.getShortname();
//		String casetype="";
//		Map paraMap = new HashMap();
//		paraMap.put("codetype", "GK7788");
//		paraMap.put("codeid", courttype);
//		Map temMap =  systemCodeService.getDataByMap(paraMap);
//		if(null!=temMap){
//			casetype = temMap.get("name")==null?"":temMap.get("name").toString();
//		}
//		
//		request.setAttribute("casenumber", casenumber);
//		request.setAttribute("curyear", curyear);
//		request.setAttribute("shortname", shortname);
//		request.setAttribute("casetype", casetype);
//		request.setAttribute("courttype", courttype);
//		request.setAttribute("flowdefid", flowdefid);
//		request.setAttribute("tempid", tempid);
//		
//		ModelAndView mav = null;
//		View view = new InternalResourceView("WEB-INF/JSP/court/courtLiAnCaseList.jsp");
//		mav = new ModelAndView(view);
//		return mav;
//	}
	
	/**
	 * 方法描述：列表页面
	 * 2014-8-11
	 */
	@RequestMapping(value = "/getCourtLiAnList")
	@ResponseBody
	public Object getProvinceLiAnList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map> data = new ArrayList<Map> ();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));       
			SystemUser su = getLoginUser(request);
			String orgid = su.getOrgid();
			//String departid = su.getDepartid();
			String departid = su.getOrganization().getOrgid();
			String jailid = request.getParameter("jailid");
			String flowdefid = request.getParameter("flowdefid");
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			Map<String,Object> map = new HashMap<String,Object>();
			if(StringNumberUtil.notEmpty(key)){
				map.put("key", key);
			}
			map.put("orgid", orgid);
			map.put("departid", departid);
			map.put("jailto", "1");
			if(StringNumberUtil.notEmpty(jailid)){
				map.put("jailid", jailid);
			}
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("flowdefid", flowdefid);
	    	//map.put("state", "-1,0,1,2");
	    	int count = 0;
			if(StringNumberUtil.notEmpty(flowdefid)&&"other_fyjxjssp".equals(flowdefid)){//法院减刑假释
				count = tbxfSentencealterationService.countCourtLiAnList(map);
		    	data= tbxfSentencealterationService.getCourtLiAnList(map);
			}else if(StringNumberUtil.notEmpty(flowdefid)&&"other_fyjxjsbbsp".equals(flowdefid)){//法院报备
				count = tbxfSentencealterationService.countCourtReportLiAnList(map);
		    	data= tbxfSentencealterationService.getCourtReportLiAnList(map);
			}else if(StringNumberUtil.notEmpty(flowdefid)&&"other_fyjxjsjcsp".equals(flowdefid)){//法院检查
				count = tbxfSentencealterationService.countCourtCheckLiAnList(map);
		    	data= tbxfSentencealterationService.getCourtCheckLiAnList(map);
			}else if(StringNumberUtil.notEmpty(flowdefid)&&"other_fyjxjsjdsp".equals(flowdefid)){//法院监督
				count = tbxfSentencealterationService.countCourtJianduLiAnList(map);
		    	data= tbxfSentencealterationService.getCourtJianduLiAnList(map);
			}
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultmap;
	}
	
	
	
	/**
	 * 法院立案
	 * @param request
	 * @param response
	 * @return 成功返回0 失败返回-1
	 */
	@RequestMapping(value = "/courtLiAn.action")
	@ResponseBody
	@SuppressWarnings("all")
	public Object courtLiAn(HttpServletRequest request,HttpServletResponse response) {
		String result = "-1";
		
		// 用户对象
		SystemUser user = getLoginUser(request);
		
		//取得参数
		String data = request.getParameter("data");//获取表单页面的数据
		
		String resid = request.getParameter("resid");//和流程相关按钮ID
		String flowid = request.getParameter("flowid");//流程ID
		String conent = request.getParameter("conent");//内容
		String tempid = request.getParameter("tempid");//表单ID
		String examineid = request.getParameter("examineid");
		String orgid = request.getParameter("orgid");//申请人的部门ID
		String applyid = request.getParameter("applyid");//申请人ID
		String applyname = request.getParameter("applyname");//申请人名称
		String flowdefid = request.getParameter("flowdefid");//流程自定义ID
		String docconent = request.getParameter("docconent");//审批大字段
		String operateType = request.getParameter("operateType");//判断新增、修改
		String flowdraftid = request.getParameter("flowdraftid");//流程草稿ID
		String commenttext = request.getParameter("commenttext");//审批意见
		String curyear = request.getParameter("curyear");//案件年度
		String acceptdate = request.getParameter("acceptdate");
		String liandate = request.getParameter("liandate");
//		String commutetype = request.getParameter("commutetype");

		Map temMap2 = new HashMap();
		temMap2.put("departid", user.getDepartid());
		temMap2.put("flowdefid", flowdefid);
		temMap2.put("curyear", curyear);
//		if(StringNumberUtil.notEmpty(commutetype)){
//			temMap2.put("commutetype", commutetype);
//		}
		//修改之前 取 数据库中的最大案件号+1
//		String casenumber = flowBaseService.getMaxCaseNum(temMap2);//案件号
		//修改啦，可以取页面输入的 案件号啦
		String casenumber = request.getParameter("casenumber");
		
		String casetype = request.getParameter("casetype");
		String courttype = request.getParameter("courttype");
//		if(StringNumberUtil.notEmpty(commutetype)&&"0".equals(commutetype.trim())){
//			casetype = "减字";
//		}else if(StringNumberUtil.notEmpty(commutetype)&&"1".equals(commutetype.trim())){
//			casetype = "假字";
//		}
		
		if(user!=null){
			if(applyid == null || "".equals(applyid)){
				applyid =user.getUserid();//申请人ID
			}
			if(applyname == null || "".equals(applyname)){
				applyname =user.getName();//申请人名称
			}
		}
		
		//流程流转
		Map<String,Object> tempmap = new HashMap<String,Object>();
		
		tempmap.put("resid", resid);//和流程相关按钮ID
		tempmap.put("tempid", tempid);
		tempmap.put("flowid", flowid);//流程ID
		tempmap.put("conent", conent);//内容
		tempmap.put("orgid", orgid);//部门ID
		tempmap.put("applyid", applyid);//申请人ID
		tempmap.put("applyname", applyname);//申请人名称
		tempmap.put("docconent", docconent);//审批大字段
		tempmap.put("flowdefid", flowdefid);//流程ID
		tempmap.put("examineid", examineid);//审批指定人
		tempmap.put("operateType", operateType);
		tempmap.put("casetype", casetype);
		tempmap.put("courttype", courttype);
		tempmap.put("acceptdate", acceptdate);
		tempmap.put("liandate", liandate);
		tempmap.put("flowdraftid", flowdraftid);//流程草稿ID
		tempmap.put("commenttext", commenttext);//审批意见
//		if(StringNumberUtil.notEmpty(commutetype)){
//			tempmap.put("jxjs_1", commutetype);
//		}
		if(StringNumberUtil.notEmpty(curyear)&&StringNumberUtil.notEmpty(casenumber)){
			tempmap.put("casenum", curyear+casenumber);
		}
		
		result=uvFlowService.courtLiAn(data, user, tempmap);
		
		return result;
	}
	
	
//	/**
//	 * 法院批理立案
//	 * @param request
//	 * @param response
//	 * @return 成功返回0 失败返回-1
//	 */
//	@RequestMapping(value = "/courtBatchLiAn.action")
//	@ResponseBody
//	public Object courtBatchLiAn(HttpServletRequest request,HttpServletResponse response) {
//		
//		// 用户对象
//		SystemUser user = getLoginUser(request);
//		//定义变量
//		int resultval = 0;
//		//取得参数
//		String data = request.getParameter("data");//获取表单页面的数据
//		
//		String resid = request.getParameter("resid");//和流程相关按钮ID
//		String flowid = request.getParameter("flowid");//流程ID
//		String conent = request.getParameter("conent");//内容
//		String tempid = request.getParameter("tempid");//表单ID
//		String flowdefid = request.getParameter("flowdefid");//流程自定义ID
//		String docconent = request.getParameter("docconent");//审批大字段
//		String operateType = request.getParameter("operateType");//判断新增、修改
//		String flowdraftid = request.getParameter("flowdraftid");//流程草稿ID
//		String commenttext = request.getParameter("commenttext");//审批意见
//		String curyear = request.getParameter("curyear");
//		String acceptdate = request.getParameter("acceptdate");
//		String liandate = request.getParameter("liandate");
////		String commutetype = request.getParameter("commutetype");//案件类刑
//		String casetype = request.getParameter("casetype");
//		String courttype = request.getParameter("courttype");
//		String casenumber = request.getParameter("casenumber");
////		if(StringNumberUtil.notEmpty(commutetype)&&"0".equals(commutetype.trim())){
////			casetype = "减字";
////		}else if(StringNumberUtil.notEmpty(commutetype)&&"1".equals(commutetype.trim())){
////			casetype = "假字";
////		}
//		
//		String dataStr = request.getParameter("dataStr");
//		
//		Map<Object,Object> datamap = new HashMap<Object,Object>();
//		datamap.put("dataStr", dataStr);
//		datamap.put("data", data);
//		datamap.put("resid", resid);
//		datamap.put("flowid", flowid);
//		datamap.put("conent", conent);
//		datamap.put("tempid", tempid);
//		datamap.put("flowdefid", flowdefid);
//		datamap.put("docconent", docconent);
//		datamap.put("operateType", operateType);
//		datamap.put("flowdraftid", flowdraftid);
//		datamap.put("commenttext", commenttext);
//		datamap.put("curyear", curyear);
//		datamap.put("casetype", casetype);
//		datamap.put("courttype", courttype);
//		datamap.put("acceptdate", acceptdate);
//		datamap.put("liandate", liandate);
//		datamap.put("casenumber", casenumber);
////		if(StringNumberUtil.notEmpty(commutetype)){
////			datamap.put("commutetype", commutetype);
////		}
//		resultval = uvFlowService.batchCourtLiAnFlowApprove(user,datamap);
//		
//		return resultval;
//	}
	
	/**
	 * 法院查看监狱提请建议书
	 * liuyi
	 * 2014年12月5日 10:42:58
	 */
	@RequestMapping(value = "/courtToGetJailCommuteDocFormPage")
	public ModelAndView courtToGetJailCommuteDocFormPage(HttpServletRequest request,HttpServletResponse response) {
		String crimid = request.getParameter("crimid");
		String jailFlowdefid = request.getParameter("jailFlowdefid");
		String flowdraftid = request.getParameter("flowdraftid");
		String tempid = request.getParameter("tempid");
		request.setAttribute("crimid", crimid);
		request.setAttribute("jailFlowdefid", jailFlowdefid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("flowdraftid", flowdraftid);
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/court/suggestModelForFy.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	/**
	 * 查看法律文书文本
	 *//*
	@RequestMapping(value = "/checkFlwsText")
	public ModelAndView checkFlwsText(HttpServletRequest request,HttpServletResponse response) {
		String flowdraftid = request.getParameter("flowdraftid");
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		request.setAttribute("flowdraftid", flowdraftid);
		SystemUser user = getLoginUser(request);
		UvCourtBigData bg = uvCourtBigDataMapper.getCourtFlwsDoc(flowdraftid, tempid, user.getDepartid());
		if(bg!=null){
			String docconent = bg.getDocconent();
			if(docconent!=null){
				JSONObject js = new JSONObject();
				js.put("docconent", docconent);
				request.setAttribute("docconent", js.toString());
			}
		}
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/court/falvwenshu.jsp");
		mav = new ModelAndView(view);
		return mav;
	}*/
	
	/**
	 * 跳转刑期变动列表页
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toCourtSentenceChangeListPage.page")
	public ModelAndView toCourtSentenceChangeListPage(HttpServletRequest request) {
		returnResourceMap(request);
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String menuid=request.getParameter("menuid")==null?"":request.getParameter("menuid");
		String tempid=request.getParameter("tempid")==null?"":request.getParameter("tempid");
		
		String toolbar = request.getParameter("toolbar");
		request.setAttribute("crimid", crimid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid",tempid);
		if(StringNumberUtil.notEmpty(toolbar)){
			request.setAttribute("toolbar", toolbar);
		}
		ModelAndView mav =  new ModelAndView("court/courtCheckSentenceChangeListPage");
		return mav;
	}
	
	
	@RequestMapping(value = "/toHeYiOrKaiTingReportPage")
	public ModelAndView toHeYiOrKaiTingReportPage(HttpServletRequest request,HttpServletResponse response) {
		JSONArray docconent = new JSONArray();
		String crimid = request.getParameter("crimid");
		String tempid = request.getParameter("tempid");
		String flowdraftid = request.getParameter("flowdraftid");
		
		FlowBaseOther fbo = null;
		String otherid = null;
		if(StringNumberUtil.notEmpty(tempid)&&StringNumberUtil.notEmpty(flowdraftid)){
			request.setAttribute("tempid", tempid);
			request.setAttribute("flowdraftid", flowdraftid);
			Map paramMap = new HashMap();
			boolean flag = true;
			if("10160".equals(tempid)){//开庭笔录
				Map map = new HashMap();
				map.put("flowdraftid", flowdraftid);
				FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(map);
				String text8 = fb.getText8();
				if(text8!=null && "bukaiting".equals(text8)){
					request.setAttribute("info", "<span style='color:red'>此罪犯未开庭！</span>");
					flag = false;
				}
			}
			if(flag){
				paramMap.put("tempid", tempid);
				paramMap.put("flowdraftid", (flowdraftid));
	
				otherid = flowBaseOtherService.getLastOtheridByFlowdraftid(paramMap);
				tempid = tempid+"report@"+otherid;
				paramMap.put("tempid", tempid);
				String content =  flowBaseOtherService.getDocconentByFlowdraftid(paramMap);
				docconent.add(content);
				request.setAttribute("formcontent", docconent.toString());
			}
		}
		
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/court/courtJailDocForm.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	
	@RequestMapping(value = "/courtToGetSentenceDocPage")
	public ModelAndView courtToGetSentenceDocPage(HttpServletRequest request,HttpServletResponse response) {
		
		String crimid = request.getParameter("crimid");
		String modeledit = request.getParameter("modeledit");
		String id = request.getParameter("id");
		String menuid = request.getParameter("menuid");
		String flowdraftid = request.getParameter("flowdraftid");
		String flowid = request.getParameter("flowid");
		String flowdefid = request.getParameter("flowdefid");
		String doctype = request.getParameter("doctype");
		String tempid = request.getParameter("tempid");
		String resid1 = request.getParameter("resid1");
		String resid2 = request.getParameter("resid2");
		String waijiresid1 = request.getParameter("waijiresid1");//外籍犯的报表对应的资源id 14249_1
		String waijiresid2 = request.getParameter("waijiresid2");//14249_2
		String juedingshuresid1 = request.getParameter("juedingshuresid1");//决定书正本
		String juedingshuresid2 = request.getParameter("juedingshuresid2");//决定书稿
		
		//上一个下一个用
		String allid = request.getParameter("allid");
		String nextflag = request.getParameter("nextflag");
		String closetype = request.getParameter("closetype");
		String courttype = request.getParameter("courttype");
		String fathermenuid = request.getParameter("fathermenuid");
		request.setAttribute("fathermenuid", fathermenuid);
		request.setAttribute("courttype", courttype);
		request.setAttribute("closetype", closetype);
		if(StringNumberUtil.notEmpty(allid)){//缓存所有id
			request.setAttribute("allid", allid);
		}
		if(StringNumberUtil.notEmpty(nextflag)){//是上一个还是下一个
			request.setAttribute("nextflag", nextflag);
		}
		request.setAttribute("crimid", crimid);
		request.setAttribute("id", id);
		request.setAttribute("menuid", menuid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("modeledit", modeledit);
		request.setAttribute("doctype", doctype);
		if(StringNumberUtil.notEmpty(tempid)){
			request.setAttribute("tempid", tempid);
		}
		if(StringNumberUtil.notEmpty(waijiresid1) && StringNumberUtil.notEmpty(waijiresid2)){
			request.setAttribute("waijiresid1", waijiresid1);
			request.setAttribute("waijiresid2", waijiresid2);
		}
		if(StringNumberUtil.notEmpty(juedingshuresid1) && StringNumberUtil.notEmpty(juedingshuresid2)){
			request.setAttribute("juedingshuresid1", juedingshuresid1);
			request.setAttribute("juedingshuresid2", juedingshuresid2);
		}
		request.setAttribute("resid1", resid1);
		request.setAttribute("resid2", resid2);
		
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/court/courtSentenceDocPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 方法描述：列表页面
	 * 2014-8-11
	 */
	@RequestMapping(value = "/getJailListUnderCourt")
	@ResponseBody
	public Object getJailListUnderCourt(HttpServletRequest request,HttpServletResponse response){
		List<Map> data = new ArrayList<Map> ();
		try {
			SystemUser su = getLoginUser(request);
			String departid = su.getDepartid();
			Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("departid",departid);
	    	data= tbxfSentencealterationService.getJailListUnderCourt(map);
	    	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * 方法描述：法院获取提请减刑建议书
	 * 2014年12月5日 09:51:15
	 * liuyi
	 */
	@RequestMapping(value = "/getJianYiShuForFy")
	@ResponseBody
	public Object getJianYiShuForFy(HttpServletRequest request,HttpServletResponse response){
		String content =  "";
		Map map = new HashMap();
		try {
			String crimid = request.getParameter("crimid");
			String jailFlowdefid = request.getParameter("jailFlowdefid");
			String tempid = request.getParameter("tempid");
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("crimid", crimid);
			paramMap.put("tempid", tempid);
			paramMap.put("flowdefid", jailFlowdefid);
			content =  flowBaseOtherService.courtGetJailApproveDocByMap(paramMap);
			map.put("aaa", content);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 法院立案页面（收案登记表）
	 * @return
	 */
	@RequestMapping("/toShouAnDengJiBiaoPage")
	public ModelAndView toShouAnDengJiBiaoPage(HttpServletRequest request){
		String menuid=request.getParameter("menuid");
		String year=request.getParameter("caseyear");
		String anjiantype=request.getParameter("casetype");
		String shortname=request.getParameter("shortname");
		request.setAttribute("menuid",menuid);
		request.setAttribute("anjiantype", anjiantype);
		request.setAttribute("year", year);
		request.setAttribute("shortname", shortname);
		return new ModelAndView("court/shouAnDengJiBiao");
	}
	/**
	 * 返回reportdata
	 * @param request
	 * @return
	 */
	@RequestMapping("/getReportData")
	@ResponseBody
	public String getReportData(HttpServletRequest request){
		String data="";
		String anjianhao=request.getParameter("anjianhao");
		if(null!=anjianhao){
			SystemUser user = getLoginUser(request);
			RMEngine engine=systemResourceService.queryQualificationDataRmEngine("", user, request);
			if(null!=engine){
				data=engine.dedaoReportData();
			}
		}
		return data;
	}
	/**
	 * 法院统计案件分布
	 * @param request
	 * @return
	 */
	@RequestMapping("toCaseFenbuPage")
	public ModelAndView toCaseFenbuPage(HttpServletRequest request){
		returnResourceMap(request);
		SystemUser user = getLoginUser(request);
		RMEngine engine=systemResourceService.queryQualificationDataRmEngine("", user, request);
		request.setAttribute("mydata", engine.dedaoReportData());
		return new ModelAndView("court/courtcasefenbu");
	}
	
	
	/**
	 *保存法院流程其他表单
	 * @param request
	 * @return
	 */
	@RequestMapping("saveCourtFlowOther")
	@ResponseBody
	public Object saveCourtFlowOther(HttpServletRequest request){
		String flag = null;
		try{
			flag = this.flowBaseOtherService.saveCourtFlowOther(request);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 法院国籍/户籍选择页面
	 * @param request
	 * @return
	 */
	@RequestMapping("toJiGuanSelect")
	public ModelAndView toJiGuanSelect(HttpServletRequest request){
		return new ModelAndView("court/jiguanorguoji");
	}
	/**
	 * 获取国籍列表数据等
	 * @param request
	 * @return
	 */
	@RequestMapping("/getJiGuanShuJu")
	@ResponseBody
	public Object getJiGuanShuJu(HttpServletRequest request) {
		String codeType = request.getParameter("codeType");
		return systemCodeService.ajaxGetcodeValueForOpinion(codeType);
	}
	
	
	/**
	 * 获取国籍单个数据 取remark
	 * @param request
	 * @return
	 */
	@RequestMapping("/getJiGuanShuJuData")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getJiGuanShuJuData(HttpServletRequest request) {
		String codeType = request.getParameter("codeType");
		String codeId = request.getParameter("codeId");
		Map map = new HashMap();
		if(StringNumberUtil.notEmpty(codeType) && StringNumberUtil.notEmpty(codeId)){
			map.put("codetype", codeType);
			map.put("codeid", codeId);
		}
		return systemCodeService.selectValueByCodeTypeAndCodeid(map);
	}
	
	/**
	 * 开庭信息设置页面
	 * @param request
	 * @return
	 *//*
	@RequestMapping("/toKaiTingBiLuSettingPage")
	public ModelAndView toKaiTingBiLuSettingPage(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		String userid = user.getUserid();
		Map map = tbCourtFullCourtMapper.getKaiTingBiLuSettingInfo(userid);
		if(map!=null){
			request.setAttribute("kaitingshijian", map.get("KAITINGSHIJIAN")==null?"":map.get("KAITINGSHIJIAN").toString());
			request.setAttribute("jcyname1",map.get("JCYNAME1")==null?"":map.get("JCYNAME1").toString());
			request.setAttribute("jcyname2", map.get("JCYNAME2")==null?"":map.get("JCYNAME2").toString());
			request.setAttribute("sjname1", map.get("SJNAME1")==null?"":map.get("SJNAME1").toString());
			request.setAttribute("sjname2", map.get("SJNAME2")==null?"":map.get("SJNAME2").toString());
			request.setAttribute("userid", map.get("USERID")==null?"":map.get("USERID").toString());
		}else{
			request.setAttribute("kaitingshijian", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		}
		return new ModelAndView("court/kaiTingBiLuSetting");
	}*/
	
	/**
	 * 保存开庭信息
	 * @param request
	 * @return
	 *//*
	@RequestMapping("/setKaiTingBiLuInfo")
	@ResponseBody
	public Object setKaiTingBiLuInfo(HttpServletRequest request) {
		Map map = new HashMap();
		String kaitingshijian = request.getParameter("kaitingshijian");
		String jcyname1 = request.getParameter("jcyname1");
		String jcyname2 = request.getParameter("jcyname2");
		String sjname1 = request.getParameter("sjname1");
		String sjname2 = request.getParameter("sjname2");
		String userid = request.getParameter("userid");
		if(StringNumberUtil.notEmpty(kaitingshijian)){
			map.put("kaitingshijian", kaitingshijian);
		}
		if(StringNumberUtil.notEmpty(jcyname1)){
			map.put("jcyname1", jcyname1);
		}
		if(StringNumberUtil.notEmpty(jcyname2)){
			map.put("jcyname2", jcyname2);
		}
		if(StringNumberUtil.notEmpty(sjname1)){
			map.put("sjname1", sjname1);
		}
		if(StringNumberUtil.notEmpty(sjname2)){
			map.put("sjname2", sjname2);
		}
		if(StringNumberUtil.notEmpty(userid)){//用户id不为空则更新否则插入
			map.put("userid", userid);
			tbCourtFullCourtMapper.updateKaiTingBiLuInfo(map);
		}else{
			SystemUser user = getLoginUser(request);
			String nuserid = user.getUserid();
			map.put("userid", nuserid);
			tbCourtFullCourtMapper.setKaiTingBiLuInfo(map);
		}
		return "";
	}*/
	
	//办案
	@RequestMapping(value = "/getCourtCaseBigDataContent")
	@ResponseBody
	@SuppressWarnings("all")
	public String getCourtCaseBigDataContent(HttpServletRequest request){
		
		String tempid = request.getParameter("tempid");
		String flowdraftid = request.getParameter("flowdraftid");
		String int2 = request.getParameter("int2");
		
		Map paramMap = new HashMap();
		paramMap.put("flowdraftid", flowdraftid);
		paramMap.put("tempid", tempid);
		request.setAttribute("tempid", tempid);
		String content =  flowBaseOtherService.getDocconentByConditionTwo(paramMap);
		if(StringNumberUtil.notEmpty(int2)){
			paramMap.put("int2", int2);
			flowBaseOtherService.updateSignatureProcess(paramMap);
		}
		return content;
	}
	/**
	 * 批量结案
	 * @param request
	 *//*
	@RequestMapping(value = "piLiangJieAnForCourtCase")
	@ResponseBody
	public void piLiangJieAnForCourtCase(HttpServletRequest request) {
		courtApprovalService.piLiangJieAnForCourtCase(request);
	}*/
	
	/**
	 * 方法描述：获取所有的省份
	 * @author:mushuhong
	 * @version 2016年1月17日16:40:51
	 */
	@RequestMapping(value="getAllSFSelect")
	@ResponseBody
	public Object getAllSFSelect(HttpServletRequest request){
		List<Map> listMaps = systemCodeService.getAllSFSelectImpl(request,getLoginUser(request));
		
		return listMaps;
	}
}

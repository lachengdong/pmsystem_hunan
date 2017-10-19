package com.sinog2c.mvc.controller.outexecute;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.flow.FlowBaseOtherMapper;
import com.sinog2c.dao.api.flow.FlowOtherFlowMapper;
import com.sinog2c.dao.api.flow.UvFlowMapper;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.flow.FlowOtherFlow;
import com.sinog2c.model.flow.UvFlow;
import com.sinog2c.model.prisoner.TbprisonerSocialRelation;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 保外就医
 *
 */
@Controller
public class OutCaseController extends ControllerBase {
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private SystemTemplateService systemTemplateService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Resource
	private UvFlowService uvFlowService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private FlowBaseOtherMapper flowBaseOtherMapper;
	@Autowired
	private FlowOtherFlowMapper flowOtherFlowMapper;
	@Autowired 
	private UvFlowMapper uvFlowMapper;
	
	/**
	 * 监狱初保立案
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/outPrisonLiAnCaseListPage")
	public ModelAndView outPrisonLiAnCaseListPage(HttpServletRequest request){
		SystemUser su = getLoginUser(request);
		String departid = su.getDepartid();
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		String status = request.getParameter("status");
		Date date = new Date();
		String curyear = DateUtil.dateFormat(date, "yyyy");
		
		Map<String, String> temMap2 = new HashMap<String, String>();
		temMap2.put("departid", departid);
		temMap2.put("flowdefid", flowdefid);
		temMap2.put("curyear", curyear);
		String casenumber = flowBaseService.getMaxBaowaiCaseNum(temMap2);//案件号
		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jyconfig.getProperty(GkzxCommon.PROVINCECODE);
		
		SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
		String shortname = so.getShortname();
		request.setAttribute("casenumber", casenumber);
		request.setAttribute("curyear", curyear);
		request.setAttribute("shortname", shortname);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("status", status);
		request.setAttribute("provincecode", provincecode);
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/outexecute/outPrisonLiAnCaseListPage.jsp"));
	}
	
	/**
	 * 省局保外入监lyg
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/outPrisonLiAnCaseListPage123")
	public ModelAndView outPrisonLiAnCaseListPage123(HttpServletRequest request){
		SystemUser su = getLoginUser(request);
		String departid = su.getDepartid();
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		String status = request.getParameter("status");
		Date date = new Date();
		String curyear = DateUtil.dateFormat(date, "yyyy");
		
		Map<String, String> temMap2 = new HashMap<String, String>();
		temMap2.put("departid", departid);
		temMap2.put("flowdefid", flowdefid);
		temMap2.put("curyear", curyear);
		String casenumber = flowBaseService.getMaxBaowaiCaseNum(temMap2);//案件号
		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jyconfig.getProperty(GkzxCommon.PROVINCECODE);
		
		SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
		String shortname = so.getShortname();
		request.setAttribute("casenumber", casenumber);
		request.setAttribute("curyear", curyear);
		request.setAttribute("shortname", shortname);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("status", status);
		request.setAttribute("provincecode", provincecode);
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/outexecute/outPrisonLiAnCaseListPage123.jsp"));
	}
	
	
	
	
	
	/**
	 * 监区经办
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toBaoWaiJianBanListPage")
	public ModelAndView toBaoWaiJianBanListPage(HttpServletRequest request) {
		returnResourceMap(request);
		SystemUser su = getLoginUser(request);
		String shortname = su.getPrisonShortName();
		request.setAttribute("shortname", shortname);
		Date date = new Date();
		String year = DateUtil.dateFormat(date, "yyyy");
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		request.setAttribute("year", year);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		View view = new InternalResourceView("WEB-INF/JSP/outexecute/baoWaiJianBanListPage.jsp");
		ModelAndView mav = new ModelAndView(view);
		return mav;
	}
	
	
	@RequestMapping(value = "/toOutPrisonOfJailCaseTabs")
	public ModelAndView toOutPrisonOfJailCaseTabs(HttpServletRequest request) {
		//crimid+"@"+orgid+"@"+flowdraftid+"@"+flowid+"@"+findid;
		String crimid = "";
		String orgid = "";
		String flowdraftid = "";
		String flowid = "";
		String lastnodeid = "";
		String tabtype="";
		String fathermenuid = request.getParameter("fathermenuid");
		String flowdefid = request.getParameter("flowdefid");
		String tempid=  request.getParameter("tempid");
		String menuid = request.getParameter("menuid");
		String closetype = request.getParameter("closetype");
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		
		//宁夏进入不同的病残鉴定表
		String ningxia = (jyconfig.getProperty(GkzxCommon.NINGXIA)== null?"":jyconfig.getProperty(GkzxCommon.NINGXIA));
		if (ningxia.contains(departid)) {
			request.setAttribute("ningxia", 1);
		}else {
			request.setAttribute("ningxia", 0);
		}
		
		String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
		if(StringNumberUtil.notEmpty(id)){//如果罪犯编号等信息不为空就表示批量处理
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			String temps = ids[0];
			String[] temArr = temps.split("@");
			crimid = temArr[0];//罪犯编号就从数组里取出第一个罪犯编号
			orgid = temArr[1];
			flowdraftid = temArr[2];
			flowid = temArr[3];
			if(!StringNumberUtil.notEmpty(flowid)||"flowidnull".equals(flowid.trim())||"undefined".equals(flowid.trim())){
				flowid = "";
			}
			
			lastnodeid = temArr[4];
		}
		//获取省份代码——陕西用
		String provincecode = jyconfig.getProperty(GkzxCommon.PROVINCECODE);
		if(provincecode.equals("6100")){
			tabtype="shanxi";
			request.setAttribute("tabtype", tabtype);
			request.setAttribute("sjtempid", "SX_SJBWJYSPB");
		}
		//根据部门id查询部门级别
		SystemOrganization orgin =systemOrganizationService.getByOrganizationId(user.getOrganization().getOrgid());//当前登陆人
		String unitLevel=orgin.getUnitlevel();
		
		ModelAndView mav = null;
		request.setAttribute("provincecode", provincecode);
		request.setAttribute("unitLevel", unitLevel);
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("fathermenuid", fathermenuid);
		request.setAttribute("orgid", orgid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("lastnodeid", lastnodeid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("closetype", closetype);
		request.setAttribute("flowdefid", flowdefid);
		
		View view = new InternalResourceView("WEB-INF/JSP/aip/outPrisonOfJailCaseTabs.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	//保外办案
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/outPrisonOfJailCase")
	public ModelAndView outPrisonOfJailCase(HttpServletRequest request){
		JSONArray docconent = new JSONArray();
		Map<String,Object> map = new HashMap<String,Object>();
		returnResourceMap(request);//根据资源父ID查询子资源对应的权限
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		String city=user.getOrganization().getCity();
		
		String crimid = "";
		String orgid = "";
		String flowdraftid = "";
		String flowid = "";
		String lastnodeid = "";
		String fathermenuid = request.getParameter("fathermenuid");
		String tempid = request.getParameter("tempid");
		String menuid = request.getParameter("menuid");
		String closetype = request.getParameter("closetype");
		String flowdefid = request.getParameter("flowdefid");
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		String ningxia = jyconfig.getProperty(GkzxCommon.NINGXIA)==null?"":jyconfig.getProperty(GkzxCommon.NINGXIA);
		//判断使用流程的表单
		String signcheckbiaodan = jyconfig.getProperty(GkzxCommon.SIGNCHECKBIAODAN)==null?"":jyconfig.getProperty(GkzxCommon.SIGNCHECKBIAODAN);
		request.setAttribute("signcheckbiaodan", signcheckbiaodan);
		if (ningxia.contains(departid)) {
			request.setAttribute("shanxi", 1);
		}else if(provincecode.contains("6100")){
			request.setAttribute("shanxi", 2);
		}else{
			request.setAttribute("shanxi", 0);//登陆部门不是宁夏时
		}
		String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
		if(StringNumberUtil.notEmpty(id)){//如果罪犯编号等信息不为空就表示批量处理
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			String temps = ids[0];
			String[] temArr = temps.split("@");
			crimid = temArr[0];//罪犯编号就从数组里取出第一个罪犯编号
			orgid = temArr[1];
			flowdraftid = temArr[2];
			flowid = temArr[3];
			if(!StringNumberUtil.notEmpty(flowid)||"flowidnull".equals(flowid.trim())){
				flowid = "";
			}
			
			lastnodeid = temArr[4];
		}
		
		if(!StringNumberUtil.notEmpty(flowid)){
			Map<String, String> temPara = new HashMap<String, String>();
			temPara.put("flowdraftid", flowdraftid);
			flowid = uvFlowService.getFlowidByFlowdraftid(temPara);
		}
		
		//将案件加锁
		String islocked = GkzxCommon.ZERO;//加锁状态 0解锁
		FlowBase flowBase = flowBaseService.findById(flowdraftid);
		if(flowBase!=null) {
			if(!StringNumberUtil.notEmpty(flowBase.getIslocked()))	islocked = String.valueOf(flowBase.getIslocked());
			if(!GkzxCommon.ONE.equals(islocked))	flowBaseService.updateById(islocked,flowdraftid,user.getUserid(),user.getName());
		}
		
		request.setAttribute("fathermenuid", fathermenuid);
		request.setAttribute("applyid", crimid);
		request.setAttribute("orgid", orgid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("lastnodeid", lastnodeid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("closetype", closetype);
		request.setAttribute("flowdefid", flowdefid);
		
		String applyname = tbxfSentencealterationService.getCrimiNameByCrimid(crimid);
		request.setAttribute("applyname", applyname);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("flowdraftid", flowdraftid);
		paramMap.put("tempid", tempid);
		
		String content =  flowBaseOtherService.getDocconentByConditionTwo(paramMap);
		
		if(StringNumberUtil.notEmpty(content)){
			docconent.add(content);
		}else{
			
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
			}
			Map outPrisonDataMap = new HashMap();
			//宁夏 监外执行审批表 页面显示内容分离(mushuhong)
			if (ningxia.contains(departid)) {
				outPrisonDataMap=prisonerService.getCriminalInfoByCrimid(crimid);
			}else {
				outPrisonDataMap=prisonerService.getJailOutPrisonFormData(crimid);
			}
			String fzss="";
			if(outPrisonDataMap.size()>0){
				fzss=(String)outPrisonDataMap.get("zyfzss");
				fzss=fzss.replaceAll("rn", "\\\\r\\\\n");
				outPrisonDataMap.put("fanzuishishi", fzss);
				outPrisonDataMap.put("text13", outPrisonDataMap.get("formtitle"));
				outPrisonDataMap.put("panjuejiguan",outPrisonDataMap.get("cjicourtname"));
				outPrisonDataMap.put("prisonterm", outPrisonDataMap.get("prisonterm"));
				outPrisonDataMap.put("xianxingqiqizhi", outPrisonDataMap.get("nowxingqiqizhi"));
				
			}
			map.putAll(outPrisonDataMap);
			//天津的
			if(provincecode!=null&&provincecode!=""&&provincecode.equals("1200")){
				Map<String,Object> bMap  =  new HashMap<String,Object>();
				bMap.put("flowdefid", "other_zfbwjyjdsq");
				bMap.put("crimid", crimid);
				bMap.put("nodeid", "N0003");
				String tempvalue=prisonerService.getbsjdyjByCrimid(bMap);
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("bsjdyj", tempvalue==null?"":tempvalue.replaceAll("\r\n", ""));
				map.putAll(map1);
			}else{
				//查询出病残鉴定、妊娠、不能自理的鉴定结果。放入到审核表中
			    Map map2 = prisonerService.getBingChanJianDingContent(crimid,flowdefid);
			    map.putAll(map2);
				map.put("crimid", crimid);
			}
			
			String gaizaobiaoxian="";
			// gaizaobiaoxian = systemTemplateService.getSystemTemplateByCondition("9993",null).getContent();
			String querySql = "select t.crimid,F_FORMATDATE(t.REWARDSTART) 考核起日,F_FORMATDATE(t.REWARDEND) 考核止日," +
								"(case when t.REWARDSTART is not null and t.REWARDEND is not null then '考核期间：'||" +
								"F_FORMATDATE(t.REWARDSTART)||'至'||F_FORMATDATE(t.REWARDEND) else null end) as 考核期间," +
								"nvl(t.REWARDINFO, '') 奖励情况,nvl(t.PUNISHINFO, '') 惩罚情况 " +
								"from TBXF_SENTENCEALTERATION t where t.crimid = '"+crimid+"'";
			Map contMap = tbxfSentencealterationService.selectTbxfMapBySql(querySql);
			gaizaobiaoxian = MapUtil.replaceBracketContent(gaizaobiaoxian, contMap);
			gaizaobiaoxian = MapUtil.formatFormString(gaizaobiaoxian);
			map.put("gaizaobiaoxian", gaizaobiaoxian);
			// 家庭成员、主要社会关系
			List<TbprisonerSocialRelation> relationList = prisonerService.findRelationBycrimid(crimid);
			StringBuffer value = new StringBuffer();
			if (!relationList.isEmpty() && relationList != null) {
				for (int i = 0; i < relationList.size(); i++) {
					TbprisonerSocialRelation tsr = relationList.get(i);
					if(StringNumberUtil.notEmpty(tsr.getName())){
						value.append("姓名："+tsr.getName());
					}else{
						value.append("姓名： ");
					}
					if(StringNumberUtil.notEmpty(tsr.getRelationship())){
						value.append("，关系："+tsr.getRelationship());
					}else{
						value.append("，关系：不详");
					}
					if(StringNumberUtil.notEmpty(tsr.getVocation())){
						value.append("，职业："+tsr.getVocation());
					}
					if(StringNumberUtil.notEmpty(tsr.getPolitical())){
						value.append("，政治面貌："+tsr.getPolitical());
						value.append("，家庭住址："+tsr.getHomeaddress());
					}
					if(i < relationList.size()-1){
						value.append(";\\r\\n");
					}
				}
				if(value.length()>0){
					value = value.append("。");
				}
			}
			Map<String, String> tempmap=new HashMap<String, String>();
			tempmap.put("crimid", crimid);
			tempmap.put("tempid", "ZFBCJDB");
			String zhenduaninfo=prisonerService.getZhendInfo(tempmap);
			map.put("yiyuanjiandingyijian", zhenduaninfo);
			
			String jailname = systemOrganizationService.getOrginfoByOrgid(departid).getName();
			
			map.put("jianyu", jailname);
			map.put("jtzycyxm", value);
			String applyvalue = crimid+","+outPrisonDataMap.get("name");
			request.setAttribute("applyvalue",applyvalue);
		
			UvFlow uvflow=uvFlowMapper.getuvflowByMap(paramMap);
			if(StringNumberUtil.notEmpty(uvflow)){
				map.put("text10",uvflow.getConent());
				map.put("text11", uvflow.getOpname());
				map.put("text12", uvflow.getEndsummry());
			}
			//map.put("text21", city);
		}
		map.put("text26", city);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("flowdefid", flowdefid);
		View view = new InternalResourceView("WEB-INF/JSP/aip/outPrisonOfJailCase.jsp");
		ModelAndView mav = new ModelAndView(view);
		return mav;
	}
	
	/*
	 * 保外就医获取病情鉴定的相关表单
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/identificationOfDisease")
	public ModelAndView identificationOfDisease(HttpServletRequest request) {
		JSONArray docconent = new JSONArray();
		String crimid = request.getParameter("crimid");//罪犯编号
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("crimid", crimid);
		map =  flowBaseOtherService.identificationOfDisease(map);
		if(map != null){
			docconent.add(map.get("docconent"));
		}else{
			request.setAttribute("nocontent", GkzxCommon.NOCONTENT);
		}
		request.setAttribute("formcontent", docconent.toString());
		View view = new InternalResourceView("WEB-INF/JSP/aip/outPrisonOfJailCase.jsp");
		return new ModelAndView(view);
	}
	/**
	 * 省局保外归档时，要把监狱审批签章并保存，然后归档，回复给监狱(陕西)
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/sjSaveJYForm")
	public ModelAndView sjSaveJYForm(HttpServletRequest request){
		JSONArray docconent = new JSONArray();
		String crimid = request.getParameter("crimid");
		String flowdefid = request.getParameter("flowdefid");
		String flowdraftid = request.getParameter("flowdraftid");
		String tempid = request.getParameter("tempid");
		String fathermenuid = request.getParameter("fathermenuid");
		String menuid = request.getParameter("menuid");
		Map map = new HashMap();
		map.put("crimid", crimid);
		map.put("docid", "103");
		
		String jzsuggest = uvFlowMapper.getSuggestJZ(crimid);
		Map tempmap = new HashMap();
		tempmap.put("sjyj1", jzsuggest);
		request.setAttribute("formDatajson", new JSONObject(tempmap).toString());
		String doccoment = "";
		String content =  flowBaseOtherService.getDocconent(map);
		docconent.add(content);
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("fathermenuid", fathermenuid);
		request.setAttribute("temptype", "sj");
		request.setAttribute("menuid", menuid);
		View view =null;
		view = new InternalResourceView("WEB-INF/JSP/aip/outPrisonOfJailCase.jsp");
		return new ModelAndView(view);
	}
	
	
	/**
	 * 初保案件办理
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toBaoWaiOfJailCaseListPage")
	public ModelAndView toBaoWaiCaseListPage(HttpServletRequest request) {
		SystemUser su = getLoginUser(request);
		String shortname = su.getPrisonShortName();
		//String casetype = "初保字";
		//request.setAttribute("casetype", casetype);
		request.setAttribute("shortname", shortname);
		
		ModelAndView mav = null;
		returnResourceMap(request);
		Date date = new Date();
		String year = DateUtil.dateFormat(date, "yyyy");
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
//		String temptype=request.getParameter("temptype");
//		if(StringNumberUtil.notEmpty(temptype)&&"sj".equals(temptype)){
//			tempid="JWZX_ZYJWZXSPB";
//		}
		request.setAttribute("year", year);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		View view = new InternalResourceView("WEB-INF/JSP/outexecute/baoWaiOfJailCaseListPage.jsp"); 
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 方法描述：获取续保科室办理列表数据
	 * 2014-8-7
	 */
	@RequestMapping(value = "/getBaoWaiJianBanRenList")
	@ResponseBody
	public Object getBaoWaiJianBanRenList(HttpServletRequest request){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map> data = new ArrayList<Map> ();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			SystemUser su = getLoginUser(request);
			String orgid = su.getOrgid();
			String departid = su.getDepartid();
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			String jianqu = request.getParameter("jianqu");
			String xing = request.getParameter("xing");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			String casenums = request.getParameter("casenums");
			String flowdefid = request.getParameter("flowdefid");
			String year = request.getParameter("year");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("suid", su.getUserid());
			map.put("orgid", orgid);
			map.put("departid", departid);
			map.put("flowdefid", flowdefid);
			map.put("key", key);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	
	    	map.put("jianqu", jianqu);
	    	map.put("xingqileixing", xing);
	    	map.put("year", year);
	    	if(StringNumberUtil.notEmpty(casenums)&&StringNumberUtil.notEmpty(year)){
	    		casenums = StringNumberUtil.formatCaseNo(casenums,year);
		    	map.put("casenums", casenums);
	    	}

	    	int count = uvFlowService.countBaoWaiJianBanRenList(map);
	    	data= uvFlowService.getBaoWaiJianBanRenList(map);
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	/**
	 * 方法描述：列表页面
	 * 2014-8-7
	 */
	@RequestMapping(value = "/getBaoWaiJailCaseList")
	@ResponseBody
	public Object getBaoWaiJailCaseList(HttpServletRequest request){
		
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map> data = new ArrayList<Map> ();
		try {
			String flowdefid = request.getParameter("flowdefid");
			String key = request.getParameter("key");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));   
			SystemUser user = getLoginUser(request);
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			String jianqu = request.getParameter("jianqu");
			String xingqileixing = request.getParameter("xingqileixing");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			String casenums = request.getParameter("casenums");
			String year = request.getParameter("year");
			
			Map<String,Object> map = new HashMap<String,Object>();
			if(StringNumberUtil.notEmpty(key)){
				map.put("key", key);
			}
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("flowdefid",flowdefid);
	    	map.put("jianqu",jianqu);
	    	map.put("xingqileixing",xingqileixing);
	    	map.put("year", year);
	    	
			if(StringNumberUtil.notEmpty(casenums)&&StringNumberUtil.notEmpty(year)){
	    		casenums = StringNumberUtil.formatCaseNo(casenums,year);
		    	map.put("casenums", casenums);
	    	}
			
			Object buttonstr = "";
			if(user!=null){
				map.put("orgid", user.getOrgid());
				map.put("departid", user.getDepartid());
				map.put("suid", user.getUserid());
				//获取该用户拥有的按钮资源id
		    	buttonstr = this.returnButtonResourceByUser(user,null,null);
		    	map.put("connsql", buttonstr);
	    	}
			
	    	int count = uvFlowService.countBaoWaiJailCaseList(map);
	    	data= uvFlowService.getBaoWaiJailCaseList(map);
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	
	@RequestMapping(value = "/getFlowBaseDocByMap")
	public Object getFlowBaseDocByMap(HttpServletRequest request){
		JSONArray docconent = new JSONArray();
		String crimid = request.getParameter("crimid");//罪犯编号
		String tempid = request.getParameter("tempid");//模板编号
		String flowdefid = request.getParameter("flowdefid");//流程自定义id
		//三种可能
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("crimid", crimid);
		map.put("tempid", tempid);
		map.put("flowdefid", flowdefid);
		Map mapDoc = tbsysDocumentService.getFlowBaseDocByMap(map);
		if(mapDoc != null){
			docconent.add(mapDoc.get("DOCCONENT"));
		}else{
			request.setAttribute("nocontent", GkzxCommon.NOCONTENT);
		}
		request.setAttribute("formcontent", docconent.toString());
		View view = new InternalResourceView("WEB-INF/JSP/aip/addbqjd.jsp");
		ModelAndView mav = null;
		mav = new ModelAndView(view);
		return mav;
	}
	/**
	 * 监狱操作保外审批表tab页，保存省局表单
	 * @return
	 */
	@RequestMapping("/saveSJBWJYSPB")
	@ResponseBody
	public String saveSJBWJYSPB(HttpServletRequest request){
		SystemUser user=getLoginUser(request);
		String flowdraftid=request.getParameter("flowdraftid");
		String tempid=request.getParameter("tempid");
		String data=request.getParameter("datafile");
		Map valuemap=new HashMap();
		int i=0;
		valuemap.put("flowdraftid", flowdraftid);
		valuemap.put("tempid", tempid);
		FlowBaseOther flowbaseother=flowBaseOtherMapper.findBaseOtherBymap(valuemap);
		FlowOtherFlow flowOtherFlow;
		if(StringNumberUtil.isEmpty(flowbaseother)){
			String id = flowBaseOtherMapper.getOtherId(user.getDepartid());
			FlowBaseOther fbo = new FlowBaseOther();
			flowOtherFlow= new FlowOtherFlow();
			fbo.setOtherid(id);
			fbo.setDocconent(data);
			fbo.setSn(0);
			fbo.setOpid(user.getUserid());
			fbo.setOptime(new Date());
			flowBaseOtherMapper.saveMeetingContentBigText(fbo);
			flowOtherFlow.setOtherid(id);
			flowOtherFlow.setTempid(tempid);
			flowOtherFlow.setFlowdraftid(flowdraftid);
			flowOtherFlow.setOpid(user.getUserid());
			flowOtherFlow.setOptime(new Date());
			flowOtherFlowMapper.insert(flowOtherFlow);
		}else{
			Map map=new HashMap();
			map.put("flowdraftid", flowdraftid);
			map.put("docconent", data);
			map.put("tempid", tempid);
			map.put("otherid", flowbaseother.getOtherid());
			
			i=flowBaseOtherMapper.updateByOtherid(map);
		}
		
		return i+"";
	}
		
}

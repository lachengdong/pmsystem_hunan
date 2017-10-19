package com.sinog2c.mvc.controller.outexecute;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
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
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.flow.FlowArchives;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowArchivesService;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Controller
public class BaowaiMinutesController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private UvFlowService uvFlowService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private SystemTemplateService systemTemplateService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private FlowArchivesService flowarchivesservice;
	/**
	 * 省局监外执行处事经办
	 */
	@RequestMapping(value="toOutPrisonProvinceChuShiListPage")
	public ModelAndView toOutPrisonProvinceChuShiListPage(HttpServletRequest request){
		
		returnResourceMap(request);
		SystemUser su = getLoginUser(request);
		String orgid = su.getOrgid();
		String departid = su.getDepartid();
		SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
		String shortname = so.getShortname();
		String casetype = "保字";
		request.setAttribute("casetype", casetype);
		request.setAttribute("shortname", shortname);
		
		Date date = new Date();
		String year = DateUtil.dateFormat(date, "yyyy");
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		request.setAttribute("year", year);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		View view = new InternalResourceView("WEB-INF/JSP/outexecute/outPrisonProvinceChuShiListPage.jsp");
		ModelAndView mav = new ModelAndView(view);
		return mav;
		
	}
	
	
	@RequestMapping(value = "/toProvinceOutPrisonTabs")
	public ModelAndView toProvinceOutPrisonTabs(HttpServletRequest request) {
		String crimid = "";
		String orgid = "";
		String flowdraftid = "";
		String flowid = "";
		String lastnodeid = "";
		String fathermenuid = request.getParameter("fathermenuid");
		String tempid=  request.getParameter("tempid");
		String menuid = request.getParameter("menuid");
		String closetype = request.getParameter("closetype");
		String flowdefid = request.getParameter("flowdefid");
		
		//获取和河北 监狱单位code值
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String hebei = jyconfig.getProperty(GkzxCommon.HEBEI_CODE);
		if (hebei.contains(getLoginUser(request).getDepartid())) {
			request.setAttribute("hebei", 1);
		}else{
			request.setAttribute("hebei", 0);
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
			lastnodeid = temArr[4];
		}
		
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
		
		View view = new InternalResourceView("WEB-INF/JSP/outexecute/provinceOutPrisonTabs.jsp");
		ModelAndView mav = new ModelAndView(view);
		return mav;
	}
	
	
	//省局办案
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/provinceOutPrisonCase")
	public ModelAndView provinceOutPrisonCase(HttpServletRequest request){
		
		JSONArray docconent = new JSONArray();
		Map<String,Object> map = new HashMap<String,Object>();
		returnResourceMap(request);
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		
		String crimid = "";
		String orgid = "";
		String flowdraftid = "";
		String flowid = "";
		String lastnodeid = "";
		String fathermenuid = request.getParameter("fathermenuid");
		String tempid = request.getParameter("tempid");
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		String menuid = request.getParameter("menuid");
		String closetype = request.getParameter("closetype");
		String flowdefid = request.getParameter("flowdefid");
		
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
			Map temPara = new HashMap();
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
		
		Map paramMap = new HashMap();
		paramMap.put("flowdefid", flowdefid);
		paramMap.put("flowdraftid", flowdraftid);
		paramMap.put("tempid", tempid);
		String content ="";
		content =  flowBaseOtherService.getDocconentByConditionTwo(paramMap);
		//天津省局初保单子和监狱相同
		if("1200".equals(provincecode)||"4400".equals(provincecode)){
			if(StringNumberUtil.isEmpty(content)){
				Map bMap = new HashMap();
				bMap.put("flowdefid", "other_jybwjycbsp");
				bMap.put("tempid", tempid);
				bMap.put("crimid", crimid);
				bMap.put("nodeid", "N0005");
				bMap.put("state", "1");
				content =  flowBaseOtherService.getDocconent4other(bMap);
			}
		}
		//陕西表单取监狱归档后电子档表表单
		if("6100".equals(provincecode)&&(null==content||"".equals(content))){
			paramMap.put("crimid", crimid);
			paramMap.put("docid", "114");//省局保外审批表
			FlowArchives flowarchives=flowarchivesservice.getArchivesData(paramMap);
			if(StringNumberUtil.notEmpty(flowarchives)){
				content=flowarchives.getDocconent();
			}
		}
		if(StringNumberUtil.notEmpty(content)){
			docconent.add(content);
		}else{
			map = prisonerService.getProvinceOutPrisonFormData(crimid);	
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
				map.put("titlename", template.getTempname());//表单名称
			}
			map.put("crimid", crimid);
			String orgname = systemOrganizationService.getByOrganizationId(departid).getName();
			map.put("orgname", orgname);
			String querySql = "	select t.crimid,to_char(t.REWARDSTART,'yyyy\"年\"mm\"月\"dd\"日\"') 考核起日, "
				+ "	       to_char(t.REWARDEND,'yyyy\"年\"mm\"月\"dd\"日\"') 考核止日,	 "
				+ "	       (case"
				+ "	         when t.REWARDSTART is not null and t.REWARDEND is not null then case when t.predate is not null then "
				+ "	          '考核期间：' || F_FORMATDATE(trunc(add_months(t.REWARDSTART,0),'mm')) || '至' ||"
				+ "	          F_FORMATDATE(last_day(add_months(t.REWARDEND,-1)))"
				+ "	          else "
				+ "	                  '考核期间：' ||F_FORMATDATE(t.REWARDSTART) || '至' ||	"
				+ "	          F_FORMATDATE(last_day(add_months(t.REWARDEND,-1)))"
				+ "	          end  "
				+ "	         else "
				+ "	          null	"
				+ "	       end) as 考核期间,	"
				+ "	       replace(nvl(t.REWARDINFO, ''),'，','\r\n') 奖励情况,"
				+ "	       nvl(t.PUNISHINFO, '') 惩罚情况 "
				+ "	  from TBXF_SENTENCEALTERATION t "
				+ "	 where t.crimid = '" + crimid + "'";
			String gaizaobiaoxian = systemTemplateService.getSystemTemplateByCondition("9993",null).getContent();
			Map contMap = tbxfSentencealterationService.selectTbxfMapBySql(querySql);
			gaizaobiaoxian = MapUtil.replaceBracketContent(gaizaobiaoxian, contMap);
			gaizaobiaoxian = MapUtil.formatFormString(gaizaobiaoxian);
			map.put("gaizaobiaoxian", gaizaobiaoxian);
			Map<String,Object> bMap = new HashMap<String,Object>();
			bMap.put("crimid", crimid);
			bMap.put("flowdefid", "other_jybwjycbsp");
			bMap.put("nodeid", "N0005");
			String jyyj = prisonerService.getbsjdyjByCrimid(bMap);
			map.put("sjdanweiyijian", jyyj);
			
			String applyvalue = crimid+","+map.get("name");
			request.setAttribute("applyvalue",applyvalue);
			
		}
		
		//表单下来列表的 存放容器
		Map selectmap = new HashMap();
		map.put("xinbanli", GkzxCommon.XINBANLI);//默认值
		selectmap.put("xinbanli", GkzxCommon.XINBANLIS);//下拉列表
		request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());

		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("flowdefid", flowdefid);
		
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/aip/provinceOutPrisonPage.jsp");
//		if("1200".equals(provincecode)){
//			 view = new InternalResourceView("WEB-INF/JSP/aip/provinceOutPrisonPageTJ.jsp");
//			
//		}
	

		mav = new ModelAndView(view);
		return mav;
		
	}


	/**
	 * 省局监外执行案件办理
	 */
	@RequestMapping(value="toOutPrisonProvinceCaseListPage")
	public ModelAndView toOutPrisonProvinceCaseListPage(HttpServletRequest request){
		
		returnResourceMap(request);
		SystemUser su = getLoginUser(request);
		String orgid = su.getOrgid();
		String departid = su.getDepartid();
		SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
		String shortname = so.getShortname();
		String casetype = "保字";
		request.setAttribute("casetype", casetype);
		request.setAttribute("shortname", shortname);
		
		ModelAndView mav = null;
		Date date = new Date();
		String year = DateUtil.dateFormat(date, "yyyy");
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
//		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
//		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
//		if("1200".equals(provincecode)){
//			tempid ="ZFABWJYSPB";
//		}
		request.setAttribute("year", year);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		View view = new InternalResourceView("WEB-INF/JSP/outexecute/outPrisonProvinceCaseListPage.jsp");
		mav = new ModelAndView(view);
		return mav;
		
	}
	
	/**
	 *处事经办列表
	 * 2014-8-11
	 */
	
	@RequestMapping(value="/getOutPrisonChuShiListDate.json")
	@ResponseBody
	public Object getOutPrisonChuShiListDate(HttpServletRequest request,HttpServletResponse response){
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
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			String jailid = request.getParameter("jailid");
			String flowdefid = request.getParameter("flowdefid");
			String casenums = request.getParameter("casenums");
			String year = request.getParameter("year");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("suid", su.getUserid());
			map.put("orgid", orgid);
			map.put("departid", departid);
			map.put("flowdefid", flowdefid);
			map.put("key", key);
			map.put("jailid", jailid);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	if(StringNumberUtil.notEmpty(casenums)&&StringNumberUtil.notEmpty(year)){
	    		casenums = StringNumberUtil.formatCaseNo(casenums,year);
		    	map.put("casenums", casenums);
	    	}else if(!StringNumberUtil.notEmpty(casenums)&&StringNumberUtil.notEmpty(year)){
	    		map.put("year", year);
	    	}
	    	
	    	int count = uvFlowService.countOutPrisonChuShiList(map);
	    	data= uvFlowService.getOutPrisonChuShiList(map);
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	
	
	/**
	 *处事经办列表
	 * 2014-8-11
	 */
	@RequestMapping(value="/getOutPrisonProvinceCaseList.json")
	@ResponseBody
	public Object getOutPrisonProvinceCaseList(HttpServletRequest request,HttpServletResponse response){
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
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			String jailid = request.getParameter("jailid");
			String casenums = request.getParameter("casenums");
			String year = request.getParameter("year");
			
			Map<String,Object> map = new HashMap<String,Object>();
			if(StringNumberUtil.notEmpty(jailid)){
				map.put("jailid", jailid);
			}
			if(StringNumberUtil.notEmpty(key)){
				map.put("key", key);
			}
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("flowdefid",flowdefid);
	    	
			if(StringNumberUtil.notEmpty(casenums)&&StringNumberUtil.notEmpty(year)){
	    		casenums = StringNumberUtil.formatCaseNo(casenums,year);
		    	map.put("casenums", casenums);
	    	}else if(!StringNumberUtil.notEmpty(casenums)&&StringNumberUtil.notEmpty(year)){
	    		map.put("year", year);
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
			
	    	int count = uvFlowService.countOutPrisonProvinceCaseList(map);
	    	data= uvFlowService.getOutPrisonProvinceCaseList(map);
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	
	/**
	 * @author kexz
	 *处事经办办案
	 * 2014-8-11
	 */
	@RequestMapping(value="tochushiHandle")
	public ModelAndView tochushiHandle(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		String tempid = request.getParameter("tempid");
//		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
//		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
//		if("1200".equals(provincecode)){
//			tempid ="ZFABWJYSPB";
//		}
		request.setAttribute("tempid", tempid);
		JSONArray docconent = new JSONArray();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		if (template != null) {
			docconent.add(template.getContent());
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);//根据部门Id获取单位信息
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
		TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		map.put("criofficiallyplacedate",sdf.format(crime.getInprisondate()));
		map.put("text10",org.getName());
		map.put("cbiname", info.getName());
		map.put("cbigendername", info.getGender());
		map.put("cbihomeaddress",info.getAddressdetail());
		map.put("age", "");
		map.put("cbinativenamedetail", info.getOriginplacearea()+info.getOriginplaceaddress());
		if(crime != null){
			map.put("anyouhuizong", crime.getMaincase());
			//map.put("cjibegindate opcinprisonend", "自"+sdf.format(info.getSentencestime())==null?null:sdf.format(info.getSentencestime())+"至"+sdf.format(info.getSentencestime()==null?null:sdf.format(info.getSentencestime())));
			map.put("", crime.getExecutiondate());//入监日期
			map.put("jxmx", "");//刑期变动
			map.put("", "");//上次保外期限
		}
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/aip/toHanding.jsp"));
	}
	
	/**
	 * @author kexz
	 *处事经办办案建议书
	 * 2014-8-11
	 */
	@RequestMapping(value="tochushiHandlejianyishu")
	public ModelAndView renewalRecommendation(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/aip/tochushiHandlejianyishu.jsp"));
	}
	
	
	/**
	 * @author kexz
	 *处事经办办案建议书
	 * 2014-8-11
	 */
	@RequestMapping(value = "getJianyishu")
	@ResponseBody
	public HashMap getJianyishu(String tempid,HttpServletRequest request){
		SystemUser user = getLoginUser(request);
        HashMap<String, String> map = new HashMap<String, String>();
        String content = systemModelService.getRecommendationContent(tempid,user, request);
		map.put("annexcontent", content);
		return map;
	}
	
	
	
	//监外执行处室审查---------------------------------------------------------------
	
	@RequestMapping(value="jwzxchushiExamine")
	public ModelAndView jwzxchushiExamine(){
		return new ModelAndView(new InternalResourceView(
		"/WEB-INF/JSP/outexecute/jwzxchushiExamine.jsp"));
	}
	
//	/**
//	 * @author kexz
//	 *处事经办审查
//	 * 2014-8-11
//	 */
//	
//	@RequestMapping(value="jwzxchushiExamineList")
//	@ResponseBody
//	public Object jwzxchushiExamineList(HttpServletRequest request,HttpServletResponse response){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			//取得参数
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			//分页
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
//			//字段排序
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("key", key);
//			map.put("orgid", getLoginUser(request).getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//	    	//根据map传参获取总条数
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	//根据map传参获取罪犯列表
//	    	data= chooseCriminalService.getBasicInfoList(map);
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}
	
	
	/**
	 * @author kexz
	 *处事经办审查
	 * 2014-8-11
	 */
	@RequestMapping(value="tojwzxchushiExamine")
	public ModelAndView tojwzxchushiExamine(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		JSONArray docconent = new JSONArray();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		if (template != null) {
			docconent.add(template.getContent());
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);//根据部门Id获取单位信息
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
		TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		map.put("criofficiallyplacedate",sdf.format(crime.getInprisondate()));
		map.put("text10",org.getName());
		map.put("cbiname", info.getName());
		map.put("cbigendername", info.getGender());
		map.put("cbihomeaddress",info.getAddressdetail());
		map.put("age", "");
		map.put("cbinativenamedetail", info.getOriginplacearea()+info.getOriginplaceaddress());
		if(crime != null){
			map.put("anyouhuizong", crime.getMaincase());
			//map.put("cjibegindate opcinprisonend", "自"+sdf.format(info.getSentencestime())==null?null:sdf.format(info.getSentencestime())+"至"+sdf.format(info.getSentencestime()==null?null:sdf.format(info.getSentencestime())));
			map.put("", crime.getExecutiondate());//入监日期
			map.put("jxmx", "");//刑期变动
			map.put("", "");//上次保外期限
		}
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/aip/tojwzxchushiExamine.jsp"));
	}
	
	/**
	 * @author kexz
	 *处事经办审查建议书
	 * 2014-8-11
	 */
	@RequestMapping(value="tojwzxchushiExaminejianyishu")
	public ModelAndView tojwzxchushiExaminejianyishu(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/aip/tojwzxchushiExaminejianyishu.jsp"));
	}
	
	
	/**
	 * @author kexz
	 *处事经办审查建议书
	 * 2014-8-11
	 */
	@RequestMapping(value = "getjwzxscJianyishu")
	@ResponseBody
	public HashMap getjwzxscJianyishu(String tempid,HttpServletRequest request){
		SystemUser user = getLoginUser(request);
        HashMap<String, String> map = new HashMap<String, String>();
        String content = systemModelService.getRecommendationContent(tempid,user, request);
		map.put("annexcontent", content);
		return map;
	}
	
	
//监外执行省局审批----------------------
	@RequestMapping(value="jwzxProvinceExamine")
	public ModelAndView jwzxProvinceExamine(){
		return new ModelAndView(new InternalResourceView(
		"/WEB-INF/JSP/outexecute/jwzxProvinceExamine.jsp"));
	}
	
//	/**
//	 * @author kexz
//	 *处事经办省局审批
//	 * 2014-8-11
//	 */
//	
//	@RequestMapping(value="jwzxProvinceExamineList")
//	@ResponseBody
//	public Object jwzxProvinceExamineList(HttpServletRequest request,HttpServletResponse response){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			//取得参数
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			//分页
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
//			//字段排序
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("key", key);
//			map.put("orgid", getLoginUser(request).getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//	    	//根据map传参获取总条数
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	//根据map传参获取罪犯列表
//	    	data= chooseCriminalService.getBasicInfoList(map);
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}
	
	
	/**
	 * @author kexz
	 *处事经办省局审批
	 * 2014-8-11
	 */
	@RequestMapping(value="tojwzxProvinceExamine")
	public ModelAndView tojwzxProvinceExamine(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		JSONArray docconent = new JSONArray();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		if (template != null) {
			docconent.add(template.getContent());
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);//根据部门Id获取单位信息
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
		TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		map.put("criofficiallyplacedate",sdf.format(crime.getInprisondate()));
		map.put("text10",org.getName());
		map.put("cbiname", info.getName());
		map.put("cbigendername", info.getGender());
		map.put("cbihomeaddress",info.getAddressdetail());
		map.put("age", "");
		map.put("cbinativenamedetail", info.getOriginplacearea()+info.getOriginplaceaddress());
		if(crime != null){
			map.put("anyouhuizong", crime.getMaincase());
			//map.put("cjibegindate opcinprisonend", "自"+sdf.format(info.getSentencestime())==null?null:sdf.format(info.getSentencestime())+"至"+sdf.format(info.getSentencestime()==null?null:sdf.format(info.getSentencestime())));
			map.put("", crime.getExecutiondate());//入监日期
			map.put("jxmx", "");//刑期变动
			map.put("", "");//上次保外期限
		}
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/aip/tojwzxProvinceExamine.jsp"));
	}
	
	/**
	 * @author kexz
	 *处事经办省局审批
	 * 2014-8-11
	 */
	@RequestMapping(value="tojwzxProvinceExaminejianyishu")
	public ModelAndView tojwzxProvinceExaminejianyishu(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/aip/tojwzxProvinceExaminejianyishu.jsp"));
	}
	
	
	/**
	 * @author kexz
	 *处事经办省局审批
	 * 2014-8-11
	 */
	@RequestMapping(value = "getjwzxProvinceExamineJianyishu")
	@ResponseBody
	public HashMap getjwzxProvinceExamineJianyishu(String tempid,HttpServletRequest request){
		SystemUser user = getLoginUser(request);
        HashMap<String, String> map = new HashMap<String, String>();
        String content = systemModelService.getRecommendationContent(tempid,user, request);
		map.put("annexcontent", content);
		return map;
	}
	
}

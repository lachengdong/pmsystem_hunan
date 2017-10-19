package com.sinog2c.mvc.controller.commutationParole;

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
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.commutationParole.CommuteParoleBatchService;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 省局立案
 * 
 * @author hzl
 * 
 */
@Controller
public class ProvinceController extends ControllerBase {
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private CommuteParoleBatchService commuteParoleBatchService;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private SystemTemplateService systemTemplateService;
	@Resource
	private UvFlowService uvFlowService;
	/**
	 * 跳转列表页
	 * 
	 */
	@RequestMapping(value = "/toProvinceLiAnPage")
	public ModelAndView toProvinceLiAnPage(HttpServletRequest request) {
		returnResourceMap(request);
		
		String curyear = "";
		Date date = new Date();
		curyear = DateUtil.dateFormat(date, "yyyy");
		
		SystemUser su = getLoginUser(request);
		String departid = su.getDepartid();
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("departid", departid);
		paramMap.put("flowdefid", flowdefid);
		paramMap.put("curyear", curyear);
		String casenumber = flowBaseService.getLastCaseNum(paramMap);//案件号
		
		SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
		String shortname = so.getShortname();
		request.setAttribute("casenumber", casenumber);
		request.setAttribute("curyear", curyear);
//		request.setAttribute("batch", batch);
		request.setAttribute("shortname", shortname);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/commutationParole/provinceListPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	
	/**
	 * 方法描述：列表页面
	 * 2014-8-11
	 */
	@RequestMapping(value = "/getProvinceLiAnList")
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
			String departid = su.getDepartid();
			String jailid = request.getParameter("jailid");
			String changetype = request.getParameter("changetype");
			String flowdefid = request.getParameter("flowdefid");
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("orgid", orgid);
			map.put("departid", departid);
			map.put("jailid", jailid);
			map.put("changetype", changetype);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("flowdefid", flowdefid);
	    	map.put("state", "-1,0,1,2");
			
	    	int count = tbxfSentencealterationService.provinceLiAnCount(map);
	    	data= tbxfSentencealterationService.provinceLiAnList(map);
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	
	/**
	 * 方法描述：列表页面
	 * 2014-8-11
	 */
	@RequestMapping(value = "/getJailList")
	@ResponseBody
	public Object getJailList(HttpServletRequest request,HttpServletResponse response){
		List<Map> data = new ArrayList<Map> ();
		try {
			SystemUser su = getLoginUser(request);
			String orgid = su.getOrgid();
			Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("orgid",orgid);
	    	data= tbxfSentencealterationService.getJailList(map);
	    	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	
	//立案
	@RequestMapping(value = "/toProvince")
	public void toProvince(HttpServletRequest request) {
		int count = 0;// 保存结果：0、失败，1、成功
		String crimid = request.getParameter("crimid");
		String tempid=  request.getParameter("tempid");
		String action = request.getParameter("action");
		String menuid = request.getParameter("menuid");
		ModelAndView mav = null;
		request.setAttribute("crimid", crimid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("action", action);
		request.setAttribute("menuid", menuid);
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		JSONArray docconent = new JSONArray();
		TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService.getCrimeByCrimid(crimid);
		Map<String,Object> tbxfMap = tbxfSentencealterationService.selectTbxfByCrimid(crimid);
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
		Map<String,Object> map = new HashMap<String,Object>();
		if (template != null) {
			map.put("text34",template.getTempname());
			docconent.add(template.getContent());
		}
		if(tbprisonerBaseinfo!=null){
			String deptid = tbprisonerBaseinfo.getDepartid();
			SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);
			if(org!=null){
				map.put("departid",org.getName());
				
			}
			map.put("cbiname",tbprisonerBaseinfo.getName());
			//age;年龄
			map.put("cbinativename",tbprisonerBaseinfo.getOriginplaceaddress());
			map.put("caiformeraddress",tbprisonerBaseinfo.getAddressdetail());
			//yuanpanxingqi原判刑期
			//lyplacedatestring入监日期
			//jianyuyijian单位意见
			//gaizaobiaoxian改造表现
			map.put("cbigendername",tbprisonerBaseinfo.getGender());
			map.put("cbinativenamedetail",tbprisonerBaseinfo.getOrigin());
			
		}
		if(tbprisonerBaseCrime!=null){
			map.put("cjicourtname", tbprisonerBaseCrime.getJudgmentname());//法院
			map.put("fujiaxing", tbprisonerBaseCrime.getLosepoweryear()+"年"+tbprisonerBaseCrime.getLosepowermonth()+"个月"+tbprisonerBaseCrime.getLosepowereday()+"天");// 剥夺年限
			map.put("anyouhuizong", tbprisonerBaseCrime.getCauseaction());
			String gaizaobiaoxian = systemModelService.getRecommendationContent("9993", user, request);
			map.put("gaizaobiaoxian", gaizaobiaoxian);
			
		}
		if(tbxfMap!=null&&!tbxfMap.isEmpty()){
			map.put("parolenumber","("+tbxfMap.get("COURTYEAR")+")"+tbxfMap.get("COURTSHORT")+"第"+tbxfMap.get("COURTSN")+"号");
		}
		map.put("criminalid", crimid);
		String applyvalue = crimid+","+tbprisonerBaseinfo.getName();
		request.setAttribute("applyvalue",applyvalue);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
	}
	
	
	/**
	 * 修改/保存大字段
	 * 
	 * @author
	 * 
	 */
	@RequestMapping(value = "/saveProvince")
	@ResponseBody
	public int saveProvince(
			HttpServletRequest request) {
		int countnum = 0;// 保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date();
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator");
		String tempid = request.getParameter("tempid") == null ? "" : request.getParameter("tempid");
		String docid = request.getParameter("docid") == null ? "" : request.getParameter("docid");
		String introduction = "省局立案";
		String content = request.getParameter("content") == null ? "" : request.getParameter("content");
		String crimid = request.getParameter("crimid");
		if ("new".equals(operator)) {
			document.setDepartid(crimid);
			document.setTempid(tempid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype("省局立案操作");
			log.setOpaction("新增");
			log.setOpcontent("省局立案");
			log.setOrgid(user.getUserid());
			log.setRemark("省局立案新增事件");
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		} else if ("edit".equals(operator)) {
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype("省局立案操作");
			log.setOpaction("修改");
			log.setOpcontent("省局立案修改");
			log.setOrgid(user.getUserid());
			log.setRemark("省局立案修改事件");
			countnum = tbsysDocumentService.updateTbsysDocument(document);
		}
		if (countnum == 1)
			status = 1;// 如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return countnum;
	}
	
	
	@RequestMapping(value = "/toProvinceCommuteTabs")
	public ModelAndView toProvinceCommuteTabs(HttpServletRequest request) {
		String crimid = "";
		String orgid = "";
		String flowdraftid = "";
		String flowid = "";
		String lastnodeid = "";
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String fathermenuid = request.getParameter("fathermenuid");
		String tempid=  request.getParameter("tempid");
		String menuid = request.getParameter("menuid");
		String closetype = request.getParameter("closetype");
		String flowdefid = request.getParameter("flowdefid");
		String nextButton = request.getParameter("nextButton");
		String tabtype = request.getParameter("tabtype");//0立案时案件查看
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
		
		ModelAndView mav = null;
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
		request.setAttribute("nextButton", nextButton);
		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String chengpibiao = jyconfig.getProperty(GkzxCommon.CHENGPIBIAO)==null?"":jyconfig.getProperty(GkzxCommon.CHENGPIBIAO);
        if (!"".equals(chengpibiao)) {
            request.setAttribute("chengpibiao", chengpibiao);
        }
		if(StringNumberUtil.belongTo(user.getDepartid(), GkzxCommon.SHANGHAI_PROVINCE)) {
			request.setAttribute("proposal", "province");//建议书类型为省局建议书
		} 
		if(StringNumberUtil.belongTo(user.getDepartid(), GkzxCommon.XINJIANG_PROVINCE)) {
			request.setAttribute("xinjiang", 1);//建议书类型为省局建议书
		}else{
			request.setAttribute("xinjiang", 0);
		} 
		if(GkzxCommon.ZERO.equals(tabtype)) mav = new ModelAndView("aip/provinceCommuteTabs2");
		else mav = new ModelAndView("aip/provinceCommuteTabs");
		return mav;
	}
	
	
	//省局办案
	@RequestMapping(value = "/provinceCommuteCase")
	public ModelAndView provinceCommuteCase(HttpServletRequest request){
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		String ischeckseal = (jyconfig.getProperty("ischeckseal")== null?"":jyconfig.getProperty("ischeckseal"));
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
		String menuid = request.getParameter("menuid");
		String closetype = request.getParameter("closetype");
		String flowdefid = request.getParameter("flowdefid");
		String nextButton = request.getParameter("nextButton");
		
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
		
		request.setAttribute("fatherMenuid", fathermenuid);
		request.setAttribute("applyid", crimid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("orgid", orgid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("lastnodeid", lastnodeid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("closetype", closetype);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("nextButton", nextButton);
		request.setAttribute("ischeckseal", ischeckseal);
		
		String applyname = tbxfSentencealterationService.getCrimiNameByCrimid(crimid);
		request.setAttribute("applyname", applyname);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("flowdefid", flowdefid);
		paramMap.put("flowdraftid", flowdraftid);
		paramMap.put("tempid", tempid);
		
		String content =  flowBaseOtherService.getDocconentByConditionTwo(paramMap);
		
		//上海市局减刑假释和监狱相同
		if(GkzxCommon.SHANGHAI_PROVINCE.equals(provincecode)){
			if(StringNumberUtil.isEmpty(content)){
				Map<String, String> bMap = new HashMap<String, String>();
				bMap.put("crimid", crimid);
				bMap.put("state", "1");
				content =  flowBaseOtherService.getDocconent5other(bMap);
			}
		}
		if("6500".equals(provincecode)){
			if(StringNumberUtil.isEmpty(content)){
				Map bMap = new HashMap();
				bMap.put("flowdefid", "other_zfjyjxjssp");
				bMap.put("tempid", "JXJS_JXJSSHB");
				bMap.put("crimid", crimid);
				bMap.put("nodeid", "N0005");
				bMap.put("state", "1");
				content =  flowBaseOtherService.getDocconent4other(bMap);
			}
		}
		if(StringNumberUtil.notEmpty(content)){
			docconent.add(content);
		}else{
			Map<String, String> paramap = new HashMap<String, String>();
			paramap.put("crimid", crimid);
			paramap.put("tempid", tempid);
			
			TbsysDocument template1 = tbsysDocumentService.getTbsysDocumentByMap2(paramap);
			if(null != template1){
				docconent.add(template1.getContent());
			}else{
				TbsysTemplate template2 = systemModelService.getTemplateAndDepartid(tempid, departid);
				if(template2 !=null) docconent.add(template2.getContent());
			}
			
			String orgName = ""; //单位
			SystemOrganization org = systemOrganizationService.getByOrganizationId(user.getOrgid());
			if(org!=null){
				orgName = org.getName();
				map.put("dwei",orgName);
			}
			
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年M月d日");
			
			//为表单数据做准备
			Map<String, String> paramMap3 = new HashMap<String, String>();
			paramMap3.put("flowdraftid", flowdraftid);
			FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap3);
			if(fb != null){
				map.put("casenum", fb.getText6());
				int tem = fb.getInt1()== null?0:fb.getInt1();
				if(tem==1){
					map.put("jxorjs", "罪犯假释审批表");
				}else{
					map.put("jxorjs", "罪犯减刑审批表");
				}
				map.put("jxjs_1", tem);
			}
			
			TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
			TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService.getCrimeByCrimid(crimid);
			Map<String,Object> tbxfMap = tbxfSentencealterationService.selectTbxfByCrimid(crimid);
			
			if(tbprisonerBaseinfo!=null){
				
				map.put("cbiname",tbprisonerBaseinfo.getName());
				map.put("cbitruename",tbprisonerBaseinfo.getUsedname());
				map.put("cbigendername",tbprisonerBaseinfo.getGender());
				map.put("caieducation",tbprisonerBaseinfo.getEducation());
				map.put("cbinativenamedetail",tbprisonerBaseinfo.getOrigin());
				map.put("cbination",tbprisonerBaseinfo.getNation());
				map.put("cbibirthday",sdf.format(tbprisonerBaseinfo.getBirthday()));
				map.put("cbihomeaddress",tbprisonerBaseinfo.getFamilyaddress());
				map.put("age", DateUtil.getAge(tbprisonerBaseinfo.getBirthday()));
				map.put("huji", tbprisonerBaseinfo.getRegisteraddressdetail());
			}
			
			if(tbprisonerBaseCrime!=null){
				map.put("pjrq", sdf.format(tbprisonerBaseCrime.getJudgedate()));//判决日期
				map.put("rjrq", sdf.format(tbprisonerBaseCrime.getInprisondate()));//INPRISONDATE 入监日期
				map.put("zhixingdate", sdf.format(tbprisonerBaseCrime.getExecutiondate()));
				map.put("cjicourtname", StringNumberUtil.getDefaultStringOnNull(tbprisonerBaseCrime.getJudgmentname(),"") + StringNumberUtil.getDefaultStringOnNull(tbprisonerBaseCrime.getJudgmentshort(),""));//法院
				map.put("pjfy", tbprisonerBaseCrime.getJudgmentname());//判裁法院
				if(tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_YOUQI) || tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_YOUQI_ZH)){
					String strPunishment = StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getPunishmentyear())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getPunishmentyear().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getPunishmentyear().toString()) + "年";
					strPunishment += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getPunishmentmonth())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getPunishmentmonth().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getPunishmentmonth().toString()) + "个月";
					strPunishment += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getPunishmentday())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getPunishmentday().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getPunishmentday().toString()) + "天";
					if (!StringNumberUtil.isNullOrEmpty(strPunishment)) {
						map.put("zhuxing", GkzxCommon.XINGQI_YOUQI_ZH + strPunishment);// 刑期
					}
				}else if(tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_SIHUAN) || tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_SIHUAN_ZH)){
					map.put("zhuxing", GkzxCommon.XINGQI_SIHUAN_ZH);
				}else if(tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_WUQI) || tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_WUQI_ZH)){
					map.put("zhuxing", GkzxCommon.XINGQI_WUQI_ZH);
				}else {
					map.put("zhuxing", "");
				}
				String strLosepower = "";
				if(!StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getLosepoweryear()) && (tbprisonerBaseCrime.getLosepoweryear().toString().equals(GkzxCommon.LOSEPOWER_97) || tbprisonerBaseCrime.getLosepoweryear().toString().equals(GkzxCommon.LOSEPOWER_99))){
					strLosepower = GkzxCommon.LOSEPOWER_ZH;
				} else {
					strLosepower = StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getLosepoweryear())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getLosepoweryear().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getLosepoweryear().toString()) + "年";
					strLosepower += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getLosepowermonth())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getLosepowermonth().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getLosepowermonth().toString()) + "个月";
					strLosepower += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getLosepowereday())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getLosepowereday().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getLosepowereday().toString()) + "天";
				}
				map.put("fujiaxing", strLosepower);// 剥夺年限
				map.put("anyouhuizong", tbprisonerBaseCrime.getCauseaction());
				String Sentencestime = "";
				if(tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_YOUQI) || tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_YOUQI_ZH)){
					Sentencestime += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getSentencestime())?"":"从" + sdf2.format(tbprisonerBaseCrime.getSentencestime());
					Sentencestime += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getSentenceetime())?"": "起至"+sdf2.format(tbprisonerBaseCrime.getSentenceetime());
					map.put("xqqz", Sentencestime);// 刑期
				} else {
					map.put("xqqz", "");// 刑期
				}
				map.put("fanzuishijian", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getCrimedate()));
				map.put("cjimulct", tbprisonerBaseCrime.getForfeit());
				map.put("fajinjiaonaqingkuang", tbprisonerBaseCrime.getPayment());
				map.put("cjipeichangjine", tbprisonerBaseCrime.getCompensation());
				map.put("cjmoneydisgorged", tbprisonerBaseCrime.getStolenmoney());
				String gaizaobiaoxian = systemModelService.getRecommendationContent("9993", user, request);
				gaizaobiaoxian=gaizaobiaoxian.replace("\n", "\\r\\n");
				gaizaobiaoxian=gaizaobiaoxian.replace("rn", "\\r\\n");
				map.put("gaizaobiaoxian", gaizaobiaoxian);
			}
			
			if(tbxfMap!=null&&!tbxfMap.isEmpty()){
				//刑期变动
				String xqbd = "";
				if (StringNumberUtil.notEmpty(tbxfMap.get("SENTENCECHAGEINFO"))){
					xqbd = StringNumberUtil.getTrimRtnStr(tbxfMap.get("SENTENCECHAGEINFO").toString());
					map.put("xqbd", xqbd);
				}
				
				String text1 = "";// JAILINFO 监狱意见
				String text18 ="";
				if(StringNumberUtil.notEmpty(tbxfMap.get("JAILREPORT"))){
					text18= tbxfMap.get("JAILREPORT").toString().substring(0, 10);// JAILREPORT监狱呈报日期
				}
				if (StringNumberUtil.notEmpty(tbxfMap.get("JAILINFO"))){
					text1 = StringNumberUtil.getTrimRtnStr(tbxfMap.get("JAILINFO").toString());
					map.put("text1", text1);
				}
				if (StringNumberUtil.notEmpty(text18)){
					map.put("text18", DateUtil.dateFormatForAip(DateUtil.StringParseDate(text18, GkzxCommon.DATEFORMAT)));
				}
				
				if (!StringNumberUtil.isNullOrEmpty(fb.getText6())){
					try {
						map.put("parolenumber","("+fb.getText6().substring(0, 4)+")"+orgName+"第"+fb.getText6().substring(4)+"号");
					} catch (Exception e) {
					}
				}
			}
			map.put("criminalid", crimid);
			String applyvalue = crimid+","+tbprisonerBaseinfo.getName();
			request.setAttribute("applyvalue",applyvalue);
		}
		
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("flowdefid", flowdefid);
		
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/aip/provinceCommutePage.jsp");
		mav = new ModelAndView(view);
		return mav;
		
	}
	
	
	@RequestMapping(value = "/toCommuteOfProvinceCaseTabs")
	public ModelAndView toCommuteOfProvinceCaseTabs(HttpServletRequest request) {
		
		String fathermenuid = request.getParameter("fathermenuid");
		String crimid = request.getParameter("crimid");
		String flowid = request.getParameter("flowid");
		String orgid = request.getParameter("orgid");
		String flowdraftid = request.getParameter("flowdraftid");
//		String applyname = request.getParameter("applyname");
		String tempid = request.getParameter("tempid");
		
		String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
		if(StringNumberUtil.notEmpty(id)){//如果罪犯编号为空就表示批量处理
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			String temps = ids[0];
			String[] tempArr = temps.split("@");
			crimid = tempArr[0];//罪犯编号就从数组里取出第一个罪犯编号
			orgid = tempArr[1];
			flowid = tempArr[2];
			flowdraftid = tempArr[3];
		}
		
		String action = request.getParameter("action");
		String menuid = request.getParameter("menuid");
		ModelAndView mav = null;
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("fathermenuid", fathermenuid);
		request.setAttribute("orgid", orgid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("action", action);
		request.setAttribute("menuid", menuid);
		
		View view = new InternalResourceView("WEB-INF/JSP/aip/commuteOfProvinceCaseTabs.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	
	//办案
	@RequestMapping(value = "/commuteOfProvinceLeaderCase")
	public ModelAndView showAddFrom(HttpServletRequest request){
		JSONArray docconent = new JSONArray();
		Map<String,Object> map = new HashMap<String,Object>();
		returnResourceMap(request);
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		
		String crimid = request.getParameter("crimid");
		String fathermenuid = request.getParameter("fathermenuid");
		String orgid = request.getParameter("orgid");
		String flowid = request.getParameter("flowid");
		String flowdraftid = request.getParameter("flowdraftid");
//		String applyname = request.getParameter("applyname");
		String tempid = request.getParameter("tempid");
		String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
		
		if(StringNumberUtil.notEmpty(id)){//如果罪犯编号、部门等信息不为空就表示批量处理
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			String temps = ids[0];
			String[] tempArr = temps.split("@");
			crimid = tempArr[0];//罪犯编号就从数组里取出第一个罪犯编号
			orgid = tempArr[1];
			flowid = tempArr[2];
			flowdraftid = tempArr[3];
		}
		
		if(!StringNumberUtil.notEmpty(flowid)){
			Map<String, String> temPara = new HashMap<String, String>();
			temPara.put("flowdefid", "other_sjjxjssp");
			temPara.put("applyid", crimid);
			flowid = uvFlowService.getFlowidByCrimid(temPara);
		}
		
		request.setAttribute("flowid", flowid);
		request.setAttribute("fathermenuid", fathermenuid);
		request.setAttribute("orgid", orgid);
		request.setAttribute("applyid", crimid);
		request.setAttribute("flowdraftid", flowdraftid);
		String applyname = tbxfSentencealterationService.getCrimiNameByCrimid(crimid);
		request.setAttribute("applyname", applyname);
		request.setAttribute("tempid", tempid);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("flowdefid", "other_sjjxjssp");
		paramMap.put("flowid", flowid);
		
		String content =  flowBaseOtherService.getDocconentByCondition(paramMap);
		
		docconent.add(content);
//		Map paramMap2 = new HashMap();
//		paramMap2.put("flowdraftid", flowdraftid);
//		FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap2);
//		
//		map.put("fajinjiaonaqingkuang", fb.getText1());
//		map.put("paymentzk", fb.getText2());
//		map.put("paymentpc", fb.getText3());
//		map.put("paymentcc", fb.getText4());
//		map.put("gaizaobiaoxian", fb.getText5());
//		map.put("casenum", fb.getText6());
//		map.put("jxjs_1", fb.getInt1());
//		map.put("jxjs_2", fb.getInt2());
//		map.put("jxjs_3", fb.getInt3());
//		map.put("jxjs_4", fb.getInt4());
//		map.put("jxjs_5", fb.getInt5());	
		
//		request.setAttribute("formDatajson", new JSONObject(map).toString());
		
		request.setAttribute("formcontent", docconent.toString());
		ModelAndView mav = null;
		View view =null;
			view = new InternalResourceView(
			"WEB-INF/JSP/aip/addCommuteOfProvinceLeaderCase.jsp");
		mav = new ModelAndView(view);
		return mav;
		
	}

}

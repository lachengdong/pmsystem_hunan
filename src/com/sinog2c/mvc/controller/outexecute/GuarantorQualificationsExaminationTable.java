package com.sinog2c.mvc.controller.outexecute;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.flow.FlowBaseMapper;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.prisoner.TbprisonerSocialRelation;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.report.RMEngine;
//import javax.xml.registry.infomodel.User;


/**
 * @author hzl
 *保证人资格审查表
 * 2014-7-17
 */
@Controller
public class GuarantorQualificationsExaminationTable extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private SystemResourceService systemResourceService;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private SystemCodeService systemCodeService;
	@Autowired
	private FlowBaseMapper flowBaseMapper;
	
	String[] CheckBoxIsguanfan = {"Isminor","Isrecidivism","Recidivist","Isremission","Focus","Mediaattention","Specialcontrol","Falungong","Qitaxiejiao"};//惯犯对应字段
	String[] CheckBoxsishe = {"Drugs","Gun","Underworld","Wickedness"};//四涉对应字段
    String[] CheckBoxsishi = {"Takedrugs","Escapes","Suicide","Assaultepolice"};//四史对应字段
	String[][] CheckBoxs = {CheckBoxIsguanfan,CheckBoxsishe,CheckBoxsishi};//所有复选框的节点名称拼成数组
    String[] CheckBox = {"CheckBoxIsguanfan","CheckBoxsishe","CheckBoxsishi"};//所有复选框的节点名称拼成数组
	
	/**
	 * 方法描述：跳转到列表页
	 * 
	 * @author
	 */
	@RequestMapping("/guarantorQualificationsExaminationTable")
	public ModelAndView inPrisonNotice(HttpServletRequest request){
		returnResourceMap(request);
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String tempid=request.getParameter("tempid");
		String crimid=request.getParameter("crimid");
		String idname=request.getParameter("idname");
		String id=request.getParameter("id");
		String menuid=request.getParameter("menuid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			request.setAttribute("id", id);
			String[] ids = id == null ? null:id.split(",");
			String[] idnames = idname == null?null:idname.split(",");
			crimid = ids ==null?"":ids[0];//罪犯编号就从数组里取出第一个罪犯编号
			name = idnames == null?"":idnames[0];
		}
		request.setAttribute("idname", idname);
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid", tempid);
		return new ModelAndView("outexecute/guarantorQualificationsExaminationTable");
	}
	
	/**
	 * @author kexz
	 *保证人资格审查表罪犯列表页面
	 * 2014-8-9
	 */
	@RequestMapping("/GuarantorCrimidChooseList")
	public ModelAndView GuarantorCrimidChooseList(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/GuarantorChoose");
	}
	
//	/**
//	 * 罪犯列表页面
//	 * @author kexz
//	 * @return
//	 */
//	@RequestMapping("/getGuarantorChooseList")
//	@ResponseBody
//	public Object getGuarantorChooseList(HttpServletRequest request,
//			HttpServletResponse response){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
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
//	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
//	    	data= chooseCriminalService.getBasicInfoList(map);
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//			
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}
	
	/**
	 * 新增、修改、查看页面 
	 * 
	 * @author hzl
	 * @throws Exception 
	 */
	@RequestMapping(value = "/addGetGuarantor")
	public ModelAndView addGetGuarantor(HttpServletRequest request){
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String flowdraftid=request.getParameter("flowdraftid");	
		Map<String, Object> selectmap = new HashMap<String, Object>();
		selectmap.put("text11", systemCodeService.getcodeValue("GB003"));
		if(!StringNumberUtil.isNullOrEmpty(flowdraftid)){
			String baseDoc= flowBaseService.getDocConentByFlowdraftId(flowdraftid);
			if (baseDoc != null) {
				docconent.add(baseDoc);
			}
			request.setAttribute("flowdraftid", flowdraftid);
		}else{
			String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
			String menuid=request.getParameter("menuid")==null?"":request.getParameter("menuid");
			TbprisonerBaseinfo baseinfo=prisonerService.getBasicInfoByCrimid(crimid);
			TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(crimid);
			String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");;
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
			Map<String,Object> map = new HashMap<String,Object>();
			if (template != null) {
				docconent.add(template.getContent());
			}
			//text23 ，text24 ,text25 ,text26,text27 宁夏特有 请勿 给其赋值mushuhong
			//表单上面 客户容易让修改的内容，写为模板mushuhong宁夏用，勿动
			String nxString = GkzxCommon.NX_CONTENT.replace(GkzxCommon.NX_CRIMINAME, baseinfo.getName());
			
//			map.put("text29", content);
			map.put("cbiname",baseinfo.getName());
			map.put("jianqu", user.getOrganization().getName());
			request.setAttribute("crimid",crimid);
			request.setAttribute("tempid",tempid);
			request.setAttribute("menuid",menuid);
			request.setAttribute("applyname",baseinfo.getName());
			request.setAttribute("applyid",crimid);
			request.setAttribute("flowdefid", "other_bzrzgsp");
			request.setAttribute("orgid",baseCrime.getOrgid1());
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
		return new ModelAndView("aip/addGuarantorQualificationsExaminationTable");
	}
	/**
	 * w文档列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getGuarantorQualificationsExaminationTableList")
	@ResponseBody
	public Object getGuarantorQualificationsExaminationTableList(HttpServletRequest request) throws Exception{
		SystemUser user = getLoginUser(request);
		HashMap<String, Object> result = new HashMap<String, Object>();
		String deptId=user.getDepartid();//获取当前登录的用户
		String applyid=request.getParameter("crimid")==null? "":request.getParameter("crimid");//申请人id
		String key = request.getParameter("key")==null?"":request.getParameter("key");
		key = URLDecoder.decode(key,"UTF-8");
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("key", key);
		map.put("departId", deptId);
		map.put("applyid", applyid);
		map.put("flowdefid", "other_bzrzgsp");
    	map.put("sortField", sortField);
    	map.put("sortOrder", sortOrder);
    	map.put("start", String.valueOf(start));
    	map.put("end",String.valueOf(end));   
		List<Map> list = flowBaseService.findByMapCondition(map);
		int count = flowBaseService.countAllByCondition(map);
		result.put("total", count);
		result.put("data", list);
		return result;
	}	
	
	/**
	 * 收押花名册
	 * 
	 * 2014-9-16 17:11:45
	 */
	@RequestMapping(value = "/detainRegister")
	public ModelAndView detainRegister(HttpServletRequest request) {
		returnResourceMap(request);
		String status = "";
		SystemUser user = getLoginUser(request);
		String otherid = request.getParameter("otherid");
		RMEngine engine=systemResourceService.queryQualificationDataRmEngine("", user, request);
		request.setAttribute("mydata", engine.dedaoReportData());
		FlowBaseOther fbo = new FlowBaseOther();
		if(otherid!=null && !"".equals(otherid)) {
			fbo = flowBaseOtherService.findByOtherid(otherid);
			if(fbo!=null){
				otherid = fbo.getOtherid().toString();
				status = GkzxCommon.EDIT;
			}else{
				status = GkzxCommon.NEW;
			}
		}
		String querybutton = request.getParameter("querybutton");
		request.setAttribute("querybutton", querybutton);
		request.setAttribute("status", status);
        request.setAttribute("otherid", otherid);
		return new ModelAndView("report/detainRegister");
	}
	
	@RequestMapping(value = "/queryConditionPage")
	public ModelAndView queryConditionPage(HttpServletRequest request) {
		return new ModelAndView("report/queryConditionPage");
	}
	
	/**
	 *方法描述：跳转到新犯入监处理页面 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toBasicInfoRuJianPage.page")
	public ModelAndView toBasicInfoRuJianPage(HttpServletRequest request) throws Exception {
		String menuid = request.getParameter("menuid");
		String  _criminalid= request.getParameter("_criminalid")==null?"undefined":request.getParameter("_criminalid");
		menuid = menuid.replace("'", "");
		request.setAttribute("menuid", menuid);
		
		if (!"undefined".equals(_criminalid)) {
			request.setAttribute("_criminalid", _criminalid);
			
		}
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/inPrisonManagement/basicInfoRuJian");
	}
	
	/**
	 *方法描述：获取罪犯入监列表数据 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getBasicInfoRuJian.json")
	@ResponseBody
	public Object getBasicInfoRuJian(HttpServletRequest request) throws Exception {
		
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		SystemUser user = this.getLoginUser(request);
		//
		Map<String,Object> map = this.parseParamMap(request);
		map = this.parsePageInfoOfMap(map);
		
		map.put("departId", user.getOrgid());
		map.put("userid", user.getUserid());
    	map.put("flowdefid", "doc_rjdjsp");
    	
    	int count = chooseCriminalService.countFindWithFlow(map);//根据map传参获取总条数
    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
    	
    	resultmap.put("data", data);
		resultmap.put("total", count);
		return resultmap;
	}
	
	/**
	 *方法描述：根据idnumber判断是否处理过 ，如果处理过将大字段显示出来
	 *   ，没被处理过显示一个新的表单
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/basicRuJian.page")
	public ModelAndView basicInformation(HttpServletRequest request)throws Exception {
		
		String sdid=request.getParameter("sdid");
		request.setAttribute("sdid", sdid);
		String zaiyacombo1=request.getParameter("zaiyacombo1");
		request.setAttribute("zaiyacombo1", zaiyacombo1);
		String key=request.getParameter("key");
		if(null!=key&&!"".equals(key)){
			try {
				key = URLDecoder.decode(key,"UTF-8");
				request.setAttribute("key", key);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		String tempid = request.getParameter("tempid");
		String idnumber = request.getParameter("idnumber");
		String flowsymbol=request.getParameter("idnumber");
		String crimid = request.getParameter("crimid");
		if(StringNumberUtil.isEmpty(crimid)){
			crimid = request.getParameter("applyid");
		}
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		request.setAttribute("provincecode", provincecode);
		//如果罪犯编号为空就表示批量处理,将id解析成数组，取第一个作为复合编号（crimid+flowdraftid）
		if(crimid==null || "".equals(crimid)){
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];
			//将这个复合编号解析成数组，第一个是罪犯编号，第二个是流程草稿Id
			ids = crimid.split("@");
			crimid = ids[0];
			if(ids.length>1)idnumber = ids[1];
		}
		
		
		//request.setAttribute("flowdraftid", idnumber);
		JSONArray docconent = new JSONArray();
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Map<String,Object>> jianxinginfoinfoList = prisonerService.getCriminalInfo(crimid);
		Map<String,Object> jianxinginfo = new HashMap<String,Object>();
		if (!jianxinginfoinfoList.isEmpty() && jianxinginfoinfoList != null) {
			jianxinginfo = jianxinginfoinfoList.get(0);
		}
		
		SystemUser user = getLoginUser(request);
		String username=user.getName();
		request.setAttribute("username", username);
		
		
		tempid = "XFZX_ZFRJDJ";//request.getParameter("tempid");
		
//		List<Map<String, Object>> excecutedatalist =tbdataSentchageMapper.selectExecuteDateBycrimid(crimid);
//		Map<String,Object> excecutedatemap=new HashMap<String,Object>();
//		excecutedatemap=excecutedatalist.get(0);
//		
//		map.put("shouyashijian",excecutedatemap.get("SJXFZXJGZR"));//获取刑法变动表里的执行日期
		
		
		map = dealBasicInfoAip(map, jianxinginfo);
		
		
		request.setAttribute("tempid", tempid);
		
		String currnodeid=request.getParameter("lastnodeid");
		
		String status="";
		if(flowsymbol!=null){
		  status = flowBaseMapper.getStateFromTbflowBase(flowsymbol);
		  request.setAttribute("flowdraftid", flowsymbol);
		}
		
		if("0".equals(currnodeid)&&"2".equals(status)){//如果为管教办理，重新取数并且案件状态为退回
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());//获取表单
			if (template != null) docconent.add(template.getContent());
		}else if(!StringNumberUtil.isNullOrEmpty(idnumber)){//把大字段当做模板
			String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
			docconent.add(docConent);
			request.setAttribute("flowdraftid", idnumber);
		}else{
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());//获取表单
			if (template != null) docconent.add(template.getContent());
		}
		
		map = dealCriminalAttachInfo(map,crimid);
		
		String orgid=user.getOrgid();
		String unitlevel=flowBaseService.getUnitlevel(orgid);
		FlowBase flowBase = flowBaseService.getTianBiaoRen(crimid);
		String tianbiaoren = "";
		String shenheren = "";
		if(StringNumberUtil.notEmpty(flowBase)){
			tianbiaoren = flowBase.getText29();
			shenheren = flowBase.getText30();
		}
		if(StringNumberUtil.notEmpty(unitlevel) && "5".equals(unitlevel)){
			map.put("tianbiaoren",tianbiaoren);
			map.put("shenheren", username);
		}else{
			map.put("tianbiaoren", username);
			map.put("shenheren", shenheren);
		}
		
		
//		if("".equals(map.get("tianbiaoren"))){
//			map.put("tianbiaoren", jianxinginfo.get("TIANBIAOREN"));
//		}
		
		map = StringNumberUtil.dealMapForForm(map);
		//所有下拉列表显示
		Map<String, Object> selectmap = new HashMap<String, Object>();
		selectmap = dealBasicRuJianSelectMap(selectmap);
		request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
		//
		
		String menuid = request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		String closetype = request.getParameter("closetype");
		if(StringNumberUtil.notEmpty(closetype)){
			request.setAttribute("closetype", closetype);
		}
		String fathermenuid = request.getParameter("fathermenuid");
		if(StringNumberUtil.notEmpty(fathermenuid)){
			request.setAttribute("fathermenuid", fathermenuid);
		}
		//
		
		request.setAttribute("crimid", crimid);
		request.setAttribute("applyid", crimid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("applyname",jianxinginfo.get("CRIMNAME"));
		request.setAttribute("flowdefid", "doc_rjdjsp");
		request.setAttribute("orgid",jianxinginfo.get("ORGID1"));
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		//
		return new ModelAndView("penaltyPerform/inPrisonManagement/addBasicInformationRuJian");
	}
	
	
	private Map<String,Object> dealBasicInfoAip(Map<String, Object> map, Map<String,Object> jianxinginfo) throws SQLException{
		
		map.put("danwei",jianxinginfo.get("ORGNAME1"));//部门
		map.put("criminalid", jianxinginfo.get("CRIMID"));// 罪犯编号
		map.put("danganhao",jianxinginfo.get("FILENO"));// 档案号
		
		map.put("xingming", jianxinginfo.get("CRIMNAME"));//姓名
		map.put("cengyongming", jianxinginfo.get("USEDNAME"));// 别名
		map.put("cbigender", jianxinginfo.get("CRIMSEX"));// 性别
		
		map.put("chushengnianyue",jianxinginfo.get("CHUSHENGRIQI2"));//出生日期
		map.put("minzu", jianxinginfo.get("MINZU"));// 民族
		map.put("hunyinzhuangkuang",jianxinginfo.get("MARITALSTATUS"));// 婚姻状况	
		
		map.put("wenhuachengdu",jianxinginfo.get("WENHUACHENGDU"));//文化程度
		map.put("caipoliticalappearance",jianxinginfo.get("ZHENGZHIMIANMAO"));// 捕前面貌
		map.put("aiformervocation",jianxinginfo.get("BUQIANZHIYE"));// 捕前职业code
		
		map.put("aiformerduty", jianxinginfo.get("BUQIANZHIWU"));// 捕前职务
		map.put("caiformerlevel", jianxinginfo.get("ZHIWUJIBIE"));// 职务级别
		
		map.put("shenfenzhenghaoma",jianxinginfo.get("SHENFENZHENGHAO"));// 身份证号
		map.put("fazhengjiguan",jianxinginfo.get("FAZHENGJIGUAN"));// 发证机关
		
		map.put("cbinativeplaceaddress",jianxinginfo.get("JIGUAN"));//籍贯
		map.put("hujisuozaidi",jianxinginfo.get("HUJISUOZAIDI"));// 户籍所在地						
		map.put("cbiresidenttype", jianxinginfo.get("HUKOULEIXING"));// 户口类型
		
		map.put("juzhudi",jianxinginfo.get("JIATINGZHUZHI"));// 家庭住址
		map.put("cbispeak",jianxinginfo.get("KOUYIN"));// 口音
		
		map.put("criofficiallydepartment",jianxinginfo.get("JIAOFUJIGUAN"));// 交付机关
		map.put("cbispeciality",jianxinginfo.get("TECHANG"));// 特长
		
		map.put("criofficiallyplacetype",jianxinginfo.get("SHOUYALEIXING"));// 收押类型
		map.put("rujianshijian",jianxinginfo.get("RUJIANRIQI2"));// 入监日期
		map.put("cjicriminalsort",jianxinginfo.get("ZUIFANLEIBIE"));//罪犯类别
		
		map.put("caiflowtype",jianxinginfo.get("LIUCUANLEIBIE"));// 流窜类别
		map.put("cairecordnumber",jianxinginfo.get("QIANKECISHU"));// 前科次数
		map.put("caicommentnumber",jianxinginfo.get("TUANHUORENSHU"));// 团伙人数
		
		map.put("shouyashijian", jianxinginfo.get("EXECUTIONDATEAIP"));// 羁押日期
		map.put("caiarrestoffice",jianxinginfo.get("DAIBUJIGUAN"));// 逮捕机关
		map.put("caiarrestdate", jianxinginfo.get("DAIBURIQI2"));// 逮捕日期
		
		map.put("shenpanjiguan", jianxinginfo.get("YUANPANFAYUAN"));// 判决机关
		map.put("cjicourtnumber",jianxinginfo.get("YUANPANZIHAO") );// 判决书号
		map.put("cjijudgedate",jianxinginfo.get("YUANPANPANCAIRIQI2"));// 判决日期
		
		map.put("zuiming",jianxinginfo.get("CAUSEACTION"));// 罪名（主案由）
		map.put("qitaanyou",jianxinginfo.get("QITAZUIMING"));// （其他案由）
		map.put("fanzuishijian",jianxinginfo.get("FANZUISHIJIAN2"));// 犯罪时间
		map.put("xingba",jianxinginfo.get("XINGBA"));//是否刑八
		
		map.put("cjiimprisontype",jianxinginfo.get("YUANPANXINGZHONG"));// 刑罚种类
		map.put("xingqi", jianxinginfo.get("YUANPANXINGQI"));//刑期
		map.put("cjidisfranchiseyear", jianxinginfo.get("YUANPANBOQUAN"));// 剥夺年限
	
		map.put("cjibegindate",jianxinginfo.get("YUANPANXINGQIQIRI2"));// 刑期起日
		map.put("cjienddate", jianxinginfo.get("YUANPANXINGQIZHIRI2"));// 刑期止日
		
		map.put("youwuqianke", jianxinginfo.get("YOUWUQIANKE"));   //有无前科
		map.put("jiankangzhuangkuang", jianxinginfo.get("JIANKANG_INFO"));//健康状况
		
		String yuanpanxingqiqiri2 = jianxinginfo.get("YUANPANXINGQIQIRI2")==null?"":jianxinginfo.get("YUANPANXINGQIQIRI2").toString();
		String yuanpanxingqizhiri2 = jianxinginfo.get("YUANPANXINGQIZHIRI2") == null ?"":jianxinginfo.get("YUANPANXINGQIZHIRI2").toString();
		yuanpanxingqiqiri2 = DateUtil.dateFormat(DateUtil.StringParseDate1(yuanpanxingqiqiri2));
		yuanpanxingqizhiri2 = DateUtil.dateFormat(DateUtil.StringParseDate1(yuanpanxingqizhiri2));
		map.put("qizhishijian", yuanpanxingqiqiri2 + "-" + yuanpanxingqizhiri2);
		//刑期折抵
		
		map.put("cjisequestrateproperty",jianxinginfo.get("YUANMOSHOU")==null?"/":jianxinginfo.get("YUANMOSHOU"));// 没收财产
		
		map.put("cjotherjudge",jianxinginfo.get("YUANMINPEI")==null?"/":jianxinginfo.get("YUANMINPEI"));// 民事赔偿
		map.put("cjappeal",jianxinginfo.get("SHANGSUQINGKUANG"));// 上诉情况
		
		map.put("caioldpunish",jianxinginfo.get("QKQK"));// 曾受过处罚
		
		map.put("fayuancaipan",jianxinginfo.get("FAYUANPANCAI")==null?"":jianxinginfo.get("FAYUANPANCAI").toString().replace("&#13;&#10;", "\\r\\n").replace("rn", "\\r\\n").replace("\r\n", "\\r\\n"));//法院判裁
		if(null!=jianxinginfo.get("FANZUISHISHI")){
			Clob clob = (Clob)jianxinginfo.get("FANZUISHISHI");
			String fanzuishishi = clob.getSubString(1, (int)clob.length());
			map.put("fanzuishishi",fanzuishishi.replace("&#13;&#10;", "\\r\\n").replace("rn", "\\r\\n").replace("\r\n", "\\r\\n"));// 犯罪事实
		}
		
		return map;
	}
	
	
	
	private Map<String,Object> dealCriminalAttachInfo(Map<String,Object> map, String crimid){
		
		List<TbprisonerSocialRelation> relationList = prisonerService.findRelationBycrimid(crimid);
		
		// 家庭成员、主要社会关系
		if (!relationList.isEmpty() && relationList != null) {
			for (int i = 0; i < relationList.size(); i++) {
				TbprisonerSocialRelation tsr = relationList.get(i);
				map.put("srid"+i, tsr.getSrid());
				map.put("chengwei"+i, tsr.getRelationship());
				map.put("xingming"+i, tsr.getName());
				map.put("political"+i, tsr.getPolitical());
				map.put("zhuzhi"+i, tsr.getHomeaddress());
				map.put("phone"+i, tsr.getPhone());
				
				map.put("birthday"+i, DateUtil.dateFormatForAip(DateUtil.StringParseDate(tsr.getBirthday())));
				map.put("srorgname"+i, tsr.getPaperid()==null?"":tsr.getPaperid());
			}
		}
		
		return map;
	}
	
	
	/**
	 * 处理入监登记表的下拉选择
	 * @param selectmap
	 * @return
	 */
	private Map<String, Object> dealBasicRuJianSelectMap(Map<String, Object> selectmap){
		
		String cbigender = systemCodeService.getcodeValue("GB003");
		selectmap.put("cbigender", cbigender);
		for (int i = 0; i < 6; i++) selectmap.put("sex"+i, cbigender);
		
		selectmap.put("caiformermarriage", systemCodeService.getcodeValue("GB004"));
		selectmap.put("cbination", systemCodeService.getcodeValue("GB006"));
		selectmap.put("caieducation", systemCodeService.getcodeValue("GB007"));
		selectmap.put("xingba", systemCodeService.getcodeValue("GKXBLB"));
		
		String political = systemCodeService.getcodeValue("GB008");
		selectmap.put("caipoliticalappearance", political);
		for (int i = 0; i < 10; i++) selectmap.put("political"+i, political);
		
		selectmap.put("cbiresidenttype", systemCodeService.getcodeValue("GB010"));
		selectmap.put("caiformerlevel", systemCodeService.getcodeValue("GB018"));
		selectmap.put("cjiimprisontype", systemCodeService.getcodeValue("GB022"));
		selectmap.put("caiflowtype", systemCodeService.getcodeValue("GB025"));
		selectmap.put("criofficiallyplacetype", systemCodeService.getcodeValue("GB031"));
		selectmap.put("cjicriminalsort", systemCodeService.getcodeValue("GB036"));
		
		String relationship = systemCodeService.getcodeValue("GK004");
		for (int i = 0; i < 7; i++) selectmap.put("relationship"+i, relationship);
		
		selectmap.put("aiformervocation", systemCodeService.getcodeValue("GK041"));  
		selectmap.put("aiformerduty", systemCodeService.getcodeValue("GK043"));
		selectmap.put("cjappeal", systemCodeService.getcodeValue("GK046"));
		selectmap.put("cjidisfranchiseyear", systemCodeService.getcodeValue("GK059"));
		
		return selectmap;
	}
	
}

package com.sinog2c.mvc.controller.commutationParole;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletException;
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
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.prisoner.TbprisonerAccomplice;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.prisoner.TbprisonerResume;
import com.sinog2c.model.prisoner.TbprisonerSocialRelation;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemTemplate;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.commutationParole.TbxfScreeningService;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.FlowOtherFlowService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.PropertypunishmentperformService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.service.api.system.TbViewMaintainService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
/**
 * 经办人办案
 * 
 * @author hzl
 * 
 */
@Controller
public class CommuteOfJailCaseController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private PropertypunishmentperformService propertypunishmentperformService;
	@Autowired 
	private SentenceAlterationService sentenceAlterationService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Resource
	private UvFlowService uvFlowService;
	@Autowired
	private FlowOtherFlowService flowOtherFlowService;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private SystemTemplateService systemTemplateService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private TbViewMaintainService tbViewMaintainService;
	@Autowired
	private SystemResourceService systemResourceService;
	@Autowired
	private TbxfScreeningService tbxfScreeningService;
	
	
	/**
	 * 跳转列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toCommuteOfJailCasePage")
	public ModelAndView toTabsPage(HttpServletRequest request) {
		
		//获取 jyconfig.properties文件中的内容
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ningxia = jyconfig.getProperty(GkzxCommon.NINGXIA)==null?"":jyconfig.getProperty(GkzxCommon.NINGXIA);
		if (ningxia.contains(getLoginUser(request).getDepartid())) {
			request.setAttribute("ningxia", 1);
		}else{
			request.setAttribute("ningxia", 0);
		}
		SystemUser su = getLoginUser(request);
		String departid = su.getDepartid();
		SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
		String shortname = so.getShortname();
		request.setAttribute("shortname", shortname);
		
		ModelAndView mav = null;
		returnResourceMap(request);
		Date date = new Date();
		String year = DateUtil.dateFormat(date, "yyyy");
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		request.setAttribute("year", year);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		
		Map paraMap = new HashMap();
		paraMap.put("flowdefid", flowdefid);
		paraMap.put("provinceid", so.getPorgid());
		Map condi = tbViewMaintainService.selectByCondition(paraMap);
		if(null!=condi){
			//provinceid和nodeid用于当某个省份的监狱减刑假释审批是，某一级节点的一些特殊处理时传的参数
			Object provinceid = condi.get("provinceid");
			Object nodeid = condi.get("nodeid");
			Object days = condi.get("days");
			if(StringNumberUtil.notEmpty(provinceid)){
				request.setAttribute("provinceid", provinceid);
			}
			if(StringNumberUtil.notEmpty(nodeid)){
				request.setAttribute("nodeid", nodeid);
			}
			if(StringNumberUtil.notEmpty(days)){
				request.setAttribute("days", days);
			}
		}
		String ischeckseal = (jyconfig.getProperty("ischeckseal")== null?"":jyconfig.getProperty("ischeckseal"));
		
		request.setAttribute("ischeckseal", ischeckseal);
		View view = new InternalResourceView("WEB-INF/JSP/commutationParole/commuteOfJailCaseListPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	@RequestMapping(value = "/toCommuteOfJailCaseTabs")
	public ModelAndView toCommuteOfJailCaseTabs(HttpServletRequest request) {
		//crimid+"@"+orgid+"@"+flowdraftid+"@"+flowid+"@"+findid;
		String crimid = "";
		String orgid = "";
		String flowdraftid = "";
		String flowid = "";
		String lastnodeid = "";
		String fathermenuid = request.getParameter("fathermenuid");
		String flowdefid = request.getParameter("flowdefid");
		String tempid=  request.getParameter("tempid");
		String tempid2 = request.getParameter("tempid2");//宁夏无期死缓 审核表
		String menuid = request.getParameter("menuid");
		String closetype = request.getParameter("closetype");
		String provinceid=  request.getParameter("provinceid");
		String nodeid = request.getParameter("nodeid");
		String days = request.getParameter("days");
		String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
		
		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ischeckseal = (jyconfig.getProperty("ischeckseal")== null?"":jyconfig.getProperty("ischeckseal"));
		
		//如果是山西 或者宁夏的 进行另外一个打开审核表的类中
		String shanxi = jyconfig.getProperty(GkzxCommon.SHAN_XI)==null ?"":jyconfig.getProperty(GkzxCommon.SHAN_XI);
		String shengfen = jyconfig.getProperty(GkzxCommon.PROVINCECODE)==null ?"":jyconfig.getProperty(GkzxCommon.PROVINCECODE);
		if (shanxi.contains(getLoginUser(request).getDepartid())) {
			request.setAttribute("shanxi", "1");
		}else{
			request.setAttribute("shanxi", "0");
		}
		if (GkzxCommon.PROVINCE_SHANXI.equals(shengfen)) {
			request.setAttribute("shanxi", "2");
		}else{
			request.setAttribute("shanxi", "0");
		}
		if (GkzxCommon.XINJIANG_PROVINCE.equals(shengfen)) {
			request.setAttribute("xinjiang", "1");
		}else{
			request.setAttribute("xinjiang", "0");
		}
		//是否使用呈批表(宁夏不需要做呈批表的控制)
		String chengpibiao = jyconfig.getProperty(GkzxCommon.CHENGPIBIAO)==null?"":jyconfig.getProperty(GkzxCommon.CHENGPIBIAO);
		if (!"".equals(chengpibiao)) {
			request.setAttribute("chengpibiao", chengpibiao);
		}
		//档案资料（上海）
		String danganziliao = jyconfig.getProperty(GkzxCommon.DANGANZILIAO)==null?"":jyconfig.getProperty(GkzxCommon.DANGANZILIAO);
		if (!"".equals(danganziliao)) {
			request.setAttribute("danganziliao", danganziliao);
		}
		
		//判断 当前选中的罪犯 的刑种 根据刑种不同进入到不同的审核表中(宁夏 减刑、假释审核表分开)
		String ningxia = jyconfig.getProperty(GkzxCommon.NINGXIA);
//		if (ningxia.contains(getLoginUser(request).getDepartid())) {
//			String nowpub = flowBaseService.judgePunishmentByCrimid(id);
//			if ("1".equals(nowpub)) {
//				tempid = tempid2;
//			}
//		}
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
		request.setAttribute("ischeckseal", ischeckseal);
		if(StringNumberUtil.notEmpty(provinceid)){
			request.setAttribute("provinceid", provinceid);
		}
		
		if(StringNumberUtil.notEmpty(nodeid)){
			request.setAttribute("nodeid", nodeid);
		}
		
		if(StringNumberUtil.notEmpty(days)){
			request.setAttribute("days", days);
		}
		View view = null;
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String userdepartid = user.getDepartid();
		int num = this.systemOrganizationService.getNumByDepartid(userdepartid,"4400");
		if(num!=0){
			view = new InternalResourceView("WEB-INF/JSP/aip/guangdong/jianxingbananyemian.jsp");
		}else{
			if("1".equals(ischeckseal)){
				view = new InternalResourceView("WEB-INF/JSP/aip/commuteOfJailCaseTabs.jsp");
			}else{
				view = new InternalResourceView("WEB-INF/JSP/aip/commuteOfJailCaseTabsNoCheckSeal.jsp");
			}
		}
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value = "/getCommuteOfJailCaseList")
	@ResponseBody
	public Object getBasicInfoList(HttpServletRequest request,
			HttpServletResponse response,String key,String flowdefid,String provinceid,String nodeid,String days,String jianqu,
			String xingqileixing,String casetype,String banjieqingkuang,Integer pageIndex,Integer pageSize,String sortField,String sortOrder,
			String casenums,String year){
		
		SystemUser user = getLoginUser(request);
		List<Map> data = new ArrayList<Map>();
		Object buttonstr = "";
		Map<String, Object> resultmap = new HashMap<String,Object>();
		
		try {
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("flowdefid", flowdefid);
	    	map.put("casetype", casetype);
			map.put("suid", user.getUserid());
	    	map.put("key", key);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("jianqu", jianqu);
	    	map.put("xingqileixing", xingqileixing);
	    	map.put("banjieqingkuang", banjieqingkuang);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	if(StringNumberUtil.notEmpty(casenums)&&StringNumberUtil.notEmpty(year)){
	    		casenums = StringNumberUtil.formatCaseNo(casenums,year);
		    	map.put("casenums", casenums);
	    	}
	    	if(StringNumberUtil.notEmpty(year)){
	    		map.put("year", year);
	    	}
	    	
	    	if(user!=null){
	    		String orgid = user.getOrganization().getOrgid();
	    		String departid = user.getDepartid();
				map.put("orgid", orgid);
				map.put("departid", departid);
				
				//获取该用户拥有的按钮资源id
		    	buttonstr = this.returnButtonResourceByUser(user,null,null);
		    	map.put("connsql", buttonstr);
	    	}
	    	if(StringNumberUtil.notEmpty(provinceid)&&StringNumberUtil.notEmpty(nodeid)&&StringNumberUtil.notEmpty(days)){
	    		map.put("provinceid", provinceid.trim());
	    		map.put("nodeid", nodeid.trim());
	    		map.put("days", days.trim());
	    	}
	    	
	    	//获取总条数
	    	int count = uvFlowService.countAllOfCommuteByCondition(map);
			data= uvFlowService.findCommutatioByCondition(map);
			
			resultmap.put("data", data);
			resultmap.put("total", count);

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return resultmap;
	}
	/**
	 * 获取经办人办案的数据
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value = "/getJianBanRenCommuteList")
	@ResponseBody
	public Object getJianBanRenCommuteList(HttpServletRequest request,HttpServletResponse response){
		
		SystemUser user = getLoginUser(request);
		String state = "-1";//流转状态
		List<Map> data = new ArrayList<Map>();
		Object buttonstr = "";
		Map<String, Object> resultmap = new HashMap<String,Object>();
		
		try {
			//取得参数
			String key = request.getParameter("key")==null?"":request.getParameter("key");//检索关键字
			String flowdefid = "other_zfjyjxjssp";//罪犯减刑假释审批流程定义
			
			//分页
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			//字段排序
			String sortField = request.getParameter("sortField");
			if("anjianhao".equals(sortField.toLowerCase())){
				sortField = "to_number(substr(text6,5))";
			}
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			String casetype = request.getParameter("casetype");
			String casenums = request.getParameter("casenums");
			String year = request.getParameter("year");
			String xingqileixing = request.getParameter("xingqileixing");
			String jianqu = request.getParameter("jianqu");
			
			//获取当前菜单ID 对应的自定义流程ID 
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("flowdefid", flowdefid);
			map.put("jianqu", jianqu);
			map.put("xingqileixing", xingqileixing);
	    	map.put("suid", user.getUserid());
	    	map.put("key", key);
	    	map.put("casetype", casetype);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	if(StringNumberUtil.notEmpty(casenums)&&StringNumberUtil.notEmpty(year)){
	    		casenums = StringNumberUtil.formatCaseNo(casenums,year);
		    	map.put("casenums", casenums);
	    	}
	    	if(StringNumberUtil.notEmpty(year)){
	    		map.put("year", year);
	    	}
	    	if(user!=null){
	    		String orgid = user.getOrganization().getOrgid();
	    		String departid = user.getDepartid();
				map.put("orgid", orgid);
				map.put("departid", departid);
				
				//获取该用户拥有的按钮资源id
		    	buttonstr = this.returnButtonResourceByUser(user,null,null);
		    	map.put("connsql", buttonstr);
	    	}
	    	
	    	//获取总条数
	    	int count = uvFlowService.countAllOfJinBanRenCommute(map);
			data= uvFlowService.findCommutatioOfJianBanRen(map);
			
			resultmap.put("data", data);
			resultmap.put("total", count);

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return resultmap;
	}

	//办案
	@RequestMapping(value = "/commuteOfJailCase")
	public ModelAndView showAddFrom(HttpServletRequest request){
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

		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ischeckseal = (jyconfig.getProperty("ischeckseal")== null?"":jyconfig.getProperty("ischeckseal"));
		String tianjinconde = jyconfig.getProperty(GkzxCommon.TIANJIN_CODE);
		String guangdong = jyconfig.getProperty(GkzxCommon.GUANGDONG)==null?"":jyconfig.getProperty(GkzxCommon.GUANGDONG);
		String provincecode = jyconfig.getProperty("provincecode")==null?"":jyconfig.getProperty("provincecode");
		String provinceid = request.getParameter("provinceid");
		String chengpibiao = jyconfig.getProperty(GkzxCommon.CHENGPIBIAO)==null?"":jyconfig.getProperty(GkzxCommon.CHENGPIBIAO);
		request.setAttribute("chengpibiao", chengpibiao);
		String nodeid = request.getParameter("nodeid");
		String days = request.getParameter("days");
		//判断使用流程的表单
		String signcheckbiaodan = jyconfig.getProperty(GkzxCommon.SIGNCHECKBIAODAN)==null?"":jyconfig.getProperty(GkzxCommon.SIGNCHECKBIAODAN);
		request.setAttribute("signcheckbiaodan", signcheckbiaodan);
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
		String unitlevel = prisonerService.getUnitlevelByCrimidOrgid(crimid);
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
		request.setAttribute("ischeckseal", ischeckseal);
		
		if(StringNumberUtil.notEmpty(provinceid)){
			request.setAttribute("provinceid", provinceid);
		}
		
		if(StringNumberUtil.notEmpty(nodeid)){
			request.setAttribute("nodeid", nodeid);
		}
		
		if(StringNumberUtil.notEmpty(days)){
			request.setAttribute("days", days);
		}
		
		String applyname = tbxfSentencealterationService.getCrimiNameByCrimid(crimid);
		request.setAttribute("applyname", applyname);
		//用于审核表意见弹出框的处理 
		if(GkzxCommon.SHANGHAI_PROVINCE.equals(provincecode) || GkzxCommon.HUNAN_PROVINCE.equals(provincecode)){
			request.setAttribute("opiontype", GkzxCommon.ONE);
		}
		
		Map paramMap = new HashMap();
		paramMap.put("flowdraftid", flowdraftid);
		paramMap.put("tempid", tempid);
		
		String content =  flowBaseOtherService.getDocconentByConditionTwo(paramMap);
		
		if(StringNumberUtil.notEmpty(content)){
			docconent.add(content);
		}else{
			if(GkzxCommon.SHANGHAI_PROVINCE.equals(provincecode)|| GkzxCommon.HUNAN_PROVINCE.equals(provincecode)){
				//重写审核表显示用 zhenghui 2014.12.6 16:40
				map = ReturnShbMap(flowdraftid, crimid, request);
				docconent.add(map.get("docconent"));
			}else{
				Map paramMap3 = new HashMap();
				paramMap3.put("flowdraftid", flowdraftid);
				FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap3);
				String casetype = "";
				map.put("casenum", fb.getText6());
				map.put("jxjs_1", fb.getInt1());
				if(fb.getInt1()==0){
					casetype="减字";
					map.put("jxojs", "罪犯减刑审核表");
				}else if(fb.getInt1()==1){
					casetype="假字";
					map.put("jxojs", "罪犯假释审核表");
				}
				TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
				TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService.getCrimeByCrimid(crimid);
				Map<String,Object> tbxfMap = tbxfSentencealterationService.selectTbxfByCrimid(crimid);
				
				TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年M月d日");
				
				String orgName = ""; 
				if (template != null){
					docconent.add(template.getContent());
				}
				if(tbprisonerBaseinfo!=null){
					String deptid = tbprisonerBaseinfo.getDepartid();
					SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);
					if(org!=null){
						map.put("departid",org.getName());
						orgName = org.getShortname();
					}
					map.put("orgname", user.getOrganization().getFullname());
					map.put("cbiname",tbprisonerBaseinfo.getName());
					map.put("cbitruename",tbprisonerBaseinfo.getUsedname());
					map.put("cbigendername",tbprisonerBaseinfo.getGender());
					map.put("caieducation",tbprisonerBaseinfo.getEducation());
					map.put("cbinativenamedetail",tbprisonerBaseinfo.getCountryarea());//2015.1.14 by luan  数据交换时交换进Countryarea字段
					map.put("cbination",tbprisonerBaseinfo.getNation());
					map.put("cbibirthday",sdf.format(tbprisonerBaseinfo.getBirthday()));
					map.put("cbihomeaddress",tbprisonerBaseinfo.getFamilyaddress());
					map.put("idnumber",tbprisonerBaseinfo.getIdnumber());
					map.put("text35",tbprisonerBaseinfo.getRewardpunish());
				}
				if(tbprisonerBaseCrime!=null){
					Date rujiandate = tbprisonerBaseCrime.getInprisondate();
					String rujianriqistr = "";
					if(rujiandate!=null){
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(rujiandate);
						rujianriqistr = calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月";
					}
					map.put("criofficiallyplacedate",DateUtil.dateFormatForAip(rujiandate));
					map.put("text32", "执行日期");
					map.put("zhixingdate", sdf.format(tbprisonerBaseCrime.getExecutiondate()));
					map.put("judgedate", sdf.format(tbprisonerBaseCrime.getJudgedate()));
					if(tbprisonerBaseCrime.getDetaindate() != null && !"".equals(tbprisonerBaseCrime.getDetaindate())){
						map.put("text34", sdf.format(tbprisonerBaseCrime.getDetaindate()));
					}
					
					if(StringNumberUtil.belongTo(departid, tianjinconde) || GkzxCommon.XINJIANG_PROVINCE.equals(provincecode)) {
						map.put("cjicourtname", StringNumberUtil.getDefaultStringOnNull(tbprisonerBaseCrime.getJudgmentname(),""));//法院
					} else {
						map.put("cjicourtname", StringNumberUtil.getDefaultStringOnNull(tbprisonerBaseCrime.getJudgmentname(),"") + StringNumberUtil.getDefaultStringOnNull(tbprisonerBaseCrime.getJudgmentshort(),""));//法院
					}
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
						Sentencestime += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getSentenceetime())?"": "起\\r\\n至"+sdf2.format(tbprisonerBaseCrime.getSentenceetime())+"止";
						map.put("xingqiqizhi", Sentencestime);// 刑期
					} else {
//						if(departid.equals(GkzxCommon.TIANJIN)) {
						if(StringNumberUtil.belongTo(departid, tianjinconde)) {
							Sentencestime += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getSentencestime())?"":"从" + sdf2.format(tbprisonerBaseCrime.getSentencestime()) + "起";
							map.put("xingqiqizhi", Sentencestime);// 刑期
						} else {
							map.put("xingqiqizhi", "");
						}
					}
//					if(GkzxCommon.TIANJIN.equals(getLoginUser(request).getDepartid())) {
					if(StringNumberUtil.belongTo(getLoginUser(request).getDepartid(),tianjinconde)) {
						String fanzuishijian = DateUtil.dateFormat(tbprisonerBaseCrime.getCrimedate());
						if(fanzuishijian!=null && !"".equals(fanzuishijian)) {
							fanzuishijian = fanzuishijian.substring(0, 8);
						}
						map.put("fanzuishijian", fanzuishijian);// 天津犯罪时间精确到月
					} else {
						map.put("fanzuishijian", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getCrimedate()));
					}
					map.put("cjimulct", tbprisonerBaseCrime.getForfeit());
					Map mm = new HashMap();
					mm.put("remark1", "1");
					mm.put("crimid", crimid);
					map.put("fajinjiaonaqingkuang", propertypunishmentperformService.findSumPro(mm));
					map.put("cjipeichangjine", tbprisonerBaseCrime.getCompensation());
					map.put("cjmoneydisgorged", tbprisonerBaseCrime.getStolenmoney());
					Map ms = new HashMap();
					ms.put("remark1", "2");
					ms.put("crimid", crimid);
					map.put("paymentzk", propertypunishmentperformService.findSumPro(ms));
					Map mp = new HashMap();
					mp.put("remark1", "4");
					mp.put("crimid", crimid);
					map.put("paymentpc", propertypunishmentperformService.findSumPro(mp));
					map.put("cjisequestrateproperty", tbprisonerBaseCrime.getForfeitureproperty());
					Map ma = new HashMap();
					ma.put("crimid", crimid);
					ma.put("remark1", "3");
					map.put("paymentcc", propertypunishmentperformService.findSumPro(ma));
					
					//刑种
					map.put("punishmenttype", tbprisonerBaseCrime.getPunishmenttype());
					if(tbprisonerBaseCrime.getCrimeface()!=null && !"".equals(tbprisonerBaseCrime.getCrimeface())) {
						map.put("fanzuishishi","    "+tbprisonerBaseCrime.getCrimeface().replace("&#13;&#10;", "\\r\\n").replace("rn", "\\r\\n").replace("\r\n", "\\r\\n"));
					} else {
						map.put("fanzuishishi","    "+tbprisonerBaseCrime.getCrimeface());
					}
					map.put("text9", sdf.format(new Date()));
					String gaizaobiaoxian = "";
					SystemTemplate systemTemplate = systemTemplateService.getSystemTemplateByCondition("9993",user.getPrison().getPorgid());
					if(systemTemplate!=null) gaizaobiaoxian = systemTemplate.getContent();
					String querySql = "	select t.crimid,"
						+ "	       F_FORMATDATE(t.REWARDSTART) as 考核起日, "
						+ "	       F_FORMATDATE(t.REWARDEND) as 考核止日,	 "
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
					
					Map contMap = tbxfSentencealterationService.selectTbxfMapBySql(querySql);
					gaizaobiaoxian = MapUtil.replaceBracketContent(gaizaobiaoxian, contMap);
					gaizaobiaoxian = gaizaobiaoxian.replace("；", "；rn");

					if("6500".equals(provincecode)) {
						String huigailigong = systemModelService.getRecommendationContent("10362", user, request);
						huigailigong = huigailigong.replace("\n", "\\r\\n");
						map.put("huigailigong", huigailigong);
					}
					gaizaobiaoxian = MapUtil.formatFormString(gaizaobiaoxian);
					gaizaobiaoxian=gaizaobiaoxian.replace("rn", "\\r\\n");
					map.put("gaizaobiaoxian", gaizaobiaoxian);
				}
				if(tbxfMap!=null&&!tbxfMap.isEmpty()){
					String prisonterm = "";
					if (!StringNumberUtil.isNullOrEmpty(tbxfMap.get("SENTENCECHAGEINFO"))){
						prisonterm = StringNumberUtil.getTrimRtnStr(tbxfMap.get("SENTENCECHAGEINFO").toString().replace("，。", "。").replace("，；", "；"));
					}
					if (!StringNumberUtil.isNullOrEmpty(fb.getText6())){
						try {
							if(GkzxCommon.XINJIANG_PROVINCE.equals(provincecode)){
								map.put("anjianhao","["+fb.getText6().substring(0, 4)+"]新狱"+orgName+"减（假）字"+fb.getText6().substring(4)+"号");
							}
							map.put("parolenumber","("+fb.getText6().substring(0, 4)+")"+orgName+"第"+fb.getText6().substring(4)+"号");
							map.put("text31","("+fb.getText6().substring(0, 4)+")"+orgName+"第"+fb.getText6().substring(4)+"号");
							if(StringNumberUtil.belongTo(departid,guangdong)) {
								map.put("anjianhao1","("+fb.getText6().substring(0, 4)+")"+orgName+casetype+"第"+fb.getText6().substring(4)+"号");
								map.put("anjianhao2","("+fb.getText6().substring(0, 4)+")"+orgName+casetype+"第"+fb.getText6().substring(4)+"号");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					map.put("prisonterm", "    "+prisonterm);
					
					String xianxingqizhiri = "";
					if(tbxfMap.get("COURTCHANGETO")!=null){
						xianxingqizhiri = sdf.format(tbxfMap.get("COURTCHANGETO"));
					}
					map.put("xianxingqizhiri", xianxingqizhiri);
					map.put("zclhq", tbxfMap.get("REMUNERATION"));
					map.put("gsk", tbxfMap.get("WITHCASH"));
					map.put("hk", tbxfMap.get("REMITTANCE"));
					map.put("qtsr", tbxfMap.get("INCOME"));
					map.put("gwzc", tbxfMap.get("SHOPPING"));
					map.put("payeverymon", tbxfMap.get("PAYAVERAGEMONTHLY"));
					map.put("qtzc", tbxfMap.get("PAY"));
					map.put("balance",tbxfMap.get("OVERPLUS"));
				}
				try{
					if(StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getArrestdate())){
						map.put("text38", sdf.format(tbprisonerBaseCrime.getArrestdate()));
					}
					if(!StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getSentencestime())){
						map.put("text39", sdf.format(tbprisonerBaseCrime.getSentencestime()));
					}
				}catch(Exception e){e.printStackTrace();}
				
				map.put("criminalid", crimid);
				//陕西表单用
				map.put("suname", user.getName());
				map.put("cpdregisterdate", DateUtil.dateFormatForAip(new Date()));
				
				String applyvalue = crimid+","+tbprisonerBaseinfo.getName();
				request.setAttribute("applyvalue",applyvalue);
				request.setAttribute("forfeituretype", tbxfMap.get("FORFEITURETYPE"));
			}
			//
		}
		request.setAttribute("unitlevel", unitlevel);//罪犯所在部门级别,用于判断监区退回按钮（陕西）；
		request.setAttribute("tempid", tempid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("flowdefid", flowdefid);
		ModelAndView mav = null;
		View view =null;
		if(GkzxCommon.ONE.equals(ischeckseal)){
			view = new InternalResourceView("WEB-INF/JSP/aip/addCommuteOfJailCase.jsp");
		}else{
			view = new InternalResourceView("WEB-INF/JSP/aip/addCommuteOfJailCaseNoCheckSeal.jsp");
		}
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 跳转列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 * @throws IOException
	 * @throws ServletException
	 */	
	@RequestMapping(value = "/toNewMinutesOfTheMeeting")
	public ModelAndView toNewPage(HttpServletRequest request,HttpServletResponse response) {
		returnResourceMap(request);
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();
		JSONArray docconent = new JSONArray();
		String curyear = "";//年度
		String batch = "";//批次
		
		String shortname = user.getOrganization().getShortname();
		ModelAndView mav = null;
		Date date = new Date();
		String year = DateUtil.dateFormat(date, "yyyy");
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		String xtempid = request.getParameter("xtempid");
		String flowdraftids = request.getParameter("flowdraftids");
		request.setAttribute("year", year);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("xtempid", xtempid);
		request.setAttribute("shortname", shortname);
		request.setAttribute("flowdraftids", flowdraftids);

		Map<String, Object> map = new HashMap<String, Object>();

		FlowBaseOther other = null;
		// 判断大字段是否有内容 tbflowbaseother tbflowotherflow
		if (StringNumberUtil.notEmpty(flowdraftids)) {
			// sql查出clob内容
			//String sql = "SELECT T.docconent FROM tbflow_base_other T,tbflow_other_flow B WHERE T.otherid=B.otherid";
			
			//获取当前减刑批次信息
			HashMap batchmap = systemModelService.getCuryearBatch(departid);
			if(batchmap!=null){
				batchmap = (HashMap) MapUtil.turnKeyToLowerCase(batchmap);
				curyear = batchmap.get("curyear").toString();
				batch = batchmap.get("batch").toString();
			}
			//为选择 罪犯 直接取 保存过的(文本)大字段
			//通过 用户所在 部门 以及 批次年度查询出 会议记录内容
			Map meetMap = new HashMap();
			meetMap.put("curyear", curyear);
			meetMap.put("batch", batch);
			meetMap.put("orgid", user.getOrgid());
			meetMap.put("tempid", tempid);
			other = flowBaseOtherService.getMeetingRecord(meetMap);
			if(other!=null){
				//查询数据库保存的大字段是否是当前批次数据
				if(other.getText1().equals(curyear) && other.getText2().equals(batch)){
					docconent.add(other.getDocconent());
					String otherid = other.getOtherid().toString();
					request.setAttribute("formcontent", otherid);
				}
			}else{// other = null; CLOB大字段为空时取表单管理模板
				TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
				if (template != null) {
					docconent.add(template.getContent());
				}
				// 向表单放入显示的map集合
				map = systemResourceService.queryQualificationData(user, request);
				request.setAttribute("formDatajson", new JSONObject(map).toString());
			}
			//
		}
		request.setAttribute("formcontent", docconent.toString());

		View view = new InternalResourceView("WEB-INF/JSP/list.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	//办案
	@RequestMapping(value = "/commuteOfJailCaseOfNew")
	public ModelAndView commuteOfJailCaseOfNew(HttpServletRequest request){
		JSONArray docconent = new JSONArray();
		Map<String,Object> map = new HashMap<String,Object>();
		returnResourceMap(request);
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();
		
		String flowdraftid = request.getParameter("id");
		String menuid = request.getParameter("menuid");
		String tempid = request.getParameter("tempid");
		String closetype = request.getParameter("closetype");
		String flowdefid = request.getParameter("flowdefid");
		String fathermenuid = request.getParameter("fathermenuid");

		Map uvFlowMap = flowBaseService.findUvFlowByFlowdraftid(flowdraftid);
		String crimid = uvFlowMap.get("applyid").toString();
		String flowid = uvFlowMap.get("flowid").toString();
		String lastnodeid = uvFlowMap.get("nodeid").toString();
		
		Map<String,Object> tbxfMap = tbxfSentencealterationService.selectTbxfByCrimid(crimid);
		String orgid = tbxfMap.get("AREAID").toString();

		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ischeckseal = (jyconfig.getProperty("ischeckseal")== null?"":jyconfig.getProperty("ischeckseal"));
		String tianjinconde = jyconfig.getProperty(GkzxCommon.TIANJIN_CODE);
		String hebeicode = jyconfig.getProperty(GkzxCommon.HEBEI_CODE);
		String shanxi = jyconfig.getProperty(GkzxCommon.SHAN_XI)==null?"":jyconfig.getProperty(GkzxCommon.SHAN_XI);
		String provinceid = jyconfig.getProperty("provincecode")==null?"":jyconfig.getProperty("provincecode");

		//判断使用流程的表单
		String signcheckbiaodan = jyconfig.getProperty(GkzxCommon.SIGNCHECKBIAODAN)==null?"":jyconfig.getProperty(GkzxCommon.SIGNCHECKBIAODAN);
		request.setAttribute("signcheckbiaodan", signcheckbiaodan);
		
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
		request.setAttribute("ischeckseal", ischeckseal);
		
		String applyname = tbxfSentencealterationService.getCrimiNameByCrimid(crimid);
		request.setAttribute("applyname", applyname);
		//用于审核表意见弹出框的处理 
		if(GkzxCommon.SHANGHAI_PROVINCE.equals(provinceid)){
			request.setAttribute("opiontype", GkzxCommon.ONE);
		}
		
		Map paramMap = new HashMap();
		paramMap.put("flowdraftid", flowdraftid);
		paramMap.put("tempid", tempid);
		
		String content =  flowBaseOtherService.getDocconentByConditionTwo(paramMap);
		
		if(StringNumberUtil.notEmpty(content)){
			docconent.add(content);
		}else{
			if(GkzxCommon.SHANGHAI_PROVINCE.equals(provinceid)){
				//重写审核表显示用 zhenghui 2014.12.6 16:40
				map = ReturnShbMap(flowdraftid, crimid, request);
				docconent.add(map.get("docconent"));
			}else{
				Map paramMap3 = new HashMap();
				paramMap3.put("flowdraftid", flowdraftid);
				FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap3);
				String casetype = "";
				if(fb != null && StringNumberUtil.belongTo(departid, hebeicode)) {
					map.put("casenum", fb.getText6());
					map.put("jxjs_1", fb.getInt1());
					String title = "";
					title = user.getOrganization().getText1() + "\\r\\n";
					if(fb.getInt1()==0){
						casetype="减字";
						title = title + "提请罪犯减刑审核表";
					}else if(fb.getInt1()==1){
						casetype="假字";
						title = title + "提请罪犯假释审核表";
					}
					map.put("jxojs", title);
				} else if(fb != null){
					map.put("casenum", fb.getText6());
					map.put("jxjs_1", fb.getInt1());
					if(fb.getInt1()==0){
						casetype="减字";
						map.put("jxojs", "罪犯减刑审核表");
					}else if(fb.getInt1()==1){
						casetype="假字";
						map.put("jxojs", "罪犯假释审核表");
					}
				}
				
				TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
				TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService.getCrimeByCrimid(crimid);
				
				TbsysTemplate template = new TbsysTemplate();
				if(StringNumberUtil.belongTo(departid, hebeicode)) {
					if(!StringNumberUtil.isNullOrEmpty(tbxfMap.get("PUNISHMENTYEAR")) && !tbxfMap.get("PUNISHMENTYEAR").equals(GkzxCommon.XINGQI_WUQI) && !tbxfMap.get("PUNISHMENTYEAR").equals(GkzxCommon.XINGQI_SIHUAN)) {
						template = systemModelService.getTemplateAndDepartid("JXJS_JXJSSHB_YQ", departid);
					} else {
						template = systemModelService.getTemplateAndDepartid(tempid, departid);
					}
				} else {
					template = systemModelService.getTemplateAndDepartid(tempid, departid);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年M月d日");
				
				String orgName = ""; 
				if (template != null){
					docconent.add(template.getContent());
				}
				if(tbprisonerBaseinfo!=null){
					if(StringNumberUtil.belongTo(departid, hebeicode)) {
						map.put("departid",user.getOrganization().getName());//单位id
					} else {
						String deptid = tbprisonerBaseinfo.getDepartid();
						SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);
						if(org!=null){
							map.put("departid",org.getName());
							orgName = org.getShortname();
						}
					}
					//山西 阳泉 审核表 单位：对应的是部门的全称(如：阳泉二监第一监区第二分监区)
					map.put("orgname", user.getOrganization().getFullname());
					map.put("cbiname",tbprisonerBaseinfo.getName());
					map.put("cbitruename",tbprisonerBaseinfo.getUsedname());
					map.put("cbigendername",tbprisonerBaseinfo.getGender());
					map.put("caieducation",tbprisonerBaseinfo.getEducation());
					// 山西 ：籍贯显示字段 
					 
					if (shanxi.contains(departid)) {
						map.put("cbinativenamedetail",tbprisonerBaseinfo.getBirtharea());
						//监狱名称+刑种 =组织减刑假释审核表标题
						map.put("jxjsshb_title", map.get("departid")+""+tbprisonerBaseCrime.getPunishmenttype());
					}else{
						map.put("cbinativenamedetail",tbprisonerBaseinfo.getCountryarea());//2015.1.14 by luan  数据交换时交换进Countryarea字段
					}
					
					map.put("cbination",tbprisonerBaseinfo.getNation());
					map.put("cbibirthday",sdf.format(tbprisonerBaseinfo.getBirthday()));
					map.put("cbihomeaddress",tbprisonerBaseinfo.getFamilyaddress());
					map.put("idnumber",tbprisonerBaseinfo.getIdnumber());
					map.put("text35",tbprisonerBaseinfo.getRewardpunish());
				}
				if(tbprisonerBaseCrime!=null){
					Date rujiandate = tbprisonerBaseCrime.getInprisondate();
					map.put("criofficiallyplacedate",DateUtil.dateFormatForAip(rujiandate));
					map.put("text32", "执行日期");
					map.put("zhixingdate", sdf.format(tbprisonerBaseCrime.getExecutiondate()));
					map.put("judgedate", sdf.format(tbprisonerBaseCrime.getJudgedate()));
					if(tbprisonerBaseCrime.getDetaindate() != null && !"".equals(tbprisonerBaseCrime.getDetaindate())){
						map.put("text34", sdf.format(tbprisonerBaseCrime.getDetaindate()));
					}
					
					if(StringNumberUtil.belongTo(departid, tianjinconde) || StringNumberUtil.belongTo(departid, hebeicode)) {
						map.put("cjicourtname", StringNumberUtil.getDefaultStringOnNull(tbprisonerBaseCrime.getJudgmentname(),""));//法院
					} else {
						map.put("cjicourtname", StringNumberUtil.getDefaultStringOnNull(tbprisonerBaseCrime.getJudgmentname(),"") + StringNumberUtil.getDefaultStringOnNull(tbprisonerBaseCrime.getJudgmentshort(),""));//法院
					}
					String xingqi = tbprisonerBaseCrime.getPunishmentyear()+","+tbprisonerBaseCrime.getPunishmentmonth()+","+tbprisonerBaseCrime.getPunishmentday();
						xingqi = StringNumberUtil.getXingqi(tbprisonerBaseCrime.getPunishmenttype(), xingqi);
					map.put("zhuxing", "xingqi");
					
					String boquan = tbprisonerBaseCrime.getLosepoweryear()+","+tbprisonerBaseCrime.getLosepowermonth()+","+tbprisonerBaseCrime.getLosepowereday();
						boquan = StringNumberUtil.getBoquan(xingqi);
					map.put("fujiaxing", boquan);// 剥夺年限
					
					map.put("anyouhuizong", tbprisonerBaseCrime.getCauseaction());
					String Sentencestime = "";
					if(tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_YOUQI) || tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_YOUQI_ZH)){
						Sentencestime += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getSentencestime())?"":"从" + sdf2.format(tbprisonerBaseCrime.getSentencestime());
						Sentencestime += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getSentenceetime())?"": "起\\r\\n至"+sdf2.format(tbprisonerBaseCrime.getSentenceetime())+"止";
						map.put("xingqiqizhi", Sentencestime);// 刑期
					} else {
//						if(departid.equals(GkzxCommon.TIANJIN)) {
						if(StringNumberUtil.belongTo(departid, tianjinconde)) {
							Sentencestime += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getSentencestime())?"":"从" + sdf2.format(tbprisonerBaseCrime.getSentencestime()) + "起";
							map.put("xingqiqizhi", Sentencestime);// 刑期
						} else {
							map.put("xingqiqizhi", "");
						}
					}
//					if(GkzxCommon.TIANJIN.equals(getLoginUser(request).getDepartid())) {
					if(StringNumberUtil.belongTo(getLoginUser(request).getDepartid(),tianjinconde)) {
						String fanzuishijian = DateUtil.dateFormat(tbprisonerBaseCrime.getCrimedate());
						if(fanzuishijian!=null && !"".equals(fanzuishijian)) {
							fanzuishijian = fanzuishijian.substring(0, 8);
						}
						map.put("fanzuishijian", fanzuishijian);// 天津犯罪时间精确到月
					} else {
						map.put("fanzuishijian", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getCrimedate()));
					}
					map.put("cjimulct", tbprisonerBaseCrime.getForfeit());
					map.put("fajinjiaonaqingkuang", tbprisonerBaseCrime.getPayment());
					map.put("cjipeichangjine", tbprisonerBaseCrime.getCompensation());
					map.put("cjmoneydisgorged", tbprisonerBaseCrime.getStolenmoney());
					map.put("paymentzk", tbprisonerBaseCrime.getReturnloot());
					map.put("paymentpc", tbprisonerBaseCrime.getFulfilcompensation());
					map.put("cjisequestrateproperty", tbprisonerBaseCrime.getForfeitureproperty());
					map.put("paymentcc", tbprisonerBaseCrime.getExpropriation());
					
					//刑种
					map.put("punishmenttype", tbprisonerBaseCrime.getPunishmenttype());
					if(tbprisonerBaseCrime.getCrimeface()!=null && !"".equals(tbprisonerBaseCrime.getCrimeface())) {
						map.put("fanzuishishi","    "+tbprisonerBaseCrime.getCrimeface().replace("&#13;&#10;", "\\r\\n").replace("rn", "\\r\\n").replace("\r\n", "\\r\\n"));
					} else {
						map.put("fanzuishishi","    "+tbprisonerBaseCrime.getCrimeface());
					}
					map.put("text9", sdf.format(new Date()));
					String gaizaobiaoxian = "";
					SystemTemplate systemTemplate = systemTemplateService.getSystemTemplateByCondition("9993",user.getPrison().getPorgid());
					if(systemTemplate!=null) gaizaobiaoxian = systemTemplate.getContent();
					String querySql = "	select t.crimid,"
						+ "	       F_FORMATDATE(t.REWARDSTART) as 考核起日, "
						+ "	       F_FORMATDATE(t.REWARDEND) as 考核止日,	 "
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
					
					Map contMap = tbxfSentencealterationService.selectTbxfMapBySql(querySql);
					gaizaobiaoxian = MapUtil.replaceBracketContent(gaizaobiaoxian, contMap);
	//				gaizaobiaoxian = gaizaobiaoxian.replaceAll("\\r\\n","&#13;&#10;");
					gaizaobiaoxian = MapUtil.formatFormString(gaizaobiaoxian);
					gaizaobiaoxian=gaizaobiaoxian.replace("rn", "\\r\\n");
					map.put("gaizaobiaoxian", gaizaobiaoxian);
				}
				if(tbxfMap!=null&&!tbxfMap.isEmpty()){
					String prisonterm = "";
					if (!StringNumberUtil.isNullOrEmpty(tbxfMap.get("SENTENCECHAGEINFO"))){
						prisonterm = StringNumberUtil.getTrimRtnStr(tbxfMap.get("SENTENCECHAGEINFO").toString());
					}
					if (!StringNumberUtil.isNullOrEmpty(fb.getText6())){
						try {
							map.put("parolenumber","("+fb.getText6().substring(0, 4)+")"+orgName+"第"+fb.getText6().substring(4)+"号");
							map.put("text31","("+fb.getText6().substring(0, 4)+")"+orgName+"第"+fb.getText6().substring(4)+"号");
						} catch (Exception e) {
						}
					}
					map.put("prisonterm", "    "+prisonterm);
					if(StringNumberUtil.belongTo(departid, hebeicode)) {
						String sentenceStartEnd = "    现刑期起止：";
						sentenceStartEnd = sentenceStartEnd + "刑期自" + sdf2.format(tbprisonerBaseCrime.getSentencestime()) + "起";
						if(tbxfMap.get("COURTCHANGETO")!=null) {
							sentenceStartEnd = sentenceStartEnd + "至" + sdf2.format(tbxfMap.get("COURTCHANGETO")) + "止";
						}
						if(prisonterm!=null && !"".equals(prisonterm)) {
							map.put("prisonterm", "    历次减刑情况："+prisonterm + "\\r\\n" + sentenceStartEnd + "。");
						}
					}
					map.put("zclhq", tbxfMap.get("REMUNERATION"));
					map.put("gsk", tbxfMap.get("WITHCASH"));
					map.put("hk", tbxfMap.get("REMITTANCE"));
					map.put("qtsr", tbxfMap.get("INCOME"));
					map.put("gwzc", tbxfMap.get("SHOPPING"));
					map.put("qtzc", tbxfMap.get("PAY"));
					map.put("balance",tbxfMap.get("OVERPLUS"));
				}
				map.put("text38", sdf.format(tbprisonerBaseCrime.getArrestdate()));
				map.put("text39", sdf.format(tbprisonerBaseCrime.getSentencestime()));
				map.put("criminalid", crimid);
				String applyvalue = crimid+","+tbprisonerBaseinfo.getName();
				request.setAttribute("applyvalue",applyvalue);
				request.setAttribute("forfeituretype", tbxfMap.get("FORFEITURETYPE"));
			}
			//
		}
		request.setAttribute("tempid", tempid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("flowdefid", flowdefid);
		ModelAndView mav = null;
		View view =null;
		if(GkzxCommon.ONE.equals(ischeckseal)){
			view = new InternalResourceView("WEB-INF/JSP/aip/addCommuteOfJailCase.jsp");
		}else{
			view = new InternalResourceView("WEB-INF/JSP/aip/addCommuteOfJailCaseNoCheckSeal.jsp");
		}
		mav = new ModelAndView(view);
		return mav;
	}
	
	//办案
	@RequestMapping(value = "/commuteOfJailCase_sx")
	public ModelAndView showAddFrom_sx(HttpServletRequest request){
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

		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ischeckseal = (jyconfig.getProperty("ischeckseal")== null?"":jyconfig.getProperty("ischeckseal"));
		String tianjinconde = jyconfig.getProperty(GkzxCommon.TIANJIN_CODE);
		String hebeicode = jyconfig.getProperty(GkzxCommon.HEBEI_CODE);
		//山西 宁夏 特殊和其他公司的区别
		String shanxi = jyconfig.getProperty(GkzxCommon.SHAN_XI)==null?"":jyconfig.getProperty(GkzxCommon.SHAN_XI);
		//判断是否受 呈批表的控制(宁夏不适用呈批表：mushuhong)
		String chengpibiao = jyconfig.getProperty(GkzxCommon.CHENGPIBIAO)==null?"":jyconfig.getProperty(GkzxCommon.CHENGPIBIAO);
		//组织宁夏标题
		String ningxia = jyconfig.getProperty(GkzxCommon.NINGXIA)==null?"":jyconfig.getProperty(GkzxCommon.NINGXIA);
		//判断使用流程的表单
		String signcheckbiaodan = jyconfig.getProperty(GkzxCommon.SIGNCHECKBIAODAN)==null?"":jyconfig.getProperty(GkzxCommon.SIGNCHECKBIAODAN);
		request.setAttribute("signcheckbiaodan", signcheckbiaodan);
		//判断 更新签章进程的流程自定义id
		String flowdefids = jyconfig.getProperty(GkzxCommon.FLOWDEFIDS)==null?"":jyconfig.getProperty(GkzxCommon.FLOWDEFIDS);
		String provinceid = request.getParameter("provinceid");
		String nodeid = request.getParameter("nodeid");
		String days = request.getParameter("days");
	    if (shanxi.contains(departid)) {
			request.setAttribute("shanxi", 1);
		}else{
			request.setAttribute("shanxi", 0);
		}
	    if (!"".equals(chengpibiao)) {
			request.setAttribute("chengpibiao", chengpibiao);
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
		request.setAttribute("ischeckseal", ischeckseal);
		
		if(StringNumberUtil.notEmpty(provinceid)){
			request.setAttribute("provinceid", provinceid);
		}
		
		if(StringNumberUtil.notEmpty(nodeid)){
			request.setAttribute("nodeid", nodeid);
		}
		
		if(StringNumberUtil.notEmpty(days)){
			request.setAttribute("days", days);
		}
		
		String applyname = tbxfSentencealterationService.getCrimiNameByCrimid(crimid);
		request.setAttribute("applyname", applyname);
		
		Map paramMap = new HashMap();
		paramMap.put("flowdraftid", flowdraftid);
		paramMap.put("tempid", tempid);
		
		String content =  flowBaseOtherService.getDocconentByConditionTwo(paramMap);
		
		if(StringNumberUtil.notEmpty(content)){
			docconent.add(content);
		}else{
			Map paramMap3 = new HashMap();
			paramMap3.put("flowdraftid", flowdraftid);
			FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap3);
			String casetype = "";
			if(fb != null && StringNumberUtil.belongTo(departid, hebeicode)) {
				map.put("casenum", fb.getText6());
				map.put("jxjs_1", fb.getInt1());
				String title = "";
				title = user.getOrganization().getText1() + "\\r\\n";
				if(fb.getInt1()==0){
					casetype="减字";
					title = title + "提请罪犯减刑审核表";
				}else if(fb.getInt1()==1){
					casetype="假字";
					title = title + "提请罪犯假释审核表";
				}
				map.put("jxojs", title);
			} else if(fb != null){
				map.put("casenum", fb.getText6());
				map.put("jxjs_1", fb.getInt1());
				if(fb.getInt1()==0){
					casetype="减字";
					map.put("jxojs", "罪犯减刑审核表");
				}else if(fb.getInt1()==1){
					casetype="假字";
					map.put("jxojs", "罪犯假释审核表");
				}
			}
			
			TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
			TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService.getCrimeByCrimid(crimid);
			Map<String,Object> tbxfMap = tbxfSentencealterationService.selectTbxfByCrimid(crimid);
			
			TbsysTemplate template = new TbsysTemplate();
			if(StringNumberUtil.belongTo(departid, hebeicode)) {
				if(!StringNumberUtil.isNullOrEmpty(tbxfMap.get("PUNISHMENTYEAR")) && !tbxfMap.get("PUNISHMENTYEAR").equals(GkzxCommon.XINGQI_WUQI) && !tbxfMap.get("PUNISHMENTYEAR").equals(GkzxCommon.XINGQI_SIHUAN)) {
					template = systemModelService.getTemplateAndDepartid("JXJS_JXJSSHB_YQ", departid);
				} else {
					template = systemModelService.getTemplateAndDepartid(tempid, departid);
				}
			} else {
				template = systemModelService.getTemplateAndDepartid(tempid, departid);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年M月d日");
			
			String orgName = ""; 
			if (template != null){
				docconent.add(template.getContent());
			}
			if(tbprisonerBaseinfo!=null){
				if(StringNumberUtil.belongTo(departid, hebeicode)) {
					map.put("departid",user.getOrganization().getName());//单位id
				} else {
					String deptid = tbprisonerBaseinfo.getDepartid();
					SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);
					if(org!=null){
						map.put("departid",org.getName());
						orgName = org.getShortname();
					}
				}
				//山西 阳泉 审核表 单位：对应的是部门的全称(如：阳泉二监第一监区第二分监区)
				map.put("orgname", user.getOrganization().getFullname());
				map.put("cbiname",tbprisonerBaseinfo.getName());
				map.put("cbitruename",tbprisonerBaseinfo.getUsedname());
				map.put("cbigendername",tbprisonerBaseinfo.getGender());
				map.put("caieducation",tbprisonerBaseinfo.getEducation());
				// 宁夏（shanxi） ：籍贯显示字段 
				if (shanxi.contains(departid)) {
					map.put("cbinativenamedetail",tbprisonerBaseinfo.getBirtharea());
					//省份+监狱名称+刑种 =组织减刑假释审核表标题
					map.put("jxjsshb_title", user.getAddress()+map.get("departid")+GkzxCommon.NX_TITLE);
				}else{
					map.put("cbinativenamedetail",tbprisonerBaseinfo.getCountryarea());//2015.1.14 by luan  数据交换时交换进Countryarea字段
				}
				//宁夏 显示罪犯编号 
				map.put("crimid",crimid);
				map.put("cbination",tbprisonerBaseinfo.getNation());
				map.put("cbibirthday",sdf.format(tbprisonerBaseinfo.getBirthday()));
				map.put("cbihomeaddress",tbprisonerBaseinfo.getFamilyaddress());
				map.put("idnumber",tbprisonerBaseinfo.getIdnumber());
				map.put("text35",tbprisonerBaseinfo.getRewardpunish());
			}
			if(tbprisonerBaseCrime!=null){
				Date rujiandate = tbprisonerBaseCrime.getInprisondate();
				String rujianriqistr = "";
				if(rujiandate!=null){
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(rujiandate);
					rujianriqistr = calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月";
				}
				map.put("criofficiallyplacedate",DateUtil.dateFormatForAip(rujiandate));
				map.put("text32", "执行日期");
				map.put("zhixingdate", sdf.format(tbprisonerBaseCrime.getExecutiondate()));
				map.put("judgedate", sdf.format(tbprisonerBaseCrime.getJudgedate()));
				if(tbprisonerBaseCrime.getDetaindate() != null && !"".equals(tbprisonerBaseCrime.getDetaindate())){
					map.put("text34", sdf.format(tbprisonerBaseCrime.getDetaindate()));
				}
				
//				map.put("cjicourtname", tbprisonerBaseCrime.getJudgmentname());//法院
//				if(departid.equals(GkzxCommon.TIANJIN)) {
				if(StringNumberUtil.belongTo(departid, tianjinconde) || StringNumberUtil.belongTo(departid, hebeicode)) {
					map.put("cjicourtname", StringNumberUtil.getDefaultStringOnNull(tbprisonerBaseCrime.getJudgmentname(),""));//法院
				} else {
					map.put("cjicourtname", StringNumberUtil.getDefaultStringOnNull(tbprisonerBaseCrime.getJudgmentname(),"") + StringNumberUtil.getDefaultStringOnNull(tbprisonerBaseCrime.getJudgmentshort(),""));//法院
				}
//				if(tbprisonerBaseCrime.getPunishmenttype()!=GkzxCommon.XINGQI_YOUQI){
//					map.put("zhuxing", tbprisonerBaseCrime.getPunishmentyear()+"年"+tbprisonerBaseCrime.getPunishmentmonth()+"个月"+tbprisonerBaseCrime.getPunishmentday()+"天");// 刑期
//				}else if(tbprisonerBaseCrime.getPunishmenttype()!=GkzxCommon.XINGQI_SIHUAN){
//					map.put("zhuxing", "死缓");
//				}else if(tbprisonerBaseCrime.getPunishmenttype()!=GkzxCommon.XINGQI_WUQI){
//					map.put("zhuxing", "无期");
//				}else {
//					map.put("zhuxing", "死刑");
//				}
				if(tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_YOUQI) || tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_YOUQI_ZH)){
					String strPunishment = StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getPunishmentyear())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getPunishmentyear().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getPunishmentyear().toString()) + "年";
					strPunishment += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getPunishmentmonth())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getPunishmentmonth().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getPunishmentmonth().toString()) + "个月";
					strPunishment += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getPunishmentday())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getPunishmentday().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getPunishmentday().toString()) + "天";
					if (!StringNumberUtil.isNullOrEmpty(strPunishment)) {
						//山西 刑期前面 不加 刑种
						if (shanxi.contains(departid)) {
							map.put("zhuxing", strPunishment);// 刑期
						}else{
							map.put("zhuxing", GkzxCommon.XINGQI_YOUQI_ZH + strPunishment);// 刑期
						}
					}
				}else if(tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_SIHUAN) || tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_SIHUAN_ZH)){
					map.put("zhuxing", GkzxCommon.XINGQI_SIHUAN_ZH);
				}else if(tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_WUQI) || tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_WUQI_ZH)){
					map.put("zhuxing", GkzxCommon.XINGQI_WUQI_ZH);
				}else {
					map.put("zhuxing", "");
				}
//				map.put("fujiaxing", tbprisonerBaseCrime.getLosepoweryear()+"年"+tbprisonerBaseCrime.getLosepowermonth()+"个月"+tbprisonerBaseCrime.getLosepowereday()+"天");// 剥夺年限
				String strLosepower = "";
				if(!StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getLosepoweryear()) && (tbprisonerBaseCrime.getLosepoweryear().toString().equals(GkzxCommon.LOSEPOWER_97) || tbprisonerBaseCrime.getLosepoweryear().toString().equals(GkzxCommon.LOSEPOWER_99))){
					strLosepower = GkzxCommon.LOSEPOWER_ZH;
				} else {
					//山西 附加刑 剥夺政治权利+原判罚金+没收财产
					if (shanxi.contains(departid)) {
						strLosepower = StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getLosepoweryear())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getLosepoweryear().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getLosepoweryear().toString()) + "年";
						strLosepower += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getLosepowermonth())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getLosepowermonth().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getLosepowermonth().toString()) + "个月";
						strLosepower += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getLosepowereday())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getLosepowereday().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getLosepowereday().toString()) + "天";
					    if (!StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getForfeit())) {
					    	//判断 strLosepower == "" 那么不加，否则加上，
					    	if (!"".equals(strLosepower)) {
					    		strLosepower += "，罚金:"+tbprisonerBaseCrime.getForfeit().replace("元", "")+"元";
							}else{
								strLosepower += "罚金:"+tbprisonerBaseCrime.getForfeit().replace("元", "")+"元";
							}
						}
					    if (!StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getForfeitureproperty())) {
					    	//判断 strLosepower == "" 那么不加，否则加上，
					    	if (!"".equals(strLosepower)) {
					    		strLosepower += "，没收财产:"+tbprisonerBaseCrime.getForfeit().replace("元", "")+"元";
							}else{
								strLosepower += "没收财产:"+tbprisonerBaseCrime.getForfeit().replace("元", "")+"元";
							}
						}
					}else{
						strLosepower = StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getLosepoweryear())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getLosepoweryear().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getLosepoweryear().toString()) + "年";
						strLosepower += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getLosepowermonth())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getLosepowermonth().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getLosepowermonth().toString()) + "个月";
						strLosepower += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getLosepowereday())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getLosepowereday().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getLosepowereday().toString()) + "天";
					}
				}
				map.put("fujiaxing", strLosepower);// 剥夺年限
//				map.put("anyouhuizong", tbprisonerBaseCrime.getMaincase());
				map.put("anyouhuizong", tbprisonerBaseCrime.getCauseaction());
//				map.put("xingqiqizhi","从"+sdf2.format(tbprisonerBaseCrime.getSentencestime())+"至"+sdf2.format(tbprisonerBaseCrime.getSentenceetime()));
				String Sentencestime = "";
				if(tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_YOUQI) || tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_YOUQI_ZH)){
					Sentencestime += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getSentencestime())?"":"从" + sdf2.format(tbprisonerBaseCrime.getSentencestime());
					Sentencestime += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getSentenceetime())?"": "起\\r\\n至"+sdf2.format(tbprisonerBaseCrime.getSentenceetime())+"止";
					map.put("xingqiqizhi", Sentencestime);// 刑期
				} else {
//					if(departid.equals(GkzxCommon.TIANJIN)) {
					if(StringNumberUtil.belongTo(departid, tianjinconde)) {
						Sentencestime += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getSentencestime())?"":"从" + sdf2.format(tbprisonerBaseCrime.getSentencestime()) + "起";
						map.put("xingqiqizhi", Sentencestime);// 刑期
					} else {
						map.put("xingqiqizhi", "");
					}
				}
//				if(GkzxCommon.TIANJIN.equals(getLoginUser(request).getDepartid())) {
				if(StringNumberUtil.belongTo(getLoginUser(request).getDepartid(),tianjinconde)) {
					String fanzuishijian = DateUtil.dateFormat(tbprisonerBaseCrime.getCrimedate());
					if(fanzuishijian!=null && !"".equals(fanzuishijian)) {
						fanzuishijian = fanzuishijian.substring(0, 8);
					}
					map.put("fanzuishijian", fanzuishijian);// 天津犯罪时间精确到月
				} else {
					map.put("fanzuishijian", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getCrimedate()));
				}
				map.put("cjimulct", tbprisonerBaseCrime.getForfeit());
				map.put("fajinjiaonaqingkuang", tbprisonerBaseCrime.getPayment());
				map.put("cjipeichangjine", tbprisonerBaseCrime.getCompensation());
				map.put("cjmoneydisgorged", tbprisonerBaseCrime.getStolenmoney());
				map.put("paymentzk", tbprisonerBaseCrime.getReturnloot());
				map.put("paymentpc", tbprisonerBaseCrime.getFulfilcompensation());
				map.put("cjisequestrateproperty", tbprisonerBaseCrime.getForfeitureproperty());
				map.put("paymentcc", tbprisonerBaseCrime.getExpropriation());
				//山西 ：三类罪犯 需要 在备注中添加三类罪犯的描述
				 
				if (shanxi.contains(departid)) {
					if (tbprisonerBaseCrime.getSanclassstatus() != null && "1".equals(tbprisonerBaseCrime.getSanclassstatus()) ) {
						map.put("text32", GkzxCommon.SANLEIZUIFAN);
					}else {
						map.put("text32", "");
					}
				}
				
				//刑种
				map.put("punishmenttype", tbprisonerBaseCrime.getPunishmenttype());
				if(tbprisonerBaseCrime.getCrimeface()!=null && !"".equals(tbprisonerBaseCrime.getCrimeface())) {
					map.put("fanzuishishi","    "+tbprisonerBaseCrime.getCrimeface().replace("&#13;&#10;", "\\r\\n").replace("rn", "\\r\\n").replace("\r\n", "\\r\\n").replace("“", "").replace("”", "").replace("\n", ""));
				} else {
					map.put("fanzuishishi","    "+tbprisonerBaseCrime.getCrimeface());
				}
				map.put("text9", sdf.format(new Date()));
//				String gaizaobiaoxian = "";
				SystemTemplate systemTemplate = systemTemplateService.getSystemTemplateByCondition("9993",user.getPrison().getPorgid());
//				if(systemTemplate!=null) gaizaobiaoxian = systemTemplate.getContent();
//				String querySql = "select t.crimid,t.REWARDSTART 考核起日,t.REWARDEND 考核止日," +
//										 "nvl(t.REWARDINFO,'') 奖励情况,nvl(t.PUNISHINFO,'')  惩罚情况 " +
//										  "from TBXF_SENTENCEALTERATION t where t.crimid='"+crimid+"'";

			}
			if(tbxfMap!=null&&!tbxfMap.isEmpty()){
				String prisonterm = "";
				if (!StringNumberUtil.isNullOrEmpty(tbxfMap.get("SENTENCECHAGEINFO"))){
					prisonterm = StringNumberUtil.getTrimRtnStr(tbxfMap.get("SENTENCECHAGEINFO").toString());
				}
				if (!StringNumberUtil.isNullOrEmpty(fb.getText6())){
					try {
						map.put("parolenumber","("+fb.getText6().substring(0, 4)+")"+orgName+"第"+fb.getText6().substring(4)+"号");
						map.put("text31","("+fb.getText6().substring(0, 4)+")"+orgName+"第"+fb.getText6().substring(4)+"号");
					} catch (Exception e) {
					}
				}
				map.put("prisonterm", "    "+prisonterm);
				if(StringNumberUtil.belongTo(departid, hebeicode)) {
					String sentenceStartEnd = "    现刑期起止：";
					sentenceStartEnd = sentenceStartEnd + "刑期自" + sdf2.format(tbprisonerBaseCrime.getSentencestime()) + "起";
					if(tbxfMap.get("COURTCHANGETO")!=null) {
						sentenceStartEnd = sentenceStartEnd + "至" + sdf2.format(tbxfMap.get("COURTCHANGETO")) + "止";
					}
					if(prisonterm!=null && !"".equals(prisonterm)) {
						map.put("prisonterm", "    历次减刑情况："+prisonterm + "\\r\\n" + sentenceStartEnd + "。");
					}
				}
				map.put("zclhq", tbxfMap.get("REMUNERATION"));
				map.put("gsk", tbxfMap.get("WITHCASH"));
				map.put("hk", tbxfMap.get("REMITTANCE"));
				map.put("qtsr", tbxfMap.get("INCOME"));
				map.put("gwzc", tbxfMap.get("SHOPPING"));
				map.put("qtzc", tbxfMap.get("PAY"));
				map.put("balance",tbxfMap.get("OVERPLUS"));
			}
			map.put("text38", sdf.format(tbprisonerBaseCrime.getArrestdate()));
			map.put("text39", sdf.format(tbprisonerBaseCrime.getSentencestime()));
			map.put("criminalid", crimid);
			String applyvalue = crimid+","+tbprisonerBaseinfo.getName();
			request.setAttribute("applyvalue",applyvalue);
			
			//注掉部分为上海重写审核表显示用 zhenghui 2014.12.6 16:40
//			map = ReturnShbMap(flowdraftid, crimid, request);
//			docconent.add(map.get("docconent"));
		}
		request.setAttribute("tempid", tempid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("flowdefid", flowdefid);
		ModelAndView mav = null;
		View view =null;
		if(GkzxCommon.ONE.equals(ischeckseal)){
			view = new InternalResourceView("WEB-INF/JSP/aip/addCommuteOfJailCase.jsp");
		}else{
			view = new InternalResourceView("WEB-INF/JSP/aip/addCommuteOfJailCaseNoCheckSeal.jsp");
		}
		mav = new ModelAndView(view);
		return mav;
	}
	/*
	 * 处理审核表表单显示
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> ReturnShbMap(String flowdraftid ,String crimid ,HttpServletRequest request){
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		Map<String,Object> map = new HashMap<String,Object>();
		map = tbxfSentencealterationService.getParoleByCrimid(crimid,flowdraftid,user,request);
		request.setAttribute("applyvalue",map.get("applyvalue"));
		return map;
	}
	
	//办案
	@RequestMapping(value = "/commuteOfJianBanRenJailCase")
	public ModelAndView commuteOfJianBanRenJailCase(HttpServletRequest request){
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
		
		//---------------------------
		
		request.setAttribute("fathermenuid", fathermenuid);
		request.setAttribute("applyid", crimid);
		request.setAttribute("orgid", orgid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("lastnodeid", lastnodeid);
		request.setAttribute("menuid", menuid);
		
		
		String applyname = tbxfSentencealterationService.getCrimiNameByCrimid(crimid);
		request.setAttribute("applyname", applyname);
		
		Map paramMap = new HashMap();
		String content = "";
		paramMap.put("tempid", tempid);
		paramMap.put("flowdraftid", flowdraftid);
		content =  flowBaseOtherService.getDocconentByFlowdraftid(paramMap);
		
		if(!StringNumberUtil.notEmpty(flowid)){
			Map temPara = new HashMap();
			temPara.put("flowdraftid", flowdraftid);
			flowid = uvFlowService.getFlowidByFlowdraftid(temPara);
		}
		
		//------------------
		
		if(StringNumberUtil.notEmpty(content)){
			docconent.add(content);
			Map paramMap2 = new HashMap();
			paramMap2.put("flowdraftid", flowdraftid);
			FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap2);
			
			map.put("fajinjiaonaqingkuang", fb.getText1());
			map.put("paymentzk", fb.getText2());
			map.put("paymentpc", fb.getText3());
			map.put("paymentcc", fb.getText4());
			map.put("gaizaobiaoxian", fb.getText5());
			map.put("casenum", fb.getText6());
			map.put("jxjs_1", fb.getInt1());
			map.put("jxjs_2", fb.getInt2());
			map.put("jxjs_3", fb.getInt3());
			map.put("jxjs_4", fb.getInt4());
			map.put("jxjs_5", fb.getInt5());
			
			docconent.add(content);
//			Map paramMap2 = new HashMap();
//			paramMap2.put("flowdraftid", flowdraftid);
//			FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap2);
//			
//			map.put("fajinjiaonaqingkuang", fb.getText1());
//			map.put("paymentzk", fb.getText2());
//			map.put("paymentpc", fb.getText3());
//			map.put("paymentcc", fb.getText4());
//			map.put("gaizaobiaoxian", fb.getText5());
//			map.put("casenum", fb.getText6());
//			map.put("jxjs_1", fb.getInt1());
//			map.put("jxjs_2", fb.getInt2());
//			map.put("jxjs_3", fb.getInt3());
//			map.put("jxjs_4", fb.getInt4());
//			map.put("jxjs_5", fb.getInt5());	
			
//			request.setAttribute("formDatajson", new JSONObject(map).toString());
			
			
		}else{
			Map paramMap3 = new HashMap();
			paramMap3.put("flowdraftid", flowdraftid);
			FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap3);
			if(fb != null){
				map.put("casenum", fb.getText6());
			}
			
			TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
			TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService.getCrimeByCrimid(crimid);
			Map<String,Object> tbxfMap = tbxfSentencealterationService.selectTbxfByCrimid(crimid);
			
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年M月d日");
			
			String orgName = ""; 
			if (template != null) {
				map.put("text34",template.getTempname());
				docconent.add(template.getContent());
			}
			if(tbprisonerBaseinfo!=null){
				String deptid = tbprisonerBaseinfo.getDepartid();
				SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);
				if(org!=null){
					map.put("departid",org.getName());
					orgName = org.getName();
				}
				map.put("cbiname",tbprisonerBaseinfo.getName());
				map.put("cbitruename",tbprisonerBaseinfo.getUsedname());
				map.put("cbigendername",tbprisonerBaseinfo.getGender());
				map.put("caieducation",tbprisonerBaseinfo.getEducation());
				map.put("cbinativenamedetail",tbprisonerBaseinfo.getCountryarea());//2015.1.14 by luan  数据交换时交换进Countryarea字段
				map.put("cbination",tbprisonerBaseinfo.getNation());
				map.put("cbibirthday",sdf.format(tbprisonerBaseinfo.getBirthday()));
				map.put("cbihomeaddress",tbprisonerBaseinfo.getFamilyaddress());
			}
			if(tbprisonerBaseCrime!=null){
				map.put("criofficiallyplacedate",sdf.format(tbprisonerBaseCrime.getInprisondate()));
				map.put("text32", "执行日期");
				map.put("zhixingdate", sdf.format(tbprisonerBaseCrime.getExecutiondate()));
//				map.put("cjicourtname", tbprisonerBaseCrime.getJudgmentname());//法院
				map.put("cjicourtname", StringNumberUtil.getDefaultStringOnNull(tbprisonerBaseCrime.getJudgmentname(),"") + StringNumberUtil.getDefaultStringOnNull(tbprisonerBaseCrime.getJudgmentshort(),""));//法院
//				if(tbprisonerBaseCrime.getPunishmenttype()!=GkzxCommon.XINGQI_YOUQI){
//					map.put("zhuxing", tbprisonerBaseCrime.getPunishmentyear()+"年"+tbprisonerBaseCrime.getPunishmentmonth()+"个月"+tbprisonerBaseCrime.getPunishmentday()+"天");// 刑期
//				}else if(tbprisonerBaseCrime.getPunishmenttype()!=GkzxCommon.XINGQI_SIHUAN){
//					map.put("zhuxing", "死缓");
//				}else if(tbprisonerBaseCrime.getPunishmenttype()!=GkzxCommon.XINGQI_WUQI){
//					map.put("zhuxing", "无期");
//				}else {
//					map.put("zhuxing", "死刑");
//				}
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
//				map.put("fujiaxing", tbprisonerBaseCrime.getLosepoweryear()+"年"+tbprisonerBaseCrime.getLosepowermonth()+"个月"+tbprisonerBaseCrime.getLosepowereday()+"天");// 剥夺年限
				String strLosepower = "";
				if(!StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getLosepoweryear()) && (tbprisonerBaseCrime.getLosepoweryear().toString().equals(GkzxCommon.LOSEPOWER_97) || tbprisonerBaseCrime.getLosepoweryear().toString().equals(GkzxCommon.LOSEPOWER_99))){
					strLosepower = GkzxCommon.LOSEPOWER_ZH;
				} else {
					strLosepower = StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getLosepoweryear())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getLosepoweryear().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getLosepoweryear().toString()) + "年";
					strLosepower += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getLosepowermonth())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getLosepowermonth().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getLosepowermonth().toString()) + "个月";
					strLosepower += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getLosepowereday())?"":GkzxCommon.ZERO.equals(tbprisonerBaseCrime.getLosepowereday().toString())?"":StringNumberUtil.digit2speech(tbprisonerBaseCrime.getLosepowereday().toString()) + "天";
				}
				map.put("fujiaxing", strLosepower);// 剥夺年限
//				map.put("anyouhuizong", tbprisonerBaseCrime.getMaincase());
				map.put("anyouhuizong", tbprisonerBaseCrime.getCauseaction());
//				map.put("xingqiqizhi","从"+sdf2.format(tbprisonerBaseCrime.getSentencestime())+"至"+sdf2.format(tbprisonerBaseCrime.getSentenceetime()));
				String Sentencestime = "";
				if(tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_YOUQI) || tbprisonerBaseCrime.getPunishmenttype().equals(GkzxCommon.XINGQI_YOUQI_ZH)){
					Sentencestime += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getSentencestime())?"":"从" + sdf2.format(tbprisonerBaseCrime.getSentencestime());
					Sentencestime += StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime.getSentenceetime())?"": "起至"+sdf2.format(tbprisonerBaseCrime.getSentenceetime());
					map.put("xingqiqizhi", Sentencestime);// 刑期
				} else {
					map.put("xingqiqizhi", "");// 刑期
				}
				map.put("fanzuishijian", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getCrimedate()));
				map.put("cjimulct", tbprisonerBaseCrime.getForfeit());
				map.put("fajinjiaonaqingkuang", tbprisonerBaseCrime.getPayment());
				map.put("cjipeichangjine", tbprisonerBaseCrime.getCompensation());
				map.put("cjmoneydisgorged", tbprisonerBaseCrime.getStolenmoney());
				map.put("text33", "退回赃款");
				map.put("paymentzk", tbprisonerBaseCrime.getReturnloot());
				map.put("paymentpc", tbprisonerBaseCrime.getFulfilcompensation());
				map.put("cjisequestrateproperty", tbprisonerBaseCrime.getForfeitureproperty());
				map.put("paymentcc", tbprisonerBaseCrime.getExpropriation());
				map.put("fanzuishishi",tbprisonerBaseCrime.getCrimeface()==null?"":"    "+tbprisonerBaseCrime.getCrimeface().replace("&#13;&#10;", "\\r\\n").replace("\r\n", "\\r\\n").replace("rn", "\\r\\n"));
				map.put("text9", sdf.format(new Date()));
				String gaizaobiaoxian = systemTemplateService.getSysTemplateInfoByTempid("9993",null);
				map.put("gaizaobiaoxian", gaizaobiaoxian);
			}
			
			String abc = fb.getText6();
//			System.out.println(abc.substring(0,4));
//			System.out.println(abc.substring(4));
			
			if(tbxfMap!=null&&!tbxfMap.isEmpty()){
				String prisonterm = "";
				if (!StringNumberUtil.isNullOrEmpty(tbxfMap.get("SENTENCECHAGEINFO"))){
					prisonterm = StringNumberUtil.getTrimRtnStr(tbxfMap.get("SENTENCECHAGEINFO").toString());
				}
				if (!StringNumberUtil.isNullOrEmpty(fb.getText6())){
					try {
						map.put("parolenumber","("+fb.getText6().substring(0, 4)+")"+orgName+"第"+fb.getText6().substring(4)+"号");
					} catch (Exception e) {
					}
				}
				map.put("prisonterm", "    "+prisonterm);
				map.put("zclhq", tbxfMap.get("REMUNERATION"));
				map.put("gsk", tbxfMap.get("WITHCASH"));
				map.put("hk", tbxfMap.get("REMITTANCE"));
				map.put("qtsr", tbxfMap.get("INCOME"));
				map.put("gwzc", tbxfMap.get("SHOPPING"));
				map.put("qtzc", tbxfMap.get("PAY"));
				map.put("balance",tbxfMap.get("OVERPLUS"));
			}
			map.put("criminalid", crimid);
			String applyvalue = crimid+","+tbprisonerBaseinfo.getName();
			request.setAttribute("applyvalue",applyvalue);
		}
		request.setAttribute("tempid", tempid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/aip/jinBanRenCommuteOfJailCase.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	
	
	//办案
	@RequestMapping(value = "/getCaseBigDataContent")
	@ResponseBody
	@SuppressWarnings("all")
	public String getCaseBigDataContent(HttpServletRequest request){
		
		String tempid = request.getParameter("tempid");
		String flowdraftid = request.getParameter("flowdraftid");
		
		Map paramMap = new HashMap();
		paramMap.put("flowdraftid", flowdraftid);
		paramMap.put("tempid", tempid);
		request.setAttribute("tempid", tempid);
		String content =  flowBaseOtherService.getDocconentByConditionTwo(paramMap);
		return content;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getJXJSBigData")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getJXJSBigData(HttpServletRequest request){
		String tempid= request.getParameter("tempid");//模板id
		String flowdraftid = request.getParameter("flowdraftid");//流程草稿id
		String flowid = request.getParameter("flowid");//流程id
		String flowdefid = request.getParameter("flowdefid");//流程自定义id
		
		Map map = new HashMap();
		map.put("tempid", tempid);
		map.put("flowdraftid", flowdraftid);
		map.put("flowid", flowid);
		
		//获取大字段，主要是为了获取大字段共有多少个章
		String content = flowBaseOtherService.getJXJSBigDataImpl(map);
		
		map.clear();
		SystemUser user = getLoginUser(request);
		map.put("userid", user.getUserid());
		map.put("departid", user.getDepartid());
		map.put("tempid", tempid);
		map.put("flowdefid", flowdefid);
		//获取 当前级别 提交时候，一共要多个章 和多少个批注
		int signnumber = 0;
		int notation = 0;
	    List<Map> signNonations = flowBaseOtherService.getMaxProgress(map);
	    if (signNonations.size() > 0) {
			for (Map map2 : signNonations) {
				signnumber += Integer.parseInt(map2.get("SIGNNUMBER").toString());
				notation += Integer.parseInt(map2.get("NOTATION").toString());
			}
		}
	    
		Map mapValue = new HashMap();
		mapValue.put("signnumber", signnumber);
		mapValue.put("notation", notation);
		mapValue.put("content", content);
		return  mapValue;
	}
	
	//办案
	@RequestMapping(value = "/getCommuteOfJailCaseFileBase64")
	@ResponseBody
	public Object getCommuteOfJailCaseFileBase64(HttpServletRequest request){
		JSONArray docconent = new JSONArray();
		Map<String,Object> map = new HashMap<String,Object>();
//		returnResourceMap(request);
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
		
		
		request.setAttribute("fathermenuid", fathermenuid);
		request.setAttribute("applyid", crimid);
		request.setAttribute("orgid", orgid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("lastnodeid", lastnodeid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("flowdraftid", flowdraftid);
		
		String applyname = tbxfSentencealterationService.getCrimiNameByCrimid(crimid);
		request.setAttribute("applyname", applyname);
		
		Map paramMap = new HashMap();
		paramMap.put("flowdefid", "other_zfjyjxjssp");
		paramMap.put("flowid", flowid);
		paramMap.put("tempid", tempid);
		
		String content =  flowBaseOtherService.getDocconentByConditionTwo(paramMap);
		
		docconent.add(content);
		request.setAttribute("tempid", tempid);
		request.setAttribute("formcontent", docconent.toString());
		ModelAndView mav = null;
		View view =null;
			view = new InternalResourceView(
			"WEB-INF/JSP/aip/addCommuteOfJailCase.jsp");
		mav = new ModelAndView(view);
		return mav;
		
	}
	
	
	
	//案件撤销
	@RequestMapping(value = "/withdrawalCasesOfJail")
	@ResponseBody
	public Object withdrawalCasesOfJail(HttpServletRequest request){
		
		String flowdraftids = request.getParameter("flowdraftids");
//		String menuid = request.getParameter("menuid");
		String crimids = request.getParameter("crimids");
		//案件撤销成功返success
		String result = tbxfSentencealterationService.withdrawalCasesOfJail(flowdraftids,crimids);
		
		return result;
	}
	
	//处室经办案件退回
	@RequestMapping(value = "/backCasesOfJail")
	@ResponseBody
	public Object backCasesOfJail(HttpServletRequest request){
		
		String flowdraftids = request.getParameter("flowdraftids");
		String crimids = request.getParameter("crimids");
		//案件撤销成功返success
		String result = tbxfSentencealterationService.backCasesOfJail(flowdraftids,crimids);
		
		return result;
	}
	
	//进入会议记录界面
	@RequestMapping(value = "/toMeetingRecordPage")
	public ModelAndView toMeetingRecordPage(HttpServletRequest request){
		returnResourceMap(request);
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/meetingRecordPage.jsp");
		return new ModelAndView(view);
	}
	
	
	//进入会议记录界面
	@RequestMapping(value = "/toChenkMeetingRecord")
	public ModelAndView toChenkMeetingRecord(HttpServletRequest request){
		//returnResourceMap(request);
		String otherid = request.getParameter("otherid");
		request.setAttribute("otherid", otherid);
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/checkMeetingRecordPage.jsp");
		return new ModelAndView(view);
	}
	
	
	//会议记录
//	@RequestMapping(value = "/obtainMeetingRecord")
//	@ResponseBody
//	public Object getMeetingRecord(HttpServletRequest request){
//		String crimids = request.getParameter("crimids");
//		String butMenuid = request.getParameter("butMenuid");//按钮ID
//		String tempid = request.getParameter("tempid");//系统模板ID
//		String meetingContent = "";
//		
//		String status = "";//存状态 是否为新增或修改
//		String otherid = "";//当会议记录为修改时，存的主键值
//		if(StringNumberUtil.notEmpty(crimids)){
////			String templateInfo = systemTemplateService.getSysTemplateInfoByTempid(tempid);
//			meetingContent = tbxfSentencealterationService.getCrimiCommuteOpinion(crimids,butMenuid,tempid);
//			status = "new";
//		}else{
//			FlowBaseOther fbo = flowBaseOtherService.getMeetingRecord(tempid);
//			if(null!=fbo){
//				meetingContent = fbo.getDocconent();
//				status = "edit";
//				otherid = fbo.getOtherid()+"";
//			}
//		}
//		
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("annexcontent", meetingContent);
//		map.put("status", status);
//		map.put("otherid", otherid);
//		List list = new ArrayList();
//		list.add(map);
//		return list;
////		JSONMessage message = JSONMessage.newMessage();
////		message.setTotal(0);
////		message.setData(list);
////		return message;
//	}
	
	
	//会议记录
	@RequestMapping(value = "/getMeetingRecordById")
	@ResponseBody
	public Object getMeetingRecordById(HttpServletRequest request){
		String otherid = request.getParameter("otherid");
		String meetingContent = "";
		
		FlowBaseOther fbo = flowBaseOtherService.getMeetingRecordById(otherid);
		if(null!=fbo){
			meetingContent = fbo.getDocconent();
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("annexcontent", meetingContent);
		map.put("otherid", otherid);
		List list = new ArrayList();
		list.add(map);
		return list;
	}
	
	
	//会议记录
	@RequestMapping(value = "/saveOrUpdateMeetingRecord")
	@ResponseBody
	public Object saveOrUpdateMeetingRecord(HttpServletRequest request){
		String annexcontent = request.getParameter("annexcontent");
		String status = request.getParameter("status");
		String otherid = request.getParameter("otherid");
		String flowid = request.getParameter("flowid");
		flowid = "aaa";
		String tempid = request.getParameter("tempid");
		SystemUser user = getLoginUser(request);
//		Object obj = JsonUtil.Decode(data);
//		data = ((List<Map<String,Object>>)obj).get(0).get("annexcontent").toString();
		String batchidStr = request.getParameter("batchid");
		Integer batchid=1 ;
		if(StringNumberUtil.notEmpty(batchidStr)){
			batchid = Integer.parseInt(batchidStr);
		}
		String abc = user.getOrganization().getOrgid();
		HashMap map = new HashMap();
		flowOtherFlowService.saveOrUpdateMeetingRecord(map,user);
		return  "success";
	}
	
	
	/**
	 * 方法描述：会议记录列表页展示
	 * @param request
	 * @return resultmap
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/getMeetingList")
	@ResponseBody
	public Object getMeetingList(HttpServletRequest request){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		try {
			String orgid = getLoginUser(request).getOrgid();
			String starttime=request.getParameter("starttime")==null?"":request.getParameter("starttime");
			String endtime=request.getParameter("endtime")==null?"":request.getParameter("endtime");
			String flowdefid = request.getParameter("flowdefid");
			if (StringNumberUtil.isNullOrEmpty(flowdefid)) {
				//默认是监狱的减刑假释审批
				flowdefid = "other_zfjyjxjssp";
			}
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize"))); 
			int start = pageIndex * pageSize;
			int end = (pageIndex+1)*pageSize;
			Map<String,Object> map = new HashMap<String,Object>();
			String meetcontent = request.getParameter("meetcontent");
			//标示 是选择下拉列表 得到数据
			String change = request.getParameter("change");
			//下拉列表的部门
			String orgidPar = request.getParameter("orgid");
			//如果选择 下拉列表 那么orgid=orgidPar 否者 orgid=orgid
			if(orgidPar != null && !"".equals(orgidPar)){
				orgid = orgidPar;
			}else if(change != null && !"".equals(change)){
				map.put("type","all");
			}
			map.put("orgid", orgid);
			map.put("starttime", starttime);
			map.put("endtime", endtime);
	    	map.put("pageIndex", String.valueOf(start));
	    	map.put("pageSize",String.valueOf(end));
	    	//如果 选择 下拉列表 那么 map集合重新添加值
		    map.put("change", change);
		    SystemUser user = getLoginUser(request);
		    String departid = user.getDepartid();
		    //获取 最大的批次
		    HashMap mapList = systemModelService.getCuryearBatch(departid);
		    String curyear = mapList.get("CURYEAR").toString();
		    String batch = mapList.get("BATCH").toString();
		    map.put("curyear", curyear);
		    map.put("batch", batch);
		    map.put("meetcontent", meetcontent);
		    map.put("flowdefid", flowdefid);
		    List data= flowOtherFlowService.selectDataByMapCondition(map);
	    	List<Map> coList = flowOtherFlowService.getAllMeetByOrgid(map);
	    	int count = coList.size();
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	/**
	 * 方法描述：删除会议记录
	 * @version 2014年12月2日22:30:16
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteMeeting")
	@ResponseBody
	public int deleteMeeting(HttpServletRequest request){
		
		int value = this.flowOtherFlowService.deleteMeetingImpl(request);
		
		return value;
	}
	
	/**
	 * 方法描述：会议记录列表页展示
	 * @param request
	 * @author ：mushuhong
	 * @version 2014年12月19日16:48:17
	 * @return resultmap
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/getMeetingList_nx")
	@ResponseBody
	public Object getMeetingList_nx(HttpServletRequest request){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		try {
			String orgid = getLoginUser(request).getOrgid();
			String starttime=request.getParameter("starttime")==null?"":request.getParameter("starttime");
			String endtime=request.getParameter("endtime")==null?"":request.getParameter("endtime");
			String flowdefid = request.getParameter("flowdefid");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize"))); 
			int start = pageIndex * pageSize;
			int end = (pageIndex+1)*pageSize;
			Map<String,Object> map = new HashMap<String,Object>();
			String meetcontent = request.getParameter("meetcontent");
			//标示 是选择下拉列表 得到数据
			String change = request.getParameter("change");
			//下拉列表的部门 
			String orgidPar = request.getParameter("orgid");
			//如果选择 下拉列表 那么orgid=orgidPar 否者 orgid=orgid
			if(orgidPar != null && !"".equals(orgidPar)){
				orgid = orgidPar;
			}else if(change != null && !"".equals(change)){
				map.put("type","all");
			}
			map.put("orgid", orgid);
			map.put("starttime", starttime);
			map.put("endtime", endtime);
	    	map.put("pageIndex", String.valueOf(start));
	    	map.put("pageSize",String.valueOf(end));
	    	//如果 选择 下拉列表 那么 map集合重新添加值
		    map.put("change", change);
		    SystemUser user = getLoginUser(request);
		    String departid = user.getDepartid();
		    //获取 最大的批次
		    HashMap mapList = systemModelService.getCuryearBatch(departid);
		    String curyear = mapList.get("CURYEAR").toString();
		    String batch = mapList.get("BATCH").toString();
		    map.put("curyear", curyear);
		    map.put("batch", batch);
		    map.put("meetcontent", meetcontent);
		    map.put("flowdefid", flowdefid);
		    List data= flowOtherFlowService.selectDataByMapCondition_nx(map);
	    	List<Map> coList = flowOtherFlowService.getAllMeetByOrgid_nx(map);
	    	int count = coList.size();
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	@RequestMapping(value="/showCQAJPSBs.page")
	public ModelAndView showCQAJPSB(HttpServletRequest request)
			throws Exception {
		JSONArray docconent = new JSONArray();
		String  tempid="CQAJPSB";
		String crimid = request.getParameter("crimid");
		Map<String, Object> selectmap = new HashMap<String, Object>();
		SystemUser user = getLoginUser(request);
		String deptid = user.getDepartid();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
		Map<String, Object> map = new HashMap<String, Object>();
		SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);
		TbprisonerBaseinfo tbprisonerBaseinfo = prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService.getCrimeByCrimid(crimid);
		List<TbprisonerAccomplice> accompliceList = prisonerService.selectAccompliceByCrimid(crimid);
		List<TbprisonerResume> resumeList = prisonerService.findByCrimidResume(crimid);
		List<TbprisonerSocialRelation> relationList = prisonerService.findRelationBycrimid(crimid);
		if (tbprisonerBaseinfo != null) {
			map.put("name", tbprisonerBaseinfo.getName());
			map.put("mz", tbprisonerBaseinfo.getNation());
			map.put("whcd", tbprisonerBaseinfo.getEducation());
			map.put("csdate", DateUtil.dateFormatForAip(tbprisonerBaseinfo.getBirthday()));
			map.put("hjaddre", tbprisonerBaseinfo.getOriginplaceaddress());
			map.put("xzhuzhi", tbprisonerBaseinfo.getRegisteraddress());
			map.put("jiguan", tbprisonerBaseinfo.getCountryarea());
			map.put("puqianzhiye", tbprisonerBaseinfo.getVocation());
		}
		TbxfSentenceAlteration tbxf =sentenceAlterationService.selectByPrimaryKey(crimid);
		if(tbxf!=null){
			map.put("guanyadanwei", tbxf.getAreaname());//关押监狱、监区
			map.put("sfdate", DateUtil.dateFormatForAip(tbxf.getCourtchangeto()));
		}
		if(tbprisonerBaseCrime!=null){
			
			map.put("panjuefayuan", tbprisonerBaseCrime.getJudgmentname());//判决法院
			map.put("jiyariq", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getDetaindate()));//羁押日期
		}
		if(accompliceList!=null){
			String  str = "";
			for (TbprisonerAccomplice ta:accompliceList) {
				str+=ta.getName()+";";
			}
			map.put("tongan", str);
		}
		if (!relationList.isEmpty() && relationList != null) {
			String  str = "";
			for (TbprisonerSocialRelation tsr:relationList) {
				str+=tsr.getName()+",关系 ： "+tsr.getRelationship()+",出生日期  ："+tsr.getBirthday()+";";
				}
			map.put("guanxi", str);
		}
		if(resumeList!=null){
			String  str="";
			for(TbprisonerResume tr:resumeList){
				str+=tr.getBegindate()+"-"+tr.getEnddate()+",单位："+tr.getOrgname()+",职务："+tr.getDuty()+";";
			}
			map.put("jianli", str);
		}
		
		if (template != null) {
			docconent.add(template.getContent());
		}
		request.setAttribute("tempid", tempid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());

		request.setAttribute("selectDatajson", new JSONObject(selectmap)
				.toString());
		request.setAttribute("formcontent", docconent.toString());
		
		return new ModelAndView(
				"penaltyPerform/tianjin/showCQAJPSB");
	}

	/**
	 * 方法描述：批量签章必须判断当前输入的案件号罪犯是否属于当前级别
	 * @author：mushuhong
	 * @version：2015年2月8日16:30:43
	 */
	public Object judgeBeLongToNowJibieCriminal(HttpServletRequest request){
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 跳转资格筛查查看页面
	 * 
	 * @author YangZR
	 * @Date 2015-05-16
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toQualificationScreeningPage.page")
	public ModelAndView toQualificationScreeningPage(HttpServletRequest request) {
		
		returnResourceMap(request);
		// 获取用户
//		SystemUser user = getLoginUser(request);
		
		Map<String, ? extends Object> paraMap = super.parseParamMap(request);
		super.addMap2Attribute(paraMap, request);
		
		return new ModelAndView("common/qualificationScreeningPage");
	}
	
	
	/**
	 * 描述：特案办理按钮
	 * @author YangZr 2015.05.17
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dealSpecialCase.json")
	@ResponseBody
	public Object commonRemoveDate(HttpServletRequest request){

		JSONMessage message = JSONMessage.newMessage();
		String crimids = request.getParameter("crimids");
		if(StringNumberUtil.notEmpty(crimids)){
			Map<String,String> map = new HashMap<String,String>();
			map.put("crimids", crimids);
			// 获取用户
			SystemUser user = getLoginUser(request);
			//调用业务层方法，并返回结果
			message = tbxfScreeningService.dealSpecialCase(map,user);
		}
		
		return message;

	}
}

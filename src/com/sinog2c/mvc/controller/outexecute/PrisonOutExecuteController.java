package com.sinog2c.mvc.controller.outexecute;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.common.LogCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.report.RMEngine;

/**
 * @author wangxf   
 * 
 */
@Controller
public class PrisonOutExecuteController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private PrisonerService prisonerService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Autowired
	private UvFlowService uvFlowService;
	@Autowired
	private SentenceAlterationService sentencealterationservice;
	
	
	/**
	 * @author kexz
	 *暂予监外执行收监决定书罪犯处理页面
	 * 2014-8-9
	 */
	@RequestMapping("/prisonOutExecute")
	public ModelAndView prisonOutExecute(HttpServletRequest request){
		returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/chooseCriminal/chooseOutExecute.jsp"));
	}
	
//	/**
//	 * @author kexz
//	 *暂予监外执行收监决定书罪犯处理数据列表
//	 * 2014-8-9
//	 */
//	@RequestMapping("/getOutExecuteChoose")
//	@ResponseBody
//	public Object getOutExecuteChoose(HttpServletRequest request) {
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");//取得参数
//			String condition = prisonerService.getTheQueryCondition("10149");
//			//分页
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
//			//字段排序
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("key", key);
//			//map.put("departId", getLoginUser(request).getOrgid());
//			map.put("condition", condition);
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
//	    	data= chooseCriminalService.getBasicInfoList(map);//根据map传参获取罪犯列表
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//		}
//		
//		return resultmap;
//	}
	
	/**
	 * @author kexz
	 *暂予监外执行收监决定书
	 * 2014-8-9
	 */
	@RequestMapping("/prisonOutExecuteList")
	public ModelAndView chooseOutExecute(HttpServletRequest request){
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String idname = request.getParameter("idname")==null?"":request.getParameter("idname");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String menuid = request.getParameter("menuid")==null?"":request.getParameter("menuid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
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
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/outexecute/prisonOutExecute.jsp"));
	}
	

	/**
	 * 方法描述：获取列表数据
	 * 2014-8-9
	 * @author kexz
	 */
	@RequestMapping("/getOutExecuteList")
	@ResponseBody
	public HashMap<String, Object> getOutExecuteList(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		returnResourceMap(request);
		String tempid =request.getParameter("tempid");
		String key = request.getParameter("key");
		String crimid = request.getParameter("crimid");
		SystemUser user = getLoginUser(request);
		//根据根据登录用户去获取对应部门id
		String deptid=user.getDepartid();
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize"))); 
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid,crimid,deptid, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid,crimid, user.getDepartid());
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	
	/**
	 * 查看、修改时，根据文书Id查询出大字段回显
	 * @author kexz 
	 * 2014-8-9
	 */
	@RequestMapping(value = "/editOutExecute")
	public ModelAndView editOutExecute(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");//大字段主键
		request.setAttribute("docid", docid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null,null, null);
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView(new InternalResourceView(
				"/WEB-INF/JSP/aip/editOutExecute.jsp"));
	}
	

	/**
	 * 修改/保存大字段
	 * @author kexz
	 * 2014-8-9
	 */
	@RequestMapping(value = "/saveOutExecute")
	@ResponseBody
	public int saveOutExecute(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if("new".equals(operator)){
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setCrimid(crimid);
			document.setIntroduction(LogCommon.ZANYUJWZXSJJDS);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			document.setIntroduction(LogCommon.ZANYUJWZXSJJDS);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ZANYUJWZXSJJDS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.ZANYUJWZXSJJDS+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZANYUJWZXSJJDS+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ZANYUJWZXSJJDS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.ZANYUJWZXSJJDS+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZANYUJWZXSJJDS+LogCommon.EDIT);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
		}
		if(countnum == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return countnum;
	}
	

	/**
	 * 根据文书Id删除数据
	 * @author kexz
	 * 2014-8-9
	 */
	@RequestMapping(value = "/deleteOutExecute")
	@ResponseBody
	public int deleteOutExecute(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.ZANYUJWZXSJJDS+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.ZANYUJWZXSJJDS+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.ZANYUJWZXSJJDS+LogCommon.DEL);
		if(result == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}	
	
	/**
	 * @author kexz
	 *新增
	 * 2014-8-9
	 */
	@RequestMapping("/OutExecuteAdd")
	@SuppressWarnings("all")
	public ModelAndView OutExecuteAdd(HttpServletRequest request){
		returnResourceMap(request);
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();
		String crimid = request.getParameter("crimid");
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		TbprisonerBaseinfo tbinfo=prisonerService.getBasicInfoByCrimid(crimid);//罪犯基本信息表
		TbprisonerBaseCrime crimeName = prisonerService.getCrimeByCrimid(crimid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", tbinfo.getName());
		map.put("xingbie",tbinfo.getGender());
		map.put("birthday",DateUtil.dateFormatForAip(tbinfo.getBirthday()));
		map.put("minzu",tbinfo.getNation());
		map.put("jiatingzhuzhi", tbinfo.getFamilyaddress());
		if(crimeName!=null && !"".equals(crimeName)){
			map.put("zhuxing",crimeName.getRemark());
			map.put("zuiming",crimeName.getCauseaction());
			map.put("judgedate",DateUtil.dateFormatForAip(crimeName.getJudgedate()));
			map.put("fayuan",crimeName.getJudgmentname());
			map.put("fujiaxing",crimeName.getSanremark());
			String xqqr = DateUtil.dateFormat(crimeName.getSentencestime(), "yyyy.MM.dd");
			String xqzr  ="";
			String xqqz = "";
			xqqz = xqqr;
			if(crimeName.getSentenceetime()!=null){
				xqzr = DateUtil.dateFormat(crimeName.getSentenceetime(), "yyyy.MM.dd");
				xqqz = ("自"+xqqr+"至"+xqzr+"止");
			}
			map.put("xingqiqizhi", xqqz);
			if(crimeName.getSentencestime()!=null){
//				map.put("yuanpanxingqiqizhi",sdf.format(crimeName.getSentencestime()) + "至" + sdf.format(crimeName.getSentenceetime()) );
			}
		}
		map.put("text14",user.getPrisonShortName());
		map.put("text16",tbinfo.getName());
		if (template != null) {
			docconent.add(template.getContent());
		}
		//获取河北的省份代码
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String hebei = jyconfig.getProperty(GkzxCommon.HEBEI_CODE);
		//判断当前登录人是否是河北监狱用户
		if (hebei.contains(user.getDepartid())) {
			//根据单位的id查询出对应的模板内容
			TbsysTemplate mapTemplete = systemModelService.getTemplateAndDepartid("ZYJWZXSJ_JDS", departid);
			String tempcontent = mapTemplete.getContent();
			map.clear();
			map.put("titlename", mapTemplete.getTempname());//模板名称
			map.put("casenumcontent", mapTemplete.getRemark()+"("+(new Date().getYear()+1900)+")"+GkzxCommon.HB_DI+" "+GkzxCommon.HB_HAO);//案件字号
			map.put("text2", user.getOrganization().getName()+"：");//当前用户所在监狱
			//根据罪犯编号查询出对应的基本信息
			Map criinfoMap = prisonerService.getBaseInfoByCrimidMap(crimid,user.getDepartid());
			Iterator iterator = criinfoMap.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next().toString();//键
				String value = criinfoMap.get(key)==null?"":criinfoMap.get(key).toString();//键对应值
				if (tempcontent.contains(key)) {
					key = "["+key+"]";
					tempcontent = tempcontent.replace(key, value);
				}
			}
			map.put("jdscontent", "   "+tempcontent);
			map.put("nowdate", criinfoMap.get("NOWDATE"));//当前日期
            //查询 表单 内容
			TbsysTemplate mapTemplete2 = systemModelService.getTemplateAndDepartid("HB_ZYJWZXSJ_JDS", departid);
			docconent.clear();
			docconent.add(mapTemplete2.getContent());
		}
		request.setAttribute("crimid", crimid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/aip/editOutExecute.jsp"));
	}
	
	
	
//------------------------------暂予监外执行决定书---------------------------------------	
	

	
	/**
	 * 罪犯选择页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/prisonOutExecuteChoose")
	public  ModelAndView prisonOutExecuteChoose(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/chooseCriminal/prisonOutExecuteChoose.jsp"));
	}

	/**
	 * 获取省局保外收监数据lyg
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getJailOutPrisonLiAnData123.action")
	@ResponseBody
	public Object getJailOutPrisonLiAnData123(HttpServletRequest request) {
		
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map> data = new ArrayList<Map> ();
		try {
			Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			//status值为资源保外立案url传值，0为在押,1为保外
			String status = "".equals(request.getParameter("status"))?"1":request.getParameter("status");
			String statusname = GkzxCommon.BAOWAI;
			if("1".equals(status)) statusname = GkzxCommon.ZAIYA;
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));       
			SystemUser su = getLoginUser(request);
			String orgid = su.getOrgid();
			String departid = su.getDepartid();
			String flowdefid = request.getParameter("flowdefid");
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			Map<String,Object> map = new HashMap<String,Object>();

			map.put("key", key);
			map.put("provincecode", provincecode);
			map.put("orgid", orgid);
			map.put("departid", departid);
			map.put("status", status);  
			map.put("statusname", statusname);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("flowdefid", flowdefid);
			
	    	int count = uvFlowService.jailBaoWaiLiAnCount123(map);
	    	data= uvFlowService.jailBaoWaiLiAnList123(map);
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultmap;
	}
	
	
	

	
	/**
	 * 获取保外立案数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getJailOutPrisonLiAnData.action")
	@ResponseBody
	public Object getJailOutPrisonLiAnData(HttpServletRequest request) {
		
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map> data = new ArrayList<Map> ();
		try {
			Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			//status值为资源保外立案url传值，0为在押,1为保外
			String status = "".equals(request.getParameter("status"))?"1":request.getParameter("status");
			String statusname = GkzxCommon.BAOWAI;
			if("1".equals(status)) statusname = GkzxCommon.ZAIYA;
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));       
			SystemUser su = getLoginUser(request);
			String orgid = su.getOrgid();
			String departid = su.getDepartid();
			String flowdefid = request.getParameter("flowdefid");
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			Map<String,Object> map = new HashMap<String,Object>();

			map.put("key", key);
			map.put("provincecode", provincecode);
			map.put("orgid", orgid);
			map.put("departid", departid);
			map.put("status", status);  
			map.put("statusname", statusname);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("flowdefid", flowdefid);
			
	    	int count = uvFlowService.jailBaoWaiLiAnCount(map);
	    	data= uvFlowService.jailBaoWaiLiAnList(map);
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultmap;
	}
	

	/**
	 * 监狱保外立案
	 * @param request
	 * @param response
	 * @return 成功返回0 失败返回-1
	 */
	@RequestMapping(value = "/baoWaiJailLiAn.action")
	@ResponseBody
	public Object baoWaiJailLiAn(HttpServletRequest request,HttpServletResponse response) {
		String result = "-1";
		SystemUser user = getLoginUser(request);// 用户对象
		//取得参数
		String data = request.getParameter("data");//获取表单页面的数据
		
		String resid = request.getParameter("resid");//和流程相关按钮ID
		String flowid = request.getParameter("flowid");//流程ID
		String conent = request.getParameter("conent");//内容
		String tempid = request.getParameter("tempid");//表单ID
		String examineid = request.getParameter("examineid");//表单ID
		String orgid = request.getParameter("orgid");//申请人的部门ID
		String applyid = request.getParameter("applyid");//申请人ID
		String applyname = request.getParameter("applyname");//申请人名称
		String flowdefid = request.getParameter("flowdefid");//流程自定义ID
		String docconent = request.getParameter("docconent");//审批大字段
		String operateType = request.getParameter("operateType");//判断新增、修改
		String flowdraftid = request.getParameter("flowdraftid");//流程草稿ID
		String commenttext = request.getParameter("commenttext");//审批意见
		String curyear = request.getParameter("curyear");//案件年度
		String shortname = request.getParameter("shortname");//单位简称(办案用)
		String commutetype = request.getParameter("commutetype");

		Map<String, String> temMap2 = new HashMap<String, String>();
		temMap2.put("departid", user.getDepartid());
		temMap2.put("flowdefid", flowdefid);
		temMap2.put("curyear", curyear);
		if(StringNumberUtil.notEmpty(commutetype)){
			temMap2.put("commutetype", commutetype);
		}
		String casenumber = flowBaseService.getMaxBaowaiCaseNum(temMap2);//案件号
		
		String casetype = "保字";
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
		tempmap.put("shortname", shortname);
		tempmap.put("flowdraftid", flowdraftid);//流程草稿ID
		tempmap.put("commenttext", commenttext);//审批意见
		tempmap.put("jxjs_1", commutetype);
		if(StringNumberUtil.notEmpty(curyear)&&StringNumberUtil.notEmpty(casenumber)){
			tempmap.put("casenum", curyear+casenumber);
		}
		
		result=uvFlowService.baoWaiJailLiAn(data, user, tempmap).toString();
		
		return result;
	}
	
	
	/**
	 * 省局保外入监lyg
	 * @param request
	 * @param response
	 * @return 成功返回0 失败返回-1
	 */
	@RequestMapping(value = "/baoWaiJailLiAn123.action")
	@ResponseBody
	public Object baoWaiJailLiAn123(HttpServletRequest request,HttpServletResponse response) {
		String result = "-1";
		SystemUser user = getLoginUser(request);// 用户对象
		//取得参数
		String data = request.getParameter("data");//获取表单页面的数据
		
		String resid = request.getParameter("resid");//和流程相关按钮ID
		String flowid = request.getParameter("flowid");//流程ID
		String conent = request.getParameter("conent");//内容
		String tempid = request.getParameter("tempid");//表单ID
		String examineid = request.getParameter("examineid");//表单ID
		String orgid = request.getParameter("orgid");//申请人的部门ID
		String applyid = request.getParameter("applyid");//申请人ID
		String applyname = request.getParameter("applyname");//申请人名称
		String flowdefid = request.getParameter("flowdefid");//流程自定义ID
		String docconent = request.getParameter("docconent");//审批大字段
		String operateType = request.getParameter("operateType");//判断新增、修改
		String flowdraftid = request.getParameter("flowdraftid");//流程草稿ID
		String commenttext = request.getParameter("commenttext");//审批意见
		String curyear = request.getParameter("curyear");//案件年度
		String shortname = request.getParameter("shortname");//单位简称(办案用)
		String commutetype = request.getParameter("commutetype");

		Map<String, String> temMap2 = new HashMap<String, String>();
		temMap2.put("departid", user.getDepartid());
		temMap2.put("flowdefid", flowdefid);
		temMap2.put("curyear", curyear);
		if(StringNumberUtil.notEmpty(commutetype)){
			temMap2.put("commutetype", commutetype);
		}
		String casenumber = flowBaseService.getMaxBaowaiCaseNum(temMap2);//案件号
		
		String casetype = "刑收监字";
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
		tempmap.put("shortname", shortname);
		tempmap.put("flowdraftid", flowdraftid);//流程草稿ID
		tempmap.put("commenttext", commenttext);//审批意见
		tempmap.put("jxjs_1", commutetype);
		if(StringNumberUtil.notEmpty(curyear)&&StringNumberUtil.notEmpty(casenumber)){
			tempmap.put("casenum", curyear+casenumber);
		}
		
		result=uvFlowService.baoWaiJailLiAn123(data, user, tempmap).toString();
		
		return result;
	}
	
	
	/**
	 * 流程审批 流转操作
	 * 
	 * @return
	 */
	@RequestMapping(value = "/baoWaiJailBatchLiAn.action")
	@ResponseBody
	public Object baoWaiJailBatchLiAn(HttpServletRequest request,HttpServletResponse response) {
		SystemUser user = getLoginUser(request);// 用户对象
		int resultval = 0;//定义变量
		
		//取得参数
		String data = request.getParameter("data");//获取表单页面的数据
		String dataStr = request.getParameter("dataStr");
		String resid = request.getParameter("resid");//和流程相关按钮ID
		String flowid = request.getParameter("flowid");//流程ID
		String conent = request.getParameter("conent");//内容
		String tempid = request.getParameter("tempid");//表单ID
		String flowdefid = request.getParameter("flowdefid");//流程自定义ID
		String docconent = request.getParameter("docconent");//审批大字段
		String operateType = request.getParameter("operateType");//判断新增、修改
		String flowdraftid = request.getParameter("flowdraftid");//流程草稿ID
		String commenttext = request.getParameter("commenttext");//审批意见
		String curyear = request.getParameter("curyear");
		String shortname = request.getParameter("shortname");//单位简称(办案用)
		String commutetype = request.getParameter("commutetype");//案件类刑
		String casetype = "保字";
		Map<Object,Object> datamap = new HashMap<Object,Object>();
		datamap.put("data", data);
		datamap.put("resid", resid);
		datamap.put("flowid", flowid);
		datamap.put("conent", conent);
		datamap.put("tempid", tempid);
		datamap.put("flowdefid", flowdefid);
		datamap.put("docconent", docconent);
		datamap.put("operateType", operateType);
		datamap.put("flowdraftid", flowdraftid);
		datamap.put("commenttext", commenttext);
		datamap.put("curyear", curyear);
		datamap.put("casetype", casetype);
		datamap.put("shortname", shortname);
		datamap.put("commutetype", commutetype);
		datamap.put("dataStr", dataStr);
		resultval = uvFlowService.baoWaiJailBatchLiAn(user,datamap);
		return resultval;
	}
	
	/*
	 * 调取存储过程生成保外立案数据
	 */
	@RequestMapping(value="/shengchengBWLAdata.action")
	@ResponseBody
	public void shengchengBWLAdata(){
		sentencealterationservice.autoUpdateSentenceChangeData();
	}
	
	/**
	 * @方法描述：暂予监外执行登记(报表)
	 * @author zgl  qq
	 * @version 2014-9-24 下午08:41:04
	 * @return
	 */
	@RequestMapping("/prisonExecuteRegister")
	public ModelAndView prisonExecuteRegister(HttpServletRequest request){
		String toolbar = request.getParameter("toolbar");//用于在jsp页面是否屏避 toolbar    0：屏避
		if(StringNumberUtil.notEmpty(toolbar)){
			request.setAttribute("toolbar", toolbar);
		}
		String casenos = request.getParameter("casenos");
		if(StringNumberUtil.notEmpty(casenos)){
			request.setAttribute("casenos", casenos);
		}
		SystemUser user = getLoginUser(request);
		RMEngine engine=this.systemResourceService.queryQualificationDataRmEngine("", user, request);
		request.setAttribute("mydata", engine.dedaoReportData());
		ModelAndView mav = new ModelAndView("report/yulandayin");
		return mav;
	}
}

package com.sinog2c.mvc.controller.outexecute;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;


/**
 * 委托函（继续保外就医）
 * @author liuxin
 * 2014-7-28 15:28:28
 */
@Controller
public class LetterOfAuthorizationContinuingMedicalParole  extends ControllerBase{
	@Resource
	private ChooseCriminalService chooseCriminalService;
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Resource
	private UvFlowService uvFlowService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	/**
	 * 跳转保外就医通知书（报法院）页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/letterOfAuthorizationContinuingMedicalParole")
	public ModelAndView letterOfAuthorizationContinuingMedicalParole(HttpServletRequest request){
		ModelAndView mav =  new ModelAndView("chooseCriminal/letterOfAuthorizationContinuingMedicalParoleChoose");
		returnResourceMap(request);
		return mav;
	}
	/**
	 * 获取罪犯列表
	 * 
	 * @author liuxin
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value = "/getLetterOfAuthorizationContinuingMedicalParoleBasicInfoList")
	@ResponseBody
	public Object getBasicInfoList(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<TbprisonerBaseinfo> data = new ArrayList<TbprisonerBaseinfo>();
		try {
			//取得参数
			String key = request.getParameter("key")==null?"":request.getParameter("key");
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
			map.put("departId", getLoginUser(request).getOrgid());
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("flowdefid", "other_zfjyjxjssp");
			
	    	//根据map传参获取总条数
	    	//int count = chooseCriminalService.countAllByCondition(map);
	    	//根据map传参获取罪犯列表
	    	//data= chooseCriminalService.getBasicInfoList(map);
	    	//获取检察院可操作罪犯信息列表
	    	int count=uvFlowService.getJianChaYuanopinionsDataCount(map);
	    	List<Map> flowlist=uvFlowService.getJianChaYuanopinionsData(map);
	    	
	    	resultmap.put("data", flowlist);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultmap;
	}
	/**
	 * 方法描述：跳转到列表页
	 * 
	 * @author
	 */
	@RequestMapping("/letterOfAuthorizationContinuingMedicalParoleList")
	public ModelAndView inPrisonNotice(HttpServletRequest request){
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String tempid=request.getParameter("tempid");
		String crimid=request.getParameter("crimid");
		String idname=request.getParameter("idname");
		String id=request.getParameter("id");
		String menuid=request.getParameter("menuid");
		if(crimid==null || "".equals(crimid)){
			request.setAttribute("id", id);
			String[] ids = id == null ? null:id.split(",");
			String[] idnames = idname == null?null:idname.split(",");
			crimid = ids ==null?"":ids[0];
			name = idnames == null?"":idnames[0];
		}
		request.setAttribute("idname", idname);
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid", tempid);
		return new ModelAndView("outexecute/letterOfAuthorizationContinuingMedicalParoleList");
	}
	/**
	 * 方法描述：列表页面获取数据
	 * 
	 * @author 
	 */
	@RequestMapping(value = "/getLetterOfAuthorizationContinuingMedicalParoleList")
	@ResponseBody
	public HashMap<String, Object> getTbsysDocumentList(
			HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		String tempid = request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		String departid =getLoginUser(request).getDepartid();
		String key = request.getParameter("key");
		String sortField = request.getParameter("optime");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid,crimid, departid, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid,crimid, departid);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	/**
	 * 跳转委托函（继续保外就医）新增页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/letterOfAuthorizationContinuingMedicalParoleAdd")
	public ModelAndView toLetterOfAuthorizationContinuingMedicalParoleAdd(HttpServletRequest request){
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
		
		//根据罪犯id获取罪犯信息
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cbiname", baseinfo.getName());
		map.put("text1", baseinfo.getName());
		map.put("text2", baseinfo.getName());
		map.put("text3", baseinfo.getName());
		map.put("text4", baseinfo.getName());
		map.put("text5", baseinfo.getName());
		String departid = user.getDepartid();
		String jailname = systemOrganizationService.getOrginfoByOrgid(departid).getName();
		map.put("text6", jailname);
		map.put("cbinativenamedetail", baseinfo.getOrigin());
		map.put("inscribedate", DateUtil.dateFormatForAip(new Date()));
		if (template != null) {
			docconent.add(template.getContent());
		}
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("operator", GkzxCommon.NEW);
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("tempid", tempid);
		request.setAttribute("crimid", crimid);
		return new ModelAndView("aip/letterOfAuthorizationContinuingMedicalParoleAdd");
		
	}
	/**
	 * 修改/保存大字段
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/saveLetterOfAuthorizationContinuingMedicalParole")
	@ResponseBody
	public int saveGuarantorQualificationsExaminationTable(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		//获取当前登录的用户
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String crimid = request.getParameter("crimid");
		String operator = request.getParameter("operator");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if("new".equals(operator)){
			String tempid = "SZXF_JXBWHSJWTH";
			String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
			String introduction = year+GkzxCommon.YEAR+template.getTempname();//文书简介
			TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction(baseinfo.getName()+introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(introduction+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(introduction+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(introduction+LogCommon.ADD+LogCommon.EVENT);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.WTHJXBWJY+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.WTHJXBWJY+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.WTHJXBWJY+LogCommon.EDIT+LogCommon.EVENT);
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
	 * 查看、修改委托函（继续保外就医）时，根据文书Id查询出大字段回显
	 * @author 
	 */
	@RequestMapping(value = "/letterOfAuthorizationContinuingMedicalParoleLook")
	public ModelAndView editorlook(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");
		String crimid = request.getParameter("crimid");
		request.setAttribute("docid", docid);
		request.setAttribute("crimid", crimid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null, null,"");
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		ModelAndView mav =  new ModelAndView("aip/letterOfAuthorizationContinuingMedicalParoleAdd");
		return mav;
	}
	/**
	 * 根据文书Id删除数据
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/deleteLetterOfAuthorizationContinuingMedicalParole")
	@ResponseBody
	public int deleteInPrisonNotice(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String introduction=LogCommon.WTHJXBWJY;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(introduction+LogCommon.DEL);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(introduction+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(introduction+LogCommon.DEL+LogCommon.EVENT);
		if(result == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}
}
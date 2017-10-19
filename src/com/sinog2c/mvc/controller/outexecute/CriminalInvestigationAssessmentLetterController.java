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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.prisoner.TbprisonerSocialRelation;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
/**
 * 罪犯调查评估委托函
 * @author liuxin
 * 2014-7-28 12:48:40
 */
@Controller
public class CriminalInvestigationAssessmentLetterController extends ControllerBase {
	@Resource
	private ChooseCriminalService chooseCriminalService;
	@Resource
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	@Resource
	private TbsysDocumentService sysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	
	/**
	 * 跳转犯调查评估委托函选择罪犯页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value="criminalInvestigationAssessmentLetter")
	public ModelAndView sentenceChange(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/criminalInvestigationAssessmentLetterChoose");
	}
	
	
	/**
	 * 跳转罪犯调查评估委托函页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value="criminalInvestigationAssessmentLetterList")
	public ModelAndView criminalInvestigationAssessmentLetterList(HttpServletRequest request){
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
		return new ModelAndView("outexecute/criminalInvestigationAssessmentLetter");
	}
	
//	/**
//	 * 获取罪犯列表
//	 * 
//	 * @author liuxin
//	 * @param request
//	 * @return resultmap
//	 */
//	@RequestMapping(value="getCriminalInvestigationAssessmentLetterBasicInfoList")
//	@ResponseBody
//	public Object getBasicInfoList(HttpServletRequest request,
//			HttpServletResponse response){
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
	 * 根据罪犯编号获取罪犯调查评估委托函列表
	 * 
	 * @author 
	 */
	@RequestMapping(value="getCriminalInvestigationAssessmentLetterByCrimid")
	@ResponseBody
	public HashMap<String, Object> getCriminalInvestigationAssessmentLetterByCrimid(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String crimid = request.getParameter("crimid");
		String tempid = request.getParameter("tempid");
		String key = request.getParameter("key");
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = sysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid, crimid ,null, sortField, sortOrder);
		int count = sysDocumentService.getCount(key, tempid, crimid, null);
		result.put("total", count);
		result.put("data", list);
		return result;
		
	}
	/**
	 * 跳转罪犯调查评估委托函新增页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value="criminalInvestigationAssessmentLetterAdd")
	public ModelAndView toCriminalInvestigationAssessmentLetterAdd(HttpServletRequest request){
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String deptid=user.getDepartid();
		String tempid = request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		String menuid=request.getParameter("menuid");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
		SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);
		String isprimarycontact = "1";//是否主联系人，1：是
		TbprisonerSocialRelation social = prisonerService.findByCrimidRelation(crimid, isprimarycontact);//查询罪犯社会关系
		TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(crimid);//处罚信息
		Map mmm = tbxfSentencealterationService.selectTbxfByCrimid(crimid);
		Object  t = mmm.get("COURTCHANGETO");
		Map<String,Object> map = new HashMap<String,Object>();
		if (template != null) {
			docconent.add(template.getContent());
		}
		map.put("xianshi", baseinfo.getFamilyaddress());
		map.put("xingbie", baseinfo.getGender());
		map.put("age", DateUtil.getAge(baseinfo.getBirthday()));
		map.put("panjueriqi", DateUtil.dateFormatForAip(baseCrime.getJudgedate()));
		map.put("zuiming",baseCrime.getCauseaction());
		map.put("panjuefayuan",baseCrime.getJudgmentname());
		map.put("xingqi",baseCrime.getRemark());
		map.put("xianxingqizhiri", DateUtil.dateFormatForAip(tbxfSentencealterationService.selectTbxfByCrimid(crimid).get("COURTCHANGETO")));
		map.put("fullname", org.getName());
		map.put("cbiname", baseinfo.getName());
		map.put("text15", baseinfo.getName());
		map.put("jiguan", baseinfo.getOrigin());
		map.put("juzhudi", baseinfo.getFamilyaddress());
		map.put("hujidi", baseinfo.getRegisteraddressdetail());
		map.put("text17", sdf.format(new Date()));
		//COURTCHANGETO
		if(social!=null){
			map.put("baozhengrenname", social.getName());
			map.put("text16", social.getPhone());
			map.put("baozhengrenguanxi", social.getRelationship());
			map.put("baozhengrenzhuzhi", social.getHomeaddress());
		}
		if(org!=null){
			map.put("depaddress", org.getAddress());
			map.put("zipcode", org.getPostcode());
			map.put("telephone", org.getExttel());
			map.put("contacts", org.getExtcontacts());
		}
		request.setAttribute("tempid", tempid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("menuid",menuid);
		request.setAttribute("name", baseinfo.getName());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		return new ModelAndView("aip/criminalInvestigationAssessmentLetterAdd");
	}
	/**
	 * 修改/保存大字段
	 * @author liuxin
	 */
	@RequestMapping(value="saveCriminalInvestigationAssessmentLetter")
	@ResponseBody
	public int saveCriminalInvestigationAssessmentLetter(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("temp");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		//根据罪犯id获取罪犯信息
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
		if("new".equals(operator)){
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction("罪犯"+baseinfo.getName()+LogCommon.ZFDCPGWTH);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ZFDCPGWTH+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.ZFDCPGWTH+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZFDCPGWTH+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ZFDCPGWTH+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.ZFDCPGWTH+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZFDCPGWTH+LogCommon.EDIT);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
		}
		log.setStatus(status);
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
	 * 查看、修改罪犯调查评估委托函时，根据文书Id查询出大字段回显
	 * @author 
	 */
	@RequestMapping(value="criminalInvestigationAssessmentLetterLook")
	public ModelAndView editorlook(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");
		String crimid = request.getParameter("crimid");
		request.setAttribute("docid", docid);
		request.setAttribute("crimid", crimid);
		TbsysDocument document = sysDocumentService.getTbsysDocument(docid, null, crimid,"");
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/criminalInvestigationAssessmentLetterAdd");
	}
	/**
	 * 根据文书Id删除数据
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value="criminalInvestigationAssessmentLetterDelete")
	@ResponseBody
	public int sentenceChangeDelete(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.ZFDCPGWTH+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.ZFDCPGWTH+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.ZFDCPGWTH+LogCommon.DEL);
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

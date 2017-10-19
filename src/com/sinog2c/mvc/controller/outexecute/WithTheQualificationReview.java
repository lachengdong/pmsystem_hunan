package com.sinog2c.mvc.controller.outexecute;


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
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;

/**
 * @author kexz
 *具保人资格审查
 * 2014-8-28
 */
@Controller
public class WithTheQualificationReview extends ControllerBase{
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	
	@RequestMapping("/WithTheQualificationReviewChoose")
	public ModelAndView WithTheQualificationReviewChoose(HttpServletRequest request) throws Exception {
			returnResourceMap(request);
			return new ModelAndView(new InternalResourceView("WEB-INF/JSP/chooseCriminal/WithTheQualificationReview.jsp"));
	}
	
	//点击处理
	@RequestMapping("/DoWithTheQualificationReview")
	public ModelAndView DoWithTheQualificationReview(HttpServletRequest request) throws Exception {
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
		return new ModelAndView("outexecute/DoWithTheQualificationReview");
	}
	
//	//初始化获取列表
//	@RequestMapping("/getWithTheQualificationReviewList")
//	@ResponseBody
//	public Object getWithTheQualificationReviewList(HttpServletRequest request) throws Exception {
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
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	data= chooseCriminalService.getBasicInfoList(map);
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}
	
	/**
	 * 显示表单
	 */
	@RequestMapping("/TheQualificationReview")
	public ModelAndView TheQualificationReview(HttpServletRequest request, HttpServletResponse response) {
		returnResourceMap(request);
		SystemUser user = getLoginUser(request);
		//根据根据登录用户去获取对应部门id
		String deptid=user.getDepartid();
		JSONArray docconent = new JSONArray();
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String menuid=request.getParameter("menuid")==null?"":request.getParameter("menuid");
		TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService.getCrimeByCrimid(crimid);
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
		TbxfSentenceAlteration tbxfSentencealteration=tbxfSentencealterationService.selectByPrimaryKey(crimid);
		if (template != null) {
			docconent.add(template.getContent());
		}
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("crimid", crimid);
		map.put("date", DateUtil.dateFormatForAip(new Date()));
		map.put("fzname",tbprisonerBaseinfo.getName());
		map.put("xb",tbprisonerBaseinfo.getGender());
		map.put("csdate",DateUtil.dateFormatForAip(tbprisonerBaseinfo.getBirthday()));
		map.put("jtzz2",tbprisonerBaseinfo.getFamilyaddress());
		if(tbprisonerBaseCrime!=null){
			map.put("ay", tbprisonerBaseCrime.getCauseaction());
			//map.put("xingqizhiri", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getBailend()));
			
		}
		if(tbxfSentencealteration!=null){
			map.put("xqqz","自"+ DateUtil.dateFormat(tbxfSentencealteration.getSentencestart())+"至"+DateUtil.dateFormat(tbxfSentencealteration.getCourtchangeto()));
			
		}
		request.setAttribute("crimid",crimid);
		request.setAttribute("tempid",tempid);
		request.setAttribute("menuid",menuid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/TheQualificationReview");
	}
	
	@RequestMapping(value = "/saveQualificationReview")
	@ResponseBody
	public int saveQualificationReview(HttpServletRequest request){
		
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator")==null?"":request.getParameter("operator");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
		if("new".equals(operator)){
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction("罪犯"+tbprisonerBaseinfo.getName()+LogCommon.JBRZGSCB);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.JBRZGSCB+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.JBRZGSCB+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.JBRZGSCB+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.JBRZGSCB+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.JBRZGSCB+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.JBRZGSCB+LogCommon.EDIT);
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
	 * 获取列表数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getWithTheQualificationList")
	@ResponseBody
	public HashMap<String, Object> getWithTheQualificationList(
			HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String deptid = getLoginUser(request).getDepartid();
		String key = request.getParameter("key");
		String sortField = request.getParameter("optime");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(pageIndex, pageSize, key, tempid,crimid, deptid, sortField, sortOrder);
		int count = tbsysDocumentService.getCount(key, tempid, crimid,deptid);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	/**
	 * 根据文书id 查询大字段并返回到修改页面
	 * @author 
	 */
	@RequestMapping(value = "/editTheQualificationReview")
	public ModelAndView editTheQualificationReview(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String menuid=request.getParameter("menuid")==null?"":request.getParameter("menuid");
		request.setAttribute("docid", docid);
		request.setAttribute("menuid",menuid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null, null,null);
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView(new InternalResourceView(
				"/WEB-INF/JSP/aip/TheQualificationReview.jsp"));
	}
	
	/**
	 * 删除
	 * 
	 */
	@RequestMapping(value = "/deleteTheQualificationReview")
	@ResponseBody
	public int deleteTheQualificationReview(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.JBRZGSCB+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.JBRZGSCB+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.JBRZGSCB+LogCommon.DEL);
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

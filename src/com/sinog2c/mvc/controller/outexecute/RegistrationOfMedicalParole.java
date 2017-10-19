package com.sinog2c.mvc.controller.outexecute;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * 保外就医合议登记
 * @author hzl
 *
 */
@Controller
public class RegistrationOfMedicalParole extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	
	
	//跳转到罪犯处理页面
	@RequestMapping("/toRegistrationOfMedicalParoleChoosePage")
	public ModelAndView toRegistrationOfMedicalParoleChoosePage(HttpServletRequest request) throws Exception {
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/chooseCriminal/registrationOfMedicalParoleChoose.jsp"));
	}
	
//	//获取罪犯数据
//	@RequestMapping("/getRegistrationOfMedicalParoleChoose")
//	@ResponseBody
//	public Object getRegistrationOfMedicalParoleChoose(HttpServletRequest request) throws Exception {
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
//			String key = request.getParameter("key")==null?"":request.getParameter("key");//取得参数
//			String condition = prisonerService.getTheQueryCondition("10156");
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
//			map.put("tempid", tempid);
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
//			
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}
	
	/**
	 * 方法描述：打开新增表单页面
	 * @author hzl
	 * @throws Exception 
	 */
	@RequestMapping(value = "/showRegistrationOfMedicalParole")
	public ModelAndView showRegistrationOfMedicalParole(HttpServletRequest request){
		returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		if(crimid==null || "".equals(crimid)){
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];
		}
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);;
		String tempid = "GDSJ_JWZX_HYDJ";
		TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService.getCrimeByCrimid(crimid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid, crimid, null);
		if (document != null) {
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}else{
			String year = new SimpleDateFormat("yyyy").format(new Date());
			TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
			SystemOrganization org=systemOrganizationService.getByOrganizationId(user.getDepartid());
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
			if (template != null) {
				docconent.add(template.getContent());
			}
			String xuhao = tbsysDocumentService.getXuHao(year+GkzxCommon.YEAR, tempid, null, null);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("text1", org.getName());
			map.put("text17", year);
			map.put("text18", org.getShortname());
			map.put("text19", "");
			map.put("text20", xuhao);
			if(tbprisonerBaseinfo!=null){
				map.put("cbiname",tbprisonerBaseinfo.getName());
				map.put("cbigendername", tbprisonerBaseinfo.getGender());
				map.put("age",DateUtil.getAge(tbprisonerBaseinfo.getBirthday()));
				map.put("cbinativenamedetail", tbprisonerBaseinfo.getRegisteraddressdetail());
				map.put("cbihomeaddress", tbprisonerBaseinfo.getFamilyaddress());
			}
			if(tbprisonerBaseCrime!=null){
				map.put("anyouhuizong", tbprisonerBaseCrime.getCauseaction());
				map.put("zhuxing", tbprisonerBaseCrime.getMaincase());
				if(tbprisonerBaseCrime.getSentencestime()!=null){
					map.put("cjibegindate", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getSentencestime()));
					map.put("opcinprisonend", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getSentenceetime()));
					map.put("criofficiallyplacedate",DateUtil.dateFormatForAip(tbprisonerBaseCrime.getInprisondate()));
				}
			}
			Map<String, Object> tmap=tbxfSentencealterationService.selectTbxfByCrimid(crimid);
			if(tmap!=null){
				map.put("prisonterm", tmap.get("SENTENCECHAGEINFO"));
			}
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("crimid",crimid);
		request.setAttribute("tempid",tempid);
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/aip/addRegistrationOfMedicalParole.jsp"));
	}
	/**
	 * 修改/保存大字段
	 * @author kexz
	 * 2014-7-29
	 */
	@RequestMapping(value = "/saveRegistrationOfMedicalParole")
	@ResponseBody
	public int saveRegistrationOfMedicalParole(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String introduction = "";//文书简介
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		String noteinfo = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		if (noteinfo != null) {
			ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(noteinfo, Object.class);
			if(list!=null && list.size()>0){
				Map<String,String> map = (Map<String, String>)list.get(0);
				introduction = map.get("cbiname")+LogCommon.BAOWAIJIUYIHEYIDENGJI+map.get("text17")+"年"+map.get("text20");
			}
		}
		if(StringNumberUtil.isNullOrEmpty(docid)){
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setCrimid(crimid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.BAOWAIJIUYIHEYIDENGJI+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.BAOWAIJIUYIHEYIDENGJI+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BAOWAIJIUYIHEYIDENGJI+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else {
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.BAOWAIJIUYIHEYIDENGJI+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.BAOWAIJIUYIHEYIDENGJI+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BAOWAIJIUYIHEYIDENGJI+LogCommon.EDIT);
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
	
	
}

package com.sinog2c.mvc.controller.outexecute;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 保外就医罪犯考察表
 * @author liuchaoyang
 * 下午01:02:41
 */

@Controller
@RequestMapping("/criminalInvestigation")
public class TheCriminalInvestigationTable extends ControllerBase {
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
	
	
	/**
	 * 罪犯处理页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/criminalInvestigationChoose.page")
	public ModelAndView criminalInvestigationChoose(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/criminalInvestigationChoose");
	}
	
//	/**
//	 * 获取所有罪犯列表
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/getCriminalInvestigationChoose.json")
//	@ResponseBody
//	public Object getCcriminalInvestigationChoose(HttpServletRequest request) throws Exception {
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");//取得参数
//			String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");//取得模板编号
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));     
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("tempid", tempid);
//			map.put("key", key);
//			map.put("orgid", getLoginUser(request).getOrgid());
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
//		return resultmap;
//	}
	
	/**
	 * 处理操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/criminalInvestigation.page")
	public ModelAndView criminalInvestigation(HttpServletRequest request){
		String idname = request.getParameter("name");
		String menuid = request.getParameter("menuid");
		String tempid = request.getParameter("tempid");
		String id = request.getParameter("crimid");
		String crimid=request.getParameter("id");
		String name="";
		
		if(crimid==null || "".equals(crimid)){
			request.setAttribute("id", id);
			String[] ids = id == null ? null:id.split(",");
			String[] idnames = idname == null?null:idname.split(",");
			crimid = ids ==null?"":ids[0];
			name = idnames == null?"":idnames[0];
		}
		request.setAttribute("menuid", menuid);
		request.setAttribute("idname",idname);
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("tempid", tempid);
		return new ModelAndView("outexecute/prisonDocuments/shanghai/criminalInvestigationList");
	}
	/**
	 * 方法描述：列表页面
	 */
	@RequestMapping("/getCriminalInvestigationList.json")
	@ResponseBody
	public HashMap<String, Object> getCriminalInvestigationList(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
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
	 * @author kexz
	 * 处理、查看、修改跳转页面并显示表单
	 * 2014-7-29
	 */
	@RequestMapping("/criminalInvestigationAdd.page")
	public ModelAndView criminalInvestigationAdd(HttpServletRequest request){
		returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		String docid = request.getParameter("docid");
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();//根据根据登录用户去获取对应部门id
		if (!StringNumberUtil.isNullOrEmpty(docid)) {
			TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null, null, null);
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}else{
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			TbprisonerBaseinfo baseInfo = prisonerService.getBasicInfoByCrimid(crimid);//基本信息
			TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(crimid);//处罚信息
			if (template != null) {
				docconent.add(template.getContent());
			}
			SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("orgname",org.getName());
			map.put("cbiname",baseInfo.getName());
			map.put("xingbie",baseInfo.getGender());
			map.put("age",DateUtil.getAge(baseInfo.getBirthday()));
			map.put("zhuzhi",baseInfo.getFamilyaddress());
			
			if (!StringNumberUtil.isNullOrEmpty(baseCrime)){
				map.put("zuiming",baseCrime.getMaincase());
				map.put("xingqiqiri",DateUtil.dateFormatForAip(baseCrime.getSentencestime()));
				map.put("xingqizhiri", DateUtil.dateFormatForAip(baseCrime.getSentenceetime()));
			}
			request.setAttribute("crimid", crimid);
			request.setAttribute("formDatajson", new JSONObject(map).toString());//转json
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/criminalInvestigationAdd");
	}
	
	
	/**
	 * 修改/保存大字段
	 * @author kexz
	 * 2014-7-29
	 */
	@RequestMapping(value = "/saveCriminalInvestigation")
	@ResponseBody
	public int saveCriminalInvestigation(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String departid=user.getDepartid();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if(StringNumberUtil.isNullOrEmpty(docid)){
			document.setDepartid(departid);
			document.setTempid(tempid);
			document.setCrimid(crimid);
			document.setIntroduction(template.getTempname());
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.BWJYZUIFANKAOCHABIAO+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.BWJYZUIFANKAOCHABIAO+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BWJYZUIFANKAOCHABIAO+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else {
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.BWJYZUIFANKAOCHABIAO+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.BWJYZUIFANKAOCHABIAO+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.BWJYZUIFANKAOCHABIAO+LogCommon.EDIT);
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
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/deleteCriminalInvestigation.json")
	@ResponseBody
	public int deleteCriminalInvestigation(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
		result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.SHOUJIANRIZHI+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.SHOUJIANRIZHI+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.SHOUJIANRIZHI+LogCommon.DEL);
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

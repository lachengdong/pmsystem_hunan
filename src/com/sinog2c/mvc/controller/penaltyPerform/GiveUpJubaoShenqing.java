package com.sinog2c.mvc.controller.penaltyPerform;

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
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;

/**
 * 放弃具保申请书
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/jubao")
public class GiveUpJubaoShenqing extends ControllerBase{

	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Autowired
	private SystemModelService systemModelService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	
	@RequestMapping("/toCrimidChoosePage")
	public ModelAndView toCrimidChoosePage(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/giveupjubao");
	}
	
//	@RequestMapping(value = "/getCrimidList.json")
//	@ResponseBody
//	public Object getCrimidList(HttpServletRequest request){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			//取得参数
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			//分页
//			key = URLDecoder.decode(key,"UTF-8");
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
//			map.put("orgid", getLoginUser(request).getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//			
//	    	//根据map传参获取总条数
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	//根据map传参获取罪犯列表
//	    	data= chooseCriminalService.getBasicInfoList(map);
//	    	
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//			
//		}catch (Exception e) {
//		}
//		
//		return resultmap;
//	}
	
	/**
	 * 方法描述：新增数据
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addjubao.page")
	public ModelAndView commissionedAssessmentLetterAdd(HttpServletRequest request){
		String menuid = request.getParameter("menuid");
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		SystemUser user = getLoginUser(request);
		String departid=user.getDepartid();
		HashMap<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> selectmap = new HashMap<String, Object>();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		if (template != null) {
			docconent.add(template.getContent());
			SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);
			TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
			map.put("name", info.getName());
			map.put("date", DateUtil.dateFormatForAip(new Date()));
			if(org!=null){
				map.put("depname", org.getName());
			}
		}
		//转json
		request.setAttribute("tempid", tempid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
		request.setAttribute("menuid", menuid);
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("penaltyPerform/addgiveupjubao");
	}
	
	/**
	 * 修改/保存大字段
	 */
	@RequestMapping(value = "/saveBailBook.json")
	@ResponseBody
	public int saveBailBook(HttpServletRequest request){
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
		String departid=user.getDepartid();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		//通过部门id去找组织机构的信息
		SystemOrganization org=systemOrganizationService.getByOrganizationId(departid);
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if("new".equals(operator)){
			String introduction = ""+org.getName()+template.getTempname();//文书简介
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setCrimid(crimid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.FQJBSQS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.FQJBSQS+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.FQJBSQS+LogCommon.ADD);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.FQJBSQS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.FQJBSQS+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.FQJBSQS+LogCommon.EDIT);
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
	 * 处理操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/chuliShenQing.json")
	public ModelAndView chuliShenQing(HttpServletRequest request){
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
		return new ModelAndView("penaltyPerform/criminaljubao");
	}
	
	/**
	 * 方法描述：列表页面
	 */
	@RequestMapping("/getSingleCrimid.json")
	@ResponseBody
	public HashMap<String, Object> getSingleCrimid(HttpServletRequest request) {
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
	 * 根据文书Id删除数据
	 */
	@RequestMapping(value = "/deleteSinogCrimid")
	@ResponseBody
	public int deleteSinogCrimid(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.FQJBSQS+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.FQJBSQS+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.FQJBSQS+LogCommon.DEL);
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
	 * 查看、修改时，根据文书Id查询出大字段回显
	 */
	@RequestMapping(value = "/editSinogCrimid.page")
	public ModelAndView editSinogCrimid(HttpServletRequest request) {
		String menuid = request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");//大字段主键
		request.setAttribute("docid", docid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null,null, null);
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("penaltyPerform/addgiveupjubao");
	}
	
}

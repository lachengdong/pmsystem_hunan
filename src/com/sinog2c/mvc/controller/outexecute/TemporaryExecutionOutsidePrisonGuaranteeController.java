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


/**
 * 
 *  暂予监外执行保证书
 * @author hzl  
 *
 */
@Controller
public class TemporaryExecutionOutsidePrisonGuaranteeController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private  ChooseCriminalService  chooseCriminalService;
	
	
    @RequestMapping("/temporaryExecutionOutsidePrisonGuaranteeChoose")
	public ModelAndView chooseCriminal(){
	  return new ModelAndView("chooseCriminal/temporaryExecutionOutsidePrisonGuaranteeChoose");
    }
	
//    //获取罪犯列表
//	@RequestMapping("/getTemporaryExecutionOutsidePrisonGuarantee")
//	@ResponseBody
//	public Object getbasicInfoPage(HttpServletRequest request) throws Exception {
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
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	data= chooseCriminalService.getBasicInfoList(map);
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//			
//		}catch (Exception e) {
//		}
//		return resultmap;
//	}
	
	/**
	 * 方法描述：跳转到列表页
	 * 
	 * @author
	 */
	@RequestMapping("/temporaryExecutionOutsidePrisonGuarantee")
	public ModelAndView inPrisonNotice(HttpServletRequest request){
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
		return new ModelAndView("outexecute/temporaryExecutionOutsidePrisonGuarantee");
	}
	
	/**
	 * 方法描述：列表页面获取数据
	 * 
	 * @author 
	 */
	@RequestMapping(value = "/getTemporaryExecutionOutsidePrisonGuaranteeList")
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
	 * 修改/保存大字段
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/saveTemporaryExecutionOutsidePrisonGuarantee")
	@ResponseBody
	public int saveGuarantorQualificationsExaminationTable(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		//获取当前登录的用户
		SystemUser user = getLoginUser(request);
		//根据根据登录用户去获取对应部门id
		String departid=user.getDepartid();
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator");
		String tempid = "JWZX_ZYJWZXBZS";
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		String introduction = template.getTempname();//文书简介
		if("new".equals(operator)){
			String crimid = request.getParameter("crimid");
			TbprisonerBaseinfo tbprisonerBaseinfo=prisonerService.getBasicInfoByCrimid(crimid); 
			String name = tbprisonerBaseinfo.getName();
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction("罪犯"+name+introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ZYJWZXBZS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.ZYJWZXBZS+LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZYJWZXBZS+LogCommon.ADD+LogCommon.EVENT);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else if("edit".equals(operator)){
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(LogCommon.ZYJWZXBZS+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(LogCommon.ZYJWZXBZS+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.ZYJWZXBZS+LogCommon.EDIT+LogCommon.EVENT);
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
	 * 新增页面 
	 * 
	 * @author hzl
	 * @throws Exception 
	 */
	@RequestMapping(value = "/showTemporaryExecutionOutsidePrisonGuaranteeAddForm")
	public ModelAndView showAddFrom(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		String deptid=user.getDepartid();
		JSONArray docconent = new JSONArray();
		String menuid=request.getParameter("menuid");
		String crimid = request.getParameter("crimid");
		TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
		String tempid = request.getParameter("tempid");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
		if (template != null) {
			docconent.add(template.getContent());
		}
		Map<String,Object> map = new HashMap<String,Object>();
		SystemOrganization org=systemOrganizationService.getByOrganizationId(deptid);
		if(org!=null){
			map.put("cbinativenamedetail",org.getName());
		}
		if(tbprisonerBaseinfo!=null){
			map.put("cbiname",tbprisonerBaseinfo.getName());
		}
		
		map.put("crimid", crimid);
		request.setAttribute("crimid",crimid);
		request.setAttribute("tempid",tempid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/ZYJWZXBZS");
	}
	
	/**
	 * 查看、修改暂予监外执行保证书时，根据文书Id查询出大字段回显
	 * @author 
	 */
	@RequestMapping(value = "/editorlookTemporaryExecutionOutsidePrisonGuarantee")
	public ModelAndView editorlook(HttpServletRequest request) {
		JSONArray docconent = new JSONArray();
		String menuid=request.getParameter("menuid");
		String docid = request.getParameter("docid");
		request.setAttribute("menuid", menuid);
		request.setAttribute("docid", docid);
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null,null, null);
		if (document != null) {
			docconent.add(document.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/ZYJWZXBZS");
	}
	/**
	 * 根据文书Id删除数据
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/deleteTemporaryExecutionOutsidePrisonGuarantee")
	@ResponseBody
	public int deleteInPrisonNotice(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.ZYJWZXBZS+LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.ZYJWZXBZS+LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.ZYJWZXBZS+LogCommon.DEL+LogCommon.EVENT);
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

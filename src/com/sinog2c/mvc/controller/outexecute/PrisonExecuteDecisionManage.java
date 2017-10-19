package com.sinog2c.mvc.controller.outexecute;

import java.util.ArrayList;
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
import com.sinog2c.util.common.StringNumberUtil;
/**
 * 
 *  暂予监外执行管理事项告知书
 * @author hzl  
 *
 */
@Controller
public class PrisonExecuteDecisionManage extends ControllerBase {
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
	
	
    @RequestMapping("/prisonExecuteDecisionManageChoose")
	public ModelAndView chooseCriminal(HttpServletRequest request){
	  returnResourceMap(request);
	  return new ModelAndView("chooseCriminal/prisonExecuteDecisionManageChoose");
   }
    
//    //获取罪犯列表
//	@RequestMapping("/getprisonExecuteDecisionManageChoose")
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
//			
//	    	//根据map传参获取总条数
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	//根据map传参获取罪犯列表
//	    	data= chooseCriminalService.getBasicInfoList(map);
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//		}
//		
//		return resultmap;
//	}
	
	/**
	 * 方法描述：跳转到列表页
	 * 
	 * @author
	 */
	@RequestMapping("/prisonExecuteDecisionManageList")
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
		return new ModelAndView("outexecute/prisonExecuteDecisionManage");
	}
	
	/**
	 * 方法描述：列表页面获取数据
	 * 
	 * @author 
	 */
	@RequestMapping(value = "/getprisonExecuteDecisionManage")
	@ResponseBody
	public HashMap<String, Object> getprisonExecuteDecisionManage(
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
	@RequestMapping(value = "/saveprisonExecuteDecisionManage")
	@ResponseBody
	public int saveprisonExecuteDecisionManage(HttpServletRequest request){
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String introduction = LogCommon.ZYJWZXGLSXGZS;
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if("new".equals(operator)){
			String crimid = request.getParameter("crimid");
			TbprisonerBaseinfo tbprisonerBaseinfo  =prisonerService.getBasicInfoByCrimid(crimid);
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction("罪犯"+tbprisonerBaseinfo.getName()+introduction);
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
			log.setLogtype(introduction+LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(introduction+LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(introduction+LogCommon.EDIT+LogCommon.EVENT);
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
	 * 新增、查看、修改页面 
	 * 
	 * @author hzl
	 * @throws Exception 
	 */
	@RequestMapping(value = "/addprisonExecuteDecisionManage")
	public ModelAndView addprisonExecuteDecisionManage(HttpServletRequest request){
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String menuid=request.getParameter("menuid");
		String docid = request.getParameter("docid");
		if(!StringNumberUtil.isNullOrEmpty(docid)){
			TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null,null, null);
			if (document != null) {
				docconent.add(document.getContent());
			}
			request.setAttribute("docid", docid);
		}else{
			SystemUser user = getLoginUser(request);
			String crimid = request.getParameter("crimid");
			TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
			String tempid = request.getParameter("tempid");
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
			if (template != null) {
				docconent.add(template.getContent());
			}
			Map<String,Object> map = new HashMap<String,Object>();
			SystemOrganization org = systemOrganizationService.getByOrganizationId(user.getDepartid());
			org = systemOrganizationService.getByOrganizationId(org.getPorgid());//根据用户所在单位的上级部门id获取监狱局名称
			map.put("shengju",org.getName());
			map.put("cbinativenamedetail",org.getFullname());
			map.put("cbiname",tbprisonerBaseinfo.getName());
			map.put("crimid", crimid);
			request.setAttribute("crimid",crimid);
			request.setAttribute("tempid",tempid);
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("menuid", menuid);
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/addprisonExecuteDecisionManage");
	}
	
	/**
	 * 根据文书Id删除数据
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/deleteprisonExecuteDecisionManage")
	@ResponseBody
	public int deleteprisonExecuteDecisionManage(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String introduction=LogCommon.ZYJWZXGLSXGZS;
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

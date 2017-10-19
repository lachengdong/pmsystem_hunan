package com.sinog2c.mvc.controller.penaltyPerform.inPrisonManagement;

import java.text.SimpleDateFormat;
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
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;

/**
 * @author kexz
 *入监收押通知
 * 2014-7-17
 */
@Controller
@RequestMapping("/inPrisonNotice")
public class InPrisonNoticeController  extends ControllerBase{
	@Resource
	protected FlowBaseService flowBaseService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Resource
	public SystemOrganizationService systemOrganizationService;
	
	/**
	 * 方法描述：跳转到罪犯选择列表页
	 * 
	 * @author liuchaoyang 2014-7-24 17:11:45
	 */
	@RequestMapping("/inPrisonNotice.page")
	public ModelAndView inPrisonNotice(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/inPrisonManagement/inPrisonNotice");
	}
	
	/**
	 * 方法描述：查询表中所有符合条件的数据并按条件分页
	 * 
	 * @author liuchaoyang 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/getInPrisonNoticeList.json")
	@ResponseBody
	public HashMap<String, Object> getInPrisonNoticeList(HttpServletRequest request) throws Exception{
		HashMap<String, Object> result = new HashMap<String, Object>();
		SystemUser user = getLoginUser(request);//获取当前登录的用户   
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
		map.put("departid", user.getOrgid());
		map.put("flowdefid", "doc_rjsytzsp");
    	map.put("sortField", sortField);
    	map.put("sortOrder", sortOrder);
    	map.put("start", String.valueOf(start));
    	map.put("end",String.valueOf(end));
    	List<FlowBase> list = flowBaseService.getBaseListNotInforming(map);
		int count = flowBaseService.getBaseListNotInformingCount(map);
		result.put("total", count);
		result.put("data", list);
		return result;	
	}
	/**
	 * 查看、修改入监收押通知时，根据文书Id查询出大字段回显
	 * @author liuchaoyang
	 * 2014-7-22  11:06:45
	 */
	@RequestMapping(value = "/getInPrisonNotice.page")
	public ModelAndView getInPrisonNotice(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String flowdraftid =request.getParameter("flowdraftid")==null?"":request.getParameter("flowdraftid");
		String docConent  = flowBaseService.getDocConentByFlowdraftId(flowdraftid);
		if (docConent != null) {
			docconent.add(docConent);
		}
		request.setAttribute("orgid",user.getOrgid());
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("penaltyPerform/inPrisonManagement/addInPrisonNotice");
	}
	
	/**
	 * 根据文书Id删除数据
	 * @author liuchaoyang
	 * 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/deleteInPrisonNotice.json")
	@ResponseBody
	public int deleteInPrisonNotice(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
        result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.RJSYTZ+LogCommon.OPERATE);
		log.setOpaction(LogCommon.ADD);
		log.setOpcontent(LogCommon.RJSYTZ+LogCommon.ADD);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.RJSYTZ+LogCommon.ADD);
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
	 * 方法描述：根据表单Id到模板信息表查询大字段，查询出表单需要显示的数据，显示到新增页面 
	 * 
	 * @author liuchaoyang 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/getInPrisonNoticeFrom.page")
	public ModelAndView getInPrisonNoticeFrom(HttpServletRequest request) {
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		String menuid = request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid", tempid);
		HashMap<String, Object> map = new HashMap<String, Object>();
		String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
		if (template != null) {
			docconent.add(template.getContent());
			String xuhao = flowBaseService.getFlowXuHao(year, "doc_rjsytzsp", null, user.getDepartid());//获取序号
			
			String departid = user.getDepartid();
			String jailname = systemOrganizationService.getOrginfoByOrgid(departid).getName();
			
			map.put("text33",jailname+template.getTempname());//取部门名称、表单名称拼接为标题
			map.put("text2", user.getPrison().getShortname());//取部门简称		
			map.put("cdnyear",year);
			map.put("cdnid", xuhao);
			map.put("cdnreceivepoliceman", user.getName());
			map.put("cdnfillindate", DateUtil.dateFormatForAip(new Date()));
			map.put("applyid", flowBaseService.getSeqBySeqname("seq_doc_rjsytzsp"));    
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("flowdefid", "doc_rjsytzsp");
		request.setAttribute("orgid",user.getOrgid());
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/inPrisonManagement/addInPrisonNotice");
	}
}

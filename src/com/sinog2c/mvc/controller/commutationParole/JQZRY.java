package com.sinog2c.mvc.controller.commutationParole;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.util.common.StringNumberUtil;
@Controller
public class JQZRY extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
//	@Autowired
//	private FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private FlowBaseService flowBaseService;
//	@Autowired
//	private PrisonerService prisonerService;
	@RequestMapping("/JQZRYChooseList")
	public ModelAndView GuarantorCrimidChooseList(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/JQZRYChoose");
	}
	
	@RequestMapping(value = "/getJQZRYList")
	@ResponseBody
	public Object getGuarantorQualificationsExaminationTableList(HttpServletRequest request) throws Exception{
		SystemUser user = getLoginUser(request);
		HashMap<String, Object> result = new HashMap<String, Object>();
		String deptId=user.getDepartid();//获取当前登录的用户
		String key = request.getParameter("key")==null?"":request.getParameter("key");
		key = URLDecoder.decode(key,"UTF-8");
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
		map.put("departId", deptId);
		map.put("applyid", user.getUserid());
		map.put("flowdefid", "other_jqzbanry");
    	map.put("sortField", sortField);
    	map.put("sortOrder", sortOrder);
    	map.put("start", String.valueOf(start));
    	map.put("end",String.valueOf(end));   
		List<Map> list = flowBaseService.findByMapCondition(map);
		int count = flowBaseService.countAllByCondition(map);
		result.put("total", count);
		result.put("data", list);
		return result;
	}	
	@RequestMapping(value = "/addJQZRY")
	public ModelAndView addGetGuarantor(HttpServletRequest request){
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);
		String flowdraftid=request.getParameter("flowdraftid");	
		Map<String, Object> selectmap = new HashMap<String, Object>();
		if(!StringNumberUtil.isNullOrEmpty(flowdraftid)){
			String baseDoc= flowBaseService.getDocConentByFlowdraftId(flowdraftid);
			if (baseDoc != null) {
				docconent.add(baseDoc);
			}
			request.setAttribute("flowdraftid", flowdraftid);
		}else{
			String menuid=request.getParameter("menuid")==null?"":request.getParameter("menuid");
			String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");;
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
			Map<String,Object> map = new HashMap<String,Object>();
			if (template != null) {
				docconent.add(template.getContent());
			}
			map.put("jianqu", user.getOrganization().getName());
			request.setAttribute("tempid",tempid);
			request.setAttribute("menuid",menuid);
			request.setAttribute("applyname",user.getName());
			request.setAttribute("applyid",user.getUserid());
			request.setAttribute("flowdefid", "other_jqzbanry");
			request.setAttribute("orgid",user.getOrganization().getOrgid());
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
		return new ModelAndView("aip/addJQZRY");
	}
}

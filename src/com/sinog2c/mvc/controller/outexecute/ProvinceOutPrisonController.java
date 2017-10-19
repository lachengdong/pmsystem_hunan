package com.sinog2c.mvc.controller.outexecute;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 省局保外办案
 *
 */
@Controller
public class ProvinceOutPrisonController extends ControllerBase {

	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Autowired
	private UvFlowService uvFlowService;
	
	@RequestMapping(value="toBaowaiProvinceLiAnListPage")
	public ModelAndView jaiAssuc(HttpServletRequest request){
		
		returnResourceMap(request);
		
		String curyear = "";
		Date date = new Date();
		curyear = DateUtil.dateFormat(date, "yyyy");
		
		SystemUser su = getLoginUser(request);
		String orgid = su.getOrgid();
		String departid = su.getDepartid();
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		Map paramMap = new HashMap();
		paramMap.put("departid", departid);
		paramMap.put("flowdefid", flowdefid);
		paramMap.put("curyear", curyear);
		String casenumber = flowBaseService.getLastCaseNum(paramMap);//案件号
		
		SystemOrganization so = systemOrganizationService.getByOrganizationId(orgid);
		String shortname = so.getShortname();
		String casetype = GkzxCommon.BWTYPE;//省局保外无初保、续保之分
		request.setAttribute("casenumber", casenumber);
		request.setAttribute("curyear", curyear);
		request.setAttribute("shortname", shortname);
		request.setAttribute("casetype", casetype);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/outexecute/baowaiProvinceLiAnListPage.jsp"));
	}
	
	
	/**
	 * 方法描述：列表页面
	 * 2014-8-11
	 */
	@RequestMapping(value = "/getProvinceOutPrisonLiAnListData")
	@ResponseBody
	public Object getProvinceOutPrisonLiAnListData(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map> data = new ArrayList<Map> ();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));       
			SystemUser su = getLoginUser(request);
			String orgid = su.getOrgid();
			String departid = su.getDepartid();
			String jailid = request.getParameter("jailid");
			String flowdefid = request.getParameter("flowdefid");
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			Map<String,Object> map = new HashMap<String,Object>();
			if(StringNumberUtil.notEmpty(key)){
				map.put("key", key);
			}
			map.put("orgid", orgid);
			map.put("departid", departid);
			map.put("status", "1");
			if(StringNumberUtil.notEmpty(jailid)){
				map.put("jailid", jailid);
			}
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("flowdefid", flowdefid);
	    	map.put("state", "-1,0,1,2");
			
	    	int count = uvFlowService.countProvinceOutPrisonLiAnList(map);
	    	data= uvFlowService.getProvinceOutPrisonLiAnList(map);
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultmap;
	}
	
	
}

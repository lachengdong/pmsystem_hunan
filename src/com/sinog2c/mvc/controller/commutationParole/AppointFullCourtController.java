package com.sinog2c.mvc.controller.commutationParole;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;
/**
 * 指定合议庭
 * 
 * @author hzl
 * 
 */
@Controller
public class AppointFullCourtController extends ControllerBase {
	
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private UvFlowService uvFlowService;
//	@Autowired
//	private TbCourtFullCourtService tbCourtFullCourtService;
	

	/**指定合议庭
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toAppointFullCourtPage")
	public ModelAndView toAppointFullCourtPage(HttpServletRequest request) {
		
		returnResourceMap(request);
		
		String curyear = "";
		Date date = new Date();
		curyear = DateUtil.dateFormat(date, "yyyy");
		
		SystemUser su = getLoginUser(request);
		String orgid = su.getOrgid();
		String departid = su.getDepartid();
//		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		String changecourt = request.getParameter("changecourt");//更改合议庭标志  yes
		
		SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
		String shortname = so.getShortname();
		request.setAttribute("curyear", curyear);
		request.setAttribute("shortname", shortname);
//		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("changecourt", changecourt);
		ModelAndView mav = null;
		if(changecourt!=null && "yes".equals(changecourt)){//更改合议庭
			View view = new InternalResourceView("WEB-INF/JSP/court/changeFullCourt.jsp");
			mav = new ModelAndView(view);
		}else{
			View view = new InternalResourceView("WEB-INF/JSP/court/appointFullCourt.jsp");
			mav = new ModelAndView(view);
		}
		return mav;
	}
	 
	 /**
		 * 方法描述：获取指定合议庭页面数据列表数据
		 * 2014-8-7
	*/
	@RequestMapping(value = "/getCollegialPanelListData")
	@ResponseBody
	public Object getCollegialPanelListData(HttpServletRequest request){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map> data = new ArrayList<Map> ();
		try {
			String changecourt = request.getParameter("changecourt");
			
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String flowdefid = request.getParameter("flowdefid");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? 0:Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? 0:Integer.parseInt(request.getParameter("pageSize")));        
			SystemUser su = getLoginUser(request);
			String orgid = su.getOrgid();
			String departid = su.getDepartid();
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			String jailid = request.getParameter("jailid");
//			String casetype = request.getParameter("casetype");
			String casenums = request.getParameter("casenums");
			String curyear = request.getParameter("curyear");
			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("casetype", casetype);
			map.put("suid", su.getUserid());
			map.put("orgid", orgid);
			map.put("departid", departid);
			map.put("flowdefid", flowdefid);
			map.put("key", key);
			map.put("jailid", jailid);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	if(StringNumberUtil.notEmpty(casenums)&&StringNumberUtil.notEmpty(curyear)){
	    		casenums = StringNumberUtil.formatCaseNo(casenums,curyear);
		    	map.put("casenums", casenums);
	    	}
	    	
	    	int count = 0;
	    	if(StringNumberUtil.notEmpty(changecourt)&&"yes".equals(changecourt.trim())){
	    		//更改合议庭数据
	    		count = uvFlowService.countChangeCollegialPanelListData(map);
		    	data= uvFlowService.getChangeCollegialPanelListData(map);
	    	}else{
	    		//指定合议庭数据
	    		count = uvFlowService.countCollegialPanelListData(map);
		    	data= uvFlowService.getCollegialPanelListData(map);
	    	}
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	/**
	 * 跳转人民陪审员列表页
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/openPeoplesAssessorPage")
	public ModelAndView toTabsPage() {
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/court/peopleJuryman.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	
	/**
	 * 方法描述：获取人民陪审员列表
	 * 2014-8-7
	*/
	@RequestMapping(value = "/queryPeoplesAssessor")
	@ResponseBody
	public Object queryPeoplesAssessor(HttpServletRequest request){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map> data = new ArrayList<Map> ();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
		String flowdefid = request.getParameter("flowdefid");
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		SystemUser su = getLoginUser(request);
		String orgid = su.getOrgid();
		String departid = su.getDepartid();
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		
		String jailid = request.getParameter("jailid");
		String casenums = request.getParameter("casenums");
		String curyear = request.getParameter("curyear");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("suid", su.getUserid());
		map.put("orgid", orgid);
		map.put("departid", departid);
		map.put("flowdefid", flowdefid);
		map.put("key", key);
		map.put("jailid", jailid);
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		map.put("start", String.valueOf(start));
		map.put("end",String.valueOf(end));
		if(StringNumberUtil.notEmpty(casenums)&&StringNumberUtil.notEmpty(curyear)){
			casenums = StringNumberUtil.formatCaseNo(casenums,curyear);
	    	map.put("casenums", casenums);
		}
		
		int count = uvFlowService.countCollegialPanelListData(map);
		data= uvFlowService.getCollegialPanelListData(map);
		
		resultmap.put("data", data);
		resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	/**
	 * 跳转合议庭列表页
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/fullCourtSettle")
	public ModelAndView tofullCourtSettlePage() {
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/court/fullCourtSettle.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
//	/**
//	 * 方法描述：获取合议庭列表
//	 * 2014-8-7
//	*/
//	@RequestMapping(value = "/getFullCourtsListData")
//	@ResponseBody
//	public Object getFullCourtsListData(HttpServletRequest request){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map> data = new ArrayList<Map> ();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
//			SystemUser su = getLoginUser(request);
//			String orgid = su.getOrgid();
//			String departid = su.getDepartid();
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("suid", su.getUserid());
//			map.put("departid", departid);
//			map.put("key", key);
//			map.put("sortField", sortField);
//			map.put("sortOrder", sortOrder);
//			map.put("start", String.valueOf(start));
//			map.put("end",String.valueOf(end));
//			
//			int count = tbCourtFullCourtService.countFullCourtsListData(map);
//			data= tbCourtFullCourtService.getFullCourtsListData(map);
//			
//			resultmap.put("data", data);
//			resultmap.put("total", count);
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return resultmap;
//	}
	
//	/**
//	 * 方法描述：获取合议庭列表
//	 * 2014-8-7
//	*/
//	@RequestMapping(value = "/getCourtsListData")
//	@ResponseBody
//	public Object getCourtsListData(HttpServletRequest request){
//		List<Map> data = new ArrayList<Map>();
//		SystemUser su = getLoginUser(request);
//		String orgid = su.getOrgid();
//		String departid = su.getDepartid();
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("departid", departid);
//		
//		data= tbCourtFullCourtService.getCourtsListData(map);
//		return data;
//	}
	
//	/**
//	 * 方法描述：获取合议庭列表
//	 * 2014-8-7
//	*/
//	@RequestMapping(value = "/getCourtFullCourtDataById")
//	@ResponseBody
//	public Object getCourtFullCourtDataById(HttpServletRequest request){
//		String fullcourtid = request.getParameter("fullcourtid");
//		if(StringNumberUtil.notEmpty(fullcourtid)){
//			return tbCourtFullCourtService.selectByPrimaryKey(Integer.parseInt(fullcourtid));
//		}
//		return null;
//	}
	
	
	/**
	 * 跳转列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addPeopleJuryman")
	public ModelAndView toAddPage() {
		ModelAndView mav = null;
		View view = new InternalResourceView(
				"WEB-INF/JSP/commutationParole/addPeopleJuryman.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	
	/**
	 * 跳转合议庭新增列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toOperateFullCourtPage")
	public ModelAndView toOperateFullCourtPage(HttpServletRequest request){
		SystemUser su = getLoginUser(request);
		String departid = su.getDepartid();
		String orgname = systemOrganizationService.getByOrganizationId(departid).getName();
		if(StringNumberUtil.notEmpty(orgname)){
			request.setAttribute("orgname", orgname);
		}
		
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/court/operateFullCourtPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
//	/**
//	 * 此方法描述的是：保存合议庭记录
//	 * @exception 异常对象
//	 */
//	@RequestMapping(value = "/saveOrUpdateColloegiate")
//	@ResponseBody
//	public String saveOrUpdateColloegiate(HttpServletRequest request) {
//		SystemUser su = this.getLoginUser(request);
//		String data = request.getParameter("data");
//		String operatetype = request.getParameter("operatetype");
//		String fullcourtid = request.getParameter("fullcourtid");
//		Object obj = JsonUtil.Decode(data);
//		Map map = new HashMap();
//		if(null!=obj){
//			List<Map> list = (ArrayList<Map>)obj;
//			map = list.get(0);
//		}
//		map.put("departid", su.getDepartid());
//		int count = 0;
//		if(StringNumberUtil.notEmpty(operatetype)&&"edit".equals(operatetype.trim())){
//			map.put("fullcourtid", Integer.parseInt(fullcourtid.trim()));
//			count = tbCourtFullCourtService.updateByPrimaryKeyMapSelective(map);
//		}else{
//			count = tbCourtFullCourtService.insertSelectiveByMap(map);
//		}
//		if(count==1){
//			return "success";
//		}else{
//			return "error";
//		}
//	}
	
//	/**
//	 * 此方法描述的是：删除合议庭记录
//	 * @exception 异常对象
//	 */
//	@RequestMapping(value = "/deleteColloegiate")
//	@ResponseBody
//	public String deleteColloegiate(HttpServletRequest request) {
//		
//		String id = request.getParameter("id");
//		if(StringNumberUtil.isEmpty(id)){
//			return "error";
//		}
//		int count = 0;
//		Map paraMap = new HashMap();
//		paraMap.put("fullcourtids", id);
//		count = tbCourtFullCourtService.deleteFullCourtsByMap(paraMap);
//		
//		if(count >=1){
//			return "success";
//		}else{
//			return "error";
//		}
//	}
	
}

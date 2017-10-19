package com.sinog2c.mvc.controller.solution;

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
import com.sinog2c.model.solution.FormParamMapping;
import com.sinog2c.model.solution.FormSolutionDetail;
import com.sinog2c.model.solution.FormSqlGroup;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.solution.FormParamMappingService;
import com.sinog2c.service.api.solution.FormSolutionDetailService;
import com.sinog2c.service.api.solution.FormSolutionService;
import com.sinog2c.service.api.solution.FormSqlGroupService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
@Controller
@RequestMapping("/solution")
public class FormSolutionController extends ControllerBase {
	@Autowired
	private FormSolutionService formSolutionService;
	@Autowired
	private FormSolutionDetailService formSolutionDetailService;
	@Autowired
	private FormSqlGroupService formSqlGroupService;
	@Autowired
	private FormParamMappingService formParamMappingService;
	@Autowired
	private CommonSQLSolutionService commonSQLSolutionService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemResourceService systemResourceService;
	
	@RequestMapping(value = "/toFormSolutionPage")
	public ModelAndView toFormSolutionPage(){
		// 模型和视图.
		ModelAndView mav = new ModelAndView("system/queryScheme2");
		return mav;
	}
	

	@RequestMapping(value = "/getsinglesolution.page")
	public ModelAndView getSingleSolutionPage(){
		// 模型和视图.
		ModelAndView mav = new ModelAndView("solution/getsinglesolution");
		return mav;
	}
	
	/**
	 * 拷贝方案。。。
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSolutionSchemeTree.json")
	@ResponseBody
	public Object getSolutionSchemeTree(HttpServletRequest request) {
		Map<String, Object> map= parseParamMap(request);
		return formSolutionService.getTreeDataOfSolution(map);
	}
	
	@RequestMapping("/ajax/copySolution.json")
	@ResponseBody
	public JSONMessage copySolution(HttpServletRequest request) {
		Map<String, Object> map= parseParamMap(request);
		SystemUser operator = getLoginUser(request);
		boolean result = formSolutionService.copySolution(map, operator);
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		String info = "";
		if(result){
			message.setSuccess();
			info = "拷贝成功";
		} else {
			info = "操作失败";
		}
		// 
		message.setInfo(info);
		//
		return message;
	}
	
	@RequestMapping("/getSingleSolutionScheme.action")
	@ResponseBody
	public Object getSingleSolutionScheme(HttpServletRequest request) {
		String solutionid = request.getParameter("solutionid");
		return formSolutionService.getById(solutionid);
	}
	
	@RequestMapping("/getSingleSolutionDetail.action")
	@ResponseBody
	public Object getSingleSolutionDetail(HttpServletRequest request) {
		Map<String,Object> map = this.parseParamMap(request);
		return formSolutionDetailService.getSingleSolutionDetail(map);
	}
	
	@RequestMapping("/getSingleSqlGroup.action")
	@ResponseBody
	public Object getSingleSqlGroup(HttpServletRequest request) {
		Map<String,Object> map = this.parseParamMap(request);
		return formSqlGroupService.getFormSqlGroupData(map);
	}
	@RequestMapping("/getSingleParamMapping.action")
	@ResponseBody
	public Object getSingleParamMapping(HttpServletRequest request) {
		Map<String,Object> map = this.parseParamMap(request);
		return formParamMappingService.getSingleParamMapping(map);
	}
	
	@RequestMapping("/saveSolutionScheme.action")
	@ResponseBody
	public Object saveSolutionScheme(HttpServletRequest request){
		SystemUser su = this.getLoginUser(request);
		Map<String,Object> map = this.parseParamMap(request);
		map.putAll(parseParamData("data",request));
		return formSolutionService.saveSolutionScheme(map,su);
	}
	@RequestMapping("/saveSolutionDetail.action")
	@ResponseBody
	public Object saveSolutionDetail(HttpServletRequest request){
		SystemUser su = this.getLoginUser(request);
		Map<String,Object> map = this.parseParamMap(request);
		map.putAll(parseParamData("data",request));
		return formSolutionDetailService.saveSolutionDetail(map,su);
	}
	@RequestMapping("/saveDetailSqlGroup.action")
	@ResponseBody
	public Object saveDetailSqlGroup(HttpServletRequest request){
		SystemUser su = this.getLoginUser(request);
		Map<String,Object> map = this.parseParamMap(request);
		map.putAll(parseParamData("data",request));
		return formSqlGroupService.saveDetailSqlGroup(map,su);
	}
	@RequestMapping("/saveParamMapping.action")
	@ResponseBody
	public Object saveParamMapping(HttpServletRequest request){
		SystemUser su = this.getLoginUser(request);
		Map<String,Object> map = this.parseParamMap(request);
		map.putAll(parseParamData("data",request));
		return formParamMappingService.saveParamMapping(map,su);
	}
	
	
	@RequestMapping("/getSolutionDetailList.json")
	@ResponseBody
	public Object getSolutionDetailList(HttpServletRequest request) {
		Map<String, Object> map= parseParamMap(request);
		List<FormSolutionDetail> list = formSolutionDetailService.getTreeDataOfSolutionDetail(map);
		return list;
	}
	
	@RequestMapping("/getFormSqlGroupList.json")
	@ResponseBody
	public Object getFormSqlGroupList(HttpServletRequest request) {
		Map<String, Object> map= parseParamMap(request);
		List<FormSqlGroup> list = formSqlGroupService.getTreeDataOfFormSqlGroup(map);
		return list;
	}
	
	@RequestMapping(value = "/toEditSolutionClobPage")
	public ModelAndView toEditSolutionClobPage(){
		ModelAndView mav = new ModelAndView("solution/clobEdit");
		return mav;
	}
	
	@RequestMapping("/getFormParamMappingDataList")
	@ResponseBody
	public Object getFormParamMappingDataList(HttpServletRequest request) {
		Map<String, Object> map= parseParamMap(request);
		map = parsePageInfoOfMap(map);
		if(StringNumberUtil.isEmpty(map.get("solutionid"))&&StringNumberUtil.isEmpty(map.get("detailid"))&&StringNumberUtil.isEmpty(map.get("sqlgroupid"))){
			return null;
		}
		List<FormParamMapping> list = formParamMappingService.getFormParamMappingDataList(map);
		int count = formParamMappingService.countFormParamMappingDataList(map);
		Map<String, Object> resultmap = new HashMap<String,Object>();
		resultmap.put("data", list);
		resultmap.put("total", count);
		return resultmap;
	}
	
	@RequestMapping("/getFormSqlGroupData")
	@ResponseBody
	public Object getFormSqlGroupData(HttpServletRequest request){
		Map<String, Object> map= parseParamMap(request);
		FormSqlGroup result = formSqlGroupService.getFormSqlGroupData(map);
		return result;
	}
	
	@RequestMapping(value = "/toOperateSolutionPage")
	public ModelAndView toOperateSolutionPage(){
		return new ModelAndView("solution/operateSolution");
	}
	@RequestMapping(value = "/toOperateSolutionDetailPage")
	public ModelAndView toOperateSolutionDetailPage(){
		return new ModelAndView("solution/operateSolutionDetail");
	}
	@RequestMapping(value = "/toOperateSqlGroupPage")
	public ModelAndView toOperateSqlGroupPage(){
		return new ModelAndView("solution/operateDetailSqlGroup");
	}
	@RequestMapping(value = "/toOperateParamMappingPage")
	public ModelAndView toOperateParamMappingPage(){
		return new ModelAndView("solution/operateSqlParamMapping");
	}
	
	@RequestMapping("/deleteSolutionScheme.action")
	@ResponseBody
	public Object deleteSolutionScheme(HttpServletRequest request){
		String solutionid = request.getParameter("solutionid");
		SystemUser su = this.getLoginUser(request);
		return formSolutionService.delete(solutionid,su);
	}

	@RequestMapping({"/ajax/removesolution.json"})
	@ResponseBody
	public Object deleteSolutionJSON(HttpServletRequest request){
		JSONMessage message = JSONMessage.newMessage();
		SystemUser su = this.getLoginUser(request);
		try{
			String solutionid = request.getParameter("solutionid");
			int rows = formSolutionService.delete(solutionid,su);
			//
			message.setStatus(rows);
			message.setInfo("删除成功");
		} catch (Exception e) {
			message.setInfo("删除失败");
		}
		//
		return message;
	}
	
	@RequestMapping("/deleteSolutionDetail.action")
	@ResponseBody
	public Object deleteSolutionDetail(HttpServletRequest request){
		String detailid = request.getParameter("detailid");
		SystemUser su = this.getLoginUser(request);
		return formSolutionDetailService.deleteSolutionDetail(detailid,su);
	}
	
	@RequestMapping("/deleteDetailSqlGroup.action")
	@ResponseBody
	public Object deleteDetailSqlGroup(HttpServletRequest request){
		String sqlgroupid = request.getParameter("sqlgroupid");
		SystemUser su = this.getLoginUser(request);
		return formSqlGroupService.deleteDetailSqlGroup(sqlgroupid,su);
	}
	
	@RequestMapping("/deleteParamMapping.action")
	@ResponseBody
	public Object deleteParamMapping(HttpServletRequest request){
		Map<String,Object> map = this.parseParamMap(request);
		String paramid = request.getParameter("paramid");
		paramid = StringNumberUtil.formatString(paramid,",");
		map.put("paramid", paramid);
		return formParamMappingService.deleteParamMapping(map);
	}
	
	
	/**
	 * 测试用，用后删掉
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/test_toBasicInfoChoosePage.page")
	public ModelAndView testToBasicInfoChoosePage(HttpServletRequest request) throws Exception {
		String menuid = request.getParameter("menuid");
		menuid = menuid.replace("'", "");
		request.setAttribute("menuid", menuid);
		return new ModelAndView("solution/test_basicInfo");
	}
	
//	/**
//	 * 测试用，用后删掉
//	 *方法描述：获取罪犯列表数据 
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@Autowired
//	PrisonerService prisonerService;
//	@RequestMapping("/getBasicInfoList.json")
//	@ResponseBody
//	public Object getBasicInfoList(HttpServletRequest request) throws Exception {
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<TbprisonerBaseinfo> data = new ArrayList<TbprisonerBaseinfo>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			key = URLDecoder.decode(key,"UTF-8");
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("key", key);
//			map.put("departId", getLoginUser(request).getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));   
//	    	map.put("end",String.valueOf(end));
//	    	int count = prisonerService.countAllByCondition(map);//根据map传参获取总条数
//	    	map.put("flowdefid", "doc_zfrjdjsp");
//	    	data= prisonerService.getBasicInfoList(map);//根据map传参获取罪犯列表
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return resultmap;
//	}
	
	@RequestMapping(value = "/toPublicFormPage")
	public ModelAndView toPublicFormPage(HttpServletRequest request){
		SystemUser user = this.getLoginUser(request);
		
		String menuid = request.getParameter("menuid");
		List<SystemResource> topButtonList = systemResourceService.listByMenuid(user,menuid,12);//页面顶部按钮
		
		//组合参数Map: paramMap
		String solutionid = request.getParameter("solutionid");
		String tempid = request.getParameter("tempid");
		String flowdefid = request.getParameter("flowdefid");
		String params = request.getParameter("params");
		Map<String,Object> paramMap = MapUtil.getDataByAip(params);
		paramMap.put("solutionid", solutionid);
		paramMap.put("tempid", tempid);
		paramMap.put("flowdefid", flowdefid);
		
		Map<String,Object> resultMap = commonSQLSolutionService.query(paramMap, user);
		/*
		 * 得到表单所需要的值Map : key为form为表单大字段的值，key为root的值为表单文本域Map，key为options为表单下拉选择map，
		 */
		Map formMap =  resultMap.get("form") instanceof Map ?(Map)resultMap.get("form"):null;//表单上非选择域的值Map
		Map textMap =  resultMap.get("root") instanceof Map ?(Map)resultMap.get("root"):null;//表单上非选择域的值Map
		Map selectMap = resultMap.get("options") instanceof Map?(Map)resultMap.get("options"):null;//表单上选择域的值Map
		
		//
		String formClob = (null!=formMap)? StringNumberUtil.toString(formMap.get("content")) : null ;//获取表单大字段
		if(StringNumberUtil.isEmpty(formClob)){
			formClob = (null!=formMap)? StringNumberUtil.toString(formMap.get("CONTENT")) : null ;//获取表单大字段
		}
		
		
		JSONArray docconent = new JSONArray();
		docconent.add(formClob);
		
		request.setAttribute("formcontent", docconent.toString());
		if(StringNumberUtil.notEmpty(textMap)){
			request.setAttribute("formDatajson", new JSONObject(textMap).toString());
		}
		if(StringNumberUtil.notEmpty(selectMap)){
			request.setAttribute("selectDatajson", new JSONObject(selectMap).toString());
		}
		//将参数保存到JSP页面
		
		request.setAttribute("params",params);
		request.setAttribute("solutionid", solutionid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("topButtonList", topButtonList);
		
		return new ModelAndView("solution/publicFormPage");
	}
	
	@RequestMapping(value = "/saveFormData")
	@ResponseBody
	public Object saveFormData(HttpServletRequest request){
		SystemUser user = this.getLoginUser(request);
		
		//组合参数Map: paramMap
		String solutionid = request.getParameter("solutionid");//查询方案Id
		String tempid = request.getParameter("tempid");//表单模板Id
		String flowdefid = request.getParameter("flowdefid");//流程定义ID
		String params = request.getParameter("params");
		String formclob = request.getParameter("formclob");//表单大字段
		String noteinfo = request.getParameter("noteinfo");//表单各域的值
		
		Map<String,Object> paramMap = MapUtil.getDataByAip(params);
		paramMap.put("solutionid", solutionid);
		paramMap.put("tempid", tempid);
		paramMap.put("flowdefid", flowdefid);
		Map<String,Object> formmap = MapUtil.getDataByAip(noteinfo);
		formmap.put("formclob", formclob);
		paramMap.put("formmap", formmap);
		//paramMap : {solution:solution, tempid:tempid, formmap:{formclob:formclob, key1:value1, ...}}
		Map result = commonSQLSolutionService.save(paramMap, user);
		return result.get("_status");
	}
	
	@RequestMapping("/getSolutionSchemeTreeBySolutionpname.json")
	@ResponseBody
	public Object getSolutionSchemeTreeBySolutionpname(HttpServletRequest request) {
		Map<String, Object> map= parseParamMap(request);
		return formSolutionService.getSolutionSchemeTreeBySolutionpname(map);
	}

}

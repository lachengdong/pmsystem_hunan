package com.sinog2c.mvc.controller.common;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 通用表单处理控制器
 */
@Controller
@RequestMapping("/common")
public class CommonFormController extends ControllerBase{

	@Autowired(required=true)
	private CommonSQLSolutionService solutionService;
	
	public CommonFormController(){
		this.DEBUG_MODE = false;
	}
	/**
	 * 方法描述：所有罪犯-选择-列表页面 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectprisoner.page")
	public String  selectprisonerPage(HttpServletRequest request) throws Exception {

		// 获取用户
		SystemUser user = getLoginUser(request);
		
		Map<String, ? extends Object> paraMap = super.parseParamMap(request);
		super.addMap2Attribute(paraMap, request);
		
		// 将所有参数封装为JS字符串
		Map<String,String> jsParaMap = parseParamMapString(request);
			//request.getParameterMap();
		//
		Object jsonParamObject = JSONObject.toJSON(jsParaMap);
		request.setAttribute("jsonParamObject", jsonParamObject);
		
		//组合参数Map: paramMap
		//String flowdefid = request.getParameter("flowdefid");
		//String params = request.getParameter("params");
		//
		//String tempid = getParameterString(request, "tempid", "");
		//String solutionid = getParameterString(request, "solutionid", "");
		//
		//
		//request.setAttribute("params", params);
		//request.setAttribute("flowdefid", flowdefid);
		//request.setAttribute("menuid", menuid);
		//request.setAttribute("tempid", tempid);
		//request.setAttribute("solutionid", solutionid);
		// 
		String menuid = getParameterString(request, "menuid", "");
		// 页面顶部按钮
		List<SystemResource> topButtonList = systemResourceService.listByMenuid(user,menuid,12);
		request.setAttribute("topButtonList", topButtonList);
		// 列中按钮
		List<SystemResource> inlineButtonList = systemResourceService.listByMenuid(user,menuid,13);
		request.setAttribute("inlineButtonList", inlineButtonList);
		// 获取资源信息. Grid信息
		int gridtypeid = 19;
		List<SystemResource> gridList =systemResourceService.listByMenuid(user, menuid, gridtypeid);
		request.setAttribute("gridList", gridList);
		//
		if(null != gridList && !gridList.isEmpty()){
			SystemResource grid1 = gridList.get(0);
			//
			if(null != grid1){
				//
				request.setAttribute("grid1", grid1);
				//
				int gridcoltypeid = 20;
				String gridid_1 = grid1.getResid();

				List<SystemResource> grid1ColList =systemResourceService.listByMenuid(user, gridid_1, gridcoltypeid);
				request.setAttribute("grid1ColList", grid1ColList);
			}
		}
		//
		// 这里应该
		//
		String pagestr = getParameterString(request, "pagestr", "");
		if(StringNumberUtil.notEmpty(pagestr)){
			return pagestr.trim(); // 如果菜单中直接指定了页面,则直接返回即可
		} else {
			// 否则应该返回公共页面
			// ... 等待临时设置
			return ("common/prisoner/selectprisoner");
		}
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/showform.page")
	public ModelAndView commonformPage(HttpServletRequest request){
		// 获取用户
		SystemUser user = getLoginUser(request);

		//
		Map<String,Object> paramMap = parseParamMap(request);
		// 将参数封装到 Attribute
		addMap2Attribute(paramMap, request);
		//Map<String, ? extends Object> paraMap = super.parseParamMap(request);
		//super.addMap2Attribute(paraMap, request);
		returnResourceMap(request);
		
		String menuid = request.getParameter("menuid");
		//组合参数Map: paramMap
		String solutionid = request.getParameter("solutionid");
		String tempid = request.getParameter("tempid");
		String flowdefid = request.getParameter("flowdefid");
		String params = request.getParameter("params");
		//
		//
		request.setAttribute("params", params);
		request.setAttribute("solutionid", solutionid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("flowdefid", flowdefid);
		// 页面顶部按钮
		// 
		List<SystemResource> topButtonList = systemResourceService.listByMenuid(user,menuid,12);
		request.setAttribute("topButtonList", topButtonList);
		//
		
		Map<String,Object> paramsMap = MapUtil.getDataByAip(params);
		//
		paramMap.putAll(paramsMap);
		//
		paramMap.put("solutionid", solutionid);
		paramMap.put("tempid", tempid);
		paramMap.put("flowdefid", flowdefid);
		
		Map<String,Object> resultMap = solutionService.query(paramMap, user);
		/*
		 * 得到表单所需要的值Map : key为form为表单大字段包装对象，
		 * key为root的值为表单文本域Map，
		 * key为options为表单下拉选择map，
		 */
		//
		resultMap = MapUtil.convertKey2LowerCase(resultMap);
		Object form = resultMap.get("form");
		Object root = resultMap.get("root");
		Object options = resultMap.get("options");
		
		//
		if(form instanceof Map<?, ?>){
			//
			Map<String,Object> mapF = (Map<String, Object>) form;
			mapF = MapUtil.convertKey2LowerCase(mapF);
			//
			Object content = null;
			content = mapF.get("content");

			JSONArray docconent = new JSONArray();
			docconent.add(content);
			// 表单本身
			request.setAttribute("formcontent", docconent.toString());

			//将参数带到JSP页面
			//this.DEBUG_MODE = true;
			//debug("docconent.toString():\n"+docconent.toString());
			//String formClob = StringNumberUtil.notEmpty(resultMap.get("form"))?resultMap.get("form").toString() : null ;//获取表单大字段
		}
		//
		if(root instanceof Map<?, ?>){
			//
			Map<String,Object> mapR = (Map<String, Object>) root;
			mapR = MapUtil.convertKey2LowerCase(mapR);
			// 对日期进行处理,将日期换成字符串
			mapR = processDateMap(mapR, "yyyyMMdd");
			//
			//content = mapR.get("content");
			//Map textMap =  resultMap.get("root") instanceof Map ?(Map)resultMap.get("root"):null;//表单上非选择域的值Map
			// 
			request.setAttribute("formDatajson", new JSONObject(mapR).toString());
			
		} else if(root instanceof List<?>){
			//
			List<Map<String,Object>> mapListR = (List<Map<String,Object>>) root;
			mapListR = MapUtil.convertKeyList2LowerCase(mapListR);
			//
			//content = mapR.get("content");
			// TODO
			//表单上非选择域的值Map
			request.setAttribute("formDatajson", new JSONObject(mapListR.get(0)).toString());
		}
		//
		if(options instanceof Map<?, ?>){
			//
			Map<String,Object> mapO = (Map<String, Object>) options;
			mapO = MapUtil.convertKey2LowerCase(mapO);
			//
			//content = mapR.get("content");
			//Map selectMap = resultMap.get("options") instanceof Map?(Map)resultMap.get("options"):null;//表单上选择域的值Map
			
			request.setAttribute("selectDatajson", new JSONObject(mapO).toString());
			
		} else if(options instanceof List<?>){
			//
			debug("如果执行到这里,那就是出问题了!" + this.getClass() + "; options instanceof List<?> ");
			//
			Map<String,Object> mapO = (Map<String, Object>) options;
			mapO = MapUtil.convertKey2LowerCase(mapO);
			//
			//content = mapR.get("content");
			Map selectMap = resultMap.get("options") instanceof Map?(Map)resultMap.get("options"):null;//表单上选择域的值Map
			
			if(StringNumberUtil.notEmpty(selectMap)){
				request.setAttribute("selectDatajson", new JSONObject(selectMap).toString());
			}
		}
		
		return new ModelAndView("common/form/commonform");
	}
	

	@RequestMapping("/customjs.json")
	@ResponseBody
	public void customJS(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//
		String jsCode = "";
		// 获取用户
		SystemUser user = getLoginUser(request);
		

		String menuid = getParameterString(request, "menuid", "");
		// 资源
		SystemResource menu = systemResourceService.getResourceByUserAndId(user, menuid);
		
		//
		if(null != menu){
			String remark = menu.getRemark();
			if(null != remark){
				jsCode = remark;
			}
		}
		
		//

		response.setContentType("text/plain;charset=utf-8"); 
		PrintWriter writer = response.getWriter();
		writer.write(jsCode);
		writer.flush();
		writer.close();
	}


	/**
	 *方法描述：获取罪犯列表数据 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/listprisoner.json","/list.json"})
	@ResponseBody
	public Object listprisonerJSON(HttpServletRequest request) throws Exception {
		//
		// 获取用户
		SystemUser user = getLoginUser(request);
		
		//
		Map<String,Object> paramMap = parseParamMap(request);
		paramMap = ServiceImplBase.processMapPage(paramMap);
		

		String params = request.getParameter("params");
		Map<String,Object> paramsMap = MapUtil.getDataByAip(params);
		//
		paramMap.putAll(paramsMap);
		//
		String key = getParameterString(request, "key", "");
		key = URLDecoder.decode(key,"UTF-8");
		paramMap.put("key", key);
    	//paramMap.put("flowdefid", "doc_zfrjdjsp"); // 流程ID应该在资源控制?
		//
		List<?> data = new ArrayList<Object>();
		int total = 0;

		try{
			Map<String,Object> resultMap = solutionService.list(paramMap, user);
			//
			Object count = resultMap.get("count");
			Object list = resultMap.get("list");
			//
			if(count instanceof Map<?,?>){
				//
				Map<String, Object> countMap = (Map<String, Object>) count;
				count = MapUtil.getFirstValue(countMap);
			}
			//
			if(StringNumberUtil.isLong(""+count)){
				total = StringNumberUtil.parseInt(count, 0);
			}
			//
			if(list instanceof List<?>){
				List<Map<String, Object>> dataList = (List<Map<String, Object>>)list;
				data = MapUtil.convertKeyList2LowerCase(dataList);
			}
			//
			if(0 == total && !data.isEmpty()){
				total = data.size();
			}
			//
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//
		JSONMessage message = JSONMessage.newMessage();
		message.setSuccess();
		message.setData(data);
		message.setTotal(total);
		return message;
	}
	
	

	private static Map<String, Object> processDateMap(Map<String, Object> mapR, String dateFormatStr) {
		if(null == mapR || mapR.isEmpty() || null == dateFormatStr){
			return mapR;
		}
		//
		SimpleDateFormat format = new SimpleDateFormat(dateFormatStr);
		//
		Set<String> keySet = mapR.keySet();
		List<String> keyList = new ArrayList<String>(keySet);
		//
		Iterator<String> iteratorK = keyList.iterator();
		while (iteratorK.hasNext()) {
			String key = (String) iteratorK.next();
			Object value = mapR.get(key);
			if(value instanceof Date){
				Date date = (Date) value;
				String dateStr = format.format(date);
				//
				mapR.put(key, dateStr); // 将日期换成字符串.
			}
		}
		//
		return mapR;
	}
	
	
}
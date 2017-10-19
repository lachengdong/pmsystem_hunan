package com.sinog2c.mvc.controller.commutationParole.threeTypesOfCrime;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;

/**
 * 三类犯管理
 * 
 * @author hzl
 */
@Controller
@RequestMapping(value = "/threeOfCrimManage")
public class ThreeOfCrimManageController extends ControllerBase {
	
	@Autowired
	private ChooseCriminalService choosecriminalservice;
	
	
	/**
	 * 跳转列表页
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toThreeOfCrimManagePage.page")
	public ModelAndView toTabsPage(HttpServletRequest request) {
		returnResourceMap(request);
		return  new ModelAndView("commutationParole/threeTypesOfCrime/ThreeOfCrimManage");
	}
	
	@RequestMapping(value="/getSanLeiFan")
	@ResponseBody
	public void getSanLeiFan(HttpServletRequest request,HttpServletResponse response){
		SystemUser user = (SystemUser) getSessionAttribute(request, ControllerBase.SESSION_USER_KEY); 
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		if(sortField!=null && !"".equals(sortField) && sortField.equals("originalyearval")) {
			sortField = "to_number(originalyear)";
		}
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		int count=0;
		String key=request.getParameter("key");
		String jailid=request.getParameter("jailid");
		String state=request.getParameter("state");
		String sanleitype=request.getParameter("sanleitype");	
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("pageIndex", pageIndex);
		map.put("pageSize", pageSize);
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		map.put("start", start);
		map.put("end", end);
		map.put("key", key);
		map.put("jailid", jailid);
		map.put("state", state);
		map.put("sanleitype", sanleitype);
		count=choosecriminalservice.getSLFcount(map);
		List<Map> list=new ArrayList<Map>();
		list=choosecriminalservice.getSanLeiFan(map);
		List<Object> data=new ArrayList<Object>();
		for(int i=0;i<list.size();i++){
			Map<String,Object> tempmap=new HashMap<String,Object>();
			tempmap.put("crimid", list.get(i).get("CRIMID"));
			tempmap.put("name", list.get(i).get("NAME"));
			tempmap.put("age", list.get(i).get("AGE"));
			tempmap.put("jailname", list.get(i).get("JAILNAME"));
			tempmap.put("originalyearval", list.get(i).get("ORIGINALYEARVAL"));
			tempmap.put("sentencestime", list.get(i).get("SENTENCESTIME"));
			tempmap.put("sentenceetime", list.get(i).get("SENTENCEETIME"));
			tempmap.put("sanclassstatus", list.get(i).get("SANCLASSSTATUS"));
			tempmap.put("inpersion", list.get(i).get("INPERSION"));
			tempmap.put("causeaction", list.get(i).get("CAUSEACTION"));
			data.add(tempmap);
		}
		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("data", data);
		resultmap.put("total", count);
		PrintWriter writer;
		String result="";
		try {
			result = new JSONObject(resultmap).toString();
			response.setContentType("text/plain;charset=utf-8"); 
			writer = response.getWriter();
			writer.write(result);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/lockCriminal")
	@ResponseBody
	public void lockCriminal(HttpServletRequest request,HttpServletResponse response){
		String crimid=request.getParameter("crimid");
		String state=request.getParameter("state");
		Map<String,Object> map=new HashMap<String,Object>();
		if(null!=crimid && !"".equals(crimid)){
			//map.put("state", state);
			String[] crimidArr = crimid.split(",");
			String crimids = "";
			for(int i=0; i<crimidArr.length; i++) {
				if(i==0) {
					crimids = crimids + "'" + crimidArr[i] + "'";
				} else {
					crimids = crimids + ",'" + crimidArr[i] + "'";
				}
			}
			String sql = "update TBPRISONER_BASE_CRIME set sanclassstatus='" + state + "' where crimid in (" + crimids + ")";
			map.put("sql", sql);
			choosecriminalservice.lockCriminal(map);
		}
	}
	@RequestMapping(value="/getJianYuName")
	@ResponseBody
	public void getJianYuName(HttpServletRequest request,HttpServletResponse response){
		List<Map> list=choosecriminalservice.getJianYuName();
		List<Object> data=new ArrayList<Object>();
		for(int i=0;i<list.size();i++){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("jailid", list.get(i).get("JAILID"));
			map.put("jailname", list.get(i).get("JAILNAME"));
			data.add(map);
		}
		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("data", data);
		PrintWriter writer;
		String result="";
		try {
			result = new JSONObject(resultmap).toString();
			response.setContentType("text/plain;charset=utf-8"); 
			writer = response.getWriter();
			writer.write(result);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package com.sinog2c.mvc.controller.commutationParole;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.system.TbsysDocumentMapper;
import com.sinog2c.model.flow.Flow;
import com.sinog2c.model.flow.UvFlow;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.StringNumberUtil;
/**
 * 案件检查
 * 
 * @author hzl
 * 
 */
@Controller
public class CaseCheckController extends ControllerBase {
	@Autowired
	private UvFlowService uvFlowService;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private FlowService flowService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private TbsysDocumentMapper tbsysdocumentmapper;
	
	/**
	 * 跳转列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toCaseCheckPage")
	public ModelAndView toTabsPage(HttpServletRequest request) {
		ModelAndView mav = null;
		returnResourceMap(request);
		
		//menuid有时候传不过来值
		String menuid = request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/caseCheckListPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 检查监督填写意见
	 * 
	 * @author xuebo 2014-9-2 15:36:20
	 */
	@RequestMapping(value = "/toOpinionPage")
	public ModelAndView toOpinionPage(HttpServletRequest request) {
		String id = request.getParameter("id");
		String[] ids = id.split("@");
		Flow flow = new Flow();
//		flow.setFlowdraftid(Integer.valueOf(ids[2]));
		flow.setFlowdraftid(ids[2]);
		Flow opinionflow = flowService.getOpinion(flow);
		request.setAttribute("flow", opinionflow);
		request.setAttribute("id", id);
		ModelAndView mav = null;
		returnResourceMap(request);
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/supervisionViews.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 检查监督获取列表数据
	 * 
	 * @author xuebo 2014-9-2 15:36:20
	 */
	@RequestMapping(value = "/getCaseCheckList.action")
	@ResponseBody
	public Object getCaseCheckList(HttpServletRequest request) {
		Map<String, Object> resultmap = new HashMap<String,Object>();
		SystemUser user = getLoginUser(request);
		List<Map> data = new ArrayList<Map>();
		String key = request.getParameter("key");
		String nodeid = request.getParameter("nodeid");
		String approvestype = request.getParameter("approvestype");
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		Map<String,Object> map = new HashMap<String,Object>();
    	map.put("sortField", sortField);
    	map.put("sortOrder", sortOrder);
    	map.put("start", String.valueOf(start));
    	map.put("end",String.valueOf(end));
		map.put("departid", user.getDepartid());
		map.put("key", key);
		map.put("nodeid", nodeid);
		map.put("approvestype", approvestype);
		map.put("orgid", user.getOrgid());
		int count = uvFlowService.getCountCaseCheckList(map);
		data = uvFlowService.getCaseCheckList(map);
		
		resultmap.put("data", data);
		resultmap.put("total", count);
		return resultmap;
	}
	
	@RequestMapping(value = "/toCaseCheckTab")
	public ModelAndView toCaseCheckTab(HttpServletRequest request){
		String id = request.getParameter("id");
		String resid = request.getParameter("menuid");
		String tempid = request.getParameter("tempid");
		if(id!=null && id!="") {
			String[] idArr = id.split("@");
			request.setAttribute("crimid", idArr[0]);
			request.setAttribute("flowdraftid", idArr[2]);
			request.setAttribute("flowid", idArr[3]);
		}
		request.setAttribute("id", id);
		request.setAttribute("resid", resid);
		request.setAttribute("tempid", tempid);
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/commutationParole/caseCheckTab.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	/**
	 * 检查监督查看案件页面
	 * 
	 * @author xuebo 2014-9-2 15:36:20
	 */
	@RequestMapping(value = "/caseCheckPage")
	public ModelAndView commuteOfJianBanRenJailCase(HttpServletRequest request){
		JSONArray docconent = new JSONArray();
		Map<String,Object> map = new HashMap<String,Object>();
		String crimid = "";
		String orgid = "";
		String flowdraftid = "";
		String flowid = "";
		String lastnodeid = "";
		String fathermenuid = request.getParameter("fathermenuid");
		String tempid = request.getParameter("tempid");
		
		String id = request.getParameter("id");
		if(StringNumberUtil.notEmpty(id)){
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			String temps = ids[0];
			String[] temArr = temps.split("@");
			crimid = temArr[0];
			orgid = temArr[1];
			flowdraftid = temArr[2];
			flowid = temArr[3];
			if(!StringNumberUtil.notEmpty(flowid)||"flowidnull".equals(flowid.trim())){
				flowid = "";
			}
			lastnodeid = temArr[4];
			Flow flow = new Flow();
//			flow.setFlowdraftid(Integer.valueOf(temArr[2]));
			flow.setFlowdraftid(temArr[2]);
			Flow opinionflow = flowService.getOpinion(flow);
			if(opinionflow.getInt2()==null) {
				flow.setInt2(0);
				flowService.updateByFlowdraftid(flow);
			}
		}
		
		request.setAttribute("fathermenuid", fathermenuid);
		request.setAttribute("applyid", crimid);
		request.setAttribute("orgid", orgid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("lastnodeid", lastnodeid);
		
		Map paramMap = new HashMap();
		String content = "";
		paramMap.put("tempid", tempid);
		paramMap.put("flowdraftid", flowdraftid);
		content =  flowBaseOtherService.getDocconentByFlowdraftid(paramMap);
		
		if(StringNumberUtil.notEmpty(content)){
			docconent.add(content);
			Map paramMap2 = new HashMap();
			paramMap2.put("flowdraftid", flowdraftid);
		}
		
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/aip/caseCheck.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 检查监督保存意见
	 * 
	 * @author xuebo 2014-9-2 15:36:20
	 */
	@RequestMapping(value = "/saveOpinion")
	@ResponseBody
	public String saveOpinion(HttpServletRequest request) {
		String opion = request.getParameter("opion");
		String id = request.getParameter("id");
		String[] ids = id.split("@");
		Flow flow = new Flow();
//		flow.setFlowdraftid(Integer.valueOf(ids[2]));
		flow.setFlowdraftid(ids[2]);
		flow.setText3(opion);
		flow.setInt2(1);
		int success = flowService.updateByFlowdraftid(flow);
		if(success==1) {
			return "success";
		}
		return "error";
	}
	
	@RequestMapping(value = { "/viewCaseCheck"})
	public Object viewCaseCheck(HttpServletRequest request, HttpServletResponse response) {
		View view = new InternalResourceView("WEB-INF/JSP/aip/viewCaseCheck.jsp");
		ModelAndView mav = new ModelAndView(view);
		JSONArray jsonarray = new JSONArray();
		//String flowdraftid = request.getParameter("flowdraftid");
		//String flowid = request.getParameter("flowid");
		String crimid=request.getParameter("crimid");
		String tempid=request.getParameter("tempid");
		Map paramMap = new HashMap();
		paramMap.put("tempid", tempid);;
		paramMap.put("crimid", crimid);
		//content =  flowBaseOtherService.getDocconentByFlowdraftid(paramMap);
		String docment=tbsysdocumentmapper.findreportDataByDocid(paramMap);
		jsonarray.add(docment);
		request.setAttribute("formcontent",jsonarray.toString());
		if(docment==null || docment=="") {
			request.setAttribute("notexist", GkzxCommon.NOTEXIST);
		}
		return mav;
	}
	
	/*
	 * 获取检查监督数据
	 * 
	 */
	@RequestMapping(value={"/getJianChaData"})
	@ResponseBody
	public Object getJianChaData(HttpServletRequest request,HttpServletResponse response){
		
		String type=request.getParameter("type");
		String crimid =request.getParameter("crimid");
		String orgid=request.getParameter("orgid");
		String key = request.getParameter("key")==null?"":request.getParameter("key");
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));       
		SystemUser su = getLoginUser(request);
		String departid=su.getDepartid();
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		
		Map<String,Object> map=new HashMap<String,Object>();
		String temptype="";
		JSONMessage message = JSONMessage.newMessage();
		Map<String, Object> resultmap = new HashMap<String, Object>();
		if(null!=type&&!"".equals(type)){
			if(type.equals("bwjd")){
				//temptype="doc_bwcbhysp";
				temptype="other_jybwjycbsp";
			}else if(type.equals("jxjd")){
				temptype="other_zfjyjxjssp";
			}else if(type.equals("sjbajd")){
				temptype="other_sjjxjssp";
			}else if(type.equals("sjjd")){
				
			}
		map.put("flowdefid", temptype);
		map.put("applyid", crimid);
		map.put("start", start);
		map.put("end", end);
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		map.put("key", key);
		map.put("orgid", orgid);
		map.put("departid", departid);
		int total=flowBaseOtherService.getJianChaDatas(map);
		List<Map> mapdata=flowBaseOtherService.getJianChaData(map);
		ArrayList<Object> data= new ArrayList<Object>();
		for(int i=0;i<mapdata.size();i++){
			Map m=new HashMap();
			Map mas=new HashMap();
			mas=mapdata.get(i);
			m.put("name", (String)mas.get("NAME"));
			m.put("applyid", (String)mas.get("APPLYID"));
			m.put("applyname", (String)mas.get("APPLYNAME"));
			data.add(m);
		}
		message.setTotal(total);
		message.setData(data);
	}
		return message;
	}
	
	/*
	 * 查看监督意见
	 */
	@RequestMapping("/toViewJianChaYijian")
	public Object toViewJianChaYijian(HttpServletRequest request){
		String crimid=request.getParameter("crimid");
		String type=request.getParameter("type");
		String menuid=request.getParameter("menuid");
		String tempid="";
		if(null!=type&&!"".equals(type)){
			if(type.equals("bwjd")){
				tempid="SZ_JXJS_JXJD_bwjd";
			}else if(type.equals("jxjd")){
				tempid="SZ_JXJS_JXJD_BAYJ";
			}else if(type.equals("sjbajd")){
				tempid="other_sjjxjssp";
			}else if(type.equals("sjjd")){
				tempid="SZ_JXJS_JXJD_sjjd";
			}
		}
		JSONArray docconent = new JSONArray();
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid,crimid, null);
		if(null!=document){
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid", tempid);
		docconent.add(document.getContent());
		request.setAttribute("formcontent", docconent.toString());
		}
		return new ModelAndView("outexecute/viewjianchayijian");
		}
	@RequestMapping("/getJianYuName")
	@ResponseBody
	//获取XX省所有监狱，departid怎么取？
	public void getJianYuName(HttpServletRequest request,HttpServletResponse response){
		SystemUser user = getLoginUser(request);
		String depid=user.getDepartid();
		
		String departid=request.getParameter("departid");
		List<Map> map=flowBaseOtherService.getJianYuName(depid);
		List<Object> list=new ArrayList<Object>();
		for(int i=0;i<map.size();i++){
			Map<String,Object> maps=new HashMap<String,Object>();
			maps.put("name", map.get(i).get("NAME"));
			maps.put("orgid", map.get(i).get("ORGID"));
			list.add(maps);
		}
		Map<String, Object> resultmap = new HashMap<String, Object>();
		resultmap.put("data", list);
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

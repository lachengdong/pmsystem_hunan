package com.sinog2c.mvc.controller.commutationParole;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.flow.FlowArchivesMapper;
import com.sinog2c.model.flow.FlowArchives;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowArchivesService;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * 减刑假释 案件查看
 * 
 * @author hzl
 * 
 */
@Controller
public class CaseLookController extends ControllerBase {
	@Autowired
	private FlowArchivesMapper flowarchivesmapper;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private FlowArchivesService flowarchivesservice;
	/**
	 * 跳转案件查看列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/tocaseLookPage")
	public ModelAndView toCaseLookPage(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/commutationParole/caseLookListPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	
	/**
	 * 跳转案件查看列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 * mushuhong 用，请勿动
	 */
	@RequestMapping(value = "/tocaseLookPage_sx")
	public ModelAndView toCaseLookPage_sx(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/caseLookListPage_sx.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	/**
	 * 获取减刑数据
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/getCaseLookList.json")
	@ResponseBody
	public Object getCaseLookList(HttpServletRequest request) throws Exception {
		
		SystemUser user = this.getLoginUser(request);
		Map<String, Object> resultmap = new HashMap<String,Object>();
		
		Map<String,Object> map = this.parseParamMap(request);
		map = this.parsePageInfoOfMap(map);
		
		map.put("orgid", user.getOrgid());
		map.put("departid", user.getDepartid());
		
		String anhao = StringNumberUtil.getStrFromMap("anhao", map);
		if("false".equals(anhao)){
    		anhao ="";
    	}
    	map.put("anhao",anhao);
		
    	//案件号 年份
		String caseyear = StringNumberUtil.getStrFromMap("caseyear", map);
		String casenums = StringNumberUtil.getStrFromMap("casenums", map);
    	if(StringNumberUtil.notEmpty(casenums) && StringNumberUtil.notEmpty(caseyear)){
    		casenums = StringNumberUtil.formatCaseNo(casenums,caseyear);
	    	map.put("casenums", casenums);
    	}
    	
		//查询出 所有的 
    	int count = tbxfSentencealterationService.selectTbxfs2Count(map);
    	List<Map<String,Object>> data = tbxfSentencealterationService.selectTbxfs2(map);
    	resultmap.put("data", data);
		resultmap.put("total", count);
		
		return resultmap;
	}

	
	/**
	 * 获取重要罪犯减刑数据
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/imGetCaseLookList.json")
	@ResponseBody
	public Object imGetCaseLookList(HttpServletRequest request) throws Exception {
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = null;	
		
		try {
			//案件号 年份
			String caseyear = request.getParameter("caseyear")==null?"":request.getParameter("caseyear");
			String anhao = request.getParameter("anhao")==null?"":request.getParameter("anhao");
			//案件号 年份
			String jianqu = request.getParameter("jianqu")==null?"":request.getParameter("jianqu");
			//案件类型
			String casetype = request.getParameter("casetype")==null?"":request.getParameter("casetype");
			casetype = java.net.URLDecoder.decode(casetype, "utf-8");
			//标识 案件整理页面 
			String approval = request.getParameter("approval");
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			//按照 下拉列表 进行 过滤的问题
			String nodeid = request.getParameter("nodeid")==null?"":request.getParameter("nodeid");
			
			String im = request.getParameter("im");//重要罪犯标示
			
			String nodeid1 = "";
			String nodeid2 = "";
			String state1 = "";
			String state2 = "";
			String keynodeid = "";
			if(!"".equals(nodeid)){
				String[] nodeArr = nodeid.split("_");
				if (nodeArr.length >0) {
					nodeid1 = nodeArr[0];
					state1 = nodeArr[1];
					nodeid2  = nodeArr[2];
					state2  = nodeArr[3];
				}
			}
			//分页 参数
			int index = Integer.parseInt(request.getParameter("pageIndex"));
			int size = Integer.parseInt(request.getParameter("pageSize"));
			int start = index * size + 1;
			int end = start + size -1;
			
			String orgid = getLoginUser(request).getOrgid();
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			if ("anjianhao".equals(sortField)) {//默认是正序的排列 
				sortField = "to_number(text6)";
			}
			
			String casenums = request.getParameter("casenums")==null?"":request.getParameter("casenums");
		   
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("orgid", orgid);
			map.put("nodeid1", nodeid1);
			map.put("nodeid2", nodeid2);
			map.put("state1", state1);
			map.put("state2", state2);
			map.put("nodeid", keynodeid);
			map.put("searchnodeid", nodeid);
			map.put("flowdefid", "other_zfjyjxjssp");
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("caseyear", caseyear);
	    	map.put("approval", approval);
	    	map.put("jianqu", jianqu); 
	    	map.put("casetype", casetype);
	    	map.put("im", im);
	    	if("false".equals(anhao)){
	    		anhao ="";
	    	}
	    	map.put("anhao",anhao);
	    	
	    	if(StringNumberUtil.notEmpty(casenums)&&StringNumberUtil.notEmpty(caseyear)){
	    		casenums = StringNumberUtil.formatCaseNo(casenums,caseyear);
		    	map.put("casenums", casenums);
	    	}
			//查询出 所有的 
	    	int count = tbxfSentencealterationService.imselectTbxfs2Count(map);
	    	data= tbxfSentencealterationService.imselectTbxfs2(map);
	    	resultmap.put("data", data);
	    	//查询出所有的 罪犯
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultmap;
	}
	
	/**
	 * 获取减刑重号数据列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getCaseLookList1.json")
	@ResponseBody
	public Object getCaseLookList1(HttpServletRequest request) throws Exception {
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = null;	
		
		try {
			//案件号 年份        year:caseyear,nums:casenums,jian:jianqu,k:key
			String caseyear = request.getParameter("year")==null?"":request.getParameter("year");
			//标识 案件整理页面 
			String approval = request.getParameter("approval");
			String key = request.getParameter("k")==null?"":request.getParameter("k");
			
			String jianqu = request.getParameter("jian")==null?"":request.getParameter("jian");
			
			String casenums = request.getParameter("nums")==null?"":request.getParameter("nums");
			
			String casetype = request.getParameter("casetype")==null?"":request.getParameter("casetype");
			//按照 下拉列表 进行 过滤的问题
			String nodeid = request.getParameter("nodeid")==null?"":request.getParameter("nodeid");
			
			
			String nodeid1 = "";
			String nodeid2 = "";
			String state1 = "";
			String state2 = "";
			String keynodeid = "";
			if(!"".equals(nodeid)){
				String[] nodeArr = nodeid.split("_");
				if (nodeArr.length >0) {
					nodeid1 = nodeArr[0];
					state1 = nodeArr[1];
					nodeid2  = nodeArr[2];
					state2  = nodeArr[3];
				}
			}
		/*	//分页 参数
			int index = Integer.parseInt(request.getParameter("pageIndex"));
			int size = Integer.parseInt(request.getParameter("pageSize"));
			int start = index * size + 1;
			int end = start + size -1;
		  */  	
				//分页 参数
		//	int index = 1;
		//	int size =50;
			int start =1;
			int end = 10;
		  	
			String orgid = getLoginUser(request).getOrgid();
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			if ("anjianhao".equals(sortField)) {//默认是正序的排列 
				sortField = "to_number(text6)";
			}
		//	String casenums = request.getParameter("casenums");
			Map<String,Object> map = new HashMap<String,Object>();

			map.put("nodeid1", nodeid1);
			map.put("nodeid2", nodeid2);
			map.put("state1", state1);
			map.put("state2", state2);
			map.put("nodeid", keynodeid);
			map.put("searchnodeid", nodeid);
			map.put("flowdefid", "other_zfjyjxjssp");
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	     	map.put("end",String.valueOf(end));
			map.put("key", key);
			map.put("orgid", orgid);
	    	map.put("caseyear", caseyear);
	    	map.put("approval", approval);
	    	map.put("jianqu", jianqu); 
	    	map.put("casetype", casetype);
	    	
	    	if(StringNumberUtil.notEmpty(casenums)&&StringNumberUtil.notEmpty(caseyear)){
	    		casenums = StringNumberUtil.formatCaseNo(casenums,caseyear);
		    	map.put("casenums", casenums);
	    	}
			//查询出 所有的 
	    	int count = tbxfSentencealterationService.selectTbxfs3Count(map);
	    	data= tbxfSentencealterationService.selectTbxfs3(map);
	    	resultmap.put("data", data);
	    	//查询出所有的 罪犯
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultmap;
	}
	
	
	
	
//	@RequestMapping("/getCaseLookList_sx.json")
//	@ResponseBody
//	public Object getCaseLookList_sx(HttpServletRequest request) throws Exception {
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>> ();		
//		List<Map<String,Object>> data1 = new ArrayList<Map<String,Object>> ();
//		
//		try {
//			//案件号 年份
//			String caseyear = request.getParameter("caseyear")==null?"":request.getParameter("caseyear");
//			//标识 案件整理页面 
//			String approval = request.getParameter("approval");
//			
//			String key1 = request.getParameter("key")==null?"":request.getParameter("key");
//			String key= java.net.URLDecoder.decode(key1, "utf-8");
//			//按照 下拉列表 进行 过滤的问题
//			String nodeid = request.getParameter("nodeid")==null?"":request.getParameter("nodeid");
//			String nodename = request.getParameter("nodename") == null?"":request.getParameter("nodename");
//			nodename = java.net.URLDecoder.decode(nodename, "utf-8");
//			String searcenodeid = nodeid;
//			String nodeid1 = "";
//			String nodeid2 = "";
//			String state1 = "";
//			String state2 = "";
//			String keynodeid = "";
//			if(!"".equals(nodeid)){
//				String[] nodeArr = nodeid.split("_");
//				if (nodeArr.length >0) {
//					nodeid1 = nodeArr[0];
//					state1 = nodeArr[1];
//					nodeid2  = nodeArr[2];
//					state2  = nodeArr[3];
//				}
//			}
//			//分页 参数
//			String pageIndex = request.getParameter("pageIndex");
//			String pageSize = request.getParameter("pageSize");
//			int index = Integer.parseInt(pageIndex);
//			int size = Integer.parseInt(pageSize);
//			
//			String orgid = getLoginUser(request).getOrgid();
//			String departid = getLoginUser(request).getDepartid();
//			String sortField = request.getParameter("sortField");
//			String casenums = request.getParameter("casenums");
//			//默认是正序的排列 
//			sortField = "to_number(substr(text6,5))";
//			//排序方式 
//			String sortOrder = request.getParameter("sortOrder");
//			if (sortOrder == null || "".equals(sortOrder)) {
//				sortOrder = "asc";
//			}
//			int start = index * size + 1;
//			int end = start + size -1;
//			
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("key", key);
//			map.put("orgid", orgid);
//			map.put("nodeid1", nodeid1);
//			map.put("nodeid2", nodeid2);
//			map.put("state1", state1);
//			map.put("state2", state2);
//			map.put("nodeid", keynodeid);
//			map.put("nodeid", nodeid);
//			map.put("flowdefid", "other_zfjyjxjssp");
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//	    	map.put("caseyear", caseyear);
//	    	map.put("approval", approval);
//	    	map.put("departid", departid);
//	    	map.put("nodename", nodename);
//	    	if(StringNumberUtil.notEmpty(casenums)&&StringNumberUtil.notEmpty(caseyear)){
//	    		casenums = StringNumberUtil.formatCaseNo(casenums,caseyear);
//		    	map.put("casenums", casenums);
//	    	}
//			//查询出 所有的 
//	    	int count = tbxfSentencealterationService.selectTbxfs2Count_sx(map);
//	    	data= tbxfSentencealterationService.selectTbxfs2_sx(map);
//	    	for(Map<String,Object> map3:data){
//	    		Map<String,Object> map2 = new HashMap<String,Object>();
//	    		map2.put("courtyear", map3.get("TEXT6").toString().substring(0, 4));
//	    		map2.put("applyid", map3.get("APPLYID"));
//	    		map2.put("applyname", map3.get("APPLYNAME"));
//	    		map2.put("areaname", systemOrganizationService.getByOrganizationId(map3.get("ORGID").toString()).getShortname());
//	    		map2.put("jincheng", map3.get("TEXT8"));
//	    		map2.put("casestate", map3.get("CASESTATE"));
//	    		map2.put("nodeid", map3.get("NODEID"));
//	    		map2.put("casenumber", map3.get("CASENUMBER"));
//	    		map2.put("flowdraftid", map3.get("FLOWDRAFTID"));
//	    		map2.put("flowid", map3.get("FLOWID"));
//	    		map2.put("flowdefid", map3.get("FLOWDEFID"));
//	    		map2.put("anjianhao", map3.get("ANJIANHAO"));
//	    		data1.add(map2);
//	    	}
//	    	resultmap.put("data", data1);
//	    	//查询出所有的 罪犯
//			resultmap.put("total", count);
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return resultmap;
//	}
	/**
	 * 处理省局决定书和监狱审批表签章（陕西）
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/toSJcasepage")
	public ModelAndView toSJcasepage(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		String flowid = request.getParameter("flowid");
		String flowdraftid = request.getParameter("flowdraftid");
		String flowdefid = request.getParameter("flowdefid");
		String menuid = request.getParameter("menuid");
		String docid = request.getParameter("docid");//审批表档案id
		String jysdocid = request.getParameter("jysdocid");//决定书
		String tempid = request.getParameter("tempid");//审批表表单id
		String jdstempid = request.getParameter("jdstempid");//建议书表单id
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("docid", docid);
		request.setAttribute("jysdocid", jysdocid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("jdstempid", jdstempid);
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/spbandjds_sx.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	/**
	 * 案件查看
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tocaseLookTabPage")
	public ModelAndView tocaseLookTabPage(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		String flowid = request.getParameter("flowid");
		String flowdraftid = request.getParameter("flowdraftid");
		String flowdefid = request.getParameter("flowdefid");
		String menuid = request.getParameter("menuid");
		String docid = request.getParameter("docid");//审批表档案id
		String cpbid = request.getParameter("cpbid");//呈批表档案id
		String jysdocid = request.getParameter("jysdocid");//建议书档案id
		String tempid = request.getParameter("tempid");//审批表表单id
		String jxstempid = request.getParameter("jxstempid");//建议书表单id
		String cpbtempid = request.getParameter("cpbtempid");//建议书表单id
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("docid", docid);
		request.setAttribute("cpbid", cpbid);
		request.setAttribute("jysdocid", jysdocid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("jxstempid", jxstempid);
		request.setAttribute("cpbtempid", cpbtempid);
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		request.setAttribute("provincecode", provincecode);
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/caseLookTabPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	/*
	 * 案件查看获取大字段，优先从档案获取
	 * 档案表没有数据从流程表获取
	 */
	@RequestMapping(value="/toCaseLook")
	public ModelAndView toCaseLook(HttpServletRequest request,HttpServletResponse response) {
		View view = null;
		String crimid = request.getParameter("crimid");
		String menuid = request.getParameter("menuid");
		String flowdefid = request.getParameter("flowdefid");
		String flowdraftid = request.getParameter("flowdraftid");
		String tempid = request.getParameter("tempid");
		String docid=request.getParameter("docid");
		String cpbid=request.getParameter("cpbid");
		request.setAttribute("menuid", menuid);
		Map<String, String> map = new HashMap<String, String>();
		map.put("crimid", crimid);
		map.put("flowdefid", flowdefid);
		map.put("tempid", tempid);
		map.put("docid", docid);
		map.put("cpbid", cpbid);
		String aipBigdata=flowarchivesmapper.findArchivesById(map);//取案件归档后电子档案表大字段值
		if(!StringNumberUtil.isNullOrEmpty(aipBigdata)||"123".equals(docid)){
			request.setAttribute("aipdata", aipBigdata);
			view = new InternalResourceView("WEB-INF/JSP/report/chaKanSuggestReport.jsp");//报表
		}else{
			JSONArray docconent = new JSONArray();
			map.put("flowdraftid", flowdraftid);
			map.put("tempid", tempid);
			String content =  flowBaseOtherService.getDocconentByConditionTwo(map);//取流程表大字段值	
			if(content!=null && !"".equals(content)) {
				docconent.add(content);
			}else{
				request.setAttribute("nocontent", GkzxCommon.NOTEXIST);
			}
			request.setAttribute("formcontent", docconent.toString());
			view = new InternalResourceView("WEB-INF/JSP/aip/showCaseLook.jsp");//报表
		}
		ModelAndView mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 省局案件查看，监狱归档后的建议书；
	 * @param request
	 * @return
	 */
	@RequestMapping("/chakansuggestPage")
	public ModelAndView suggestPage(HttpServletRequest request){
		String content="";
		JSONArray docconent = new JSONArray();
		String crimid=request.getParameter("crimid");
		String docid=request.getParameter("docid");
		Map paramMap=new HashMap();
		paramMap.put("crimid", crimid);
		paramMap.put("docid", docid);
		FlowArchives flowarchives=flowarchivesservice.getArchivesData(paramMap);
		if(StringNumberUtil.notEmpty(flowarchives)){
			content=flowarchives.getDocconent();
			docconent.add(content);
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/aip/showCaseLook.jsp"));
	}
	
	/**
	 * 跳转省局案件查看列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toSJcaseLookPage")
	public ModelAndView toSJcaseLookPage() {
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/SJcaseLook.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	/**
	 * 省局获取立案数据
	 * @author zgl
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSJCaseLookList.json")
	@ResponseBody
	public Object getSJCaseLookList(HttpServletRequest request) throws Exception {

		String flowdefid = "other_sjjxjssp";
		
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map> data = new ArrayList<Map> ();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String key = request.getParameter("key")==null?"": request.getParameter("key");
			if(null!=key && !"".equals(key)){
				key = java.net.URLDecoder.decode(request.getParameter("key") , "utf-8");
			}
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? 0:Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? 0:Integer.parseInt(request.getParameter("pageSize")));        
			String orgid = getLoginUser(request).getDepartid();
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			String lianstarttime   = request.getParameter("lianstarttime");
			String lianendtime     = request.getParameter("lianendtime");
			String caidingstarttime= request.getParameter("caidingstarttime");
			String caidingendtime  = request.getParameter("caidingendtime");
			String birthstarttime  = request.getParameter("birthstarttime");
			String birthendtime    = request.getParameter("birthendtime");
			String anjiantype	   = request.getParameter("anjiantype");
			String punishmenttype  = request.getParameter("punishmenttype");
			String caseid          = request.getParameter("caseid");
			String depid           = request.getParameter("depid");
			
			if("0_1".equals(caseid)){
				caseid = "0";
			}else if(("N0002_1").equals(caseid)){
				caseid = "N0002";
			}else if(("N0003_1").equals(caseid)){
				caseid = "N0003";
			}else if(("-1_1").equals(caseid)){
				caseid = "-1";
			}else if(("1_1").equals(caseid)){
				caseid = "1";
			}else if(null!=caseid&&!"".equals(caseid)){
				flowdefid = "other_zfjyjxjssp";
			}else{
				flowdefid = null;
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("orgid", orgid);
			map.put("caseid", caseid);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("flowdefid", flowdefid);
	    	map.put("anjiantype",anjiantype);
	    	map.put("punishmenttype",punishmenttype);
	    	
	    	if(!"".equals(depid) && null!=depid ){
	    		map.put("depid", depid);
	    	}
	    	if(!"".equals(lianstarttime) && null!=lianstarttime ){
	    		map.put("lianstarttime",sdf.parse(lianstarttime));                                                                                             
	    	}
	    	if(!"".equals(lianendtime) && null!=lianendtime ){
	    		map.put("lianendtime",sdf.parse(lianendtime));
	    	}
	    	if(!"".equals(caidingstarttime) && null!=caidingstarttime ){
	    		map.put("caidingstarttime",sdf.parse(caidingstarttime));                                                                                               
	    	}
	    	if(!"".equals(caidingendtime) && null!=caidingendtime ){
	    		map.put("caidingendtime",sdf.parse(caidingendtime));
	    	}
	    	if(!"".equals(birthstarttime) && null!=birthstarttime ){
	    		map.put("birthstarttime",sdf.parse(birthstarttime));                                                                                                
	    	}
	    	if(!"".equals(birthendtime) && null!=birthendtime ){
	    		map.put("birthendtime",sdf.parse(birthendtime));
	    	}
			
	    	data = tbxfSentencealterationService.getSJCaseList(map);
	    	int count = tbxfSentencealterationService.getSJCaseCount(map);
	    	
	    	List<Map> returnData = new ArrayList<Map> ();//页面显示list
	    	for(Map map3:data){
	    		Map map2 = new HashMap();
	    		String text = (String)map3.get("text6");
	    		String departid = (String)map3.get("departid");
	    		SystemOrganization organization = systemOrganizationService.getByOrganizationId(departid);
	    		String shortName = organization.getShortname();
	    		if(text!=null){
	    			String nianhao = text.substring(0,4);
	    			String anjianhao = text.substring(4);
	    			map2.put("nianhao", nianhao);
	    			map2.put("anjianhao", "("+nianhao+")"+shortName+"减字"+anjianhao+"号");
	    		}else{
	    			map2.put("nianhao", map3.get("nianhao"));
	    			map2.put("anjianhao",map3.get("anjianhao"));
	    		}
	    		map2.put("crimid", map3.get("crimid"));
	    		map2.put("name", map3.get("name"));
	    		map2.put("jailname", map3.get("jailname"));
	    		map2.put("jincheng", map3.get("jincheng"));
	    		map2.put("nodeid", map3.get("nodeid"));
	    		map2.put("flowid", map3.get("flowid"));
	    		map2.put("flowdefid", map3.get("flowdefid"));
	    		map2.put("flowdraftid", map3.get("flowdraftid"));
	    		returnData.add(map2);
	    	}
	    	
	    	resultmap.put("data", returnData);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			//e.printStackTrace();
		}
		
		return resultmap;
	}
	/**
	 * 省局案件查看  单位过滤选择
	 * @author zgl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDepartId.action")
	@ResponseBody
	public Object getDepartId(HttpServletRequest request) throws Exception {
		String orgid = getLoginUser(request).getDepartid();
		List<SystemOrganization> list = systemOrganizationService.getByPorgId(orgid);
		return list;
	}
	/**
	 * 跳转法院案件查看列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toCourtcaseLookPage")
	public ModelAndView toCourtListPage(HttpServletRequest request) {
		returnResourceMap(request);
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/courtCaseLook.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 方法描述：河北省局保外案件查看
	 * @author:mushuhong
	 * @version：2015年2月5日16:44:05
	 */
	@RequestMapping(value="/judgeBwCaseExistSave")
	@ResponseBody
	public int judgeBwCaseExistSave(HttpServletRequest request){
		int count = tbxfSentencealterationService.judgeBwCaseExistSave(request,getLoginUser(request));
		return count;
	}
}

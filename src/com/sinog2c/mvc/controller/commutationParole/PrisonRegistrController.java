package com.sinog2c.mvc.controller.commutationParole;

import java.util.ArrayList;
import java.util.Date;
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

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.commutationParole.CommuteParoleBatchService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.report.RMEngine;
/**
 * 监区立案
 * 
 * @author hzl
 * 
 */
@Controller
public class PrisonRegistrController extends ControllerBase {
	
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private CommuteParoleBatchService commuteParoleBatchService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	/**
	 * 跳转列表页
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toPrisonRegistrPage.action")
	public ModelAndView toTabsPage(HttpServletRequest request) {
		ModelAndView mav = null;
		SystemUser su = getLoginUser(request);
		String orgid = su.getOrgid();
		String departid = su.getDepartid();
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		String menuid = request.getParameter("menuid");
		Map<String, Object> departMap = new HashMap<String, Object>();
		departMap.put("departid", departid);
		Map temMap = commuteParoleBatchService.getCommuteParoleBatchInfo(departMap);
		String curyear;
		String batch="";
		if(temMap !=null&&temMap.size()>0){
			curyear = temMap.get("curyear").toString();
			batch = temMap.get("batch").toString();
		}else{
			Date date = new Date();
			curyear = DateUtil.dateFormat(date, "yyyy");
		}
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		request.setAttribute("provincecode", provincecode);
		Map<String, Object> temMap2 = new HashMap<String, Object>();
		temMap2.put("departid", departid);
		temMap2.put("flowdefid", flowdefid);
		temMap2.put("curyear", curyear);
		temMap2.put("casetype", "减（有）");//湖南的减刑案件号查询
		String casenumber = "";
		
//		if(StringNumberUtil.notEmpty(provincecode)&&"4400".equals(provincecode.trim())){//广东的单独处理：只有刑执字
//			casenumber = flowBaseService.getMaxCaseNum(temMap2);//案件号
//		}else{
//		}
		casenumber = flowBaseService.getMaxCaseNum4HuNanJailCommute(temMap2);//案件号
		
		
		SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
		String shortname = so.getShortname();
		request.setAttribute("casenumber", casenumber);
		request.setAttribute("curyear", curyear);
		request.setAttribute("batch", batch);
		request.setAttribute("shortname", shortname);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("menuid", menuid);
		
		View view = new InternalResourceView("WEB-INF/JSP/commutationParole/prisonRegistrListPage.jsp");
//		if(StringNumberUtil.notEmpty(provincecode)&&"4400".equals(provincecode.trim())){
//			view = new InternalResourceView("WEB-INF/JSP/commutationParole/prisonRegistrListPageForGD.jsp");
//		}
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 跳转列表页
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toPrisonRegistrPageForSD")
	public ModelAndView toPrisonRegistrPageForSD(HttpServletRequest request) {
		ModelAndView mav = null;
		SystemUser su = getLoginUser(request);
		String orgid = su.getOrgid();
		String departid = su.getDepartid();
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		Map<String, Object> departMap = new HashMap<String, Object>();
		departMap.put("departid", departid);
		Map temMap = commuteParoleBatchService.getCommuteParoleBatchInfo(departMap);
		String curyear;
		String batch="";
		if(temMap !=null&&temMap.size()>0){
			curyear = temMap.get("curyear").toString();
			batch = temMap.get("batch").toString();
		}else{
			Date date = new Date();
			curyear = DateUtil.dateFormat(date, "yyyy");
		}
		
		Map<String, String> temMap2 = new HashMap<String, String>();
		temMap2.put("departid", departid);
		temMap2.put("flowdefid", flowdefid);
		temMap2.put("curyear", curyear);
		temMap2.put("commutetype", "0");//监区立案页面案件类型默认减刑，获取案件号也应是减刑
		String casenumber = flowBaseService.getMaxCaseNum(temMap2);//案件号
		
		SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
		String shortname = so.getShortname();
		request.setAttribute("casenumber", casenumber);
		request.setAttribute("curyear", curyear);
		request.setAttribute("batch", batch);
		request.setAttribute("shortname", shortname);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/prisonRegistrListPageForSD.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value = "/getLiAaInfoList")
	@ResponseBody
	public Object getLiAaInfoList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List data = new ArrayList();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String sel=request.getParameter("sel")==null?"1":request.getParameter("sel");
			String anjiantype = request.getParameter("anjiantype");
			SystemUser su = getLoginUser(request);
			String orgid = su.getOrgid();
			String departid = su.getDepartid();
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1; 
			Map<String,Object> map = new HashMap<String,Object>();
			String flowdefid = request.getParameter("flowdefid");
			//上海特有
			Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
			if(GkzxCommon.SHANGHAI_PROVINCE.equals(provincecode)){
				map.put("isstatus", 5);
			}
			map.put("sel", sel);
			map.put("key", key);
			map.put("state", -1);
			map.put("nodeid", "3");//案件不同意的nodeid为3
			map.put("anjiantype", anjiantype);
			map.put("orgid", orgid);
			map.put("departid", departid);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
			map.put("flowdefid", flowdefid);
			map.put("provincecode", provincecode);
			
	    	int count = tbxfSentencealterationService.getLiAnCount(map);
	    	data= tbxfSentencealterationService.getLiAnList(map);
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultmap;
	}
	
	
	@RequestMapping(value = "/getAnJinaType")
	@ResponseBody
	public Object getAnJinaType() {
		
		ArrayList<Map<String, Comparable>> data = new ArrayList<Map<String, Comparable>>();
		Map<String, Comparable> map1 = new HashMap<String, Comparable>();
		map1.put("id", 1);
		map1.put("type","减刑");
		data.add(map1);
		
		Map<String, Comparable> map2 = new HashMap<String, Comparable>();
		map2.put("id", 2);
		map2.put("type","假释");
		data.add(map2);
		
		return data;
	}
	@RequestMapping(value = "/getYnjcReport")
	public ModelAndView getYnjcReport(HttpServletRequest request){
		
		String toolbar = request.getParameter("toolbar");//用于在jsp页面是否屏避 toolbar    0：屏避
		if(StringNumberUtil.notEmpty(toolbar)){
			request.setAttribute("toolbar", toolbar);
		}
		String casenos = request.getParameter("casenos");
		if(StringNumberUtil.notEmpty(casenos)){
			request.setAttribute("casenos", casenos);
		}
		SystemUser user = getLoginUser(request);
		RMEngine engine=this.systemResourceService.queryQualificationDataRmEngine("", user, request);
		request.setAttribute("mydata", engine.dedaoReportData());
		ModelAndView mav = new ModelAndView("report/ynjcReport");
		return mav;
	}
	
	/**
	 * 跳转列表页(报备立案)
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/outBaobeiLiAnCaseListPage")
	public ModelAndView toBaobeiTabsPage(HttpServletRequest request) {
		ModelAndView mav = null;
		SystemUser su = getLoginUser(request);
		String orgid = su.getOrgid();
		String departid = su.getDepartid();
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		Map<String, Object> departMap = new HashMap<String, Object>();
		departMap.put("departid", departid);
		Map temMap = commuteParoleBatchService.getCommuteParoleBatchInfo(departMap);//batchid,batch,curyear
		String curyear;
		String batch="";
		if(temMap !=null&&temMap.size()>0){
			curyear = temMap.get("curyear").toString();
			batch = temMap.get("batch").toString();
		}else{
			Date date = new Date();
			curyear = DateUtil.dateFormat(date, "yyyy");
		}
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		request.setAttribute("provincecode", provincecode);
		Map<String, String> temMap2 = new HashMap<String, String>();
		temMap2.put("departid", departid);
		temMap2.put("flowdefid", flowdefid);
		temMap2.put("curyear", curyear);
		String casenumber = "";
		casenumber = flowBaseService.getMaxCaseNum(temMap2);//案件号
		SystemOrganization so = systemOrganizationService.getByOrganizationId("4400");
		String shortname = so.getShortname();
		request.setAttribute("casenumber", casenumber);
		request.setAttribute("curyear", curyear);
		request.setAttribute("batch", batch);
		request.setAttribute("shortname", shortname);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		
		View view = new InternalResourceView("WEB-INF/JSP/commutationParole/baobeiRegistrListPageForGD.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 获取报备立案列表数据
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value = "/getbaobeiLiAnInfoList")
	@ResponseBody
	public Object getbaobeiLiAnInfoList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List data = new ArrayList();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String sel=request.getParameter("sel")==null?"1":request.getParameter("sel");
			String anjiantype = request.getParameter("anjiantype");
			SystemUser su = getLoginUser(request);
			String orgid = su.getOrgid();
			String departid = su.getDepartid();
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1; 
			Map<String,Object> map = new HashMap<String,Object>();
			String flowdefid = request.getParameter("flowdefid");
			//上海特有
			Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
			
			map.put("sel", sel);
			map.put("key", key);
			map.put("state", -1);
			map.put("nodeid", "3");//案件不同意的nodeid为3
			map.put("anjiantype", anjiantype);
			map.put("orgid", orgid);
			map.put("departid", departid);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
			map.put("flowdefid", flowdefid);
			
	    	int count = tbxfSentencealterationService.getBaobeiLiAnCount(map);
	    	data= tbxfSentencealterationService.getBaobeiLiAnList(map);
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultmap;
	}
	
	//三类犯报备
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/addSanLeiFanBaoBei.json")
	@ResponseBody
	public Object addSanLeiFanBaoBei(HttpServletRequest request,HttpServletResponse response){
		String states = request.getParameter("states")==null?"":request.getParameter("states").toString();
		String flowdraftids = request.getParameter("flowdraftids")==null?"":request.getParameter("flowdraftids").toString();
		String orgflg = request.getParameter("orgflg")==null?"":request.getParameter("orgflg").toString();
		String[] flowid = flowdraftids.split(",");
		String flag = "0";//新增
		//orgFlg	数据源标记。0:监狱；1省局；2法院；3检察院
		if(orgflg.equals("1")){//1省局
			flag="1";//修改	
		}
		Map map = new HashMap();
		if(flowid.length>0){
			for(int i= 0;i<flowid.length;i++){
				String flowdraftid = flowid[i];
				Map crimMap = tbxfSentencealterationService.getCrimidFlowData(flowdraftid);
				Map resourceMap = new HashMap();
				resourceMap.put("crimid", crimMap.get("APPLYID"));
				resourceMap.put("flowdefid", crimMap.get("FLOWDEFID"));
				String stateColumn = String.valueOf(Integer.parseInt(orgflg)+1);//指定要改变状态的字段
				resourceMap.put("state"+stateColumn, states);
				if(flag.equals("0")){
					resourceMap.put("flowdraftid", crimMap.get("FLOWDRAFTID"));
					resourceMap.put("flowid", crimMap.get("FLOWID"));
					resourceMap.put("departid", crimMap.get("DEPARTID"));
					resourceMap.put("caseno", crimMap.get("TEXT6"));
					resourceMap.put("orgflg", Integer.parseInt(orgflg));	
					tbxfSentencealterationService.addSanLeiFanBaoBei(resourceMap);
				}else if(flag.equals("1")){
					tbxfSentencealterationService.updateSanLeiFanBaoBei(resourceMap);
					
				}
				
			}
			
		}
		
		return null;
		
	}
	
	
	//三类犯省局往监狱报备
		@RequestMapping(value="/addSanLeiFanBaoBei123.json")
		@ResponseBody
		public Object addSanLeiFanBaoBei123(HttpServletRequest request,HttpServletResponse response){
			String states = request.getParameter("states")==null?"":request.getParameter("states").toString();
			String flowdraftids = request.getParameter("flowdraftids")==null?"":request.getParameter("flowdraftids").toString();
			String orgflg = request.getParameter("orgflg")==null?"":request.getParameter("orgflg").toString();
			String[] flowid = flowdraftids.split(",");
			
			return null;
			
		}
	
}

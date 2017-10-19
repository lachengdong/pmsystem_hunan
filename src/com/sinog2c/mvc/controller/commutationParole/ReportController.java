package com.sinog2c.mvc.controller.commutationParole;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.sinog2c.dao.api.system.TbsysDocumentMapper;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.report.RMEngine;

/**
 * 报表保存操作
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/report")
public class ReportController extends ControllerBase {

	@Resource
	private TbsysDocumentMapper tbsysdocumentmapper;
	@Resource
	SystemResourceService systemResourceService;
	/**
	 * 方法描述:报表保存
	 * 不涉及流程保存到 TBSYS_DOCUMENT 表
	 * 涉及到流程保存到 TBFLOW_OTHER_FLOW 表
	 */
	@RequestMapping("/saveReportData")
	@ResponseBody
	public int saveReportData(HttpServletRequest request){
		String menuid=request.getParameter("menuid");
		String data=request.getParameter("annexcontent");
		String reportname=request.getParameter("reportname");
		SystemUser user=getLoginUser(request);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("departid", user.getOrganization().getOrgid());
		map.put("introduction", reportname);
		map.put("opid", user.getUserid());
		map.put("content", data);
		map.put("tempid", "report");
		int i=tbsysdocumentmapper.insertByMap(map);
		return i;
	}
	/**
	 * 跳转报表数据列表页
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/toReportListPage.page")
	public ModelAndView toReportListPage(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("report/lookreport");
	}
	/**
	 * 获取保存报表数据列表
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value="/reprotList.json")
	@ResponseBody
	public Object reprotList(HttpServletRequest request){
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		SystemUser user=getLoginUser(request);
		Map resultmap=new HashMap();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("departid", user.getOrganization().getOrgid());
		map.put("tempid", "report");
		map.put("start", start);
		map.put("end", end);
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		List list=new ArrayList();
		list.add(MapUtil.convertKeyList2LowerCase(tbsysdocumentmapper.findreprotList(map)));
		resultmap.put("data", list);
		resultmap.put("total", 30);
		return MapUtil.convertKeyList2LowerCase(tbsysdocumentmapper.findreprotList(map));
	}
	@RequestMapping(value="/chakanReportPage.page")
	public ModelAndView chakanReportPage(HttpServletRequest request){
		JSONArray json=new JSONArray();
		String docid=request.getParameter("docid");
		Map<String,String> map=new HashMap<String,String>();
		map.put("docid", docid);
		String docment=tbsysdocumentmapper.findreportDataByDocid(map);
		if(null!=docment&&!"".equals(docment)){
			json.add(docment);
		}
		request.setAttribute("formcontent", json.toString());
		return new ModelAndView("/aip/showReportDataPage");
	}
	
	@RequestMapping(value="/flowReportPage.page")
	public ModelAndView flowReportPage(HttpServletRequest request){
		SystemUser user=getLoginUser(request);
		String menuid=request.getParameter("menuid");
		request.setAttribute("menuid",menuid);
		String departname=user.getOrganization().getName();
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(cal.MONTH, -1);
		request.setAttribute("ecwDate", new SimpleDateFormat("yyyy-MM").format(cal.getTime()));
		request.setAttribute("cfdescribe", departname+StringNumberUtil.formatDateUpper(new SimpleDateFormat("yyyy年M月").format(cal.getTime()))+"考核考勤月报表");
		return new ModelAndView("/report/generateMonthReport");
	}
	
	@RequestMapping(value="/generateMonthReport.page")
	public ModelAndView generateMonthReport(HttpServletRequest request){
		
		SystemUser user=getLoginUser(request);
		
		String ecwDate=request.getParameter("statdate");
		String cfdescribe=request.getParameter("cfdescribe");
		request.setAttribute("ecwDate", ecwDate);
		request.setAttribute("cfdescribe", cfdescribe);
		RMEngine engine=systemResourceService.queryQualificationDataRmEngine("", user, request);
		String aa = engine.dedaoReportData();
		request.setAttribute("mydata",aa );
		request.setAttribute("menuid", request.getParameter("menuid"));
		return new ModelAndView("/report/generateMonthReport");
	}
}

package com.sinog2c.mvc.controller.commutationParole;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
/**
 * 监区评议
 * 
 * @author hzl
 * 
 */
@Controller("jxjsPrisonReviewController")
public class PrisonReviewController extends ControllerBase {
	
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	/**
	 * 跳转列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toPrisonReviewPage")
	public ModelAndView toPage(HttpServletRequest request) {
		ModelAndView mav = null;
		returnResourceMap(request);
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/prisonReviewListPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 跳转监区长办公会
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toPrisonReviewMeetingPage")
	public ModelAndView toTabsPage() {
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/prisonReview_Meeting.jsp");
		mav = new ModelAndView(view);
		return mav;
	}

	
	/**
	 * 获取监区评议列表
	 * 
	 * @author hzl
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value = "/getPrisonReviewList")
	@ResponseBody
	public Object getBasicInfoList(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>> ();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			String orgid = getLoginUser(request).getOrgid();
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("orgid", orgid);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
			
	    	int count = tbxfSentencealterationService.allCount(map);
	    	data= tbxfSentencealterationService.selectTbxfs(map);
	    	if(data!=null &&!data.isEmpty()){
	    		
	    	}
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
		}
		
		return resultmap;
	}
	
	@RequestMapping(value = "/toPrisonReviewTabs")
	public ModelAndView toAgentCaseTabs(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		String tempid=  request.getParameter("tempid");
		String action = request.getParameter("action");
		String menuid = request.getParameter("menuid");
		ModelAndView mav = null;
		request.setAttribute("crimid", crimid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("action", action);
		request.setAttribute("menuid", menuid);
		View view = new InternalResourceView(
		"WEB-INF/JSP/aip/prisonReviewTabs.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	//办案
	@RequestMapping(value = "/prisonReview")
	public ModelAndView showAddFrom(HttpServletRequest request){
		JSONArray docconent = new JSONArray();
		returnResourceMap(request);
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		String crimid = request.getParameter("crimid");
		TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService
		.getCrimeByCrimid(crimid);
		Map<String,Object> tbxfMap = tbxfSentencealterationService.selectTbxfByCrimid(crimid);
		String tempid = request.getParameter("tempid");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
		Map<String,Object> map = new HashMap<String,Object>();
		if (template != null) {
			map.put("text34",template.getTempname());
			docconent.add(template.getContent());
		}
		if(tbprisonerBaseinfo!=null){
			String deptid = tbprisonerBaseinfo.getDepartid();
			SystemOrganization org = systemOrganizationService
			.getByOrganizationId(deptid);
			if(org!=null){
				map.put("departid",org.getName());
				
			}
			map.put("cbiname",tbprisonerBaseinfo.getName());
			map.put("cbitruename",tbprisonerBaseinfo.getUsedname());
			map.put("cbigendername",tbprisonerBaseinfo.getGender());
			map.put("caieducation",tbprisonerBaseinfo.getEducation());
			map.put("cbinativenamedetail",tbprisonerBaseinfo.getRegisteraddressdetail());
			map.put("cbination",tbprisonerBaseinfo.getNation());
			map.put("cbibirthday",sdf.format(tbprisonerBaseinfo.getBirthday()));
			map.put("cbihomeaddress",tbprisonerBaseinfo.getFamilyaddress());
			
		}
		if(tbprisonerBaseCrime!=null){
			map.put("criofficiallyplacedate",sdf.format(tbprisonerBaseCrime.getInprisondate()));
			map.put("text32", "执行日期");
			map.put("zhixingdate", sdf.format(tbprisonerBaseCrime.getExecutiondate()));
			map.put("cjicourtname", tbprisonerBaseCrime.getJudgmentname());//法院
			if(tbprisonerBaseCrime.getPunishmenttype()!=GkzxCommon.XINGQI_YOUQI){
				map.put("zhuxing", tbprisonerBaseCrime.getPunishmentyear()+"年"+tbprisonerBaseCrime.getPunishmentmonth()+"个月"+tbprisonerBaseCrime.getPunishmentday()+"天");// 刑期
			}else if(tbprisonerBaseCrime.getPunishmenttype()!=GkzxCommon.XINGQI_SIHUAN){
				map.put("zhuxing", "死缓");
			}else if(tbprisonerBaseCrime.getPunishmenttype()!=GkzxCommon.XINGQI_WUQI){
				map.put("zhuxing", "无期");
			}else {
				map.put("zhuxing", "死刑");
			}
			map.put("fujiaxing", tbprisonerBaseCrime.getLosepoweryear()+"年"+tbprisonerBaseCrime.getLosepowermonth()+"个月"+tbprisonerBaseCrime.getLosepowereday()+"天");// 剥夺年限
			map.put("anyouhuizong", tbprisonerBaseCrime.getCauseaction());
			map.put("xingqiqizhi","从"+sdf2.format(tbprisonerBaseCrime.getSentencestime())+"至"+sdf2.format(tbprisonerBaseCrime.getSentenceetime()));
			map.put("fanzuishijian", sdf.format(tbprisonerBaseCrime.getCrimedate()));
			map.put("cjimulct", tbprisonerBaseCrime.getForfeit());
			map.put("fajinjiaonaqingkuang", tbprisonerBaseCrime.getPayment());
			map.put("cjipeichangjine", tbprisonerBaseCrime.getCompensation());
			map.put("cjmoneydisgorged", tbprisonerBaseCrime.getStolenmoney());
			map.put("text33", "退回赃款");
			map.put("paymentzk", tbprisonerBaseCrime.getReturnloot());
			map.put("paymentpc", tbprisonerBaseCrime.getFulfilcompensation());
			map.put("cjisequestrateproperty", tbprisonerBaseCrime.getForfeitureproperty());
			map.put("paymentcc", tbprisonerBaseCrime.getExpropriation());
			map.put("fanzuishishi",tbprisonerBaseCrime.getCrimeface()==null?"":tbprisonerBaseCrime.getCrimeface().replace("&#13;&#10;", "\\r\\n").replace("\r\n", "\\r\\n").replace("rn", "\\r\\n"));
			map.put("text9", sdf.format(new Date()));
			map.put("gaizaobiaoxian", "改造表现--没查");
			
		}
		if(tbxfMap!=null&&!tbxfMap.isEmpty()){
			map.put("parolenumber","("+tbxfMap.get("COURTYEAR")+")"+tbxfMap.get("COURTSHORT")+"第"+tbxfMap.get("COURTSN")+"号");
			map.put("prisonterm", tbxfMap.get("CHANGEINFO"));
			map.put("zclhq", tbxfMap.get("INCOME"));
			map.put("gwzc", tbxfMap.get("PAY"));
			if(tbxfMap.get("INCOME")!=null&&tbxfMap.get("PAY")!=null){
				map.put("balance",map.get("OVERPLUS"));
			}
		}
		map.put("criminalid", crimid);
		String applyvalue = crimid+","+tbprisonerBaseinfo.getName();
		request.setAttribute("tempid", tempid);
		request.setAttribute("applyvalue",applyvalue);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		ModelAndView mav = null;
		View view =null;
			view = new InternalResourceView(
			"WEB-INF/JSP/aip/addPrisonReview.jsp");
		mav = new ModelAndView(view);
		return mav;
		
	}
}

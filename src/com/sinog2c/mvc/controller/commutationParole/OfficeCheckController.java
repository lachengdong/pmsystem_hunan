package com.sinog2c.mvc.controller.commutationParole;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 处室审核
 * 
 * @author hzl
 * 
 */
@Controller
public class OfficeCheckController extends ControllerBase {
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private SystemModelService systemModelService;
	/**
	 * 跳转列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toOfficeCheckPage")
	public ModelAndView toTabsPage(HttpServletRequest request) {
		returnResourceMap(request);
		SystemUser su = getLoginUser(request);
		SystemOrganization so = systemOrganizationService.getByOrganizationId(su.getDepartid());
		String shortname = so.getShortname();
		request.setAttribute("shortname", shortname);
		
		ModelAndView mav = null;
		Date date = new Date();
		String year = DateUtil.dateFormat(date, "yyyy");
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		String fatherMenuid = request.getParameter("fatherMenuid");
		request.setAttribute("year", year);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("fatherMenuid", fatherMenuid);
		View view = new InternalResourceView("WEB-INF/JSP/commutationParole/provinceOfficeChuShiCheck.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	

	/**
	 * 方法描述：列表页面
	 * 2014-8-7
	 */
	@RequestMapping(value = "/getOfficeShenHeList")
	@ResponseBody
	public Object getOfficeShenHeList(HttpServletRequest request){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map> data = new ArrayList<Map> ();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
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
			String casetype = request.getParameter("casetype");
			String casenums = request.getParameter("casenums");
			String year = request.getParameter("year");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("suid", su.getUserid());
			map.put("orgid", orgid);
			map.put("departid", departid);
			map.put("flowdefid", "other_sjjxjssp");
			map.put("key", key);
			map.put("jailid", jailid);
			map.put("casetype", casetype);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	if(StringNumberUtil.notEmpty(casenums)&&StringNumberUtil.notEmpty(year)){
	    		casenums = StringNumberUtil.formatCaseNo(casenums,year);
		    	map.put("casenums", casenums);
	    	}else{
	    		map.put("year", year);
	    	}
	    	
	    	int count = tbxfSentencealterationService.countOfficeShenHeList(map);
	    	data= tbxfSentencealterationService.officeShenHeList(map);
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	//办案
	@RequestMapping(value = "/toOfficeCheck")
	public ModelAndView toOfficeCheck(HttpServletRequest request) {
		returnResourceMap(request);
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		String crimid = request.getParameter("crimid");
		String tempid=  request.getParameter("tempid");
		String action = request.getParameter("action");
		String menuid = request.getParameter("menuid");
		ModelAndView mav = null;
		request.setAttribute("crimid", crimid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("action", action);
		request.setAttribute("menuid", menuid);
		
		JSONArray docconent = new JSONArray();
		TbprisonerBaseinfo tbprisonerBaseinfo  = prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService.getCrimeByCrimid(crimid);
		Map<String,Object> tbxfMap = tbxfSentencealterationService.selectTbxfByCrimid(crimid);
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
			SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);
			if(org!=null){
				map.put("departid",org.getName());
				
			}
			map.put("cbiname",tbprisonerBaseinfo.getName());
			//age;年龄
			map.put("cbinativename",tbprisonerBaseinfo.getOriginplaceaddress());
			map.put("caiformeraddress",tbprisonerBaseinfo.getAddressdetail());
			//yuanpanxingqi原判刑期
			//lyplacedatestring入监日期
			//jianyuyijian单位意见
			//gaizaobiaoxian改造表现
			map.put("cbigendername",tbprisonerBaseinfo.getGender());
			map.put("cbinativenamedetail",tbprisonerBaseinfo.getRegisteraddressdetail());
			
		}
		if(tbprisonerBaseCrime!=null){
			map.put("cjicourtname", tbprisonerBaseCrime.getJudgmentname());//法院
			map.put("fujiaxing", tbprisonerBaseCrime.getLosepoweryear()+"年"+tbprisonerBaseCrime.getLosepowermonth()+"个月"+tbprisonerBaseCrime.getLosepowereday()+"天");// 剥夺年限
			map.put("anyouhuizong", tbprisonerBaseCrime.getCauseaction());
			map.put("gaizaobiaoxian", "改造表现--没查");
			
		}
		if(tbxfMap!=null&&!tbxfMap.isEmpty()){
			map.put("parolenumber","("+tbxfMap.get("COURTYEAR")+")"+tbxfMap.get("COURTSHORT")+"第"+tbxfMap.get("COURTSN")+"号");
		}
		map.put("criminalid", crimid);
		String applyvalue = crimid+","+tbprisonerBaseinfo.getName();
		request.setAttribute("flowdefid", "other_sjjxjssp");
		request.setAttribute("applyvalue",applyvalue);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		
		View view = new InternalResourceView(
		"WEB-INF/JSP/aip/toOfficeCheck.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
}

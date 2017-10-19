package com.sinog2c.mvc.controller.outexecute;

import java.net.URLDecoder;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;

/**
 * 类描述：保外初保（续保）合议
 * 
 * @author liuchaoyang 下午07:25:52
 */
@Controller
public class forProtocolController extends ControllerBase {
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;

	/**
	 * 方法描述：跳转到保外初保罪犯选择列表页
	 * @author liuchaoyang 2014-8-06 22:08:45
	 */
	@RequestMapping("/primaryHealthPanel")
	public ModelAndView primaryHealthPanel(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/primaryHealthPanelChoose");
	}
	/**
	 * 获取保外初保罪犯数据
	 * @author liuxin
	 * 2014-8-06 22:08:45
	 */
	@RequestMapping(value = "/getprimaryHealthPanelList")
	@ResponseBody
	public Object getprimaryHealthPanelList(HttpServletRequest request) {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String condition = prisonerService.getTheQueryCondition("10152");
			key = URLDecoder.decode(key,"UTF-8");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			//map.put("departId", getLoginUser(request).getOrgid());
			map.put("condition", condition);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));   
	    	map.put("end",String.valueOf(end));
	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
	    	map.put("flowdefid", "doc_bwcbhysp");
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}

	/**
	 * 方法描述：保外初保合议表单页面
	 * 
	 * @author liuchaoyang 2014-7-26 18:52:45
	 */
	@RequestMapping("/disposePrimaryHealthPanel")
	public ModelAndView disposePrimaryHealthPanel(HttpServletRequest request) {
		String idnumber = request.getParameter("idnumber");
		String crimid = request.getParameter("crimid");
		//如果罪犯编号为空就表示批量处理,将id解析成数组，取第一个作为复合编号（crimid+flowdraftid）
		if(crimid==null || "".equals(crimid)){
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];
			//将这个复合编号解析成数组，第一个是罪犯编号，第二个是流程草稿Id
			ids = crimid.split("@");
			crimid = ids[0];
			if(ids.length>1)idnumber = ids[1];
		}
		//String menuid = request.getParameter("menuid");
		//request.setAttribute("menuid", menuid);
		JSONArray docconent = new JSONArray();
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);// 查询犯人基本信息
		TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
		if(idnumber != null && !"".equals(idnumber)){
			String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
			docconent.add(docConent);
			request.setAttribute("flowdraftid", idnumber);
		}else{
			String tempid = "SJ_BWJY_CBHYDJB";
			request.setAttribute("tempid", tempid);
			SystemUser user = getLoginUser(request);
			String departid = user.getDepartid();// 根据用户Id获取所在部门Id
			HashMap<String, Object> map = new HashMap<String, Object>();
			String year = new SimpleDateFormat("yyyy").format(new Date());// 定义时间格式获取年号
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) docconent.add(template.getContent());
			String xuhao = tbsysDocumentService.getXuHao(null, tempid, crimid,departid);// 获取序号
			SystemOrganization org = systemOrganizationService.getByOrganizationId(departid);// 根据部门Id获取单位信息
			map.put("text17", year);
			map.put("text20", xuhao);
			map.put("text1", org.getName());
			map.put("cbiname", info.getName());
			map.put("cbigendername", info.getGender());
			String ressarea=info.getAddressarea()==null?"":info.getAddressarea();
			String ressdetail=info.getAddressdetail()==null?"":info.getAddressdetail();
			map.put("cbihomeaddress", ressarea+ressdetail);
			map.put("addressdetail", info.getAddressdetail());
			map.put("age", DateUtil.getAge(info.getBirthday()));// 年龄
			map.put("cbinativenamedetail", info.getOrigin());
			if (crime != null) {
				map.put("anyouhuizong", crime.getCauseaction());
				map.put("ypxq", info.getRemark());
				map.put("cjibegindate", DateUtil.dateFormatForAip(crime.getSentencestime()));// 刑期起日
				map.put("cjibegindate", DateUtil.dateFormatForAip(crime.getSentenceetime()));// 刑期止日
				map.put("text36", DateUtil.dateFormatForAip(crime.getInprisondate()));// 入监时间
			}
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("flowdefid", "doc_bwcbhysp");
		request.setAttribute("crimid", crimid);
		request.setAttribute("applyid", crimid);
		request.setAttribute("applyname", info.getName());
		request.setAttribute("orgid",crime.getOrgid1());
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("aip/primaryHealthPanel");
	}
	

	

	/**
	 * 方法描述：跳转到保外续保罪犯选择列表页
	 * 
	 * @author liuchaoyang 2014-8-06 22:08:45
	 */
	@RequestMapping("/renewalOfInsurancePanel")
	public ModelAndView renewalOfInsurancePanel(HttpServletRequest request) {
		return new ModelAndView("chooseCriminal/renewalOfInsurancePanelChoose");
	}

	/**
	 * 获取保外续保罪犯数据
	 * 
	 * @author liuchaoyang 2014-8-06 22:08:45
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value = "/getRenewalOfInsurancePanelList")
	@ResponseBody
	public Object getRenewalOfInsurancePanelList(HttpServletRequest request) {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			key = URLDecoder.decode(key,"UTF-8");
			String condition = prisonerService.getTheQueryCondition("10154");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			//map.put("departId", getLoginUser(request).getOrgid());
			map.put("condition", condition);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));   
	    	map.put("end",String.valueOf(end));
	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
	    	map.put("flowdefid", "doc_bwxbhysp");
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	/**
	 * 方法描述：保外续保合议
	 * 
	 * @author liuchaoyang 2014-7-26 18:52:45
	 */
	@RequestMapping("/disposeRenewalOfInsurancePanel")
	public ModelAndView disposeRenewalOfInsurancePanel(HttpServletRequest request) {
		String idnumber = request.getParameter("idnumber") == null ? "": request.getParameter("idnumber");// 流程编号
		String crimid = request.getParameter("crimid");
		//如果罪犯编号为空就表示批量处理,将id解析成数组，取第一个作为复合编号（crimid+flowdraftid）
		if(crimid==null || "".equals(crimid)){
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];
			//将这个复合编号解析成数组，第一个是罪犯编号，第二个是流程草稿Id
			ids = crimid.split("@");
			crimid = ids[0];
			if(ids.length>1)idnumber = ids[1];
		}
		//String menuid = request.getParameter("menuid");// 罪犯编号
		//request.setAttribute("menuid", menuid);
		JSONArray docconent = new JSONArray();
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);// 查询犯人基本信息
		TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
		if(idnumber != null && !"".equals(idnumber)){
			String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
			docconent.add(docConent);
			request.setAttribute("flowdraftid", idnumber);
		}else{
			String tempid = "SJ_BWJY_XBHYDJB";
			request.setAttribute("tempid", tempid);
			HashMap<String, Object> map = new HashMap<String, Object>();
			String year = new SimpleDateFormat("yyyy").format(new Date());// 定义时间格式获取年号
			SystemUser user = getLoginUser(request);
			String departid = user.getDepartid();// 根据用户Id获取所在部门Id
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) docconent.add(template.getContent());
			String xuhao = tbsysDocumentService.getXuHao(null, tempid, null,departid);// 获取序号
			SystemOrganization org = systemOrganizationService.getByOrganizationId(departid);// 根据部门Id获取单位信息
			map.put("text19", org.getName());
			map.put("text12", year);
			map.put("text14", xuhao);
			if (info != null) {
				map.put("cbiname", info.getName());
				map.put("xingbie", info.getGender());
				map.put("jiatingzhuzhi", info.getFamilyaddress());
				map.put("age", DateUtil.getAge(info.getBirthday()));// 年龄
				map.put("jiguan", info.getOriginplaceaddress());
			}
			if (crime != null) {
				map.put("zuiming", crime.getCauseaction());
				map.put("yuanpanxingqi", crime.getRemark());
				String xingqiqizhi = "自"+ DateUtil.dateFormatForAip(crime.getSentencestime())+ "至"+ DateUtil.dateFormatForAip(crime.getSentenceetime());
				map.put("xianxingqiqizhi", xingqiqizhi);// 刑期起止
				map.put("rujianriqi", crime.getDetaindate());// 入监时间
			}
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("flowdefid", "doc_bwxbhysp");
		request.setAttribute("crimid", crimid);
		request.setAttribute("applyid", crimid);
		request.setAttribute("applyname", info.getName());
		request.setAttribute("orgid",crime.getOrgid1());
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("aip/renewalOfInsurancePanel");
	}

}

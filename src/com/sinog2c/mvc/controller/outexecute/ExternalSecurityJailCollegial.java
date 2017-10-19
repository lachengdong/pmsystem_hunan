package com.sinog2c.mvc.controller.outexecute;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;


/**
 * 保外收监合议
 * @author liuxin
 * 2014-7-28 15:28:28
 */
@Controller
public class ExternalSecurityJailCollegial  extends ControllerBase{
	@Resource
	private ChooseCriminalService chooseCriminalService;
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	@Resource
	protected FlowBaseService flowBaseService;
	
	/**
	 * 跳转保外收监合议罪犯处理页面
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/externalSecurityJailCollegial")
	public ModelAndView externalSecurityJailCollegial(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/externalSecurityJailCollegialChoose");
	}
	/**
	 * 获取罪犯列表
	 * 
	 * @author liuxin
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value = "/getExternalSecurityJailCollegialList")
	@ResponseBody
	public Object getExternalSecurityJailCollegialList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String condition = prisonerService.getTheQueryCondition("10153");
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
	    	map.put("flowdefid", "doc_bwsjhysp");
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
		}
		return resultmap;
	}
	/**
	 * 跳转保外收监合议新增页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/externalSecurityJailCollegialAdd")
	public ModelAndView toExternalSecurityJailCollegialAdd(HttpServletRequest request){
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
		JSONArray docconent = new JSONArray();
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime baseCrime =  prisonerService.getCrimeByCrimid(crimid);
		if(idnumber != null && !"".equals(idnumber)){
			String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
			docconent.add(docConent);
			request.setAttribute("flowdraftid", idnumber);
		}else{
			SystemUser user = getLoginUser(request);//获取当前登录的用户
			String deptid=user.getDepartid();
			String tempid = "SJ_BWJY_CJHYDJB";//request.getParameter("tempid");
			request.setAttribute("tempid", tempid);
			SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);//通过部门id去找所在单位名称
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
			if (template != null) docconent.add(template.getContent());
			Map<String, Object> map = new HashMap<String,Object>();
			SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy");
			map.put("cbiname", baseinfo.getName());
			map.put("xingbie", baseinfo.getGender());
			map.put("age", DateUtil.getAge(baseinfo.getBirthday()));
			map.put("jiguan", baseinfo.getOriginplacearea());
			map.put("jiatingzhuzhi", baseinfo.getAddressdetail());
			map.put("text20", org.getName());
			if(baseCrime != null){
				map.put("zuiming", baseCrime.getCauseaction());
				map.put("yuanpanxingqi",baseCrime.getRemark());
				map.put("rujianriqi", DateUtil.dateFormatForAip(baseCrime.getInprisondate()));
				//刑期起止
				Date startTime = baseCrime.getSentencestime();
				Date endTime = baseCrime.getSentenceetime();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd");
				if(startTime!=null&& endTime!=null){
					String xingqiqizhi = "自" + sdf.format(startTime) + "起至" + sdf.format(endTime) + "止";
					map.put("xianxingqiqizhi",xingqiqizhi);
				}
				map.put("text8", formatter3.format(new Date()));
				map.put("text9", org.getShortname()+GkzxCommon.CASE_TYPE_BWJY_PARAM);
			}
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		returnResourceMap(request);
		request.setAttribute("flowdefid", "doc_bwsjhysp");
		request.setAttribute("crimid", crimid);
		request.setAttribute("orgid", baseCrime.getOrgid1());
		request.setAttribute("applyid", crimid);
		request.setAttribute("applyname",baseinfo.getName());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/externalSecurityJailCollegialAdd");
	}
	
	
}
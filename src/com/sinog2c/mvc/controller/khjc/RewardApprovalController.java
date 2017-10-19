package com.sinog2c.mvc.controller.khjc;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.sinog2c.service.api.khjc.RewardMonthlySummaryService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLocalcodeService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.StringNumberUtil;

@Controller
public class RewardApprovalController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Resource
	private PrisonerService prisonerService;
	@Autowired
	protected FlowBaseService flowBaseService;
	@Autowired
	private RewardMonthlySummaryService rewardMonthlySummaryService;
	@Autowired
	private SystemLocalcodeService systemLocalcodeService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	
	@RequestMapping("/rewardApprovalChoose")
	public  ModelAndView rewardApprovalChoose(HttpServletRequest request){
		return new ModelAndView("chooseCriminal/rewardApprovalChoose");
	}
	
	@RequestMapping(value = "/getRewardApprovalChoose")
	@ResponseBody
	public Object getRewardApprovalChoose(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		Calendar calendar = Calendar.getInstance();
		int cyear = calendar.get(Calendar.YEAR);
	    int cmonth = calendar.get(Calendar.MONTH) + 1;
		try {
			//取得参数
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			//分页
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			//字段排序
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			if(!StringNumberUtil.isNullOrEmpty(sortField) && sortField.equals("CSEASON")) {
				sortField = "seasonsort";
			}
			
			if(!StringNumberUtil.isNullOrEmpty(sortField) && sortField.equals("PASTSEASON")) {
				sortField = "pastseasonsort";
			}
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("orgid", getLoginUser(request).getOrgid());
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("cyear", cyear);
	    	map.put("cmonth", cmonth);
	    	int count = rewardMonthlySummaryService.getRewardMonthlySummaryCount(map);
	    	data= rewardMonthlySummaryService.getRewardMonthlySummaryList(map);
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	@RequestMapping("/rewardApproval")
	public ModelAndView rewardApproval(HttpServletRequest request){
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String idname = request.getParameter("idname")==null?"":request.getParameter("idname");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String menuid = request.getParameter("menuid")==null?"":request.getParameter("menuid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id == null ? null:id.split(",");
			String[] idnames = idname == null?null:idname.split(",");
			crimid = ids ==null?"":ids[0];//罪犯编号就从数组里取出第一个罪犯编号
			name = idnames == null?"":idnames[0];
		}
		request.setAttribute("idname", idname);
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("menuid", menuid);
		return new ModelAndView("khjc/zxjf/rewardApproval");
	}
	
	@RequestMapping(value = "/rewardApprovalList")
	@ResponseBody
	public Object rewardApprovalList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			SystemUser user = getLoginUser(request);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("departId", user.getDepartid());
			map.put("applyid", crimid);
			map.put("key", key);
			map.put("flowdefid", "other_khrewardsp");
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));  
	    	List<Map> list = flowBaseService.findByMapCondition(map);
			int count = flowBaseService.countAllByCondition(map);
	    	resultmap.put("data", list);
			resultmap.put("total", count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	@RequestMapping(value = "rewardApprovalAdd")
	public ModelAndView rewardApprovalAdd(HttpServletRequest request) {
		returnResourceMap(request);
		// 获取当前登录的用户
		SystemUser user = getLoginUser(request);
		String userId = user.getDepartid();
		String tempid = "TKH_REWARDSPB";
		String crimid = request.getParameter("crimid");
		JSONArray docconent = new JSONArray();
		Map<String,Object> map = new HashMap<String,Object>();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, userId);
		if (template != null) {
			docconent.add(template.getContent());
		}
		TbprisonerBaseinfo baseInfo = prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(crimid);
		
		if(baseInfo.getBirthday()!=null) {
			Calendar birthday = Calendar.getInstance();
			birthday.setTime(baseInfo.getBirthday());
			Calendar sysdate = Calendar.getInstance();
			sysdate.setTime(new Date());
			int birthyear = sysdate.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
			map.put("age", birthyear);
		}
		String orginalsentence = "";
		if(!StringNumberUtil.isNullOrEmpty(baseCrime.getPunishmentyear()) && baseCrime.getPunishmentyear()==9995) {
			orginalsentence = GkzxCommon.XINGQI_WUQI_ZH;
		} else if(!StringNumberUtil.isNullOrEmpty(baseCrime.getPunishmentyear()) && baseCrime.getPunishmentyear()==9996) {
			orginalsentence = GkzxCommon.XINGQI_SIHUAN_ZH;
		} else {
			orginalsentence = GkzxCommon.XINGQI_YOUQI_ZH;
			if(!StringNumberUtil.isNullOrEmpty(baseCrime.getPunishmentyear()) && baseCrime.getPunishmentyear()>0) {
				orginalsentence = orginalsentence + baseCrime.getPunishmentyear() + GkzxCommon.YEAR;
			}
			if(!StringNumberUtil.isNullOrEmpty(baseCrime.getPunishmentmonth()) && baseCrime.getPunishmentmonth()>0) {
				orginalsentence = orginalsentence + baseCrime.getPunishmentmonth() + GkzxCommon.MONTHS;
			}
		}
		
		SystemOrganization provinceOrg = systemOrganizationService.getProvinceCode(user.getOrgid());
		Map<String, Object> selectmap = new HashMap<String, Object>();
		Map<String, Object> codeConditionMap = new HashMap<String, Object>();
		codeConditionMap.put("codetype", "GK100019");
		codeConditionMap.put("pcodeid", "7");
		codeConditionMap.put("orgpid", provinceOrg.getOrgid());
		selectmap.put("rewardtype", systemLocalcodeService.getLocalcodeUserbytemp(codeConditionMap));
		
		map.put("rewardcrimid", baseCrime.getCrimid());
		map.put("orgname", baseCrime.getOrgname1());
		map.put("name", baseInfo.getName());
		map.put("maincase", baseCrime.getMaincase());
		map.put("punishment", orginalsentence);
		request.setAttribute("flowdefid", "other_khrewardsp");
		request.setAttribute("orgid", baseCrime.getOrgid1());
		request.setAttribute("applyid", crimid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("applyname",baseInfo.getName());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
		return new ModelAndView("aip/khjc/rewardApprovalAdd");
	}
	
	@RequestMapping(value = "/toRewardApproval")
	public ModelAndView toRewardApproval(HttpServletRequest request) {
		String menuid=request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		String applyid=request.getParameter("applyid");
		String name=request.getParameter("name");
		try {
			TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(applyid);
			name=baseinfo.getName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbprisonerBaseCrime tbprisonerBaseCrime =prisonerService.getCrimeByCrimid(applyid);
		String orgid="";
		if(tbprisonerBaseCrime!=null){
			orgid = tbprisonerBaseCrime.getOrgid1();
		}
		request.setAttribute("crimid", applyid);
		request.setAttribute("applyid", applyid);
		request.setAttribute("applyname", name);
		request.setAttribute("orgid", orgid);
		ModelAndView mav = new ModelAndView("aip/khjc/rewardApprovalAdd");
		JSONArray docconent = new JSONArray();		
		String flowdraftid=request.getParameter("flowdraftid");	
		request.setAttribute("flowdraftid", flowdraftid);
		String baseDoc= flowBaseService.getDocConentByFlowdraftId(flowdraftid);
		if (baseDoc != null) {
			docconent.add(baseDoc);
		}
		request.setAttribute("formcontent", docconent.toString());
		return mav;
	}
}

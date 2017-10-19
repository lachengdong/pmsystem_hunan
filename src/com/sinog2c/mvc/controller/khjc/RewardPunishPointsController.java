package com.sinog2c.mvc.controller.khjc;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.khjc.RewardMonthlySummaryService;
import com.sinog2c.service.api.khjc.RewardPunishPointsService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLocalcodeService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DataBaseDateTransfer;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/*
 * 批量、专项奖扣分；处罚、批量奖励处罚、解除处罚审批
 */
@Controller
public class RewardPunishPointsController extends ControllerBase {
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private SystemModelService systemModelService;
	@Resource
	private PrisonerService prisonerService;
	@Autowired
	private RewardPunishPointsService rewardPunishPointsService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	protected FlowBaseService flowBaseService;
	@Autowired
	protected SystemLocalcodeService systemLocalcodeService;
	@Autowired
	protected RewardMonthlySummaryService rewardMonthlySummaryService;
	
	//跳转到罪犯选择页面
	@RequestMapping("/rewardPunishsPointsChoose")
	public  ModelAndView rewardPunishsPointsChoose(HttpServletRequest request){
		returnResourceMap(request);
		String tempid = request.getParameter("tempid");
		String menuid = request.getParameter("menuid");
		String pcodeid=request.getParameter("pcodeid");
		String codetype=request.getParameter("codetype");
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("pcodeid", pcodeid);		
		request.setAttribute("codetype", codetype);
		return new ModelAndView("chooseCriminal/rewardPunishsPointsChoose");
	}
//	//获取当前部门可操作的罪犯相关数据列表
//	@RequestMapping(value = "/getRewardPunishPointsChoose")
//	@ResponseBody
//	public Object getRewardPunishPointsChoose(HttpServletRequest request,HttpServletResponse response){
//		SystemUser user = getLoginUser(request);
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			//取得参数
//			String flowdefid = request.getParameter("flowdefid");
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			//分页
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
//			//字段排序
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("key", key);
//			map.put("flowdefid", flowdefid);
//			map.put("orgid", user.getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	data= chooseCriminalService.getBasicInfoList(map);
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return resultmap;
//	}
	//跳转到单个罪犯相关数据页面
	@RequestMapping("/rewardPunishsPoints")
	public ModelAndView rewardPunishsPoints(HttpServletRequest request){
		returnResourceMap(request);
		SystemResource resource = null;
		String name = request.getParameter("name")==null?"":request.getParameter("name");  
		String idname = request.getParameter("idname")==null?"":request.getParameter("idname");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String menuid = request.getParameter("menuid")==null?"":request.getParameter("menuid");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String codetype = request.getParameter("codetype")==null?"":request.getParameter("codetype");
		String pcodeid = request.getParameter("pcodeid")==null?"":request.getParameter("pcodeid");
		String flowdefid = request.getParameter("flowdefid")==null?"":request.getParameter("flowdefid");
		String fathermenuid = request.getParameter("fathermenuid")==null?"":request.getParameter("fathermenuid");
	    if(!StringNumberUtil.isNullOrEmpty(fathermenuid)) resource = systemResourceService.getByResourceId(fathermenuid);//获取父类菜单名字
	    else resource = systemResourceService.getByResourceId(menuid);//获取菜单名字
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
		request.setAttribute("tempid", tempid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("codetype", codetype);
		request.setAttribute("pcodeid", pcodeid);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("menuname", resource.getName());
		request.setAttribute("fathermenuid", fathermenuid);
		return new ModelAndView("khjc/zxjf/rewardPunishsPoints");
	}
	//获取单个罪犯相关数据列表
	@RequestMapping(value = "/rewardPunishsPointsList")
	@ResponseBody
	public Object rewardPunishsPointsList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		try {
			String flowdefid = request.getParameter("flowdefid");
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
			map.put("flowdefid", flowdefid);
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
	//跳转到表单页面
	@RequestMapping(value = "rewardPunishsPointsAdd")
	public ModelAndView rewardPunishsPointsAdd(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String menuid=request.getParameter("menuid");
		String codetype=request.getParameter("codetype");
		String pcodeid=request.getParameter("pcodeid");
		String crimid = request.getParameter("crimid");
		String tempid = request.getParameter("tempid");
		String flowdefid = request.getParameter("flowdefid");
		String flowdraftid=request.getParameter("flowdraftid");	
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		TbprisonerBaseinfo baseInfo = prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(crimid);
		if(!StringNumberUtil.isNullOrEmpty(flowdraftid)){
			String docConent =  flowBaseService.getDocConentByFlowdraftId(flowdraftid);
			docconent.add(docConent);
			request.setAttribute("flowdraftid", flowdraftid);
		}else{
			Map<String,Object> map = new HashMap<String,Object>();
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
			if (template != null) {
				docconent.add(template.getContent());
			}
			if(!StringNumberUtil.isNullOrEmpty(baseInfo)){
				map.put("name", baseInfo.getName());
				map.put("age", DateUtil.getAge(baseInfo.getBirthday()));
			}
			if(!StringNumberUtil.isNullOrEmpty(baseCrime)){
				String xingqi = baseCrime.getPunishmentyear()+","+baseCrime.getPunishmentmonth()+","+baseCrime.getPunishmentday();
				String orginalsentence = StringNumberUtil.getXingqi(baseCrime.getPunishmenttype(), xingqi);
				orginalsentence = orginalsentence.replace(GkzxCommon.XINGQI_SIHUAN_ZH, GkzxCommon.XINGQI_SIHUAN_ZH);
				map.put("gaizaobiaoxian", baseCrime.getCrimid());
				map.put("orgname", baseCrime.getOrgname1());
				map.put("maincase", baseCrime.getMaincase());
				map.put("orginalsentence", orginalsentence);
				map.put("punishment", orginalsentence);
				map.put("rewardcrimid", baseCrime.getCrimid());
				map.put("maincase", baseCrime.getMaincase());
			}
			map.put("orgname", user.getOrganization().getFullname());
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		if(!StringNumberUtil.isNullOrEmpty(baseInfo)){
			request.setAttribute("orgid", baseCrime.getOrgid1());
			request.setAttribute("applyid", crimid);
			request.setAttribute("applyname",baseInfo.getName());
		}else{
			request.setAttribute("applyid", flowBaseService.getSeqBySeqname("seq_doc_rjsytzsp"));
			request.setAttribute("orgid",user.getOrgid());
		}
		
		SystemOrganization provinceOrg = systemOrganizationService.getProvinceCode(user.getOrgid());
		Map<String, Object> selectmap = new HashMap<String, Object>();
		Map<String, Object> codeConditionMap = new HashMap<String, Object>();
		codeConditionMap.put("codetype", codetype);
		codeConditionMap.put("pcodeid", pcodeid);
		codeConditionMap.put("orgpid", provinceOrg.getOrgid());
		selectmap.put("rewardtype", systemLocalcodeService.getLocalcodeUserbytemp(codeConditionMap));
		
		request.setAttribute("tempid", tempid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/khjc/rewardPunishsPointsAdd");
	}

	//奖扣分罪犯选择页面
	@RequestMapping("/batchRewardPunishlPointsCrimSelect")
	public ModelAndView batchRewardPunishlPointsCrimSelect(){
		return new ModelAndView("khjc/zxjf/batchRewardPunishlPointsCrimSelect");
	}
	
	//奖扣分编辑页
	@RequestMapping("/rewardPunishsPointsEdit")
	public ModelAndView rewardPunishsPointsEdit(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String resultval = "khjc/zxjf/rewardPunishsPointsEdit";
		ModelAndView mav = new ModelAndView(resultval, "yjmap", map);
		return mav;
	}
	
	//奖惩条款
	@RequestMapping(value = "/ajaxRewardPunishRules")
	@ResponseBody
	public Object ajaxRewardPunishRules(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		String key = request.getParameter("key");
		Map<String,Object> map = new HashMap<String,Object>();
		//map.put("orgid", user.getDepartid());
		String porganization = systemOrganizationService.getProvinceCode(user.getOrgid()).getOrgid();
		map.put("porgid", porganization);
		map.put("key", key);
		List<Map<String,Object>> list = rewardPunishPointsService.ajaxRewardPunishRules(map);
		return list;
	}
	//奖惩条款详细信息
	@RequestMapping(value = "/getRewardPunishRulesDetail")
	@ResponseBody
	public Object getRewardPunishRulesDetail(HttpServletRequest request) {
		Object returnVal = "";
		SystemUser user = getLoginUser(request);
		String ruleid = request.getParameter("ruleid");
		Map<String,Object> conditionMap = new HashMap<String,Object>();
		//map.put("orgid", user.getDepartid());
		String porganization = systemOrganizationService.getProvinceCode(user.getOrgid()).getOrgid();
		conditionMap.put("porgid", porganization);
		conditionMap.put("ruleid", ruleid);
		Map<String,Object> returnMap = rewardPunishPointsService.ajaxRewardPunishRulesDetail(conditionMap);
		if(DataBaseDateTransfer.ToBigDecimal(returnMap.get("isleaf")).intValue()==1 || DataBaseDateTransfer.ToBigDecimal(returnMap.get("isprovisions")).intValue()==1) {
			returnVal = returnMap;
		}
		return returnVal;
	}
	
	//处罚意见编辑页面
	@RequestMapping(value = "/punsuggestSelect")
	public ModelAndView punsuggestSelect(HttpServletRequest request) {
		return new ModelAndView("khjc/zxjf/punsuggestSelect");
	}
	
	
	//批量奖励罪犯选择页
	@RequestMapping("/batchRewardApprovalCrimSelect")
	public ModelAndView batchRewardApprovalCrimSelect(HttpServletRequest request,HttpServletResponse response){
		String selected = request.getParameter("selected")==null?"":request.getParameter("selected");
		String sortval = request.getParameter("sortval")==null?"":request.getParameter("sortval");
		request.setAttribute("selected", selected);
		request.setAttribute("sortval", sortval);
		return new ModelAndView("khjc/zxjf/batchRewardApprovalCrimSelect");
	}
	//批量奖励罪犯选择页数据
	@RequestMapping(value = "/seasonPointsSortList")
	@ResponseBody
	public Object seasonPointsSortList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		Calendar calendar = Calendar.getInstance();
		int cyear = calendar.get(Calendar.YEAR);
	    int cmonth = calendar.get(Calendar.MONTH) + 1;
		try {
			//取得参数
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String selectorgid = request.getParameter("selectorgid")==null?"":request.getParameter("selectorgid");
			String selected = request.getParameter("selected")==null?"":request.getParameter("selected");
			String selectedVal = "";
			if(!StringNumberUtil.isNullOrEmpty(selected)) {
				String[] selectedArr = selected.split(",");
				for(int i=0; i<selectedArr.length; i++) {
					selectedVal = selectedVal + "'" + selectedArr[i] + "'";
					if(i<selectedArr.length-1) {
						selectedVal = selectedVal + ",";
					}
				}
			}
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
			if(!StringNumberUtil.isNullOrEmpty(selectorgid)) {
				map.put("orgid", selectorgid);
			} else {
				map.put("orgid", getLoginUser(request).getOrgid());
			}
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("cyear", cyear);
	    	map.put("cmonth", cmonth);
	    	map.put("selected", selectedVal);
	    	int count = rewardMonthlySummaryService.getRewardMonthlySummaryCount(map);
	    	data= MapUtil.convertKeyList2LowerCase(rewardMonthlySummaryService.getRewardMonthlySummaryList(map));
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;		
	}
	
}

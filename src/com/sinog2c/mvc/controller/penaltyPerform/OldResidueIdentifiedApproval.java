package com.sinog2c.mvc.controller.penaltyPerform;

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
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * @author kexz
 *老病残认定审批
 * 2014-7-18
 */
@Controller
public class OldResidueIdentifiedApproval extends ControllerBase{
	@Resource
	protected FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private FlowBaseService flowBaseService;
	
	
	/**
	 * 老病残认定罪犯处理页面
	 * @author wangxf
	 */
	@RequestMapping("/criminalProveOldChoose")
	public ModelAndView criminalProveOldChoose(HttpServletRequest request){
		returnResourceMap(request);
		String menuid=request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		return new ModelAndView(new InternalResourceView(
								"/WEB-INF/JSP/chooseCriminal/criminalProveOldChoose.jsp"));
	}
//	/**
//	 * 罪犯处理列表页数据
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/getOldCriminalList")
//	@ResponseBody
//	public Object getOldCriminalList(HttpServletRequest request,
//			HttpServletResponse response){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			//取得参数
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
//			map.put("orgid", getLoginUser(request).getOrgid());
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
	/**
	 * 被处理罪犯的数据列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/criminalProveOld")
	public ModelAndView oldResidueIdentifiedApproval(HttpServletRequest request){
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String idname = request.getParameter("idname")==null?"":request.getParameter("idname");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String menuid = request.getParameter("menuid")==null?"":request.getParameter("menuid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
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
		return new ModelAndView("penaltyPerform/criminalProveOld");
	}
	/**
	 * 方法描述：列表页面
	 * 
	 * @author 
	 */
	@RequestMapping("/ajaxGetzflbcrdspList")
	@ResponseBody
	public Object ajaxGetzflbcrdspList(HttpServletRequest request) {
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
			map.put("flowdefid", "other_zflbcrdsp");
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
	/**
	 * 添加老年犯表单
	 * @param request
	 * @return 添加老犯表单页面
	 * @author wangxf
	 */
	@RequestMapping(value = "criminalProveOldAdd")
	public ModelAndView criminalProveOldAdd(HttpServletRequest request) {
		returnResourceMap(request);
		String crimid=request.getParameter("crimid");
		String menuid=request.getParameter("menuid");
		String tempid = request.getParameter("tempid");
		// 获取当前登录的用户
		SystemUser user = getLoginUser(request);
		JSONArray docconent = new JSONArray();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
		if (template != null) {
			docconent.add(template.getContent());
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		TbprisonerBaseinfo baseInfo = prisonerService.getBasicInfoByCrimid(crimid);//罪犯基本信息
		TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(crimid);//罪犯处罚信息
		map.put("cbiname", baseInfo.getName());
		map.put("cbigendername", baseInfo.getGender());
		map.put("cbinationname", baseInfo.getNation());
		map.put("cbinativename", baseInfo.getOrigin());
		map.put("age", DateUtil.getAge(baseInfo.getBirthday()));
		map.put("zuiminghuizong", baseCrime.getCauseaction());
		// 判断
		if(GkzxCommon.XINGQI_YOUQI.equals(baseCrime.getPunishmenttype()) || GkzxCommon.XINGQI_YOUQI_ZH.equals(baseCrime.getPunishmenttype())){
			String strPunishment = StringNumberUtil.isNullOrEmpty(baseCrime.getPunishmentyear())?"":GkzxCommon.ZERO.equals(baseCrime.getPunishmentyear().toString())?"":StringNumberUtil.digit2speech(baseCrime.getPunishmentyear().toString()) + "年";
			strPunishment += StringNumberUtil.isNullOrEmpty(baseCrime.getPunishmentmonth())?"":GkzxCommon.ZERO.equals(baseCrime.getPunishmentmonth().toString())?"":StringNumberUtil.digit2speech(baseCrime.getPunishmentmonth().toString()) + "个月";
			strPunishment += StringNumberUtil.isNullOrEmpty(baseCrime.getPunishmentday())?"":GkzxCommon.ZERO.equals(baseCrime.getPunishmentday().toString())?"":StringNumberUtil.digit2speech(baseCrime.getPunishmentday().toString()) + "天";
			if (!StringNumberUtil.isNullOrEmpty(strPunishment)) {
				map.put("zhuxing", GkzxCommon.XINGQI_YOUQI_ZH + strPunishment);// 刑期
			}
		}else if(GkzxCommon.XINGQI_SIHUAN.equals(baseCrime.getPunishmenttype()) ||GkzxCommon.XINGQI_SIHUAN_ZH.equals( baseCrime.getPunishmenttype())){
			map.put("zhuxing", GkzxCommon.XINGQI_SIHUAN_ZH);
		}else if(GkzxCommon.XINGQI_WUQI.equals(baseCrime.getPunishmenttype()) || GkzxCommon.XINGQI_WUQI_ZH.equals(baseCrime.getPunishmenttype())){
			map.put("zhuxing", GkzxCommon.XINGQI_WUQI_ZH);
		}else {
			map.put("zhuxing", "");
		}
		map.put("orgid1", baseCrime.getOrgid1());
		String st=DateUtil.dateFormat(baseCrime.getSentenceetime());
		String et=DateUtil.dateFormat(baseCrime.getSentencestime());
		if (!StringNumberUtil.isNullOrEmpty(st) && !StringNumberUtil.isNullOrEmpty(et))
			map.put("xingqiqizhi", "自"+et+"起至"+st+"止");
		map.put("yuanpanxingqi", baseCrime.getRemark());//刑期
		map.put("anyouhuizong",baseCrime.getCharge());//罪名汇总
		map.put("yuanpanxingzhong","asdfasdfsdf");//原判刑种
		map.put("fanzuishishi", baseCrime.getCrimeface());//犯罪事实
		map.put("text1", user.getOrganization().getFullname()+"老病残犯审批表");
		request.setAttribute("tempid", tempid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("applyid", crimid);
		request.setAttribute("flowdefid", "other_zflbcrdsp");
		request.setAttribute("applyname", map.get("cbiname"));
		request.setAttribute("orgid", map.get("orgid1"));
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());		
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/aip/criminalProveDiseaseAdd.jsp"));
	}
	
	/**
	 * 查看、修改收监审批时，根据文书Id查询出大字段回显
	 * @author 
	 */
	@RequestMapping( "/criminalProveOldEdit")
	public ModelAndView editorlook(HttpServletRequest request) {
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
		JSONArray docconent = new JSONArray();		
		String flowdraftid=request.getParameter("flowdraftid");	
		request.setAttribute("flowdraftid", flowdraftid);
		String baseDoc= flowBaseService.getDocConentByFlowdraftId(flowdraftid);
		if (baseDoc != null) {
			docconent.add(baseDoc);
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/criminalProveDiseaseAdd");
	}	
	
}

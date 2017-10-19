package com.sinog2c.mvc.controller.outexecute;

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
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.util.common.DateUtil;

/**
 * 保外就医不计入刑期审批表
 * @author Administrator
 *
 */
@Controller
public class NotIncludedApproval extends ControllerBase {
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Resource
	private PrisonerService prisonerService;
	
	
	/**
	 * 罪犯选择页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/NotIncludedApprovalChoose")
	public  ModelAndView NotIncludedApprovalChoose(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/NotIncludedApprovalChoose");
	}
//	/**
//	 * 获取罪犯
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/getNotIncludedApprovalChoose")
//	@ResponseBody
//	public Object getNotIncludedApprovalChoose(HttpServletRequest request,
//			HttpServletResponse response){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
//		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");//取得参数
//			//分页
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
//			//字段排序
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("key", key);
//			map.put("orgid", getLoginUser(request).getOrgid());
//			map.put("tempid", "SDXF_BWJYBJRXQSPB");
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
	 * 文档列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/NotIncludedApprovalChooseCrim")
	@ResponseBody
	public Object NotIncludedApprovalChooseCrim(HttpServletRequest request){
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
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("orgid", getLoginUser(request).getOrgid());
	    	map.put("sortField", sortField);
	    	map.put("applyid", crimid);
	    	map.put("type", "1");
			map.put("flowdefid", "other_bjrxqspb");
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	List<FlowBase> list = flowBaseService.getBaseListByCondition(map);
			int count = flowBaseService.countAllByCondition(map);
	    	resultmap.put("data", list);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	@RequestMapping("/NotIncludedApproval")
	public ModelAndView conditionDiagnoseBook(HttpServletRequest request){
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
		return new ModelAndView("outexecute/NotIncludedApproval");
	}

	@RequestMapping(value = "NotIncludedApprovalAdd")
	public ModelAndView conditionDiagnoseBookAdd(HttpServletRequest request) {

		returnResourceMap(request);
		// 获取当前登录的用户
		SystemUser user = getLoginUser(request);
		String userId = user.getDepartid();
		String tempid = "SDXF_BWJYBJRXQSPB";
		String criminalid = request.getParameter("crimid");
		JSONArray docconent = new JSONArray();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, userId);
		if (template != null) {
			docconent.add(template.getContent());
		}
		Map<String,Object> map = new HashMap<String,Object>();
		TbprisonerBaseinfo baseInfo = prisonerService.getBasicInfoByCrimid(criminalid);
		TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(criminalid);
		Map<String,Object> tbxfMap = tbxfSentencealterationService.selectTbxfByCrimid(criminalid);
		map.put("cbigendername", baseInfo.getGender());
		map.put("cbiname", baseInfo.getName());
		map.put("cbibirthday", DateUtil.dateFormatForAip(baseInfo.getBirthday()));
		map.put("cbinationname", baseInfo.getNation());
		map.put("jiatingzhuzhi", baseInfo.getFamilyaddress());
		
		if(null!=tbxfMap){
			String xianxingqiqizhi = "";
			Object courtchangefromObj = tbxfMap.get("COURTCHANGEFROM");
			Object courtchangetoObj = tbxfMap.get("COURTCHANGETO");
			if(null!=courtchangefromObj){
				xianxingqiqizhi +="从"+DateUtil.dateFormat(courtchangefromObj)+"起";
			}
			if(null!=courtchangetoObj){
				xianxingqiqizhi +="   至"+DateUtil.dateFormat(courtchangetoObj)+"止";
			}
			map.put("xingqiqizhi", xianxingqiqizhi);
		}
		if(baseCrime!=null){
			map.put("fujiaxing", baseCrime.getSanremark());
			map.put("zhuxing", baseCrime.getRemark());
			map.put("anyouhuizong",baseCrime.getCauseaction());
			map.put("yuanpanxingzhong", baseCrime.getPunishmenttype());
			if(baseCrime.getSentencestime()!=null){
				map.put("yuanpanxingqiqizhi", DateUtil.dateFormat(baseCrime.getSentencestime(),"yyyy年MM月dd日")+"至"+DateUtil.dateFormat(baseCrime.getSentenceetime(), "yyyy年MM月dd日")+"止");
			}
		}
		request.setAttribute("tempid", tempid);
		request.setAttribute("flowdefid", "other_bjrxqspb");
		request.setAttribute("orgid", baseCrime.getOrgid1());
		request.setAttribute("applyid", criminalid);
		request.setAttribute("applyname",baseInfo.getName());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());		
		return new ModelAndView("aip/NotIncludedApprovalAdd");
	}
	
	/**
	 * 修改/查看
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toNotIncludedApproval")
	public ModelAndView toregistrationLook(HttpServletRequest request) {
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
		return new ModelAndView("aip/NotIncludedApprovalAdd");
	}
	
}

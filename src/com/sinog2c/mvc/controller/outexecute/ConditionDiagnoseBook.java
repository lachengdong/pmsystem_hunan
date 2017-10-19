package com.sinog2c.mvc.controller.outexecute;

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
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 类描述：病情诊断
 * @author wangxf
 */

@Controller
public class ConditionDiagnoseBook extends ControllerBase {
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
	 * 方法描述：打开罪犯选择页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/conditionDiagnoseBookChoose")
	public  ModelAndView conditionDiagnoseBookChoose(HttpServletRequest request){
		return new ModelAndView("chooseCriminal/conditionDiagnoseBookChoose");
	}
	/**
	 * 获取罪犯数据列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getBasicInfoChoose")
	@ResponseBody
	public Object getBasicInfoChoose(HttpServletRequest request,HttpServletResponse response){
		SystemUser user = getLoginUser(request);
		Map<String, Object> resultmap = new HashMap<String,Object>();
		
		Map<String,Object> map = this.parseParamMap(request);
		map = this.parsePageInfoOfMap(map);
		
//		map.put("flowdefid", "doc_bwbqzdsp");
		map.put("orgid", user.getOrgid());
		
    	int count = chooseCriminalService.countAllByCondition(map);
    	List<Map<String,Object>> data = chooseCriminalService.getBasicInfoList(map);
    	resultmap.put("data", data);
		resultmap.put("total", count);
		
		return resultmap;
	}
	
	
	/**
	 * 方法描述：获取罪犯的病情诊断数据列表页数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/conditionDiagnoseBookList")
	@ResponseBody
	public Object conditionDiagnoseBookList(HttpServletRequest request,HttpServletResponse response){
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
			map.put("flowdefid", "doc_bwbqzdsp");
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
	/*
	 * 方法描述：跳转到罪犯的病情诊断数据列表页
	 */
	@RequestMapping("/conditionDiagnoseBook")
	public ModelAndView conditionDiagnoseBook(HttpServletRequest request){
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
		return new ModelAndView("outexecute/conditionDiagnoseBook");
	}
	/*
	 * 表单新增页面
	 */
	@RequestMapping(value = "conditionDiagnoseBookAdd")
	public ModelAndView conditionDiagnoseBookAdd(HttpServletRequest request) {
		returnResourceMap(request);
		// 获取当前登录的用户
		SystemUser user = getLoginUser(request);
		String userId = user.getDepartid();
		String tempid = "JWZX_ZFBQZDS";
		String crimid = request.getParameter("crimid");
		JSONArray docconent = new JSONArray();
		Map<String,Object> map = new HashMap<String,Object>();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, userId);
		if (template != null) {
			docconent.add(template.getContent());
		}
		TbprisonerBaseinfo baseInfo = prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(crimid);
		Map<String,Object> tbxfMap = tbxfSentencealterationService.selectTbxfByCrimid(crimid);
		map.put("cbiname", baseInfo.getName());
		map.put("xingbie", baseInfo.getGender());
		map.put("minzu", baseInfo.getNation());
		map.put("jiatingzhuzhi", baseInfo.getFamilyaddress());
		map.put("cbibirthday", DateUtil.dateFormatForAip(baseInfo.getBirthday()));
		map.put("education", baseInfo.getEducation());
		map.put("text19", user.getPrison().getCity());
		if(null!=tbxfMap){
			String xianxingqiqizhi = "";
			Object courtchangefromObj = tbxfMap.get("COURTCHANGEFROM");
			Object courtchangetoObj = tbxfMap.get("COURTCHANGETO");
			String prisonterm = "";
			if (!StringNumberUtil.isNullOrEmpty(tbxfMap.get("SENTENCECHAGEINFO"))){
				prisonterm = StringNumberUtil.getTrimRtnStr(tbxfMap.get("SENTENCECHAGEINFO").toString().replace("，。", "。").replace("，；", "；"));
			}
			if(null!=courtchangefromObj){
				xianxingqiqizhi +=GkzxCommon.NX_ZI+DateUtil.dateFormat(courtchangefromObj)+GkzxCommon.NX_QI;
			}
			if(null!=courtchangetoObj){
				xianxingqiqizhi +=GkzxCommon.NX_ZHIO+DateUtil.dateFormat(courtchangetoObj)+GkzxCommon.NX_ZHIT;
			}
			if(null==xianxingqiqizhi||"".equals(xianxingqiqizhi)){
				if(null!=baseCrime.getSentenceetime()){
					xianxingqiqizhi=GkzxCommon.NX_ZI+DateUtil.dateFormat(baseCrime.getSentencestime())+GkzxCommon.NX_QI;
				}
				if(null!=baseCrime.getSentencestime()){
					xianxingqiqizhi+=GkzxCommon.NX_ZHIO+DateUtil.dateFormat(baseCrime.getSentenceetime())+GkzxCommon.NX_ZHIT;
				}
			}
			map.put("xianxingqiqizhi", xianxingqiqizhi);
			map.put("xingqibiandong", prisonterm);
		}
		if(baseCrime!=null){
			map.put("fujiaxing", baseCrime.getSanremark());
			map.put("zuiming",baseCrime.getMaincase());
			map.put("yuanpanxingzhong", baseCrime.getPunishmenttype());
			map.put("yuanpanxingqi", baseCrime.getRemark());
			if(baseCrime.getSentencestime()!=null){
				map.put("yuanpanxingqiqizhi", DateUtil.dateFormat(baseCrime.getSentencestime())+
						GkzxCommon.NX_ZHIO+DateUtil.dateFormat(baseCrime.getSentenceetime())+GkzxCommon.NX_ZHIT);
			}
		}
		request.setAttribute("flowdefid", "doc_bwbqzdsp");
		request.setAttribute("orgid", baseCrime.getOrgid1());
		request.setAttribute("applyid", crimid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("applyname",baseInfo.getName());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());		
		return new ModelAndView("aip/conditionDiagnoseBookAdd");
	}
	
	/**
	 * 修改/查看
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toConditionDiagnoseBook")
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
		ModelAndView mav = new ModelAndView("aip/conditionDiagnoseBookAdd");
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

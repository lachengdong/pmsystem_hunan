package com.sinog2c.mvc.controller.publicMain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/** 
 * 功能描述：建议书生成及保存
 * @author liuchaoyang
 * 上午10:44:08
 */
@Controller
public class RecommendationController extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Resource
	public SystemLogService logService;
	@Resource
	public SystemTemplateService systemTemplateService;
	@Resource
	public UvFlowService uvFlowService;
	@Resource
	public FlowBaseService flowBaseService;
	
	/**
	 * 根据模版编号查询出文书内容、查询方案。根据查询方案id查询数据，返回到某一个字段显示出来
	 * 获取建议书
	 * @author liuchaoyang
	 */
	@RequestMapping(value = "/getRecommendationContent")
	@ResponseBody
	public HashMap<String, String> getRecommendationContent(String tempid,HttpServletRequest request){
		SystemUser user = getLoginUser(request);
        HashMap<String, String> map = new HashMap<String, String>();
        String content = systemModelService.getRecommendationContent(tempid,user, request);
		map.put("annexcontent", content);
		return map;
	}
	/**
	 * 跳转到减刑、假释建议书显示页面
	 * @author liuchaoyang
	 * 2014-8-05 10:07:45
	 * @return 
	 */
	@RequestMapping(value = "/commutationParoleSuggestion")
	public ModelAndView commutationParoleSuggestion(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		String flowdraftid = request.getParameter("flowdraftid");
		String menuid = request.getParameter("menuid");
		String flowid = request.getParameter("flowid");
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("flowid", flowid);
		
		
		return new ModelAndView("commutationParole/suggestDocumentsPage");
	}
	
	/**
	 * 跳转到减刑、假释建议书显示页面
	 * 保外
	 * @return 
	 */
	@RequestMapping(value = "/toSuggestionDocPage")
	public ModelAndView toSuggestionDocPage(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		String modeledit = request.getParameter("modeledit");
		String id = request.getParameter("id");
		String menuid = request.getParameter("menuid");
		String flowdraftid = request.getParameter("flowdraftid");
		String flowid = request.getParameter("flowid");
		String flowdefid = request.getParameter("flowdefid");
		String doctype = request.getParameter("doctype");
		String tempid = request.getParameter("tempid");
		String resid = request.getParameter("resid");
		if(crimid==null || "".equals(crimid)){
			String[] ids = id.split(",");
			crimid = ids[0];
			//将这个复合编号解析成数组，第一个是罪犯编号，第二个是流程草稿Id
			ids = crimid.split("@");
			crimid = ids[1];
			if(ids.length>1) flowdraftid = ids[0];
		}
		request.setAttribute("crimid", crimid);
		request.setAttribute("id", id);
		request.setAttribute("menuid", menuid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("modeledit", modeledit);
		

		if(StringNumberUtil.notEmpty(tempid)){
			request.setAttribute("tempid", tempid);
		}
		request.setAttribute("resid", resid);

		if(StringNumberUtil.notEmpty(doctype)){
			request.setAttribute("doctype", doctype);
			if("jailcommute".equals(doctype.trim()) || "provincecommute".equals(doctype.trim())){//监狱建议书
				Map<String, String> paraMap = new HashMap<String, String>();
				paraMap.put("crimid", crimid);
				paraMap.put("flowdefid", flowdefid);
				Map temMap = uvFlowService.getPrisonerParoleType(paraMap);
				if(null!=temMap){
					String paroletype = temMap.get("paroletype")==null?"":temMap.get("paroletype").toString().trim();//减假暂类型
					String nowpunishmentyear = temMap.get("nowpunishmentyear")==null?"":temMap.get("nowpunishmentyear").toString();//有期、无期或死缓
					request.setAttribute("paroletype", paroletype);
					request.setAttribute("nowpunishmentyear", nowpunishmentyear);
				}
			}
		}
		if ("casecheck".equals(doctype)) {
			return new ModelAndView("commutationParole/checkCourtDocPage");
		}else {
			return new ModelAndView("commutationParole/suggestionDocPage");
		}
		
	}
	
	
	/**
	 * 跳转到减刑、假释建议书显示页面
	 * 保外
	 * @return 
	 */
	@RequestMapping(value = "/toSuggestionDocPageNew")
	public ModelAndView toSuggestionDocPageNew(HttpServletRequest request) {
		String modeledit = request.getParameter("modeledit");
		String menuid = request.getParameter("menuid");
		String flowdraftid = request.getParameter("id");
		String flowdefid = request.getParameter("flowdefid");
		String doctype = request.getParameter("doctype");
		String tempid = request.getParameter("tempid");
		String resid = request.getParameter("resid");
		Map paraMap = new HashMap();
		paraMap.put("flowdraftid", (flowdraftid));
		FlowBase flowBase = flowBaseService.getFlowBaseByFlowdraftid(paraMap);
		String crimid = flowBase.getApplyid();
		String flowid = flowBase.getFlowid();
		request.setAttribute("crimid", crimid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("modeledit", modeledit);
		
		if(StringNumberUtil.notEmpty(tempid)){
			request.setAttribute("tempid", tempid);
		}
		request.setAttribute("resid", resid);

		if(StringNumberUtil.notEmpty(doctype)){
			request.setAttribute("doctype", doctype);
			if("jailcommute".equals(doctype.trim()) || "provincecommute".equals(doctype.trim())){//监狱建议书
				paraMap.put("crimid", crimid);
				paraMap.put("flowdefid", flowdefid);
				Map temMap = uvFlowService.getPrisonerParoleType(paraMap);
				if(null!=temMap){
					String paroletype = temMap.get("paroletype")==null?"":temMap.get("paroletype").toString().trim();//减假暂类型
					String nowpunishmentyear = temMap.get("nowpunishmentyear")==null?"":temMap.get("nowpunishmentyear").toString();//有期、无期或死缓
					request.setAttribute("paroletype", paroletype);
					request.setAttribute("nowpunishmentyear", nowpunishmentyear);
				}
			}
		}
		if ("casecheck".equals(doctype)) {
			return new ModelAndView("commutationParole/checkCourtDocPage");
		}else {
			return new ModelAndView("commutationParole/suggestionDocPage");
		}
		
	}
	
	/**
	 * 跳转到减刑、假释建议书显示页面
	 * 保外
	 * @return 
	 */
	@RequestMapping(value = "/toSuggestionDocPage_nx")
	public ModelAndView toSuggestionDocPage_nx(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		String modeledit = request.getParameter("modeledit");
		String id = request.getParameter("id");
		String menuid = request.getParameter("menuid");
		String flowdraftid = request.getParameter("flowdraftid");
		String flowid = request.getParameter("flowid");
		String flowdefid = request.getParameter("flowdefid");
		String doctype = request.getParameter("doctype");
		String tempid = request.getParameter("tempid");
		String resid = request.getParameter("resid");
		//宁夏  跳转到 自己使用的页面当中
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ningxia = jyconfig.getProperty(GkzxCommon.NINGXIA);
		
		if(crimid==null || "".equals(crimid)){
			String[] ids = id.split(",");
			crimid = ids[0];
			//将这个复合编号解析成数组，第一个是罪犯编号，第二个是流程草稿Id
			ids = crimid.split("@");
			crimid = ids[1];
			if(ids.length>1) flowdraftid = ids[0];
		}
		request.setAttribute("crimid", crimid);
		request.setAttribute("id", id);
		request.setAttribute("menuid", menuid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("modeledit", modeledit);
		
		if(StringNumberUtil.notEmpty(tempid)){
			request.setAttribute("tempid", tempid);
		}
		request.setAttribute("resid", resid);
		if(StringNumberUtil.notEmpty(resid)){
			request.setAttribute("resid", resid);
		}
		if(StringNumberUtil.notEmpty(doctype)){
			request.setAttribute("doctype", doctype);
			if("jailcommute".equals(doctype.trim()) || "provincecommute".equals(doctype.trim())){//监狱建议书
				Map<String, String> paraMap = new HashMap<String, String>();
				paraMap.put("crimid", crimid);
				paraMap.put("flowdefid", flowdefid);
				Map temMap = uvFlowService.getPrisonerParoleType(paraMap);
				if(null!=temMap){
					String paroletype = temMap.get("paroletype")==null?"":temMap.get("paroletype").toString().trim();//减假暂类型
					String nowpunishmentyear = temMap.get("nowpunishmentyear")==null?"":temMap.get("nowpunishmentyear").toString();//有期、无期或死缓
					request.setAttribute("paroletype", paroletype);
					request.setAttribute("nowpunishmentyear", nowpunishmentyear);
				}
			}
		}
		if ("casecheck".equals(doctype)) {
			return new ModelAndView("commutationParole/checkCourtDocPage");
		}else {
			if (ningxia.contains(getLoginUser(request).getDepartid())) {
				return new ModelAndView("commutationParole/suggestionDocPage_nx");
			}else{
				return new ModelAndView("commutationParole/suggestionDocPage");
			}
		}
		
	}
	
	/**
	 * 跳转到减刑、假释建议书显示页面（表单）
	 * @author liuchaoyang
	 * 2014-8-05 10:07:45
	 * @return 
	 */
	@RequestMapping(value = "/commutationParoleSuggestionAppro")
	public ModelAndView commutationParoleSuggestionAppro(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		String flowdraftid = request.getParameter("flowdraftid");
		String flowid = request.getParameter("flowid");
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("flowid", flowid);
		
		return new ModelAndView("commutationParole/suggestDocumentsPageAppro");
	}
	
	
	/**
	 * 跳转到呈批表显示页面
	 * @return 
	 */
	@RequestMapping(value = "/toChengPiReportPage.page")
	public ModelAndView toChengPiReportPage(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		String flowdraftid = request.getParameter("flowdraftid");
		String menuid = request.getParameter("menuid");
		String flowid = request.getParameter("flowid");
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("flowid", flowid);
		
		return new ModelAndView("commutationParole/chengPiReportPage");
	}
	
	/**
	 * 获取系统模板选择下拉列表
	 * @return 
	 */
	@RequestMapping(value = "/getDocModelSelectList")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getDocModelSelectList(HttpServletRequest request) {
		SystemUser su = this.getLoginUser(request);
		SystemOrganization so = systemOrganizationService.getByOrganizationId(su.getDepartid());
		
		String crimid = request.getParameter("crimid");
		String flowdraftid = request.getParameter("flowdraftid");
		String menuid = request.getParameter("menuid");
		String flowid = request.getParameter("flowid");
		String doctype = request.getParameter("doctype");
		//String tempid = request.getParameter("tempid");
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("flowid", flowid);
		Properties jyconfig = new GetProperty().bornProp(
				GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode") == null ? ""
				: jyconfig.getProperty("provincecode"));
		Map<String, String> paramap = new HashMap<String, String>();
		if(doctype!=null && doctype!="") {
			if(doctype.equals("casecheck")) {//监督意见
				paramap.put("tempid", "'10080','10081'");
				paramap.put("departid", provincecode);
			}else if(doctype.equals("jailcommute")) {	//监狱减刑、假释建议书
				paramap.put("tempid", "'10020','10021','10022','10023','10024','10522'");
				paramap.put("departid", so.getPorgid());
			}else if(doctype.equals("provincecommute")) {//省局减刑建议书 '10026',
				paramap.put("tempid", "'10027','10028','10029'");
			}else if(doctype.equals("jailbaowai")) {
				paramap.put("tempid", "'9706'");
			}else if(doctype.equals("jailxubao")) {//续保建议书
				paramap.put("tempid", "'10025'");
			}else if(doctype.equals("courtBackupOpin")){//报备意见
				paramap.put("tempid", "'10162'");
			}else if(doctype.equals("courtSentence")){//法律文书
				paramap.put("tempid", "'10140','10144','10152','10153','10141','10154','10142','10143','10145','10146','10147','10149','10148','10150','10151'");
			}else if(doctype.equals("sjjds")){//省局决定书
				paramap.put("tempid", "'10428'");
			}
		}
		List<Map> list = systemTemplateService.getTemplateListByTempids(paramap);
		return list;
	}
	
	/**
	 * 跳转到保外就医建议书显示页面
	 * @author liuchaoyang
	 * 2014-8-07 11:30:45
	 * @return 
	 */
	@RequestMapping(value = "/medicalParoleRecommendation")
	public ModelAndView medicalParoleRecommendation(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		return new ModelAndView("aip/medicalParoleRecommendation");
	}
	
	
	
	
	
	
	/**
	 *   天津版本
	 *方法描述：跳转到减刑假释建议书表单页面
	 */
	@RequestMapping("/toTjRecommendation")
	public ModelAndView toTjRecommendation(HttpServletRequest request) throws Exception {
		String tempid = "TJ_JXJSJYS";//天津的建议书类型统一命名
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		JSONArray docconent = new JSONArray();
		TbsysDocument document = tbsysDocumentService.getTbsysDocument(null, tempid, crimid,null);
		if (document != null) {
			docconent.add(document.getContent());
			request.setAttribute("docid", document.getDocid());
		}
		returnResourceMap(request);
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("penaltyPerform/tianjin/TjRecommendationPage");
	}
	/**
	 *  天津版本
	 * 方法描述：减刑假释建议书类型改变时，获取对应模版和罪犯信息
	 */
	@RequestMapping("/getTjRecommendationContent")
	public ModelAndView getRecommendationForm(HttpServletRequest request) throws Exception {
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);//获取当前登录的用户
		String departid=user.getDepartid();//根据用户Id获取所在部门Id
		String  tempid = request.getParameter("tempid");
		String crimid = request.getParameter("crimid");
		String docid = request.getParameter("docid");
		request.setAttribute("docid", docid);
		request.setAttribute("crimid", crimid);
		Map<String, Object> selectmap = new HashMap<String, Object>();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
		if (template != null) {
			docconent.add(template.getContent());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		SystemOrganization org = systemOrganizationService.getByOrganizationId(user.getDepartid());
		TbprisonerBaseinfo tbprisonerBaseinfo = prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService.getCrimeByCrimid(crimid);
		Map<String,Object> tbxfMap = tbxfSentencealterationService.selectTbxfByCrimid(crimid);
		if (tbprisonerBaseinfo != null) {
			map.put("cbiname", tbprisonerBaseinfo.getName());
			map.put("xingbie", tbprisonerBaseinfo.getGender());
			map.put("birthday", DateUtil.dateFormatForAip(tbprisonerBaseinfo.getBirthday()));
			map.put("huji", tbprisonerBaseinfo.getRegisteraddressdetail());
			map.put("minzu", tbprisonerBaseinfo.getNation());
		}
		if(tbprisonerBaseCrime!=null){
			map.put("zhuanyou", tbprisonerBaseCrime.getMaincase());// 罪名（主案由）
			map.put("panjuefayuan", tbprisonerBaseCrime.getJudgmentname());// 判决机关
			map.put("panjueriqi", DateUtil
					.dateFormatForAip(tbprisonerBaseCrime.getJudgedate()));// 判决机关
			map.put("xingqi", tbprisonerBaseCrime.getRemark());//刑期
			map.put("fujiaxing", tbprisonerBaseCrime.getSanremark());//附加刑
			map.put("xingqiqizhi", "自"+DateUtil.dateFormatForAip(tbprisonerBaseCrime.getSentencestime())==null?"":sdf.format(tbprisonerBaseCrime.getSentencestime())
					+"至"+DateUtil.dateFormatForAip(tbprisonerBaseCrime.getSentenceetime())==null?"":sdf.format(tbprisonerBaseCrime.getSentenceetime()));
			map.put("rujianriqi", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getInprisondate()));//入监日期
		}
		if(tbxfMap!=null&&!tbxfMap.isEmpty()){
			map.put("xingqibiandongqingkuang",tbxfMap.get("SENTENCECHAGEINFO"));
			map.put("zi",tbxfMap.get("COURTSHORT")+"减");
			map.put("shenhehao",tbxfMap.get("COURTSN"));
			map.put("shenheriqi", DateUtil.dateFormatForAip(new Date()));
			map.put("panjuezi",tbxfMap.get("COURTSHORT")+"减");
			map.put("panjuehao",tbxfMap.get("COURTSN"));
		}
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/tianjin/TjRecommendationPage");
	}
	/**
	 *   天津版本
	 *方法描述：保存减刑假释建议书
	 */
	@RequestMapping("/saveTjRecommendationContent")
	@ResponseBody
	public int saveTjRecommendationContent(HttpServletRequest request) throws Exception {
		int countnum = 0;//保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date(); 
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		String tempid = "TJ_JXJSJYS";//天津的建议书类型统一命名
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String docid = request.getParameter("docid")==null?"":request.getParameter("docid");
		String introduction = crimid+"建议书";
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		if(docid==null || "".equals(docid)){
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction(introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		}else {
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
		}
		/*if(countnum == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}*/
		return countnum;
	}
	
	/**
	 * 获取系统模板选择下拉列表
	 * @return 
	 */
	@RequestMapping(value = "/getCourtSystemModelTree")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getCourtSystemModelTree(HttpServletRequest request) {
		SystemUser su = this.getLoginUser(request);
		Map<String,Object> map = new HashMap<String,Object>();
		String temptype = request.getParameter("temptype");
		map.put("temptype", temptype);
		map.put("departid", su.getDepartid());
		List<Map<String,Object>> list = systemTemplateService.getCourtSystemModelTree(map);
		return list;
	}
}

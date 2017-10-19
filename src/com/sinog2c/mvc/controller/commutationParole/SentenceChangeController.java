package com.sinog2c.mvc.controller.commutationParole;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.dao.api.commutationParole.TbxfCommuteParoleBatchMapper;
import com.sinog2c.dao.api.prisoner.TbPrisonerCaiChanXingMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerBaseCrimeMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerBaseinfoMapper;
import com.sinog2c.model.commutationParole.TbdataSentchage;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.prisoner.TbPrisonerCaiChanXing;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.CommuteParoleBatchService;
import com.sinog2c.service.api.commutationParole.SentenceAlterationHisService;
import com.sinog2c.service.api.commutationParole.TbdataSentchageService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbPrisonerCaiChanXingService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 刑期变动
 * @author hzl
 * 2014-7-20 15:04:27
 */
@Controller
public class SentenceChangeController extends ControllerBase {
	
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	@Resource
	private TbsysDocumentService sysDocumentService;
	@Resource
	private SentenceAlterationHisService sentenceAlterationHisService;
	@Resource
	private TbdataSentchageService tbdataSentchageService;
	String selectLX = "[[1]]原判||[[7]]减刑||[[4]]加刑||[[6]]漏罪加刑||[[20]]刑期顺延";
	@Resource
	private SystemCodeService systemCodeService;
	@Resource
	private TbprisonerBaseinfoMapper prisonerBaseinfoMapper;
	@Resource
	private FlowBaseService flowBaseService;
	@Resource 
	SystemOrganizationService sysOrganizationService;
	@Resource
	private TbxfCommuteParoleBatchMapper tbxfCommuteParoleBatchMapper;
	@Resource
	private TbPrisonerCaiChanXingService tbPrisonerCaiChanXingService;
	@Resource
	private SystemLogService systemLogService;
	@Resource
	private TbprisonerBaseCrimeMapper baseCrimeMapper;
	@Resource
	private TbPrisonerCaiChanXingMapper tbPrisonerCaiChanXingMapper;
	@Resource
	private CommuteParoleBatchService commuteParoleBatchService;
	
	/**
	 * 跳转刑期变动列表页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/sentenceChange.page")
	public ModelAndView sentenceChange(HttpServletRequest request){
		System.out.println(request.getParameter("_criminalid"));
		String criminalid = request.getParameter("_criminalid");
		
		if (criminalid!=null && !"undefined".equals(criminalid)) {
			request.setAttribute("_criminalid", criminalid);
		}
		ModelAndView mav =  new ModelAndView("chooseCriminal/sentenceChangechoose");
		return mav;
	}
	
	
	/**
	 * 跳转计分考核补录页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/jiFenBuLu.page")
	public ModelAndView jiFenBuLu(HttpServletRequest request){
		String criminalid = request.getParameter("_criminalid");
		
		if (criminalid!=null && !"undefined".equals(criminalid)) {
			request.setAttribute("_criminalid", criminalid);
		}
		ModelAndView mav =  new ModelAndView("chooseCriminal/jiFenBuLuChoose");
		return mav;
	}
	
	
	/**
	 * 跳转计分补录列表页
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toJiFenBuLuListPage.page")
	public ModelAndView toJiFenBuLuListPage(HttpServletRequest request) {
		returnResourceMap(request);
		String name = "";
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		if(StringNumberUtil.isEmpty(crimid)){
			crimid = request.getParameter("applyid");
		}
		if(StringNumberUtil.notEmpty(crimid)){
			TbprisonerBaseinfo  base = prisonerBaseinfoMapper.selectByPrimaryKey(crimid);
			if(base!=null) name = base.getName();
		}
		Map map = new HashMap<>();
		map.put("crimid",crimid);
		List list = tbdataSentchageService.getKaoHeZongFenByCrimid(map);
		if(list.size()>0){
			Map m = (Map)list.get(0);
		    request.setAttribute("kaohezongfen", m.get("SUMFEN"));
		    
		    request.setAttribute("leijibiaoyang", m.get("LEIJIBIAOYANG"));
		    request.setAttribute("yufen", m.get("YUFEN"));
			
		}
		
		String menuid=request.getParameter("menuid")==null?"":request.getParameter("menuid");
		String tempid=request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String closetype=request.getParameter("closetype")==null?"":request.getParameter("closetype");
		
		String toolbar = request.getParameter("toolbar");
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid",tempid);
		request.setAttribute("closetype",closetype);
		if(StringNumberUtil.notEmpty(toolbar)){
			request.setAttribute("toolbar", toolbar);
		}
		ModelAndView mav =  new ModelAndView("commutationParole/jiFenBuLuListPage");
		return mav;
	}
	
	
	/**
	 * 根据罪犯编号获取刑期变动列表
	 * @author 
	 */
	@RequestMapping(value = "/getJiFenBuLuByCrimid.json")
	@ResponseBody
	public HashMap<String, Object> getJiFenBuLuByCrimid(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		Map<String,Object> map = this.parseParamMap(request);
		map = this.parsePageInfoOfMap(map);
		
		int count = sentenceAlterationHisService.countJiFenBuLuOfCrimid(map);
		List<Map<String,Object>> list = sentenceAlterationHisService.getJiFenBuLuOfCrimid(map);
		//
		result.put("total", count);
		result.put("data", list);
		//
		return result;
	}
	
	
	
	/**
	 * 跳转计分补录新增页面
	 * @param request
	 * @return ModelAndView
	 */
	
	@RequestMapping(value = "/toJiFenBuLuAdd.page")
	public ModelAndView toJiFenBuLuAdd(HttpServletRequest request){
		//获取用户
		Map<String, Object> selectmap = new HashMap<String, Object>();
		selectmap.put("citchangetype", selectLX);
		TbsysTemplate template = null;
		SystemUser user = getLoginUser(request);
		String deptid=user.getDepartid();
		JSONArray docconent = new JSONArray();
		String id = request.getParameter("id")==null?"":request.getParameter("id");
		String menuid= request.getParameter("menuid")==null?"":request.getParameter("menuid");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String operator = request.getParameter("operator")==null?"":request.getParameter("operator");
		Map<String,Object> h = new HashMap<String,Object>();
		h.put("crimid", crimid);
		
		List<Map<String, Object>> lMaps = tbdataSentchageService.selectDataBycrimid(h);
		StringBuffer sb = new StringBuffer();
		if(lMaps!=null){
			for(Map<String, Object> m:lMaps){
				if(m.get("COURTSANCTION")!=null){
					sb.append(m.get("COURTSANCTION")+",");
				}
			}
			if(sb.length()>=1){
				String strs = sb.substring(0, sb.length()-1);
				request.setAttribute("strs", strs);
			}
		}
		
		template = systemModelService.getTemplateAndDepartid(tempid, deptid);
		if(template!=null){
			docconent.add(template.getContent());
		}
		
		List<HashMap<Object, Object>> list = prisonerService.getCrimeBaseInfo(crimid);
		Map<Object,Object> map = list.get(0);
		
		
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		Set<Object> set = map.keySet();
		for(Object m:set){
			jsonmap.put(((String) m).toLowerCase(), map.get(m));
		}
		
		Map<String,String> map1 = new HashMap<String,String>();
		map1.put("id",id);
		List<Map> jifenlist = tbdataSentchageService.getJiFenBuLuById(map1);
		if(jifenlist.size() > 0 ){
			Map jifenmap = jifenlist.get(0);
			Set<Object> set1 = jifenmap.keySet();
			for(Object m:set1){
				jsonmap.put(((String) m).toLowerCase(), jifenmap.get(m));
			}
		}
		
		jsonmap = StringNumberUtil.dealMapForForm(jsonmap);
		
        //获取所有的犯罪类型
		selectmap.put("charge",systemCodeService.getcodeValue("GB075"));
		
		//转json
		request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
		request.setAttribute("formDatajson", new JSONObject(jsonmap).toString());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("tempid", tempid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdefid","other_jfbl");
		request.setAttribute("menuid", menuid);
		request.setAttribute("operator", operator);
		ModelAndView mav =  new ModelAndView("aip/jiFenBuLuInfoOfCrim");
		return mav;
	}
	
	/**
	 * 修改/保存大字段
	 * @auhtor:mushuhong
	 */
	@RequestMapping(value = "/saveJiFenBuLu.action")
	@ResponseBody
	@Transactional
	@SuppressWarnings("all")
	public int saveJiFenBuLu(HttpServletRequest request){
		
		int countnum = 0;//保存结果：0、失败，1、成功
		Date date = new Date(); 
		SystemUser user = getLoginUser(request);
//		TbsysDocument document = new TbsysDocument();
		//获取当前登录的用户
		String deptid=user.getDepartid();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);
		String operator = request.getParameter("operator");
		String tempid = "XFZX_JFBL";
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String introduction = "罪犯计分补录登记表";
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		String data = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		
		String xfbduuid = request.getParameter("xfbduuid");//主键
		String text1 = request.getParameter("text1");
//		TbsysDocument tbsysDocument = sysDocumentService.selectByText1(text1);
		//集合
		List<Object> list = (ArrayList<Object>)JSON.parseArray(data, Object.class);
        Map<String,String> map = (Map<String, String>)list.get(0);
        map.put("username", user.getName());
        
        map = StringNumberUtil.dealMapForForm(map);
        
		if(GkzxCommon.NEW.equals(operator)){
//			document.setDepartid(org.getOrgid());
//			document.setTempid(tempid);
//			document.setIntroduction(introduction);
//			document.setCrimid(crimid);
//			document.setContent(content);
//			document.setOpid(user.getUserid());
//			document.setOptime(date);
//			countnum = sysDocumentService.saveTbsysDocument(document);
			tbdataSentchageService.insertJiFenBuLu(map);
		}else if(GkzxCommon.EDIT.equals(operator)){
//			document.setDepartid(org.getOrgid());
//			document.setTempid(tempid);
//			document.setIntroduction(introduction);
//			document.setCrimid(crimid);
//			document.setContent(content);
//			document.setOpid(user.getUserid());
//			document.setOptime(date);
//			if(tbsysDocument!=null){
//				sysDocumentService.deleteByText1(text1);
//				sysDocumentService.saveTbsysDocument(document);
//			}else{
//				sysDocumentService.saveTbsysDocument(document);
//			}
			//更新刑期变动表
			tbdataSentchageService.updateJiFenBuLuById(map);
		}
		
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("crimid", crimid);
		paramap.put("departid", user.getDepartid());
		commuteParoleBatchService.correctKaoHeDataInfo(paramap);
		
		
		return countnum;
	}
	
	
	@RequestMapping(value = "removeJiFenBuLu.json")
	@ResponseBody
	public Object removeJiFenBuLu(HttpServletRequest request){
		
		SystemUser user = this.getLoginUser(request);
		
		int flag = 0;
		String id = request.getParameter("id");//主键
		String crimid = request.getParameter("crimid");
		
		Map map = new HashMap<>();
		map.put("id",id);
		flag = tbdataSentchageService.removeJiFenBuLuById(map);//根据uuid删除刑期变动信息
		
		
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("crimid", crimid);
		paramap.put("departid", user.getDepartid());
		commuteParoleBatchService.correctKaoHeDataInfo(paramap);
		
		return flag;
	}
	
	/**
	 * 跳转刑期变动列表页
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toSentenceChangeListPage.page")
	public ModelAndView toSentenceChangeListPage(HttpServletRequest request) {
		returnResourceMap(request);
		String name = "";
		String crimid = this.getParameterString(request, "crimid", "");
		if(StringNumberUtil.isEmpty(crimid)){
			crimid = this.getParameterString(request, "applyid", "");
			if(StringNumberUtil.isEmpty(crimid)){
				String flowdraftid = this.getParameterString(request, "flowdraftid", "");
				if(StringNumberUtil.notEmpty(flowdraftid)){
					Map<String,Object> paramap = new HashMap<String,Object>();
					paramap.put("flowdraftid", flowdraftid);
					FlowBase fb = flowBaseService.getFlowBaseByMap(paramap);
					crimid = fb.getApplyid();
				}
			}
		}
		if(StringNumberUtil.notEmpty(crimid)){
			TbprisonerBaseinfo  base = prisonerBaseinfoMapper.selectByPrimaryKey(crimid);
			if(base!=null) {
				name = base.getName();
			}
		}
		
		String menuid = this.getParameterString(request, "menuid", "");
		String tempid = this.getParameterString(request, "tempid", "");
		String closetype = this.getParameterString(request, "closetype", "");
		String toolbar = this.getParameterString(request, "toolbar", "");
		
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("menuid", menuid);
		request.setAttribute("tempid",tempid);
		request.setAttribute("closetype",closetype);
		if(StringNumberUtil.notEmpty(toolbar)){
			request.setAttribute("toolbar", toolbar);
		}
		ModelAndView mav =  new ModelAndView("commutationParole/sentenceChangeListPage");
		return mav;
	}
	
	
	/**
	 * 根据罪犯编号获取刑期变动列表
	 * @author 
	 */
	@RequestMapping(value = "/getSentenceChangeByCrimid.json")
	@ResponseBody
	public HashMap<String, Object> getSentenceChangeByCrimid(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String crimid = request.getParameter("crimid");
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		Map<String,Object> map = new HashMap<String,Object>();
    	map.put("sortField", sortField);
    	map.put("sortOrder", sortOrder);
    	map.put("start", String.valueOf(start));
    	map.put("end",String.valueOf(end));
    	map.put("crimid", crimid);
		int count = sentenceAlterationHisService.countSentenceChangesOfCrimid(map);
		List<Map> list = sentenceAlterationHisService.getSentenceChangesOfCrimid(map);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	/**
	 * 刑期变动批量重审
	 * @author 
	 */
	@RequestMapping(value = "/batchReview.json")
	@ResponseBody
	public Object batchReview(HttpServletRequest request) {
		int result = 0;
		Map parmMap = parseParamMapString(request);
		result = tbdataSentchageService.updateBatchReview(parmMap);
		return result;
	}
	/**
	 * 跳转刑期变动列表页新增页面
	 * @param request
	 * @return ModelAndView
	 */
	
	@RequestMapping(value = "/sentenceChangeAdd.page")
	public ModelAndView toInSentenceChangeAdd(HttpServletRequest request){
		//获取用户
		Map<String, Object> selectmap = new HashMap<String, Object>();
		selectmap.put("citchangetype", selectLX);
		TbsysTemplate template = null;
		SystemUser user = getLoginUser(request);
		String deptid=user.getDepartid();
		JSONArray docconent = new JSONArray();
		String menuid= request.getParameter("menuid")==null?"":request.getParameter("menuid");
		String tempid = request.getParameter("tempid")==null?"":request.getParameter("tempid");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String operator = request.getParameter("operator")==null?"":request.getParameter("operator");
		Map<String,Object> h = new HashMap<String,Object>();
		h.put("crimid", crimid);
		List<Map<String, Object>> lMaps = tbdataSentchageService.selectDataBycrimid(h);
		StringBuffer sb = new StringBuffer();
		if(lMaps!=null){
			for(Map<String, Object> m:lMaps){
				if(m.get("COURTSANCTION")!=null){
					sb.append(m.get("COURTSANCTION")+",");
				}
			}
			if(sb.length()>=1){
				String strs = sb.substring(0, sb.length()-1);
				request.setAttribute("strs", strs);
			}
		}
		if(GkzxCommon.NEW.equals(operator)){
			template = systemModelService.getTemplateAndDepartid(tempid, deptid);
			if(template!=null){
				docconent.add(template.getContent());
			}
		}
		
		List<HashMap<Object, Object>> list = prisonerService.getCrimeBaseInfo(crimid);
		Map<Object,Object> map = list.get(0);
		
		
		HashMap<String, Object> jsonmap = new HashMap<String, Object>();
		Set<Object> set = map.keySet();
		for(Object m:set){
			jsonmap.put(((String) m).toLowerCase(), map.get(m));
		}
        //获取所有的犯罪类型
		selectmap.put("charge",systemCodeService.getcodeValue("GB075"));
		selectmap.put("xianzhijianxing",systemCodeService.getcodeValue("GKM1002"));
		selectmap.put("zhongshenjianjin",systemCodeService.getcodeValue("GKM1002"));
		
		//转json
		request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
		request.setAttribute("formDatajson", new JSONObject(jsonmap).toString());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("tempid", tempid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdefid","other_xqbd");
		request.setAttribute("menuid", menuid);
		request.setAttribute("operator", operator);
		ModelAndView mav =  new ModelAndView("aip/sentenceChangeInfoOfCrim");
		return mav;
	}
	
	
	private void operatePrisonerBaseCrime(String crimid, Map<String,String> map, String result_bhxq, String result_bz){
		
		Map<String,Object> parMap = new HashMap<String,Object>();
		
		String xianzhijianxing = map.get("xianzhijianxing");
		Integer isremission = 0; 
		if(StringNumberUtil.notEmpty(xianzhijianxing) && "是".equals(xianzhijianxing)){//是否限制减刑
			isremission = 1;
			parMap.put("isremission", isremission);
		}
    	//只有原判才放进去
    	parMap.put("judgmentname", map.get("courtnamecode"));//判决法院
    	parMap.put("caseyear", map.get("citcaseidyear"));//年号
    	parMap.put("caseorgshort", map.get("citcaseidbrief"));//字号
    	parMap.put("caseno", map.get("citcaseidnumber"));//号
    	
    	parMap.put("executiondate", DateUtil.StringParseDate(map.get("citexecutedate")) );//原判执行日期
    	parMap.put("sentencestime", DateUtil.StringParseDate(map.get("citchangefrom")) );//原判刑期起日
    	parMap.put("sentenceetime", DateUtil.StringParseDate(map.get("citchangeto")) );//原判刑期止日
    	parMap.put("judgedate", DateUtil.StringParseDate(map.get("citjudgedate")) );//判决日期
    	
    	//原判刑期
    	if ("9995".equals(result_bhxq) || "9996".equals(result_bhxq) || "9997".equals(result_bhxq)) {
			parMap.put("punishmentyear", result_bhxq);
			parMap.put("punishmentmonth", "0");
			parMap.put("punishmentday", "0");
			if ("9995".equals(result_bhxq)) {
				parMap.put("punishmenttype", "无期");
			}else if ("9996".equals(result_bhxq)) {
				parMap.put("punishmenttype", "死刑，缓期两年执行");
			}else if ("9997".equals(result_bhxq)) {
				parMap.put("punishmenttype", "死刑");
			}
		}else{
			parMap.put("punishmentyear", StringNumberUtil.parseNumOfDate(result_bhxq.substring(0, 2)));
			parMap.put("punishmentmonth", StringNumberUtil.parseNumOfDate(result_bhxq.substring(2, 4)));
			parMap.put("punishmentday", StringNumberUtil.parseNumOfDate(result_bhxq.substring(4, 6)));
			parMap.put("punishmenttype", "有期徒刑");
		}
    	
    	//原判剥夺政治权利
    	if ("97".equals(result_bz)) {
			parMap.put("losepoweryear", result_bz);
			parMap.put("losepowermonth", "0");
			parMap.put("losepowereday", "0");
		}else{
			parMap.put("losepoweryear", StringNumberUtil.parseNumOfDate(result_bz.substring(0, 2)));
			parMap.put("losepowermonth", StringNumberUtil.parseNumOfDate(result_bz.substring(2, 4)));
			parMap.put("losepowereday", StringNumberUtil.parseNumOfDate(result_bz.substring(4, 6)));
		}
    	
    	parMap.put("crimid", crimid);
    	parMap.put("charge", map.get("charge"));
		//只有原判信息会更新进去更新基本信息刑罚表
    	prisonerService.updateBaseByCrimid(parMap);
		
	}
	
	/**
	 * 修改/保存大字段
	 * @auhtor:mushuhong
	 */
	@RequestMapping(value = "/saveSentenceChange.action")
	@ResponseBody
	@Transactional
	@SuppressWarnings("all")
	public int saveSentenceChange(HttpServletRequest request){
		
		int countnum = 0;//保存结果：0、失败，1、成功
		Date date = new Date(); 
		SystemUser user = getLoginUser(request);
		TbsysDocument document = new TbsysDocument();
		TbdataSentchage  tbdataSentchage  = new TbdataSentchage();
		
		
		//获取当前登录的用户
		String deptid=user.getDepartid();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);
		String tempid = "XFZX_XQBD";
		
		String operator = request.getParameter("operator");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		String data = request.getParameter("noteinfo")==null?"":request.getParameter("noteinfo");
		String xfbduuid = request.getParameter("xfbduuid");//主键
		String text1 = request.getParameter("text1");
		
		
		String introduction = "罪犯刑期变动登记表";
		
		TbsysDocument tbsysDocument = sysDocumentService.selectByText1(text1);
		//集合
		List<Object> list = (ArrayList<Object>)JSON.parseArray(data, Object.class);
        Map<String,String> map = (Map<String, String>)list.get(0);
        
        map = StringNumberUtil.dealMapForForm(map);
        
        
        if(map.get("citjudgedate")==null||"".equals(map.get("citjudgedate"))){
        	return 0;
        }
        if(!"".equals(map.get("citjudgedate"))&&!"".equals(map.get("citchangetype"))){
        	introduction = "裁判日期  : " + map.get("citjudgedate") + " 变动类型 : " + map.get("citchangetype");
        }
        if(!"".equals(map.get("citextent"))){
        	introduction += " 变动幅度  : " + map.get("citextent");
        }
        tbdataSentchage.setCrimid(crimid);
        tbdataSentchage.setUuid(xfbduuid);
        
        if(map.get("citjudgedate")!=null&&!"".equals(map.get("citjudgedate"))){
        	tbdataSentchage.setCourtsanction(sdf.format(DateUtil.StringParseDate(map.get("citjudgedate"))));
        }
        if(map.get("citexecutedate")!=null&&!"".equals(map.get("citexecutedate"))){
        	tbdataSentchage.setExectime(sdf.format(DateUtil.StringParseDate(map.get("citexecutedate"))));
        }
        
        //

        //剥夺政治权利
        String result_bz = "";
        String bz = map.get("citchangedisfranchiseto");
    	if(bz!=null&&!"终身".equals(bz)){
    		result_bz += changenyr(bz);
        }else{
        	if("终身".equals(bz)){
        		result_bz+="97";
        	}
        }
        //变动后刑期
        String bhxq = map.get("citchangetermto");
        String result_bhxq = "";
        if(bhxq!=null  && !"无期".equals(bhxq) && !"无期徒刑".equals(bhxq) && !"死刑，缓期二年执行".equals(bhxq)&&!"死刑".equals(bhxq)){
        	result_bhxq += changenyr(bhxq);
        }else{
        	if("无期".equals(bhxq) || "无期徒刑".equals(bhxq)){
        		result_bhxq+="9995";
        	}else if("死刑，缓期二年执行".equals(bhxq)){
        		result_bhxq+="9996";
        	}else if("死刑".equals(bhxq)){
        		result_bhxq+="9997";
        	}
        }
        
        String lb = map.get("citchangetype");
        tbdataSentchage.setCharge(map.get("charge"));
        if(lb.equals("原判")){
        	lb = "1";
        	operatePrisonerBaseCrime(crimid, map, result_bhxq, result_bz);
        }else if(lb.equals("减刑")){
        	lb = "7";
        }else if(lb.equals("漏罪加刑")){
        	lb = "6";
        }else if(lb.equals("加刑")){
        	lb = "4";
        }else if(lb.equals("刑期顺延")){
        	lb = "20";
        }else{
        	lb = "";
        }
        
        //把数据保存放到map中进行
        
        String bdfd = map.get("citextent");
        String result_bdfd = "";
        if(bdfd!=null&&!"无期".equals(bdfd)&& !"无期徒刑".equals(bdfd) &&!"死刑，缓期二年执行".equals(bdfd)&&!"死刑".equals(bdfd)){
        	result_bdfd = changenyr(bdfd);
        }else{
        	if("无期".equals(bdfd) || "无期徒刑".equals(bdfd)){
        		result_bdfd += "9995";
        	}else if("死刑，缓期二年执行".equals(bdfd)){
        		result_bdfd += "9996";
        	}else if("死刑".equals(bdfd)){
        		result_bdfd += "9997";
        	}
        }
        tbdataSentchage.setCourtchange(result_bdfd);
        
        tbdataSentchage.setCourtname(map.get("court_name"));
        tbdataSentchage.setCourttitle(map.get("court_title"));
        tbdataSentchage.setCourtname_code(map.get("court_code"));
        tbdataSentchage.setCourtarea(map.get("court_code"));
        
        tbdataSentchage.setCategory(lb);
        
        tbdataSentchage.setSentence(result_bhxq);
        tbdataSentchage.setFajin(map.get("fajin"));
        tbdataSentchage.setMinpei(map.get("minpei"));
        tbdataSentchage.setMoshoucaichan(map.get("moshoucaichan"));
        tbdataSentchage.setXianzhijianxing(map.get("xianzhijianxing"));
        tbdataSentchage.setZhongshenjianjin(map.get("zhongshenjianjin"));
        
        //获取变动类型
        String citchangetype = map.get("citchangetype");
        Map<String, Object> codemap = new HashMap<String, Object>();
        codemap.put("name", citchangetype);
        codemap.put("codetype", "GB035");
        List<TbsysCode> codelist = systemCodeService.listAllByCondition(codemap);
        if(codelist!=null && codelist.size() >1) {
        	citchangetype = codelist.get(0).getCodeid();
        }
        
        tbdataSentchage.setLosepower(result_bz);
        if(map.get("citchangefrom")!=null&&!"".equals(map.get("citchangefrom"))){
        	tbdataSentchage.setCourtchangefrom(sdf.format(DateUtil.StringParseDate(map.get("citchangefrom"))));
        }else{
        	tbdataSentchage.setCourtchangefrom("");
        }
        if(map.get("citchangeto")!=null&&!"".equals(map.get("citchangeto"))){
        	tbdataSentchage.setCourtchangeto(sdf.format(DateUtil.StringParseDate(map.get("citchangeto"))));
        }else{
        	tbdataSentchage.setCourtchangeto("");
        }
      //考核起日
        if(map.get("citcheckdatefrom")!=null&&!"".equals(map.get("citcheckdatefrom"))){
        	tbdataSentchage.setAwardbegin(sdf.format(DateUtil.StringParseDate(map.get("citcheckdatefrom"))));
        }else{
        	tbdataSentchage.setAwardbegin("");
        }
        //考核止日
        if(map.get("citcheckdateend")!=null&&!"".equals(map.get("citcheckdateend"))){
        	tbdataSentchage.setAwardend(sdf.format(DateUtil.StringParseDate(map.get("citcheckdateend"))));
        }else{
        	tbdataSentchage.setAwardend("");
        }
        Calendar a=Calendar.getInstance();
        tbdataSentchage.setCourtyear(map.get("citcaseidyear"));//a.get(Calendar.YEAR)+""
        tbdataSentchage.setCourtshort(map.get("citcaseidbrief"));//手写
        tbdataSentchage.setCourtsn(map.get("citcaseidnumber"));//手写
        if(map.get("citjudgedate")!=null&&!"".equals(map.get("citjudgedate"))){
        	document.setText1(sdf.format(DateUtil.StringParseDate(map.get("citjudgedate")))+"-"+crimid);//裁判日期和crimid 組成串 citjudgedate
        }
		if(GkzxCommon.NEW.equals(operator)){
			document.setDepartid(org.getOrgid());
			document.setTempid(tempid);
			document.setIntroduction(introduction);
			document.setCrimid(crimid);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			countnum = sysDocumentService.saveTbsysDocument(document);
			tbdataSentchageService.insertSelective(tbdataSentchage);
		}else if(GkzxCommon.EDIT.equals(operator)){
			document.setDepartid(org.getOrgid());
			document.setTempid(tempid);
			document.setIntroduction(introduction);
			document.setCrimid(crimid);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			if(tbsysDocument!=null){
				sysDocumentService.deleteByText1(text1);
				sysDocumentService.saveTbsysDocument(document);
			}else{
				sysDocumentService.saveTbsysDocument(document);
			}
			//更新刑期变动表
			tbdataSentchageService.updateByExampleSelective(tbdataSentchage);
			
			
			//更新TBPRISONER_BASE_CRIME的原判期期
			tbdataSentchageService.updateOriginalSentenceData(crimid);
			
			
			//----------更新刑期变动信息表tbxf_sentencealteration表数据
			Map callMap = new HashMap();
			callMap.put("orgid", user.getDepartid());
			callMap.put("crimid", crimid);
			prisonerService.callXFsentencealteration(callMap);
		}
		
		
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("crimid", crimid);
		paramap.put("departid", user.getDepartid());
		commuteParoleBatchService.correctKaoHeDataInfo(paramap);
		
		
		return countnum;
	}
	
	/**
	 * 查看、修改刑期变动时，根据文书Id查询出大字段回显
	 * @author 
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/examineSentenceChange.page")
	public ModelAndView examineSentenceChange(HttpServletRequest request) throws ParseException {
		
		String menuid = request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		
		String crimid = request.getParameter("crimid");
		String tempid = request.getParameter("tempid");
		String uuid = request.getParameter("uuid");
		request.setAttribute("crimid", crimid);
		request.setAttribute("tempid", tempid);
		//
		String courtsanction = request.getParameter("courtsanction")==null?"":request.getParameter("courtsanction");
		String operator = request.getParameter("operator")==null?"":request.getParameter("operator");
		request.setAttribute("courtsanction", courtsanction);
		//
		Map<String, Object> selectmap = new HashMap<String, Object>();
		SystemUser user = getLoginUser(request);
		TbprisonerBaseinfo tbprisonerBaseinfo =prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(crimid);//处罚信息
		
		String text1 = courtsanction+"-"+crimid;		
//		TbsysDocument tbsysDocument = sysDocumentService.selectByText1(text1);
		
		Map<String,Object> jsonmap = new HashMap<String, Object>();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("courtsanction", courtsanction);
		map.put("crimid", crimid);
		map.put("uuid", uuid);
		
		JSONArray docconent = new JSONArray();
		TbsysTemplate  template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
		docconent.add(template.getContent());
		
//		if (tbsysDocument != null) {
//			docconent.add(tbsysDocument.getContent());
//		}else{
//		}
		
		//
		jsonmap.put("sddiscribe", baseCrime.getOrgname2());
		jsonmap.put("criminalid",crimid);
		jsonmap.put("cbiname",tbprisonerBaseinfo.getName());
//		jsonmap.put("charge", baseCrime.getCharge());
		
		TbdataSentchage tbdataSentchage =tbdataSentchageService.selectDataByUuid(map);
		if(tbdataSentchage!=null){
			//String abc = tbdataSentchage.getCourtshort().replace("第","");
			jsonmap.put("citcaseidbrief",tbdataSentchage.getCourtshort());
			jsonmap.put("citcheckdatefrom",tbdataSentchage.getAwardbegin());
			jsonmap.put("citcheckdateend",tbdataSentchage.getAwardend());
			jsonmap.put("citcaseidyear", tbdataSentchage.getCourtyear());
			jsonmap.put("citcaseidnumber",tbdataSentchage.getCourtsn() );
			jsonmap.put("citjudgedate",tbdataSentchage.getCourtsanction());
			jsonmap.put("citexecutedate", tbdataSentchage.getExectime());
			jsonmap.put("courtnamecode",tbdataSentchage.getCourtname()+tbdataSentchage.getCourttitle());
			
			jsonmap.put("court_code", tbdataSentchage.getCourtarea());
			jsonmap.put("court_title", tbdataSentchage.getCourttitle());
			jsonmap.put("court_name", tbdataSentchage.getCourtname());
			
//			if (tbdataSentchage.getCourtname_code()!=null && !"".equals(tbdataSentchage.getCourtname_code())) {
//				Map mapres = systemCodeService.getSJCodeidByXjCodeid(tbdataSentchage.getCourtname_code(), null);
//				if (mapres != null) {
//					jsonmap.put("courtnamecode",mapres.get("REMARK")+""+tbdataSentchage.getCourttitle());
//				}
//				jsonmap.put("court_code", tbdataSentchage.getCourtname_code());
//				jsonmap.put("court_title", tbdataSentchage.getCourtname());
//				jsonmap.put("court_name", tbdataSentchage.getCourttitle());
//			}
			
			jsonmap.put("citchangetype",tbdataSentchage.getCategory());
			jsonmap.put("charge",tbdataSentchage.getCharge());
			jsonmap.put("citextent", tbdataSentchage.getCourtchange());
			jsonmap.put("citchangetermto",tbdataSentchage.getSentence());
			jsonmap.put("citchangedisfranchiseto", tbdataSentchage.getLosepower());
			jsonmap.put("citchangefrom", tbdataSentchage.getCourtchangefrom());
			jsonmap.put("citchangeto", tbdataSentchage.getCourtchangeto());
			jsonmap.put("fajin", tbdataSentchage.getFajin());
			jsonmap.put("minpei", tbdataSentchage.getMinpei());
			jsonmap.put("moshoucaichan", tbdataSentchage.getMoshoucaichan());
			jsonmap.put("xianzhijianxing", tbdataSentchage.getXianzhijianxing());
			jsonmap.put("zhongshenjianjin", tbdataSentchage.getZhongshenjianjin());
		}
		
		jsonmap = StringNumberUtil.dealMapForForm(jsonmap);
		
		selectmap.put("xianzhijianxing",systemCodeService.getcodeValue("GKM1002"));
		selectmap.put("zhongshenjianjin",systemCodeService.getcodeValue("GKM1002"));
		//request.setAttribute("formDatajson", new JSONObject(jsonmap).toString());
		//下拉列表
		request.setAttribute("operator", operator);
		
		request.setAttribute("isapprove", "yes");
		request.setAttribute("snodeid", 0);
		request.setAttribute("flowdefid", "other_xqbd");
		request.setAttribute("xfbduuid", uuid);
		selectmap.put("citchangetype",selectLX);
		request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
		request.setAttribute("formDatajson", new JSONObject(jsonmap).toString());
		request.setAttribute("text1", text1);
		request.setAttribute("formcontent", docconent.toString());
		ModelAndView mav =  new ModelAndView("aip/sentenceChangeInfoOfCrim");
		return mav;
	}
	//把 变量 bdfd 转换为六位数字（如：bdfd = 12年5个月,return 120500,保存数据的时候用）
	public static String  changenyr(String bdfd){
		String result = "";
		int n_ch = bdfd.indexOf("年");
		int y_ch = bdfd.indexOf("个");
		int r_ch = bdfd.indexOf("天");
		if(n_ch>-1){
			String nian = bdfd.substring(0,n_ch);
			result+=getrightFormat(nian);
    	}else{
    		result+="00";
    	}
    	if(y_ch>-1){
    		String yue = bdfd.substring(n_ch+1,y_ch);
    		result+=getrightFormat(yue);
    	}else{
    		result+="00";
    	}
    	if(r_ch>-1){
    		String ri = "";
    		if(y_ch > -1)//有月的情况
    			ri=bdfd.substring(y_ch+2, r_ch);
    		else
    			ri=bdfd.substring(n_ch+1, r_ch);
    		result+=getrightFormat(ri);
    	}else{
    		result+="00";
    	}
		return result;
	}
	//如果str 长度为1 则str 前面+ '0'，长度为2则返回
	public static String getrightFormat(String str) {
		String result = "";
		if (str.length() == 1)
			result = "0" + str;
		else
			result = str;
		return result;
	}
	/**
	 * 保存变动幅度信息（新）
	 */
	@RequestMapping(value = "/SaveBianDongFuDuData.action")
	@ResponseBody
	public Object SaveBianDongFuDuData(HttpServletRequest request){
		returnResourceMap(request);
		List<Map> data = new ArrayList<Map>();
		String commuterange = request.getParameter("commuterange");
		String id = request.getParameter("id");
		JSONMessage message = new JSONMessage();
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("commuterange",commuterange);
				String[] ids = id.split(",");
				for(int i=0; i<ids.length; i++){
					 String crimid = ids[i];
					 map.put("crimid",crimid);
					 String info = "保存失败";
					 int total = 0;
					 total = tbdataSentchageService.updateBianDongFuDuByMap(map);
					 message.setTotal(total);
					 if(total > 0){
					    info = "保存成功";
						message.setSuccess();
					 }
					 message.setInfo(info);
				}
		        request.setAttribute("str",commuterange);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		return message;
		
	}
	/**
	 * 跳转刑期变动列表页面(新)
	 * 
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/sentenceChangedPage.page")
	public ModelAndView sentenceChangedPage(HttpServletRequest request){
		ModelAndView mav =  new ModelAndView("commutationParole/sentenceChangePage");
		return mav;
	}
	/**
	 * 根据罪犯编号获取刑期变动列表(新)
	 * @author 
	 */
	@RequestMapping(value = "/getSentenceChangeByBatch.json")
	@ResponseBody
	public Map<String, Object> getSentenceChangeByBatch(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String,Object> map = this.parseParamMap(request);
		map = this.parsePageInfoOfMap(map);
		map.put("flowdefid", "other_zfjyjxjssp");
		map.put("departid", user.getDepartid());
		map.put("orgid", user.getOrgid());
		
    	int count = tbdataSentchageService.countAllBianDongByCondition(map);
    	List<Map<String,Object>> data= tbdataSentchageService.getSentenceChangeByBatch(map);
    	//
    	result.put("data", data);
		result.put("total", count);
		return result;
	}
	
	/**
	 * 不予减刑、假释（新）
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/BuYuJianJiaByCrimid.action")
	@ResponseBody
	public Object BuYuJianJiaByCrimid(HttpServletRequest request){
		returnResourceMap(request);
		String id = request.getParameter("id");
		JSONMessage message = new JSONMessage();
		try {
			String[] ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				FlowBase flowbase = new FlowBase();
				flowbase.setFlowdraftid(ids[i]);
				flowbase.setText16("1");
				flowBaseService.updateByFlowdraftid(flowbase);
			}
          
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return message;
	}
	
	@RequestMapping(value="toEditFuduPage.page")
	public ModelAndView toEditFuduPage(HttpServletRequest request){
		String yemian = "/commutationParole/editFuduPage";
		ModelAndView mav = new ModelAndView(yemian);
		return mav;
	}
	
	@RequestMapping(value="saveChangeSentenceChange.action")
	@ResponseBody
	public Object saveChangeSentenceChange(HttpServletRequest request) throws ParseException{
		int result  = 0;
		SystemUser user = getLoginUser(request);
		//获得部门编号
		String departid = user.getDepartid();
		SystemOrganization organization = sysOrganizationService.getByOrganizationId(departid);
		String zhongyuan = sysOrganizationService.getByOrganizationId(organization.getIntermediatecourt()).getFullname();
		String gaoyuan =  sysOrganizationService.getByOrganizationId(organization.getHighcourt()).getFullname();
		String zhongyuanshort = sysOrganizationService.getByOrganizationId(organization.getIntermediatecourt()).getShortname()+GkzxCommon.CASE_TYPE_JXJS_FY+"第";
		String gaoyuanshort =  sysOrganizationService.getByOrganizationId(organization.getHighcourt()).getShortname()+GkzxCommon.CASE_TYPE_JXJS_FY+"第";
		
		String json = request.getParameter("data") == null ? "" : request.getParameter("data");
		String curyear = request.getParameter("curyear");
		String pancaitime = request.getParameter("pancaitime");
		String zhixingdate = request.getParameter("zhixingdate");
		String courtsanction = pancaitime.replace("-", "");
		String exectime = zhixingdate.replace("-", "");
		List list = new ArrayList();
		HashMap<String, Object> map = new HashMap<String, Object>();
		list = (List) JsonUtil.Decode(json);
		if (list != null && list.size() > 0) {
			for(int i=0;i<list.size();i++){
				Map tempmap = (Map)list.get(i);
				String crimid = (String)tempmap.get("applyid");
				int int1 = (Integer)tempmap.get("int1");
				String category = "";
				if (int1 ==0 || int1==4) {
					category = "7";
				}else if (int1 == 1) {
					category = "14";
				}
				//当前止刑日
				String courtchangeto1 = (String)tempmap.get("courtchangeto");
				//现刑期年
				String nowpunishmentyear = (String)tempmap.get("nowpunishmentyear");
				//法院所在地区
				String courtname;
				//法院名称
				String courttitle;
				//字号简称
				String courtshort;
				if (nowpunishmentyear == "9995" || nowpunishmentyear == "9996") {
					courtname = gaoyuan.substring(0,gaoyuan.indexOf(GkzxCommon.GAOJIFAYUAN));
					courttitle = GkzxCommon.GAOJIFAYUAN;
					courtshort = gaoyuanshort;
				}else {
					courtname = zhongyuan.substring(0,zhongyuan.indexOf(GkzxCommon.ZHONGJIFAYUAN));
					courttitle = GkzxCommon.ZHONGJIFAYUAN;
					courtshort = zhongyuanshort;
				}
				//字号
				String courtsn = (String)tempmap.get("caseno");
				String commuterange_id = (String)tempmap.get("commuterange_id");
				//变动幅度
				String courtchange;
				//剥夺政治权利
				String losepower="000000";
				//当前刑期
				String sentence="000000";
				if (commuterange_id.indexOf("@")!=-1) {
					String[] commuterange_ids = commuterange_id.split("@");
					courtchange = commuterange_ids[0];
					if (commuterange_ids[1].length()==1) {
						losepower = "0"+commuterange_ids[1]+"0000";
					}else if (commuterange_ids[1].equals("97")) {
						losepower = commuterange_ids[1];
					}else{
						losepower = commuterange_ids[1]+"0000";
					}
					if (nowpunishmentyear == "9995" || nowpunishmentyear == "9996") {
						sentence = commuterange_ids[0];
					}
				}else {
					courtchange = commuterange_id;
					if (nowpunishmentyear == "9995" || nowpunishmentyear == "9996") {
						sentence = commuterange_id;
					}
				}
				//刑期止日
				String courtchangeto = "";
				if ("9995".equals(courtchange)) {
					courtchangeto="";
				}else if ("9995".equals(nowpunishmentyear)) {
					Date pancai = new SimpleDateFormat("yyyy-MM-dd").parse(pancaitime);
					String year = courtchange.substring(0,2);
					String month = courtchange.substring(2,4);
					String day = courtchange.substring(4,6);
					Map  datemap =new HashMap<String,Object>();
					datemap.put("year", -Integer.parseInt(year));
					datemap.put("month", -Integer.parseInt(month));
					datemap.put("day", -Integer.parseInt(day));
					datemap.put("courtchangeto", pancai);
					courtchangeto = tbdataSentchageService.getZhiXingDate(datemap);
				}else {
					Date pancai = null;
					if(StringNumberUtil.notEmpty(courtchangeto1)){
						pancai = new SimpleDateFormat("yyyyMMdd").parse(courtchangeto1);
						
						String year = courtchange.substring(0,2);
						String month = courtchange.substring(2,4);
						String day = courtchange.substring(4,6);
						Map  datemap =new HashMap<String,Object>();
						datemap.put("year", Integer.parseInt(year));
						datemap.put("month", Integer.parseInt(month));
						datemap.put("day", Integer.parseInt(day));
						datemap.put("courtchangeto", pancai);
						
						courtchangeto = tbdataSentchageService.getZhiXingDate(datemap);
					}
					
					
				}
				//刑期起日
				String courtchangefrom = (String)tempmap.get("sentencestime");
				if (StringNumberUtil.notEmpty(courtchangeto)) {
					Map dateMap1 = new HashMap<String,String>();
					dateMap1.put("courtchangefrom", courtchangefrom);
					dateMap1.put("courtchangeto", courtchangeto);
					sentence = tbdataSentchageService.getXianXingQi(dateMap1);
				}
				//查询批次ID
				int batchid = (Integer)tempmap.get("batchid");
				Date awardend1 = tbxfCommuteParoleBatchMapper.selectByPrimaryKey(batchid).getExamineend();
				//考核止日
				String awardend = DateUtil.dateFormat(awardend1, "yyyyMMdd");
				TbdataSentchage tbdataSentchage = new TbdataSentchage();
				tbdataSentchage.setCrimid(crimid);
				tbdataSentchage.setCategory(category);
				tbdataSentchage.setCourtname(courtname);
				tbdataSentchage.setCourttitle(courttitle);
				tbdataSentchage.setCourtyear(curyear);
				tbdataSentchage.setCourtshort(courtshort);
				tbdataSentchage.setCourtsn(courtsn);
				tbdataSentchage.setCourtsanction(courtsanction);
				tbdataSentchage.setCourtchange(courtchange);
				tbdataSentchage.setSentence(sentence);
				tbdataSentchage.setCourtchangefrom(courtchangefrom);
				tbdataSentchage.setCourtchangeto(courtchangeto);
				tbdataSentchage.setLosepower(losepower);
				tbdataSentchage.setAwardend(awardend);
				tbdataSentchage.setExectime(exectime);
				
				tbdataSentchageService.insertSelective(tbdataSentchage);
				
				FlowBase flowbase = new FlowBase();
				flowbase.setFlowdraftid((String)tempmap.get("flowdraftid"));
				flowbase.setText16("1");
				flowBaseService.updateByFlowdraftid(flowbase);
			}
		}
		
		return result;
	}
	/**
	 * 跳转的到财产刑罪犯列表页
	 * @return
	 */
	@RequestMapping(value="toCrimeCaiChanList.page")
	public ModelAndView toCrimeCaiChanList(){
		String yemian="/commutationParole/hunan/CrimeCaiChanListPage";
		ModelAndView mav=new ModelAndView(yemian);
		return mav;
	}
	/**
	 * 财产刑罪犯列表页数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="crimeCaiChanList")
	@ResponseBody
	public Object crimeCaiChanList(HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Map> data = new ArrayList<Map>();
			int pageIndex = (Integer) (request.getParameter("pageIndex") == null ? ""
					: Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer) (request.getParameter("pageSize") == null ? ""
					: Integer.parseInt(request.getParameter("pageSize")));
			//字段排序
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize - 1;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sortField", sortField);
			map.put("sortOrder", sortOrder);
			map.put("start", String.valueOf(start));
			map.put("end", String.valueOf(end));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 跳转到财产刑列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="toCaiChanXingListPage.page")
	public ModelAndView toCaiChanXingListPage(HttpServletRequest request){
		
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		returnResourceMap(request);
		//
		String crimid=request.getParameter("crimid") == null?"":request.getParameter("crimid");
		String closetype =request.getParameter("closetype") == null?"" : request.getParameter("closetype");
		//
		TbprisonerBaseCrime baseCrime=baseCrimeMapper.selectPrisonerById(crimid);
		TbprisonerBaseinfo Baseinfo = prisonerBaseinfoMapper.selectByPrimaryKey(crimid);
		
		request.setAttribute("forfeit", StringNumberUtil.getDefaultStringOnNull(baseCrime.getForfeit(), "/"));
		request.setAttribute("payment", StringNumberUtil.getDefaultStringOnNull(baseCrime.getPayment(), "/"));
		request.setAttribute("forfeitureproperty", StringNumberUtil.getDefaultStringOnNull(baseCrime.getForfeitureproperty(), "/"));
		request.setAttribute("expropriation", StringNumberUtil.getDefaultStringOnNull(baseCrime.getExpropriation(), "/"));
		request.setAttribute("compensation", StringNumberUtil.getDefaultStringOnNull(baseCrime.getCompensation(), "/"));
		request.setAttribute("fulfilcompensation", StringNumberUtil.getDefaultStringOnNull(baseCrime.getFulfilcompensation(), "/"));
		request.setAttribute("closetype", closetype);
		
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", Baseinfo.getName());
		
		//判断是否能够删除，先写死
		Map<String,String> map = new HashMap<String,String>();
		map.put("userid",user.getUserid());
		List list = prisonerBaseinfoMapper.getResidByUserid(map);
		if(list.size() > 1){
			if(list.toString().indexOf("18577") > -1){
				request.setAttribute("removestatus", "1");
			}
		}
		
		String yemian="/commutationParole/hunan/caichanxinglistpage";
		ModelAndView mav=new ModelAndView(yemian);
		return mav;
	}
	/**
	 * 财产刑列表数据显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getCaiChanByCrimid.json")
	@ResponseBody
	public Object getCaiChanByCrimid(HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<String, Object>();
		String crimid = request.getParameter("crimid");
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		Map<String,Object> map = new HashMap<String,Object>();
    	map.put("sortField", sortField);
    	map.put("sortOrder", sortOrder);
    	map.put("start", String.valueOf(start));
    	map.put("end",String.valueOf(end));
    	map.put("crimid", crimid);
		int count = tbPrisonerCaiChanXingService.countCaiChanByCrimid(map);
		List<Map> list = tbPrisonerCaiChanXingService.getCaiChanByCrimid(map);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	/**
	 * 打开财产刑补录窗口
	 * @param request
	 * @return
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("caiChanAdd.page")
	public ModelAndView caiChanAdd(HttpServletRequest request,HttpServletResponse response) throws ParseException, UnsupportedEncodingException{
		returnResourceMap(request);
		String crimid=request.getParameter("crimid") == null? "" : request.getParameter("crimid");
		String operator=request.getParameter("operator") == null?"" :request.getParameter("operator");
		String zhixingdate = request.getParameter("zhixingdate") == null? "" : request.getParameter("zhixingdate");
		String danjuhao = request.getParameter("danjuhao")==null?"":request.getParameter("danjuhao");
		danjuhao = URLDecoder.decode(danjuhao, "UTF-8");
		Date zhiDate=null;
		if (zhixingdate == null||"".equals(zhixingdate)) {
			zhiDate=new Date();
		}else {
			zhiDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(zhixingdate);
		}
		if (operator.equals("edit")||operator.equals("chakan")) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("crimid", crimid);
			map.put("danjuhao", danjuhao);
			TbPrisonerCaiChanXing caichan=tbPrisonerCaiChanXingService.getByCrimidAndDate(map);
			if (caichan != null) {
				request.setAttribute("caichantype", caichan.getCaichantype());
				request.setAttribute("zhixingjine", caichan.getZhixingjine());
				request.setAttribute("zhixingjiguan", caichan.getZhixingjiguan());
				request.setAttribute("danjuhao", caichan.getDanjuhao());
				request.setAttribute("weizhixing", caichan.getWeizhixing());
			}
		}
		request.setAttribute("crimid", crimid);
		request.setAttribute("operator", operator);
		request.setAttribute("zhixingdate", zhiDate);
		
		String yemian="/commutationParole/hunan/caichanaddpage";
		ModelAndView mav=new ModelAndView(yemian);
		return mav;
	}
	
	@RequestMapping(value="saveCaiChanXing")
	@ResponseBody
	public int saveCaiChanXing(HttpServletRequest request) throws Exception{
		
		int countnum = 0;//保存结果：0、失败，1、成功
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		SystemLog log = new SystemLog();
		String json = request.getParameter("data");

		Map<String, String> map = new HashMap<String, String>();
		Map<String,String> tempMap = new HashMap<String,String>();
		Object data = json.substring(2, json.length() - 2);
		String string = data.toString().replace("\"", "");
		String[] strings = string.split(",");
		for (int i = 0; i < strings.length; i++) {
			String[] strs = strings[i].split(":");
			if (strs.length == 1) {
				map.put(strs[0], "");
			} else {
				map.put(strs[0], strs[1]);
			}
		}
		tempMap = MapUtil.ObjectMapToStringMap(map);
		String crimid=request.getParameter("crimid");
		String caiChanType=request.getParameter("caichantype");
		String zhiDate = request.getParameter("zhixingdate");
		String kaiPiaoDate = request.getParameter("kaipiaodate");
		String status = request.getParameter("status") == null ? "" : request.getParameter("status");
		String operator=request.getParameter("operator");
		
		
		SimpleDateFormat format = new SimpleDateFormat(GkzxCommon.DATETIMEFORMAT);
		if("new".equals(operator)){
			
			map.put("crimid", crimid);
			map.put("caichantype", caiChanType);
			map.put("zhixingjine", tempMap.get("zhixingjine"));
			map.put("danjuhao", tempMap.get("danjuhao"));
			map.put("zhixingjiguan", tempMap.get("zhixingjiguan"));
			map.put("zhixingdate", zhiDate);
			map.put("optime", DateUtil.dateFormat(new Date(), "yyyy.MM.dd"));
			map.put("weizhixing", tempMap.get("weizhixing"));
			map.put("status", status);
			map.put("kaipiaodate", kaiPiaoDate);
			
			log.setLogtype("财产刑补录");
			log.setOpcontent("财产刑添加");
			log.setOpaction("财产刑添加");
			log.setOrgid(user.getOrgid());
			log.setRemark("添加成功");
			
			countnum = tbPrisonerCaiChanXingService.insertSelective(map);
		}else if("edit".equals(operator)){
			
			map.put("crimid",crimid);
			map.put("caichantype",caiChanType);
			map.put("zhixingjine",tempMap.get("zhixingjine"));
			map.put("danjuhao",tempMap.get("danjuhao"));
			map.put("zhixingjiguan", tempMap.get("zhixingjiguan"));
			map.put("zhixingdate",zhiDate);
			map.put("status", status);
			map.put("kaipiaodate", kaiPiaoDate);
			
			log.setLogtype("财产刑补录");
			log.setOpcontent("财产刑修改");
			log.setOpaction("财产刑修改");
			log.setOrgid(user.getOrgid());
			log.setRemark("修改成功");
			
			countnum = tbPrisonerCaiChanXingService.updateByPrimaryKeySelective(map);
		}
		
		systemLogService.add(log, user);
		
		return countnum;
	}
	
	@RequestMapping(value="deleteByIdAndZhiDate.action")
	@ResponseBody
	public int deleteByIdAndZhiDate(HttpServletRequest request) throws ParseException, Exception{
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		int result=0;
		String crimid=request.getParameter("crimid");
		String zhixingdate=request.getParameter("zhixingdate");
		String caichantype=request.getParameter("caichantype");
		String danjuhao = request.getParameter("danjuhao");
		danjuhao = URLDecoder.decode(danjuhao,"UTF-8");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("crimid", crimid);
		map.put("zhixingdate", zhixingdate);
		map.put("caichantype", caichantype);
		map.put("danjuhao", danjuhao);
		log.setLogtype("财产刑补录");
		log.setOpcontent("财产刑删除");
		log.setOpaction("财产刑删除");
		log.setOrgid(user.getOrgid());
		log.setRemark("删除成功");
		
		result=tbPrisonerCaiChanXingService.deleteByCrimidAndZhiDate(map);
		if (result == 1) {
			log.setStatus(1);
		}
		try {
			systemLogService.add(log, user);
		} catch (Exception e) {
			//吃掉异常
		}
		return result;
	}
	
	@RequestMapping(value="backCaiChanXing.action")
	@ResponseBody
	public int backCaiChanXing (HttpServletRequest request) throws Exception{
		int result = 0;
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		String crimid = request.getParameter("crimid") == null ? "" : request.getParameter("crimid");
		String zhixingdate = request.getParameter("zhixingdate") == null ? "" : request.getParameter("zhixingdate");
		String danjuhao = request.getParameter("danjuhao") == null ? "" : request.getParameter("danjuhao");
		danjuhao = URLDecoder.decode(danjuhao, "UTF-8");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("crimid", crimid);
		map.put("zhixingdate", zhixingdate);
		map.put("danjuhao", danjuhao);
		map.put("status", "0");
		log.setLogtype("财产刑补录");
		log.setOpcontent("财产刑退回");
		log.setOpaction("财产刑退回");
		log.setOrgid(user.getOrgid());
		log.setRemark("退回成功");
		result = tbPrisonerCaiChanXingMapper.updateByPrimaryKeySelective(map);
		if (result == 1) {
			log.setStatus(1);
		}
		try {
			systemLogService.add(log, user);
		} catch (Exception e) {
			//吃掉异常
		}
		return result;
	}
	
	@RequestMapping(value="/getSanLeiType")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getSanLeiType(HttpServletRequest request){
		
		List<Map> listMap = systemCodeService.getSanLeiType(request);
		
		return listMap;
	}
	
	@RequestMapping(value = "removeSentenceChange.json")
	@ResponseBody
	public Object removeSentenceChange(HttpServletRequest request){
		int flag = 0;
		String data = request.getParameter("xfbduuids");//主键
		String[] dataStr = data.split(",");
		for(int i=0;i<dataStr.length;i++){
			String[] ArrayStr = dataStr[i].split(";");
			String text1 = ArrayStr[3]+"-"+ArrayStr[2];
			//if(!ArrayStr[1].equals("1")){}//原判不能删除
			Map map = new HashMap<>();
			map.put("xfbduuid",ArrayStr[0]);
			flag = tbdataSentchageService.removeSentenceChange(map);//根据uuid删除刑期变动信息
			if(flag == 1){
			    TbsysDocument tbsysDocument = sysDocumentService.selectByText1(text1);
			    if (tbsysDocument != null) {
				    	flag = sysDocumentService.deleteByText1(text1);//删除刑期变动大字段
			    }
				  
			}
			
		}
		return flag;
	}
	
	@RequestMapping(value="isSaveCaiChanXing.action")
	@ResponseBody
	public int isSaveCaiChanXing(HttpServletRequest request) throws Exception{
		int result = 0;
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);
		String crimid = request.getParameter("crimid") == null ? "" : request.getParameter("crimid");
		String danjuhao = request.getParameter("danjuhao") == null ? "" : request.getParameter("danjuhao");
		Map map = new HashMap<String, String>();
		map.put("crimid", crimid);
		map.put("danjuhao", danjuhao);
		log.setLogtype("财产刑补录");
		log.setOrgid(user.getOrgid());
		result = tbPrisonerCaiChanXingMapper.isByPrimaryKeySelective(map);
		if (result == 1) {
			log.setStatus(1);
		}
		try {
			//systemLogService.add(log, user);
		} catch (Exception e) {
			//吃掉异常
		}
		return result;
	}
	
	
	@RequestMapping(value = "saveKaoHeZongFen.json")
	@ResponseBody
	public Object saveKaoHeZongFen(HttpServletRequest request){
		SystemUser user = this.getLoginUser(request);
		
		int flag = 0;
		String crimid = request.getParameter("crimid");//罪犯编号
		String kaohezongfen = request.getParameter("kaohezongfen");//考核总分
		String leijibiaoyang = request.getParameter("leijibiaoyang");//累计表扬次数
		String yufen = request.getParameter("yufen");//余分
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("kaohezongfen",kaohezongfen);
		map.put("crimid",crimid);
		map.put("leijibiaoyang",leijibiaoyang);
		map.put("yufen",yufen);
		//
		List<Map<String,Object>> list = tbdataSentchageService.getKaoHeZongFenByCrimid(map);
		if(list.size() > 0){
			flag = tbdataSentchageService.updateKaoHeZongFenById(map);
			tbdataSentchageService.updateSentenceAlterationRewardinfo(map);
		}else{
			flag = tbdataSentchageService.insertKaoHeZongFen(map);
			tbdataSentchageService.updateSentenceAlterationRewardinfo(map);
		}
		
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("crimid", crimid);
		paramap.put("departid", user.getDepartid());
		commuteParoleBatchService.correctKaoHeDataInfo(paramap);
		
		return flag;
	}
	
	
	//跳转到裁定录入页面
	@RequestMapping("/caiDingLuRu.page")
	public ModelAndView caiDingLuRu(HttpServletRequest request){
		ModelAndView mav =  new ModelAndView("commutationParole/caiDingLuRuListPage");
		return mav;
	}
	
	@RequestMapping(value="/getCaiDingList")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getCaiDingList(HttpServletRequest request){
		String batchid = request.getParameter("batchid") == null?"":request.getParameter("batchid");
		Map map =  new HashMap<>();
		map.put("batchid",batchid);
				
		List<Map> listMap = tbdataSentchageService.getCaiDingList(map);
		
		return listMap;
	}
	
	
	@RequestMapping(value="saveChangeSentenceChangeForHuNan.action")
	@ResponseBody
	public Object saveChangeSentenceChangeForHuNan(HttpServletRequest request) throws ParseException{
		
		int result  = 0;
		SystemUser user = getLoginUser(request);
		
		Map<String,String> paramap = this.parseParamMapString(request);
		result = tbdataSentchageService.saveChangeSentenceChangeForHuNan(user, paramap);
		
		return result;
	}
	
	
	@RequestMapping("caiDingSheZhiPage.page")
	public ModelAndView caiDingSheZhiPage(HttpServletRequest request,HttpServletResponse response) throws ParseException, UnsupportedEncodingException{
		returnResourceMap(request);
		String crimid=request.getParameter("crimid") == null? "" : request.getParameter("crimid");
		String operator=request.getParameter("operator") == null?"" :request.getParameter("operator");
		String zhixingdate = request.getParameter("zhixingdate") == null? "" : request.getParameter("zhixingdate");
		String danjuhao = request.getParameter("danjuhao")==null?"":request.getParameter("danjuhao");
		danjuhao = URLDecoder.decode(danjuhao, "UTF-8");
		Date zhiDate=null;
		if (zhixingdate == null||"".equals(zhixingdate)) {
			zhiDate=new Date();
		}else {
			zhiDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(zhixingdate);
		}
		if (operator.equals("edit")||operator.equals("chakan")) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("crimid", crimid);
			map.put("danjuhao", danjuhao);
			TbPrisonerCaiChanXing caichan=tbPrisonerCaiChanXingService.getByCrimidAndDate(map);
			if (caichan != null) {
				request.setAttribute("caichantype", caichan.getCaichantype());
				request.setAttribute("zhixingjine", caichan.getZhixingjine());
				request.setAttribute("zhixingjiguan", caichan.getZhixingjiguan());
				request.setAttribute("danjuhao", caichan.getDanjuhao());
				request.setAttribute("weizhixing", caichan.getWeizhixing());
			}
		}
		request.setAttribute("crimid", crimid);
		request.setAttribute("operator", operator);
		request.setAttribute("zhixingdate", zhiDate);
		
		String yemian="/commutationParole/hunan/caiDingSheZhiPage";
		ModelAndView mav=new ModelAndView(yemian);
		return mav;
	}
}

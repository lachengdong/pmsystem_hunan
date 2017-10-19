package com.sinog2c.mvc.controller.commutationParole;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.gkzx.common.LogCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.prisoner.TbprisonerAccomplice;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.prisoner.TbprisonerResume;
import com.sinog2c.model.prisoner.TbprisonerSocialRelation;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemTemplate;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Controller
public class TuiAnHanDocumentsController extends ControllerBase {

	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SentenceAlterationService sentenceAlterationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Autowired
	private SystemTemplateService systemTemplateService;
	@Resource
	private UvFlowService uvFlowService;

	@RequestMapping(value = "/tuian")
	public ModelAndView totuian(HttpServletRequest request) {
		ModelAndView mav = null;
		returnResourceMap(request);
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		Properties jyconfig = new GetProperty().bornProp(
				GkzxCommon.DATABASETYPE, null);
		String ischeckseal = (jyconfig.getProperty("ischeckseal") == null ? ""
				: jyconfig.getProperty("ischeckseal"));
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("ischeckseal", ischeckseal);
		View view = new InternalResourceView(
				"WEB-INF/JSP/commutationParole/tuian.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	@RequestMapping(value = "/getTuiAnList")
	@ResponseBody
	public Object getTuiAnList(HttpServletRequest request,HttpServletResponse response){
		SystemUser user = getLoginUser(request);
		String state = "-1";//流转状态
		List<Map> data = new ArrayList<Map>();
		Object buttonstr = "";
		Map<String, Object> resultmap = new HashMap<String,Object>();
		try {
			//取得参数
			String key = request.getParameter("key")==null?"":request.getParameter("key");//检索关键字
			String flowdefid = "other_zfjyjxjssp";//罪犯减刑假释审批流程定义
			//分页
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			//字段排序
			String sortField = request.getParameter("sortField");
			if("anjianhao".equals(sortField.toLowerCase())){
				sortField = "to_number(substr(text6,5))";
			}
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			//获取当前菜单ID 对应的自定义流程ID
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("flowdefid", flowdefid);
			map.put("jailto", "2");
	    	map.put("state", state);
	    	map.put("suid", user.getUserid());
	    	map.put("key", key);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	if(user!=null){
	    		String orgid = user.getOrganization().getOrgid();
	    		String departid = user.getDepartid();
				map.put("orgid", orgid);
				map.put("departid", departid);
				//获取该用户拥有的按钮资源id
		    	buttonstr = this.returnButtonResourceByUser(user,null,null);
		    	map.put("connsql", buttonstr);
	    	}
	    	//获取总条数
	    	int count = uvFlowService.countAllOfTuiAnByCondition(map);
			data= uvFlowService.findTuiAnByCondition(map);
			resultmap.put("data", data);
			resultmap.put("total", count);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultmap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/toTuiAnHanDocumentPage.action")
	public ModelAndView toTuiAnHanDocumentPage(HttpServletRequest request)
			throws Exception {
		String crimid = request.getParameter("crimid");
		String flowdraftid = request.getParameter("flowdraftid");
		String menuid = request.getParameter("menuid");
		String flowid = request.getParameter("flowid");
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("flowid", flowid);
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String tempid = "SH_TUIANHAN";
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("tempid", tempid);
		String aipBigdata = "";
		Map<String, Comparable> paramMap = new HashMap<String, Comparable>();
		paramMap.put("tempid", tempid);// 所有建议书类刑的tempid存时都用suggestdoc
		if (StringNumberUtil.notEmpty(flowdraftid)) {
			if ("undefined".equals(flowdraftid)) {
				paramMap.put("flowdraftid", 0);
			} else {
				paramMap.put("flowdraftid", Integer.parseInt(flowdraftid));
			}
			aipBigdata = flowBaseOtherService
					.getDocconentByFlowdraftid(paramMap);
		}

		if (StringNumberUtil.notEmpty(aipBigdata)) {
			JSONArray docconent = new JSONArray();
			docconent.add(aipBigdata);
			Map<String, Object> map = new HashMap<String, Object>();
			request
					.setAttribute("formDatajson", new JSONObject(map)
							.toString());
			request.setAttribute("formcontent", docconent.toString());
		} else {
			JSONArray docconent = new JSONArray();
			Map<String, Object> selectmap = new HashMap<String, Object>();
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(
					tempid, user.getDepartid());
			Map<String, Object> map = new HashMap<String, Object>();
			TbprisonerBaseinfo tbprisonerBaseinfo = prisonerService
					.getBasicInfoByCrimid(crimid);
			TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService
					.getCrimeByCrimid(crimid);
			List<TbprisonerAccomplice> accompliceList = prisonerService
					.selectAccompliceByCrimid(crimid);
			List<TbprisonerResume> resumeList = prisonerService
					.findByCrimidResume(crimid);
			List<TbprisonerSocialRelation> relationList = prisonerService
					.findRelationBycrimid(crimid);
			if (tbprisonerBaseinfo != null) {
				map.put("name", tbprisonerBaseinfo.getName());
				map.put("mz", tbprisonerBaseinfo.getNation());
				map.put("whcd", tbprisonerBaseinfo.getEducation());
				map.put("csdate", DateUtil.dateFormatForAip(tbprisonerBaseinfo
						.getBirthday()));
				map.put("hjaddre", tbprisonerBaseinfo
						.getRegisteraddressdetail());
				map.put("xzhuzhi", tbprisonerBaseinfo.getFamilyaddress());
				map.put("jiguan", tbprisonerBaseinfo.getOrigin());
				map.put("puqianzhiye", tbprisonerBaseinfo.getVocation());
			}
			TbxfSentenceAlteration tbxf = sentenceAlterationService
					.selectByPrimaryKey(crimid);
			if (tbxf != null) {
				map
						.put("guanyadanwei", tbxf.getJailname()
								+ tbxf.getAreaname());// 关押监狱、监区
				map.put("sfdate", DateUtil.dateFormatForAip(tbxf
						.getCourtchangeto()));
			}
			if (tbprisonerBaseCrime != null) {
				map.put("panjuefayuan", tbprisonerBaseCrime.getJudgmentname());// 判决法院
				map.put("jiyariq", DateUtil
						.dateFormatForAip(tbprisonerBaseCrime.getDetaindate()));// 羁押日期
			}
			if (accompliceList != null && accompliceList.size() > 0) {
				String str = "";
				for (TbprisonerAccomplice ta : accompliceList) {
					str += ta.getName() + "；";
				}
				str = str.substring(0, str.lastIndexOf("；") - 1) + "。";
				map.put("tongan", str);
			}
			if (!relationList.isEmpty() && relationList != null
					&& relationList.size() > 0) {
				String str = "";
				for (TbprisonerSocialRelation tsr : relationList) {
					String birthday = tsr.getBirthday() == null ? "" : tsr
							.getBirthday();
					String relationship = tsr.getRelationship() == null ? ""
							: tsr.getRelationship();
					str += tsr.getName();
					if (StringNumberUtil.notEmpty(relationship)) {
						str += "，关系：" + relationship;
					}
					if (StringNumberUtil.notEmpty(birthday)) {
						str += "，出生日期：" + birthday;
					}
					str += "\\r\\n";
				}
				map.put("guanxi", str);
			}
			if (resumeList != null && resumeList.size() > 0) {
				String str = "";
				for (TbprisonerResume tr : resumeList) {
					if (StringNumberUtil.notEmpty(tr.getBegindate())) {
						str += tr.getBegindate();
					}
					if (StringNumberUtil.notEmpty(tr.getEnddate())) {
						str += "-" + tr.getEnddate();
					}
					if (StringNumberUtil.notEmpty(tr.getOrgname())) {
						str += "，单位：" + tr.getOrgname();
					}
					if (StringNumberUtil.notEmpty(tr.getVocation())) {
						str += "，职务(职业)：" + tr.getVocation();
					}
					str += "\\r\\n";
				}
				map.put("jianli", str);
			}

			SimpleDateFormat s = new SimpleDateFormat("yyyy");
			/*
			 * 退案函 年号和号设置
			 */
			String opid = user.getOpid();
			int text3 = tbsysDocumentService.getCount(tempid, opid);
			String text2 = "沪";
			map.put("text1", s.format(new Date()));
			map.put("text2", text2);
			map.put("text3", text3);
			if (template != null) {
				docconent.add(template.getContent());
			}
			request
					.setAttribute("formDatajson", new JSONObject(map)
							.toString());
			request.setAttribute("selectDatajson", new JSONObject(selectmap)
					.toString());
			request.setAttribute("formcontent", docconent.toString());
		}
		return new ModelAndView("commutationParole/tuiAnHan");
	}

	@RequestMapping("/toEditTuiAnPage.json")
	@ResponseBody
	public Object toEditTuiAnPage(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		Object resultval = -1;
		String suid = user.getUserid();
		String flowdraftid = request.getParameter("flowdraftid");// 流程草稿ID
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("flowdraftid", flowdraftid);
		FlowBase flowBase = flowBaseService.getFlowBaseByFlowdraftid(paramMap);
		if (flowBase != null) {
			Short islocked = flowBase.getIslocked();
			String opname = flowBase.getOpname() == null ? "" : flowBase
					.getOpname();
			String opid = flowBase.getOpid() == null ? "" : flowBase.getOpid();
			if (islocked == 1 && !suid.equals(opid)) {
				resultval = opname;
			}
		}
		return resultval;
	}

	@RequestMapping(value = "/saveTuiAnHan")
	@ResponseBody
	public int saveTuiAnHan(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		Date date = new Date();
		String content = request.getParameter("bigdata") == null ? "" : request
				.getParameter("bigdata");
		String crimid = request.getParameter("crimid") == null ? "" : request
				.getParameter("crimid");
		String tempid = request.getParameter("tempid") == null ? "" : request
				.getParameter("tempid");
		String id = request.getParameter("id") == null ? "" : request
				.getParameter("id");
		String tid = request.getParameter("tid") == null ? "" : request
				.getParameter("tid");
		if (StringNumberUtil.notEmpty(id)) {
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			String temps = ids[0];
			String[] temArr = temps.split("@");
			crimid = temArr[0];
		}
		TbsysDocument document = new TbsysDocument();
		document.setCrimid(crimid);
		document.setDepartid(user.getDepartid());
		document.setTempid(tempid);
		TbprisonerBaseinfo baseinfo = prisonerService
				.getBasicInfoByCrimid(crimid);
		document.setIntroduction(baseinfo.getName() + "的" + LogCommon.TUIANHAN);
		document.setContent(content);
		document.setOpid(user.getUserid());
		document.setOptime(date);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.OPERATE);
		log.setOpaction(LogCommon.ADD);
		log.setOpcontent(LogCommon.JSBZS + LogCommon.ADD);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.ADD);
		int co = 1;
		if ("12321".equals(tid)) {
			co = tbsysDocumentService.saveTbsysDocument(document);
		}
		if ("12322".equals(tid)) {
			co = tbsysDocumentService.saveTbsysDocument(document);
			if (co == 1) {
				String jailto = "2";
				sentenceAlterationService.autoUpdateSentenceChangeJailto(
						jailto, crimid);
			}
		}
		if ("1".equals(tid)) {
			co = tbsysDocumentService.saveTbsysDocument(document);
		}
		if ("2".equals(tid)) {
			co = tbsysDocumentService.saveTbsysDocument(document);
			if (co == 1) {
				String jailto = "1";
				sentenceAlterationService.autoUpdateSentenceChangeJailto(
						jailto, crimid);
			}
		}
		return co;
	}

	@RequestMapping(value = "/toTuiAnTabs")
	public ModelAndView toTuiAnTabs(HttpServletRequest request) {
		String crimid = "";
		String orgid = "";
		String flowdraftid = "";
		String flowid = "";
		String lastnodeid = "";
		String fathermenuid = request.getParameter("fathermenuid");
		String flowdefid = request.getParameter("flowdefid");
		String tempid = request.getParameter("tempid");
		String menuid = request.getParameter("menuid");
		String closetype = request.getParameter("closetype");
		String provinceid = request.getParameter("provinceid");
		String nodeid = request.getParameter("nodeid");
		String days = request.getParameter("days");
		String id = request.getParameter("id");// 获取罪犯编号字符串，解析成数组
		Properties jyconfig = new GetProperty().bornProp(
				GkzxCommon.DATABASETYPE, null);
		String ischeckseal = (jyconfig.getProperty("ischeckseal") == null ? ""
				: jyconfig.getProperty("ischeckseal"));
		if (StringNumberUtil.notEmpty(id)) {// 如果罪犯编号等信息不为空就表示批量处理
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			String temps = ids[0];
			String[] temArr = temps.split("@");
			crimid = temArr[0];
			orgid = temArr[1];
			flowdraftid = temArr[2];
			flowid = temArr[3];
			if (!StringNumberUtil.notEmpty(flowid)
					|| "flowidnull".equals(flowid.trim())
					|| "undefined".equals(flowid.trim())) {
				flowid = "";
			}
			lastnodeid = temArr[4];
		}

		ModelAndView mav = null;
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("fathermenuid", fathermenuid);
		request.setAttribute("orgid", orgid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("lastnodeid", lastnodeid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("closetype", closetype);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("ischeckseal", ischeckseal);
		if (StringNumberUtil.notEmpty(provinceid)) {
			request.setAttribute("provinceid", provinceid);
		}

		if (StringNumberUtil.notEmpty(nodeid)) {
			request.setAttribute("nodeid", nodeid);
		}

		if (StringNumberUtil.notEmpty(days)) {
			request.setAttribute("days", days);
		}
		View view = new InternalResourceView(
				"WEB-INF/JSP/commutationParole/tuiAnTabs.jsp");
		mav = new ModelAndView(view);
		return mav;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> ReturnShbMap(String flowdraftid, String crimid,
			HttpServletRequest request) {
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		Map<String, Object> map = new HashMap<String, Object>();
		map = tbxfSentencealterationService.getParoleByCrimid(crimid,
				flowdraftid, user, request);
		request.setAttribute("applyvalue", map.get("applyvalue"));
		return map;
	}

	@RequestMapping(value = "/tuian_sh")
	public ModelAndView tuian_sh(HttpServletRequest request) {
		JSONArray docconent = new JSONArray();
		Map<String, Object> map = new HashMap<String, Object>();
		returnResourceMap(request);
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String departid = user.getDepartid();// 根据用户Id获取所在部门Id
		String crimid = "";
		String orgid = "";
		String flowdraftid = "";
		String flowid = "";
		String lastnodeid = "";
		String fathermenuid = request.getParameter("fathermenuid");
		String tempid = request.getParameter("tempid");
		String menuid = request.getParameter("menuid");
		String closetype = request.getParameter("closetype");
		String flowdefid = request.getParameter("flowdefid");
		Properties jyconfig = new GetProperty().bornProp(
				GkzxCommon.DATABASETYPE, null);

		String ischeckseal = (jyconfig.getProperty("ischeckseal") == null ? ""
				: jyconfig.getProperty("ischeckseal"));
		String tianjinconde = jyconfig.getProperty(GkzxCommon.TIANJIN_CODE);
		String hebeicode = jyconfig.getProperty(GkzxCommon.HEBEI_CODE);
		String shanxi = jyconfig.getProperty(GkzxCommon.SHAN_XI) == null ? ""
				: jyconfig.getProperty(GkzxCommon.SHAN_XI);
		String provincecode = jyconfig.getProperty("provincecode") == null ? ""
				: jyconfig.getProperty("provincecode");
		String provinceid = request.getParameter("provinceid");
		String nodeid = request.getParameter("nodeid");
		String days = request.getParameter("days");

		String id = request.getParameter("id");// 获取罪犯编号字符串，解析成数组
		if (StringNumberUtil.notEmpty(id)) {// 如果罪犯编号等信息不为空就表示批量处理
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			String temps = ids[0];
			String[] temArr = temps.split("@");
			crimid = temArr[0];// 罪犯编号就从数组里取出第一个罪犯编号
			orgid = temArr[1];
			flowdraftid = temArr[2];
			flowid = temArr[3];
			if (!StringNumberUtil.notEmpty(flowid)
					|| "flowidnull".equals(flowid.trim())) {
				flowid = "";
			}
			lastnodeid = temArr[4];
		}

		if (!StringNumberUtil.notEmpty(flowid)) {
			Map temPara = new HashMap();
			temPara.put("flowdraftid", flowdraftid);
			flowid = uvFlowService.getFlowidByFlowdraftid(temPara);
		}
		// 将案件加锁
		String islocked = GkzxCommon.ZERO;// 加锁状态 0解锁
		FlowBase flowBase = flowBaseService.findById(flowdraftid);
		if (flowBase != null) {
			if (!StringNumberUtil.notEmpty(flowBase.getIslocked()))
				islocked = String.valueOf(flowBase.getIslocked());
			if (!GkzxCommon.ONE.equals(islocked))
				flowBaseService.updateById(islocked, flowdraftid, user
						.getUserid(), user.getName());
		}

		request.setAttribute("fathermenuid", fathermenuid);
		request.setAttribute("applyid", crimid);
		request.setAttribute("orgid", orgid);
		request.setAttribute("tempid", tempid);
		request.setAttribute("flowid", flowid);
		request.setAttribute("lastnodeid", lastnodeid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("closetype", closetype);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("ischeckseal", ischeckseal);

		if (StringNumberUtil.notEmpty(provinceid)) {
			request.setAttribute("provinceid", provinceid);
		}

		if (StringNumberUtil.notEmpty(nodeid)) {
			request.setAttribute("nodeid", nodeid);
		}

		if (StringNumberUtil.notEmpty(days)) {
			request.setAttribute("days", days);
		}
		String applyname = tbxfSentencealterationService
				.getCrimiNameByCrimid(crimid);
		request.setAttribute("applyname", applyname);
		Map paramMap = new HashMap();
		paramMap.put("flowdraftid", flowdraftid);
		paramMap.put("tempid", tempid);
		String content = flowBaseOtherService
				.getDocconentByConditionTwo(paramMap);
		if (StringNumberUtil.notEmpty(content)) {
			docconent.add(content);
		} else {
			if (GkzxCommon.SHANGHAI_PROVINCE.equals(provincecode)) {
				map = ReturnShbMap(flowdraftid, crimid, request);
				docconent.add(map.get("docconent"));
			} else {
				Map paramMap3 = new HashMap();
				paramMap3.put("flowdraftid", flowdraftid);
				FlowBase fb = flowBaseService
						.getFlowBaseByFlowdraftid(paramMap3);
				String casetype = "";
				if (fb != null
						&& StringNumberUtil.belongTo(departid, hebeicode)) {
					map.put("casenum", fb.getText6());
					map.put("jxjs_1", fb.getInt1());
					String title = "";
					title = user.getOrganization().getText1() + "\\r\\n";
					if (fb.getInt1() == 0) {
						casetype = "减字";
						title = title + "提请罪犯减刑审核表";
					} else if (fb.getInt1() == 1) {
						casetype = "假字";
						title = title + "提请罪犯假释审核表";
					}
					map.put("jxojs", title);
				} else if (fb != null) {
					map.put("casenum", fb.getText6());
					map.put("jxjs_1", fb.getInt1());
					if (fb.getInt1() == 0) {
						casetype = "减字";
						map.put("jxojs", "罪犯减刑审核表");
					} else if (fb.getInt1() == 1) {
						casetype = "假字";
						map.put("jxojs", "罪犯假释审核表");
					}
				}

				TbprisonerBaseinfo tbprisonerBaseinfo = prisonerService
						.getBasicInfoByCrimid(crimid);
				TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService
						.getCrimeByCrimid(crimid);
				Map<String, Object> tbxfMap = tbxfSentencealterationService
						.selectTbxfByCrimid(crimid);
				TbsysTemplate template = new TbsysTemplate();
				if (StringNumberUtil.belongTo(departid, hebeicode)) {
					if (!StringNumberUtil.isNullOrEmpty(tbxfMap
							.get("PUNISHMENTYEAR"))
							&& !tbxfMap.get("PUNISHMENTYEAR").equals(
									GkzxCommon.XINGQI_WUQI)
							&& !tbxfMap.get("PUNISHMENTYEAR").equals(
									GkzxCommon.XINGQI_SIHUAN)) {
						template = systemModelService.getTemplateAndDepartid(
								"JXJS_JXJSSHB_YQ", departid);
					} else {
						template = systemModelService.getTemplateAndDepartid(
								tempid, departid);
					}
				} else {
					template = systemModelService.getTemplateAndDepartid(
							tempid, departid);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年M月d日");

				String orgName = "";
				if (template != null) {
					docconent.add(template.getContent());
				}
				if (tbprisonerBaseinfo != null) {
					if (StringNumberUtil.belongTo(departid, hebeicode)) {
						map.put("departid", user.getOrganization().getName());// 单位id
					} else {
						String deptid = tbprisonerBaseinfo.getDepartid();
						SystemOrganization org = systemOrganizationService
								.getByOrganizationId(deptid);
						if (org != null) {
							map.put("departid", org.getName());
							orgName = org.getShortname();
						}
					}
					// 山西 阳泉 审核表 单位：对应的是部门的全称(如：阳泉二监第一监区第二分监区)
					map.put("orgname", user.getOrganization().getFullname());
					map.put("cbiname", tbprisonerBaseinfo.getName());
					map.put("cbitruename", tbprisonerBaseinfo.getUsedname());
					map.put("cbigendername", tbprisonerBaseinfo.getGender());
					map.put("caieducation", tbprisonerBaseinfo.getEducation());
					// 山西 ：籍贯显示字段
					if (shanxi.contains(departid)) {
						map.put("cbinativenamedetail", tbprisonerBaseinfo
								.getBirtharea());
						// 监狱名称+刑种 =组织减刑假释审核表标题
						map.put("jxjsshb_title", map.get("departid") + ""
								+ tbprisonerBaseCrime.getPunishmenttype());
					} else {
						map.put("cbinativenamedetail", tbprisonerBaseinfo
								.getOrigin());
					}
					map.put("cbination", tbprisonerBaseinfo.getNation());
					map.put("cbibirthday", sdf.format(tbprisonerBaseinfo
							.getBirthday()));
					map.put("cbihomeaddress", tbprisonerBaseinfo
							.getFamilyaddress());
					map.put("idnumber", tbprisonerBaseinfo.getIdnumber());
					map.put("text35", tbprisonerBaseinfo.getRewardpunish());
				}
				if (tbprisonerBaseCrime != null) {
					Date rujiandate = tbprisonerBaseCrime.getInprisondate();
					String rujianriqistr = "";
					if (rujiandate != null) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(rujiandate);
						rujianriqistr = calendar.get(Calendar.YEAR) + "年"
								+ (calendar.get(Calendar.MONTH) + 1) + "月";
					}
					map.put("criofficiallyplacedate", DateUtil
							.dateFormatForAip(rujiandate));
					map.put("text32", "执行日期");
					map.put("zhixingdate", sdf.format(tbprisonerBaseCrime
							.getExecutiondate()));
					map.put("judgedate", sdf.format(tbprisonerBaseCrime
							.getJudgedate()));
					if (tbprisonerBaseCrime.getDetaindate() != null
							&& !"".equals(tbprisonerBaseCrime.getDetaindate())) {
						map.put("text34", sdf.format(tbprisonerBaseCrime
								.getDetaindate()));
					}

					if (StringNumberUtil.belongTo(departid, tianjinconde)
							|| StringNumberUtil.belongTo(departid, hebeicode)) {
						map.put("cjicourtname", StringNumberUtil
								.getDefaultStringOnNull(tbprisonerBaseCrime
										.getJudgmentname(), ""));// 法院
					} else {
						map.put("cjicourtname", StringNumberUtil
								.getDefaultStringOnNull(tbprisonerBaseCrime
										.getJudgmentname(), "")
								+ StringNumberUtil.getDefaultStringOnNull(
										tbprisonerBaseCrime.getJudgmentshort(),
										""));// 法院
					}
					if (tbprisonerBaseCrime.getPunishmenttype().equals(
							GkzxCommon.XINGQI_YOUQI)
							|| tbprisonerBaseCrime.getPunishmenttype().equals(
									GkzxCommon.XINGQI_YOUQI_ZH)) {
						String strPunishment = StringNumberUtil
								.isNullOrEmpty(tbprisonerBaseCrime
										.getPunishmentyear()) ? ""
								: GkzxCommon.ZERO.equals(tbprisonerBaseCrime
										.getPunishmentyear().toString()) ? ""
										: StringNumberUtil
												.digit2speech(tbprisonerBaseCrime
														.getPunishmentyear()
														.toString())
												+ "年";
						strPunishment += StringNumberUtil
								.isNullOrEmpty(tbprisonerBaseCrime
										.getPunishmentmonth()) ? ""
								: GkzxCommon.ZERO.equals(tbprisonerBaseCrime
										.getPunishmentmonth().toString()) ? ""
										: StringNumberUtil
												.digit2speech(tbprisonerBaseCrime
														.getPunishmentmonth()
														.toString())
												+ "个月";
						strPunishment += StringNumberUtil
								.isNullOrEmpty(tbprisonerBaseCrime
										.getPunishmentday()) ? ""
								: GkzxCommon.ZERO.equals(tbprisonerBaseCrime
										.getPunishmentday().toString()) ? ""
										: StringNumberUtil
												.digit2speech(tbprisonerBaseCrime
														.getPunishmentday()
														.toString())
												+ "天";
						if (!StringNumberUtil.isNullOrEmpty(strPunishment)) {
							// 山西 刑期前面 不加 刑种
							if (shanxi.contains(departid)) {
								map.put("zhuxing", strPunishment);// 刑期
							} else {
								map.put("zhuxing", GkzxCommon.XINGQI_YOUQI_ZH
										+ strPunishment);// 刑期
							}
						}
					} else if (tbprisonerBaseCrime.getPunishmenttype().equals(
							GkzxCommon.XINGQI_SIHUAN)
							|| tbprisonerBaseCrime.getPunishmenttype().equals(
									GkzxCommon.XINGQI_SIHUAN_ZH)) {
						map.put("zhuxing", GkzxCommon.XINGQI_SIHUAN_ZH);
					} else if (tbprisonerBaseCrime.getPunishmenttype().equals(
							GkzxCommon.XINGQI_WUQI)
							|| tbprisonerBaseCrime.getPunishmenttype().equals(
									GkzxCommon.XINGQI_WUQI_ZH)) {
						map.put("zhuxing", GkzxCommon.XINGQI_WUQI_ZH);
					} else {
						map.put("zhuxing", "");
					}
					String strLosepower = "";
					if (!StringNumberUtil.isNullOrEmpty(tbprisonerBaseCrime
							.getLosepoweryear())
							&& (tbprisonerBaseCrime.getLosepoweryear()
									.toString().equals(GkzxCommon.LOSEPOWER_97) || tbprisonerBaseCrime
									.getLosepoweryear().toString().equals(
											GkzxCommon.LOSEPOWER_99))) {
						strLosepower = GkzxCommon.LOSEPOWER_ZH;
					} else {
						// 山西 附加刑 剥夺政治权利+原判罚金+没收财产
						if (shanxi.contains(departid)) {
							strLosepower = StringNumberUtil
									.isNullOrEmpty(tbprisonerBaseCrime
											.getLosepoweryear()) ? ""
									: GkzxCommon.ZERO
											.equals(tbprisonerBaseCrime
													.getLosepoweryear()
													.toString()) ? ""
											: StringNumberUtil
													.digit2speech(tbprisonerBaseCrime
															.getLosepoweryear()
															.toString())
													+ "年";
							strLosepower += StringNumberUtil
									.isNullOrEmpty(tbprisonerBaseCrime
											.getLosepowermonth()) ? ""
									: GkzxCommon.ZERO
											.equals(tbprisonerBaseCrime
													.getLosepowermonth()
													.toString()) ? ""
											: StringNumberUtil
													.digit2speech(tbprisonerBaseCrime
															.getLosepowermonth()
															.toString())
													+ "个月";
							strLosepower += StringNumberUtil
									.isNullOrEmpty(tbprisonerBaseCrime
											.getLosepowereday()) ? ""
									: GkzxCommon.ZERO
											.equals(tbprisonerBaseCrime
													.getLosepowereday()
													.toString()) ? ""
											: StringNumberUtil
													.digit2speech(tbprisonerBaseCrime
															.getLosepowereday()
															.toString())
													+ "天";
							if (!StringNumberUtil
									.isNullOrEmpty(tbprisonerBaseCrime
											.getForfeit())) {
								// 判断 strLosepower == "" 那么不加，否则加上，
								if (!"".equals(strLosepower)) {
									strLosepower += "，罚金:"
											+ tbprisonerBaseCrime.getForfeit()
													.replace("元", "") + "元";
								} else {
									strLosepower += "罚金:"
											+ tbprisonerBaseCrime.getForfeit()
													.replace("元", "") + "元";
								}
							}
							if (!StringNumberUtil
									.isNullOrEmpty(tbprisonerBaseCrime
											.getForfeitureproperty())) {
								// 判断 strLosepower == "" 那么不加，否则加上，
								if (!"".equals(strLosepower)) {
									strLosepower += "，没收财产:"
											+ tbprisonerBaseCrime.getForfeit()
													.replace("元", "") + "元";
								} else {
									strLosepower += "没收财产:"
											+ tbprisonerBaseCrime.getForfeit()
													.replace("元", "") + "元";
								}
							}
						} else {
							strLosepower = StringNumberUtil
									.isNullOrEmpty(tbprisonerBaseCrime
											.getLosepoweryear()) ? ""
									: GkzxCommon.ZERO
											.equals(tbprisonerBaseCrime
													.getLosepoweryear()
													.toString()) ? ""
											: StringNumberUtil
													.digit2speech(tbprisonerBaseCrime
															.getLosepoweryear()
															.toString())
													+ "年";
							strLosepower += StringNumberUtil
									.isNullOrEmpty(tbprisonerBaseCrime
											.getLosepowermonth()) ? ""
									: GkzxCommon.ZERO
											.equals(tbprisonerBaseCrime
													.getLosepowermonth()
													.toString()) ? ""
											: StringNumberUtil
													.digit2speech(tbprisonerBaseCrime
															.getLosepowermonth()
															.toString())
													+ "个月";
							strLosepower += StringNumberUtil
									.isNullOrEmpty(tbprisonerBaseCrime
											.getLosepowereday()) ? ""
									: GkzxCommon.ZERO
											.equals(tbprisonerBaseCrime
													.getLosepowereday()
													.toString()) ? ""
											: StringNumberUtil
													.digit2speech(tbprisonerBaseCrime
															.getLosepowereday()
															.toString())
													+ "天";
						}
					}
					map.put("fujiaxing", strLosepower);// 剥夺年限
					map.put("anyouhuizong", tbprisonerBaseCrime
							.getCauseaction());
					String Sentencestime = "";
					if (tbprisonerBaseCrime.getPunishmenttype().equals(
							GkzxCommon.XINGQI_YOUQI)
							|| tbprisonerBaseCrime.getPunishmenttype().equals(
									GkzxCommon.XINGQI_YOUQI_ZH)) {
						Sentencestime += StringNumberUtil
								.isNullOrEmpty(tbprisonerBaseCrime
										.getSentencestime()) ? "" : "从"
								+ sdf2.format(tbprisonerBaseCrime
										.getSentencestime());
						Sentencestime += StringNumberUtil
								.isNullOrEmpty(tbprisonerBaseCrime
										.getSentenceetime()) ? "" : "起\\r\\n至"
								+ sdf2.format(tbprisonerBaseCrime
										.getSentenceetime()) + "止";
						map.put("xingqiqizhi", Sentencestime);// 刑期
					} else {
						if (StringNumberUtil.belongTo(departid, tianjinconde)) {
							Sentencestime += StringNumberUtil
									.isNullOrEmpty(tbprisonerBaseCrime
											.getSentencestime()) ? "" : "从"
									+ sdf2.format(tbprisonerBaseCrime
											.getSentencestime()) + "起";
							map.put("xingqiqizhi", Sentencestime);// 刑期
						} else {
							map.put("xingqiqizhi", "");
						}
					}
					if (StringNumberUtil.belongTo(getLoginUser(request)
							.getDepartid(), tianjinconde)) {
						String fanzuishijian = DateUtil
								.dateFormat(tbprisonerBaseCrime.getCrimedate());
						if (fanzuishijian != null && !"".equals(fanzuishijian)) {
							fanzuishijian = fanzuishijian.substring(0, 8);
						}
						map.put("fanzuishijian", fanzuishijian);// 天津犯罪时间精确到月
					} else {
						map.put("fanzuishijian", DateUtil
								.dateFormatForAip(tbprisonerBaseCrime
										.getCrimedate()));
					}
					map.put("cjimulct", tbprisonerBaseCrime.getForfeit());
					map.put("fajinjiaonaqingkuang", tbprisonerBaseCrime
							.getPayment());
					map.put("cjipeichangjine", tbprisonerBaseCrime
							.getCompensation());
					map.put("cjmoneydisgorged", tbprisonerBaseCrime
							.getFulfilcompensation());
					map.put("paymentzk", tbprisonerBaseCrime.getStolenmoney());
					map.put("paymentpc", tbprisonerBaseCrime.getReturnloot());
					map.put("cjisequestrateproperty", tbprisonerBaseCrime
							.getForfeitureproperty());
					map
							.put("paymentcc", tbprisonerBaseCrime
									.getExpropriation());
					// 山西 ：三类罪犯 需要 在备注中添加三类罪犯的描述
					if (shanxi.contains(departid)) {
						if (tbprisonerBaseCrime.getSanclassstatus() != null
								&& "1".equals(tbprisonerBaseCrime
										.getSanclassstatus())) {
							map.put("text32", GkzxCommon.SANLEIZUIFAN);
						} else {
							map.put("text32", "");
						}
					}
					// 刑种
					map.put("punishmenttype", tbprisonerBaseCrime
							.getPunishmenttype());
					if (tbprisonerBaseCrime.getCrimeface() != null
							&& !"".equals(tbprisonerBaseCrime.getCrimeface())) {
						map.put("fanzuishishi", "    "
								+ tbprisonerBaseCrime.getCrimeface().replace(
										"&#13;&#10;", "\\r\\n").replace("rn",
										"\\r\\n").replace("\r\n", "\\r\\n"));
					} else {
						map.put("fanzuishishi", "    "
								+ tbprisonerBaseCrime.getCrimeface());
					}
					map.put("text9", sdf.format(new Date()));
					String gaizaobiaoxian = "";
					SystemTemplate systemTemplate = systemTemplateService
							.getSystemTemplateByCondition("9993", user
									.getPrison().getPorgid());
					if (systemTemplate != null)
						gaizaobiaoxian = systemTemplate.getContent();
					if ("1412".equals(departid)) {
						String querySql = "SELECT "
								+ "case when (select count(*) from TBDATA_SENTCHAGE b2 where b2.crimid=b1.crimid and b2.category=7) > 0  then "
								+ "'该犯自上次减刑以来' "
								+ "else "
								+ " '该犯自入狱以来'"
								+ "end 入监或减刑,"
								+ "decode(to_number(b3.int11),0,'',null,'','政治'||replace(b3.int11,'分','')||'分') as 政治,"
								+ "decode(to_number(b3.int12),0,'',null,'','文化'||replace(b3.int12,'分','')||'分') as 文化,"
								+ "decode(to_number(b3.int13),0,'',null,'','技术'||replace(b3.int13,'分','')||'分') as 技术,"
								+ "trim('，' from decode(b3.int2,null,'',0,'','表扬'||b3.int2||'次，')"
								+ " ||decode(b3.int3,null,'',0,'','记功'||b3.int3||'次，')"
								+ " ||decode(b3.int1,null,'',0,'','嘉奖'||b3.int1||'次，')"
								+ " ||decode(b3.int4,null,'',0,'','改造积极分子'||b3.int4||'次，')"
								+ " ||decode(b3.int6,null,'',0,'','重大立功'||b3.int6||'次，')"
								+ " ||decode(b3.int5,null,'',0,'','立功'||b3.int5||'次，')"
								+ " ||decode(b3.int7,null,'',0,'','警告'||b3.int7||'次，')"
								+ " ||decode(b3.int8,null,'',0,'','记过'||b3.int8||'次，')"
								+ " ||decode(b3.int9,null,'',0,'','禁闭'||b3.int9||'次，')"
								+ " ) as 考核奖惩, "
								+ " b1.*"
								+ "FROM TBPRISONER_BASEINFO b1, "
								+ "     TBXF_PRISONERPERFORMANCE b3, "
								+ "    (SELECT *  FROM (  SELECT * FROM tbxf_commuteparole_batch WHERE curyear = (SELECT MAX (curyear)"
								+ "  FROM TBXF_COMMUTEPAROLE_BATCH WHERE departid = '"
								+ departid
								+ "')  AND departid = '"
								+ departid
								+ "' ORDER BY batch DESC)  WHERE ROWNUM = 1) b4  "
								+ "   where b1.crimid=b3.criid(+)   "
								+ "    and   b3.batchid=b4.batchid(+)"
								+ "    and b1.crimid='" + crimid + "'";

						Map contMap = tbxfSentencealterationService
								.selectTbxfMapBySql(querySql);
						gaizaobiaoxian = MapUtil.replaceBracketContent(
								gaizaobiaoxian, contMap);
						gaizaobiaoxian = MapUtil
								.formatFormString(gaizaobiaoxian);
						map.put("gaizaobiaoxian", gaizaobiaoxian);
					}
				}
				if (tbxfMap != null && !tbxfMap.isEmpty()) {
					String prisonterm = "";
					if (!StringNumberUtil.isNullOrEmpty(tbxfMap
							.get("SENTENCECHAGEINFO"))) {
						prisonterm = StringNumberUtil.getTrimRtnStr(tbxfMap
								.get("SENTENCECHAGEINFO").toString());
					}
					if (!StringNumberUtil.isNullOrEmpty(fb.getText6())) {
						try {
							map.put("parolenumber", "("
									+ fb.getText6().substring(0, 4) + ")"
									+ orgName + "第"
									+ fb.getText6().substring(4) + "号");
							map.put("text31", "("
									+ fb.getText6().substring(0, 4) + ")"
									+ orgName + "第"
									+ fb.getText6().substring(4) + "号");
						} catch (Exception e) {
						}
					}
					map.put("prisonterm", "    " + prisonterm);
					if (StringNumberUtil.belongTo(departid, hebeicode)) {
						String sentenceStartEnd = "    现刑期起止：";
						sentenceStartEnd = sentenceStartEnd
								+ "刑期自"
								+ sdf2.format(tbprisonerBaseCrime
										.getSentencestime()) + "起";
						if (tbxfMap.get("COURTCHANGETO") != null) {
							sentenceStartEnd = sentenceStartEnd + "至"
									+ sdf2.format(tbxfMap.get("COURTCHANGETO"))
									+ "止";
						}
						if (prisonterm != null && !"".equals(prisonterm)) {
							map.put("prisonterm", "    历次减刑情况：" + prisonterm
									+ "\\r\\n" + sentenceStartEnd + "。");
						}
					}
					map.put("zclhq", tbxfMap.get("REMUNERATION"));
					map.put("gsk", tbxfMap.get("WITHCASH"));
					map.put("hk", tbxfMap.get("REMITTANCE"));
					map.put("qtsr", tbxfMap.get("INCOME"));
					map.put("gwzc", tbxfMap.get("SHOPPING"));
					map.put("qtzc", tbxfMap.get("PAY"));
					map.put("balance", tbxfMap.get("OVERPLUS"));
				}
				map.put("text38", sdf.format(tbprisonerBaseCrime
						.getArrestdate()));
				map.put("text39", sdf.format(tbprisonerBaseCrime
						.getSentencestime()));
				map.put("criminalid", crimid);
				String applyvalue = crimid + "," + tbprisonerBaseinfo.getName();
				request.setAttribute("applyvalue", applyvalue);
			}
		}
		request.setAttribute("tempid", tempid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("flowdefid", flowdefid);
		ModelAndView mav = null;
		View view = null;
		view = new InternalResourceView(
				"WEB-INF/JSP/aip/editTuiAnOfJailCase.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
}

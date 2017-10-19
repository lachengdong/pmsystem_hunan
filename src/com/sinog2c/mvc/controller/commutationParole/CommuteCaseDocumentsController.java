package com.sinog2c.mvc.controller.commutationParole;

import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.flow.FlowArchivesMapper;
import com.sinog2c.dao.api.flow.FlowMapper;
import com.sinog2c.dao.api.flow.UvFlowMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerBaseinfoMapper;
import com.sinog2c.dao.api.system.TbsysTemplateMapper;
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.flow.Flow;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.flow.FlowOtherFlow;
import com.sinog2c.model.flow.UvFlow;
import com.sinog2c.model.prisoner.TbprisonerAccomplice;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.prisoner.TbprisonerFeatures;
import com.sinog2c.model.prisoner.TbprisonerResume;
import com.sinog2c.model.prisoner.TbprisonerSocialRelation;
import com.sinog2c.model.prisoner.ZpublicDaMtxx;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.model.system.TbxfPrisonerperformancemerge;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.assessAndPunish.TbxfPrisonerperformancemergeService;
import com.sinog2c.service.api.commutationParole.SentenceAlterationHisService;
import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.flow.FlowOtherFlowService;
import com.sinog2c.service.api.flow.UvFlowService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.ReportUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.report.RMEngine;

@Controller
public class CommuteCaseDocumentsController extends ControllerBase {
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SentenceAlterationHisService sentenceAlterationHisService;
	@Autowired
	private SentenceAlterationService sentenceAlterationService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private FlowOtherFlowService flowOtherFlowService;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbxfPrisonerperformancemergeService tbxfPrisonerperformancemergeService;
	@Autowired
	private TbsysTemplateMapper tbsysTemplateMapper;
	@Autowired
	private SystemResourceService systemResourceService;
	@Autowired
	private UvFlowService uvFlowService;
	@Autowired
	private TbprisonerBaseinfoMapper tbprisonerBaseinfoMapper;
	@Autowired
	private SystemTemplateService systemTemplateService;
	@Autowired
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Autowired
	private FlowArchivesMapper flowarchivesmapper;
	@Autowired
	private FlowMapper flowmapper;
	@Autowired 
	private UvFlowMapper uvflowmapper;

	/**
	 * 
	 * 跳转办案的司法文书页面
	 */
	@RequestMapping(value = "/toJudicialDocumentsPage")
	public ModelAndView toJudicialDocumentsPage(HttpServletRequest request) {
		returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		String toolbar = request.getParameter("toolbar");
		request.setAttribute("crimid", crimid);
		if (StringNumberUtil.notEmpty(toolbar)) {
			request.setAttribute("toolbar", toolbar);
		}
		// 查询出 用户 对应的权限
		SystemUser user = getLoginUser(request);

		View view = new InternalResourceView(
				"WEB-INF/JSP/commutationParole/judicialDocumentsPage.jsp");
		return new ModelAndView(view);
	}

	@RequestMapping(value = "/toSingleJudicialDocumentPage")
	public ModelAndView toSingleJudicialDocumentPage(HttpServletRequest request) {

		returnResourceMap(request);
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String departid = user.getDepartid();// 根据用户Id获取所在部门Id
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		String datatype = request.getParameter("datatype");
		try {
			datatype = java.net.URLDecoder.decode(datatype, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (StringNumberUtil.notEmpty(datatype)) {
			if (datatype.startsWith("formtemplate@")) {

				// 以下为测试formtemplate代码
				JSONArray docconent = new JSONArray();
				String courtsanction = request.getParameter("courtsanction");
				courtsanction = "2014-08-15";
				String action = request.getParameter("action");

				String tempid = "XXBD_JXJS";
				TbsysTemplate template = systemModelService
						.getTemplateAndDepartid(tempid, departid);
				if (template != null) {
					docconent.add(template.getContent());
				}

				Map<String, String> params = new HashMap<String, String>();
				params.put("crimid", crimid);
				params.put("courtsanction", courtsanction);

				Map<String, Object> singleSGMap = sentenceAlterationHisService
						.getSingleSentenceChangeOfCrim(params);

				Map<String, Object> map = new HashMap<String, Object>();
				if (singleSGMap != null) {
					map.put("rewardend", singleSGMap.get("rewardend"));
					map.put("rewardstart", singleSGMap.get("rewardstart"));
					map.put("courtchangeto", singleSGMap.get("courtchangeto"));
					map.put("courtchangefrom", singleSGMap
							.get("courtchangefrom"));
					map
							.put("punishmentyear", singleSGMap
									.get("punishmentyear"));
					map.put("courtchangeyear", singleSGMap
							.get("courtchangeyear"));
					map.put("changetype", singleSGMap.get("changetype"));
					map.put("execdate", singleSGMap.get("execdate"));
					map.put("courtsanction", singleSGMap.get("courtsanction"));
					map.put("crimid", singleSGMap.get("crimid"));
					map.put("courtsn", singleSGMap.get("courtsn"));
					map.put("courtshort", singleSGMap.get("courtshort"));
					map.put("courtyear", singleSGMap.get("courtyear"));
					map.put("courtname", singleSGMap.get("courtname"));
					map.put("name", singleSGMap.get("name"));
				}

				request.setAttribute("formDatajson", new JSONObject(map)
						.toString());
				request.setAttribute("formcontent", docconent.toString());
				// 测试formtemplate代码结束

			} else if (datatype.startsWith("formdata@")) {
				// 以下为测试表单大字段代码
				TbsysDocument td = tbsysDocumentService.getTbsysDocument("440",
						"BWJY_JYTQZYJWZXKC", crimid, "4463");
				request.setAttribute("aipdata", td.getContent());
				// 测试表单大字段代码结束
			} else if (datatype.startsWith("reportdata@")) {
				String[] dtArray = datatype.split("@");
				// 测试报表
				List<Map> list = new ArrayList<Map>();
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("column1", "测试字段11");
				map1.put("column2", "测试字段12");
				map1.put("column3", "测试字段13");
				list.add(map1);

				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("column1", "测试字段21");
				map2.put("column2", "测试字段22");
				map2.put("column3", "测试字段23");
				// 此方法将生成的RMEngine的相关数据放入request中

				ReportUtil.dealReportEngine(list, dtArray[1], "testReport",
						request);
			}
		}

		View view = new InternalResourceView(
				"WEB-INF/JSP/commutationParole/singleJudicialDocument.jsp");
		return new ModelAndView(view);
	}

	@RequestMapping(value = "/getSingleSuggestDocumentData_sx")
	@ResponseBody
	public Object getSingleSuggestDocumentData_sx(HttpServletRequest request)
			throws Exception {
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String departid = user.getDepartid();// 根据用户Id获取所在部门Id
		String crimid = request.getParameter("crimid");
		String flowdraftid = request.getParameter("flowdraftid");
		String flowid = request.getParameter("flowid");
		String tempid = request.getParameter("tempid");
		String flowdefid = request.getParameter("flowdefid");
		String nowpunishmentyear = request.getParameter("nowpunishmentyear");
		String paroletype = request.getParameter("paroletype");
		// 保存检察监督意见书tempid
		String doctype = request.getParameter("doctype");

		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdraftid", flowdraftid);
		if (StringNumberUtil.notEmpty(flowid)) {
			request.setAttribute("flowid", flowid);
		}

		String aipBigdata = "";
		Map<String, Comparable> paramMap = new HashMap<String, Comparable>();
		paramMap.put("tempid", tempid);
		if (StringNumberUtil.notEmpty(flowdraftid)) {
			paramMap.put("flowdraftid", (flowdraftid));
			aipBigdata = flowBaseOtherService
					.getDocconentByFlowdraftid(paramMap);
		}

		/**
		 * 描述：检察监督意见书查询出检察院的案件号进行显示修改
		 */

		paramMap.put("doctype", doctype);
		int caseNumber = 0;
		/**
		 * 如果当前罪犯对应的没有案件号，那么就取最大的+1否则就取当前的
		 */
		if (doctype != null && "casecheck".equals(doctype)) {
			Map caseNumberMap1 = flowBaseOtherService
					.getJCYCaseNumber(paramMap);

			Map flowsMap = new HashMap();
			flowsMap.put("doctype", doctype);
			flowsMap.put("flowdraftid", "");

			Map caseNumberMap2 = flowBaseOtherService
					.getJCYCaseNumber(flowsMap);

			if (Integer.parseInt(caseNumberMap1.get("CASENUMBER").toString()) != 0) {
				caseNumber = Integer.parseInt(caseNumberMap1.get("CASENUMBER")
						.toString());
			} else if (Integer.parseInt(caseNumberMap2.get("CASENUMBER")
					.toString()) != 0) {
				caseNumber = Integer.parseInt(caseNumberMap2.get("CASENUMBER")
						.toString());
			}
		}

		Map map = new HashMap();
		if (StringNumberUtil.notEmpty(aipBigdata)) {
			map.put("annexcontent", aipBigdata);
		} else if (StringNumberUtil.notEmpty(tempid)) {
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("crimid", crimid);
			paraMap.put("flowdefid", flowdefid);
			Map temMap = uvFlowService.getPrisonerParoleType(paraMap);

			if (StringNumberUtil.notEmpty(temMap)) {
				if ("jailcommute".equals(tempid)) {// 如果为监狱减假建议书类型 : jailcommute
					if (StringNumberUtil.notEmpty(paroletype)
							&& (paroletype.equals("0") || paroletype
									.equals("1"))) {
						if (StringNumberUtil.notEmpty(paroletype)
								&& paroletype.equals("1")) {
							tempid = "10021"; // 天津用 有期提请假释建议书
						} else if (StringNumberUtil.notEmpty(nowpunishmentyear)
								&& "9996".equals(nowpunishmentyear.trim())) {
							tempid = "10022"; // 天津用 死缓提请减刑建议书
						} else {
							tempid = "10020"; // 天津用 有期、无期提请减刑建议书
						}
					}
				} else if ("provincecommute".equals(tempid)) {// 如果为省局减假建议书类型 :
					// provincecommute
					if (StringNumberUtil.notEmpty(paroletype)
							&& (paroletype.equals("0") || paroletype
									.equals("1"))) {
						if (StringNumberUtil.notEmpty(nowpunishmentyear)
								&& "9996".equals(nowpunishmentyear.trim())) {
							tempid = "10028"; // 上海用 死缓提请减刑建议书
						} else if (StringNumberUtil.notEmpty(nowpunishmentyear)
								&& "9995".equals(nowpunishmentyear.trim())) {
							tempid = "10029"; // 上海用 无期提请减刑建议书
						} else {
							tempid = "10027"; // 上海用 外籍、处级以上干部提请减刑建议书
						}
					}
				}
			}
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(
					tempid, departid);
			if (null != template) {
				String findid = String.valueOf(template.getFindid());
				String infosql = tbsysTemplateMapper.getSqlText(findid);// 和罪犯相关的信息
				infosql = systemResourceService
						.whereSql(user, infosql, request);
				String result = template.getContent();
				if (StringNumberUtil.notEmpty(infosql)) {
					infosql = infosql.replace("CHR(10)", "\r\n");
					List<Map<String, Object>> list = systemModelService
							.getDocumentContentList(infosql);
					if (null != list && list.size() > 0) {
						result = MapUtil.replaceBracketContent(result, list
								.get(0));
						result = result.replaceAll("\"", "＂");// 把双引号替换成全角的双引号
					}
				}
				map.put("annexcontent", result);
			}
		}
		map.put("caseNumber", caseNumber);
		map.put("tempid", tempid);
		return map;
	}

	@RequestMapping(value = "/toSingleSuggestDocumentPage")
	public ModelAndView toSingleSuggestDocumentPage(HttpServletRequest request)
			throws Exception {
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String departid = user.getDepartid();// 根据用户Id获取所在部门Id

		String crimid = request.getParameter("crimid");
		String flowdraftid = request.getParameter("flowdraftid");
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		String vtype = request.getParameter("vtype");
		String caseno = request.getParameter("caseno");

		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdraftid", flowdraftid);

		String aipBigdata = "";
		String casenum = "";
		if (StringNumberUtil.notEmpty(vtype) && "0".equals(vtype.trim())
				|| !StringNumberUtil.notEmpty(tempid)) {
			Map<String, Comparable> paramMap = new HashMap<String, Comparable>();
			paramMap.put("tempid", "suggestdoc");// 所有建议书类刑的tempid存时都用suggestdoc
			if (StringNumberUtil.notEmpty(flowdraftid)) {
				paramMap.put("flowdraftid", (flowdraftid));
				aipBigdata = flowBaseOtherService
						.getDocconentByFlowdraftid(paramMap);
				// paramMap2.put("flowdraftid", flowdraftid);
			}
		}
		Map<String, Comparable> paramMap = new HashMap<String, Comparable>();
		paramMap.put("tempid", "suggestdoc");// 所有建议书类刑的tempid存时都用suggestdoc
		paramMap.put("flowdraftid", (flowdraftid));
		FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap);
		casenum = fb.getText6();
		if (!StringNumberUtil.isNullOrEmpty(fb.getText6())) {
			try {
				TbprisonerBaseCrime tbprisonerBaseCrime = prisonerService
						.getCrimeByCrimid(crimid);
				SystemOrganization org = systemOrganizationService
						.getByOrganizationId(tbprisonerBaseCrime.getOrgid());
				if (org != null) {
					casenum = "(" + fb.getText6().substring(0, 4) + ")"
							+ org.getName() + "第" + fb.getText6().substring(4)
							+ "号";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(
					tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
			}

			Map<String, String> params = new HashMap<String, String>();
			params.put("crimid", crimid);
			Map sgmap = sentenceAlterationService
					.getSuggestDocumentInfoOfCrim(params);

			Map<String, Object> map = new HashMap<String, Object>();
			if (null != sgmap) {
				map.put("cbiname", sgmap.get("name"));
				map.put("cbiname1", sgmap.get("name"));
				map.put("xingbie", sgmap.get("gender"));// 性别
				map.put("birthday", DateUtil.dateFormatForAip(sgmap
						.get("brithday")));
				map.put("minzu", sgmap.get("nation"));// 民族
				map.put("hj", sgmap.get("registeraddress"));// 户籍
				map.put("huji", sgmap.get("registeraddress"));// 户籍
				map.put("zhuanyou", sgmap.get("maincase"));// 主案由
				map.put("panjuefayuan", sgmap.get("judgmentname"));// 判决法院
				map.put("fayuan", sgmap.get("judgmentname"));// 判决法院
				map.put("panjueriqi", DateUtil.dateFormatForAip(sgmap
						.get("judgedate")));// 判决日期
				map.put("panjuehao", sgmap.get("caseno"));// 判决号
				map.put("shenhehao", sgmap.get("caseno"));// 判决号
				map.put("zi", sgmap.get("casetype"));// 判决字
				map.put("panjuezi", sgmap.get("caseno"));// 判决字
				map.put("anjianhao", casenum);// 判决字

				if (sgmap.get("punishmenttype").equals(GkzxCommon.XINGQI_YOUQI)
						|| sgmap.get("punishmenttype").equals(
								GkzxCommon.XINGQI_YOUQI_ZH)) {
					String strPunishment = StringNumberUtil.isNullOrEmpty(sgmap
							.get("punishmentyear")) ? ""
							: GkzxCommon.ZERO.equals(sgmap
									.get("punishmentyear").toString()) ? ""
									: StringNumberUtil.digit2speech(sgmap.get(
											"punishmentyear").toString())
											+ "年";
					strPunishment += StringNumberUtil.isNullOrEmpty(sgmap
							.get("punishmentmonth")) ? ""
							: GkzxCommon.ZERO.equals(sgmap.get(
									"punishmentmonth").toString()) ? ""
									: StringNumberUtil.digit2speech(sgmap.get(
											"punishmentmonth").toString())
											+ "个月";
					strPunishment += StringNumberUtil.isNullOrEmpty(sgmap
							.get("punishmentday")) ? "" : GkzxCommon.ZERO
							.equals(sgmap.get("punishmentday").toString()) ? ""
							: StringNumberUtil.digit2speech(sgmap.get(
									"punishmentday").toString())
									+ "天";
					if (!StringNumberUtil.isNullOrEmpty(strPunishment)) {
						map.put("zhuxing", GkzxCommon.XINGQI_YOUQI_ZH
								+ strPunishment);// 刑期
					}
				} else if (sgmap.get("punishmenttype").equals(
						GkzxCommon.XINGQI_SIHUAN)
						|| sgmap.get("punishmenttype").equals(
								GkzxCommon.XINGQI_SIHUAN_ZH)) {
					map.put("zhuxing", GkzxCommon.XINGQI_SIHUAN_ZH);
				} else if (sgmap.get("punishmenttype").equals(
						GkzxCommon.XINGQI_WUQI)
						|| sgmap.get("punishmenttype").equals(
								GkzxCommon.XINGQI_WUQI_ZH)) {
					map.put("zhuxing", GkzxCommon.XINGQI_WUQI_ZH);
				} else {
					map.put("zhuxing", "");
				}

				String strLosepower = GkzxCommon.LOSEPOWER_BDZZ;
				if (!StringNumberUtil.isNullOrEmpty(sgmap.get("losepoweryear"))
						&& (sgmap.get("losepoweryear").toString().equals(
								GkzxCommon.LOSEPOWER_97) || sgmap.get(
								"losepoweryear").toString().equals(
								GkzxCommon.LOSEPOWER_99))) {
					strLosepower += GkzxCommon.LOSEPOWER_ZH;
				} else {
					strLosepower += StringNumberUtil.isNullOrEmpty(sgmap
							.get("losepoweryear")) ? "" : GkzxCommon.ZERO
							.equals(sgmap.get("losepoweryear").toString()) ? ""
							: StringNumberUtil.digit2speech(sgmap.get(
									"losepoweryear").toString())
									+ "年";
					strLosepower += StringNumberUtil.isNullOrEmpty(sgmap
							.get("losepowermonth")) ? ""
							: GkzxCommon.ZERO.equals(sgmap
									.get("losepowermonth").toString()) ? ""
									: StringNumberUtil.digit2speech(sgmap.get(
											"losepowermonth").toString())
											+ "个月";
					strLosepower += StringNumberUtil.isNullOrEmpty(sgmap
							.get("losepowereday")) ? "" : GkzxCommon.ZERO
							.equals(sgmap.get("losepowereday").toString()) ? ""
							: StringNumberUtil.digit2speech(sgmap.get(
									"losepowereday").toString())
									+ "天";
				}
				map.put("fujiaxing", strLosepower);// 剥夺年限

				String Sentencestime = "";
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年M月d日");
				if (sgmap.get("punishmenttype").equals(GkzxCommon.XINGQI_YOUQI)
						|| sgmap.get("punishmenttype").equals(
								GkzxCommon.XINGQI_YOUQI_ZH)) {
					Sentencestime += StringNumberUtil.isNullOrEmpty(sgmap
							.get("sentencestime")) ? "" : "从"
							+ sdf2.format(sgmap.get("sentencestime"));
					Sentencestime += StringNumberUtil.isNullOrEmpty(sgmap
							.get("sentenceetime")) ? "" : "起至"
							+ sdf2.format(sgmap.get("sentenceetime"));
					map.put("xingqiqizhi", Sentencestime);// 刑期
				} else {
					map.put("xingqiqizhi", "");// 刑期
				}
				Object inprisondateObj = sgmap.get("inprisondate");
				if (StringNumberUtil.notEmpty(inprisondateObj)) {
					map.put("rujianriqi", DateUtil
							.dateFormatForAip((Date) inprisondateObj));// 入监日期
				}

				map.put("biandongqingkuang", sgmap.get("sentencechageinfo"));// 变动情况
				map.put("xingqibiandongqingkuang", sgmap
						.get("sentencechageinfo"));// 刑期变动情况

			}
			request
					.setAttribute("formDatajson", new JSONObject(map)
							.toString());
			request.setAttribute("formcontent", docconent.toString());
		}

		View view = new InternalResourceView(
				"WEB-INF/JSP/commutationParole/singleSuggestDocument.jsp");
		return new ModelAndView(view);
	}

	@RequestMapping("/getJianChaYuanopinions")
	@ResponseBody
	public Object getJianChaYuanopinions(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		Map map = new HashMap();
		String flowid = request.getParameter("flowid");
		String flowdraftid = request.getParameter("flowdraftid");
		String crimid = request.getParameter("crimid");
		String tempid = request.getParameter("tempid");
		String result = "";
		if(StringNumberUtil.isNullOrEmpty(tempid)){
			map.put("crimid", crimid);
			map.put("flowdraftid", flowdraftid);
			UvFlow flow = uvflowmapper.getJianChaYuanopinions(map);
			if (null!=flow&&!"".equals(flow)) {
				result = flow.getConent();
			}
		} else {
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(
					tempid, user.getDepartid());
			if (null != template) {
				result = template.getContent();
			}
		}
		Map datamap = new HashMap();
		datamap.put("annexcontent", result);
		datamap.put("tempid", tempid);
		return datamap;
	}
	@RequestMapping(value = "/getSingleSuggestDocumentData")
	@ResponseBody
	public Object getSingleSuggestDocumentData(HttpServletRequest request)
			throws Exception {
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String departid = user.getDepartid();// 根据用户Id获取所在部门Id
		String crimid = request.getParameter("crimid");
		String flowdraftid = request.getParameter("flowdraftid");
		String flowid = request.getParameter("flowid");
		String tempid = request.getParameter("tempid");
		String flowdefid = request.getParameter("flowdefid");
		String nowpunishmentyear = request.getParameter("nowpunishmentyear");
		String paroletype = request.getParameter("paroletype");

		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdraftid", flowdraftid);
		if (StringNumberUtil.notEmpty(flowid)) {
			request.setAttribute("flowid", flowid);
		}

		String aipBigdata = "";
		Map<String, Comparable> paramMap = new HashMap<String, Comparable>();
		paramMap.put("tempid", tempid);
		if (StringNumberUtil.notEmpty(flowdraftid)) {
			paramMap.put("flowdraftid", (flowdraftid));
			aipBigdata = flowBaseOtherService.getDocconentByFlowdraftid(paramMap);
		}

		HashMap<String, String> map = new HashMap<String, String>();
		if (StringNumberUtil.notEmpty(aipBigdata)) {
			// 宁夏需要 ohtierid 所以拼接 大字段内容和otherid两个字段，下面需要截图，不影响之前的使用@author
			// mushuhong
			map.put("annexcontent", aipBigdata.split("@")[0]);
			map.put("otherid", aipBigdata.split("@")[1]);
			map.put("suggesttime", aipBigdata.split("@")[2]);
		} else if (StringNumberUtil.notEmpty(tempid)) {
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("crimid", crimid);
			paraMap.put("flowdefid", flowdefid);
			Map temMap = uvFlowService.getPrisonerParoleType(paraMap);

			if (StringNumberUtil.notEmpty(temMap)) {
				if ("jailcommute".equals(tempid)) {// 如果为监狱减假建议书类型 : jailcommute
					if (StringNumberUtil.notEmpty(paroletype)
							&& (paroletype.equals("0") || paroletype
									.equals("1"))) {
						if (StringNumberUtil.notEmpty(paroletype)
								&& paroletype.equals("1")) {
							tempid = "10021"; // 天津用 有期提请假释建议书
						} else if (StringNumberUtil.notEmpty(nowpunishmentyear)
								&& "9996".equals(nowpunishmentyear.trim())) {
							tempid = "10022"; // 天津用 死缓提请减刑建议书
						} else {
							tempid = "10020"; // 天津用 有期、无期提请减刑建议书
							if(GkzxCommon.HUNAN_PROVINCE.equals(provincecode) && "9995".equals(nowpunishmentyear.trim())){
								tempid = "10522";
							}
						}
					}
				} else if ("jailbaowai".equals(tempid)) {// 如果为监狱保外就医建议书类型 :
					if (StringNumberUtil.notEmpty(paroletype)
							&& (paroletype.equals("0") || paroletype.equals("1"))) {
						tempid = "9706";
					}
				}else if ("provincecommute".equals(tempid)) {// 如果为省局减假建议书类型 :
					// provincecommute
					if (StringNumberUtil.notEmpty(paroletype)
							&& (paroletype.equals("0") || paroletype
									.equals("1"))) {
						if (StringNumberUtil.notEmpty(nowpunishmentyear)
								&& "9996".equals(nowpunishmentyear.trim())) {
							tempid = "10028"; // 上海用 死缓提请减刑建议书
						} else if (StringNumberUtil.notEmpty(nowpunishmentyear)
								&& "9995".equals(nowpunishmentyear.trim())) {
							tempid = "10029"; // 上海用 无期提请减刑建议书
						} else {
							tempid = "10027"; // 上海用 外籍、处级以上干部提请减刑建议书
						}
					}
				}
			}
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(
					tempid, departid);
			if (null != template) {
				String findid = String.valueOf(template.getFindid());
				String infosql = tbsysTemplateMapper.getSqlText(findid);// 和罪犯相关的信息
				infosql = systemResourceService
						.whereSql(user, infosql, request);
				String result = template.getContent();
				if (StringNumberUtil.notEmpty(infosql)) {
					infosql = infosql.replace("CHR(10)", "\r\n");
					List<Map<String, Object>> list = systemModelService
							.getDocumentContentList(infosql);
					if (null != list && list.size() > 0) {
						result = MapUtil.replaceBracketContent(result, list
								.get(0));
						result = result.replaceAll("\"", "＂");// 把双引号替换成全角的双引号
					}
				}
				map.put("annexcontent", result);
			}
		}
		map.put("tempid", tempid);
		return map;
	}

	@RequestMapping(value = "/getCourtPageDocumentData")
	@ResponseBody
	public Object getCourtPageDocumentData(HttpServletRequest request)
			throws Exception {

		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String departid = "";// user.getDepartid();//根据用户Id获取所在部门Id

		String crimid = request.getParameter("crimid");
		String flowdraftid = request.getParameter("flowdraftid");
		String flowid = request.getParameter("flowid");
		String tempid = request.getParameter("tempid");
		String otherid = request.getParameter("otherid");

		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdraftid", flowdraftid);
		if (StringNumberUtil.notEmpty(flowid)) {
			request.setAttribute("flowid", flowid);
		}

		FlowBaseOther fbo = null;
		Map<String, Comparable> paramMap = new HashMap<String, Comparable>();
		if (StringNumberUtil.notEmpty(otherid)) {
			fbo = flowBaseOtherService.findById(otherid);
		} else {
			paramMap.put("tempid", tempid);
			if (StringNumberUtil.notEmpty(flowdraftid)) {
				paramMap.put("flowdraftid", (flowdraftid));
				fbo = flowBaseOtherService
						.getLastDocconentByFlowdraftid(paramMap);
			}
		}

		Map<String, Comparable> map = new HashMap<String, Comparable>();
		if (StringNumberUtil.notEmpty(fbo)
				&& StringNumberUtil.notEmpty(fbo.getDocconent())) {
			map.put("annexcontent", fbo.getDocconent());
			map.put("otherid", fbo.getOtherid());
			map.put("modelid", fbo.getText6());
		} else if (StringNumberUtil.notEmpty(tempid)) {

			TbsysTemplate template = systemModelService.getTemplateAndDepartid(
					tempid, departid);
			if (null != template) {
				Properties jypro = new GetProperty().bornProp(
						GkzxCommon.DATABASETYPE, null);
				if (jypro != null) {
					String provincecode = jypro.getProperty("provincecode");
					if ("fy".equals(provincecode)) {// 法院模版打开 判断是私有共有
													// 私有去取TBSYS_COURT_USER_TEMPLATE表中的数据。
						String gt = template.getGeneraltype();
						if (gt != null && "5".equals(gt)) {// 私有
							Map<String, Object> map2 = systemModelService
									.getCourtUserTemplateTextById(tempid, user
											.getUserid());
							if (map2 != null) {
								Clob clob = (Clob) map2.get("TMTEXT");
								if (clob != null) {
									String tmtext = clob.getSubString(1,
											(int) clob.length());
									if (tmtext != null) {
										template.setContent(tmtext);
									}
								}
							}
						}
					}
				}
				String findid = String.valueOf(template.getFindid());
				String infosql = tbsysTemplateMapper.getSqlText(findid);// 和罪犯相关的信息
				infosql = systemResourceService
						.whereSql(user, infosql, request);
				String result = template.getContent();
				if (StringNumberUtil.notEmpty(infosql)) {
					List<Map<String, Object>> list = systemModelService
							.getDocumentContentList(infosql);
					if (null != list && list.size() > 0) {
						result = MapUtil.replaceBracketContent(result, list
								.get(0));
					}
				}
				map.put("annexcontent", result);
			}
		}
		map.put("tempid", tempid);
		return map;
	}

	@RequestMapping(value = "/getNewCourtPageDocumentData")
	@ResponseBody
	public Object getNewCourtPageDocumentData(HttpServletRequest request)
			throws Exception {

		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		// SystemOrganization so = user.getOrganization();
		String departid = user.getDepartid();// 根据用户Id获取所在部门Id

		String crimid = request.getParameter("crimid");
		String flowdraftid = request.getParameter("flowdraftid");
		String flowid = request.getParameter("flowid");
		String tempid = request.getParameter("tempid");
		String flowdefid = request.getParameter("flowdefid");
		String otherid = request.getParameter("otherid");

		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdraftid", flowdraftid);
		if (StringNumberUtil.notEmpty(flowid)) {
			request.setAttribute("flowid", flowid);
		}

		Map<String, String> map = new HashMap<String, String>();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(
				tempid, departid);
		if (null != template) {
			String findid = String.valueOf(template.getFindid());
			String infosql = tbsysTemplateMapper.getSqlText(findid);// 和罪犯相关的信息
			infosql = systemResourceService.whereSql(user, infosql, request);
			String result = template.getContent();
			if (StringNumberUtil.notEmpty(infosql)) {
				List<Map<String, Object>> list = systemModelService
						.getDocumentContentList(infosql);
				if (null != list && list.size() > 0) {
					result = MapUtil.replaceBracketContent(result, list.get(0));
				}
			}
			map.put("annexcontent", result);
		}
		map.put("tempid", tempid);
		return map;
	}

	@RequestMapping(value = "/checkSingleSuggestDocumentData")
	@ResponseBody
	public Object checkSingleSuggestDocumentData(HttpServletRequest request)throws Exception {
		String flowdraftid = request.getParameter("flowdraftid");
		String tempid = request.getParameter("tempid");
		String aipBigdata = getAipBigDataByMap(tempid, flowdraftid);

		// 如果存在大字段则返回1;
		if (StringNumberUtil.notEmpty(aipBigdata)) {
			return "1";
		} else {
			return "-1";
		}
	}
	
	private String getAipBigDataByMap(String tempid, String flowdraftid){
		Map<String, Comparable> paramMap = new HashMap<String, Comparable>();
		paramMap.put("tempid", tempid);
		if (StringNumberUtil.notEmpty(flowdraftid)) {
			paramMap.put("flowdraftid", (flowdraftid));
			return flowBaseOtherService.getDocconentByFlowdraftid(paramMap);
		}
		return null;
	}
	
	/*
	 * 呈批表用
	 */
	@RequestMapping(value = "/toChengBaoReportDocumentPage.action")
	public ModelAndView toChengBaoReportDocumentPage(HttpServletRequest request)
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

		String tempid = "CQAJPSB";
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
				paramMap.put("flowdraftid", (flowdraftid));
			}
			aipBigdata = flowBaseOtherService.getDocconentByFlowdraftid(paramMap);
		}

		if (StringNumberUtil.notEmpty(aipBigdata)) {
			JSONArray docconent = new JSONArray();
			docconent.add(aipBigdata);
			Map<String, Object> map = new HashMap<String, Object>();
			request.setAttribute("formDatajson", new JSONObject(map).toString());
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
				map.put("csdate", DateUtil.dateFormatForAip(tbprisonerBaseinfo.getBirthday()));
				map.put("hjaddre", tbprisonerBaseinfo.getRegisteraddressdetail());
				map.put("xzhuzhi", tbprisonerBaseinfo.getFamilyaddress());
				map.put("jiguan", tbprisonerBaseinfo.getCountryarea());//2015.1.14 by luan  数据交换时交换进Countryarea字段
				map.put("puqianzhiye", tbprisonerBaseinfo.getVocation());
			}
			TbxfSentenceAlteration tbxf = sentenceAlterationService
					.selectByPrimaryKey(crimid);
			if (tbxf != null) {
				map.put("guanyadanwei", tbxf.getJailname()+ tbxf.getAreaname());// 关押监狱、监区
				map.put("sfdate", DateUtil.dateFormatForAip(tbxf.getCourtchangeto()));
			}
			if (tbprisonerBaseCrime != null) {
				map.put("panjuefayuan", tbprisonerBaseCrime.getJudgmentname());// 判决法院
				map.put("jiyariq", DateUtil.dateFormatForAip(tbprisonerBaseCrime.getDetaindate()));// 羁押日期
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

			Map<String, String> paramap = new HashMap<String, String>();
			paramap.put("crimid", crimid);
			// 根据罪犯所在部门id(监狱id）
			String tempdepartid = tbxfPrisonerperformancemergeService
					.getDepartidByCrimid(crimid);
			paramap.put("departid", tempdepartid);
			TbxfPrisonerperformancemerge tp = tbxfPrisonerperformancemergeService
					.selectByCrimidAndBatchid(paramap);
			if (tp != null) {
				map.put("zdlg", tp.getInt6());// 重大立功
				map.put("lgong", tp.getInt5());// 立功
				map.put("shiji", tp.getInt4());// 市级积极
				map.put("jyjj", tp.getInt3());// 监狱积极
				map.put("jyby", tp.getInt2());// 监狱表扬
				map.put("dxiang", tp.getInt1());// 单项
			}
			map.put("qita", 0);// 其它
			SimpleDateFormat s = new SimpleDateFormat("yyyy");
			/*
			 * 退案函 年号和号设置，待修改
			 */
			map.put("text8", s.format(new Date()));
			map.put("text9", 3);
			if (template != null) {
				docconent.add(template.getContent());
			}
			request.setAttribute("formDatajson", new JSONObject(map).toString());
			request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
			request.setAttribute("formcontent", docconent.toString());
		}
		return new ModelAndView("commutationParole/singleChengBaoReport");
	}

	@RequestMapping(value = "/saveSuggestion")
	@ResponseBody
	public Object saveSuggestion(HttpServletRequest request) {
		try {
			SystemUser user = getLoginUser(request);
			String bigdata = request.getParameter("bigdata");
			String tempid = request.getParameter("tempid");
			String crimid = request.getParameter("crimid");
			String flowdraftid = request.getParameter("flowdraftid");
			String flowid = request.getParameter("flowid");
			String otherid = request.getParameter("otherid");
			String caseNumber = request.getParameter("caseNumber");
			String suggesttime = request.getParameter("suggesttime");//建议书显示时间为空时保存当前时间
			if(StringNumberUtil.isNullOrEmpty(suggesttime)){
				suggesttime = DateUtil.dateFormat(new Date(),GkzxCommon.DATEFORMAT);
			}
			return flowBaseOtherService.saveSuggestion(user, bigdata, tempid,
					crimid, flowdraftid, flowid, otherid, caseNumber ,suggesttime);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	/**
	 * 保存案件检查检察院意见到tbflow表
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveCheckCaseSuggest")
	@ResponseBody
	public String saveCheckCaseSuggest(HttpServletRequest request){
		SystemUser user=getLoginUser(request);
		String flowdraftid=request.getParameter("flowdraftid");
		String bigdata=request.getParameter("bigdata");
		String flowid=request.getParameter("flowid");
		Flow flow=new Flow();
		flow.setText1(bigdata);
		flow.setFlowdraftid(flowdraftid);
		flow.setFlowid(flowid);
		flow.setOpid(user.getUserid());
		flow.setOpname(user.getName());
		flow.setOptime(new Date());
		int num=flowmapper.updateByFlowdraftid(flow);
		return num+"";
	}

	/**
	 * 方法描述：宁夏保外建议书预览
	 * 
	 * @author mushuhong
	 * @version 2014年12月18日10:03:21
	 */
	@RequestMapping(value = "/saveSuggestion_nx")
	@ResponseBody
	public Object saveSuggestion_nx(HttpServletRequest request) {
		try {
			SystemUser user = getLoginUser(request);
			String bigdata = request.getParameter("bigdata");
			String tempid = request.getParameter("tempid");
			String crimid = request.getParameter("crimid");
			String flowdraftid = request.getParameter("flowdraftid");
			String flowid = request.getParameter("flowid");
			String otherid = request.getParameter("otherid");
			String caseNumber = request.getParameter("caseNumber");

			return flowBaseOtherService.saveSuggestion_nx(user, bigdata,
					tempid, crimid, flowdraftid, flowid, otherid, caseNumber);

		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	/**
	 * 方法描述：预览建议书 首先判断建议书是否保存过大字段，如果保存，取出Otherid传到表单显示页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/previewSuggestionDoc")
	public ModelAndView previewSuggestionDoc(HttpServletRequest request) {
		returnResourceMap(request);
		SystemUser user = getLoginUser(request);

		RMEngine engine = systemResourceService.queryQualificationDataRmEngine(
				"", user, request);
		request.setAttribute("mydata", engine.dedaoReportData());

		String status = "";
		String otherid = request.getParameter("otherid");// 获取当前会议记录文本的Id
		FlowBaseOther fbo = flowBaseOtherService.findByOtherid(otherid);
		if (fbo != null) {
			otherid = fbo.getOtherid().toString();
			status = GkzxCommon.EDIT;
		} else {
			status = GkzxCommon.NEW;
		}
		request.setAttribute("status", status);
		request.setAttribute("otherid", otherid);
		return new ModelAndView("report/yulandayin");
	}

	@RequestMapping(value = "/saveChengBaoReport")
	@ResponseBody
	public Object saveChengBaoReport(HttpServletRequest request) {

		try {
			SystemUser su = getLoginUser(request);
			String bigdata = request.getParameter("bigdata");
			String tempid = request.getParameter("tempid");
			String flowdraftid = request.getParameter("flowdraftid");
			String flowid = request.getParameter("flowid");

			String otheridStr = "";
			Map<String, Comparable> param = new HashMap<String, Comparable>();
			param.put("tempid", tempid); // 所有建议书类刑的tempid存时都用suggestdoc
			if (StringNumberUtil.notEmpty(flowdraftid)) {
					param.put("flowdraftid", (flowdraftid));
				otheridStr = flowBaseOtherService
						.getOtheridByFlowdraftid(param);
			}

			int rows = 0;
			if (StringNumberUtil.notEmpty(otheridStr)) {
				char[] c = bigdata.toCharArray();
				Map<String, Comparable> map = new HashMap<String, Comparable>();
				map.put("docconent", new String(c));
				map.put("otherid", (otheridStr));
				map.put("opid", su.getUserid());
				map.put("optime", new Date());
				rows = flowBaseOtherService.updateByOtherid(map);

				FlowOtherFlow flowOtherFlow = new FlowOtherFlow();
				flowOtherFlow.setFlowdraftid(flowdraftid);
				if (StringNumberUtil.notEmpty(flowid)) {
					flowOtherFlow.setFlowid(flowid);
				}
				flowOtherFlow.setOpid(su.getUserid());
				flowOtherFlow.setOptime(new Date());
				flowOtherFlow.setOtherid(otheridStr);
				flowOtherFlow.setTempid(tempid);
				rows = flowOtherFlowService.updateByCondition2(flowOtherFlow);

			} else {
				FlowOtherFlow flowOtherFlow = new FlowOtherFlow();
				FlowBaseOther flowBaseOther = new FlowBaseOther();

				String otherid = flowBaseOtherService.getOtherId(su.getDepartid());
				flowBaseOther.setOtherid(otherid);
				flowBaseOther.setDocconent(bigdata);
				flowBaseOther.setSn(Integer.valueOf(GkzxCommon.ONE));
				flowBaseOther.setOpid(su.getUserid());
				flowBaseOther.setOptime(new Date());
				rows = flowBaseOtherService.insert(flowBaseOther);
				// 保存流程与和流程相关的其他文档关系信息表
				flowOtherFlow.setOtherid(otherid);
				flowOtherFlow.setTempid(tempid);// 所有建议书类刑的tempid存时都用suggestdoc
				flowOtherFlow.setOpid(su.getUserid());
				if (StringNumberUtil.notEmpty(flowid)) {
					flowOtherFlow.setFlowid(flowid);
				}
				flowOtherFlow.setFlowdraftid(flowdraftid);

				flowOtherFlow.setOptime(new Date());
				rows = flowOtherFlowService.insert(flowOtherFlow);
			}
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	/**
	 * 跳转办案的监督意见页面 reportName: 广东省监狱监督意见书(监狱用)
	 */
	@RequestMapping(value = "/toSupervisionOpinionPage")
	public ModelAndView toSupervisionOpinionPage(HttpServletRequest request) {

		// returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);

		// 此方法将生成的RMEngine的相关数据放入request中
		ReportUtil.dealReportEngine(null, "广东省监狱监督意见书(监狱用)", "tbCode", request);

		View view = new InternalResourceView(
				"WEB-INF/JSP/commutationParole/supervisionOpinionPage.jsp");
		return new ModelAndView(view);
	}

	/**
	 * 跳转办案的改造评估页面
	 */
	@RequestMapping(value = "/toReformAndAssessmentPage")
	public ModelAndView toReformAndAssessmentPage(HttpServletRequest request) {
		returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String departid = user.getDepartid();// 根据用户Id获取所在部门Id
		JSONArray docconent = new JSONArray();
		String tempid = "SZ_RZHZPGH";
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(
				tempid, departid);
		if (template != null) {
			docconent.add(template.getContent());
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("crimid", crimid);

		Map<String, Object> srMap = null;
		// srMap = someService.getReturnMap(params);//后续补充

		Map<String, Object> map = new HashMap<String, Object>();
		if (srMap != null) {
			map.put("name", srMap.get("name"));// 罪犯姓名
			map.put("crimid", srMap.get("crimid"));// 罪犯编号
			map.put("anyou", srMap.get("anyou"));// 案由
			map.put("sentenceperiod", srMap.get("sentenceperiod"));// 刑期
			map.put("assessmentdate", srMap.get("assessmentdate"));// 评估日期
			map.put("isdenyguilty", srMap.get("isdenyguilty"));// 明确不认罪
			map.put("ishidenguilty", srMap.get("ishidenguilty"));// 隐瞒余罪
			map.put("ishiddeninfo", srMap.get("ishiddeninfo"));// 隐瞒信息
			map.put("attitudeofcourtsentence", srMap
					.get("attitudeofcourtsentence"));// 对法院判决的认识态度
			map.put("confirmofcrimfact", srMap.get("confirmofcrimfact"));// 对犯罪事实的认识
			map.put("recognizeofcrimereason", srMap
					.get("recognizeofcrimereason"));// 对犯罪原因的认识
			map.put("recognizeofcrimeharm", srMap.get("recognizeofcrimeharm"));// 对犯罪危害的认识
			map.put("confessioninfo", srMap.get("confessioninfo"));// 认罪书书写情况
			map.put("finepenaltyexecution", srMap.get("finepenaltyexecution"));// 罚金履行情况
			map.put("illegalattitude", srMap.get("illegalattitude"));// 违法违纪行为态度
			map
					.put("appearanceandactivity", srMap
							.get("appearanceandactivity"));// 现身说法和活动表现
			map.put("reformplan", srMap.get("reformplan"));// 落实改造计划
			map
					.put("contritionperformance", srMap
							.get("contritionperformance"));// 悔罪表现
			map.put("totalsum", srMap.get("totalsum"));// 合计
			map.put("assessmentperson", srMap.get("assessmentperson"));// 评估人
			map.put("examineperson", srMap.get("examineperson"));// 审核人
		}

		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());

		View view = new InternalResourceView(
				"WEB-INF/JSP/commutationParole/reformAndAssessmentPage.jsp");
		return new ModelAndView(view);
	}

	/**
	 * 跳转办案的集体讨论审核表页面
	 */
	@RequestMapping(value = "/toGroupDiscussExaminePage")
	public ModelAndView toGroupDiscussExaminePage(HttpServletRequest request) {
		returnResourceMap(request);
		String crimid = request.getParameter("crimid");
		String flowdraftid = request.getParameter("flowdraftid");
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdraftid", flowdraftid);
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String departid = user.getDepartid();// 根据用户Id获取所在部门Id
		JSONArray docconent = new JSONArray();
		String tempid = "XFZX_JXJS_JTTLSHB";
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(
				tempid, departid);
		if (template != null) {
			docconent.add(template.getContent());
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("crimid", crimid);

		Map<String, Object> srMap = null;
		srMap = this.tbprisonerBaseinfoMapper.getJitiShenHeBiao(params);// 后续补充

		Map<String, Comparable> paramMap3 = new HashMap<String, Comparable>();
		paramMap3.put("flowdraftid", flowdraftid);
		FlowBase fb = flowBaseService.getFlowBaseByFlowdraftid(paramMap3);
		String biaoti = "罪犯减刑假释案件集体讨论审核表";
		if (fb.getInt1() == 0) {
			biaoti = "罪犯减刑案件集体讨论审核表";
		} else if (fb.getInt1() == 1) {
			biaoti = "罪犯假释案件集体讨论审核表";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (srMap != null) {
			map.put("biaoti", biaoti);// 标题
			map.put("sddiscribe", srMap.get("SDDISCRIBE"));// 单位
			map.put("cbiname", srMap.get("CBINAME"));// 姓名
			map.put("cbigendername", srMap.get("CBIGENDERNAME"));// 姓名
			map.put("zlgf", srMap.get("ZLGF"));// 主犯、累犯、惯犯
			map.put("jobs", srMap.get("JOBS"));// 工种
			map.put("cbihomeaddress", srMap.get("HUJIDI"));// 户籍地
			map.put("prisonterm", srMap.get("PRISONTERM"));// 刑期变动
			map.put("anyouhuizong", srMap.get("ANYOUHUIZONG"));// 罪名
			map.put("zhuxing", srMap.get("ZHUXING"));// 原判刑期
			map.put("text1", srMap.get("TEXT1"));// 原判刑期
			String querySql = "	select t.crimid,"
					+ "	       t.REWARDSTART 考核起日, "
					+ "	       t.REWARDEND 考核止日,	 "
					+ "	       (case"
					+ "	         when t.REWARDSTART is not null and t.REWARDEND is not null then case when t.predate is not null then "
					+ "	          '考核期间：' || F_FORMATDATE(trunc(add_months(t.REWARDSTART,0),'mm')) || '至' ||"
					+ "	          F_FORMATDATE(last_day(add_months(t.REWARDEND,-1)))"
					+ "	          else "
					+ "	                  '考核期间：' ||F_FORMATDATE(t.REWARDSTART) || '至' ||	"
					+ "	          F_FORMATDATE(last_day(add_months(t.REWARDEND,-1)))"
					+ "	          end  " + "	         else "
					+ "	          null	" + "	       end) as 考核期间,	"
					+ "	       replace(nvl(t.REWARDINFO, ''),'，','\r\n') 奖励情况,"
					+ "	       nvl(t.PUNISHINFO, '') 惩罚情况 "
					+ "	  from TBXF_SENTENCEALTERATION t "
					+ "	 where t.crimid = '" + crimid + "'";
			String gaizaobiaoxian = systemTemplateService
					.getSystemTemplateByCondition("9993", null).getContent();
			Map contMap = tbxfSentencealterationService
					.selectTbxfMapBySql(querySql);
			gaizaobiaoxian = MapUtil.replaceBracketContent(gaizaobiaoxian,
					contMap);
			gaizaobiaoxian = MapUtil.formatFormString(gaizaobiaoxian);
			map.put("jcmx", gaizaobiaoxian);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String nowdate = sdf.format(new Date());
			map.put("jtpysj", nowdate);// 监区集体评议时间
			map.put("jqhysj", nowdate);// 监区意见时间
		}

		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());

		View view = new InternalResourceView(
				"WEB-INF/JSP/commutationParole/groupDiscussExaminePage.jsp");
		return new ModelAndView(view);
	}
	

	/**
	 * 跳转至建议书管理页面
	 */
	@RequestMapping(value = "/toSuggestionManageListPage.page")
	public ModelAndView toSuggestionPageList(HttpServletRequest request) {
		ModelAndView mav = null;
		returnResourceMap(request);
		String flowdefid = "";
		String modetype=request.getParameter("modetype");
		String menuid=request.getParameter("menuid");
		if(modetype!=""&&modetype!=null&&modetype.equals("jw")){
			flowdefid="other_jybwjycbsp";//罪犯保外就医审批流程定义
			
		}else{
			flowdefid= "other_zfjyjxjssp";// 罪犯减刑假释审批流程定义
		}
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("menuid",menuid);
		View view = new InternalResourceView(
				"WEB-INF/JSP/commutationParole/suggestionManageListPage.jsp");
		mav = new ModelAndView(view);
		return mav;

	}

	/**
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value = "/getCrimSuggestionList")
	@ResponseBody
	public Object getBasicInfoList(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);

		String state = "'-1','0','1','2'";// 流转状态保存：0 完成：1 退回：2 未完成： -1 不同意： 3
		List<Map> data = new ArrayList<Map>();
		Map<String, Object> resultmap = new HashMap<String, Object>();

		try {
			// 取得参数
			String key = request.getParameter("key") == null ? "" : request.getParameter("key");// 检索关键字
			String flowdefid = request.getParameter("flowdefid");

			// 分页
			int pageIndex = (Integer) (request.getParameter("pageIndex") == null ? ""
					: Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer) (request.getParameter("pageSize") == null ? ""
					: Integer.parseInt(request.getParameter("pageSize")));
			// 字段排序
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize - 1;

			// 获取当前菜单ID 对应的自定义流程ID
			String casenums = request.getParameter("casenums");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flowdefid", flowdefid);
			map.put("state", state);
			map.put("key", key);
			map.put("sortField", sortField);
			map.put("sortOrder", sortOrder);
			map.put("start", String.valueOf(start));
			map.put("end", String.valueOf(end));
			if (StringNumberUtil.notEmpty(casenums)) {
				casenums = StringNumberUtil.formatCaseNo(casenums);
				map.put("casenums", casenums);
			}

			if (user != null) {
				String orgid = user.getOrganization().getOrgid();
				String departid = user.getDepartid();
				map.put("orgid", orgid);
				map.put("departid", departid);
			}
			map.put("tempid", "suggestreport");

			// 获取总条数
			int count = sentenceAlterationService.countSuggestionHandleList(map);
			data = sentenceAlterationService.findSuggestionHandleList(map);

			resultmap.put("data", data);
			resultmap.put("total", count);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}

	/*
	 * 档案资料用
	 */
	@RequestMapping(value = "/toArchivalDataDocumentPage.page")
	public ModelAndView toArchivalDataDocumentPage(HttpServletRequest request)throws Exception {
		String crimid = request.getParameter("crimid");
		String flowdraftid = request.getParameter("flowdraftid");
		String menuid = request.getParameter("menuid");
		String flowid = request.getParameter("flowid");
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("menuid", menuid);
		request.setAttribute("flowid", flowid);
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String tempid = "SH_ZFDAZL";
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("tempid", tempid);

		String aipBigdata = "";
		Map<String, Comparable> paramMap = new HashMap<String, Comparable>();
		paramMap.put("tempid", tempid);
		if (StringNumberUtil.notEmpty(flowdraftid)) {
			if ("undefined".equals(flowdraftid)) {
				paramMap.put("flowdraftid", 0);
			} else {
				paramMap.put("flowdraftid", (flowdraftid));
			}
			aipBigdata = flowBaseOtherService.getDocconentByFlowdraftid(paramMap);
		}
		JSONArray docconent = new JSONArray();
		if (StringNumberUtil.notEmpty(aipBigdata)) {
			docconent.add(aipBigdata);
		} else {
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
			if (template != null) {
				docconent.add(template.getContent());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			TbprisonerBaseinfo baseinfo = prisonerService.getBasicInfoByCrimid(crimid);
			TbprisonerBaseCrime baseCrime = prisonerService.getCrimeByCrimid(crimid);
			List<TbprisonerResume> resumeList = prisonerService.findByCrimidResume(crimid);
			TbprisonerFeatures features = prisonerService.selectTbprisonerFeatures(crimid);
			List<TbprisonerSocialRelation> relationList = prisonerService.findRelationBycrimid(crimid);
			//半身照
			Map<String,Object> mtxxMap = new HashMap<String,Object>();
			mtxxMap.put("bh", crimid);
			mtxxMap.put("mtbm", "11");
			mtxxMap.put("mtlb", "1");
			ZpublicDaMtxx baseMtxx = prisonerService.getHeadPicture(mtxxMap); 
			if(baseMtxx != null) {
				map.put("picrjdjimg", baseMtxx.getNr());
			}
			if (baseinfo != null) {
				map.put("criname", baseinfo.getName());
				map.put("usedname", baseinfo.getUsedname());
				map.put("gender", baseinfo.getGender());
				map.put("birthday", DateUtil.dateFormat(baseinfo.getBirthday(),GkzxCommon.DATEFORMAT));
				map.put("nation", baseinfo.getNation());
				map.put("education", baseinfo.getEducation());
				map.put("noweducation", "");//现文化
				map.put("vocation", baseinfo.getVocation());
				map.put("politicalstatus", baseinfo.getPoliticalstatus());
				map.put("specialist", baseinfo.getSpecialist());
				map.put("origin", baseinfo.getCountryarea());
				map.put("originplaceaddress", baseinfo.getRegisteraddressdetail());
				map.put("familyaddress", baseinfo.getFamilyaddress());
				map.put("orgmark", baseinfo.getOrgmark());
				map.put("maritalstatus", baseinfo.getMaritalstatus());
			}
			if (baseCrime != null) {
				map.put("aliasno", baseCrime.getAliasno());
				map.put("arrestauthority", baseCrime.getArrestauthority());
				map.put("arrestdate", DateUtil.dateFormat(baseCrime.getArrestdate(),GkzxCommon.DATEFORMAT));
				map.put("judgmentname", baseCrime.getJudgmentname());
				map.put("judgedate", DateUtil.dateFormat(baseCrime.getJudgedate(),GkzxCommon.DATEFORMAT));
				String zuiming = baseCrime.getCauseaction()== null?"":baseCrime.getCharge();
				map.put("zuiming", zuiming+"罪".replace("罪罪", "罪"));
				String yuanxingqi = baseCrime.getPunishmentyear()+","+(baseCrime.getPunishmentmonth())+","+baseCrime.getPunishmentday();
				yuanxingqi = StringNumberUtil.getXingqi(baseCrime.getPunishmenttype(), yuanxingqi);
				try{
					if(yuanxingqi.contains(GkzxCommon.XINGQI_YOUQI_ZH)){
						yuanxingqi = StringNumberUtil.frontCompWithZore(baseCrime.getPunishmentyear(),2)+"-"+StringNumberUtil.
						frontCompWithZore(baseCrime.getPunishmentmonth(),2)+"-"+StringNumberUtil.frontCompWithZore(baseCrime.getPunishmentday(),2);
					}
				}catch(Exception e){e.printStackTrace();}
				map.put("yuanxingqi", yuanxingqi);
				String yuanbozheng = baseCrime.getLosepoweryear()+","+baseCrime.getLosepowermonth()+","+baseCrime.getLosepowereday();
				yuanbozheng = StringNumberUtil.getBoquan(yuanbozheng);
				if(!yuanbozheng.contains(GkzxCommon.LOSEPOWER_ZH)){
					yuanbozheng = StringNumberUtil.frontCompWithZore(baseCrime.getLosepoweryear(),2)+"-"+StringNumberUtil.
					frontCompWithZore(baseCrime.getLosepowermonth(),2)+"-"+StringNumberUtil.frontCompWithZore(baseCrime.getLosepowereday(),2);
				}
				map.put("yuanbozheng", yuanbozheng);// 剥夺年限
				map.put("inprisondate", DateUtil.dateFormat(baseCrime.getInprisondate(),GkzxCommon.DATEFORMAT));
				map.put("indate", "");//调入日期
				map.put("whereto", baseCrime.getWhereto());
				map.put("orgname", baseCrime.getOrgname1());
				map.put("prdigreenum", baseCrime.getPedigreenum());
				map.put("lieji", "");
				map.put("isrecidivism", Integer.parseInt(baseCrime.getIsrecidivism()+baseCrime.getRecidivist())==0?"否":"是");
				map.put("criminaltype", baseCrime.getCriminaltype());
				map.put("remark", "");//备注
				String crimeface = "";
				if(!StringNumberUtil.isNullOrEmpty(baseCrime.getCrimeface())) 
				crimeface = baseCrime.getCrimeface().replace("&#13;&#10;", "\\r\\n").replace("rn", "\\r\\n").replace("\r\n", "\\r\\n");
				map.put("crimeface", crimeface);
				
				String accomplice = "";//同案犯
				if(!StringNumberUtil.isNullOrEmpty(baseCrime.getCaseorgshort()) && !StringNumberUtil.isNullOrEmpty(baseCrime.getCaseno())){}
				List<Map> accompliceList = prisonerService.getCrimeBaseInfoByCaseNo(crimid,baseCrime.getCaseorgshort(), baseCrime.getCaseno());
				if (!accompliceList.isEmpty() && accompliceList != null && accompliceList.size() > 0) {
					for (Map accmap : accompliceList) {
						accomplice += accmap.get("name")== null?"   ":accmap.get("name")+" ";
						accomplice += accmap.get("birthday")== null?"   ":accmap.get("birthday")+" ";
						accomplice += accmap.get("gender")== null?"   ":accmap.get("gender")+" ";
						accomplice += accmap.get("nation")== null?"   ":accmap.get("nation")+" ";
						accomplice += accmap.get("familyaddress")== null?"   ":accmap.get("familyaddress")+" ";
						accomplice += "\\r\\n";
					}
				}
				map.put("accomplice", accomplice);
			}
			map.put("crimid", crimid);
			Map<String,Object> tbxfMap = tbxfSentencealterationService.getNowYuXing(map);
			if (tbxfMap != null){
				String rewardinfo = "";
				if(!StringNumberUtil.isNullOrEmpty(tbxfMap.get("REWARDINFO"))){
					String[] rewardinfos = tbxfMap.get("REWARDINFO").toString().split("，");
					for (int i=0;i<rewardinfos.length;i++) {
						rewardinfo += rewardinfos[i]+"\\r\\n";;
					}
				}
				map.put("rewardinfo", rewardinfo);  
				String prisonterm = "";
				if(!StringNumberUtil.isNullOrEmpty(tbxfMap.get("SENTENCECHAGEINFOSHORT"))){
					String[] prisonterms = tbxfMap.get("SENTENCECHAGEINFOSHORT").toString().split("，");
					for (int i=0;i<prisonterms.length;i++) {
						prisonterm += prisonterms[i]+"\\r\\n";;
					}
				}
				map.put("prisonterm", prisonterm);//刑期变动
				map.put("crimtype", tbxfMap.get("CRIMTYPE")== null?"否":tbxfMap.get("CRIMTYPE"));//老病残
				map.put("xingqiqizhi", "原起日："+DateUtil.dateFormat((Date) tbxfMap.get("SENTENCESTART"),GkzxCommon.DATEFORMAT)+
						"现止日："+DateUtil.dateFormat((Date) tbxfMap.get("COURTCHANGETO"),GkzxCommon.DATEFORMAT));//现刑期起止
				String xianxingqi = tbxfMap.get("PUNISHMENTYEAR")+","+tbxfMap.get("PUNISHMENTMONTH")+","+tbxfMap.get("PUNISHMENTDAY");
				xianxingqi = StringNumberUtil.getXingqi(baseCrime.getPunishmenttype(), xianxingqi);
				if(xianxingqi.contains(GkzxCommon.XINGQI_YOUQI_ZH)){
					xianxingqi = StringNumberUtil.frontCompWithZore(Integer.parseInt(tbxfMap.get("PUNISHMENTYEAR").toString()),2)+"-"+
					StringNumberUtil.frontCompWithZore(Integer.parseInt(tbxfMap.get("PUNISHMENTMONTH").toString()),2)+"-"+
					StringNumberUtil.frontCompWithZore(Integer.parseInt(tbxfMap.get("PUNISHMENTDAY").toString()),2);
				}
				map.put("xianxingqi", xianxingqi);//现刑期
				String fujiaxing = tbxfMap.get("LOSEPOWERYEAR")+","+tbxfMap.get("LOSEPOWERMONTH")+","+tbxfMap.get("LOSEPOWEREDAY");;
				fujiaxing = StringNumberUtil.getBoquan(fujiaxing);
				if(!fujiaxing.contains(GkzxCommon.LOSEPOWER_ZH)){
					fujiaxing = StringNumberUtil.frontCompWithZore(Integer.parseInt(tbxfMap.get("LOSEPOWERYEAR").toString()),2)+"-"+
					StringNumberUtil.frontCompWithZore(Integer.parseInt(tbxfMap.get("LOSEPOWERMONTH").toString()),2)+"-"+
					StringNumberUtil.frontCompWithZore(Integer.parseInt(tbxfMap.get("LOSEPOWEREDAY").toString()),2);
				}
				map.put("xianbozheng", fujiaxing);//现剥夺年限
			}
			String resume = "";//简历
			if (resumeList != null && resumeList.size() > 0) {
				resume = "起\t\t止\t\t单位\t职业\\r\\n";
				for (TbprisonerResume tr : resumeList) {
					resume += tr.getBegindate()== null?"":tr.getBegindate()+"\t";
					resume += tr.getEnddate()== null?"":tr.getEnddate()+"\t\t";
					resume += tr.getOrgname()== null?"":tr.getOrgname()+"\t\t";
					resume += tr.getVocation()== null?"":tr.getVocation()+"\t";
					resume += "\\r\\n";
				}
			}
			String relation = "";//社会关系
			if (!relationList.isEmpty() && relationList != null && relationList.size() > 0) {
				for (TbprisonerSocialRelation tsr : relationList) {
					relation += tsr.getRelationship()== null?"    ":tsr.getRelationship()+"  ";
					relation += tsr.getName()== null?"    ":tsr.getName()+"  ";
					relation += tsr.getBirthday()== null?"    ":Integer.parseInt(tsr.getBirthday())
							+Integer.parseInt(DateUtil.getAge(baseCrime.getInprisondate()))+"  ";
					relation += tsr.getPolitical()== null?"    ":tsr.getPolitical()+"  ";
					relation += tsr.getHomeaddress()== null?"    ":"住址:"+tsr.getHomeaddress()+"  ";
					relation += tsr.getOrgaddress()== null?"单位:   ":"单位:"+tsr.getOrgaddress();
					relation += tsr.getOrgname()== null?"      ":tsr.getOrgname()+"  ";
					relation += "\\r\\n";
				}
			}
			String feature = "";
			if (!StringNumberUtil.isNullOrEmpty(features)) {
				String height = features.getHeight().toString();
				if(height==null){
					height="无";
				}
				feature += "身高："+height== null?"    ":"身高："+height+"  ";
				String shape = features.getShape();
				if(shape==null){
					shape="无";
				}
				feature += "体型："+shape== null?"    ":"体型："+shape+"  ";
				String bloodtype = features.getBloodtype();
				if(bloodtype==null){
					bloodtype="无";
				}
				feature += "血型："+bloodtype==null?"\\r\\n":"血型："+bloodtype+"\\r\\n";
				String face = features.getFace();
				if(face==null){
					face="无";
				}
				feature += "脸型："+face== null?"    ":"脸型："+face+"  ";
				String footlength=features.getFootlength().toString();
				if(footlength==null){
					footlength="无";
				}
				feature += "足长："+footlength== null?"  ":"足长："+footlength+"  ";
				String feature1 = features.getFeature();
				if(feature1==null){
					feature1="无";
				}
				feature += "特征："+feature1== null?"\\r\\n":"特征："+feature1+"\\r\\n";
				String skin = features.getSkin();
				if(skin==null){
					skin="无";
				}
				feature += "皮肤特殊标记："+skin== null?"":"皮肤特殊标记："+skin+"";
			}
			map.put("resume", resume);
			map.put("relation", relation);
			map.put("feature", feature);
			map.put("jifenkaohe", "");//计分考核
			map.put("nowdate", DateUtil.dateFormatForAip(new Date()));
			map.put("depname", user.getPrison().getFullname()+"罪犯档案资料");
			//各个编辑域没有值的，替换为“无”
			Set<String> keys = map.keySet( );   
			if(keys != null) {   
				Iterator<String> iterator = keys.iterator();
				while(iterator.hasNext()) {   
					Object key = iterator.next(); 
					Object value = map.get(key);  
					if(StringNumberUtil.isNullOrEmpty(value)){
						map.put(key.toString(), "无");
					}
				}  
			}
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("commutationParole/singleChengBaoReport");
	}
}

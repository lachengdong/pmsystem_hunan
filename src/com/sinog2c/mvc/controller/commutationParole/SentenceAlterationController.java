package com.sinog2c.mvc.controller.commutationParole;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import com.gkzx.common.LogCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.commutationParole.TbxfScreening;
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.commutationParole.ZfsqZfsqfd;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysQueryplansql;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.commutationParole.TbxfScreeningService;
import com.sinog2c.service.api.commutationParole.ZfsqZfsqfdService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.QuerySchemeService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

@Controller
@RequestMapping("/commutationParole")
public class SentenceAlterationController extends ControllerBase {
	@Resource
	private SentenceAlterationService sentenceAlterationService;
	@Resource
	private TbxfScreeningService tbxfScreeningService;
	@Resource
	private TbsysDocumentService tbsysDocumentService;
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	@Resource
	private SystemLogService logService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private TbxfSentencealterationService tbxfSentencealterationService;
	@Resource
	private QuerySchemeService querySchemeService;
	@Resource
	private ZfsqZfsqfdService zfsqfdService;

	/**
	 * 跳转列表页
	 * 
	 */
	@RequestMapping(value = "/toSentenceAlterationListPage.action")
	public ModelAndView toSentenceAlterationListPage(HttpServletRequest request) {
		ModelAndView mav = null;
		this.returnResourceMap(request);
		//获取配置文件中的内容（宁夏有资格筛查列表页，有部分内容不需要：mushuhong）
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ningxia = jyconfig.getProperty(GkzxCommon.NINGXIA)==null?"":jyconfig.getProperty(GkzxCommon.NINGXIA);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		request.setAttribute("provincecode", provincecode);
		request.setAttribute("ningxia", 0);
		mav = new ModelAndView("commutationParole/sentenceAlterationListPage");
		
		return mav;
	}

	/**
	 * 方案列表数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetSentenceAlterationList.action")
	@ResponseBody
	public Object ajaxGetSentenceAlterationList(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		
		String apply = request.getParameter("apply");
		String key = request.getParameter("key");
		String crimtype = request.getParameter("crimtype");
		String status = request.getParameter("status");
		String sanclasscrime  = request.getParameter("sanclasscrime");
		String schemeid = request.getParameter("schemeid");
		
		String cstatus=request.getParameter("cstatus");
		HashMap<String, Object> map = new HashMap<String, Object>();
		String schemeCondition = "";
		
		//cstatus : 是否特案办理，0：特案办理
		if(StringNumberUtil.isEmpty(cstatus)) {
			map.put("cstatus",1);
		}else {
			map.put("cstatus", Integer.valueOf(cstatus));
		}
		
		//apply : 是否申报，0：未申报
		if(StringNumberUtil.isEmpty(apply)) {
			map.put("apply", 0);
		}else {
			map.put("apply",  Integer.valueOf(apply));
		}
		
		map.put("key", key);
		map.put("start", start);
		map.put("end", end);
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")== null?"":jyconfig.getProperty("provincecode"));
		map.put("provincecode", provincecode);
		if (sortField != null && !"".equals(sortField)&& "original".equals(sortField)) {
			map.put("originalsortfield", sortField);
		} else if (sortField != null && !"".equals(sortField)&& "punishment".equals(sortField)) {
			map.put("punishmentsortfield", sortField);
		} else if (sortField != null && !"".equals(sortField)&& "nowpunishmentyear".equals(sortField)) {
			map.put("nowpunishmentyearsortfield", sortField);
		} else if (sortField != null && !"".equals(sortField)&& "jianggeqi".equals(sortField)) {
			map.put("intervalfield", sortField);
		} else if (sortField != null && !"".equals(sortField) && "jianxinfudu".equals(sortField)) {
			map.put("jianxinfudufield", sortField);
		} else {
			map.put("sortField", sortField);
		}
		
		map.put("crimtype", crimtype);
		map.put("status", status);
		map.put("sanclasscrime", sanclasscrime);
		map.put("sortOrder", sortOrder);
		map.put("departid", user.getDepartid());
		map.put("orgid", user.getOrgid());
		
		
		if (!StringNumberUtil.isNullOrEmpty(schemeid)) {
			Map<String,Object> screeningMap = new HashMap<String,Object>();
			screeningMap.put("planids", schemeid);
			List<TbsysQueryplansql> schemeSqlList = querySchemeService.getScreeningSchemeSqlListByPlanId(screeningMap);
			for(int i=0; i<schemeSqlList.size(); i++) {
				if(i<schemeSqlList.size()-1) {
					schemeCondition = schemeCondition + schemeSqlList.get(i).getSqltext() + " union " ;
				} else {
					schemeCondition = schemeCondition + schemeSqlList.get(i).getSqltext();
				}
			}
		}
		
		map.put("schemeCondition", schemeCondition);
		
		int total = sentenceAlterationService.getCount(map);
		List<Map<String,Object>> dataList = MapUtil.convertKeyList2LowerCase(sentenceAlterationService.getSentenceAlterationList(map));
		
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(dataList);
		return message;
	}

	/**
	 * 方案列表数据
	 * 方法描述：宁夏资格筛查分离：mushuhong
	 * @version 2014年12月26日09:34:05
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetSentenceAlterationList_nx.action")
	@ResponseBody
	public Object ajaxGetSentenceAlterationList_nx(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String apply = request.getParameter("apply");
		String key = request.getParameter("key");
		String sanclasscrime  = request.getParameter("sanclasscrime");
		String schemeid = request.getParameter("schemeid");
		String sel=request.getParameter("sel");
		int start = pageIndex * pageSize;
		int end = (pageIndex +1) * pageSize;
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(sel==null || "".equals(sel)){
			map.put("sel",0);
		}else{
			map.put("sel", Integer.valueOf(sel));
		}
		map.put("key", key);
		map.put("start", start);
		map.put("end", end);
//		if (sortField != null && !"".equals(sortField)
//				&& "original".equals(sortField)) {
//			map.put("originalsortfield", sortField);
//		} else if (sortField != null && !"".equals(sortField)
//				&& "punishment".equals(sortField)) {
//			map.put("punishmentsortfield", sortField);
//		} else if (sortField != null && !"".equals(sortField)
//				&& "nowpunishmentyear".equals(sortField)) {
//			map.put("nowpunishmentyearsortfield", sortField);
//		} else if (sortField != null && !"".equals(sortField)
//				&& "jianggeqi".equals(sortField)) {
//			map.put("intervalfield", sortField);
//		} else if (sortField != null && !"".equals(sortField)
//				&& "jianxinfudu".equals(sortField)) {
//			map.put("jianxinfudufield", sortField);
//		} else {
//			map.put("sortField", sortField);
//		}
		map.put("sanclasscrime", sanclasscrime);
		map.put("sortOrder", sortOrder);
		map.put("departid", user.getDepartid());
		map.put("orgid", user.getOrgid());
		map.put("apply", apply);
//		if (schemeid != null && !"".equals(schemeid) && "3".equals(schemeid)) {
//			map.put("cstatus", 0);
//			schemeid = "";
//		} else if (schemeid != null && !"".equals(schemeid)) {
//			String schemeidArr[] = schemeid.split(",");
//			String sanleiScheme = "";
//			String orgScheme = "";
//			for (int i = 0; i < schemeidArr.length; i++) {
//				if (schemeidArr[i].length() > 1) {
//					String lastStr = schemeidArr[i].substring(schemeidArr[i]
//							.length() - 3);
//					if (lastStr.equals("001")) {
//						orgScheme = orgScheme
//								+ schemeidArr[i].substring(0, schemeidArr[i]
//										.length() - 3) + ",";
//					} else {
//						sanleiScheme = sanleiScheme
//								+ schemeidArr[i].substring(0, schemeidArr[i]
//										.length() - 3) + ",";
//					}
//				}
//			}
//			Map<String, Object> senidMap = new HashMap<String, Object>();
//			schemeid = "";
//			if (orgScheme != null && !"".equals(orgScheme)) {
//				orgScheme = orgScheme.substring(0, orgScheme.length() - 1);
//				senidMap.put("departid", user.getDepartid());
//				senidMap.put("typeid", "-1,10001");
//				senidMap.put("senid", orgScheme);
//				List<TbxfCommutationReference> orgReflist = this.sentenceAlterationService
//						.getRefids(senidMap);
//				for (int i = 0; i < orgReflist.size(); i++) {
//					schemeid = schemeid + orgReflist.get(i).getRefid() + ",";
//					if (i == orgReflist.size() - 1) {
//						schemeid = schemeid.substring(0, schemeid.length() - 1);
//					}
//				}
//			}
//			if (sanleiScheme != null && !"".equals(sanleiScheme)) {
//				sanleiScheme = sanleiScheme.substring(0,
//						sanleiScheme.length() - 1);
//				senidMap.put("departid", user.getDepartid());
//				senidMap.put("typeid", "10000");
//				senidMap.put("senid", sanleiScheme);
//				List<TbxfCommutationReference> sanleiReflist = this.sentenceAlterationService
//						.getRefids(senidMap);
//				for (int i = 0; i < sanleiReflist.size(); i++) {
//					if (schemeid != null && !"".equals(schemeid) && i == 0) {
//						schemeid = schemeid + ",";
//					}
//					schemeid = schemeid + sanleiReflist.get(i).getRefid() + ",";
//					if (i == sanleiReflist.size() - 1) {
//						schemeid = schemeid.substring(0, schemeid.length() - 1);
//					}
//				}
//			}
//			if (schemeid == null || schemeid.equals("")) {
//				schemeid = "0";
//			}
//			map.put("cstatus", 1);
//		} else {
//			map.put("cstatus", 1);
//		}
		map.put("schemeid", schemeid);
		int total = sentenceAlterationService.getCount_nx(map);
		List<HashMap> list = sentenceAlterationService.getSentenceAlterationList_nx(map);
		//将key转化为小写
		List<Map> dataList = new ArrayList<Map>();
		if (list != null && list.size() > 0) {
			for (HashMap hashMap : list) {
				dataList.add(MapUtil.turnKeyToLowerCase((Map) hashMap));
			}
		}
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(dataList);
		return message;
	}
	/**
	 * 修改/保存大字段
	 * 
	 * @author liuchaoyang 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/saveResourcecruds")
	@ResponseBody
	public int saveGuarantorQualificationsExaminationTable(
			HttpServletRequest request) {
		TbprisonerBaseinfo tbprisonerBaseinfo = new TbprisonerBaseinfo();
		int countnum = 0;// 保存结果：0、失败，1、成功
		short status = 0;
		Date date = new Date();
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		TbsysDocument document = new TbsysDocument();
		String operator = request.getParameter("operator");
		String tempid = request.getParameter("tempid") == null ? "" : request
				.getParameter("tempid");
		String docid = request.getParameter("docid") == null ? "" : request
				.getParameter("docid");
		String content = request.getParameter("content") == null ? "" : request
				.getParameter("content");
		String introduction = "资格筛查申报";// 文书简介
		if ("new".equals(operator)) {
			String crimid = request.getParameter("crimid");
			String name = tbprisonerBaseinfo.getName();
			document.setCrimid(crimid);
			document.setDepartid(user.getDepartid());
			document.setTempid(tempid);
			document.setIntroduction("罪犯" + name + introduction);
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(introduction + LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(introduction + LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(introduction + LogCommon.ADD + LogCommon.EVENT);
			countnum = tbsysDocumentService.saveTbsysDocument(document);
		} else if ("edit".equals(operator)) {
			document.setDocid(Integer.parseInt(docid));
			document.setContent(content);
			document.setOpid(user.getUserid());
			document.setOptime(date);
			log.setLogtype(introduction + LogCommon.OPERATE);
			log.setOpaction(LogCommon.EDIT);
			log.setOpcontent(introduction + LogCommon.EDIT);
			log.setOrgid(user.getUserid());
			log.setRemark(introduction + LogCommon.EDIT + LogCommon.EVENT);
			countnum = tbsysDocumentService.updateTbsysDocument(document);
		}
		String docid1 = "" + document.getDocid();
		request.setAttribute("docid", docid1);
		if (countnum == 1)
			status = 1;// 如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return countnum;
	}

	/**
	 * 申报
	 * 
	 * @param request
	 * @return
	 * @author
	 */
	@RequestMapping(value = "/jianxingshenbao")
	@ResponseBody
	public String jianxingshenbao(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		String isstatus = request.getParameter("isstatus");
		List<String> list = new ArrayList<String>();
		list.add(crimid);
		tbxfScreeningService.updateIsdeclare(list);// 调用 更新申报状态
		if (GkzxCommon.ONE.equals(isstatus)) {
			Map paraMap = new HashMap();
			paraMap.put("crimids", crimid);
			paraMap.put("specialcrim", "1");
			tbxfScreeningService.updateSpecialCrim(paraMap);
		}
		return GkzxCommon.RESULT_SUCCESS;
	}

	/**
	 * 批量申报
	 * 
	 * @author liyufeng
	 */
	@RequestMapping(value = "/piliangshenbao")
	@ResponseBody
	public String piliangshenbao(HttpServletRequest request) {
		String crimids = request.getParameter("crimids");
		String isstatus = request.getParameter("isstatus");
		String[] ids = crimids.split(",");
		List<String> list = java.util.Arrays.asList(ids);
		tbxfScreeningService.updateIsdeclare(list);
		if (GkzxCommon.ONE.equals(isstatus)) {
			Map paraMap = new HashMap();
			paraMap.put("crimids", crimids);
			paraMap.put("specialcrim", "1");
			tbxfScreeningService.updateSpecialCrim(paraMap);
		}
		return GkzxCommon.RESULT_SUCCESS;
	}


	/**
	 * 获取表单数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author wangxf
	 */
	@RequestMapping("/sentenceCommutationparolelist")
	public ModelAndView sentenceCommutationparolelist(HttpServletRequest request) {

		String crimid = request.getParameter("criminalid");
		String tempid = request.getParameter("tempid");

		returnResourceMap(request);
		JSONArray docconent = new JSONArray();

		// 获取当前登录的用户
		SystemUser user = getLoginUser(request);
		String deptid = user.getDepartid();
		// 通过部门id去找所在单位名称
		SystemOrganization org = systemOrganizationService.getByOrganizationId(deptid);

		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());

		int count = tbsysDocumentService.getCount("", tempid, crimid, "");
		if (count == 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (org != null) {
				map.put("text1", org.getName());
			}

			map.put("text2", DateUtil.dateFormatForAip(new Date()));
			map.put("criminalid", crimid);
			TbprisonerBaseinfo baseinfo = prisonerService
					.getBasicInfoByCrimid(crimid);
			TbprisonerBaseCrime baseCrime = prisonerService
					.getCrimeByCrimid(crimid);
			TbxfSentenceAlteration alteration = tbxfSentencealterationService
					.selectByPrimaryKey(crimid);

			map.put("cbiname", baseinfo.getName());
			map.put("cbination", baseinfo.getNation());
			map.put("cbihomeaddress", baseinfo.getFamilyaddress());
			map.put("caieducation", baseinfo.getEducation());
			map.put("aiformervocation", baseinfo.getVocation());
			map.put("cbibirthday", DateUtil.dateFormatForAip(baseinfo.getBirthday()));

			if (baseCrime != null) {

				map.put("criofficiallyplacedate", DateUtil.dateFormatForAip(baseCrime.getInprisondate()));
				map.put("anyouhuizong", baseCrime.getCauseaction());
				String yuanpanxingqi = prisonerService.getSentencedetail(baseCrime.getPunishmentyear(), baseCrime.getPunishmentmonth());
				map.put("yuanpanxingqi", yuanpanxingqi);
				if (null != baseCrime.getSentenceetime())
					map.put("nowcjienddate", DateUtil.dateFormatForAip(baseCrime.getSentenceetime()));
				if (baseCrime.getForfeit() != null
						&& !GkzxCommon.ZERO.equals(baseCrime.getForfeit()))
					map.put("text3", baseCrime.getForfeit() + "元");
				map.put("text4", baseCrime.getPayment());
			}
			if (alteration != null && alteration.getPunishmentyear() != null
					&& !"".equals(alteration.getPunishmentyear())
					&& alteration.getPunishmentmonth() != null
					&& !"".equals(alteration.getPunishmentmonth())) {
				String xxq = prisonerService.getSentencedetail(Integer
						.valueOf(alteration.getPunishmentyear()), Integer
						.valueOf(alteration.getPunishmentmonth()));
				map.put("xxq", xxq);
				map.put("jxmx", alteration.getSentencechageinfo());
			}
			if (template != null) {
				docconent.add(template.getContent());
			}
			request
					.setAttribute("formDatajson", new JSONObject(map)
							.toString());
			request.setAttribute("operator", GkzxCommon.NEW);
		} else {
			TbsysDocument document = tbsysDocumentService.getTbsysDocument(
					null, tempid, crimid, deptid);
			if (document != null) {
				docconent.add(document.getContent());
				request.setAttribute("docid", document.getDocid());
			}
			request.setAttribute("operator", GkzxCommon.EDIT);
		}

		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("tempid", tempid);
		request.setAttribute("crimid", crimid);
		return new ModelAndView("aip/resourcecrud");
	}

	@RequestMapping(value = "/getReferenceList")
	@ResponseBody
	public Object getReferenceList(HttpServletRequest request) {
		return querySchemeService.ajaxScreeningShecmeAll();
	}

	@RequestMapping(value = "/isDeclare")
	public Object isDeclare(HttpServletRequest request) {
		return sentenceAlterationService.getReferenceList(getLoginUser(request)
				.getDepartid());
	}
	
	@RequestMapping(value = "/commutationaccord.action")
	public ModelAndView commutationaccord(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("crimid", crimid);
		map.put("departid", departid);
		Map<String,Object> criMap = tbxfSentencealterationService.getCommutationaccordByCrimid(map);
		System.out.println(criMap.get("CRIMINALINFO").toString().replace("@", "<br>"));
		request.setAttribute("criminalinfo", criMap.get("CRIMINALINFO").toString().replace("@", "<br>"));
		ModelAndView mav = new ModelAndView("commutationParole/commutationaccord");
		return mav;
	}
	
	
	/**
	 * 加载弹出的数据列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loadDataGrid.action")
	@ResponseBody
	public Object loadDataGrid(HttpServletRequest request){
		String type = request.getParameter("type");
		String crimid = request.getParameter("crimid");
		TbprisonerBaseCrime  crime = prisonerService.getCrimeByCrimid(crimid);
		String tmp1 = "";
		if(crime!=null){
			if(GkzxCommon.ONE.equals(type)){
				if(GkzxCommon.ONE.equalsIgnoreCase(crime.getImportantcriminal()) ||
				GkzxCommon.ONE.equalsIgnoreCase(crime.getQitareport())){
					tmp1 = GkzxCommon.ONE;
				}
			}else{
				tmp1=crime.getCasenature();
			}
		}
		return tmp1;
	}
	
	@RequestMapping(value = "saveData.action")
	@ResponseBody
	public Object saveData(HttpServletRequest request){
		int rows = 0;
		Map<String,Object> parmMap = parseParamMap(request);
		rows = tbxfScreeningService.saveData(parmMap);
		return rows;
	}
	//保存罪犯申请幅度
		@RequestMapping(value = "/saveShenQingFuDu.json")
		@ResponseBody
		public int saveShenQingFuDu(HttpServletRequest request){
			int rows = 0;
			SystemUser user = getLoginUser(request);
			String result = request.getParameter("data");
			String crimids = request.getParameter("crimids");
			String isstatus = request.getParameter("isstatus");
			List list = new ArrayList();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("opid", user.getUserid());
			list = (List) JsonUtil.Decode(result);
			if (list != null && list.size() > 0) {
				for(int i=0;i<list.size();i++){
					Map tempmap = (Map)list.get(i);
					map.putAll(tempmap);
					String id = (String)tempmap.get("id");
					if(StringNumberUtil.notEmpty(id))
						rows = zfsqfdService.updateByMap(map);
					else 
						rows = zfsqfdService.insertByMap(map);
				}
			}
			piliangshenbao(request);
			return rows;
		}
	
	@RequestMapping(value="/zfsqfdListNew.page")
	public ModelAndView zfsqfdListNew(HttpServletRequest request){
		Map<String,Object> parmmap = parseParamMap(request);
		request.setAttribute("crimids", parmmap.get("crimids"));
		request.setAttribute("isstatus", parmmap.get("isstatus"));
		ModelAndView mav=new ModelAndView("commutationParole/zfsqfdListNew"); 
		return mav;
	}
	
	@RequestMapping(value="zfsqList.json")
	@ResponseBody
	public Object zfsqList(HttpServletRequest request){
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String key = request.getParameter("key");
		String crimids=request.getParameter("crimids");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("crimids", crimids);
		map.put("start", start);
		map.put("end", end);
		map.put("sortField", sortField);
		map.put("key", key);
		map.put("sortOrder", sortOrder);
		int total = zfsqfdService.getCount(map);
		List<Map<String,Object>> list = zfsqfdService.getSentenceAlterationList(map);
		// 将key转化为小写
		List<Map> dataList = new ArrayList<Map>();
		if (list != null && list.size() > 0) {
			for (Map hashMap : list) {
				dataList.add(MapUtil.turnKeyToLowerCase((Map) hashMap));
			}
		}
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(dataList);
		return message;
	}
	
	/**
	 * 跳转到罪犯申请幅度页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="shenqingpage.page")
	public ModelAndView shenqingpage(HttpServletRequest request){
		String crimid=request.getParameter("crimid") == null? "" : request.getParameter("crimid");
		String isstatus= request.getParameter("isstatus") == null? "" : request.getParameter("isstatus");
		request.setAttribute("crimid", crimid);
		request.setAttribute("isstatus", isstatus);
		
		TbxfSentenceAlteration tbxfSentenceAlteration=tbxfSentencealterationService.selectByPrimaryKey(crimid);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("crimid", crimid);
		map.put("batchid", tbxfSentenceAlteration.getBatchid());
		ZfsqZfsqfd zfsqfd=zfsqfdService.selectByCrimidAndBatchId(map);
		if (zfsqfd!=null) {
			request.setAttribute("fudu", zfsqfd.getFudu());
		}
		String applyname = tbxfSentenceAlteration.getName();
		request.setAttribute("applyname", applyname);
		String yemian = "commutationParole/hunan/shenqingfudu";
		ModelAndView mav= new ModelAndView(yemian);
		return mav;
	}
	/**
	 * 保存申请幅度并申报
	 * @param request
	 * @return
	 */
	@RequestMapping(value="saveFuDu.action")
	@ResponseBody
	public int saveFuDu(HttpServletRequest request){
		SystemUser user=getLoginUser(request);
		int result;
		String crimid=request.getParameter("crimid") == null? "" : request.getParameter("crimid");
		String shenqingfudu = request.getParameter("shenqingfudu") == null? "" : request.getParameter("shenqingfudu");
		TbxfScreening tbxfScreening=tbxfScreeningService.selectByPrimaryKey(crimid);
		String batchid=tbxfScreening.getBatchid().toString();
		
		TbxfSentenceAlteration tbxfSentenceAlteration=tbxfSentencealterationService.selectByPrimaryKey(crimid);
		String name = tbxfSentenceAlteration.getName();
		
		Map map = new HashMap();
		map.put("crimid", crimid);
		map.put("batchid", batchid);
		ZfsqZfsqfd zf=zfsqfdService.selectByCrimidAndBatchId(map);
		if (zf == null) {
			ZfsqZfsqfd zfsqfd=new ZfsqZfsqfd();
			zfsqfd.setCrimid(crimid);
			zfsqfd.setName(name);
			zfsqfd.setFudu(shenqingfudu);
			zfsqfd.setBatchid(batchid);
			zfsqfd.setOpid(user.getUserid());
			zfsqfd.setOptime(new Date());
			result=zfsqfdService.insert(zfsqfd);
		}else {
			ZfsqZfsqfd zfsqfd=zfsqfdService.selectByCrimidAndBatchId(map);
			zfsqfd.setCrimid(crimid);
			zfsqfd.setName(name);
			zfsqfd.setFudu(shenqingfudu);
			zfsqfd.setBatchid(batchid);
			zfsqfd.setOpid(user.getUserid());
			zfsqfd.setOptime(new Date());
			result=zfsqfdService.updateByPrimaryKeySelective(zfsqfd);
		}
		jianxingshenbao(request);
		return result;
	}


}

package com.sinog2c.mvc.controller.publicMain;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.servlet.view.InternalResourceView;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.prisoner.TbprisonerBaseCrimeMapper;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.commutationParole.SentenceAlterationService;
import com.sinog2c.service.api.commutationParole.TbdataSentchageService;
import com.sinog2c.service.api.prisoner.TbxfSentencealterationService;
import com.sinog2c.service.api.system.SignatureSchemeService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;

import flexjson.JSONDeserializer;

@Controller
public class OpinionController extends ControllerBase {
	@Autowired
	SystemCodeService systemCodeService;
	@Autowired
	TbdataSentchageService tbdataSentchageService;
	@Autowired
	TbxfSentencealterationService tbxfSentencealterationService;
	@Autowired
	SentenceAlterationService sentenceAlterationService;
	@Autowired
	SignatureSchemeService signatureSchemeService;
	@Resource
	SystemTemplateService systemTemplateService;
	@Resource
	TbprisonerBaseCrimeMapper tbprisonerBaseCrimeMapper;

	@RequestMapping("/tanChuOpinionPage")
	public ModelAndView choosePage(HttpServletRequest request) throws Exception {
		String strName = request.getParameter("strName");
		String jxjsyj = request.getParameter("jxjsyj");
		String crimid = request.getParameter("crimid");
		Map<String, Object> map = new HashMap<String, Object>();
		String[] yj = new String[20];
		if (null != jxjsyj) {
			jxjsyj = URLDecoder.decode(jxjsyj, "UTF-8");
			yj = jxjsyj.split(",");
			for (String m : yj) {
				String[] arry = m.replace("_", "").split(":");
				String key = arry[0];
				String value = "";
				if (arry.length == 2) {
					value = arry[1];
				}
				map.put(key, value);
			}
		}
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode")==null?"":jyconfig.getProperty("provincecode"));
		request.setAttribute("provincecode", provincecode);
		request.setAttribute("crimid", crimid);
		// 判断是否存在立功或重大立功
		if (crimid != null) {
			List<HashMap<Object, Object>> list = systemCodeService.getAmplitudeData(crimid);
			if (list != null && list.size() > 0) {
				HashMap<Object, Object> jxfdmap = list.get(0);
				if (jxfdmap != null) {
					int lgnum = Integer.parseInt(jxfdmap.get("INT5").toString());
					int zdlgnum = Integer.parseInt(jxfdmap.get("INT6").toString());
					int range = Integer.parseInt(jxfdmap.get("JXCZFD")==null?"0":jxfdmap.get("JXCZFD").toString());
					if(range==0) range = Integer.parseInt(map.get("range").toString()==null?"":"12");//默认减刑幅度为空时，默认一年
					String merit = GkzxCommon.ZERO;
					if (lgnum > 0 || zdlgnum > 0) {
						merit = GkzxCommon.ONE;
					}
					request.setAttribute("merit", merit);// 立功、重大立功
					request.setAttribute("range", range);// 减刑建议幅度
					request.setAttribute("punishmenttype", jxfdmap.get("PUNISHMENTTYPE"));// 原判刑种
					request.setAttribute("specicalcrim", jxfdmap.get("SPECIALCRIM"));// 高院核准犯人
					if(map.get("jxjs1").equals(GkzxCommon.ZERO)&&StringNumberUtil.isNullOrEmpty(map.get("jxjs2"))
							&&StringNumberUtil.isNullOrEmpty(map.get("jxjs3"))&&StringNumberUtil.isNullOrEmpty(map.get("jxjs4"))){
						if((GkzxCommon.XINGQI_WUQI).equals(range)){
							map.put("jxjs2", range);
						}else if(range>=12){
							map.put("jxjs2", range/12);
			       	 		if((range%12)!=0)
			       	 			map.put("jxjs3", range%12);
			       	 	}else if(range<12){	       	 		
			       	 		map.put("jxjs3", range%12);
			       	 	}else{
			       	 		map.put("jxjs2", "1");
			       	 	}
					}
				}
			}
		}
		String resultval = "aip/tanchuOption_sh";
		if(strName.indexOf("sjsuggest") != -1){
			resultval = "aip/sjtanchuOption";
		}
		
		if ("4300".equals(provincecode)) {
			resultval = "aip/tanchuOption_hn";
		}
//		if (GkzxCommon.SHANGHAI_PROVINCE.equals(provincecode)|| GkzxCommon.HUNAN_PROVINCE.equals(provincecode)){
//			resultval = "aip/tanchuOption_sh";
//		}else if(GkzxCommon.HEBEI_PROVINCE.equals(provincecode)){
//			resultval = "aip/tanchuOption_hb";
//		}else if(GkzxCommon.PROVINCE_SHENZHEN.equals(provincecode)){
//			resultval = "aip/tanchuOption_gd";
//		}
			
		ModelAndView mav = new ModelAndView(resultval, "yjmap", map);
		return mav;
	}
	
	
	@RequestMapping("/chooseCriminalPage.page")
	public ModelAndView chooseCriminalPage(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("aip/chooseCriminalPage");
		return mav;
	}
	
	@RequestMapping(value = "/getCommuteRangeInfo.json")
	@ResponseBody
	public Object getCommuteRangeInfo(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		Map<String, Object> map = MapUtil.turnKeyToLowerCase(systemCodeService.getAmplitudeData(crimid).get(0));
		return map;
	}
	
	@RequestMapping(value = "/findContent.json")
	@ResponseBody
	public List<String> findContent(HttpServletRequest request) {
		/*
		 *9560 经办人意见
		 *9453 监区意见（减刑假释
		 *9573 科室意见（减刑假释
		 *9574 监狱委员会意见（减刑假释
		 *10341 监狱意见
		 */
		SystemUser user = getLoginUser(request);
		List<String> listContent = new ArrayList<String>();
		String strs = request.getParameter("tempids");
		String[] str=null;
		if (strs != null) {
			str = strs.split(",");
		}
		String crimid = StringNumberUtil.returnString2(request.getParameter("crimid"));
		if (!StringNumberUtil.isNullOrEmpty(crimid)) {
			for (String s : str) {
				s = tbxfSentencealterationService.getGaizaobiaoxian(crimid, s,user, request);
				listContent.add(s);
			}
		}
		return listContent;
	}

	@RequestMapping("/tanChuOpinionPage_nx")
	public ModelAndView choosePage_nx(HttpServletRequest request)
			throws Exception {
		String jxjsyj = request.getParameter("jxjsyj");
		String crimid = request.getParameter("crimid");
		Map<String, Object> map = new HashMap<String, Object>();
		String[] yj = new String[20];
		if (null != jxjsyj) {
			jxjsyj = URLDecoder.decode(jxjsyj, "UTF-8");
			yj = jxjsyj.split(",");
			for (String m : yj) {
				String[] arry = m.replace("_", "").split(":");
				String key = arry[0];
				String value = "";
				if (arry.length == 2) {
					value = arry[1];
				}
				map.put(key, value);
			}
		}
		Properties jyconfig = new GetProperty().bornProp(
				GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode") == null ? ""
				: jyconfig.getProperty("provincecode"));
		request.setAttribute("provincecode", provincecode);
		// 判断是否存在立功或重大立功
		if (crimid != null) {
			List<HashMap<Object, Object>> list = systemCodeService
					.getAmplitudeData(crimid);
			if (list != null && list.size() > 0) {
				HashMap<Object, Object> jxfdmap = list.get(0);
				if (jxfdmap != null) {
					int lgnum = Integer
							.parseInt(jxfdmap.get("INT5").toString());
					int zdlgnum = Integer.parseInt(jxfdmap.get("INT6")
							.toString());
					String ismerit = GkzxCommon.ZERO;
					if (lgnum > 0 || zdlgnum > 0) {
						ismerit = GkzxCommon.ONE;
					}
					request.setAttribute("merit", ismerit);// 重大立功
					request.setAttribute("range", jxfdmap.get("JXCZFD"));// 减刑建议幅度
				}
			}
		}
		return new ModelAndView(new InternalResourceView(
				"WEB-INF/JSP/aip/tanchuOption_nx.jsp"), "yjmap", map);
	}

	@RequestMapping("/ajaxGetCode.json")
	@ResponseBody
	public Object ajaxGetCode(HttpServletRequest request) {
		String codeType = request.getParameter("codeType");
		return systemCodeService.ajaxGetcodeValueForOpinion(codeType);
	}
	
	@RequestMapping("/ajaxGetCode1.json")
	@ResponseBody
	public Object ajaxGetCode1(HttpServletRequest request) {
		String codeType = request.getParameter("codeType");
		return systemCodeService.ajaxGetcodeValueForOpinion1(codeType);
	}

	@RequestMapping(value = "/gotoWritTypePage")
	public ModelAndView gotoWritTypePage() {
		ModelAndView view = new ModelAndView("aip/gotoWritTypePage");
		return view;
	}

	@RequestMapping(value = "/toCjicourtPage.page")
	public ModelAndView toCjicourtname(HttpServletRequest request)
			throws Exception {
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		ModelAndView view = new ModelAndView("aip/getCjicourts");
		return view;
	}

	// 查询判决法院
	@RequestMapping(value = "/getCjicourt.json")
	@ResponseBody
	public Object cjicourtname(HttpServletRequest request) throws Exception {
		List<Map> list = new ArrayList<Map>();
		String crimid = request.getParameter("crimid");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("crimid", crimid);
		map.put("category", "1");
		list = tbdataSentchageService.selectAllBycrimidAndCategory(map);
		return list;
	}

	@RequestMapping(value = "/getData4pk.json")
	@ResponseBody
	public Object selectAllBycrimidAndCategoryAndCourtsanction(
			HttpServletRequest request) throws Exception {
		String crimid = request.getParameter("crimid");
		String courtsanction = request.getParameter("courtsanction");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("crimid", crimid);
		map.put("category", "1");
		map.put("courtsanction", courtsanction);
		return tbdataSentchageService
				.selectAllBycrimidAndCategoryAndCourtsanction(map);
	}

	@RequestMapping(value = "/getCjicourtNumber.json")
	public ModelAndView getCjicourtNumber(HttpServletRequest request)
			throws Exception {
		String cjicourtzh = request.getParameter("cjicourtzh");
		Map<String, Object> map = new HashMap<String, Object>();
		String[] yj = new String[3];
		if (null != cjicourtzh) {
			cjicourtzh = URLDecoder.decode(cjicourtzh, "UTF-8");
			yj = cjicourtzh.split(",");
			for (String m : yj) {
				String key = m.substring(0, m.indexOf(":"));
				String value = m.substring(m.indexOf(":") + 1);
				map.put(key, value);
			}
		}
		return new ModelAndView("aip/getCjicourtNumber", "yjmap",map);
	}

	@RequestMapping("/fytanChuOpinionPage")
	public ModelAndView fychoosePage(HttpServletRequest request)
			throws Exception {
		String jxjsyj = request.getParameter("jxjsyj");
		Map<String, Object> map = new HashMap<String, Object>();
		String[] yj = new String[20];
		if (null != jxjsyj) {
			jxjsyj = URLDecoder.decode(jxjsyj, "UTF-8");
			yj = jxjsyj.split(",");
			for (String m : yj) {
				String[] arry = m.replace("_", "").split(":");
				String key = arry[0];
				String value = "";
				if (arry.length == 2) {
					value = arry[1];
				}
				map.put(key, value);
			}
		}
		return new ModelAndView(new InternalResourceView(
				"WEB-INF/JSP/aip/fytanchuOption.jsp"), "yjmap", map);
	}
	
	@RequestMapping("/toXingQiSelectPage")
	public ModelAndView toXingQiSelectPage(HttpServletRequest request)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String zhuxingnum = request.getParameter("zhuxingnum");
		String[] zhuxing = zhuxingnum.split(",");
		if(zhuxing.length>0){
			map.put("year", zhuxing[0]);
			map.put("month", zhuxing[1]);
			map.put("day", zhuxing[2]);
		}
		return new ModelAndView("aip/xingqiopinion", "yjmap", map);
	}

	@RequestMapping("/fyShenpiYijiankuang")
	public ModelAndView fyShenpiYijiankuang(HttpServletRequest request)
			throws Exception {
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/court/fyShenpiYijiankuang.jsp"));
	}
	
	/**
	 * 审批意见自动生成理由
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/generateReasonForSuggest")
	@ResponseBody
	public Object generateReasonForSuggest(HttpServletRequest request) {
		String flowdraftid = request.getParameter("flowdraftid");
		String liyou = "";
		if(StringNumberUtil.notEmpty(flowdraftid)){
			Map<String,Object> map = tbprisonerBaseCrimeMapper.getGenerateResonInfo(flowdraftid);
			liyou = StringNumberUtil.generateReasonByMap(map);
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("liyou", liyou);
		return map;
	}
	
	@RequestMapping("/toFyopinionMaintainPage")
	public ModelAndView toFyopinionMaintainPage(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView(new InternalResourceView(
				"WEB-INF/JSP/aip/opinionMaintainPage.jsp"), "yjmap", map);
	}

//	@RequestMapping(value = "/ajaxGetOpinionmaintainList")
//	@ResponseBody
//	public Object ajaxGetOpinionmaintainList(HttpServletRequest request) {
//		SystemUser user = getLoginUser(request);
//		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
//		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
//		int total = tbcourtOpinionmaintainMapper.getCount(user.getUserid());
//		int start = pageIndex * pageSize + 1;
//		int end = start + pageSize - 1;
//		List<Map<String, Object>> list = tbcourtOpinionmaintainMapper
//				.getOpinionmaintainList(user.getUserid(), start, end);
//		JSONMessage message = JSONMessage.newMessage();
//		message.setTotal(total);
//		message.setData(list);
//		return message;
//	}

//	@RequestMapping(value = { "/saveOpinionmaintain" })
//	@ResponseBody
//	public String saveOpinionmaintain(HttpServletRequest request) {
//		String result = "success";
//		short status = 1;
//		SystemUser user = getLoginUser(request);
//		String json = request.getParameter("data");
//		ArrayList rows = (ArrayList) Decode(json);
//		if (null != rows && rows.size() > 0) {
//			for (int i = 0; i < rows.size(); i++) {
//				HashMap row = (HashMap) rows.get(i);
//				TbxfMergeReference tbxfMergeReference = new TbxfMergeReference();
//				TbcourtOpinionmaintain tbcourtOpinionmaintain = new TbcourtOpinionmaintain();
//				tbcourtOpinionmaintain.setOpinioncontent(row.get(
//						"OPINIONCONTENT").toString());
//				tbcourtOpinionmaintain.setOptime(new Timestamp(System
//						.currentTimeMillis()));
//				tbcourtOpinionmaintain.setOpid(user.getUserid());
//				tbcourtOpinionmaintain.setSn(getIntValue(row.get("SN")));
//				tbcourtOpinionmaintain
//						.setOpinionidstate((short) getIntValue(row
//								.get("OPINIONIDSTATE")));
//				tbcourtOpinionmaintain.setOpinionid(getIntValue(row
//						.get("OPINIONID")));
//				tbcourtOpinionmaintain.setRemark(row.get("REMARK")==null?"":row.get("REMARK").toString());
//				if ("added".equals((String) row.get("_state"))) {
//					try {
//						tbcourtOpinionmaintainMapper
//								.insertSelective(tbcourtOpinionmaintain);
//					} catch (Exception e) {
//						e.printStackTrace();
//						result = "error";
//					}
//				} else if ("modified".equals((String) row.get("_state"))) {
//					try {
//						tbcourtOpinionmaintainMapper
//								.updateOpinionmaintainByopinionid(tbcourtOpinionmaintain);
//					} catch (Exception e) {
//						e.printStackTrace();
//						result = "error";
//					}
//				} else if ("removed".equals((String) row.get("_state"))) {
//					try {
//						tbcourtOpinionmaintainMapper
//								.deleteByPrimaryKey(getIntValue(row
//										.get("OPINIONID")));
//					} catch (NumberFormatException e) {
//						e.printStackTrace();
//						result = "error";
//					}
//				}
//			}
//		}
//		return result;
//	}

//	@RequestMapping(value = "/ajaxGetOpinionmaintainSelect")
//	@ResponseBody
//	public Object ajaxGetOpinionmaintainSelect(HttpServletRequest request) {
//		return tbcourtOpinionmaintainMapper
//				.getOpinionmaintainSelect(getLoginUser(request).getUserid());
//	}
//	@RequestMapping(value = "/getPrivateOpinionSelect")
//	@ResponseBody
//	public Object getPrivateOpinionSelect(HttpServletRequest request) {
//		return tbcourtOpinionmaintainMapper
//				.getPrivateOpinionSelect(getLoginUser(request).getUserid());
//	}
//	@RequestMapping(value = "/getCourtSignatureSchemeSelect")
//	@ResponseBody
//	public Object getCourtSignatureSchemeSelect(HttpServletRequest request) {
//		HashMap map = new HashMap();
//		map.put("userid", getLoginUser(request).getUserid());
//		return signatureSchemeService.getCourtSignatureSchemeList(map);
//	}
//	@RequestMapping(value = "/selectFirstSuggest")
//	@ResponseBody
//	public Object selectFirstSuggest(HttpServletRequest request) {
//		 TbcourtOpinionmaintain courtOption  = tbcourtOpinionmaintainMapper
//				.selectFirstSuggest(getLoginUser(request).getUserid());
//		 return courtOption;
//	}
	public Object Decode(String json) {
		if (null == json || "".equals(json))
			return "";
		JSONDeserializer deserializer = new JSONDeserializer();
		Object obj = deserializer.deserialize(json);
		if (obj != null && obj.getClass() == String.class) {
			return Decode(obj.toString());
		}
		return obj;
	}

	public int getIntValue(Object o) {
		int returnValue = -1;
		if (null != o) {
			try {
				if (o instanceof Integer) {
					returnValue = (Integer) o;
				}
				if (o instanceof String) {
					if (!"".equals(o.toString())) {
						returnValue = Integer.valueOf(o.toString());
					}
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				returnValue = -1;
			}
		}
		return returnValue;
	}

	// 监狱保外弹出意见
	@RequestMapping("/bwjyspbTanChuOpinionPage")
	public ModelAndView bwjyspbTanChuOpinionPage(HttpServletRequest request)
			throws Exception {
		
		//宁夏不同于其他地方，所有要进行分离 弹出框内容
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ningxia = jyconfig.getProperty(GkzxCommon.NINGXIA);
		if (ningxia.contains(getLoginUser(request).getDepartid())) {
			String strName = request.getParameter("strName")==null?"":request.getParameter("strName");
			String cbiname = request.getParameter("cbiname")==null?"":URLDecoder.decode(request.getParameter("cbiname"), "utf-8");
			String strContent = request.getParameter("strContent")==null?"":URLDecoder.decode(request.getParameter("strContent"), "utf-8");
			Object object = request.getParameter("bystartdate")==null?new Date():URLDecoder.decode(request.getParameter("bystartdate"),"UTF-8");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			String startDate = "";
			if (!"".equals(strContent)) {
//				System.out.println(format.format(format.parse(object.toString().replace("年", "-").replace("月", "-").replace("日", ""))));
			    //String bwjydate = format.format(format.parse(object.toString().replace("年", "-").replace("月", "-").replace("日", "")));
				//request.setAttribute("bwjy_startDate", bwjydate);
				request.setAttribute("strContent", strContent);
			}

			request.setAttribute("strName", strName);
			request.setAttribute("cbiname", cbiname);
			
			return new ModelAndView("aip/bwjyspbTanChuOption_nx");
		} else {
			String startDate = request.getParameter("bystartdate") == null ? "": URLDecoder.decode(request.getParameter("bystartdate"),"UTF-8");
			String endDate = request.getParameter("byenddate") == null ? "": URLDecoder.decode(request.getParameter("byenddate"), "UTF-8");
			String select3 = request.getParameter("select3") == null ? "": URLDecoder.decode(request.getParameter("select3"), "UTF-8");
			// String unlimit = request.getParameter("unlimit");
			request.setAttribute("bwjy_startDate", startDate);
			request.setAttribute("bwjy_endDate", endDate);
			// request.setAttribute("bwjy_unlimit", unlimit);
			request.setAttribute("bwjy_select3", select3);
			return new ModelAndView("aip/bwjyspbTanChuOption");
		}
	}

	@RequestMapping("/sjtanChuOpinionPage")
	public ModelAndView sjtanChuOpinionPage(HttpServletRequest request)
			throws Exception {
		String jxjsyj = request.getParameter("jxjsyj");
		String crimid = request.getParameter("crimid");
		Map<String, Object> map = new HashMap<String, Object>();
		String[] yj = new String[20];
		if (null != jxjsyj) {
			jxjsyj = URLDecoder.decode(jxjsyj, "UTF-8");
			yj = jxjsyj.split(",");
			for (String m : yj) {
				String[] arry = m.replace("_", "").split(":");
				String key = arry[0];
				String value = "";
				if (arry.length == 2) {
					value = arry[1];
				}
				map.put(key, value);
			}
		}
		// 判断是否存在立功或重大立功
		if (crimid != null) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("crimid", crimid);
			Map sgmap = sentenceAlterationService
					.getSuggestDocumentInfoOfCrim(params);
			if (null != sgmap) {
				request.setAttribute("punishmenttype", sgmap.get("codepunishmenttype"));// 原判刑种
			}
		}
		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = (jyconfig.getProperty("provincecode") == null ? "" : jyconfig.getProperty("provincecode"));
		String resultval = "aip/sjtanchuOption";
		if(GkzxCommon.HEBEI_PROVINCE.equals(provincecode)){
			request.setAttribute("nofudu", "yes");//省局不要建议幅度的flag
			resultval = "aip/tanchuOption_hb";
		}else if(GkzxCommon.PROVINCE_SHENZHEN.equals(provincecode)){
			request.setAttribute("nofudu", "yes");//省局不要建议幅度的flag
			resultval = "aip/sjtanchuOption_gd";
		}
			
		ModelAndView mav = new ModelAndView(resultval, "yjmap", map);
		return mav;
		
	}
	
	
	@RequestMapping("/sjtanChuOpinionPage4GX")
	public ModelAndView sjtanChuOpinionPage4GX(HttpServletRequest request)
			throws Exception {
		String jxjsyj = request.getParameter("jxjsyj");
		String crimid = request.getParameter("crimid");
		Map<String, Object> map = new HashMap<String, Object>();
		String[] yj = new String[20];
		if (null != jxjsyj) {
			jxjsyj = URLDecoder.decode(jxjsyj, "UTF-8");
			yj = jxjsyj.split(",");
			for (String m : yj) {
				String[] arry = m.replace("_", "").split(":");
				String key = arry[0];
				String value = "";
				if (arry.length == 2) {
					value = arry[1];
				}
				map.put(key, value);
			}
		}
		// 判断是否存在立功或重大立功
		if (crimid != null) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("crimid", crimid);
			Map sgmap = sentenceAlterationService
					.getSuggestDocumentInfoOfCrim(params);
			if (null != sgmap) {
				request.setAttribute("punishmenttype", sgmap.get("codepunishmenttype"));// 原判刑种
			}
		}
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/aip/sjtanchuOption4GX.jsp"), "yjmap", map);
	}

	/**
	 * 返回表单运用系统模板的显示内容
	 * 
	 * @return
	 * @author zhenghui
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ajaxGetWindowSelect.json")
	@ResponseBody
	public Object ajaxGetWindowSelect(HttpServletRequest request)
			throws Exception {
		// 用户对象
		SystemUser user = getLoginUser(request);
		List list = systemTemplateService.listByTempid(user, request);
		return list;
	}

	/**
	 * 表单弹出窗口
	 * 
	 * @return
	 * @author zhenghui
	 */
	@RequestMapping("/toGetWindowSelect.page")
	public ModelAndView toGetWindowSelect(HttpServletRequest request)
			throws Exception {
		String winid = request.getParameter("winid");
		request.setAttribute("crimid", request.getParameter("crimid"));
		ModelAndView mav = new ModelAndView("form/tanchuOption", "winid", winid);

		return mav;
	}
	
	@RequestMapping("/towanfPage.page")
	public ModelAndView towanfPage(HttpServletRequest request) throws Exception {
		String cjicriminalsort = request.getParameter("cjicriminalsort");
		ModelAndView mav = new ModelAndView("aip/xinjiang/zflx","cjicriminalsort",cjicriminalsort);
		return mav;
	}
	@RequestMapping("/tobdfdPage.page")
	public ModelAndView tobdfdPage(HttpServletRequest request) throws Exception {
		String citextent = request.getParameter("citextent");
		ModelAndView mav = new ModelAndView("aip/xinjiang/tanchuxqbd","citextent",citextent);
		return mav;
	}
	@RequestMapping("/tobzPage.page")
	public ModelAndView tobzPage(HttpServletRequest request) throws Exception {
		String citchangedisfranchiseto = request.getParameter("citchangedisfranchiseto");
		ModelAndView mav = new ModelAndView("aip/xinjiang/tanchuBz","citchangedisfranchiseto",citchangedisfranchiseto);
		return mav;
	}
	@RequestMapping("/toGuoJiPage.page")
	public ModelAndView toGuoJiPage(HttpServletRequest request) throws Exception {
		String fazhengjiguan = request.getParameter("fazhengjiguan");
		String sfaddress = request.getParameter("sfaddress")==null?"":request.getParameter("sfaddress");//省份
		String sqaddress = request.getParameter("sqaddress")==null?"":request.getParameter("sqaddress");//市区
		String dqxjaddress = request.getParameter("dqxjaddress")==null?"":request.getParameter("dqxjaddress");//地区或县
		String xxdzaddress = request.getParameter("xxdzaddress")==null?"":request.getParameter("xxdzaddress");//详细地址
		String par5 = request.getParameter("par5")==null?"":request.getParameter("par5");
		String par6 = request.getParameter("par6")==null?"":request.getParameter("par6");
		
		xxdzaddress = java.net.URLDecoder.decode(xxdzaddress, "utf-8");
		//java.net.URLDecoder.decode(xxdzaddress, "utf-8");
		System.out.println(java.net.URLDecoder.decode(xxdzaddress, "utf-8"));
		
		request.setAttribute("sfaddress", sfaddress);
		request.setAttribute("sqaddress", sqaddress);
		request.setAttribute("dqxjaddress", dqxjaddress);
		request.setAttribute("xxdzaddress", xxdzaddress);
		request.setAttribute("par5", par5);
		
		
		ModelAndView mav = null;
		if ("yes".equals(par6)) {
			mav = new ModelAndView("aip/xinjiang/getCourtAddress","fazhengjiguan",fazhengjiguan);
		}else{
			mav = new ModelAndView("aip/xinjiang/guoji","fazhengjiguan",fazhengjiguan);
		}
		return mav;
	}
	
	@RequestMapping(value="/getCourtAddress.json")
	public ModelAndView getCourtAddress(HttpServletRequest request) throws Exception{
		
		String sfaddress = request.getParameter("sfaddress")==null?"":request.getParameter("sfaddress");//省份
		String sqaddress = request.getParameter("sqaddress")==null?"":request.getParameter("sqaddress");//市区
		String dqxjaddress = request.getParameter("dqxjaddress")==null?"":request.getParameter("dqxjaddress");//地区或县
		String xxdzaddress = request.getParameter("xxdzaddress")==null?"":request.getParameter("xxdzaddress");//详细地址
		String par5 = request.getParameter("par5")==null?"":request.getParameter("par5");
		
		xxdzaddress = java.net.URLDecoder.decode(xxdzaddress, "utf-8");
		//java.net.URLDecoder.decode(xxdzaddress, "utf-8");
		System.out.println(java.net.URLDecoder.decode(xxdzaddress, "utf-8"));
		
		request.setAttribute("sfaddress", sfaddress);
		request.setAttribute("sqaddress", sqaddress);
		request.setAttribute("dqxjaddress", dqxjaddress);
		request.setAttribute("xxdzaddress", xxdzaddress);
		request.setAttribute("par5", par5);
		
		
		ModelAndView mav = new ModelAndView("aip/xinjiang/getCourtAddress");
		
		return null;
	}
	
	@RequestMapping(value="/getChinaCodeid.json")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getChinaCodeid(HttpServletRequest request){
		String codeType = request.getParameter("codeType");
		List<Map> object = systemCodeService.getCodeByCodeType(codeType,"","");
		for (Map hashMap : object) {
			hashMap.put("codeid", ((Map)hashMap).get("name"));
		}
		return object;
	}
	
	@RequestMapping(value="getCodeByCodeType.json")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getCodeByCodeType(HttpServletRequest request){
		String codeType = request.getParameter("codeType");
		String codeid = request.getParameter("codeid")==null?"":request.getParameter("codeid");
		String pcodeid = request.getParameter("pcodeid")==null?"":request.getParameter("pcodeid");
		List<Map> object = systemCodeService.getCodeByCodeType(codeType,codeid,pcodeid);
		
		List lists = new ArrayList();
		if (!"".equals(pcodeid) || !"".equals(codeid)) {
			for (Map object2 : object) {
				Map maps = new HashMap();
				//把name分别给id和text
				maps.put("id", object2.get("remark"));
				maps.put("text", object2.get("remark"));
				lists.add(maps);
			}
			object.clear();
			object=lists;
		}
		return object;
	}
	
	@RequestMapping(value="getCodeByCode.json")
	@ResponseBody
	@SuppressWarnings("all")
	public Object getCodeByCode(HttpServletRequest request){
		List<Map> object = systemCodeService.getCodeByCode(request,getLoginUser(request));
		List lists = new ArrayList();
		if (object != null) {
			for (Map object2 : object) {
				Map maps = new HashMap();
				//把name分别给id和text
				maps.put("id", object2.get("codeid"));
				maps.put("text", object2.get("name"));
				lists.add(maps);
			}
			object.clear();
			object=lists;
		}
		return object;
	}
	
	@RequestMapping("/toBFQKopinionSelect.page")
	public ModelAndView toBfqkopinionSelect(HttpServletRequest request) throws Exception {
		request.setAttribute("code", request.getParameter("code")==null?"":request.getParameter("code").toString());
		ModelAndView mav = new ModelAndView("khjc/popup/bfqk/bfqkopinionSelect_hunan");
		return mav;
	}
	
	@RequestMapping("/toSlfopinionSelect.page")
	public ModelAndView toSlfopinionSelect(HttpServletRequest request) throws Exception {
		request.setAttribute("code", request.getParameter("code")==null?"":request.getParameter("code").toString());
		ModelAndView mav = new ModelAndView("khjc/popup/slf/slfopinionSelect_hunan");
		return mav;
	}
	
	@RequestMapping("/toZYZFopinionSelect.page")
	public ModelAndView toZYZFopinionSelect(HttpServletRequest request) throws Exception {
		request.setAttribute("code", request.getParameter("code")==null?"":request.getParameter("code"));
		System.out.println(request.getParameter("code"));
		ModelAndView mav = new ModelAndView("khjc/popup/slf/zyzfopinionSelect_hunan");
		return mav;
	}
	
	@RequestMapping("/toQTLXopinionSelect.page")
	public ModelAndView toQTLXopinionSelect(HttpServletRequest request) throws Exception {
		request.setAttribute("code", request.getParameter("code")==null?"":request.getParameter("code"));
		ModelAndView mav = new ModelAndView("khjc/popup/slf/qtlxopinionSelect_hunan");
		return mav;
	}
}

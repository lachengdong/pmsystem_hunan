package com.sinog2c.mvc.controller.penaltyPerform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.system.SignatureScheme;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.system.SignatureSchemeService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.StringNumberUtil;

@Controller
@RequestMapping(value = "/penaltyPerform")
public class BatchSignatureForAipController extends ControllerBase {
	@Autowired
	private SignatureSchemeService signatureSchemeService;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
//	@Autowired
//	private BatchSignatureService batchSignatureService;
	/**
	 * 进入批量签章页面
	 * 
	 * @author shily 2014-8-16 23:41:26
	 */
	@RequestMapping(value = "/batchSignatureforaip.action")
	@SuppressWarnings("all")
	public ModelAndView batchSignature(HttpServletRequest request) {
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String ischeckseal = jyconfig.getProperty("ischeckseal");
		request.setAttribute("ischeckseal", ischeckseal);
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		// 签章日期
		String sealdate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		request.setAttribute("sealdate", sealdate);
		SystemUser user = getLoginUser(request);
		// 单位id，不能用用户的orgid替换
		SystemOrganization org = systemOrganizationService.getByOrganizationId(user.getDepartid());
		String provinceid = org.getPorgid();//也可通过jyconfig.properties获取
		
		Calendar calendar = Calendar.getInstance();//获取当前年
	    int cYear = calendar.get(Calendar.YEAR);
		request.setAttribute("cYear", cYear);
		
		//案件类型
		//年号（此处不能是当前时间，必须是批次的年份）
		//查询出最大的批次年份(然后取当前年，当前年之前的年，当前年之后的年)
		
		List listYear = new ArrayList();
		if(!StringNumberUtil.isNullOrEmpty(type) && type.equals("shengju")) {
			listYear.add(cYear-1);
			listYear.add(cYear);
			listYear.add(cYear+1);
		} else {
			Map mapCuyear = signatureSchemeService.signGetMaxCuryear(request, user);
			String curBatchYear = mapCuyear.get("CURYEAR").toString();
			listYear.add(Integer.parseInt(curBatchYear)-1);
			listYear.add(Integer.parseInt(curBatchYear));
			listYear.add(Integer.parseInt(curBatchYear)+1);
		}
		request.setAttribute("listYear", listYear);
		
		
		// 单位简称
		request.setAttribute("casedepsimplename", org.getShortname());
		String lv = user.getOrganization().getUnitlevel();
		// 签章方案
		HashMap map = new HashMap();
		map.put("userid", user.getUserid());
		map.put("departid", user.getDepartid());
		map.put("provinceid", provinceid);
		List<SignatureScheme> signatureSchemes = null;
		//6,7 是法院的签章内容
		if(lv!=null && ( "6".equals(lv) || "7".equals(lv))){
			signatureSchemes = signatureSchemeService.getCourtSignatureSchemeList(map);
		}else{
			signatureSchemes = signatureSchemeService.getSignatureSchemeList(map);
		}
		request.setAttribute("signatureSchemes", signatureSchemes);
		if("fayuan".equals(type)){
			return new ModelAndView("penaltyPerform/BatchSignatureForFayuan");
		}else{
			return new ModelAndView("penaltyPerform/BatchSignatureForAip");
		}
	}

	/**
	 * 此方法描述的是：获取当前批次所有案件号
	 * 
	 * @param name
	 * @param @return
	 * @Exception 异常对象
	 * @author: 李祺亮
	 * @version: 2013-7-18 下午05:02:37
	 */
	@RequestMapping(value = "/getSignatureSchemeListForAip.json")
	@ResponseBody
	public String ajaxPiCiAnJianHao(HttpServletRequest request) {
		String tempvalueString = "";
		// 处理手填案件号
		String fanwei = request.getParameter("fanwei");
		List<String> fanweilist = new ArrayList<String>();
		try {
			if (null != fanwei && fanwei.length() >= 1) {
				fanwei = fanwei.trim().replaceAll("，", ",");
				String[] str1 = fanwei.split(",");
				for (int i = 0; i < str1.length; i++) {
					String temp = str1[i].trim();
					if (temp.indexOf("-") > -1) {
						String start = temp.split("-")[0].trim();
						String end = temp.split("-")[1].trim();
						if (null != start && null != end && start.length() > 0
								&& end.length() > 0) {
							for (int j = 0; j <= Integer.valueOf(end)- Integer.valueOf(start); j++) {
								fanweilist.add(Integer.valueOf(start) + j + "");
							}
						}
					} else {
						fanweilist.add(temp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0, n = fanweilist.size(); i < n; i++) {
			String aaa = fanweilist.get(i);
			if (i != n - 1) {
				tempvalueString += aaa + ",";
			} else {
				tempvalueString += aaa;
			}
		}
		return tempvalueString;
	}
	/**
	 * 此方法描述的是：获取当前批次所有案件号 宁夏分离
	 * 
	 * @param name
	 * @param @return
	 * @Exception 异常对象
	 * @author: mushuhong
	 * @version: 2013-7-18 下午05:02:37
	 */
	@RequestMapping(value = "/getSignatureSchemeListForAip_nx.json")
	@ResponseBody
	public String ajaxPiCiAnJianHao_nx(HttpServletRequest request) {
		String tempvalueString = "";
		// 处理手填案件号
		String fanwei = request.getParameter("fanwei");
		List<String> fanweilist = new ArrayList<String>();
		try {
			if (null != fanwei && fanwei.length() >= 1) {
				fanwei = fanwei.trim().replaceAll("，", ",");
				String[] str1 = fanwei.split(",");
				for (int i = 0; i < str1.length; i++) {
					String temp = str1[i].trim();
					if (temp.indexOf("-") > -1) {
						String start = temp.split("-")[0].trim();
						String end = temp.split("-")[1].trim();
						if (null != start && null != end && start.length() > 0
								&& end.length() > 0) {
							for (int j = 0; j <= Integer.valueOf(end)- Integer.valueOf(start); j++) {
								fanweilist.add(Integer.valueOf(start) + j + "");
							}
						}
					} else {
						fanweilist.add(temp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0, n = fanweilist.size(); i < n; i++) {
			String aaa = fanweilist.get(i);
			if (i != n - 1) {
				tempvalueString += aaa + ",";
			} else {
				tempvalueString += aaa;
			}
		}
		return tempvalueString;
	}
//	@RequestMapping(value = "/handleAllSealForAip.json")
//	@ResponseBody
//	public Map handleAllSeal(HttpServletRequest request) throws Exception {
//		return batchSignatureService.batchSignature(request);
//	}
//    
//	
//	@RequestMapping(value = "/ajaxsavepiliangqianzhangForAip.json")
//	@ResponseBody
//	public String ajaxsavepiliangqianzhang(HttpServletRequest request) {
//		int result = 1;
//		result = batchSignatureService.savePiLiangQianZhang(request);
//		if(result==1){
//			return GkzxCommon.RESULT_SUCCESS;
//		}else{
//			return GkzxCommon.RESULT_ERROR; 
//		}
//	}
	@RequestMapping(value = "/ajaxsaveSignatureForAip.json")
	@ResponseBody
	public Object ajaxsaveSignatureForAip(HttpServletRequest request){
		String flowdraftid = request.getParameter("flowdraftid");//唯一标识某个罪犯
//		String signature = request.getParameter("signature");//签章的个数，只有在保存的时候 才可以用签章个数
		String operateType = request.getParameter("operateType");//状态(保存，提交，退回)
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("flowdraftid", flowdraftid);
		if ("back".equals(operateType)||"no".equals(operateType)) {
			map.put("signature", this.getMinPregerssByUserid(request)-1);
		}else{
			map.put("signature", this.getSubmitPregerssByUserid(request));
		}
		return flowBaseService.updateSignatureByFlowdraftid(map);
	}
	/**
	 * 方法描述：获取当前 用户 所有的签章方案最小的那个进程数
	 * 当前用户对应的最小签章进程数字减去1，肯定是当前级别退回的签章进程
	 * @author mushuhong
	 * @version 2014年12月9日20:43:54
	 * 
	 */
	@SuppressWarnings("all")
	public int getMinPregerssByUserid (HttpServletRequest request){
		int value = 0;
		Map map = new HashMap();
		map.put("userid", getLoginUser(request).getUserid());
		map.put("flowdefid", request.getParameter("flowdefid"));
		map.put("tempid", request.getParameter("tempid"));
		Map valueMap = flowBaseService.getMinPregerssByUserid(map);
		
		if (valueMap.size() > 0) {
			value = Integer.parseInt(valueMap.get("PROGRESS").toString());
		}
		
		return value;
	}
	/**
	 * 方法描述：获取当前 用户提交时 签章方案的那个进程数
	 * @author YangZR
	 * @version 2015年01月20日20:43:54
	 * 
	 */
	@SuppressWarnings("all")
	public int getSubmitPregerssByUserid (HttpServletRequest request){
		int value = 0;
		Map map = new HashMap();
		SystemUser su = getLoginUser(request);
		map.put("userid", su.getUserid());
//		map.put("departid", su.getDepartid());
		map.put("flowdefid", request.getParameter("flowdefid"));
		map.put("tempid", request.getParameter("tempid"));
		String signnumbers = request.getParameter("signature");
		if(StringNumberUtil.notEmpty(signnumbers)){
			map.put("signnumbers", Integer.parseInt(signnumbers));
		}
		Map valueMap = flowBaseService.getSubmitPregerssByUserid(map);
		
		if (StringNumberUtil.notEmpty(valueMap)&&valueMap.size() > 0) {
			value = Integer.parseInt(valueMap.get("PROGRESS").toString());
		}
		
		return value;
	}
	
	/**
	 * 方法描述：查询当前用户 可以批注多少个
	 * @author mushuhong
	 * @version 2014年12月8日15:16:34
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@RequestMapping(value = "/ajaxNonationNumber")
	@ResponseBody
	public Object ajaxNonationNumber(HttpServletRequest request){
		Object number = flowBaseService.ajaxNonationNumberImpl(request,getLoginUser(request));
		
		return number;
	}
	
	/**
	 * 方法描述：获取小于当前级别的章的个数
	 * @author mushuhong
	 * @version 2014年12月9日19:58:19
	 */
	@RequestMapping(value="/ajaxNoShowNonationNumber")
	@ResponseBody
	public Object ajaxNoShowNonationNumber(HttpServletRequest request){
		Object number = flowBaseService.ajaxNoShowNonationNumber(request,getLoginUser(request));
		return number;
	}
	public String ClobToString(Clob clob) throws SQLException, IOException {

		String reString = "";
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			sb.append(s);
			s = br.readLine();
		}
		reString = sb.toString();
		return reString;
	}
//	/**
//	 * 方法描述：进入批量签章页面 分离宁夏 
//	 * @author mushuhong
//	 * @version 2015年1月8日19:09:44
//	 */
//	/**
//	 * 进入批量签章页面
//	 * 
//	 * @author shily 2014-8-16 23:41:26
//	 */
//	@RequestMapping(value = "/batchSignatureforaip_nx.action")
//	@SuppressWarnings("all")
//	public ModelAndView batchSignature_nx(HttpServletRequest request) {
//		String type = request.getParameter("type");
//		request.setAttribute("type", type);
//		// 签章日期
//		String sealdate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar
//				.getInstance().getTime());
//		request.setAttribute("sealdate", sealdate);
//		SystemUser user = getLoginUser(request);
//		// 单位id，不能用用户的orgid替换
//		SystemOrganization org = systemOrganizationService.getByOrganizationId(user.getDepartid());
//		//案件类型
//		//年号（此处不能是当前时间，必须是批次的年份）
//		//查询出最大的批次年份(然后取当前年，当前年之前的年，当前年之后的年)
//		Map mapCuyear = signatureSchemeService.signGetMaxCuryear(request, user);
//		String curyear = mapCuyear.get("CURYEAR").toString();
//		List listYear = new ArrayList();
//		listYear.add(Integer.parseInt(curyear)-1);
//		listYear.add(Integer.parseInt(curyear));
//		listYear.add(Integer.parseInt(curyear)+1);
//		Calendar c = Calendar.getInstance();
//		
////		request.setAttribute("caseyear", String.valueOf(c.get(Calendar.YEAR)));
//		
//		request.setAttribute("listYear", listYear);
//		// 单位简称
//		request.setAttribute("casedepsimplename", org.getShortname());
//		String lv = user.getOrganization().getUnitlevel();
//		// 签章方案
//		HashMap map = new HashMap();
//		map.put("userid", user.getUserid());
//		map.put("departid", user.getDepartid());
//		List<SignatureScheme> signatureSchemes = null;
//		//6,7 是法院的签章内容
//		if(lv!=null && ( "6".equals(lv) || "7".equals(lv))){
//			signatureSchemes = signatureSchemeService.getCourtSignatureSchemeList(map);
//		}else{
//			signatureSchemes = signatureSchemeService.getSignatureSchemeList_nx(map);
//		}
//		request.setAttribute("signatureSchemes", signatureSchemes);
//		if("fayuan".equals(type)){
//			return new ModelAndView("penaltyPerform/BatchSignatureForFayuan");
//		}else{
//			return new ModelAndView("penaltyPerform/BatchSignatureForAip_nx");
//		}
//	}
//	/**
//     * 方法描述：批量签章宁夏分离
//     * @author mushuhong
//     * @version 2015年1月8日19:20:58
//     * @param request
//     * @return
//     * @throws Exception
//     */
//	@RequestMapping(value = "/handleAllSealForAip_nx.json")
//	@ResponseBody
//	public Map handleAllSeal_nx(HttpServletRequest request) throws Exception {
//		return batchSignatureService.batchSignature_nx(request);
//	}
//	 /**
//     * 方法描述：保存签章文件，宁夏分离
//     * @param request
//     * @return
//     */
//	@RequestMapping(value = "/ajaxsavepiliangqianzhangForAip_nx.json")
//	@ResponseBody
//	public String ajaxsavepiliangqianzhang_nx(HttpServletRequest request) {
//		int result = 0;
//		String aipFileStr = request.getParameter("aipFileStr");
//		String otherFileStr = request.getParameter("otherAipFileStr");
//		if(aipFileStr!=null&&!aipFileStr.equals("")){
//			String otherid = request.getParameter("otherid");
//			FlowBaseOther other = new FlowBaseOther();
//			other.setDocconent(aipFileStr);
//			other.setOtherid(otherid);
//			
//			result = flowBaseOtherService.updateMeetContextByOhterid_nx(other);
//		}else if(otherFileStr!=null&&!otherFileStr.equals("")){
//			String docidStr = request.getParameter("docid");
//			String jyotheridStr = request.getParameter("jyotherid");
//			if(!StringNumberUtil.isNullOrEmpty(docidStr)){
//				int docid = Integer.parseInt(docidStr);
//				TbsysDocument document = new TbsysDocument();
//				document.setContent(otherFileStr);
//				document.setDocid(docid);
//				
//				result = tbsysDocumentService.updateTbsysDocumentByDocId_nx(document);
//			}else if(!StringNumberUtil.isNullOrEmpty(jyotheridStr)){
//				String jyotherid = jyotheridStr;
//				FlowBaseOther other = new FlowBaseOther();
//				other.setDocconent(otherFileStr);
//				other.setOtherid(jyotherid);
//				
//				result = flowBaseOtherService.updateMeetContextByOhterid_nx(other);
//			}
//		}
//		if(result==1){
//			return GkzxCommon.RESULT_SUCCESS;
//		}else{
//			return GkzxCommon.RESULT_ERROR; 
//		}
//	}
	
}

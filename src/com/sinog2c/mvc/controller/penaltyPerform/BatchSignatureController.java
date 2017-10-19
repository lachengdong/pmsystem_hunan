package com.sinog2c.mvc.controller.penaltyPerform;
 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.system.SignatureScheme;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.system.SignatureSchemeService;
import com.sinog2c.service.api.system.SystemOrganizationService;


@Controller
@RequestMapping(value = "/penaltyPerform")
public class BatchSignatureController extends ControllerBase {
	@Autowired
	private SignatureSchemeService signatureSchemeService;
	@Autowired
	private FlowBaseOtherService flowBaseOtherService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	/**
	 * 进入批量签章页面
	 * 
	 * @author shily 2014-8-16 23:41:26
	 */
	@RequestMapping(value = "/batchSignature.action")
	public ModelAndView batchSignature(HttpServletRequest request) {
		// 签章日期
		String sealdate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar
				.getInstance().getTime());
		request.setAttribute("sealdate", sealdate);
		SystemUser user = getLoginUser(request);
		//单位id，不能用用户的orgid替换
		SystemOrganization org = systemOrganizationService.getByOrganizationId(user.getDepartid());
		// //案件类型
		// 年号
		request.setAttribute("casedepsimplename", org.getShortname());
		Calendar c = Calendar.getInstance();
		request.setAttribute("caseyear", String.valueOf(c.get(Calendar.YEAR)));
		// 单位简称
		
		//签章方案
		HashMap map = null;
		List<SignatureScheme> signatureSchemes = signatureSchemeService.getListAll(map);
		request.setAttribute("signatureSchemes", signatureSchemes);
		
		return new ModelAndView("penaltyPerform/BatchSignature");
	}

	/**
	 * 进入法院批量签章页面
	 *  ！！！更新完就不好使了，这个方法也分出来了
	 */
	@RequestMapping(value = "/batchSignatureforFayuan.action")
	@SuppressWarnings("all")
	public ModelAndView batchSignatureforFayuan(HttpServletRequest request) {
		String type = request.getParameter("type");
		// 签章日期
		String sealdate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar
				.getInstance().getTime());
		request.setAttribute("sealdate", sealdate);
		SystemUser user = getLoginUser(request);
		// 单位id，不能用用户的orgid替换
		SystemOrganization org = systemOrganizationService
				.getByOrganizationId(user.getDepartid());
		//案件类型
		// 年号
		Calendar c = Calendar.getInstance();
		request.setAttribute("caseyear", String.valueOf(c.get(Calendar.YEAR)));
		// 单位简称
		request.setAttribute("casedepsimplename", org.getShortname());
		String lv = user.getOrganization().getUnitlevel();
		// 签章方案
		HashMap map = new HashMap();
		map.put("userid", user.getUserid());
		List<SignatureScheme> signatureSchemes = null;
		if(lv!=null && ( "6".equals(lv) || "7".equals(lv))){
			signatureSchemes = signatureSchemeService.getCourtSignatureSchemeList(map);
		}else{
			signatureSchemes = signatureSchemeService.getSignatureSchemeList(map);
		}
		request.setAttribute("signatureSchemes", signatureSchemes);
		if("shengju".equals(type)){
			return new ModelAndView("penaltyPerform/BatchSignatureForShengju");
		}else if("fayuan".equals(type)){
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
	@RequestMapping(value = "/getSignatureSchemeList.json")
	@ResponseBody
	public String ajaxPiCiAnJianHao(HttpServletRequest request) {
		String tempvalueString = "";
		// 处理手填案件号
		String fanwei = request.getParameter("fanwei");
		List<String> fanweilist = new ArrayList<String>();
		try {
			if (null != fanwei && fanwei.length() >= 1) {
				String[] str1 = fanwei.split(",");
				for (int i = 0; i < str1.length; i++) {
					if (str1[i].indexOf("-") > -1) {
						String start = str1[i].split("-")[0];
						String end = str1[i].split("-")[1];
						if (null != start && null != end && start.length() > 0
								&& end.length() > 0) {
							for (int j = 0; j <= Integer.valueOf(end)
									- Integer.valueOf(start); j++) {
								fanweilist.add(Integer.valueOf(start) + j + "");
							}
						}
					} else {
						fanweilist.add(str1[i]);
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

	@RequestMapping(value = "/handleAllSeal.json")
	@ResponseBody
	public Map handleAllSeal(HttpServletRequest request) throws Exception {
		// 获取报表数据的条件
		String tempid = request.getParameter("tempid");
		String year = request.getParameter("caseyear");
		String sdnameforshort = request.getParameter("casedepsimplename");
		String cpscasetype = request.getParameter("casetype");
		if("jxjs".equals(cpscasetype)){
			cpscasetype = GkzxCommon.CASE_TYPE_JXJS;
		}

		String cpdnumber = request.getParameter("cpdnumber").replaceAll("\"", "");
		String remark = request.getParameter("fanganname");// 签章文书的名称
		
		SystemUser user = this.getLoginUser(request);
		//根据案件号、单位、流程自定义id查询表单
		HashMap pmap = new HashMap();
		pmap.put("departid", user.getDepartid());
		pmap.put("casenum", ""+year+cpdnumber);
		//流程自定义id
		pmap.put("flowdefid", tempid);
		//模板id
		pmap.put("tempid", "JXJS_JXJSSHB");
		
		FlowBaseOther other = flowBaseOtherService.selectByCpdnumber(pmap);
		
		String showstr1 = "签章成功!\t"
				+ new java.text.SimpleDateFormat(GkzxCommon.DATETIMEFORMAT)
						.format(new Date());
//		String showstr2 = "签章失败";
//		String showstr3 = "您已经签过了";
//		String showstr4 = "签章方案配置不正确";
		String redstr1 = "<span style='color:red'> ";
		String redstr2 = "</span> ";
		String messageString = "【(" + year + ")" + sdnameforshort + cpscasetype
				+ "第" + redstr1 + cpdnumber + redstr2 + "号】案件的司法文书  ";

		
		Map map = new HashMap();
		if(other!=null){
			try {
				map.put("aipFileStr", other.getDocconent());
				map.put("title", messageString + redstr1
						+ remark + redstr2+showstr1);
				map.put("keyiqianzhangString", "yes");
				map.put("baobiaobiaodanString", "biaodan");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			map.put("title", messageString + "不存在");
		}
		return map;
	}

	@RequestMapping(value = "/ajaxsavepiliangqianzhang.json")
	@ResponseBody
	public String ajaxsavepiliangqianzhang(HttpServletRequest request) {
		String tempid = request.getParameter("tempid");
		String year = request.getParameter("caseyear");		
		String cpdnumber = request.getParameter("cpdnumber").replaceAll("\"", "");
		String aipFileStr = request.getParameter("aipFileStr");
		
		SystemUser user = this.getLoginUser(request);
		
		FlowBaseOther other = new FlowBaseOther();
		other.setDocconent(aipFileStr);
		other.setText1(user.getDepartid());
		other.setText2(""+year+cpdnumber);
		other.setText3(tempid);
		other.setText4("JXJS_JXJSSHB");
		
		int result = flowBaseOtherService.updateByCpdnumber(other);
		if(result==1){
			return GkzxCommon.RESULT_SUCCESS;
		}else{
			return GkzxCommon.RESULT_ERROR; 
		}
	}
	/**
	 * 方法描述：把案件导出为pdf文件
	 * @version 2014年1月3日19:52:29
	 * @auhtor mushuhong
	 */
	@RequestMapping(value="/batchExportHandCaseFile.json")
	@ResponseBody
	@SuppressWarnings("all")
	public List<Map> batchExportHandCaseFile(HttpServletRequest request){
		SystemUser user = this.getLoginUser(request);
		List<Map> mapsMap = signatureSchemeService.batchExportHandCaseFile(request,user);
		
		return mapsMap;
	}
	/**
	 * 方法描述：把pdf文件 保存到服务器上面
	 * @version 2014年9月4日16:24:52
	 * @author mushuhong
	 * 
	 */
	@RequestMapping(value="/savePDFDocToService")
	@SuppressWarnings("all")
	public String savePDFDocToService(HttpServletRequest request){
		this.signatureSchemeService.savePDFDocToService(request);
		return null;
	}
}

package com.sinog2c.mvc.controller.system;

import java.util.Calendar;
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

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.system.PrintScheme;
import com.sinog2c.model.system.SignatureScheme;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.system.PrintBatchService;
import com.sinog2c.service.api.system.PrintSchemeService;
import com.sinog2c.service.api.system.SignatureSchemeService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.StringNumberUtil;

@Controller
@RequestMapping("/print")
public class PrintBatchController extends ControllerBase{

	@Resource
	PrintBatchService printBatchService;
	@Resource
	PrintSchemeService printSchemeService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private SystemCodeService systemCodeService;
	@Autowired
	private SignatureSchemeService signatureSchemeService;
	
	/**
	 * 跳向批量打印页面
	 * @author liuyi
	 * 2014年9月1日 09:32:58
	 */
	@RequestMapping("/toPrintBatchPage")
	public ModelAndView toPrintBatchPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("system/print/printBatch");
		Calendar cal = Calendar.getInstance();
		mav.addObject("caseyear", cal.get(cal.YEAR));//案件年，默认当前年
		java.util.Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jyconfig.getProperty("provincecode");//获取省份代码
		request.setAttribute("provincecode", provincecode);
		//SystemUser user = (SystemUser) WebUtils.getSessionAttribute(request, SESSION_USER_KEY);
		SystemUser user = getLoginUser(request);
		SystemOrganization org = printBatchService.getByOrganizationId(user.getDepartid());
		mav.addObject("shortname", org.getShortname());//单位简称
		Map paramap = new HashMap();
		paramap.put("scearch", "JXJS");
		paramap.put("codetype", "GK7799");
		paramap.put("orgid", user.getDepartid());
		List<TbsysCode> list = systemCodeService.getCodesByMap(paramap);
		if (list.size() < 0) {
			paramap.put("orgid", provincecode);
			list = systemCodeService.getCodesByMap(paramap);
		}
		mav.addObject("casetype",list);//案件类型 减字，刑执字等
		List<PrintScheme> schemeList = printSchemeService.getPrintSchemeComboBox(request);
		mav.addObject("printscheme",schemeList);//打印方案
		return mav;
	}
	
	
	/**
	 * 跳向公共批量打印页面
	 * @author YangZR
	 * 2015年5月4日
	 */
	@RequestMapping("/toBatchPrintPage.page")
	public ModelAndView toBatchPrintPage(HttpServletRequest request) {
		
		Map paramap = new HashMap();
		
		SystemUser user = getLoginUser(request);
		SystemOrganization org = systemOrganizationService.getByOrganizationId(user.getDepartid());
		String level = org.getUnitlevel();
		String codeSearch = StringNumberUtil.getCodeSearchByOrgLevel(level);
		paramap.put("scearch", codeSearch);
		paramap.put("codetype", "GK7799");
		List<TbsysCode> caseTypeList = systemCodeService.getCodesByMap(paramap);
		request.setAttribute("caseTypeList", caseTypeList);
		
		Calendar calendar = Calendar.getInstance();//获取当前年
	    int currYear = calendar.get(Calendar.YEAR);
		request.setAttribute("currYear", currYear);
		
		// 单位简称
		request.setAttribute("orgShortname", org.getShortname());
		
		// 打印方案
		paramap.put("userid", user.getUserid());
		paramap.put("departid", user.getDepartid());
		paramap.put("restypeid", "14");//打印方案
		List<SignatureScheme> signatureSchemes = signatureSchemeService.getSignatureSchemesByUser(paramap);
		request.setAttribute("signatureSchemes", signatureSchemes);
		
		ModelAndView mav = new ModelAndView("system/print/batchPrintPage");
		return mav;
	}
	

//	/**
//	 * 跳向法院批量打印页面
//	 * @author liuyi
//	 * 2015年1月19日 17:06:02
//	 */
//	@RequestMapping("/toCourtPrintBatchPage")
//	public ModelAndView toCourtPrintBatchPage(HttpServletRequest request) {
//		ModelAndView mav = new ModelAndView("system/print/printBatchForFayuan");
//		Calendar cal = Calendar.getInstance();
//		mav.addObject("caseyear", cal.get(cal.YEAR));//案件年，默认当前年
//		SystemUser user = (SystemUser) WebUtils.getSessionAttribute(request, SESSION_USER_KEY);
//		SystemOrganization org = printBatchService.getByOrganizationId(user.getDepartid());
//		mav.addObject("shortname", org.getShortname());//单位简称
//		List<TbsysCode> list = printBatchService.getPrintBatchCaseType(request);
//		mav.addObject("casetype",list);//案件类型 减字，刑执字等
//		List<PrintScheme> schemeList = printSchemeService.getPrintSchemeComboBox(request);
//		mav.addObject("printscheme",schemeList);//打印方案
//		return mav;
//	}
	
	/**
	 * 批量打印
	 * @author liuyi
	 * 2014年9月1日 14:36:29
	 */
	@RequestMapping("/printBatch")
	@ResponseBody
	public Object printBatch(HttpServletRequest request){
		Object obj = printSchemeService.printBatch(request);
		return obj;
	}
}

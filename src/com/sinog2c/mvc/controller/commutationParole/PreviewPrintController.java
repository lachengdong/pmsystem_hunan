package com.sinog2c.mvc.controller.commutationParole;

import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.util.WebUtils;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.PreviewPrintService;

/**
 * 预览打印
 * @author liuyi
 *
 */

@Controller
@RequestMapping("/print")
public class PreviewPrintController extends ControllerBase{
	
	@Autowired
	public PreviewPrintService previewPrintService;
	
	/**
	 * 跳向预览打印页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toPreviewPrintPage")
	public ModelAndView toPreviewPrintPage(HttpServletRequest request){
		String type=request.getParameter("type");
		String viewurl="system/print/previewPrint";
		String doctype=request.getParameter("doctype");
		request.setAttribute("doctype", doctype);
		if(null!=type&&"gonggao".equals(type)){
			viewurl="system/print/previewPrintgonggao";
			request.setAttribute("type",type);
		}
		ModelAndView mav = new ModelAndView(viewurl);
		Calendar cal = Calendar.getInstance();
		mav.addObject("caseyear", cal.get(Calendar.YEAR));
		SystemUser user = (SystemUser) WebUtils.getSessionAttribute(request, SESSION_USER_KEY);
		//此处应该获取的是prison(单位信息)对象的数据 而不是：getOrganization(部门信息);
		SystemOrganization org = user.getPrison();
		if(org!=null && org.getShortname()!=null){
			mav.addObject("shortname", org.getShortname());//单位简称
		}
		return mav;
	}
	
	/**
	 * 获取预览打印的列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPreviewReportList")
	@ResponseBody
	public Object getPreviewReportList(HttpServletRequest request){
		Object obj = previewPrintService.getPreviewReportList(request);
		return obj;
	}
	
	/**
	 * 获取案件类型
	 * @param request
	 * @return
	 * 2014年8月18日 10:59:51
	 */
	@RequestMapping(value = "/getPreviewPringCaseType")
	@ResponseBody
	public Object getPreviewPringCaseType(HttpServletRequest request) {
		List<TbsysCode> list = previewPrintService.getPreviewPrintCaseType(request);
		JSONMessage message = JSONMessage.newMessage();
		if(list!=null){
			message.setData(list);
		}
		return message.getData();
	}
}

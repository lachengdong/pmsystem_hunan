package com.sinog2c.mvc.controller.officeAssistant;

import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONArray;
import com.gkzx.common.LogCommon;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;

/**
 * 类描述：表单打印、收监指南
 * @author liuchaoyang
 * 下午09:34:15
 */
@Controller
@RequestMapping("/formPrintingAndToGuide")
public class FormPrintingAndToGuideContoller extends ControllerBase{
	@Resource
	public SystemLogService logService;
	@Autowired
	private SystemModelService systemModelService;
	
	/**
	 * 方法描述：跳转到表单打印列表页
	 * 
	 * @author shily 2014-7-13 12:46:45
	 */
	@RequestMapping(value = "/formPrintingList.page")
	public ModelAndView printForm() {
		return new ModelAndView("officeAssistant/formPrintingList");
	}

	/**
	 * 方法描述：跳到收监指南页面显示收监说明
	 * @param request
	 * @return
	 */
	@RequestMapping("/showFormPrintingAndToGuide.page")
	public ModelAndView showToIllustrateThe(HttpServletRequest request){
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		String menuid = request.getParameter("menuid");
		if(menuid==null) menuid = "10182";//如果菜单编号为空，默认为收监指南菜单编号
		String tempid = request.getParameter("tempid");
		if(tempid==null) tempid = "SJGL_SJSM";//如果模版编号为空，默认为收监指南模版编号
		request.setAttribute("tempid", tempid);
		request.setAttribute("menuid", menuid);
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
		if (template != null) {
			docconent.add(template.getContent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("officeAssistant/showFormPrintingAndToGuide");
	}
	/**
	 * 方法描述：保存收监指南
	 */
	@RequestMapping("/saveToIllustrateThe.json")
	@ResponseBody
	public int saveToIllustrateThe(HttpServletRequest request){
		int result = 0;
		short status = 0;
		String tempid = request.getParameter("tempid");
		String content = request.getParameter("content");
		TbsysTemplate template = new TbsysTemplate();
		SystemLog log = new SystemLog();
		SystemUser user = getLoginUser(request);// 获取当前登录的用户
		template.setTempid(tempid);
		template.setDepartid(user.getDepartid());
		template.setTempname(LogCommon.SHOUJIANZHINAN);
		template.setContent(content);
		template.setOpid(user.getUserid());
		template.setOptime(new Date());
		result = systemModelService.updateTemplateDetail(template);
		log.setLogtype(LogCommon.SHOUJIANZHINAN+LogCommon.OPERATE);
		log.setOpaction(LogCommon.EDIT);
		log.setOpcontent(LogCommon.SHOUJIANZHINAN+LogCommon.EDIT);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.SHOUJIANZHINAN+LogCommon.OPERATE);
		if(result == 1) status = 1;//如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}
}

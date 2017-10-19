package com.sinog2c.mvc.controller.khjc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.khjc.KhjcPublicService;
import com.sinog2c.service.api.khjc.TbxfOutExecuteService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 省局保外
 *
 */
@Controller
public class ProvinceOutExecuteController extends ControllerBase {

	@Autowired
	private TbxfOutExecuteService tbxfOutExecuteService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private KhjcPublicService khjcPublicService;
	
	@RequestMapping(value="toOutExecuteProvinceLiAnPageForSD")
	public ModelAndView toOutExecuteProvinceLiAnPage(HttpServletRequest request){
		
		returnResourceMap(request);
		Date date = new Date();
		String curyear = DateUtil.dateFormat(date, "yyyy");
		String flowdefid = request.getParameter("flowdefid");
		String templetid = request.getParameter("templetid");
		SystemUser su = getLoginUser(request);
		String departid = su.getDepartid();
		String outexecutetype = "1"; //1：保外，2：继保
		Map paramMap = new HashMap();
		paramMap.put("departid", departid);
		paramMap.put("caseyear", curyear);
		paramMap.put("flowdefid", flowdefid);
		paramMap.put("outexecutetype", outexecutetype);
		int casenumber = tbxfOutExecuteService.getMaxOutExecuteCaseNo(paramMap);//案件号
		
		SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
		String shortname = so.getShortname();
		String casetype = GkzxCommon.BWTYPE;//省局保外无初保、续保之分
		request.setAttribute("casenumber", casenumber);
		request.setAttribute("curyear", curyear);
		request.setAttribute("shortname", shortname);
		request.setAttribute("casetype", casetype);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("templetid", templetid);
		
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/khjc/outexecute/outExecuteProvinceLiAnPageForSD.jsp"));
	}
	
	/**
	 * 获取最大的案件号
	 */
	@RequestMapping(value = "/ajaxGetMaxOutExecuteCaseNo")
	@ResponseBody
	public Object getMaxOutExecuteCaseNo(HttpServletRequest request){
		SystemUser su = getLoginUser(request);
		String departid = su.getDepartid();
		String flowdefid = request.getParameter("flowdefid");
		String curyear = request.getParameter("curyear");
		String outexecutetype = "1"; //1：保外，2：继保
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("departid", departid);
		paramMap.put("caseyear", curyear);
		paramMap.put("flowdefid", flowdefid);
		paramMap.put("outexecutetype", outexecutetype);
		return tbxfOutExecuteService.getMaxOutExecuteCaseNo(paramMap);
	}
	
	/**
	 * 省局保外立案
	 * 
	 */
	@RequestMapping(value = "/provinceOutExecuteLiAn")
	@ResponseBody
	public Object provinceOutExecuteLiAn(HttpServletRequest request){
		
		JSONMessage msg = JSONMessage.newMessage();
		//立案时，要判断该案件是否已被其它用户立案 如果有罪犯已立案，则返回相关信息，
		//后继处理
//		if(true){
//			msg.setInfo("xxx已立案");
//			return msg;
//		}
		try{
			String ids = request.getParameter("ids");
			if(StringNumberUtil.isEmpty(ids)){
				msg.setInfo("请至少选择一条记录！");
				return msg;
			}
			
			String flowdefid = request.getParameter("flowdefid");
			String curyear = request.getParameter("curyear");
			String templetid = request.getParameter("templetid");
			String outexecutetype = request.getParameter("outexecutetype");
			String casetype = request.getParameter("casetype");
			
			Map<String,String> paraMap = new HashMap<String,String>();
			SystemUser user = getLoginUser(request);
			paraMap.put("ids", ids);
			paraMap.put("flowdefid", flowdefid);
			paraMap.put("departid", user.getDepartid());
			paraMap.put("caseyear", curyear);
			paraMap.put("templetid", templetid);
			paraMap.put("outexecutetype", outexecutetype);
			paraMap.put("casetype", casetype);
			
			msg = khjcPublicService.saveProvinceOutExecuteLiAnData(paraMap,user);
			
		}catch(Exception e){
			e.printStackTrace();
			msg.setInfo("操作失败！");
		}
		return msg;
	}
	
}

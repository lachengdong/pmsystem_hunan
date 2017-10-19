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
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.khjc.KhjcPublicService;
import com.sinog2c.service.api.khjc.TbxfCommutationService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;
/**
 * 省局减刑假释办理
 * 
 */
@Controller
public class ProvinceCommuteController extends ControllerBase {
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private TbxfCommutationService tbxfCommutationService;
	@Autowired
	private KhjcPublicService khjcPublicService;
	
	
	/**
	 */
	@RequestMapping(value = "/toProvinceCommuteLiAnPage")
	public ModelAndView toProvinceCommuteLiAnPage(HttpServletRequest request){
		returnResourceMap(request);
		Date date = new Date();
		String curyear = DateUtil.dateFormat(date, "yyyy");
		SystemUser su = getLoginUser(request);
		String departid = su.getDepartid();
		String flowdefid = request.getParameter("flowdefid");
		String templetid = request.getParameter("templetid");
		int commutetype = 0; //减刑类型 0：减刑，1：假释
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("departid", departid);
		paramMap.put("flowdefid", flowdefid);
		paramMap.put("caseyear", curyear);
		paramMap.put("commutetype", commutetype);
		int caseNo = tbxfCommutationService.getMaxCommuteCaseNo(paramMap);//案件号
		
		SystemOrganization so = systemOrganizationService.getByOrganizationId(departid);
		String shortname = so.getShortname();
		request.setAttribute("casenumber", caseNo);
		request.setAttribute("curyear", curyear);
		request.setAttribute("shortname", shortname);
		request.setAttribute("flowdefid", flowdefid);
		request.setAttribute("templetid", templetid);
		
		ModelAndView mav = null;
		View view = new InternalResourceView("WEB-INF/JSP/khjc/commute/commuteProvinceLiAnListPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	
	/**
	 * 获取最大的案件号
	 */
	@RequestMapping(value = "/ajaxGetMaxCommuteCaseNo")
	@ResponseBody
	public Object getMaxCommuteCaseNo(HttpServletRequest request){
		SystemUser su = getLoginUser(request);
		String departid = su.getDepartid();
		String flowdefid = request.getParameter("flowdefid");
		String curyear = request.getParameter("curyear");
		String commutetype = request.getParameter("commutetype");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("departid", departid);
		paramMap.put("flowdefid", flowdefid);
		paramMap.put("caseyear", curyear);
		paramMap.put("commutetype", commutetype);
		return tbxfCommutationService.getMaxCommuteCaseNo(paramMap);
	}
	
	/**
	 * 省局减刑假释立案
	 * 
	 */
	@RequestMapping(value = "/provinceCommuteLiAn")
	@ResponseBody
	public Object provinceCommuteLiAn(HttpServletRequest request){
		
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
			String commutetype = request.getParameter("commutetype");
			
			Map<String,String> paraMap = new HashMap<String,String>();
			SystemUser user = getLoginUser(request);
			paraMap.put("ids", ids);
			paraMap.put("flowdefid", flowdefid);
			paraMap.put("departid", user.getDepartid());
			paraMap.put("caseyear", curyear);
			paraMap.put("templetid", templetid);
			paraMap.put("commutetype", commutetype);
			
			msg = khjcPublicService.saveProvinceCommuteLiAnData(paraMap,user);
			
		}catch(Exception e){
			e.printStackTrace();
			msg.setInfo("操作失败！");
		}
		return msg;
	}
	
}

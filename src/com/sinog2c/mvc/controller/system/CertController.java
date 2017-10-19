package com.sinog2c.mvc.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.user.UserCertificate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.CertService;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 处理与证书有关的类
 * @author shengyang
 *
 */
@Controller
@SuppressWarnings("all")
public class CertController extends ControllerBase {
	
	
	
	@Autowired
	private CertService certService;
	
	/**
	 * 进入证书绑定列表页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toUserBindCertList.page")
	public ModelAndView editRoleByUser(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = new ModelAndView("system/toUserBindCertList.page");

		return mav;
	}
	
	
	
	/**
	 * 获取用户绑定证书列表
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value = "/getUserCertList.json")
	@ResponseBody
	public Object getUserCertList(HttpServletRequest request,HttpServletResponse response){
		
		SystemUser user = this.getLoginUser(request);
		Map<String,Object> map = this.parseParamMap(request);
		map = this.parsePageInfoOfMap(map);
		map.put("orgid", user.getOrgid());
		
		Map<String, Object> resultmap = new HashMap<String,Object>();
		
		int count = certService.countUserCertList(map);
		List<Map<String,Object>> data= certService.getUserCertList(map);
    	
    	resultmap.put("data", data);
		resultmap.put("total", count);
		
		return resultmap;
	}
	
	
	/**
	 * 管理证书界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/manageCertPage.page")
	public ModelAndView manageCertPage(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("system/manageCertPage");
		
		String id = request.getParameter("id");
		if(StringNumberUtil.notEmpty(id)){
			UserCertificate userCertificate = certService.getCertById(id);
			mav.addObject("cert", userCertificate);
		}
		return mav;
	}
	
	
	
	@RequestMapping(value = "/saveUserCert.json")
	@ResponseBody
	public Object saveUserCert(HttpServletRequest request, HttpServletResponse response) {
		//
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		//
		String userid = request.getParameter("userid");
		String explian = request.getParameter("subject");
		String certsn = request.getParameter("certno");
		String dn = request.getParameter("certdn");
		String certdata = request.getParameter("certdata");
		//
		boolean inputCheckOK = true;
		String inputCheckMessage = "绑定出错";
		// 获取用户信息
		SystemUser user = this.getLoginUser(request);
		
		// 新包装对象
		UserCertificate cert = new UserCertificate();
		if(StringNumberUtil.notEmpty(id)){//主键
			cert.setId(id);
		}
		cert.setCertsn(certsn);
		cert.setDn(dn);
		cert.setCertdata(certdata);
		cert.setExplian(explian);
		cert.setUserid(userid);
		cert.setOpid(user.getUserid());
		cert.setOptime(new Date());
		
		//
		JSONMessage message = JSONMessage.newMessage();
		
		//
		if(inputCheckOK){
			//// 更新
			int rows = certService.saveUserCert(cert, type);
			if(1 == rows){
				message.setSuccess();
				message.setInfo("绑定成功!");
			} else {
				message.setInfo("绑定失败!");
			}
		} else {
			message.setInfo(inputCheckMessage);
		}
		
		return message;
	}
	
	
	
	@RequestMapping(value = "/deleteUserCert.json")
	@ResponseBody
	public Object deleteUserCert(HttpServletRequest request, HttpServletResponse response) {
		//
		String id = request.getParameter("id");
		//
		JSONMessage message = JSONMessage.newMessage();
		
		int rows = certService.deleteUserCert(id);
		if(1 == rows){
			message.setSuccess();
			message.setInfo("删除成功!");
		} else {
			message.setInfo("删除失败!");
		}
		
		return message;
	}

	
	
	@RequestMapping(value = "/isUserSignatureKey.json")
	@ResponseBody
	public Object isUserSignatureKey(HttpServletRequest request, HttpServletResponse response) {
		//
		String certsn = request.getParameter("certno");
		SystemUser user = this.getLoginUser(request);
		
		//
		JSONMessage message = JSONMessage.newMessage();
		
		int rows = certService.isUserSignatureKey(user.getUserid(), certsn);
		if(1 == rows){
			message.setSuccess();
		} else {
			message.setInfo("该电子签章不属于此用户！");
		}
		
		return message;
	}
	
}

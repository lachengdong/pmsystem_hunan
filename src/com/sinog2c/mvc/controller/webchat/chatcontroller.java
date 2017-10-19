package com.sinog2c.mvc.controller.webchat;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.sinog2c.model.chat.iMessage;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.webchat.EchoHandler;
import com.sinog2c.service.api.message.IMessageService;
import com.sinog2c.service.api.system.SystemOrganizationService;

@Controller
@RequestMapping("/chat")
public class chatcontroller {

	@Resource
	SystemOrganizationService systemOrganizationService;
	@Resource
	private IMessageService iMessageService;

	/**
	 * 进入即时消息窗口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listuser", method = RequestMethod.GET)
	public ModelAndView listuser(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("开始查询系统用户数据：" + System.currentTimeMillis());
		SystemUser user = ControllerBase.getLoginUser(request);
		ModelAndView mav = new ModelAndView("chat/chat_userlist");
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		String orgid = request.getParameter("orgid");
		if (orgid == null || orgid.isEmpty())
			orgid = "-1";
		List<SystemOrganization> list = this.getChatUserList(orgid);
		mav.addObject("path", path);
		mav.addObject("basePath", basePath);
		mav.addObject("userlist", JSON.toJSONString(list));
		mav.addObject("onlineUser", user.getName());
		System.out.println("结束查询系统用户数据：" + System.currentTimeMillis());
		return mav;
	}

	/**
	 * 重新获取用户列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ajax/relistuser")
	@ResponseBody
	public List<SystemOrganization> listOrganizationByPage(
			HttpServletRequest request, HttpServletResponse response) {
		String orgid = request.getParameter("orgid");
		if (orgid == null || orgid.isEmpty())
			orgid = "-1";
		List<SystemOrganization> list = this.getChatUserList(orgid);
		return list;
	}

	private List<SystemOrganization> getChatUserList(String orgid) {
		List<SystemOrganization> list = this.systemOrganizationService
				.getAllOrginfoAndmember();
		HashMap<String, WebSocketSession> onlineUsers = EchoHandler.onlineUsers;
		for (SystemOrganization org : list) {
			if (onlineUsers.containsKey(org.getOrgid().substring(1))) {
				org.setInt2(1);
			} else {
				org.setInt2(0);
			}
		}
		return list;
	}

	/**
	 * 获取用户离线消息
	 * 
	 * @param request
	 * @param createUser
	 * @param toUid
	 * @return
	 */
	@RequestMapping(value = "/getUserUnreadMsg", method = RequestMethod.POST)
	@ResponseBody
	public List<iMessage> getUserUnreadMsg(
			HttpServletRequest request,
			@RequestParam(value = "createUser", required = true) String createUser,
			@RequestParam(value = "toUid", required = true) String toUid) {
		return this.iMessageService.getUserUnreadMsg(createUser, toUid);
	}
}

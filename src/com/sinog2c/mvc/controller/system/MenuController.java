package com.sinog2c.mvc.controller.system;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.SystemResourceService;

@Controller
public class MenuController extends ControllerBase{

	@Resource
	SystemResourceService systemResourceService;
	/**
	 * 菜单树,根据用户权限,显示
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/menutree.json")
	@ResponseBody
	public Object getUserMenu(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取登录用户
		SystemUser user = getLoginUser(request);

		// 得到数据 menu
    	List<SystemResource> menus = systemResourceService.getMenuByUser(user);
    	//// 总数
    	//int total = menus.size();
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		//message.setTotal(total);
		message.setData(menus);
		//
		return message.getData();
	}
}

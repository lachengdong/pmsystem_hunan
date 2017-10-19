package com.sinog2c.mvc.controller.oa;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.oa.OaLeave;
import com.sinog2c.service.api.oa.OaLeaveService;
import com.sinog2c.util.common.RequestUtil;


@Controller
@RequestMapping("/leave")
public class OaLeaveController {
	@Resource
	private OaLeaveService oaLeaveService;
	
	/**
	 * @author 肖岩
     * date 2015/08/31
	 * 查询请假单信息列表
	 * request 请求参数
	 * */
	@RequestMapping(value = "/getLeaveListInfo.action", method = RequestMethod.POST)
	@ResponseBody
	public JSONMessage<OaLeave> getLeaveInfoList(
			HttpServletRequest request) {
		return this.oaLeaveService.getLeaveListInfo(RequestUtil
				.parseParamMapForPagination(request));
	}
	
	/**
	 * @author 肖岩
     * date 2015/09/01
	 * ajax请求，按id查看请假单数据
	 * request 请求参数
	 * */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getLeaveInfo.json", method = RequestMethod.POST)
	@ResponseBody
	public Map getLeaveInfo (String bybdid) {
		Map map=this.oaLeaveService.getLeaveInfo(bybdid); 	
		return map;
	}
}

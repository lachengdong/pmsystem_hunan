package com.sinog2c.mvc.controller.system;

import static com.sinog2c.util.common.StringNumberUtil.parseInt;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.SystemLogService;


/**
 * 日志相关
 */
@Controller
@RequestMapping("/log")
public class LogController extends ControllerBase{
	
	@Resource
	public SystemLogService logService;

	/**
	 * 显示日志信息页面
	 */
	@RequestMapping("/manage.action")
	public ModelAndView manageUser(HttpServletRequest request, HttpServletResponse response) {
		//资源对象
		returnResourceMap(request);
		ModelAndView mav = new ModelAndView("system/log/logmanage");

		return mav;
	}

	@RequestMapping(value = "/ajax/list.json")
	@ResponseBody
	public Object listResourceByPage(HttpServletRequest request) throws UnsupportedEncodingException {
		// 页码, 0 开始
		String pageIndexStr = request.getParameter("pageIndex");
		// 每页显示条数
		String pageSizeStr = request.getParameter("pageSize");
		// 排序列名
		String sortField = getParameterString(request, "sortField", "LOGID");
		// asc, desc
		String sortOrder = getParameterString(request, "sortOrder", "desc");
		// 搜索
		String key = request.getParameter("key");
		String logtype = request.getParameter("logtype");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		//
		int pageIndex = parseInt(pageIndexStr, 0);
		int pageSize  = parseInt(pageSizeStr, 20);

		//// 总数
		int total = 0;
		// 分页得到的数据;
		List<SystemLog> logs = null;
		
		//key= URLDecoder.decode(key,"UTF-8");
		total = logService.countBySearch(logtype, key, starttime, endtime);
		// 分页得到的数据
		logs = logService.search(pageIndex, pageSize, logtype, key, sortField, sortOrder, starttime, endtime);		

		//
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setTotal(total); 
		message.setSuccess();
		message.setData(logs);
		
		//
		return message;
	}
	/*
	 *删除审计日志
	 */
	@RequestMapping(value = "/removelog.json")
	@ResponseBody
	public int removelog(HttpServletRequest request) throws UnsupportedEncodingException {
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		return logService.removelog(starttime, endtime);
	}
}

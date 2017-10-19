package com.sinog2c.mvc.controller.system;

import static com.sinog2c.util.common.StringNumberUtil.parseInt;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.ParoleReferenceService;
import com.sinog2c.util.common.StringNumberUtil;

@Controller
public class ParoleReferenceController extends ControllerBase{

	@Resource
	ParoleReferenceService paroleReferenceService;
	
	/**
	 * 页面跳转
	 * 
	 * @author YangZR 2014-7-24 13:46:45
	 */
	@RequestMapping(value = "/system/paroleReference")
	public ModelAndView printForm() {
		return new ModelAndView(new InternalResourceView(
				"/WEB-INF/JSP/system/paroleReference.jsp"));
	}
	
	
	@RequestMapping(value = "/system/ajaxGetParoleReference")
	@ResponseBody
	public Object ajaxGetParoleReference(HttpServletRequest request) {
		
		// 页码, 0 开始
		String pageIndexStr = request.getParameter("pageIndex");
		// 每页显示条数
		String pageSizeStr = request.getParameter("pageSize");
		// 排序列名
		//String sortField = request.getParameter("sortField");
		// asc, desc
		//String sortOrder = request.getParameter("sortOrder");
		// 搜索
		String key = request.getParameter("key");
		//
		int pageIndex = parseInt(pageIndexStr, 0);
		int pageSize  = parseInt(pageSizeStr, 20);

		// 总数
		int total = 0;
		// 分页得到的数据;
		List<Map> values = null;
		if(StringNumberUtil.notEmpty(key)){
			// 搜索
			total = paroleReferenceService.countSearchSql(key);
			// 分页得到的数据
			values = paroleReferenceService.searchByPageSql(pageIndex, pageSize,key);
		} else {
			total = paroleReferenceService.countAllSql();
			// 分页得到的数据
			values = paroleReferenceService.searchAllByPageSql(pageIndex, pageSize);
		}
		
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setTotal(total); 
		message.setData(values);
		
		//
		return message;
	}
	
}

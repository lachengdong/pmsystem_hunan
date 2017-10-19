package com.sinog2c.mvc.controller.commutationParole;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.commutationParole.PrisonerPerformanceService;

@Controller
public class PrisonerPerformanceController extends ControllerBase {
	
	@Resource
	private PrisonerPerformanceService prisonerPerformanceService;
	
	@RequestMapping(value = "/getPrisonerPerformance")
	@ResponseBody
	public Object getPrisonerPerformance(HttpServletRequest request) {
		String key = request.getParameter("key");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		String crimid = request.getParameter("crimid");
		
		HashMap map = new HashMap();
		map.put("crimid", crimid);
		map.put("key", key);
		map.put("start", start);
		map.put("end", end);
		
		int total = prisonerPerformanceService.getCount(map);
		
		List<HashMap> list = prisonerPerformanceService.getPrisonerPerformanceList(map);
		
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}
	
}

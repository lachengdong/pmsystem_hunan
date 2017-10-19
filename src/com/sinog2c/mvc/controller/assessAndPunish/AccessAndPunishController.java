package com.sinog2c.mvc.controller.assessAndPunish;

import java.util.ArrayList;
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
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.assessAndPunish.AccessAndPunishService;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 考核奖惩
 * 
 * @author yangzr
 * 
 */
@Controller
public class AccessAndPunishController extends ControllerBase{

	@Autowired
	private AccessAndPunishService accessAndPunishService;
	
	@RequestMapping(value = "/toPunishmentInfoOfCrim")
	public ModelAndView toPunishmentInfoOfCrim(HttpServletRequest request) {
		ModelAndView mav = null;
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		returnResourceMap(request);
		View view = new InternalResourceView(
		"WEB-INF/JSP/assessAndPunish/punishmentInfoOfCrim.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	@RequestMapping(value = "/getPunishmentInfoOfCrim")
	@ResponseBody
	public Object getPunishmentInfoOfCrim(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultmap = new HashMap<String,Object>();
		List<Map>  data = new ArrayList<Map>();
		try {
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			String crimid = request.getParameter("crimid");
			if(!StringNumberUtil.notEmpty(crimid)){
				return null;
			}
//			String orgid = getLoginUser(request).getDepartid();
			
			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("key", key);
//			map.put("orgid", orgid);
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("crimid", crimid);
			
	    	int count = accessAndPunishService.getPunishInfoCountOfCrim(map);
	    	data= accessAndPunishService.getPunishInfoOfCrim(map);
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return resultmap;
	}
	
	
}

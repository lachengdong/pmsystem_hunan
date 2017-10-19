package com.sinog2c.mvc.controller.penaltyPerform;

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

import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;

/**
 * 选择罪犯
 * 
 * @author liuxin
 * 
 */
@Controller
public class ChooseCriminal extends ControllerBase{
	
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	/**
	 * 跳转选择罪犯页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toChooseCriminalPage")
	public ModelAndView toChooseCriminalPage(HttpServletRequest request) {
//		String action = request.getParameter("action");//需要跳转的action
//		if(action==null){
//			action = "publicMainController";
//		}
//		String resid = request.getParameter("resid");//资源编号
//		request.setAttribute("resid", resid);
//		request.setAttribute("action", action);
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/chooseCriminal");
	}
	/**
	 * 获取罪犯列表
	 * 
	 * @author liuxin
	 * @param request
	 * @return resultmap
	 */
	@RequestMapping(value = "/getBasicInfoList")
	@ResponseBody
	public Object getBasicInfoList(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try {
			//取得参数
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			//分页
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			//字段排序
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("departId", getLoginUser(request).getOrgid());
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
			
	    	//根据map传参获取总条数
	    	int count = chooseCriminalService.countAllByCondition(map);
	    	map.put("flowdefid", "other_slzfrdsp");
	    	//根据map传参获取罪犯列表
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);
	    	
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
		}
		
		return resultmap;
	}
	
}

package com.sinog2c.mvc.controller.penaltyPerform;

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
import com.sinog2c.model.penaltyPerform.TbyzPublicity;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.penaltyPerform.TheOpenOfPrisonAffairsService;


/**
 * 类描述：狱务公开
 * @author liuchaoyang
 */
@Controller
@RequestMapping("/theOpenOfPrisonAffairs")
public class TheOpenOfPrisonAffairsController extends ControllerBase{
	@Autowired
	private TheOpenOfPrisonAffairsService theOpenOfPrisonAffairsService;
	
	/**
	 * 功能描述：跳转到列表页
	 * @author liuchaoyang
	 */
	@RequestMapping("/publicityList.page")
	public ModelAndView reviewList(HttpServletRequest request){
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/theOpenOfPrisonAffairs/publicityList");
	}
	/**
	 * 功能描述：获取数据信息
	 * @author liuchaoyang
	 * 2014-8-09 下午07:29:22
	 */
	@RequestMapping(value="/ajaxGetPublicityList.json")
	@ResponseBody
	public Object ajaxGetPublicityList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			String type = request.getParameter("type")==null?"":request.getParameter("type");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
	    	//获取罪犯列表
	    	List<TbyzPublicity> list = theOpenOfPrisonAffairsService.ajaxGetPublicityList(pageIndex, pageSize, key, type, sortField, sortOrder);
	    	//获取总条数
	    	int count = theOpenOfPrisonAffairsService.ajaxGetPublicityCount(key, type);
	    	resultmap.put("data", list);
			resultmap.put("total", count);
		}catch (Exception e) {
		}
		return resultmap;
	}
	/**
	 * 功能描述：跳转到评审榜新增页面
	 * @param request
	 * @return
	 */
	@RequestMapping("addPublicityList.page")
	public ModelAndView addReviewTheList(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/theOpenOfPrisonAffairs/addPublicityList");
	}
	
}

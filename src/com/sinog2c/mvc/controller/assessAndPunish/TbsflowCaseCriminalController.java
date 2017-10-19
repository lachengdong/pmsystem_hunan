package com.sinog2c.mvc.controller.assessAndPunish;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinog2c.model.assessAndPunish.TbsflowCaseCriminal;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.assessAndPunish.TbsflowCaseCriminalService;
import com.sinog2c.util.common.DateUtil;
/**
 * 暂缓解除
 * @author liuxuli
 *
 */
@Controller
public class TbsflowCaseCriminalController extends ControllerBase{

	@Resource
	private TbsflowCaseCriminalService tbsflowCaseCriminalService;
	
	/**
	 * 跳转到暂缓解除列表页
	 * @return
	 */
	@RequestMapping(value="/toTbsflowCaseCriminal")
	public ModelAndView toTbsflowCaseCriminal(HttpServletRequest request){
		ModelAndView mav=null;
		mav=new ModelAndView("/assessAndPunish/tbsflowCaseCriminalList");
		return mav;
	}
	
	/**
	 * 获取罪暂缓执行犯列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/tbsflowCaseCriminalList.action")
	@ResponseBody
	public Object tbsflowCaseCriminalList(HttpServletRequest request) throws Exception{
		//定义返回结果
		Map<String, Object> resultmap=new HashMap<String, Object>();
		//定义查询参数
		List<Map> data=new ArrayList<Map>();
		
		String key = request.getParameter("key") == null ? "" : request.getParameter("key");
		String defertime=request.getParameter("defertime") == null ? "" : request.getParameter("defertime");
		key = URLDecoder.decode(key,"UTF-8");
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("crimid", key);
		map.put("defertime", defertime);
		map.put("pageIndex",pageIndex);
		map.put("pageSize", pageSize);
		map.put("sortField", sortField);
		map.put("sortOrder", sortOrder);
		map.put("start", String.valueOf(start));
		map.put("end", String.valueOf(end));
		
		data=tbsflowCaseCriminalService.selectByDateAndId(map);
		int count=tbsflowCaseCriminalService.getTbsflowCaseCriminalCount(map);
		
		resultmap.put("data",data);
		resultmap.put("total", count);
		
		return resultmap;
	}
	
	/**
	 * 批量解除
	 * @throws Exception
	 */
	@RequestMapping(value="tbsflowCaseCriminalPiLiangJieChu.action")
	@ResponseBody
	public void tbsflowCaseCriminalPiLiangJieChu(HttpServletRequest request) throws Exception{
		String id=request.getParameter("id");
		String[] ids=id.split(",");
		for (String tb:ids) {
			TbsflowCaseCriminal tbsflowCaseCriminal=tbsflowCaseCriminalService.selectByPrimaryKey(tb);
			//为解除后值为0是为解除状态
			tbsflowCaseCriminal.setIsdefer((short) 0);
			tbsflowCaseCriminalService.updateByPrimaryKeySelective(tbsflowCaseCriminal);
		}
	}
	/**
	 * 单个解除
	 * @param request
	 */
	@RequestMapping(value="tbsflowCaseCriminalJieChu.action")
	@ResponseBody
	public void tbsflowCaseCriminalJieChu(HttpServletRequest request){
		String id=request.getParameter("id");
		TbsflowCaseCriminal tbsflowCaseCriminal=tbsflowCaseCriminalService.selectByPrimaryKey(id);
		//为解除后值为0是为解除状态
		tbsflowCaseCriminal.setIsdefer((short)0);
		tbsflowCaseCriminalService.updateByPrimaryKeySelective(tbsflowCaseCriminal);
	}
}

package com.sinog2c.mvc.controller.khjc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.khjc.ScoreCancelService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.MapUtil;

/**
 *考核分数注销
 */
@Controller
public class TkhScoreCancellationController extends ControllerBase{
	@Autowired		
	private ScoreCancelService scoreCancelService;
	
	@RequestMapping(value="tkhScoreCancellation")
	public ModelAndView tkhScoreCancellation(HttpServletRequest request) throws Exception {
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/khjc/TkhScoreCancellationList.jsp"));
	}
	
	@RequestMapping("/toScoreFeiQi.action")
	public ModelAndView toScoreFeiQi(HttpServletRequest request) throws Exception {
		String crimid=request.getParameter("crimid");
		String type = request.getParameter("type");
		String cancelreason = request.getParameter("cancelreason");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("crimid", crimid);
		map.put("cancelreason", cancelreason);
		String str=scoreCancelService.getCancelReasonView(map);
		request.setAttribute("type", type);
		request.setAttribute("crimid", crimid);
		request.setAttribute("cancelreason", str);
		returnResourceMap(request);
		return new ModelAndView(new InternalResourceView("/WEB-INF/JSP/khjc/khjcScoreFeiQiPage.jsp"));
	}
	
	/**
	 * 获取罪犯列表
	 * 初始化加载罪犯列表
	 */
	@RequestMapping("tkhScoreCancellationList")
	@ResponseBody
	public Object TkhScoreCancellationList(HttpServletRequest request) throws Exception {
		Map<String, Object> resultmap = new HashMap<String,Object>();//结果集
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();//拿到的数据
		try {
			String key = request.getParameter("key")==null?"":request.getParameter("key");
			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize -1;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("key", key);
			map.put("departid",getLoginUser(request).getDepartid() );
			map.put("orgid",getLoginUser(request).getOrgid() );
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	int count = scoreCancelService.countAllByCondition(map);//根据map传参获取总条数
	    	data= scoreCancelService.getBasicInfoList(map);//根据map传参获取罪犯列表
	    	data = MapUtil.convertKeyList2LowerCase(data);//将大写转化为小写
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
		}
		return resultmap;
	}
	/*
	 * 加载某一个罪犯的分数信息
	 */
	@RequestMapping("/tkhScoreCancell")
	@ResponseBody
	public Object tkhScoreCancell(HttpServletRequest request) throws Exception{
		Map<String,Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try{
			int pageIndex = (Integer) (request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
			int pageSize = (Integer) (request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));
			String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
			String sortField = request.getParameter("sortField");
			String sortOrder = request.getParameter("sortOrder");
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize - 1;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("sortField", sortField);
			map.put("sortOrder", sortOrder);
			map.put("crimid", crimid);
			map.put("start", String.valueOf(start));
			map.put("end", String.valueOf(end));
			int count = scoreCancelService.countCondition(map);
			data = scoreCancelService.getInfoList(map);
			data = MapUtil.convertKeyList2LowerCase(data);
			resultmap.put("data",data);
			resultmap.put("total", count);
			request.setAttribute("crimid", crimid);
		}catch (Exception e){
			
		}
		return resultmap;
	}
	
	@RequestMapping("/saveScoreCancellation.json")
	@ResponseBody
	public String saveScoreCancellation(HttpServletRequest request) throws Exception{
		try{
			String crimid = request.getParameter("crimid");
			String cancelreason = request.getParameter("cancelreason");
			SystemUser user = getLoginUser(request);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("crimid",crimid);
			map.put("cancelreason",cancelreason);
			map.put("iscancel",1);
			map.put("cancetime", DateUtil.dateFormatForAip(new Date()));//废弃时间，当前时间
			map.put("canceid", user.getName());//废弃操作人，当前用户
			scoreCancelService.getCancelReason(map);
		}catch (Exception e){
		
	}
		return null;
	}

	@RequestMapping("/toTkhScoreCancellationList")
	public ModelAndView toTkhScoreCancellationList(HttpServletRequest request){
		String name = request.getParameter("name")==null?"":request.getParameter("name");	
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String menuid = request.getParameter("menuid")==null?"":request.getParameter("menuid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
			String idname = request.getParameter("idname")==null?"":request.getParameter("idname");
			request.setAttribute("id", id);
			request.setAttribute("idname", idname);
			String[] ids = id == null ? null:id.split(",");
			String[] idnames = idname == null?null:idname.split(",");
			crimid = ids ==null?"":ids[0];//罪犯编号就从数组里取出第一个罪犯编号
			name = idnames == null?"":idnames[0];
		}
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("menuid", menuid);
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/khjc/TkhScoreCancellationListToProcess.jsp"));
	}
}

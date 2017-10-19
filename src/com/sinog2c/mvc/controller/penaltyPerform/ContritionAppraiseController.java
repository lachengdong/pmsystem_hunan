package com.sinog2c.mvc.controller.penaltyPerform;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 方法描述：罪犯认罪悔罪评估量表
 * @author liuchaoyang
 * 上午09:21:37
 */
@Controller
public class ContritionAppraiseController extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Resource
	protected FlowBaseService flowBaseService;
	
	
	/**
	 * 方法描述：跳转到罪犯认罪悔罪评估量表的罪犯选择页面
	 * @author liuchaoyang 2014-7-26 18:52:45
	 */
	@RequestMapping("/contritionAppraiseTable")
	public ModelAndView contritionAppraiseTable(HttpServletRequest request){
		//资源对象
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/contritionAppraiseTableChoose");
	}
	/**
	 * 方法描述：跳转到罪犯劳动情况统计表的罪犯选择页面
	 */
	@RequestMapping("/laborStatisticsTable.page")
	public ModelAndView laborStatisticsTable(HttpServletRequest request){
		//资源对象
		returnResourceMap(request);
		request.setAttribute("tempid", request.getParameter("tempid"));
		return new ModelAndView("penaltyPerform/shanghai/LaborStatisticsTable");
	}
	/**
	 * 获取罪犯列表
	 * @author liuchaoyang
	 * 2014-8-05 20:37:45
	 */
	@RequestMapping(value = "/getContritionAppraiseTableList")
	@ResponseBody
	public Object getContritionAppraiseTableList(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> resultmap = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
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
			map.put("departId", getLoginUser(request).getOrgid());
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
	    	map.put("flowdefid", "doc_zfrzhzpglbsp");
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);
		}catch (Exception e) {
		}
		return resultmap;
	}
	
	 @RequestMapping("/disposeContritionAppraiseTable")
	public ModelAndView disposeContritionAppraiseTable(HttpServletRequest request){
	    String crimid = request.getParameter("crimid");		
	    String idnumber = request.getParameter("idnumber")==null?"":request.getParameter("idnumber");//流程草稿ID
	  //如果罪犯编号为空就表示批量处理,将id解析成数组，取第一个作为复合编号（crimid+flowdraftid）
		if(crimid==null || "".equals(crimid)){
			String id = request.getParameter("id");
			request.setAttribute("id", id);
			String[] ids = id.split(",");
			crimid = ids[0];
			//将这个复合编号解析成数组，第一个是罪犯编号，第二个是流程草稿Id
			ids = crimid.split("@");
			crimid = ids[0];
			if(ids.length>1)idnumber = ids[1];
		}
		request.setAttribute("crimid", crimid);
		String menuid=request.getParameter("menuid");
		request.setAttribute("menuid",menuid);
		String tempid = "SZ_RZHZPGH";
		request.setAttribute("tempid", tempid);
		JSONArray docconent = new JSONArray();
		HashMap<String, Object> map = new HashMap<String, Object>();
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
		TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
		if(idnumber != null && !"".equals(idnumber)){
			String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
			docconent.add(docConent);
			request.setAttribute("flowdraftid", idnumber);
		}else{
			SystemUser user = getLoginUser(request);//获取当前登录的用户
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
			if (template != null) {
				docconent.add(template.getContent());
			}
			map.put("name", info.getName());
			map.put("crimid", info.getCrimid());
			map.put("assessmentperson", user.getName());
			map.put("assessmentdate", DateUtil.dateFormatForAip(new Date()));
			if(crime!=null){
				map.put("anyou", crime.getMaincase());
				String xingqi = crime.getPunishmentyear()+","+crime.getPunishmentmonth()+","+crime.getPunishmentday();
				map.put("sentenceperiod", StringNumberUtil.getXingqi(crime.getPunishmenttype(), xingqi));//主刑
			}
		}	
		returnResourceMap(request);//资源对象
		request.setAttribute("applyid", crimid);
		request.setAttribute("applyname", info.getName());
		request.setAttribute("flowdefid", "doc_zfrzhzpglbsp");
		request.setAttribute("orgid", crime.getOrgid1());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/contritionAppraiseTable");
	}

	 @RequestMapping("/laborStatistics")
		public ModelAndView laborStatistics(HttpServletRequest request){
		    String crimid = request.getParameter("crimid");		
		    String tempid = request.getParameter("tempid");	
		    String flowdefid = request.getParameter("flowdefid");	
		    String idnumber = request.getParameter("idnumber")==null?"":request.getParameter("idnumber");//流程草稿ID
		  //如果罪犯编号为空就表示批量处理,将id解析成数组，取第一个作为复合编号（crimid+flowdraftid）
			if(crimid==null || "".equals(crimid)){
				String id = request.getParameter("id");
				request.setAttribute("id", id);
				String[] ids = id.split(",");
				crimid = ids[0];
				//将这个复合编号解析成数组，第一个是罪犯编号，第二个是流程草稿Id
				ids = crimid.split("@");
				crimid = ids[0];
				if(ids.length>1)idnumber = ids[1];
			}
			request.setAttribute("crimid", crimid);
			String menuid=request.getParameter("menuid");
			request.setAttribute("menuid",menuid);
			request.setAttribute("tempid", tempid);
			JSONArray docconent = new JSONArray();
			HashMap<String, Object> map = new HashMap<String, Object>();
			TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
			TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
			if(idnumber != null && !"".equals(idnumber)){
				String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
				docconent.add(docConent);
				request.setAttribute("flowdraftid", idnumber);
			}else{
				SystemUser user = getLoginUser(request);//获取当前登录的用户
				TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
				if (template != null) {
					docconent.add(template.getContent());
				}
				map.put("name", info.getName());
				map.put("crimid", info.getCrimid());
				map.put("assessmentperson", user.getName());
				map.put("assessmentdate", DateUtil.dateFormatForAip(new Date()));
				if(crime!=null){
					map.put("anyou", crime.getMaincase());
					map.put("sentenceperiod",crime.getRemark());
				}
			}	
			returnResourceMap(request);//资源对象
			request.setAttribute("applyid", crimid);
			request.setAttribute("applyname", info.getName());
			request.setAttribute("flowdefid", flowdefid);
			request.setAttribute("orgid", crime.getOrgid1());
			request.setAttribute("formDatajson", new JSONObject(map).toString());
			request.setAttribute("formcontent", docconent.toString());
			return new ModelAndView("penaltyPerform/shanghai/LaborStatisticsForm");
		}
		/**
		 * 获取罪犯列表
		 * @author liuchaoyang
		 * 2014-8-05 20:37:45
		 */
		@RequestMapping(value = "/getContritionApproveTableList")
		@ResponseBody
		public Object getContritionApproveTableList(HttpServletRequest request,
				HttpServletResponse response){
			Map<String, Object> resultmap = new HashMap<String,Object>();
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			try {
				String key = request.getParameter("key")==null?"":request.getParameter("key");
				String flowdefid = request.getParameter("flowdefid")==null?"":request.getParameter("flowdefid");
				int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
				int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
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
		    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
		    	map.put("flowdefid", flowdefid);
		    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
		    	resultmap.put("data", data);
				resultmap.put("total", count);
			}catch (Exception e) {
			}
			return resultmap;
		}
}

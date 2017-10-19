package com.sinog2c.mvc.controller.outexecute;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 保外就医讨论意见表
 * @author liuchaoyang
 * 下午05:46:26
 */
@Controller 
public class TheDiscussionTableViews extends ControllerBase{
	@Resource
	protected FlowBaseService flowBaseService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Resource
	private PrisonerService prisonerService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	
	/**
	 * 选择罪犯页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/discussionTableViewsChoose")
	public ModelAndView discussionTableViewsChoose(HttpServletRequest request){
		 returnResourceMap(request);
		return new ModelAndView("chooseCriminal/discussionTableViewsChoose");
	}
	
//	/**
//	 * 获取罪犯获取数据
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/getDiscussionTableViewsChoose")
//	@ResponseBody
//	public Object getDiscussionTableViewsChoose(HttpServletRequest request,
//			HttpServletResponse response){
//		Map<String, Object> resultmap = new HashMap<String,Object>();
//		try {
//			//取得参数
//			String key = request.getParameter("key")==null?"":request.getParameter("key");
//			key = URLDecoder.decode(key,"UTF-8");
//			//分页
//			int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
//			int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
//			//字段排序
//			String sortField = request.getParameter("sortField");
//			String sortOrder = request.getParameter("sortOrder");
//			int start = pageIndex * pageSize + 1;
//			int end = start + pageSize -1;
//			Map<String,Object> map = new HashMap<String,Object>();  
//			
//			map.put("orgid", getLoginUser(request).getOrgid());
//	    	map.put("sortField", sortField);
//	    	map.put("sortOrder", sortOrder);
//	    	map.put("start", String.valueOf(start));
//	    	map.put("end",String.valueOf(end));
//	    	int count = chooseCriminalService.countAllByCondition(map);
//	    	map.put("key", key);
//	    	List<Map<String,Object>> data= chooseCriminalService.getBasicInfoList(map);
//	    	resultmap.put("data", data);
//			resultmap.put("total", count);
//		}catch (Exception e) {
//		}
//		
//		return resultmap;
//	}
	
	/**
	 * 方法描述：列表页面（流程数据）
	 * 
	 * @author 
	 */
	@RequestMapping("/ajaxGetDiscussionList")
	@ResponseBody
	public HashMap<String, Object> ajaxGetDiscussionList(HttpServletRequest request) throws Exception{
		SystemUser user = getLoginUser(request);
		HashMap<String, Object> result = new HashMap<String, Object>();
		String deptId=user.getDepartid();//获取当前登录的用户
		String applyid=request.getParameter("crimid")==null? "":request.getParameter("crimid");//申请人id
		String key = request.getParameter("key")==null?"":request.getParameter("key");
		key = URLDecoder.decode(key,"UTF-8");
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("departId", deptId);
		map.put("applyid", applyid);
		map.put("flowdefid", "");
    	map.put("sortField", sortField);
    	map.put("sortOrder", sortOrder);
    	map.put("start", String.valueOf(start));
    	map.put("end",String.valueOf(end));   
		List<Map> list = flowBaseService.findByMapCondition(map);
		map.put("key", key);
		int count = flowBaseService.countAllByCondition(map);
		result.put("total", count);
		result.put("data", list);
		return result;		
	}
	
	/**
	 * 罪犯保外就医讨论意见表列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/discussionTableViews")
	public ModelAndView discussionTableViews(HttpServletRequest request){
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String idname = request.getParameter("idname")==null?"":request.getParameter("idname");
		String crimid = request.getParameter("crimid")==null?"":request.getParameter("crimid");
		String menuid = request.getParameter("menuid")==null?"":request.getParameter("menuid");
		if(crimid==null || "".equals(crimid)){//如果罪犯编号为空就表示批量处理
			String id = request.getParameter("id");//获取罪犯编号字符串，解析成数组
			request.setAttribute("id", id);
			String[] ids = id == null ? null:id.split(",");
			String[] idnames = idname == null?null:idname.split(",");
			crimid = ids ==null?"":ids[0];//罪犯编号就从数组里取出第一个罪犯编号
			name = idnames == null?"":idnames[0];
		}
		request.setAttribute("idname", idname);
		request.setAttribute("crimid", crimid);
		request.setAttribute("name", name);
		request.setAttribute("menuid", menuid);
		return new ModelAndView("outexecute/discussionTableViewsList");
	}
	/**
	 * 表单添加页面
	 * @param request
	 * @return	
	 * @throws Exception 
	 */
	@RequestMapping("/toDiscussionTableViewsAddPage")
	public ModelAndView toDiscussionTableViewsAddPage(HttpServletRequest request){
		JSONArray docconent = new JSONArray();
		
		String menuid=request.getParameter("menuid");
		String crimid = request.getParameter("crimid");
		String flowdraftid = request.getParameter("flowdraftid");//流程编号
		TbprisonerBaseinfo baseinfo =  prisonerService.getBasicInfoByCrimid(crimid);
		TbprisonerBaseCrime baseCrime =prisonerService.getCrimeByCrimid(crimid);
		if(flowdraftid != null && !"".equals(flowdraftid)){
			String docConent =  flowBaseService.getDocConentByFlowdraftId(flowdraftid);
			docconent.add(docConent);
			request.setAttribute("flowdraftid",flowdraftid);
		}else{
			Map<String, Object> map=new HashMap<String, Object>();
			String tempid = request.getParameter("tempid");
			request.setAttribute("tempid", tempid);
			SystemUser user = getLoginUser(request);
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
			SystemOrganization org=systemOrganizationService.getByOrganizationId(user.getDepartid());//根据部门Id获取单位信息
			if (template != null) {
				docconent.add(template.getContent());
			}
			map.put("crimid",crimid);
			map.put("name",baseinfo.getName());
			map.put("gender",baseinfo.getGender());
			map.put("birthday",DateUtil.dateFormatForAip(baseinfo.getBirthday()));
			map.put("depname", org.getName());
			map.put("username", user.getName());
			map.put("date", DateUtil.dateFormatForAip(new Date()));
			if(baseCrime!=null){
				map.put("zuiming",baseCrime.getMaincase());
				// 判断
				if(GkzxCommon.XINGQI_YOUQI.equals(baseCrime.getPunishmenttype()) || GkzxCommon.XINGQI_YOUQI_ZH.equals(baseCrime.getPunishmenttype())){
					String strPunishment = StringNumberUtil.isNullOrEmpty(baseCrime.getPunishmentyear())?"":GkzxCommon.ZERO.equals(baseCrime.getPunishmentyear().toString())?"":StringNumberUtil.digit2speech(baseCrime.getPunishmentyear().toString()) + "年";
					strPunishment += StringNumberUtil.isNullOrEmpty(baseCrime.getPunishmentmonth())?"":GkzxCommon.ZERO.equals(baseCrime.getPunishmentmonth().toString())?"":StringNumberUtil.digit2speech(baseCrime.getPunishmentmonth().toString()) + "个月";
					strPunishment += StringNumberUtil.isNullOrEmpty(baseCrime.getPunishmentday())?"":GkzxCommon.ZERO.equals(baseCrime.getPunishmentday().toString())?"":StringNumberUtil.digit2speech(baseCrime.getPunishmentday().toString()) + "天";
					if (!StringNumberUtil.isNullOrEmpty(strPunishment)) {
						map.put("xingqi", GkzxCommon.XINGQI_YOUQI_ZH + strPunishment);// 刑期
					}
				}else if(GkzxCommon.XINGQI_SIHUAN.equals(baseCrime.getPunishmenttype()) ||GkzxCommon.XINGQI_SIHUAN_ZH.equals( baseCrime.getPunishmenttype())){
					map.put("xingqi", GkzxCommon.XINGQI_SIHUAN_ZH);
				}else if(GkzxCommon.XINGQI_WUQI.equals(baseCrime.getPunishmenttype()) || GkzxCommon.XINGQI_WUQI_ZH.equals(baseCrime.getPunishmenttype())){
					map.put("xingqi", GkzxCommon.XINGQI_WUQI_ZH);
				}else {
					map.put("xingqi", "");
				}
				map.put("xingqiqiri", DateUtil.dateFormatForAip(baseCrime.getSentencestime()));// 刑期起日
				map.put("xingqizhiri", DateUtil.dateFormatForAip(baseCrime.getSentenceetime()));// 刑期止日
			}
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("menuid", menuid);
		request.setAttribute("crimid", crimid);
		request.setAttribute("flowdefid", "");
		request.setAttribute("orgid",baseCrime.getOrgid1());
		request.setAttribute("applyname",baseinfo.getName() );
		request.setAttribute("applyid",crimid );
		request.setAttribute("formcontent", docconent.toString());
		 
		return  new ModelAndView("aip/addDiscussionTableViews");
	}

}

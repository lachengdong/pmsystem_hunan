package com.sinog2c.mvc.controller.penaltyPerform;

import java.text.SimpleDateFormat;
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
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;

/**
 * 罪犯离监通知单
 * 
 */
@Controller
public class LeaveAoticeController extends ControllerBase {
	@Resource
	protected FlowBaseService flowBaseService;
	@Autowired
	private SystemModelService systemModelService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private PrisonerService prisonerService;
	@Autowired
	private ChooseCriminalService chooseCriminalService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;

	/**
	 * 方法描述：跳转到罪犯处理页面
	 */
	@RequestMapping("/leaveAoticeChoose")
	public ModelAndView leaveAoticeChoose(HttpServletRequest request){
		returnResourceMap(request);
		return new ModelAndView("chooseCriminal/leaveAoticeChoose");
	}
	/*
	 * 方法描述：获取罪犯处理列表
	 */
	@RequestMapping(value = "/getLeaveAoticeChooseList")
	@ResponseBody
	public Object getLeaveAoticeChooseList(HttpServletRequest request,
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
			map.put("userid", getLoginUser(request));
	    	map.put("sortField", sortField);
	    	map.put("sortOrder", sortOrder);
	    	map.put("start", String.valueOf(start));
	    	map.put("end",String.valueOf(end));
	    	map.put("userid", getLoginUser(request).getUserid());//给map传userid为了mapper中id为findWithFlow使用
	    	int count = chooseCriminalService.countAllByCondition(map);//根据map传参获取总条数
	    	map.put("flowdefid", "other_zfljtzsp");
	    	data= chooseCriminalService.getBasicInfoListWithFlow(map);//根据map传参获取罪犯列表
	    	resultmap.put("data", data);
			resultmap.put("total", count);
			
		}catch (Exception e) {
		}
		return resultmap;
	}
	/*
	 * 方法描述：跳转到表单页面
	 */
	@RequestMapping("/showLeaveAotice")
	public ModelAndView showLeaveAotice(HttpServletRequest request){
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
		String menuid=request.getParameter("menuid");
		request.setAttribute("crimid", crimid);
		JSONArray docconent = new JSONArray();
		TbprisonerBaseinfo info = prisonerService.getBasicInfoByCrimid(crimid);//查询犯人基本信息
		TbprisonerBaseCrime crime = prisonerService.getCrimeByCrimid(crimid);//查询犯人犯罪处罚信息
		if(idnumber != null && !"".equals(idnumber)){
			String docConent =  flowBaseService.getDocConentByFlowdraftId(idnumber);
			docconent.add(docConent);
			request.setAttribute("flowdraftid", idnumber);
		}else{
			String tempid = "SDXF_XFZX_ZFLJTZS";
			request.setAttribute("tempid", tempid);
			SystemUser user = getLoginUser(request);
			String departid=user.getDepartid();//根据用户Id获取所在部门Id
			HashMap<String, Object> map = new HashMap<String, Object>();
			String year = new SimpleDateFormat("yyyy").format(new Date());//定义时间格式获取年号
			String xuhao = flowBaseService.getFlowXuHao(year, "doc_tzjssp", null, user.getDepartid());//获取序号
			TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, departid);
			if (template != null) {
				docconent.add(template.getContent());
			}
			map.put("cbiname",info.getName());
			
			String jailname = systemOrganizationService.getOrginfoByOrgid(departid).getName();
			
			map.put("dapert",jailname);
			map.put("text21", DateUtil.dateFormatForAip(new Date()));
			map.put("xuhao", xuhao);
			request.setAttribute("formDatajson", new JSONObject(map).toString());
		}
		request.setAttribute("flowdefid", "other_zfljtzsp");
		request.setAttribute("orgid", crime.getOrgid1());
		request.setAttribute("applyid", crimid);
		request.setAttribute("applyname",info.getName());
		request.setAttribute("flowdraftid",idnumber);
		request.setAttribute("menuid",menuid);
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("aip/showLeaveAotice");
	}
	
	
}


package com.sinog2c.mvc.controller.penaltyPerform.inPrisonManagement;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;

/**
 * @author kexz
 *不予收监审批
 * 2014-7-17
 */
@Controller
@RequestMapping(value = "/inPrisonApproval")
public class InPrisonApproval extends ControllerBase {
	@Resource
	private SystemModelService systemModelService;
	@Resource
	public SystemLogService logService;
	@Resource
	private SystemCodeService systemCodeService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Resource
	protected FlowBaseOtherService flowBaseOtherService;
	@Resource
	protected PrisonerService prisonerService;
	/**
	 * 跳转不予收监审批页面
	 * 
	 * @author liuxin
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toInPrisonApproval.page")
	public ModelAndView toInPrisonApproval(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/inPrisonManagement/inPrisonApproval");
	}
	/**
	 * 方法描述：列表页面
	 * 
	 * @author 
	 */
	@RequestMapping("/ajaxGetInPrisonApprovalList.json")
	@ResponseBody
	public HashMap<String, Object> ajaxGetInPrisonApprovalList(HttpServletRequest request) throws Exception{
		String key = request.getParameter("key")==null? "":request.getParameter("key");
		key = URLDecoder.decode(key,"UTF-8");
		SystemUser user = getLoginUser(request);
		HashMap<String, Object> result = new HashMap<String, Object>();
		//分页
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));        
		//字段排序
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("departid", user.getOrgid());
		map.put("userid", getLoginUser(request).getUserid());
		map.put("key", key);
		map.put("flowdefid", "other_zfsjsp");
    	map.put("sortField", sortField);
    	map.put("sortOrder", sortOrder);
    	map.put("start", String.valueOf(start));
    	map.put("end",String.valueOf(end));
    	List<FlowBase> list = flowBaseService.getBaseListNotInforming(map);
		int count = flowBaseService.getBaseListNotInformingCount(map);
		result.put("total", count);
		result.put("data", list);
		return result;		
	}
	/** 
	 *显示不予收监审批新增表单
	 * @author liuxin
	 * 2014-7-25
	 */
	@RequestMapping("/inPrisonApprovalAdd.page")
	public ModelAndView inPrisonApprovalAdd(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		//根据根据登录用户去获取对应部门id
		String menuid = request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, user.getDepartid());
		//处理下拉框的值
		Map<String, Object> selectmap = new HashMap<String, Object>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("applyid", flowBaseService.getSeqBySeqname("seq_other_zfsjsp"));    
		selectmap.put("cbigendername", systemCodeService.getcodeValue("GB003"));
		selectmap.put("cbinationname", systemCodeService.getcodeValue("GB006"));
		if (template != null) {
			docconent.add(template.getContent());
		}
		request.setAttribute("orgid",user.getOrgid());
		request.setAttribute("tempid", tempid);
		request.setAttribute("flowdefid", "other_zfsjsp");
		request.setAttribute("formcontent", docconent.toString());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		//下拉框
		request.setAttribute("selectDatajson",new JSONObject(selectmap).toString());
		return new ModelAndView("penaltyPerform/inPrisonManagement/inPrisonApprovalAdd");
	}
	
	/**
	 * 查看、修改不予收监审批时，根据文书Id查询出大字段回显
	 * @author 
	 */
	@RequestMapping(value = "/inPrisonApprovalLook.page")
	public ModelAndView editorlook(HttpServletRequest request) {
		returnResourceMap(request);
		SystemUser user = getLoginUser(request);
		JSONArray docconent = new JSONArray();
		String flowdraftid = request.getParameter("flowdraftid")==null? "":request.getParameter("flowdraftid");
		FlowBaseOther baseOther  = flowBaseOtherService.findByFlowdraftId(flowdraftid);
		if (baseOther != null) {
			docconent.add(baseOther.getDocconent());
		}
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("flowdefid", "other_zfsjsp");
		request.setAttribute("orgid",user.getOrgid());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("penaltyPerform/inPrisonManagement/inPrisonApprovalAdd");
	}	
}

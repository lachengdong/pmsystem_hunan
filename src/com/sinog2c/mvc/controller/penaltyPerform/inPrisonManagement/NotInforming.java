package com.sinog2c.mvc.controller.penaltyPerform.inPrisonManagement;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
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
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseOtherService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.DateUtil;

/**
 * @author kexz 不予收监通知 2014-7-17
 */
@Controller
@RequestMapping("/notInforming")
public class NotInforming extends ControllerBase {
	@Autowired
	private SystemCodeService systemCodeService;
	@Resource
	protected FlowBaseService flowBaseService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Resource
	protected FlowBaseOtherService flowBaseOtherService;
	/**
	 * 菜单跳转页面
	 * @return 页面
	 *  @author wangxf
	 */
	@RequestMapping("/toNotInforming.page")
	public ModelAndView toNotInforming(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/inPrisonManagement/notInforming");
	}
	/**
	 * 列表页展示数据(暂时没数据)
	 * @return 数据集合
	 *  @author wangxf
	 */
	@RequestMapping("/ajaxGetNotInforming.json")
	@ResponseBody
	public HashMap<String, Object> ajaxGetNotInforming(HttpServletRequest request) throws Exception{
		SystemUser user = getLoginUser(request);
		HashMap<String, Object> result = new HashMap<String, Object>();
		String key = request.getParameter("key")==null?"":request.getParameter("key");
		key = URLDecoder.decode(key,"UTF-8");
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));    
		String sortField = request.getParameter("sortField");//字段排序
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("key", key);
		map.put("departid", user.getOrgid());
		map.put("flowdefid", "doc_zfbysjtzsp");
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
	 * 点击添加
	 * 
	 * @param request
	 * @return 表单页面
	 * @author wangxf
	 */
	@RequestMapping("/notInformingAdd.page")
	public ModelAndView notInformingAdd(HttpServletRequest request) {
		// 模板编号
		String tempid = request.getParameter("tempid");
		String menuid = request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		SystemUser user = getLoginUser(request);
		String userId = user.getDepartid();
		SystemOrganization org = systemOrganizationService.getByOrganizationId(userId);
		JSONArray docconent = new JSONArray();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, userId);
		if (template != null) {
			docconent.add(template.getContent());
			Map<String, Object> map = new HashMap<String, Object>();
			Date date = new Date();
			String year = new SimpleDateFormat("yyyy").format(date);// 定义年号
			String xuhao = flowBaseService.getFlowXuHao(year, "doc_zfbysjtzsp", null, user.getDepartid());
			Map<String, Object> selectmap = new HashMap<String, Object>();
			map.put("text21", org.getName() + template.getTempname());
			map.put("depshrotname", org.getShortname());
			map.put("noyear", year);
			map.put("xuhao", xuhao);
			selectmap.put("cbisex", systemCodeService.getcodeValue("GB003"));
			map.put("jbr", user.getName());
			map.put("lrsj", DateUtil.dateFormatForAip(date));
			map.put("applyid", flowBaseService.getSeqBySeqname("seq_doc_zfbysjtzsp"));    
			request.setAttribute("formDatajson", new JSONObject(map).toString());
			request.setAttribute("selectDatajson", new JSONObject(selectmap).toString());
		}
		request.setAttribute("tempid", tempid);
		request.setAttribute("flowdefid", "doc_zfbysjtzsp");
		request.setAttribute("orgid",user.getOrgid());
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("penaltyPerform/inPrisonManagement/notInformingAdd");
	}
	/**
	 * 查看、修改不予收监通知时，根据文书Id查询出大字段回显
	 * @author 
	 */
	@RequestMapping(value = "/notInformingEdit.page")
	public ModelAndView editorlook(HttpServletRequest request) {
		returnResourceMap(request);
		SystemUser user = getLoginUser(request);
		JSONArray docconent = new JSONArray();
		String flowdraftid =request.getParameter("flowdraftid")==null?"":request.getParameter("flowdraftid");
		String docConent  = flowBaseService.getDocConentByFlowdraftId(flowdraftid);
		if (docConent != null) {
			docconent.add(docConent);
		}
		request.setAttribute("orgid",user.getOrgid());
		request.setAttribute("flowdraftid", flowdraftid);
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("penaltyPerform/inPrisonManagement/notInformingAdd");
	}	
}

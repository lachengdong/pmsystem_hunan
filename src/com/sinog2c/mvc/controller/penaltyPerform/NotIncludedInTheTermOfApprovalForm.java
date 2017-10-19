/**
 * 
 */
package com.sinog2c.mvc.controller.penaltyPerform;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;

/**
 * @author kexz
 *不计入刑期审批表
 * 2014-7-29
 */
@Controller
public class NotIncludedInTheTermOfApprovalForm extends ControllerBase{
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private PrisonerService prisonerService;
	
	@RequestMapping("/NotIncludedInTheTermOfApprovalForm")
	public ModelAndView NotIncludedInTheTermOfApprovalForm(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/penaltyPerform/NotIncludedInTheTermOfApprovalForm.jsp"));
	}
	
	@RequestMapping("/NotIncludedInTheTermOfApprovalFormAdd")
	public ModelAndView NotIncludedInTheTermOfApprovalFormAdd(HttpServletRequest request){
		String crimid = request.getParameter("crimid");
		request.setAttribute("crimid", crimid);
		//json数组
		JSONArray docconent = new JSONArray();
		//获取对应表单打印中表单编号
		String tempid = request.getParameter("tempid");
		//获取当前登录的用户
		SystemUser user = getLoginUser(request);
		//根据根据登录用户去获取对应部门id
		String deptid=user.getDepartid();
		//根据表单编号获取表单
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(tempid, deptid);
		//通过部门id去找组织机构的信息
		SystemOrganization org=systemOrganizationService.getByOrganizationId(deptid);
		TbprisonerBaseinfo tbinfo=prisonerService.getBasicInfoByCrimid(crimid);
		Map map=new HashMap();
		//取出组织机构的名字赋值给表单上的sdcourtcheck
		map.put("sdcourtcheck",org.getFullname());
		map.put("cbiname",tbinfo.getName());
		map.put("cbigendername",tbinfo.getGender());
		map.put("bzradress", tbinfo.getAddressarea());
		if (template != null) {
			docconent.add(template.getContent());
		}
		//转json
		request.setAttribute("tempid", tempid);
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/aip/NotIncludedInTheTermOfApprovalFormAdd.jsp"));
	}
}

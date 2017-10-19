package com.sinog2c.mvc.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysLocalcode;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.system.SystemLocalcodeService;
import com.sinog2c.service.api.system.SystemOrganizationService;

@Controller
public class SystemLocalCodeController extends ControllerBase{
	@Autowired
	private SystemLocalcodeService systemLocalcodeService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	
	@RequestMapping(value = "/ajaxLocalcodeList")
	@ResponseBody
	public Object ajaxLocalcodeList(HttpServletRequest request){
		String codetype = request.getParameter("codetype");
		String pcodeid = request.getParameter("pcodeid");
		SystemUser user = getLoginUser(request);
		SystemOrganization provinceOrg = systemOrganizationService.getProvinceCode(user.getOrgid());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("codetype", codetype);
		map.put("pcodeid", pcodeid);
		map.put("orgpid", provinceOrg.getOrgid());
		List<TbsysLocalcode> codeList = systemLocalcodeService.getLocalcodeList(map);
		return codeList;
	}	
}

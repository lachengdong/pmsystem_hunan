package com.sinog2c.mvc.controller.prisoner;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.prisoner.PrisonerService;


@Controller
public class PrisonerController extends ControllerBase {
	
	@Resource
	private PrisonerService prisonerService;
	
	@RequestMapping(value = "/toThreeKindCrimeIdentifyPage.page")
	public ModelAndView releaseInformchoose(HttpServletRequest request) {
		Map<String,Object> map = this.parseParamMap(request);
		this.addMap2Attribute(map, request);
		
		ModelAndView mav = new ModelAndView("prisoner/threeKindCrimeIdentifyPage");
		return mav;
	}
	
	
	//保存修改信息  
	@RequestMapping(value = { "/saveThreeKindCrimeIdentify.json" })
	@ResponseBody
	public Object saveThreeKindCrimeIdentify(HttpServletRequest request){
		
		SystemUser user = this.getLoginUser(request);
		Map<String,Object> map = this.parseParamMap(request);
		
		return prisonerService.saveThreeKindCrimeIdentify(map, user);
		
	}
	
}

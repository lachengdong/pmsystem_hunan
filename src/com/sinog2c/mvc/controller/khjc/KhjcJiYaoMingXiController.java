package com.sinog2c.mvc.controller.khjc;
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
import com.sinog2c.dao.api.khjc.KhjcJiYaoMingXiMapper;
import com.sinog2c.dao.api.khjc.KhjcMeetingInfoMapper;
import com.sinog2c.dao.api.khjc.KhjcTbflowBaseDocMapper;
import com.sinog2c.model.khjc.KhjcMeetingInfo;
import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.prisoner.TbprisonerSocialRelation;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.khjc.KhjcCriminalScoreService;
import com.sinog2c.service.api.khjc.KhjcJiYaoMingXiService;
import com.sinog2c.service.api.khjc.KhjcMeetingService;
import com.sinog2c.service.api.khjc.KhjcPublicService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
/**
 * 考核奖惩相关
 * @author yanqutai
 */
@Controller
public class KhjcJiYaoMingXiController extends ControllerBase {
	@Autowired
	private KhjcJiYaoMingXiMapper khjcJiYaoMingXiMapper;
	@Resource
	private ChooseCriminalService chooseCriminalService;
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private KhjcMeetingService khjcMeetingService;
	@Resource
	private KhjcJiYaoMingXiService khjcJiYaoMingXiService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private KhjcCriminalScoreService khjcCriminalService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	
	
	/**
	 * 纪要明细/政策法规保存
	 * yanqutai
	 */
	@RequestMapping(value = "/saveJiYaoMingXiInfo")
	@ResponseBody
	public String saveJiYaoMingXiInfo(HttpServletRequest request){
		SystemUser user = this.getLoginUser(request);
		return khjcJiYaoMingXiService.saveJiYaoMingXiInfo(request,user);
	}
	
	/**
	 * 跳转纪要明细列表页面
	 * @author yanqutai
	 * @param request
	 */
	@RequestMapping(value = "khjcJiYaoMingXiPage")
	public ModelAndView khjcJiYaoMingXiPage(HttpServletRequest request){
		returnResourceMap(request);
		String type = request.getParameter("type")==null?"":request.getParameter("type");//会议记录的类型
		request.setAttribute("type", type);
		return new ModelAndView("khjc/khjcJiYaoMingXiPage");
	}
	
}

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
import com.sinog2c.service.api.khjc.KhjcMeetingService;
import com.sinog2c.service.api.khjc.KhjcPublicService;
import com.sinog2c.service.api.khjc.PublicBaseDocService;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.api.prisoner.PrisonerService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemOrganizationService;
/**
 * 考核奖惩相关
 * @author yanqutai
 */
@Controller
public class KhjcMeetingController extends ControllerBase {
	@Autowired
	private KhjcMeetingInfoMapper khjcMeetingInfoMapper;
	@Resource
	private ChooseCriminalService chooseCriminalService;
	@Resource
	private PublicBaseDocService publicBaseDocService;
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private KhjcMeetingService khjcMeetingService;
	@Resource
	private PrisonerService prisonerService;
	@Resource
	private KhjcCriminalScoreService khjcCriminalService;
	@Resource
	private SystemOrganizationService systemOrganizationService;
	
	/**
	 * 跳转会议记录页面
	 * @author yanqutai
	 * @param request
	 */
	@RequestMapping(value = "/khjcMeetingPage")
	public ModelAndView khjcMeetingPage(HttpServletRequest request){
		returnResourceMap(request);
		String type = request.getParameter("type")==null?"":request.getParameter("type");//会议记录的类型
		request.setAttribute("type", type);
		return new ModelAndView("khjc/khjcMeetingPage");
	}
	
	/**
	 * 会议记录数据保存
	 * yanqutai
	 */
	@RequestMapping(value = "/saveMeetingInfo")
	@ResponseBody
	public String saveMeetingInfo(HttpServletRequest request){
		khjcMeetingService.saveMeetingInfo(request);
		return "";
	}
	
	/**
	 * 根据templetid查询相关的会议记录信息
	 * yanqutai
	 * @author 
	 */
	@RequestMapping(value="getKhjcMeetingByType")
	@ResponseBody
	public HashMap<String, Object> getKhjcMeetingByType(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Map paraMap = new HashMap();
		
		String crimid = request.getParameter("crimid");
		String tempid = request.getParameter("tempid");
		String key = request.getParameter("key");
		String nodeid = request.getParameter("nodeid");
		paraMap.put("crimid", crimid);
		paraMap.put("tempcondition", tempid);
		paraMap.put("key", key);
		paraMap.put("nodeid", nodeid);
		
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex")==null?"1":request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize")==null?"20":request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");//排序字段
		String sortOrder = request.getParameter("sortOrder");//排序方式
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize - 1;
		
		paraMap.put("start", start);
		paraMap.put("end", end);
		paraMap.put("sortField", sortField);
		paraMap.put("sortOrder", sortOrder);
		
		
		List<Map> list = publicBaseDocService.getBaseDocByCondition(paraMap);
		int count = publicBaseDocService.countBaseDocByCondition(paraMap);
		result.put("total", count);
		result.put("data", list);
		return result;
	}
	
	/**
	 * 查看、修改罪犯调查评估委托函时，根据文书Id查询出大字段回显
	 * @author 
	 */
	@RequestMapping(value="khjcMeetingLook")
	public ModelAndView khjcMeetingLook(HttpServletRequest request) {
		returnResourceMap(request);
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");
		request.setAttribute("docid", docid);
		KhjcMeetingInfo document = khjcMeetingInfoMapper.selectByPrimaryKey(docid);
		if (document != null) {
			docconent.add(document.getDocconent());
		}
		request.setAttribute("formcontent", docconent.toString());
		return new ModelAndView("aip/khjc/zhuanXiangJiangFenAdd");
	}

}

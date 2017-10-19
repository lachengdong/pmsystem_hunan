package com.sinog2c.mvc.controller.meeting;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.gkzx.common.LogCommon;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysDocument;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.api.meeting.TbprisonerMeetingService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.TbsysDocumentService;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * @author wine(张永有) 评议记录 2014-12-7
 */
@Controller
@RequestMapping("/meeting")
public class MeetController extends ControllerBase {
	@Resource
	private TbprisonerMeetingService tbprisonerMeetingService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private SystemModelService systemModelService;
	@Autowired
	private TbsysDocumentService tbsysDocumentService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private SystemCodeService systemCodeService;

	/**
	 * 跳转收监日志列表页
	 * 
	 * @author wine(张永有)
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/meeting.page")
	public ModelAndView tomeeting(HttpServletRequest request) {
		returnResourceMap(request);
		return new ModelAndView("meeting/meet");
	}

	/**
	 * 方法描述：列表页面
	 * 
	 * @author wine(张永有)
	 */
	@RequestMapping("/getMeetingList.json")
	@ResponseBody
	public HashMap<String, Object> getMeetingList(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		String deptid = user.getDepartid();
		HashMap<String, Object> result = new HashMap<String, Object>();
		String tempid = request.getParameter("tempid");
		String key = request.getParameter("key") == null ? "" : request
				.getParameter("key");
		try {
			key = java.net.URLDecoder.decode(key, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbsysDocument> list = tbsysDocumentService.getTbsysDocumentList(
				pageIndex, pageSize, key, tempid, "", deptid, sortField,
				sortOrder);
		List<TbsysDocument> newlist = new ArrayList<TbsysDocument>();
		TbsysDocument opidmap = new TbsysDocument();
		String opid = user.getName();
		for (int i = 0; i < list.size(); i++) {
			opidmap = list.get(i);
			String opidname = (String) opidmap.getOpid();
			if (opidname != null && opid.equals(opidname)) {
				newlist.add(list.get(i));
			}
		}
		int count = tbsysDocumentService.getCount(key, tempid, "", deptid);
		result.put("total", count);
		result.put("data", newlist);
		return result;
	}

	/**
	 * 跳转新增页面
	 * 
	 * @author wine(张永有)
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/meetingAdd.page")
	public ModelAndView meetingAdd(HttpServletRequest request) {
		JSONArray docconent = new JSONArray();
		String tempid = request.getParameter("tempid");
		request.setAttribute("tempid", tempid);
		// 获取当前登录的用户
		SystemUser user = getLoginUser(request);
		String deptid = user.getDepartid();
		TbsysTemplate template = systemModelService.getTemplateAndDepartid(
				tempid, deptid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tempid", user.getOrganization().getName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		map.put("listyear", sdf.format(new Date()));
		map.put("opid", user.getName());
		Map<String, Object> selectmap = new HashMap<String, Object>();
		selectmap.put("mname", systemCodeService.getcodeValue("GB005T"));
		if (template != null) {
			docconent.add(template.getContent());
		}
		// 转json
		request.setAttribute("selectDatajson", new JSONObject(selectmap)
				.toString());
		request.setAttribute("formDatajson", new JSONObject(map).toString());
		request.setAttribute("formcontent", docconent.toString());
		returnResourceMap(request);
		return new ModelAndView("meeting/meetadd");
	}

	/**
	 * 修改/保存大字段
	 * 
	 * @author wine(张永有)
	 * @throws ParseException
	 */
	@RequestMapping(value = "/savemeeting.json")
	@ResponseBody
	public String savemeeting(HttpServletRequest request) throws ParseException {
		int rows = 0;// 保存结果：0、失败，1、成功
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		try {
			rows = tbprisonerMeetingService.savemeet(user, request);
			if (rows > 0) {
			}
			log.setLogtype(LogCommon.SHOUJIANRIZHI + LogCommon.OPERATE);
			log.setOpaction(LogCommon.ADD);
			log.setOpcontent(LogCommon.SHOUJIANRIZHI + LogCommon.ADD);
			log.setOrgid(user.getUserid());
			log.setRemark(LogCommon.SHOUJIANRIZHI + LogCommon.ADD);
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return "";
	}

	/**
	 * 查看、修改收监日志时，根据文书Id查询出大字段回显
	 * 
	 * @author wine(张永有)
	 */
	@RequestMapping(value = "/meetingLook.page")
	public ModelAndView editorlook(HttpServletRequest request) {
		JSONArray docconent = new JSONArray();
		String docid = request.getParameter("docid");
		request.setAttribute("docid", docid);
		String menuid=request.getParameter("menuid")==null?"":request.getParameter("menuid");
		request.setAttribute("menuid", menuid);
		if (!StringNumberUtil.isNullOrEmpty(docid)) {
			TbsysDocument document = tbsysDocumentService.getTbsysDocument(docid, null, null, null);
			if (document != null)
				docconent.add(document.getContent());
			request.setAttribute("formcontent", docconent.toString());
		}
		return new ModelAndView("meeting/meetadd");
	}

	/**
	 * 根据文书Id删除数据
	 * 
	 * @author wine(张永有) 2014-7-24 17:11:45
	 */
	@RequestMapping(value = "/meetingDelete.json")
	@ResponseBody
	public int inLogDelete(HttpServletRequest request) {
		int result = 0;
		short status = 0;
		String docid = request.getParameter("docid");
		result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		SystemLog log = new SystemLog();
		log.setLogtype(LogCommon.SHOUJIANRIZHI + LogCommon.OPERATE);
		log.setOpaction(LogCommon.DEL);
		log.setOpcontent(LogCommon.SHOUJIANRIZHI + LogCommon.DEL);
		log.setOrgid(user.getUserid());
		log.setRemark(LogCommon.SHOUJIANRIZHI + LogCommon.DEL);
		if (result == 1)
			status = 1;// 如果新增或修改成功，日志记录成功
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}
	
	
	@RequestMapping(value = "/toMeetRecord.page")
	public ModelAndView toMeetRecord(HttpServletRequest request){
		// 模型和视图.
		Map<String,Object> map = super.parseParamMap(request);
		String flowdraftid = StringNumberUtil.getStrFromMap("flowdraftid", map);
		if(StringNumberUtil.notEmpty(flowdraftid)){
			FlowBase fb = flowBaseService.getFlowBaseByMap(map);
			map.put("applyid", fb.getApplyid());
		}
		super.addMap2Attribute(map, request);
		ModelAndView mav = new ModelAndView("system/meetRecordPage");
		return mav;
	}
	
	/**
	 * 描述:某罪犯的会议记录
	 * @author YangZR
	 * @date  2015-05-10
	 */
	@RequestMapping(value = "/getMeetRecordList.json")
	@ResponseBody
	public Object getMeetRecordList(HttpServletRequest request){
		
//		result = tbsysDocumentService.deleteTbsysDocument(docid);
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();
		String applyid = request.getParameter("applyid");
		String tempid = request.getParameter("tempid");
		String type = request.getParameter("type");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("applyid", applyid);
		map.put("tempid", tempid);
		map.put("type", type);
		map.put("departid", departid);
		return tbprisonerMeetingService.getMeetTreeList(map,type);
		
	}
	
	
	/**
	 * 查询会议记录
	 * @return
	 */
	@RequestMapping("/selectMeetingDetail.json")
	@ResponseBody
	public Object selectMeetingDetail(HttpServletRequest request){
		String batchid = request.getParameter("batchid")==null?"":request.getParameter("batchid").toString();
		SystemUser user = getLoginUser(request);
		Map<String, Object> paraMap = parseParamMap(request);//参数集合
		paraMap.put("departid",user.getDepartid());
		paraMap.put("orgid",user.getOrgid());
		paraMap.put("batchid",batchid);
		List<Map> data= tbprisonerMeetingService.selectMeetingDetail(paraMap);
		return data;
	}
	
	/**
	 * 查询会议记录
	 * @return
	 */
	@RequestMapping("/selectPublicityDetail.json")
	@ResponseBody
	public Object selectPublicityDetail(HttpServletRequest request){
		String batchid = request.getParameter("batchid")==null?"":request.getParameter("batchid").toString();
		String tempid = request.getParameter("tempid") == null?"":request.getParameter("tempid").toString();
		SystemUser user = getLoginUser(request);
		Map<String, Object> paraMap = parseParamMap(request);//参数集合
		paraMap.put("departid",user.getDepartid());
		paraMap.put("orgid",user.getOrgid());
		paraMap.put("batchid",batchid);
		paraMap.put("tempid", tempid);
		List<Map> data= tbprisonerMeetingService.selectPublicityDetail(paraMap);
		return data;
	}
	
}

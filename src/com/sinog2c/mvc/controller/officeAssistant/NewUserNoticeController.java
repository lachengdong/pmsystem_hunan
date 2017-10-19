package com.sinog2c.mvc.controller.officeAssistant;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import com.alibaba.fastjson.JSONObject;
import com.sinog2c.model.officeAssistant.TbuserNotice;
import com.sinog2c.model.officeAssistant.TbuserUserNotice;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.officeAssistant.SchedulingService;
import com.sinog2c.service.api.officeAssistant.UserNoticeService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemUserService;

/**
 * 用户通知（授权、日程、通知）
 * 
 * @author shily 2014-7-9 15:35:46
 */
@Controller
public class NewUserNoticeController extends ControllerBase {

	@Autowired
	private UserNoticeService userNoticeService;
	@Autowired
	private SchedulingService schedulingService;
	@Resource
	public SystemLogService logService;
	@Autowired
	private SystemOrganizationService systemOrganizationService;
	@Autowired
	private SystemUserService systemUserService;
	
	/**
	 * 授权管理
	 * 
	 * @author shily 2014-7-9 15:36:20
	 */
	@RequestMapping(value = "/impowerManage")
	public ModelAndView impowerManage() {
		ModelAndView mav = null;
		View view = new InternalResourceView(
				"/WEB-INF/JSP/officeAssistant/impowerManage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}

	/**
	 * 授权管理取数据
	 * 
	 * @author shily 2014-7-9 15:36:20
	 */
	@RequestMapping(value = "/getDataForImpowerManage")
	@ResponseBody
	public HashMap<String, Object> getDataForImpowerManage(HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		List<TbuserNotice> list = userNoticeService.getDataForImpowerManage(1);
		String key = request.getParameter("key");
		if (key != null && !"".equals(key)) {
			List<TbuserNotice> tempList = new ArrayList<TbuserNotice>();
			for (TbuserNotice notice : list) {
				if (notice.getUsername().contains(key)) {
					tempList.add(notice);
				}
			}
			list = tempList;
		}
		result.put("total", list.size());
		List<TbuserNotice> temp = new ArrayList<TbuserNotice>();
		for (int i = pageSize * pageIndex; i < pageSize * (pageIndex + 1); i++) {
			if (i < list.size()) {
				temp.add(list.get(i));
			} else {
				break;
			}
		}
		result.put("data", temp);
		return result;
	}

	/**
	 * 添加用户
	 */
	@RequestMapping(value = "/addImpowerManage")
	public ModelAndView adduser(HttpServletRequest request) {
		ModelAndView mav = null;
		String type = request.getParameter("type");
		View view = null;
		if ("add".equals(type)) {
			view = new InternalResourceView(
					"/WEB-INF/JSP/officeAssistant/addImpowerManage.jsp");
			mav = new ModelAndView(view);
		} else {
			int strid = Integer.parseInt(request.getParameter("id"));
			TbuserNotice tbuserNotice = schedulingService
					.getDataByNoticeId(strid);
			Map<String, Object> map = new HashMap<String, Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			map.put("noticeid", tbuserNotice.getNoticeid());
			map.put("remark", tbuserNotice.getRemark());
			map.put("username", tbuserNotice.getUsername());
			map.put("aistarttime", sdf.format(tbuserNotice.getStarttime()));
			map.put("aiendtime", sdf.format(tbuserNotice.getEndtime()));
			map.put("shouquan", tbuserNotice.getState());

			view = new InternalResourceView(
					"/WEB-INF/JSP/officeAssistant/updateImpowerManage.jsp");
			mav = new ModelAndView(view, "tbuserNotice", map);
		}
		return mav;
	}
	

	/**
	 * 发送用户通知
	 * 
	 * @author shily 2014-7-9 15:36:20
	 */
	@RequestMapping(value = "/saveUserNotice")
	@ResponseBody
	public String saveUserNotice(HttpServletRequest request) {
		try {
			String userid = request.getParameter("userid");
			String message = request.getParameter("message");
			TbuserNotice tbuserNotice = new TbuserNotice();
			tbuserNotice.setMessagetype(3);
			tbuserNotice.setTitle("用户通知");
			tbuserNotice.setContent(message);
			tbuserNotice.setUsername(userid);
			tbuserNotice.setStarttime(new Date());
			tbuserNotice.setEndtime(new Date());
			tbuserNotice.setState((short) 0);
			tbuserNotice.setOptime(new Date());
			tbuserNotice.setOpid("001");
			int result = userNoticeService.saveMessage(tbuserNotice);
			return "" + result;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	/* 
	 * 批量删除用户事件
	 */
	@RequestMapping(value = { "/delectIdList" })
	public void delectIdList(HttpServletRequest request, HttpServletResponse response){
		List idList=new ArrayList();
		String noticeids=request.getParameter("noticeid");
		String temp[]=null;
		if(null!=noticeids&&!"".equals(noticeids)){
			temp=noticeids.split(",");
		}
		if(temp.length>0){
			for(int i=0;i<temp.length;i++){
				idList.add(temp[i]);
			}
		}
		userNoticeService.delectIdList(idList);
	}
	/*
	 * 用户事件置为已读
	 */
	@RequestMapping(value = { "/setEvent" })
	public void setEvent(HttpServletRequest request, HttpServletResponse response){
		SystemUser user=(SystemUser) getSessionAttribute(request, ControllerBase.SESSION_USER_KEY); 
		String noticeid=request.getParameter("noticeid");
		String messagetype=request.getParameter("messagetype");
		String state=request.getParameter("state");
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("userid", user.getUserid());
		map.put("noticeid", noticeid);
		map.put("state", state);
		userNoticeService.setEvent(map);
	}
	
	/*
	 * 业务提醒浏览
	 */
	@RequestMapping(value={"/toEventManage"})
	public ModelAndView toEventManage(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav;
		mav=new ModelAndView("main/eventList");
		return mav;
	} 
	
	/**
	 * 用户通知
	 * 
	 * @author shily 2014-7-9 15:36:20
	 */
	@RequestMapping(value = "/userNotice")
	public ModelAndView userNotice() {
		ModelAndView mav = null;
		View view = new InternalResourceView(
				"/WEB-INF/JSP/officeAssistant/userNotice.jsp");
		mav = new ModelAndView(view);
		return mav;
	}

	/**
	 * 获取全部用户
	 * 
	 * @author shily 2014-7-13 15:07:43
	 */
	@RequestMapping(value = "/getAllUserForNotice")
	@ResponseBody
	public Object getAllUserForNotice(HttpServletRequest request) {
		SystemUser user = getLoginUser(request);
		SystemOrganization so = user.getOrganization();
		List<SystemOrganization>  orgList = systemOrganizationService.listByOrganizationPid(so.getOrgid(), null);
		List<SystemOrganization> treeList = orgList;
		int length = orgList.size();
		Map<String,Object> map = new HashMap<String,Object>();
		for(int i=0; i<length; i++) {
			map.put("orgid", orgList.get(i).getOrgid());
			List<SystemUser> userList = systemUserService.getUsersByOrgid(map);
			for(int j=0; j<userList.size(); j++) {
				SystemOrganization treeObj = new SystemOrganization();
				treeObj.setOrgid(userList.get(j).getUserid());
				treeObj.setPorgid(orgList.get(i).getOrgid());
				treeObj.setName(userList.get(j).getName());
				treeList.add(treeObj);
			}
			if(userList.isEmpty() || userList.size()==0) {
				treeList.remove(orgList.get(i));
				length--;
				i--;
			}
		}
		return treeList;
	}

	/**
	 * 发送用户通知
	 * 
	 * @author shily 2014-7-9 15:36:20
	 */
	@RequestMapping(value = "/sendMessage")
	@ResponseBody
	public String sendMessage(HttpServletRequest request) {
		try {
			SystemUser user = getLoginUser(request);
			String noticePk = userNoticeService.getNoticePk();
			String userids = request.getParameter("userids");
			String message = request.getParameter("message");
			String noticeTitle = request.getParameter("noticeTitle");
			TbuserNotice tbuserNotice = new TbuserNotice();
			tbuserNotice.setMessagetype(3);
			if(noticeTitle!=null && !"".equals(noticeTitle)) {
				tbuserNotice.setTitle(noticeTitle);
			} else {
				tbuserNotice.setTitle("用户通知");
			}
			tbuserNotice.setContent(message);
			tbuserNotice.setUsername(userids);
			tbuserNotice.setStarttime(new Date());
			tbuserNotice.setEndtime(new Date());
			tbuserNotice.setState((short) 0);
			tbuserNotice.setOptime(new Date());
			tbuserNotice.setOpid(user.getUserid());
			tbuserNotice.setNoticeid(Integer.valueOf(noticePk));
			
			TbuserUserNotice tbuserUserNotice = new TbuserUserNotice();
			tbuserUserNotice.setNoticeid(Integer.valueOf(noticePk));
			tbuserUserNotice.setOptime(new Date());
			tbuserUserNotice.setOpid(user.getUserid());
			int result = userNoticeService.saveUserMessage(tbuserNotice,tbuserUserNotice);
			return "" + result;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 获取全部用户
	 * 
	 * @return：
	 */
	@RequestMapping(value = "/ajaxShowAuthor")
	@ResponseBody
	public Object getUser(HttpServletRequest request) {
		return getAllUserForNotice(request);
	}

	/**
	 * 保存授权
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @author wangxf 2014-7-17 15:03:14
	 */
	@RequestMapping(value = "/saveAuthorizationAction")
	@ResponseBody
	public String saveUserPower(HttpServletRequest request) throws Exception {
		SystemUser user = getLoginUser(request);
		short status = 1;
		String result = "success";
		
		TbuserNotice tbuserNotice = new TbuserNotice();
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

		String remark = request.getParameter("remark");
		String aistarttime = request.getParameter("aistarttime").substring(1,
				11);
		String aiendtime = request.getParameter("aiendtime").substring(1, 11);
		String username = request.getParameter("uname");
		int shouquan = Integer.parseInt(request.getParameter("shouquan"));

		tbuserNotice.setMessagetype(1);
		tbuserNotice.setTitle("授权");
		tbuserNotice.setState((short) shouquan);
		tbuserNotice.setUsername(username);
		tbuserNotice.setRemark(remark);
		tbuserNotice.setContent("授权权利");
		tbuserNotice.setStarttime(formatDate.parse(aistarttime));
		tbuserNotice.setEndtime(formatDate.parse(aiendtime));
		tbuserNotice.setOpid("当前用户");
		tbuserNotice.setOptime(new Date());
		try {
			userNoticeService.saveMessage(tbuserNotice);
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
			status = 0;
		}

		SystemLog log = new SystemLog();
		log.setLogtype("授权操作");
		log.setOpaction("新增");
		log.setOpcontent("新增");
		log.setOrgid(user.getUserid());
		log.setRemark("新增授权操作");
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return "success";
	}

	/**
	 * 修改授权
	 * 
	 * @param request
	 * @return 是否成功
	 * @throws Exception
	 * @author wangxf
	 */
	@RequestMapping(value = "/updateAuthorizationAction")
	@ResponseBody
	public String updateUserPower(HttpServletRequest request) throws Exception {
		SystemUser user = getLoginUser(request);
		short status = 1;
		String result = "success";
		TbuserNotice tbuserNotice = new TbuserNotice();
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		int id = Integer.parseInt(request.getParameter("id"));
		String remark = request.getParameter("remark");
		String aistarttime = request.getParameter("aistarttime").substring(1,
				11);
		String aiendtime = request.getParameter("aiendtime").substring(1, 11);
		String username = request.getParameter("uname");
		int shouquan = Integer.parseInt(request.getParameter("shouquan"));
		System.out.println(shouquan);

		tbuserNotice.setMessagetype(1);
		tbuserNotice.setNoticeid(id);
		tbuserNotice.setTitle("授权");
		tbuserNotice.setState((short) shouquan);
		tbuserNotice.setUsername(username);
		tbuserNotice.setRemark(remark);
		tbuserNotice.setContent("授权权利");
		tbuserNotice.setStarttime(formatDate.parse(aistarttime));
		tbuserNotice.setEndtime(formatDate.parse(aiendtime));
		tbuserNotice.setOpid("当前用户");
		tbuserNotice.setOptime(new Date());

		try {
			int count = userNoticeService.updateMessage(tbuserNotice);
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
			status = 0;
		}

		SystemLog log = new SystemLog();
		log.setLogtype("授权操作");
		log.setOpaction("修改");
		log.setOpcontent("修改,noticeid=" + id);
		log.setOrgid(user.getUserid());
		log.setRemark("修改授权操作");
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}

		return result;
	}

	/**
	 * 根据消息Id删除指定信息
	 * 
	 * @param request
	 * @return
	 * @author wangxf
	 */
	@RequestMapping(value = "/removeTbuserNotice")
	@ResponseBody
	public String removeUser(HttpServletRequest request) {
		String result = "success";
		SystemUser user = getLoginUser(request);
		String id = request.getParameter("id");
		short status = 1;

		try {
			String[] strids = id.split(",");
			List idlist = new ArrayList();
			for (int i = 0; i < strids.length; i++) {
				idlist.add(Integer.parseInt(strids[i]));
			}
			userNoticeService.delectIdList(idlist);
		} catch (Exception e) {
			e.printStackTrace();
			status = 0;
			result = "error";
		}

		SystemLog log = new SystemLog();
		log.setLogtype("授权操作");
		log.setOpaction("删除");
		log.setOpcontent("删除,noticeid=" + id);
		log.setOrgid(user.getUserid());
		log.setRemark("删除授权操作");
		log.setStatus(status);
		try {
			logService.add(log, user);
		} catch (Exception e) {
			// 吃掉异常
		}

		return result;
	}

	@RequestMapping(value = "/selectTreeUser")
	public ModelAndView getTreeUser(HttpServletRequest request) {
		return new ModelAndView(new InternalResourceView(
				"/WEB-INF/JSP/officeAssistant/selectTreeUser.jsp"));
	}

	@RequestMapping(value = "/getUserTree")
	@ResponseBody
	public void getUserTree() {

	}
	
	@RequestMapping(value = "/getNextNotice")
	@ResponseBody
	public Object getNextNotice(HttpServletRequest request) {
		String noticeid = request.getParameter("noticeid");
		SystemUser user = getLoginUser(request);
		HashMap<String,String> map = new HashMap<String,String>();
		HashMap<String,String> nextMap = new HashMap<String,String>();
		HashMap<String,String> returnMap = new HashMap<String,String>(); 
		if(noticeid!=null && !"".equals(noticeid)) {
			map.put("userid", user.getUserid());
			map.put("noticeid", noticeid);
			map.put("state", "1");
			userNoticeService.updateNoticeByusernotice(map);
			nextMap.put("userid", user.getUserid());
			nextMap.put("state", "0");
			nextMap.put("noticeid", noticeid);
			List<TbuserNotice> noticeList = userNoticeService.getNextNotice(nextMap);
			if(noticeList.size()>0) {
				TbuserNotice tbuserNotice = noticeList.get(0);
				returnMap.put("noticeid", tbuserNotice.getNoticeid().toString());
				returnMap.put("title", tbuserNotice.getTitle());
				returnMap.put("content", tbuserNotice.getContent());
				returnMap.put("next", "1");
			} else {
				returnMap.put("next", "0");
			}
		}
		return returnMap;
	}
	/*
	 * 获取用户所有事件列表
	 */
	@RequestMapping(value={"/getAllEvent"})
	@ResponseBody
	public void getAllEvent(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map=new HashMap<String,Object>();
		SystemUser user = (SystemUser) getSessionAttribute(request, ControllerBase.SESSION_USER_KEY); 
		int pageIndex =(Integer)(request.getParameter("pageIndex")==null? "":Integer.parseInt(request.getParameter("pageIndex")));
		int pageSize = (Integer)(request.getParameter("pageSize")==null? "":Integer.parseInt(request.getParameter("pageSize")));  
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		int count=0;
		Map<String, Object> resultmap = new HashMap<String, Object>();
		String state=request.getParameter("state");
		map.put("userid", user.getUserid());
		map.put("departid", user.getDepartid());
		map.put("state", state);
		map.put("sortFileids", sortField);
		map.put("sortOrders", sortOrder);
		map.put("start", start);
		map.put("end", end);
		count=userNoticeService.getCount(map);
		List<TbuserNotice> list=userNoticeService.getAllEvent(map);
		TbuserNotice tn;
		ArrayList<Object> data= new ArrayList<Object>();
		for(int i=0;i<list.size();i++){
			tn=list.get(i);
			HashMap<String,Object> tempmap = new HashMap<String,Object>();
			tempmap.put("content", tn.getContent());
			tempmap.put("titile", tn.getTitle());
			tempmap.put("state", tn.getState());
			tempmap.put("noticeid", tn.getNoticeid());
			tempmap.put("messagetype", tn.getMessagetype());
			tempmap.put("opid",tn.getOpid());
			
			data.add(tempmap);
		}
		resultmap.put("data", data);
		resultmap.put("total", count);
		PrintWriter writer;
		String result="";
		try {
			result = new JSONObject(resultmap).toString();
			response.setContentType("text/plain;charset=utf-8"); 
			writer = response.getWriter();
			writer.write(result);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 设置主页面业务提醒状态
	 */
	@RequestMapping(value={"/setEventStatus"})
	@ResponseBody
	public void setEventStatus(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		SystemUser user = (SystemUser) getSessionAttribute(request, ControllerBase.SESSION_USER_KEY); 
		String noticeid=request.getParameter("noticeid");
		map.put("noticeid", noticeid);
		map.put("state", 1);
		map.put("userid", user.getUserid());
		userNoticeService.setEventStatus(map);
	}
	
}

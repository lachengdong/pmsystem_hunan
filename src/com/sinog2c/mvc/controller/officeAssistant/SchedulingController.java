package com.sinog2c.mvc.controller.officeAssistant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.officeAssistant.TbuserNotice;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.officeAssistant.SchedulingService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * 日程安排
 * 
 * @author hzl 2014-7-17 12:32:07
 */
@Controller
public class SchedulingController extends ControllerBase {

	@Autowired
	private SchedulingService schedulingService;
	@Resource
	public SystemLogService logService;

	/**
	 * 跳转tabs主列表页
	 * 
	 * @author huzl 2014-7-17 12:32:59
	 */
	@RequestMapping(value = "/toSchedulePage")
	public ModelAndView toTabsPage() {
		return new ModelAndView(new InternalResourceView(
				"WEB-INF/JSP/officeAssistant/scheduling.jsp"));
	}

	/**
	 * 跳转日程事件列表页
	 * 
	 * @author huzl 2014-7-17 12:33:41
	 */
	@RequestMapping(value = "/toScheduleEventPage")
	public ModelAndView toScheduleEventPage() {
		return new ModelAndView(new InternalResourceView(
				"WEB-INF/JSP/officeAssistant/scheduleEvents.jsp"));
	}

	/**
	 * 跳转日程事件新增页面
	 * 
	 * @author huzl 2014-7-17 12:34:32
	 */
	@RequestMapping(value = "/toScheduleAddPage")
	public ModelAndView toAddSchedulePage(HttpServletRequest request) {
		String starttime = request.getParameter("date");
		request.setAttribute("starttime", starttime);
		return new ModelAndView(new InternalResourceView("WEB-INF/JSP/officeAssistant/addSchedule.jsp"));
	}

	/**
	 * 查询日程列表方法
	 * 
	 * @author huzl
	 * @param request
	 * @return List<TbuserNotice> 2014-7-17 12:35:37
	 */
	@RequestMapping(value = "/ajaxGetDataById")
	@ResponseBody
	public Object getList(HttpServletRequest request) {
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		String title = request.getParameter("key");
		String opid = getLoginUser(request).getUserid();
		int total = schedulingService.getScheduleCount(2, opid, title);
		List<TbuserNotice> list = schedulingService.getDataById(2, opid, title,pageIndex, pageSize, sortField,sortOrder);
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}

	/**
	 * 日程安排新增方法
	 * 
	 * @author huzl
	 * @param request
	 * @return String 2014-7-17 12:37:20
	 */
	@RequestMapping(value = "/saveSchedule")
	@ResponseBody
	public String saveSchedule(HttpServletRequest request) {
		String result = "success";
		short status = 1;
		SystemUser user = getLoginUser(request);
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String noticeid = request.getParameter("noticeid");
		SimpleDateFormat format = new SimpleDateFormat(GkzxCommon.DATETIMEFORMATTWO);
		SystemLog log = new SystemLog();
		try {
			TbuserNotice tbuserNotice = new TbuserNotice();
			tbuserNotice.setTitle(title);
			tbuserNotice.setOptime(new Date());
			tbuserNotice.setOpid(user.getUserid());
			tbuserNotice.setUsername(user.getName());
			tbuserNotice.setContent(content);
			tbuserNotice.setStarttime(format.parse(starttime));
			tbuserNotice.setEndtime(format.parse(endtime));
			if(!StringNumberUtil.isNullOrEmpty(noticeid)){
				log.setLogtype("日程事件操作");
				log.setOpaction("修改");
				log.setOpcontent("修改日程事件:" + title);
				log.setOrgid(user.getUserid());
				log.setRemark("修改日程事件");
				tbuserNotice.setNoticeid(Integer.valueOf(noticeid));
				schedulingService.updateSchedule(tbuserNotice);
			}else{
				log.setLogtype("日程事件操作");
				log.setOpaction("新增");
				log.setOpcontent("新增日程事件");
				log.setOrgid(user.getUserid());
				log.setRemark("新增日程事件");
				tbuserNotice.setState((short) 1);
				tbuserNotice.setMessagetype(2);
				tbuserNotice.setCanceltime(new Date());
				schedulingService.addData(tbuserNotice);
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = 0;
			result = "error";
		}
		log.setStatus(status);
		try {
			//logService.add(log,user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}

	/**
	 * 根据日程编号获取信息
	 * @author huzl
	 * @param request
	 */
	@RequestMapping(value = "/ajaxGetSchedule")
	@ResponseBody
	public HashMap<String, Object> ajaxGetSchedule(HttpServletRequest request) {
		int noticeid = Integer.parseInt(request.getParameter("noticeid"));
		TbuserNotice tbuserNotice = schedulingService.getDataByNoticeId(noticeid);
		HashMap<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat format = new SimpleDateFormat(GkzxCommon.DATETIMEFORMATTWO);
		map.put("noticeid", tbuserNotice.getNoticeid());
		map.put("title", tbuserNotice.getTitle());
		map.put("starttime", format.format(tbuserNotice.getStarttime()));
		map.put("endtime", format.format(tbuserNotice.getEndtime()));
		map.put("content", tbuserNotice.getContent());
		return  map;
	}

	/**
	 * 日程安排删除方法
	 * 
	 * @author huzl
	 * @param request
	 * @return String 2014-7-17 12:47:31
	 */
	@RequestMapping(value = "/deleteSchedule")
	@ResponseBody
	public String deleteScheduleById(HttpServletRequest request) {
		String result = "success";
		short status = 1;
		SystemUser user = getLoginUser(request);
		String id = request.getParameter("noticeid");
		try {
			int noticeid = Integer.parseInt(id);
			schedulingService.deleteNoticeByid(noticeid);
			status = 1;
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
			status = 0;
		}
		SystemLog log = new SystemLog();
		log.setLogtype("日程事件操作");
		log.setOpaction("删除");
		log.setOpcontent("删除日程事件 ,noticeid=" + id);
		log.setOrgid(user.getUserid());
		log.setRemark("删除日程事件");
		log.setStatus(status);
		try {
			logService.add(log,user);
		} catch (Exception e) {
			// 吃掉异常
		}
		return result;
	}

	
	@RequestMapping(value = "/toScheduleWeekView")
	public ModelAndView toScheduleWeekView(HttpServletRequest request) throws ParseException{
		//获取本周日期
		Map<String, String> map = this.getDayOfWeek();
		request.setAttribute("dayOfWeek", map);
		//获取本周日程列表
		String opid = getLoginUser(request).getUserid();
		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(map.get("monday"));
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(map.get("sunday"));
		List<TbuserNotice> scheduleList = schedulingService.getCurrentWeekDataList(2, startDate, endDate, opid);
		request.setAttribute("sysdate", new Date());	
		//跳转页面
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"/WEB-INF/JSP/officeAssistant/scheduleWeekView.jsp");
		mav = new ModelAndView(view,"scheduleList",scheduleList);
		return mav;
	}
	/**
	 * 月视图
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/toScheduleMonthView")
	public ModelAndView toScheduleMonthView(HttpServletRequest request) throws ParseException{
		String opid = getLoginUser(request).getUserid();
		String timeSelect = request.getParameter("time");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
		String time=df.format(new Date());
		if(!(null==timeSelect || "".equals(timeSelect))){
			time = timeSelect;
		}
		String startDateStr =  time+"-01";
		String endDateStr = time+"-31";
		
		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);
		List<TbuserNotice> scheduleList = schedulingService.getCurrentMonthDataList(2, startDate, endDate, opid);
		String[] yearAndMonth = time.split("-");
		request.removeAttribute("SYValue");
		request.setAttribute("SYValue", yearAndMonth[0]);
		request.removeAttribute("SMValue");
		request.setAttribute("SMValue", yearAndMonth[1]);
		request.setAttribute("time", startDateStr);
		ModelAndView mav = null;
		View view = new InternalResourceView(
		"/WEB-INF/JSP/officeAssistant/scheduleMonthView.jsp");
		mav = new ModelAndView(view,"scheduleList",scheduleList);
		return mav;
	}
	
	@RequestMapping(value = "/ajaxCheckScheduleDetail")
	@ResponseBody
	public  Object ajaxCheckScheduleDetail(HttpServletRequest request) throws ParseException{
		String date = request.getParameter("date");
		String opid = getLoginUser(request).getUserid();
		Date currentDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		List<TbuserNotice> scheduleList = schedulingService.getCurrentDateDataList(2, currentDate,  opid);
		JSONMessage message = JSONMessage.newMessage();
		message.setData(scheduleList);
		return message;
	}
	/**
	 * 获取本周日期
	 * @return
	 */
	public Map<String,String> getDayOfWeek() {
		 Calendar calendar = Calendar.getInstance(); 
		 while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) { 
			 calendar.add(Calendar.DATE, -1); 
		 }
		 Map<String,String> map = new HashMap<String,String>();
		 for (int i = 0; i < 7; i++) { 
			 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			 String date = dateFormat.format(calendar.getTime());
			 if(i==0){
				 map.put("monday", date);
			 }else if(i==1){
				 map.put("tuesday", date);
			 }else if(i==2){
				 map.put("wednesday", date);
			 }else if(i==3){
				 map.put("thursday", date);
			 }else if(i==4){
				 map.put("friday", date);
			 }else if(i==5){
				 map.put("saturday", date);
			 }else if(i==6){
				 map.put("sunday", date);
			 }
			 calendar.add(Calendar.DATE, 1); 
		 }
		 return map;
	 }

}

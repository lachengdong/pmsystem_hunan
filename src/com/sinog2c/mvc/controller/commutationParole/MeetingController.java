package com.sinog2c.mvc.controller.commutationParole;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
/**
 * 会议记录
 * @author huzl
 *
 */
@Controller
public class MeetingController extends ControllerBase {

	/**
	 * 跳转列表页
	 * 
	 * @author hzl
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toMeetingListPage")
	public ModelAndView toListPage(HttpServletRequest request) {
		ModelAndView mav = null;
		SystemUser user = getLoginUser(request);
		String meetcontent = request.getParameter("meetcontent");
		String flowdefid = request.getParameter("flowdefid");
		request.setAttribute("userid", user.getUserid());
		request.setAttribute("meetcontent", meetcontent);
		request.setAttribute("flowdefid", flowdefid);
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/meetingListPage.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
	
	/**
	 * 跳转列表页
	 * 
	 * @author mushuhong
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toMeetingListPage_nx")
	public ModelAndView toMeetingListPage_nx(HttpServletRequest request) {
		ModelAndView mav = null;
		SystemUser user = getLoginUser(request);
		String meetcontent = request.getParameter("meetcontent");
		request.setAttribute("userid", user.getUserid());
		request.setAttribute("meetcontent", meetcontent);
		View view = new InternalResourceView(
		"WEB-INF/JSP/commutationParole/meetingListPage_nx.jsp");
		mav = new ModelAndView(view);
		return mav;
	}
}

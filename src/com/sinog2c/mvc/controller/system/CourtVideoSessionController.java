package com.sinog2c.mvc.controller.system;

import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.system.CourtVideoSessionService;
import com.sinog2c.service.api.system.SystemLogService;

@Controller
@RequestMapping("/video")
public class CourtVideoSessionController extends ControllerBase{

	@Resource
	CourtVideoSessionService courtVideoSessionService;
	@Resource
	public SystemLogService logService;
	
	/**
	 *  跳向信息列表页面
	 * @author liuyi
	 * 2015年4月2日 14:38:43
	 */
	@RequestMapping("/toVideoSessionManagePage")
	public ModelAndView toVideoSessionManagePage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("court/videoSessionManage");
		return mav;
	}
	
	/**
	 * 获取视频开庭连接信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getVideoSessionList")
	@ResponseBody
	public Object getVideoSessionList(HttpServletRequest request){
		Object obj = courtVideoSessionService.getVideoSessionList(request);
		return obj;
	} 
	
	/**
	 * 获取所属部门
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getVideoSessionDepartments")
	@ResponseBody
	public Object getVideoSessionDepartments(HttpServletRequest request){
		Object obj = courtVideoSessionService.getVideoSessionDepartments(request);
		return obj;
	}
	
	/**
	 * 更新
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateVideoSession")
	@ResponseBody
	public Object updateVideoSession(HttpServletRequest request){
		Object obj = courtVideoSessionService.updateVideoSession(request);
		return obj;
	}
	
	/**
	 * 插入
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/insertVideoSession")
	@ResponseBody
	public Object insertVideoSession(HttpServletRequest request){
		Object obj = courtVideoSessionService.insertVideoSession(request);
		return obj;
	}
	
	/**
	 * 查看
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getVideoSessionById")
	@ResponseBody
	public Object getVideoSessionById(HttpServletRequest request){
		Object obj = courtVideoSessionService.getVideoSessionById(request);
		return obj;
	}
	
	/**
	 * 单个删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteVideoSessionById")
	@ResponseBody
	public Object deleteVideoSessionById(HttpServletRequest request){
		Object obj = courtVideoSessionService.deleteVideoSessionById(request);
		return obj;
	}
	
	/**
	 * 批量删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteBatchVideoSessionByIds")
	@ResponseBody
	public Object deleteBatchVideoSessionByIds(HttpServletRequest request){
		Object obj = courtVideoSessionService.deleteBatchVideoSessionByIds(request);
		return obj;
	}
	
	//--------上边为连接信息配置方法
	
	/**
	 *  跳向直播列表页面
	 * @author liuyi
	 * 2015年4月3日 11:06:04
	 */
	@RequestMapping("/toVideoPlayManagePage")
	public ModelAndView toVideoPlayManagePage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("court/videoPlayManage");
		return mav;
	}
	
	/**
	 * 获取直播页面部门树
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getVideoPlayDepartments")
	@ResponseBody
	public Object getVideoPlayDepartments(HttpServletRequest request){
		Object obj = courtVideoSessionService.getVideoPlayDepartments(request);
		return obj;
	}
	
	
	/**
	 *  跳向回放页面
	 * @author liuyi
	 * 2015年4月6日 10:37:43
	 */
	@RequestMapping("/toVideoPlayBackPage")
	public ModelAndView toVideoPlayBackPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("court/videoPlayBackPage");
		Calendar cal = Calendar.getInstance();
		mav.addObject("caseyear", cal.get(Calendar.YEAR));
		SystemUser user = (SystemUser) WebUtils.getSessionAttribute(request, SESSION_USER_KEY);
		SystemOrganization org = user.getOrganization();
		if(org!=null && org.getShortname()!=null){
			mav.addObject("shortname", org.getShortname());//单位简称
		}
		return mav;
	}
}

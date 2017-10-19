package com.sinog2c.mvc.controller.system;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.system.ReportManageService;

/**
 * 报表管理
 * @author liuyi
 *
 */
@Controller
@RequestMapping("/report")
public class ReportManageController extends ControllerBase{
	
	@Resource
	ReportManageService reportManageService;
	
	/**
	 *  跳向报表管理页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toReportManagePage")
	public ModelAndView toReportManagePage(HttpServletRequest request) {
		returnResourceMap(request);
		ModelAndView mav = new ModelAndView("report/reportManage");
		return mav;
	}
	
	/**
	 * 获取报表列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getReportList")
	@ResponseBody
	public Object getReportList(HttpServletRequest request){
		Object obj = reportManageService.getReportList(request);
		return obj;
	} 
	
	/**
	 * 根据报表id获取单个报表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getReportById")
	@ResponseBody
	public Object getReportById(HttpServletRequest request){
		Object obj = reportManageService.getReportById(request);
		return obj;
	} 

	/**
	 * 插入记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/insertReportManage")
	@ResponseBody
	public Object insertReportManage(HttpServletRequest request){
		Object obj = reportManageService.insertReportManage(request);
		return obj;
	} 
	
	/**
	 * 更新记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateReportManage")
	@ResponseBody
	public Object updateReportManage(HttpServletRequest request){
		Object obj = reportManageService.updateReportManage(request);
		return obj;
	} 
	
	/**
	 * 删除记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteReportById")
	@ResponseBody
	public Object deleteReportById(HttpServletRequest request){
		Object obj = reportManageService.deleteReportById(request);
		return obj;
	} 
	
	/**
	 * 批量删除记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteBatchReportByIds")
	@ResponseBody
	public Object deleteBatchReportByIds(HttpServletRequest request){
		Object obj = reportManageService.deleteBatchReportByIds(request);
		return obj;
	} 
}

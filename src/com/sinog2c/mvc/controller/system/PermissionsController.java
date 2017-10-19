package com.sinog2c.mvc.controller.system;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemPermissions;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemPermissionsService;
import com.sinog2c.util.common.StringNumberUtil;

@Controller
@RequestMapping("/permissions")
public class PermissionsController extends ControllerBase{

	@Resource
	SystemPermissionsService systemPermissionsService;
	@Resource
	public SystemLogService logService;
	/**
	 * 显示权限
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/manage.action")
	public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = new ModelAndView("system/permissionsmanage");

		return mav;
	}
	
	@RequestMapping(value = "/ajax/list.json")
	@ResponseBody
	public Object listPermissionsByPage(HttpServletRequest request, HttpServletResponse response) {
		// 页码, 0 开始
		//String pageIndexStr = request.getParameter("pageIndex");
		// 每页显示条数
		//String pageSizeStr = request.getParameter("pageSize");
		// 排序列名
		//String sortField = request.getParameter("sortField");
		// asc, desc
		//String sortOrder = request.getParameter("sortOrder");
		// 只查询菜单?
		//String ismenu = request.getParameter("ismenu");
		
		// 后期考虑,只显示某些部门的子部门
		
		//
		//int pageIndex = StringNumberUtil.parseInt(pageIndexStr, 0);
		//int pageSize = StringNumberUtil.parseInt(pageSizeStr, 20);
		
		//// 总数
		int total = systemPermissionsService.countAll();
		// 取得所有数据? 顶层数据?
		List<SystemPermissions> permissions = systemPermissionsService.selectAll();
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setTotal(total); 
		message.setData(permissions);
		
		//
		return message.getData();
	}

	/**
	 * 下一个id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ajax/getnextid.json")
	@ResponseBody
	public Object getNextid(HttpServletRequest request, HttpServletResponse response) {
		
	
		// 调用业务方法
		int nextid = 0;
		
		try{
			nextid = systemPermissionsService.getNextId();
		} catch (Exception e) {
		}
		
		if(nextid < 1){
			try{
				nextid = systemPermissionsService.getNextId();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 提示成功
		}
		//
		JSONMessage message = JSONMessage.newMessage();
		if(nextid > 1){
			message.setSuccess();
			//
			//DecimalFormat format = new DecimalFormat("00000");
			message.addMeta("nextid", nextid);
		}
		// 
		
		//
		return message;
	}
	
	@RequestMapping(value = "/ajax/add.json")
	@ResponseBody
	public Object addPermissions(HttpServletRequest request, HttpServletResponse response) {
		return updatePermissions(request, response); 
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ajax/update.json")
	@ResponseBody
	public Object updatePermissions(HttpServletRequest request, HttpServletResponse response) {
		
		// 获取用户信息
		SystemUser operator = getLoginUser(request);
		//
		String spparent = request.getParameter("spparent");
		String spid = request.getParameter("spid");
		String spdiscribe = request.getParameter("spdiscribe");
		// TODO 是否新增.
		String islocalnew = request.getParameter("islocalnew");
		System.out.println("islocalnew="+islocalnew);
		//
		//String snStr = request.getParameter("sn");
		String remark = request.getParameter("remark");
		//
    	//short sn = StringNumberUtil.parseShort(snStr, 0);
		//
		boolean inputCheckOK = true;
		String inputCheckMessage = "输入的信息不正确";

		//
		Map<String, Object> paraMap = new HashMap<String, Object>();
		Enumeration<String> paraNames = request.getParameterNames();
		while (paraNames.hasMoreElements()) {
			String paraName = (String) paraNames.nextElement();
			String paraValue = request.getParameter(paraName);
			if(StringNumberUtil.notEmpty(paraValue)){
				//
				paraMap.put(paraName, paraValue);
			}
		}
    	
		// 
		if (!StringNumberUtil.notEmpty(spid)) {
			inputCheckOK = false;
			inputCheckMessage = "spid不正确";
		}
		if (!StringNumberUtil.notEmpty(spparent)) {
			spparent = "-1";
		}
		if (!StringNumberUtil.notEmpty(spdiscribe)) {
			inputCheckOK = false;
			inputCheckMessage = "spdiscribe不正确";
		}
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		boolean isUpdatePermissions = false;
		if(inputCheckOK){
			//
			SystemPermissions permiExist = systemPermissionsService.getByPermissionsId(spid);
	    	int rows = 0; 
			if(null == permiExist){
				isUpdatePermissions = false;// 新包装对象
				SystemPermissions permiNew = new SystemPermissions();
		    	//
		    	if(null == spparent){
		    		spparent = "0";
		    	}
		    	permiNew.setRemark(remark);
		    	// 添加
				//rows = systemPermissionsService.add(permiNew, operator); // 新增
				rows = systemPermissionsService.insertByMap(paraMap, operator); //  TODO 可能有问题
			} else {
				isUpdatePermissions = true;
				// 旧的权限
		    	permiExist.setRemark(remark);
		    	// 提交请求
				//rows = systemPermissionsService.update(permiExist, operator);
				rows = systemPermissionsService.updateByMap(paraMap, operator); // TODO 可能有问题
			}
			//// 更新
			if( 1== rows){
				if(isUpdatePermissions){
					message.setInfo("修改成功!");
				} else {
					message.setInfo("添加成功!");
				}
				message.setSuccess();
			} else {
				if(isUpdatePermissions){
					message.setInfo("修改失败!");
				} else {
					message.setInfo("添加失败!");
				}
			}
		} else {
			message.setInfo(inputCheckMessage);
		}

		// 日志
		SystemLog log = new SystemLog();
		log.setLogtype("权限操作");
		log.setOpaction("新增/修改");
		log.setOpcontent("新增/修改权限,spid="+spid+",spdiscribe="+spdiscribe);
		log.setOrgid("sys");
		log.setRemark(message.getInfo());
		log.setStatus((short)message.getStatus());
		try{
			logService.add(log, operator);
		} catch (Exception e) {
			// 吃掉异常
		}
		//
		return message;
	}	
	
	@RequestMapping(value = "/ajax/delete.json")
	@ResponseBody
	public Object deletePermissions(HttpServletRequest request, HttpServletResponse response) {

		SystemUser operator = getLoginUser(request);
		// 获取参数
		String spid = request.getParameter("spid");
		JSONMessage message = JSONMessage.newMessage();

		String info = "spid错误";
		int rows = 0;
		if(StringNumberUtil.notEmpty(spid)){
			// 调用业务方法
			rows = systemPermissionsService.delete(spid, operator);
		} else {
			
		}
		if(1 == rows){
			// 提示成功
			message.setSuccess();
			info = "删除成功";
		} else {
			info = "删除失败";
		}
		message.setInfo(info);

		// 日志
		SystemLog log = new SystemLog();
		log.setLogtype("权限操作");
		log.setOpaction("删除");
		log.setOpcontent("删除权限,spid="+spid);
		log.setOrgid("sys");
		log.setRemark(message.getInfo());
		log.setStatus((short)message.getStatus());
		try{
			logService.add(log, operator);
		} catch (Exception e) {
			// 吃掉异常
		}
		//
		return message;
	}
}

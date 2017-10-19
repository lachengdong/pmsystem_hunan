package com.sinog2c.mvc.controller.system;

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
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.util.common.StringNumberUtil;

@Controller
@RequestMapping("/org")
public class SystemOrganizationController extends ControllerBase{

	@Resource
	SystemOrganizationService systemOrganizationService;
	@Resource
	public SystemLogService logService;
	/**
	 * 显示机构
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/manage.action")
	public ModelAndView managePage(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = new ModelAndView("system/orgmanage");

		return mav;
	}
	
	@RequestMapping(value = "/ajax/list")
	@ResponseBody
	public Object listOrganizationByPage(HttpServletRequest request, HttpServletResponse response) {
		SystemUser user = getLoginUser(request);
		if(null == user){
			return null;
		}
		String unitlevel = getParameterString(request, "unitlevel", "");

		JSONMessage message = JSONMessage.newMessage();
		
		String userid = user.getUserid();
		if("root".equals(userid)){
			// root 取得所有数据
			List<SystemOrganization> ALLorgs = systemOrganizationService.selectAll();
			message.setData(ALLorgs);
		} else {
			//
			SystemOrganization org = user.getOrganization();
			if(null == org){
				return null;
			}
			String porgid = org.getOrgid();
			// 取得所有数据
			List<SystemOrganization> orgs = systemOrganizationService.listByOrganizationPid(porgid,unitlevel);
			message.setData(orgs);
		}
		
		return message.getData();
	}
	

	@RequestMapping(value = "/ajax/listbylevel.json")
	@ResponseBody
	public Object listOrgByLevel(HttpServletRequest request, HttpServletResponse response) {
		// 机构级别
		String unitlevel = getParameterString(request, "unitlevel", "");
		String porgid = getParameterString(request, "porgid", "");
		//
		SystemUser user = getLoginUser(request);
		if(null == user){
			return null;
		}
		
		// 取得所有数据
//		List<SystemOrganization> orgs = systemOrganizationService.listByLevel(unitlevel);
		List<Map<String,Object>> orgs = systemOrganizationService.listMapByLevel(unitlevel,porgid);
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		//message.setTotal(total); 
		message.setData(orgs);
		
		//
		return message.getData();
	}
	
	@RequestMapping(value = "/ajax/userlist")
	@ResponseBody
	public Object listOrganizationWithUser(HttpServletRequest request, HttpServletResponse response) {
		SystemUser user = getLoginUser(request);
		if(null == user){
			return null;
		}
		SystemOrganization org = user.getOrganization();
		if(null == org){
			return null;
		}
		String orgid = org.getOrgid();
		HashMap map = new HashMap();
		map.put("orgId", orgid);
		// 取得所有数据
		List<HashMap> orgs = systemOrganizationService.getOrgWithUserByOrgId(map);
		JSONMessage message = JSONMessage.newMessage();
 
		message.setData(orgs);
		
		return message.getData();
	}
	
	
	@RequestMapping(value = "/ajax/getdepartunderself")
	@ResponseBody
	public Object getOrganizationByuserid(HttpServletRequest request, HttpServletResponse response) {
		SystemUser user = getLoginUser(request);
		if(null == user){
			return null;
		}
		SystemOrganization org = user.getOrganization();
		if(null == org){
			return null;
		}
		String orgid = org.getOrgid();
		HashMap map = new HashMap();
		map.put("orgid", orgid);
		// 取得所有数据
		List<Map> orgs = systemOrganizationService.getOrganizationByuserid(map);
		JSONMessage message = JSONMessage.newMessage();
 
		message.setData(orgs);
		
		return message.getData();
	}
	
	@RequestMapping(value = "/ajax/add")
	@ResponseBody
	public Object addOrganization(HttpServletRequest request, HttpServletResponse response) {
		return updateOrganization(request, response); 
	}
	
	@RequestMapping(value = "/ajax/update")
	@ResponseBody
	public Object updateOrganization(HttpServletRequest request, HttpServletResponse response) {
		
		// 获取用户信息
		SystemUser operator = getLoginUser(request);
		//
		String userid = operator.getUserid();
		//
		String porgid = request.getParameter("porgid");
		String orgid = request.getParameter("orgid");
		String name = request.getParameter("name");
		// 单位级别
		String unitlevelN = request.getParameter("unitlevel");
		//
		String snStr = request.getParameter("sn");
		String remark = request.getParameter("remark");
		int islocalnew = getParameterInt(request, "islocalnew", 0);
		//
    	int sn = StringNumberUtil.parseShort(snStr, 0);
		//
		boolean inputCheckOK = true;
		String inputCheckMessage = "输入的信息不正确";

		Map<String, Object> paraMap = parseParamMap(request);
		//
		/*
		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		Enumeration<String> paraNames = request.getParameterNames();
		while (paraNames.hasMoreElements()) {
			String paraName = (String) paraNames.nextElement();
			String paraValue = request.getParameter(paraName);
			if(StringNumberUtil.notEmpty(paraValue)){
				//
				paraMap.put(paraName, paraValue);
			}
		}
    	*/
		// 
		if (!StringNumberUtil.notEmpty(orgid)) {
			inputCheckOK = false;
			inputCheckMessage = "orgid不正确";
		}
		if (!StringNumberUtil.notEmpty(porgid)) {
			inputCheckOK = false;
			inputCheckMessage = "porgid不正确";
		}
		if (!StringNumberUtil.notEmpty(name)) {
			inputCheckOK = false;
			inputCheckMessage = "name不正确";
		}
		// 是否是 root
		boolean hasRoot = false;
		if ("root".equals(userid.trim())) {
			// 非root,不允许
			hasRoot = true;
		}
		JSONMessage message = JSONMessage.newMessage();
		//
		boolean updateOrganization = false;
		if(inputCheckOK){
			//
			SystemOrganization orgExist = systemOrganizationService.getByOrganizationId(orgid);
			//
	    	int rows = 0; 
			if(null == orgExist){
				updateOrganization = false;// 新包装对象
				SystemOrganization resNew = new SystemOrganization();
		    	//
		    	if(null == porgid){
		    		porgid = "0";
		    	}
//		    	resNew.setOrgid(orgid);
//		    	resNew.setPorgid(porgid);
//		    	resNew.setName(name);
//		    	resNew.setOpid(operator.getUserid());  // 操作员
//		    	resNew.setSn(sn);
//		    	resNew.setRemark(remark);
		    	// 添加
				//rows = systemOrganizationService.add(resNew, operator); // 新增
		    	//
				if(SystemOrganization.UNITLEVEL_PRISON.equals(unitlevelN) ){
					// 不允许非root修改添加监狱
					if(!hasRoot){ // 简单拦截
						inputCheckOK = false;
						inputCheckMessage = "新增失败,未授权添加监狱";
					}
				}
				if(inputCheckOK){
					rows = systemOrganizationService.insertByMap(paraMap, operator); 
				}
			}  else if(islocalnew > 0){
				updateOrganization = true;
				rows = 0;
			} else {
				// 已存在的单位级别
				String unitlevelE = orgExist.getUnitlevel();
				// 监狱级别,不相等
				// 修改为监狱。。。
				if(SystemOrganization.UNITLEVEL_PRISON.equals(unitlevelN)
						&& !unitlevelN.equals(unitlevelE)  
						){
					// 不允许非root修改单位级别
					if(!hasRoot){ // 简单拦截
						inputCheckOK = false;
						inputCheckMessage = "修改失败,未授权修改监狱";
					}
				}
				// 将监狱修改为其他级别
				if(SystemOrganization.UNITLEVEL_PRISON.equals(unitlevelE)
						&& !unitlevelE.equals(unitlevelN)  
						){
					// 不允许非root修改单位级别
					if(!hasRoot){
						inputCheckOK = false;
						inputCheckMessage = "修改失败,未授权修改监狱";
					}
				}
				updateOrganization = true;
				// 旧的机构
//				orgExist.setName(name);
//		    	orgExist.setPorgid(porgid);
//		    	orgExist.setName(name);
//		    	orgExist.setSn(sn);
//		    	orgExist.setRemark(remark);
		    	// 提交请求
				//rows = systemOrganizationService.update(orgExist, operator);
				if(inputCheckOK){
					rows = systemOrganizationService.updateByMap(paraMap, operator); // TODO 可能有问题
				}
			}
			//// 更新
			if( 1== rows){
				if(updateOrganization){
					message.setInfo("修改成功!");
				} else {
					message.setInfo("添加成功!");
				}
				message.setSuccess();
			} else {
				if(islocalnew > 0 && updateOrganization){
					message.setInfo("机构编号已存在!");
				} else if (updateOrganization) {
					message.setInfo("修改失败!");
				} else {
					message.setInfo("添加失败!");
				}
			}
			//
		}
		
		// 如果输入检查有错误
		if(!inputCheckOK){
			message.setInfo(inputCheckMessage);
		}

		// 日志
		SystemLog log = new SystemLog();
		log.setLogtype("组织操作");
		log.setOpaction("新增/修改");
		log.setOpcontent("新增/修改组织,orgid="+orgid+",name="+name);
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
	@RequestMapping(value = "/ajax/delete")
	@ResponseBody
	public Object deleteOrganization(HttpServletRequest request, HttpServletResponse response) {

		SystemUser operator = getLoginUser(request);
		// 获取参数
		String orgid = request.getParameter("orgid");
		JSONMessage message = JSONMessage.newMessage();

		String info = "orgid错误";
		int rows = 0;
		if(StringNumberUtil.notEmpty(orgid)){
			// 调用业务方法
			rows = systemOrganizationService.delete(orgid, operator);
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
		log.setLogtype("机构操作");
		log.setOpaction("删除");
		log.setOpcontent("删除机构,orgid="+orgid);
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
	//获取当前登录人所在部门下的所有监区
	@RequestMapping(value = "/ajaxGetJianQuByPorgid.json")
	@ResponseBody
	public Object ajaxGetJianQuByPorgid(HttpServletRequest request){
		String key = request.getParameter("key");
		String orgid = getLoginUser(request).getOrgid();
		List<SystemOrganization> resources = systemOrganizationService.selectJianQuByPorgid(orgid,key);
		JSONMessage message = JSONMessage.newMessage();
		message.setData(resources);
		return message.getData();
	}
	
	
	//获取当前登录人所在部门下的所有监区
	@RequestMapping(value = "/getOrginfoByOrgnameAndDepartid.json")
	@ResponseBody
	public Object getOrginfoByOrgnameAndDepartid(HttpServletRequest request){
		Map<String,Object> map = this.parseParamMap(request);
		String departid = getLoginUser(request).getDepartid();
		map.put("departid", departid);
		
		return systemOrganizationService.getOrginfoByOrgnameAndDepartid(map);
	}
	
	
	@RequestMapping(value = "/ajax/getOrganizationByPorgid")
	@ResponseBody
	public Object getOrganizationByPorgid(HttpServletRequest request, HttpServletResponse response) {
		String porgid = request.getParameter("porgid")==null?"":request.getParameter("porgid").toString();
		String unitlevel = request.getParameter("unitlevel")==null?"":request.getParameter("unitlevel").toString();
		SystemUser user = getLoginUser(request);
		if(null == user){
			return null;
		}
		SystemOrganization org = user.getOrganization();
		if(null == org){
			return null;
		}
		if("".equals(porgid)){
			porgid = org.getOrgid();
		}
		HashMap map = new HashMap();
		map.put("porgid", porgid);
		map.put("unitlevel", unitlevel);
		// 取得所有数据
		List<Map> orgs = systemOrganizationService.getOrganizationByPorgid(map);
		JSONMessage message = JSONMessage.newMessage();
		message.setData(orgs);
		return message.getData();
	}
	
}

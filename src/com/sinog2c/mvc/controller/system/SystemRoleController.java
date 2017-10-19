package com.sinog2c.mvc.controller.system;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.system.RolePermissionsWrapper;
import com.sinog2c.model.system.RoleResourceWrapper;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemPermissions;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemRole;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemPermissionsService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.service.api.system.SystemRoleService;
import com.sinog2c.util.common.StringNumberUtil;

@Controller
@RequestMapping("/role")
public class SystemRoleController extends ControllerBase {
	@Resource
	SystemOrganizationService systemOrganizationService;
	@Resource
	SystemRoleService systemRoleService;
	@Resource
	SystemPermissionsService permissionsService;
	@Resource
	SystemResourceService resourceService;
	@Resource
	public SystemLogService logService;

	/**
	 * 显示角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/manage.action")
	public ModelAndView manage(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = new ModelAndView("system/role/rolemanage");

		return mav;
	}
	
	/**
	 * 编辑角色/权限页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/editPermissionByRole.page")
	public ModelAndView editPermissionByRole(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = new ModelAndView("system/role/editPermissionByRole");

		return mav;
	}

	/**
	 * 编辑 角色/资源页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/editResourceByRole.page")
	public ModelAndView editResourceByRole(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = new ModelAndView("system/role/editResourceByRole");

		return mav;
	}
	
	@RequestMapping(value = "/ajax/list.json")
	@ResponseBody
	public Object listRoleByPage(HttpServletRequest request, HttpServletResponse response) {
		// 页码, 0 开始
		String pageIndexStr = request.getParameter("pageIndex");
		// 每页显示条数
		String pageSizeStr = request.getParameter("pageSize");
		SystemUser user  = getLoginUser(request);
		
		int pageIndex = StringNumberUtil.parseInt(pageIndexStr, 0);
		int pageSize = StringNumberUtil.parseInt(pageSizeStr, 20);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("departid", user.getDepartid());
		map.put("unitlevel", user.getOrganization().getUnitlevel());
		// // 总数
		int total = systemRoleService.countAll(map);
		// 取得所有数据? 顶层数据?
		//List<SystemRole> resources = systemRoleService.selectAll();
		// 角色,目前,获取的是所有角色。
		List<SystemRole> resources = systemRoleService.getAllByPage(pageIndex, pageSize, map);
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setTotal(total);
		message.setData(resources);

		//
		return message;
	}


	@RequestMapping(value = "/ajax/add.json")
	@ResponseBody
	public Object addRole(HttpServletRequest request, HttpServletResponse response) {
		return updateRole(request, response);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ajax/update.json")
	@ResponseBody
	public Object updateRole(HttpServletRequest request, HttpServletResponse response) {

		// 获取用户信息
		SystemUser operator = getLoginUser(request);
		//
		
		String roleid = request.getParameter("roleid");
		String name = request.getParameter("name");
		String text1 = request.getParameter("text1");
		//
		String snStr = request.getParameter("sn");
		String remark = request.getParameter("remark");
		int islocalnew = getParameterInt(request, "islocalnew", 0);
		//
		short sn = StringNumberUtil.parseShort(snStr, 0);
		//
		boolean inputCheckOK = true;
		String inputCheckMessage = "输入的信息不正确";

		//
		Map<String, Object> paraMap = new HashMap<String, Object>();
		Enumeration<String> paraNames = request.getParameterNames();
		while (paraNames.hasMoreElements()) {
			String paraName = (String) paraNames.nextElement();
			String paraValue = request.getParameter(paraName);
			if (StringNumberUtil.notEmpty(paraValue)) {
				//
				paraMap.put(paraName, paraValue);
			}
		}
		// 获取用户的部门ID
		// String departid =  operator.getOrganization().getOrgid();
		String departid = operator.getDepartid();
		String unitlevel = operator.getOrganization().getUnitlevel();//部门级别
		if(GkzxCommon.TWO.equals(text1)){//省局通用
			if(GkzxCommon.TWO.equals(unitlevel)){//获取省局单位ID
				departid = operator.getDepartid();
			}else{
				SystemOrganization  org = systemOrganizationService.getByOrganizationId(operator.getDepartid());
				if(org!=null){
					departid = org.getPorgid();
				}
			}
		}else if(GkzxCommon.THREE.equals(text1)){//全国通用
			departid = GkzxCommon.ZERO;
		}
		
		if (!StringNumberUtil.notEmpty(roleid)) {
			inputCheckOK = false;
			inputCheckMessage = "roleid不正确";
		}
		if (!StringNumberUtil.notEmpty(departid)) {
			inputCheckOK = false;
			inputCheckMessage = "departid不正确";
		}
		if (!StringNumberUtil.notEmpty(name)) {
			inputCheckOK = false;
			inputCheckMessage = "name不正确";
		}
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		boolean updateRole = false;
		if (inputCheckOK) {
			//
			SystemRole resExist = systemRoleService.getByRoleId(roleid);
			int rows = 0;
			if (null == resExist) {
				updateRole = false;// 新包装对象
				SystemRole resNew = new SystemRole();
				resNew.setText1(text1);
				resNew.setDepartid(departid);
				resNew.setName(name);
				resNew.setOpid(operator.getUserid()); // 操作员
				resNew.setSn(sn);
				resNew.setRemark(remark);
				// 添加
				// rows = systemRoleService.add(resNew, operator); // 新增

				paraMap.put("departid", departid);
				rows = systemRoleService.insertByMap(paraMap, operator); //
			} else if(islocalnew > 0){
				updateRole = true;
				rows = 0;
			} else {
				updateRole = true;
				// 旧的角色
				resExist.setText1(text1);
				resExist.setName(name);
				resExist.setDepartid(departid);
				resExist.setName(name);
				resExist.setSn(sn);
				resExist.setRemark(remark);
				// 提交请求
				// rows = systemRoleService.update(resExist, operator);
				
				paraMap.put("departid", departid);
				rows = systemRoleService.updateByMap(paraMap, operator); // 可能有问题
			}
			// // 更新
			if (1 == rows) {
				if (updateRole) {
					message.setInfo("修改成功!");
				} else {
					message.setInfo("添加成功!");
				}
				message.setSuccess();
			} else {
				if(islocalnew > 0 && updateRole){
					message.setInfo("角色ID已存在!");
				}else if (updateRole) {
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
		log.setLogtype("角色操作");
		log.setOpaction("新增/修改");
		log.setOpcontent("新增/修改角色,roleid=" + roleid + ",name=" + name);
		log.setOrgid("sys");
		log.setRemark(message.getInfo());
		log.setStatus((short) message.getStatus());
		try {
			logService.add(log, operator);
		} catch (Exception e) {
			// 吃掉异常
		}
		//
		return message;
	}

	@RequestMapping(value = "/ajax/delete.json")
	@ResponseBody
	public Object deleteRole(HttpServletRequest request, HttpServletResponse response) {

		SystemUser operator = getLoginUser(request);
		// 获取参数
		String roleid = request.getParameter("roleid");
		JSONMessage message = JSONMessage.newMessage();

		String info = "roleid错误";
		int rows = 0;
		if (StringNumberUtil.notEmpty(roleid)) {
			// 调用业务方法
			rows = systemRoleService.delete(roleid, operator);
		} else {

		}
		if (1 == rows) {
			// 提示成功
			message.setSuccess();
			info = "删除成功";
		} else {
			info = "删除失败";
		}
		message.setInfo(info);

		// 日志
		SystemLog log = new SystemLog();
		log.setLogtype("角色操作");
		log.setOpaction("删除");
		log.setOpcontent("删除角色,roleid=" + roleid);
		log.setOrgid("sys");
		log.setRemark(message.getInfo());
		log.setStatus((short) message.getStatus());
		try {
			logService.add(log, operator);
		} catch (Exception e) {
			// 吃掉异常
		}
		//
		return message;
	}

	/**
	 * 根据角色,获取对应的权限树.列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ajax/getPermissionTreeByRole.json")
	@ResponseBody
	public Object getPermissionTreeByRole(HttpServletRequest request, HttpServletResponse response) {

		String roleid = request.getParameter("qroleids");
		//// 总数
		int total = permissionsService.countAll();
		// 取得所有数据? 顶层数据?
		// 后期考虑,只显示某些部门的子部门
		List<SystemPermissions> permissions = permissionsService.listByRoleid(roleid);
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setTotal(total); 
		message.setData(permissions);
		
		//
		return message.getData();
	}

	/**
	 * 为角色保存权限
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ajax/savePermissionForRole.json")
	@ResponseBody
	public Object savePermissionForRole(HttpServletRequest request, HttpServletResponse response) {
		// srid
		String roleid = request.getParameter("srid");
		// spids
		String spids = request.getParameter("spids");
		// 去除空格
		spids = spids.replaceAll(" ", "");
		//
		String[] perms = spids.split(",");
		List<RolePermissionsWrapper> newpermissions = new ArrayList<RolePermissionsWrapper>();
		for (int i = 0; i < perms.length; i++) {
			String perm = perms[i];
			if(StringUtils.isEmpty(perm)){
				continue;
			}
			//
			RolePermissionsWrapper nwrapper = new RolePermissionsWrapper();
			//
			nwrapper.setSrid(roleid);
			nwrapper.setSpid(perm);
			//
			newpermissions.add(nwrapper);
		}
		//
		boolean success = permissionsService.updateByRole(roleid, newpermissions);
		
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setData(null);
		if(success){
			message.setInfo("更新权限成功!");
			message.setSuccess();
		} else {
			message.setInfo("更新权限失败!");
		}

		//
		return message;
	}

	/**
	 * 根据角色,获取对应的资源树.列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ajax/getResourceTreeByRole.json")
	@ResponseBody
	public Object getResourceTreeByRole(HttpServletRequest request, HttpServletResponse response) {

		String roleid = request.getParameter("qroleids");
		//// 总数
		//int total = resourceService.countAll();
		// 取得所有数据? 顶层数据?
		// 后期考虑,只显示某些部门的子部门
		List<SystemResource> resources = resourceService.listByRoleid(roleid);
		//
		truncOtherData(resources);
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		//message.setTotal(total); 
		message.setData(resources);
		
		//
		return message.getData();
	}
	
	// 会污染数据
	private void truncOtherData(List<SystemResource> resources){
		//
		if(null == resources){
			return ;
		}
		//
		Iterator<SystemResource> iteratorR = resources.iterator();
		while (iteratorR.hasNext()) {
			SystemResource res = (SystemResource) iteratorR.next();
			//
			//
			if(null != res){
				SystemResource.truncOtherData(res);
			}
		}
	}

	/**
	 * 为角色保存资源
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ajax/saveResourceForRole.json")
	@ResponseBody
	public Object saveResourceForRole(HttpServletRequest request, HttpServletResponse response) {
		//
		SystemUser operator = getLoginUser(request);
		
		String opid = operator.getUserid();
		String departid = "sys";
		// srid
		String roleidStr = request.getParameter("roleid");
		Integer roleid = StringNumberUtil.parseInt(roleidStr, 0);
		// resids
		String resids = request.getParameter("resids");
		// 去除空格
		resids = resids.replaceAll(" ", "");
		//
		String[] ress = resids.split(",");
		List<RoleResourceWrapper> newresources = new ArrayList<RoleResourceWrapper>();
		for (int i = 0; i < ress.length; i++) {
			String perm = ress[i];
			if(StringUtils.isEmpty(perm)){
				continue;
			}
			//
			RoleResourceWrapper nwrapper = new RoleResourceWrapper();
			//
			nwrapper.setRoleid(roleid);
			nwrapper.setResid(perm);
			nwrapper.setOpid(opid);
			nwrapper.setDepartid(departid);
			//
			newresources.add(nwrapper);
		}
		//
		boolean success = resourceService.updateByRole(""+roleid, newresources);
		
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setData(null);
		if(success){
			message.setInfo("更新资源成功!");
			message.setSuccess();
		} else {
			message.setInfo("更新资源失败!");
		}

		//
		return message;
	}
	
	
	@RequestMapping(value = "/ajax/getAllRoleByUser.json")
	@ResponseBody
	public List<SystemRole> getAllRoleByUser(HttpServletRequest request,
			HttpServletResponse response)
	{
		//SystemUser user = UserProfile.getInstance().getLoginUser(request);
		
		
		///TODO:需修改
		List<SystemRole> list=systemRoleService.selectAll();
		
		
		return list;
	}
}

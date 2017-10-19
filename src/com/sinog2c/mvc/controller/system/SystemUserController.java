package com.sinog2c.mvc.controller.system;

import static com.sinog2c.util.common.StringNumberUtil.notEmpty;
import static com.sinog2c.util.common.StringNumberUtil.parseInt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemRole;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.UserOrgWrapper;
import com.sinog2c.model.system.UserRoleWrapper;
import com.sinog2c.model.user.UserCertificate;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemRoleService;
import com.sinog2c.service.api.system.SystemUserService;
import com.sinog2c.util.common.PasswordUtil;
import com.sinog2c.util.common.RequestUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 用户相关,但不涉及用户登录与注销
 */
@Controller
@RequestMapping("/user")
public class SystemUserController extends ControllerBase{

	@Resource
	public SystemUserService systemUserService;
	@Resource
	public SystemRoleService roleService;
	@Resource
	public SystemOrganizationService orgService;
	@Resource
	public SystemLogService logService;
	/*
	@RequestMapping(value = "/menu.action")
	public ModelAndView menu(HttpServletRequest request,
			HttpServletResponse response) {
		//
		View view = new InternalResourceView("/login.jsp");

		ModelAndView mav = new ModelAndView(view);
		//
		return mav;
	}
	
	*/
	/**
	 * 显示用户信息页面
	 */
	@RequestMapping("/manage.action")
	public ModelAndView manageUser(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("user/usermanage");

		return mav;
	}
	/**
	 * 修改个人信息,页面
	 */
	@RequestMapping("/updateselfinfo.action")
	public ModelAndView updateSelfInfo(HttpServletRequest request, HttpServletResponse response) {
		
		// 获取用户信息
		SystemUser systemUser = getLoginUser(request);
		if(null == systemUser){
			// 出错逻辑,交给拦截器
		} else {
			//
			String userId = systemUser.getUserid();
			SystemUser user = systemUserService.getByUserId(userId);
			request.setAttribute("user", user);
		}
		// 
		ModelAndView mav = new ModelAndView("user/updateselfinfo");

		return mav;
	}
	

	/**
	 * 绑定证书
	 */
	@RequestMapping("/bindcert.page")
	public ModelAndView bindcert(HttpServletRequest request, HttpServletResponse response) {
		
		// 获取用户信息
		SystemUser systemUser = getLoginUser(request);
		if(null == systemUser){
			// 出错逻辑,交给拦截器
		} else {
			//
			String userId = systemUser.getUserid();
			SystemUser user = systemUserService.getByUserId(userId);
			UserCertificate cert = systemUserService.getCertByUserId(userId);
			request.setAttribute("user", user);
			request.setAttribute("cert", cert);
		}
		// 
		ModelAndView mav = new ModelAndView("user/bindcert");

		return mav;
	}
	
	/**
	 * 编辑用户-角色页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/editRoleByUser.page")
	public ModelAndView editRoleByUser(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = new ModelAndView("user/editRoleByUser");

		return mav;
	}
	/**
	 * 编辑用户-部门页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/editOrgByUser.page")
	public ModelAndView editOrgByUser(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = new ModelAndView("user/editOrgByUser");
		SystemUser operator = getLoginUser(request);

		String userid = request.getParameter("userid");
		//
		if(StringNumberUtil.isEmpty(userid)){
			if(null != operator){
				userid = operator.getUserid();
			}
		}
		
		//
		request.setAttribute("userid", userid);

		return mav;
	}
	
	/**
	 * 获取用户-部门页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getOrgByUser.page")
	public ModelAndView getOrgByUser(HttpServletRequest request, HttpServletResponse response) {
		// 权宜
		ModelAndView mav = new ModelAndView("user/getOrgByUser");
		SystemUser operator = getLoginUser(request);

		String userid = request.getParameter("userid");
		//
		if(StringNumberUtil.isEmpty(userid)){
			if(null != operator){
				userid = operator.getUserid();
			}
		}
		
		//
		request.setAttribute("userid", userid);

		return mav;
	}
	@RequestMapping(value = "/ajax/updateselfinfo.json")
	@ResponseBody
	public Object ajaxUpdateSelfInfo(HttpServletRequest request, HttpServletResponse response) {
		// name
		String name = request.getParameter("name");
		// userid
		//String userid = request.getParameter("userid");
		String gender = request.getParameter("gender");
		String mobile = request.getParameter("mobile");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String newpassword = request.getParameter("newpassword");
		String repassword = request.getParameter("repassword");

		//
		boolean inputCheckOK = true;
		String inputCheckMessage = "输入的信息不正确";
		boolean ifChangePassword = false;
		// 获取用户信息
		SystemUser systemUser = getLoginUser(request);
		
		// 新包装对象
		SystemUser newUser = new SystemUser();
		newUser.setUserid(systemUser.getUserid());
		newUser.setName(name);
		newUser.setGender(StringNumberUtil.parseShort(gender, 1));
		newUser.setMobile(mobile);
		newUser.setEmail(email);
		
		
		//
		if(notEmpty(password)){
			//
			String userPassword = systemUser.getPassword(); 
			if(PasswordUtil.passwordCheck(password, userPassword)){
				// 检查成功
				// 
				if(notEmpty(newpassword) && notEmpty(repassword) && newpassword.equals(repassword)){
					// 成功
					// 需要设置用户的新密码?
					ifChangePassword = true;
					//
					String npassword = PasswordUtil.password(newpassword);
					//
					newUser.setPassword(npassword);
				} else {
					//失败
					inputCheckOK = false;
					inputCheckMessage = "新密码输入不正确";
				}
			} else {
				// 检查失败
				inputCheckOK = false;
				inputCheckMessage = "原密码错误";
			}
		}
		//
		JSONMessage message = JSONMessage.newMessage();
		
		//
		if(inputCheckOK){
			//// 更新
			int rows = systemUserService.updateSelfInfo(newUser, systemUser);
			if( 1== rows){
				message.setSuccess();
				message.setInfo("修改成功!");
				if(ifChangePassword){
					// 重新设置用户密码
					systemUser.setPassword(newUser.getPassword());
				}
			}
		} else {
			message.setInfo(inputCheckMessage);
		}
		// 日志
		SystemLog log = new SystemLog();
		log.setLogtype("修改信息");
		log.setOpaction("修改");
		log.setOpcontent("修改个人资料/密码");
		log.setOrgid("sys");
		log.setRemark(inputCheckMessage);
		if(inputCheckOK){
			log.setRemark("修改成功!");
			log.setStatus((short)1);
		}
		try{
			logService.add(log, systemUser);
		} catch (Exception e) {
			// 吃掉异常
		}
		
		//
		return message;
	}
	
	//
	@RequestMapping(value = "/ajax/bindkey.json")
	@ResponseBody
	public Object ajaxBindSelfKey(HttpServletRequest request, HttpServletResponse response) {
		// 
		String explian = request.getParameter("subject");
		String certsn = request.getParameter("certno");
		String dn = request.getParameter("certdn");
		String certdata = request.getParameter("certdata");
		
		//
		boolean inputCheckOK = true;
		String inputCheckMessage = "绑定出错";
		// 获取用户信息
		SystemUser operator = getLoginUser(request);
		
		// 新包装对象
		UserCertificate cert = new UserCertificate();
		cert.setCertsn(certsn);
		cert.setDn(dn);
		cert.setCertdata(certdata);
		cert.setExplian(explian);
		
		//
		JSONMessage message = JSONMessage.newMessage();
		
		//
		if(inputCheckOK){
			//// 更新
			int rows = systemUserService.bindUserCert(cert, operator);
			if(1 == rows){
				message.setSuccess();
				message.setInfo("绑定成功!");
			} else {
				message.setInfo("绑定失败!");
			}
		} else {
			message.setInfo(inputCheckMessage);
		}
		

		// 日志
		SystemLog log = new SystemLog();
		log.setLogtype("修改信息");
		log.setOpaction("绑定证书");

		try{
			logService.add(log, operator, message);
		} catch (Exception e) {
			// 吃掉异常
		}
		//
		return message;
	}

	// 更新用户
	@RequestMapping(value = {"/ajax/updateuserinfo.json"})
	@ResponseBody
	public Object ajaxUpdateUserInfo(HttpServletRequest request, HttpServletResponse response) {
		// 获取用户信息
		SystemUser operator = getLoginUser(request);
		// name
		String name = request.getParameter("name");
		// userid
		String userid = request.getParameter("userid");
		String orgid = request.getParameter("orgid");
		String gender = request.getParameter("gender");
		String mobile = request.getParameter("mobile");
		String email = request.getParameter("email");
		String duty = request.getParameter("duty");
		String remark = request.getParameter("remark");
		String isadminStr = request.getParameter("isadmin");
		String fjqStr = request.getParameter("fjq");
		boolean isadmin = Boolean.parseBoolean(isadminStr);
		boolean fjq = Boolean.parseBoolean(fjqStr);
		String newpassword = request.getParameter("newpassword");
		int islocalnew = getParameterInt(request, "islocalnew", 0);
		// 有 BUG, 修改信息需要改密码不?

		//
		boolean inputCheckOK = true;
		String inputCheckMessage = "输入的信息不正确";
		
		// 新包装对象
		SystemUser newUser = new SystemUser();
		newUser.setUserid(userid);
		newUser.setName(name);
		newUser.setGender(StringNumberUtil.parseShort(gender, 1));
		newUser.setMobile(mobile);
		newUser.setRemark(remark);
		newUser.setEmail(email);
		newUser.setDuty(duty);
		// 
		String password = PasswordUtil.password(newpassword);
		newUser.setPassword(password);
		newUser.setInt1(isadmin ? 1:0);
		newUser.setInt2(fjq ? 1:0);
		//
		//
		if (!StringNumberUtil.notEmpty(userid)) {
			inputCheckOK = false;
			inputCheckMessage = "用户ID不正确";
		}
		if(StringNumberUtil.notEmpty(orgid)){
			SystemOrganization organization = new SystemOrganization();
			organization.setOrgid(orgid);
			newUser.setOrganization(organization);
		} else {
			newUser.setOrganization(operator.getOrganization());
		}

		//
		JSONMessage message = JSONMessage.newMessage();
		//
		boolean updateUser = false;
		if(inputCheckOK){
			SystemUser exitUser = systemUserService.getByUserId(userid);
			int rows = 0;
			if(null == exitUser){
				updateUser = false;
				rows = systemUserService.addUser(newUser, operator);
			} else if(islocalnew > 0){
				updateUser = true;
				rows = 0;
			} else {
				updateUser = true;
				exitUser.setName(name);
				exitUser.setGender(StringNumberUtil.parseShort(gender, 1));
				exitUser.setMobile(mobile);
				exitUser.setEmail(email);
				exitUser.setDuty(duty);
				exitUser.setRemark(remark);
				if(StringNumberUtil.notEmpty(newpassword)){
					//
					exitUser.setPassword(password);
				}
				exitUser.setInt1(isadmin? 1:0);
				exitUser.setInt2(fjq ? 1:0);
				rows = systemUserService.updateUserInfo(exitUser, operator);
			}
			//// 更新
			if( 1== rows){
				if(updateUser){
					message.setInfo("修改成功!");
				} else {
					message.setInfo("添加成功!");
				}
				message.setSuccess();
			} else {
				if(islocalnew > 0 && updateUser){
					message.setInfo("用户账号已存在!");
				} else if (updateUser) {
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
		if(islocalnew > 0){
			log.setOpaction("新增用户");
		} else{
			log.setOpaction("修改用户");
		}
		log.setLogtype("用户信息");

		try{
			logService.add(log, operator, message);
		} catch (Exception e) {
			// 吃掉异常
		}
		//
		return message;
	}

	// 删除用户
	@RequestMapping(value = {"/ajax/deleteuser.json"})
	@ResponseBody
	public Object ajaxDeleteUserInfo(HttpServletRequest request, HttpServletResponse response) {
		// 获取用户信息
		SystemUser operator = getLoginUser(request);
		// userid
		String userid = request.getParameter("userid");
		String delflag = request.getParameter("delflag");

		//
		boolean inputCheckOK = true;
		String inputCheckMessage = "输入的信息不正确";
		
		// 新包装对象
		SystemUser newUser = new SystemUser();
		newUser.setUserid(userid);
		newUser.setOpid(userid);
		newUser.setOptime(new Date());
		newUser.setDelflag(delflag);
		//
		if (!StringNumberUtil.notEmpty(userid)) {
			inputCheckOK = false;
			inputCheckMessage = "用户ID不正确";
		} else if (!StringNumberUtil.notEmpty(delflag)) {
			inputCheckOK = false;
			inputCheckMessage = "非法请求";
		}

		JSONMessage message = JSONMessage.newMessage();
		//
		if(inputCheckOK){
			//int rows = systemUserService.updateUserInfo(newUser, operator);
			int rows = systemUserService.deleteToHistory(newUser, operator);
			//// 更新
			if( 1== rows){
				message.setInfo("删除成功!");
				message.setSuccess();
			} else {
				message.setInfo("删除失败!");
			}
		} else {
			message.setInfo(inputCheckMessage);
		}

		// 日志
		SystemLog log = new SystemLog();
		log.setLogtype("删除用户");
		log.setOpaction("删除");
		try{
			logService.add(log, operator, message);
		} catch (Exception e) {
			// 吃掉异常
		}
		//
		return message;
	}


	// 锁定用户,调离用户
	@RequestMapping(value = {"/ajax/lockuser.json"})
	@ResponseBody
	public Object ajaxLockUserInfo(HttpServletRequest request, HttpServletResponse response) {
		// 获取用户信息
		SystemUser operator = getLoginUser(request);
		// userid
		String userid = request.getParameter("userid");
		String islocked = request.getParameter("islocked");

		//
		boolean inputCheckOK = true;
		String inputCheckMessage = "输入的信息不正确";
		
		// 新包装对象
		SystemUser newUser = new SystemUser();
		newUser.setUserid(userid);
		newUser.setIslocked(islocked);
		newUser.setOpid(userid);
		newUser.setOptime(new Date());
		//
		if (!StringNumberUtil.notEmpty(userid)) {
			inputCheckOK = false;
			inputCheckMessage = "用户ID不正确";
		} else if (!StringNumberUtil.notEmpty(islocked)) {
			inputCheckOK = false;
			inputCheckMessage = "非法请求";
		}

		JSONMessage message = JSONMessage.newMessage();
		//
		if(inputCheckOK){
			//int rows = systemUserService.updateUserInfo(newUser, operator);
			int rows = systemUserService.deleteToHistory(newUser, operator);
			//// 更新
			if( 1== rows){
				message.setInfo("调离成功!");
				message.setSuccess();
			} else {
				message.setInfo("调离失败!");
			}
		} else {
			message.setInfo(inputCheckMessage);
		}

		// 日志
		SystemLog log = new SystemLog();
		log.setLogtype("调离用户");
		log.setOpaction("调离");
		try{
			logService.add(log, operator, message);
		} catch (Exception e) {
			// 吃掉异常
		}
		//
		return message;
	}


	@RequestMapping(value = {"/ajax/list.action", "/ajax/list.json"})
	@ResponseBody
	public Object listUserByPage(HttpServletRequest request, HttpServletResponse response) {
		//
		SystemUser operator = getLoginUser(request);
		// 页码, 0 开始
		String pageIndexStr = request.getParameter("pageIndex");
		// 每页显示条数
		String pageSizeStr = request.getParameter("pageSize");
		// 排序列名
		//String sortField = request.getParameter("sortField");
		// asc, desc
		//String sortOrder = request.getParameter("sortOrder");
		// 搜索
		String key = request.getParameter("key");
		String orgid = request.getParameter("orgid");
		SystemOrganization org = new SystemOrganization();
		org.setOrgid(orgid);

		if(false ==StringNumberUtil.notEmpty(orgid)){
			org = operator.getOrganization();
			orgid = org.getOrgid();
		}
		
		//
		int pageIndex = parseInt(pageIndexStr, 0);
		int pageSize  = parseInt(pageSizeStr, 20);
		
		int total = 0;
		List<SystemUser> users = null;
		// 不为空,则执行搜索
		if(StringNumberUtil.notEmpty(key)){
			// 执行搜索
			key = key.trim();
			//// 总数
			total = systemUserService.countByOrgKey(org, key);
			// 分页得到的数据
			users = systemUserService.getPageByOrgKey(org, key, pageIndex, pageSize);
			
		} else if(StringNumberUtil.notEmpty(orgid)){
			//// 总数
			total = systemUserService.countByOrg(org);
			// 分页得到的数据
			users = systemUserService.getPageByOrg(org, pageIndex, pageSize);
		
		} else {
			//// 总数
			total = 0;
			users = new ArrayList<SystemUser>();
		}
		//
		processUserBizRole(users);
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setTotal(total);
		message.setData(users);
		
		//
		return message;
	}
	
	// 处理用户角色
	private void processUserBizRole(List<SystemUser> users) {
		// 获取所有 Role,使用缓存
		List<SystemRole> roleList = roleService.selectAll(true);
		if(null == roleList || null == users){
			return;
		}
		// 查询用户的role Map, 这方法有点土
		List<UserRoleWrapper> wrappers = roleService.getAllRoleWrappersByUserids(users);
		
		// 遍历设值
		Iterator<SystemUser> iteratorU = users.iterator();
		while (iteratorU.hasNext()) {
			SystemUser systemUser = (SystemUser) iteratorU.next();
			//
			String userid = systemUser.getUserid();
			if(null == userid){
				continue;
			}
			//
			processAndSetUserBizRole(systemUser, roleList, wrappers);
		}
	}
	private void processAndSetUserBizRole(SystemUser user, List<SystemRole> roleList, List<UserRoleWrapper> wrappers){
		//
		if(null == user || null == roleList || null == wrappers){
			return;
		}
		// 普通用户的 roleID, 暂时不管
		Integer roleid_generalUser = 2;
		//
		String userid = user.getUserid();
		if(null == userid){
			return;
		} else {
			userid = userid.trim();
		}
		Set<SystemRole> bizroles = new HashSet<SystemRole>();
		List<UserRoleWrapper> bizWrappers = new ArrayList<UserRoleWrapper>();
		
		// 默认的普通用户
		UserRoleWrapper _generalUserWrapper = new UserRoleWrapper();
		_generalUserWrapper.setUserid(userid);
		_generalUserWrapper.setRoleid(roleid_generalUser);
		//bizWrappers.add(_generalUserWrapper);
		//SystemRole bizrole = null;
		//Integer  bizroleId = roleid_generalUser;
		//
		Iterator<UserRoleWrapper> iteratorW = wrappers.iterator();
		while (iteratorW.hasNext()) {
			UserRoleWrapper userRoleWrapper = (UserRoleWrapper) iteratorW.next();
			if(null == userRoleWrapper){
				continue;
			}
			String useridW = userRoleWrapper.getUserid();
			if(null != useridW){
				useridW = useridW.trim();
			}
			//
			if(userid.equalsIgnoreCase(useridW)){
				// 不等于普通用户
				if(!useridW.equalsIgnoreCase(""+roleid_generalUser)){
					//
					//bizroleId = userRoleWrapper.getRoleid(); //
				}
				bizWrappers.add(userRoleWrapper);
			}
		}
		// 小循环做外层遍历
		Iterator<UserRoleWrapper> iteratorBizW = bizWrappers.iterator();
		while (iteratorBizW.hasNext()) {
			UserRoleWrapper w = (UserRoleWrapper) iteratorBizW.next();
			Integer  bizroleId = w.getRoleid();

			//
			// 内部遍历
			Iterator<SystemRole> iteratorR = roleList.iterator();
			// 内层循环
			while (iteratorR.hasNext()) {
				SystemRole systemRole = (SystemRole) iteratorR.next();
				//
				if(null == systemRole){
					continue;
				}
				Integer roleId = systemRole.getRoleid();
				
				if(null != roleId && roleId.equals(bizroleId)){
					bizroles.add(systemRole);
					break; // 跳出此 while 循环
				}
			} // 内层循环结束
		}
		// 
		//
		user.setBizroles(bizroles);
		return;
	}
	/**
	 * 根据用户,获取对应的角色.列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ajax/getRoleTreeByUser.json")
	@ResponseBody
	public Object getRoleTreeByUser(HttpServletRequest request, HttpServletResponse response) {
		SystemUser user=getLoginUser(request);
		String userid = request.getParameter("userid");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("departid", user.getDepartid());
		map.put("unitlevel", user.getOrganization().getUnitlevel());
		//// 总数
		//int total = roleService.countAll();
		// 取得所有数据? 顶层数据?
		// 后期考虑,只显示某些部门的子部门
		List<SystemRole> permissions = roleService.listByUserid(userid,map);
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		//message.setTotal(total); 
		message.setData(permissions);
		
		//
		return message.getData();
	}


	/**
	 * 根据用户,获取对应的部门
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ajax/getOrgTreeByUser.json")
	@ResponseBody
	public Object getOrgTreeByUser(HttpServletRequest request, HttpServletResponse response) {

		String userid = request.getParameter("userid");
		request.setAttribute("userid", userid);
		//
		SystemUser operator = getLoginUser(request);
		// 
		String porgid = operator.getOrganization().getOrgid();
		//// 总数
		//int total = roleService.countAll();
		// 取得所有数据? 顶层数据?
		// 后期考虑,只显示某些部门的子部门
		List<SystemOrganization> orgs = orgService.listByOrganizationPid(porgid, null);
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		//message.setTotal(total);
		message.setData(orgs);
		
		//
		return message.getData();
	}

	/**
	 * 根据用户,保存对应的角色
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ajax/saveRoleForUser.json")
	@ResponseBody
	public Object saveRoleForUser(HttpServletRequest request, HttpServletResponse response) {
		//
		SystemUser operator = getLoginUser(request);
		// srid
		String userid = request.getParameter("userid");
		String departid = "sys";
		String opid = operator.getUserid();
		// roleids
		String roleids = request.getParameter("roleids");
		// 去除空格
		roleids = roleids.replaceAll(" ", "");
		//
		String[] perms = roleids.split(",");
		List<UserRoleWrapper> newroles = new ArrayList<UserRoleWrapper>();
		for (int i = 0; i < perms.length; i++) {
			String perm = perms[i];
			int roleid = StringNumberUtil.parseInt(perm, 0);
			if(roleid < 1){
				continue;
			}
			if(StringUtils.isEmpty(perm)){
				continue;
			}
			//
			UserRoleWrapper nwrapper = new UserRoleWrapper();
			//
			nwrapper.setDepartid(departid);
			nwrapper.setOpid(opid);
			nwrapper.setUserid(userid);
			nwrapper.setRoleid(roleid);
			//
			newroles.add(nwrapper);
		}
		//
		boolean success = roleService.updateByUser(userid, newroles);
		
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setData(null);
		if(success){
			message.setInfo("更新用户-角色成功!");
			message.setSuccess();
		} else {
			message.setInfo("更新用户-角色失败!");
		}

		//
		return message;
	}

	/**
	 * 根据用户,保存对应的部门
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ajax/saveOrgForUser.json")
	@ResponseBody
	public Object saveOrgForUser(HttpServletRequest request, HttpServletResponse response) {
		//
		SystemUser operator = getLoginUser(request);
		// srid
		String userid = request.getParameter("userid");
		String orgid = request.getParameter("orgid");
		String opid = operator.getUserid();
		//
		//
		UserOrgWrapper nwrapper = new UserOrgWrapper();
		//
		nwrapper.setUserid(userid);
		nwrapper.setOrgid(orgid);
		nwrapper.setOpid(opid);
			//
		//
		boolean success =   systemUserService.updateUserOrg(nwrapper, operator);
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setData(null);
		if(success){
			message.setInfo("更新用户-部门成功!");
			message.setSuccess();
		} else {
			message.setInfo("更新用户-部门失败!");
		}

		//
		return message;
	}
	
	//start add by blue_lv 2015-07-14
	@RequestMapping("/getUserinfos")
	@ResponseBody
	public com.sinog2c.model.JSONMessage<SystemUser> getArchFileInfos(HttpServletRequest request) {
		return this.systemUserService.getPageByOrgidAndKey2(RequestUtil
				.parseParamMapForPagination(request));
	}
	//end add by blue_lv 2015-07-14
	
	@RequestMapping(value="getKeYuanList.action")
	@ResponseBody
	public Object getKeYuanList(HttpServletRequest request){
		
		SystemUser user = getLoginUser(request);
		String departid = user.getDepartid();
		Map<String, Object> map = this.parseParamMap(request);
		map.put("departid", departid);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = systemUserService.getKeYuanList(map);
		return list;
	}
	@RequestMapping(value="ajax/jiechukey.action")
	@ResponseBody
	public Object jiechukey(HttpServletRequest request){
		int result = 0;
		String certno = request.getParameter("certno") == null ? "" : request.getParameter("certno");
		result = systemUserService.jiechukey(certno);
		JSONMessage message = JSONMessage.newMessage();
		if (result == 0) {
			message.setInfo("证书不存在");
		}else {
			message.setInfo("解除成功");
		}
		return message;
	}
}

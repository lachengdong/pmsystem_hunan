package com.sinog2c.mvc.controller.officeAssistant;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sinog2c.model.officeAssistant.TbuserNotice;
import com.sinog2c.model.system.OrgUserTreeNode;
import com.sinog2c.model.system.ResourceType;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.user.UserGrantDetail;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.exception.LoginFailureException;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.officeAssistant.SchedulingService;
import com.sinog2c.service.api.officeAssistant.UserGrantService;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemOrganizationService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.service.api.system.SystemUserService;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 授权管理. <br/>
 * 需要一个主表与一个细节表,为的是牺牲修改速度,优化查询速度.<br/>
 * 主表为:TBUSER_NOTICE 中 messagetype=1的记录;(其实这有点杂乱,但先实现,等待后期维护)<br/>
 * 细节表为: TBUSER_USERRES;
 */
@Controller					// @Controller 注解,所有的MVC控制器类,都需要加上
@RequestMapping("/grant")	// 配置统一的二级URL映射路径,本类下都生效;  如: /pmsys/grant/xxxxx.xxx
public class GrantController extends ControllerBase{	// 需要继承 ControllerBase

	@Autowired 
	private SystemResourceService systemResourceService;
	// 使用Spring自动注入功能,引入需要的服务层对象
	@Autowired
	private UserGrantService grantService; 
	@Autowired
	private SystemOrganizationService orgService;
	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private SystemLogService logService;

	// TODO 需要替换
	//s@Autowired
	//private UserNoticeService userNoticeService;
	@Autowired
	private SchedulingService schedulingService;
    
	/**
	 * 方法描述：授权管理 进入 "被受权限"和"已受权限" tab页面
	 * @author ：mushuhong
	 * @version 2015年1月14日15:21:22
	 */
	@RequestMapping("/beiSouOrYiSouGrantPage.page")
	public ModelAndView beiSouOrYiSouGrantPage(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("officeAssistant/grant/grantManageTab");
		
		return mav;
	}
	
	/**
	 * 方法描述：已受权限
	 */
	@RequestMapping("/manage.page")
	public ModelAndView manageGrantPage(HttpServletRequest request, HttpServletResponse response) {
		// 注入权限值;父类的方法
		returnResourceMap(request);
		// 模型和视图.
		ModelAndView mav = new ModelAndView("officeAssistant/grant/grantmanage");
		// 返回该对象
		return mav;
	}

	/**
	 * 方法描述：被受权限 
	 * @author :mushuhong
	 * @version：2015年1月14日15:28:56
	 */
	@RequestMapping("/beiSouManageGrantPage")
	public ModelAndView beiSouManageGrantPage(HttpServletRequest request, HttpServletResponse response) {
		// 注入权限值;父类的方法
		returnResourceMap(request);
		// 模型和视图.
		ModelAndView mav = new ModelAndView("officeAssistant/grant/beiSouGrantManage");
		// 返回该对象
		return mav;
	}
	/**
	 * 显示选择用户页面
	 */
	@RequestMapping("/selectuser.page")
	public ModelAndView selectuserPage(HttpServletRequest request, HttpServletResponse response) {
		// 注入权限值;父类的方法
		returnResourceMap(request);
		// 模型和视图.
		ModelAndView mav = new ModelAndView("officeAssistant/grant/selectuser");
		// 返回该对象
		return mav;
	}

	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/update.page")
	public ModelAndView addOrUpdatePage(HttpServletRequest request) {
		//获取当前登录用户内容
		SystemUser operator = getLoginUser(request);
		//获取前台数据
		int noticeid = getParameterInt(request, "noticeid", 0);
		String type = request.getParameter("type");
		request.setAttribute("action", type);//传到前台当做 选择罪犯下拉列表的过滤条件
		request.setAttribute("noticeid", noticeid);
		//转向 修改页面
		String viewName = "officeAssistant/grant/add";
		ModelAndView mav = new ModelAndView(viewName);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if ("add".equals(type)) {
			// 新增时没有多余的数据
		} else {
			TbuserNotice tbuserNotice = schedulingService.getDataByNoticeId(noticeid);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			map.put("noticeid", tbuserNotice.getNoticeid());
			map.put("remark", tbuserNotice.getRemark());
			map.put("username", tbuserNotice.getUsername());
			map.put("aistarttime", sdf.format(tbuserNotice.getStarttime()));
			map.put("aiendtime", sdf.format(tbuserNotice.getEndtime()));
			map.put("shouquan", tbuserNotice.getState());
			//viewName = "officeAssistant/grant/update"; // 不使用这个
		}
		//
		if(noticeid > 0){
			TbuserNotice notice = grantService.getByNoticeid(noticeid, operator);
			if(null != notice){
				mav.addObject("notice", notice);
			}
		}
		//修改的时候不能 添加 已经有的权限(不包含已经授权过的权限)
		String grantids = grantService.checkXZUserGrantids(request, getLoginUser(request));
		request.setAttribute("grantids", grantids);
		//mav.addObject("tbuserNotice", map);
		// 1. 如果是新增，则所有数据只需要查出来即可.
		// 2. 如果是更新，则需要根据ID获取记录数据
		// 3. 然后填值,获取授权资源树. 展示
		
		// 统一进入 add 页面
		
		return mav;
	}

	

	/**
	 * 用户授权管理取数据. 列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ajax/list.json")
	@ResponseBody
	public Object listUserGrantByPage(HttpServletRequest request, HttpServletResponse response) 
			throws UnsupportedEncodingException {
		SystemUser user = getLoginUser(request);
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		String key = request.getParameter("key");
		if(key!=null&&key.length()>0){
			key= URLDecoder.decode(key,"UTF-8");
		}
		HashMap map = new HashMap();
		int total = grantService.selectGrantCount(1, user.getUserid(), key);
		List<TbuserNotice> list = grantService.selectGrantList(1, user.getUserid(), key, pageIndex, pageSize, sortField, sortOrder);
		//处理
		if (list != null) {
			for (TbuserNotice tbuserNotice : list) {
				int noticeid = tbuserNotice.getNoticeid();//获取事件id
				String userid = tbuserNotice.getUsername();//被授权id
				String opid = tbuserNotice.getOpid();//授权人的id
				map.clear();
				map.put("noticeid", noticeid);
				map.put("userid", userid);
				map.put("opid", opid);
				//根据noticeid查询出 对应的最低级的权限
				String remark = grantService.queryMinGrantByNoticeids(map);
				tbuserNotice.setRemark(remark);
			}
		}
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}
	
	/**
	 * 方法描述：用户被受权限的列表
	 * @author ：mushuhong
	 * @version：2015年1月14日16:00:36
	 */
	@RequestMapping(value = "/ajax/beiSouList.json")
	@ResponseBody
	@SuppressWarnings("all")
	public Object beiSoulistUserGrantByPage(HttpServletRequest request, HttpServletResponse response) 
			throws UnsupportedEncodingException {
		SystemUser user = getLoginUser(request);
		int pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String sortField = request.getParameter("sortField");
		String sortOrder = request.getParameter("sortOrder");
		String key = request.getParameter("key");
		HashMap map = new HashMap();
		int total = grantService.selectGrantCount_bs(1, user.getUserid(), key);
		List<TbuserNotice> list = grantService.selectGrantList_bs(1, user.getUserid(), key, pageIndex, pageSize, sortField, sortOrder);
		if (list != null) {
			for (TbuserNotice tbuserNotice : list) {
				int noticeid = tbuserNotice.getNoticeid();//获取事件id
				String userid = tbuserNotice.getUsername();//被授权id
				String opid = tbuserNotice.getOpid();//授权人的id
				map.clear();
				map.put("noticeid", noticeid);
				map.put("userid", userid);
				map.put("opid", opid);
				//根据noticeid查询出 对应的最低级的权限
				String remark = grantService.queryMinGrantByNoticeids(map);
				tbuserNotice.setRemark(remark);
			}
		}
		JSONMessage message = JSONMessage.newMessage();
		message.setTotal(total);
		message.setData(list);
		return message;
	}

	@RequestMapping(value = "/ajax/granttree.json")
	@ResponseBody
	public Object listGrantTree(HttpServletRequest request, HttpServletResponse response) 
			throws UnsupportedEncodingException {
		// 登录用户
		SystemUser user = getLoginUser(request);
		//
		if(null == user){
			throw new LoginFailureException("请登录.");
		}
		// 获取参数
		int noticeid = getParameterInt(request, "noticeid", 0);
		String opid = user.getUserid();
		
		//
		List<SystemResource> resources = null;
		
		// 后门,如果有什么资源赋权出问题,则  root 可以直接看到
		if("root".equals(opid)){
			// root 获取所有资源
			resources = systemResourceService.selectAll();
		} else {
			// 理论上,普通管理员不能新建资源,或者新建以后即给自己的角色赋权.
			// 取得管理员被授权的资源,管理员可以将该资源授权给其他用户...
			// 目前遇到一些问题,创建资源时,需要将资源赋给当前角色, 
			// 但是当前用户可能有多个角色,所以应该是根据创建者ID来进行拥有者赋权.
			// 管理员有普通用户的资源合不合理? 没权限就看不见,合不合理
			resources = systemResourceService.getAllResourceByUser(user);
		}
		// 过滤14的类型,不显示
		resources = filterWithout(resources, ResourceType.TYPE_ID_REPORT);
		
		// 总数
		int total = resources.size();
		
		// 如果 noticeid 不为空,则需要进行处理
		if(noticeid > 0){
			//
			TbuserNotice notice = grantService.getByNoticeid(noticeid, user);
			if(null != notice){
				List<UserGrantDetail> details = notice.getGrantDetails();
				//
				if(null == details || details.isEmpty()){
					String username = notice.getUsername();
					request.setAttribute("noticeid", noticeid);
					details = grantService.listGrantByUseridAndOpid(username, user,noticeid);
				}
				//
				// 设置是否已授权
				processUserGrantChecked(resources, details);
			}
		} else {
			// 直接返回即可
		}
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		message.setTotal(total); 
		message.setData(resources);
		message.setSuccess();
		//
		return message.getData();
	}
	

	/** 
	 * 处理用户授权的选中 ; 无返回值,污染resources 资源
	 * @param resources 资源
	 * @param grants 权限
	 */
	public void processUserGrantChecked(List<SystemResource> resources, List<UserGrantDetail> grants) {
		
		if(null == resources || null == grants){
			return;
		}
		// 迭代,设置. 优化算法,外层迭代大的集合,内层迭代小的集合。
		Iterator<SystemResource> iteratorR = resources.iterator();
		while (iteratorR.hasNext()) {
			SystemResource systemResource = (SystemResource) iteratorR.next();
			if(null == systemResource){
				continue;
			}
			String resid = systemResource.getResid();
			// 根据spid,判断是否有对应权限记录
			if (hasGrant(grants, resid)) {
				// 增加checked信息
				systemResource.setChecked(true);
			} else {
			}
		}
	}

	private boolean hasGrant(List<UserGrantDetail> grants, String resid){
		if(null == grants || null == resid){
			return false;
		}
		//
		Iterator<UserGrantDetail> iteratorG = grants.iterator();
		while (iteratorG.hasNext()) {
			UserGrantDetail userGrantDetail = (UserGrantDetail) iteratorG.next();
			if(null != userGrantDetail){
				String residG = userGrantDetail.getResid();
				// 具有记录
				if(resid.equalsIgnoreCase(residG)){
					return true;
				}
			}
		}
		//
		return false;
	}
	

	//
	public List<SystemResource> filterWithout(List<SystemResource> resources, final int restypeid){
		if(null == resources){
			return null;
		}
		List<SystemResource> result = new ArrayList<SystemResource>();
		//
		Iterator<SystemResource> iteratorR = resources.iterator();
		while (iteratorR.hasNext()) {
			SystemResource systemResource = (SystemResource) iteratorR.next();
			if(null != systemResource){
				int type = systemResource.getRestypeid();
				if(restypeid == type){
					// 不加到新List中
				} else {
					result.add(systemResource);
				}
			}
		}
		//
		return result;
	}

	@RequestMapping(value = {"/ajax/save.json", "/ajax/update.json", "/ajax/add.json"})
	@ResponseBody
	public Object ajaxAddOrUpdateGrant(HttpServletRequest request, HttpServletResponse response) {
		// 获取用户信息
		SystemUser operator = getLoginUser(request);
		
		String opid = operator.getUserid();//当前用户的id(账号)
		String opname = operator.getName();//当前用户的名称(姓名)
		// 是否新增
		int noticeid = getParameterInt(request, "noticeid", 0);
		int state = getParameterInt(request, "state", 0);
		
		String starttimeStr = request.getParameter("starttime");//授权开始时间
		String endtimeStr = request.getParameter("endtime");//授权结束时间
		String  userid= request.getParameter("username");//被授权的用户id
		String remark = request.getParameter("remark");//备注
		String username = request.getParameter("name");//当前用户的名称
		
		String grantids = getParameterString(request, "grantids", "");//所有选择的权限id

		//
		boolean inputCheckOK = true;
		String inputCheckMessage = "输入的信息不完整";
		
		if (!StringNumberUtil.notEmpty(username)) {
			inputCheckOK = false;
			inputCheckMessage = "被授权用户不能为空";
		}
		//
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//
		Date starttime = new Date();
		Date endtime = new Date();
		try{
			starttime = format.parse(starttimeStr);
			endtime = format.parse(endtimeStr);
		} catch (Exception e) {
		}
		
		// 新包装对象
		TbuserNotice notice = new TbuserNotice();
		notice.setNoticeid(noticeid);
		notice.setState(state);
		notice.setStarttime(starttime);
		notice.setEndtime(endtime);
		notice.setUsername(userid);
		notice.setRemark(remark);
		notice.setOpid(opid);
		notice.setOptime(new Date());
		notice.setMessagetype(1);
		notice.setTitle(""+opname+"授权给"+username);
		notice.setContent(""+opname+"授权给"+username);
		
		//
		List<UserGrantDetail> grantDetails = new ArrayList<UserGrantDetail>();
		
		// 处理授权信息
		String[] grantArray = grantids.split(",");
		for (int i = 0; i < grantArray.length; i++) {
			String resid = grantArray[i];
			//
			UserGrantDetail detail = new UserGrantDetail();
			detail.setResid(resid);
			detail.setUserid(userid);
			detail.setOpid(opid);
			detail.setOptime(starttime);
			detail.setDistime(endtime);
			detail.setName(null);
			detail.setPresid(null);
			//
			grantDetails.add(detail);
		}
		//
		notice.setGrantDetails(grantDetails);
		
		//
		int rows = 0;
		//
		JSONMessage message = JSONMessage.newMessage();
		//
		if(inputCheckOK){

			// 新增; 则走新增的方法
			if(0 == noticeid){
				rows = grantService.add(notice, operator);
			} else {
				rows = grantService.update(notice, operator);
			}
			//
			if( 1== rows){
				if(noticeid > 0){
					message.setInfo("修改成功!");
				} else {
					message.setInfo("新增成功!");
				}
				message.setSuccess();
			} else {
				if(noticeid > 0){
					message.setInfo("修改失败!");
				} else {
					message.setInfo("新增失败!");
				}
			}
		} else {
			message.setInfo(inputCheckMessage);
		}
		//
		SystemLog log = new SystemLog();
		log.setLogtype("授权管理");
		log.setOpaction("新增/修改");
		log.setStatus(message.getStatus());
		log.setOpcontent(message.getInfo());
		log.setRemark(message.getInfo());
		try {
			logService.add(log, operator);
		} catch (Exception e) {
			// 吃掉异常
		}
		//
		return message;
	}

	/**
	 * 根据消息Id删除指定信息
	 */
	@RequestMapping(value = "/ajax/remove.json")
	@ResponseBody
	public Object ajaxRemoveUser(HttpServletRequest request) {
		// 获取用户信息
		SystemUser operator = getLoginUser(request);
		//String opid = operator.getUserid();
		// 
		int noticeid = getParameterInt(request, "noticeid", 0);

		//
		boolean inputCheckOK = true;
		String inputCheckMessage = "输入的信息不完整";
		
		if (noticeid < 1) {
			inputCheckOK = false;
			inputCheckMessage = "错误的请求";
		}
		
		// 需要删除对应的记录,以及所授予的权限
		// 1. 查询出记录
		// 2. 删除授权
		// 3. 删除主表记录
		JSONMessage message = JSONMessage.newMessage();
		if(inputCheckOK){

			int rows = grantService.delete(noticeid, operator);

			//
			if( 0 != rows){
				message.setInfo("删除成功!");
				message.setSuccess();
			} else {
				message.setInfo("删除失败!");
			}
		} else {
			message.setInfo(inputCheckMessage);
		}

		SystemLog log = new SystemLog();
		log.setLogtype("授权管理");
		log.setOpaction("删除");
		log.setOpcontent("删除,noticeid=" + noticeid);
		log.setRemark(message.getInfo());
		log.setStatus(message.getStatus());
		try {
			logService.add(log, operator);
		} catch (Exception e) {
			// 吃掉异常
		}

		return message;
	}
	

	/**
	 * 根据用户,获取对应的部门以及用户树
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ajax/getOrgAndUserTree.json")
	@ResponseBody
	public Object getOrgAndUserTree(HttpServletRequest request, HttpServletResponse response) {

		// 
		SystemUser operator = getLoginUser(request);
		// 
		String porgid = operator.getOrganization().getOrgid();
		
		String noticeid = request.getParameter("noticeid");
		String typeString = request.getParameter("type");
		TbuserNotice tbuserNotice = null;
		if (noticeid != null && !"".equals(noticeid)) {
			tbuserNotice = schedulingService.getDataByNoticeId(Integer.parseInt(noticeid));
		}
		
		//// 总数
		//int total = roleService.countAll();
		// 取得所有数据? 顶层数据?
		// 后期考虑,只显示某些部门的子部门
		List<SystemOrganization> orgs = orgService.listByOrganizationPid(porgid, null);
		
		// 获取所有部门
		SystemOrganization porg = new SystemOrganization();
		porg.setOrgid(porgid);
		
		// 获取所有用户，删除当前登录用户
		int userCount = systemUserService.countByOrg(porg);
		List<SystemUser> users = systemUserService.getPageByOrg(porg, 0, userCount);
		for(int i=0;i<users.size();i++){
			if(users.get(i).getUserid().equals(operator.getUserid()))users.remove(i);
		}
		//如果是修改：那么只能 是当前被受权限的用户，其他用户不能显示
		List<SystemUser> listUsers = new ArrayList<SystemUser>();
		List<OrgUserTreeNode> nodes2 = new ArrayList<OrgUserTreeNode>();
		if ("update".equals(typeString)) {
			for (SystemUser systemUser : users) {
				if (tbuserNotice.getUsername().equals(systemUser.getUserid())) {
					listUsers.add(systemUser);
				}
			}
			//修改和更新的user内容是不同的
			nodes2 = OrgUserTreeNode.parseUserList(listUsers);
		}else {
			//修改和更新的user内容是不同的
			nodes2 = OrgUserTreeNode.parseUserList(users);
		}
		// 拼接
		List<OrgUserTreeNode> nodes1 = OrgUserTreeNode.parseOrgList(orgs);
		//
		List<OrgUserTreeNode> data = new ArrayList<OrgUserTreeNode>();
		data.addAll(nodes1);
		data.addAll(nodes2);
		
		
		JSONMessage message = JSONMessage.newMessage();
		//
		//message.setTotal(total);
		message.setData(data);
		
		//
		return message.getData();
	}
	/**
	 * 方法描述：判断当前被授权的用户已经 被当前登录用户授过得权限
	 * @author：mushuhong
	 * @version：2015年1月14日17:15:29
	 * 
	 */
	@RequestMapping(value="/checkXZUserBSGrant")
	@ResponseBody
	public String checkXZUserBSGrant(HttpServletRequest request){
		SystemUser user = getLoginUser(request);
		String value = grantService.checkXZUserBSGrant(request, user);
		return value;
	}
}

package com.sinog2c.mvc.controller.flow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gkzx.common.OAParameter;
import com.sinog2c.config.SpringContextHolder;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.flow.TbflowDefine;
import com.sinog2c.model.flow.TbflowDefineWithBLOBs;
import com.sinog2c.model.flow.TbflowInstance;
import com.sinog2c.model.flow.TbflowInstanceTask;
import com.sinog2c.model.system.SystemResource;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.model.system.TemplateGridHeader;
import com.sinog2c.model.system.UserRoleWrapper;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.mvc.controller.common.CommonController;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.flow.FlowDeliverService;
import com.sinog2c.service.api.flow.FlowInstanceExecutionService;
import com.sinog2c.service.api.flow.FlowInstanceManagerService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.service.api.system.SystemRoleService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.RequestUtil;

@Controller
@RequestMapping("/deliver")
public class OAFlowDeliver_Pub_Controller {

	@Resource
	SystemTemplateService systemTemplateService;
	@Resource
	FlowInstanceManagerService flowInstanceManagerService;
	@Resource
	SystemResourceService systemResourceService;
	@Resource
	private FlowDeliverService flowDeliverService;
	@Resource
	SystemRoleService systemRoleService;

	@Autowired
	private CommonSQLSolutionService commonSQLSolutionService;

	@Autowired(required = true)
	private CommonSQLSolutionService solutionService;

	/**
	 * 流程发起--起点 保存草稿
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/doSaveFlowDraft.action", method = RequestMethod.POST)
	@ResponseBody
	public int doSaveFlowDraft(HttpServletRequest request) {
		// int result = -1;
		SystemUser user = ControllerBase.getLoginUser(request);
		TbflowInstance instance = JSON.parseObject(
				request.getParameter("data"), TbflowInstance.class);
		this.switchFlowInstanceHandle(instance);
		return this.flowInstanceManagerService.creatFlowInstanceDraft(instance,
				user);
	}

	/**
	 * 流程实例删除
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/doRemoveflowDraft.action", method = RequestMethod.POST)
	@ResponseBody
	public int doRemoveflowDraft(HttpServletRequest request) {

		List<TbflowInstance> list = JSON.parseArray(
				request.getParameter("data"), TbflowInstance.class);
		return this.flowInstanceManagerService.doRemoveFlowInstances(list);
	}

	/**
	 * 流程流转操作
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/doflowInstanceDeliver.action", method = RequestMethod.POST)
	@ResponseBody
	public int doflowInstanceDeliver(HttpServletRequest request) {
		SystemUser user = ControllerBase.getLoginUser(request);
		TbflowInstance instance = JSON.parseObject(
				request.getParameter("data"), TbflowInstance.class);
		this.switchFlowInstanceHandle(instance);
		if (instance.getAction().equalsIgnoreCase("new")) {
			return this.flowInstanceManagerService.creatFlowInstance(instance,
					user);
		}
		return this.flowInstanceManagerService.executeTask(instance, user);
	}

	/**
	 * 当前获取流程实例数据(当前用户的任务信息)
	 * 
	 * @param request
	 * @param templetid
	 *            :模板ID
	 * @param flowdefid
	 *            ：流程定义ID
	 * @param key
	 *            ：查询关键字的值
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listFlowInstanceBydefineID.action", method = RequestMethod.POST)
	@ResponseBody
	public JSONMessage<TbflowInstance> listFlowInstanceBydefineID(
			HttpServletRequest request,
			@RequestParam("templetid") String templetid,
			@RequestParam("flowdefid") String flowdefid,
			@RequestParam(value = "key", required = false, defaultValue = "") String key,
			@RequestParam(value = "state", required = false, defaultValue = "") String state,
			@RequestParam(value = "flowtype", required = false, defaultValue = "1") String flowtype) {

		Map<String, Object> map = RequestUtil
				.parseParamMapForPagination(request);

		Object tpl = RequestUtil.getSessionAttribute(request, templetid);
		TbsysTemplate template = null;
		if (tpl != null) {
			template = (TbsysTemplate) tpl;
		} else { // 对于类似于公文审批流程，获取流程起始节点上的表单模板
			template = this.systemTemplateService.gettemplatebyflowid(
					flowdefid, "0");
		}

		SystemUser user = ControllerBase.getLoginUser(request);
		return (JSONMessage<TbflowInstance>) this.flowInstanceManagerService
				.getflowInstanceTaskbyUserid(template, map, user.getUserid(),
						state, flowtype);
	}

	private String[] flowTypes = { "", " 公文发文列表", " 公文收文列表", " 行政审批列表 " };

	/**
	 * 获取流程定义信息列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getFlowByTypeAndDept", method = RequestMethod.POST)
	@ResponseBody
	public JSONMessage<TbflowDefine> getFlowByTypeAndDept(
			HttpServletRequest request) {
		SystemUser user = ControllerBase.getLoginUser(request);
		String departid = user.getDepartid();
		Map<String, Object> map = RequestUtil
				.parseParamMapForPagination(request);
		map.put("departid", departid);
		JSONMessage<TbflowDefine> message = this.flowDeliverService
				.getFlowByTypeAndDept(map);
		return message;
	}

	/**
	 * to流程实例发起页面---行政公文类流程发起页面（公文起草）
	 * 
	 * @param request
	 * @param type
	 *            :流程类型
	 * @param myjs1
	 * @param myjs2
	 * @author: blue
	 * @version: 2015-3-15 上午18:46:33
	 * @return
	 */
	@RequestMapping(value = "/toflowLaunch_Doc.page", method = RequestMethod.GET)
	public ModelAndView toflowLaunch_Doc(
			HttpServletRequest request,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "menuid", required = true) String menuid,
			@RequestParam(value = "myjs1", required = false, defaultValue = "") String myjs1,
			@RequestParam(value = "myjs2", required = false, defaultValue = "") String myjs2,
			@RequestParam(value = "solutionid", required = false, defaultValue = "") String solutionid) {
		ModelAndView mav = new ModelAndView("flow/flowLaunch_Doc");
		String path = request.getContextPath();
		mav.addObject("path", path);
		// /TODO:需修改
		int Itype = Integer.parseInt(type);
		mav.addObject("typeName", flowTypes[Itype]);

		mav.addObject("type", type);
		mav.addObject("menuid", menuid);
		mav.addObject("myjs1", myjs1);
		mav.addObject("myjs2", myjs2);
		mav.addObject("solutionid", solutionid);
		return mav;
	}

	/**
	 * to流程实例发起页面---一般类流程
	 * 
	 * @param request
	 * @param flowdefid
	 * @param menuid
	 * @param tempid
	 * @param myjs1
	 * @param myjs2
	 * @return
	 */
	@RequestMapping(value = "/toflowLaunch_Pub.page", method = RequestMethod.GET)
	public ModelAndView toflowLaunch_Pub(
			HttpServletRequest request,
			@RequestParam(value = "type", required = false, defaultValue = "3") String type,
			@RequestParam(value = "flowdefid", required = true) String flowdefid,
			@RequestParam(value = "menuid", required = true) String menuid,
			@RequestParam(value = "tempid", required = true) String tempid,
			@RequestParam(value = "myjs1", required = false, defaultValue = "") String myjs1,
			@RequestParam(value = "myjs2", required = false, defaultValue = "") String myjs2,
			@RequestParam(value = "solutionid", required = false, defaultValue = "") String solutionid,
			@RequestParam(value = "viewName", required = false, defaultValue = "") String viewName) {
		ModelAndView mav = new ModelAndView("flow/flowLaunch_Pub");
		if (!viewName.isEmpty())
			mav = new ModelAndView(viewName);
		this.toFlowInstanceNodeView(request, mav, flowdefid, menuid, myjs1,
				myjs2, tempid);
		mav.addObject("type", type);
		mav.addObject("solutionid", solutionid);
		return mav;
	}

	/**
	 * to流程流转--主页面，包含撰写表单、正文、上传版式正文、上传附件等页签（不同的流程页签不同）
	 * 
	 * @param request
	 * @param menuid
	 *            ：菜单ID
	 * @param templetid
	 *            ：公文表单模板
	 * @param flowdefid
	 *            ：公文流程定义ID
	 * @param type
	 *            ：流程定义类型
	 * @param attachjs
	 *            ：附加的js
	 * @param action
	 *            :当前动作
	 * @param flowid
	 *            ：流程实例ID
	 * @param taskid
	 *            ：任务ID
	 * @return
	 */
	@RequestMapping(value = "/toflowDeliver_main.page", method = RequestMethod.GET)
	public ModelAndView toflowdeliver_main(
			HttpServletRequest request,
			@RequestParam("menuid") String menuid,
			@RequestParam("templetid") String templetid,
			@RequestParam("flowdefid") String flowdefid,
			@RequestParam("type") String type,
			@RequestParam(value = "attachjs", required = false, defaultValue = "") String attachjs,
			@RequestParam(value = "action", required = false, defaultValue = "new") String action,
			@RequestParam(value = "flowid", required = false, defaultValue = "") String flowid,
			@RequestParam(value = "taskid", required = false, defaultValue = "") String taskid,
			@RequestParam(value = "solutionid", required = false, defaultValue = "") String solutionid) {

		SystemUser user = ControllerBase.getLoginUser(request);
		String path = request.getContextPath();
		ModelAndView mav = new ModelAndView("flow/flowDeliver_Main");
		// 流程流转处理，初始化页面顶端流程流转按钮
		if (!action.equalsIgnoreCase("view")) {
			this.setFlowdDeliverBtns(flowdefid, user, menuid, taskid, mav);
		}
		if (action.equalsIgnoreCase("new")) {
			// 分配一个flow实例id
			flowid = java.util.UUID.randomUUID().toString().replace("-", "");
		}
		mav.addObject("path", path);
		mav.addObject("type", type);
		mav.addObject("flowdefid", flowdefid);
		mav.addObject("menuid", menuid);
		mav.addObject("templetid", templetid);
		mav.addObject("attachjs", attachjs);
		mav.addObject("flowid", flowid);
		mav.addObject("taskid", taskid);
		mav.addObject("action", action);
		mav.addObject("solutionid", solutionid);
		return mav;
	}

	/**
	 * to流程流转--表单页签
	 * 
	 * @param request
	 * @param menuid
	 * @param templetid
	 * @param flowdefid
	 * @param type
	 * @param attachjs
	 * @param action
	 * @param flowid
	 * @param taskid
	 * @return
	 */
	@RequestMapping(value = "/toflowDeliver_Form.page", method = RequestMethod.GET)
	public ModelAndView toflowDeliver_Form(
			HttpServletRequest request,
			@RequestParam("menuid") String menuid,
			@RequestParam("templetid") String templetid,
			@RequestParam("flowdefid") String flowdefid,
			@RequestParam("type") String type,
			@RequestParam(value = "attachjs", required = false, defaultValue = "") String attachjs,
			@RequestParam(value = "action", required = false, defaultValue = "new") String action,
			@RequestParam(value = "flowid", required = false, defaultValue = "") String flowid,
			@RequestParam(value = "taskid", required = false, defaultValue = "") String taskid,
			@RequestParam(value = "solutionid", required = false, defaultValue = "") String solutionid) {

		SystemUser user = ControllerBase.getLoginUser(request);
		String path = request.getContextPath();
		String topstr = "";
		String roles = "";
		String formcontent = "";
		String Keyfields = "";
		String modiflag = "";
		String flowdocid = "";

		ModelAndView mav = new ModelAndView("flow/flowDeliver_Form");

		if (!action.equalsIgnoreCase("view")) {
			// 获取用户所具有的角色
			roles = this.getUserRoles(request, user);
			// 模板ID获取模板信息
			TbsysTemplate systemTemplate = systemTemplateService
					.selectByTempid1(templetid, user.getDepartid());
			// 关键字段映射---需要分开入库的数据
			Keyfields = systemTemplate.getKeyfields();
			if (action.equalsIgnoreCase("new")) {
				formcontent = String.format("[\"%s\"]",
						systemTemplate.getContent());
			}
			// 流程流转处理，初始化页面顶端AIP文档操作按钮
			topstr = this.getAipTopBtns(request, user, menuid);
		}
		// 查看或者编辑，查找分配给当前用户的流程任务
		if (!action.equalsIgnoreCase("new")) {
			TbflowInstance instance = this.flowInstanceManagerService
					.getflwoInstancebyidforAudit(flowid, user.getUserid());
			formcontent = String.format("[\"%s\"]", instance.getDoccontent());
			// 文档最新更新时间(防止交叉更新文档)
			modiflag = instance.getText6();
			flowdocid = instance.getFlowdocid();
		}

		// 设置任务的开始执行时间
		if (action.equalsIgnoreCase("edit")) {
			this.flowInstanceManagerService.updateTaskStartDate(taskid);
		}

		Map<String, Object> paramMap = RequestUtil.parseParamMap(request);
		this.assembleFormData(paramMap, user, mav);
		mav.addObject("keysreflect", Keyfields);
		mav.addObject("path", path);
		mav.addObject("topTools", topstr);
		mav.addObject("resid", menuid);
		mav.addObject("flowdefid", flowdefid);
		mav.addObject("templetid", templetid);
		mav.addObject("userRoles", roles);
		mav.addObject("attachjs", attachjs);
		mav.addObject("formcontent", formcontent);
		mav.addObject("flowid", flowid);
		mav.addObject("taskid", taskid);
		mav.addObject("action", action);
		mav.addObject("modiflag", modiflag);
		mav.addObject("flowdocid", flowdocid);
		mav.addObject("userName", user.getName());
		mav.addObject("userid", user.getUserid());
		mav.addObject("orgName", user.getOrganization().getName());
		mav.addObject("orgid", user.getOrganization().getOrgid());
		mav.addObject("type", type);
		return mav;
	}

	/**
	 * to流程流转--流程定义、流程任务完成情况页签
	 * 
	 * @param request
	 * @param menuid
	 * @param flowdefid
	 * @param type
	 * @param action
	 * @param flowid
	 * @return
	 */
	@RequestMapping(value = "/toflowDeliver_Inf.page", method = RequestMethod.GET)
	public ModelAndView toflowInD_flowInf(
			HttpServletRequest request,
			@RequestParam("menuid") String menuid,
			@RequestParam("flowdefid") String flowdefid,
			@RequestParam("type") String type,
			@RequestParam(value = "action", required = false, defaultValue = "new") String action,
			@RequestParam(value = "flowid", required = false, defaultValue = "") String flowid) {

		SystemUser user = ControllerBase.getLoginUser(request);
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		String cNode = "";
		String cNodeName = "";
		String tasks = "";
		ModelAndView mav = new ModelAndView("flow/flowDeliver_Info");
		TbflowDefineWithBLOBs flowdefine = this.flowDeliverService
				.selectByPrimaryKey(flowdefid);

		TbflowInstance instance = this.flowInstanceManagerService
				.getflwoInstancebyidforAudit(flowid, user.getUserid());

		if (instance == null) {
			FlowDeliver deliver = this.flowInstanceManagerService
					.getchildNodebyDeliverExplain(flowdefid,
							OAParameter.STARTNODE, "");
			cNodeName = deliver.getText3();
			cNode = deliver.getDnodeid();
		} else {
			cNode = instance.getCnode();
			cNodeName = instance.getCnodename();
			List<TbflowInstanceTask> list = this.flowInstanceManagerService
					.getflowTaskListByFlowid(flowid);

			tasks = JSON.toJSONString(list);
		}
		mav.addObject("flowdefine", flowdefine);
		mav.addObject("path", path);
		mav.addObject("basePath", basePath);
		mav.addObject("flowdefid", flowdefid);
		mav.addObject("flowid", flowid);
		mav.addObject("action", action);
		mav.addObject("cNodeName", cNodeName);
		mav.addObject("cNode", cNode);
		mav.addObject("tasks", tasks);
		mav.addObject("type", type);
		return mav;
	}

	/**
	 * to流程流转--任务列表页面（流程流转之个人任务）
	 * 
	 * @param request
	 * @param flowdefid
	 * @param menuid
	 * @param myjs1
	 * @param myjs2
	 * @return
	 */
	@RequestMapping(value = "/toflowDeliverTaskList.page", method = RequestMethod.GET)
	public ModelAndView toMydocListView(
			HttpServletRequest request,
			@RequestParam(value = "flowdefid", required = true) String flowdefid,
			@RequestParam(value = "menuid", required = true) String menuid,
			@RequestParam(value = "tempid", required = true) String tempid,
			@RequestParam(value = "flowtype", required = false, defaultValue = "1") String flowtype,
			@RequestParam(value = "state", required = false, defaultValue = "0") String state,
			@RequestParam(value = "myjs1", required = false, defaultValue = "") String myjs1,
			@RequestParam(value = "myjs2", required = false, defaultValue = "") String myjs2) {
		ModelAndView mav = new ModelAndView("flow/flowDeliver_TaskList");
		this.toFlowInstanceNodeView(request, mav, flowdefid, menuid, myjs1,
				myjs2, tempid);
		mav.addObject("flowtype", flowtype);
		mav.addObject("state", state);
		return mav;
	}

	@RequestMapping(value = "/toMyFlowTaskManage.page", method = RequestMethod.GET)
	public ModelAndView toMyFlowTaskManage(HttpServletRequest request,
			@RequestParam(value = "menuid", required = true) String menuid) {
		ModelAndView mav = new ModelAndView("flow/flowTask_manage");
		SystemUser user = ControllerBase.getLoginUser(request);
		String path = request.getContextPath();
		List<SystemResource> res = CommonController.GetResourceByType(user,
				menuid, OAParameter.LEFTOUTLOOKMENU);
		mav.addObject("path", path);
		mav.addObject("treeMenus", JSON.toJSONString(res));
		return mav;
	}

	/**
	 * 查看流程表单信息
	 * 
	 * @param request
	 * @param templetid
	 * @return
	 */
	@RequestMapping(value = "/toViewFlowFormInfo.page", method = RequestMethod.GET)
	public ModelAndView toViewFlowFormInfo(HttpServletRequest request,
			@RequestParam("templetid") String templetid) {

		SystemUser user = ControllerBase.getLoginUser(request);
		String path = request.getContextPath();
		String formcontent = "";
		ModelAndView mav = new ModelAndView("flow/viewFlowFormInfo");
		TbsysTemplate systemTemplate = systemTemplateService.selectByTempid1(
				templetid, user.getDepartid());
		formcontent = String.format("[\"%s\"]", systemTemplate.getContent());
		mav.addObject("path", path);
		mav.addObject("templetid", templetid);
		mav.addObject("formcontent", formcontent);
		return mav;
	}

	/**
	 * 查看流程定义信息
	 * 
	 * @param request
	 * @param flowdefid
	 * @return
	 */
	@RequestMapping(value = "/toViewFlowDefineInfo.page", method = RequestMethod.GET)
	public ModelAndView toViewFlowDefineInfo(HttpServletRequest request,
			@RequestParam("flowdefid") String flowdefid) {
		String path = request.getContextPath();
		ModelAndView mav = new ModelAndView("flow/viewFlowDefineInfo");
		TbflowDefineWithBLOBs flowdefine = this.flowDeliverService
				.selectByPrimaryKey(flowdefid);
		mav.addObject("flowdefine", flowdefine);
		mav.addObject("path", path);
		mav.addObject("flowdefid", flowdefid);

		return mav;
	}

	/**
	 * 获取当前任务节点的所有可执行者
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@RequestMapping(value = "/toGetCurrentNodeAssigner.page", method = RequestMethod.GET)
	public ModelAndView toGetCurrentNodeAssigner(HttpServletRequest request)
			throws InstantiationException, IllegalAccessException {
		TbflowInstance instance = RequestUtil.getObjectFromRequest(request,
				TbflowInstance.class);
		List<SystemUser> list = this.flowInstanceManagerService
				.getNodeAssigner(instance);
		ModelAndView mav = new ModelAndView("flow/flowDeliver_ExeUsersSelector");
		mav.addObject("assigners", JSON.toJSONString(list));
		return mav;
	}

	/**
	 * 流程处理后台service设置
	 * 
	 * @param instance
	 */
	private void switchFlowInstanceHandle(TbflowInstance instance) {
		switch (instance.getFlowtype()) {
		case "1":
		case "2":
			FlowInstanceExecutionService execution = SpringContextHolder
					.getBean("flow_docInstanceExcutionService",
							FlowInstanceExecutionService.class);
			this.flowInstanceManagerService
					.setFlowInstanceExcutionService(execution);
			break;
		default:
			execution = SpringContextHolder.getBean(
					"flowInstanceExecutionService",
					FlowInstanceExecutionService.class);
			this.flowInstanceManagerService
					.setFlowInstanceExcutionService(execution);
			break;

		}

	}

	// TODO:此方法需重写
	/**
	 * 设置页面流转按钮
	 * 
	 * @param flowdefid
	 * @param user
	 * @param menuid
	 * @param taskid
	 * @param mav
	 */	
	public void setFlowdDeliverBtns(String flowdefid, SystemUser user,
			String menuid, String taskid, ModelAndView mav) {
		String result = "";
		//boolean ispub = true;
		// 任务所处的流程节点
		String cNodeID = "";

		// 获取当前用户的任务信息
		if (!taskid.isEmpty()) {
			TbflowInstanceTask task = this.flowInstanceManagerService
					.getTbflowInstanceTaskbyID(taskid);
			cNodeID = task.getCnode();

		} else {
			// 获取开始节点
			FlowDeliver deliver = this.flowInstanceManagerService
					.getchildNodebyDeliverExplain(flowdefid,
							OAParameter.STARTNODE, "");
			cNodeID = deliver.getDnodeid();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mav.addObject(
					"title",
					String.format("%s(%s)", deliver.getRemark(),
							sdf.format(new Date())));
		}

		// 获取当前流程的所用节点信息
		List<FlowDeliver> flowNodes = this.flowDeliverService
				.getflowNodelistbyflowdefineid(flowdefid);
		if (!taskid.isEmpty()) {
			for (FlowDeliver node : flowNodes) {
				if (node.getDnodeid().equalsIgnoreCase(cNodeID)) {
					mav.addObject(
							"title",
							String.format("%s--%s", node.getRemark(),
									node.getText3()));
					break;
				}
			}
		}

		// 获取用户所拥有的资源列表
		// List<SystemResource> reslist =
		// systemResourceService.listByMenuid(user,
		// menuid);
		List<SystemResource> reslist = systemResourceService
				.getAllResourceByUser(user);
		// 获取当前节点所具有的所用流转按钮
		for (SystemResource res : reslist) {
			// 不是流程按钮，继续
			if (res.getRestypeid() != OAParameter.FLOWBUTTON)
				continue;
			// 是公用的流转按钮，
			//ispub = true;
			for (int i = 0; i < flowNodes.size(); i++) {
				if (res.getResid()
						.equalsIgnoreCase(flowNodes.get(i).getResid())) {
					// 不是公用的流转按钮，已经被流程的某个节点所占用
					//ispub = false;
					// 并且是当前节点的流转按钮
					if (flowNodes.get(i).getSnodeid().equalsIgnoreCase(cNodeID)) {
						// 打个补丁 2015-3-24
						Pattern pattern = Pattern.compile("\\'(.*?)\\'");// 获取单引号之间的内容
						Matcher matcher = pattern.matcher(res.getSrurl());
						// 替换掉第一个参数
						res.setSrurl(matcher.replaceFirst(String.format("'%s'",
								flowNodes.get(i).getExplain())));
						result += this.setBtnscript(res);
					}
				}
			}
			// if (ispub) {
			// 如果是公用的流转按钮，也打印出来
			// result += this.setBtnscript(res);
			// }
		}
		// return result;
		mav.addObject("topTools", result);
	}

	/**
	 * 流程实例--新增、查看、处理页面顶端按钮设置
	 * 
	 * @param request
	 * @param mav
	 * @param flowdefid
	 * @param menuid
	 * @param myjs1
	 * @param myjs2
	 * @param tempid
	 */
	private void toFlowInstanceNodeView(HttpServletRequest request,
			ModelAndView mav, String flowdefid, String menuid, String myjs1,
			String myjs2, String tempid) {
		String topstr = "";
		SystemUser user = ControllerBase.getLoginUser(request);
		// 资源对象
		List<SystemResource> reslist = systemResourceService.listByMenuid(user,
				menuid);
		if (reslist != null) {
			for (SystemResource res : reslist) {
				// 表单顶部按钮
				if (res.getRestypeid() == OAParameter.FORMTOPBUTTON) {
					topstr += this.setBtnscript(res);
				}
			}
		}

		String path = request.getContextPath();
		TbsysTemplate template = this.systemTemplateService.selectByTempid1(
				tempid, user.getDepartid());
		RequestUtil
				.setSessionAttribute(request, template.getTempid(), template);

		List<TemplateGridHeader> headers = JSON.parseArray(
				template.getGridheaders2(), TemplateGridHeader.class);
		mav.addObject("topTools", topstr);
		mav.addObject("titleList", headers);
		mav.addObject("currentUserid", user.getUserid());
		mav.addObject("path", path);
		mav.addObject("menuid", menuid);
		mav.addObject("flowdefid", flowdefid);
		mav.addObject("templetid", template.getTempid());
		mav.addObject("myjs1", myjs1);
		mav.addObject("myjs2", myjs2);
	}

	/**
	 * 生成页面按钮html脚本
	 * 
	 * @param res
	 * @return
	 */
	private String setBtnscript(SystemResource res) {
		String btnStr = "";
		String url = res.getSrurl();
		String name = res.getName();
		String resid = res.getResid();
		if (name.contains("_")) {
			name = name.split("_")[0];
		}
		String ico = "";
		if (res.getShowico() != null) {
			ico = res.getShowico();
			btnStr += "<a class=\"mini-button\" id=\"" + resid
					+ "\" iconCls=\"" + ico + "\" plain=\"true\" onclick=\""
					+ url + "\"> &nbsp;" + name + "</a> ";
		} else {
			btnStr += "<a class=\"mini-button\" id=\"" + resid
					+ "\" iconCls=\"" + ico + "\" plain=\"false\" onclick=\""
					+ url + "\">" + name + "</a> ";
		}
		return btnStr;
	}

	/**
	 * 获取页面aip操作按钮
	 * 
	 * @param request
	 * @param user
	 * @param menuid
	 * @return
	 */
	private String getAipTopBtns(HttpServletRequest request, SystemUser user,
			String menuid) {
		String topstr = "";
		// 资源对象
		List<SystemResource> reslist = systemResourceService.listByMenuid(user,
				menuid);
		if (reslist != null) {
			for (SystemResource res : reslist) {
				if (res.getRestypeid() == OAParameter.FORMAIPBUTTON) {
					topstr += this.setBtnscript(res);
				}
			}
		}
		return topstr;
	}

	/**
	 * 获取当前登录用户的角色信息
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getUserRoles(HttpServletRequest request, SystemUser user) {
		String roles = "";
		List<UserRoleWrapper> list = null;
		Object temp = RequestUtil.getSessionAttribute(request,
				"currentUserRolse");

		if (temp != null) {
			list = (List<UserRoleWrapper>) temp;
		} else {
			list = systemRoleService.getRolesOfUser(user.getUserid());
			RequestUtil.setSessionAttribute(request, "currentUserRolse", list);
		}
		for (int i = 0; i < list.size(); i++) {
			roles += String.format("@%s&,", list.get(i).getRoleid());
		}
		return roles;
	}

	@SuppressWarnings("unchecked")
	public void assembleFormData(Map<String, Object> paramMap, SystemUser user,
			ModelAndView mav) {
		// 查询表单数据
		Map<String, Object> resultMap = solutionService.query(paramMap, user);
		Map<String, Object> map = (Map<String, Object>) resultMap.get("root");
		if (null != map) {

			mav.addObject("formDatajson", new JSONObject(map).toString());
		}
		//
		Object _options = (Map<String, Object>) resultMap.get("options");
		if (null != _options && _options instanceof Map) {
			Map<String, Object> options = (Map<String, Object>) _options;
			options = MapUtil.convertKey2LowerCase(options);
			mav.addObject("selectDatajson", new JSONObject(options).toString());
		}
	}
}

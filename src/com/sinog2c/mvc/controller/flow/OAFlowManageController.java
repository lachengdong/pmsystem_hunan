package com.sinog2c.mvc.controller.flow;

import java.util.List;
import java.util.Map;

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
import com.gkzx.common.OAParameter;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.flow.TbflowDefine;
import com.sinog2c.model.flow.TbflowDefineWithBLOBs;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.LogManage;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.flow.FlowDeliverService;
import com.sinog2c.service.api.system.SystemCodeService;
import com.sinog2c.service.api.system.SystemModelService;
import com.sinog2c.service.api.system.SystemResourceService;
import com.sinog2c.service.api.system.SystemRoleService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.util.common.RequestUtil;

@Controller
@RequestMapping("/OAflow")
public class OAFlowManageController {

	@Autowired
	public SystemCodeService codeService;
	
	@Resource
	private FlowDeliverService flowDeliverService;
	@Resource
	private SystemResourceService systemResourceService;
	@Resource
	private SystemModelService systemModelService;
	@Resource
	private SystemRoleService systemRoleService;	
	@Resource
	private SystemTemplateService systemTemplateService;

	/**
	 * 获取流程定义信息列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFlowByDepartid")
	@ResponseBody
	public JSONMessage<TbflowDefine> getFlowByDepartid(
			HttpServletRequest request) {
		SystemUser user = ControllerBase.getLoginUser(request);
		String departid = user.getDepartid();
		Map<String, Object> map = RequestUtil
				.parseParamMapForPagination(request);
		map.put("departid", departid);
		JSONMessage<TbflowDefine> message = this.flowDeliverService
				.getFlowDeliversBydep(map);
		return message;
	}

	/**
	 * 进入流程定义设计页面
	 * 
	 * @param request
	 * @param flowdefid
	 * @param actionType
	 * @return
	 */
	@RequestMapping(value = "/toFlowDesign.page", method = RequestMethod.GET)
	public ModelAndView toFlowDesign(
			HttpServletRequest request,
			@RequestParam(value = "flowdefid", required = false) String flowdefid,
			@RequestParam(value = "actionType", required = true) String actionType) {
		ModelAndView mav = new ModelAndView("flow/flowDesign");
		String path = request.getContextPath();
		if (actionType.equalsIgnoreCase("new")) {
			int id = this.flowDeliverService.getNextId();
			flowdefid = "other_" + String.valueOf(id);
		} else if (actionType.equalsIgnoreCase("edit")) {
			TbflowDefineWithBLOBs flowdefine = this.flowDeliverService
					.selectByPrimaryKey(flowdefid);
			mav.addObject("flowdefine", flowdefine);
		}
		List<TbsysTemplate> list=systemModelService.listAllByType(OAParameter.OATEMPLATETYPE);	
		List<TbsysCode> list2=this.codeService.listByCodetype("flowType");
		
		String templates=JSON.toJSONString(list);
		mav.addObject("templates", templates);	
		mav.addObject("flowTypes", JSON.toJSONString(list2));
		mav.addObject("flowdefid", flowdefid);		
		mav.addObject("path", path);
		mav.addObject("actionType", actionType);		
		return mav;
	}

	/**
	 * 流程定义信息更新（新增或修改已有流程定义数据）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addOrUpdateFlow.action", method = RequestMethod.POST)
	@ResponseBody
	public int addOrUpdateFlow(HttpServletRequest request) {
		int count = -1;
		
		SystemUser user = ControllerBase.getLoginUser(request);
		TbflowDefineWithBLOBs tbdine = JSON
				.parseObject(request.getParameter("flowdefine"),
						TbflowDefineWithBLOBs.class);
		List<FlowDeliver> dlist = JSON.parseArray(
				request.getParameter("flowdeliver"), FlowDeliver.class);
		tbdine.setDepartid(user.getDepartid());
		tbdine.setOpid(user.getUserid());
		TbflowDefineWithBLOBs flowdefine = this.flowDeliverService
				.selectByPrimaryKey(tbdine.getFlowdefid());
		if (flowdefine == null) {
			count = this.flowDeliverService.addNewFlowDefine(tbdine, dlist);
			LogManage.getInstance().AddLog(tbdine, LogManage.getInstance().ADD,
					request);
		} else {
			count = this.flowDeliverService.updateFlowDefine(tbdine, dlist);
			LogManage.getInstance().AddLog(tbdine,
					LogManage.getInstance().UPDATE, request);
		}
		return count;
	}

	/**
	 * 根据流程定义id删除流程定义数据，包括流程定义的所用节点数据
	 * 
	 * @param request
	 * @param flowdefid
	 * @return
	 */
	@RequestMapping(value = "/removeflowbyid.action", method = RequestMethod.POST)
	@ResponseBody
	public int removeflowbyid(
			HttpServletRequest request,
			@RequestParam(value = "flowdefid", required = false) String flowdefid) {
		int count = -1;
		count = this.flowDeliverService.deleteFlowDefinebyID(flowdefid);
		TbflowDefine define = new TbflowDefine();
		define.setFlowdefid(flowdefid);
		try {
			LogManage.getInstance().AddLog(define,
					LogManage.getInstance().DELETE, request);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}
}

package com.sinog2c.mvc.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gkzx.common.OAParameter;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemRole;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.mvc.controller.LogManage;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.system.SystemLogService;
import com.sinog2c.service.api.system.SystemRoleService;
import com.sinog2c.service.api.system.SystemTemplateService;
import com.sinog2c.util.common.RequestUtil;


@Controller
@RequestMapping("/templet")
public class SystemTempletControler {

	@Resource
	private SystemTemplateService systemTemplateService;

	@Resource
	private SystemLogService logService;

	@Resource
	private SystemRoleService systemRoleService;

	/**
	 * 方法描述：获取数据列表
	 */
	@RequestMapping(value = "/getTemplateList")
	@ResponseBody
	public JSONMessage<TbsysTemplate> getTemplateList(HttpServletRequest request) {
		SystemUser user = ControllerBase.getLoginUser(request);
		String departid = user.getDepartid();
		Map<String, Object> map = RequestUtil
				.parseParamMapForPagination(request);
		map.put("departid", departid);
		// 查询出所有数据集合
		JSONMessage<TbsysTemplate> message = systemTemplateService
				.getTemplateList(map);
		return message;
	}

	/**
	 * 判断表单ID是否存在
	 * 
	 * @return
	 */
	@RequestMapping(value = "/isexisttempid.json")
	@ResponseBody
	public Object isExistTempid(HttpServletRequest request) {
		// 用户对象
		SystemUser user = ControllerBase.getLoginUser(request);
		// 参数
		int rows = 0;
		int resultval = -1;
		String departid = user.getDepartid();// 部门id
		String tempid = request.getParameter("tempid");// 模板id
		// String generaltype = request.getParameter("rbl");//通用类型
		try {
			if (tempid != null) {
				rows = systemTemplateService.validateTempidByDepartid(tempid,
						departid);
				if (rows > 0) {
					resultval = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultval;
	}

	/**
	 * 添加表单管理信息
	 * 
	 * @return
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addreportmanage.action")
	@ResponseBody
	public JSONMessage<?> Object(HttpServletRequest request) {
		// 用户对象
		SystemLog log = new SystemLog();
		SystemUser user = ControllerBase.getLoginUser(request);
		JSONMessage<?> message = JSONMessage.newMessage();
		TbsysTemplate systemTemplate = new TbsysTemplate();

		// 参数
		int rows = 0;
		String departid = "";
		Date now = new Date();
		String rbl = request.getParameter("rbl");// 通用类型
		String resid = request.getParameter("resid");// 按钮ID
		String data = request.getParameter("data");// json 数据
		String content = request.getParameter("content");// 模板大字段内容

		if (data != null) {
			ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(data,
					Object.class);

			if (list != null && list.size() > 0) {
				Map<?, ?> map = (Map<?, ?>) list.get(0);
				Short type = map.get("type") == null ? 0 : Short
						.valueOf((String) map.get("type"));// 模板类型

				if (OAParameter.THREE.equals(rbl)) {// 全国通用
					departid = OAParameter.ZERO;
				} else {
					departid = user.getDepartid();
				}
				systemTemplate.setTempid((String) map.get("tempid"));
				systemTemplate
						.setFunctionname((String) map.get("functionname"));
				systemTemplate.setTempname((String) map.get("tempname"));

				systemTemplate.setContent(content);
				systemTemplate.setOpid(user.getUserid());
				systemTemplate.setOptime(now);
				systemTemplate.setType(Short.valueOf(type));
				systemTemplate.setGeneraltype(rbl);
				systemTemplate.setDelflag(Short.valueOf(OAParameter.ZERO));
				systemTemplate.setDepartid(departid);
				rows = systemTemplateService.insert(systemTemplate);
				if (1 == rows) {
					message.setInfo("添加成功!");
					message.setSuccess();
				} else {
					message.setInfo("操作失败!");
				}
				try {
					// 日志
					log.setLogtype("表单信息新增操作");
					log.setOpaction("新增");
					log.setOpcontent("表单信息新增,resid=" + resid);
					log.setOrgid("templet");
					log.setRemark("添加成功!");
					log.setStatus((short) message.getStatus());
					logService.add(log, user);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return message;
	}

	/**
	 * 进入模板编辑页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toeditftpl.page")
	public ModelAndView editFormManage(HttpServletRequest request,
			HttpServletResponse response) {
		String returnval = "ftpl/editftpl";
		String path = request.getContextPath();

		ModelAndView mav = new ModelAndView(returnval);
		mav.addObject("departid", request.getParameter("departid"));
		mav.addObject("tempid", request.getParameter("tempid"));
		mav.addObject("path", path);

		return mav;
	}

	/**
	 * 进入表单管理编辑
	 */
	@RequestMapping(value = "/getTemplateInfo.action")
	@ResponseBody
	public TbsysTemplate getTemplateInfo(HttpServletRequest request,
			HttpServletResponse response) {
		// 用户对象
		TbsysTemplate systemTemplate = new TbsysTemplate();
		// 定义变量

		String tempid = request.getParameter("tempid");
		String departid = request.getParameter("departid");
		try {
			systemTemplate = systemTemplateService.selectByTempid1(tempid,
					departid);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return systemTemplate;
	}

	/**
	 * 修改表单模板信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updatsysTemplateInfo.action")
	@ResponseBody
	public Object updateReportManage(HttpServletRequest request) {
		// 用户对象
		SystemLog log = new SystemLog();
		SystemUser user = ControllerBase.getLoginUser(request);
		TbsysTemplate templet = new TbsysTemplate();
		JSONMessage<?> message = JSONMessage.newMessage();

		// 参数
		int rows = 0;
		String resid = request.getParameter("resid");
		String content = request.getParameter("content");
		String data = request.getParameter("data");// json 数据
		try {
			if (data != null) {
				ArrayList<Object> list = (ArrayList<Object>) JSON.parseArray(
						data, Object.class);
				if (list != null && list.size() > 0) {
					@SuppressWarnings("unchecked")
					Map<String, Object> map = (Map<String, Object>) list.get(0);
					String tempid = (String) map.get("tempid");
					String departid = request.getParameter("departid");
					String functionname = (String) map.get("functionname");
					String tempname = (String) map.get("tempname");
					// 查找对象
					templet = systemTemplateService.selectByTempid1(tempid,
							departid);
					templet.setContent(content);
					templet.setFunctionname(functionname);
					templet.setTempname(tempname);
					rows = systemTemplateService.update(templet);
				}
			}
			if (1 == rows) {
				message.setInfo("更新成功!");
				message.setSuccess();
			} else {
				message.setInfo("操作失败!");
			}
			// 日志
			log.setLogtype("表单信息更新操作");
			log.setOpaction("更新");
			log.setOpcontent("表单信息更新,resid=" + resid);
			log.setOrgid("templet");
			log.setRemark("更新成功!");
			log.setStatus((short) message.getStatus());
			logService.add(log, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	@RequestMapping(value = "/listAllByType.json")
	@ResponseBody
	public List<TbsysTemplate> listAllByType(@RequestParam("type") String type,
			HttpServletRequest request) {
		List<TbsysTemplate> list = null;
		try {
			list = systemTemplateService.listAllByType(type);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 此方法描述的是：修改表单数据
	 * 
	 * @param
	 * @return
	 * @Exception 异常对象
	 * @author: blue
	 * @version: 2015-3-15 上午09:59:33
	 */
	@RequestMapping(value = "/updateTemplate.action", method = RequestMethod.POST)
	@ResponseBody
	public int updateTemplate(HttpServletRequest request) {
		// 参数
		int rows = 0;
		TbsysTemplate data = JSON.parseObject(request.getParameter("data"),
				TbsysTemplate.class);

		String f1 = "select * from (select rownum rn,t.* from (select * from (Select %s,tbflow_execution.flowid,tbflow_execution.flowstate,tbflow_execution.flowtype,tbflow_execution.flowname,tbflow_execution.applyid,tbflow_task.startdate startdatetime,tbflow_task.state CNODESTATE,tbflow_task.note,tbflow_task.assigneertime applydatetime,tbflow_task.enddate optime,tbflow_task.cnode,tbflow_task.cnodename,tbflow_task.explain,tbflow_task.lnode,tbflow_task.lnodename,tbflow_task.lassigneername,tbflow_task.feedback,tbflow_task.response,tbflow_task.urgeint1,tbflow_task.taskid from tbflow_execution join tbflow_task on tbflow_execution.flowid=tbflow_task.flowid and assigneer='${userid}' where tbflow_execution.flowdefid='${flowdefid}' ) t1 where ${condition} order by ${orderby}) t where rownum<=${end}) a where a.rn>=${start}";
		String f2 = "select * from (select rownum rn,t.* from (Select %s,tbflow_execution.flowid,flowstate,optime from tbflow_execution where ${condition} order by ${orderby} ) t where rownum<=${end}) a where a.rn>=${start}";
		String f3="select count(*) from (Select %s,tbflow_execution.flowtype,tbflow_execution.flowid,tbflow_execution.flowstate,tbflow_execution.applyid,tbflow_task.state CNODESTATE, tbflow_task.assigneertime applydatetime,tbflow_task.startdate optime,tbflow_task.cnode,tbflow_task.cnodename,tbflow_task.explain,tbflow_task.lnode,tbflow_task.lnodename,tbflow_task.feedback,tbflow_task.response,tbflow_task.taskid from tbflow_execution join tbflow_task on tbflow_execution.flowid=tbflow_task.flowid and assigneer='${userid}' where tbflow_execution.flowdefid='${flowdefid}' ) t1 where ${condition}";
		
		//tbflow_execution.flowid,tbflow_execution.flowstate,tbflow_task.state CNODESTATE, tbflow_task.assigneertime applydatetime,tbflow_task.startdate optime,tbflow_task.cnode,tbflow_task.cnodename,tbflow_task.explain,tbflow_task.lnode,tbflow_task.lnodename,tbflow_task.feedback,tbflow_task.taskid	
		
		data.setMainsqltemp(String.format(f1, data.getKeyfieldsTemp()));
		data.setMainsqltemp2(String.format(f2, data.getKeyfieldsTemp()));
		data.setMaincountsql(String.format(f3, data.getKeyfieldsTemp()));
		rows = systemTemplateService.updateByPrimaryKeySelective(data);
		try {
			LogManage.getInstance().AddLog(data,
					LogManage.getInstance().UPDATE, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}

	/**
	 * 删除表单管理信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/removeTemplate.action", method = RequestMethod.POST)
	@ResponseBody
	public int removeReportManage(HttpServletRequest request,
			@RequestParam(value = "tempid", required = false) String tempid,
			@RequestParam(value = "departid", required = true) String departid) {

		// 此操作不能包在try块中，否则异常前台ajax捕获不了
		int rows = systemTemplateService.deleteByTempid(tempid, departid);

		try {
			TbsysTemplate data = new TbsysTemplate();
			data.setTempid(tempid);
			LogManage.getInstance().AddLog(data,
					LogManage.getInstance().DELETE, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}

	@RequestMapping(value = "/toftplGridHeadset.page", method = RequestMethod.GET)
	public ModelAndView toftplGridHeadset(HttpServletRequest request,
			@RequestParam(value = "tempid", required = false) String tempid,
			@RequestParam(value = "departid", required = true) String departid

	) {
		ModelAndView mav = new ModelAndView("ftpl/ftpl_gridHeadSet");
		TbsysTemplate systemTemplate = systemTemplateService.selectByTempid1(
				tempid, departid);
		JSONArray obj = JSON.parseArray(systemTemplate.getGridheaders1());
		mav.addObject("fields", obj);
		String path = request.getContextPath();
		mav.addObject("path", path);
		return mav;
	}

	@RequestMapping(value = "/toftplcolRoleSet.page", method = RequestMethod.GET)
	public ModelAndView toftplcolRoleSet(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("ftpl/ftpl_colRoleSet");
		List<SystemRole> list = systemRoleService.selectAll();
		String roles = "[";
		for (int i = 0; i < list.size(); i++) {
			roles += String.format("{\"id\":\"%s\",\"text\":\"%s\"},", list
					.get(i).getRoleid().toString(), list.get(i).getName());
		}
		roles = roles.substring(0, roles.length() - 1) + "]";
		String path = request.getContextPath();
		mav.addObject("roles", roles);
		mav.addObject("path", path);
		return mav;
	}

}

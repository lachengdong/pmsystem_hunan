package com.sinog2c.service.impl.flow;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;
import com.gkzx.common.OAParameter;
import com.sinog2c.config.SpringContextHolder;
import com.sinog2c.dao.api.flow.FlowBaseDocMapper;
import com.sinog2c.dao.api.flow.FlowDeliverMapper;
import com.sinog2c.dao.api.flow.TbflowInstanceMapper;
import com.sinog2c.dao.api.flow.TbflowInstanceTaskMapper;
import com.sinog2c.dao.api.officeAssistant.TbuserNoticeMapper;
import com.sinog2c.dao.api.officeAssistant.TbuserUserNoticeMapper;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.flow.TbflowInstance;
import com.sinog2c.model.flow.TbflowInstanceDoc;
import com.sinog2c.model.flow.TbflowInstanceTask;
import com.sinog2c.model.officeAssistant.TbuserNotice;
import com.sinog2c.model.officeAssistant.TbuserUserNotice;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.errorhandler.OAException;
import com.sinog2c.service.api.flow.FlowInstanceExecutionService;
import com.sinog2c.service.api.officeAssistant.UserNoticeService;

@Service("flowInstanceExecutionService")
public class Flow_commonInstanceExcutionImpl implements
		FlowInstanceExecutionService {

	@Autowired
	private TbflowInstanceTaskMapper tbflowInstanceTaskMapper;
	@Autowired
	private TbflowInstanceMapper tbflowInstanceMapper;
	@Autowired
	private FlowBaseDocMapper tbflowInstanceDocMapper;
	@Autowired
	private FlowDeliverMapper tbflow_deliverMapper;
	@Autowired
	private UserNoticeService userNoticeService;
	@Autowired
	private TbuserUserNoticeMapper tbuserUserNoticeMapper;
	@Autowired
	private TbuserNoticeMapper TbuserNoticeMapper;

	@Override
	public <T> int creatFlowInstanceDraft(T instance1, SystemUser user) {
		TbflowInstance instance = (TbflowInstance) instance1;
		int row = -1;
		if (tbflowInstanceMapper.selectByPrimaryKey(instance.getFlowid()) == null) {
			// 获取流程起始节点的信息
			FlowDeliver nNode = tbflow_deliverMapper
					.getchildNodebyDeliverExplain(instance.getFlowdefid(),
							OAParameter.STARTNODE, "");
			// 配置流程实例信息（包括下一个任务的节点名称、节点要多少人完成等信息）
			instance = Flow_commonInstanceExcutionImpl.createFlowInstance(
					instance, nNode, user);
			instance.setCnodestate(OAParameter.STATE_Draft);

			// 创建任务日志
			TbflowInstanceTask task = Flow_commonInstanceExcutionImpl
					.createTaskForLaunch(instance, user, nNode);
			// 保存任务日志数据
			row = tbflowInstanceTaskMapper.insertSelective(task);
			TbflowInstanceDoc doc = Flow_commonInstanceExcutionImpl
					.createFlowInstanceDoc(instance);
			// 保存流程实例表单信息
			row += tbflowInstanceDocMapper.insertSelective(doc);
			// 保存流程实例信息
			row += tbflowInstanceMapper.insertSelective(instance);
		} else {
			// 更新流程实例文档信息
			TbflowInstanceDoc doc = Flow_commonInstanceExcutionImpl
					.createFlowInstanceDoc(instance);
			doc.setBaseid(instance.getFlowdocid());
			row = tbflowInstanceDocMapper.updateByPrimaryKeySelective(doc);
			row += tbflowInstanceMapper.updateByPrimaryKeySelective(instance);
		}
		return row;
	}

	@Override
	public <T> int creatFlowInstance(T instance1, SystemUser user) {
		TbflowInstance instance = (TbflowInstance) instance1;
		// 流程实例已经创建
		if (tbflowInstanceMapper.selectByPrimaryKey(instance.getFlowid()) != null)
			return 1;
		this.ExceuteExtendMethod(instance);
		int result = 0;

		FlowDeliver lNode = tbflow_deliverMapper.getchildNodebyDeliverExplain(
				instance.getFlowdefid(), OAParameter.STARTNODE, "");

		// 获取起始节点的下下一节点也就是流程中的第三层
		FlowDeliver nNode = tbflow_deliverMapper.getflowGrandNodebyidexplain(
				instance.getFlowdefid(), OAParameter.STARTNODE,
				instance.getExplain());
		// 初始化流程实例数据
		instance = Flow_commonInstanceExcutionImpl.createFlowInstance(instance,
				nNode, user);
		// 补起始节点发起流程的任务日志
		TbflowInstanceTask task = Flow_commonInstanceExcutionImpl
				.createTaskForLaunch(instance, user, lNode);
		task.setFeedback(nNode.getExplain());
		task.setEnddate(new Date());
		task.setState(OAParameter.STATE_FINISHED);//
		result = tbflowInstanceTaskMapper.insertSelective(task);

		// 生成下一节点的任务执行者任务列表
		List<TbflowInstanceTask> taskList;
		if (instance.getAssigners() != null
				&& instance.getAssigners().size() > 0) {
			taskList = Flow_commonInstanceExcutionImpl.CreateTasks(nNode,
					instance, user, lNode.getDnodeid(), lNode.getText3());
		} else {
			taskList = Flow_commonInstanceExcutionImpl.CreateTasks(nNode,
					instance.getFlowid(), lNode.getDnodeid(), lNode.getText3(),
					user, instance.getFlowtype());
		}

		// 根据用户提交生成流程文档信息
		TbflowInstanceDoc doc = Flow_commonInstanceExcutionImpl
				.createFlowInstanceDoc(instance);
		// 保存流程文档信息
		result = tbflowInstanceDocMapper.insertSelective(doc);
		// 保存流程实例信息
		result += tbflowInstanceMapper.insertSelective(instance);
		// 给下一个节点任务的所用任务执行者发任务
		result = this.addUserTask(taskList, instance);
		return result;
	}

	@Override
	public <T> int executeTask(T instance1, SystemUser user) {
		TbflowInstance instance = (TbflowInstance) instance1;
		this.ExceuteExtendMethod(instance);
		int result = 0;
		// 流程下一节点的信息
		FlowDeliver nNode = null;
		// 获取当前流程实例的信息
		TbflowInstance cInstance = tbflowInstanceMapper
				.selectByPrimaryKey(instance.getFlowid());
		// 获取当前实例下当前用户的当前任务
		TbflowInstanceTask cTask = tbflowInstanceTaskMapper
				.selectByPrimaryKey(instance.getTaskid());

		// 更新流程实例的用户提交的表单aip内容信息（注意文件内容是否在用户提交前被其他用户变动，用锁还是不用锁）
		// TODO:需修改
		TbflowInstanceDoc doc = Flow_commonInstanceExcutionImpl
				.createFlowInstanceDoc(instance);
		doc.setBaseid(instance.getFlowdocid());
		result = tbflowInstanceDocMapper.updateByPrimaryKeySelective(doc);

		// 更改当前用户的当前节点的任务信息
		TbflowInstanceTask task = new TbflowInstanceTask();
		task.setTaskid(cTask.getTaskid());
		task.setEnddate(new Date());
		task.setNote(instance.getExplain()); // 本人所做的操作描述
		task.setState(OAParameter.STATE_FINISHED); // 自己的任务已经完成
		result += tbflowInstanceTaskMapper // 更新当前任务
				.updateByPrimaryKeySelective(task);

		// 并根据流程实例的完成情况信息，判断流程的流向，决定是否给下一个节点发任务信息 如果不需要 则 更新流程实例总状态信息（要
		// 锁记录）， 后退出
		int finished = cInstance.getInt3(); // 当前节点任务已经完成的人数
		int need = cInstance.getInt2(); // 当前节点任务需要完成的人数

		// 当前任务节点的 其他分配人还未完成
		if ((finished + 1) < need) {
			// 当前节点任务其他人还未完成，只更新流程实例数据
			instance.setInt3(finished + 1);
			// 更新流程实例数据
			return result += this.tbflowInstanceMapper
					.updateByPrimaryKeySelective(instance);
		}

		// 当前节点任务完成
		// 获取流程下一个节点的信息
		nNode = tbflow_deliverMapper.getchildNodebyDeliverExplain(
				instance.getFlowdefid(), cTask.getCnode(),
				instance.getExplain());
		// 设置流程实例中上一个节点中的所用任务状态信息
		TbflowInstanceTask ltask = new TbflowInstanceTask();
		ltask.setFlowid(cTask.getFlowid());
		ltask.setCnode(cTask.getLnode());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ltask.setText1(sdf.format(new Date())); // 回复时间
		ltask.setFeedback(instance.getExplain()); // 页面给出的流转操作描述,反馈信息 ///TODO
		ltask.setResponse(OAParameter.FLOWRESPOSEYES);

		// 整个流程实例结束
		if (nNode.getDnodeid().startsWith("end")
				|| nNode.getDnodeid().equals("1")
				|| nNode.getDnodeid().equals("-1")) {
			instance.setFlowstate(OAParameter.STATE_FINISHED);
			instance.setCnodestate(OAParameter.STATE_FINISHED);
			instance.setCnode(null);
			// 更前新流程实例中上一个节点中的所用任务状态信息
			result += this.tbflowInstanceTaskMapper
					.updateByflownodeuserSelective(ltask);
			// 更新流程实例数据
			return result += this.tbflowInstanceMapper
					.updateByPrimaryKeySelective(instance);
		}

		// 流程未结束，当前节点任务结束，进入下一个节点
		// 创建流程实例更新数据
		if (instance.getAssigners() != null
				&& instance.getAssigners().size() > 0) {
			instance = this.createFlowInstanceforUpdate2(instance, nNode);
		} else {
			instance = this.createFlowInstanceforUpdate(instance, nNode);
		}

		// 更新流程实例数据
		result += this.tbflowInstanceMapper
				.updateByPrimaryKeySelective(instance);

		instance.setDepartid(cInstance.getDepartid());
		if (nNode.getDnodeid().equalsIgnoreCase(cTask.getLnode())) {
			if (instance.getAssigners() != null
					&& instance.getAssigners().size() > 0) {
				List<TbflowInstanceTask> tasktempList = new ArrayList<TbflowInstanceTask>();
				for (SystemUser assigner : instance.getAssigners()) {
					TbflowInstanceTask tasktemp = new TbflowInstanceTask();
					tasktemp.setFlowid(instance.getFlowid());
					tasktemp.setFlowdefid(instance.getFlowdefid());
					tasktemp.setAssigneer(assigner.getUserid());
					tasktemp.setAssigneername(assigner.getName());
					tasktemp.setAssigneertime(new Date());
					tasktemp.setExplain(nNode.getExplain());
					// 任务在流程实例中所处的节点id
					tasktemp.setCnode(nNode.getDnodeid());
					tasktemp.setCnodename(nNode.getText3());
					tasktemp.setLassigneer(user.getUserid());
					tasktemp.setLassigneername(user.getName());
					tasktemp.setLnode(cTask.getCnode());
					tasktemp.setLnodename(cTask.getCnodename());
					// 设置任务类型，（对应流程定义的类型）
					tasktemp.setText1(instance.getFlowtype());
					tasktempList.add(tasktemp);
				}
				return this.addUserTask(tasktempList, instance);
			} else {
				// 如果下一个节点任务跟当前任务的父节点相同说明当前操作为回退操作
				// 更前新流程实例中上一个节点中的所用任务状态信息 （被回退）
				ltask.setResponse(OAParameter.FLOWRESPOSERETURN);
				result += tbflowInstanceTaskMapper
						.updateByflownodeuserSelective(ltask);
				// 获取流程实例中当前节点的父节点任务（对已经完成的任务，准备发回退任务）
				List<TbflowInstanceTask> taskListhis = tbflowInstanceTaskMapper
						.flowtaskisrollback(cTask.getFlowid(),
								nNode.getDnodeid(), OAParameter.STATE_FINISHED);
				// 更新被回退节点任务的状态信息，重新打开他
				for (int i = 0; i < taskListhis.size(); i++) {
					taskListhis.get(i).setState(OAParameter.STATE_RETURN); // 被回退的任务
					taskListhis.get(i).setExplain(instance.getExplain());
					taskListhis.get(i).setAssigneertime(new Date());
					taskListhis.get(i).setResponse("0");
					taskListhis.get(i).setFeedback("");
					taskListhis.get(i).setNote("");					
				}
				return this.addUserTask(taskListhis, instance);
			}
		} else { // 正常流转顺序，进入下层节点任务
			// 更新上个节点
			result += tbflowInstanceTaskMapper
					.updateByflownodeuserSelective(ltask);
			// 生成下一节点的任务执行者任务列表
			List<TbflowInstanceTask> taskList;
			if (instance.getAssigners() != null
					&& instance.getAssigners().size() > 0) {
				taskList = Flow_commonInstanceExcutionImpl.CreateTasks(nNode,
						instance, user, cTask.getCnode(), cTask.getCnodename());
			} else {
				taskList = Flow_commonInstanceExcutionImpl
						.CreateTasks(nNode, cTask.getCnode(),
								cTask.getCnodename(), cInstance, user);
			}
			// 给下一个节点任务的所用任务执行者发任务
			result = this.addUserTask(taskList, instance);
		}
		return result;
	}

	@Override
	public <T> boolean canExecuteTask(T instance, SystemUser user) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 根据流程节点的配置信息生成任务流程实例信息
	 * 
	 * @param instance
	 * @param nNode
	 * @param user
	 * @return
	 */
	public static TbflowInstance createFlowInstance(TbflowInstance instance,
			FlowDeliver nNode, SystemUser user) {

		if (instance.getAssigners() != null
				&& instance.getAssigners().size() > 0) {
			return Flow_commonInstanceExcutionImpl.createFlowInstance2(
					instance, nNode, user);
		}

		instance.setApplyid(user.getUserid());
		instance.setApplyname(user.getName());
		instance.setDepartid(user.getDepartid());
		instance.setOpid(user.getUserid());
		instance.setOpname(user.getName());
		instance.setCnode(nNode.getDnodeid());
		// TODO:修修改
		instance.setConent(String.format("%s--%s", nNode.getRemark(),
				nNode.getText3()));
		instance.setCnodename(nNode.getText3());
		instance.setFlowname(nNode.getRemark());

		// 流程发起节点
		if (nNode.getSnodeid().equalsIgnoreCase(OAParameter.STARTNODE)) {
			instance.setInt1(1);
			instance.setInt2(1);
		} else {
			// 当前节点的执行人数
			int original = Integer.parseInt(nNode.getText2());
			int need = 0;

			// 实际的发送任务执行人数
			instance.setInt1(original);
			String strategy = nNode.getStrategy();

			// 任务完成策略:'1E':分配的任务的每个人都被须完成;'1D':分配到任务的1个人完成即可;'1C':分配到任务的1%的人完成即可
			if (strategy.equalsIgnoreCase("1e")) {
				// 需要完成的人数
				instance.setInt2(original);
			} else if (strategy.endsWith("D")) {
				instance.setInt2(Integer.parseInt(strategy.substring(0,
						strategy.length() - 1)));
			} else if (strategy.endsWith("C")) {
				double percent = Integer.parseInt(strategy.substring(0,
						strategy.length() - 1));
				need = (int) (original * percent / 100);
				instance.setInt2(need);
			}
		}
		return instance;
	}

	public static TbflowInstance createFlowInstance2(TbflowInstance instance,
			FlowDeliver nNode, SystemUser user) {
		// 实例申请者
		instance.setApplyid(user.getUserid());
		instance.setApplyname(user.getName());
		instance.setDepartid(user.getDepartid());
		instance.setOpid(user.getUserid());
		instance.setOpname(user.getName());
		instance.setCnode(nNode.getDnodeid());
		instance.setConent(String.format("%s--%s", nNode.getRemark(),
				nNode.getText3()));
		instance.setCnodename(nNode.getText3());
		instance.setFlowname(nNode.getRemark());
		// 流程发起节点
		if (nNode.getSnodeid().equalsIgnoreCase(OAParameter.STARTNODE)) {
			instance.setInt1(1);
			instance.setInt2(1);
		} else {
			// 当前节点的执行人数
			int original = instance.getAssigners().size();
			int need = 0;
			// 实际的发送任务执行人数
			instance.setInt1(original);
			String strategy = nNode.getStrategy();
			// 任务完成策略:'1E':分配的任务的每个人都被须完成;'1D':分配到任务的1个人完成即可;'1C':分配到任务的1%的人完成即可
			if (strategy.equalsIgnoreCase("1e")) {
				// 需要完成的人数
				need = original;
			} else if (strategy.endsWith("D")) {
				need = Integer.parseInt(strategy.substring(0,
						strategy.length() - 1));
			} else if (strategy.endsWith("C")) {
				double percent = Integer.parseInt(strategy.substring(0,
						strategy.length() - 1));
				need = (int) (original * percent / 100);
			}
			if (need < 1)
				need = 1;
			if (need > original)
				need = original;
			instance.setInt2(need);
		}
		return instance;
	}

	private void ExceuteExtendMethod(TbflowInstance instance) {
		if (!instance.getClassName().isEmpty()) {
			try {
				Class<?> cls0 = Class.forName(instance.getClasstype());
				Object obj = SpringContextHolder.getBean(
						instance.getClassName(), cls0);
				Method method = null;
				Method[] methods = cls0.getDeclaredMethods();
				for (Method tmpMethod : methods) {
					if (tmpMethod.getName().equalsIgnoreCase(
							instance.getMethodName())) {
						method = tmpMethod;
						break;
					}
				}
				// Method method=cls0.getMethod(instance.getMethodName());
				Class<?>[] paratypes = method.getParameterTypes();
				if (paratypes.length > 0) {
					Object paramObj = JSON.parse(instance.getJsonParameter());
					Object o = TypeUtils.castToJavaBean(paramObj, paratypes[0]);
					method.invoke(obj, o);
				} else {
					method.invoke(obj);
				}

			} catch (Exception e) {
				throw new OAException(String.format(
						"excute flow:%s fail,error:%s", instance.getFlowid(),
						e.getMessage()));
			}
		}

	}

	/**
	 * 生成流转实例更新数据
	 * 
	 * @param instance
	 * @param nNode
	 * @return
	 */
	private TbflowInstance createFlowInstanceforUpdate(TbflowInstance instance,
			FlowDeliver nNode) {

		instance.setCnode(nNode.getDnodeid());
		instance.setConent(String.format("%s--%s", nNode.getRemark(),
				nNode.getText3()));
		// 流程实例的当前所处节点的描述信息
		instance.setCnodename(nNode.getText3());
		instance.setFlowname(nNode.getRemark());

		// 获取流程起始节点
		FlowDeliver lNode = tbflow_deliverMapper.getchildNodebyDeliverExplain(
				instance.getFlowdefid(), OAParameter.STARTNODE, "");

		// 流程发起节点(回退操作导致回退到了起始节点)
		if (nNode.getDnodeid().equalsIgnoreCase(lNode.getDnodeid())) {
			instance.setInt1(1);
			instance.setInt2(1);
			instance.setInt3(0);
		} else {
			// 当前节点的执行人数
			int original = Integer.parseInt(nNode.getText2());
			int need = 0;
			// 实际的发送任务执行人数
			instance.setInt1(original);
			instance.setInt3(0);
			String strategy = nNode.getStrategy();

			// 任务完成策略:'1E':分配的任务的每个人都被须完成;'1D':分配到任务的1个人完成即可;'1C':分配到任务的1%的人完成即可
			if (strategy.equalsIgnoreCase("1e")) {
				// 需要完成的人数
				instance.setInt2(original);
			} else if (strategy.endsWith("D")) {
				instance.setInt2(Integer.parseInt(strategy.substring(0,
						strategy.length() - 1)));
			} else if (strategy.endsWith("C")) {
				double percent = Integer.parseInt(strategy.substring(0,
						strategy.length() - 1));
				need = (int) (original * percent / 100);
				instance.setInt2(need);
			}
		}
		return instance;
	}

	private TbflowInstance createFlowInstanceforUpdate2(
			TbflowInstance instance, FlowDeliver nNode) {
		instance.setCnode(nNode.getDnodeid());
		instance.setConent(String.format("%s--%s", nNode.getRemark(),
				nNode.getText3()));
		// 流程实例的当前所处节点的描述信息
		instance.setCnodename(nNode.getText3());
		instance.setFlowname(nNode.getRemark());

		// 获取流程起始节点
		FlowDeliver sNode = tbflow_deliverMapper.getchildNodebyDeliverExplain(
				instance.getFlowdefid(), OAParameter.STARTNODE, "");

		// 流程发起节点(回退操作导致回退到了起始节点)
		if (nNode.getDnodeid().equalsIgnoreCase(sNode.getDnodeid())) {
			instance.setInt1(1);
			instance.setInt2(1);
			instance.setInt3(0);
		} else {
			// 当前节点的执行人数
			int original = instance.getAssigners().size();
			int need = 0;
			// 实际的发送任务执行人数
			instance.setInt1(original);
			instance.setInt3(0);
			String strategy = nNode.getStrategy();

			// 任务完成策略:'1E':分配的任务的每个人都被须完成;'1D':分配到任务的1个人完成即可;'1C':分配到任务的1%的人完成即可
			if (strategy.equalsIgnoreCase("1e")) {
				// 需要完成的人数
				need = original;
			} else if (strategy.endsWith("D")) {
				need = Integer.parseInt(strategy.substring(0,
						strategy.length() - 1));
			} else if (strategy.endsWith("C")) {
				double percent = Integer.parseInt(strategy.substring(0,
						strategy.length() - 1));
				need = (int) (original * percent / 100);
			}
			if (need < 1)
				need = 1;
			if (need > original)
				need = original;
			instance.setInt2(need);
		}
		return instance;
	}

	/**
	 * 创建流程文档信息
	 * 
	 * @param instance
	 * @return
	 */
	public static TbflowInstanceDoc createFlowInstanceDoc(
			TbflowInstance instance) {
		TbflowInstanceDoc doc = new TbflowInstanceDoc();
		doc.setDoccontent(instance.getDoccontent());
		doc.setFlowdefid(instance.getFlowdefid());
		doc.setFlowdescription(instance.getConent());
		doc.setFlowid(instance.getFlowid());
		doc.setOpid(instance.getOpid());
		doc.setTempletid(instance.getTempletid());
		// /TODO:创建生成GUID（每次更新都更新它，客户端根据他的变动情况判断文档是否被改动，暂时用此方法，以后改为根据文档内容生成hash序列来判断文件的变动情况
		doc.setText6(java.util.UUID.randomUUID().toString().replace("-", ""));
		return doc;
	}

	/**
	 * 创建节点任务列表
	 * 
	 * @param nNode
	 * @param flowid
	 * @param lNodeId
	 * @param lNodeName
	 * @param user
	 * @param flowType
	 * @return
	 */
	public static List<TbflowInstanceTask> CreateTasks(FlowDeliver nNode,
			String flowid, String lNodeId, String lNodeName, SystemUser user,
			String flowType) {
		List<TbflowInstanceTask> list = new ArrayList<TbflowInstanceTask>();
		String[] users = nNode.getAssigneer().split(";");
		String[] usersName = nNode.getText1().split(";");
		for (int i = 0; i < users.length; i++)//
		{
			if (users[i].isEmpty())
				continue;
			TbflowInstanceTask task = new TbflowInstanceTask();
			task.setAssigneer(users[i]);
			task.setAssigneername(usersName[i]);
			task.setFlowdefid(nNode.getFlowdefid());
			task.setFlowid(flowid);
			task.setLassigneer(user.getUserid());
			task.setLassigneername(user.getName());
			// 任务当前节点
			task.setCnode(nNode.getDnodeid());
			task.setCnodename(nNode.getText3());
			task.setExplain(nNode.getExplain());
			task.setState(OAParameter.STATE_START);
			task.setLnode(lNodeId);
			task.setLnodename(lNodeName);
			// 任务类型，（所对应的流程定义的类型）
			task.setText1(flowType);
			list.add(task);
		}
		return list;
	}

	/**
	 * 创建节点任务列表
	 * 
	 * @param nNode
	 * @param lNodeId
	 * @param lNodeName
	 * @param instance
	 * @param user
	 * @return
	 */
	public static List<TbflowInstanceTask> CreateTasks(FlowDeliver nNode,
			String lNodeId, String lNodeName, TbflowInstance instance,
			SystemUser user) {
		List<TbflowInstanceTask> list = new ArrayList<TbflowInstanceTask>();
		// 节点任务执行者为null，则由流程实例申请人来执行当前节点任务
		if (nNode.getAssigneer() == null || nNode.getAssigneer().isEmpty()) {
			TbflowInstanceTask task = new TbflowInstanceTask();
			task.setAssigneer(instance.getApplyid());
			task.setAssigneername(instance.getApplyname());
			task.setFlowdefid(nNode.getFlowdefid());
			task.setFlowid(instance.getFlowid());
			task.setLassigneer(user.getUserid());
			task.setLassigneername(user.getName());
			// 任务当前节点
			task.setCnode(nNode.getDnodeid());
			task.setCnodename(nNode.getText3());
			task.setExplain(nNode.getExplain());
			task.setState(OAParameter.STATE_START);
			task.setLnode(lNodeId);
			task.setLnodename(lNodeName);
			// 任务类型，（所对应的流程定义的类型）
			task.setText1(instance.getFlowtype());
			list.add(task);
			return list;
		}
		String[] users = nNode.getAssigneer().split(";");
		String[] usersName = nNode.getText1().split(";");
		for (int i = 0; i < users.length; i++)//
		{
			if (users[i].isEmpty())
				continue;
			TbflowInstanceTask task = new TbflowInstanceTask();
			task.setAssigneer(users[i]);
			task.setAssigneername(usersName[i]);
			task.setFlowdefid(nNode.getFlowdefid());
			task.setFlowid(instance.getFlowid());
			task.setLassigneer(user.getUserid());
			task.setLassigneername(user.getName());
			// 任务当前节点
			task.setCnode(nNode.getDnodeid());
			task.setCnodename(nNode.getText3());
			task.setExplain(nNode.getExplain());
			task.setState(OAParameter.STATE_START);
			task.setLnode(lNodeId);
			task.setLnodename(lNodeName);
			// 任务类型，（所对应的流程定义的类型）
			task.setText1(instance.getFlowtype());
			list.add(task);
		}
		return list;
	}

	/**
	 * 创建任务列表 --用户手动指定任务执行者
	 * 
	 * @param nNode
	 * @param instance
	 * @param user
	 * @param lnodeId
	 * @param lnodeName
	 * @return
	 */
	public static List<TbflowInstanceTask> CreateTasks(FlowDeliver nNode,
			TbflowInstance instance, SystemUser user, String lnodeId,
			String lnodeName) {
		List<TbflowInstanceTask> list = new ArrayList<TbflowInstanceTask>();
		for (SystemUser assigner : instance.getAssigners()) {
			TbflowInstanceTask task = new TbflowInstanceTask();
			task.setAssigneer(assigner.getUserid());
			task.setAssigneername(assigner.getName());
			task.setFlowdefid(nNode.getFlowdefid());
			task.setFlowid(instance.getFlowid());
			task.setLassigneer(user.getUserid());
			task.setLassigneername(user.getName());
			// 任务当前节点
			task.setCnode(nNode.getDnodeid());
			task.setCnodename(nNode.getText3());
			task.setExplain(nNode.getExplain());
			task.setState(OAParameter.STATE_START);
			task.setLnode(lnodeId);
			task.setLnodename(lnodeName);
			// 任务类型，（所对应的流程定义的类型）
			task.setText1(instance.getFlowtype());
			list.add(task);
		}
		return list;
	}

	/**
	 * 
	 * @param instance
	 * @param user
	 * @param nNode
	 * @return
	 */
	public static TbflowInstanceTask createTaskForLaunch(
			TbflowInstance instance, SystemUser user, FlowDeliver nNode) {
		TbflowInstanceTask task = new TbflowInstanceTask();
		task.setFlowid(instance.getFlowid());
		task.setFlowdefid(instance.getFlowdefid());
		task.setAssigneer(user.getUserid());
		task.setAssigneername(user.getName());
		task.setExplain(nNode.getExplain());
		task.setState(OAParameter.STATE_Draft);
		// 任务在流程实例中所处的节点id
		task.setCnode(nNode.getDnodeid());
		task.setCnodename(nNode.getText3());
		task.setStartdate(new Date());
		// 任务类型，（所对应的流程定义的类型）
		task.setText1(instance.getFlowtype());

		return task;
	}

	public int addUserTask(List<TbflowInstanceTask> taskList,
			TbflowInstance instance) {
		int result = 0;
		int noticeid = 0;
		String content = String
				.format("<a href=\"javascript:dowork('{path}/deliver/toflowDeliver_main.page?action=edit&menuid=%s&flowdefid=%s&templetid=%s&type=%s&flowid=%s&taskid={taskid}&attachjs=%s');\">您有待办事务：%s</a>",
						instance.getMenuid(), instance.getFlowdefid(),
						instance.getTempletid(), instance.getFlowtype(),
						instance.getFlowid(), instance.getAttachjs(),
						instance.getConent());

		if (instance.getNeedNotice() == 1) {
			TbuserNotice notice = new TbuserNotice();
			noticeid = TbuserNoticeMapper.selectNoticeid();
			notice.setNoticeid(noticeid);
			notice.setText1(instance.getFlowname());
			notice.setMessagetype(3);
			notice.setTitle("待办事务处理");
			notice.setContent(content);
			notice.setUsername(instance.getDepartid());
			notice.setEndtime(new Date());
			notice.setStarttime(new Date());
			notice.setState(0);
			notice.setOptime(new Date());
			notice.setOpid("sysauto");
			userNoticeService.insertDataToUserNotice(notice);
		}
		for (TbflowInstanceTask task : taskList) {
			String taskid = String.valueOf(this.tbflowInstanceTaskMapper
					.getNextId());
			task.setTaskid(taskid);
			result += this.tbflowInstanceTaskMapper.insertSelective2(task);
			if (instance.getNeedNotice() == 1) {
				TbuserUserNotice usernotice = new TbuserUserNotice();
				usernotice.setNoticeid(noticeid);// 通知id
				usernotice.setOpid("sysauto");
				usernotice.setOptime(new Date());
				usernotice.setUserid(task.getAssigneer());
				usernotice.setTaskid(taskid);
				tbuserUserNoticeMapper.insertSelective(usernotice);
			}
		}
		return result;
	}

}

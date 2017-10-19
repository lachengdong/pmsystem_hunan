package com.sinog2c.service.impl.flow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gkzx.common.OAParameter;
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
import com.sinog2c.service.api.flow.FlowInstanceExecutionService;
import com.sinog2c.service.api.officeAssistant.UserNoticeService;

/**
 * 公文流转处理
 * 
 * @author blue
 * 
 */
@Service("flow_docInstanceExcutionService")
public class Flow_docInstanceExcutionImpl implements
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
	private FlowInstanceExecutionService commonflowInsanceExecution;
	@Autowired
	private UserNoticeService userNoticeService;
	@Autowired
	private TbuserUserNoticeMapper tbuserUserNoticeMapper;
	@Autowired
	private TbuserNoticeMapper TbuserNoticeMapper;

	private TbflowInstanceTask currentTask;

	@Override
	public <T> int creatFlowInstanceDraft(T instance, SystemUser user) {
		return this.commonflowInsanceExecution.creatFlowInstanceDraft(instance,
				user);
	}

	@Override
	public <T> int creatFlowInstance(T instance1, SystemUser user) {
		TbflowInstance instance = (TbflowInstance) instance1;
		// 流程实例已经创建
		if (tbflowInstanceMapper.selectByPrimaryKey(instance.getFlowid()) != null)
			return 1;
		int result = 0;
		// 流程起始节点的信息
		FlowDeliver lNode = tbflow_deliverMapper.getchildNodebyDeliverExplain(
				instance.getFlowdefid(), OAParameter.STARTNODE, "");
		// 获取起始节点的下下一节点
		FlowDeliver nNode = tbflow_deliverMapper.getflowGrandNodebyidexplain(
				instance.getFlowdefid(), OAParameter.STARTNODE,
				instance.getExplain());
		// 初始化流程实例数据
		instance = Flow_commonInstanceExcutionImpl.createFlowInstance2(
				instance, nNode, user);
		// 补起始节点发起流程的任务日志
		TbflowInstanceTask task = Flow_commonInstanceExcutionImpl
				.createTaskForLaunch(instance, user, lNode);
		task.setFeedback(nNode.getExplain());
		task.setEnddate(new Date());
		task.setState(OAParameter.STATE_FINISHED);//
		result = tbflowInstanceTaskMapper.insertSelective(task);

		// 生成下一节点的任务执行者任务列表
		List<TbflowInstanceTask> taskList = Flow_commonInstanceExcutionImpl
				.CreateTasks(nNode, instance, user, lNode.getDnodeid(),
						lNode.getText3());
		// 生成流程文档信息
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
		int result = 0;

		// 更新流程实例的用户提交的表单aip内容信息（注意文件内容是否在用户提交前被其他用户变动，用锁还是不用锁）
		// TODO:需修改
		TbflowInstanceDoc doc = Flow_commonInstanceExcutionImpl
				.createFlowInstanceDoc(instance);
		doc.setBaseid(instance.getFlowdocid());
		result = tbflowInstanceDocMapper.updateByPrimaryKeySelective(doc);

		// 更改当前用户的当前节点的任务信息
		TbflowInstanceTask task = new TbflowInstanceTask();
		task.setTaskid(this.getCurrentTask().getTaskid());
		task.setEnddate(new Date());
		task.setNote(instance.getExplain()); // 本人所做的操作描述
		task.setState(OAParameter.STATE_FINISHED); // 自己的任务已经完成
		result += tbflowInstanceTaskMapper // 更新当前任务
				.updateByPrimaryKeySelective(task);

		// 获取当前流程实例的信息
		TbflowInstance cInstance = tbflowInstanceMapper
				.selectByPrimaryKey(instance.getFlowid());
		// 并根据流程实例的完成情况信息，判断流程的流向，决定是否给下一个节点发任务信息 如果不需要 则 更新流程实例总状态信息（要
		// 锁记录）， 后退出
		int finished = cInstance.getInt3(); // 当前节点任务已经完成的人数
		int need = cInstance.getInt2(); // 当前节点任务需要完成的人数
		// 当前任务节点的 其他分配人还未完成
		if ((finished + 1) < need) {
			// 当前节点任务其他人还未完成，只更新流程实例数据
			cInstance.setInt3(finished + 1);
			// 更新流程实例数据
			return result += this.tbflowInstanceMapper
					.updateByPrimaryKeySelective(cInstance);
		}

		// 当前节点任务完成
		// 获取流程下一个节点的信息
		FlowDeliver nNode = tbflow_deliverMapper.getchildNodebyDeliverExplain(
				instance.getFlowdefid(), this.getCurrentTask().getCnode(),
				instance.getExplain());
		// 设置前新流程实例中上一个节点中的所有任务状态信息
		TbflowInstanceTask ltask = new TbflowInstanceTask();
		ltask.setFlowid(this.getCurrentTask().getFlowid());
		ltask.setCnode(this.getCurrentTask().getLnode());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ltask.setText1(sdf.format(new Date())); // 回复时间
		ltask.setFeedback(instance.getExplain()); // 页面给出的流转操作描述,反馈信息
		ltask.setResponse(OAParameter.FLOWRESPOSEYES);
		result += tbflowInstanceTaskMapper.updateByflownodeuserSelective(ltask);

		// 整个流程实例结束
		if (nNode.getDnodeid().startsWith("end")) {
			cInstance.setFlowstate(OAParameter.STATE_FINISHED);
			cInstance.setCnodestate(OAParameter.STATE_FINISHED);
			// instance.setCnode(null);
			// 更新流程实例数据
			return result += this.tbflowInstanceMapper
					.updateByPrimaryKeySelective(cInstance);
		}

		// 流程未结束，当前节点任务结束，进入下一个节点
		// 创建流程实例更新数据
		instance = this.createFlowInstanceforUpdate2(instance, nNode);
		// 更新流程实例数据
		result += this.tbflowInstanceMapper
				.updateByPrimaryKeySelective(instance);

		instance.setDepartid(cInstance.getDepartid());
		if (nNode.getDnodeid().equalsIgnoreCase(
				this.getCurrentTask().getLnode())) {
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
				tasktemp.setLnode(this.currentTask.getCnode());
				tasktemp.setLnodename(this.currentTask.getCnodename());
				// 设置任务类型，（对应流程定义的类型）
				tasktemp.setText1(instance.getFlowtype());
				tasktempList.add(tasktemp);
			}
			return this.addUserTask(tasktempList, instance);
		}
		// 正常流转顺序，进入下层节点任务
		// 更新上个节点
		result += tbflowInstanceTaskMapper.updateByflownodeuserSelective(ltask);
		// 生成下一节点的任务执行者任务列表
		List<TbflowInstanceTask> taskList = Flow_commonInstanceExcutionImpl
				.CreateTasks(nNode, instance, user, this.getCurrentTask()
						.getCnode(), this.getCurrentTask().getCnodename());		
		result = this.addUserTask(taskList, instance);
		return result;
	}

	@Override
	public <T> boolean canExecuteTask(T instance1, SystemUser user) {

		// /TODO:需修改
		TbflowInstance instance = (TbflowInstance) instance1;
		// 获取当前实例下当前用户的当前任务
		TbflowInstanceTask cTask = tbflowInstanceTaskMapper
				.selectByPrimaryKey(instance.getTaskid());
		this.setCurrentTask(cTask);
		// 获取当前节点任务已经完成的用户任务列表（多个人执行同一任务）
		List<TbflowInstanceTask> taskListhis = tbflowInstanceTaskMapper
				.flowtaskisrollback(cTask.getFlowid(), cTask.getCnode(),
						OAParameter.STATE_FINISHED);

		if (taskListhis.size() > 0) {
			// 更新当前任务为完成状态即可
			cTask.setState(OAParameter.STATE_FINISHED);
			cTask.setEnddate(new Date());
			this.tbflowInstanceTaskMapper.updateByPrimaryKeySelective(cTask);
			return false;
		}
		return true;
	}

	public TbflowInstanceTask getCurrentTask() {
		return currentTask;
	}

	public void setCurrentTask(TbflowInstanceTask currentTask) {
		this.currentTask = currentTask;
	}

	/**
	 * 生成公文流转实例更新数据
	 * 
	 * @param instance
	 * @param nNode
	 * @return
	 */
	@SuppressWarnings("unused")
	private TbflowInstance createFlowInstanceforUpdate(TbflowInstance instance,
			FlowDeliver nNode) {
		instance.setCnode(nNode.getDnodeid());
		instance.setConent(String.format("%s--%s", nNode.getRemark(),
				nNode.getText3()));
		// 流程实例的当前所处节点的描述信息
		instance.setCnodename(nNode.getText3());
		instance.setFlowname(nNode.getRemark());
		// 实际的发送任务执行人数
		instance.setInt1(Integer.parseInt(nNode.getText2()));
		instance.setInt3(0);
		instance.setInt2(1);
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

	public int addUserTask(List<TbflowInstanceTask> taskList,
			TbflowInstance instance) {
		int result = 0;
		int noticeid = 0;

		// var url = "${path}/deliver/toflowDeliver_main.page?action="
		// + cmd + "&menuid=${menuid}&flowdefid=${flowdefid}"
		// + "&templetid=${templetid}&type=${flowtype}&flowid="
		// + flowid + "&taskid=" + taskid + "&attachjs=${myjs2}";

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

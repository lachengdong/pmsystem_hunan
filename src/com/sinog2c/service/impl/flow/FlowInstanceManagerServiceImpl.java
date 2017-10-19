package com.sinog2c.service.impl.flow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.gkzx.common.OAParameter;
import com.sinog2c.dao.api.flow.FlowBaseDocMapper;
import com.sinog2c.dao.api.flow.FlowDeliverMapper;
import com.sinog2c.dao.api.flow.TbflowInstanceMapper;
import com.sinog2c.dao.api.flow.TbflowInstanceTaskMapper;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.flow.FieldReflector;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.flow.TbflowInstance;
import com.sinog2c.model.flow.TbflowInstanceTask;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;
import com.sinog2c.service.api.flow.FlowInstanceExecutionService;
import com.sinog2c.service.api.flow.FlowInstanceManagerService;

@Service("flowInstanceManagerService")
public class FlowInstanceManagerServiceImpl implements
		FlowInstanceManagerService {

	@Autowired
	private TbflowInstanceTaskMapper tbflowInstanceTaskMapper;
	@Autowired
	private TbflowInstanceMapper tbflowInstanceMapper;
	@Autowired
	private FlowBaseDocMapper tbflowInstanceDocMapper;
	@Autowired
	private FlowDeliverMapper tbflow_deliverMapper;
	@Autowired
	private FlowInstanceExecutionService flowInstanceExecutionService;

	// private FlowInstanceQueryService<T> flowInstanceQueryService;

	@Override
	@Transactional
	public int creatFlowInstanceDraft(Object instance, SystemUser user) {
		return this.flowInstanceExecutionService.creatFlowInstanceDraft(
				instance, user);
	}

	@Override
	@Transactional
	public int creatFlowInstance(Object instance, SystemUser user) {
		return this.flowInstanceExecutionService.creatFlowInstance(instance,
				user);
	}

	@Override
	@Transactional
	public int executeTask(TbflowInstance instance, SystemUser user) {
		if (this.flowInstanceExecutionService.canExecuteTask(instance, user))
			return this.flowInstanceExecutionService
					.executeTask(instance, user);
		return -1;
	}

	@Override
	public int updateTaskStartDate(String taskID) {
		TbflowInstanceTask task = new TbflowInstanceTask();
		task.setTaskid(taskID);
		task.setStartdate(new Date());
		return this.tbflowInstanceTaskMapper.updateByPrimaryKeySelective(task);
	}

	@Override
	public void setFlowInstanceExcutionService(
			FlowInstanceExecutionService execution) {
		this.flowInstanceExecutionService = execution;
	}

	@Override
	public JSONMessage<?> getflowInstanceTaskbyUserid(TbsysTemplate template,
			Map<String, Object> map, String userid, String state,
			String flowtype) {
		int total = Integer.parseInt(map.get("total").toString());
		String start = map.get("start").toString();
		String end = map.get("end").toString();
		String sortField = map.get("sortField").toString();
		String sortOrder = map.get("sortOrder").toString();
		String flowdefid = map.get("flowdefid").toString();
		Object response = map.get("response");
		Object applyid=map.get("applyid");

		String orderby = String.format("t1.%s %s", sortField, sortOrder);
		String key = map.get("key").toString().trim();
		String condition = "1=1";
		if (!key.isEmpty()) {
			key = "%" + key + "%";
			List<FieldReflector> list = JSON.parseArray(
					template.getKeyfields(), FieldReflector.class);
			for (int i = 0; i < list.size(); i++) {
				condition += String.format(" or (t1.%s like '%s')", list.get(i)
						.getClo(), key);
			}
		}
		// TODO:当前的处理并不是最佳
		condition = condition.replace("1=1 or ", " ");
		state = state.trim();
		condition = String.format("(%s)", condition);

		if (state.length() == 1) {
			condition = String.format("t1.cnodestate='%s' and %s", state,
					condition);
		} else if (state.length() > 1) {
			condition = String.format("instr('%s',t1.cnodestate)>0 and %s",
					state, condition);
		}
		if (!flowtype.isEmpty()) {
			condition = String.format("t1.flowtype='%s' and %s", flowtype,
					condition);
		}		
		if(response!=null)
		{
			condition = String.format("t1.response='%s' and %s", response,
					condition);
		}	
		if(applyid!=null)
		{
			condition = String.format("t1.applyid='%s' and %s", applyid,
					condition);
		}
		String sql = template.getMainsqltemp();
		sql = sql.replace("${flowdefid}", flowdefid);
		sql = sql.replace("${userid}", userid);
		sql = sql.replace("${condition}", condition);
		sql = sql.replace("${orderby}", orderby);
		sql = sql.replace("${end}", end);
		sql = sql.replace("${start}", start);
		if (total < 0) {
			String countsql = template.getMaincountsql();
			countsql = countsql.replace("${flowdefid}", flowdefid);
			countsql = countsql.replace("${userid}", userid);
			countsql = countsql.replace("${condition}", condition);
			total = this.tbflowInstanceMapper
					.getCountflowInstancebyUserid(countsql);
		}
		List<TbflowInstance> list = this.tbflowInstanceMapper
				.getflowInstancebyUserid(sql);
		return new JSONMessage<TbflowInstance>(list, total);
	}

	/**
	 * 获取流程实例
	 */
	@Override
	public TbflowInstance getflwoInstancebyidforAudit(String flowid,
			String userid) {
		return this.tbflowInstanceMapper.getflwoInstancebyidforAudit(flowid,
				userid);
	}

	/**
	 * 删除流程实例数据
	 */
	@Override
	@Transactional
	public int doRemoveFlowInstances(List<TbflowInstance> list) {
		int result = -1;
		for (int i = 0; i < list.size(); i++) {
			result += this.tbflowInstanceDocMapper.deleteByflowid(list.get(i)
					.getFlowid());
			result += this.tbflowInstanceTaskMapper.deleteByflowid(list.get(i)
					.getFlowid());
			result += this.tbflowInstanceMapper.deleteByPrimaryKey(list.get(i)
					.getFlowid());
		}
		return result;
	}

	@Override
	public TbflowInstanceTask getTbflowInstanceTaskbyID(String taskID) {
		return tbflowInstanceTaskMapper.selectByPrimaryKey(taskID);
	}

	/**
	 * 获取当前任务节点的下一个节点数据（流程定义数据）
	 */
	@Override
	public FlowDeliver getchildNodebyDeliverExplain(String flowdefid,
			String snodeid, String explain) {
		return this.tbflow_deliverMapper.getchildNodebyDeliverExplain(
				flowdefid, snodeid, explain);

	}

	@Override
	public List<TbflowInstanceTask> getflowTaskListByFlowid(String flowid) {
		return this.tbflowInstanceTaskMapper.getflowTaskListByid(flowid);
	}

	@Override
	public TbflowInstanceTask getInstanceTaskbyTaskID(String taskid) {
		return this.tbflowInstanceTaskMapper.selectByPrimaryKey(taskid);
	}

	@Override
	public List<SystemUser> getNodeAssigner(TbflowInstance instance) {

		List<SystemUser> list = new ArrayList<SystemUser>();

		FlowDeliver nNode = null;
		if (instance.getTaskid() == null || instance.getTaskid().isEmpty()) {
			// 获取起始节点的下下一节点也就是流程中的第三层
			nNode = tbflow_deliverMapper.getflowGrandNodebyidexplain(
					instance.getFlowdefid(), OAParameter.STARTNODE,
					instance.getExplain());
		} else {
			// 获取当前实例下当前用户的当前任务
			TbflowInstanceTask cTask = tbflowInstanceTaskMapper
					.selectByPrimaryKey(instance.getTaskid());
			// 当前节点任务完成
			// 获取流程下一个节点的信息
			nNode = tbflow_deliverMapper.getchildNodebyDeliverExplain(
					instance.getFlowdefid(), cTask.getCnode(),
					instance.getExplain());

			if (nNode.getDnodeid().equalsIgnoreCase(cTask.getLnode())) {
				List<TbflowInstanceTask> taskListhis = tbflowInstanceTaskMapper
						.flowtaskisrollback(cTask.getFlowid(),
								nNode.getDnodeid(), OAParameter.STATE_FINISHED);
				// 回退任务给已完成的人发回退任务
				for (TbflowInstanceTask task : taskListhis) {
					SystemUser user = new SystemUser();
					user.setUserid(task.getAssigneer());
					user.setName(task.getAssigneername());
					list.add(user);
				}
				return list;
			}
		}
		if (nNode.getAssigneer() != null) {
			String[] userids = nNode.getAssigneer().split(";");
			String[] userNames = nNode.getText1().split(";");
			for (int i = 0; i < userids.length; i++) {
				SystemUser user = new SystemUser();
				user.setUserid(userids[i]);
				user.setName(userNames[i]);
				list.add(user);
			}
		}
		return list;
	}

}

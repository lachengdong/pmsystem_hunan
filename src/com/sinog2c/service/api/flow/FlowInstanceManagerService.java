package com.sinog2c.service.api.flow;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.flow.TbflowInstance;
import com.sinog2c.model.flow.TbflowInstanceTask;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;


public interface FlowInstanceManagerService  {
	/**
	 * 创建流程实例草稿
	 * 
	 * @param instance
	 * @param user
	 * @return
	 */
	int creatFlowInstanceDraft(Object instance, SystemUser user);

	/**
	 * 创建流程实例
	 * 
	 * @param instance
	 * @param user
	 * @return
	 */
	int creatFlowInstance(Object instance, SystemUser user);

	/**
	 * 流程实例用户任务执行
	 * 
	 * @param instance
	 * @param user
	 * @return
	 */
	int executeTask(TbflowInstance instance, SystemUser user);
	
	/**
	 * 设置任务的开始处理时间
	 * 
	 * @param taskID
	 * @return
	 */
	int updateTaskStartDate(String taskID);
	
	
	void setFlowInstanceExcutionService(FlowInstanceExecutionService execution);

	/**
	 * 获取流程实例
	 * @param flowid
	 * @param userid
	 * @return
	 */
	TbflowInstance getflwoInstancebyidforAudit(String flowid, String userid);

	/**
	 * 删除流程实例数据
	 * @param list
	 * @return
	 */
	int doRemoveFlowInstances(List<TbflowInstance> list);

	/**
	 * 获取流程实例节点任务信息
	 * @param taskID
	 * @return
	 */
	TbflowInstanceTask getTbflowInstanceTaskbyID(String taskID);

	/**
	 * 获取当前任务节点的下一个节点数据（流程定义数据）
	 * @param flowdefid
	 * @param snodeid
	 * @param explain
	 * @return
	 */
	FlowDeliver getchildNodebyDeliverExplain(String flowdefid,
			String snodeid, String explain);

	/**
	 * 根据流程实例查询当前实例完成情况任务列表
	 * @param flowid
	 * @return
	 */
	List<TbflowInstanceTask> getflowTaskListByFlowid(String flowid);

	/**
	 * 获取用户任务列表
	 * @param template
	 * @param map
	 * @param userid
	 * @param state
	 * @param flowtype
	 * @return
	 */
	JSONMessage<?> getflowInstanceTaskbyUserid(TbsysTemplate template,
			Map<String, Object> map, String userid, String state,
			String flowtype);
	
	/**
	 * 根据任务ID获取任务信息
	 * @param taskid
	 * @return
	 */
	TbflowInstanceTask getInstanceTaskbyTaskID(String taskid);

	/**
	 * 根据流程实例获取当前任务节点所有可执行者
	 * @param taskid
	 * @return
	 */
	List<SystemUser> getNodeAssigner(TbflowInstance instance);
}

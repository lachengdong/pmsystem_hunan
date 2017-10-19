package com.sinog2c.service.api.flow;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.flow.TbflowInstance;
import com.sinog2c.model.flow.TbflowInstanceTask;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;



public interface FlowInstanceDeliverService {

	TbflowInstance getflwoInstancebyidforAudit(String flowid, String userid);

	int doRemoveFlowInstances(List<TbflowInstance> list);

	JSONMessage<TbflowInstance> getflowInstanceTaskbyUserid(
			TbsysTemplate template, Map<String, Object> map, String userid,
			String state);

	int creatFlowInstanceDraft(TbflowInstance instance, SystemUser user);

	int doflowInstanceDeliver(TbflowInstance instance, SystemUser user);

	TbflowInstanceTask getTbflowInstanceTaskbyID(String taskID);

	/**
	 * 设置任务的开始处理时间
	 * 
	 * @param taskID
	 * @return
	 */
	int updateTaskStartDate(String taskID);

	FlowDeliver getchildNodebyDeliverExplain(String flowdefid,
			String snodeid, String explain);

}

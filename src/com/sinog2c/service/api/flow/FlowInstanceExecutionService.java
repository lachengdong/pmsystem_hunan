package com.sinog2c.service.api.flow;

import com.sinog2c.model.system.SystemUser;


public interface FlowInstanceExecutionService {
	//创建流程实例草稿
	<T> int  creatFlowInstanceDraft(T instance, SystemUser user);	
	//创建流程实例
	<T> int creatFlowInstance(T instance, SystemUser user);
	//流程实例用户任务执行
	<T> int executeTask(T instance, SystemUser user);
	//当前任务是否可以执行
	<T> boolean canExecuteTask(T instance, SystemUser user);	
}

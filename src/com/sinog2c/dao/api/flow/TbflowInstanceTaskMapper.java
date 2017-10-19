package com.sinog2c.dao.api.flow;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.flow.TbflowInstanceTask;

public interface TbflowInstanceTaskMapper {
	int deleteByPrimaryKey(String taskid);

	int insert(TbflowInstanceTask record);

	int insertSelective(TbflowInstanceTask record);
	int insertSelective2(TbflowInstanceTask record);

	TbflowInstanceTask selectByPrimaryKey(String taskid);

	int updateByPrimaryKeySelective(TbflowInstanceTask record);

	int updateByPrimaryKey(TbflowInstanceTask record);

	int deleteByflowid(String flowid);

	int updateByflownodeuserSelective(TbflowInstanceTask record);

	List<TbflowInstanceTask> flowtaskisrollback(@Param("flowid") String flowid,
			@Param("cnode") String cnode, @Param("state") String state);	
	
	List<TbflowInstanceTask> getflowTaskListByid(@Param("flowid") String flowid);
	
	int getNextId();

}
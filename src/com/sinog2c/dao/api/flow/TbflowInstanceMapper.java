package com.sinog2c.dao.api.flow;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.flow.TbflowInstance;


public interface TbflowInstanceMapper {
    int deleteByPrimaryKey(String flowid);

    int insert(TbflowInstance record);

    int insertSelective(TbflowInstance record);

    TbflowInstance selectByPrimaryKey(String flowid);

    int updateByPrimaryKeySelective(TbflowInstance record);

    int updateByPrimaryKey(TbflowInstance record);
    
    List<TbflowInstance> getflowInstancebyUserid(@Param("sql") String sql);

	int getCountflowInstancebyUserid(@Param("sql") String sql);

	TbflowInstance getflwoInstancebyidforAudit(@Param("flowid") String flowid,
			@Param("userid") String userid);
}
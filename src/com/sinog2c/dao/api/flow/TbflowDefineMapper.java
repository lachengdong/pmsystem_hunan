package com.sinog2c.dao.api.flow;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.flow.TbflowDefine;
import com.sinog2c.model.flow.TbflowDefineWithBLOBs;



public interface TbflowDefineMapper {
	int deleteByPrimaryKey(String flowdefid);

	int insert(TbflowDefineWithBLOBs record);

	int insertSelective(TbflowDefineWithBLOBs record);

	TbflowDefineWithBLOBs selectByPrimaryKey(String flowdefid);

	int updateByPrimaryKeySelective(TbflowDefineWithBLOBs record);

	int updateByPrimaryKeyWithBLOBs(TbflowDefineWithBLOBs record);

	int updateByPrimaryKey(TbflowDefine record);

	int getFlowCountBydepid(Map<String, Object> map);

	int getNextId();

	List<TbflowDefine> findFlowByDepartid(Map<String, Object> map);
	
	List<TbflowDefine> findFlowByTypeandDept(Map<String, Object> map);
	
	
}
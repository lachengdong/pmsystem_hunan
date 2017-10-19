package com.sinog2c.service.api.flow;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.flow.FlowDeliver;
import com.sinog2c.model.flow.TbflowDefine;
import com.sinog2c.model.flow.TbflowDefineWithBLOBs;


public interface FlowDeliverService {
	  /**
	 * 返回表数据的总数
	 * @return
	 */
	public int countAll();
	 /**
	 * 查询所有表数据并返回对象
	 * @return
	 */
	List<FlowDeliver> selectAll();
	/**
	 * 插入表数据
	 * @return
	 */
    public int insert(FlowDeliver flowDeliver);
    /**
	 * 根据部门ID查询数据
	 * @param departid
	 * @return
	 */
    List<FlowDeliver> findByDepartid(String departid);
    /**
	 * 根据资源ID查询数据
	 * @param resid
	 * @param departid
	 * @return
	 */
    public FlowDeliver findByCondition(String resid,String departid);
    /**
	 * 根据参数查询数据
	 * @param map
	 * @return
	 */
    public List<FlowDeliver> findByFlowdefid(Map<String, String> map);
    
    public List<Map<String,Object>> getCaseProcess(Map<String, String> map);
    /**
     * 根据流程节点、流程自定义id、单位查询该节点的上级节点
     */
    public String findSnodeidByDnodeid(Map<String, String> map);
    
    public List<Map<String,Object>> getFyJinCheng(Map map);
    
  //start add by blue 2015-07-16 
 	/**
 	 * 获取流程定义squence
 	 * 
 	 * @return
 	 */
 	int getNextId();

 	/**
 	 * 新增流程定义信息
 	 * 
 	 * @param flowDefine
 	 * @param deList
 	 * @return
 	 */
 	int addNewFlowDefine(TbflowDefineWithBLOBs flowDefine,
 			List<FlowDeliver> deList);

 	/**
 	 * 修改流程定义信息
 	 * 
 	 * @param flowDefine
 	 * @param deList
 	 * @return
 	 */
 	int updateFlowDefine(TbflowDefineWithBLOBs flowDefine,
 			List<FlowDeliver> deList);

 	/**
 	 * 根据flowdefid查询流程定义信息
 	 * 
 	 * @param flowdefid
 	 * @return
 	 */
 	TbflowDefineWithBLOBs selectByPrimaryKey(String flowdefid);

 	/**
 	 * 删除流程定义信息
 	 * 
 	 * @param flowDefineid
 	 * @return
 	 */
 	int deleteFlowDefinebyID(String flowDefineid);

 	/**
 	 * 获取流程定义信息列表
 	 * 
 	 * @param map
 	 * @return
 	 */
 	JSONMessage<TbflowDefine> getFlowDeliversBydep(Map<String, Object> map);

 	/**
 	 * 获取流程节点信息列表
 	 * 
 	 * @param flowdefid
 	 * @return
 	 */
 	List<FlowDeliver> getflowNodelistbyflowdefineid(String flowdefid);

 	/**
 	 * 根据流程类型和所属部门获取流程信息
 	 * 
 	 * @param map
 	 * @return
 	 */
 	JSONMessage<TbflowDefine> getFlowByTypeAndDept(Map<String, Object> map);
	
 	public String getFlowCurrnodeidOfDataGrid(Map<String,Object> map);
 	
	//end add by blue 2015-07-16
}
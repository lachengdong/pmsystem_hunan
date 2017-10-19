package com.sinog2c.dao.api.flow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.sinog2c.model.flow.FlowDeliver;
@Component("flowDeliverMapper")
public interface FlowDeliverMapper {
	
	int countAll();
	
	List<FlowDeliver> selectAll();
	
    int insert(FlowDeliver record);

    FlowDeliver findByCondition(@Param("resid")String resid,@Param("departid")String departid);
    
    FlowDeliver findByParamMap(Map<String, Object> paraMap);
    
    List<FlowDeliver> findByParam(@Param("flowdefid")String flowdefid,@Param("departid")String departid);
    
    List<HashMap> selectTree(String snodeid);
    
    List<FlowDeliver> findByDepartid(String departid);

	List<FlowDeliver> findByflowdefid(Map<String, String> map);
	
	List<Map<String,Object>> getCaseProcess(Map<String, String> map);
	
	String findSnodeidByDnodeid(Map<String, String> map);
	
	List<FlowDeliver> findFlowByDepartid(Map map);
	
	int flowManageServicenum(Map map);
	
	int getCountByDepartid(String str);
	
	void beginCopy1(Map map);

	void beginCopy2(Map map);
	
	int getFlowconfByDepartid(Map map);
	
	List<FlowDeliver> getFlowById(Map map);
	
	int getFlowByIdCount(Map map);
	
	void updateFlowInfo(Map map);
	
	void createSequences();
	
	void removeFlow(Map map);
	
	void createOrgOrg(Map map);
	
	void delteOrgByid(String str);
	
	List getOrgInfo(Map map);
	
	int getOrgInfoCount(Map map);
	
	void removeOrgOrg(Map map);
	
	int findDepartidCount(Map map);
	
	int getXingwenCount(Map map);
	
	List<Map> getXingwenData(Map map);
	
	List getXingWenDepart();
	
	String CopyXingWen(Map  map);
	
	int selectXingWenCount(Map map);
	
	int deleXingWen(Map map);

	List<Map<String,Object>> getFlowDeliverTreeByDepartid(String departid);
	
	List<Map<String,Object>> getFyJinCheng(Map map);
	
	FlowDeliver setValue(String str);
	
	int findCountFlowid(Map map);
	
	void addFlowToFlowconf(Map map);
	
	String returnUserResourceByCondition(Map map);
	
	int getDeliverCount(Map map);

	FlowDeliver findByConditionFlowdef(@Param("resid")String resid,@Param("departid")String departid,
			@Param("flowdefid")String flowdefid);

	 List<Map<String, Object>> getFlowdefListForYwgkByDepartid(String departid);
	 

		//start add by blue_lv 2015-07-15
		int insertSelective(FlowDeliver record);

		int updateByPrimaryKeySelective(FlowDeliver record);

		int deleteByflowdefid(String flowdefid);

		List<FlowDeliver> getflowNodelistbyflowdefineid(
				@Param("flowdefid") String flowdefid);

		/**
		 * 根据流转方向描述获取当前节点的下一节节点信息
		 * 
		 * @param flowdefid
		 * @param snodeid
		 * @param explain
		 * @return
		 */
		FlowDeliver getchildNodebyDeliverExplain(
				@Param("flowdefid") String flowdefid,
				@Param("snodeid") String snodeid, @Param("explain") String explain);

		/**
		 * 根据explain获取当前节点的下下节点信息
		 * 
		 * @param flowdefid
		 * @param snodeid
		 * @param explain
		 * @return
		 */
		FlowDeliver getflowGrandNodebyidexplain(
				@Param("flowdefid") String flowdefid,
				@Param("snodeid") String snodeid, @Param("explain") String explain);
		
		FlowDeliver getflowDNodeinfobyTaskId(String taskId);
		//end add by blue_lv 2015-07-15
		
		public String getFlowCurrnodeidOfDataGrid(Map<String,Object> map);
}
package com.sinog2c.dao.api.flow;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.sinog2c.model.flow.Flow;
@Component("flowMapper")
public interface FlowMapper {
    int insert(Flow record);

    int delete(String id);
    
    int removeByArchid(String archiveid);
    
    int countAll();
    
    int updateByFlowdraftid(Flow flow);
    
    int updateFlowState(@Param("state")String state, @Param("flowdraftid")String flowdraftid);
    
    int updateFlowStateByMap(Map<Object, Object> map);
    
    String getFlowid(@Param("orgid")String orgid,@Param("departid")String departid,@Param("flowdefid")String flowdefid,@Param("isflow")int isflow);

    List<Flow> selectAll();
   
    Flow findById(String id);
    
    Flow getOpinion(Flow flow);
    void updateZZByFlowSn(Flow flow);
    /**
	 * 根据流程草稿ID判断是否存在呈批表
	 * @param flowid
	 * @return
	 */
    public int validatecpb(@Param("flowdraftid")String flowdraftid);
    
    public List<Map> selectFlowForScar(Map<String, Object> paraMap);
    
    public Map findflowByflowdefid(Map map);
    
    Flow findByFristId(String id);
    
}
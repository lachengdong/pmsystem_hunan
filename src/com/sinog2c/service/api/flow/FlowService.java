package com.sinog2c.service.api.flow;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.flow.Flow;

public interface FlowService {
	/**
	 * 返回表数据的总数
	 * @return
	 */
    public int countAll();
    /**
	 * 获取流程ID
	 *@param orgid
	 *@param departid
	 *@param flowdefid
	 *@param isflow
	 * @return
	 */
    public String getFlowid(String orgid,String departid,String flowdefid,int isflow);
    /**
	 * 插入表数据
	 * @return
	 */
    public int insert(Flow flow);
    /**
	 * 根据参数更新数据
	 * @param map
	 * @return
	 */
    public int updateByFlowdraftid(Flow flow);
    /**
	 * 根据参数更新流程操作状态
	 *@param state
	 *@param 
	 * @return
	 */
    public int updateFlowState(@Param("state")String state,@Param("applyid")String applyid,@Param("flowdraftid")String flowdraftid);
    /**
	 * 根据归档id删除表数据
	 * @param flowid
	 * @return
	 */
    public int delete(String flowid);
    /**
	 * 查询所有表数据并返回对象
	 * @return
	 */
    public List<Flow> selectAll();
    /**
	 * 根据流程ID查询数据
	 * @param flowid
	 * @return
	 */
    public Flow findById(String flowid);
    
    public Flow getOpinion(Flow flow);
    void updateZZByFlowSn(Flow flow);
    /**
	 * 根据流程草稿ID判断是否存在呈批表
	 * @param flowid
	 * @return
	 */
    public int validatecpb(String flowdraftid);
}
package com.sinog2c.service.api.flow;

import java.util.List;

import com.sinog2c.model.flow.FlowDeliverHis;

public interface FlowDeliverHisService {
	/**
	 * 插入表数据
	 * @return
	 */
    public int insert(FlowDeliverHis flowDeliverHis);
    /**
	 * 根据id删除表数据
	 * @param id
	 * @return
	 */
    public int delete(String id);
    /**
	 * 查询所有表数据并返回对象
	 * @return
	 */
    public List<FlowDeliverHis> selectAll();
    /**
	 * 返回表数据的总数
	 * @return
	 */
    public int countAll();
    /**
	 * 根据流程自定义ID查询数据
	 * @param flowdefid
	 * @return
	 */
    public FlowDeliverHis findById(String flowdefid);
    
}
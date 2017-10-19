package com.sinog2c.service.api.flow;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.flow.FlowArchivesView;

public interface FlowArchivesViewService {
	/**
	 * 插入表数据
	 * @return
	 */
    public int insert(FlowArchivesView flowArchivesView);
    /**
	 * 根据map传递参数进行更新
	 * @param map
	 * @return
	 */
    public int update(Map<String,Object> map);
    /**
	 * 根据map传递参数进行删除
	 * @param map
	 * @return
	 */
    public int delete(Map<String,Object> map);
    /**
	 * 根据申请人所在部门id查询数据并返回对象集合
	 * @param suid
	 * @return
	 */
    public List<FlowArchivesView> selectAllBySuid(String orgid);
    
}
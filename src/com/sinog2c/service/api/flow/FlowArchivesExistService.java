package com.sinog2c.service.api.flow;

import com.sinog2c.model.flow.FlowArchivesExist;

public interface FlowArchivesExistService {
	/**
	 * 插入表数据
	 * @return
	 */
    public int insertSelective(FlowArchivesExist flowArchivesExist);
    /**
	 * 根据map传递参数进行更新
	 * @param map
	 * @return
	 */
    public int updateByExample(FlowArchivesExist flowArchivesExist);
    
}
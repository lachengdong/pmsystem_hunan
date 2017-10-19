package com.sinog2c.service.api.flow;

import java.util.List;

import com.sinog2c.model.flow.FlowDefineCode;

public interface FlowDefineCodeService {
	/**
	 * 插入表数据
	 * @return
	 */
    public int insert(FlowDefineCode flowDefineCode);
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
    public List<FlowDefineCode> selectAll();
    /**
	 * 返回表数据的总数
	 * @return
	 */
    public int countAll();
    /**
	 * 根据归档ID查询数据
	 * @param archiveid
	 * @return
	 */
    public FlowDefineCode findById(String id);
    
}
package com.sinog2c.service.api.flow;

import java.sql.Clob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinog2c.model.flow.FlowBaseDoc;

public interface FlowBaseDocService {
	/**
	 * 插入表数据
	 * @return
	 */
//    public int insert(FlowBaseDoc flowBaseDoc);
    /**
	 * 根据id传递参数进行删除
	 * @param id
	 * @return
	 */
    public int delete(String id);
    /**
	 * 查询表数据返回对象集合
	 * @param map
	 * @return
	 */
    public List<FlowBaseDoc> selectAll();
    /**
	 * 查询表数据返回总数
	 * @param map
	 * @return
	 */
    public int countAll();
    /**
	 * 根据id传递参数进行查询
	 * @param id
	 * @return
	 */
    public FlowBaseDoc findById(String id);
    /**
	 * 根据参数进行更新
	 * @param flowdraftid
	 * @param suid
	 * @return
	 */
    public int updateByCondition(FlowBaseDoc flowBaseDoc);
    
    public FlowBaseDoc findLastDocByflowdraftid(Map<String,Object> map);

  
}
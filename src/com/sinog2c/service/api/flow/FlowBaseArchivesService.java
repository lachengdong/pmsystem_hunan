package com.sinog2c.service.api.flow;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.flow.FlowBaseArchives;

public interface FlowBaseArchivesService {
	/**
	 * 插入表数据
	 * @return
	 */
    public int insert(FlowBaseArchives flowBaseArchives);
    /**
	 * 根据归档ID传递参数进行删除
	 * @param map
	 * @return
	 */
    public int deleteById(String archiveid);
    /**
	 * 根据map传递参数进查询返回总数
	 * @param map
	 * @return
	 */
    public int countAll(Map<String,Object> map);
    public int countAllForSD(Map<String,Object> map);
    public int countAllShengju(Map<String,Object> map);
    /**
	 * 根据map传递参数进行查询返回总数
	 * @param map
	 * @return
	 */
    public int countAllByCondition(Map<String,Object> map);
    /**
	 * 根据map传递参数进行查询返回集合
	 * @param map
	 * @return
	 */
    public List<FlowBaseArchives> selectAllByCondition(Map<String,Object> map);
    /**
	 * 根据map传递参数进行查询返回集合
	 * @param map
	 * @return
	 */
    public List<FlowBaseArchives> selectAllByPage(Map<String,Object> map);
    public List<FlowBaseArchives> selectAllByPageForSD(Map<String,Object> map);
    public List<FlowBaseArchives> selectAllByPageShengju(Map<String,Object> map);
    
	int insertCaseInfo(Map<Object, Object> map);
    
}
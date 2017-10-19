package com.sinog2c.dao.api.solution;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.solution.FormParamMapping;

public interface FormParamMappingMapper {
	
    int deleteById(String paramid);
    
    int deleteByMultiId(Map<String,Object> map);

    int insert(FormParamMapping record);

    int insertSelective(FormParamMapping record);

    FormParamMapping selectById(String paramid);
    
    FormParamMapping selectByCombinationId(Map<String,Object> map);

    int updateByIdSelective(FormParamMapping record);

    int updateById(FormParamMapping record);
    

	/**
	 * 获取数据列表
	 * @return
	 */
	List<Map<String, Object>> listMapByPage(Map<String, Object> map);

	// 统计所有
	public int countAll();
	// 获取所有 list
	public List<FormParamMapping> listAll();
	// 根据条件统计
	public int countByCondition(Map<String, Object> map);
	// 根据条件获取数据
	public List<FormParamMapping> listByCondition(Map<String, Object> map);
	// 根据Map更新
	public int updateByMap(Map<String, Object> map);
	// 根据Map插入
	public int insertByMap(Map<String, Object> map);
}
package com.sinog2c.dao.api.solution;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.solution.FormSqlGroup;

public interface FormSqlGroupMapper {
    int deleteById(String sqlgroupid);

    int insert(FormSqlGroup record);

    int insertSelective(FormSqlGroup record);

    FormSqlGroup selectById(String sqlgroupid);

    int updateByIdSelective(FormSqlGroup record);

    int updateById(FormSqlGroup record);

	/**
	 * 获取数据列表
	 * @return
	 */
	List<Map<String, Object>> listMapByPage(Map<String, Object> map);

	// 统计所有
	public int countAll();
	// 获取所有 list
	public List<FormSqlGroup> listAll();
	// 根据条件统计
	public int countByCondition(Map<String, Object> map);
	// 根据条件获取数据
	public List<FormSqlGroup> listByCondition(Map<String, Object> map);
	// 根据Map更新
	public int updateByMap(Map<String, Object> map);
	// 根据Map插入
	public int insertByMap(Map<String, Object> map);
	
	public List<FormSqlGroup> getFormSqlGroupData(Map<String, Object> map);

}
package com.sinog2c.dao.api.dbmsnew;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sinog2c.model.dbmsnew.DbmsCodeChemeNew;
import com.sinog2c.model.dbmsnew.DbmsCodeTypeNew;

@Repository
public interface DbmsCodeTypeNewMapper {
    int deleteByPrimaryKey(String codetypeid);

    int insert(DbmsCodeTypeNew record);

    int insertSelective(DbmsCodeTypeNew record);

    List<Map<String, Object>> selectByPrimaryKey(DbmsCodeChemeNew keyString);

    int updateByPrimaryKeySelective(DbmsCodeTypeNew record);

    int updateByPrimaryKey(DbmsCodeTypeNew record);

	/**
	 * 获取数据列表
	 * @return
	 */
	List<Map<String, Object>> listCodeTypeMapByPage(Map<String, Object> map);
	
	// 统计所有
	public int countAll();
	// 获取所有 list
	public List<DbmsCodeTypeNew> listAll();
	// 根据条件统计
	public int countByCondition(Map<String, Object> map);
	// 根据条件获取数据
	public List<DbmsCodeTypeNew> listByCondition(Map<String, Object> map);
	// 根据Map更新
	public int updateByMap(Map<String, Object> map);
	// 根据Map插入
	public int insertByMap(Map<String, Object> map);
}
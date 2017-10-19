package com.sinog2c.dao.api.dbmsnew;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sinog2c.model.dbmsnew.DbmsDatabaseNew;

@Repository
public interface DbmsDatabaseNewMapper {
	
	DbmsDatabaseNew selectByPrimaryKey(String ddid);
	
	int deleteByPrimaryKey(String ddid);
	
	int insert(DbmsDatabaseNew record);
	
	int insertSelective(DbmsDatabaseNew record);
	
	int updateByPrimaryKeySelective(DbmsDatabaseNew record);
	
	int updateByPrimaryKey(DbmsDatabaseNew record);
	
    int allCount(Map<String, Object> map);
	/**
	 * 获取数据列表
	 * @return
	 */
	List<Map<String, Object>> listDBConnMapByPage(Map<String, Object> map);
	
	// 统计所有
	public int countAll(Map<String, Object> map);
	// 获取所有 list
	public List<DbmsDatabaseNew> listAll(Map<String, Object> map);
	// 根据条件统计
	public int countByCondition(Map<String, Object> map);
	// 根据条件获取数据
	public List<DbmsDatabaseNew> listByCondition(Map<String, Object> map);
	// 根据Map更新
	public int updateByMap(Map<String, Object> map);
	// 根据Map插入
	public int insertByMap(Map<String, Object> map);
	// 根据Map插入
	public int insertByOrgid(Map<String, Object> map);
	// 根据Map更新行文
	public int updateByExchangeWritingMap(Map<String, Object> map);
	//显示表中 所有数据
	List<Map> listByOrgid(Map<String, Object> map);
	
	int listByOrgidCount(Map<String, Object> map);
	
	List<DbmsDatabaseNew> getDbmsDataBaseNewByDepartid(Map<String, Object> map);
	
	DbmsDatabaseNew selectFromDatabaseByDdcid(String ddcid);
	
}
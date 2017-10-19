package com.sinog2c.service.api.dbmsnew;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.dbmsnew.DbmsDatabaseNew;
import com.sinog2c.model.system.SystemUser;


public interface DbmsDatabaseNewService {
	
	public DbmsDatabaseNew selectByPrimaryKey(String ddid);
	 
	public int deleteByPrimaryKey(String ddid);
	 
	public int insert(DbmsDatabaseNew record);
	 
	public int insertSelective(DbmsDatabaseNew record);
	 
	public int updateByPrimaryKeySelective(DbmsDatabaseNew record);
	 
	public int updateByPrimaryKey(DbmsDatabaseNew record);
	
	public int allCount(Map<String,Object> map);
	/**
	 * 获取数据库配置数据列表
	 * @return
	 */
	public List<Map<String, Object>> listDBConnMapByPage(Map<String,Object> map);

	// 统计所有
	public int countAll(SystemUser user);
	// 获取所有 list
	public List<DbmsDatabaseNew> listAll(SystemUser user);
	// 根据条件统计
	public int countByCondition(Map<String, Object> map, SystemUser user);
	// 根据条件获取数据
	public List<DbmsDatabaseNew> listByCondition(Map<String, Object> map, SystemUser user);
	// 根据Map更新
	public int updateByMap(Map<String, Object> map, SystemUser user);
	// 根据Map插入
	public int insertByMap(Map<String, Object> map, SystemUser user);
	// 根据Map插入
	public int insertByOrgid(Map<String, Object> map);
	// 根据Map更新行文
	public int updateByExchangeWritingMap(Map<String, Object> map);
    //显示所有数据
	public List<Map> listByOrgid(Map<String, Object> map);
	
	public int listByOrgidCount(Map<String, Object> map);
	
	public DbmsDatabaseNew selectFromDatabaseByDdcid(String ddcid);
	
}

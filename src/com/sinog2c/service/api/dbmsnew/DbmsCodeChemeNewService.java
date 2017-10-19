package com.sinog2c.service.api.dbmsnew;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.dbmsnew.DbmsCodeChemeNew;

public interface DbmsCodeChemeNewService {

	public List<Map<String, Object>> selectByPrimaryKey(DbmsCodeChemeNew key);
	
	public int deleteByPrimaryKey(DbmsCodeChemeNew key);

	public int insert(DbmsCodeChemeNew record);

	public int insertSelective(DbmsCodeChemeNew record);

	public int updateByPrimaryKeySelective(DbmsCodeChemeNew record);

	public int updateByPrimaryKey(DbmsCodeChemeNew record);

	// 统计所有
	public int countAll();
	// 获取所有 list
	public List<DbmsCodeChemeNew> listAll();
	// 根据条件统计
	public int countByCondition(Map<String, Object> map);
	// 根据条件获取数据
	public List<DbmsCodeChemeNew> listByCondition(Map<String, Object> map);
	public List<DbmsCodeChemeNew> alllistcodescheme(Map<String, Object> map);
	// 根据Map更新
	public int updateByMap(Map<String, Object> map);
	// 根据Map插入
	public int insertByMap(Map<String, Object> map);
    /**
     * 获取自增长的id
     * @return
     */
    public int getNextId();
}

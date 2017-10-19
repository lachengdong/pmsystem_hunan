package com.sinog2c.dao.api.dbmsnew;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinog2c.model.dbmsnew.DbmsDatasChemeNew;

@Repository
public interface DbmsDatasChemeNewMapper {

	List<Map<String, Object>> selectByPrimaryKey(String ddcid);
	
	List<Map<String, Object>> selectBySdid(String sdid);
	
	int deleteByPrimaryKey(String ddcid);
	
    int insert(DbmsDatasChemeNew record);

    int insertSelective(DbmsDatasChemeNew record);

    int updateByPrimaryKeySelective(DbmsDatasChemeNew record);

    int updateByPrimaryKey(DbmsDatasChemeNew record);

	/**
	 * 获取数据列表
	 * @return
	 */
	List<Map<String, Object>> listMapByPage(Map<String, Object> map);

	// 根据ID取得
	public DbmsDatasChemeNew getById(@Param("ddcid") String ddcid);
	// 统计所有
	public int countAll();
	// 获取所有 list
	public List<DbmsDatasChemeNew> listAll();
	// 根据条件统计
	public int countByCondition(Map<String, Object> map);
	// 根据条件获取数据
	public List<DbmsDatasChemeNew> listByCondition(Map<String, Object> map);
	// 根据Map更新
	public int updateByMap(Map<String, Object> map);
	// 根据Map插入
	public int insertByMap(Map<String, Object> map);
	
	public Map<String, Object> getRemoteIp(Map<String, Object> map);
	
	List<Map<String, Object>> getDbmsresolvewebxmlList(Map<String,Object> map);
	
}
package com.sinog2c.dao.api.dbmsnew;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sinog2c.model.dbmsnew.DbmsCodeChemeNew;

@Repository
public interface DbmsCodeChemeNewMapper {
	List<Map<String, Object>> selectByPrimaryKey(DbmsCodeChemeNew key);
	
    int deleteByPrimaryKey(DbmsCodeChemeNew key);

    int insert(DbmsCodeChemeNew record);

    int insertSelective(DbmsCodeChemeNew record);

    int updateByPrimaryKeySelective(DbmsCodeChemeNew record);

    int updateByPrimaryKey(DbmsCodeChemeNew record);	
    
	// 统计所有
	public int countAll();
	// 获取所有 list
	public List<DbmsCodeChemeNew> listAll();
	// 根据条件统计
	public int countByCondition(Map<String, Object> map);
	// 根据条件获取数据
	public List<DbmsCodeChemeNew> listByCondition(Map<String, Object> map);
	// 根据Map更新
	public int updateByMap(Map<String, Object> map);
	// 根据Map插入
	public int insertByMap(Map<String, Object> map);
    /**
     * 获取自增长的 id
     * @return
     */
    public int getNextId();
}
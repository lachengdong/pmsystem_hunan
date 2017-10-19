package com.sinog2c.dao.api.dbmsnew;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinog2c.model.dbmsnew.DbmsDatasTableNew;
import com.sinog2c.model.dbmsnew.DbmsDatasTableNewKey;

@Repository
public interface DbmsDatasTableNewMapper {
    int deleteByPrimaryKey(DbmsDatasTableNewKey key);

    int insert(DbmsDatasTableNew record);

    int insertSelective(DbmsDatasTableNew record);

    DbmsDatasTableNew selectByPrimaryKey(DbmsDatasTableNewKey key);

    int updateByPrimaryKeySelective(DbmsDatasTableNew record);

    int updateByPrimaryKey(DbmsDatasTableNew record);

	// 根据条件获取数据
	List<DbmsDatasTableNew> listByCondition(Map<String, Object> map);
	
	List<Map> getShcemeTables(Map<String,Object> map);
	
	List<Map> getSchemetablecolumn(Map<String,Object> map);
	
	int updateSchemeTablecolumn(Map map);
	
	int saveSchemetable(Map map);
}
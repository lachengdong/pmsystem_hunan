package com.sinog2c.dao.api.dbmsnew;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinog2c.model.dbmsnew.DbmsDatasChemeDetailNew;
import com.sinog2c.model.dbmsnew.DbmsDatasChemeDetailNewKey;

@Repository
public interface DbmsDatasChemeDetailNewMapper {
    int deleteByPrimaryKey(DbmsDatasChemeDetailNewKey key);

    int insert(DbmsDatasChemeDetailNew record);

    int insertSelective(DbmsDatasChemeDetailNew record);

    DbmsDatasChemeDetailNew selectByPrimaryKey(DbmsDatasChemeDetailNewKey key);

    int updateByPrimaryKeySelective(DbmsDatasChemeDetailNew record);

    int updateByPrimaryKey(DbmsDatasChemeDetailNew record);
	// 根据条件获取数据
	List<DbmsDatasChemeDetailNew> listByCondition(Map<String, Object> map);
    
    List<DbmsDatasChemeDetailNew> getcolumnByddcid(@Param("ddcid") String ddcid);
}
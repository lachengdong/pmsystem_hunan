package com.sinog2c.service.api.dbmsnew;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.dbmsnew.DbmsDatasChemeDetailNew;
import com.sinog2c.model.dbmsnew.DbmsDatasChemeDetailNewKey;

public interface DbmsDatasChemeDetailNewService {
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

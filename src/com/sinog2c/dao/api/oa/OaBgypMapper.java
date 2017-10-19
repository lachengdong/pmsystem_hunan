package com.sinog2c.dao.api.oa;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.oa.OaBgyp;

public interface OaBgypMapper {
    int deleteByPrimaryKey(@Param("bgypid")String bgypid);

    int insert(OaBgyp record); 

    int insertSelective(OaBgyp record);

    OaBgyp selectByPrimaryKey(String bgypid);

    int updateByPrimaryKeySelective(OaBgyp record);

    int updateByPrimaryKey(OaBgyp record);
    
    List<Map> getBgypList(@Param("key")String key);
}
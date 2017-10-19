package com.sinog2c.dao.api.oa;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.oa.OAyz;

public interface OAyzMapper {
    int deleteByPrimaryKey(String yzid);

    int insert(OAyz record);

    int insertSelective(OAyz record);

    OAyz selectByPrimaryKey(String yzid);

    int updateByPrimaryKeySelective(OAyz record);

    int updateByPrimaryKey(OAyz record);
    
    List<Map> getYzList(Map map);
    void deleteOayz(@Param("yzids")String yzids);
}
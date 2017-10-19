package com.sinog2c.dao.api.flow;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.flow.FlowArchivesCode;

public interface FlowArchivesCodeMapper {
    int insert(FlowArchivesCode record);
    
    FlowArchivesCode selectByTempid(@Param("tempid")String tempid,@Param("departid")String departid);
    
    FlowArchivesCode selectByPrimaryKey(@Param("codeid")String codeid,@Param("departid")String departid);
    
    int updateByPrimaryKey(FlowArchivesCode record);
    
    List<FlowArchivesCode> selectAllByDepid(Map map);
    
    List<FlowArchivesCode> selectAllByDepidByType(Map map);
    
    List<FlowArchivesCode> selectAllByMap(Map map);
    
    int selectAllByMapCount(Map map);
    
    int findCodeByMap(Map map);
    
    int deleteCodeByMap(Map map);
}
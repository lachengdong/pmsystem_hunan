package com.sinog2c.dao.api.flow;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.flow.FlowBaseArchives;

public interface FlowBaseArchivesMapper {
    int insert(FlowBaseArchives record);

    int insertSelective(FlowBaseArchives record);
    
    int deleteById(String archiveid);
    int deleteFlowBaseById(String archiveid);
    int deleteFlowById(String archiveid);
    
    
    int selectById(Map<String,Object> map);
    int countAll(Map<String,Object> map);
    int countAllForSD(Map<String,Object> map);
    int countAllShengju(Map<String,Object> map);

    int countAllByCondition(Map<String,Object> map);
    
    List<FlowBaseArchives> selectAllByCondition(Map<String,Object> map);
    
    List<FlowBaseArchives> selectAllByPage(Map<String,Object> map);
    List<FlowBaseArchives> selectAllByPageForSD(Map<String,Object> map);
    List<FlowBaseArchives> selectAllByPageShengju(Map<String,Object> map);
    
   int insertCaseInfo(Map<Object, Object> map);

}
package com.sinog2c.dao.api.flow;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.flow.FlowArchivesView;

public interface FlowArchivesViewMapper {
    int insert(FlowArchivesView record);

    int update(Map<String,Object> map);
    
    int delete(Map<String,Object> map);
    
    List<FlowArchivesView> selectAllBySuid(String orgid);

}
package com.sinog2c.dao.api.flow;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sinog2c.model.flow.FlowDeliverHis;
@Component("flowDeliverHisMapper")
public interface FlowDeliverHisMapper {
    int insert(FlowDeliverHis record);

    int delete(String id);
    
    List<FlowDeliverHis> selectAll();
   
    int countAll();
   
    FlowDeliverHis findById(String id);
    
}
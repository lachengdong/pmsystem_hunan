package com.sinog2c.dao.api.flow;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sinog2c.model.flow.FlowDefineCodeHis;
@Component("flowDefineCodeHisMapper")
public interface FlowDefineCodeHisMapper {
    int insert(FlowDefineCodeHis record);

    int delete(String id);
    
    List<FlowDefineCodeHis> selectAll();
   
    int countAll();
   
    FlowDefineCodeHis findById(String id);
    
}
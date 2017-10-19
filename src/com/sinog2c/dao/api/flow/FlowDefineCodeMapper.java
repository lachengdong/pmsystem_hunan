package com.sinog2c.dao.api.flow;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sinog2c.model.flow.FlowDefineCode;
@Component("flowDefineCodeMapper")
public interface FlowDefineCodeMapper {
    int insert(FlowDefineCode record);

    int delete(String id);
    
    List<FlowDefineCode> selectAll();
   
    int countAll();
   
    FlowDefineCode findById(String id);
    
}
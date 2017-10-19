package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.SignatureFlow;

public interface SignatureFlowMapper {
	
    int insert(SignatureFlow record);

    int insertSelective(SignatureFlow record);
    
    int insertMapSelective(Map<String,Object> map);
    
    Map<String,Object> getLastSignatureFlow(Map<String,Object> map);
    
    SignatureFlow findLastFlowNodeSignatureFlow(Map<String,Object> map);
    
    public List<SignatureFlow> findLastFlowNodeSignatureFlows(Map<String, Object> map);
    
    List<Map<String,Object>> getSignatureFlowByMap(Map<String,Object> paramap);
    
}
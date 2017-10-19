package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.SignatureFlow;

public interface SignatureFlowService {
    
	int insertMapSelective(Map<String,Object> map);
	
    Map<String,Object> getLastSignatureFlow(Map<String,Object> map);
    
    public SignatureFlow findLastFlowNodeSignatureFlow(Map<String, Object> map);
    
    public List<SignatureFlow> findLastFlowNodeSignatureFlows(Map<String, Object> map);
    
    public void operateSignatureFlow(Map<String,Object> map, String operatetype, String currnodeid, Object requaresealnum);
    
}
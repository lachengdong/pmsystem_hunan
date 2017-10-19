package com.sinog2c.dao.api.flow;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.flow.TbFlowPersonConfig;
import com.sinog2c.model.flow.TbFlowPersonConfigKey;

public interface TbFlowPersonConfigMapper {
    int deleteByPrimaryKey(TbFlowPersonConfigKey key);

    int insert(TbFlowPersonConfig record);

    int insertSelective(TbFlowPersonConfig record);

    TbFlowPersonConfig selectByPrimaryKey(TbFlowPersonConfigKey key);

    int updateByPrimaryKeySelective(TbFlowPersonConfig record);

    int updateByPrimaryKey(TbFlowPersonConfig record);
    
    public List<Map<String,Object>> getChengBanPersons(Map<String,Object> map);
    
    public String getNextApprovePersons(Map<String,Object> map);
    
    public Map<String,Object> getFlowDeliverType(Map<String,Object> map);
    
    public Map<String,Object> getFlowDeliverNodeName(Map<String,Object> map);
    
    public List<Map<String,Object>> ajaxGetFlowDeliverNodeid(Map<String,Object> map);
    
    public List<Map<String,Object>> getFlowPersonConfigs(Map<String,Object> map);
    
    public int countFlowPersonConfigs(Map<String,Object> map);
    
}
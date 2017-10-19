package com.sinog2c.dao.api.khjc;

import java.util.Map;

import com.sinog2c.model.khjc.KhjcTbflowBaseDocSlave;
import com.sinog2c.model.khjc.KhjcTbflowBaseDocSlaveKey;

public interface KhjcTbflowBaseDocSlaveMapper {
    int deleteByPrimaryKey(KhjcTbflowBaseDocSlaveKey key);

    int insert(KhjcTbflowBaseDocSlave record);

    int insertSelective(KhjcTbflowBaseDocSlave record);

    KhjcTbflowBaseDocSlave selectByPrimaryKey(KhjcTbflowBaseDocSlaveKey key);

    int updateByPrimaryKeySelective(KhjcTbflowBaseDocSlave record);

    int updateByPrimaryKeyWithBLOBs(KhjcTbflowBaseDocSlave record);

    int updateByPrimaryKey(KhjcTbflowBaseDocSlave record);
    
    String selectSNByMap(Map map);
    
    KhjcTbflowBaseDocSlave getKhjcTbflowBaseDocSlaveByCondition(Map map);
    
}
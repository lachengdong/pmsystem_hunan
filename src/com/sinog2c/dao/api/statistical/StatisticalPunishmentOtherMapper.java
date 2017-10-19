package com.sinog2c.dao.api.statistical;

import java.util.Map;

import com.sinog2c.model.statistical.StatisticalPunishmentOther;

public interface StatisticalPunishmentOtherMapper {
    int insert(StatisticalPunishmentOther record);

    int insertSelective(StatisticalPunishmentOther record);
    
    int insertByMap(Map map);
    
    int updateByMap(Map map);
    
}
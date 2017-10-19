package com.sinog2c.dao.api.statistical;

import java.util.Map;

import com.sinog2c.model.statistical.StatisticalPunishmentRelease;

public interface StatisticalPunishmentReleaseMapper {
    int insert(StatisticalPunishmentRelease record);

    int insertSelective(StatisticalPunishmentRelease record);

    int insertByMap(Map map);
    
    int updateByMap(Map map);
}
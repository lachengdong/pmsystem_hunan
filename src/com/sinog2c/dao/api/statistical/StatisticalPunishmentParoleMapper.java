package com.sinog2c.dao.api.statistical;
import java.util.Map;
import com.sinog2c.model.statistical.StatisticalPunishmentParole;

public interface StatisticalPunishmentParoleMapper {
    int insert(StatisticalPunishmentParole record);

    int insertSelective(StatisticalPunishmentParole record);

    int insertByMap(Map map);
    
    int updateByMap(Map map);
}
package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.Propertypunishmentperform;

public interface PropertypunishmentperformMapper {
    int deleteByPrimaryKey(String propertyid);

    int insert(Propertypunishmentperform record);

    int insertSelective(Propertypunishmentperform record);

    Propertypunishmentperform selectByPrimaryKey(String propertyid);

    int updateByPrimaryKeySelective(Propertypunishmentperform record);

    int updateByPrimaryKey(Propertypunishmentperform record);
    List findByProperty(Map map);
    List findByPropertyDetail(Map map);
    int findByPropertyCount(Map map);
    int findByPropertyDetailCount(Map map);
    int findMaxid();
    String findSumPro(Map map);
}
package com.sinog2c.dao.api.khjc;

import com.sinog2c.model.khjc.KhjcDepartScoreSd;

public interface KhjcDepartScoreSdMapper {
    int deleteByPrimaryKey(String id);

    int insert(KhjcDepartScoreSd record);

    int insertSelective(KhjcDepartScoreSd record);

    KhjcDepartScoreSd selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(KhjcDepartScoreSd record);

    int updateByPrimaryKey(KhjcDepartScoreSd record);
}
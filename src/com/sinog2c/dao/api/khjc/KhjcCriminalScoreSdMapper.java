package com.sinog2c.dao.api.khjc;

import java.util.HashMap;
import java.util.Map;

import com.sinog2c.model.khjc.KhjcCriminalScoreSd;

public interface KhjcCriminalScoreSdMapper {
    int deleteByPrimaryKey(String id);

    int insert(KhjcCriminalScoreSd record);

    int insertSelective(KhjcCriminalScoreSd record);

    KhjcCriminalScoreSd selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(KhjcCriminalScoreSd record);

    int updateByPrimaryKey(KhjcCriminalScoreSd record);

	Map<String, Object> getSeq(HashMap map);
}
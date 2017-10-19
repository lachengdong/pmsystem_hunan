package com.sinog2c.dao.api.khjc;

import com.sinog2c.model.khjc.KhjcCriminalMonthScoreBlSd;

public interface KhjcCriminalMonthScoreBlSdMapper {
    int deleteByPrimaryKey(String criminalid);

    int insert(KhjcCriminalMonthScoreBlSd record);

    int insertSelective(KhjcCriminalMonthScoreBlSd record);

    KhjcCriminalMonthScoreBlSd selectByPrimaryKey(String criminalid);

    int updateByPrimaryKeySelective(KhjcCriminalMonthScoreBlSd record);

    int updateByPrimaryKey(KhjcCriminalMonthScoreBlSd record);
}
package com.sinog2c.dao.api.khjc;

import com.sinog2c.model.khjc.KhjcCriminalJiangChengSd;

public interface KhjcCriminalJiangChengSdMapper {
    int deleteByPrimaryKey(String id);

    int insert(KhjcCriminalJiangChengSd record);

    int insertSelective(KhjcCriminalJiangChengSd record);

    KhjcCriminalJiangChengSd selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(KhjcCriminalJiangChengSd record);

    int updateByPrimaryKey(KhjcCriminalJiangChengSd record);
}
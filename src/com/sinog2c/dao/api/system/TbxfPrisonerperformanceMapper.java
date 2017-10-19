package com.sinog2c.dao.api.system;

import com.sinog2c.model.system.TbxfPrisonerperformance;
import com.sinog2c.model.system.TbxfPrisonerperformanceKey;

public interface TbxfPrisonerperformanceMapper {
    int deleteByPrimaryKey(TbxfPrisonerperformanceKey key);

    int insert(TbxfPrisonerperformance record);

    int insertSelective(TbxfPrisonerperformance record);

    TbxfPrisonerperformance selectByPrimaryKey(TbxfPrisonerperformanceKey key);

    int updateByPrimaryKeySelective(TbxfPrisonerperformance record);

    int updateByPrimaryKey(TbxfPrisonerperformance record);
}
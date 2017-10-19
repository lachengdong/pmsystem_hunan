package com.sinog2c.dao.api.commutationParole;

import com.sinog2c.model.commutationParole.TbxfPrisonerPerformanceMerge;
import com.sinog2c.model.commutationParole.TbxfPrisonerPerformanceMergeKey;

public interface TbxfPrisonerPerformanceMergeMapper {
    int deleteByPrimaryKey(TbxfPrisonerPerformanceMergeKey key);

    int insert(TbxfPrisonerPerformanceMerge record);

    int insertSelective(TbxfPrisonerPerformanceMerge record);

    TbxfPrisonerPerformanceMerge selectByPrimaryKey(TbxfPrisonerPerformanceMergeKey key);

    int updateByPrimaryKeySelective(TbxfPrisonerPerformanceMerge record);

    int updateByPrimaryKey(TbxfPrisonerPerformanceMerge record);
}
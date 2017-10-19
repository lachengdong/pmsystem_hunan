package com.sinog2c.dao.api.prisoner;

import com.sinog2c.model.prisoner.TbprisonerFeatures;

public interface TbprisonerFeaturesMapper {
    int deleteByPrimaryKey(String crimid);

    int insertSelective(TbprisonerFeatures record);

    TbprisonerFeatures selectByPrimaryKey(String crimid);

    int updateByPrimaryKeySelective(TbprisonerFeatures record);

}
package com.sinog2c.dao.api.prisoner;

import com.sinog2c.model.prisoner.TbprisonerLanguages;

public interface TbprisonerLanguagesMapper {
    int deleteByPrimaryKey(Integer languid);

    int insert(TbprisonerLanguages record);

    int insertSelective(TbprisonerLanguages record);

    TbprisonerLanguages selectByPrimaryKey(Integer languid);

    int updateByPrimaryKeySelective(TbprisonerLanguages record);

    int updateByPrimaryKey(TbprisonerLanguages record);
}
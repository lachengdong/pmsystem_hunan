package com.sinog2c.dao.api.prisoner;

import com.sinog2c.model.prisoner.TbprisonerCrimename;

public interface TbprisonerCrimenameMapper {
    int deleteByPrimaryKey(Short crimenameid);

    int insert(TbprisonerCrimename record);

    int insertSelective(TbprisonerCrimename record);

    TbprisonerCrimename selectByPrimaryKey(String crimenameid);

    int updateByPrimaryKeySelective(TbprisonerCrimename record);

    int updateByPrimaryKey(TbprisonerCrimename record);
}
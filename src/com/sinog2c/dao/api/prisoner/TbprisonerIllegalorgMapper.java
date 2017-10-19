package com.sinog2c.dao.api.prisoner;

import com.sinog2c.model.prisoner.TbprisonerIllegalorg;

public interface TbprisonerIllegalorgMapper {
    int deleteByPrimaryKey(Integer illid);

    int insert(TbprisonerIllegalorg record);

    int insertSelective(TbprisonerIllegalorg record);

    TbprisonerIllegalorg selectByPrimaryKey(Integer illid);

    int updateByPrimaryKeySelective(TbprisonerIllegalorg record);

    int updateByPrimaryKey(TbprisonerIllegalorg record);
}
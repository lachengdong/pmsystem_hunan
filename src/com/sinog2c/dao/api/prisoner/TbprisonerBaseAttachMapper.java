package com.sinog2c.dao.api.prisoner;

import com.sinog2c.model.prisoner.TbprisonerBaseAttach;

public interface TbprisonerBaseAttachMapper {
    int insert(TbprisonerBaseAttach record);

    int insertSelective(TbprisonerBaseAttach record);
}
package com.sinog2c.dao.api.system;

import com.sinog2c.model.system.TbxfPrisonerhealth;

public interface TbxfPrisonerhealthMapper {
    int deleteByPrimaryKey(Integer phid);

    int insert(TbxfPrisonerhealth record);

    int insertSelective(TbxfPrisonerhealth record);

    TbxfPrisonerhealth selectByPrimaryKey(Integer phid);

    int updateByPrimaryKeySelective(TbxfPrisonerhealth record);

    int updateByPrimaryKey(TbxfPrisonerhealth record);
}
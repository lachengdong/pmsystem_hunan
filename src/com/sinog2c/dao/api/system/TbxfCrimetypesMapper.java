package com.sinog2c.dao.api.system;

import com.sinog2c.model.system.TbxfCrimetypes;

public interface TbxfCrimetypesMapper {
    int deleteByPrimaryKey(Integer typeid);

    int insert(TbxfCrimetypes record);

    int insertSelective(TbxfCrimetypes record);

    TbxfCrimetypes selectByPrimaryKey(Integer typeid);

    int updateByPrimaryKeySelective(TbxfCrimetypes record);

    int updateByPrimaryKey(TbxfCrimetypes record);
}
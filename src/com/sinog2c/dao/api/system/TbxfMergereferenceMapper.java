package com.sinog2c.dao.api.system;

import com.sinog2c.model.system.TbxfMergereference;

public interface TbxfMergereferenceMapper {
    int deleteByPrimaryKey(Integer mergeid);

    int insert(TbxfMergereference record);

    int insertSelective(TbxfMergereference record);

    TbxfMergereference selectByPrimaryKey(Integer mergeid);

    int updateByPrimaryKeySelective(TbxfMergereference record);

    int updateByPrimaryKey(TbxfMergereference record);
}
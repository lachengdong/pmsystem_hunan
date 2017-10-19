package com.sinog2c.dao.api.khjc;

import com.sinog2c.model.khjc.TbxfRetrial;

public interface TbxfRetrialMapper {
    int deleteByPrimaryKey(String docid);

    int insert(TbxfRetrial record);

    int insertSelective(TbxfRetrial record);

    TbxfRetrial selectByPrimaryKey(String docid);

    int updateByPrimaryKeySelective(TbxfRetrial record);

    int updateByPrimaryKey(TbxfRetrial record);
}
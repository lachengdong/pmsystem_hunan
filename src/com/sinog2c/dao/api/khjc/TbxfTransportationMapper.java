package com.sinog2c.dao.api.khjc;

import com.sinog2c.model.khjc.TbxfTransportation;

public interface TbxfTransportationMapper {
    int deleteByPrimaryKey(String docid);

    int insert(TbxfTransportation record);

    int insertSelective(TbxfTransportation record);

    TbxfTransportation selectByPrimaryKey(String docid);

    int updateByPrimaryKeySelective(TbxfTransportation record);

    int updateByPrimaryKey(TbxfTransportation record);
}
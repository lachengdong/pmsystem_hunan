package com.sinog2c.dao.api.chat;

import com.sinog2c.model.chat.iMessageRelation;



public interface iMessageRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(iMessageRelation record);

    int insertSelective(iMessageRelation record);

    iMessageRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(iMessageRelation record);

    int updateByPrimaryKey(iMessageRelation record);
}
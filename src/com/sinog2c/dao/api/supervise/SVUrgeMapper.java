package com.sinog2c.dao.api.supervise;

import com.sinog2c.model.supervise.SVUrge;

public interface SVUrgeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SVUrge record);

    int insertSelective(SVUrge record);

    SVUrge selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SVUrge record);

    int updateByPrimaryKey(SVUrge record);
}
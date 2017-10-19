package com.sinog2c.dao.api.oa;

import com.sinog2c.model.oa.TbOaWcsp;

public interface TbOaWcspMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(TbOaWcsp record);

    int insertSelective(TbOaWcsp record);

    TbOaWcsp selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(TbOaWcsp record);

    int updateByPrimaryKey(TbOaWcsp record);
}
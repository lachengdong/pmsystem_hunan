package com.sinog2c.dao.api.khjc;

import com.sinog2c.model.khjc.BgzlXfzxsqjl;

public interface BgzlXfzxsqjlMapper {
    int deleteByPrimaryKey(String id);

    int insert(BgzlXfzxsqjl record);

    int insertSelective(BgzlXfzxsqjl record);

    BgzlXfzxsqjl selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BgzlXfzxsqjl record);

    int updateByPrimaryKey(BgzlXfzxsqjl record);
}
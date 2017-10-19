package com.sinog2c.dao.api.khjc;

import com.sinog2c.model.khjc.BgzlXfzxgzjl;

public interface BgzlXfzxgzjlMapper {
    int deleteByPrimaryKey(String id);

    int insert(BgzlXfzxgzjl record);

    int insertSelective(BgzlXfzxgzjl record);

    BgzlXfzxgzjl selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BgzlXfzxgzjl record);

    int updateByPrimaryKey(BgzlXfzxgzjl record);
}
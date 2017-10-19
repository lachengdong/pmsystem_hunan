package com.sinog2c.dao.api.khjc;

import com.sinog2c.model.khjc.KhjcJiYaoMingXi;

public interface KhjcJiYaoMingXiMapper {
    int deleteByPrimaryKey(String id);

    int insert(KhjcJiYaoMingXi record);

    int insertSelective(KhjcJiYaoMingXi record);

    KhjcJiYaoMingXi selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(KhjcJiYaoMingXi record);

    int updateByPrimaryKeyWithBLOBs(KhjcJiYaoMingXi record);
}
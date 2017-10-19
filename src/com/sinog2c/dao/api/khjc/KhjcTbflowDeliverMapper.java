package com.sinog2c.dao.api.khjc;

import com.sinog2c.model.khjc.KhjcTbflowDeliver;
import com.sinog2c.model.khjc.KhjcTbflowDeliverKey;

public interface KhjcTbflowDeliverMapper {
    int deleteByPrimaryKey(KhjcTbflowDeliverKey key);

    int insert(KhjcTbflowDeliver record);

    int insertSelective(KhjcTbflowDeliver record);

    KhjcTbflowDeliver selectByPrimaryKey(KhjcTbflowDeliverKey key);

    int updateByPrimaryKeySelective(KhjcTbflowDeliver record);

    int updateByPrimaryKey(KhjcTbflowDeliver record);
}
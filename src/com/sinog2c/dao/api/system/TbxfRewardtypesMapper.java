package com.sinog2c.dao.api.system;

import com.sinog2c.model.system.TbxfRewardtypes;
import java.math.BigDecimal;

public interface TbxfRewardtypesMapper {
    int deleteByPrimaryKey(BigDecimal rewardid);

    int insert(TbxfRewardtypes record);

    int insertSelective(TbxfRewardtypes record);

    TbxfRewardtypes selectByPrimaryKey(BigDecimal rewardid);

    int updateByPrimaryKeySelective(TbxfRewardtypes record);

    int updateByPrimaryKey(TbxfRewardtypes record);
}
package com.sinog2c.dao.api.commutationParole;

import com.sinog2c.model.commutationParole.TbxfRewardTypes;
import java.math.BigDecimal;

public interface TbxfRewardTypesMapper {
    int deleteByPrimaryKey(BigDecimal rewardid);

    int insert(TbxfRewardTypes record);

    int insertSelective(TbxfRewardTypes record);

    TbxfRewardTypes selectByPrimaryKey(BigDecimal rewardid);

    int updateByPrimaryKeySelective(TbxfRewardTypes record);

    int updateByPrimaryKey(TbxfRewardTypes record);
}
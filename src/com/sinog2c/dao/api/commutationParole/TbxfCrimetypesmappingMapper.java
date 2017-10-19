package com.sinog2c.dao.api.commutationParole;

import com.sinog2c.model.commutationParole.TbxfCrimetypesmapping;
import com.sinog2c.model.commutationParole.TbxfCrimetypesmappingKey;

public interface TbxfCrimetypesmappingMapper {
    int deleteByPrimaryKey(TbxfCrimetypesmappingKey key);

    int insert(TbxfCrimetypesmapping record);

    int insertSelective(TbxfCrimetypesmapping record);

    TbxfCrimetypesmapping selectByPrimaryKey(TbxfCrimetypesmappingKey key);

    int updateByPrimaryKeySelective(TbxfCrimetypesmapping record);

    int updateByPrimaryKey(TbxfCrimetypesmapping record);
}
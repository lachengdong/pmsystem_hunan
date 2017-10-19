package com.sinog2c.dao.api.commutationParole;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.commutationParole.TbxfScreeningProperty;
import com.sinog2c.model.commutationParole.TbxfScreeningPropertyKey;

public interface TbxfScreeningPropertyMapper {
    int deleteByPrimaryKey(TbxfScreeningPropertyKey key);

    int insert(TbxfScreeningProperty record);

    int insertSelective(TbxfScreeningProperty record);

    TbxfScreeningProperty selectByPrimaryKey(TbxfScreeningPropertyKey key);

    List<TbxfScreeningProperty> selectByDepartid(Map map);

    int updateByPrimaryKeySelective(TbxfScreeningProperty record);

    int updateByPrimaryKey(TbxfScreeningProperty record);
}
package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.TbxfPunishmentrang;

public interface TbxfPunishmentrangMapper {
    int deleteByPrimaryKey(Integer punid);

    int insert(TbxfPunishmentrang record);

    int insertSelective(TbxfPunishmentrang record);

    TbxfPunishmentrang selectByPrimaryKey(Integer punid);

    int updateByPrimaryKeySelective(TbxfPunishmentrang record);

    int updateByPrimaryKey(TbxfPunishmentrang record);
    
    int countBySql(String sql);
    
    List<Map> searchBySql(String sql);
    
}
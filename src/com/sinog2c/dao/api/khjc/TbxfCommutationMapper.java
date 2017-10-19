package com.sinog2c.dao.api.khjc;

import java.util.Map;

import com.sinog2c.model.khjc.TbxfCommutation;

public interface TbxfCommutationMapper {
    int deleteByPrimaryKey(String docid);

    int insert(TbxfCommutation record);

    int insertSelective(TbxfCommutation record);
    
    int insertSelectiveByMap(Map map);

    TbxfCommutation selectByPrimaryKey(String docid);

    int updateByPrimaryKeySelective(TbxfCommutation record);

    int updateByPrimaryKey(TbxfCommutation record);
    
    /**
     * 获取减刑假释最大案件号
     * @return
     */
    public String getMaxCommuteCaseNo(Map map);
    
}
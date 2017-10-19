package com.sinog2c.dao.api.khjc;

import java.util.Map;

import com.sinog2c.model.khjc.TbxfOutExecute;

public interface TbxfOutExecuteMapper {
    int deleteByPrimaryKey(String docid);

    int insert(TbxfOutExecute record);

    int insertSelective(TbxfOutExecute record);
    
    int insertSelectiveByMap(Map map);

    TbxfOutExecute selectByPrimaryKey(String docid);

    int updateByPrimaryKeySelective(TbxfOutExecute record);

    int updateByPrimaryKey(TbxfOutExecute record);
    
    String getMaxOutExecuteCaseNo(Map map);
    
}
package com.sinog2c.dao.api.commutationParole;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.commutationParole.ZfsqZfsqfd;

public interface ZfsqZfsqfdMapper {
    int deleteByPrimaryKey(String crimid);

    int insert(ZfsqZfsqfd record);

    int insertSelective(ZfsqZfsqfd record);
    
    int insertByMap(Map<String,Object> map);

    ZfsqZfsqfd selectByPrimaryKey(String crimid);

    int updateByPrimaryKeySelective(ZfsqZfsqfd record);

    int updateByPrimaryKey(ZfsqZfsqfd record);
    
    int updateByMap(Map<String,Object> map);
    
    int getCount(Map<String, Object> map);
    
    List<Map<String,Object>> getSentenceAlterationList(Map<String, Object> map);
    
    String selectMaxId();

	ZfsqZfsqfd selectByCrimidAndBatchId(Map map);
}
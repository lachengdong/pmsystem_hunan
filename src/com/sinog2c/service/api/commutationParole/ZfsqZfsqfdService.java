package com.sinog2c.service.api.commutationParole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinog2c.model.commutationParole.ZfsqZfsqfd;


public interface ZfsqZfsqfdService {

	int deleteByPrimaryKey(String crimid);

    int insert(ZfsqZfsqfd record);

    int insertSelective(ZfsqZfsqfd record);
    
    int insertByMap(Map<String,Object> map);

    ZfsqZfsqfd selectByPrimaryKey(String crimid);

    int updateByPrimaryKeySelective(ZfsqZfsqfd record);

    int updateByMap(Map<String,Object> map);
    
    int updateByPrimaryKey(ZfsqZfsqfd record);
    
    int getCount(Map<String,Object> map);
    
    List<Map<String, Object>> getSentenceAlterationList(Map<String,Object> map);
    
    ZfsqZfsqfd selectByCrimidAndBatchId(Map map);
}

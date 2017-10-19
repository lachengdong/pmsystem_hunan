package com.sinog2c.dao.api.prisoner;

import java.util.Map;

import com.sinog2c.model.prisoner.ZpublicDaMtxx;
import com.sinog2c.model.prisoner.ZpublicDaMtxxKey;

public interface ZpublicDaMtxxMapper {
    int deleteByPrimaryKey(ZpublicDaMtxxKey key);

    int insert(ZpublicDaMtxx record);

    int insertSelective(ZpublicDaMtxx record);

    ZpublicDaMtxx selectByPrimaryKey(Map<String,Object> map);

    int updateByPrimaryKeySelective(ZpublicDaMtxx record);

    int updateByPrimaryKeyWithBLOBs(ZpublicDaMtxx record);

    int updateByPrimaryKey(ZpublicDaMtxx record);
    
    int gettNextID();
    
}
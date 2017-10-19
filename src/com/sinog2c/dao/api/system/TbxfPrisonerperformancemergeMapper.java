package com.sinog2c.dao.api.system;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sinog2c.model.system.TbxfPrisonerperformancemerge;
import com.sinog2c.model.system.TbxfPrisonerperformancemergeKey;

@Repository("tbxfPrisonerperformancemergeMapper")
public interface TbxfPrisonerperformancemergeMapper {
    int deleteByPrimaryKey(TbxfPrisonerperformancemergeKey key);

    int insert(TbxfPrisonerperformancemerge record);

    int insertSelective(TbxfPrisonerperformancemerge record);

    TbxfPrisonerperformancemerge selectByPrimaryKey(TbxfPrisonerperformancemergeKey key);

    int updateByPrimaryKeySelective(TbxfPrisonerperformancemerge record);

    int updateByPrimaryKey(TbxfPrisonerperformancemerge record);
    
    TbxfPrisonerperformancemerge selectByCrimidAndBatchid(Map map);
    
    String getDepartidByCrimid(String str);
}
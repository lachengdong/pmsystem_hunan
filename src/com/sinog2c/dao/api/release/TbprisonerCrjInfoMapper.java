package com.sinog2c.dao.api.release;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sinog2c.model.release.TBPRISONERCRJINFO;

@Repository
public interface TbprisonerCrjInfoMapper {
    int deleteByPrimaryKey(String crimid);

    int insert(TBPRISONERCRJINFO record);

    int insertSelective(TBPRISONERCRJINFO record);

    TBPRISONERCRJINFO selectByPrimaryKey(String crimid);

    int updateByPrimaryKeySelective(TBPRISONERCRJINFO record);

    int updateByPrimaryKey(TBPRISONERCRJINFO record);
    
    List<Map> ajaxCodeData(Map map);
    
    int savePrison(Map map);
    
    List<Map> getOutPrison(String crimid);
}
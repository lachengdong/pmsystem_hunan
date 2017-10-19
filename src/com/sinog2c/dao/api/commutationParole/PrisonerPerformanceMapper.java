package com.sinog2c.dao.api.commutationParole;

import java.util.HashMap;
import java.util.List;

import com.sinog2c.model.commutationParole.PrisonerPerformance;
import com.sinog2c.model.commutationParole.PrisonerPerformanceExample;
import com.sinog2c.model.commutationParole.PrisonerPerformanceKey;
import org.apache.ibatis.annotations.Param;

public interface PrisonerPerformanceMapper {
    int deleteByPrimaryKey(PrisonerPerformanceKey key);

    int insert(PrisonerPerformance record);

    int insertSelective(PrisonerPerformance record);

    PrisonerPerformance selectByPrimaryKey(PrisonerPerformanceKey key);

    int updateByExampleSelective(@Param("record") PrisonerPerformance record, @Param("example") PrisonerPerformanceExample example);

    int updateByExample(@Param("record") PrisonerPerformance record, @Param("example") PrisonerPerformanceExample example);

    int updateByPrimaryKeySelective(PrisonerPerformance record);

    int updateByPrimaryKey(PrisonerPerformance record);
    
    int getCount(HashMap map);
    
    List<HashMap> getPrisonerPerformanceList(HashMap map);
    
}
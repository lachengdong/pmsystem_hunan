package com.sinog2c.dao.api.flow;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.flow.FlowArchivesExist;

public interface FlowArchivesExistMapper {
	
    int insertSelective(FlowArchivesExist record);

    int updateByExample(FlowArchivesExist record);
     
    int removeByArchid(@Param("archiveid")String archiveid);
}
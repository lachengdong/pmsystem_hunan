package com.sinog2c.dao.api.arch;

import java.util.List;

import com.sinog2c.model.arch.ArchTree;

public interface ArchTreeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArchTree record);

    int insertSelective(ArchTree record);

    ArchTree selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArchTree record);

    int updateByPrimaryKey(ArchTree record);
    
    List<ArchTree> getAllArchFolder();
    
    Long getNextId();
}
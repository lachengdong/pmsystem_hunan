package com.sinog2c.dao.api.arch;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.arch.ArchFile;

public interface ArchFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArchFile record);

    int insertSelective(ArchFile record);

    ArchFile selectByPrimaryKey(Long id);
    
    ArchFile getArchFileByid(Long id);

    int updateByPrimaryKeySelective(ArchFile record);

    int updateByPrimaryKey(ArchFile record);
    
    int updatearchfilebox(Long box_id);
    
    List<ArchFile> getarchfilebycondition(Map<String, Object> map);   
    
    int getcountofarchfilebycondition(Map<String,Object> map);
    
    int setArchflag(Long box_id);
    
    Long getNextId();
}
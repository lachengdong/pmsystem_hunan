package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sinog2c.model.system.TbsysFormDetails;

@Repository("tbsysFormDetailsMapper")
public interface TbsysFormDetailsMapper {
    int insert(TbsysFormDetails record);

    int insertSelective(TbsysFormDetails record);
    
    List<TbsysFormDetails> getFormDetails(Map<String,Object> map);
    
}
package com.sinog2c.dao.api.dbmsnew;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sinog2c.model.dbmsnew.TbsysExchangeScheme;

@Repository
public interface TbsysExchangeSchemeMapper {
    int deleteByPrimaryKey(Integer excid);

    int insert(TbsysExchangeScheme record);

    int insertSelective(TbsysExchangeScheme record);

    TbsysExchangeScheme selectByPrimaryKey(Integer excid);

    int updateByPrimaryKeySelective(TbsysExchangeScheme record);

    int updateByPrimaryKey(TbsysExchangeScheme record);
    
    List<TbsysExchangeScheme> getListByPrimaryKey(Map<String,Object> map);
    
}
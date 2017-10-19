package com.sinog2c.dao.api.dbmsnew;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.dbmsnew.TbsysServices;

public interface TbsysServicesMapper {
    int deleteByPrimaryKey(String departid);

    int insert(TbsysServices record);

    int insertSelective(TbsysServices record);

    TbsysServices selectByPrimaryKey(String departid);

    int updateByPrimaryKeySelective(TbsysServices record);

    int updateByPrimaryKey(TbsysServices record);
    
    Map<String, Object> selectByDepartid(String str);
    
    void callProcedures();
    
    List<Map> getAllServies(Map map);
}
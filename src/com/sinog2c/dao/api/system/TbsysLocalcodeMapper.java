package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.TbsysLocalcode;

public interface TbsysLocalcodeMapper {
    int deleteByPrimaryKey(String noid);

    int insert(TbsysLocalcode record);

    int insertSelective(TbsysLocalcode record);

    TbsysLocalcode selectByPrimaryKey(String noid);

    int updateByPrimaryKeySelective(TbsysLocalcode record);

    int updateByPrimaryKey(TbsysLocalcode record);
    
    List<TbsysLocalcode> getLocalcodeList(Map<String,Object> map);
}
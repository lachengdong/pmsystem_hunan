package com.sinog2c.dao.api.prisoner;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.prisoner.TbxfKpcj;

public interface TbxfKpcjMapper {
    int deleteByPrimaryKey(String propertyid);

    int insert(TbxfKpcj record);

    int insertSelective(TbxfKpcj record);

    TbxfKpcj selectByPrimaryKey(String propertyid);

    int updateByPrimaryKeySelective(TbxfKpcj record);

    int updateByPrimaryKey(TbxfKpcj record);
    String selectKPCJMaxidByCrimid();
    List findByKPCJDetail(Map map);
	int findByKPCJDetailCount(Map map);
}
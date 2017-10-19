package com.sinog2c.dao.api.prisoner;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.prisoner.TbxfLaboreducation;

public interface TbxfLaboreducationMapper {
	String getLaborid();
	 
    int deleteByPrimaryKey(String laborid);

    int insertSelective(TbxfLaboreducation record);

    TbxfLaboreducation selectByPrimaryKey(String laborid);

    int updateByPrimaryKeySelective(TbxfLaboreducation record);

    public TbxfLaboreducation findLaboreducationBycrimid(
    	@Param("crimid")String crimid,@Param("year")String year,@Param("tempid")String tempid);

}
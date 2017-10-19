package com.sinog2c.dao.api.commutationParole;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.commutationParole.TbxfWideandthinscheme;

public interface TbxfWideandthinschemeMapper {
    int deleteByPrimaryKey(Integer lssid);

    int insert(TbxfWideandthinscheme record);

    int insertSelective(TbxfWideandthinscheme record);

    TbxfWideandthinscheme selectByPrimaryKey(Integer lssid);
    
    int deleteByPkstr(@Param("punid")String punid,@Param("toid")String toid);
    
    int insertByPkstr(@Param("punid")String punid,@Param("toid")String toid);

    int updateByPrimaryKeySelective(TbxfWideandthinscheme record);

    int updateByPrimaryKey(TbxfWideandthinscheme record);
    
    List<Map> ajaxByWideandthins();
}
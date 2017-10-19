package com.sinog2c.dao.api.commutationParole;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.commutationParole.TbxfPrisonerSentence;

public interface TbxfPrisonerSentenceMapper {
    int deleteByPrimaryKey(Integer senid);

    int insert(TbxfPrisonerSentence record);

    int insertSelective(TbxfPrisonerSentence record);
    
    int insertByMap(Map map);

    TbxfPrisonerSentence selectByPrimaryKey(Integer senid);

    int updateByPrimaryKeySelective(TbxfPrisonerSentence record);

    int updateByPrimaryKey(TbxfPrisonerSentence record);
    
    int updateByMap(Map map);
    
    int countSentenc(Map map);
    
    int getKey();
    
    List<Map> selectSentenc(Map map);
    
    Map getSentencInfo(@Param("senid")Integer senid);
    
}
package com.sinog2c.dao.api.commutationParole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.commutationParole.TbxfPunishmentRang;

public interface TbxfPunishmentRangMapper {
    int deleteByPrimaryKey(Integer punid);

    int insert(TbxfPunishmentRang record);

    TbxfPunishmentRang selectByPrimaryKey(Integer punid);

    int updateByPrimaryKey(TbxfPunishmentRang record);
    
    int selectCount(@Param("departid")String departid, @Param("key")String key);
    
    List<HashMap> selectDataList(@Param("departid")String departid, @Param("key")String key, @Param("sortField")String sortField, @Param("sortOrder")String sortOrder, @Param("start")int start, @Param("end")int end);
    
    List<HashMap> ajaxSelectData(@Param("id")String id, @Param("text")String text, @Param("tableName")String tableName);
    
    List<HashMap> selectPrisonerSentence(@Param("departid")String departid);
    
    HashMap getPrisonersentenceById(@Param("senid")Integer senid);
    
    int deleteCommutationreferenceByPunid(@Param("punid")Integer punid);
    
    int deleteMergereferenceByPunid(@Param("punid")Integer punid);
    
    int deleteWideandthinschemeByPunid(@Param("punid")Integer punid);
    
    int insertTbxfWideandthinscheme(Map map);
    List<HashMap<String,Object>> getSchemeDepart(@Param("depart")String depart);
    
    List<TbxfPunishmentRang> getrangdataBydepartid(@Param("departid")String departid);
    
    int getPunid();
    
    int insertAuto (TbxfPunishmentRang record);
    
    int updateByPrimaryKeySelective(TbxfPunishmentRang record);
    
    int insertBySql(Map<String,Object> map);
    
    public List<Map<String,Object>> getAllQualifierItem(Map<String,Object> map);
    
    
    
    
    
    
    public List<Map<String,Object>> getQualifierSet(Map<String,Object> map);
    
    public List<Map<String,Object>> getQualifierItem(Map<String,Object> map);
    
    int insertQualifierSet(Map<String,Object> map);
    
    int updateQualifierSet(Map<String,Object> map);
    
    int removeQualifierSet(Map<String,Object> map);
    
    int insertQualifierItem(Map<String,Object> map);
    
    int updateQualifierItem(Map<String,Object> map);
    
    int updateQualifierItemFormula(Map<String,Object> map);
    
    int removeQualifierItem(Map<String,Object> map);
}
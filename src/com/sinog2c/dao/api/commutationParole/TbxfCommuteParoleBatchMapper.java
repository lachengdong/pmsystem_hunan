package com.sinog2c.dao.api.commutationParole;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.commutationParole.TbxfCommuteParoleBatch;

public interface TbxfCommuteParoleBatchMapper {
	
    int deleteByPrimaryKey(Long batchid);

    int insert(TbxfCommuteParoleBatch record);

    int insertSelective(TbxfCommuteParoleBatch record);

    TbxfCommuteParoleBatch selectByPrimaryKey(Integer batchid);

    int updateByPrimaryKeySelective(TbxfCommuteParoleBatch record);

    int updateByPrimaryKey(TbxfCommuteParoleBatch record);
    
    int getgetCommuteParoleBatchCount(@Param("departid")String departid, @Param("key")String key);
    
    List<TbxfCommuteParoleBatch> getCommuteParoleBatchList(@Param("departid")String departid, @Param("key")String key, @Param("sortField")String sortField, @Param("sortOrder")String sortOrder, @Param("start")int start, @Param("end")int end);
    
    Map<String,Object> getCommuteParoleBatchInfo(Map<String,Object> map);
   // Map getMaxData(Map map);
    
    void callStoredProcedure(@Param("departid")String departid);
    
    void callSenotherinfoProcedure(@Param("departid")String departid);
    void callPublicSenotherinfoProcedure(@Param("departid")String departid);
    void callPublicCallBackProcedure(@Param("departid")String departid);
    
    void callPunishmentProcedure(@Param("departid")String departid);
    
    void callCriminaltypemappingProcedure(@Param("departid")String departid);
    
    int selectMaxBatch(@Param("departid")String departid,@Param("year")String year);
    
    Map<String,Object> getCurrentCommuteParoleBatch(Map<String,Object> map);
    
    List<Map<String,Object>> getIntervaldateList(Map<String,Object> map);
    
    List<Map<String,Object>> getSurplusSentence(Map<String,Object> map);
    
    List<String> selectdepartidlist();
    
    void callShStoredProcedure(@Param("departid")String departid);
    
    void callPublicStoredProcedure(@Param("departid")String departid);
    
    int checkUnfinishedCase(@Param("departid")String departid);
    
    int deleteMergeByBatchid(@Param("batchid")Long batchid);
    
    void shengChengData1(Map map);
    
    void shengChengData2(Map map);
    
    List<Map> shengChengData3();
    void clearMaxBatch(Map<String,Object> map);
    void updateMaxBatch(Map<String,Object> map);
    void updateCrimeType(Map<String,Object> map);
    
    void callCPScreeingDataProcedure(@Param("departid")String departid,@Param("id")String id,@Param("userid")String userid);

    void generateQualificationList(@Param("departid")String departid);
    
    int deleteQualifierTemp(@Param("departid")String departid);
    
    List<Map<String,Object>> getKaoHeNum4HuNan(@Param("departid")String departid);
    
    List<Map<String,Object>> getKaoHeNum4HuNanSingle(@Param("crimid")String crimid);
    
    List<Map<String,Object>> getJiangLiInfo4HuNan(@Param("departid")String departid);
    
    List<Map<String,Object>> getJiangLiInfo4HuNanSingle(@Param("crimid")String crimid);
	
	List<Map<String,Object>> getKoufenInfoList4HuNan(@Param("departid")String departid);
	
	List<Map<String,Object>> getKoufenInfoList4HuNanSingle(@Param("crimid")String crimid);
	
	List<Map<String,Object>> getJiJiFenziList4HuNan(@Param("departid")String departid);
	
	List<Map<String,Object>> getJiJiFenziList4HuNanSingle(@Param("crimid")String crimid);
	
	void callProcInitSentenceData(Map<String,Object> paramap);
	
}
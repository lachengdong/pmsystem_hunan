package com.sinog2c.dao.api.commutationParole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.commutationParole.TbdataSentchage;
import com.sinog2c.model.commutationParole.TbdataSentchageExample;
import com.sinog2c.model.commutationParole.TbdataSentchageKey;


public interface TbdataSentchageMapper {
    int deleteByPrimaryKey(TbdataSentchageKey key);

    int insert(TbdataSentchage record);

    int insertSelective(TbdataSentchage record);

    TbdataSentchage selectByPrimaryKey(TbdataSentchageKey key);

    int updateByExampleSelective(@Param("record") TbdataSentchage record, @Param("example") TbdataSentchageExample example);

    int updateByExample(@Param("record") TbdataSentchage record, @Param("example") TbdataSentchageExample example);

    int updateByPrimaryKeySelective(TbdataSentchage record);
    
    int updateDataSentenceAwardend(Map<String,Object> map);

    int updateByPrimaryKey(TbdataSentchage record);
    
    List<TbdataSentchage> selectDataList(Map<String,Object> map);
    
    List<Map<String,Object>> getSentenceChangeData(Map<String,Object> map);
    
    int selectDataListCount(Map<String,Object> map);
    
    int countSentenceChangeData(Map<String,Object> map);
    
    int updateSentenceChangeKHZRDate(Map<String,Object> map);
    
    int updateKHZR(Map<String,Object> map);
    int setCrimeTypesMapping(Map<String,Object> map);
    int deleteCrimeTypesMapping(Map<String,Object> map);
    Map<String,Object> viewScreeningExcuse(Map<String,Object> map);
    void callREWARDSTARTProcedure();
    int updateByExampleSelective(TbdataSentchage record);
    TbdataSentchage selectDataByPk(Map<String,Object> map);
    
    TbdataSentchage selectDataByUuid(Map<String,Object> map);
    
    
    List<Map<String,Object>>  selectDataBycrimid(Map<String,Object> map);
    List<Map>  selectAllBycrimidAndCategory(Map<String,Object> map);
    Map  selectAllBycrimidAndCategoryAndCourtsanction(Map<String,Object> map);
    List<TbdataSentchage> getLastCommutationData(Map<String,Object> map);
    int getLastCommutationDataCount(Map<String,Object> map);
    int getOrgSentenceInfoCount(Map<String,Object> map);
    List<Map> getOrgSentenceInfoList(Map<String,Object> map);
    /*
     * 根据罪犯编号查询刑期变动次数
     */
    int getSentenceCountByCrimid(String crimid);
    /*
     * 根据罪犯编号查询第一次刑期变动信息
     */
    TbdataSentchage getCheckendByCrimid(String crimid);

	List<String> getCourtInfoByCrimid(String crimid);
	
    public int getCrimQpbTeaCount(@Param("crimid")String crimid);
	
	public List<HashMap> getCrimQpbTeaList(@Param("crimid")String crimid, @Param("sortField")String sortField, @Param("sortOrder")String sortOrder, @Param("start")int start, @Param("end")int end);
	
    public int getConsumeCount(@Param("crimid")String crimid);
	
	public List<HashMap> getConsumeList(@Param("crimid")String crimid, @Param("sortField")String sortField, @Param("sortOrder")String sortOrder, @Param("start")int start, @Param("end")int end);
	
	
	int insertCrimQpb(Map<String,Object> map);
	int insertCrimSjgzjj(Map<String,Object> map);
	int insertCrimTea(Map<String,Object> map);
	int deleteCrimQpb(Map<String,Object> map);
	int deleteCrimSjgzjj(Map<String,Object> map);
	int deleteCrimTea(Map<String,Object> map);
	List<Map<String,Object>> getSentenceChangedData(Map<String,Object> map);
	int countSentenceChangedData(Map<String,Object> map);
	
	int updateBianDongFuDuByMap(Map<String,Object> map);
	
	List<String> selectJxCrimid(Map map);

	int updateAwardends(Map map);
	
    int countAllBianDongByCondition(Map<String,Object> map);
    
    int countSentenceChangeByBatch(Map<String,Object> map);
    
	List<Map> getBasicBianDongInfoList(Map<String,Object> map);
	
	List<Map<String,Object>> getSentenceChangeByBatch(Map<String,Object> map);
	

	String getZhiXingDate(Map map);

	String getXianXingQi(Map map);
	
	void manual_UpdateSentncechang(Map map);
	
	int removeSentenceChange(Map map);
	
	int removeJiFenBuLuById(Map map);
	
	 List<Map> getJiFenBuLuById(Map<String,String> map);
	
	int insertJiFenBuLu(Map<String,Object> map);
	
	 int updateJiFenBuLuById(Map<String,Object> map);
	 
	 
	 int insertKaoHeZongFen(Map<String,Object> map);
		
	 int updateKaoHeZongFenById(Map<String,Object> map);
	 
	 List<Map<String,Object>> getKaoHeZongFenByCrimid(Map<String,Object> map);
	 
	 List<Map> getCaiDingList(Map map);
	
	 int updateSentenceAlterationRewardinfo(Map<String,Object> map);
	 
	 public int updateOriginalSentenceData(@Param("crimid")String crimid);
	 
}
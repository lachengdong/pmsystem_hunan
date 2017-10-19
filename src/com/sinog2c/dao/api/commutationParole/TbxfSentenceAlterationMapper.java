package com.sinog2c.dao.api.commutationParole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;

public interface TbxfSentenceAlterationMapper {
	
    int deleteByPrimaryKey(String crimid);

    int insert(TbxfSentenceAlteration record);

    int insertSelective(TbxfSentenceAlteration record);

    TbxfSentenceAlteration selectByPrimaryKey(String crimid);

    int updateByPrimaryKeySelective(TbxfSentenceAlteration record);

    int updateByPrimaryKey(TbxfSentenceAlteration record);
    
    int updateSentenceAlterationRewardstart(Map map);
    
    List<TbxfSentenceAlteration> sentenceAlterationList(@Param("departid")String departid);
    
    int getCount(HashMap<String, Object> map);
    
    List<Map<String,Object>> getSentenceAlterationList(HashMap<String, Object> map);
    
    int getCount_nx(HashMap<String, Object> map);
    
    List<HashMap> getSentenceAlterationList_nx(HashMap<String, Object> map);
    
    List<HashMap<Object,Object>> getGuidangCrimeBaseInfo(HashMap<Object,Object> map);
    
    int countByGuidangCrimeBaseInfo(HashMap<Object,Object> map);
    
    List<Map> selectTbxfs(Map<String,Object> map);
    
    int allCount(Map<String,Object> map);
    
    Map<String,Object> selectTbxfByCrimid(String crimid);
    
    
    int provinceLiAnCount(Map map);
    
    List<Map> getParoleByCrimid(@Param("crimid")String crimid);
    
    List<Map> provinceLiAnList(Map map);
    
    List getJailList(Map map);
    
    List getJailListUnderCourt(Map map);
    
    public int countOfficeShenHeList(Map map);
    
    public List<Map> officeShenHeList(Map map);
    /**
     * 省局案件查看
     */
    List<Map> getSJCaseList(Map map);
    int getSJCaseCount(Map map);
    /**
     * 保外就医案件查看
     */
    List<Map> getByjyCaseList(Map map);
    int getByjyCaseCount(Map map);
    
    Map selectTbxfMapBySql(@Param("sql")String sql);
    
    public Map getSuggestDocumentInfoOfCrim(Map map);
    List<Map<String,Object>> selectTbxfs2(Map<String,Object> map);
    
    List<Map<String,Object>> imselectTbxfs2(Map<String,Object> map);
    
    List<Map<String,Object>> selectTbxfs3(Map<String,Object> map);

	int imselectTbxfs2Count(Map<String,Object> map);
	
	int selectTbxfs2Count(Map<String,Object> map);

	int selectTbxfs3Count(Map<String,Object> map);
	
	
//	List<Map<String,Object>> selectTbxfs2_sx(Map<String,Object> map);
//	int selectTbxfs2Count_sx(Map<String,Object> map);
	/**
	 * 流程审批通过后更新刑期变动表
	 */
	void autoUpdateSentenceChangeData();
	
	Map<String,Object> isLiGongExecute();
	
	void updateLiGongData(List<Map<String,Object>> list);
	
	void updateHaseDealStatus(List<Map<String,Object>> list);
	
	List<Map<String,Object>> getLiGongData();
	
	public int countSuggestionHandleList(Map map);
	
	public int SelectWaitOutPrisonListCount(Map map);
	
	public List<Map> findSuggestionHandleList(Map map);
	
	void updateRewardDate(Map<String, Object> map);
	
	List<Map> SelectWaitOutPrisonList(Map map);
	
	List<Map> SelectOutPrisonList(Map map);

	public int SelectOutPrisonListCount(Map map);
	
	List<String> departidlist();
	
	List<TbxfSentenceAlteration> sentenceAlterationLists(Map map);
	
	List<TbxfSentenceAlteration> sentenceAlterationListByJailid(@Param("departid")String departid);
	
	List<Map> getAreaidList(Map map);
	
	public int countCourtLiAnList(Map map);
	
	public List<Map> getCourtLiAnList(Map map);
	
	public int countCourtReportLiAnList(Map map);
	
	public List<Map> getCourtReportLiAnList(Map map);
	
	public int countCourtCheckLiAnList(Map map);
	
	public List<Map> getCourtCheckLiAnList(Map map);
		
	public int deleteTbflowbaseByflowdraftids(Map map);
	
	public int deleteTbflowByflowdraftids(Map map);
	
	public int deleteTbflowOhterByflowdraftids(Map map);
	
	public int deleteTbflowBaseOhterByflowdraftids(Map map);
	
	public int updateDetainStatusByCrimds(Map map);
	
	public int updateStatusByCrimids(Map map);
	
	public Map getCourtCaseBaseInfoOfCrimid(Map map);
	
	public void batchUpdateNowpunishment(List<Map> list);
	
	public void batchUpdateInterval(List<Map<String,Object>> list);
	
	public void batchUpdateRewardDate(List<Map<String,Object>> list);
	
	//更新考核起日
	public void batchUpdateRewardStart(String departid);
	
	public int getSentenceinfoCount(Map<String,Object> map);
	
	public List<TbxfSentenceAlteration> getSentenceinfoList(Map<String,Object> map);
	
	public String getReportauditByCrimid(String crimid);
	
	public List<Map> getBWCaseDataInfo(Map map);
	 /**
     * 获取刑罚统计数据信息
     */
	public List<HashMap<Object,Object>> getStatisticalPunishment(HashMap<Object,Object> map);
	
	public int getBWCaseDataInfoCount(Map map); 
	
	Map<String,Object> getThreeOfCrimBaseinfo(@Param("crimid")String crimid);
	
//	List<Map> getReferenceList_sx(Map map); 
	
	List<Map<String,Object>> getPunishmentChangeinfo(@Param("crimid")String crimid);
	
	public List<Map> getCourtLocalCaseData(Map map);
	
	public int getCourtLocalCaseDataCount(Map map);

	Map<String,Object> getNowYuXing(Map<String, Object> map);
	
	Map<String,Object> getCommutationaccordByCrimid(Map<String,Object> map);
	
	public List<Map> bwjyLookCaseDataInfoMapper(Map map);
	
	public int bwjyLookCaseDataInfoCount(Map map);

	void autoUpdateSentenceChangeJailto(@Param("jailto")String jailto,@Param("crimid")String crimid);

	Map getCourtFuyiInfo(Map paraMap);
	
	Map judgeBwCaseExistSave(Map map);

	String getFuxingbiaoxianByCrimid(@Param("crimid")String crimid);

	int countCourtJianduLiAnList(Map<String, Object> map);
	
	int saveThreeKindInfoOfSentence(Map<String, Object> map);

	List<Map> getCourtJianduLiAnList(Map<String, Object> map);
	
    List<Map> bwgetCaseCriminalInfoServiceImplList(Map mapParameter);
	
    int bwgetCaseCriminalInfoServiceImplCount(Map mapParameter);
    
    HashMap ajaxtongji1(Map map);
    HashMap ajaxtongji2(Map map);
    HashMap ajaxtongji3(Map map);
    HashMap ajaxtongji4(Map map);
    HashMap ajaxtongji5(Map map);
    HashMap ajaxtongji6(Map map);
    HashMap ajaxtongjisan1(Map map);
    HashMap ajaxtongjisan2(Map map);
    HashMap ajaxtongjisan3(Map map);
    HashMap ajaxtongjisan4(Map map);
    
    List<Map> batchUpdateCaseNumberGetData(Map maps);
    
    int batchUpdateCaseNumber(Map map);
    
    List<Map> batchUpdateCaseNumberGetDataNoSuccess(Map maps);
    
    public void batchUpdateJiangChenInfo(Map<String,Object> map);
    
}
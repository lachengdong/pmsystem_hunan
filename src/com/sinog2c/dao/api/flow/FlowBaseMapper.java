package com.sinog2c.dao.api.flow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;

@Component("flowBaseMapper")
public interface FlowBaseMapper {
    int insert(FlowBase record);

    int delete(String id);
    
    List<FlowBase> selectAll();
   
    int countAll();
   
    FlowBase findById(String id);
    
    public Map findUvFlowByFlowdraftid(@Param("flowdraftid")String flowdraftid);
    
    int update(FlowBase flowBase);
    
    int updateById(@Param("islocked")String islocked,@Param("flowdraftid")String flowdraftid,@Param("suid")String suid,@Param("opname")String opname);
    
    public List<Map<String,Object>> selectUnable2unLockCases(Map<String,Object> map);
    
    int updateSensitiveByFlowdraftid(FlowBase flowbase);

	List<FlowBase> findByCondition(Map<String, Object> map);

	int countAllByCondition(Map<String, Object> map);
	
	List<FlowBase> getBaseListNotInforming(Map<String, Object> map);

	int getBaseListNotInformingCount(Map<String, Object> map);
    
	HashMap getSeq(Map map);
			
	String getMaxCaseNum(Map map);
	
	String getMaxCaseNum4HuNanJailCommute(Map<String,Object> map);
	
	Integer getLastCaseNum(Map map);
	
	FlowBase getFlowBaseByFlowdraftid(Map map);
	
	List<FlowBase> getFlowBaseByFlowdraftid2(Map map);
	
	List<Map> getFlowBaseListByFlowdraftids(Map map);
	
	List<Map> getParoleByFlowdraftid(@Param("flowdraftid")String flowdraftid);
	
	List<Map<String, Object>> getPublicRecordsData(Map<String,Object> map);
	
	int getJYZCount(Map<String,Object> map);
	
	int updateFlowBaseAfterWithdraw(Map map);
	
	List<Map<String,Object>> getJHZSData(Map<String,Object> map);
	
	int getJHZSDataCount(Map<String,Object> map);
	
	int countAllByMapCondition(Map map);
	
	List<Map> findByMapCondition(Map<String,Object> map);
	int findByMapConditionCount(Map<String,Object> map);
	
	
	int deleteFlowBaseOtherDateByFlowdraftids(Map map);
	
	int deleteFlowOtherFlowDateByFlowdraftids(Map map);
	
	int deleteFlowDateByFlowdraftids(Map map);
	
	int deleteFlowBaseDateByFlowdraftids(Map map);
	
	int deleteJXJSCPBByFlowdraftids(Map map);
	
	int updateTbflowCaseCriminalDep(Map map);
	
	int updateTbflowCaseCriminalFlow(Map map);
	
	int insertTbflowCaseCriminal(Map map);
	
	int updateCaseNumberByFlowdraftid(Map<String,Object> map);
	
	Map selectSignaByCaseNum(Map<String,Object> map);
	
	Map selectSignaByCaseNum_nx(Map<String,Object> map);
	
	int updateSignatureByFlowdraftid(Map<String,Object> map);
	
	int updateSignatureByFlowdraftid_nx(Map<String,Object> map);
	
	String getFuyiflagFromUvFlow(Map map);
	
	Map getDataFromUvFlow(Map map);
	
	Map getCourtCaseHandleInfo(Map map);
	
	int setDiscussionDate(Map map);
	
	void deleteFlowBaseCaseByanhao(Map map);
	
	Map ajaxNoShowNonationNumberMapper(Map map);
	
	List<Map> ajaxGetDepartidNoShowNonationNumber(Map map);
	
	Map getMinPregerssByUserid(Map map);
	
	Map getSubmitPregerssByUserid(Map map);
	
	void deleteBaseOtherCase(Map map);
	
	Map maxBatchByDepartid(@Param("departid")String departid);
	
	void deleteOtherFlowCase(Map map);
	
	void deleteFlow(Map map);
	
	void deleteFlowBaseDoc(Map map);
	
	void updateIsdeclareByCrimid(Map map);
	List<Map> ajaxNonationNumberMapper(Map map);
	
	Map judgePunishmentByCrimid(Map map);

	String getFlowXuHao(@Param("year")String year,@Param("flowdefid")String flowdefid, @Param("applyid")String applyid,@Param("departid") String departid);

//	String getCourtMaxCaseNum(Map map);
//
//	String getCourtMaxCaseYear(Map paramMap);
	/*
	 *验证是否已立案
	 */
	int validateRecord(Map map);
	
	String getBingQingZhenDuan(@Param("crimid")String str);
	
	FlowBase getFlowBaseByMap(Map map);
	
	int updateBatchReview(Map map);
	
	int updateByFlowdraftids(Map map);
	
	public List<Map> getProvinceJHZSData(Map<String, Object> map);

	public int countProvinceJHZSData(Map<String, Object> map);
	
	List<Map> findByCaseCondition(Map<String,Object> map);

	void deleteCourtFlowBaseInfo(Map map);

	void deleteCourtCaseFullCourt(Map map);

	void deleteCourtBiluInfo(Map map);
	
	List<Map<String,Object>> getCasenumsByCondition(Map<String, Object> map);
	
	List<Map<String,Object>> getFlowdraftidsByCondition(Map<String, Object> map);

	String getAnjianhaoByFlowdraftid(String flowdraftid);

	String getMaxBaowaiCaseNum(Map map);
	
	String getSJMaxCaseNumForGD(Map map);
	
	List<Map<String,Object>> getUnDealFlowdraftids(Map<String, Object> map);
	
	String getTextByflowdraftid(Map<String, Object> map);
	Integer updateTextByflowdraftid(@Param("text8")String text8,@Param("flowdraftid")String flowdraftid);
	
	int updateText25Byflowdraftid(Map map);
	
	List<Map<String,Object>> getCrimeByFlowdraftids(Map<String,Object> map);
	
	void updateText23ById(FlowBase fb);
	
	List<Map<String,Object>> findUvFlowData();
	
	public List<Map<String,Object>> findFlowBaseData(Map<String,Object> paramap);
	
	public String getMastExistTempidOnFlow(Map<String,Object> map);
	
	public int setSendedStatus(Map<String,Object> map);
	
	public List<Map<String,Object>> distinguishFlowForwardOrEnd(Map<String,Object> map);
	
	public List<Map<String,Object>> distinguishSendToProvince(Map<String,Object> map);
	
	public List<TbprisonerBaseinfo> getAllCrimid();
    String getAnnexcontent(String crimid);
    
    String ajaxGetRepeatAnHao(Map map);

	List<Map<String, Object>> getAnJianTypeList(Map map);
	
	public String getBackNodeidByCurrnodeid(Map<String,Object> paramap);
	
	public void exeProcdeure(@Param("sql")String sql);
	
	public String findByFlowDraftid(@Param("flowdraftid")String flowdraftid);
	
	public String getOrgLevelByFlowdraftid(Map<String,Object> map);

	int deleteById(String m);
	
    String getUnitlevel(String departid);
	
	FlowBase getTianBiaoRen(String crimid);
	
	List<Map> getCaseTypeFromShengju();
	
	
	/**
	 * 根据flowdraftid区别是否是重要罪犯三类犯和无期死缓罪犯
	 * 无期死缓返回值为2，重要罪犯三类犯返回值为1，普通罪犯返回值为0
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> distinguishFinishAtSJCZ(Map<String,Object> map);
	
	
	String getStateFromTbflowBase(String flowdraftid);
}
package com.sinog2c.dao.api.flow;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.flow.UvFlow;

public interface UvFlowMapper {   
	
    List<UvFlow> findByFlowdefid(Map<String,Object> map);
    List<Map> findByFlowdefidForSD(Map<String,Object> map);
    int countAllByCondition(Map<String,Object> map);
    
    int countAllOfCommuteByCondition(Map map);
    int countAllOfTuiAnByCondition(Map map);
    
    List<Map> findCommutatioByCondition(Map map);
    List<Map> findTuiAnByCondition(Map map);
    
    int countByMapParams(Map<String,Object> map);
    
    List<UvFlow> findByMapParams(Map map);
    
    int countAllOfJinBanRenCommute(Map map);
    
    List<Map> findCommutatioOfJianBanRen(Map map);
    
    int countOutPrisonProvinceCaseList(Map map);
    
    List<Map> getOutPrisonProvinceCaseList(Map map);
    
    List<Map> getCasenumsOfCommuteByCondition(Map map);
    
    int jailBaoWaiLiAnCount(Map map);
    
    int jailBaoWaiLiAnCount123(Map map);
    
    List<Map> jailBaoWaiLiAnList(Map map);
    
    List<Map> jailBaoWaiLiAnList123(Map map);
    
    List<Map> getCaseLiAnData(Map map);
    
    List<Map> getSJCaseLiAnData(Map map);
    
    Map getPrisonerParoleType(Map map);
    
    String getFlowidByFlowdraftid(Map map);
    
    String getStateByFlowdraftid(Map map);
    
    String getNodeidByFlowdraftid(Map map);
    
    String getFlowidByCrimid(Map map);
    
    List<UvFlow> selecthavetodoitemByOpid(Map<String,Object> map);
    
    int selectCount1(Map map);
    
    int countAllOfProvinceCommuteByCondition(Map map);
    
    List<Map> findProvinceCommutatioByCondition(Map map);

    List<Map> getCaseCheckList(Map map);
    
    int getCountCaseCheckList(Map map);
    
    int countBaoWaiJianBanRenList(Map map);
    
    List<Map> getBaoWaiJianBanRenList(Map map);
    
    int countBaoWaiJailCaseList(Map map);
    
    List<Map> getBaoWaiJailCaseList(Map map);
    
    int countProvinceOutPrisonLiAnList(Map map);
    
    List<Map> getProvinceOutPrisonLiAnList(Map map);
    
    int countOutPrisonChuShiList(Map map);
    
    List<Map> getOutPrisonChuShiList(Map map);
    
    public List<Map> getCourtCaseHandleList(Map map);
    
    public List<Map> getCourtCaseGuiDangList(Map map);
    
    public int countCourtCaseHandleList(Map map);
    
    public List getAllDocumentIdByCasenums(Map map);
    
    public List<Map> getCourtCaseCheckDataList(Map map);
    
    public List<Map> getCourtCasePercentCheckData(Map map);
    
    public int countCourtCaseCheckDataList(Map map);
    
    public List<Map> getCourtCaseBaoBeiDataList(Map map);
    
    public int countCourtCaseBaoBeiDataList(Map map);
    
    public List<Map> getCollegialPanelListData(Map map);
    
    public int countCollegialPanelListData(Map map);
    
    public List<Map> getChangeCollegialPanelListData(Map map);
    
    public int countChangeCollegialPanelListData(Map map);
    
    public List<Map> getCourtCaseShengPiList(Map map);
    
    public int countCourtCaseShengPiList(Map map);
    
    public List<Map> getCriminalForCourtList(Map map);
    
    public int countCriminalForCourt(Map map);
    
    public String getCourtCaseState(Map map);
    
  //根据申请人编号获取省局减刑假释最终意见
	public String getCommenttextByApplyid(String applyid,String flowdefid);
	//根据流程草稿ID获取对象
	public UvFlow getFlowByFlowdraftid(String flowdraftid);
	
	public int updateCriminalDetainStatus(Map map);
	
	public int updateSenStatusDetainStatus(Map map);
	int getCourtCaseGuiDangListCount(Map map);
	
	public List<Map> getShujyData(Map map);
	
	public int getShujyDataCount(Map map);

	int countAllByConditionForCourt(Map<String, Object> map);

	List<UvFlow> findByFlowdefidForCourt(Map<String, Object> map);
	
	List<Map> getBaseDocByCondition(Map map);
	
	int countBaseDocByCondition(Map map);
	
	List<Map> getMonthApproval(Map map);
	
	int getMonthApprovalCount(Map map);
	
	UvFlow getJianChaYuanopinions(Map map);
	
	UvFlow getuvflowByMap(Map map);
	
	List<Map> getJianChaYuanopinionsData(Map map);
	
	int getJianChaYuanopinionsDataCount(Map map);
	
	public List<Map> getDoneCaseType(Map map);
		
	public List<Map<String,Object>> getTodoListInfo(Map<String, Object> map);
	
//	public int countTodoListInfo(Map<String, Object> map);
	
	public int updateFjqUser(Map<String,Object> map);
	
	public String getSuggestJZ(String crimid);
	
	Map searchChuJianDateByFlowdraftID(Map map);
	
	public UvFlow getUvFlowDataByParam(Map<String,Object> map);
	
	Map isLookTab(Map map);
	
	Map<String,Object> submitBaseInfo(Map<String,Object> map);
	
	Map<String,Object> isDealJxjsCase(Map<String,Object> map);
	
	Map<String,Object> submitSentchange(Map<String,Object> map);
	
    public int isRepeatCaseNum(Map map);
	
	public void baoWaiCheHui(String crimid);
	
	public void baoWaiCheHui1(String crimid);
	
	public UvFlow getUvFlowDataByParam2(Map<String,Object> map);
}
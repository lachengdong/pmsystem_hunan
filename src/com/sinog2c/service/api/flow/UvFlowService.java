package com.sinog2c.service.api.flow;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.flow.FlowBaseArchives;
import com.sinog2c.model.flow.UvFlow;
import com.sinog2c.model.system.SystemUser;

public interface UvFlowService {
	 /**   
	 * 根据map传参查询数据返回对象的集合
	 * @param map
	 * @return
	 */
    public List<UvFlow> findByFlowdefid(Map<String,Object> map);
    public List<Map> findByFlowdefidForSD(Map<String,Object> map);
    
    /**
	 * 根据map传参查询数据返回总数
	 * @param map
	 * @return
	 */
    public int countAllByCondition(Map<String,Object> map);
    
    public List<Map<String,Object>> getTodoListInfo(Map<String,Object> map);
    
//    public int countTodoListInfo(Map<String,Object> map);
    
    public String getCurrnodeidByFlowdraftid(String flowdraftid);
    
    public int countAllOfCommuteByCondition(Map map);
    public int countAllOfTuiAnByCondition(Map map);
    public List<Map> findCommutatioByCondition(Map map);
    public List<Map> findTuiAnByCondition(Map map);
    
    public int countBaoWaiJianBanRenList(Map map);
    
    public List<Map> getBaoWaiJianBanRenList(Map map);
    
    public int jailBaoWaiLiAnCount(Map map);
    
    public int jailBaoWaiLiAnCount123(Map map);
    
    public List<Map> jailBaoWaiLiAnList(Map map);
    
    public List<Map> jailBaoWaiLiAnList123(Map map);
    
    public int countAllOfJinBanRenCommute(Map map);
    
    public List<Map> findCommutatioOfJianBanRen(Map map);
    
    public List<Map> getCasenumsOfCommuteByCondition(Map map);
    
    public String getFlowidByFlowdraftid(Map map);
    
    public String getStateByFlowdraftid(Map map);
    
    public String getNodeidByFlowdraftid(Map map);
    
    public String getFlowidByCrimid(Map map);
    
    public List<UvFlow> selecthavetodoitemByOpid(Map<String,Object> map);
    
    public int countByMapParams(Map<String,Object> map);
    
    public List<UvFlow> findByMapParams(Map map);
    
    public int selectCount1(Map map);
    
    public int countAllOfProvinceCommuteByCondition(Map map);
    
    public List<Map> findProvinceCommutatioByCondition(Map map);
    
    public int countOutPrisonProvinceCaseList(Map map);
    
    public List<Map> getOutPrisonProvinceCaseList(Map map);
    
    public int countProvinceOutPrisonLiAnList(Map map);
    
    public List<Map> getProvinceOutPrisonLiAnList(Map map);
    
    public List<Map> getCaseCheckList(Map map);
    
    public int getCountCaseCheckList(Map map);
    
    public List<Map> getBaoWaiJailCaseList(Map map);
    
    public int countBaoWaiJailCaseList(Map map);
    
    public List<Map> getOutPrisonChuShiList(Map map);
    
    public int countOutPrisonChuShiList(Map map);
    
    
    public Map getPrisonerParoleType(Map map);
    
    /**
	 * 操作流程信息
	 * 
	 * @return
	 */
	public Object operationFlow(SystemUser user, Map<String,Object> map);
	
	public Map<String,Object> operationFlowByParam(SystemUser user, Map<String,Object> map, UvFlow uvflow);
	/**
	 * 流程审批 流转操作
	 */
	public Object flowApprove(String data,SystemUser user, Map<String,Object> datamap);
	
	public String saveCourtCaseReview(String data,SystemUser user, Map datamap);
	/**
	 * 档案审批操作
	 */
	public Object approveArchives(SystemUser user, Map<String,Object> datamap);
	/**
	 * 档案审批鉴定
	 */
	public Object batchApprove(FlowBaseArchives flowBaseArchives, SystemUser user, Map<String,Object> datamap);
	/**
	 * 获取aip表单的显示数据
	 * 
	 * @return
	 */
	public Map<String,String> getDataByAip(String data);
	
	public Object jiangQuLiAn(String data,SystemUser user, Map<String,Object> datamap);
	
	public Object baoWaiJailLiAn(String data,SystemUser user, Map<String,Object> datamap);
	
	public Object baoWaiJailLiAn123(String data,SystemUser user, Map<String,Object> datamap);
	
	public Object isHaveCaseLiAn(Map<String,Object> map);
	
	public Object isSJHaveCaseLiAn(String flowdraftids);
	
	public int provinceLiAn(String data,SystemUser user, Map<String,Object> datamap);
	
	public int provinceLiAnForGD(Map<String,Object> paramMap);
	
	public String courtLiAn(String data,SystemUser user, Map<String,Object> datamap);
	
	public int batchJailLiAnFlowApprove(SystemUser user,Map<String,Object> datamap);
	
	public int baoWaiJailBatchLiAn(SystemUser user,Map<Object,Object> datamap);
	
	public int batchProvinceLiAnFlowApprove(SystemUser user,Map<Object,Object> datamap);
	
//	public int batchCourtLiAnFlowApprove(SystemUser user,Map<Object,Object> datamap);
	
	public List<Map> getCourtCaseHandleList(Map map);
	
	public List<Map> getCourtCaseGuiDangList(Map map);
	
	public List getAllDocumentIdByCasenums(Map map);
    
    public int countCourtCaseHandleList(Map map);
    
    public List<Map> getCourtCaseCheckDataList(Map map);
    
    public String getCourtCasePercentCheckData(Map map);
    
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
	
	public Map saveModelContent(Map<String,String> map,SystemUser user);
	
	public int removearchives(String archiveid);
    //根据申请人编号获取省局减刑假释最终意见
	public String getCommenttextByApplyid(String applyid,String flowdefid);
	
	public int updateCriminalDetainStatus(HttpServletRequest request,SystemUser user);
	public int getCourtCaseGuiDangListCount(Map map);
	
	public List<Map> getShujyData(Map map);
	
	public int getShujyDataCount(Map map);
	public int countAllByConditionForCourt(Map<String, Object> map);
	public List<UvFlow> findByFlowdefidForCourt(Map<String, Object> map);
	/**
	 * 判断是否加锁
	 * @param flowdraftid
	 * @param user
	 * @return 返回-1时可以处理  否则返回操作人姓名
	 */
	public Object ajaxReturnLockUser(String flowdraftid,SystemUser user);
	/**
	 * 判断填写意见是否为空验证
	 * @param paraMap
	 * @return
	 */
	public Object ajaxGetFlowOpinionColumn(Map<String, Object> paraMap);
	 
	 /**
	 * 描述：判断是否加锁，
	 * @author YangZR 2015-03-03 
	 * @param flowdraftid
	 * @param user
	 * @return 返回JSONMessage，如果案件被其它用户加锁，则返回其它用户审批案件的相关信息，同时JSONMessage.status = 0,否则为1
	*/
	public Object ajaxReturnLockUser2(String flowdraftids,SystemUser user);
	
	/**
	 * 根据权限控制表单节点的编辑
	 * @param flowdefid 流程自定义ID
	 * @param departid 组织机构ID(监狱id、省局ID)
	 * @param resnodeid 资源权限对应的流程节点ID
	 * @param tempid 表单ID
	 * @return 
	 */
	public List<Object> ajaxNotEditAttributeList(String departid, String flowdefid, String resnodeid, String tempid);
	
	/**描述：保存电子档案
	 * @author YangZR 2015-03-01
	 * @param docconent
	 * @param flowid
	 * @param user
	 * @return
	 */
	public int saveFlowArchives(String docconent,String flowid,String flowdraftid, String applyid,SystemUser user);
	
	/**描述：全程留痕
	 * @author zhenghui 2015-03-20
	 * @param paraMap
	 * @return
	 */
	public List<Map> selectFlowForScar(Map<String, Object> paraMap);
	
	public List<Map> getJianChaYuanopinionsData(Map map);
	
	public int getJianChaYuanopinionsDataCount(Map map);
	
	public Map findflowByflowdefid(Map map);
		
	public int updateFjqUser(Map<String,Object> map);
		
	public String saveFormBySolutionID(Map<String,Object> paramMap, SystemUser user);

	public Map searchChuJianDateByFlowdraftID(Map<String, Object> params);
	
	Object rollBack(Map<String, Object> paraMap);
	
	public Object getUvFlowDataByParam(Map<String,Object> map);
	
	public UvFlow getFlowByFlowdraftid(String flowdraftid);
	
	public int isRepeatCaseNum(Map map);
	
	public void baoWaiCheHui(String crimid);
	
	public void baoWaiCheHui1(String flowdrafid);
	
	public Object getUvFlowDataByParam2(Map<String,Object> map);
    
	public Map isLookTab(HttpServletRequest request);
	
	public Object submitBaseInfo(Map<String,Object> map);
	
	public Object isDealJxjsCase(Map<String,Object> map);
	
	public Object submitSentchange(Map<String,Object> map);
}
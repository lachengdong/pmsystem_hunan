package com.sinog2c.service.api.flow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.sinog2c.model.flow.FlowBaseOther;
import com.sinog2c.model.system.SystemUser;

public interface FlowBaseOtherService {
	/**
	 * 插入表数据
	 * @return
	 */
    public int insert(FlowBaseOther flowBaseOther);
    /**
	 * 根据归档id删除表数据
	 * @param id
	 * @return
	 */
    public int delete(String id);
    /**
	 * 查询所有表数据并返回对象
	 * @return
	 */
    public List<FlowBaseOther> selectAll();
    /**
	 * 获取表自增长ID
	 * @return
	 */
    public String getOtherId(String departid);
    /**
     * 返回表数据的总数
     * @return
     */
    public int countAll();
    /**
	 * 根据流程ID查询数据
	 * @param id
	 * @return
	 */
    public FlowBaseOther findById(String otherid);
    /**
	 * 根据流程ID,提交人id 进行更新
	 * @param flowdraftid
	 * @param suid
	 * @return
	 */
    public int updateByCondition(HashMap<Object,Object> map);
    /**
	 * 根据流程草稿ID查询数据
	 * @param id
	 * @return
	 */
    public FlowBaseOther findByFlowdraftId(String flowdraftid);
    
    /**
     * 根据模板ID查询数据
     */
    public FlowBaseOther getMeetingRecord(Map meetMap);
    
    public FlowBaseOther getMeetingRecordById(String otherid);
    
    public String getDocconentByCondition(Map map);
    
    public String getDocconentByConditionTwo(Map map);
    
    public String getJXJSBigDataImpl(Map map);
    
    public List<Map> getMaxProgress(Map map);
    
    public String getDocconentOfNewestFuYiData(Map map);
    
    public String getDocconentByFlowdraftid(Map map);
    
    public FlowBaseOther getLastDocconentByFlowdraftid(Map map);
    
    public String getDocconentByFlowid(Map map);
    
    /**
	 * 根据模板类型和案件号进行查询
	 * @param flowdraftid
	 * @param suid
	 * @return
	 */
    public FlowBaseOther selectByCpdnumber(HashMap map);
    /**
	 * 根据模板类型和案件号进行查询
	 * @param flowdraftid
	 * @param suid
	 * @return
	 */
    public FlowBaseOther selectByCpdnumber_nx(HashMap map);
    /**
	 * 根据模板类型和案件号进行更新
	 * @param flowdraftid
	 * @param suid
	 * @return
	 */
    public int updateByCpdnumber(FlowBaseOther other);
    
    
    public String getDocconentByMap(Map map);
    
    
    public Map getFlowBaseOtherDataByMap(Map map);
    
    public List<Map> selectCpdnumbersByCpdnumber(Map map);
    
    public int updateByOtherid(Map map);
    
    public String getOtheridByFlowdraftid(Map map);
    
    public String getLastOtheridByFlowdraftid(Map map);
    
    public String courtGetJailApproveDocByMap(Map map);
    
    /**
     * 根据Otherid查询数据
     */
	public FlowBaseOther findByOtherid(String otherid);
	public int updateMeetContextByOhterid(FlowBaseOther other);
	
	public int updateMeetContextByOhterid_nx(FlowBaseOther other);
	
	public List<Map> getJianChaData(Map map);
	
	public int getJianChaDatas(Map map);
	
	public List<Map> getJianYuName(String str);
	
	public int countCriminalCourtHandleCaseList(Map map);
	
	public List<Map> getCriminalCourtHandleCaseList(Map map);
	
	public String saveSuggestion(SystemUser user,String bigdata,String tempid,String crimid,String flowdraftid,String flowid,String otherid,String caseNumber,String suggesttime);
	
	public String saveSuggestion_nx(SystemUser user,String bigdata,String tempid,String crimid,String flowdraftid,String flowid,String otherid,String caseNumber);

	public String getDocconentByOtherid(Map map);
	//通过罪犯编号获取罪犯减刑假释监狱文书
	public FlowBaseOther getCaseDocconentByApplyid(String tempid,String applyid,String flowdefid);
	
	public String getDocconent4other(Map map);
	/**
	 * 方法描述：省局办案时查询监狱审核表内容
	 * @param map
	 * @author zhenghui
	 */
	public String getDocconent5other(Map map);
	
	/**
	 * 方法描述：获取检察院的案件号，可以进行修改
	 */
	public Map getJCYCaseNumber(Map map);
	public String getSuggestDocconent(Map map);
	public String saveCourtFlowOther(HttpServletRequest request);
	public String getAnJianBigData(Map<String, String> paramMap);
	
	public String deleFyData(Map map);
	public String getAnJianText(Map<String, String> paramMap);
	public void updateSignatureProcess(Map paramMap);
	
	public Map<String,Object> checkCaseFilingInfo(Map<String,Object> paramap, SystemUser user);
	
	public List<Map<String,Object>> getCaseFilingAssembleData(Map<String,Object> paramap, SystemUser user);
	
	public Object caseFileWithData(Map<String,Object> paramap, List<Map<String,Object>> assembleDataList, SystemUser user);

	public Map identificationOfDisease(Map map);
	
	public void autoExecuteCaseFile();
	
	public String getDocconent(Map map);
	
}
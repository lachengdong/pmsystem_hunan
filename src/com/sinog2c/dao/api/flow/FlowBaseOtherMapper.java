package com.sinog2c.dao.api.flow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.sinog2c.model.flow.FlowBaseOther;


@Component("flowBaseOtherMapper")
public interface FlowBaseOtherMapper {
    int insert(FlowBaseOther record);

    int delete(String id);
    
    List<FlowBaseOther> selectAll();
   
    int countAll();
    
    String getOtherId(String departid);
   
    FlowBaseOther findById(String id);
    
    int updateByCondition(HashMap<Object,Object> map);

	FlowBaseOther findByFlowdraftId(String flowdraftid);
	FlowBaseOther getMeetingRecordByTempid(Map meetMap);
	FlowBaseOther findByOtherid(String otherid);
	
	int updateFlowBaseOther(FlowBaseOther flowBaseOther);
	/**
	 * 方法描述：保存会议记录的大字段
	 * @author mushuhong
	 * @version 2014年9月2日17:01:23
	 * @param map
	 * @return
	 */
	int saveMeetingContentBigText(FlowBaseOther flowBaseOther);
	
	String getMeetSn(Map map);
	
	int allCountByMapCondition(Map map);
	
	List<Map> selectDataByMapCondition(Map map);
	
	List<Map> selectDataByMapCondition_nx(Map map);
	
	String getDocconentByCondition(Map map);
	
	String getDocconentByConditionTwo(Map map);
	
	String getJXJSBigDataMapper(Map map);
	
	List<Map> getMaxProgressMapper(Map map);
	
	List<Map> selectCpdnumbersByCpdnumber(Map map);
	
	String courtGetJailApproveDocByMap(Map map);
	
	String getDocconentOfNewestFuYiData(Map map);
	
	String getDocconentByFlowdraftid(Map map);
	
	String getLastOtheridByFlowdraftid(Map map);

	FlowBaseOther selectByCpdnumber(HashMap map);

	FlowBaseOther selectByCpdnumber_nx(HashMap map);
	
	int updateByCpdnumber(FlowBaseOther other);
	
	String getDocconentByMap(Map map);
	
	String isExistClobByMap(Map map);
	
	Map getFlowBaseOtherDataByMap(Map map);
	
	int updateByOtherid(Map map);
	
	String getOtheridByFlowdraftid(Map map);
    /**
     * 方法描述：判断会议记录 是否已经保存为大字段
     * @author mushuhong
     * @version 2014年9月3日09:23:00
     * @param otherid
     * @return
     */
    List<Map> juegeMeetWhetherExistDoc(String otherid);
    /**
     * 方法描述：更新会议记录内容
     * @author mushuhong
     * @version 2014年9月3日09:23:41
     */
    int updateMeetContextByOhterid(FlowBaseOther fOther);
    
    /**
     * 方法描述：更新会议记录内容
     * @author mushuhong
     * @version 2014年9月3日09:23:41
     */
    int updateMeetContextByOhterid_nx(FlowBaseOther fOther);
    /**
     * 方法描述：查询 会议记录
     * @auhtor mushuhong
     * @version 2014年9月3日11:25:15
     * 
     */
    public List<Map> priviewMeetDocMaper(String otherid);
    
    public String getOtherIdByOther(FlowBaseOther other);
    
    public List getAllMeetByOrgid(Map map);
    
    public List getAllMeetByOrgid_nx(Map map);
    
    public List<Map> getJianChaData(Map map);
    
    public int getJianChaDatas(Map map);
    
    public  List<Map> getJianYuName(String str);
    
    int countCriminalCourtHandleCaseList(Map map);
	
	public List<Map> getCriminalCourtHandleCaseList(Map map);
	
	public FlowBaseOther getLastDocconentByFlowdraftid(Map map);
	
	public String getDocconentByOtherid(Map map);
	
	public String getDocconent4other(Map map);
	
	public String getDocconent5other(Map map);
	
	public FlowBaseOther getCaseDocconentByApplyid(@Param("tempid")String tempid,@Param("applyid")String applyid,@Param("flowdefid")String flowdefid);
	
	public String getSuggestDocconent(Map map);
	
	public FlowBaseOther getFlowBaseOtherData(Map map);
	
	public Map getJCYCaseNumber(Map map);

	String selectMaxOtheridByFlowdraftidAndTempid(Map<String,Object> map);

	void updateFlowBaseOtherByOtherid(FlowBaseOther bo);
	
	String getDocconentByFlowid(Map map);
	
	FlowBaseOther findBaseOtherBymap(Map map);

	void deleteBaseOtherById(@Param("otherid")String otherid);

	void deleteOtherFlowById(@Param("otherid")String otherid, @Param("flowdraftid")String flowdraftid);

	void updateSignatureProcess(Map paramMap);

	Map<String, Object> chakanCourtBanliBigData(@Param("flowdraftid")String flowdraftid);
	
	public List<Map<String,Object>> filterFlowdraftidOfMakedPage(Map<String,Object> paramap);
	
}
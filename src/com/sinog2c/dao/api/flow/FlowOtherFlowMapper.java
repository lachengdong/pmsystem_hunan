package com.sinog2c.dao.api.flow;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.flow.FlowOtherFlow;

public interface FlowOtherFlowMapper {
    int countAll();
    
    int insert(FlowOtherFlow record);
    
    List<FlowOtherFlow> selectAll();
   
    FlowOtherFlow findById(String id);
    
    int getSequenceNextValue(@Param("sql")String sql);
    
    int updateByCondition(Map map);
    
    int updateByCondition2(FlowOtherFlow record);
    
    int updateByOtherid(FlowOtherFlow record);
    
    FlowOtherFlow findById2(Map<String,Object> map);
    
    int isExistJys(@Param("flowdraftid")String flowdraftid,@Param("tempid")String tempid);
    
    List<Map> judgeExistMeetByOrgid(Map map);
    
    List<Map> getMeetContent(Map map);
    
    List<Map> getMeetPlanSqlByResid(Map map);
    
    List<Map> getMeetReportDataByPlanSql(Map mapSql);
    
    List<Map> queryMaxBatchMapper(Map map);
    
    int saveMeetContentsOtherFlow(Map map);
    
    int saveMeetContentsBaseOther(Map map);
    
    int updateMeetContentOther(Map map);
     List<Map> getPrisonErperManceImplMapper(Map map);
    /**
     * 方法描述：新增考核信息
     * @param map
     * @return
     */
    int insertPrisonErperManceImplMapper(Map map);
    /**
     * 方法描述：更新考核信息
     * @version 2014年11月7日14:38:59
     */
    int updatePrisonErperManceImplMapper(Map map);
    /**
     * 方法描述：删除会议记录
     * @version 2014年12月2日22:35:04
     */
    int deleteMeetingImplMapper(Map map);
    int updateMeetContentBase(Map map);

	String getAnJianBigData(Map<String, String> paramMap);

	String getAnJianText(Map<String, String> paramMap);
    
	Map identificationOfDisease(Map<String, String> map);
	
	List<Map<String,Object>> judgeExistMeetRecordByUser(Map<String,Object> map);
	
}
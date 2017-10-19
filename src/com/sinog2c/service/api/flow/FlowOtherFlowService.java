package com.sinog2c.service.api.flow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.flow.FlowOtherFlow;
import com.sinog2c.model.system.SystemUser;

public interface FlowOtherFlowService {
	/**
	 * 插入表数据
	 * @return
	 */
    public int insert(FlowOtherFlow flowOtherFlow);
    /**
	 * 查询所有表数据并返回对象
	 * @return
	 */
    public List<FlowOtherFlow> selectAll();
    /**
	 * 返回表数据的总数
	 * @return
	 */
    public int countAll();
    /**
	 * 根据流程ID查询数据
	 * @param flowid
	 * @return
	 */
    public FlowOtherFlow findById(String flowid);
    
    public String saveOrUpdateMeetingRecord(HashMap map,SystemUser user);
    
    /**
     * 方法描述：判断 建议书是否保存大字段
     * @auhtor mushuhong
     * @version 2014年9月2日20:10:02
     * @param 
     * @return
     */
    public int juegeMeetWhetherExistDoc(String otherid);
    /**
     * 方法描述：查询出 需要查看的报表
     * @author mushuhong
     * @version 2014年9月3日11:23:07
     * @param map
     * @return
     */
    public List<Map> priviewMeetDoc(String otherid);
    public int  allCountByMapCondition(Map map);
    
	public List<Map> selectDataByMapCondition(Map map);
	public List<Map> getAllMeetByOrgid(Map map);
	
	/**
	 * 方法描述：删除会议记录
	 * @param request
	 * @return
	 * @version 2014年12月2日22:32:20
	 */
	public int deleteMeetingImpl(HttpServletRequest request);
	
	/**
	 * 宁夏 查询监外执行会议记录内容
	 * @param fof
	 * @return
	 */
	public List<Map> selectDataByMapCondition_nx(Map map);
	
	public List<Map> getAllMeetByOrgid_nx(Map map);
	
	
	public int updateByCondition2(FlowOtherFlow fof);
	public FlowOtherFlow findById2(Map<String,Object> map);
	 /**
     * 方法描述：判断是否存在建议书
     * @author mushuhong
     * @version 2014年9月28日11:23:07
     * @param map
     * @return
     */
	public int isExistJys(String flowdraftid,String tempid);

    public int judgeExistMeetByOrgid(HttpServletRequest request,SystemUser user);
    
    public Map getMeetContent(HttpServletRequest request,SystemUser user);
    
    public Map queryMaxBatchMapper(SystemUser user,HttpServletRequest request);
    
    public String saveMeetContents(Map map);
	
    /**
     * 根据罪犯编号和批次id 查询出对应的 考核内容
     * @version 2014年11月7日13:25:15
     */
    public List<Map> getPrisonErperManceImpl(SystemUser user,Map map,HttpServletRequest request);
    
	public int caoZuoPrisonErperMance(SystemUser user,HttpServletRequest request);
	
	public Map<String,Object> judgeExistMeetRecordByUser(Map<String,Object> paramap);
	
	public String filterFlowdraftidOfMakedPage(Map<String,Object> paramap);
	
}
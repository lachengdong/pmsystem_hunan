package com.sinog2c.service.api.flow;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.prisoner.TbprisonerBaseinfo;
import com.sinog2c.model.system.SystemUser;

public interface FlowBaseService {
	/**
	 * 插入表数据
	 * @return
	 */
    public int insert(FlowBase flowBase);
    /**
	 * 根据id删除表数据
	 * @param id
	 * @return
	 */
    public int delete(String id);
    /**
	 * 查询所有表数据并返回对象
	 * @return
	 */
    public List<FlowBase> selectAll();
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
    public FlowBase findById(String id);
    
    public Map findUvFlowByFlowdraftid(String flowdraftid);
    /**
	 * 更新数据
	 * @param flowBase
	 * @return
	 */
    public int update(FlowBase flowBase);
    /**
	 * 根据参数设置加解锁
	 * @param islocked
	 * @param flowid
	 * @return
	 */
    public int updateById(String islocked,String flowdraftid,String suid,String opname);

    /**
	 * 查询出不能解锁的案件：非本人加锁的案件，不能由本人解锁
	 * YangZR	2016-03-11
	 */
    public List<Map<String,Object>> selectUnable2unLockCases(Map<String,Object> map);
    
    /**
	 * 根据参数更新数据
	 * @param map
	 * @return
	 */
    public int updateByFlowdraftid(FlowBase flowbase);
    /**
	 * 根据map查询数据并返回对象
	 * @param map
	 * @return
	 */
	List<FlowBase> getBaseListByCondition(Map<String, Object> map);
	/**
	 * 根据map查询表数据总数
	 * @param map
	 * @return
	 */
	int countAllByCondition(Map<String, Object> map);
	/**
	 * 根据序列名称获取序列下一个值
	 * @author shily 2014-8-14 22:25:57
	 */
	public List getNonationAndSealNumberOfUser(String flowdefid,String tempid,SystemUser user);
	
	public String getSeqBySeqname(String name); 
	/**
	 * 通过流程草稿id获取表单数据（doc表没有则去other表取数据）
	 * @param flowdraftId
	 * @return
	 */
	public String getDocConentByFlowdraftId(String flowdraftId);
	
	
	public String getMaxCaseNum(Map map);
	
	/**
	 * YanGZR	湖南监狱减刑假释获取案件号
	 * @param map
	 * @return
	 */
	public String getMaxCaseNum4HuNanJailCommute(Map<String,Object> map);
	
	public String getSJMaxCaseNumForGD(Map map);
	
	public String getMaxBaowaiCaseNum(Map map);
	
	public String getLastCaseNum(Map map);
	
	public FlowBase getFlowBaseByFlowdraftid(Map map);
	
	public List<FlowBase> getFlowBaseByFlowdraftid2(Map map);
	
	public List<Map> getFlowBaseListByFlowdraftids(Map map);
	
	//查询不予收监数据 
	public List<FlowBase> getBaseListNotInforming(Map<String, Object> map);
	public int getBaseListNotInformingCount(Map<String, Object> map);
	
	List<Map<String, Object>> getPublicRecordsData(Map<String,Object> map);
	int  getJYZCount(Map<String,Object> map);
	List<Map<String,Object>> getJHZSData(Map<String,Object> map);
	int getJHZSDataCount(Map<String,Object> map);
	
	
	int countAllByMapCondition(Map map);
	
	List<Map> findByMapCondition(Map<String,Object> map);
	int findByMapConditionCount(Map<String,Object> map);
	
	//批量重审案件
	int updateBatchReview(Map<String,Object> map);
	//根据流程草稿id更新案件号
	int updateCaseNumberByFlowdraftid(Map<String,Object> map);
	//根据流程草稿id更新签章进程
	int updateSignatureByFlowdraftid(Map<String,Object> map);
	
	//根据流程草稿id更新签章进程 宁夏分离
	int updateSignatureByFlowdraftid_nx(Map<String,Object> map);
	
	//查询签章进程
	Map getMinPregerssByUserid(Map map);
	
	Map getSubmitPregerssByUserid(Map map);
	
	public Object ajaxNonationNumberImpl(HttpServletRequest request,SystemUser user);
	
	public Object ajaxNoShowNonationNumber(HttpServletRequest request,SystemUser user);
	
	Map selectSignaByCaseNum(Map<String,Object> map);
	
	Map selectSignaByCaseNum_nx(Map<String,Object> map);
	
	String getFuyiflagFromUvFlow(Map map);
	
	Map getDataFromUvFlow(Map map);
	
	String setDiscussionDate(Map map);
	/**
	 * 方法描述：当对案件进行拒绝的时候，那么把状态修改为申报之前的状态
	 */
	public void updateIsdeclareByCrimid(HttpServletRequest request,SystemUser user);
	
	Map getCourtCaseHandleInfo(Map map);
	
	String executeCourtCaseOperate(Map map);
	/**
	 * 获取流程序号
	 * @param flowdefid	流程定义id
	 * @param applyid	申请人编号
	 * @param applydatetime	申请时间
	 * @return
	 */
	public String getFlowXuHao(String year,String flowdefid,String applyid,String departid);
	
	//根据罪犯编号 和现刑期来确定该罪犯是 打开 有期减刑审核表还是死缓无期审核表
	String judgePunishmentByCrimid(String map);
	
//	public String getCourtMaxCaseNum(Map temMap2);
//	public String getCourtMaxCaseYear(Map paramMap);
	
	public String getBingQingZhenDuan(String str);
	
	public FlowBase getFlowBaseByMap(Map map);
	
	public int updateByFlowdraftids(Map map);
	
	public List<Map> getProvinceJHZSData(Map<String, Object> map);

	public int countProvinceJHZSData(Map<String, Object> map);
	/**
	 * 获取导出列表信息
	 * @param request	
	 * @return
	 */
	public List<Map> ajaxExportDataList(Map<String, Object> paraMap);
	
	
	public List<Map<String,Object>> getCasenumsByCondition(Map<String, Object> paraMap);
	
	
	public List<Map<String,Object>> getFlowdraftidsByCondition(Map<String, Object> paramap);
	/**
	 * 将表单上的批注、签章个数以批注个数@签章个数的格式存在tbflow_base表的text8字段上
	 * @param text8
	 * @param flowdraftid
	 * @return
	 */
	public Integer updateTextByflowdraftid(String text8,String flowdraftid);
	/**
	 * 获得tbflow_base表的text8字段上的批注、签章个数
	 * @param text8
	 * @param flowdraftid
	 * @return
	 */
	public String getTextByflowdraftid(Map<String,Object> map);
	
	public List<Map<String,Object>> getCrimeByFlowdraftids(Map<String,Object> map);
	
	public void updateText23ById(FlowBase fb);
	
	public List<Map<String,Object>> findUvFlowData();
	
	public List<Map<String,Object>> findFlowBaseData(Map<String,Object> paramap);
	
	public int setSendedStatus(Map<String,Object> paramap);
	
	/**
	 * 描述：区分出流程继续走或直接结束的flowdraftids
	 * @author YangZR	2015-10-16
	 * @return Map{fordard: 流程继续走的flowdraftids, 	end: 流程结束的flowdraftids}
	 */
	public Map<String,Object> distinguishFlowForwardOrEnd(Map<String,Object> paramap);
	
	public Map<String,Object> distinguishSendToProvince(Map<String,Object> paramap);
	
	public String getAnnexcontent(String crimid);
	
    public List<TbprisonerBaseinfo> getAllCrimid();
    /**
	 * 查看是否有重复的案件号
	 * @param parolenumber
	 * @return
	 */
	String ajaxGetRepeatAnHao(Map map);
	
	/**获取案件类型下拉框
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getAnJianTypeList(Map map);
	
	public String getBackNodeidByCurrnodeid(Map<String,Object> paramap);
	
	public String findByFlowDraftid(String flowdraftid);
	
	public Object validateFlowCondition(Map<String,Object> map);
	
	public Object getOrgLevelByFlowdraftid(Map<String,Object> map);
	
    public String getUnitlevel(String departid);
	
	public FlowBase getTianBiaoRen(String crimid);
	
	public List<Map> getCaseTypeFromShengju();
	
	public Map<String,Object> distinguishFinishAtSJCZ(Map<String,Object> map);
}
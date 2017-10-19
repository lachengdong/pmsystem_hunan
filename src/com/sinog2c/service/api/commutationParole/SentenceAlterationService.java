package com.sinog2c.service.api.commutationParole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.sinog2c.model.commutationParole.TbxfCommutationReference;
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.system.SystemUser;

public interface SentenceAlterationService {
	
	public int getCount(HashMap<String, Object> map);
	
	public int getCount_nx(HashMap<String, Object> map);
	
	public List<Map<String,Object>> getSentenceAlterationList(HashMap<String, Object> map);
	
	public List<HashMap> getSentenceAlterationList_nx(HashMap<String, Object> map);
	
	public List<HashMap<Object,Object>> getGuidangCrimeBaseInfo(HashMap<Object,Object> map);
	
	public int countByGuidangCrimeBaseInfo(HashMap<Object,Object> map);
	
	public TbxfSentenceAlteration selectByPrimaryKey(String crimid);
	
	public Map getSuggestDocumentInfoOfCrim(Map param);
		
	/**
	 * 流程审批通过后更新刑期变动表
	 */
	public void autoUpdateSentenceChangeData();
	
	public void autoUpdateLiGongData(String provincecode);
	
	public List<TbxfCommutationReference> getReferenceList(String departid);
	
//	public List<TbxfCommutationReference> getReferenceList_sx(String departid);
	
	int countSuggestionHandleList(Map map);
	
	int SelectWaitOutPrisonListCount(Map map);
	List<Map> findSuggestionHandleList(Map map);
	
	List<Map> SelectWaitOutPrisonList(Map map);
	
	List<TbxfCommutationReference> getRefids(Map<String, Object> senidMap);
	
	int SelectOutPrisonListCount(Map map);

	List<Map> SelectOutPrisonList(Map map);
	/**
     * 获取刑罚统计数据信息
     */
	List<HashMap<Object,Object>> getStatisticalPunishment(HashMap<Object,Object> map);
	/**
     * 保存刑罚统计数据信息
     */
	int saveStatisticalPunishment(HttpServletRequest request, SystemUser user);
	
	void statisticalReport(HttpServletRequest request, SystemUser user);

	public void autoUpdateSentenceChangeJailto(String jailto, String crimid);
	
	
	
	
	Object getReportData(HttpServletRequest request);
    

	 public HashMap ajaxtongji1(Map map);
	 public HashMap ajaxtongji2(Map map);
	 public HashMap ajaxtongji3(Map map);
	 public HashMap ajaxtongji4(Map map);
	 public HashMap ajaxtongji5(Map map);
	 public HashMap ajaxtongji6(Map map);
	 public HashMap ajaxtongjisan1(Map map);
	 public HashMap ajaxtongjisan2(Map map);
	 public HashMap ajaxtongjisan3(Map map);
	 public HashMap ajaxtongjisan4(Map map);
	 public int deleteByPrimaryKey(String crimid);
}

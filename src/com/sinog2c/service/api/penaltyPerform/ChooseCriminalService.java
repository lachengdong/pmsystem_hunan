package com.sinog2c.service.api.penaltyPerform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.system.SystemUser;


/**
 * 选择罪犯接口
 * 
 * @author liuxin 2014-7-22 11:35:23
 */
public interface ChooseCriminalService {
	/**
	 * 根据map传参获取罪犯列表
	 * 
	 * @author liuxin 2014-7-22 17:36:00
	 */
	public List<Map<String,Object>> getBasicInfoList(Map<String, Object> map);
	
	public List<Map> getProvinceBasicInfoList(Map<String, Object> map);
	public List<HashMap<Object,Object>> getNewCriminalList(Map<String, Object> map);
	/**
	 * 根据map传参查询数据返回总数
	 * @param map
	 * @return
	 */
	public int countAllByCondition(Map<String, Object> map);
	
	public int countFindWithFlow(Map<String, Object> map);
	
	public int countProvinceAllByCondition(Map<String, Object> map);
	public int getNewCriminalCount(Map<String,Object> map);
	/**
	 * 根据map传参获取罪犯列表(含流程状态，identity：0为未处理，1为保存未提交，2为已提交，idnumber 为流程草稿id)
	 * 
	 * @author shily 2014-8-12 15:32:12
	 */
	public List<Map<String,Object>> getBasicInfoListWithFlow(Map<String, Object> map);
	
	public List<Map> getSanLeiFan(Map<String, Object> map);
	
	public int getSLFcount(Map map);
	
	public void lockCriminal(Map map);
	
	public List<Map> getJianYuName();
	
	/**
	 * 把罪犯修改为三类罪犯
	 * @version 2014年11月17日20:33:32
	 */
	public int threeCrimeAffirmService(HttpServletRequest request);
	
	/**
	 * 方法描述：查询出三类罪犯的罪犯
	 * @version 2014年11月18日09:03:30
	 */
	public Map getThreeOfCrimAffirmList_sx(HttpServletRequest request,SystemUser user);
	public List<Map> getJianChaInfo(Map<String, Object> map);
	
	int getJianChaInfoCount(Map<String,Object> map);
	
	public int countAllByConditions(Map<String, Object> map);
	public List<Map> getBasicInfoForm(Map<String, Object> map);
	public void updateReportaudit(String crimid);
	
	
	public int countCrimNumByParaMap(Map<String, Object> map);
	
	public List<Map<String, Object>> getCrimInfoByParaMap(Map<String, Object> map);

						
}

package com.sinog2c.service.impl.penaltyPerform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.prisoner.TbprisonerBaseinfoMapper;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.penaltyPerform.ChooseCriminalService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;

/**
 * 选择罪犯
 * 
 * @author liuxin 2014-7-22 11:35:30
 */
@Service("chooseCriminalService")
public class ChooseCriminalServiceImpl extends ServiceImplBase implements ChooseCriminalService{
	@Autowired
	private TbprisonerBaseinfoMapper tbprisonerBaseinfoMapper;
	
	/**
	 * 根据map传参获取罪犯列表
	 * 
	 * @author liuxin 2014-7-22 17:36:12
	 */
	@Override
	public List<Map<String,Object>> getBasicInfoList(Map<String, Object> map) {
		return MapUtil.convertKeyList2LowerCase(tbprisonerBaseinfoMapper.findByCondition(map));
	}
	/**
	 * 根据map传参查询数据返回总数
	 * @param map
	 * @return
	 */
	@Override
	public int countAllByCondition(Map<String, Object> map) {
		return tbprisonerBaseinfoMapper.countAllByCondition(map);
	}
	/**
	 * 根据map传参查询数据返回总数(含流程状态，identity：0为未处理，1为保存未提交，2为已提交，idnumber 为流程草稿id)
	 * @param map
	 * @return
	 */
	@Override
	public int countFindWithFlow(Map<String, Object> map) {
		return tbprisonerBaseinfoMapper.countFindWithFlow(map);
	}
	/**
	 * 根据map传参获取罪犯列表(含流程状态，identity：0为未处理，1为保存未提交，2为已提交，idnumber 为流程草稿id)
	 * 
	 * @author shily 2014-8-12 15:32:12
	 */
	@Override
	public List<Map<String,Object>> getBasicInfoListWithFlow(Map<String, Object> map) {
		return MapUtil.convertKeyList2LowerCase(tbprisonerBaseinfoMapper.findWithFlow(map));
	}
	@Override
	public List<HashMap<Object,Object>> getNewCriminalList(Map<String, Object> map) {
		return tbprisonerBaseinfoMapper.getNewCriminalList(map);
	}

	@Override
	public int getNewCriminalCount(Map<String, Object> map) {
		return tbprisonerBaseinfoMapper.getNewCriminalCount(map);
	}
	@Override
	public List<Map> getSanLeiFan(Map<String, Object> map) {
		return tbprisonerBaseinfoMapper.getSanLeiFan(map);
	}
	@Override
	public int getSLFcount(Map map) {
		return tbprisonerBaseinfoMapper.getSLFcount(map);
	}
	@Override
	public void lockCriminal(Map map) {
		tbprisonerBaseinfoMapper.lockCriminal(map);
	}
	@Override
	public List<Map> getJianYuName() {
		return tbprisonerBaseinfoMapper.getJianYuName();
	}
	@Override
	public List<Map> getJianChaInfo(Map<String, Object> map) {
		return MapUtil.turnKeyToLowerCaseOfList(tbprisonerBaseinfoMapper.getJianChaInfo(map));
	}
	@Override
	@SuppressWarnings("all")
	public int threeCrimeAffirmService(HttpServletRequest request) {
		String crimid = request.getParameter("crimid");
		String state = request.getParameter("state");
		Map map = new HashMap();
		map.put("crimid", crimid);
		if ("1".equals(state)) {
			map.put("sanclassstatus", 1);
		}else if("0".equals(state)){
			map.put("sanclassstatus", "");
		}
		return tbprisonerBaseinfoMapper.threeCrimeAffirmService(map);
	}
	@Override
	@SuppressWarnings("all")
	public Map getThreeOfCrimAffirmList_sx(HttpServletRequest request,SystemUser user) {
		List<Map> listMaps = new ArrayList<Map>();
		Map mapValue = new HashMap();
		try {
			//如果状态时1，那么属于三类罪犯，状态是0不属于三类罪犯
			String state = request.getParameter("state");
			String pageIndex = request.getParameter("pageIndex");//当前页
			String pageSize = request.getParameter("pageSize");//每页显示多少条数据
			int pageindex = Integer.parseInt(pageIndex);
			int pagesize = Integer.parseInt(pageSize);
			
			String key = request.getParameter("key");
			
			String orgid = user.getOrgid();//部门编号
			int start = pageindex * pagesize;
			int end = (pageindex + 1) * pagesize;
			
			Map map = new HashMap();
			map.put("state", state);
			
			map.put("start", start);
			map.put("end", end);
			map.put("orgid", orgid);
			
			map.put("key", key);
			listMaps = this.tbprisonerBaseinfoMapper.getThreeOfCrimAffirmList_sx(map);
			List<Map> count = this.tbprisonerBaseinfoMapper.getThreeOfCrimAffirmList_sxcount(map);
			
			mapValue.put("total", count.size());
			mapValue.put("data", MapUtil.turnKeyToLowerCaseOfList(listMaps));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapValue;
	}
	@Override
	public int getJianChaInfoCount(Map<String, Object> map) {
		return tbprisonerBaseinfoMapper.getJianChaInfoCount(map);
	}
	@Override
	public int countAllByConditions(Map<String, Object> map) {
		return tbprisonerBaseinfoMapper.countAllByConditions(map);
	}
	@Override
	public List<Map> getBasicInfoForm(Map<String, Object> map) {
		return MapUtil.turnKeyToLowerCaseOfList(tbprisonerBaseinfoMapper.findByConditions(map)) ;
	}
	@Override
	public void updateReportaudit(String crimid) {
		 tbprisonerBaseinfoMapper.updateReportaudit(crimid);
	}
	
	@Override
	public int countProvinceAllByCondition(Map<String, Object> map){
		return tbprisonerBaseinfoMapper.countProvinceAllByCondition(map);
	}
	
	@Override
	public List<Map> getProvinceBasicInfoList(Map<String, Object> map) {
		return MapUtil.turnKeyToLowerCaseOfList(tbprisonerBaseinfoMapper.getProvinceBasicInfoList(map));
	}
	
	@Override
	public int countCrimNumByParaMap(Map<String, Object> map){
		return tbprisonerBaseinfoMapper.countCrimNumByParaMap(map);
	}
	
	@Override
	public List<Map<String, Object>> getCrimInfoByParaMap(Map<String, Object> map){
		return MapUtil.convertKeyList2LowerCase(tbprisonerBaseinfoMapper.getCrimInfoByParaMap(map));
	}

}

package com.sinog2c.service.impl.commutationParole;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.commutationParole.TbdataSentchageMapper;
import com.sinog2c.dao.api.commutationParole.TbxfSentenceAlterationMapper;
import com.sinog2c.dao.api.flow.FlowBaseMapper;
import com.sinog2c.model.commutationParole.TbdataSentchage;
import com.sinog2c.model.commutationParole.TbdataSentchageKey;
import com.sinog2c.model.flow.FlowBase;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.commutationParole.TbdataSentchageService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.CalendarUtil;
import com.sinog2c.util.common.DateUtil;
import com.sinog2c.util.common.JsonUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
@Service("tbdataSentchageService")
public class TbdataSentchageServiceImpl extends ServiceImplBase implements TbdataSentchageService {
	@Resource
	private TbdataSentchageMapper tbdataSentchageMapper;
	@Resource
	private TbxfSentenceAlterationMapper tbxfSentenceAlterationMapper;
	@Resource
	private FlowBaseMapper flowBaseMapper;
	
	
	@Override
	public List<TbdataSentchage> selectDataList(Map<String, Object> map) {
		return tbdataSentchageMapper.selectDataList(map);
	}
	
	@Override
	public int selectDataListCount(Map<String, Object> map) {
		return tbdataSentchageMapper.selectDataListCount(map);
	}
	
	@Override
	public List<Map<String,Object>> getSentenceChangeData(Map<String, Object> map) {
		return MapUtil.convertKeyList2LowerCase(tbdataSentchageMapper.getSentenceChangeData(map));
	}
	
	@Override
	public int countSentenceChangeData(Map<String, Object> map) {
		return tbdataSentchageMapper.countSentenceChangeData(map);
	}
	@Override
	public int updateKHZR(Map<String, Object> map) {
		return tbdataSentchageMapper.updateKHZR(map);
	}
	
	@Override
	@Transactional
	public int updateSentenceChangeKHZRDate(List list) {
		if (list != null && list.size() > 0) {
			for(int i=0;i<list.size();i++){
				Map<String,Object> map = (Map<String,Object>)list.get(i);
				String awardend = DateUtil.dateFormatForAip(map.get("awardend"));
				map.put("awardend", awardend);
				
				Date rewardstart = DateUtil.StringParseDate(awardend, "yyyyMMdd");
				map.put("rewardstart", rewardstart);
				
				tbdataSentchageMapper.updateDataSentenceAwardend(map);
				tbxfSentenceAlterationMapper.updateSentenceAlterationRewardstart(map);
			}
			return 1;
		}
		return 0;
	}
	
	@Override
	public int setCrimeTypesMapping(Map<String, Object> map) {
		return tbdataSentchageMapper.setCrimeTypesMapping(map);
	}
	@Override
	public int deleteCrimeTypesMapping(Map<String, Object> map) {
		return tbdataSentchageMapper.deleteCrimeTypesMapping(map);
	}
	@Override
	public void callREWARDSTARTProcedure() {
		tbdataSentchageMapper.callREWARDSTARTProcedure();
	}
	@Override
	public int insertSelective(TbdataSentchage record) {
		return  tbdataSentchageMapper.insertSelective(record);
	}
	@Override
	public int updateByExampleSelective(TbdataSentchage record) {
		return tbdataSentchageMapper.updateByExampleSelective(record);
	}
	
	@Override
	public int updateOriginalSentenceData(String crimid){
		
		int result = 1;
		tbdataSentchageMapper.updateOriginalSentenceData(crimid);
		return result;
	}
	
	@Override
	public TbdataSentchage selectDataByPk(Map<String, Object> map) {
		return tbdataSentchageMapper.selectDataByPk(map);
	}
	
	@Override
	public TbdataSentchage selectDataByUuid(Map<String, Object> map) {
		return tbdataSentchageMapper.selectDataByUuid(map);
	}
	
	@Override
	public List<Map<String, Object>> selectDataBycrimid(Map<String, Object> map) {
		return tbdataSentchageMapper.selectDataBycrimid(map);
	}
	@Override
	public List<Map> selectAllBycrimidAndCategory(
			Map<String, Object> map) {
		return tbdataSentchageMapper.selectAllBycrimidAndCategory(map);
	}
	@Override
	public Map selectAllBycrimidAndCategoryAndCourtsanction(
			Map<String, Object> map) {
		return tbdataSentchageMapper.selectAllBycrimidAndCategoryAndCourtsanction( map);
	}
	@Override
	public List<TbdataSentchage> getLastCommutationData(Map<String, Object> map) {
		return tbdataSentchageMapper.getLastCommutationData(map);
	}
	@Override
	public int getLastCommutationDataCount(Map<String,Object> map) {
		return tbdataSentchageMapper.getLastCommutationDataCount(map);
	}
	@Override
	public int getOrgSentenceInfoCount(Map<String,Object> map) {
		return tbdataSentchageMapper.getOrgSentenceInfoCount(map);
	}
	@Override
	public List<Map> getOrgSentenceInfoList(Map<String,Object> map) {
		return MapUtil.turnKeyToLowerCaseOfList(tbdataSentchageMapper.getOrgSentenceInfoList(map));
	}
	@Override
	public int deleteSentenceInfo(TbdataSentchageKey key) {
		return tbdataSentchageMapper.deleteByPrimaryKey(key);
	}
	@Override
	public Map<String, Object> viewScreeningExcuse(Map<String, Object> map) {
		return tbdataSentchageMapper.viewScreeningExcuse(map);
	}
	@Override
	public int getSentenceCountByCrimid(String crimid) {
		return tbdataSentchageMapper.getSentenceCountByCrimid(crimid);
	}
	@Override
	public TbdataSentchage getCheckendByCrimid(String crimid) {
		return tbdataSentchageMapper.getCheckendByCrimid(crimid);
	}

	@Transactional
	public int updateBatchReview(Map<String, Object> map) {
		int rows = 0;
		Map<String, Object> updatemap = new HashMap<String,Object>();
		if(map!=null && map.size()>0){
			String type = StringNumberUtil.returnString2(map.get("type"));
			String crimid = StringNumberUtil.returnString2(map.get("crimid"));
//			String courtsanction = StringNumberUtil.returnString2(map.get("ids[]"));
			String courtsanction = StringNumberUtil.returnString2(map.get("ids"));
			String[] courtsanctions = courtsanction.split(",");
			updatemap.put("CRIMID", crimid);
			updatemap.put("ISTHROUGH", Integer.valueOf(type));
			for(String obj:courtsanctions){
				updatemap.put("COURTSANCTION", obj);
				rows = tbdataSentchageMapper.updateKHZR(updatemap);
			}
			//
		}
		return rows;
	}

	@Override
	public String getCourtInfoByCrimid(String crimid) {
		 List<String> list = tbdataSentchageMapper.getCourtInfoByCrimid(crimid);
		 StringBuffer sb = new StringBuffer();
			 for(String str:list){
				 sb.append(str+"\\r\\n");
			 }
		 return sb.toString();
	}
	
	@Override
	public int getConsumeCount(String crimid) {
		return tbdataSentchageMapper.getConsumeCount(crimid);
	}
	
	@Override
	public List<HashMap> getConsumeList(String crimid, String sortField,
			String sortOrder, int start, int end) {
		return tbdataSentchageMapper.getConsumeList(crimid, sortField, sortOrder, start, end);
	}

	
	@Override
	public int getCrimQpbTeaCount(String crimid) {
		return tbdataSentchageMapper.getCrimQpbTeaCount(crimid);
	}
	
	@Override
	public List<HashMap> getCrimQpbTeaList(String crimid, String sortField,
			String sortOrder, int start, int end) {
		return tbdataSentchageMapper.getCrimQpbTeaList(crimid, sortField, sortOrder, start, end);
	}

	@Override
	public int insertCrimQpbTea(Map<String, Object> map) {
		int intResult = 0;

		if (StringNumberUtil.isNullOrEmpty(map.get("awardno")) ||
			StringNumberUtil.isNullOrEmpty(map.get("rewardid")) ||
			StringNumberUtil.isNullOrEmpty(map.get("rewarddate"))) {
			return intResult;
		}
		if("10000".equals(map.get("rewardid"))){
			intResult = tbdataSentchageMapper.insertCrimQpb(getCrimQpbTeaMapping(map));
		} else if("10003".equals(map.get("rewardid"))){
			intResult = tbdataSentchageMapper.insertCrimSjgzjj(getCrimQpbTeaMapping(map));
		} else {
			intResult = tbdataSentchageMapper.insertCrimTea(getCrimQpbTeaMapping(map));
		}
		
		return intResult;
	}


	@Override
	public int deleteCrimQpbTea(Map<String, Object> map) {
		int intResult = 0;
		
		if (StringNumberUtil.isNullOrEmpty(map.get("awardno")) ||
				StringNumberUtil.isNullOrEmpty(map.get("rewardid")) ||
				StringNumberUtil.isNullOrEmpty(map.get("rewarddate"))) {
				return intResult;
		}
		
		if("10000".equals(map.get("rewardid"))){
			intResult = tbdataSentchageMapper.deleteCrimQpb(getCrimQpbTeaMapping(map));
		} else if("10003".equals(map.get("rewardid"))){
			intResult = tbdataSentchageMapper.deleteCrimSjgzjj(getCrimQpbTeaMapping(map));
		} else {
			intResult = tbdataSentchageMapper.deleteCrimTea(getCrimQpbTeaMapping(map));
		}
		
		return intResult;
	}
	
	public Map getCrimQpbTeaMapping(Map<String, Object> map) {
		HashMap<String, Object> mapResult = new HashMap<String,Object>();
		//奖励和表之间的映射
		HashMap<String, String> mapTable = new HashMap<String,String>();
		mapTable.put("10000", "ZTIANJIN_VI_INTERFACE_QPB");
		mapTable.put("10001", "ZTIANJIN_VI_INTERFACE_TEA");
		mapTable.put("10002", "ZTIANJIN_VI_INTERFACE_TEA");
		mapTable.put("10003", "ZTIANJIN_VI_INTERFACE_SJGZJJFZ");
		mapTable.put("10005", "ZTIANJIN_VI_INTERFACE_TEA");
		mapTable.put("10006", "ZTIANJIN_VI_INTERFACE_TEA");
		HashMap<String, String> mapType = new HashMap<String,String>();
		//页面录入的奖励类型和数据库里类型间映射
		mapType.put("10000", "10000");
		mapType.put("10001", "98");
		mapType.put("10002", "99");
		mapType.put("10003", "10003");
		mapType.put("10005", "10005");
		mapType.put("10006", "10006");
		mapType.put("20000", "20000");
		mapType.put("20001", "20001");
		mapType.put("20002", "20002");
		mapType.put("20003", "20003");
		mapType.put("20004", "20004");
		mapType.put("20005", "20005");
		mapType.put("20006", "20006");
		mapType.put("20007", "20007");
		mapType.put("20008", "20008");
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");   
        java.util.Date date;
		try {
			date = myFormatter.parse(StringNumberUtil.returnString2(map.get("rewarddate")));
			mapResult.put("zfbh", StringNumberUtil.returnString2(map.get("zfbh")));
			
			int term = 1;
			int q = 1;
			//id为空时新增
			if (StringNumberUtil.isNullOrEmpty(map.get("id"))){
				mapResult.put("id", "");
				mapResult.put("y", StringNumberUtil.returnString2(CalendarUtil.getSpecifiedDayYear(date)));
				q = CalendarUtil.getSpecifiedDaySeason(date);
				
				
				if (q == 1 || q == 2) {
					term = 1;
				} else {
					term = 2;
				}
			//id不为空时删除
			} else {
				int id = 0;
				String strIds[] = StringNumberUtil.returnString2(map.get("id")).split("@");
				mapResult.put("id", strIds[0]);
				mapResult.put("y", strIds[1]);
				q = Integer.valueOf(strIds[2]);
				term = Integer.valueOf(strIds[2]);
			}
			mapResult.put("q", StringNumberUtil.returnString2(q));
			mapResult.put("term", StringNumberUtil.returnString2(term));
			mapResult.put("a", "0");
			mapResult.put("awardno", StringNumberUtil.returnString2(map.get("awardno")));
			mapResult.put("award", StringNumberUtil.returnString2(mapType.get(map.get("rewardid"))));
			mapResult.put("state", "1");
			mapResult.put("llrq", map.get("rewarddate"));
			mapResult.put("sj_type", "1");
			mapResult.put("donedate", date);
			mapResult.put("rewardname", StringNumberUtil.returnString2(map.get("rewardname")));
		} catch (ParseException e) {
			e.printStackTrace();
		}  

		return mapResult;
	}
	@Override
	public List<Map<String,Object>> getSentenceChangedData(Map<String, Object> map) {
		return MapUtil.convertKeyList2LowerCase(tbdataSentchageMapper.getSentenceChangedData(map));
	}
	
	@Override
	public int countSentenceChangedData(Map<String, Object> map) {
		return tbdataSentchageMapper.countSentenceChangedData(map);
	}
	public int updateBianDongFuDuByMap(Map<String, Object> map){
		return tbdataSentchageMapper.updateBianDongFuDuByMap(map);
	}
	
	@Override
	public List<String> selectJxCrimid(Map map) {
		return tbdataSentchageMapper.selectJxCrimid(map);
	}
	
	@Override
	public int updateAwardends(Map map) {
		return tbdataSentchageMapper.updateAwardends(map);
	}
	
	public List<Map> getBasicBianDongInfoList(Map<String, Object> map){
		return tbdataSentchageMapper.getBasicBianDongInfoList(map);
	}
	
	public List<Map<String,Object>> getSentenceChangeByBatch(Map<String, Object> map){
		return MapUtil.convertKeyList2LowerCase(tbdataSentchageMapper.getSentenceChangeByBatch(map));
	}
	
	
	public int countAllBianDongByCondition(Map<String, Object> map){
		return tbdataSentchageMapper.countAllBianDongByCondition(map);
	}
	
	public int countSentenceChangeByBatch(Map<String, Object> map){
		return tbdataSentchageMapper.countSentenceChangeByBatch(map);
	}
	
	@Override
	public String getZhiXingDate(Map map) {
		return tbdataSentchageMapper.getZhiXingDate(map);
	}
	@Override
	public String getXianXingQi(Map map) {
		return tbdataSentchageMapper.getXianXingQi(map);
	}
	
	public void manual_updatesentncechang(Map map){
		tbdataSentchageMapper.manual_UpdateSentncechang(map);
	}

	@Override
	public int removeSentenceChange(Map map) {
		// TODO Auto-generated method stub
		return tbdataSentchageMapper.removeSentenceChange(map);
	}
	
	
	
	@Override
	public int insertJiFenBuLu(Map map) {
		return  tbdataSentchageMapper.insertJiFenBuLu(map);
	}
	
	@Override
	public int updateJiFenBuLuById(Map map) {
		return tbdataSentchageMapper.updateJiFenBuLuById(map);
	}
	
	@Override
	public int removeJiFenBuLuById(Map map) {
		return tbdataSentchageMapper.removeJiFenBuLuById(map);
	}
	
	
	@Override
	public int insertKaoHeZongFen(Map<String,Object> map) {
		return  tbdataSentchageMapper.insertKaoHeZongFen(map);
	}
	
	@Override
	public int updateKaoHeZongFenById(Map<String,Object> map) {
		return tbdataSentchageMapper.updateKaoHeZongFenById(map);
	}
	
	@Override
	public List<Map<String,Object>> getKaoHeZongFenByCrimid(Map<String,Object> map){
		return tbdataSentchageMapper.getKaoHeZongFenByCrimid(map);
	}
	
	@Override
	public List<Map> getJiFenBuLuById(Map map){
		return tbdataSentchageMapper.getJiFenBuLuById(map);
	}
	
	
	@Override
	public List<Map> getCaiDingList(Map map){
		return tbdataSentchageMapper.getCaiDingList(map);
	}
	
	@Override
	@Transactional
	public int saveChangeSentenceChangeForHuNan(SystemUser user, Map<String,String> map){
		
		List list = (List) JsonUtil.Decode(map.get("data"));
		if (list != null && list.size() > 0) {
			for(int i=0;i<list.size();i++){
				UUID uuid = UUID.randomUUID();
				Map<String,Object> tempmap = (Map)list.get(i);
				//考核止日
				TbdataSentchage tbdataSentchage = new TbdataSentchage();
				tbdataSentchage.setCrimid((String)tempmap.get("crimid"));
				tbdataSentchage.setCategory((String)tempmap.get("category"));
				tbdataSentchage.setCourtname_code(map.get("courtnamecode"));
				tbdataSentchage.setCourtarea(map.get("courtnamecode"));
				tbdataSentchage.setCourtname(map.get("courtname"));
				tbdataSentchage.setCourttitle(map.get("courttitle"));
				tbdataSentchage.setCourtyear(map.get("curyear"));
				tbdataSentchage.setCourtshort(map.get("courtshort"));
				tbdataSentchage.setCourtsn(tempmap.get("courtsn")+"");
				tbdataSentchage.setCourtsanction(map.get("courtsanction").replace("-",""));
				tbdataSentchage.setCourtchange((String)tempmap.get("courtchange"));
				tbdataSentchage.setSentence((String)tempmap.get("sentence"));
				
				tbdataSentchage.setCourtchangefrom((String)tempmap.get("courtchangefrom"));
				tbdataSentchage.setCourtchangeto((String)tempmap.get("courtchangeto"));
				
				tbdataSentchage.setLosepower((String)tempmap.get("losepower"));
				tbdataSentchage.setAwardend((String)tempmap.get("awardend"));
				tbdataSentchage.setExectime(map.get("exectime").replace("-",""));
				tbdataSentchage.setIsthrough("1");
				tbdataSentchage.setUuid(uuid.toString());
				
				insertSelective(tbdataSentchage);
				
				FlowBase flowbase = new FlowBase();
				flowbase.setFlowdraftid((String)tempmap.get("flowdraftid"));
				flowbase.setText30("1");
//				flowBaseMapper.updateSensitiveByFlowdraftid(flowbase);
			}
		}
		
		return 1;
	}

	
	
	@Override
	public int updateSentenceAlterationRewardinfo(Map<String, Object> map) {
		return tbdataSentchageMapper.updateSentenceAlterationRewardinfo(map);
		
	}
	
}

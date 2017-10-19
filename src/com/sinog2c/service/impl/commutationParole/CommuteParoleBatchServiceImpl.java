package com.sinog2c.service.impl.commutationParole;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.commutationParole.TbxfCommuteParoleBatchMapper;
import com.sinog2c.dao.api.commutationParole.TbxfPunishmentRangMapper;
import com.sinog2c.dao.api.commutationParole.TbxfScreeningPropertyMapper;
import com.sinog2c.dao.api.commutationParole.TbxfSentenceAlterationMapper;
import com.sinog2c.model.commutationParole.TbxfCommuteParoleBatch;
import com.sinog2c.model.commutationParole.TbxfScreeningProperty;
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.commutationParole.CommuteParoleBatchService;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;


@Service("commuteParoleBatchService")
public class CommuteParoleBatchServiceImpl implements CommuteParoleBatchService {
	
	@Resource
	private TbxfCommuteParoleBatchMapper tbxfCommuteParoleBatchMapper;
	@Resource
	private TbxfScreeningPropertyMapper tbxfScreeningPropertyMapper;
	@Resource
	private TbxfSentenceAlterationMapper tbxfSentenceAlterationMapper;
	@Resource
	private TbxfPunishmentRangMapper tbxfPunishmentRangMapper;

	@Override
	@Transactional
	public int deleteCommuteParoleBatch(Long batchid) {
		tbxfCommuteParoleBatchMapper.deleteMergeByBatchid(batchid);
		return tbxfCommuteParoleBatchMapper.deleteByPrimaryKey(batchid);
	}

	@Override
	public TbxfCommuteParoleBatch getCommuteParoleBatchById(Integer batchid) {
		return tbxfCommuteParoleBatchMapper.selectByPrimaryKey(batchid);
	}

	@Override
	public int getCommuteParoleBatchCount(String departid ,String key) {
		return tbxfCommuteParoleBatchMapper.getgetCommuteParoleBatchCount(departid, key);
	}

	@Override
	public List<TbxfCommuteParoleBatch> getCommuteParoleBatchList(String departid, String key,
			String sortField, String sortOrder, int start, int end) {
		return tbxfCommuteParoleBatchMapper.getCommuteParoleBatchList(departid, key, sortField, sortOrder, start, end);
	}

	@Override
	public int insertCommuteParoleBatch(TbxfCommuteParoleBatch record) {
		return tbxfCommuteParoleBatchMapper.insert(record);
	}

	@Override
	public int updateCommuteParoleBatch(TbxfCommuteParoleBatch record) {
		return tbxfCommuteParoleBatchMapper.updateByPrimaryKey(record);
	}
	
	@Override
	public Map<String,Object> getCommuteParoleBatchInfo(Map<String,Object> departidMap){
		return MapUtil.convertKey2LowerCase(tbxfCommuteParoleBatchMapper.getCommuteParoleBatchInfo(departidMap));
	}

	@Override
	public void callStoredProcedure(String departid) {
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String tianjinconde = jyconfig.getProperty(GkzxCommon.TIANJIN_CODE);
		String provincecode = jyconfig.getProperty("provincecode");
		if(StringNumberUtil.belongTo(departid, tianjinconde)) {
			//天津
			tbxfCommuteParoleBatchMapper.callStoredProcedure(departid);
			tbxfCommuteParoleBatchMapper.callSenotherinfoProcedure(departid);
		}else if(GkzxCommon.SHANGHAI_PROVINCE.equals(provincecode)) {
			//上海
			tbxfCommuteParoleBatchMapper.callShStoredProcedure(departid);
			tbxfCommuteParoleBatchMapper.callPublicSenotherinfoProcedure(departid);//
		} else {
			//计算考核成绩\减刑起始时间，考核成绩汇总到表TBXF_PRISONERPERFORMANCEMERGE
			tbxfCommuteParoleBatchMapper.callPublicStoredProcedure(departid);
			//计算上次减刑裁定时间
			tbxfCommuteParoleBatchMapper.callPublicSenotherinfoProcedure(departid);
		}
		//罪犯类型映射：三类犯、老病残或未成年犯、限制减刑、
		//危害国家安全罪、危害公共安全、服刑期间再犯罪、发现漏罪等
		tbxfCommuteParoleBatchMapper.callCriminaltypemappingProcedure(departid);
	}

	@Override
	public int selectMaxBatch(String departId,String year) {
		return tbxfCommuteParoleBatchMapper.selectMaxBatch(departId,year);
	}
	
	@Override
	public void updateRewardDate(String departid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("departid", departid);
		map.put("xuhao", 1);
		Map<String,Object> currentBatchMap = tbxfCommuteParoleBatchMapper.getCurrentCommuteParoleBatch(map);
		List<TbxfSentenceAlteration> sentenceList = tbxfSentenceAlterationMapper.sentenceAlterationListByJailid(departid);
		List<Map<String,Object>> batchList = new ArrayList<Map<String,Object>>();
		for(int i=0; i<sentenceList.size(); i++) {
			Map<String,Object> rewardMap = new HashMap<String,Object>();
			rewardMap.put("remainderpunishment", currentBatchMap.get("REMAINDERPUNISHMENT"));
			rewardMap.put("rewardend", (Date) currentBatchMap.get("EXAMINEEND"));
			rewardMap.put("crimid", sentenceList.get(i).getCrimid());
			batchList.add(rewardMap);
			if(batchList.size()>500) {
				//更新筛查数据
				tbxfSentenceAlterationMapper.batchUpdateRewardDate(batchList);
				batchList.clear();
			}
		}
		if(batchList.size()>0) {
			//更新减刑起始时间、考核奖惩止日
			tbxfSentenceAlterationMapper.batchUpdateRewardDate(batchList);
		}
//		//更新考核起日
//		tbxfSentenceAlterationMapper.batchUpdateRewardStart(departid);
	}
	
	
	/**
	 * 批量保存减刑间隔期
	 */
	@Override
	public void updateInterval(String departid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("departid", departid);
		map.put("xuhao", 1);
		//共通化处理开始2015/1/5
		Map<String,Object> mapPro = new HashMap<String,Object>();
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jyconfig.getProperty("provincecode");
		mapPro.put("departid", "'" + departid + "','" + provincecode + "'");
		mapPro.put("type", "0");
		
		List<TbxfScreeningProperty> tbxfScreeningPropertyList = tbxfScreeningPropertyMapper.selectByDepartid(mapPro);
		TbxfScreeningProperty tbxfScreeningProperty = new TbxfScreeningProperty();
		for(int i = 0; i < tbxfScreeningPropertyList.size(); i++){
			tbxfScreeningProperty = tbxfScreeningPropertyList.get(i);
			if(departid.equals(tbxfScreeningProperty.getDepartid())){
				break;
			}
		}
		
		//减刑间隔期起：默认用上次减刑法院判裁日期的字段，也可在表TBXF_SCREENING_PROPERTY做单独的配制
		String courtsanction = GkzxCommon.SCREEN_INTERVAL_START;
		if(StringNumberUtil.notEmpty(tbxfScreeningProperty.getScontent())){
			courtsanction = tbxfScreeningProperty.getScontent();
		}
		map.put("courtsanction", courtsanction);
		//减刑间隔期止：默认用批次设置的考核止日字段，也可在表TBXF_SCREENING_PROPERTY做单独的配制
		String examineend = GkzxCommon.SCREEN_INTERVAL_END;
		if(StringNumberUtil.notEmpty(tbxfScreeningProperty.getEcontent())){
			examineend = tbxfScreeningProperty.getEcontent();
		}
		map.put("examineend", examineend);
		//共通化处理结束2015/1/5
		
		List<Map<String,Object>> intervaldateList = tbxfCommuteParoleBatchMapper.getIntervaldateList(map);
		List<Map<String,Object>> batchList = new ArrayList<Map<String,Object>>();
		Map<String,String> resultMap = null;
		
		for(int i=0; i<intervaldateList.size(); i++) {
			Map<String,Object> intervaldateMap = intervaldateList.get(i);
			String intervaldate = (String) intervaldateMap.get("INTERVALDATE");
			resultMap = this.getYearMonthDayVal(intervaldate);
//			TbxfSentenceAlteration sentence = new TbxfSentenceAlteration();
//			sentence.setIntervalyear(resultMap.get("year"));
//			sentence.setIntervalmonth(resultMap.get("month"));
//			sentence.setIntervalday(resultMap.get("day"));
			Map<String,Object> batchMap = new HashMap<String,Object>();
			batchMap.put("crimid", (String) intervaldateMap.get("CRIMID"));
			batchMap.put("intervalyear", resultMap.get("year"));
			batchMap.put("intervalmonth", resultMap.get("month"));
			batchMap.put("intervalday", resultMap.get("day"));
			batchList.add(batchMap);
			if(batchList.size()>500) {
				tbxfSentenceAlterationMapper.batchUpdateInterval(batchList);
				batchList.clear();
			}
		}
		if(batchList.size()>0) {
			tbxfSentenceAlterationMapper.batchUpdateInterval(batchList);
		}
	}
	
	/**
	 * 更新罪犯的现刑期年、月、日、批次ID, 
	 * 注释由YangZR编写
	 */
	public void updateNowpunishment(String departid) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("departid", departid);
		
		//通过表tbxf_sentencealteration的裁判余刑年、月、日，批次设置的余刑起算日，计算出现余刑年、月、日
		List<Map<String,Object>> nowpunishmentList = tbxfCommuteParoleBatchMapper.getSurplusSentence(map);
		//
		List<Map> batchList = new ArrayList<Map>();
		Map<String,String> resultMap = null;
		
		
		for(int i=0; i<nowpunishmentList.size(); i++) {
			Map<String,Object> nowpunishmentMap = nowpunishmentList.get(i);
			String nowpunishmentdate = (String) nowpunishmentMap.get("SURPLUSSENTENCE");//计算出的现刑期
			
			if(StringNumberUtil.isEmpty(nowpunishmentdate)) {
				continue;
			}
			//格式化现刑期 年月日，将其分成三个字段存入Map，如果是无期、死缓，则只存9995、9996
			resultMap = this.getYearMonthDayVal(nowpunishmentdate);
			//
			Map<String,Object> batchMap = new HashMap<String,Object>();
			batchMap.put("crimid", (String)nowpunishmentMap.get("CRIMID"));
			batchMap.put("nowpunishmentyear", resultMap.get("year"));
			batchMap.put("nowpunishmentmonth", resultMap.get("month"));
			batchMap.put("nowpunishmentday", resultMap.get("day"));
			batchMap.put("batchid", Integer.valueOf(nowpunishmentMap.get("BATCHID").toString()));
			batchList.add(batchMap);
			//更新罪犯的现刑期年、月、日、批次ID
			if(batchList.size()>500) {
				tbxfSentenceAlterationMapper.batchUpdateNowpunishment(batchList);
				batchList.clear();
			}
		}
		//更新罪犯的现刑期年、月、日、批次ID
		if(batchList.size()>0) {
			tbxfSentenceAlterationMapper.batchUpdateNowpunishment(batchList);
		}
		
	}
	
	/**
	 * 格式化数据 年月日，将其分成三个字段存入Map，如果是无期、死缓，则只存9995、9996
	 * @param yearMonthDay
	 * @return
	 */
	private Map<String,String> getYearMonthDayVal(String yearMonthDay) {
		String year = "";
		String month = "";
		String day = "";
		if(StringNumberUtil.isEmpty(yearMonthDay)){
			year = "0";
			month = "0";
			day = "0";
		}else if(yearMonthDay.equals(GkzxCommon.XINGQI_WUQI) || yearMonthDay.equals(GkzxCommon.XINGQI_SIHUAN) || yearMonthDay.equals(GkzxCommon.XINGQI_SIXING)) {
			year = yearMonthDay;
		} else {
			if(yearMonthDay.indexOf(GkzxCommon.YEAR)>0) {
				year = yearMonthDay.substring(0, yearMonthDay.indexOf(GkzxCommon.YEAR));
			}
			if(yearMonthDay.indexOf(GkzxCommon.MONTH)>0) {
				month = yearMonthDay.substring(yearMonthDay.indexOf(GkzxCommon.YEAR)+1, yearMonthDay.indexOf(GkzxCommon.MONTHS));
			}
			if(yearMonthDay.indexOf(GkzxCommon.DAY)>0) {
				if(yearMonthDay.indexOf(GkzxCommon.MONTH)>0) {
					day = yearMonthDay.substring(yearMonthDay.indexOf(GkzxCommon.MONTH)+1, yearMonthDay.indexOf(GkzxCommon.DAY));
				} else {
					day = yearMonthDay.substring(yearMonthDay.indexOf(GkzxCommon.YEAR)+1, yearMonthDay.indexOf(GkzxCommon.DAY));
				}
			}
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("year", year);
		map.put("month", month);
		map.put("day", day);
		return map;
	}
	
	public int checkUnfinishedCase(String departid) {
		return tbxfCommuteParoleBatchMapper.checkUnfinishedCase(departid);
	}

	@SuppressWarnings("all")
	public String shengChengData(HttpServletRequest request, SystemUser user) {
		Map map = new HashMap();
		String value = "";
		try {
			map.put("departid", user.getDepartid());
			map.put("status", 1);
			map.put("cstatus", 1);
			map.put("isdeclare", 0);
			//1、所有的在资格筛查表中，并且在在罪犯基本信息表中的罪犯进行修改
			tbxfCommuteParoleBatchMapper.shengChengData1(map);
			//2、插入在基本信息中，但是不在资格筛查表中的罪犯
			List<Map> listMaps = tbxfCommuteParoleBatchMapper.shengChengData3();
			if (listMaps != null) {
				for (Map map2 : listMaps) {
					String crimid = map2.get("CRIMID").toString();
					map.put("crimid", crimid);
					tbxfCommuteParoleBatchMapper.shengChengData2(map);
				}
			}
			value = "1";
		} catch (Exception e) {
			value = "0";
			e.printStackTrace();
		}
		return value;
	}

	@Override
	public void callCallBackProcedure(String departid) {
	    try {
	    	tbxfCommuteParoleBatchMapper.callPublicCallBackProcedure(departid);
	    } catch(Exception e) {
	    }
	}

	@Override
	public void clearMaxBatch(Map<String, Object> map) {
		 tbxfCommuteParoleBatchMapper.clearMaxBatch(map);
	}

	@Override
	public void updateMaxBatch(Map<String, Object> map) {
		tbxfCommuteParoleBatchMapper.updateMaxBatch(map);
	}

	@Override
	public void updateCrimeType(Map<String, Object> map) {
		tbxfCommuteParoleBatchMapper.updateCrimeType(map);
		return;
	}
	
	@Override
	public void callCPScreeingDataProcedure(String departid, String id, String userid) {
	    try {
	    	tbxfCommuteParoleBatchMapper.callCPScreeingDataProcedure(departid,id,userid);
	    } catch(Exception e) {
	    }
	}
	
	
	
	public void dealKaoHeJianChen4HuNan(String departid){
		
		//计算：考核分(INT24), 累计表扬(INT2)、余分(INT40)、累计奖分(INT28)、累计扣分(INT45) 、减刑前一年（本批次考核止日前一年）累计扣分(TEXT3)
		//重大违规次数（一次性扣分5分或者以上）(TEXT4)
		List<Map<String,Object>> kaoHeList = MapUtil.convertKeyList2LowerCase(tbxfCommuteParoleBatchMapper.getKaoHeNum4HuNan(departid));
		
		//
		List<Map<String,Object>> jiangLiInfoList = MapUtil.convertKeyList2LowerCase(tbxfCommuteParoleBatchMapper.getJiangLiInfo4HuNan(departid));
		
		List<Map<String,Object>> koufenInfoList = MapUtil.convertKeyList2LowerCase(tbxfCommuteParoleBatchMapper.getKoufenInfoList4HuNan(departid));
		
		List<Map<String,Object>> jjfzList = MapUtil.convertKeyList2LowerCase(tbxfCommuteParoleBatchMapper.getJiJiFenziList4HuNan(departid));
		
		
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : kaoHeList){
			
			String crimid = StringNumberUtil.getStrFromMap("crimid", map);
			//处理奖励情况
			String singleJiangLiInfo = dealSingleJiangChenInfo(crimid, jiangLiInfoList, "jiangli");//dealSingleJiangLiInfo(crimid, jiangLiInfoList);
			map.put("text1", singleJiangLiInfo);
			//处理扣分情况
			String singleKoufenInfo = dealSingleJiangChenInfo(crimid, koufenInfoList, "koufen");//dealSingleKoufenInfo(crimid, koufenInfoList);
			map.put("text2", singleKoufenInfo);
			//处理积极分子年份情况
			String singJJFZInfo = dealSingleJiangChenInfo(crimid, jjfzList, "jj_info");//dealSingleJJFZInfo(crimid, jjfzList);
			map.put("text5", singJJFZInfo);
			
			list.add(map);
			
			if(list.size()>500) {
				Map<String,Object> tempMap = new HashMap<String,Object>();
				tempMap.put("list", list);
				tbxfSentenceAlterationMapper.batchUpdateJiangChenInfo(tempMap);
				list.clear();
			}
		}
		if(list.size()>0) {
			Map<String,Object> tempMap = new HashMap<String,Object>();
			tempMap.put("list", list);
			tbxfSentenceAlterationMapper.batchUpdateJiangChenInfo(tempMap);
		}
	}
	
	
	private String dealSingleJiangChenInfo(String crimid, List<Map<String,Object>> jiangLiInfoList, String key){
		
		String result = null;
		if(null != jiangLiInfoList && jiangLiInfoList.size() > 0){
			
			List<String> tList = new ArrayList<String>();
			
			for(Map<String,Object> tmap : jiangLiInfoList){
				String tCrimid = tmap.get("crimid").toString().trim();
				if(crimid.equals(tCrimid)){
					if(StringNumberUtil.notEmpty(tmap.get(key))){
						tList.add(tmap.get(key).toString().trim());
					}
				}
			}
			
			result = StringNumberUtil.formatList2String(tList, "，");
		}
		return result;
	}
	
	
	public void dealKaoHeJianChen4HuNanSingle(String crimid){
		
		//计算：考核分(INT24), 累计表扬(INT2)、余分(INT40)、累计奖分(INT28)、累计扣分(INT45) 、减刑前一年（本批次考核止日前一年）累计扣分(TEXT3)
		//重大违规次数（一次性扣分5分或者以上）(TEXT4)
		List<Map<String,Object>> kaoHeList = MapUtil.convertKeyList2LowerCase(tbxfCommuteParoleBatchMapper.getKaoHeNum4HuNanSingle(crimid));
		//
		List<Map<String,Object>> jiangLiInfoList = MapUtil.convertKeyList2LowerCase(tbxfCommuteParoleBatchMapper.getJiangLiInfo4HuNanSingle(crimid));
		
		List<Map<String,Object>> koufenInfoList = MapUtil.convertKeyList2LowerCase(tbxfCommuteParoleBatchMapper.getKoufenInfoList4HuNanSingle(crimid));
		
		List<Map<String,Object>> jjfzList = MapUtil.convertKeyList2LowerCase(tbxfCommuteParoleBatchMapper.getJiJiFenziList4HuNanSingle(crimid));
		
		
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : kaoHeList){
			
			//处理奖励情况
			String singleJiangLiInfo = dealSingleJiangChenInfo(crimid, jiangLiInfoList, "jiangli");//dealSingleJiangLiInfo(crimid, jiangLiInfoList);
			map.put("text1", singleJiangLiInfo);
			//处理扣分情况
			String singleKoufenInfo = dealSingleJiangChenInfo(crimid, koufenInfoList, "koufen");//dealSingleKoufenInfo(crimid, koufenInfoList);
			map.put("text2", singleKoufenInfo);
			//处理积极分子年份情况
			String singJJFZInfo = dealSingleJiangChenInfo(crimid, jjfzList, "jj_info");//dealSingleJJFZInfo(crimid, jjfzList);
			map.put("text5", singJJFZInfo);
			
			list.add(map);
			
			if(list.size()>500) {
				Map<String,Object> tempMap = new HashMap<String,Object>();
				tempMap.put("list", list);
				tbxfSentenceAlterationMapper.batchUpdateJiangChenInfo(tempMap);
				list.clear();
			}
		}
		if(list.size()>0) {
			Map<String,Object> tempMap = new HashMap<String,Object>();
			tempMap.put("list", list);
			tbxfSentenceAlterationMapper.batchUpdateJiangChenInfo(tempMap);
		}
	}
	
	
	/**
	 * 初始化数据
	 * @param departid
	 */
	@Override
	@Transactional
	public void initData4Qualification(String departid) {
		
		//初始化第一步。将一些基础数据初始化到表TBXF_SENTENCEALTERATION中去，用于在办理减刑案件时使用
		tbxfCommuteParoleBatchMapper.callPublicStoredProcedure(departid);
		
		initSecondData4Qualifier(departid);
		
	}
	
	
	
	/**
	 * 初始化第二步。生成更多基础数据到表TBXF_QUALIFIER_TEMP中去，用于进行资格筛查时计算用
	 * @param departid
	 */
	private void initSecondData4Qualifier(String departid){
		
		//只处理有资格参与资格筛查(即除了正在办理减刑案件的罪犯以外的所有本单位罪犯)的数据初始化到临时表TBXF_QUALIFIER_TEMP中去。
		//清空表TBXF_QUALIFIER_TEMP数据，或者清空前备份表TBXF_QUALIFIER_TEMP的数据，以防万一。
		tbxfCommuteParoleBatchMapper.deleteQualifierTemp(departid);
		
		
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("departid", departid);
		List<Map<String,Object>> allQualifierItemList = MapUtil.convertKeyList2LowerCase(tbxfPunishmentRangMapper.getAllQualifierItem(paramap));
		
		Map<String,String> allQualifierItemMap = new HashMap<String,String>();
		for(Map<String,Object> item : allQualifierItemList){
			String key = StringNumberUtil.getStrFromMap("col_name", item);
			String formula = StringNumberUtil.getStrFromMap("formula", item);
			if(StringNumberUtil.notEmpty(formula)){
				allQualifierItemMap.put(key, formula);
			}
		}
		
		StringBuilder sbInsert = new StringBuilder();
		StringBuilder sbSelect = new StringBuilder();
		sbInsert.append("insert into TBXF_QUALIFIER_TEMP (crimid,departid,batchid");
		sbSelect.append(" select sen.crimid,sen.jailid,sen.batchid");
		
		Set<String> keySet = allQualifierItemMap.keySet();
		for(String key : keySet){
			String formula = allQualifierItemMap.get(key);
			sbInsert.append(",").append(key);
			sbSelect.append(",").append(formula);
		}
		
		sbInsert.append(")");
		
		sbSelect.append(" from TBXF_SENTENCEALTERATION sen,");
		sbSelect.append(" TBPRISONER_BASE_CRIME tbc,");
		sbSelect.append(" TBXF_PRISONERPERFORMANCEMERGE pem,");
		sbSelect.append(" TBXF_COMMUTEPAROLE_BATCH bat,");
		sbSelect.append(" UV_LAST_COMMUTE_SENTCHAGE ucs,");
		sbSelect.append(" UV_COMMUTE_SENTCHAGE_TIMES ust");
		sbSelect.append(" where sen.jailid=").append(departid);
		sbSelect.append(" and tbc.detainstatus='在押'");
		sbSelect.append(" and sen.crimid=tbc.crimid");
		sbSelect.append(" and sen.crimid=pem.crimid");
		sbSelect.append(" and sen.batchid=pem.batchid");
		sbSelect.append(" and sen.batchid=bat.batchid");
		sbSelect.append(" and sen.crimid=ucs.crimid(+)");
		sbSelect.append(" and sen.crimid=ust.crimid(+)");
		sbSelect.append(" and not exists(select 'x' from tbflow_base t2 where t2.applyid=sen.crimid and t2.flowdefid = 'other_zfjyjxjssp' ");
		sbSelect.append(" and (t2.state=-2 or t2.state=-1 or t2.state=0 or t2.state=2) ) ");
		
		String sql = sbInsert.append(sbSelect).toString();
		System.out.println(sql);
		
		paramap.put("sql", sql);
		tbxfPunishmentRangMapper.insertBySql(paramap);
	}

	@Override
	public void generateQualificationList(String departid) {
		tbxfCommuteParoleBatchMapper.generateQualificationList(departid);
		
	}
	
	/**
	 * 考核数据校正
	 */
	@Override
	public String correctKaoHeDataInfo(Map<String,Object> paramap){
		
		//数据初始化
		tbxfCommuteParoleBatchMapper.callProcInitSentenceData(paramap);
		
		//考核初始化
		String crimid = StringNumberUtil.getStrFromMap("crimid", paramap);
		dealKaoHeJianChen4HuNanSingle(crimid);
		
		return "1";
		
	}
	
	
}
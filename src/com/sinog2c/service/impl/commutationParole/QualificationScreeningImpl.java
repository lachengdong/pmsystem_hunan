package com.sinog2c.service.impl.commutationParole;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wltea.expression.datameta.Variable;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.commutationParole.TbxfCommutationReferenceMapper;
import com.sinog2c.dao.api.commutationParole.TbxfCommuteParoleBatchMapper;
import com.sinog2c.dao.api.commutationParole.TbxfPunishmentRangMapper;
import com.sinog2c.dao.api.commutationParole.TbxfScreeningMapper;
import com.sinog2c.dao.api.commutationParole.TbxfScreeningPropertyMapper;
import com.sinog2c.dao.api.commutationParole.TbxfSentenceAlterationMapper;
import com.sinog2c.dao.api.system.SystemOrganizationMapper;
import com.sinog2c.model.commutationParole.TbxfScreeningProperty;
import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.system.SystemOrganization;
import com.sinog2c.service.api.commutationParole.QualificationScreeningService;
import com.sinog2c.util.common.CalendarUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.QualifierUtil;
import com.sinog2c.util.common.StringNumberUtil;


/**
 * 
 * 资格筛查
 * 
 * @author
 *
 */

@Service("qualificationScreeningService")
public class QualificationScreeningImpl implements QualificationScreeningService{
	
	@Resource
	private TbxfCommutationReferenceMapper tbxfCommutationReferenceMapper;//幅度参照
	
	@Resource
	private TbxfSentenceAlterationMapper tbxfSentenceAlterationMapper;//刑期变动
	
	@Resource
	private TbxfScreeningMapper tbxfScreeningMapper;//减刑假释幅度筛查信息
	
	@Resource
	private TbxfCommuteParoleBatchMapper tbxfCommuteParoleBatchMapper;
	
	@Resource
	private SystemOrganizationMapper systemOrganizationMapper;
	
	@Resource
	private TbxfPunishmentRangMapper tbxfPunishmentRangMapper;
	
	StringBuffer excuseBuffer = new StringBuffer();
	String screeningdebug = GkzxCommon.ZERO;
	private static HashMap<String, String> xmlLogMsg = new GetProperty().readXml(GkzxCommon.XMLLOGMSG, null);//获取Message

	@Resource
	private TbxfScreeningPropertyMapper tbxfScreeningPropertyMapper;
	/**
	 * 筛查数据
	 */
	public void qualificationScreening(String departid, String orgid, HttpServletRequest request){
		
		List<HashMap<String, Object>> wtList = tbxfCommutationReferenceMapper.commutationWideAndThinList(departid);
		List<Map> batchUpdate = new ArrayList<Map>();
		List<Map> jiashibatchUpdate = new ArrayList<Map>();
		Map<String,Object> sentenceMap = new HashMap<String,Object>();
		sentenceMap.put("departid", departid);
		sentenceMap.put("orgid", orgid);
		
		//共通化处理开始2015/1/5
		Map<String,Object> mapPro = new HashMap<String,Object>();
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String provincecode = jyconfig.getProperty("provincecode");
		String noscreeningprovince = jyconfig.getProperty("noscreeningprovince");
		sentenceMap.put("provincecode", provincecode);
		screeningdebug = jyconfig.getProperty("screeningdebug") == null?GkzxCommon.ZERO:jyconfig.getProperty("screeningdebug");
		mapPro.put("departid", "'" + departid + "','" + provincecode + "'");
		mapPro.put("type", "1");//无期死缓起始时间
		
		List<TbxfScreeningProperty> tbxfScreeningPropertyList = tbxfScreeningPropertyMapper.selectByDepartid(mapPro);
		TbxfScreeningProperty tbxfScreeningProperty = new TbxfScreeningProperty();
		for(int i = 0; i < tbxfScreeningPropertyList.size(); i++){
			tbxfScreeningProperty = tbxfScreeningPropertyList.get(i);
			if(departid.equals(tbxfScreeningProperty.getDepartid())){
				break;
			}
		}
		sentenceMap.put("judgedate", StringNumberUtil.isNullOrEmpty(tbxfScreeningProperty.getScontent())?GkzxCommon.SCREEN_COMMUTATIONDATE_START_WUQISIHUAN:tbxfScreeningProperty.getScontent());
		sentenceMap.put("examineend", StringNumberUtil.isNullOrEmpty(tbxfScreeningProperty.getEcontent())?GkzxCommon.SCREEN_COMMUTATIONDATE_END:tbxfScreeningProperty.getEcontent());
		
		mapPro.put("type", "2");//有期徒刑的起始时间
		tbxfScreeningPropertyList = tbxfScreeningPropertyMapper.selectByDepartid(mapPro);
		tbxfScreeningProperty = new TbxfScreeningProperty();//初始化
		for(int i = 0; i < tbxfScreeningPropertyList.size(); i++){
			tbxfScreeningProperty = tbxfScreeningPropertyList.get(i);
			if(departid.equals(tbxfScreeningProperty.getDepartid())){
				break;
			}
		}
		sentenceMap.put("execdate", StringNumberUtil.isNullOrEmpty(tbxfScreeningProperty.getScontent())?GkzxCommon.SCREEN_COMMUTATIONDATE_START_YOUQI:tbxfScreeningProperty.getScontent());
		
		mapPro.put("type", "3");//执行刑期
		tbxfScreeningPropertyList = tbxfScreeningPropertyMapper.selectByDepartid(mapPro);
		tbxfScreeningProperty = new TbxfScreeningProperty();//初始化
		for(int i = 0; i < tbxfScreeningPropertyList.size(); i++){
			tbxfScreeningProperty = tbxfScreeningPropertyList.get(i);
			if(departid.equals(tbxfScreeningProperty.getDepartid())){
				break;
			}
		}
		sentenceMap.put("sentencestimemonths", StringNumberUtil.isNullOrEmpty(tbxfScreeningProperty.getScontent())?GkzxCommon.SCREEN_EXECUTESENTENCE_START:tbxfScreeningProperty.getScontent());
		sentenceMap.put("examineendmonths", StringNumberUtil.isNullOrEmpty(tbxfScreeningProperty.getEcontent())?GkzxCommon.SCREEN_EXECUTESENTENCE_END:tbxfScreeningProperty.getEcontent());
		//共通化处理结束2015/1/5
		
		//保持资格筛查表中罪犯与基本信息表中罪犯一致.
		tbxfScreeningMapper.deleteWastedata(departid);
		tbxfScreeningMapper.insertNewdata(departid);	
		
		double count = tbxfSentenceAlterationMapper.getSentenceinfoCount(sentenceMap);
		List<HashMap<String, Object>> referenceList = tbxfCommutationReferenceMapper.commutationReferenceList(departid);//减刑幅度参照
		
		Map departMap = new HashMap();
		departMap.put("departid", departid);
		Map temMap = tbxfCommuteParoleBatchMapper.getCommuteParoleBatchInfo(departMap);
		SystemOrganization systemOrganization = systemOrganizationMapper.getProvinceCode(departid);
		int batchid=0;
		if(temMap !=null&&temMap.size()>0){
			batchid = Integer.parseInt(temMap.get("BATCHID").toString());
		}

		double complete = 0;
		for(int k=0; k<count; k+=500) {
			sentenceMap.put("start", k);
			sentenceMap.put("end", k+500);
			List<TbxfSentenceAlteration> sentenceList = new ArrayList<TbxfSentenceAlteration>();
			List<TbxfSentenceAlteration> orgSentenceList = tbxfSentenceAlterationMapper.getSentenceinfoList(sentenceMap);
			for(int i=0; i<orgSentenceList.size(); i++) {
				TbxfSentenceAlteration tbxfSentence = new TbxfSentenceAlteration();
				tbxfSentence = orgSentenceList.get(i);
				String crimid = orgSentenceList.get(i).getCrimid();
				List<Map<String,Object>> wideAndThinList = new ArrayList<Map<String,Object>>();
				for(int j=0; j<wtList.size(); j++) {
					String wtcrimid = (String)wtList.get(j).get("CRIMID");
					if(crimid.equals(wtcrimid)) {
						wideAndThinList.add(wtList.get(j));
					}
				}
				tbxfSentence.setWtList(wideAndThinList);
				sentenceList.add(tbxfSentence);
			}
			if(sentenceList!=null && sentenceList.size()>0){
				if (StringNumberUtil.belongTo(provincecode, noscreeningprovince) ) {
					for (int i = 0; i < sentenceList.size(); i++) {
						Map map = new HashMap();
						map.put("crimid", sentenceList.get(i).getCrimid());
						map.put("batchid", temMap.get("BATCHID"));
						Map screenMap = new HashMap();
						screenMap.put("batchid", temMap.get("BATCHID"));
						screenMap.put("punid", 0);
						screenMap.put("departid", departid);
						screenMap.put("refid", 0);
						screenMap.put("crimid", sentenceList.get(i).getCrimid());
						screenMap.put("status", 1);
						screenMap.put("cstatus", 1);
						screenMap.put("isdeclare", 0);
						batchUpdate.add(screenMap);
						tbxfCommutationReferenceMapper.batchUpdateSx(batchUpdate);
						batchUpdate.clear();
						complete++;
						java.text.DecimalFormat df=new java.text.DecimalFormat("#.##");   
						double percent = (complete*100/count)>=100?99.99:(complete*100/count);
						if(percent!=100) {
							request.getSession().setAttribute("percent", df.format(percent));
						}
					}
				}else{
					if(referenceList!=null && referenceList.size()>0){
						for(int i=0;i<sentenceList.size();i++){
							excuseBuffer.delete(0, excuseBuffer.length());
							boolean hasDone = false;
							for(int j=0;j<referenceList.size();j++){
								TbxfSentenceAlteration sentenceAlteration = sentenceList.get(i);
								sentenceAlteration.setBatchid(batchid);
								HashMap<String, Object> map = referenceList.get(j);
								excuseBuffer.append("\n");
								excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00003"), map.get("PUNID").toString()));
								if(sentenceAlteration.getJailid().equals((String)map.get("DEPARTID"))){
									if(judgeCondition(sentenceAlteration, map, systemOrganization.getOrgid())){
										hasDone = true;
										Map screenMap = new HashMap();
										int int2 = ((BigDecimal)map.get("TYPE")).intValue();
										screenMap.put("batchid", sentenceAlteration.getBatchid());
										screenMap.put("departid", map.get("DEPARTID"));
										screenMap.put("crimid", sentenceAlteration.getCrimid());
										screenMap.put("status", 1);
										screenMap.put("cstatus", 1);
										screenMap.put("int1", 0);
										screenMap.put("excuse", excuseBuffer.toString());
										if(int2==2) {
//											screenMap.put("refid", -1);
											screenMap.put("int3", map.get("PUNID"));
											screenMap.put("int2", int2);
											jiashibatchUpdate.add(screenMap);
										} else {
											screenMap.put("refid", map.get("REFID"));
											screenMap.put("punid", map.get("PUNID"));
											batchUpdate.add(screenMap);
										}
										if(batchUpdate.size()>1000) {
											//更新筛查符合减刑数据
											tbxfCommutationReferenceMapper.batchUpdate(batchUpdate);
											batchUpdate.clear();
										}
										
										if(jiashibatchUpdate.size()>1000) {
											//更新筛查符合假释的数据
											tbxfCommutationReferenceMapper.jiashibatchUpdate(jiashibatchUpdate);
											jiashibatchUpdate.clear();
										}
										if(int2==2) {
											continue;
										} else {
											break;
										}
									} else if(!hasDone && (j==referenceList.size()-1)) {
										Map screenMap = new HashMap();
										screenMap.put("batchid", sentenceAlteration.getBatchid());
										screenMap.put("punid", -1);
										screenMap.put("departid", map.get("DEPARTID"));
										screenMap.put("refid", -1);
										screenMap.put("crimid", sentenceAlteration.getCrimid());
										screenMap.put("status", 0);
										screenMap.put("cstatus", 0);
//										screenMap.put("status", 0);
//										screenMap.put("cstatus", 0);
										screenMap.put("int1", 0);
										screenMap.put("excuse", excuseBuffer.toString());
										batchUpdate.add(screenMap);
										if(batchUpdate.size()>1000) {
											//更新筛查符合减刑数据
											tbxfCommutationReferenceMapper.batchUpdate(batchUpdate);
											batchUpdate.clear();
										}
									}
								}
							}
							complete++;
							java.text.DecimalFormat df=new java.text.DecimalFormat("#.##");   
							double percent = (complete*100/count)>=100?99.99:(complete*100/count);
							if(percent!=100) {
								request.getSession().setAttribute("percent", df.format(percent));
							}
						}
					}
				}
			}			
		}
		if(jiashibatchUpdate.size()>0) {
			//更新筛查符合假释的数据
			tbxfCommutationReferenceMapper.jiashibatchUpdate(jiashibatchUpdate);
			jiashibatchUpdate.clear();
		}
		
		if(batchUpdate.size()>0) {
			//更新筛查符合减刑数据
			tbxfCommutationReferenceMapper.batchUpdate(batchUpdate);
			batchUpdate.clear();
		}
		
		if (!StringNumberUtil.belongTo(provincecode, noscreeningprovince)) {
			updateSuggest(request, departid, orgid, wtList);
		}
	}
	/**
	 * 更新建议幅度
	 * @param request
	 * @param departid
	 * @param orgid
	 * @param wtList
	 */
	public void updateSuggest(HttpServletRequest request,String departid, String orgid,List<HashMap<String, Object>> wtList) {
		Map<String,Object> mergeMap = new HashMap<String,Object>();
		mergeMap.put("departid", departid);
		mergeMap.put("orgid", orgid);
		//获取符合资格的奖励信息条数
		int count = tbxfCommutationReferenceMapper.getMergeandreferenceCount(mergeMap);
		//获得减刑方案和方案详细信息
		List<HashMap<String,Object>> refList = tbxfCommutationReferenceMapper.getReferenceListBydepartid(mergeMap);
		//获得奖惩明细信息
		List<HashMap<String,Object>> mergeList = tbxfCommutationReferenceMapper.getMergeReferenceList(mergeMap);
		List<Map<String,Object>> batchList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> checkList = new ArrayList<Map<String,Object>>();
		for(int i=0; i<count; i+=500) {
			mergeMap.put("start", i);
			mergeMap.put("end", i + 500);
			//获取500个罪犯的奖励信息
			List<HashMap<String,Object>> criminalList = tbxfCommutationReferenceMapper.getMergeandreferenceList(mergeMap);
			for(int k=0; k<criminalList.size(); k++) {
				HashMap<String,Object> criminalMap = criminalList.get(k);
				for(int j=0; j<wtList.size(); j++) {
					//存在从宽从严的情况
					if(wtList.get(j).get("CRIMID").equals(criminalMap.get("CRIMID")) && wtList.get(j).get("PUNID").equals(criminalMap.get("PUNID"))) {
						criminalList.get(k).put("RANGESTART", wtList.get(j).get("RANGESTART"));
						break;
					}
				}
				//获得该罪犯的减刑方案详细信息
				List<HashMap<String,Object>> crimRefList = this.getCrimRefAndMergeMap(criminalMap.get("PUNID").toString(), refList, "PUNID");
				for(int l=0; l<crimRefList.size(); l++) {
					HashMap<String,Object> refmap = crimRefList.get(l);
					int refid = ((BigDecimal)crimRefList.get(l).get("REFID")).intValue();
					//获得该罪犯的奖励信息
					List<HashMap<String,Object>> crimMergeList = this.getCrimRefAndMergeMap(crimRefList.get(l).get("REFID").toString(), mergeList, "REFID");
					//crimMergeList=该罪犯的奖励信息；criminalMap=该罪犯的奖励信息（从宽从严含、幅度限制含）；refmap=该罪犯的减刑方案详细信息
					int suggest = this.getSuggest(crimMergeList, criminalMap, refmap);
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("crimid", criminalMap.get("CRIMID").toString());
					map.put("refid", refid);
					if(suggest>0) {
						map.put("status", 1);
						map.put("cstatus", 1);
						map.put("suggest", suggest);
						batchList.add(map);
						break;
					} else if(((BigDecimal)refmap.get("ISINTERVAL")).intValue()==1 && suggest==0 && l==crimRefList.size()-1) {
						map.put("status", 0);
						map.put("cstatus", 0);
						map.put("suggest", 0);
						checkList.add(map);
					}
				}
				if(checkList.size()>500) {
					tbxfCommutationReferenceMapper.batchUpdateSuggestopion(checkList);
					checkList.clear();
				}
				if(batchList.size()>500) {
					tbxfCommutationReferenceMapper.batchUpdateSuggestopion(batchList);
					batchList.clear();
				}
			}
		}
		if(checkList.size()>0) {
			tbxfCommutationReferenceMapper.batchUpdateSuggestopion(checkList);
		}
		if(batchList.size()>0) {
			tbxfCommutationReferenceMapper.batchUpdateSuggestopion(batchList);
		}
	}
	
	/**
	 * 判断是否满足筛查条件
	 * @param sentenceAlteration 刑期变动信息
	 * @param map 减刑幅度参照
	 * @return
	 */
	private boolean judgeCondition(TbxfSentenceAlteration sentenceAlteration,
			HashMap<String, Object> map, String provinceid) {
		/**
		 * 调试用
		 */
		if (judgeSentence(sentenceAlteration, map, provinceid) // 犯罪时间
			&& judgeFirstcommutation(sentenceAlteration, map) //判断是否减为有期徒刑后首次减刑
			&& judgeAlreadycommutation(sentenceAlteration, map) //判断是否减过刑
			&& judgePunishmentType(sentenceAlteration, map) //现刑期种类
			&& judgePunishment(sentenceAlteration, map) // 现刑期
			&& judgeStartperiod(sentenceAlteration, map) // 起始时间		// 无期死缓入监日期,有期羁押日期到批次减刑起始时间
			&& judgeIntervalperiod(sentenceAlteration, map) // 间隔时间
			&& judgeOriginalType(sentenceAlteration, map) // 原判刑期
			&& judgeOriginal(sentenceAlteration, map) // 原判刑期
			&& judgeExecutesentencemonths(sentenceAlteration, map, provinceid)// 执行刑期
			&& judgeExecutesentenceLimit(sentenceAlteration, map, provinceid)
			&& judgeLouzuiExecution(sentenceAlteration, map)//判断余罪加刑
			&& judgeYuneiExecution(sentenceAlteration, map)//判断狱内加刑
		) {
			return true;
		}
		return false;
	}
	/**
	 * 
	     * 此方法描述的是：漏罪加刑的判断
		 * @param   name  
		 * @param  @return 
		 * @Exception 异常对象
	     * @author: 栾学峰 
	     * @version: 2015-1-4 下午01:52:08
	 */
	private boolean judgeLouzuiExecution(TbxfSentenceAlteration sentenceAlteration, HashMap<String, Object> map) {
		int yusinmonths = 0;
		int executionmonths = this.getWtMap(sentenceAlteration, map, "YUSINEXECUTIONMONTHS");
		if(sentenceAlteration.getYusinmonths()!=null) {//漏罪加刑
			yusinmonths = sentenceAlteration.getYusinmonths();
		}
		if(executionmonths==0) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(xmlLogMsg.get("SCREEN.MSG.00037"));
			}
			return true;
		}
		if(yusinmonths==0) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(xmlLogMsg.get("SCREEN.MSG.00038"));
			}
			return true;
		} else if(yusinmonths>0 && yusinmonths>executionmonths) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00039"),executionmonths, yusinmonths));
			}
			return true;
		} else {
			excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00040"),executionmonths, yusinmonths));
		}
		return false;
	}
	/**
	 * 
	     * 此方法描述的是：判断狱内加刑
		 * @param   name  
		 * @param  @return 
		 * @Exception 异常对象
	     * @author: 栾学峰 
	     * @version: 2015-1-4 下午03:15:37
	 */
	private boolean judgeYuneiExecution(TbxfSentenceAlteration sentenceAlteration, HashMap<String, Object> map) {
		int inprisonmonths = 0;
		int executionmonths = this.getWtMap(sentenceAlteration, map, "INPRISONEXECUTIONMONTHS");
		if(sentenceAlteration.getInprisonmonths()!=null) {//狱内加刑
			inprisonmonths = sentenceAlteration.getInprisonmonths();
		}
		if(executionmonths==0) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(xmlLogMsg.get("SCREEN.MSG.00037"));
			}
			return true;
		}
		if(inprisonmonths==0) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(xmlLogMsg.get("SCREEN.MSG.00038"));
			}
			return true;
		} else if(inprisonmonths>0 && inprisonmonths>executionmonths) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00039"),executionmonths, inprisonmonths));
			}
			return true;
		} else {
			excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00040"),executionmonths, inprisonmonths));
		}
		return false;
	}
	/**
	 * 
	     * 此方法描述的是：判断是否减过刑
		 * @param   name  
		 * @param  @return 
		 * @Exception 异常对象
	     * @author: 栾学峰 
	     * @version: 2015-1-3 下午08:20:30
	 */
	private boolean judgeAlreadycommutation(TbxfSentenceAlteration sentenceAlteration, HashMap<String, Object> map) {
		int alreadycommutation = ((BigDecimal)map.get("ALREADYCOMMUTATION")).intValue();
		if(sentenceAlteration.getPredate()==null && alreadycommutation==0) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00007"),alreadycommutation,0));
			}
			return true;
		} else if(sentenceAlteration.getPredate()!=null && alreadycommutation==1) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00007"),alreadycommutation,1));
			}
			return true;
		}
		excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00008"),alreadycommutation));
		return false;
	}
	/**
	 * 
	     * 此方法描述的是：判断是否减为有期徒刑后首次减刑
		 * @param   name  
		 * @param  @return 
		 * @Exception 异常对象
	     * @author: 栾学峰 
	     * @version: 2015-1-3 下午08:10:44
	 */
	private boolean judgeFirstcommutation(TbxfSentenceAlteration sentenceAlteration, HashMap<String, Object> map) {
		int isFirstcommutation = sentenceAlteration.getFirstcommutation();
		int firstcommutation = ((BigDecimal)map.get("FIRSTCOMMUTATION")).intValue();
		if(isFirstcommutation==firstcommutation) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00005"),isFirstcommutation));
			}
			return true;
		}
		excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00006"),firstcommutation,isFirstcommutation));
		return false;
	}
	
	
	/**
	 * 判断罪犯犯罪时间
	 * @param sentenceAlteration 刑期变动信息
	 * @param map 减刑幅度参照
	 * @return
	 */
	private boolean judgeSentence(TbxfSentenceAlteration sentenceAlteration, HashMap<String, Object> map, String provinceid){
		String nocheckcrimetime = StringNumberUtil.getJyConfigValue("nocheckcrimetime",GkzxCommon.ZERO);
		//犯罪时间不需要检查时直接设置为1即可
		if (GkzxCommon.ONE.equals(nocheckcrimetime)) {
			return true;
			
		} else {
			int nowpunishmentyear = changeStringToInt(sentenceAlteration.getPunishmentyear());
			if(provinceid!=null && provinceid.equals(GkzxCommon.TIANJIN_PROVINCE) && nowpunishmentyear<9995) {
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00001"), StringNumberUtil.isNullOrEmpty(sentenceAlteration.getCrimetime())?"":CalendarUtil.convertDateToString(sentenceAlteration.getCrimetime())));
				}
				return true;
			}
			if(sentenceAlteration.getCrimetime()==null) {
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00002"), "空"));
				return false;
			} else if(sentenceAlteration.getCrimetime().after((Date) map.get("STIME"))&&sentenceAlteration.getCrimetime().before((Date) map.get("ETIME"))) {
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00001"), CalendarUtil.convertDateToString(sentenceAlteration.getCrimetime())));
				}
				return true;
			}
			excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00002"), StringNumberUtil.isNullOrEmpty(sentenceAlteration.getCrimetime())?"":CalendarUtil.convertDateToString(sentenceAlteration.getCrimetime())));
			return false;
		}
	}

	/**
	 * 
	     * 此方法描述的是：原判刑期的判断
		 * @param   name  
		 * @param  @return 
		 * @Exception 异常对象
	     * @author: 栾学峰 
	     * @version: 2015-1-3 下午10:47:02
	 */
	private boolean judgeOriginalType(TbxfSentenceAlteration sentenceAlteration, HashMap<String, Object> map){
		if(StringNumberUtil.isEmpty(sentenceAlteration.getOriginalyear())){
			return false;
		}
		int originalyear = changeStringToInt(sentenceAlteration.getOriginalyear());
		int sentenceType = ((BigDecimal)map.get("SENTENCETYPE")).intValue();
		if(sentenceType==1) {
			if(originalyear<9995) {
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00021"), GkzxCommon.XINGQI_YOUQI_ZH));
				}
				return true;
			}
			
		} else if(sentenceType==2) {
			System.out.println(sentenceAlteration.getCrimid());
			if(sentenceAlteration.getOriginalyear().equals(GkzxCommon.XINGQI_WUQI)) {
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00021"), GkzxCommon.XINGQI_WUQI_ZH));
				}
				return true;
			}
		} else {
			if(sentenceAlteration.getOriginalyear().equals(GkzxCommon.XINGQI_SIHUAN)) {
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00021"), GkzxCommon.XINGQI_SIHUAN_ZH));
				}
				return true;
			}
		}
		excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00022"), (originalyear==9996)?GkzxCommon.XINGQI_SIHUAN_ZH:((originalyear==9995)?GkzxCommon.XINGQI_WUQI_ZH:GkzxCommon.XINGQI_YOUQI_ZH)));
		return false;
	}
	/**
	 * 判断原判刑期
	 * @param sentenceAlteration 刑期变动信息
	 * @param map 减刑幅度参照
	 * @return
	 */
	private boolean judgeOriginal(TbxfSentenceAlteration sentenceAlteration, HashMap<String, Object> map){
		int originalyear = changeStringToInt(sentenceAlteration.getOriginalyear());
		int originalmonth = changeStringToInt(sentenceAlteration.getOriginalmonth());
		int originalMonths = originalyear*12 + originalmonth;
		int originalstart = ((BigDecimal)map.get("ORIGINALSTART")).intValue();
		int originalend = ((BigDecimal)map.get("ORIGINALEND")).intValue();

		if(originalstart<0 && originalend<0){
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00023"), originalstart,originalend,originalMonths));
			}
			return true;
		}else if(originalstart==-1 && originalend!=-1){
			if(originalMonths < originalend){
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00023"), originalstart,originalend,originalMonths));
				}
				return true;
			}
		}else if(originalstart!=-1 && originalend==-1){
			if(originalMonths > originalstart){
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00023"), originalstart,originalend,originalMonths));
				}
				return true;
			}
		}else{
			if(originalMonths>originalstart && originalMonths<originalend){
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00023"), originalstart,originalend,originalMonths));
				}
				return true;
			}
		}
		excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00024"), originalstart,originalend,originalMonths));
		return false;
	}

	/**
	 * 判断现刑期种类
	 * @param sentenceAlteration 刑期变动信息
	 * @param map 减刑幅度参照
	 * @return
	 */
	private boolean judgePunishmentType(TbxfSentenceAlteration sentenceAlteration, HashMap<String, Object> map){
		int nowSentencetype = ((BigDecimal)map.get("NOWSENTENCETYPE")).intValue();
		int nowpunishmentyear = changeStringToInt(sentenceAlteration.getPunishmentyear());
		if(nowSentencetype==-1) {
			//方案没有配置
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00009"), nowpunishmentyear));
			}
			return true;
		} else if(nowSentencetype==1) {
			if(nowpunishmentyear<9995) {
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00009"), GkzxCommon.XINGQI_YOUQI_ZH));
				}
				return true;
			}
		} else if(nowSentencetype==2) {
			if(sentenceAlteration.getPunishmentyear()!=null && sentenceAlteration.getPunishmentyear().equals(GkzxCommon.XINGQI_WUQI)) {
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00009"), GkzxCommon.XINGQI_WUQI_ZH));
				}
				return true;
			}
		} else {
			if(sentenceAlteration.getPunishmentyear()!=null && sentenceAlteration.getPunishmentyear().equals(GkzxCommon.XINGQI_SIHUAN)) {
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00009"), GkzxCommon.XINGQI_SIHUAN_ZH));
				}
				return true;
			}
		}
		excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00010"), (nowpunishmentyear==9996)?GkzxCommon.XINGQI_SIHUAN_ZH:((nowpunishmentyear==9995)?GkzxCommon.XINGQI_WUQI_ZH:GkzxCommon.XINGQI_YOUQI_ZH)));
		return false;
	}
	
	/**
	 * 判断现刑期
	 * @param sentenceAlteration 刑期变动信息
	 * @param map 减刑幅度参照
	 * @return
	 */
	private boolean judgePunishment(TbxfSentenceAlteration sentenceAlteration, HashMap<String, Object> map){
//		int nowpunishmentyear = changeStringToInt(sentenceAlteration.getPunishmentyear());//判裁余刑年
//		int nowpunishmentmonth = changeStringToInt(sentenceAlteration.getPunishmentmonth());//判裁余刑月
		//此处用判裁余刑有错，判裁余刑与现刑期有时会相差十几年。edit by YangZR   2015-05-25
		int nowpunishmentyear = changeStringToInt(sentenceAlteration.getNowpunishmentyear());//现刑期年
		int nowpunishmentmonth = changeStringToInt(sentenceAlteration.getNowpunishmentmonth());//现刑期月
		
		int nowpunishmentMonths = nowpunishmentyear*12 + nowpunishmentmonth;
		int sentencestart = ((BigDecimal)map.get("SENTENCESTART")).intValue();
		int sentenceend = ((BigDecimal)map.get("SENTENCEEND")).intValue();

		if(sentencestart<0 && sentenceend<0){
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00011"), sentencestart,sentenceend,nowpunishmentMonths));
			}
			return true;
		}else if(sentencestart!=-1 && sentenceend==-1){
			if(nowpunishmentMonths>sentencestart){
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00011"), sentencestart,sentenceend,nowpunishmentMonths));
				}
				return true;
			}
		}else if(sentencestart==-1 && sentenceend!=-1){
			if(nowpunishmentMonths<sentenceend){
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00011"), sentencestart,sentenceend,nowpunishmentMonths));
				}
				return true;
			}
		}else{
			if(nowpunishmentMonths>sentencestart && nowpunishmentMonths<sentenceend){
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00011"), sentencestart,sentenceend,nowpunishmentMonths));
				}
				return true;
			}
		}
		excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00012"), sentencestart,sentenceend,nowpunishmentMonths));
		return false;
	}
	
	
	/**
	 * 判断起始时间
	 * @param sentenceAlteration 刑期变动信息
	 * @param map 减刑幅度参照
	 * @return
	 */
	private boolean judgeStartperiod(TbxfSentenceAlteration sentenceAlteration, HashMap<String, Object> map){
		/*
		 * 起始时间：无期死缓按原判判裁日期，有期按执行日期
		 * 
		 */
		Date commutationdate = sentenceAlteration.getCommutationdate();//减刑起始时间的结束日期由该批次的考核止日转义来的
		Date execdate = sentenceAlteration.getExecdate(); //有期执行日期
		Date sentencestart = sentenceAlteration.getSentencestart(); //由原判的判裁日期转义来的
		int wtval = this.getWtMap(sentenceAlteration, map, "LSSSTARTPERIOD");
		int startperiod = ((BigDecimal)map.get("STARTPERIOD")).intValue();//起始时间起
		int endperiod = ((BigDecimal)map.get("ENDPERIOD")).intValue();//起始时间止
		if(startperiod!=-1) {
			startperiod = startperiod + wtval;
		}
		if(endperiod!=-1) {
			endperiod = endperiod + wtval;
		}
		int period = 0;
		if(sentenceAlteration.getPunishmentyear()!=null && (sentenceAlteration.getPunishmentyear().equals(GkzxCommon.XINGQI_WUQI) || sentenceAlteration.getPunishmentyear().equals(GkzxCommon.XINGQI_SIHUAN))){
			//判裁日期到考核止日的月数
			period = getMonthBetweenDate(sentencestart, commutationdate);
		}else{
			//有期执行日期到考核止日的月数
			period = getMonthBetweenDate(execdate, commutationdate);
		}
		if(startperiod==-1 && endperiod==-1){
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00013"), startperiod,endperiod,period));
			}
			return true;
		}else if(startperiod!=-1 && endperiod==-1){
			if(period > startperiod){
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00013"), startperiod,endperiod,period));
				}
				return true;
			}
		}else if(startperiod==-1 && endperiod!=-1){
			if(period < endperiod){
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00013"), startperiod,endperiod,period));
				}
				return true;
			}
		}else{
			if(period>startperiod && period<endperiod){
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00013"), startperiod,endperiod,period));
				}
				return true;
			}
		}
		excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00014"), startperiod,endperiod,period));
		return false;
	}
	
	
	/**
	 * 判断间隔时间
	 * @param sentenceAlteration 刑期变动信息
	 * @param map 减刑幅度参照
	 * @return
	 */
	private boolean judgeIntervalperiod(TbxfSentenceAlteration sentenceAlteration, HashMap<String, Object> map){
		int wtval = this.getWtMap(sentenceAlteration, map, "LSSINTERVALPERIOD");
		int originalyear = changeStringToInt(sentenceAlteration.getOriginalyear());
		int originalmonth = changeStringToInt(sentenceAlteration.getOriginalmonth());
		int originalMonths = 0;
		if(originalyear!=-1) {
			originalMonths = originalyear*12 + originalmonth;
		} else {
			originalMonths = originalmonth;
		}
		int intervalyear = changeStringToInt(sentenceAlteration.getIntervalyear());
		int intervalmonth = changeStringToInt(sentenceAlteration.getIntervalmonth());
		int intervalday = changeStringToInt(sentenceAlteration.getIntervalday());
		int intervalMonths = 0;
		if(intervalyear!=-1) {
			intervalMonths = intervalyear*12 + intervalmonth;
		} else {
			intervalMonths = intervalmonth;
		}
		int intervalperiod = ((BigDecimal)map.get("INTERVALPERIOD")).intValue();
		int endintervalperiod = ((BigDecimal)map.get("ENDINTERVALPERIOD")).intValue();
		if(intervalperiod!=-1) {
			intervalperiod = intervalperiod + wtval;
		}
		if(endintervalperiod!=-1) {
			endintervalperiod = endintervalperiod + wtval;
		}
		double intervalperiodscale = ((BigDecimal)map.get("INTERVALPERIODSCALE")).doubleValue();
		if(sentenceAlteration.getPredate()==null){ //没有减刑过的罪犯不受间隔期限制.
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(xmlLogMsg.get("SCREEN.MSG.00017"));
			}
			return true;
		}
		if(intervalperiodscale!=0.0) {
			if(intervalday>25) {
				intervalMonths = intervalMonths + 1;
			}
			if(intervalMonths>=(originalMonths*intervalperiodscale + wtval)){
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00019"), intervalperiodscale,originalMonths*intervalperiodscale + wtval,intervalMonths));
				}
				return true;
			} else {
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00020"), intervalperiodscale,originalMonths*intervalperiodscale + wtval,intervalMonths));
				return false;
			}
		} else {
			if(intervalday>0) {
				intervalMonths = intervalMonths + 1;
			}
		}
		
		if(intervalperiod==-1 && endintervalperiod==-1){
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00015"), intervalperiod,endintervalperiod,intervalMonths));
			}
			return true;
		}else if(intervalperiod!=-1 && endintervalperiod==-1){
			if(intervalMonths >= intervalperiod){
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00015"), intervalperiod,endintervalperiod,intervalMonths));
				}
				return true;
			}
		}else if(intervalperiod==-1 && endintervalperiod!=-1){
			if(intervalMonths <= endintervalperiod){
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00015"), intervalperiod,endintervalperiod,intervalMonths));
				}
				return true;
			}
		}else{
			if(intervalMonths>=intervalperiod && intervalMonths<=endintervalperiod){
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00015"), intervalperiod,endintervalperiod,intervalMonths));
				}
				return true;
			}
		}
		excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00016"), intervalperiod,endintervalperiod,intervalMonths));
		return false;
	}
	/**
	 * 
	     * 此方法描述的是：执行刑期
		 * @param   name  
		 * @param  @return 
		 * @Exception 异常对象
	     * @author: 栾学峰 
	     * @version: 2015-1-3 下午10:54:52
	 */
	private boolean judgeExecutesentencemonths(TbxfSentenceAlteration sentenceAlteration, HashMap<String, Object> map,String provinceid) {
		int wtval = this.getWtMap(sentenceAlteration, map, "LSSEXECUTIONSENTENCES");
		float executesentencemonths = sentenceAlteration.getExecutesentencemonths()==null?-1:Float.valueOf(sentenceAlteration.getExecutesentencemonths());
		int executionsentencesstart = ((BigDecimal)map.get("EXECUTIONSENTENCESSTART")).intValue();
		int executionsentencesend = ((BigDecimal)map.get("EXECUTIONSENTENCESEND")).intValue();
		if(executionsentencesstart !=-1) {
			executionsentencesstart = executionsentencesstart + wtval;
		}
		if(executionsentencesend !=-1) {
			executionsentencesend = executionsentencesend + wtval;
		}
		int originalyear = changeStringToInt(sentenceAlteration.getOriginalyear());
		int originalmonth = changeStringToInt(sentenceAlteration.getOriginalmonth());
		int originalMonths = originalyear*12 + originalmonth;
		boolean executesentencescale = false;
		boolean executesentence = false;
		double executionsentencescale = ((BigDecimal)map.get("EXECUTIONSENTENCESCALE")).doubleValue();
		if(executionsentencescale==0.0) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00027"), executionsentencescale,originalMonths * executionsentencescale + wtval,executesentencemonths));
			}
			executesentencescale = true;
		} else if(provinceid!=null && provinceid.equals(GkzxCommon.TIANJIN_PROVINCE)&&(sentenceAlteration.getCourtchangeto()!=null)&&(sentenceAlteration.getCourtchangefrom()!=null)) {
			long day = (sentenceAlteration.getCourtchangeto().getTime() - sentenceAlteration.getCourtchangefrom().getTime()) / (24 * 60 * 60 * 1000); 
			if (day > (originalMonths * executionsentencescale + wtval) * 30) {
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00027"), executionsentencescale,(originalMonths * executionsentencescale + wtval)*30,day));
				}
				executesentencescale = true;
			}			
		} else if(executesentencemonths>=originalMonths * executionsentencescale + wtval) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00027"), executionsentencescale,originalMonths * executionsentencescale + wtval,executesentencemonths));
			}
			executesentencescale = true;
		}
		
		if(executionsentencesstart==-1 && executionsentencesend==-1) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00025"), executionsentencesstart,executionsentencesend,executesentencemonths));
			}
			executesentence = true;
		} else if(executionsentencesstart==-1 && executionsentencesend!=-1) {
			if(executionsentencesend>=executesentencemonths) {
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00025"), executionsentencesstart,executionsentencesend,executesentencemonths));
				}
				executesentence = true;
			}
		} else if(executionsentencesstart!=-1 && executionsentencesend==-1) {
			if(executionsentencesstart<=executesentencemonths) {
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00025"), executionsentencesstart,executionsentencesend,executesentencemonths));
				}
				executesentence = true;
			}
		} else if(executionsentencesstart!=-1 && executionsentencesend!=-1) {
			if(executionsentencesstart<=executesentencemonths && executionsentencesend>=executesentencemonths) {
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00025"), executionsentencesstart,executionsentencesend,executesentencemonths));
				}
				executesentence = true;
			}
		}
		if(!executesentencescale) {
			excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00028"), executionsentencescale,originalMonths * executionsentencescale + wtval,executesentencemonths));
		}
		if(!executesentence){
			excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00026"), executionsentencesstart,executionsentencesend,executesentencemonths));
		}
		return executesentence && executesentencescale;
	}
	/**
	 * 
	     * 此方法描述的是：执行刑期的限制
		 * @param   name  
		 * @param  @return 
		 * @Exception 异常对象
	     * @author: 栾学峰 
	     * @version: 2015-1-3 下午11:05:30
	 */
	private boolean judgeExecutesentenceLimit(TbxfSentenceAlteration sentenceAlteration, HashMap<String, Object> map,String provinceid) {
		int executesentencelimit = ((BigDecimal)map.get("EXECUTESENTENCELIMIT")).intValue();
		double executesentencescalelimit = ((BigDecimal)map.get("EXECUTESENTENCESCALELIMIT")).doubleValue();
		boolean executesentencescale = false;
		boolean executesentence = false;
//		int punishmentyear = changeStringToInt(sentenceAlteration.getPunishmentyear());
//		int punishmentmonth = changeStringToInt(sentenceAlteration.getPunishmentmonth());
//		int punishmentmonths = punishmentyear*12 + punishmentmonth;
		int nowpunishmentyear = changeStringToInt(sentenceAlteration.getNowpunishmentyear());
		int nowpunishmentmonth = changeStringToInt(sentenceAlteration.getNowpunishmentmonth());
		//执行刑期加上现余刑跟执行刑期限制比较
		double punishmentmonths = changeStringToFloat(sentenceAlteration.getExecutesentencemonths()) + nowpunishmentyear*12 + nowpunishmentmonth;
		int originalyear = changeStringToInt(sentenceAlteration.getOriginalyear());
		int originalmonth = changeStringToInt(sentenceAlteration.getOriginalmonth());
		int originalMonths = originalyear*12 + originalmonth;
		if(sentenceAlteration.getPredate()==null) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(xmlLogMsg.get("SCREEN.MSG.00029"));
			}
			return true;
		}
		if(originalyear==9995 || originalyear==9996) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(xmlLogMsg.get("SCREEN.MSG.00030"));
			}
			executesentencescale = true;
		} else if(provinceid!=null && provinceid.equals(GkzxCommon.TIANJIN_PROVINCE)&&executesentencescalelimit>0&&(sentenceAlteration.getCourtchangeto()!=null)&&(sentenceAlteration.getCourtchangefrom()!=null)) {
			long day = (sentenceAlteration.getCourtchangeto().getTime() - sentenceAlteration.getCourtchangefrom().getTime()) / (24 * 60 * 60 * 1000); 
			if (day > originalMonths * executesentencescalelimit * 30) {
				if (screeningdebug.equals(GkzxCommon.ONE)){
					excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00031"), executesentencescalelimit,originalMonths * executesentencescalelimit,punishmentmonths));
				}
				executesentencescale = true;
			}			
		} else if(punishmentmonths > originalMonths * executesentencescalelimit) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00031"), executesentencescalelimit,originalMonths * executesentencescalelimit,punishmentmonths));
			}
			executesentencescale = true;
		}
		
		if(executesentencelimit <=0) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(xmlLogMsg.get("SCREEN.MSG.00033"));
			}
			executesentence = true;
		} else if(punishmentmonths > executesentencelimit) {
			if (screeningdebug.equals(GkzxCommon.ONE)){
				excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00035"), executesentencelimit,punishmentmonths));
			}
			executesentence = true;
		}
		if (executesentence == false ||executesentencescale == false) {
			excuseBuffer.append(MessageFormat.format(xmlLogMsg.get("SCREEN.MSG.00036"), executesentencescalelimit,executesentencelimit,punishmentmonths));
		}
		return executesentence && executesentencescale;
	}
	
	/**
	 * 判断两个时间之间的月份差
	 * @param date
	 * @param commutationdate
	 * @return
	 */
	private int getMonthBetweenDate(Date date, Date commutationdate){
		int count = 0;
		if(date!=null && commutationdate!=null){
			if(commutationdate.getTime() >= date.getTime()){
				String[] dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date).split("-");
				String[] commutationdateStr = new SimpleDateFormat("yyyy-MM-dd").format(commutationdate).split("-");
				int dateYear = changeStringToInt(dateStr[0]);
				int dateMonth = changeStringToInt(dateStr[1]);
				int dateDay = changeStringToInt(dateStr[2]);
				int commutationdateYear = changeStringToInt(commutationdateStr[0]);
				int commutationdateMonth = changeStringToInt(commutationdateStr[1]);
				int commutationdateDay = changeStringToInt(commutationdateStr[2]);
				if(commutationdateDay < dateDay){
					commutationdateMonth -= 1;
				}
				if(commutationdateDay > dateDay){
					commutationdateMonth += 1;
				}				
				if(commutationdateMonth < dateMonth){
					commutationdateMonth += 12;
					commutationdateYear -= 1;
				}
				count = (commutationdateYear-dateYear)*12 + (commutationdateMonth - dateMonth);
			}	
		}
		return count;
	}
	
	
	/**
	 * 将String转化为int，如果为null或者"",则返回0
	 * @param str
	 * @return
	 */
	private int changeStringToInt(String str) {
		int returnValue = 0;
		if(null==str || "".equals(str)){
			returnValue = 0;
		}else{
			try {
				returnValue = Integer.valueOf(str);
			} catch (NumberFormatException e) {
				System.out.println("不能将\""+str+"\"转化为int类型！");
			}
		}
		return returnValue;
	}
	
	/**
	 * 将String转化为int，如果为null或者"",则返回-1
	 * @param str
	 * @return
	 */
	private double changeStringToFloat(String str) {
		double returnValue = 0;
		if(null==str || "".equals(str)){
			returnValue = -1;
		}else{
			try {
				returnValue = new BigDecimal(str).floatValue();
			} catch (NumberFormatException e) {
				System.out.println("不能将\""+str+"\"转化为float类型！");
			}
		}
		return returnValue;
	}
	
	private int getWtMap(TbxfSentenceAlteration sentenceAlteration, HashMap<String, Object> map, String returnType) {
		List<Map<String,Object>> wtList = sentenceAlteration.getWtList();
		Map<String,Object> wtMap = new HashMap<String,Object>();
		for(int i=0; i<wtList.size(); i++) {
			if(wtList.get(i).get("PUNID").equals(map.get("PUNID"))) {
				wtMap = wtList.get(i);
				break;
			}
		}
		int wtval = 0;
		if(wtMap.get(returnType)!=null) {
			wtval = ((BigDecimal)wtMap.get(returnType)).intValue();
		}
		return wtval;
	}
	
	private List<HashMap<String,Object>> getCrimRefAndMergeMap(String id,List<HashMap<String,Object>> list,String type) {
		List<HashMap<String,Object>> crimRefAndMergeList = new ArrayList<HashMap<String,Object>>();
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).get(type).toString().equals(id)) {
				crimRefAndMergeList.add(list.get(i));
			}
		}
		return crimRefAndMergeList;
	}

	/**
	 * 获取建议幅度
	 * @param mergeList 该罪犯的奖励信息；
	 * @param map 		该罪犯的奖励信息（从宽从严含、幅度限制含）；
	 * @param refmap 	该罪犯的减刑方案详细信息
	 * @return
	 */
	private int getSuggest(List<HashMap<String,Object>> mergeList, HashMap<String,Object> map, HashMap<String,Object> refmap) {
		int suggest = 0;
		boolean flag = true;
		//计算方式（根据类型计算：1；根据方案计算：0）
		int type = Integer.valueOf(refmap.get("REMARK").toString());
		//幅度限制
		int rangelimit = ((BigDecimal)map.get("RANGELIMIT")).intValue();
		//裁判余刑年
		int punishmentyear = 0;
		if(map.get("PUNISHMENTYEAR")!=null) {
			punishmentyear = Integer.valueOf(map.get("PUNISHMENTYEAR").toString());
		}
		int range = 0;
		for(int i=0; i<mergeList.size(); i++) {
			//奖励开始数(根据类型计算时：奖励对应减刑幅度；根据方案计算时：奖励开始数)
			int sno = ((BigDecimal)mergeList.get(i).get("SNO")).intValue();
			//奖励结束数(根据类型计算时：-1；根据方案计算时：奖励结束数)
			int eno = ((BigDecimal)mergeList.get(i).get("ENO")).intValue();
			//TBXF_PRISONERPERFORMANCEMERGE表的字段对应的数值
			String actioncode = StringNumberUtil.returnString2(map.get(mergeList.get(i).get("ACTIONCODE")));
			int mergenum = 0;
//			mergenum = ((BigDecimal)map.get(mergeList.get(i).get("ACTIONCODE").toString())).intValue();
			if(StringNumberUtil.notEmpty(actioncode)) mergenum = Integer.parseInt(actioncode);
			if(type==0) {
				//根据方案计算
				if(sno==-1 && eno==-1) {
					continue;
				} else if(sno==-1 && eno!=-1) {
					//奖励数大于等于结束数返回false
					if(mergenum >= eno||mergenum==0) {
						flag = false;
						break;
					}
				} else if(sno!=-1 && eno==-1) {
					//奖励数小于等于开始数返回false
					if(mergenum <=sno || mergenum==0) {
						flag = false;
						break;
					}
				} else if(sno!=-1 && eno!=-1) {
					//奖励数大于等于结束数返回false或者奖励数小于等于开始数返回false
					if(mergenum <=sno || mergenum >=eno) {
						flag = false;
						break;
					}
				}
			} else {
				//根据类型计算
				//有奖励时，记录该奖励的减刑幅度
				if(sno>range && mergenum>0) {
					range = sno;
				}
				//判处余刑是无期死缓的时候，减刑幅度直接赋给建议幅度
				if(punishmentyear>9994) {
					suggest = range;
				} else {
					//不是无期死缓的时候，建议幅度 + 奖励数 * 该奖励的减刑幅度
					suggest = suggest + mergenum * sno;
				}
			}
		}
		//根据方案计算时，奖励数在方案范围内时，取建议幅度
		if(type==0 && flag) {
			suggest = suggest + ((BigDecimal)refmap.get("SUGGESTNUM")).intValue();
		}
		//减刑幅度限制不为空，建议幅度要小于减刑幅度限制
		if(rangelimit>0) {
			if(suggest>rangelimit && suggest<60) {
				suggest = rangelimit;
			}
		}
		//从宽从严本身就是带符号的，从宽从严是被加到建议幅度的。
		int wtsuggest = 0;
		if(map.get("RANGESTART")!=null) {
			wtsuggest = ((BigDecimal)map.get("RANGESTART")).intValue();
		}
		if(suggest<60) {
			suggest = suggest + wtsuggest;
		} else if(suggest<9995) {
			suggest = suggest - wtsuggest;
		}
		return suggest;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 简化版 筛查数据
	 * @author YangZR	2016-08-21
	 */
	@Transactional
	public void qualificateCommuteData(String departid, String orgid, HttpServletRequest request){
		
		//重置TBXF_SCREENING表的数据，正在办理中的数据不做任何改动
		tbxfScreeningMapper.deleteWastedata(departid);//已申报未立案、在押犯、正在办理减刑的不删除
		tbxfScreeningMapper.insertNewdata(departid);//补全数据，只补在押犯的数据
		tbxfScreeningMapper.updateStatus4Qualificate(departid);//将状态置为不符合减刑条件、未申报，   不处理已申报未立案、正在办理减刑的数据 
		tbxfScreeningMapper.callPSENTNCECHANG(departid);

		
		//获取筛查条件：一级条件：List<Map<String,Object>> level1List，    二级条件：Map<String,List<Map<String,Object>>> conditionMap，   
		//		          特殊条件：List<Map<String,Object>> level3List
		Map<String,Object> paramap = new HashMap<String,Object>();
		paramap.put("departid", departid);
		int count = tbxfScreeningMapper.countDepartQualifierTemp(paramap);
		
		paramap.put("setlevel", 1);//一级条件
		paramap.put("qtype", 0);//减刑
		List<Map<String,Object>> level1List_jx = MapUtil.convertKeyList2LowerCase(tbxfPunishmentRangMapper.getQualifierSet(paramap));
		paramap.put("qtype", 1);//假释
		List<Map<String,Object>> level1List_js = MapUtil.convertKeyList2LowerCase(tbxfPunishmentRangMapper.getQualifierSet(paramap));
		
		paramap.put("setlevel", 3);//三级条件
		paramap.put("qtype", 0);
		List<Map<String,Object>> level3List_jx = MapUtil.convertKeyList2LowerCase(tbxfPunishmentRangMapper.getQualifierSet(paramap));
		paramap.put("qtype", 1);
		List<Map<String,Object>> level3List_js = MapUtil.convertKeyList2LowerCase(tbxfPunishmentRangMapper.getQualifierSet(paramap));
		
		paramap.remove("qtype");//删除qtype，防止影响二级条件的查询
		paramap.put("setlevel", 2);//查询二级条件
		
		Map<String,List<Map<String,Object>>> conditionMap_jx = new HashMap<String,List<Map<String,Object>>>();
		for(Map<String,Object> level1Map : level1List_jx){
			String qid = StringNumberUtil.getStrFromMap("qid", level1Map);
			paramap.put("fid", qid);//qid作为二级条件的fid
			List<Map<String,Object>> level2List = MapUtil.convertKeyList2LowerCase(tbxfPunishmentRangMapper.getQualifierSet(paramap));
			conditionMap_jx.put(qid, level2List);
		}
		
		Map<String,List<Map<String,Object>>> conditionMap_js = new HashMap<String,List<Map<String,Object>>>();
		for(Map<String,Object> level1Map : level1List_js){
			String qid = StringNumberUtil.getStrFromMap("qid", level1Map);
			paramap.put("fid", qid);//qid作为二级条件的fid
			List<Map<String,Object>> level2List = MapUtil.convertKeyList2LowerCase(tbxfPunishmentRangMapper.getQualifierSet(paramap));
			conditionMap_js.put(qid, level2List);
		}
		
		
		//筛查元素Map集合 ，以col_name作为key，以该条记录Map作为value，col_name要去重
		Map<String,Map<String,Object>> allQualifierItem = new HashMap<String,Map<String,Object>>();
		List<Map<String,Object>> allQualifierItemList = MapUtil.convertKeyList2LowerCase(tbxfPunishmentRangMapper.getAllQualifierItem(paramap));
		for(Map<String,Object> item : allQualifierItemList){
			String key = StringNumberUtil.getStrFromMap("col_name", item);
			allQualifierItem.put(key, item);
		}
		
		//
		//分批处理，每批300条罪犯数据
		int start = 1;
		int end = 300;
		while(start <= count){
			int percent = (start-1)*100/count;
			percent = percent > 95 ? 95 : percent;
			request.getSession().setAttribute("percent", percent);
			
			end = end > count ? count : end;
			paramap.put("start", start);
			paramap.put("end", end);
			qualificateCriminal(level1List_jx, level1List_js, conditionMap_jx, conditionMap_js, level3List_jx, level3List_js, allQualifierItem, paramap);
			start += 300;
			end += 300;
		}
	}
	
	
	
	
	/**
	 * 描述：通过一级条件、二级条件、三级条件筛查出罪犯的数据，并更新到表TBXF_SCREENING中去
	 * @author YangZR	2016-08-29
	 * @param level1List : 一级筛查条件
	 * @param condiMap  ：    二级筛查条件
	 * @param level3List ： 三级筛查条件
	 * @param allQualifierItem ： 筛查元素
	 * @param paramap
	 */
	private void qualificateCriminal(List<Map<String,Object>> level1List_jx, List<Map<String,Object>> level1List_js,
									 Map<String,List<Map<String,Object>>> condiMap_jx, Map<String,List<Map<String,Object>>> condiMap_js,
									 List<Map<String,Object>> level3List_jx, List<Map<String,Object>> level3List_js, 
									 Map<String,Map<String,Object>> allQualifierItem, Map<String,Object> paramap){
		
		//查询表TBXF_QUALIFIER_TEMP中要筛查的罪犯初始化数据(分批查询，从start --> end的数据)，将map中的key大写转小写，将value为数字类型字符串的转为数字
		List<Map<String,Object>> crimInitList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> crimInitListTemp = tbxfScreeningMapper.getQualifierCriminal(paramap);
		for(int i=0; i<crimInitListTemp.size(); i++){
			Map<String,Object> crimInitMap = crimInitListTemp.get(i);
			crimInitMap = MapUtil.convertKey2LowerCase(crimInitMap);
			crimInitMap = MapUtil.valueStr2Num(crimInitMap);
			crimInitList.add(crimInitMap);
		}
		
		int qtype = 0;//qtype 0:减刑，1：假释
		//减刑_第一、二级罪犯筛查结果
		List<Map<String,Object>> crimQualifierFirst_jx = qualifierLevelOne(qtype, level1List_jx, condiMap_jx, allQualifierItem, crimInitList);
		//减刑_三级条件的罪犯筛查结果
		List<Map<String,Object>> crimQualifierThree_jx = qualifierLevelThree(qtype, level3List_jx, allQualifierItem, crimQualifierFirst_jx);
		//综合减刑结果
		List<Map<String,Object>> crimQualifierThree_jx_1 = summarizeCommuteResult(qtype, crimQualifierThree_jx);
		
		qtype = 1;//qtype 0:减刑，1：假释
		//假释_第一、二级罪犯筛查结果
		List<Map<String,Object>> crimQualifierFirst_js = qualifierLevelOne(qtype, level1List_js, condiMap_js, allQualifierItem, crimQualifierThree_jx_1);
		//假释_三级条件的罪犯筛查结果
		List<Map<String,Object>> crimQualifierThree_js = qualifierLevelThree(qtype, level3List_js, allQualifierItem, crimQualifierFirst_js);
		//综合假释结果
		List<Map<String,Object>> crimQualifierThree_js_1 = summarizeCommuteResult(qtype, crimQualifierThree_js);
		
		//将crimInitList中的数据更新到TBXF_SCREENING表中去。(包括批次，PUNID)
		tbxfCommutationReferenceMapper.qualifierUpdateScreening(crimQualifierThree_js_1);
	}
	
	
	/**
	 * 描述：第一、二级罪犯筛查结果，每个罪犯最多只被一条一级条件选中，被选中后此罪犯必须满足一级条件对应的二级条件的所有情况
	 *     未被一级条件选中的罪犯默认筛查通过
	 * @author YangZR 2016-08-29
	 * @param qtype 0:减刑，1：假释
	 * @param level1List：一级筛查条件
	 * @param condiMap：二级筛查条件
	 * @param allQualifierItem ： 筛查元素
	 * @param crimInitList：罪犯的初始化数据
	 * @return
	 */
	private List<Map<String,Object>> qualifierLevelOne(int qtype, List<Map<String,Object>> level1List, Map<String,List<Map<String,Object>>> condiMap,
			Map<String,Map<String,Object>> allQualifierItem, List<Map<String,Object>> crimInitList){
		
		String status = getStatusWithQtype(qtype);//做为map的key，存放筛查结果
		String process = getProcessWithQtype(qtype);//做为map的key，存放筛查过程
		
		List<Map<String,Object>> crimQualifierFirst = new ArrayList<Map<String,Object>>();//第一、二级罪犯筛查结果
		
		for(Map<String,Object> crimMap : crimInitList){
			
			StringBuilder sb = new StringBuilder();//记录罪犯的一级、二级条件的筛查过程
			
			List<Variable> variables = QualifierUtil.assembleVariable(crimMap);
			//
			Boolean level1Status = null;
			Map<String,Object> level1Map = null;
			for(Map<String,Object> level1MapTemp : level1List){
				String init_expression = StringNumberUtil.getStrFromMap("expression", level1MapTemp);
				
				//如果crimMap中的一些key对应的值为空，则将expression中相应的原子表达式设为false
				String expression = new String(init_expression);
				expression = QualifierUtil.dealExpression4NullParam(expression, crimMap);
				
				level1Status = QualifierUtil.parseGrammar(expression,variables);
				if(true == level1Status){
					//一级筛查条件的筛查过程
					String setlevel = "1";
					String level1Process = QualifierUtil.getProcess(init_expression, setlevel, level1MapTemp, crimMap, allQualifierItem);
					sb.append("一级条件约束：");
					sb.append("\n").append("　　");
					sb.append(level1Process);
					sb.append("符合条件;");
					//
					level1Map = level1MapTemp;
					break;
				}
			}
			
			if(null==level1Status || false==level1Status){//说明此罪犯是不受一级筛查条件的约束，默认筛查通过
				crimMap.put(status, 1);
				crimMap.put("qid", qtype);
				
				sb.append("该犯不受一级条件约束，默认通过;");
			}else if(true==level1Status){//被某一级条件选中，进行二级条件的筛查
				sb.append("\n").append("二级条件约束，必须全部满足：");
				String setlevel = "2";
				String qid = StringNumberUtil.getStrFromMap("qid", level1Map);
				
				Boolean level2Status = null;
				List<Map<String,Object>> level2List = condiMap.get(qid);
				for(Map<String,Object> level2Map : level2List){//二级条件只要有一个不满足，则筛查不通过
					String init_expression = StringNumberUtil.getStrFromMap("expression", level2Map);
					
					//如果crimMap中的一些key对应的值为空，则将expression中相应的原子表达式设为false
					String expression = new String(init_expression);
					expression = QualifierUtil.dealExpression4NullParam(expression, crimMap);
					
					level2Status = QualifierUtil.parseGrammar(expression,variables);
					//
					String level2Process = QualifierUtil.getProcess(init_expression, setlevel, level2Map, crimMap, allQualifierItem);
					sb.append("\n").append("　　");
					sb.append(level2Process);
					//
					if(null==level2Status){//当为null时，忽略此筛查条件
						sb.append("忽略此筛查条件;");
					}else if(false==level2Status){
						sb.append("不符合条件;");
						break;
					}else if(true==level2Status){
						sb.append("符合条件;");
					}
				}
				
				if(null==level2Status || true==level2Status){
					crimMap.put(status, 1);
				}else if(false==level2Status){
					crimMap.put(status, 0);
				}
				
				crimMap.put("qid", qid);
			}
			
			crimMap = setQualifierProcess(crimMap, process, sb.toString());
			crimQualifierFirst.add(crimMap);
			
		}
		
		return crimQualifierFirst;
	}
	
	
	/**
	 * 描述：特殊条件的筛查结果，特殊条件按优先级数从小到大依次过滤，后面的结果会覆盖前面的结果。
	 * @author YangZR	2016-08-29
	 * @param qtype 0:减刑，1：假释
	 * @param level3List : 特殊条件
	 * @param allQualifierItem ： 筛查元素
	 * @param crimQualifierFirst ： 由一级、二级筛查后的罪犯数据
	 * @return 返回特殊条件筛查后的罪犯数据
	 */
	private List<Map<String,Object>> qualifierLevelThree(int qtype, List<Map<String,Object>> level3List, Map<String,Map<String,Object>> allQualifierItem, 
			List<Map<String,Object>> crimQualifierFirst){
		
		String status = getStatusWithQtype(qtype);//做为map的key，存放筛查结果
		String process = getProcessWithQtype(qtype);//做为map的key，存放筛查过程
		
		if(null==level3List || level3List.size()==0){//如果没有三级条件直接返回
			return crimQualifierFirst;
		}
		
		List<Map<String,Object>> crimQualifierThree = new ArrayList<Map<String,Object>>();
		
		String setlevel = "3";
		for(Map<String,Object> crimMap : crimQualifierFirst){
			
			StringBuilder sb = new StringBuilder();//记录罪犯的三级条件的筛查过程
			sb.append("\n").append("三级条件约束：");
			
			Boolean level3Status = null;
			List<Variable> variables = QualifierUtil.assembleVariable(crimMap);
			for(Map<String,Object> level3Map : level3List){
				String init_expression = StringNumberUtil.getStrFromMap("expression", level3Map);
				
				//如果crimMap中的一些key对应的值为空，则将expression中相应的原子表达式设为false
				String expression = new String(init_expression);
				expression = QualifierUtil.dealExpression4NullParam(expression, crimMap);
				
				level3Status = QualifierUtil.parseGrammar(expression,variables);
				
				String level3Process = QualifierUtil.getProcess(init_expression, setlevel, level3Map, crimMap, allQualifierItem);
				sb.append("\n").append("　　");
				sb.append(level3Process);
				
				if(null==level3Status){//当为null时，忽略此筛查条件
					sb.append("忽略此筛查条件;");
				}else if(false==level3Status){
					sb.append("不符合条件;");
					crimMap.put(status, 0);
				}else if(true==level3Status){
					sb.append("符合条件;");
					crimMap.put(status, 1);
				}
			}
			
			crimMap = setQualifierProcess(crimMap, process, sb.toString());
			
			crimQualifierThree.add(crimMap);
		}
		
		return crimQualifierThree;
	}
	
	
	private List<Map<String,Object>> summarizeCommuteResult(int qtype, List<Map<String,Object>> crimQualifierThree){
		
		String status = getStatusWithQtype(qtype);//做为map的key，存放筛查结果
		String process = getProcessWithQtype(qtype);//做为map的key，存放筛查过程
		
		List<Map<String,Object>> crimQualifierSummary = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> crimMap : crimQualifierThree){
			int statusNum = Integer.parseInt(StringNumberUtil.getStrFromMap(status, crimMap));
			StringBuilder sb = new StringBuilder();//
			sb.append("\n").append("综合以上条件，得出筛查结果：");
			sb.append("\n").append("　　");
			String summarizy = getCommuteResult(statusNum, qtype);
			sb.append(summarizy);
			crimMap = setQualifierProcess(crimMap, process, sb.toString());
			//
			crimQualifierSummary.add(crimMap);
		}
		return crimQualifierSummary;
	}
	
	private String getStatusWithQtype(int qtype){
		String result = null;
		switch(qtype){
		case 0:
			result = "_status_jx";//减刑状态标志
			break;   
		case 1:
			result = "_status_js";//假释状态标志
			break;
		}
		return result;
	}
	private String getProcessWithQtype(int qtype){
		String result = null;
		switch(qtype){
		case 0:
			result = "_process_jx";//减刑筛查过程
			break;   
		case 1:
			result = "_process_js";//假释筛查过程
			break;
		}
		return result;
	}
	
	
	private Map<String,Object> setQualifierProcess(Map<String,Object> crimMap,String key, String process){
		StringBuilder processSb = new StringBuilder(StringNumberUtil.getStrFromMap(key, crimMap));
		if(StringNumberUtil.notEmpty(process)){
			processSb.append(process);
		}
		crimMap.put(key, processSb.toString());
		return crimMap;
	}
	
	private String getCommuteResult(int status, int qtype){
		String result = "";
		switch(status){
		case 1:
			result = "符合";
			break;   
		case 0:
			result += "不符合";
			break;
		}
		
		switch(qtype){
		case 0:
			result += "减刑";
			break;   
		case 1:
			result += "假释";
			break;
		}
		return result;
	}
	
	
	
}

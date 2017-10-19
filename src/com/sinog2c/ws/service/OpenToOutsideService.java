package com.sinog2c.ws.service;

import java.util.List;
import java.util.Map;

/**
 * webservice的业务接口
 *
 */
public interface OpenToOutsideService {
	/**
	 * 得到案件信息
	 */
	public String getCaseInfo();
	
	/**
	 * 得到报请信息
	 */
	public String getDeclareInfo();
	
	/**	
	 * 得到监狱意见
	 */
	public String getPrisonOpinion();
	
	/**
	 * 得到监狱局意见
	 */
	public String getJabatanPenjaraOpinion();
	
	/**
	 * 得到检察院意见
	 */
	public String getProcuratorateOpinion();
	
	/**
	 * 得到犯罪事实
	 */
	public String getCrimeTruth();
	
	/**
	 * 得到罪犯基本信息
	 */
	public String getCriminalBaseInfo();
	
	/**
	 * 得到罪犯消费情况
	 */
	public String getCriminalConsumption();
	
	/**
	 * 得到老弱病残信息（多条）
	 */
	public String getOldAndSick();
	
	/**
	 * 得到罪犯前科信息（多条）
	 */
	public String getCriminalRecord();
	
	/**
	 * 得到罪犯社会关系信息（多条）
	 */
	public String getCriminalSocialRelations();
	
	/**
	 * 得到罪犯简历信息
	 */
	public String getCriminalResume();
	
	/**
	 * 得到罪犯基本信息
	 */
	public String getCriminalExecutedExpression();
	
	/**
	 * 得到财产性判项履行情况
	 */
	public String getAssetPerformInfo();
	
	/**
	 * 得到奖励情况（多条）
	 */
	public String getAwardInfo();
	
	/**
	 * 得到惩罚情况（多条）
	 */
	public String getPunishmentInfo();
	
	/**
	 * 得到原判信息（多条）
	 */
	public String getSentenceInfo();
	
	/**
	 * 得到历次申请刑罚变更情况（多条）
	 */
	public String getPenaltyChangeInfo();

	/**
	 * 得到批次下拉框信息
	 * @param map
	 * @return
	 */
	public List<Map> getPiciInfo(Map map);

	public int countCourtCaseHandleList(Map<String, Object> map);

	public List<Map<String, Object>> getCourtCaseHandleList(
			Map<String, Object> map);

	public String sendCaseData(Map<Object, Object> map);

	public Map findsome();

	public String getCaseData(String filepath, String fileName, String stage);

	public void sendReceiptToCourt(Map<Object, Object> map);

	public void sendReceiptToCourttest(Map<Object, Object> map);

	public List<Map> getPrisonInfo(Map map);

	public List<Map> getCourt(Map map);

	public List<Map> getCourtP(Map map);
	
	public boolean delAllFile(String path);

	public String deleteData(String picibianhao, String crimid);


}


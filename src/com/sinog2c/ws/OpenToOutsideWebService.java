package com.sinog2c.ws;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

/**
 * 对外开放的数据接口
 */
@WebService
public interface OpenToOutsideWebService {

	/**
	 * 提供给其他系统调用的  其他将文件上传到文件服务器后，调用这个接口告诉我们上传完毕，并把文件地址之类的信息传给我们
	 * @param systemId  系统编号
	 * @param dataPath  数据包文件存放路径，文件存储服务器的唯一标识
	 * @param caseId    案件编号，即业务系统中的案件编号。(检察院为统一受案号，法院为案件标识，监狱为批次编号，即业务系统中的批次编号）
	 * @param jsfCorpId 接收方单位编号
	 * @param fsfCorpId 发送方单位编号
	 * @param stageId   协同业务的阶段标识
	 * @param sjbs      数据标识 用UUID来写
	 * @return
	 */
	public String sendData(String systemId,String dataPath,String caseId,String jsfCorpId,String fsfCorpId,String stageId,String sjbs,String xml);
	
	/**
	 * 外接向我们索取数据
	 * @param systemId
	 * @param corpId
	 * @return
	 */
	public String getCaseData(String systemId,String corpId);
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
}


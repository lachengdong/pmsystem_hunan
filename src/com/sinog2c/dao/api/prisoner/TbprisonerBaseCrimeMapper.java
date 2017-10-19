package com.sinog2c.dao.api.prisoner;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.commutationParole.TbxfSentenceAlteration;
import com.sinog2c.model.prisoner.TbprisonerBaseCrime;

public interface TbprisonerBaseCrimeMapper {
	int insert(TbprisonerBaseCrime record);

    int insertTbprisonerBaseCrime(TbprisonerBaseCrime record);
    TbprisonerBaseCrime selectPrisonerById(String crimid);
    TbprisonerBaseCrime selectPrisonerById_ywgk(@Param("cardId")String cardId);
    TbprisonerBaseCrime getCrimeByByFileno(@Param("fileno")String fileno);
    int deleteByCrimid(String crimid);
    int updateByPrimaryKeySelective(TbprisonerBaseCrime record);
    int updateByPrimaryKeySelect(TbxfSentenceAlteration sentence);
    int updateBaseCrimeByCrimid(Map<String,Object> baseCrimeMap);
    List<TbprisonerBaseCrime> getPrisonerByOrgid(@Param("orgid")String orgid);
    
    Map getJailOutPrisonFormData(@Param("crimid")String crimid);
    
    Map getCriminalInfoByCrimid(@Param("crimid")String crimid);
    
    Map getBingChanJianDingContent(@Param("crimid")String crimid,@Param("flowdefid")String flowdefid);
    
    List<Map> queryPanCaiInfoService(Map map);
    
    int queryPanCaiInfoCounts(Map map);
    
    List<Map> queryTbdataSentchageImpl(Map map);
    
    int ajaxGetPropertyPunishmentCount(Map<String,Object> map);
    
    List<Map<String,Object>> ajaxGetPropertyPunishmentChoose(Map<String,Object> map);
    
    Map selectSentenceById(@Param("crimid")String crimid);
    
    List<Map> getCodeNameByCodeTypeService(Map map);
    
    List<Map> judgeIsExistYiShenAndErShen(Map map);
    List<Map> queryChangeInfo(Map map);
    
    int saveChangeData(Map map);
    
    int updateChangeData(Map map);
    
    Map getProvinceOutPrisonFormData(@Param("crimid")String crimid);
    
	List<Map> queryChengPiBiaoContentMapper(Map map);
	
	int queryChengPiBiaoContentCount(Map map);
	
    List<Map> queryChengPiBiaoContentMapper_yzk(Map map);
	
	int queryChengPiBiaoContentCount_yzk(Map map);
	
	Map getMaxBatch(@Param("departid")String departid);
	
	Map getCPBCriminalInfo(Map map);
	
	Map getDanGeCriminalInfo(Map map);
	/**
	 * 方法描述：判断减刑假释呈批表 更新or新增
	 * @author ：mushuhong
	 * @version:2015年1月9日08:59:49
	 */
	Map judgeJxJsCPbInsertOrUpdate(Map parameterMap);
	/**
	 * 方法描述：减刑假释呈批表 更新
	 * @author :mushuhong
	 * @version:2015年1月9日09:07:47
	 */
	int jxjsCPBUpdate(Map parameterMap);
	/**
	 * 方法描述：减刑假释呈批表新增
	 * @author :mushuhong
	 * @version:2015年1月9日09:07:47
	 */
	int jxjsCPBInsert(Map parameterMap);
	
	/**
	 * 方法描述：根据条件进行查询 流程
	 * @author：mushuhong
	 * @version：2015年1月9日16:32:42
	 */
	Map queryDeliverByResidAndFlowdefid(Map map);
	/**
	 * 方法描述：更新流程状态
	 * @author :mushuhong
	 * @version:2015年1月9日16:41:22
	 */
	int updateLiuChengByCrimid(Map map);
    int updateBycrimid(TbprisonerBaseCrime record);
    
    List<Map> getCriminInfor(Map map);
    
    List<Map> getSentenceAlterationList(Map map);
    
    int getCriminInforCount(Map map);
    
    int updatePropertyPunishmentBycrimid(TbprisonerBaseCrime record);
    
	String getIsforeignByFlowdraftid(String flowdraftid);

	int getCount(Map map);
	
	Map<String, Object> getGenerateResonInfo(String flowdraftid);

	TbprisonerBaseCrime selectPrisonerBaseCrimeInfoById(String crimid);
	
	int saveThreeKindInfoOfBaseCrime(Map<String,Object> map);
	
	int updateThreePunishmentBycrimid(TbprisonerBaseCrime record);
	
	int updateAdjustPunishmentBycrimid(TbprisonerBaseCrime record);
	
	List<Map<String,Object>> getCriminalPrisonData(Map<String,Object> map);

	void updateImportantPunishmentBycrimid(TbprisonerBaseCrime record);
	
	void updatePprioveAndduty(Map map);
	
	/**
     * 新增罪犯基本信息
     * @param record
     * @return
     */
    int insertTbprisonerBaseCrimeMap(Map<String, Object> record);

    /**
	 * 更新刑期变动信息表TBXF_SENTENCEALTERATION表数据
	 * @param orgid String 组织id
	 * @param rimid String 罪犯id
	 */
	public void callXFsentencealteration(Map map);
}
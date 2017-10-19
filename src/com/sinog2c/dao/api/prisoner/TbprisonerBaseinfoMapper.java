package com.sinog2c.dao.api.prisoner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.prisoner.TbprisonerBaseinfo;


public interface TbprisonerBaseinfoMapper {
	
    int deleteByPrimaryKey(String crimid);
    /**
	 * 根据crimid  查询
	 */
    TbprisonerBaseinfo selectByPrimaryKey(String crimid);
    /**
	 * 根据crimid  查询罪犯姓名拼音首字母（大写、前两位）
	 */
    String selectPinYinByCrimid(String crimid);
    
    Map selectMySelfInfoByCrimid(String crimid);
    /**	
	 * 修改数据（只处理非空字段）
	 * @author kexz 2014-7-24 15:04:46
	 */
    int updateByPrimaryKeySelective(TbprisonerBaseinfo record);
    /***
     * 新增数据
     */
    void insert(TbprisonerBaseinfo info);
	
    /**	
	 * 新增数据（只处理非空字段）
	 * @author kexz 2014-7-24 15:04:46
	 */
    int insertSelective(TbprisonerBaseinfo record);
    /**
	 * 获取罪犯列表
	 * @author kexz 2014-7-22 2014-7-24 15:04:46
     * @param map 
	 */
	List<Map<String,Object>> findByCondition(Map<String, Object> map);
	/**
	 * 根据map传参查询数据返回总数
	 * @author kexz 2014-7-22 2014-7-24 15:04:46
	 */
	int countAllByCondition(Map<String, Object> map);
	
	int countFindWithFlow(Map<String, Object> map);
	int getNewCriminalCount(Map<String,Object> map);
	
	List<HashMap<Object,Object>> getNewCriminalList(Map<String,Object> map);
	/**
	 * 方法描述：获取罪犯编号
	 */
	String getCrimid(String departid);
	TbprisonerBaseinfo selectPrisonerById(String id);
	/**
	 * 获取罪犯列表（含流程状态，identity：0为未处理，1为保存未提交，2为已提交，idnumber 为流程草稿id）
	 * 
	 * @author shily 2014-8-12 15:28:49
     * @param map 
	 */
	List<Map<String,Object>> findWithFlow(Map<String, Object> map);
	/**
	 * 获取拥有档案的犯人信息 总数
	 * @param map
	 * @return
	 */
	int countGetCrimWithArchs(Map<Object, Object> map);
	/**
	 * 获取拥有档案的犯人信息
	 * @param map
	 * @return
	 */
	List<TbprisonerBaseinfo> getCrimWithArchs(Map<Object, Object> map);
	
	void updateByCrimid(Map<String,Object> crimid);
	/**
	 * 根据编号获取犯人信息
	 * @param crimid
	 * @return
	 */
	List<HashMap<Object,Object>> getCrimeBaseInfo(@Param("crimid")String crimid);
	
	List<Map> getSanLeiFan(Map map);
	
	int getSLFcount(Map map);
	
	void lockCriminal(Map map);
	
	List<Map> getJianYuName();
	
	String getYuanPan(Map map);
	
	int checkCrimidAndFileno(Map<String,Object> map);
	
	List<TbprisonerBaseinfo> selectAllByDepartid(@Param("departid")String departid);
	
	List<Map> getJianChaInfo(Map map);
	int getJianChaInfoCount(Map map);
		
	String getZhendInfo(Map map);
	
	String getbsjdyjByCrimid(Map<String,Object> map);
	
	int threeCrimeAffirmService(Map map);
	
	List<Map> getThreeOfCrimAffirmList_sx(Map map);
	
	List<Map> getThreeOfCrimAffirmList_sxcount(Map map);
	int deleRelationByCrimid(String str);
	
	int deleteAccompliceByCrimid(String str);

	Map<String, Object> getJitiShenHeBiao(Map params);
	
	Map getBaseInfoByCrimidMap(@Param("crimid")String crimid,
			                   @Param("departid")String departid);
			                   
	int countAllByConditions(Map<String, Object> map);
	
	int countProvinceAllByCondition(Map<String, Object> map);
	
	List<Map> getProvinceBasicInfoList(Map<String, Object> map);
	
	List<Map> findByConditions(Map<String, Object> map);
	
	Map getPropertyPunishmentInfo(@Param("crimid")String crimid);
	
	Map getThreeTypeCriminalInfo(@Param("crimid")String crimid);
	
	Map getAdjustPrisonInfo(@Param("crimid")String crimid);
	/**
	 * 方法描述：立案审批表显示
	 * @author zhenghui
	 * @version 2014年12月9日13:38:54
	 */
	List<Map> getParoleApproveMap(@Param("crimid")String crimid);
	/**
	 * 方法描述：更改报备状态
	 * @author 张永有
	 * @version 2014年12月18日09:38:54
	 */
	void updateReportaudit(@Param("crimid")String crimid);
	
	int countCrimNumByParaMap(Map<String, Object> map);
	
	List<Map<String, Object>> getCrimInfoByParaMap(Map<String, Object> map);
			
	String getCrimeNameByCrimid(@Param("crimid")String crimid);
	
	String getUnitlevelByCrimidOrgid(@Param("crimid")String crimid);
	
	int findCountByCrimid(@Param("crimid")String crimid);
	
	void callSentncechangByCrimid(@Param("crimid")String crimid);
	
	public void makeNamePinyinShort();	

	Map generatePYByCrimidMapper(@Param("crimid")String crimid);
	
	List<Map> querySocialRelationMapper(@Param("crimid")String crimid);
	
	List<Map> selectByCardOrCrimid(Map<String,Object> paramMap);
	
	Map getCurrentBatch(String departid);
	
	int updateBaseByCrimid(Map map);
	
	List<Map> getResidByUserid(Map map);
} 
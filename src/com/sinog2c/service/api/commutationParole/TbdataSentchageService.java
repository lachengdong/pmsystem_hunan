package com.sinog2c.service.api.commutationParole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinog2c.model.commutationParole.TbdataSentchage;
import com.sinog2c.model.commutationParole.TbdataSentchageKey;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.SystemUser;

public interface TbdataSentchageService {
	List<TbdataSentchage> selectDataList(Map<String,Object> map);
	int selectDataListCount(Map<String,Object> map);
	
	int updateKHZR(Map<String,Object> map);
	
	int updateBatchReview(Map<String,Object> map);
	
	int updateSentenceChangeKHZRDate(List list);
	
	int setCrimeTypesMapping(Map<String, Object> map);
	
	int deleteCrimeTypesMapping(Map<String, Object> map);
	void callREWARDSTARTProcedure();
	int insertSelective(TbdataSentchage record);
	
	int updateByExampleSelective(TbdataSentchage record);
	
	int updateOriginalSentenceData(String crimid);
	
	
	TbdataSentchage selectDataByPk(Map<String,Object> map);
	

	TbdataSentchage selectDataByUuid(Map<String,Object> map);
	List<Map<String,Object>>  selectDataBycrimid(Map<String,Object> map);
	Map<String,Object>  viewScreeningExcuse(Map<String,Object> map);
	List<Map>  selectAllBycrimidAndCategory(Map<String,Object> map);
	Map  selectAllBycrimidAndCategoryAndCourtsanction(Map<String,Object> map);
	List<TbdataSentchage> getLastCommutationData(Map<String, Object> map);
    int getLastCommutationDataCount(Map<String,Object> map);
    int getOrgSentenceInfoCount(Map<String,Object> map);
    List<Map> getOrgSentenceInfoList(Map<String,Object> map);
    int deleteSentenceInfo(TbdataSentchageKey key);
    
    int getSentenceCountByCrimid(String crimid);
    
    TbdataSentchage getCheckendByCrimid(String crimid);
    
    public List<Map<String,Object>> getSentenceChangeData(Map<String, Object> map);
    
    public int countSentenceChangeData(Map<String, Object> map);
    String getCourtInfoByCrimid(String crimid);
	/**
	 * 查询符合条件的数据总条数
	 * @param key
	 * @return
	 */
	public int getConsumeCount(String crimid);
	
	/**
	 * 分页显示列表数据
	 * @param key
	 * @param start 
	 * @param end
	 * @return
	 */
	public List<HashMap> getConsumeList(String crimid, String sortField, String sortOrder, int start, int end );
	/**
	 * 查询符合条件的数据总条数
	 * @param key
	 * @return
	 */
	public int getCrimQpbTeaCount(String crimid);
	
	/**
	 * 分页显示列表数据
	 * @param key
	 * @param start 
	 * @param end
	 * @return
	 */
	public List<HashMap> getCrimQpbTeaList(String crimid, String sortField, String sortOrder, int start, int end );
	int insertCrimQpbTea(Map<String, Object> map);
	int deleteCrimQpbTea(Map<String, Object> map);
	public List<Map<String,Object>> getSentenceChangedData(Map<String, Object> map);
	public int countSentenceChangedData(Map<String, Object> map);
	
	public int updateBianDongFuDuByMap(Map<String, Object> map);
	
	List<String> selectJxCrimid(Map map);
	int updateAwardends(Map map);
	
	public List<Map> getBasicBianDongInfoList(Map<String, Object> map);
	public List<Map<String,Object>> getSentenceChangeByBatch(Map<String, Object> map);
	
	public int countAllBianDongByCondition(Map<String, Object> map);   
	
	public int countSentenceChangeByBatch(Map<String, Object> map);
	
	public String getZhiXingDate(Map map);

	public String getXianXingQi(Map map);
	
	/**
	 * 方法描述：更新刑期变动
	 * @author: mushuhong
	 * @version:2015年10月29日17:30:36
	 * @param map
	 */
	public  void manual_updatesentncechang(Map map);
	
	/**
	 * 方法描述：删除刑期变动
	 * @param xfbduuid
	 */
	public int removeSentenceChange(Map map);
	
	public List<Map> getJiFenBuLuById(Map<String, String> map);
	
	int insertJiFenBuLu(Map<String, String> map);
	
	int updateJiFenBuLuById(Map<String,String> map);
	
	public int removeJiFenBuLuById(Map map);
	
	public List<Map<String, Object>> getKaoHeZongFenByCrimid(Map<String, Object> map);
	
    int insertKaoHeZongFen(Map<String, Object> map);
	
	int updateKaoHeZongFenById(Map<String,Object> map);
	
	public List<Map> getCaiDingList(Map map);
	
	public int saveChangeSentenceChangeForHuNan(SystemUser user, Map<String,String> map);
	
	
	/**
	 * 方法描述：录入或者更改余分和累计表扬后同步TBXF_SENTENCEALTERATION的rewardinfo字段 
	 */
	int updateSentenceAlterationRewardinfo(Map<String,Object> map);
	
}

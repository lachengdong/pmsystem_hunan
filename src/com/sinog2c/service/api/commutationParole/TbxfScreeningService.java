package com.sinog2c.service.api.commutationParole;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.commutationParole.TbxfScreening;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysCode;
import com.sinog2c.mvc.message.JSONMessage;

public interface TbxfScreeningService {
	/**
	 * 根据删除
	 * @param crimid
	 * @return
	 */
	 int deleteByPrimaryKey(String crimid);
	 	/**
	 	 * 新增
	 	 * @param record
	 	 * @return
	 	 */
	    int insert(TbxfScreening record);

	    int insertSelective(TbxfScreening record);
	    /**
	     * 根据id返回单个
	     * @param crimid
	     * @return
	     */
	    TbxfScreening selectByPrimaryKey(String crimid);
	    /**
	     * 根据传人值更新
	     * @param record
	     * @return
	     */
	    int updateByPrimaryKeySelective(TbxfScreening record);
	    /**
	     * 根据参数处理案件性质
	     * @param parmMap
	     * @return
	     */
	    int saveData(Map<String,Object> parmMap);
	    /**
	     * 更新
	     * @param record
	     * @return
	     */
	    int updateByPrimaryKey(TbxfScreening record);
	    
	    List<TbxfScreening> screeningList(String departid);
	    /**
	     * 删除全部
	     */
	    void deleteAll(String departid);
	    
	    int getLiAnCount(Map map);
		
	    List<Map> getLiAnList(Map map);
	    /**
	     * 更新是否申报
	     * @param list
	     * @return
	     */
	    int updateIsdeclare(List<String> list);
	    
		int updateSpecialCrim(Map map);
	  
	    Object updateDataAfterPrisonerLiAn(Map map);
	    
	    /**
	     * 查找罪犯案件性质列表
	     */
	    List<Map> getAnJianXingZhiList(Map map);
	    
	    int getAnjianXingZhiCount(Map map);
	    
	    TbsysCode getAnJianName(Map map);
	    
	    public JSONMessage dealSpecialCase(Map<String,String> map, SystemUser user);

	    /**
	     * 案件分类列表数量
	     * @param parmMap
	     * @return
	     */
	    int getCount(Map<String,Object> parmMap);
	    
	    /**
	     * 案件分类列表信息
	     * @param parmMap
	     * @return
	     */
	    List<Map> getSentenceAlterationList(Map<String,Object> parmMap);
}

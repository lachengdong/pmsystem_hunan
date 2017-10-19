package com.sinog2c.dao.api.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.sinog2c.model.system.TbsysTemplate;

/**
 * 类描述：系统模板
 * 
 * @author shily 2014-7-17 10:12:26
 */
@Component("TbsysTemplateMapper")
public interface TbsysTemplateMapper {

	/**
     * 插入数据（只处理非空）
     * @author shily 2014-7-18 14:52:58
     */
    int insertSelective(TbsysTemplate record);
    
    /**
	 * 获取当前页面数据（分页）
	 * 
	 * @author liucy 2014-7-17 10:12:26
	 */
	List<TbsysTemplate> getTemplateList(Map map);

	/**
	 * 获取符合条件的数据总数（分页时显示总数）
	 * 
	 * @author liucy 2014-7-17 10:12:26
	 */
	int getCount(Map map);
	
	/**
	 * 获取所有数量
	 * @return 所有模板的数量
	 */
	int countAll();
	
	/**
	 * 
	 * @return 所有模板
	 */
	List<TbsysTemplate> listAll();


	/**
	 * 通过模板id获取模板
	 * @author liucy 2014-7-17 10:12:26
	 */
	TbsysTemplate getTemplateDetail(Map<String,Object> map);

	/**
	 * @方法描述：获取公示情况记录模板
	 * @author zgl
	 * @version 2014-9-22 下午04:33:23
	 */
	TbsysTemplate getTemplateGongshi(@Param("tempid")String tempid,@Param("departid")String departid);
	/**
	 * 修改保存模板信息
	 * @author liuchaoyang 2014-7-22 15:38:06
	 */
	int updateTemplateDetail(TbsysTemplate template);
	/**
	 * 根据 tempid  和   departid  修改系统模板  
	 * @param template
	 * @return
	 */
	int updateTemplateDetail2(Map<String,Object> map);
	/**
	 * 删除模板信息
	 * @author liuchaoyang 2014-7-22 15:38:06
	 */
	int deleteTemplateDetail(@Param("tempid")String tempid,@Param("departid")String departid);
	/**
	 * 通过查询方案Id得到sql语句
	 * 
	 * @author liuchaoyang 2014-7-22 15:38:06
	 */
	String getSqlText(@Param("planid") String planid);
	/**
	 * 方法描述：查询出所有的部门
	 * @author mushuhong
	 * @version 2014年9月2日13:04:47
	 * @param departid
	 * @return
	 */
	List<Map> getOrgMap(String departid);
	/**
	 * 方法描述：查询出所有的 减刑中的罪犯(选择的罪犯)
	 * @author mushuhong
	 * @version 2014年9月2日13:07:04
	 * @param crimids
	 * @return
	 */
	List<Map> getCrimeJxInfo(Map crimids);
	List<Map> getCrimeJxInfo1(Map crimids);
	List<Map> getCrimeJxInfo2(Map crimids);
	/**
	 * 方法描述：查询出本批次 减刑中的罪犯
	 * @author mushuhong
	 * @version 2014年9月2日13:09:17
	 * @return
	 */
	List<Map> getJxJsCaseList(Map criMap);
	
	
	HashMap getDocumentContent(Map map);
	List<Map<String,Object>> getDocumentContentList(Map<String,Object> map);
    
	HashMap getCuryearBatch(String departid);
	
	List<Map> getResourceMapObject();
	
	List<HashMap> ajaxGettemptype(@Param("codetype")String codetype);
	TbsysTemplate getTemplateByTempAndDept(Map<String,Object> map);
	
	Map threeClassCriminalShuoMing(Map map);
	
	int ajaxGetTempid();
	
	String getDepartNameByDepartid(@Param("departid")String departid);

	List<Map<String,Object>> getCourtSystemModelList(Map map);

	int getCourtSystemModelCount(Map map);

	TbsysTemplate getCourtTemplateById(Map<String, Object> map);

	String getCourtUserTemplateById(@Param("userid") String userid, @Param("tempid")String tempid);

	void insertCourtUserTemplate(Map<String, Object> map);

	void updateCourtUserTemplateById(Map<String, Object> map);

	Map<String, Object> getCourtUserTemplateTextById(@Param("tempid")String tempid,@Param("userid")  String userid);

	int deleteCourtUserModelByTempid(String tempid);
	
	int getLiAnApproveCount(Map map);
	
    List<Map> getLiAnApproveList(Map map);
    
    //start add by blue_lv 2015-07-20
    /**
	 * 根据模板类型获取模板信息
	 * @param type
	 * @return
	 */
	List<TbsysTemplate> listAllByType(String type);
	
	TbsysTemplate gettemplatebyflowid(@Param("flowdefid")String flowdefid, @Param("snodeid")String snodeid);

	int updateByPrimaryKeySelective(TbsysTemplate data);

	int insertSelective1(TbsysTemplate systemTemplate);

	//end add by blue_lv 2015-07-20
}
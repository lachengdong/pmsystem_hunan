package com.sinog2c.service.api.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;

/**
 * 系统模板
 * 
 * @author shily 2014-7-18 14:43:38
 */
public interface SystemModelService {
	
   	
	/**
	 * 获取所有数量
	 * @return 所有模板的数量
	 */
	public int countAll();
	
	/**
	 * 
	 * @return 所有模板
	 */
	public List<TbsysTemplate> listAll();
	
	/**
	 * 获取当前页面数据（分页）
	 * 
	 * @author liucy 2014-7-17 16:10:57
	 */
	public List<TbsysTemplate> getTemplateList(Map map);

	/**
	 * 获取符合条件的数据总数（分页时显示总数）
	 * 
	 * @author liucy 2014-7-17 16:11:03
	 */
	public int getCount(Map map);
	
	public TbsysTemplate getTemplateAndDepartid(String tempid, String departid);

	/**
	 * 通过模板id获取模板
	 * 
	 * @author liucy 2014-7-17 16:11:06
	 */
	public TbsysTemplate getTemplateAndDepartid(Map<String,Object> map);
	/**
	 * 方法描述：查询监狱名称
	 * @author mushuhong
	 * @version 2014年12月17日20:00:48
	 */
	public String getDepartNameByDepartid(String departid);
	/**
	 * @方法描述：获取公示情况记录模板
	 * @author zgl
	 * @version 2014-9-22 下午04:33:23
	 */
	public TbsysTemplate getTemplateGongshi(String tempid,String departid);
	/**
	 * 新增保存模板信息
	 * 
	 * @author liuchaoyang 2014-7-22 15:38:06
	 */
	public int saveTemplateDetail(TbsysTemplate template);
	/**
	 * 修改保存模板信息
	 * 
	 * @author liuchaoyang 2014-7-22 15:38:06
	 */
	public int updateTemplateDetail(TbsysTemplate template);
	/**
	 * 删除模板信息
	 * 
	 * @author liuchaoyang 2014-7-22 15:38:06
	 */
	public int deleteTemplateDetail(String tempid,String departid);
	/**
	 * 根据模版编号查询出文书内容、查询方案。根据查询方案id查询数据，返回到某一个字段显示出来
	 * @author liuchaoyang
	 * 2014-8-09  11:15:45
	 */
	public String getRecommendationContent(String tempid,SystemUser user,HttpServletRequest request);
	/**
	 * 执行sql
	 */
	public HashMap getDocumentContent(String departid);
	public List<Map<String,Object>> getDocumentContentList(String departid);
	
	public HashMap getCuryearBatch(String departid);
	
	/**
	 * 得到 所有的资源
	 */
	public List<Map> getResourceMapObject();
	
	public List<HashMap> ajaxGettemptype(String codetype);
	int updateTemplateDetail2(Map<String,Object> map);
	TbsysTemplate getTemplateByTempAndDept(Map<String,Object> map);
	/**
	 * 测试暂无用
	 * @author zhangyongyou
	 * 2014-12-02  11:15:45
	 */
	public Map threeClassCriminalShuoMing(Map map);
	/**
	 * 测试暂无用
	 * @author zhangyongyou
	 * 2014-12-02  11:15:45
	 */
	public int ajaxGetTempid();

	public List<Map<String,Object>> getCourtSystemModelList(Map map);

	public int getCourtSystemModelCount(Map map);

	public TbsysTemplate getCourtTemplateById(Map<String, Object> map);

	public String getCourtUserTemplateById(String userid, String tempid);

	public void insertCourtUserTemplate(Map<String, Object> map);

	public void updateCourtUserTemplateById(Map<String, Object> map);

	public Map<String, Object> getCourtUserTemplateTextById(String tempid,
			String userid);

	public int deleteCourtModelDetail(String tempid, String departid);
	
	/**
	 * 统计查询数据条数
	 * @author zhenghui
	 * 2015-5-17 11:15:45
	 */
	public int getLiAnApproveCount(Map<String, Object> parmMap);
	
	/**
	 * 统计查询数据集合
	 * @author zhengui
	 * 2014-5-17 11:15:45
	 */
	public List<Map> getLiAnApproveList(Map<String, Object> parmMap);
	
	//start add by blue_lv 2015-07-15
	public List<TbsysTemplate> listAllByType(String othertemplatetype);
	//end add by blue_lv 2015-07-15
}

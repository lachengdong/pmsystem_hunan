package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.system.SystemTemplate;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.model.system.TbsysTemplate;

/**
 * @describe:
 * @author YangZR
 * @date 2014-7-24 下午07:07:48
 */
public interface SystemTemplateService {
	
	/**
     * 插入指定数据
     * @param record
     * @return
     */
    int insert(SystemTemplate record);
    /**
     * 插入指定数据
     * @param record
     * @return
     */
    int insertSelective(SystemTemplate record);
    /**
     * 根据模板id删除
     * @param tempid
     * @return
     */
    int deleteByTempid(String tempid,String departid);
    /**
     * 更新数据
     * @param record
     * @return
     */
    int update(SystemTemplate record);
    
    /**
     * 更新数据（区域锁定）
     * @param record
     * @return
     */
    int updateUneditedfields(SystemTemplate record);
    /**
     * 根据表单ID传参查询 
     * @param tempid
     * @return
     */ 
    SystemTemplate selectByTempid(String tempid,String departid);
    
    /**
     * 根据部门id验证是否存在模板ID 
     * @param departid
     * @return
     */ 
    int validateTempidByDepartid(String tempid,String departid);

    /**
     * 根据map传参查询 返回条数
     * @param map
     * @return
     */ 
    int countAllByCondition(Map<String,Object> map);
    /**
     * 根据map传参查询
     * @param map
     * @return
     */
    List<SystemTemplate> selectAllByCondition(Map<String,Object> map);
    /**
     * 根据部门id查询需归档的模板内容
     * @param departid
     * @return
     */
    List<SystemTemplate> selectAllByDepartid(String departid);
    
    /**
     * 根据模板ID查询模板内容
     * @param map
     * @return
     */
    String getSysTemplateInfoByTempid(String tempid,String departid);
    
    public SystemTemplate getSystemTemplateByCondition(String tempid,String departid);
    
    List<Map> getTemplateListByTempids(Map map );
    /**
     * 根据表单ID传参查询 
     * @param tempid
     * @return
     */ 
    List listByTempid(SystemUser user, HttpServletRequest request);
	String findTemplate(String s);
	List<Map<String, Object>> getCourtSystemModelTree(Map<String,Object> map);
	
	//start add by blue_lv 2015-07-15
	/**
	 * 查询表单模板信息（分页）
	 * @param map
	 * @return
	 */
	JSONMessage<TbsysTemplate> getTemplateList(Map<String, java.lang.Object> map);
	/**
	 * 新增表单模板信息
	 * @param systemTemplate
	 * @return
	 */
	int insert(TbsysTemplate systemTemplate);
	/**
	 * 根据模板ID、部门ID查询表单模板信息
	 * @param tempid
	 * @param departid
	 * @return
	 */
	TbsysTemplate selectByTempid1(String tempid, String departid);
	/**
	 * 修改表单模板信息
	 * @param templet
	 * @return
	 */
	int update(TbsysTemplate templet);
	/**
	 * 根据模板类型type获取模板信息
	 * @param type
	 * @return
	 */
	List<TbsysTemplate> listAllByType(String type);
	/**
	 * 模板信息更新（动态）
	 * @param data
	 * @return
	 */
	int updateByPrimaryKeySelective(TbsysTemplate data);
	
	TbsysTemplate gettemplatebyflowid(String flowdefid, String snodeid);
	
	
	//end add by blue_lv 2015-07-15
}

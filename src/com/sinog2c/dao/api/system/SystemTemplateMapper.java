package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.sinog2c.model.system.SystemTemplate;

public interface SystemTemplateMapper {
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
    int deleteByTempid(@Param("tempid")String tempid, @Param("departid")String departid);
    /**
     * 更新数据
     * @param record
     * @return
     */
    int update(SystemTemplate record);
    /**
     * 更新不可编辑区域
     * @param record
     * @return
     */
    int updateUneditedfields(SystemTemplate record);
    /**
     * 根据表单ID传参查询 
     * @param tempid
     * @return
     */ 
    SystemTemplate selectByTempid(@Param("tempid")String tempid, @Param("departid")String departid);
    /**
     * 根据部门ID查询验证表单ID是否存在
     * @param departid
     * @return
     */ 
    int validateTempidByDepartid(@Param("tempid")String tempid, @Param("departid")String departid);
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
     * 查询所有表单
     * @return
     */
	List<Map> selectAllTemplate();
	
	List<Map> getTemplateListByTempids(Map map );
	String findTemplate(String s);
	List<Map<String, Object>> getCourtSystemModelTree(Map<String,Object> map);
	String getContentByTempid(Map map);
}
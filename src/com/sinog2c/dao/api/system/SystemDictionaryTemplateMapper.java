package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.system.SystemDictionaryTemplate;

public interface SystemDictionaryTemplateMapper {
    /**
     * 插入指定数据
     * @param record
     * @return
     */
    int insert(SystemDictionaryTemplate record);
    /**
     * 根据map传参查询 返回条数
     * @param map
     * @return
     */ 
	int countAllByCondition(Map<String, Object> map);
	/**
     * 根据map传参查询
     * @param map
     * @return
     */
	List<SystemDictionaryTemplate> selectAllByCondition(Map<String, Object> map);
	/**
     * 根据模板ID传参查询
     * @param tempid
     * @return
     */
	List<SystemDictionaryTemplate> selectAllByTempid(String tempid);
	 /**
     * 根据模板id和列名删除数据
     * @param tempid
     * @param ecolname
     * @return
     */
	int deleteByEcolname(@Param("tempid")String tempid,@Param("ecolname")String ecolname,@Param("ename")String ename);
	/**
	 * 根据模板id删除数据
	 * @param tempid
	 * @return
	 */
	int deleteByTempid(@Param("tempid")String tempid);
	
	public List<Map<String,Object>> getSystemDictionaryTemplate(Map<String,Object> map);
	
}
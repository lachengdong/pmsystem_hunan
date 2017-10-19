package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.SystemDictionaryTemplate;

/**
 * @describe:
 * @author YangZR
 * @date 2014-7-24 下午07:07:48
 */
public interface SystemDictionaryTemplateService {
	
    /**
     * 根据模板id和列名删除数据
     * @param tempid
     * @param ecolname
     * @return
     */
    int deleteByEcolname(String tempid,String ecolname,String ename);
    
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
    int countAllByCondition(Map<String,Object> map);
    /**
     * 根据map传参查询
     * @param map
     * @return
     */
    List<SystemDictionaryTemplate> selectAllByCondition(Map<String,Object> map);
    
    /**
     * 根据模板ID传参查询
     * @param tempid
     * @return
     */
    List<SystemDictionaryTemplate> selectAllByTempid(String tempid);
    
    List<Map<String,Object>> getSystemDictionaryTemplate(Map<String,Object> map);
	
}

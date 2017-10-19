package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.SystemDictionary;
import com.sinog2c.model.system.SystemDictionaryTemplate;

	public interface SystemDictionaryService {
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
    List<SystemDictionary> selectAllByCondition(Map<String,Object> map);
    /**
     * 根据模板ID传参查询
     * @param tempid
     * @return
     */
    List<SystemDictionary> selectAllByTempid(String tempid);
}

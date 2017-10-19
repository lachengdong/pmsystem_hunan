package com.sinog2c.service.api.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinog2c.model.system.TbsysQueryplan;
import com.sinog2c.model.system.TbsysQueryplansql;
/**
 * 查询方案接口
 * 
 * @author liuxin 2014-7-22 16:14:56
 */
public interface QuerySchemeService {
	/**
	 * 保存查询方案信息
	 * 
	 * @author liuxin 2014-7-22 16:14:56
	 */
	int saveQueryScheme(String type, TbsysQueryplan queryplan);
	/**
	 * 获得查询方案信息列表
	 * 
	 * @author liuxin 2014-7-22 16:41:50
	 */
	List<Map> ajaxShecmeAll(String key);
	/**
	 * 根据查询方案信息id查找查询方案SQL
	 * 
	 * @author liuxin 2014-7-23 12:24:00
	 */
	TbsysQueryplansql getSchemeSqlByPlanId(Integer planid);
	/**
	 * 保存查询方案SQL
	 * 
	 * @author liuxin 2014-7-22 15:12:22
	 */
	int saveQuerySchemeSql(String type, TbsysQueryplansql queryplansql);
	/**
	 * 删除查询方案
	 * 
	 * @author liuxin 2014-7-23 16:41:22
	 */
	int delQueryScheme(TbsysQueryplan queryplan, TbsysQueryplansql queryplansql);
	/**
	 * 根据查询方案信息id查找查询方案SQL
	 * 
	 * @author shily 2014-8-9 8:44:34
	 */
	List<TbsysQueryplansql> getSchemeSqlListByPlanId(HashMap map);

	List<Map> ajaxScreeningShecmeAll();
	
	List<TbsysQueryplansql> getScreeningSchemeSqlListByPlanId(Map<String,Object> map);
}

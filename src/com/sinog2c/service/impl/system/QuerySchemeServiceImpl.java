package com.sinog2c.service.impl.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.system.TbsysQueryplanMapper;
import com.sinog2c.dao.api.system.TbsysQueryplansqlMapper;
import com.sinog2c.model.system.TbsysQueryplan;
import com.sinog2c.model.system.TbsysQueryplansql;
import com.sinog2c.service.api.system.QuerySchemeService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.MapUtil;
/**
 * 查询方案
 * 
 * @author liuxin 2014-7-22 16:14:56
 */
@Service("querySchemeService")
public class QuerySchemeServiceImpl extends ServiceImplBase implements QuerySchemeService {
	@Autowired
	TbsysQueryplanMapper tbsysQueryplanMapper;
	@Autowired
	TbsysQueryplansqlMapper tbsysQueryplansqlMapper;
	/**
	 * 保存查询方案信息
	 * 
	 * @author liuxin 2014-7-22 16:14:56
	 */
	@Override
	public int saveQueryScheme(String type, TbsysQueryplan queryplan) {
		if("new".equals(type)){
			return tbsysQueryplanMapper.saveQueryScheme(queryplan);
		}else if("edit".equals(type)){
			return tbsysQueryplanMapper.updateQueryScheme(queryplan);
		}else{
			return 0;
		}
		
	}
	/**
	 * 获得查询方案信息列表
	 * 
	 * @author liuxin 2014-7-22 16:41:50
	 */
	@Override
	public List<Map> ajaxShecmeAll(String key) {
		return MapUtil.turnKeyToLowerCaseOfList(tbsysQueryplanMapper.selectAll(key));
	}
	/**
	 * 根据查询方案信息id查找查询方案SQL
	 * 
	 * @author liuxin 2014-7-23 12:24:00
	 */
	@Override
	public TbsysQueryplansql getSchemeSqlByPlanId(Integer planid) {
		return tbsysQueryplansqlMapper.selectByPlanId(planid);
	}
	/**
	 * 保存查询方案SQL
	 * 
	 * @author liuxin 2014-7-22 15:12:22
	 */
	@Override
	public int saveQuerySchemeSql(String type, TbsysQueryplansql queryplansql) {
		if("insert".equals(type)){
			return tbsysQueryplansqlMapper.insertQueryplansql(queryplansql);
		}else{
			return tbsysQueryplansqlMapper.updateQueryplansql(queryplansql);
		}
	}
	/**
	 * 删除查询方案
	 * 
	 * @author liuxin 2014-7-23 16:41:22
	 */
	@Override
	public int delQueryScheme(TbsysQueryplan queryplan, TbsysQueryplansql queryplansql) {
		int result = 0;
		result = tbsysQueryplanMapper.delQueryScheme(queryplan);
		TbsysQueryplansql tb = tbsysQueryplansqlMapper.selectByPlanId(queryplan.getPlanid());
		if(tb != null){
			result = tbsysQueryplansqlMapper.delQuerySchemesql(queryplansql);
		}
	
		return result;
	}
	/**
	 * 根据查询方案信息id查找查询方案SQL
	 * 
	 * @author shily 2014-8-9 8:44:34
	 */
	@Override
	public List<TbsysQueryplansql> getSchemeSqlListByPlanId(HashMap map) {
		return tbsysQueryplansqlMapper.selectSqlListByPlanId(map);
	}
	
	@Override
	public List<Map> ajaxScreeningShecmeAll() {
		return MapUtil.turnKeyToLowerCaseOfList(tbsysQueryplanMapper.ajaxScreeningShecmeAll());
	}
	
	@Override
	public List<TbsysQueryplansql> getScreeningSchemeSqlListByPlanId(Map<String,Object> map) {
		return tbsysQueryplansqlMapper.getScreeningSchemeSqlListByPlanId(map);
	}
}

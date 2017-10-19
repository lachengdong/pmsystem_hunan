package com.sinog2c.service.api.common;

import java.util.Map;

import com.sinog2c.model.system.SystemUser;

public interface CommonService {
	
	/**
	 * 描述：组装系统模板：系统模板&系统模板数据
	 * @author YangZR	2015-03-28
	 */
	public String assembleSysTemplateData(Map<String,Object> paramMap,SystemUser user);
	
	
	/**
	 * 通过表名和字段名获取表的字段结构信息
	 * @param map
	 * @return
	 */
	public Map<String,Object> getTableColumn(Map<String,Object> map);
	
	/**
	 * 修改字段名
	 */
	public void alterTableRenameColumn(Map<String,Object> map);
	
	/**
	 * 添加字段
	 * @param map
	 */
	public void alterTableAddColumn(Map<String,Object> map);
	
	/**
	 * 修改字段信息
	 */
	public void alterTableModifyColumn(Map<String,Object> map);
	
	/**
	 * 删除字段
	 * @param map
	 */
	public void alterTableDropColumn(Map<String,Object> map);
}

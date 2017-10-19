package com.sinog2c.service.api.flow;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.system.Tbsys_doctemplate;
import com.sinog2c.model.system.Tbsys_doctemplateWithBLOBs;



public interface TbsysDocTemplateService {

	int deleteByPrimaryKey(String doctempid);

	int insertSelective(Tbsys_doctemplateWithBLOBs record);

	Tbsys_doctemplateWithBLOBs selectByPrimaryKey(String doctempid);

	int updateByPrimaryKeySelective(Tbsys_doctemplateWithBLOBs record);

	/**
	 * 根据部门获取公文正文模板信息
	 * 
	 * @param map
	 * @return
	 */
	JSONMessage<Tbsys_doctemplate> getDocTemplateBydept(Map<String, Object> map);	

	/**
	 * 根据模板模块与部门查询模板信息
	 * @param departid
	 * @param type
	 * @return
	 */
	List<Tbsys_doctemplateWithBLOBs> getDocTemplateBydeptAndModule(
			String departid, String module);

	/**
	 *  根据模板模块与部门查询公文套红模板信息
	 * @param departid
	 * @param type
	 * @return
	 */
	List<Tbsys_doctemplate> getDocTemplateBydeptAndModule2(String departid,
			String module);

}

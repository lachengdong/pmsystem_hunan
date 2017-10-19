package com.sinog2c.dao.api.flow;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.system.Tbsys_doctemplate;
import com.sinog2c.model.system.Tbsys_doctemplateWithBLOBs;


public interface Tbsys_doctemplateMapper {
	int deleteByPrimaryKey(String doctempid);

	int insert(Tbsys_doctemplateWithBLOBs record);

	int insertSelective(Tbsys_doctemplateWithBLOBs record);

	Tbsys_doctemplateWithBLOBs selectByPrimaryKey(String doctempid);

	int updateByPrimaryKeySelective(Tbsys_doctemplateWithBLOBs record);

	int updateByPrimaryKeyWithBLOBs(Tbsys_doctemplateWithBLOBs record);

	int updateByPrimaryKey(Tbsys_doctemplate record);

	int getTemplateCountBydept(Map<String, Object> map);

	List<Tbsys_doctemplate> getDocTemplateBydept(Map<String, Object> map);

	List<Tbsys_doctemplateWithBLOBs> getDocTemplateBydeptAndmodule(
			@Param("departid") String departid, @Param("module") String module);
	List<Tbsys_doctemplate> getDocTemplateBydeptAndmodule2(
			@Param("departid") String departid, @Param("module") String module);

}
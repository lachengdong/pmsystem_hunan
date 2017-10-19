package com.sinog2c.service.impl.flow;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.flow.Tbsys_doctemplateMapper;
import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.system.Tbsys_doctemplate;
import com.sinog2c.model.system.Tbsys_doctemplateWithBLOBs;
import com.sinog2c.service.api.flow.TbsysDocTemplateService;



@Service("tbsysDocTemplateService")
public class TbsysDocTemplateServiceImpl implements TbsysDocTemplateService {

	@Autowired
	private Tbsys_doctemplateMapper tbsys_doctemplateMapper;
	@Override
	public int deleteByPrimaryKey(String doctempid) {
		return this.tbsys_doctemplateMapper.deleteByPrimaryKey(doctempid);
	}

	@Override
	public int insertSelective(Tbsys_doctemplateWithBLOBs record) {
		return this.tbsys_doctemplateMapper.insertSelective(record);
	}

	@Override
	public Tbsys_doctemplateWithBLOBs selectByPrimaryKey(String doctempid) {
		return this.tbsys_doctemplateMapper.selectByPrimaryKey(doctempid);
	}

	@Override
	public int updateByPrimaryKeySelective(Tbsys_doctemplateWithBLOBs record) {
		return this.tbsys_doctemplateMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public JSONMessage<Tbsys_doctemplate> getDocTemplateBydept(
			Map<String, Object> map) {
		int rowCount = Integer.parseInt(map.get("total").toString());		
		if (rowCount < 0) {
			rowCount = this.tbsys_doctemplateMapper.getTemplateCountBydept(map);
		}
		List<Tbsys_doctemplate> list = this.tbsys_doctemplateMapper.getDocTemplateBydept(map);

		JSONMessage<Tbsys_doctemplate> result = new JSONMessage<Tbsys_doctemplate>(
				list, rowCount);
		return result;
	}

	@Override
	public List<Tbsys_doctemplateWithBLOBs> getDocTemplateBydeptAndModule(
			String departid, String module) {
		return this.tbsys_doctemplateMapper.getDocTemplateBydeptAndmodule(departid, module);
	}

	@Override
	public List<Tbsys_doctemplate> getDocTemplateBydeptAndModule2(
			String departid, String module) {
		return this.tbsys_doctemplateMapper.getDocTemplateBydeptAndmodule2(departid, module);
	}
	
	

}

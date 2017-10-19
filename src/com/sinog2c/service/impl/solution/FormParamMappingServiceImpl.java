package com.sinog2c.service.impl.solution;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.solution.FormParamMappingMapper;
import com.sinog2c.model.solution.FormParamMapping;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.solution.FormParamMappingService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.StringNumberUtil;

@Service("formParamMappingService")
public class FormParamMappingServiceImpl extends ServiceImplBase implements  FormParamMappingService{

	@Autowired
	FormParamMappingMapper formParamMappingMapper;
	
	@Override
	public List<FormParamMapping> getFormParamMappingDataList(Map<String, Object> map) {
		return formParamMappingMapper.listByCondition(map);
	}
	
	@Override
	public int countFormParamMappingDataList(Map<String, Object> map) {
		return formParamMappingMapper.countByCondition(map);
	}

	@Override
	public FormParamMapping getSingleParamMapping(Map<String,Object> map) {
		return formParamMappingMapper.selectByCombinationId(map);
	}

	@Override
	@Transactional
	public int saveParamMapping(Map<String,Object> map,SystemUser su) {
		Object operateObj = map.get("operate");
		if(StringNumberUtil.notEmpty(operateObj)){
			if("new".equals(operateObj.toString())){
				map.put("createmender", su.getUserid());
				return formParamMappingMapper.insertByMap(map);
			}else if("edit".equals(operateObj.toString())){
				map.put("updatemender", su.getUserid());
				return formParamMappingMapper.updateByMap(map);
			}
		}
		return 0;
	}
	
	@Override
	@Transactional
	public int deleteParamMapping(Map<String,Object> map){
		return formParamMappingMapper.deleteByMultiId(map);
	}

}

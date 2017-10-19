package com.sinog2c.service.impl.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.system.TbsysFormDetailsMapper;
import com.sinog2c.model.system.TbsysFormDetails;
import com.sinog2c.service.api.system.TbsysFormDetailsService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("tbsysFormDetailsService")
public class TbsysFormDetailsServiceImpl extends ServiceImplBase implements TbsysFormDetailsService{
	@Autowired
	TbsysFormDetailsMapper tbsysFormDetailsMapper;
	
	@Override
	public List<TbsysFormDetails> getFormDetails(Map<String,Object> map){
		return tbsysFormDetailsMapper.getFormDetails(map);
	}
	
}
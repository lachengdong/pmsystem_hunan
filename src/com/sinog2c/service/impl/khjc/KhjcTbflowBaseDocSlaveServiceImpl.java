package com.sinog2c.service.impl.khjc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.khjc.KhjcTbflowBaseDocSlaveMapper;
import com.sinog2c.model.khjc.KhjcTbflowBaseDocSlave;
import com.sinog2c.service.api.khjc.KhjcTbflowBaseDocSlaveService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("khjcTbflowBaseDocSlaveService")
public class KhjcTbflowBaseDocSlaveServiceImpl extends ServiceImplBase implements KhjcTbflowBaseDocSlaveService{
	
	@Autowired
	KhjcTbflowBaseDocSlaveMapper  khjcTbflowBaseDocSlaveMapper;
	
	
	@Override
	public KhjcTbflowBaseDocSlave getKhjcTbflowBaseDocSlaveByCondition(String docid, String templetid) {
		Map map = new HashMap();
		map.put("docid", docid);
		map.put("templetid", templetid);
		return khjcTbflowBaseDocSlaveMapper.getKhjcTbflowBaseDocSlaveByCondition(map);
	}

}

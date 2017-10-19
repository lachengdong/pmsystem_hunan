package com.sinog2c.service.impl.oa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.oa.TbOaWcspMapper;
import com.sinog2c.model.oa.TbOaWcsp;
import com.sinog2c.service.api.oa.OaWcspService;


@Service("oaWcspService")
public class OaWcspServiceImpl implements OaWcspService {

	@Autowired
	private TbOaWcspMapper tboawcspMapper;
	    
	
	@Override
	public int add(TbOaWcsp tboawcsp) {	
		String uuid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		tboawcsp.setUuid(uuid);
		int i = tboawcspMapper.insertSelective(tboawcsp);
		return i;
	}

}

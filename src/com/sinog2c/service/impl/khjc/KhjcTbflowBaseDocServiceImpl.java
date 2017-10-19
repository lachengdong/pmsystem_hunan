package com.sinog2c.service.impl.khjc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.khjc.KhjcTbflowBaseDocMapper;
import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;
import com.sinog2c.service.api.khjc.KhjcTbflowBaseDocService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("khjcTbflowBaseDocService")
public class KhjcTbflowBaseDocServiceImpl extends ServiceImplBase implements KhjcTbflowBaseDocService{

	@Autowired
	KhjcTbflowBaseDocMapper  khjcTbflowBaseDocMapper;
	
	@Override
	public KhjcTbflowBaseDoc selectByPrimaryKey(String docid) {
		// TODO Auto-generated method stub
		return khjcTbflowBaseDocMapper.selectByPrimaryKey(docid);
	}
	
	@Override
	public KhjcTbflowBaseDoc selectByCondition(String docid,String templetid){
		Map map = new HashMap();
		map.put("docid",docid);
		map.put("templetid",templetid);
		return khjcTbflowBaseDocMapper.selectByCondition(map);
	}

}

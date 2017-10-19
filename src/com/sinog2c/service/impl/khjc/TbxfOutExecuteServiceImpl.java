package com.sinog2c.service.impl.khjc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.khjc.TbxfOutExecuteMapper;
import com.sinog2c.service.api.khjc.TbxfOutExecuteService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.StringNumberUtil;

@Service("tbxfOutExecuteService")
public class TbxfOutExecuteServiceImpl extends ServiceImplBase implements TbxfOutExecuteService{
	
	@Autowired
	private TbxfOutExecuteMapper tbxfOutExecuteMapper;
	/**
     * 获取保外最大案件号
     * @return
     */
	@Override
	public int getMaxOutExecuteCaseNo(Map map){
		String caseNo = tbxfOutExecuteMapper.getMaxOutExecuteCaseNo(map);
		if(StringNumberUtil.isEmpty(caseNo)){
			caseNo = "0";
		}
		return Integer.parseInt(caseNo)+1;
		
	}
	
	@Override
	public int saveTbxfOutExecuteSensitive(Map map){
		return tbxfOutExecuteMapper.insertSelectiveByMap(map);
	}

}

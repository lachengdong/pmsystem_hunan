package com.sinog2c.service.impl.khjc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.khjc.TbxfCommutationMapper;
import com.sinog2c.service.api.khjc.TbxfCommutationService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.StringNumberUtil;

@Service("tbxfCommutationService")
public class TbxfCommutationServiceImpl extends ServiceImplBase implements TbxfCommutationService{
	
	@Autowired
	TbxfCommutationMapper tbxfCommutationMapper;

	/**
     * 获取减刑假释最大案件号
     * @return
     */
	@Override
	public int getMaxCommuteCaseNo(Map map){
		String caseNo = tbxfCommutationMapper.getMaxCommuteCaseNo(map);
		if(StringNumberUtil.isEmpty(caseNo)){
			caseNo = "0";
		}
		return Integer.parseInt(caseNo)+1;
		
	}
	
	/**
	 * 保存一条减刑假释记录
	 */
	@Override
	public int saveTbxfCommutationSensitive(Map map){
		return tbxfCommutationMapper.insertSelectiveByMap(map);
	}

}

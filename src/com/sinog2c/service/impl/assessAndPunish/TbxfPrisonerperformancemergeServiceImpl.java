package com.sinog2c.service.impl.assessAndPunish;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.system.TbxfPrisonerperformancemergeMapper;
import com.sinog2c.model.system.TbxfPrisonerperformancemerge;
import com.sinog2c.service.api.assessAndPunish.TbxfPrisonerperformancemergeService;

@Service("tbxfPrisonerperformancemergeService")
public class TbxfPrisonerperformancemergeServiceImpl implements TbxfPrisonerperformancemergeService{

	@Resource
	TbxfPrisonerperformancemergeMapper tbxfPrisonerperformancemergeMapper;
	public TbxfPrisonerperformancemerge selectByCrimidAndBatchid(Map map){
		return tbxfPrisonerperformancemergeMapper.selectByCrimidAndBatchid(map);
	}
	@Override
	public String getDepartidByCrimid(String str) {
		return tbxfPrisonerperformancemergeMapper.getDepartidByCrimid(str);
	}
	
}

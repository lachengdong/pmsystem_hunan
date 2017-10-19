package com.sinog2c.service.impl.commutationParole;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.commutationParole.PrisonerPerformanceMapper;
import com.sinog2c.service.api.commutationParole.PrisonerPerformanceService;

@Service("prisonerPerformanceService")
public class PrisonerPerformanceServiceImpl implements PrisonerPerformanceService{
	
	@Resource
	private PrisonerPerformanceMapper PrisonerPerformanceMapper;

	@Override
	public int getCount(HashMap map) {
		// TODO Auto-generated method stub
		return PrisonerPerformanceMapper.getCount(map);
	}

	@Override
	public List<HashMap> getPrisonerPerformanceList(HashMap map) {
		// TODO Auto-generated method stub
		return PrisonerPerformanceMapper.getPrisonerPerformanceList(map);
	}
	
	
}

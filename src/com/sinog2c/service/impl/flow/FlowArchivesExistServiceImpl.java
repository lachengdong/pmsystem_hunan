package com.sinog2c.service.impl.flow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.flow.FlowArchivesExistMapper;
import com.sinog2c.model.flow.FlowArchivesExist;
import com.sinog2c.service.api.flow.FlowArchivesExistService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("flowArchivesExistService")
public class FlowArchivesExistServiceImpl extends ServiceImplBase implements FlowArchivesExistService{

	@Autowired
    private FlowArchivesExistMapper flowArchivesExistMapper;

	public FlowArchivesExistMapper getFlowArchivesExistMapper() {
		return flowArchivesExistMapper;
	}
	public void setFlowArchivesExistMapper(
			FlowArchivesExistMapper flowArchivesExistMapper) {
		this.flowArchivesExistMapper = flowArchivesExistMapper;
	}
	@Transactional
    public int insertSelective(FlowArchivesExist flowArchivesExist) {
        return this.flowArchivesExistMapper.insertSelective(flowArchivesExist);
    }
	@Transactional
	public int updateByExample(FlowArchivesExist flowArchivesExist) {
		return this.flowArchivesExistMapper.updateByExample(flowArchivesExist);
	}
}
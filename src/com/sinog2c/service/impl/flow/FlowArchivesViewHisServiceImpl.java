package com.sinog2c.service.impl.flow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.flow.FlowArchivesViewHisMapper;
import com.sinog2c.model.flow.FlowArchivesViewHis;
import com.sinog2c.service.api.flow.FlowArchivesViewHisService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("flowArchivesViewHisService")
public class FlowArchivesViewHisServiceImpl extends ServiceImplBase implements FlowArchivesViewHisService{

	@Autowired
    private FlowArchivesViewHisMapper flowArchivesViewHisMapper;

	public FlowArchivesViewHisMapper getFlowArchivesViewHisMapper() {
		return flowArchivesViewHisMapper;
	}

	public void setFlowArchivesViewHisMapper(
			FlowArchivesViewHisMapper flowArchivesViewHisMapper) {
		this.flowArchivesViewHisMapper = flowArchivesViewHisMapper;
	}
	@Transactional
    public int insert(FlowArchivesViewHis flowArchivesViewHis) {
        return this.flowArchivesViewHisMapper.insert(flowArchivesViewHis);
    }

}
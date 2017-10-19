package com.sinog2c.service.impl.flow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.flow.FlowBaseArchivesHisMapper;
import com.sinog2c.model.flow.FlowBaseArchivesHis;
import com.sinog2c.service.api.flow.FlowBaseArchivesHisService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("flowBaseArchivesHisService")
public class FlowBaseArchivesHisServiceImpl extends ServiceImplBase implements FlowBaseArchivesHisService{

	@Autowired
    private FlowBaseArchivesHisMapper flowBaseArchivesHisMapper;

    public FlowBaseArchivesHisMapper getFlowBaseArchivesHisMapper() {
		return flowBaseArchivesHisMapper;
	}

	public void setFlowBaseArchivesHisMapper(
			FlowBaseArchivesHisMapper flowBaseArchivesHisMapper) {
		this.flowBaseArchivesHisMapper = flowBaseArchivesHisMapper;
	}
	@Transactional
	public int insert(FlowBaseArchivesHis flowBaseArchivesHis) {
        return this.flowBaseArchivesHisMapper.insert(flowBaseArchivesHis);
    }

}
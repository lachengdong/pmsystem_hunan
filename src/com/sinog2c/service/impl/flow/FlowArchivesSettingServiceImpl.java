package com.sinog2c.service.impl.flow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.flow.FlowArchivesMapper;
import com.sinog2c.dao.api.flow.FlowBaseArchivesMapper;
import com.sinog2c.service.api.flow.FlowArchivesSettingService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("flowArchivesSettingService")
public class FlowArchivesSettingServiceImpl extends ServiceImplBase implements FlowArchivesSettingService{

	@Autowired
    private FlowArchivesMapper flowArchivesMapper;
	@Autowired
	private FlowBaseArchivesMapper flowBaseArchivesMapper;
	
	
	
    
}
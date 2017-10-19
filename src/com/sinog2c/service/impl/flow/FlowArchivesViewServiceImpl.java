package com.sinog2c.service.impl.flow;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.flow.FlowArchivesViewMapper;
import com.sinog2c.model.flow.FlowArchivesView;
import com.sinog2c.service.api.flow.FlowArchivesViewService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("flowArchivesViewService")
public class FlowArchivesViewServiceImpl extends ServiceImplBase implements FlowArchivesViewService{

	@Autowired
    private FlowArchivesViewMapper flowArchivesViewMapper;

	public FlowArchivesViewMapper getFlowArchivesViewMapper() {
		return flowArchivesViewMapper;
	}

	public void setFlowArchivesViewMapper(
			FlowArchivesViewMapper flowArchivesViewMapper) {
		this.flowArchivesViewMapper = flowArchivesViewMapper;
	}
	@Transactional
    public int insert(FlowArchivesView flowArchivesView) {
        return this.flowArchivesViewMapper.insert(flowArchivesView);
    }
	@Transactional
	public int update(Map<String,Object> map) {
		return this.flowArchivesViewMapper.update(map);
	}
	
	public List<FlowArchivesView> selectAllBySuid(String orgid){
		return this.flowArchivesViewMapper.selectAllBySuid(orgid);
	}
	@Transactional
	public int delete(Map<String, Object> map) {
		return this.flowArchivesViewMapper.delete(map);
	}
}
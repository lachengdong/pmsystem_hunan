package com.sinog2c.service.impl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.flow.FlowDeliverHisMapper;
import com.sinog2c.model.flow.FlowDeliverHis;
import com.sinog2c.service.api.flow.FlowDeliverHisService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("flowDeliverHisService")
public class FlowDeliverHisServiceImpl extends ServiceImplBase implements FlowDeliverHisService{

	@Autowired
    private FlowDeliverHisMapper flowDeliverHisMapper;

	public FlowDeliverHisMapper getFlowDeliverHisMapper() {
		return flowDeliverHisMapper;
	}

	public void setFlowDeliverHisMapper(FlowDeliverHisMapper flowDeliverHisMapper) {
		this.flowDeliverHisMapper = flowDeliverHisMapper;
	}

	public int countAll() {
        return this.flowDeliverHisMapper.countAll();
    }

    public FlowDeliverHis findById(String id) {
        return this.flowDeliverHisMapper.findById(id);
    }
    @Transactional
    public int insert(FlowDeliverHis flowDeliverHis) {
        return this.flowDeliverHisMapper.insert(flowDeliverHis);
    }
    @Transactional
    public int delete(String id) {
    	return this.flowDeliverHisMapper.delete(id);
    }

    public List<FlowDeliverHis> selectAll() {
        return this.flowDeliverHisMapper.selectAll();
    }
    
}
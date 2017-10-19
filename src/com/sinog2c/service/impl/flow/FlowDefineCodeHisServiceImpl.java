package com.sinog2c.service.impl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.flow.FlowDefineCodeHisMapper;
import com.sinog2c.model.flow.FlowDefineCodeHis;
import com.sinog2c.service.api.flow.FlowDefineCodeHisService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("flowDefineCodeHisService")
public class FlowDefineCodeHisServiceImpl extends ServiceImplBase implements FlowDefineCodeHisService{

	@Autowired
    private FlowDefineCodeHisMapper flowDefineCodeHisMapper;

	public FlowDefineCodeHisMapper getFlowDefineCodeHisMapper() {
		return flowDefineCodeHisMapper;
	}

	public void setFlowDefineCodeHisMapper(
			FlowDefineCodeHisMapper flowDefineCodeHisMapper) {
		this.flowDefineCodeHisMapper = flowDefineCodeHisMapper;
	}

	public int countAll() {
        return this.flowDefineCodeHisMapper.countAll();
    }

    public FlowDefineCodeHis findById(String id) {
        return this.flowDefineCodeHisMapper.findById(id);
    }
    @Transactional
    public int insert(FlowDefineCodeHis flowDefineCodeHis) {
        return this.flowDefineCodeHisMapper.insert(flowDefineCodeHis);
    }
    @Transactional
    public int delete(String id) {
    	return this.flowDefineCodeHisMapper.delete(id);
    }

    public List<FlowDefineCodeHis> selectAll() {
        return this.flowDefineCodeHisMapper.selectAll();
    }
    
}
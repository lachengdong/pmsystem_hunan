package com.sinog2c.service.impl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.flow.FlowDefineCodeMapper;
import com.sinog2c.model.flow.FlowDefineCode;
import com.sinog2c.service.api.flow.FlowDefineCodeService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("flowDefineCodeService")
public class FlowDefineCodeServiceImpl extends ServiceImplBase implements FlowDefineCodeService{

	@Autowired
    private FlowDefineCodeMapper flowDefineCodeMapper;

    public FlowDefineCodeMapper getFlowDefineCodeMapper() {
		return flowDefineCodeMapper;
	}

	public void setFlowDefineCodeMapper(FlowDefineCodeMapper flowDefineCodeMapper) {
		this.flowDefineCodeMapper = flowDefineCodeMapper;
	}

	public FlowDefineCode findById(String id) {
        return this.flowDefineCodeMapper.findById(id);
    }
	@Transactional
    public int insert(FlowDefineCode flowDefineCode) {
        return this.flowDefineCodeMapper.insert(flowDefineCode);
    }
	@Transactional
    public int delete(String id) {
    	return this.flowDefineCodeMapper.delete(id);
    }

    public List<FlowDefineCode> selectAll() {
        return this.flowDefineCodeMapper.selectAll();
    }

	public int countAll() {
		return this.flowDefineCodeMapper.countAll();
	}
    
}
package com.sinog2c.service.impl.flow;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.flow.FlowMapper;
import com.sinog2c.model.flow.Flow;
import com.sinog2c.service.api.flow.FlowService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("flowService")
public class FlowServiceImpl extends ServiceImplBase implements FlowService{

	@Autowired
    private FlowMapper flowMapper;

	public FlowMapper getFlowMapper() {
		return flowMapper;
	}

	public void setFlowMapper(FlowMapper flowMapper) {
		this.flowMapper = flowMapper;
	}

	public int countAll() {
        return this.flowMapper.countAll();
    }
	
	public  String getFlowid(String orgid,String departid,String flowdefid,int isflow){
    	return this.flowMapper.getFlowid(orgid,departid,flowdefid,isflow);
    }
  
    public Flow findById(String id) {
        return this.flowMapper.findById(id);
    }
    @Transactional
    public int insert(Flow flow) {
        return this.flowMapper.insert(flow);
    }
    @Transactional
    public int delete(String id) {
    	return this.flowMapper.delete(id);
    }

    public List<Flow> selectAll() {
        return this.flowMapper.selectAll();
    }

    @Transactional
	public int updateFlowState(String state, String applyid, String flowdraftid) {
		return this.flowMapper.updateFlowState(state, flowdraftid);
	}

    @Transactional
	public int updateByFlowdraftid(Flow flow) {
		return this.flowMapper.updateByFlowdraftid(flow);
	}
    public Flow getOpinion(Flow flow) {
    	return this.flowMapper.getOpinion(flow);
    }

	@Override
	public void updateZZByFlowSn(Flow flow) {
		 flowMapper.updateZZByFlowSn(flow);
	}

	@Override
	public int validatecpb(String flowdraftid) {
		return flowMapper.validatecpb(flowdraftid);
	}
}
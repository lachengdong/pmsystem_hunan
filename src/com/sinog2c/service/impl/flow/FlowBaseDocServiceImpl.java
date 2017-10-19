package com.sinog2c.service.impl.flow;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sinog2c.dao.api.flow.FlowBaseDocMapper;
import com.sinog2c.model.flow.FlowBaseDoc;
import com.sinog2c.service.api.flow.FlowBaseDocService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("flowBaseDocService")
public class FlowBaseDocServiceImpl extends ServiceImplBase implements FlowBaseDocService{

	@Autowired
    private FlowBaseDocMapper flowBaseDocMapper;

	public FlowBaseDocMapper getFlowBaseDocMapper() {
		return flowBaseDocMapper;
	}

	public void setFlowBaseDocMapper(FlowBaseDocMapper flowBaseDocMapper) {
		this.flowBaseDocMapper = flowBaseDocMapper;
	}

	public int countAll() {
        return this.flowBaseDocMapper.countAll();
    }

    public FlowBaseDoc findById(String id) {
        return this.flowBaseDocMapper.findById(id);
    }
//    @Transactional
//    public int insert(FlowBaseDoc flowBaseDoc) {
//        return this.flowBaseDocMapper.insert(flowBaseDoc);
//    }
    @Transactional
    public int delete(String id) {
    	return this.flowBaseDocMapper.delete(id);
    }

    public List<FlowBaseDoc> selectAll() {
        return this.flowBaseDocMapper.selectAll();
    }
    @Transactional
    public int updateByCondition(FlowBaseDoc flowBaseDoc){
    	return this.flowBaseDocMapper.updateByCondition(flowBaseDoc);
    }
    
    public FlowBaseDoc findLastDocByflowdraftid(Map<String,Object> map){
    	return this.flowBaseDocMapper.findLastDocByflowdraftid(map);
    }
    

}
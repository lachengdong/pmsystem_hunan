package com.sinog2c.service.impl.flow;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.flow.FlowBaseArchivesMapper;
import com.sinog2c.model.flow.FlowBaseArchives;
import com.sinog2c.service.api.flow.FlowBaseArchivesService;
import com.sinog2c.service.impl.base.ServiceImplBase;

@Service("flowBaseArchivesService")
public class FlowBaseArchivesServiceImpl extends ServiceImplBase implements FlowBaseArchivesService{

	@Autowired
    private FlowBaseArchivesMapper flowBaseArchivesMapper;

    public FlowBaseArchivesMapper getFlowBaseArchivesMapper() {
		return flowBaseArchivesMapper;
	}

	public void setFlowBaseArchivesMapper(
			FlowBaseArchivesMapper flowBaseArchivesMapper) {
		this.flowBaseArchivesMapper = flowBaseArchivesMapper;
	}
	@Transactional
	public int insert(FlowBaseArchives flowBaseArchives) {
        return this.flowBaseArchivesMapper.insert(flowBaseArchives);
    }
	
	public int countAll(Map<String,Object> map) {
		return this.flowBaseArchivesMapper.countAll(map);
	}
	
	public int countAllForSD(Map<String,Object> map) {
		return this.flowBaseArchivesMapper.countAllForSD(map);
	}
	
	
	@Transactional
	public int deleteById(String archiveid) {
		return this.flowBaseArchivesMapper.deleteById(archiveid);
	}

	public int countAllByCondition(Map<String,Object> map) {
		return this.flowBaseArchivesMapper.countAllByCondition(map);
	}

	public List<FlowBaseArchives> selectAllByCondition(Map<String,Object> map) {
		return this.flowBaseArchivesMapper.selectAllByCondition(map);
	}

	public List<FlowBaseArchives> selectAllByPage(Map<String,Object> map){
		return this.flowBaseArchivesMapper.selectAllByPage(map);
	}

	public List<FlowBaseArchives> selectAllByPageForSD(Map<String,Object> map){
		return this.flowBaseArchivesMapper.selectAllByPageForSD(map);
	}
	
	@Override
	public int countAllShengju(Map<String, Object> map) {
		return flowBaseArchivesMapper.countAllShengju(map);
	}

	@Override
	public List<FlowBaseArchives> selectAllByPageShengju(Map<String, Object> map) {
		return flowBaseArchivesMapper.selectAllByPageShengju(map);
	}

	@Override
	public int insertCaseInfo(Map<Object, Object> map) {
		// TODO Auto-generated method stub
		return this.flowBaseArchivesMapper.insertCaseInfo(map);
	}
	
}
package com.sinog2c.service.impl.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.flow.FlowArchivesCodeMapper;
import com.sinog2c.model.flow.FlowArchivesCode;
import com.sinog2c.service.api.flow.FlowArchivesCodeService;
import com.sinog2c.service.impl.base.ServiceImplBase;


/**
 * @describe:操作幅度参照信息的service的实现类
 * @author YangZR
 * @date 2014-7-25 上午09:30:18
 */
@Service("flowArchivesCodeService")
public class FlowArchivesCodeServiceImpl extends ServiceImplBase implements FlowArchivesCodeService{
	
	@Autowired
	private FlowArchivesCodeMapper flowArchivesCodeMapper;

	public FlowArchivesCodeMapper getFlowArchivesCodeMapper() {
		return flowArchivesCodeMapper;
	}

	public void setFlowArchivesCodeMapper(
			FlowArchivesCodeMapper flowArchivesCodeMapper) {
		this.flowArchivesCodeMapper = flowArchivesCodeMapper;
	}

	@Override
	public int insert(FlowArchivesCode record) {
		return flowArchivesCodeMapper.insert(record);
	}

	@Override
	public FlowArchivesCode selectByPrimaryKey(String codeid, String departid) {
		return flowArchivesCodeMapper.selectByPrimaryKey(codeid, departid);
	}

	@Override
	public List<FlowArchivesCode> selectAllByDepid(Map map) {
		return flowArchivesCodeMapper.selectAllByDepid(map);
	}

	@Override
	public FlowArchivesCode selectByTempid(String tempid, String departid) {
		return flowArchivesCodeMapper.selectByTempid(tempid, departid);
	}

	@Override
	public List<FlowArchivesCode> selectAllByDepidByType(Map map) {
		return flowArchivesCodeMapper.selectAllByDepidByType(map);
	}

	@Override
	public List<FlowArchivesCode> selectAllByMap(Map map) {
		return flowArchivesCodeMapper.selectAllByMap(map);
	}

	@Override
	public int selectAllByMapCount(Map map) {
		return flowArchivesCodeMapper.selectAllByMapCount(map);
	}

	@Override
	public int findCodeByMap(Map map) {
		return flowArchivesCodeMapper.findCodeByMap(map);
	}

	@Override
	public int deleteCodeByMap(Map map) {
		return flowArchivesCodeMapper.deleteCodeByMap(map);
	}

	@Override
	public int updateCode(FlowArchivesCode record) {
		return flowArchivesCodeMapper.updateByPrimaryKey(record);
	}

	
}

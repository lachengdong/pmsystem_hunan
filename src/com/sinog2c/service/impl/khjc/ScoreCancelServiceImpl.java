package com.sinog2c.service.impl.khjc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.khjc.TkhRoutineexaminationMapper;
import com.sinog2c.service.api.khjc.ScoreCancelService;


@Service("ScoreCancelService")
public class ScoreCancelServiceImpl implements ScoreCancelService{

	@Autowired
	private TkhRoutineexaminationMapper tkhRoutineexaminationMapper;
	
	@Override
	public int countAllByCondition(Map<String, Object> map) {
		return tkhRoutineexaminationMapper.countAllByCondition(map);
	}


	@Override
	public List<Map<String, Object>> getBasicInfoList(Map<String, Object> map) {
		return tkhRoutineexaminationMapper.getBasicInfoList(map);
	}


	@Override
	public int countCondition(Map<String, Object> map) {
		return tkhRoutineexaminationMapper.countCondition(map);
	}


	@Override
	public List<Map<String, Object>> getInfoList(Map<String, Object> map) {
		return tkhRoutineexaminationMapper.getInfoList(map);
	}


	@Override
	public int getCancelReason(Map<String, Object> map) {
		return tkhRoutineexaminationMapper.getCancelReason(map);
	}


	@Override
	public String getCancelReasonView(Map<String, Object> map) {
		return tkhRoutineexaminationMapper.getCancelReasonView(map);
	}

}

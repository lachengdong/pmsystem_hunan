package com.sinog2c.service.impl.assessAndPunish;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.assessAndPunish.TbsflowCaseCriminalMapper;
import com.sinog2c.model.assessAndPunish.TbsflowCaseCriminal;
import com.sinog2c.service.api.assessAndPunish.TbsflowCaseCriminalService;
import com.sinog2c.util.common.MapUtil;
import com.sun.org.apache.commons.collections.MapUtils;

@Service("tbsflowCaseCriminalService")
public class TbsflowCaseCriminalServiceImpl implements TbsflowCaseCriminalService{
	
	@Resource
	private TbsflowCaseCriminalMapper tbsflowCaseCriminalMapper;
	
	@Override
	public int deleteByPrimaryKey(String crimid) {
		// TODO Auto-generated method stub
		return tbsflowCaseCriminalMapper.deleteByPrimaryKey(crimid);
	}

	@Override
	public int insert(TbsflowCaseCriminal record) {
		// TODO Auto-generated method stub
		return tbsflowCaseCriminalMapper.insert(record);
	}

	@Override
	public int insertSelective(TbsflowCaseCriminal record) {
		// TODO Auto-generated method stub
		return tbsflowCaseCriminalMapper.insertSelective(record);
	}

	@Override
	public TbsflowCaseCriminal selectByPrimaryKey(String crimid) {
		// TODO Auto-generated method stub
		return tbsflowCaseCriminalMapper.selectByPrimaryKey(crimid);
	}

	@Override
	public int updateByPrimaryKeySelective(TbsflowCaseCriminal record) {
		// TODO Auto-generated method stub
		return tbsflowCaseCriminalMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TbsflowCaseCriminal record) {
		// TODO Auto-generated method stub
		return tbsflowCaseCriminalMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Map> selectByDateAndId(Map map) {
		
		List<Map> resultMap=tbsflowCaseCriminalMapper.selectByDateAndId(map);
		if (resultMap==null) {
			return null;
		}else {
			return MapUtil.turnKeyToLowerCaseOfList(resultMap);
		}
	}

	public int getTbsflowCaseCriminalCount(Map map){
		return tbsflowCaseCriminalMapper.getTbsflowCaseCriminalCount(map);
	}
}

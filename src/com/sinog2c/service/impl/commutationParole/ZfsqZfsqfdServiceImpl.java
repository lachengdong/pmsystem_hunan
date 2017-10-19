package com.sinog2c.service.impl.commutationParole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.commutationParole.TbxfSentenceAlterationMapper;
import com.sinog2c.dao.api.commutationParole.ZfsqZfsqfdMapper;
import com.sinog2c.model.commutationParole.ZfsqZfsqfd;
import com.sinog2c.service.api.commutationParole.ZfsqZfsqfdService;

@Service("zfsqZfsqfdService")
public class ZfsqZfsqfdServiceImpl implements ZfsqZfsqfdService {
	
	@Resource
	private ZfsqZfsqfdMapper zfsqfdMapper;
	@Resource
	private TbxfSentenceAlterationMapper tbxfSentenceAlterationMapper;

	public int deleteByPrimaryKey(String crimid) {
		return zfsqfdMapper.deleteByPrimaryKey(crimid);
	}

	public int insert(ZfsqZfsqfd record) {
		return zfsqfdMapper.insert(record);
	}

	public int insertSelective(ZfsqZfsqfd record) {
		return zfsqfdMapper.insertSelective(record);
	}

	public ZfsqZfsqfd selectByPrimaryKey(String crimid) {
		return zfsqfdMapper.selectByPrimaryKey(crimid);
	}

	public int updateByPrimaryKey(ZfsqZfsqfd record) {
		return zfsqfdMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(ZfsqZfsqfd record) {
		return zfsqfdMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int getCount(Map<String,Object> map) {
		return zfsqfdMapper.getCount(map);
	}

	@Override
	public List<Map<String, Object>> getSentenceAlterationList(Map<String,Object> map) {
		return zfsqfdMapper.getSentenceAlterationList(map);
	}

	@Override
	public int insertByMap(Map<String, Object> map) {
		return zfsqfdMapper.insertByMap(map);
	}

	@Override
	public int updateByMap(Map<String, Object> map) {
		return zfsqfdMapper.updateByMap(map);
	}

	@Override
	public ZfsqZfsqfd selectByCrimidAndBatchId(Map map) {
		return zfsqfdMapper.selectByCrimidAndBatchId(map);
	}
	
}

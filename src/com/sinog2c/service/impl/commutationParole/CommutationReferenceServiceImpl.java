package com.sinog2c.service.impl.commutationParole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinog2c.dao.api.commutationParole.TbxfCommutationReferenceMapper;
import com.sinog2c.model.commutationParole.TbxfCommutationReference;
import com.sinog2c.service.api.commutationParole.CommutationReferenceService;


@Service("commutationReferenceService")
public class CommutationReferenceServiceImpl implements CommutationReferenceService {
	
	@Resource
	private TbxfCommutationReferenceMapper tbxfCommutationReferenceMapper;

	@Override
	public List<HashMap> getCommutationReferenceList(String punid, String key,
			String sortField, String sortOrder, int start, int end) {
		// TODO Auto-generated method stub
		return tbxfCommutationReferenceMapper.getCommutationReferenceList(punid, key, sortField, sortOrder, start, end);
	}

	@Override
	public int getCount(String punid, String key) {
		// TODO Auto-generated method stub
		return tbxfCommutationReferenceMapper.getCount(punid, key);
	}

	@Override
	public int insert(TbxfCommutationReference record) {
		// TODO Auto-generated method stub
		return tbxfCommutationReferenceMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKey(TbxfCommutationReference record) {
		// TODO Auto-generated method stub
		return tbxfCommutationReferenceMapper.updateByPrimaryKey(record);
	}

	@Override
	public TbxfCommutationReference selectByPrimaryKey(Integer refid) {
		// TODO Auto-generated method stub
		return tbxfCommutationReferenceMapper.selectByPrimaryKey(refid);
	}

	@Override
	@Transactional
	public void deleteCommutationReference(Integer refid) {
		// TODO Auto-generated method stub
		this.tbxfCommutationReferenceMapper.deleteMergereferenceByRefid(refid);
		this.tbxfCommutationReferenceMapper.deleteByPrimaryKey(refid);
	}
	
	@Override
	public int insertAuto(TbxfCommutationReference record) {
		return tbxfCommutationReferenceMapper.insertAuto(record);
	}
	
	@Override
	public List<HashMap> getWideAndThinschemeList(Map map) {
		return tbxfCommutationReferenceMapper.getWideAndThinschemeList(map);
	}
}

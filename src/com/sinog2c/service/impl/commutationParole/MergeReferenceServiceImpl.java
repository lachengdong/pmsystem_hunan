package com.sinog2c.service.impl.commutationParole;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.commutationParole.TbxfMergeReferenceMapper;
import com.sinog2c.model.commutationParole.TbxfMergeReference;
import com.sinog2c.service.api.commutationParole.MergeReferenceService;


@Service("mergeReferenceService")
public class MergeReferenceServiceImpl implements MergeReferenceService {
	
	@Resource
	private TbxfMergeReferenceMapper tbxfMergeReferenceMapper;

	@Override
	public int deleteMergeReference(int mergeid) {
		// TODO Auto-generated method stub
		return tbxfMergeReferenceMapper.deleteByPrimaryKey(mergeid);
	}

	@Override
	public int getCount(String refid) {
		// TODO Auto-generated method stub
		return tbxfMergeReferenceMapper.getCount(refid);
	}

	@Override
	public TbxfMergeReference getMergeReferenceById(Integer mergeid) {
		// TODO Auto-generated method stub
		return tbxfMergeReferenceMapper.selectByPrimaryKey(mergeid);
	}

	@Override
	public List<HashMap> getMergeReferenceList(String refid, String sortField,
			String sortOrder, int start, int end) {
		// TODO Auto-generated method stub
		return tbxfMergeReferenceMapper.getCommutationReferenceList(refid, sortField, sortOrder, start, end);
	}

	@Override
	public int insertMergeReference(TbxfMergeReference record) {
		// TODO Auto-generated method stub
		return tbxfMergeReferenceMapper.insert(record);
	}

	@Override
	public int updateMergeReference(TbxfMergeReference record) {
		// TODO Auto-generated method stub
		return tbxfMergeReferenceMapper.updateByPrimaryKey(record);
	}


	@Override
	public HashMap getRewardName(Integer rewardid) {
		// TODO Auto-generated method stub
		return tbxfMergeReferenceMapper.getRewardName(rewardid);
	}
	
	@Override
	public int insertMergeReferenceAuto(TbxfMergeReference record) {
		// TODO Auto-generated method stub
		return tbxfMergeReferenceMapper.insertSelective(record);
	}
	
}

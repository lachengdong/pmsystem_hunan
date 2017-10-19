package com.sinog2c.service.api.commutationParole;

import java.util.HashMap;
import java.util.List;

import com.sinog2c.model.commutationParole.TbxfMergeReference;



public interface MergeReferenceService {
	
	/**
	 * 查询符合条件的数据总条数
	 * @param key
	 * @return
	 */
	public int getCount(String refid);
	
	/**
	 * 分页显示列表数据
	 * @param key
	 * @param start 
	 * @param end
	 * @return
	 */
	public List<HashMap> getMergeReferenceList(String refid, String sortField, String sortOrder, int start, int end );
	
	/**
	 * 删除数据
	 * @return
	 */
	public int deleteMergeReference(int mergeid);
	
	/**
	 * 添加数据
	 * @param record
	 * @return
	 */
	public int insertMergeReference(TbxfMergeReference record);
	
	/**
	 * 修改数据
	 * @param record
	 * @return
	 */
	public int updateMergeReference(TbxfMergeReference record);
	
	/**
	 * 根据id获取数据
	 * @param punid
	 * @return
	 */
	public TbxfMergeReference getMergeReferenceById(Integer mergeid);
	
	/**
	 * 根据id获取奖励类型名称
	 * @param rewardid
	 * @return
	 */
	public HashMap getRewardName(Integer rewardid);
	
	public int insertMergeReferenceAuto(TbxfMergeReference record);
}

package com.sinog2c.dao.api.commutationParole;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.commutationParole.TbxfMergeReference;

public interface TbxfMergeReferenceMapper {
    int deleteByPrimaryKey(Integer mergeid);

    int insert(TbxfMergeReference record);

    int insertSelective(TbxfMergeReference record);

    TbxfMergeReference selectByPrimaryKey(Integer mergeid);

    int updateByPrimaryKeySelective(TbxfMergeReference record);

    int updateByPrimaryKey(TbxfMergeReference record);
    
    public int getCount(@Param("refid")String refid);
	
	public List<HashMap> getCommutationReferenceList(@Param("refid")String refid, @Param("sortField")String sortField, @Param("sortOrder")String sortOrder, @Param("start")int start, @Param("end")int end);
	
	HashMap getRewardName(@Param("rewardid")Integer rewardid);
	
	List<TbxfMergeReference> getMergeListData(@Param("departid")String departid);
}
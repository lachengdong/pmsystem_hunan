package com.sinog2c.service.api.commutationParole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinog2c.model.commutationParole.TbxfCommutationReference;



public interface CommutationReferenceService {
	
	public int getCount(String punid, String key);
	
	public List<HashMap> getCommutationReferenceList(String punid, String key, String sortField, String sortOrder, int start, int end);
	
	public int insert(TbxfCommutationReference record);
	
	public int updateByPrimaryKey(TbxfCommutationReference record);
	
	public TbxfCommutationReference selectByPrimaryKey(Integer refid);
	
	public void deleteCommutationReference(Integer refid);
	
	public int insertAuto(TbxfCommutationReference record);
	
	public List<HashMap> getWideAndThinschemeList(Map map);
}

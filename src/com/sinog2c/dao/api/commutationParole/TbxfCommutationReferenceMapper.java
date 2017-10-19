package com.sinog2c.dao.api.commutationParole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.commutationParole.TbxfCommutationReference;

public interface TbxfCommutationReferenceMapper {
    int deleteByPrimaryKey(Integer refid);

    int insert(TbxfCommutationReference record);

    int insertSelective(TbxfCommutationReference record);

    TbxfCommutationReference selectByPrimaryKey(Integer refid);

    int updateByPrimaryKeySelective(TbxfCommutationReference record);

    int updateByPrimaryKey(TbxfCommutationReference record);
    
    public int getCount(@Param("punid")String punid, @Param("key")String key);
	
	public List<HashMap> getCommutationReferenceList(@Param("punid")String punid, @Param("key")String key, @Param("sortField")String sortField, @Param("sortOrder")String sortOrder, @Param("start")int start, @Param("end")int end);
	
	public List<HashMap<String, Object>> commutationReferenceList(@Param("departid")String departid);
	
	public List<HashMap<String, Object>> getMergereferenceList(@Param("departid")String departid, @Param("refid")int refid);
	
	public List<TbxfCommutationReference> getReferenceList(@Param("departid")String departid,@Param("sanlei")int sanlei);
	
	public int deleteMergereferenceByRefid(@Param("refid")Integer refid);
	
	public List<HashMap<Object, Object>> getAmplitudeData(@Param("crimid")String crimid);
	
	public List<TbxfCommutationReference> getRefids(Map<String, Object> map);
	
	public void batchUpdate(List<Map> list);
	
	public void jiashibatchUpdate(List<Map> list);
	
	public void batchUpdateSx(List<Map> list);
	
	public void batchUpdateSuggestopion(List<Map<String,Object>> list);
	
	public List<HashMap<String,Object>> getMergeandreferenceList(Map<String,Object> map);
	
	public int getMergeandreferenceCount(Map<String,Object> map);
	
	public int getSanleiCount(@Param("departid")String departid);
	
	public List<TbxfCommutationReference> getCommutationReferenceListData(@Param("departid")String departid);
	
	int insertAuto(TbxfCommutationReference record);
	
	List<Map> getScreeingByBatchidAndDepartid(Map map);
	
	public List<HashMap> getWideAndThinschemeList(Map map);
	
	public List<HashMap<String, Object>> commutationWideAndThinList(@Param("departid")String departid);
	
	public List<HashMap<String,Object>> getReferenceListBydepartid(Map<String,Object> map);
	
	public List<HashMap<String,Object>> getMergeReferenceList(Map<String,Object> map);
	
	
	
	
	public void qualifierUpdateScreening(List<Map<String,Object>> list);
}
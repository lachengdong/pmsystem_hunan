package com.sinog2c.service.api.criminalexam;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.criminalexam.TbyzCheckForWork;

public interface SxexamineCheckForWorkService {

	public List<TbyzCheckForWork> getWorkList(int pageIndex, int pageSize);

	public int getWorkCount(Map map);

	public String workSetAddOrUpdate(Map map);

	public String workAddOrUpdate(Map map);

	int updateSelective(TbyzCheckForWork record);

	List<Map> getInfobyDepartAndMonth(String departid, String yeardate);
	
	List<Map>  getListMap(Map map);

	public List<TbyzCheckForWork> workSetSelect(Map map);

	Map getList(String orgid, String yeardate);
	
	String workSetDanGeUpdate(Map map);
	
	String workSetDanGeAdd(Map map);
	
	String insertSelective(Map map);
	
	int updateSelective(Map map);
	
	int updateWorkData(Map map);
	
	int selectByCrimid(Map map);
	
	int insertWorkData(Map map);
	
}

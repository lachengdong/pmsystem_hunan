package com.sinog2c.service.api.commutationParole;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.commutationParole.TbxfCommuteParoleBatch;
import com.sinog2c.model.system.SystemUser;

public interface CommuteParoleBatchService {
	
	
	public int getCommuteParoleBatchCount(String departid, String key);
	
	public List<TbxfCommuteParoleBatch> getCommuteParoleBatchList(String departid, String key, String sortField, String sortOrder, int start, int end );
	
	public int deleteCommuteParoleBatch(Long batchid);
	
	public int insertCommuteParoleBatch(TbxfCommuteParoleBatch record);
	
	public int updateCommuteParoleBatch(TbxfCommuteParoleBatch record);
	
	public TbxfCommuteParoleBatch getCommuteParoleBatchById(Integer batchid);
	
	public Map<String,Object> getCommuteParoleBatchInfo(Map<String,Object> orgidMap);
	//public Map getMaxData(Map map);
	
	public void callStoredProcedure(String departid);
	public void callCallBackProcedure(String departid);
	
	int selectMaxBatch(String departId,String year);
	
	public void updateRewardDate(String departid);
	
	public void updateInterval(String departid);
	
	public void updateNowpunishment(String departid);
	
	public int checkUnfinishedCase(String departid);
	
	public String shengChengData(HttpServletRequest request,SystemUser user);
	
	public void clearMaxBatch(Map<String,Object> map);
	public void updateMaxBatch(Map<String,Object> map);
	public void updateCrimeType(Map<String,Object> map);
	
	public void callCPScreeingDataProcedure(String departid, String id, String userid);
	
	public void initData4Qualification(String departid);
	
	public void generateQualificationList(String departid);
	
	
	public void dealKaoHeJianChen4HuNan(String departid);
	
	public String correctKaoHeDataInfo(Map<String,Object> paramap);
	
}

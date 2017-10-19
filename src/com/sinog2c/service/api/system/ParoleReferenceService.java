package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;

/**
 * @describe:
 * @author YangZR
 * @date 2014-7-24 下午07:07:48
 */
public interface ParoleReferenceService {
	
	public Integer countSearchSql(String key);
	
	public List<Map> searchByPageSql(int pageIndex, int pageSize,String key);
	
	public Integer countAllSql();
	
	public List<Map> searchAllByPageSql(int pageIndex, int pageSize);
	
}

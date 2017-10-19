package com.sinog2c.service.api.system;

import java.util.List;
import java.util.Map;
import com.sinog2c.model.system.SystemLog;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;

public interface SystemLogService {

	/**
	 * 使用sequence自增ID方式新增
	 * @param log 新增的对象
	 * @param operator 当前用户
	 * @return
	 */
	public int add(SystemLog log, SystemUser operator);
	public int add(SystemLog log, SystemUser operator, JSONMessage message);
	public int addLog(Map<String, Object> params, SystemUser operator);
    /**
     * 搜索,获取数量
     * @param logtype
     * @param key
     * @return
     */
    public int countBySearch(String logtype, String key,String starttime,String endtime);
    /**
     * 搜索, 分页
     * @param pageIndex
     * @param pageSize
     * @param logtype
     * @param key
     * @return
     */
    public List<SystemLog> search(int pageIndex, int pageSize, String logtype, String key, 
    		String sortField, String sortOrder,String starttime,String endtime);
    
    /**
     * 根据ID获取日志详情
     * @param logid
     * @return
     */
	public SystemLog getByLogId(int logid);
	
	public int removelog(String starttime,String endtime);
}

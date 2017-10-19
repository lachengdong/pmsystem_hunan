package com.sinog2c.dao.api.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sinog2c.model.system.SystemLog;

@Repository("systemLogMapper")
public interface SystemLogMapper {
    /**
     * 插入指定数据
     * @param record
     * @return
     */
    int insertSelective(SystemLog record);  

    /**
     * 根据关键词,计算数量
     * @param logtype 日志类型
     * @param key 关键字,如果没有,请填入 ""
     * @return
     */
    int countBySearch(@Param("logtype")String logtype,@Param("key")String key,
    		@Param("starttime")String starttime,@Param("endtime")String endtime);  
    /** 
     * 搜索,分页
     * @param start
     * @param end
     * @param key
     * @return
     */
    List<SystemLog>  search(@Param("start")int start,@Param("end")int end,@Param("logtype")String logtype,
			@Param("key")String key,@Param("sortField")String sortField,@Param("sortOrder")String sortOrder,
			@Param("starttime")String starttime,@Param("endtime")String endtime);
    /**
     * 根据ID定位
     * @param logid
     * @return
     */
    public SystemLog getByLogId(
			@Param("logid")
    		int logid
    		);
    int removelog(@Param("starttime")String starttime,@Param("endtime")String endtime);
}
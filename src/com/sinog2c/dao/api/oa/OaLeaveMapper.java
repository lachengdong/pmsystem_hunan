package com.sinog2c.dao.api.oa;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.oa.OaLeave;

/**
 * @author 肖岩
 * date 2015/08/31
 * 请假单dao接口
 * */
public interface OaLeaveMapper {
	/**
	 * @author 肖岩
     * date 2015/08/31
	 * 插入请假单信息方法借口
	 * record 请假单实体
	 * */
    int insertSelective(OaLeave record);
	/**
	 * @author 肖岩
     * date 2015/08/31
	 * 查询请假单信息数量方法借口
	 * map 请求参数
	 * */
	int getCountLeaveByCondition(Map<String, Object> map);

	/**
	 * @author 肖岩
     * date 2015/08/31
	 * 查询请假单信息列表方法借口
	 * map 请求参数
	 * */
	List<OaLeave> getLeaveListByCondition(Map<String, Object> map);
	/**
	 * @author 肖岩
     * date 2015/09/01
	 * 按id查看请假单信息方法接口
	 * uuid uuid
	 * */
	List<OaLeave> getLeaveListById(@Param("uuid")String uuid);
}
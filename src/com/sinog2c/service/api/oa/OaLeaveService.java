package com.sinog2c.service.api.oa;

import java.util.Map;

import com.sinog2c.model.JSONMessage;
import com.sinog2c.model.oa.OaLeave;
/**
 * @author 肖岩
 * date 2015/08/31
 * function 请假单的业务处理接口
 * */
public interface OaLeaveService  {
	/**
	 * @author 肖岩
     * date 2015/08/31
	 * 新增方法，在流程执行完成后进行保存
	 * oasickleave 请假申请实体类
	 * */
	int save(OaLeave oaLeave);
	
	/**
	 * @author 肖岩
     * date 2015/08/31
	 * 查询请假单信息列表方法接口
	 * map 请求参数
	 * */
	JSONMessage<OaLeave> getLeaveListInfo(
			Map<String, Object> map);

	/**
	 * @author 肖岩
     * date 2015/09s/01
	 * 按id查看请假单信息方法接口
	 * uuid uuid
	 * */
	@SuppressWarnings("rawtypes")
	Map getLeaveInfo(String uuid);

	OaLeave getLeaveInfoById(String uuids);
}

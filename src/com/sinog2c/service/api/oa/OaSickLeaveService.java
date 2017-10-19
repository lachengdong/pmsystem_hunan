package com.sinog2c.service.api.oa;

import com.sinog2c.model.oa.OaSickLeave;
/**
 * @author 肖岩
 * date 2015/08/31
 * function 销假单的业务处理接口
 * */
public interface OaSickLeaveService  {
	/**
	 * @author 肖岩
     * date 2015/08/31
	 * 插入销假单信息方法接口
	 * record 销假单实体
	 * */
    int save(OaSickLeave record);
}

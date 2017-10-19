package com.sinog2c.service.api.oa;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sinog2c.model.oa.OaAppReg;
/**
 * @author 肖岩
 * date 2015/09/07
 * function 应聘人员登记表的业务处理接口
 * */
public interface OaAppRegService  {
	/**
	 * @author 肖岩
     * date 2015/08/31
	 * 新增方法，在流程执行完成后进行保存
	 * OaAppReg 请假申请实体类
	 * */
	int save(OaAppReg oaAppReg);
	
	/**
	 * @author 肖岩
     * date 2015/08/31
	 * 查询登记表方法接口
	 * request 请求参数
	 * */
	@SuppressWarnings("rawtypes")
	List getAppRegListInfo(HttpServletRequest request);
	/**
	 * @author 肖岩
     * date 2015/09/09
	 * 查询登记表方法接口
	 * request 请求参数
	 * */
	int getCount(HttpServletRequest request);
	/**
	 * @author 肖岩
     * date 2015/09/09
     * 查询登记表详细信息方法接口
	 * uuid uuid
	 * */
	OaAppReg getAppRegInfoByUuid(String uuid);
	/**
	 * @author 肖岩
     * date 2015/09/09
     * 查询登记表删除方法接口
	 * uuid uuid
	 * */
	int deleteAppReg(String uuid);
	/**
	 * @author 肖岩
     * date 2015/09/09
     * 查询登记表更新方法接口
	 * oaappreg 登记表实体类
	 * */
	int update(OaAppReg oaappreg);
}

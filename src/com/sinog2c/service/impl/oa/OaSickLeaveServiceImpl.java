package com.sinog2c.service.impl.oa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinog2c.dao.api.oa.OaSickLeaveMapper;
import com.sinog2c.model.oa.OaSickLeave;
import com.sinog2c.service.api.oa.OaSickLeaveService;
/**
 * @author 肖岩
 * date 2015/08/31
 * function 销假单的业务处理实现类
 * */
@Service("oaSickLeaveService")
public class OaSickLeaveServiceImpl implements OaSickLeaveService{
	@Autowired
	private OaSickLeaveMapper oaSickLeaveMapper;
	/**
	 * @author 肖岩
     * date 2015/08/31
	 * 新增方法，在流程执行完成后进行保存
	 * oasickleave 销假申请实体类
	 * */
	@Override
	public int save(OaSickLeave oasickleave){
		String uuid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		oasickleave.setUuid(uuid);
		int i = oaSickLeaveMapper.insertSelective(oasickleave);
		return i;
	}
}

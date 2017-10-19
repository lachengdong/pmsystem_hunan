package com.sinog2c.service.api.khjc;

import java.util.Map;

public interface TbxfCommutationService {
	
	/**
     * 获取减刑假释最大案件号
     * @return
     */
	public int getMaxCommuteCaseNo(Map map);
	
	/**
	 * 保存一条主业务表的减刑假释记录
	 * @param map
	 * @return
	 */
	public int saveTbxfCommutationSensitive(Map map);
	
}

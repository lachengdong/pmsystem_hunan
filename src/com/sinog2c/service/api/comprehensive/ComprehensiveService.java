package com.sinog2c.service.api.comprehensive;

import java.util.List;
import java.util.Map;
/**
 * 该接口提供了一系列综合查询的方法
 * @author sun
 *
 */
public interface ComprehensiveService {
	/**
	 * 通过输入服刑人员的描述信息作为检索所有具有这一条件特征的服刑人员
	 * @param map 查询参数
	 * @return
	 */
public 	List<Map> getAllCriminalByDescription(Map map);
}

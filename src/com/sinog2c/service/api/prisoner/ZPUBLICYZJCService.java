package com.sinog2c.service.api.prisoner;

import java.util.List;
import java.util.Map;
import com.sinog2c.model.prisoner.ZPUBLICYZJC;
public interface ZPUBLICYZJCService {
	String selectYZJCMaxidByCrimid();
	int insertSelective(ZPUBLICYZJC zPUBLICYZJC);
	List findByYZJCDetail(Map map);
	int findByYZJCDetailCount(Map map);
	int deleteByPrimaryKey(String id);
	int updateByPrimaryKey(ZPUBLICYZJC zPUBLICYZJC);
	/**
	 * 获取服刑人员的狱政奖惩的信息总数
	 * @param crimid
	 * @return
	 */
	int findZPUBLICYZJCCount(Map crimid);
	
	/**
	 * 根据查询条件获取当前登录的服刑人员的奖惩信息列表
	 * @param map
	 * @return
	 */
	List<Map> getZPUBLICYZJClist(Map map);
}

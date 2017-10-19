package com.sinog2c.dao.api.prisoner;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.prisoner.ZPUBLICYZJC;
import com.sinog2c.model.prisoner.ZPUBLICYZJCKey;

public interface ZPUBLICYZJCMapper {
    int deleteByPrimaryKey(ZPUBLICYZJCKey key);

    int insert(ZPUBLICYZJC record);

    int insertSelective(ZPUBLICYZJC record);

    ZPUBLICYZJC selectByPrimaryKey(ZPUBLICYZJCKey key);

    int updateByPrimaryKeySelective(ZPUBLICYZJC record);

    int updateByPrimaryKey(ZPUBLICYZJC record);
    String selectYZJCMaxidByCrimid();
    List findByYZJCDetail(Map map);
	int findByYZJCDetailCount(Map map);
	int deleteByPrimaryKey(String id);
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
	/**
	 * 
	 * @param crimid
	 * @return
	 */
	Map selectMySelfInfoByCrimid(String crimid);
}
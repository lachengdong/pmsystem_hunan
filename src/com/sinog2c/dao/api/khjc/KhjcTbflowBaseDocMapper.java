package com.sinog2c.dao.api.khjc;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.khjc.KhjcTbflowBaseDoc;

public interface KhjcTbflowBaseDocMapper {
    int deleteByPrimaryKey(String docid);

    int insert(KhjcTbflowBaseDoc record);

    int insertSelective(KhjcTbflowBaseDoc record);

    KhjcTbflowBaseDoc selectByPrimaryKey(@Param("docid")String docid);
    
    KhjcTbflowBaseDoc selectByCondition(Map map);

    int updateByPrimaryKeySelective(KhjcTbflowBaseDoc record);

    int updateByPrimaryKeyWithBLOBs(KhjcTbflowBaseDoc record);

    int updateByPrimaryKey(KhjcTbflowBaseDoc record);
    
    /**
	 * 获取当前页面数据（分页）
	 * 
	 * @author yanqutai
	 */
	List<Map> getBaseDocByCondition(Map map);
	
	/**
	 * 获取当前页面数据（分页）
	 * 
	 * @author yanqutai 
	 */
	List<Map> getKhjcFlowBaseDoctListByNodeid(Map map);

	/**
	 * 获取符合条件的数据总数（分页时显示总数）
	 * 
	 * @author yanqutai 
	 */
	int countBaseDocByCondition(Map map);
	
}
package com.sinog2c.dao.api.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.system.TbsysQueryplan;
/**
 * 查询方案信息表的相关数据库操作
 * 
 * @author liuxin 2014-7-22 14:02:30
 */

public interface TbsysQueryplanMapper {
	/**
	 * 删除数据
	 * 
	 * @author liuxin 2014-7-22 14:02:30
	 */
    int deleteByPrimaryKey(Integer planid);
    /**
	 * 新增数据
	 * 
	 * @author liuxin 2014-7-22 14:02:30
	 */
    int insert(TbsysQueryplan record);
    /**
	 * 新增数据
	 * 
	 * @author liuxin 2014-7-22 14:02:30
	 */
    int insertSelective(TbsysQueryplan record);
    /**
	 * 根据主键查询数据
	 * 
	 * @author liuxin 2014-7-22 14:02:30
	 */
    TbsysQueryplan selectByPrimaryKey(Integer planid);
    /**
	 * 修改数据
	 * 
	 * @author liuxin 2014-7-22 14:02:30
	 */
    int updateByPrimaryKeySelective(TbsysQueryplan record);
    /**
	 * 修改数据
	 * 
	 * @author liuxin 2014-7-22 14:02:30
	 */
    int updateByPrimaryKey(TbsysQueryplan record);
    /**
	 * 保存查询方案信息
	 * 
	 * @author liuxin 2014-7-22 16:14:56
	 */
	int saveQueryScheme(TbsysQueryplan queryplan);
	/**
	 * 获得查询方案信息列表
	 * 
	 * @author liuxin 2014-7-22 16:41:50
	 */
	List<Map> selectAll(@Param("key")String key);
	/**
	 * 删除查询方案
	 * 
	 * @author liuxin 2014-7-23 16:41:22
	 */
	int delQueryScheme(TbsysQueryplan queryplan);
	/**
	 * 修改查询方案信息
	 * 
	 * @author liuxin 2014-7-23 19:49:03
	 */
	int updateQueryScheme(TbsysQueryplan queryplan);
	
	List<Map> ajaxScreeningShecmeAll();
}
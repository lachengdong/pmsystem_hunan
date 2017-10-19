package com.sinog2c.dao.api.penaltyPerform;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sinog2c.model.penaltyPerform.TbyzPublicity;

public interface TbyzPublicityMapper {
	/**
	 * 获取狱务公示数据列表
	 * @return
	 */
	List<TbyzPublicity> ajaxGetPublicityList(@Param("start") int start,@Param("end") int end,@Param("key") String key,
			@Param("type") String type,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);
	/**
	 * 获取狱务公示数据总数
	 */
	int ajaxGetPublicityCount(@Param("key") String key,@Param("type") String type);
	/**
	 * 新增保存
	 */
	int insertSelective(TbyzPublicity pub);
	/**
	 * 修改保存
	 */
	int updatePublicity(TbyzPublicity pub);
	/**
	 * 获取数据详细信息
	 */
	TbyzPublicity ajaxGetPublicity(String pubid);
	/**
	 * 删除数据
	 */
	int deletePublicity(String pubid);

}
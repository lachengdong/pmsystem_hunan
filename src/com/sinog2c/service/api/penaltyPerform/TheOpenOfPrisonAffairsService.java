package com.sinog2c.service.api.penaltyPerform;

import java.util.List;
import com.sinog2c.model.penaltyPerform.TbyzPublicity;

/**
 * 接口描述：狱务公开
 * @author liuchaoyang
 * 下午08:42:26
 */
public interface TheOpenOfPrisonAffairsService {
	
	/**
	 * 获取狱务公示数据列表
	 * @return
	 */
	public List<TbyzPublicity> ajaxGetPublicityList(int pageIndex, int pageSize,
			String key,String type,String sortField,String sortOrder);
	/**
	 * 获取狱务公示数据总数
	 */
	public int ajaxGetPublicityCount(String key,String type);
	/**
	 * 新增保存
	 */
	public int insertPublicity(TbyzPublicity pub);
	/**
	 * 修改保存
	 */
	public int updatePublicity(TbyzPublicity pub);
	/**
	 * 获取数据详细信息
	 */
	public TbyzPublicity ajaxGetPublicity(String pubid);
	/**
	 * 删除数据
	 */
	public int deletePublicity(String pubid);

}

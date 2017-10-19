package com.sinog2c.dao.api.solution;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.solution.FormSolutionDetail;

public interface FormSolutionDetailMapper {
    int deleteById(String detailid);

    int insert(FormSolutionDetail record);

    int insertSelective(FormSolutionDetail record);

    FormSolutionDetail selectById(String detailid);
    
    FormSolutionDetail selectByCombinationId(Map map);

    int updateByIdSelective(FormSolutionDetail record);

    int updateById(FormSolutionDetail record);

	/**s
	 * 获取数据列表
	 * @return
	 */
	List<Map<String, Object>> listMapByPage(Map<String, Object> map);

	// 统计所有
	public int countAll();
	// 获取所有 list
	public List<FormSolutionDetail> listAll();
	// 根据条件统计
	public int countByCondition(Map<String, Object> map);
	// 根据条件获取数据
	public List<FormSolutionDetail> listByCondition(Map<String, Object> map);
	// 根据Map更新
	public int updateByMap(Map<String, Object> map);
	// 根据Map插入
	public int insertByMap(Map<String, Object> map);
	
	public List<FormSolutionDetail> getTreeDataOfSolutionDetail(Map<String, Object> map);
	
}
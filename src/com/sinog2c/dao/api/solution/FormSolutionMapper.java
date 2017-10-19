package com.sinog2c.dao.api.solution;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinog2c.model.solution.FormSolution;

public interface FormSolutionMapper {
    int deleteById(String solutionid);

    int insert(FormSolution record);

    int insertSelective(FormSolution record);
    
    FormSolution selectById(String solutionid);

    int updateByIdSelective(FormSolution record);
    
    int updateById(FormSolution record);
    /**
     * 获取下一个ID,无缓存
     * @return
     */
    public int getNextIdNoCache(
			@Param("random")
    		int random );

	/**
	 * 获取数据列表
	 * @return
	 */
	List<Map<String, Object>> listMapByPage(Map<String, Object> map);

	// 统计所有
	public int countAll();
	// 获取所有 list
	public List<FormSolution> listAll();
	// 根据条件统计
	public int countByCondition(Map<String, Object> map);
	// 根据条件获取数据
	public List<FormSolution> listByCondition(Map<String, Object> map);
	// 根据Map更新
	public int updateByMap(Map<String, Object> map);
	// 根据Map插入
	public int insertByMap(Map<String, Object> map);
	
	public List<FormSolution> getTreeDataOfSolution(Map<String, Object> map);

	List<FormSolution> getSolutionSchemeTreeBySolutionpname(
			Map<String, Object> map);
}
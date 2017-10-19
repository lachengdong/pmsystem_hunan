package com.sinog2c.service.api.solution;

import java.util.List;
import java.util.Map;

import com.sinog2c.model.solution.FormSolution;
import com.sinog2c.model.system.SystemUser;

/**
 * 表单查询方案服务
 */
public interface FormSolutionService {
	
	/**
	 * 添加
	 * @param solution
	 * @param operator
	 * @return
	 */
	public int add(FormSolution solution, SystemUser operator);

	/**
	 * 使用Map插入
	 * @param map
	 * @param operator
	 * @return
	 */
	public int add(Map<String, Object> map, SystemUser operator);
	
	/**
	 * 根据Map更新
	 * @param map
	 * @param operator
	 * @return
	 */
	public int update(Map<String, Object> map, SystemUser operator);
	/**
	 * 更新
	 * @param solution
	 * @param operator
	 * @return
	 */
	public int update(FormSolution solution, SystemUser operator);

	/**
	 * 删除
	 * @param solution
	 * @param operator
	 * @return
	 */
	public int delete(FormSolution solution, SystemUser operator);
	/**
	 * 根据ID删除
	 * @param solutionpid
	 * @param operator
	 * @return
	 */
	public int delete(String solutionpid, SystemUser operator);
	
	/**
	 * 根据ID获取
	 * @param solutionid
	 * @return
	 */
	public FormSolution getById(String solutionid);

	/**
	 * 根据条件获取数据Map列表
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> listMapByPage(Map<String, Object> map);
	
	/**
	 * 获取树形数据
	 * @param map
	 * @return
	 */
	public List<FormSolution> getTreeDataOfSolution(Map<String, Object> map);

	/**
	 * 获取全部记录的数量
	 * @return
	 */
	public int countAll();

	/**
	 * 获取全部记录,(可能Tree需要使用)
	 * @return
	 */
	public List<FormSolution> listAll();
	/**
	 * 根据条件统计
	 * @param map
	 * @return
	 */
	public int countByCondition(Map<String, Object> map);
	/**
	 * 根据条件获取数据
	 * @param map
	 * @return
	 */
	public List<FormSolution> listByCondition(Map<String, Object> map);
	
	public int saveSolutionScheme(Map<String, Object> map, SystemUser operator);
	/**
	 * 拷贝方案
	 * @param map
	 * @param operator
	 * @return
	 */
	public boolean copySolution(Map<String, Object> map, SystemUser operator);

	public Object getSolutionSchemeTreeBySolutionpname(Map<String, Object> map);
}
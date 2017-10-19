package com.sinog2c.util.solution.processor.api;

import java.sql.PreparedStatement;

/**
 * 查询方案的类型处理器接口API
 */
public interface ISolutionTypeProcessor {

	/**
	 * 处理方案类型值得注入.有框架自动调用此方法
	 * @param statement JDBC预编译语句对象
	 * @param order SQL参数位置,顺序,从1开始
	 * @param jdbcType jdbc类型,纯小写,实现类可以(不)依赖此参数
	 * @param paramValue 参数值,可能是各种类型的对象
	 * @return 返回处理成功/失败,如果处理失败,则框架使用默认处理器进行处理
	 */
	public boolean processSolutionType(PreparedStatement statement, int order, String jdbcType, Object paramValue);
}

package com.sinog2c.util.solution.processor;

import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sinog2c.util.solution.processor.api.ISolutionTypeProcessor;

public class ClobProcessor implements ISolutionTypeProcessor {

	public static final String JDBC_TYPE_CLOB = "clob";
	@Override
	public boolean processSolutionType(PreparedStatement statement, int order,
			String jdbcType, Object paramValue) {
		boolean result = false;
		if(null == statement || order < 1){
			return result;
		}
		//
		String value = "";
		if(null != paramValue){
			value = paramValue.toString();
		}
		//
		try {
			if(JDBC_TYPE_CLOB.trim().equalsIgnoreCase(jdbcType)){
				//
				Clob clob = null;
				if(paramValue instanceof Clob){
					clob = (Clob)paramValue;
				} else {
					clob = statement.getConnection().createClob();
					// 从 1 开始
					clob.setString(1, value);
				}
				// 阿里巴巴的坑
				if(clob instanceof com.alibaba.druid.proxy.jdbc.ClobProxyImpl){
					com.alibaba.druid.proxy.jdbc.ClobProxyImpl impl = (com.alibaba.druid.proxy.jdbc.ClobProxyImpl)clob;
					clob = impl.getRawClob(); // 获取原生的这个 Clob
				}
				statement.setClob(order, clob);
				//
				// 请注意, StringReader有坑,字段超过5万或者多少之后,就报错了. 所以注释了
				// MyBatis的Clob类型也是这个BUG，如果不使用Clob,直接默认String,则Mybatis不报错
				//StringReader reader = new StringReader(value);
				//Reader reader = clob.getCharacterStream();
				// 设置输出流
				//statement.setCharacterStream(order, reader, value.length());
			} else {
				statement.setString(order, value);
			}
			result = true;
		} catch (SQLException e) {
			throw new RuntimeException("设置["+jdbcType+"]类型出错!", e);
		}
		//
		return result;
	}
}

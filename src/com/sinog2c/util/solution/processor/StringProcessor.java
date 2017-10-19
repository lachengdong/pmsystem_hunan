package com.sinog2c.util.solution.processor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sinog2c.util.solution.processor.api.ISolutionTypeProcessor;

public class StringProcessor implements ISolutionTypeProcessor {

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
			statement.setString(order, value);
			result = true;
		} catch (SQLException e) {
			throw new RuntimeException("设置["+jdbcType+"]类型出错!", e);
		}
		//
		return result;
	}
}

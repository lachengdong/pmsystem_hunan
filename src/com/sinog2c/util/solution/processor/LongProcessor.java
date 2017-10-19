package com.sinog2c.util.solution.processor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sinog2c.util.solution.processor.api.ISolutionTypeProcessor;

public class LongProcessor implements ISolutionTypeProcessor {
	
	private static String LongRegexStr = "^[\\+\\-]?\\d+$";

	@Override
	public boolean processSolutionType(PreparedStatement statement, int order,
			String jdbcType, Object paramValue) {
		boolean result = false;
		if(null == statement || order < 1){
			return result;
		}
		//
		long value = 0L;
		if(null != paramValue){
			//
			String str = paramValue.toString().trim();
			if(str.matches(LongRegexStr)){
				value = Long.parseLong(str);
			}
		}
		//
		try {
			if(paramValue instanceof Long){
				value = (Long)paramValue;
			}
			statement.setLong(order, value);
			result = true;
		} catch (SQLException e) {
			throw new RuntimeException("设置["+jdbcType+"]类型出错!", e);
		}
		//
		return result;
	}

}

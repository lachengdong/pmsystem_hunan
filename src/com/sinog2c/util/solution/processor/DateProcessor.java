package com.sinog2c.util.solution.processor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sinog2c.util.solution.processor.api.ISolutionTypeProcessor;

public class DateProcessor implements ISolutionTypeProcessor {

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
			if(paramValue instanceof java.sql.Date){
				java.sql.Date date = (java.sql.Date)paramValue;
				statement.setDate(order, date);
			} else if(paramValue instanceof java.util.Date){
				java.util.Date date_u = (java.util.Date)paramValue;
				//
				java.sql.Date date = new java.sql.Date(date_u.getTime());
				statement.setDate(order, date);
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

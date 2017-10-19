package com.sinog2c.util.solution.processor;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sinog2c.util.solution.processor.api.ISolutionTypeProcessor;

public class BlobProcessor implements ISolutionTypeProcessor {

	public static final String JDBC_TYPE_BLOB = "blob";
	@Override
	public boolean processSolutionType(PreparedStatement statement, int order,
			String jdbcType, Object paramValue) {
		boolean result = false;
		if(null == statement || order < 1){
			return result;
		}
		//
		//
		try {
			if(JDBC_TYPE_BLOB.trim().equalsIgnoreCase(jdbcType)){
				//
				Blob blob = null;
				if(paramValue instanceof Blob){
					blob = (Blob)paramValue;
				} else {
					blob = statement.getConnection().createBlob();
					//
					byte[] bytes = {};
					if(paramValue instanceof byte[]){
						bytes = (byte[])paramValue;
					} else if(paramValue instanceof String){
						bytes = paramValue.toString().getBytes("UTF-8");
					}
					// 从 1 开始
					blob.setBytes(1, bytes);
				}
				//
				statement.setBlob(order, blob);
			} else {
				String value = "";
				if(null != paramValue){
					value = paramValue.toString();
				}
				statement.setString(order, value);
			}
			result = true;
		} catch (SQLException e) {
			throw new RuntimeException("设置["+jdbcType+"]类型出错!", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("编码转换异常,服务器系统不支持UTF-8!", e);
		}
		//
		return result;
	}
}

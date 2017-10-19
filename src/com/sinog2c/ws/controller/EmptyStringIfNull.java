package com.sinog2c.ws.controller;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class EmptyStringIfNull implements TypeHandler<String> {

	@Override
	public String getResult(ResultSet arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResult(ResultSet arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResult(CallableStatement arg0, int arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParameter(PreparedStatement arg0, int arg1, String arg2,
			JdbcType arg3) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}

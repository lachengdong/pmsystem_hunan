package com.sinog2c.util.solution;

/**
 * 参数类
 */
public class VariableToken {

	//
	public static final String TYPE_BLOB = "blob";
	public static final String TYPE_CLOB = "clob";
	public static final String TYPE_DATE = "date";
	public static final String TYPE_INT = "int";
	public static final String TYPE_STRING = "string";
	
	// 参数name
	private String name;
	// SQL模板中匹配的整个text
	private String wraptext;
	// 取得的jdbcType
	private String jdbcType;
	// 出现的顺序,从1开始
	private int order;
	//
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWraptext() {
		return wraptext;
	}
	public void setWraptext(String wraptext) {
		this.wraptext = wraptext;
	}
	public String getJdbcType() {
		return jdbcType;
	}
	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "VariableToken [" + name + ", jdbcType=" + jdbcType + ", order=" + order + "]";
	}
}

package com.sinog2c.util.solution;


public class LexerException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	private static String errorPrefix = "语法解析错误: ";

	public LexerException() {
		super(errorPrefix);
	}

	public LexerException(String msg) {
		super(errorPrefix + msg);
	}
	
	public LexerException(Throwable cause) {
		super(errorPrefix, cause);
	}
	
	public LexerException(String msg, Throwable cause) {
		super(errorPrefix + msg, cause);
	}
	
}

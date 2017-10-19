package com.sinog2c.mvc.exception;

@SuppressWarnings("serial")
public class WebMVCException extends Exception {

	public WebMVCException(){
		this("一个未指定详细描述信息的WebMVCException.");
	}
	public WebMVCException(String info){
		super(info);
	}
}

package com.sinog2c.mvc.exception;

/**
 * 用户未登录, 用户验证失败
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class LoginFailureException extends RuntimeException {

	public LoginFailureException(){
		this("用户验证失败");
	}
	public LoginFailureException(String info){
		super(info);
	}
	/**
	 * 直接抛出一个运行时异常
	 */
	public static void throwNewLoginFailureException(){
		throw new LoginFailureException();
	}
}

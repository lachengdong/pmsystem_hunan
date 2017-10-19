package com.sinog2c.mvc.errorhandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.sinog2c.mvc.exception.LoginFailureException;
import com.sinog2c.util.common.LogUtil;

/**
 * 异常错误统一处理
 */
public class ExceptionHandler implements HandlerExceptionResolver {

	/**
	 * 处理异常的方法,配置到Spring容器后会自动调用, Exception ex 为具体的异常对象
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// 先打印出错日志,不能吞了
		String uri = request.getRequestURI();
		LogUtil.println(LogUtil.currentTime()+" 发生错误:" + ex.getMessage() +";uri=" + uri);
		if(ex instanceof LoginFailureException){
			// 登录错误,就不用打印异常栈
		} else {
			ex.printStackTrace();
		}
		//
		View view = null;
		// 默认
		String jspViewName = "exception";
		if (ex instanceof LoginFailureException) {
			// 登录失败
			view = new InternalResourceView("/login.jsp");
			request.setAttribute("errorMessage", "请重新登录!");
			StringBuffer from = request.getRequestURL();
			request.setAttribute("from", from.toString());
		} else if (ex instanceof NumberFormatException) {
			// 数字格式错误
			//doSomething...
			jspViewName = ("number");
		} else if (ex instanceof NullPointerException) {
			// 空指针
			//doSomething...
			jspViewName = ("null");
		} else {
			// 其他错误。。。
		}
		// 是JSON
		if(uri.matches(".+\\.json$")){
			// 这里返回 JSON
			view = null;
			jspViewName = "jsonerror";
		} else if(uri.matches(".+\\.action$")){
			//
		} else if(uri.matches(".+\\.page$")){
			//
		}
		
		ModelAndView mav = new ModelAndView();
		if( null == view){
			mav.setViewName(jspViewName);
		} else {
			mav.setView(view);
		}
		// 
		return mav;
	}
	
	public static void main(String[] args) {
		String uri = "/pmsys/config/ajax/list.json";
		if(uri.matches(".+\\.json$")){
			//
			System.out.println("匹配");
		} else {
			System.out.println("不匹配");
		}
	}

}
package com.sinog2c.mvc.front.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sinog2c.dao.api.prisoner.CrimUserInfoMapper;
import com.sinog2c.dao.api.prisoner.TbprisonerBaseinfoMapper;
import com.sinog2c.model.prisoner.CrimUserInfo;
/**
 * 该拦截器主要用对 投诉复议、个人信息、我要购物  三个功能进行用户是否登录的验证
 * @author sun
 *
 */
public class FrontLoginInterceptor extends  HandlerInterceptorAdapter{
         
	
	 public   boolean  preHandle (HttpServletRequest req, HttpServletResponse resp, Object handler) {
		 String className  =  handler.getClass().getName();
		 boolean returntype=true;
		 Method []methodarray =  handler.getClass().getMethods(); // package Name .ClassName
		 methodarray[0].getName();
		 
		 //当YwgkPrisonOperationController 发生请求处理的时候，就判断该请求的用户是否存在如果不存在则，跳转到登录界面 ，如果存在则跳转到目标页面
		 if(className.equals("com.sinog2c.mvc.controller.comprehensive.YwgkPrisonOperationController")){
				//String  crimid=req.getParameter("crimid");
				//CrimUserInfo validateUserinfo=crimUserInfoMapper.selectByPrimaryKey(crimid);
			//如果是 投诉复议、个人信息、我要购物 三者发送的请求则 获取对应的url 然后判断该用户是否是已登录的合法用户
				CrimUserInfo  userinfo=(CrimUserInfo) req.getSession().getAttribute("userinfo");
			String url= req.getParameter("page");
				try {
					if(userinfo==null){
						// req.setAttribute("flage", "请您登陆后");
						req.setAttribute("URL",url);
					returntype=false;
					req.getRequestDispatcher("/WEB-INF/JSP/front/login/yugklogin.jsp").forward(req, resp);
					}
					
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
	         return returntype;
	      }
	
	
	
	
	
}

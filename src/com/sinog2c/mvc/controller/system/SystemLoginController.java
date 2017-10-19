package com.sinog2c.mvc.controller.system;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.service.api.system.SystemUserService;
import com.sinog2c.util.common.PasswordUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 登录相关的控制器,类似于 Struts中的Action<br/>
 * @ Controller 声明这是一个处理器类 由 servlet-context.xml 中定义,自动扫描, 非线程安全, 单例<br/>
 * 如果要在方法间共享变量，请使用 ThreadLocal<br/>
 */
@Controller
public class SystemLoginController extends ControllerBase {

	@Autowired
	private SystemUserService SystemUserService;

	/**
	 * 登录, 只处理 POST<br/>
	 * RequestMapping声明对应请求的映射关系,访问时加上后缀,如: szpmsys.com:8080/szpmsys/login.acion <br/>
	 * request,与 response 在方法参数中指定时 MVC 会自动传入
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/login.action", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) {
		//
		ModelAndView mav = null;
		View view = new InternalResourceView("/login.jsp");

		// 用户对象
		SystemUser systemUser = null;
		// 是否登录成功
		boolean loginSuccess = false;
		// 是否输入验证成功
		boolean validateInputOK = true;
		// 验证错误提示信息
		String validateErrorInfo = "验证失败";

		// 用户密码登录的方式 ...
		// 取得参数
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String loginType = request.getParameter("loginType");
		String userNo = request.getParameter("userNo");
		final String loginType_USBKEY = "usbkey";
		if (loginType_USBKEY.equalsIgnoreCase(loginType)) {
			//			
			String certsn = request.getParameter("certno");// 得到certno
			String certdata = request.getParameter("signdata");// 得到signdata，签名值
			// System.out.println("certsn="+certsn);
			// System.out.println("certdata="+certdata);
			//
			if (!StringNumberUtil.notEmpty(certsn)
					|| !StringNumberUtil.notEmpty(certdata)) {
				// 出错
				validateInputOK = false;
				validateErrorInfo = "Key不能为空";
			} else {
				//
				systemUser = SystemUserService.getUserByCert(certsn, certdata);
			}

		} else if (!StringNumberUtil.isNullOrEmpty(userNo)) {
			try {
				// 找出用户
				systemUser = SystemUserService.getByUserId(userNo);
			} catch (Exception e) {
				e.printStackTrace();
				// 如果连接出错
				validateInputOK = false;
				validateErrorInfo = "系统出错";
			}

		} else if (null == userName || userName.length() < 1) {
			// 进行一些校验
			validateInputOK = false;
			validateErrorInfo = "请输入帐号";
		} else if (null == password || password.length() < 1) {
			validateInputOK = false;
			validateErrorInfo = "请输入密码";
		} else {
			// 去除首尾空格
			userName = userName.trim();
			password = password.trim();
			try {
				// 找出用户
				systemUser = SystemUserService.getByUserId(userName);
			} catch (Exception e) {
				e.printStackTrace();
				// 如果连接出错
				validateInputOK = false;
				validateErrorInfo = "系统出错";
			}
		}

		// 比对密码,执行登录操作
		if (null != systemUser) {
			// 拦截已删除和已锁定用户
			String islocked = systemUser.getIslocked();
			String delflag = systemUser.getDelflag();
			if (null != islocked && islocked.equals("1")) {
				// 已锁定，已调离
				validateInputOK = false;
				validateErrorInfo = "用户已调离";
			} else if (null != delflag && delflag.equals("1")) {
				// 已删除
				validateInputOK = false;
				validateErrorInfo = "用户已删除";
			} else if (loginType_USBKEY.equalsIgnoreCase(loginType)) {
				// 证书登录成功
				loginSuccess = true;
			} else if (!StringNumberUtil.isNullOrEmpty(userNo)) {
				// 上海单点登录成功
				loginSuccess = true;
			} else {
				String userPassword = systemUser.getPassword();

				// 对比密码,判断是否登录成功
				loginSuccess = PasswordUtil.passwordCheck(password,
						userPassword);
				if (!loginSuccess) {
					// 密码错误
					validateInputOK = false;
					validateErrorInfo = "密码错误";
				}
			}
			//
		} else if (validateInputOK) {
			// 用户不存在
			validateInputOK = false;

			if (loginType_USBKEY.equalsIgnoreCase(loginType)) {
				validateErrorInfo = "证书未绑定";
			} else {
				validateErrorInfo = "用户不存在";
			}
		}

		// 用户名
		request.setAttribute("userName", userName);
//		Properties props=new Properties();
//		//后缀为properties的文件中内容为：
//		//内容为键值对方式，且值不用引号，键值对之间不用符号间隔
//		try {
//			props.load(SystemLoginController.class.getClassLoader().getResourceAsStream("jyconfig.properties"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String ischeckseal=props.getProperty("ischeckseal");
//	// 登陆成功之前从application中sysNam
//		HashMap<String, HttpSession> map = (HashMap<String, HttpSession>) request
//				.getSession().getServletContext().getAttribute("sysName");
//		if (map != null&&ischeckseal!=null&&!"".equals(ischeckseal)&&"1".equals(ischeckseal)) {
//			Set<String> keys = map.keySet();
//			//相同用户再次登录  把上个用户给顶掉   同账号用户只有一台电脑可以访问网站
//			if (keys.iterator().next() != null
//					|| userName.equals(keys.iterator().next())) { // 另一台登上 这台有提示
//				HttpSession session = map.get(keys.iterator().next()); 
//				// 让当前session失效
//				session.invalidate();
//				//创建新的session
//				session = request.getSession(true); // 47B3DB165765503FC0F301EBA8D1F78A
//                
//				HashMap<String, HttpSession> mymap = new HashMap<String, HttpSession>();
//				mymap.put(userName, session);
//                //重置上下文  session
//				request.getSession().getServletContext().setAttribute(
//						"sysName", mymap);
//               
//				setSessionAttribute(request, "" + SESSION_USER_KEY, systemUser);
//				final int aliveTimeSeconds = 100 * 24 * 60 * 60;
//				//
//				setSessionAliveTime(request, aliveTimeSeconds);
//				// 添加到在线用户列表
//				addOnlineUser(request, systemUser);
//				request.setAttribute("errorMessage", "此用户已登录");
//			}
//		} else {
			// 根据成功与否,执行定向操作
			if (loginSuccess) { 
				// 登陆成功
				view = generateView(request);

				// 要求简单直接, 使会话失效,再新创一个会话即可
				HttpSession session = request.getSession(true); 
				// yuan
				session.invalidate();
				session = request.getSession(true);

				setSessionAttribute(request, "" + SESSION_USER_KEY, systemUser);
			
				// 把用户放入application作用域中
				HashMap<String, HttpSession> mymap = new HashMap<String, HttpSession>();
				mymap.put(userName, request.getSession());

				request.getSession().getServletContext().setAttribute(
						"sysName", mymap);
				final int aliveTimeSeconds = 100 * 24 * 60 * 60;
				setSessionAliveTime(request, aliveTimeSeconds);
				// 添加到在线用户列表
				addOnlineUser(request, systemUser);
				StringNumberUtil.addCookie("userName", systemUser.getUserid(),
						60 * 60 * 24 * 365, response);// 保存用户名
				addOnlineLog(systemUser, "登录成功");
			}

			else {
				addOnlineLog(systemUser, validateErrorInfo + ";userName="
						+ userName, getLogtype(), 0);
			}
//		}

		// 返回带对象的视图
		mav = new ModelAndView(view);
		// 如果有验证错误, 则加入到attributes中
		if (false == validateInputOK) {
			// 
			mav.addObject("errorMessage", validateErrorInfo);
			request.setAttribute("errorMessage", validateErrorInfo);
		}
		return mav;
	}

	@Override
	protected String getLogtype() {
		return "用户登录";
	}

	//
	public View generateView(HttpServletRequest request) {
		View view = new InternalResourceView("/WEB-INF/JSP/main/main_index.jsp");
		String tokenName = request.getParameter("token"); // token名字
		if (StringNumberUtil.notEmpty(tokenName)) {
			String tokenValue = request.getParameter(tokenName);
			Object tokenValueSession = getSessionAttribute(request, tokenName);
			if (StringNumberUtil.notEmpty(tokenValue)
					&& tokenValue.equals(tokenValueSession)) {
				request.setAttribute("tokenValueSession", tokenValueSession);
			}
		}
	
		return view;
	}

	/**
	 * 输入页面,只处理 GET
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public View input() {
		View view = new InternalResourceView("/login.jsp");
		// ModelAndView mav = new ModelAndView(view);
		return view;
	}

	@RequestMapping(value = "/loginPage.page", method = RequestMethod.GET)
	public View loginPage() {
		View view = new InternalResourceView("/realLoginPage.jsp");
		Properties jypro = new GetProperty().bornProp(GkzxCommon.DATABASETYPE,null);
		if (jypro != null) {
			String provincecode = jypro.getProperty("provincecode");
			String projectName = jypro.getProperty("projectName");
			if ("fy".equals(provincecode)) {// 法院登录页面
				view = new InternalResourceView("/courtLoginPage.jsp");
			}
			if ("6100".equals(provincecode)) {// 陕西登录页面
				view = new InternalResourceView("/realLoginPage_sx.jsp");
			}
			if ("ywgk".equals(projectName)) {// 陕西狱务公开页面
				view = new InternalResourceView("/realLoginPage_ywgk.jsp");
			}
			// if(GkzxCommon.SHANGHAI_PROVINCE.equals(provincecode)){//上海登录页面
			// view = new InternalResourceView("/realLoginPage_sh.jsp");
			// }
		}
		// ModelAndView mav = new ModelAndView(view);
		return view;
	}

	/**
	 * 登出
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/logout", "/lackauth" })
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) {
		//
		// String myname = LogUtil.getMyName();
		SystemUser systemUser = getLoginUser(request);
		// 移除在线用户列表
		removeOnlineUser(request, systemUser);
		// 执行 注销操作
		// 如果考虑扩展,不能直接操作 session
		setSessionAttribute(request, SESSION_USER_KEY, null);
		// 如果要求简单直接, 使会话失效、再新创一个会话即可
		HttpSession session = request.getSession(true);
		session.invalidate();
		session = request.getSession(true);
		// ...
		addOnlineLog(systemUser, "退出登录");
		//

		request.getSession().getServletContext().setAttribute("sysName", null);
		View view = new InternalResourceView("/login.jsp");
		ModelAndView mav = new ModelAndView(view);
		mav.addObject("tip", "退出登录成功");
		return mav;
	}
}

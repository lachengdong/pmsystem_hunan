package com.sinog2c.config;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.sinog2c.model.system.SystemUser;
import com.sinog2c.util.common.LogUtil;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 * 
 */
public class SpringContextHolder implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext; // NOSONAR
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		return (T) applicationContext.getBeansOfType(clazz);
	}

	/**
	 * 清除applicationContext静态变量.
	 */
	public static void cleanApplicationContext() {
		applicationContext = null;
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException(
					"applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}
	

	/**
	 * 
	 * 执行指定 Bean 的指定方法; (约定好参数为 Map<String,Object> 和 SystemUser; 返回值为 int)
	 * @param beanName
	 * @param methodName
	 * @param paramMap
	 * @return -1 为参数错误, -2 为没有bean或没有方法.
	 */
	public static int excuteServiceMethod(String beanName, String methodName, Map<String,Object> paramMap, SystemUser operator){
		if(StringNumberUtil.isEmpty(beanName) || StringNumberUtil.isEmpty(methodName) || null == paramMap){
			return -1;
		}
		//
		Object bean = SpringContextHolder.getBean(beanName);
		//
		if(null == bean){
			return -2;
		}
		//
		Class<?> clazz = bean.getClass();
		//
		try {
			Method method = clazz.getDeclaredMethod(methodName, Map.class, SystemUser.class);
			//
			Object result = method.invoke(bean, paramMap, operator);
			//
			if(result instanceof Integer){
				return ((Integer)result).intValue(); 	// 正常执行
			}
		} catch (Exception e) {
			throw new RuntimeException("执行业务回调时产生异常:",e);
		}
		//
		return 0;
	}
	
	//start add by blue_lv 2015-07-13
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name,Class<T> clazz) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}
	//end add by blue_lv 2015-07-13
}

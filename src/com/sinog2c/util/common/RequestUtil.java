package com.sinog2c.util.common;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.util.TypeUtils;
import com.gkzx.common.OAParameter;
import com.sinog2c.model.PagingParameter;


public final class RequestUtil {

	/**
	 * 设置session属性
	 * 
	 * @param request
	 *            HttpServletRequest 请求对象
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值, 可序列化对象
	 */
	public static void setSessionAttribute(HttpServletRequest request,
			String name, Object value) {
		// 当前是基于单容器的实现
		HttpSession session = request.getSession(true);
		session.setAttribute(name, value);
	}

	/**
	 * 设置Session存活时间
	 * 
	 * @param request
	 * @param aliveTimeSeconds
	 */
	public static void setSessionAliveTime(HttpServletRequest request,
			int aliveTimeSeconds) {
		// 当前是基于单容器的实现
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(aliveTimeSeconds);
	}

	/**
	 * 根据 request取得Session属性值
	 * 
	 * @param request
	 *            HttpServletRequest 请求对象
	 * @param name
	 *            属性名
	 * @return
	 */
	public static Object getSessionAttribute(HttpServletRequest request,
			String name) {
		// 当前是基于单容器的实现
		HttpSession session = request.getSession(true);
		return session.getAttribute(name);
	}

	/**
	 * 获取 上下文 path, 返回如 "/pmsys"
	 * 
	 * @param request
	 * @return
	 */
	public static String path(HttpServletRequest request) {
		String path = request.getContextPath();
		return path;
	}

	/**
	 * 获取 basePath
	 * 
	 * @param request
	 * @return
	 */
	public static String basePath(HttpServletRequest request) {
		String basePath = basePathLessSlash(request) + "/";
		return basePath;
	}

	/**
	 * 获取最后面少一个斜线的basePath
	 * 
	 * @param request
	 * @return
	 */
	public static String basePathLessSlash(HttpServletRequest request) {
		String path = path(request);
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path;
		return basePath;
	}

	/**
	 * 获取参数
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		//
		return value;
	}

	/**
	 * 获取参数,如果为下面的值,则返回指定的默认值: <br/>
	 * 包括: null, "", "null", "undefined"
	 * 
	 * @param request
	 * @param name
	 *            参数名
	 * @param defValue
	 *            指定默认值
	 * @return 如果为空或不存在,则返回默认值
	 */
	public static String getParameterString(HttpServletRequest request,
			String name, String defValue) {
		String value = request.getParameter(name);
		if (null == value) {
			return defValue;
		} else {
			value = value.trim();
			if ("".equals(value) || "null".equals(value)
					|| "undefined".equals(value)) {
				return defValue;
			}
		}
		//
		return value;
	}

	/**
	 * 获取int类型参数
	 * 
	 * @param request
	 * @param name
	 * @param defValue
	 * @return
	 */
	public static int getParameterInt(HttpServletRequest request, String name,
			int defValue) {
		String value = request.getParameter(name);
		//
		return StringNumberUtil.parseInt(value, defValue);
	}

	/**
	 * 解析request中的参数Map
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> parseParamMap(HttpServletRequest request) {
		//
		Map<String, Object> map = new HashMap<String, Object>();
		//
		if (null != request) {
			Enumeration<String> enumeration = request.getParameterNames();
			// 遍历参数,其实有request的request.getParameterMap();但没泛型
			while (enumeration.hasMoreElements()) {
				String paraName = enumeration.nextElement();
				//
				String paraValue = request.getParameter(paraName);
				//
				if (null != paraValue) {
					paraValue = paraValue.trim();
				}
				if ("".equals(paraValue) || "null".equals(paraValue)
						|| "undefined".equals(paraValue)) {
					paraValue = "";
				}
				if (StringNumberUtil.notEmpty(paraValue)) {
					map.put(paraName, paraValue);
				} else {

				}
			}
		}
		//
		return map;
	}

	public static Map<String, Object> parseParamMapForPagination(
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != request) {
			int pageIndex = 0;
			int pageSize = OAParameter.PAGESIZE;
			int total = -1;
			Enumeration<String> enumeration = request.getParameterNames();
			// 遍历参数,其实有request的request.getParameterMap();但没泛型
			while (enumeration.hasMoreElements()) {
				String paraName = enumeration.nextElement();
				//
				String paraValue = request.getParameter(paraName);
				//
				if (null != paraValue) {
					paraValue = paraValue.trim();
				}
				if ("".equals(paraValue) || "null".equals(paraValue)
						|| "undefined".equals(paraValue)) {
					paraValue = "";
				}
				//
				map.put(paraName, paraValue);
				//
				if (paraName.equals("pageIndex")) {
					pageIndex = StringNumberUtil.parseInt(paraValue, 0);
				} else if (paraName.equals("pageSize")) {
					pageSize = StringNumberUtil.parseInt(paraValue,
							OAParameter.PAGESIZE);
				} else if (paraName.equals("total")) {
					total = StringNumberUtil.parseInt(paraValue, -1);
				}
			}
			int start = pageIndex * pageSize + 1;
			int end = start + pageSize - 1;
			map.put("start", start);
			map.put("end", end);
			map.put("total", total);

		}
		return map;
	}

	public static <T> T getObjectFromRequest(HttpServletRequest request,
			Class<T> cls) throws InstantiationException, IllegalAccessException {
		T obj = (T) cls.newInstance();		
		setFieldValue(obj,cls,request);
		Class<?> cls2 = cls.getSuperclass();
		if (!cls2.equals(java.lang.Object.class)) {
			setFieldValue(obj,cls2,request);
		}
		return obj;
	}

	private static <T> void setFieldValue(T obj, Class<?> cls,
			HttpServletRequest request) throws IllegalArgumentException,
			IllegalAccessException {
		Field[] fields = cls.getDeclaredFields();
		for (Field fd : fields) {
			String paraValue = request.getParameter(fd.getName());
			if (paraValue == null)
				continue;
			if (!"null".equals(paraValue) && !"undefined".equals(paraValue)) {
				fd.setAccessible(true);
				Object o = TypeUtils.castToJavaBean(paraValue, fd.getType());
				fd.set(obj, o);
			}
		}
	}

	public static PagingParameter getPagingParamFromRequest(
			HttpServletRequest request) throws IllegalArgumentException,
			IllegalAccessException {
		PagingParameter obj = new PagingParameter();
		Class<?> cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field fd : fields) {
			String paraValue = request.getParameter(fd.getName());
			if (paraValue == null)
				continue;
			if (!"null".equals(paraValue) && !"undefined".equals(paraValue)) {
				fd.setAccessible(true);
				Object o = TypeUtils.castToJavaBean(paraValue, fd.getType());
				fd.set(obj, o);
			}
		}

		obj.setStart(obj.getPageIndex() * obj.getPageSize() + 1);
		obj.setEnd(obj.getStart() + obj.getPageSize() - 1);

		return obj;
	}

}

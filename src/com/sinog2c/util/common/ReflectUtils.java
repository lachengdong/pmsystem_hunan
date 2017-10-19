package com.sinog2c.util.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public class ReflectUtils {
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Map<String,Object> param , Class clazz){
		
		Object value = null;
		
		Class[] paramTypes = new Class[1];
		
		Object obj = null;
		
		try {
			//创建实例
			obj = clazz.newInstance();
			Field[] f = clazz.getDeclaredFields();
			List<Field[]> flist = new ArrayList<Field[]>();
			flist.add(f);
			
			Class superClazz = clazz.getSuperclass();
			while(superClazz != null){
				f = superClazz.getFields();
				flist.add(f);
				superClazz = superClazz.getSuperclass();
			}
			
			for (Field[] fields : flist) {
				for (Field field : fields) {
					String fieldName = field.getName();
					value = param.get(fieldName);
					if(value != null){
						paramTypes[0] = field.getType();
						Method method = null;
						//调用相应对象的set方法
						StringBuffer methodName = new StringBuffer("set");
						methodName.append(fieldName.substring(0, 1).toUpperCase());
						methodName.append(fieldName.substring(1, fieldName.length()));
						
						//测试输出
						System.out.println(paramTypes[0].getName());
						
						method = clazz.getMethod(methodName.toString(), paramTypes);
						method.invoke(obj, ConvertUtil.getValue(value.toString(), fieldName, paramTypes[0]));
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return (T)obj;
	}
	
	
	/**
	 * 将javabean对象转为map
	 * @param thisObj
	 * @return
	 */
	public static Map<String,Object> javaBean2Map(Object thisObj) {
		Map<String,Object> map = new HashMap<String,Object>();
		Class c;
		try {
			c = Class.forName(thisObj.getClass().getName());
			Method[] m = c.getMethods();
			for (int i = 0; i < m.length; i++) {
				String method = m[i].getName();
				if (method.startsWith("get")) {
					try {
						Object value = m[i].invoke(thisObj);
						if (value != null) {
							String key = method.substring(3);
							key = key.substring(0, 1).toLowerCase() + key.substring(1);
							map.put(key, value);
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}


	
}
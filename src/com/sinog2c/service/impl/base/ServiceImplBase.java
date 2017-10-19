package com.sinog2c.service.impl.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sinog2c.config.SpringContextHolder;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.controller.base.ControllerBase;
import com.sinog2c.util.common.StringNumberUtil;

/**
 * 服务基类,留给后期拓展
 */
public abstract class ServiceImplBase{

	protected static boolean DEBUG_MODE = false;
	protected SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	public void begin() {
		// 开始执行
	}
	
	public void end() {
		// 执行结束
	}
	
	/**
	 * 计算页码
	 * @return 返回新对象
	 */
	public static Map<String, Object> processMapPage(Map<String, Object> map){
		if(null == map){
			map = new HashMap<String, Object>();
		}
		//
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.putAll(map);
		//
		Object pageIndexO = map2.get("pageIndex");
		Object pageSizeO = map2.get("pageSize");
		int pageIndex = 0;
		int pageSize = 20;
		if(null != pageIndexO){
			pageIndex = StringNumberUtil.parseInt(String.valueOf(pageIndexO), 0);
		}
		if(null != pageSizeO){
			pageSize = StringNumberUtil.parseInt(String.valueOf(pageSizeO), 20);
		}
		//
		// 计算页码
		int start = pageIndex * pageSize + 1;
		int end = start + pageSize -1;
		//
		map2.put("start", start);
		map2.put("end", end);
		//
		return map2;
	}
	
	protected String getTimeStampStr() {
		Date now = new Date();
		return getTimeStampStr(now);
	}
	protected String getTimeStampStr(Date date) {
		if(null == date){
			date = new Date();
		}
		return TimeStampFormat.format(date);
	}
	protected  void debug(String info){
		if(DEBUG_MODE){
			log(info);
		}
	}
	//
	protected  void log(String info){
		System.out.println(getTimeStampStr()+":\t" +info);
	}

	/**
	 * 
	 * 执行指定 Bean 的指定方法; (约定好参数为 Map<String,Object> 和 SystemUser; 返回值为 int)
	 * @param beanName
	 * @param methodName
	 * @param paramMap
	 * @return -1 为参数错误, -2 为没有bean或没有方法.
	 */
	protected int excuteServiceMethod(String beanName, String methodName, Map<String,Object> paramMap, SystemUser operator){
		// 包装另一个方法
		return SpringContextHolder.excuteServiceMethod(beanName, methodName, paramMap, operator);
	}

}

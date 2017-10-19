package com.sinog2c.util.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 与 Class相关的工具代码类
 */
public final class LogUtil {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
	/**
	 * 打印调用此方法的方法的名称
	 * @return className.methodName
	 */
    public static final String currentMethodName(){
    	//
    	Thread thread = Thread.currentThread();
    	String className = thread.getStackTrace()[2].getClassName();
    	String methodName = thread.getStackTrace()[2].getMethodName();
    	//
    	return className + "." +methodName;
    }
    public static final String currentClassName(){
    	//
    	Thread thread = Thread.currentThread();
    	String className = thread.getStackTrace()[2].getClassName();
    	//
    	return className;
    }
    /**
     * 返回当前时间
     * @return 格式: "yyyy-MM-dd HH:mm:ss"
     */
    public static final String currentTime(){
    	//
    	return DATE_FORMAT.format(new Date());
    }
    public static final String getMyName(){
    	Thread thread = Thread.currentThread();
    	String className = thread.getStackTrace()[2].getClassName();
    	String methodName = thread.getStackTrace()[2].getMethodName();
    	//
    	String info = className + "." +methodName;
    	//
    	return info;
    }
    public static final void printMyName(){
    	String info = getMyName();
    	println(info);
    }
    /**
     * 打印行
     * @param info
     */
    public static final void println(Object info){
    	System.out.println(info);
    }
}

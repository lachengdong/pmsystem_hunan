package com.sinog2c.util.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 时间处理工具包
 * 
 * @author shily 2014-8-14 10:24:42
 */
public class DateUtil {

	/**
	 * 将传入的日期转为yyyyMMdd格式的字符串
	 * 
	 * @param Object
	 * @return String
	 * @author shily 2014-8-14 11:53:37
	 */
	public static String dateFormatForAip(Object obj) {
		String result = "";
		if (!StringNumberUtil.isNullOrEmpty(obj)){
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				result = sdf.format(obj);
			}catch (Exception e){
				e.printStackTrace();
			}
		} 
		return result;
	}
	public static String dateFormat(Object obj) {
		String result = "";
		if (!StringNumberUtil.isNullOrEmpty(obj)){
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
				if (StringNumberUtil.isNullOrEmpty(obj)) return result;
				result = sdf.format(obj);
			}catch (Exception e){
				
			}
		} 
		return result;
	}
	public static Date StrParseDate(String obj,String format) {
		Date result = null;
		if (!StringNumberUtil.isNullOrEmpty(obj)){
			try{
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.parse(obj);
			}catch (Exception e){
				e.printStackTrace();
			}
		} 
		return result;
	}
	public static Date StringParseDate(String obj) {
		Date result = null;
		if (!StringNumberUtil.isNullOrEmpty(obj)){
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				result = sdf.parse(obj);
			}catch (Exception e){
				e.printStackTrace();
			}
		} 
		return result;
	}
	public static Date StringParseDate1(String obj) {
		Date result = null;
		if (!StringNumberUtil.isNullOrEmpty(obj)){
			try{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				result = sdf.parse(obj);
			}catch (Exception e){
				e.printStackTrace();
			}
		} 
		return result;
	}
	public static Date StringParseDate(String obj,String formStr) {
		Date result = null;
		if (!StringNumberUtil.isNullOrEmpty(obj)) {
			try{
				SimpleDateFormat sdf = new SimpleDateFormat(formStr);
				result = sdf.parse(obj);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static String getDateInfo(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		int week = c.get(Calendar.DAY_OF_WEEK);
		String weekStr = "";
		switch(week){
			case 1: weekStr = "星期日";break; 
			case 2: weekStr = "星期一";break; 
			case 3: weekStr = "星期二";break; 
			case 4: weekStr = "星期三";break; 
			case 5: weekStr = "星期四";break; 
			case 6: weekStr = "星期五";break; 
			case 7: weekStr = "星期六";break; 
		}
		
		return sdf.format(date) +"（"+ weekStr+"）";
	}
	public static String getDateTimeInfo(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		int week = c.get(Calendar.DAY_OF_WEEK);
		String weekStr = "";
		switch(week){
			case 1: weekStr = "星期日";break; 
			case 2: weekStr = "星期一";break; 
			case 3: weekStr = "星期二";break; 
			case 4: weekStr = "星期三";break; 
			case 5: weekStr = "星期四";break; 
			case 6: weekStr = "星期五";break; 
			case 7: weekStr = "星期六";break; 
		}
		SimpleDateFormat sfm = new SimpleDateFormat("HH:mm:ss");	
		weekStr=weekStr+" 时间："+sfm.format(date);
		return sdf.format(date) +"（"+ weekStr+"）";
	}
	public static void main(String[] args){
//		Date date = new Date();
//		Calendar c = Calendar.getInstance();
//		c.setTime(date);
//		System.out.println();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//		System.out.println(sdf.format(date));
//		int week = c.get(Calendar.DAY_OF_WEEK);
//		String weekStr = "";
//		switch(week){
//			case 1: weekStr = "星期日";break; 
//			case 2: weekStr = "星期一";break; 
//			case 3: weekStr = "星期二";break; 
//			case 4: weekStr = "星期三";break; 
//			case 5: weekStr = "星期四";break; 
//			case 6: weekStr = "星期五";break; 
//			case 7: weekStr = "星期六";break; 
//		}
//		
//		String abc = null;
//		System.out.println("22222222");
//		System.out.println(abc+"");
		String abc = "20040506";
		String result = "";
		if(abc!=null){
			String nyr  =dateFormat(StringParseDate(abc));
			result+=nyr.substring(0,5);
			String y1 = nyr.substring(5, 6);//月的第一位
			String r1 = nyr.substring(8,9);//日的第一位
			if(y1.equals("0")){
				result+=nyr.substring(6,8);
			}else{
				result+=nyr.substring(5,8);
			}
			if(r1.equals("0")){
				result+=nyr.substring(9);
			}else{
				result+=nyr.substring(8);
			}
			System.out.println(result);
		}
	}
	
	public static String dateFormat(Date date,String format){
		String result = "";
		if (!StringNumberUtil.isNullOrEmpty(date)){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			result = sdf.format(date);;
			
		} 
		return result;
	}
	
	/**
	 * 计算年龄
	 * @param birthDay
	 * @author shily 2014-8-22 10:20:55
	 */
	@SuppressWarnings("finally")
	public static  String getAge(Date birthDay){ 
	        String result = "";
	        try{
	        	Calendar cal = Calendar.getInstance(); 
	        	
	        	if(StringNumberUtil.isNullOrEmpty(birthDay))return result; 
	        	if (cal.before(birthDay)) { 
	        		throw new IllegalArgumentException( 
	        		"错误：出生日期大于当前日期！"); 
	        	} 
	        	
	        	int yearNow = cal.get(Calendar.YEAR); 
	        	int monthNow = cal.get(Calendar.MONTH)+1; 
	        	int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); 
	        	
	        	cal.setTime(birthDay); 
	        	int yearBirth = cal.get(Calendar.YEAR); 
	        	int monthBirth = cal.get(Calendar.MONTH); 
	        	int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH); 
	        	
	        	int age = yearNow - yearBirth; 
	        	
	        	if (monthNow <= monthBirth) { 
	        		if (monthNow == monthBirth) { 
	        			if (dayOfMonthNow < dayOfMonthBirth) { 
	        				age--; 
	        			} 
	        		} else { 
	        			age--; 
	        		} 
	        	}
	        	result = age + "";
	        }catch (Exception e){
	        	e.printStackTrace();
	        }finally{
	        	return result; 
	        }
	    } 

}

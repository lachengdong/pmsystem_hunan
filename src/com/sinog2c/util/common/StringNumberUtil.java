package com.sinog2c.util.common;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.swetake.util.Qrcode;

public class StringNumberUtil {

	/**
	 * 解析long, 不报错
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static long parseLong(String str, long defValue){
		long result = defValue;
		try{
			if(isLong(str)){
				str = str.replaceAll("^\\+", "");
				result = Long.parseLong(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 解析 int,
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int parseInt(String str, int defValue){
		int result = defValue;

		try{
			if(isLong(str)){
				str = str.replaceAll("^\\+", "");
				result = Integer.parseInt(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static int parseInt(Object obj, int defValue){
		//
		String str = String.valueOf(obj);
		return parseInt(str, defValue);
	}
	public static short parseShort(String str, int defValue){
		int result_int = parseInt(str, defValue);
		short result = 0;
		if(result_int < 9999 && result_int >= -9999){
			result = (short)result_int;
		} else {
			result = (short)defValue;
		}

		return result;
	}
	/**
	 * 是正负整数
	 * @param str
	 * @return
	 */
	public static boolean isLong(String str){
		boolean result = false;
		try{
			if(null != str && str.matches("^[\\+\\-]?\\d+$")){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 不为empty(空)
	 * @param str
	 * @return
	 */
	public static boolean notEmpty(Object str){
		boolean result = false;
		try{
			if(null != str && str.toString().trim().length() > 0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 为empty(空)
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object str){
		boolean result = false;
		try{
			if(null == str || str.toString().trim().length() <= 0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	/**
	 * @param Integer
	 * @return
	 */
	public static String numToChaNum(Integer num){
		String chaNum = "";
		switch(num){
			case 0: chaNum = "O";break;
			case 1: chaNum = "一";break;
			case 2: chaNum = "二";break;
			case 3: chaNum = "三";break;
			case 4: chaNum = "四";break;
			case 5: chaNum = "五";break;
			case 6: chaNum = "六";break;
			case 7: chaNum = "七";break;
			case 8: chaNum = "八";break;
			case 9: chaNum = "九";break;
			case 10: chaNum = "十";break;
		}
		return chaNum;
	}
	
	/**
	 * @param Integer
	 * @return
	 */
	public static String numStrToChaNum(String  numStr){
		
		String result = "";
		if(StringNumberUtil.notEmpty(numStr)){
			for(int i=0;i<numStr.length();i++){ 
				char ch = numStr.charAt(i); 
				result += numToChaNum(Integer.parseInt(String.valueOf(ch)));
			}
		}
		return result;
	}
	
	public static String removeLastStr(String obj,String lastStr) {	
		if(notEmpty(obj)){
			if(obj.endsWith(lastStr)){
				obj = obj.substring(0, obj.length()-lastStr.length());
			}
		}
		return obj;
	}
	

	public static boolean isNullOrEmpty(Object obj) {	
		return obj == null || "".equals(obj.toString())|| "null".equals(obj)||"undefined".equals(obj);
	}
	public static String toString(Object obj){
		if(obj == null) return ""; // 既然不使用String.valueOf(),那么 null 就直接返回 ""
		//if(obj == null) return GkzxCommon.NULL;
		return obj.toString().trim();
	}
	public static String join(Collection s, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator iter = s.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }
	
	//string 转换 int
	public static Integer getIntByString(String str)
	{
		if (str!=null&& !"".equals(str))
		{
			return Integer.valueOf(str);
		}
		return null;
	}
	
	
	//age 统计项
	public static String[] getArrayAge()
	{
		String[] arr_str={"不满18岁","18~25岁","26~35岁","36~50岁","51~60岁","61~64岁","65岁以上"};
		return arr_str;
	}
	//刑期  统计项
	public static String[] getArrayXingQi()
	{
		String[] arr_str={"不足2年","2年以上","4年以上","7年以上","10年以上","12年以上","15年以上","20年","无期","死缓"};
		return arr_str;
	}
	
	//刑期  统计项
	public static String[] getArrayQianke()
	{
		String[] arr_str={"无前科","有前科"};
		return arr_str;
	}
	
	//String返回值
	public static String returnString(String strValue) {
		if(!isNullOrEmpty(strValue)){
			Pattern p = Pattern.compile("\t|\r|\n");
			Matcher m = p.matcher(strValue);
			strValue = m.replaceAll("");
		}
		return isNullOrEmpty(strValue)?"":strValue;
	}
	
	//String返回值
	public static String returnString2(Object strObj) {
		return isNullOrEmpty(strObj)?"":strObj.toString().trim();
	}
	
	//String返回值  获取刑期
	public static String getXingqi(String cjiimprisontype,String xingqiStr) {
		String value ="";
		Boolean hasHeader = false; //"有期徒刑"是否存在
		Boolean hasYear = false; //"年"是否存在
		if(!isNullOrEmpty(xingqiStr)){
			if(!isNullOrEmpty(xingqiStr.replace(",", ""))){
				String[] xingqiStrs = xingqiStr.split(",");
				for(int i=0;i<xingqiStrs.length;i++){
					if(i==0){
						if(xingqiStrs[i].equals("9995")){
							value = "无期";
						}else if(xingqiStrs[i].equals("9996")){
							value = "死缓";
						}else if(xingqiStrs[i].equals("9997")){
							value = "死刑";
						}else{
							if(!"0".equals(xingqiStrs[i]) && !"00".equals(xingqiStrs[i])){
								hasHeader = true;
								hasYear = true;
								if(!"".equals(xingqiStrs[i])){
									value +="有期徒刑"+xingqiStrs[i]+GkzxCommon.YEAR;
								}else{
									value +="有期徒刑";
									hasYear = false;
								}
							}
						}
					}else if(i==1){
						if(!"0".equals(xingqiStrs[i]) && !"00".equals(xingqiStrs[i])&&!isNullOrEmpty(xingqiStrs[i])){
							if (hasHeader) {
								if (hasYear) { 
									//value += "又" + xingqiStrs[i]+GkzxCommon.MONTHS;
									value += xingqiStrs[i]+GkzxCommon.MONTHS;
								} else {
									value +=xingqiStrs[i]+GkzxCommon.MONTHS;
								}
							} else {
								hasHeader = false;
								value +="有期徒刑"+xingqiStrs[i]+GkzxCommon.MONTHS;
							}
						}
					}else if(i==2){
						if(!"0".equals(xingqiStrs[i]) && !"00".equals(xingqiStrs[i])&&!isNullOrEmpty(xingqiStrs[i])){
							if (hasHeader) {
								value +=xingqiStrs[i]+GkzxCommon.DAY;
							} else {
								value +="有期徒刑" + xingqiStrs[i]+GkzxCommon.DAY;
							}
						}
					}
				}
			}
		}
		return value;
	}
	
	//String返回值  获取刑期
		public static String getXingqi2(String cjiimprisontype,String xingqiStr) {
			String value ="";
			Boolean hasHeader = false; //"有期徒刑"是否存在
			Boolean hasYear = false; //"年"是否存在
			if(!isNullOrEmpty(xingqiStr)){
				if(!isNullOrEmpty(xingqiStr.replace(",", ""))){
					String[] xingqiStrs = xingqiStr.split(",");
					for(int i=0;i<xingqiStrs.length;i++){
						if(i==0){
							if(xingqiStrs[i].equals("9995")){
								value = "无期";
							}else if(xingqiStrs[i].equals("9996")){
								value = "死缓";
							}else if(xingqiStrs[i].equals("9997")){
								value = "死刑";
							}else{
								if(!StringNumberUtil.isNullOrEmpty(xingqiStrs[i])  && !"0".equals(xingqiStrs[i]) && !"00".equals(xingqiStrs[i])){
									hasHeader = true;
									hasYear = true;
									if(!"".equals(xingqiStrs[i])){
										value += numToChaNum(Integer.parseInt(xingqiStrs[i])) +GkzxCommon.YEAR;
									}else{
										value +="有期徒刑";
										hasYear = false;
									}
								}
							}
						}else if(i==1){
							if(!StringNumberUtil.isNullOrEmpty(xingqiStrs[i]) && !"0".equals(xingqiStrs[i]) && !"00".equals(xingqiStrs[i])&&!isNullOrEmpty(xingqiStrs[i])){
								if (hasHeader) {
									if (hasYear) { 
										//value += "又" + xingqiStrs[i]+GkzxCommon.MONTHS;
										value += numToChaNum(Integer.parseInt(xingqiStrs[i]))+GkzxCommon.MONTHS;
									} else {
										value += numToChaNum(Integer.parseInt(xingqiStrs[i])) +GkzxCommon.MONTHS;
									}
								} else {
									hasHeader = false;
									value += numToChaNum(Integer.parseInt(xingqiStrs[i])) +GkzxCommon.MONTHS;
								}
							}
						}else if(i==2){
							if(!StringNumberUtil.isNullOrEmpty(xingqiStrs[i])  && !"0".equals(xingqiStrs[i]) && !"00".equals(xingqiStrs[i])&&!isNullOrEmpty(xingqiStrs[i])){
								if (hasHeader) {
									value += numToChaNum(Integer.parseInt(xingqiStrs[i]))+GkzxCommon.DAY;
								} else {
									value +=  numToChaNum(Integer.parseInt(xingqiStrs[i]))+GkzxCommon.DAY;
								}
							}
						}
					}
				}
			}
			return value;
		}
		
	//String返回值  获取剥权
	public static String getBoquan(String xingqiStr) {
		String value ="";
		Boolean hasYear = false; //"年"是否存在
		if(!isNullOrEmpty(xingqiStr)){
			if(!isNullOrEmpty(xingqiStr.replace(",", ""))){
				String[] xingqiStrs = xingqiStr.split(",");
				for(int i=0;i<xingqiStrs.length;i++){
					if(i==0){
						if(xingqiStrs[i].equals("97")){
							value = "终身";
						}else{
							if(!"0".equals(xingqiStrs[i]) && !"00".equals(xingqiStrs[i])&&!isNullOrEmpty(xingqiStrs[i])){
								hasYear = true;
								if(!"".equals(xingqiStrs[i])){
									value += numToChaNum(Integer.parseInt(xingqiStrs[i]))+GkzxCommon.YEAR;
								}else{
									hasYear = false;
								}
							}
						}
					}else if(i==1){
						if(!"0".equals(xingqiStrs[i]) && !"00".equals(xingqiStrs[i])&&!isNullOrEmpty(xingqiStrs[i])){
							if (hasYear) { 
								value += numToChaNum(Integer.parseInt(xingqiStrs[i]))+GkzxCommon.MONTHS;
							} else {
								value += numToChaNum(Integer.parseInt(xingqiStrs[i]))+GkzxCommon.MONTHS;
							}
						}
					}else if(i==2){
						if(!"0".equals(xingqiStrs[i]) && !"00".equals(xingqiStrs[i])&&!isNullOrEmpty(xingqiStrs[i])){
							value += numToChaNum(Integer.parseInt(xingqiStrs[i]))+GkzxCommon.DAY;
						}
					}
				}
			}
		}
		return value;
	}
	//移除字符串前端的字符
	public static String removeFrontExpression(String str, char strRemove){
		String strExpression  = "^(" + strRemove + "+)";
		return str.replaceAll(strExpression, "");
	}
	
    /**
     * 判断是否是省局监外执行和省局减刑假释
     * @author:admin
     * @version：2015年6月10日10:22:59
     */
    public static boolean checkflowdefid(Map<Object, Object> objMap){
    	boolean flag = false;
    	if (objMap != null) {
			if ("other_sjjxjssp".equals(objMap.get("flowdefid")) || 
				"other_sjbwjysp".equals(objMap.get("flowdefid"))) {
				flag=true;
			}
		}
    	return flag;
    }
    
	/**
	 * 转移特殊字符
	 * @author <a href="mailto:tangjunfeng52099@gmail.com">tangjunfeng</a>
	 * @param value
	 * @return
	 */
	public static String stringFilterReplace(String value){
		if(value==null){
			return null;
		}
		return value.trim().replaceAll("<script[^>]*>(.|\\n)*<\\/script[^>]*>", "")
			.replaceAll("<style[^>]*>(.|\\n)*<\\/style[^>]*>", "")
			.replaceAll("<[^>]*>", "").replaceAll("&lt;[^(&gt;)]*&gt;", "").replaceAll("\"", "")
			.replaceAll("\'", "").replaceAll(" |&nbsp;|s|\\n|\\r", "").replace("@@@@@@@@@@", "");
	}
	/**
     * 此方法描述的是：置为null
     * @author: liuyi   
     * @version: 2013-09-06 19:35:50   
     */
	public static void setObjectNull(Object...objArr){
		for(Object obj : objArr){
			if(obj!=null){
				obj = null;
			}
		}
	}
	
	/**
	 * add by  
	 * @param num
	 * 传入参数数字字符串。
	 * @return
	 * 返回中文汉字
	 */
	public static String digit2speech(String original) {
		String result = "";
		// 整数部分
		String integerPart = "";
		// 小数部分
		String floatPart = "";
		String[] series = { "", "十", "百", "千", "万" }; // 计量单位
		String[] cnNumbers = { "", "一", "二", "三", "四", "五", "六", "七", "八", "九" }; // 大写数字
		if (original.contains(".")) {
			// 如果包含小数点
			int dotIndex = original.indexOf(".");
			integerPart = original.substring(0, dotIndex);
			floatPart = original.substring(dotIndex + 1);
		} else {
			// 不包含小数点
			integerPart = original;
		}
		// 因为是累加所以用StringBuffer
		StringBuffer sb = new StringBuffer();
		// 整数部分处理
		for (int i = 0; i < integerPart.length(); i++) {
			int number = getNumber(integerPart.charAt(i));
			sb.append(cnNumbers[number]);
			sb.append(series[integerPart.length() - 1 - i]);
		}
		// 小数部分处理
		if (floatPart.length() > 0) {
			sb.append("点");
			for (int i = 0; i < floatPart.length(); i++) {
				int number = getNumber(floatPart.charAt(i));
				sb.append(cnNumbers[number]);
			}
		}
		result = sb.toString();
		if (integerPart.length()==2){
			result = result.replace("一十", "十");
		}
		return result;
	}

	/** 
	 * 将字符形式的数字转化为整形数字 
	 * 因为所有实例都要用到所以用静态修饰 
	 * @param c 
	 * @return 
	 */
	private static int getNumber(char c) {
		String str = String.valueOf(c);
		return Integer.parseInt(str);
	}
	
	public  static void sprintf(StringBuffer result, String format, String...replace) {
		result.append(String.format(format, replace));
	}
	
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	public static String getCurrentDateStr(){
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	public static String getTrimRtnStr(String value){
		return value=value.trim().replaceAll("\"", "“").replaceAll("\"", "”").replaceAll("'", "‘").replaceAll("'", "’").replaceAll("\\\r\\\n", "&#13;&#10;").replaceAll("\\r\\n", "&#13;&#10;").replaceAll("\r\n", "&#13;&#10;").replaceAll("\r", "&#13;&#10;").replaceAll("\n", "&#13;&#10;");
	}
	/**
	 * 此方法描述的是：数据库 clob 类型转化string 放入json
	 * 
	 * @param name
	 * @param @return
	 * @Exception 异常对象
	 * @author: 李祺亮
	 * @version: 2013-5-10 上午10:30:23
	 */

	public static String clob2String(Clob clob) {
		try {
			return (clob != null ? clob.getSubString(1, (int) clob.length())
					: null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public  static Object returnStr(Object strValue,String patternStrValue) {
		Pattern p = Pattern.compile("\t|\r|\n");
		Matcher m = p.matcher(patternStrValue);
		if(m.matches()){
			m = p.matcher((String)strValue);
			strValue = m.replaceAll("");
		}
		//数据库里面的空格、换行
		if(patternStrValue.contains("\r\n")) strValue = patternStrValue.replaceAll("\r\n", "\\r\\n");
		return isNullOrEmpty(strValue)?"":strValue;
	}
	//返回当前日期到毫秒 年月日时分秒毫秒 共17位
	public static String getDateIdStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		return sdf.format(new Date());
	}
//	/**
//	 * 生成法院意见弹出窗口中的理由
//	 * @param map  存放的是罪犯的基本信息数据
//	 * @return
//	 */
//	public static String generateReasonByMap(Map row) {
//		String result = "";
//		List<String> list = new ArrayList<String>();
//		if(row!=null){
//			String cbiname = row.get("cbiname") == null ? "" : row.get("cbiname").toString(); //罪犯姓名
//			//从宽情节
//			String weichengnianfan = row.get("caiifcn") == null ? "" : row.get("caiifcn").toString(); //未成年犯
//			String laofan = row.get("laofan") == null ? "" : row.get("laofan").toString(); //老犯
//			String bingfan = row.get("bingfan") == null ? "" : row.get("bingfan").toString(); //病犯
//			String canfan = row.get("canfan") == null ? "" : row.get("canfan").toString(); //残犯
//			String ckqjqita = row.get("ckqjqita") == null ? "" : row.get("ckqjqita").toString(); //其他从宽情节
//			//从严情节
//			String xianzhijianxing = row.get("xianzhijianxing") == null ? "" : row.get("xianzhijianxing").toString(); //限制减刑
//			String caiifleifan = row.get("caiifleifan") == null ? "" : row.get("caiifleifan").toString(); //累犯
//			String dupingzf = row.get("dupingzf") == null ? "" : row.get("dupingzf").toString(); //毒品再犯
//			String duocifz = row.get("duocifz") == null ? "" : row.get("duocifz").toString(); //多次犯罪
//			String fszys = row.get("fszys") == null ? "" : row.get("fszys").toString(); //犯四罪以上
//			String yzwj = row.get("yzwj") == null ? "" : row.get("yzwj").toString(); //严重违纪
//			String szbf = row.get("szbf") == null ? "" : row.get("szbf").toString(); //数罪并罚
//			String zdxsf = row.get("zdxsf") == null ? "" : row.get("zdxsf").toString(); //重大刑事犯罪
//			String cyqjqita = row.get("cyqjqita") == null ? "" : row.get("cyqjqita").toString(); //其他从严情节
//			String zwfz = row.get("zwfz") == null ? "" : row.get("zwfz").toString(); //职务犯罪
//			String jrfz = row.get("jrfz") == null ? "" : row.get("jrfz").toString(); //金融犯罪
//			String sisheshehei = row.get("sisheshehei") == null ? "" : row.get("sisheshehei").toString(); //涉黑犯罪
//			
//			if("1".equals(weichengnianfan)){
//				list.add("属于未成年犯");
//			}
//			if("1".equals(laofan)){
//				list.add("属于老犯");
//			}
//			if("1".equals(bingfan)){
//				list.add("属于病犯");
//			}
//			if("1".equals(canfan)){
//				list.add("属于残疾犯");
//			}
//			if("1".equals(ckqjqita)){
//				list.add("存在其他从宽情节");
//			}
//			if("1".equals(xianzhijianxing)){
//				list.add("是被限制减刑的罪犯");
//			}
//			if("1".equals(caiifleifan)){
//				list.add("是累犯");
//			}
//			if("1".equals(dupingzf)){
//				list.add("是涉毒品再次犯罪的罪犯");
//			}
//			if("1".equals(duocifz)){
//				list.add("是多次犯罪的罪犯");
//			}
//			if("1".equals(fszys)){
//				list.add("是犯四罪以上的罪犯");
//			}
//			if("1".equals(szbf)){
//				list.add("是数罪并罚且有两罪在五年以上的罪犯");
//			}
//			if("1".equals(zdxsf)){
//				list.add("是重大刑事犯罪的罪罚");
//			}
//			if("1".equals(cyqjqita)){
//				list.add("存在其他从严情节");
//			}
//			if("1".equals(zwfz)){
//				list.add("属于职务犯罪");
//			}
//			if("1".equals(jrfz)){
//				list.add("属于金融犯罪");
//			}
//			if("1".equals(sisheshehei)){
//				list.add("属于涉黑犯罪");
//			}
//			
//			for(int i=0, n=list.size(); i<n; i++){
//				String str = list.get(i);
//				if(i<=n){//不是最后
//					if(i==0){//第一个
//						result += "鉴于罪犯" + cbiname + str;
//					}else{
//						result += "," + str;
//					}
//				}else{
//					result += "," + str + "。";
//				}
//			}
//		}
//		return result;
//	}
	
	public static String getDefaultStringOnNull(String strOrgValue, String strDefaultValue){
		String strValueString = strOrgValue;
		if (isNullOrEmpty(strOrgValue)) {
			strValueString = strDefaultValue;
		}
		return strValueString;
	}

	public static String getStringByMap(Map<String, Object> map,String field)
	 {
		String clobString="";
		 if(null==map.get(field)||"".equals(map.get(field).toString()))
		 {
			 return "";
		 }
		 else {
			 if(map.get(field)  instanceof oracle.sql.CLOB || map.get(field).toString().indexOf("oracle.sql.clob")>-1 || map.get(field).toString().indexOf("dm.jdbc.driver.DmdbClob")>-1) {
				Clob clob = (Clob)map.get(field);
				
				try {
					clobString = clob.getSubString(1, (int) clob.length());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return  clobString;
			 }
		 }
		return map.get(field).toString();	 
	 }
	
	 //get int 
	 @SuppressWarnings("unchecked")
	 public static short getShortByMap(Map<String, Object> map,String field)
	 {
		 if(null==map.get(field)||"".equals(map.get(field).toString()))
		 {
			 return 0;
		 }
		return Short.valueOf( map.get(field).toString() );	 
	 }
	 //get int 
	 @SuppressWarnings("unchecked")
	 public static Double getDoubleByMap(Map<String, Object> map,String field)
	 {
		 if(null==map.get(field)||"".equals(map.get(field).toString()))
		 {
			 return new Double(0);
		 }
		 return new Double(((BigDecimal) map.get(field)).doubleValue());	 
	 }
	 //get int 
	 @SuppressWarnings("unchecked")
	 public static Integer getIntegerByMap(Map<String, Object> map,String field)
	 {
		 if(null==map.get(field)||"".equals(map.get(field).toString()))
		 {
			 return new Integer(0);
		 }
		 return new Integer(((BigDecimal) map.get(field)).intValue());	 
	 }
	 //get int 
	 @SuppressWarnings("unchecked")
	 public static Long getLongByMap(Map<String, Object> map,String field)
	 {
		 if(null==map.get(field)||"".equals(map.get(field).toString()))
		 {
			 return new Long(0);
		 }
		 return new Long(((BigDecimal) map.get(field)).longValue());	 
	 }
	 //把map中date 转换成相应格式 
	 @SuppressWarnings("unchecked")
	public static Date getDate(Map<String, Object> map,String field,boolean istrue)
	 {
		 if(null==map.get(field)||"".equals(map.get(field).toString()))
		 {
			 return null;
		 }
		 else 
		 {
			 String format=GkzxCommon.DATEFORMAT;
			 if (istrue)
			{
				format=GkzxCommon.DATETIMEFORMAT;
			}
//			 SimpleDateFormat sdf=new SimpleDateFormat(format);//
			 Date dt=(Date)map.get(field);
			 return dt;
		 }
	 }
	 /**
	  * 
	      * 此方法描述的是：拼音转换
	 	 * @param   name  
	 	 * @param  @return 
	 	 * @Exception 异常对象
	      * @author: lxf   
	      * @version: 2014-8-24 下午06:26:47
	  */
	public static String toPinYin(String name){
		String value="";
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();  
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);  
		for(int i=0;i<name.length();i++){
			char m=name.charAt(i);
			String[] ttt;
			try {
				ttt = PinyinHelper.toHanyuPinyinStringArray(m,t3);
				for(String str:ttt){
					value=value+str;
				}
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	public static String formatCaseNo(String casenos){
		
		String result = "";
		String[] casenoArr = casenos.split(",");
		for(String tem:casenoArr){
			String[] tt = tem.split("-");
			if(tt.length==1){
				result += tt[0]+",";
			}else if(tt.length==2){
				int start = Integer.parseInt(tt[0]);
				int end = Integer.parseInt(tt[1]);
				for(int i=start;i<=end;i++){
					result +=i+",";
				}
			}
		}
		
		result = removeLastStr(result, ",");
		return result;
	}
	
	public static String formatCaseNo(String casenos,String year){
		casenos = casenos.replaceAll("，", ",");
		String result = "";
		String[] casenoArr = casenos.split(",");
		for(String tem:casenoArr){
			String[] tt = tem.split("-");
			if(tt.length==1){
				result += year+tt[0]+",";
//				result += "'"+year+tt[0]+"',";
			}else if(tt.length==2){
				int start = Integer.parseInt(tt[0]);
				int end = Integer.parseInt(tt[1]);
				for(int i=start;i<=end;i++){
					result +=year+i+",";
//					result +="'"+year+i+"',";
				}
			}
		}
		
		result = removeLastStr(result, ",");
		return result;
	}
	
	public static String formatCasenums(String casenums){
		casenums = casenums.replaceAll("，", ",");
		String result = "";
		String[] casenumsArr = casenums.split(",");
		for(String tem:casenumsArr){
			result += "'"+tem+"',";
		}
		
		result = removeLastStr(result, ",");
		return result;
	}
	
	/**
	 * 将类似   abc,efg,ddw等字符串格式化为: 'abc','efg','ddw'
	 * @param casenos
	 * @param year
	 * @return
	 */
	public static String formatString(String strObj, String splitStr){
		
		if(isEmpty(strObj)) return strObj;
		String[] strArr = strObj.split(splitStr);
		String result = "";
		for(String tem : strArr){
			result += "'"+tem+"',";
		}
		if(notEmpty(result)){
			result = removeLastStr(result, ",");
		}
		return result;
	}
	
	public static boolean belongTo(String depid, String strProvince){
		boolean isBelongTo = false;
		if (strProvince!=null&&strProvince.indexOf(depid) > -1) {
			isBelongTo = true;
		}
		return isBelongTo;
	}
	/*
	 * 生成[start,end]之间的count条随机整数
	 */
	public static Integer[] getRandomData(int start,int end,int count){
		Set<Integer> set = new HashSet<Integer>();
		while(set.size()<count){
			Integer a = (int)(start+Math.random()*(end-start+1));
			set.add(a);
		}
		Object[] objarr = set.toArray();
		Integer[] intArr = new Integer[objarr.length];
		for(int i=0;i<objarr.length;i++){
			intArr[i]=Integer.valueOf(objarr[i].toString());
		}
		return intArr;
	}
	 /**
	  * @param key cookie属性名
	  * @param value cookie保存值
	  * @param time cookie过期时间
	  * @param response
	  */
	 public static void addCookie(String key,String value,int time,HttpServletResponse response){
		 try{
			 if(!isEmpty(key)&&!isEmpty(value)&& response!=null){
				 Cookie cookie = new Cookie(key, value);
            	cookie.setMaxAge(time); 
            	response.addCookie(cookie);
			 }
        }catch(Exception e){
            e.printStackTrace();
        }
	 }
	 /**
	  * 获取案件号拼接sql
	  * @param year
	  * @param anjianhao
	  * @param columnname
	  * @return
	  */
	 public static String getYearCaseNumberSql(String year,String anjianhao, String columnname){
			String[] anjianhaoArr = anjianhao.split("[,，]");
			String sql = "";
			for(int i=0, n=anjianhaoArr.length; i<n; i++){
				String anjianhaoStr = anjianhaoArr[i];
				if(i==0){
					if(anjianhaoStr.contains("-")){
						String[] arr = anjianhaoStr.split("-");
						//sql += "to_number("+columnname + ") between " + year+arr[0] + " and " + year+arr[1];
						sql += "(substr("+ columnname +",0,4) = " + year + " and substr(" + columnname + ",5) between " + arr[0] + " and " + arr[1] + ")";
					}else{
						sql += "to_number("+columnname + ")=" + year+anjianhaoStr;
					}
				}else{
					if(anjianhaoStr.contains("-")){
						String[] arr = anjianhaoStr.split("-");
						//sql += " or to_number(" + columnname + ") between " + year+arr[0] + " and " + year + arr[1]+" ";
						sql += " or (substr("+ columnname +",0,4) = " + year + " and substr(" + columnname + ",5) between " + arr[0] + " and " + arr[1] + ") ";
					}else{
						sql += " or to_number(" + columnname + ")=" + year + anjianhaoStr +" ";
					}
				}
			}
			return sql;
		}
	 /**
		 * 返回字符串的长度 （插入表时使用）
		 * @param casenos
		 * @param year
		 * @return
		 */
	 public static double  returnStrLength(String strObj){
		double valueLength = 0;    
		String chinese = "[\u4e00-\u9fa5]";  
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1     
		for (int i = 0; i < strObj.length(); i++) {    
			// 获取一个字符     
			String temp = strObj.substring(i, i + 1);    
			// 判断是否为中文字符     
			if (temp.matches(chinese)) valueLength += 1;  // 中文字符长度为1
			else valueLength += 0.5;   // 其他字符长度为0.5 
		}    

		
		return Math.ceil(valueLength);
	}
	 
	/*
	 * 0 指前面补充零
	 * formatLength 字符总长度为 formatLength
	 * d 代表为正数。
	 */
	 public static String frontCompWithZore(int sourceDate,int formatLength)
	 {
	  String newString = String.format("%0"+formatLength+"d", sourceDate);
	  return  newString;
	 }
	 
	 public static List<String> formatString2List(String str,String splitStr){
		 if(notEmpty(str)){
			 String[] strs = str.split(splitStr);
			 List<String> list = new ArrayList<String>();
			 if(null != strs && strs.length>0){
				 for(String tem : strs){
					 list.add(tem);
				 }
				 return list;
			 }
		 }
		 return null;
	 }
	 
	 public static String formatList2String(List<String> list,String splitStr){
		 String result = "";
		 StringBuffer sbf = new StringBuffer();
		 if(null != list && list.size()>0){
			 for(String str : list){
				 sbf.append(str).append(splitStr);
			 }
			 result = sbf.toString();
			 if(result.endsWith(splitStr)){
				 result = removeLastStr(result, splitStr);
			 }
		 }
		 return result;
	 }
		 
	public static String generateReasonByMap(Map<String, Object> row) {
		String result = "";
		List<String> list = new ArrayList<String>();
		if(row!=null){
			String cbiname = row.get("NAME") == null ? "" : row.get("NAME").toString(); //罪犯姓名
			//从宽情节
			String weichengnianfan = row.get("ISMINOR") == null ? "" : row.get("ISMINOR").toString(); //未成年犯
			String laofan = row.get("OLDPRISONER") == null ? "" : row.get("OLDPRISONER").toString(); //老犯
			String bingfan = row.get("ILLPRISONER") == null ? "" : row.get("ILLPRISONER").toString(); //病犯
			String canfan = row.get("DEFORMITYPRISONER") == null ? "" : row.get("DEFORMITYPRISONER").toString(); //残犯
			String ckqjqita = row.get("WIDEPLOTS") == null ? "" : row.get("WIDEPLOTS").toString(); //其他从宽情节
			//从严情节
			String xianzhijianxing = row.get("ISLIMIT") == null ? "" : row.get("ISLIMIT").toString(); //限制减刑
			String caiifleifan = row.get("ISRECIDIVISM") == null ? "" : row.get("ISRECIDIVISM").toString(); //累犯
			String dupingzf = row.get("REDRUGS") == null ? "" : row.get("REDRUGS").toString(); //毒品再犯
			String duocifz = row.get("CRIMES") == null ? "" : row.get("CRIMES").toString(); //多次犯罪
			String fszys = row.get("OVERFOURCRIMES") == null ? "" : row.get("OVERFOURCRIMES").toString(); //犯四罪以上
			String yzwj = row.get("SERIOUSVIOLATIONS") == null ? "" : row.get("SERIOUSVIOLATIONS").toString(); //严重违纪
			String szbf = row.get("COMBINEDPUNISHMENT") == null ? "" : row.get("COMBINEDPUNISHMENT").toString(); //数罪并罚
			String zdxsf = row.get("MAJORCRIMINAL") == null ? "" : row.get("MAJORCRIMINAL").toString(); //重大刑事犯罪
			String cyqjqita = row.get("STRICTLYPLOT") == null ? "" : row.get("STRICTLYPLOT").toString(); //其他从严情节
			String zwfz = row.get("POSTCRIME") == null ? "" : row.get("POSTCRIME").toString(); //职务犯罪
			String jrfz = row.get("UNDERMINE") == null ? "" : row.get("UNDERMINE").toString(); //金融犯罪
			String sisheshehei = row.get("UNDERWORLD") == null ? "" : row.get("UNDERWORLD").toString(); //涉黑犯罪
			
			if("1".equals(weichengnianfan)){
				list.add("犯罪时未满十八周岁");
			}
			if("1".equals(laofan)){
				list.add("属于老犯");
			}
			if("1".equals(bingfan)){
				list.add("属于病犯");
			}
			if("1".equals(canfan)){
				list.add("属于残疾犯");
			}
			if("1".equals(ckqjqita)){
				list.add("存在其他从宽情节");
			}
			if("1".equals(xianzhijianxing)){
				list.add("是被限制减刑的罪犯");
			}
			if("1".equals(caiifleifan)){
				list.add("是累犯");
			}
			if("1".equals(dupingzf)){
				list.add("是涉毒品再次犯罪的罪犯");
			}
			if("1".equals(duocifz)){
				list.add("是多次犯罪的罪犯");
			}
			if("1".equals(fszys)){
				list.add("是犯四罪以上的罪犯");
			}
			if("1".equals(szbf)){
				list.add("是数罪并罚且有两罪在五年以上的罪犯");
			}
			if("1".equals(zdxsf)){
				list.add("是严重暴力犯罪的罪犯");//2015-04-13 将"是重大刑事犯罪的罪犯"改为"是严重暴力犯罪的罪犯"
			}
			if("1".equals(cyqjqita)){
				list.add("存在其他从严情节");
			}
			if("1".equals(zwfz)){
				list.add("属于职务犯罪");
			}
			if("1".equals(jrfz)){
				list.add("属于金融犯罪");
			}
			if("1".equals(sisheshehei)){
				list.add("属于涉黑犯罪");
			}
			
			for(int i=0, n=list.size(); i<n; i++){
				String str = list.get(i);
				if(i<=n){//不是最后
					if(i==0){//第一个
						result += "鉴于罪犯" + cbiname + str;
					}else{
						result += "，" + str;
					}
				}else{
					result += "，" + str + "。";
				}
			}
		}
		return result;
	}
	
	 /**
     * 字符数组反转
     * @param array
     * @return
     */
    public static String[] reverseArray(String[] array) {
    	String temp;
        for (int i = 0; i < array.length / 2; i++) {
            temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
        return array;
    }
    
	/**
     * 类似js的join
     * @param o
     * @param flag
     * @return
     */
    public static String join( Object[] o , String flag , int indexnum){
        StringBuffer str_buff = new StringBuffer();
       if(indexnum<0){
    	   indexnum=0;
       }
        for(int i=indexnum , len=o.length ; i<len ; i++){
            str_buff.append( String.valueOf( o[i] ) );
            if(i<len-1)str_buff.append( flag );
        }
      
        return str_buff.toString(); 
    }
    
    /**
	 * 描述：将日期转换为汉字显示方法 
	 * @param date String类型
	 * @return 2009-9-9 转换为 二〇〇九年九月九日
	 */
    public static String formatDateUpper(String date)
	{
		String retDate=date;
		if(retDate != null)
		{
			retDate=retDate.replaceAll("0", "〇");
			retDate=retDate.replaceAll("1", "一");
			retDate=retDate.replaceAll("2", "二");
			retDate=retDate.replaceAll("3", "三");
			retDate=retDate.replaceAll("4", "四");
			retDate=retDate.replaceAll("5", "五");
			retDate=retDate.replaceAll("6", "六");
			retDate=retDate.replaceAll("7", "七");
			retDate=retDate.replaceAll("8", "八");
			retDate=retDate.replaceAll("9", "九");
		}
		return retDate;
	}
    /**
     * 描述：表单上挂系统模板的数据：从文本域上获取的值，先处理回符车等特殊符号
     * @author YangZR	
     * @return
     */
    public static String dealCharForForm(String content){
    	content = content.replaceAll("\"", "＂");// 把双引号替换成全角的双引号
    	content = content.replaceAll("'", "‘");// 把单引号替换成全角的单引号
    	content = content.replace("\r", "");// 特殊字符处理
		content = content.replace("\n", "\\n");// 回车符转换
		return content;
    }
    
    public static Map dealMapForForm(Map map){
    	if(null ==map || map.size()<=0){
    		return map;
    	}
    	Set<String> set = map.keySet();
    	for(String key : set){
    		Object valueObj = map.get(key);
    		String value = "";
    		if(null != valueObj){
    			if(valueObj instanceof Clob){
    				Clob clob = (Clob)valueObj;
					try {
						value = clob.getSubString(1, (int)clob.length());
					} catch (SQLException e) {
						e.printStackTrace();
					}
//    				map.put(key,value.replace("&#13;&#10;", "\\r\\n").replace("rn", "\\r\\n").replace("\r\n", "\\r\\n"));
    			}else{
    				value = valueObj.toString();
    			}
    			value = dealCharForForm(value);
    		}
    		
    		map.put(key, value);
    	}
    	return map;
    	
    }
    
    /**
     * 描述：系统模板特殊符号数据处理
     * @author YangZR	
     * @return
     */
    public static String dealCharForSysTemplate(String content){
    	content = content.replaceAll("\\r\\n", "\n");
    	content = content.replace("\r", "");// 特殊字符处理
    	content = content.replaceAll("\"", "＂");//把双引号替换成全角的双引号。
    	return content;
    }
    
    
    /**
     * 系统模板中去掉[姓名]、[年龄]之类的数据
     * @param content
     * @return
     */
    public static String removeBracketContent(String content){
    	
    	Pattern pattern = Pattern.compile("\\[(.*?)\\]");//获取中括号之间的内容
        Matcher matcher = pattern.matcher(content);
        //
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        while(matcher.find()){
        	Map<String, String> map = new HashMap<String, String>();
        	map.put("fieldname", matcher.group(1));
        	list.add(map);
        }
        //
        for(int i=0; i<list.size(); i++){
        	String con = "\\[" + (list.get(i)).get("fieldname") + "\\]";
        	content = content.replaceAll(con, " ");
        }
        //
    	return content;
    	
    }
    
    
    /**
     * 将Set中的元素排序，并输出list
     * @author YangZR	2015-03-29
     * @param set
     * @return
     */
    public static List<String> sortSet(Set<String> set){
		List<String> list = new ArrayList<String>();
		list.addAll(set);
		Collections.sort(list);
		return list;
    }
    
	public static List<Map> getStringListData(List<Map> lowerListData)throws Exception {
		for(int j=0,m=lowerListData.size(); j<m; j++){
			Map map = lowerListData.get(j);
			Set<String> keys  = map.keySet();
			Iterator<String> iteratorK = keys.iterator();
			while (iteratorK.hasNext()) {
				String key = (String) iteratorK.next();
				Object value = map.get(key);
				if(null == key || null == value){
					continue;
				}else if(value instanceof oracle.sql.CLOB || value.toString().toLowerCase().indexOf("oracle.sql.clob")>-1) {
					Clob clob = (Clob)value;
					String clobString = clob.getSubString(1, (int) clob.length());
					lowerListData.get(j).put(key, clobString);
				}else{
					continue;
				}
			}
		}
		return lowerListData;
	}
	    
		
	public static String getCodeSearchByOrgLevel(String level){
		if(null == level ){
			return null;
		}
		int lv = Integer.parseInt(level);
		switch(lv){
		case 2:return "sj";
		case 3:return "jy";
		case 4:return "jy";
		case 5:return "jy";
		case 6:return "fy";
		case 7:return "fy";
		}
		return null;
	}
	
	/**
	 * 描述：罪犯家属的查询码
	 * @author YangZR 2015-08-25
	 * @param  crimid: 罪犯编号， cardId：家属身份证号 		pinyin ：罪犯姓名拼音的前两位
	 */
	public static String getQRCode(String crimid, String cardId, String pinyin){
		
		String qrCode = "";// 根据罪犯拼音首字母、编号、家属生日随机生成的查询码
		
		String num1 = crimid.substring(6);// 从罪犯编号截取字符串
		String num2 = crimid.substring(4);
		// 从家属身份证号截取字符串
		if(StringNumberUtil.notEmpty(cardId) && cardId.length()>=15){
			num2 = cardId.substring(8, 14);
		}
		
		if(!num2.matches("^[0-9]*$")){//如果取的不是数字串就不处理
			return null;
		}
		List<String> list = new ArrayList<String>();
		
		for (int i = 0; i < num1.length(); i++) {
			list.add(num1.substring(i, i + 1));
		}
		
		if(StringNumberUtil.notEmpty(num2)){
			for (int i = 0; i < num2.length(); i++) {
				list.add(num2.substring(i, i + 1));
			}
		}
		
		Collections.shuffle(list);
		qrCode = pinyin + turnListToString(list);
		//System.out.println(">>>>>>>>>>>>>>>>>>>>"+qrCode);
		return qrCode;
		
	}
	/**
	 * 描述：罪犯家属的查询码
	 * @author zhenghui 2015-11-22
	 * @param  content 二维码内容
	 */
	public static String encoderQRCode(String content,String FilePath) {
		String returnval = new String();
		try {
			Qrcode qrcodeHandler = new Qrcode();
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			qrcodeHandler.setQrcodeVersion(7);
			//微官网地址：如http://
			byte[] contentBytes = content.getBytes("gb2312");
			BufferedImage bufImg = new BufferedImage(140, 140,BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, 140, 140);
			// 设定图像颜色> BLACK
			gs.setColor(Color.BLACK);
			// 设置偏移量 不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容> 二维码
			if (contentBytes.length > 0 && contentBytes.length < 120) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			} else {
				System.err.println("QRCode content bytes length = "+ contentBytes.length + " not in [ 0,120 ]. ");
			}
			gs.dispose();
			bufImg.flush();
			File imgFile = new File(FilePath);
			// 生成二维码QRCode图片
			ImageIO.write(bufImg, "png", imgFile);
			//读取文件转为 二进制码
			byte[] bytes = FileUtil.getBuffer(FilePath);
			//把二进制的字符流通过base64进行转化保存到数据库中
			returnval = DatatypeConverter.printBase64Binary(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnval;
 }
	/**
	 * 方法描述：list集合转为字符串
	 * @param list
	 * @return
	 */
	public static String turnListToString(List list) {
		String listString = "";
		for (int i = 0; i < list.size(); i++) {
			listString = listString + list.get(i);
		}
		return listString;
	}
	public static void Export2Excel( String excelPath, String fileName, List<Map<String,Object>> dataList,  String sheetTitle) {
        try {
        	File file=new File(excelPath+fileName+".xls");
			if(file.exists()){
				file.delete();
			}
			int sheetNum = 1;
			int sheetSize = 50000;//规定每个sheet所含数据量
			int dataLength = dataList.size();//数据条数
			if (dataLength % sheetSize > 0) {
				sheetNum = dataLength / sheetSize + 1;
			} else {
				sheetNum = dataLength / sheetSize;
			}
            WritableWorkbook book = Workbook.createWorkbook(file);
            for(int k=0; k<sheetNum; k++){
            	WritableSheet sheet = book.createSheet("sheet"+k, k);
            	for(int i=k*sheetSize, n=(k+1)*sheetSize; i<n; i++){
            		if(i<dataLength){
	                 	Map nameMap = dataList.get(i);
	                 	Iterator iter = nameMap.entrySet().iterator();
	                 	int j=0;
	                 	if(i%sheetSize==0){//第一行数据需要取key放列头
	                 		while (iter.hasNext()) {
	                     		Entry entry = (java.util.Map.Entry)iter.next();
	                          	String columnName = entry.getKey().toString();
	                          	String content = entry.getValue()==null?"":entry.getValue().toString();
	                          	sheet.setColumnView(100, 400);
	                            Label titleLabel = new Label(j, 0, columnName);//列表头单元格
	                            sheet.addCell(titleLabel);
	                            Label contentLabel = new Label(j, (i+1)-(k*sheetSize), content);//内容
	                            sheet.addCell(contentLabel);
	                          	j = j + 1;
	                         }
	                 	}else{
	                 		while (iter.hasNext()) {
	                     		Entry entry = (java.util.Map.Entry)iter.next();
	                          	String content = entry.getValue()==null?"":entry.getValue().toString();
	                          	sheet.setColumnView(100, 400);
	                            Label contentLabel = new Label(j, (i+1)-(k*sheetSize), content);//内容
	                            sheet.addCell(contentLabel);
	                          	j = j + 1;
	                         }
	                 	}
            		}
                 }
            }
            book.write();
            book.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	
        }
    }	
 
	public static byte[] getDownloadFile(String filePath){
	    File file = new File(filePath);
	    byte[] downLoadFile = null;
	    
	    InputStream is = null;
	    String strMethodName = Thread.currentThread().getStackTrace()[1].getMethodName() ;
		try {
			is = new FileInputStream(file);
			downLoadFile = new byte[is.available()];
			is.read(downLoadFile,0,downLoadFile.length);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return downLoadFile;
	}
 
	// 缓存
	private static Properties _jyconfig = null;
	/**
	 * 
	 * @param name 配置参数名
	 * @param defVal 默认值
	 * @return
	 */
	public static String getJyConfigValue(String name, String defVal){
		if(null == name || name.trim().isEmpty()){
			return defVal;
		}
		//
		if(null == _jyconfig){
			_jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			if(null == _jyconfig){
				return defVal;
			}
		}
		String provincecode = _jyconfig. getProperty(name);
		if(null == provincecode){
			provincecode = defVal;
		}
		return provincecode.trim();
	}
	
	/**
	 * @describe 将xxxx年xx月xx日转化为yyyyMMdd
	 * @return
	 */
	public static String formatChsDate2Datestr(String chsDate){
		if(StringNumberUtil.notEmpty(chsDate) && chsDate.indexOf("年") >=0 && chsDate.indexOf("月") >=0 && chsDate.indexOf("日") >=0){
			int yearIndex = chsDate.indexOf("年");
			int monthIndex = chsDate.indexOf("月");
			int dayIndex = chsDate.indexOf("日");
			String year = chsDate.substring(0, yearIndex);
			String month = chsDate.substring(yearIndex+1 , monthIndex);
			if(month.length() == 1){
				month = "0"+month;
			}
			String day = chsDate.substring(monthIndex+1, dayIndex);
			if(day.length()==1){
				day = "0" + day;
			}
			return year + month + day;
		}
		return null;
	}
	
	
	/**
	 * @author YangZR	2015-11-09
	 * @身份证验证 	要么是15位，要么是18位，最后一位可以为字母，其它为数字
	 * @param	personid身份证号
	 * @return 验证通过返回true，验证失败返回false
	 */
	public static boolean personidValidation(String personid){//		JS-- /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		Matcher idNumMatcher = idNumPattern.matcher(personid);
		return idNumMatcher.matches();
	}
	
	/**
	 * 获取wsdl访问url串，根据ip，断口，访问路径。
	 * 例如："http://192.168.1.222:8888/pmsystem/ws/pmsystem?wsdl";
	 */
	public static String getWsdlUrl(String ip, String port, String projectPath){
		if("1".equals(port)){//如果配置的是1则采用网址形式进行拼接
			return GkzxCommon.HTTP+GkzxCommon.COLON+GkzxCommon.DOUBLESEPALINE+ip+GkzxCommon.SINGLESEPALINE+projectPath+GkzxCommon.WENHAO+GkzxCommon.WSDL;
		}else{
			return GkzxCommon.HTTP+GkzxCommon.COLON+GkzxCommon.DOUBLESEPALINE+ip+GkzxCommon.COLON+port+GkzxCommon.SINGLESEPALINE+projectPath+GkzxCommon.WENHAO+GkzxCommon.WSDL;
		}
	}
		
	public static String getStrFromMap(String key,Map<String,Object> map){
		return map.get(key) == null ? "" : map.get(key).toString().trim();
	}
	
	
	
	
	
	
	
	/*  
	  * 判断是否为整数   
	  * @param str 传入的字符串   
	  * @return 是整数返回true,否则返回false   
	*/  
	public static boolean isInteger(String str) {
		if(StringNumberUtil.isEmpty(str)) return false;
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");     
		return pattern.matcher(str).matches();     
	}
	/*   
	  * 判断是否为浮点数，包括double和float   
	  * @param str 传入的字符串   
	  * @return 是浮点数返回true,否则返回false   
	*/     
	public static boolean isDouble(String str) {
		if(StringNumberUtil.isEmpty(str)) return false;
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");     
		return pattern.matcher(str).matches();     
	} 
	
	public static String removeFirstStr(String obj,String firstStr) {	
		if(notEmpty(obj)){
			if(obj.startsWith(firstStr)){
				obj = obj.substring(firstStr.length());
			}
		}
		return obj;
	}
	
	public static String subStringFlagEnd(String obj, String flag){
		obj = obj.substring(0,obj.indexOf(flag)).trim();
		return obj;
	}
	public static String subStringFlagBegin(String obj, String flag){
		obj = obj.substring(obj.indexOf(flag)+flag.length()).trim();
		return obj;
	}
	
	public static Set<String> strArr2Set(String[] strArr){
		Set<String> set = new HashSet<String>();
		for(String str : strArr){
			set.add(str);
		}
		return set;
	}
	
	
	
	
	/**
	 * 将数据：120603转成12年6个月3天，603转成6个月3天，999600代表死缓，999500代表无期
	 * @param valueInt
	 * @return
	 */
	public static String parseYearMonthDay(int valueInt){
		String value = "";
		if(999600==valueInt){
			value = "死缓";// 死刑缓期二年执行
		}else if(999500==valueInt){
			value = "无期";//无期徒刑
		}else if(0==valueInt){
			value = "空";//
		}else{
			int year = valueInt/10000;
			int month = (valueInt - year*10000)/100;
			int day = valueInt - year*10000 - month*100;
			if(year>0){
				value += year + "年";
			}
			if(month>0){
				value += month + "个月";
			}
			if(day>0){
				value += day + "天";
			}
		}
		
		return value;
	}
	
	
	
	public static String parseNumOfDate(String num){
		if(null != num && num.length() == 2){
			String temp = num.substring(0, 1);
			if(temp.equals("0")){
				num = num.substring(1);
			}
		}
		return num;
	}
	
	
	public static void main(String[] args) {
		
		String abc = parseNumOfDate("02");
		System.out.println(abc);
		
		List<Map<String,Object>> ddd = new ArrayList<Map<String,Object>>();
	}
	
	
//	public static void getMapFromList(List<Map<String,Object>> list, String crimid){
//		
//		for(Map<String,Object> map : list){
//			map.get(key)
//		}
//		
//	}
	
}


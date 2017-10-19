package com.gkzx.util.property;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.gkzx.common.GkzxCommon;
/**
 * 加密算法
 * @author fengyuanyuan
 *  2009-09-10
 */
public class DigestInfor{
	
	/**
	 * 获得加密后的二进制信息
	 * @param text
	 * @return
	 */
	public static byte[] getDigestText(String text){
		MessageDigest alga = null;
		try {
			alga = MessageDigest.getInstance("SHA-1");//确定计算方法
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	      alga.update(text.getBytes());//添加要进行计算摘要的信息
	      byte[] digesta=alga.digest();//计算出摘要
	      return digesta;
	}
	
	
	/**
	 * 通过某中方式传给其他人你的信息(myinfo)和摘要(digesta) 对方可以判断是否更改或传输正常
	 * 对比信息是否一致
	 * @param myinfor 输入信息
	 * @param digesta 加密后的信息
	 * @return
	 */
	  public boolean testDigest(String myinfor, byte[] digesta) {
		boolean boo = false;
		try {
			MessageDigest algb = MessageDigest.getInstance("SHA-1");
			algb.update(myinfor.getBytes());
			if (MessageDigest.isEqual(digesta, algb.digest())) {
				boo = true;
			} else {
				boo = false;
			}
		} catch (java.security.NoSuchAlgorithmException ex) {
			boo = false;
		}
		return boo;
	}
	  
	  /**
	   * 二行制转字符串
	   * @param b
	   * @return
	   */
	  private static String byte2hex(byte[] b) 
	    {
	     String hs="";
	     String stmp="";
	     for (int n=0;n<b.length;n++)
	      {
	       stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
	       if (stmp.length()==1) hs=hs+GkzxCommon.ZERO+stmp;
	       else hs=hs+stmp;
	       if (n<b.length-1)  hs=hs+":";
	      }
	     return hs.toUpperCase();
	    }
	  /**
	   * 获得加密后的二进制信息
	   * @param text
	   * @return
	   */	
	  /**  
	     * 两个时间之间的小时数
	     *   
	     * @param date1  
	     * @param date2  
	     * @return  
	     */  
	    public static long getHours(Date date1, Date date2) {   
	        // 转换为标准时间   
	        long hours = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000*24);   
	        return hours;   
	    }   
	    /**  
	     * 转换表单可显示格式
	     *   
	     * @param 
	     * @return  
	     */  
	    public static Object returnStr(Object value,String type){
	    	Calendar c=Calendar.getInstance();
	    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy");//yyyy
			
	    	//普通中文日期 2013年11月11日  date
	    	if("date".equalsIgnoreCase(type)){
	    		value = sdf.format(value);
	    	}
			//普通1999年12月
	    	if("ymdate".equalsIgnoreCase(type)){
	    		value = sdf.format(value);
	    	}
			//中文小写日期二〇一三年十一月二十三日
	    	if("chinadate".equalsIgnoreCase(type)){
	    		value = formatDate(value,type);
	    	}
			//中文大写日期 贰零壹叁年玖月玖日
	    	if("chinabigdate".equalsIgnoreCase(type)){
	    		value = formatDate(value,type);
	    	}
			//年份
	    	if("year".equalsIgnoreCase(type)){
	    		value = sdf.format(value);
	    	}
			//中文年份二〇一三年
	    	if("chinayear".equalsIgnoreCase(type)){
	    		value = numUpper(String.valueOf(c.get(c.YEAR)));
	    	}
			//中文大写年份 贰零壹叁年
	    	if("chinayear".equalsIgnoreCase(type)){
	    		value = numToZh(String.valueOf(c.get(c.YEAR)));
	    	}
	    	
	    	return value;
	    }
	    
	    /**
		 * 将日期转换为汉字显示方法 
		 * @param date String类型
		 * @return 2009-9-9 转换为 二〇〇九年九月九日
		 */
		public static Object formatDate(Object date,String type)
		{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd
			String retDate=sdf.format((Date)date);
			String[] parts = null;
			parts = retDate.split("-");
			if(retDate != null)
			{
				for(int i = 0; i < parts.length; i++){
					int strLength = parts[i].length();
					if("chinadate".equalsIgnoreCase(type)){
						parts[i] = numUpper(parts[i]);
					}else if("chinabigdate".equalsIgnoreCase(type)){
						parts[i] = numToZh(parts[i]);
					}
					if(i != 0 && strLength > 1){
						parts[i]=parts[i].substring(0, 1).replaceAll("一", "十") + parts[i].substring(1, strLength);
						parts[i]=parts[i].substring(0, 1).replaceAll("二", "二十") + parts[i].substring(1, strLength);
						parts[i]=parts[i].substring(0, 1).replaceAll("三", "三十") + parts[i].substring(1, parts[i].length());
						parts[i]=parts[i].replaceAll("〇", "");
					}
				}
				retDate = parts[0] + "年" + parts[1] + "月" + parts[2] + "日";
			}
			return retDate;
		}
		
		/**
		 * 说明：2013 =>二〇一三
		 * @param num 阿拉佰数字
		 * @return 大写数字
		 * @author 郑
		 * @date Jun 3, 2013  7:53:35 PM
		 */
		public static String numUpper(String num){
			String retDate=num;
			if(retDate != null){
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
		 * 说明：2013 => 贰零壹叁
		 * @param num 阿拉佰数字
		 * @return 中文字符串
		 * @author 郑
		 * @date Jun 3, 2013  7:59:02 PM
		 */
		public static String numToZh(String num){
			String zh = "";
		    char c[]={'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'};  
		    for(int count=0;count<num.length();count++){    
				switch (Integer.parseInt(num.substring(count,count+1))){    
						case 1:zh+=c[1];break;
						case 2:zh+=c[2];break;    
						case 3:zh+=c[3];break;
						case 4:zh+=c[4];break;    
						case 5:zh+=c[5];break;
						case 6:zh+=c[6];break;    
						case 7:zh+=c[7];break;
						case 9:zh+=c[9];break;
						case 8:zh+=c[8];break;   
						case 0:zh+=c[0];break;
						default:break;      
				}
			} 
			return zh;
		}
}

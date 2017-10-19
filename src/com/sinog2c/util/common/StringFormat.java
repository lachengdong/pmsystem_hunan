package com.sinog2c.util.common;

import aj.org.objectweb.asm.Label;

/**
 * 文字大于5个字符进行换行
 * @author mushuhong
 *
 */
public class StringFormat {
    public static void main(String[] args) {
		Object obj = "大发大发生地方发的噶是的法师打发斯蒂芬";
		System.out.println(StringFormat.stringFormat(obj));
	}
    @SuppressWarnings("all")
    public static Object stringFormat (Object obj){
    	Object result = "";
    	
    	String sydata="";//当前值
    	String xydata="";//剩余值
    	int i = 0;
		if (obj != null && !"".equals(obj)) {
			Label:for (;;) {
				String value = obj.toString();
    			if (value.length() > 5) {
    				if (i == 0) {
        				//初始值
        				result = value.substring(0, 5)+"\n";
        				i++;
					}else{
						//剩余值 
						sydata = value.substring(i*5, value.length());
						//剩余值 >5那么继续 截取 换行
						if (sydata.length() > 5) {
							result = result + value.substring(i*5, (i+1)*5)+"\n";
						}else{
							result = result + value.substring(i*5, value.length());
							break Label;
						}
		    			i++;
					}
    			}else{
    				result = value;
    				break Label;
    			}
			}
		}
		return result;
    }
}

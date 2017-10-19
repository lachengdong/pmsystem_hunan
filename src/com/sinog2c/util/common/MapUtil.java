package com.sinog2c.util.common;

import java.io.InputStream;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

/**
 * map转换工具包
 * shily 2014-8-12 21:12:10
 *
 */
public class MapUtil { 
	

	/**
	 * 将List中的Key转换为小写
	 * @param list 返回新对象
	 * @return
	 */
	public static List<Map<String, Object>> convertKeyList2LowerCase(List<Map<String, Object>> list){
		if(null==list) {
			return null;
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		//
		Iterator<Map<String, Object>> iteratorL = list.iterator();
		while (iteratorL.hasNext()) {
			Map<String, Object> map = (Map<String, Object>) iteratorL.next();
			//
			Map<String, Object> result = convertKey2LowerCase(map);
			if(null != result){
				resultList.add(result);
			}
		}
		//
		return resultList;
	}
	/**
	*key转小写
	*/
	public static Map<String,String> converKeyLowerCase2(Map<String,String> map){
		if(null==map){
			return null;
		}
		Map<String,String> result=new HashMap<String,String>();
		Set<String> set=map.keySet();
		Iterator<String> it=set.iterator();
		while(it.hasNext()){
			String key=it.next();
			String value=map.get(key);
			if(null==key){
				continue;
			}
			String newkey=key.toLowerCase();
			result.put(newkey, value);
		}
		return result;
	}
	
	
	/**
	 * 转换单个map,将key转换为小写. 
	 * @param map 返回新对象
	 * @return
	 */
	public static Map<String, Object> convertKey2LowerCase(Map<String, Object> map){
		if(null==map) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		//
		Set<String> keys  = map.keySet();
		//
		Iterator<String> iteratorK = keys.iterator();
		while (iteratorK.hasNext()) {
			String key = (String) iteratorK.next();
			Object value = map.get(key);
			if(null == key){
				continue;
			}
			if(value instanceof Clob){
				Clob clob = (Clob)map.get(key);
				try {
					value = clob.getSubString(1, (int)clob.length());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			//
			String keyL = key.toLowerCase();
			result.put(keyL, value);
		}
		return result;
	}
	
	/**
	 * 转换单个map,将value转换为String. 
	 * @param map 返回新对象
	 * @return
	 */
	public static Map<String, String> convertValue2Str(Map<?, ?> map){
		if(null==map) {
			return null;
		}
		Map<String, String> result = new HashMap<String, String>();
		//
		Set<?> keys  = map.keySet();
		//
		Iterator<?> iteratorK = keys.iterator();
		while (iteratorK.hasNext()) {
			String key = (String) iteratorK.next();
			if(null == key){
				continue;
			}
			String value = map.get(key)==null?"":map.get(key).toString();
			result.put(key, value);
		}
		return result;
	}
	

	/**
	 * 
	 * @param list
	 * @param keyMap
	 * @return
	 */
	public static List<Map<String, Object>> convertKey2Another(List<Map<String, Object>> list, Map<String, String> keyMap){
		if(null==list) {
			return null;
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		//
		Iterator<Map<String, Object>> iteratorL = list.iterator();
		while (iteratorL.hasNext()) {
			Map<String, Object> map = (Map<String, Object>) iteratorL.next();
			//
			Map<String, Object> result = convertKey2Another(map, keyMap);
			if(null != result){
				resultList.add(result);
			}
		}
		//
		return resultList;
	}
	/**
	 * 将 Map的Key转换为另一个key，返回新的map
	 * @param map
	 * @param keyMap
	 * @return
	 */
	public static Map<String, Object> convertKey2Another(Map<String, Object> map, Map<String, String> keyMap){
		if(null==map) {
			return null;
		}
		if(null == keyMap) {
			return map;
		}
		//
		Map<String, Object> result = new HashMap<String, Object>();
		//
		Set<String> keys  = map.keySet();
		//
		Iterator<String> iteratorK = keys.iterator();
		while (iteratorK.hasNext()) {
			String key = (String) iteratorK.next();
			if(null == key){
				continue;
			}
			//
			String keyN = keyMap.get(key);
			//
			Object value = map.get(key);
			if(null == keyN || keyN.trim().isEmpty()){
				keyN = key;
			}
			result.put(keyN, value);
		}
		return result;
	}
	
	/**
	 * 设置默认值，如果value为null,或者没有key,则设置默认值
	 * @param list 内部是Map的List
	 * @param valueMap
	 * @return
	 */
	public static List<Map<String, Object>> setDefaultValue(List<Map<String, Object>> list, Map<String, Object> valueMap){
		if(null==list) {
			return null;
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		//
		Iterator<Map<String, Object>> iteratorL = list.iterator();
		while (iteratorL.hasNext()) {
			Map<String, Object> map = (Map<String, Object>) iteratorL.next();
			//
			Map<String, Object> result = setDefaultValue(map, valueMap);
			if(null != result){
				resultList.add(result);
			}
		}
		//
		return resultList;
	}	
	/**
	 * 设置默认值，如果value为null,或者没有key,则设置默认值
	 * @param map 单个map
	 * @param valueMap
	 * @return
	 */
	public static Map<String, Object> setDefaultValue(Map<String, Object> map, Map<String, Object> valueMap){
		if(null==map) {
			return null;
		}
		if(null == valueMap) {
			return map;
		}
		//
		Set<String> keysD  = valueMap.keySet();
		//
		Iterator<String> iteratorD = keysD.iterator();
		while (iteratorD.hasNext()) {
			String keyD = (String) iteratorD.next();
			if(null == keyD){
				continue;
			}
			//
			Object valueO = map.get(keyD);
			Object valueD = valueMap.get(keyD);
			if(null == valueO){
				map.put(keyD, valueD);
			}
		}
		return map;
	}
	/**
	 * 将List中Map的Key转换为小写.
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> trimListKeyValue(List<Map<String, Object>> list){
		if(null==list) {
			return null;
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		//
		Iterator<Map<String, Object>> iteratorL = list.iterator();
		while (iteratorL.hasNext()) {
			Map<String, Object> map = (Map<String, Object>) iteratorL.next();
			//
			Map<String, Object> result = trimKeyValue(map);
			if(null != result){
				resultList.add(result);
			}
		}
		//
		return resultList;
	}
	/**
	 * 转换单个map,将key转换为小写. 
	 * @param map 返回新对象
	 * @return
	 */
	public static Map<String, Object> trimKeyValue(Map<String, Object> map){
		if(null==map) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		//
		Set<String> keys  = map.keySet();
		//
		Iterator<String> iteratorK = keys.iterator();
		while (iteratorK.hasNext()) {
			String key = (String) iteratorK.next();
			Object value = map.get(key);
			if(null == key){
				continue;
			}
			//
			String keyT = key.trim();
			if(value instanceof String){
				String valueT = String.valueOf(value).trim();
				result.put(keyT, valueT);
			} else {
				result.put(keyT, value);
			}
		}
		return result;
	}
	/**
	 * 将Map转化为Map<String,String>（null置为""）
	 * @param map
	 * @return map
	 * shily 2014-8-12 21:13:42
	 */
	public static Map<String,String> ObjectMapToStringMap(Map map){
		Map<String,String> resultMap = new HashMap<String,String>();
		for(Object obj:map.keySet()){
			resultMap.put(obj.toString(), map.get(obj)==null?"":map.get(obj).toString());
		}
		return resultMap;
	}
	/**
	 * 将map转化为key值为小写的map（oracle数据查询出的hashmap默认key大写）
	 * @param map
	 * @return map
	 * shily 2014-8-12 21:14:52
	 * @throws SQLException 
	 */
	public static Map<String,Object> turnKeyToLowerCase(Map map){
		if(null==map) return null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			for(Object obj:map.keySet()){
				Object value = map.get(obj) == null ?"":map.get(obj);
				value = StringNumberUtil.returnStr(value,String.valueOf(value));
				if(StringNumberUtil.notEmpty(map.get(obj)) && (map.get(obj).getClass().getName().contains("Clob"))){
					Clob clob = (Clob)map.get(obj);
					value = clob.getSubString(1, (int)clob.length());
				}
				resultMap.put(obj.toString().toLowerCase(), value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	/**
	 * 将ArrayList中每个map转化为key值为小写的map（oracle数据查询出的hashmap默认key大写）
	 * @param map
	 * @return map
	 * YangZR 2014-8-15 21:14:52
	 */
	public static List<Map> turnKeyToLowerCaseOfList(List<Map> list){
		if(null==list) return null;
		List<Map> resultList = new ArrayList<Map>();
		for(Map tempMap : list){
			resultList.add(turnKeyToLowerCase(tempMap));
		}
		return resultList;
	}
	/**
	 * 方法描述：把批次转化为 大写
	 * @author  mushuhong
	 * @version 2014年8月22日15:23:45
	 */
	public static String minZbig(String string){
		String content = string == null ?"": string;
		
		String result = "";
		try {
			char[] chars = content.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				//分别比较 每一个字符的内容
				switch (chars[i]) {
					case '0':result= result+"零";break;
					case '1':result= result+"一";break;
					case '2':result= result+"二";break;
					case '3':result= result+"三";break;
					case '4':result= result+"四";break;
					case '5':result= result+"五";break;
					case '6':result= result+"六";break;
					case '7':result= result+"七";break;
					case '8':result= result+"八";break;
					case '9':result= result+"九";break;
					default:
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 将字符串中的中括号内容替换成map中对应的内容，如果map中没有对应的key，则置为空
	 * @param content
	 * @param map
	 * @return
	 */
	public static String replaceBracketContent(String content,Map map){
		try {
		if(StringNumberUtil.isEmpty(content)){
			return content;
		}
		if(StringNumberUtil.isEmpty(map)||map.size()<=0){
			return setBracketContentToReplacement(content,"");
		}
		Iterator iter = map.keySet().iterator();
		int count = 0;
		while(iter.hasNext()){
			count ++;
			String key = iter.next().toString();
			String value = map.get(key)==null?"":map.get(key).toString();
			if(map.get(key) instanceof Clob){
				Clob clob = (Clob)map.get(key);
				value = clob.getSubString(1, (int)clob.length());
			}
//			if(count==map.size()){
//				value += "。";
//			}
			content = content.replace("["+key+"]", value);
		}
		content = setBracketContentToReplacement(content,"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
	
	/**
	 * 将字符串中的中括号内容替换成map中对应的内容
	 * @author YangZR
	 * @param content
	 * @param map
	 * @return
	 */
	public static String replaceBKContent(String content,Map map){
		if(StringNumberUtil.isEmpty(content)){
			return content;
		}
		if(StringNumberUtil.isEmpty(map)||map.size()<=0){
			return content;
		}
		Iterator iter = map.keySet().iterator();
		int count = 0;
		while(iter.hasNext()){
			count ++;
			String key = iter.next().toString();
			String value = map.get(key)==null?"":map.get(key).toString();
			content = content.replace("["+key+"]", value);
		}
		return content;
	}
	
	/**
	 * 获取大字符串中所有的中括号的内容
	 * @param bigString
	 * @return
	 */
	public static List getBracketContent(String bigString){
		List list = new ArrayList<Map>();
		Pattern pattern = Pattern.compile("\\[(.*?)\\]");//获取中括号之间的内容
        Matcher matcher = pattern.matcher(bigString);
        while(matcher.find()){
        	list.add(matcher.group(1));
        }
        return list;
	}
	
	/**
	 * 将大字符串中所有的中括号的内容置成replacement，包括中括号
	 * @param bigString
	 * @return
	 */
	public static String setBracketContentToReplacement(String bigString,String replacement){
		List<String> list = getBracketContent(bigString);
		for(String key:list){
			bigString = bigString.replace("["+key+"]", replacement);
		}
		return bigString;
	}
	
	/**
	 * 将大字符串中所有的中括号的内容置成replacement，包括中括号
	 * @param bigString
	 * @return
	 */
	public static String formatFormString(String formString){
		formString = formString.replaceAll("\\r\\n","\\\\r\\\\n");
		return formString;
	}
	/**
	 * 获取aip表单的显示数据
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getDataByAip(String data){
		//集合
		Map<String,Object> map = new HashMap<String,Object>();
		if(data!=null){
	        ArrayList list = (ArrayList) JSON.parseArray(data, Object.class);
			if(list!=null && list.size()>0){
				Map tempmap = (Map)list.get(0);
				Set<String> set = tempmap.keySet();
				//遍历map
				for(String obj:set){
					String name = obj;//属性名
					String value = (String)tempmap.get(obj);//属性名对应得值
					map.put(name, value);
				}
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSONArray2Map(String data){
		//集合
		Map<String, Object> map = new HashMap<String, Object>();
		if(null != data && !data.trim().isEmpty()){
	        List<?> list = (List<?>) JSON.parseArray(data, Object.class);
			if(list!=null && !list.isEmpty()){
				Map<String, Object> tempmap = (Map<String, Object>)list.get(0);
				Set<String> set = tempmap.keySet();
				//遍历map
				for(String obj:set){
					String name = obj;//属性名
					Object value = tempmap.get(obj);//属性名对应得值
					map.put(name, value);
				}
			}
		}
		return map;
	}
	
	// 只获取第一个key
	public static String getFirstKey(Map<String, Object> map) {
		if(null == map){
			return null;
		}
		//
		String key1 = null;
		Set<String> keyset = map.keySet();
		//
		if(null != keyset && !keyset.isEmpty()){
			List<String> keyList = new ArrayList<String>(keyset);
			key1 = keyList.get(0);
		}
		//
		return key1;
	}
	// 只获取第一个Value
	public static Object getFirstValue(Map<String, Object> map) {
		if(null == map){
			return null;
		}
		//
		String key1 = getFirstKey(map);
		Object value1 = map.get(key1);
		//
		return value1;
	}
	/**
	 * 方法描述：读取proprites文件内容
	 * @version 2014年11月17日16:03:04
	 */
	public Object initProperties(HttpServletRequest request){
		Object object = "";
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("jyconfig.properties");
			
			Properties properties = new Properties();
			properties.load(in);
			
			object = properties.getProperty("shanxi");
			
			in.close();
			properties.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  object;
	}
	
	
	
	
	
	
	public static Map<String,Object> valueStr2Num(Map<String,Object> map){
    	if(null==map || map.isEmpty()){
    		return null;
    	}
    	Set<String> set = map.keySet();
    	for(String key : set){
    		String value = map.get(key)==null?"":map.get(key).toString();
    		if(StringNumberUtil.isInteger(value)){
    			map.put(key, Long.parseLong(value));
    		}else if(StringNumberUtil.isDouble(value)){
    			map.put(key, Double.parseDouble(value));
    		}
    	}
    	
    	return map;
    }
}

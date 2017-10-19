package com.sinog2c.util.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.gkzx.common.GkzxCommon;

import flexjson.JSONDeserializer;


public class JsonUtil {

	/**
	 * 此方法描述的是：将json格式数据转换为对应对象
	 * 
	 * @param name
	 * @param @return
	 * @Exception 异常对象
	 * @version: 2013-5-13 上午09:37:42
	 */

	public static Object Decode(String json) {
		if (json==null||"".equals(json))
			return "";
		JSONDeserializer deserializer = new JSONDeserializer();
		deserializer.use(String.class, new DateTransformer(GkzxCommon.DATETIMEFORMATJSON));
		Object obj = deserializer.deserialize(json);
		if (obj != null && obj.getClass() == String.class) {
			return Decode(obj.toString());
		}
		return obj;
	}
	
	public static Map<String,String> decodeToMap(String json){
		Map<String,String> map = new HashMap<String,String>();
		if(json!=null){
	        ArrayList list = (ArrayList) JSON.parseArray(json, Object.class);
			if(list!=null && list.size()>0){
				Map tempmap = (Map)list.get(0);
				Set<String> set = tempmap.keySet();
				//遍历map
				for(String obj:set){
					String name = obj;//属性名
					String value = String.valueOf(tempmap.get(obj));//属性名对应得值
					map.put(name, value);
				}
			}
		}
		return map;
	}
}

package com.sinog2c.util.common;

import java.util.Comparator;
import java.util.TreeMap;


public class MapOrder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static java.util.Map<String, String> sortMapByKey(java.util.Map<String, String> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        java.util.Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());  
        sortMap.putAll(map);  
        return sortMap;  
    }
}
class MapKeyComparator implements Comparator<String>{  
    public int compare(String str1, String str2) {  
        return str1.compareTo(str2);  
    } 
}

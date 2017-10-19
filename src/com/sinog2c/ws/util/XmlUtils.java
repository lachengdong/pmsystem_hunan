package com.sinog2c.ws.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.sinog2c.util.common.FileUtil;
  
  
 
/**
 * ClassName: XmlUtils 
 * @Description: 字符串转xml文件的工具类，主要用于数据传输时生成指定格式和内容的xml文件，（向外发送）
 * 				  用于简单的将xml文件解析为map(该类主要用于检察院系统工作时解析xml文件) （对内接收）
 * @author zhaoyang
 * @date 2016年10月27日
 */
public class XmlUtils {  
	
	/**
	 * @Description: 加载Xml文件
	 * @param filename 
	 * @return   
	 * @return Document  
	 * @throws
	 * @author zhaoyang
	 * @date 2016年10月27日
	 */
	public static Document loadXml(String filename){
	        Document doc = null;
	        SAXReader reader = new SAXReader();
	        try {
	            doc = reader.read(new File(filename));
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        }
	        return doc;
	    }
      
	 

    /**
     * @Description: 将xml文件转换为map 如：{TQBH=223c2626c811f1a2458c8a8b71cb68dd, WSMC=减刑回函}
     * @param doc 待转换的xml文件
     * @return   
     * @return Map<String,String>  
     * @throws
     * @author zhaoyang
     * @date 2016年10月27日
     */
    @SuppressWarnings("unchecked")
	public static Map<String, String> Dom2Map(Document doc){  
        Map<String, String> map = new HashMap<String, String>(); 
        if(doc == null) {
        	return map;  
        } 
        Element root = doc.getRootElement(); 
        map.put("ROOT", root.getName());
        for (Iterator<Element> iterator = root.elementIterator(); iterator.hasNext();) {  
            Element e = iterator.next();  
			List<Element> list = e.elements();
            if(list.size() > 0){  
                map.putAll(Dom2Map(e));
            }else {
            	map.put(e.getName(), e.getText());  
            } 
        }  
        return map;  
    }  
      
    /**
     * @Description: 根据标签将xml转化成map
     * @param  e 待解析的标签对象
     * @return Map<String,Object>  
     * @throws
     * @author zhaoyang
     * @date 2016年10月26日
     */
    @SuppressWarnings("unchecked")
	public static Map<String, String> Dom2Map(Element e){  
        Map<String,String> map = new HashMap<String,String>();  
        List<Element> list = e.elements();  
        if(list.size() > 0){  
            for (int i = 0;i < list.size(); i++) {  
                Element iter = (Element) list.get(i);  
                if(iter.elements().size() > 0){  
                    Map<String, String> m = Dom2Map(iter);  
                    return m;
                } else{
                	map.put(iter.getName(), iter.getText());  
                }
            }  
        }  
        return map;  
    }  
    
    /**
	 * @Description: 根据数据库中查出的信息拼接字符串
	 * @param sb	拼接字符串的存储容器
	 * @param list	数据库中查出的数据集合
	 * @param elements   xml字符串嵌套的标签层次，最多三层（即只能给两个参数）	
	 * @return void  
	 * @throws
	 * @author zhaoyang
	 * @date 2016年10月27日
	 */
	public static void createXml(StringBuilder sb,List<Map<String, String>> list,String...elements){
		//支持最多三层标签
		if (elements.length>0) {
			//第一层
			sb.append("<"+elements[0]+">");
		}
		for (int i = 0; i < list.size(); i++) {
			if (elements.length==2) {
				//第二层
				sb.append("<"+elements[1]+">");
			}
					Iterator<Entry<String, String>> iterator = list.get(i).entrySet().iterator();
					while (iterator.hasNext()) {
						Entry<String, String> next = iterator.next();
						String key = next.getKey();
						String value = next.getValue();
						//第三层
						sb.append("<"+key+">").append(value).append("</"+key+">");
			}
			if (elements.length==2) {
				sb.append("</"+elements[1]+">");
			}
		}
		if (elements.length>0) {
			sb.append("</"+elements[0]+">");
		}
	
	}
	
	/**拼接xml字符串的头信息*/
	public static void AddXmlHead(StringBuilder sb,String headName){
		sb.insert(0, "<"+headName+" xmlns=\"http://dataexchange.court.gov.cn/2009/data\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">").append("</"+headName+">");
	}
	

	/**
	 * @Description: 将字符串转存为xml文件
	 * @param sb 待转换的字符串
	 * @param headName  xml文件的 名称空间xmlns
	 * @param fileName  xml文件的名称
	 * @return boolean  转换是否成功
	 * @throws
	 * @author zhaoyang
	 * @date 2016年10月27日
	 */
	public static boolean Dom2File(StringBuilder sb,String headName,String targetPath,String fileName) {
		FileUtil.createDirIfNotExist(targetPath);
		AddXmlHead(sb, headName);
		XMLWriter writer=null;
		boolean result=false;
		try {
			Document document = DocumentHelper.parseText(sb.toString());  
			OutputFormat format = OutputFormat.createPrettyPrint();
			// 包含中文，指定XML编码                   
			format.setEncoding("UTF-8");
			writer = new XMLWriter(new FileOutputStream(targetPath+fileName),format);
			writer.write(document);
		} catch (Exception e) {
		}finally{
			try {
				writer.close();
				result=true;
			} catch (Exception e) {
			}
		}
		return result;
	} 
	
	/**
	 * @Description: 将数据库中查出来的map记录，从中提取出需要封装为xml的数据字段封装成Map
	 * @param map 数据库记录
	 * @param sqlNames sql语句中对应的字段
	 * @param Fieldnames xml中对应的标签名
	 * @return Map<String,String>  转换完成的map
	 * @throws
	 * @author zhaoyang
	 * @date 2016年10月28日
	 */
	public static Map<String, String> Sql2List(Map<String,String> map,String sqlNames,String Fieldnames){
		HashMap<String, String> result = new HashMap<String,String>();
		String[] asqls = sqlNames.split(",");
		String[] afields = Fieldnames.split(",");
		for (int i = 0; i < afields.length; i++) {
			Object field = map.get(asqls[i]);
			result.put(afields[i], field==null?"":field.toString());
		}
		return result;
	}
	
	public static void map2List(Map<String,String> map,List<Map<String, String>> list){
		//在这里多封装一层数据，方便后续方法调用无需再转为list
		list.add(map);
	}

	/**拼接xml字符串的头信息*/
	public static void AddTQXXHead(StringBuilder sb,String headName){
	    sb.insert(0,"<"+headName+">" ).append("</"+headName+">");
	}

	public static boolean fileIsExists(String path){
		  File file=new File(path);    
		  return file.exists();
		}
}  

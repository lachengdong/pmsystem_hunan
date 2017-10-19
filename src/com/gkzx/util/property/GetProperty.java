package com.gkzx.util.property;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.gkzx.common.GkzxCommon;
//import com.gkzx.datacach.DataCatchListener;

public class GetProperty {
//	GetAbsolutePath pathmodel=new GetAbsolutePath();
	 /**   
     * 此方法描述的是：获取菜单树
	 * @param   	name		文件名称 不含后缀 
	 * @param  	packageName  	包名称 例如:com.gkzx.util.property  注：classes下可为null
     * @version: 2013-5-5 13:00:09   
     */
	public Properties bornProp(String name,String packageName)
	{	
		 Properties props = null;
		{
			InputStreamReader ins = null;
			//String path="WEB-INF/classes/";
			if(packageName!=null && !"".equals(packageName)) {//存在包名获取其路径，不存在不处理
				packageName = packageName.replaceAll("\\.", "/");
				name=packageName+"/"+name;
			}

			//path = pathmodel.getAbsolutePath(path);
			//InputStream ins = this.getClass().getClassLoader().getResourceAsStream(path+name+".properties");
			String key = name+".properties";
			//if (DataCatchListener.PROPERTIES.containsKey(key)){
			//	props = DataCatchListener.PROPERTIES.get(key);
			//} else {
				props = getProps(key);
			//}
//			try {
//				ins = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(name+".properties"),GkzxCommon.encoding);
//				//InputStream ins = new FileInputStream(path+name+".properties");
//				try {
//					if (props==null)
//						props = new Properties();
//						props.load(ins);
//					
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				if (ins != null){
//					ins.close();
//				}
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
			
			
		}
		return props;
	}
	
	/**   
	 * 此方法描述的是：获取属性文件
	 * @param   	name		文件名称 (com/gkzx/systemManage/mainhql.properties)
	 * @version: 2014-6-2 13:00:09   
	 */
	public Properties getProps(String name)
	{	
		Properties props = null;
		InputStreamReader ins = null;
		try {
			ins = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(name),GkzxCommon.encoding);
			//InputStream ins = new FileInputStream(path+name+".properties");
			try {
				if (props==null)
					props = new Properties();
				props.load(ins);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (ins != null){
				ins.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return props;
	}
	
	 /**   
     * 此方法描述的是：获取输出信息
	 * @param   	name		文件名称 不含后缀 
	 * @param  	packageName  	包名称 例如:com.gkzx.util.property  注：classes下可为null
     * @version: 2013-5-5 13:00:09   
     */
//	public Properties bornMsgProp(String name,String packageName)
//	{	
//		 Properties props = null;
//		{
//			InputStreamReader ins = null;
//			//String path="WEB-INF/classes/";
//			if(packageName!=null && !"".equals(packageName)) {//存在包名获取其路径，不存在不处理
//				packageName = packageName.replaceAll("\\.", "/");
//				name=packageName+"/"+name;
//			}
//			//path = pathmodel.getAbsolutePath(path);
//			//InputStream ins = this.getClass().getClassLoader().getResourceAsStream(path+name+".properties");
//			 
//			try {
//				ins = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(name+".properties"),GkzxCommon.encoding);
//				//InputStream ins = new FileInputStream(path+name+".properties");
//				try {
//					if (props==null)
//						props = new Properties();
//						props.load(ins);
//					
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			
//			
//		}
//		return props;
//	}
	 /**   
     * 此方法描述的是：获取输出信息
	 * @param   	name		文件名称 不含后缀 
	 * @param  	packageName  	包名称 例如:com.gkzx.util.property  注：classes下可为null
     * @version: 2013-5-5 13:00:09   
     */
	@SuppressWarnings("unchecked")
	public HashMap<String, String> readXml(String name, String packageName){
		HashMap<String, String> mapResult = new HashMap<String, String>();
		if (packageName != null && !"".equals(packageName)) {// 存在包名获取其路径，不存在不处理
			packageName = packageName.replaceAll("\\.", "/");
			name = packageName + "/" + name;
		}
		
		String key = name+".xml";
		mapResult = readXmlKeyValue(key);

		return mapResult;
	}
	
	 /**   
     * 此方法描述的是：获取输出信息
	 * @param   	name		文件名称 不含后缀 
     * @version: 2013-5-5 13:00:09   
     */
	@SuppressWarnings("unchecked")
	public HashMap<String, String> readXmlKeyValue(String name){
		HashMap<String, String> mapResult = new HashMap<String, String>();
		// 使用了dom4j解析xml
		// 读取目录下的xml文件，取得xml主内容
		Document document;
		try {
			document = new SAXReader().read(
					this.getClass().getClassLoader().getResourceAsStream(name)).getDocument();
			// 遍历文档根节点（config）下的子节点列表，即messages节点的集合
			for (Element messages : (List<Element>) document.getRootElement()
					.elements()) {
				// 取得messages节点下的id节点的内容
				String strId = messages.element("id").getText().trim();
				String strValue = messages.element("value").getText().trim();
				if ((strId != null && !"".equals(strId))&&
					(strValue != null && !"".equals(strValue))){
					mapResult.put(strId, strValue);
				}
			}			
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return mapResult;
	}
	
}

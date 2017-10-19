package com.sinog2c.util.dbms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 使用DOM4j来解析XML文件
 * 
 * @author <a href="mailto:tangjunfeng52099@gmail.com">tangjunfeng</a>
 * 
 */
public class Dom4jXMLUtil {
	
	/**
	 * 创建文件
	 * @param docPath
	 * @return
	 */
	public static Document inits(String docPath,String docName) {
		Document doc = null;
		// 得到完整路径名称
		String xmlPath = docPath + "/" + docName + ".xml";
		// 如果没有那么以前就绝对没有,说明还没有到处任何数据,如果已经存在那么需要做处理.
		// 先判断文件夹是否存在
		File myFilePath = new File(docPath);
		// 创建一个临时的文件夹存放所有的xml
		if (!myFilePath.exists()) {
			myFilePath.mkdir();
		}
		// 检查文件是否存在
		File file = new File(xmlPath);
		if (file.exists()) {
			SAXReader reader = new SAXReader();
			try {
				// 得到文件信息
				doc = reader.read(file);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} else {
			//文件不存在就创建文件并添加根节点
			doc = DocumentHelper.createDocument();
			doc.addElement("ROOT");
		}
		return doc;
	}

	/**
	 * 将修改后的Document对象写回到xml文件中
	 * 
	 * @param xmlPath
	 *            文件的绝对路径
	 * @param doc
	 *            修改好的Document对象
	 */
	public static void overRideXml(String xmlPath, Document doc) {
		/** 格式化输出,类型IE浏览一样 */
		XMLWriter writer = null;
		try {
			// 写一个writer,用来最后存储改好的document对象到xml文件中
			writer = new XMLWriter(new FileOutputStream(xmlPath), OutputFormat.createPrettyPrint());
			// 将document对象写入XML文件中
			writer.write(doc);
			// 关闭格式化输出对象
			writer.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用Dom4j判断节点是否存在
	 * 
	 * @param path
	 *            文件绝对路径
	 * @param docName
	 *            文件名称
	 * @param fatherNode
	 *            该节点的父节点
	 * @param value
	 *            需要判断是否存在的节点属性的值
	 * @return true为不存在，false为存在
	 */
	public static Element isExists(String path, String docName,
			String fatherNode, String value) {
		// 得到该XML文件
		Element ment = null;
		Document doc = inits(path, docName);
		int count = 0;
		// 得到该XML文件的根节点
		Element root = doc.getRootElement();
		// 得到你要查找的节点
		Element log = root.element(fatherNode);
		if (null != log) {
			// 得到该节点的节点枚举集合，
			Iterator<Element> i = log.elementIterator();
			while (i.hasNext()) {
				// 得到枚举集合内的节点
				Element chars = (Element) i.next();
				// 得到该节点内的所有属性
				List as = chars.attributes();
				for (int j = 0; j < as.size(); j++) {
					// 循环得到节点内的所有属性
					Attribute attribute = (Attribute) as.get(j);
					if (value.equals(attribute.getStringValue())) {
						ment = chars;
						break;
					}
				}
			}
		}
		return ment;
	}

	/**
	 * 添加逻辑判断节点Judge
	 * 
	 * @param log
	 *            逻辑判断节点的父类对象LOGICSYMBOL
	 * @param basePath
	 *            文件绝对路径
	 * @param docName
	 *            文件名称
	 * @param keyTo
	 *            KEY的值
	 * @param valueTo
	 *            Value的值
	 * @return 父级对象
	 */
	public static Element addJudge(Element log, String basePath,
			String docName, String keyTo, String valueTo) {
		// 判断大于是否存在
		Element ment = Dom4jXMLUtil.isExists(basePath, docName, "LOGICSYMBOL",valueTo);
		if (null == ment) {
			Element ju = log.addElement("JUDGE");
			ju.addAttribute("KEY", keyTo);
			ju.addAttribute("VALUE", valueTo);
		} else {
			log.remove(ment);
			Element ju = log.addElement("JUDGE");
			ju.addAttribute("KEY", keyTo);
			ju.addAttribute("VALUE", valueTo);
		}
		return log;
	}
	
	/**
	 * 查找是否已经存在此条数据
	 * @param doc	修改好的document对象
	 * @param tableName 	二级节点名称
	 * @param TableFields	子节点数组
	 * @param FieldValues	子节点内的数组值
	 * @return 二级节点
	 */
	public static Element findElementById(Document doc, String tableName,String[] TableFields, String[] FieldValues) {
		//得到XML文件中的子节点集合
		Iterator<Element> it = doc.getRootElement().elementIterator(tableName);
		//循环条件为子节点集合存在下一条
		while (it.hasNext()) {
			//创建一个子节点下一条的节点对象
			Element element = it.next();
			int j = 0;
			//循环条件是子节点的节点数组的长度
			for (int i = 0; i < TableFields.length; i++) {
				//如果子节点内第 i 条值    等于    节点对象内的第 i 条子节点的值
				if (FieldValues[i].equals(element.element(TableFields[i]).getText())) {
					j++;
				}
			}
			//如果J 等于 子节点数组的长度
			if (j == TableFields.length) {
				//该记录存在     返回改记录
				return element;
			}
		}
		//该记录不存在
		return null;
	}

	public static void main(String[] args) {
		List[] list = new List[3];
		List<List<String>> rowList = new ArrayList<List<String>>();
		List<List<Object[]>> valueList = new ArrayList<List<Object[]>>();
		List<String> tableName=new ArrayList<String>();
		
		List<Object[]> value=new ArrayList<Object[]>();
		tableName.add("权限表");
			List<String> row=new ArrayList<String>();
			row.add("权限编号");
			row.add("权限名称");
			row.add("用户编号");
			rowList.add(row);
		for(int i=1;i<=10;i++){
			Object[] o=new Object[3];
			o[0]=i;   
			o[1]="管理员"+i;
			o[2]="Admin"+i;
			value.add(o);
		}
		valueList.add(value);
		
		for(int i=0;i<valueList.size();i++){
			List<Object[]> zhi=valueList.get(i);
			String biaoming=tableName.get(i);
			List<String> lie=rowList.get(i);
			for(int j=0;j<zhi.size();j++){
				Object[] obj=zhi.get(j);
				for(int a=0;a<obj.length;a++){
//					System.out.print("表名 = "+biaoming.toString());
//					System.out.print("         列名 = "+lie.get(a).toString());
//					System.out.print("         值 = "+obj[a]);
//					System.out.println();
				}
//				System.out.println();
			}
		}
	}
	
	
	
	/**
	 * 根据文件路径得到文件内容
	 * @param docPath
	 * @return
	 */
	public static Document inits(String docPath) {
		Document doc = null;
		// 如果没有那么以前就绝对没有,说明还没有到处任何数据,如果已经存在那么需要做处理.
		// 先判断文件夹是否存在
		File myFilePath = new File(docPath);
		// 创建一个临时的文件夹存放所有的xml
		if (!myFilePath.exists()) {
			myFilePath.mkdir();
		}
		// 检查文件是否存在
		File file = new File(docPath);
		if (file.exists()) {
			SAXReader reader = new SAXReader();
			try {
				// 创建文件
				doc = reader.read(file);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} else {
			// 如果文件存在就创建容器并写入自定义查询 根节点
			doc = DocumentHelper.createDocument();
			doc.addElement("ROOT");
		}
		return doc;
	}
	
	
}

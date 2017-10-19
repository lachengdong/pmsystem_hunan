package com.sinog2c.mvc.controller.dbmsnew.old;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;

import com.gkzx.common.GkzxCommon;
import com.sinog2c.model.dbmsnew.DbmsDatasChemeNew;
import com.sinog2c.model.dbmsnew.DbmsDatasTableNew;
import com.sinog2c.mvc.controller.base.ControllerBase;

 
    /**  
	 * 项目名称：jyzngzpt
	 * 类名称：DbmsNewDataImportAction
     * 此类描述的是：   不用废弃
     * @author: 李祺亮  
     * @version: 2013-4-11 下午08:25:20    
     */   
    
public class DbmsNewDataImportController extends ControllerBase {

//	private static final long serialVersionUID = 3621922684471033343L;
//	private HttpServletRequest request;
//
//	public void prepare() throws Exception {
//	}
//
//	public void setServletRequest(HttpServletRequest request) {
//		this.request = request;
//	}
//
//	private DbmsNewDataImportService dbmsNewDataImportService;
//
//	public DbmsNewDataImportService getDbmsNewDataImportService() {
//		return dbmsNewDataImportService;
//	}
//
//	public void setDbmsNewDataImportService(
//			DbmsNewDataImportService dbmsNewDataImportService) {
//		this.dbmsNewDataImportService = dbmsNewDataImportService;
//	}
//	
//	private CommonTemplateUtil commonTempletUtil;
//	public void setCommonTempletUtil(CommonTemplateUtil commonTempletUtil) {
//		this.commonTempletUtil = commonTempletUtil;
//	}
//	
//	private File upload;// 封装上传文件
//	private String uploadFileName;// 设置上传文件的文件名
//	private String uploadContentType;// 上传文件的类型
//	/** 服务器路径 */
//	private GetAbsolutePathModelImpl absolutePath = new GetAbsolutePathModelImpl();
//	/** 导入文件的绝对路径 */
//	private String importBasePath = absolutePath
//			.getAbsolutePath("dataxml/importxml/");// 获取临时目录的绝对路径
//
//	/**
//	 * xml文件上传
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public String xmlUpload() {
//		if (this.getUpload() == null) {
//			this.addActionError("不允许上传空文件，请您重新上传!");
//			return "commonPage";
//		}
//		request.setAttribute("todatabasenameid", "");
//		request.setAttribute("databaseName", "");
//		request.setAttribute("operterFlag", "");
//		FileOutputStream fos = null;
//		String basePathfile = importBasePath + getUploadFileName();
//		try {
//			fos = new FileOutputStream(basePathfile);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		FileInputStream fis = null;
//		try {
//			fis = new FileInputStream(getUpload());
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		byte[] buffer = new byte[1024];
//		int len = 0;
//		try {
//			while ((len = fis.read(buffer)) > 0) {
//				fos.write(buffer, 0, len);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			fos.flush();
//			fos.close();
//			fis.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
////		System.out.println("导入完成");
//		String basicdep = "";
//		try {
//			basicdep = (String)request.getSession().getAttribute("basicdep");
//		} catch (Exception e) {
//		}
//		List<DbmsDatasChemeNew> datas = dbmsNewDataImportService.getDatasChemeNewInfolist(basicdep);
//		ActionContext.getContext().put("docPath", basePathfile);
//		ActionContext.getContext().put("datascheme", datas);
//		return SUCCESS;
//	}
//
//	public File getUpload() {
//		return upload;
//	}
//
//	public void setUpload(File upload) {
//		this.upload = upload;
//	}
//
//	public String getUploadFileName() {
//		return uploadFileName;
//	}
//
//	public void setUploadFileName(String uploadFileName) {
//		this.uploadFileName = uploadFileName;
//	}
//
//	public String getUploadContentType() {
//		return uploadContentType;
//	}
//
//	public void setUploadContentType(String uploadContentType) {
//		this.uploadContentType = uploadContentType;
//	}
//
//	public String admissionImport() {
//		return SUCCESS;
//	}
//
//	/** 导入方案ID */
//	public String inPlinId;
//	/** 导入文件名称 */
//	public String docPath;
//
//	/** 显示自定义查询 */
//	public String showCustomQuery() {
//		String retemp=SUCCESS;
//		File file = new File(docPath);
//		if (file.exists()) {
//			// 得到解压后的文件夹名称
//			String docsName = docPath.substring(0, docPath.lastIndexOf("."));
//			try {
//				// 解压文件
//				DocZipUtil.unzip(docPath, docsName);
//				// 解压完成后删除压缩文件
//			} catch (Exception e) {
//				e.printStackTrace();
//				this.addActionError(e.getMessage());
//				retemp="commonPage";
//				String suids=(String)request.getSession().getAttribute("suid");
//				String date=new SimpleDateFormat(GkzxCommon.DATETIMEFORMAT).format(new Date());
//				String info=suids+"于"+date+"试图将已篡改的数据文件上传至数据库，被程序拦截！";
//				commonTempletUtil.insertOperateLogger(request, info, "116856");
//			}finally{
//				file.delete();
//			}
//			if(SUCCESS.equals(retemp)){
//				// 解压完成，得到解压后的文件夹内所有的文件
//				File f = new File(docsName);
//				File[] files = f.listFiles();
//				String docName = null;
//				// 得到自定义查询的XML
//				for (int i = 0; i < files.length; i++) {
//					docName = files[i].getName();
//					if(docName.lastIndexOf("information")<0){//判断没有information的存在
//						break;
//					}
//				}
//				String viewDocPath=null;
//				for (int i = 0; i < files.length; i++) {
//					viewDocPath = files[i].getName();
//					if(viewDocPath.lastIndexOf("View")>=0){
//						viewDocPath=files[i].toString();//得到视图数据文件的完整路径
//						break;
//					}
//				}
//				String outChemeid=docName.substring(0,docName.lastIndexOf("_Query"));//得到导出方案ID
//				String hou=docPath.substring(docPath.lastIndexOf("@"),docPath.lastIndexOf("."));//得到统一的后缀
//				docName=docName.substring(0,docName.lastIndexOf("."));//得到query文件名不带后缀
//				Document query=Dom4jXMLUtil.inits(docsName, docName);//得到query文件的信息
//				Element root=query.getRootElement();
//				Element coumls=root.element("SYLLABLE");//得到所有的列
//				Element log=root.element("LOGICSYMBOL");//得到所有的逻辑运算符
//				List<String[]> logList = new ArrayList<String[]>();//所有的逻辑运算符
//				List<String[]> obj = new ArrayList<String[]>();//所有的列信息
//				Iterator<Element> coumlsIt=coumls.elementIterator();
//				while(coumlsIt.hasNext()){//得到所有的列
//					String[] str=new String[4];
//					Element couml=coumlsIt.next();
//					str[0]=couml.attributeValue("COLUMNSNAME");//列名
//					str[1]=couml.attributeValue("COMM");//列描述
//					str[2]=couml.attributeValue("TYPE");//列类型
//					str[3]=couml.attributeValue("ISCODE");//列类型
//					obj.add(str);
//				}
//				Iterator<Element> logIt=log.elementIterator();
//				while(logIt.hasNext()){//得到所有的逻辑运算符
//					String[] str=new String[2];
//					Element logs=logIt.next();
//					str[0]=logs.attributeValue("KEY");
//					str[1]=logs.attributeValue("VALUE");
//					logList.add(str);
//				}
//	//			docsName//文件夹路径
//				List<DbmsDatasTableNew> tables=dbmsNewDataImportService.getDescendingTableList(outChemeid);//得到操作表集合
//				request.setAttribute("StringList", obj);//列
//				request.setAttribute("LogList", logList);//逻辑运算符
//				request.setAttribute("mainTablePath", viewDocPath);//视图文件绝对路径
//				request.setAttribute("inPlinId", inPlinId);//导入方案ID
//			}
//		}else{
//			this.addActionError("数据文件不存在或不正确！");
//			retemp="commonPage";
//		}
//		return retemp;
//	}
//
//	/** 页面添加的条件 */
//	public String condition;
//	/** 主表的绝对路径 */
//	public String mainTablePath;
//	/** 操作表ID */
//	public String tableId;
//
//	/** 显示XML中的数据 */
//	public String showDataBaseInformation() {
//		String tablePath = mainTablePath.substring(0, mainTablePath.lastIndexOf("\\"));// 得到文件夹路径
//		request.setAttribute("docPath", tablePath);
//		String docName = mainTablePath.substring(mainTablePath.lastIndexOf("\\") + 1, mainTablePath.lastIndexOf("."));// 得到主表XML文件名
//		// 得到主表的XML文件
//		Document document = Dom4jXMLUtil.inits(tablePath, docName);
//		Element root = document.getRootElement();
//		Iterator<Element> it = root.elementIterator();
//		List<Element> dataBase = new ArrayList<Element>();
//		File file=new File(tablePath);
//		File[] files=file.listFiles();
//		String queryName=null;
//		for(int i=0;i<files.length;i++){
//			queryName=files[i].getName();
//			if(queryName.lastIndexOf("_Query")>=0){
//				break;
//			}
//		}
//		queryName=queryName.substring(0,queryName.lastIndexOf("."));//得到query文件名
//		while (it.hasNext()) {
//			Element table = it.next();
//			if (null != condition && !"".equals(condition)) {
//				// 条件分开
//				String[] single = condition.split(";");
//				for (int i = 0; i < single.length; i++) {
//					// 得到单个条件
//					String[] sing = single[i].split(",");
//					if (sing.length <= 3) {// 不包括介于
//						String rowName = sing[0];// 得到列名
//						String log = sing[1];// 得到条件运算符
//						String value = sing[2];// 得到值
//						Element ment=conversionLogicOperator(table, rowName,log, value);
//						if(null!=ment){
//							dataBase.add(ment);
//						}
//					} else {
//						String rowName = sing[0];// 得到列名
//						int min, max, xmlValue;
//						try {
//							min = Integer.valueOf(sing[2]);// 得到最小值
//							max = Integer.valueOf(sing[4]);// 得到最大值
//						} catch (ClassCastException e) {
//							min = Integer.valueOf(sing[2].split("-").toString());// 得到最小值
//							max = Integer.valueOf(sing[4].split("-").toString());// 得到最大值
//						}
//						Iterator<Element> rows = table.elementIterator();
//						while (rows.hasNext()) {
//							Element row = rows.next();
//							if (rowName.equals(row.getName())) {
//								try {
//									xmlValue = Integer.valueOf(row.getText());
//								} catch (ClassCastException e) {
//									xmlValue = Integer.valueOf(row.getText().split("-").toString());
//								}
//								if (min <= xmlValue && xmlValue >= max) {
//									dataBase.add(row);
//								}
//							}
//						}
//					}
//				}
//			}else{
//				dataBase.add(table);
//			}
//		}
//		Document ment=Dom4jXMLUtil.inits(tablePath, queryName);
//		List<String> coumlsComm=new ArrayList<String>();//列描述
//		Element commRoot=ment.getRootElement();
//		Element comm=commRoot.element("SYLLABLE");
//		Iterator<Element> commIt=comm.elementIterator();
//		List<String> tableInfo = new ArrayList<String>();// 表信息 即 列名
//		while(commIt.hasNext()){
//			Element commit=commIt.next();
//			coumlsComm.add(commit.attributeValue("COMM"));//得到描述
//			tableInfo.add(commit.attributeValue("COLUMNSNAME"));//得到列名
//		}
//		List<String[]> tableFruit = new ArrayList<String[]>();// 表结果 即 值
//		for (int a = 0; a < dataBase.size(); a++) {
//			if (null != dataBase.get(a)) {
//				Iterator<Element> rows = dataBase.get(a).elementIterator();
//				String fruitInfo = "";
//				while (rows.hasNext()) {
//					Element row = rows.next();
//					for(int i=0;i<tableInfo.size();i++){
//						if(tableInfo.get(i).trim().equals(row.getName().trim())){
//							fruitInfo += row.getText() + ",";// 添加值
//							break;
//						}
//					}
//				}
//				tableFruit.add(fruitInfo.split(",")); // 单列的值添加进表结果集合
//			}
//		}
////		request.setAttribute("tableId", tableId);
//		request.setAttribute("inPlinId", inPlinId);
//		request.setAttribute("tableFruit", tableFruit);
//		request.setAttribute("tableInfo", tableInfo);
//		request.setAttribute("coumlsComm", coumlsComm);
//		return SUCCESS;
//	}
//
//	/** 转换逻辑运算符 (不包括介于) */
//	private Element conversionLogicOperator(Element table, String rowName,
//			String log, String value) {
//		Iterator<Element> rows = table.elementIterator();
//		Element ment = null;
//		try{while (rows.hasNext()) {
//			Element row = rows.next();
//			if (rowName.equals(row.getName())) {
//				if (log.equals("eqeq")) {// 等于
//					if (value.equals(row.getText())) {
//						ment = table;
//						break;
//					}
//				} else if (log.equals("!eq")) {// 不等于
//					if (!value.equals(row.getText())) {
//						ment = table;
//						break;
//					}
//				} else if (log.equals("1gt")) {// 大于
//					int v = Integer.valueOf(value);
//					int text = Integer.valueOf(row.getText());
//					if (v < text) {
//						ment = table;
//						break;
//					}
//				} else if (log.equals("1lt")) {// 小于
//					int v = Integer.valueOf(value);
//					int text = Integer.valueOf(row.getText());
//					if (v > text) {
//						ment = table;
//						break;
//					}
//				} else if (log.equals("1gteq")) {// 大于等于
//					int v = Integer.valueOf(value);
//					int text = Integer.valueOf(row.getText());
//					if (text >= v) {
//						ment = table;
//						break;
//					}
//				} else if (log.equals("1lteq")) {// 小于等于
//					int v = Integer.valueOf(value);
//					int text = Integer.valueOf(row.getText());
//					if (text <= v) {
//						ment = table;
//						break;
//					}
//				} else if (log.equals("like")) {// 包含
//					if (row.getText().indexOf(value) >= 0) {
//						ment = table;
//						break;
//					}
//				}
//			}
//		}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return ment;
//	}
//	
//	/**
//	 * 导入与主表没有关系的表数据
//	 * @return
//	 */
//	@SuppressWarnings({ "unchecked", "null" })
//	public String dataImportSuccess(){
////		String outChemeId=null;
////		String postfix=null;
////		if(null!=docPath&&!"".equals(docPath)){
////			File file=new File(docPath);
////			if(file.exists()){
////				File[] files=file.listFiles();
////				for(int i=0;i<files.length;i++){
////					File f=files[i];
////					if(f.getName().lastIndexOf("information")>=0){
////						String docName=f.getName();
////						outChemeId=docName.substring(docName.lastIndexOf("@")+1,docName.lastIndexOf("information"));//得到导出方案ID
////						postfix=docName.substring(docName.lastIndexOf("@"),docName.lastIndexOf("."));//得到统一后缀
////						break;
////					}
////				}
////			}
////		}
////		List<DbmsDatasTableNew> dbmsDatasTableList=dbmsNewDataImportService.getTableList(outChemeId);//得到操作表集合
////		DbmsDatasChemeNew importCheme=dbmsNewDataImportService.getDbmsDatasChemeNewById(inPlinId);//得到数据方案对象
////		DbmsDatabaseNew dataBase=dbmsNewDataImportService.getDataBaseNewInfo(importCheme.getFromdatabaseid());//得到数据库管理对象
////		Connection conn=dbmsNewDataImportService.getConnection(dataBase);
////		int count=0;
////		for(int i=0;i<dbmsDatasTableList.size();i++){
////			DbmsDatasTableNew dbmsDatasTable=dbmsDatasTableList.get(i);//操作表对象
////			//关系字段等于空
////			if(null==dbmsDatasTable.getDdtjoinedfields()||"".equals(dbmsDatasTable.getDdtjoinedfields())){
////				//得到数据映射集合
////				List<DbmsDatasChemeDetailNew> dbmsDatasChemeDetail=dbmsNewDataImportService.getDatasChemeDetail(inPlinId, dbmsDatasTable.getId().getDdtid());
////				Document document=Dom4jXML.inits(docPath, dbmsDatasTable.getTablename()+postfix);//得到无关系的XML文件信息
////				Element root=document.getRootElement();//得到根节点
////				Iterator<Element> rootIt=root.elementIterator();//得到数据集合
////				while(rootIt.hasNext()){
////					Element data=rootIt.next();//得到   一行    数据
////					Iterator<Element> dataIt=data.elementIterator();//得到一行数据的多列
////					List<String> sonrows = new ArrayList<String>();// 要插入的子表列集合
////					List<Object> sonvalues = new ArrayList<Object>();// 要插入的子表值集合
////					while(dataIt.hasNext()){
////						Element single=dataIt.next();//得到单个     单元格      数据
////						for(int j=0;j<dbmsDatasChemeDetail.size();j++){
////							DbmsDatasChemeDetailNew datasCheme=dbmsDatasChemeDetail.get(j);//得到一列
////							if(single.getName().trim().equals(datasCheme.getDcdfromcolumns().trim())){//判断两个列名是否一致
////								Object sonXMLText=single.getText();//值
////								String sonXML = single.getName().trim();// 列
////								if(datasCheme.getDcdfromcloumnssize()>datasCheme.getDcdtocolumnssize()){//判断两个列的长度是否一致
////									if(single.getText().length()>datasCheme.getDcdtocolumnssize()){
////										// 长度不一致，进行截取
////										long size = datasCheme.getDcdtocolumnssize() - 1;
////										int sizes = ((Number) size).intValue();
////										sonXMLText = sonXMLText.toString().substring(0,sizes);
////									}
////								}
////								//判断两个列的类型是否一致
////								if(!datasCheme.getDcdfromcloumnstype().toUpperCase().equals(datasCheme.getDcdtocolumnstype().toUpperCase())){
////									if(null!=sonXMLText.toString()&&!"".equals(sonXMLText.toString())){
////										sonXMLText = DataTypeConversion(sonXMLText.toString(),datasCheme.getDcdtocolumnstype().toUpperCase());// 如果类型不匹配就进行类型转换
////									}
////								}
////								sonrows.add(sonXML);
////								sonvalues.add(sonXMLText);
////								break;
////							}
////						}
////					}
////					String endNeedRow = null;// 最终添加的列的字符串
////					String endNeedValue = null;// 最终添加值的字符串
////					if (sonrows.size() == sonvalues.size()) {// 值集合的大小和列集合的大小一致
////						for (int a = 0; a < sonrows.size(); a++) {
////							if (null == endNeedRow|| "".equals(endNeedRow)) {// 判断列中是否有值
////								endNeedRow = sonrows.get(a);// 如果没值就赋于第一个值
////							} else {
////								endNeedRow += "," + sonrows.get(a);// 如果有值
////																	// 就在原来的值中添加
////							}
////							if (null == endNeedValue|| "".equals(endNeedValue)) {// 判断值中是否有值
////								endNeedValue = "'"+ sonvalues.get(a) + "'";
////							} else {
////								endNeedValue += ",'"+ sonvalues.get(a) + "'";
////							}
////						}
////					}
////					String SQL="insert into "+data.getName().trim()+" ("+endNeedRow+") values ("+endNeedValue+")";
////					Statement st=null;
////					try {
////						st=conn.createStatement();
////						System.out.println("第 " + count+ " 条SQL语句 = " + SQL);
////						count+=st.executeUpdate(SQL);
////					} catch (SQLException e) {
////						e.printStackTrace();
////					}finally{
////						try {
////							st.close();
////							System.out.println("子表成功插入第 "+ count + " 条");
////						} catch (SQLException e) {
////							e.printStackTrace();
////						}
////					}
////				}
////			}
////		}
//		//删除文件和文件夹
////		if(count>0){
//			File file=new File(docPath);
//			try{if(file.exists()){
//				File[] files=file.listFiles();
//				for(int i=0;i<files.length;i++){
//					File f=files[i];
//					f.delete();
//				}
//				file.delete();
//			}
////		}
//		String end=new SimpleDateFormat("HH:mm:ss").format(new Date());
////		System.out.println("结束时间:"+end);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		return SUCCESS;
//	}
//	
//	/**
//	 * 进行数据类型转换
//	 * 
//	 * @param data
//	 * @return
//	 */
//	private Object DataTypeConversion(String data, String type) {
//		Object obj = null;
//		String java = dbmsNewDataImportService.getJavaTypeByDataBaseType(type);// 得到对应的JAVA类型
//		if (java.equals("INTEGER")) {
//			obj = Integer.parseInt(data);
//		} else if (java.equals("BOOLEAN")) {
//			// 将String转换成boolean
//			obj = Boolean.parseBoolean(data);
//		} else if (java.equals("LONG")) {
//			obj = Long.parseLong(data);
//		} else if (java.equals("DOUBLE")) {
//			obj = Double.parseDouble(data);
//		} else if (java.equals("FLOAT")) {
//			obj = Float.parseFloat(data);
//		} else if (java.equals("DATE")) {
//			// 将String类型转换成Date
//			try {
//				obj = new SimpleDateFormat("yyyy-MM-dd").parse(data);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		} else if (java.equals("BYTE")) {
//			obj = Byte.parseByte(data);
//		}
//		return obj;
//	}
//	
	
}



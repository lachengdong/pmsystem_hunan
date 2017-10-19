package com.sinog2c.service.impl.dbmsnew;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.dbmsnew.DbmsDaochuListInfoMapper;
import com.sinog2c.dao.api.dbmsnew.DbmsDatabaseNewMapper;
import com.sinog2c.dao.api.dbmsnew.TbsysExchangeSchemeMapper;
import com.sinog2c.model.dbmsnew.DbmsDaochuListInfo;
import com.sinog2c.model.dbmsnew.DbmsDatabaseNew;
import com.sinog2c.model.dbmsnew.TbsysExchangeScheme;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.mvc.message.JSONMessage;
import com.sinog2c.service.api.common.CommonSQLSolutionService;
import com.sinog2c.service.api.dbmsnew.DataTransferService;
import com.sinog2c.service.api.dbmsnew.DbmsNewDataExportService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.Base64Util;
import com.sinog2c.util.common.DESUtil;
import com.sinog2c.util.common.FileUtil;
import com.sinog2c.util.common.GetAbsolutePath;
import com.sinog2c.util.common.JdbcConnUtil;
import com.sinog2c.util.common.MapUtil;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.util.common.TxtUtil;
import com.sinog2c.util.common.ZipUtil;

@Service("dataTransferService")
public class DataTransferServiceImpl extends ServiceImplBase implements DataTransferService{
	
	@Autowired
	private DbmsDaochuListInfoMapper daochuListInfoMapper;
	@Autowired
	private CommonSQLSolutionService commonSQLSolutionService;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private TbsysExchangeSchemeMapper tbsysExchangeSchemeMapper;
	@Autowired
	private DbmsDatabaseNewMapper dbmsDatabaseNewMapper;
	@Autowired
	private DbmsNewDataExportService dbmsNewDataExportService;
	
	private static SimpleDateFormat dateFormaterTimestamp = new SimpleDateFormat(GkzxCommon.DATETIMEFORMATNOSPLIT);
	

	public DataTransferServiceImpl getNewInstance() {
		DataTransferServiceImpl dataTransferService = new DataTransferServiceImpl();
//		dataTransferService.daochuListInfoMapper = this.daochuListInfoMapper;
		return dataTransferService;
	}
	
	
	
//	/**
//	 * 描述：数据导出
//	 * @author YangZR 2015-05-29
//	 */
//	@Override
//	@Transactional
//	public Map<String,Object> dataExport(HttpServletRequest request,Map<String,Object> paramap, SystemUser user){
//		request.getSession().setAttribute("percent", "处理中...");
//		
//		Map<String,Object> result = new HashMap<String,Object>();
//		result.put("status", 0);
//		
//		String basePath = "";
//		String currentTimestamp = "";
//		String preFilePath = "";
//		//
//		String xmlFileName = "";
//		
//		String fileRealName = "";
//		String filepath = "";
//		String filePartName = "";
//		
//		List<TbsysExchangeScheme> ecsList = tbsysExchangeSchemeMapper.getListByPrimaryKey(paramap);
//		if(null != ecsList && ecsList.size()>0){
//			filePartName = ecsList.get(0).getName();
//		}else{
//			return result;
//		}
//		
//		currentTimestamp = getCurrentTimestampStr();
//		// 导出时路径,得到项目绝对路径
//		basePath = GetAbsolutePath.getAbsolutePath(GkzxCommon.PATH + File.separator + filePartName);
//		basePath += currentTimestamp + File.separator;
//		//压缩包文件名	fileRealName = 减刑假释报送法院数据20160530150340157.zip
//		fileRealName = filePartName + currentTimestamp + ".zip";
//		filepath = File.separator + GkzxCommon.PATH + File.separator + fileRealName;
//		xmlFileName = basePath + currentTimestamp + ".xml";
//		//生成数据交换的xml数据
//		generateMainTransferData(request,paramap,basePath, xmlFileName, ecsList, user);
//		//将指定的罪犯的电子档案copy至临时目录中，以便后面处理
//		copyCriminalArchive2TempDir(request,paramap,basePath);
//		//
//		preFilePath = basePath.substring(0, basePath.lastIndexOf(File.separator));
//		//加密压缩数据，并将密码再加密后存到目录中去
//		compressData(request,basePath, preFilePath);
//		//保存导出信息
//		saveExportInfo(filePartName, filepath, fileRealName, user);
//		//
//		result.put("status", 1);
//		result.put("filepath", filepath);
//		
//		
//		//删除压缩前的目录
//		request.getSession().setAttribute("percent", "正在清空临时数据...");
//		try {
//			FileUtils.deleteDirectory(new File(preFilePath));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		request.getSession().setAttribute("percent", "100%");
//		
//		return result;
//	}
	
	/**
	 * 描述：数据导出
	 * @author YangZR 2015-05-29
	 */
	@Override
	@Transactional
	public Map<String,Object> dataExport(HttpServletRequest request,Map<String,Object> paramap, SystemUser user){
		
		request.getSession().setAttribute("percent", "处理中...");
		
		Map<String,Object> result = packageAndZipData(request, null, paramap, user);
		
		request.getSession().setAttribute("percent", "100%");
		
		return result;
	}
	
	private Map<String,Object> packageAndZipData(HttpServletRequest request, String filePartName, Map<String,Object> paramap, SystemUser user){
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("status", 0);
		
		String basePath = "";
		String currentTimestamp = "";
		String preFilePath = "";
		//
		String xmlFileName = "";
		
		String fileRealName = "";
		String filepath = "";
		
		List<TbsysExchangeScheme> ecsList = tbsysExchangeSchemeMapper.getListByPrimaryKey(paramap);
		if(null != ecsList && ecsList.size()>0){
			if(StringNumberUtil.isEmpty(filePartName)){
				filePartName = ecsList.get(0).getName();
			}
		}else{
			return result;
		}
		
		currentTimestamp = getCurrentTimestampStr();
		// 导出时路径,得到项目绝对路径
		basePath = GetAbsolutePath.getAbsolutePath(GkzxCommon.PATH + File.separator + filePartName);
		basePath += currentTimestamp + File.separator;
		//压缩包文件名	fileRealName = 减刑假释报送法院数据20160530150340157.zip
		fileRealName = filePartName + currentTimestamp + ".zip";
		filepath = File.separator + GkzxCommon.PATH + File.separator + fileRealName;
		xmlFileName = basePath + currentTimestamp + ".xml";
		//生成数据交换的xml数据
		generateMainTransferData(request,paramap,basePath, xmlFileName, ecsList, user);
		//将指定的罪犯的电子档案copy至临时目录中，以便后面处理
		copyCriminalArchive2TempDir(request,paramap,basePath);
		//
		preFilePath = basePath.substring(0, basePath.lastIndexOf(File.separator));
		//加密压缩数据，并将密码再加密后存到目录中去
		compressData(request,basePath, preFilePath);
		//保存导出信息
		saveExportInfo(filePartName, filepath, fileRealName, user);
		//
		result.put("status", 1);
		result.put("filepath", filepath);
		result.put("filename", fileRealName);
		
		
		//删除压缩前的目录
		if(null != request){
			request.getSession().setAttribute("percent", "正在清空临时数据...");
		}
		
		try {
			FileUtils.deleteDirectory(new File(preFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * @Description: 生成数据交换的xml数据
	 * @param
	 * @return void  
	 * @throws Exception 
	 * @throws
	 * @author 杨祖榕
	 * @date 2016年6月4日  下午7:02:14
	 * @Version 3.1
	 */
	public void generateMainTransferData(HttpServletRequest request,Map<String,Object> paramap,String basePath, String xmlFileName, 
			List<TbsysExchangeScheme> ecsList, SystemUser user){
		OutputStream outStream = null;
		try{
			SAXTransformerFactory fac = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			TransformerHandler handler = fac.newTransformerHandler();
			
			Transformer transformer = handler.getTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, GkzxCommon.encoding);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			
			File file = new File(basePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(xmlFileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			
			outStream = new FileOutputStream(file);
			handler.setResult(new StreamResult(outStream));
			if(null != ecsList && ecsList.size()>0){
				// 写入数据
				handler.startElement("", "", "root", null);
				
				if(null !=  request){
					request.getSession().setAttribute("percent", "正在处理数据...");
				}
				
				for(int i=0; i<ecsList.size(); i++){
					
					List<?> data = new ArrayList<Object>();
					
					String solutionid = ecsList.get(i).getSolutionid();
					paramap.put("solutionid", solutionid);
					
					Map<String,Object> resultMap = commonSQLSolutionService.list(paramap, user);
					Object list = resultMap.get("list");
					if(list instanceof List<?>){
						List<Map<String, Object>> dataList = (List<Map<String, Object>>)list;
						data = MapUtil.convertKeyList2LowerCase(dataList);
					}
					
					if(data.size() > 0 ){
						//
						for(int j=0; j<data.size(); j++){
							Object temObj = data.get(j);
							if(null != temObj && temObj instanceof Map){
								Map<String,Object> temMap = (Map<String,Object>)temObj;
								temMap.put("solutionid", solutionid);
								
								if(null != temMap){
									handler.startElement("", "", "map", null);
									Set<String> set = temMap.keySet();
									for(String key : set){
										handler.startElement("", "", "object", null);
										Object valueObj = temMap.get(key);
										String value = ( valueObj==null ? "":valueObj.toString() );
										setXmlNode(handler, "key", key);
										setXmlNode(handler, "value", value);
										handler.endElement("", "", "object");
									}//内层for循环结束
									handler.endElement("", "", "map");
								}
							}
							
						}//外层for循环结束
					}//data.size()
				}
				//
				handler.endElement("", "", "root");
			}
			handler.endDocument();
		}catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally{
			try {
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * @Description: 将指定的罪犯的电子档案copy至临时目录中，以便后面处理
	 * @param
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016年6月4日  下午6:47:34
	 * @Version 3.1
	 */
	private void copyCriminalArchive2TempDir(HttpServletRequest request,Map<String,Object> paramap,String basePath){
		
		List<Map<String,Object>> criminalidList = null;
		String isArch = paramap.get("isArch")==null?"":paramap.get("isArch").toString();//是否查电子档案
		if(StringNumberUtil.notEmpty(isArch) && "1".equals(isArch.trim())){
			criminalidList = getCrimeByFlowdraftids(paramap);
		}
		
		//电子档案的操作
		if(null != criminalidList && criminalidList.size()>0){
			
			if(null != request){
				request.getSession().setAttribute("percent", "正在处理电子档案...");
			}
			
			Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			//档案目录的上级目录
			String strPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH) == null ? 
										"": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH);
			String archPath = GetAbsolutePath.getAbsolutePathAppend(strPath, GkzxCommon.CRIMINALARCHIVES_PATH);
			copyCriminalArchive(archPath, basePath + GkzxCommon.CRIMINALARCHIVES_PATH,criminalidList);
		}
		
	}
	
	/**
	 * @Description: 加密压缩数据，并将密码再加密后存到目录中去。
	 * @param	zipFolder 被压缩的目录
	 * 			preFilePath	压缩后文件的路径（不包括后缀）
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016年6月4日  下午6:55:20
	 * @Version 3.1
	 */
	private void compressData(HttpServletRequest request,String zipFolder, String preFilePath){
		
		if(null != request){
			request.getSession().setAttribute("percent", "正在压缩数据...");
		}
		//压缩成zip文件的全路径
		String zipfilepath = preFilePath + ".zip";
		
		String password = ZipUtil.randomPasswd();//压缩加密
		
		ZipUtil.zipAllUnderFolder(zipFolder,zipfilepath,password);
//		DocZipUtil.compress(zipFolder, zipfilepath);
		
		//对生成的password再次加密
		String encPasswd = "";
		try {
			encPasswd = DESUtil.encrypt(password);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
		//生成文件的内容
		StringBuilder sb = new StringBuilder();
		sb.append(StringNumberUtil.getUUID()).append("\t").append(new File(preFilePath).getName()).append("\t").append(encPasswd);
		String content = sb.toString();
		//保存加密密码等内容到文件中
		String encfile = preFilePath+GkzxCommon.ENCRYPT_FILE_SUFFIX;
		TxtUtil.writeAppend(encfile, content, null);
		//再次对加密文件进行加密
		try {
			Base64Util.encode(new File(encfile));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		//将此加密文件一起放入压缩包，但是不能使用密码，后面解密时，会先无密码提取此文件，再将此文件解密后，提取里面的密码进行解密解压
		File addFile = new File(encfile);
		ZipUtil.addFile2Zip(addFile,zipfilepath);
		addFile.delete();
		
	}
	
	
	private void setXmlNode(TransformerHandler handler, String nodeName, String nodeValue) throws Exception{
		
		handler.startElement("", "", nodeName, null);
		handler.characters(nodeValue.toCharArray(), 0, nodeValue.length());
		handler.endElement("", "", nodeName);
		
	}
	
	/**
	 * 保存导出信息
	 * @author YangZR	2015-06-01
	 * @param filePartName
	 * @param filepath
	 * @param fileRealName
	 */
	private void saveExportInfo(String filePartName, String filepath, String fileRealName, SystemUser user){
		
		DbmsDaochuListInfo ddli = new DbmsDaochuListInfo();
		// 使用查询的UUID
		String queryuuid =  StringNumberUtil.getUUID();
		//
		String fileid = queryuuid;
		ddli.setCreatedate(new Date());
		ddli.setFileid(fileid);
//		ddli.setSchemeid(bean.getDdcid());
		
		ddli.setSdid(user.getDepartid());
		ddli.setFilename(filePartName);
		ddli.setFilepath(filepath);
		ddli.setFilerealname(fileRealName);
		ddli.setCreatemender(user.getUserid());
		ddli.setUpdatemender(user.getUserid());
		
		daochuListInfoMapper.insertSelective(ddli);
		
	}
	
	/**
	 * 描述：通过flowdraftids 获取crimid的集合
	 * @param paramap
	 * @return
	 */
	private List<Map<String,Object>> getCrimeByFlowdraftids(Map<String,Object> paramap){
		return flowBaseService.getCrimeByFlowdraftids(paramap);
	}
	
	private List<String> getCrimidByFlowDraftids(Map<String,Object> paramap){
		
		List<String> crimidList = new ArrayList<String>();
		List<Map<String,Object>> criminalList = getCrimeByFlowdraftids(paramap);
		if(null != criminalList && criminalList.size() >0){
			for(Map<String,Object> map : criminalList){
				Object crimidObj = map.get("crimid");
				if(null != crimidObj){
					crimidList.add(crimidObj.toString());
				}
			}
		}
		
		return crimidList;
	}
	
	/**
	 *@描述：导入数据
	 *@author YangZR	2015-05-29
	 *@param	filePath : 要导入数据的目录<br/>
	 *			user	 : 当前用户
	 */
	@Override
	@Transactional
	public boolean dataImport(HttpServletRequest request,String filePath,SystemUser user){
		try{
			File dirFile = new File(filePath);
			// 如果dir对应的文件/目录不存在，则退出
			if (!dirFile.exists()) {
				return false;
			}
			//列出目录内文件列表
			File[] files = new File[0];
			if (dirFile.isDirectory()) {
				files = dirFile.listFiles();
			}else if(dirFile.isFile()){
				files[0] = dirFile;
			}
			//
			for (int i = 0; i < files.length ; i++) {
				// 本次要处理的XML文件
				File xmlFile = null;
				//
				if (null !=files[i] && files[i].isFile() && files[i].getName().endsWith(".xml")) {
					xmlFile = files[i];
				}else if(null !=files[i] && files[i].isDirectory()){
					request.getSession().setAttribute("percent", "正在导入电子档案，请耐心等待...");
					//电子档案的导入
					importCriminalArchive(files[i]);
					continue;
				}else {
					//其他情况,则跳过此文件
					continue;
				}
				
				if (null == xmlFile || !xmlFile.exists()){
					// 不存在,跳过记录
					log("xmlFile文件不存在!");
					continue;
				}
				
				// 开始解析文件
				/*
					<root>
						<map>
							<object>
							  <key></key>
							  <value></value>
							</object>
							<object>...</object>
						</map>
						
						<map>...</map>
					</root>
				 */
				
				SAXReader saxReader = new SAXReader();// 解析
				Document document = saxReader.read(xmlFile);// 获取文档DOM对象
				Element root = document.getRootElement();//获取根元素
				// 遍历
				Iterator<Element> mapIter = root.elementIterator("map");
				
				Map<String,List<Map<String,Object>>> dataMap = new HashMap<String,List<Map<String,Object>>>();
				
				while (mapIter.hasNext()){
					//
					Element map = (Element) mapIter.next();
					Map<String,Object> paramap = new HashMap<String,Object>();
					Iterator<Element> objectIter = map.elementIterator("object");
					while ( objectIter.hasNext()) {
						Element object = (Element) objectIter.next();
						Element keyEle = object.element("key");
						Element valueEle = object.element("value");
						
						String key = keyEle.getTextTrim();
						String value = valueEle.getTextTrim();
						
						paramap.put(key, value);
					}
					
					if(null != paramap && paramap.containsKey("solutionid")){
						String solutionid = StringNumberUtil.getStrFromMap("solutionid", paramap);
						List<Map<String,Object>> tempList = null;
						if(dataMap.containsKey(solutionid)){
							tempList = dataMap.get(solutionid);
						}else{
							tempList = new ArrayList<Map<String,Object>>();
						}
						tempList.add(paramap);
						dataMap.put(solutionid, tempList);
//						commonSQLSolutionService.save(paramap, user);
					}
				}
				
				Set<String> keySet = dataMap.keySet();
				for(String key : keySet){
					List<Map<String,Object>> dataList = dataMap.get(key);
					commonSQLSolutionService.batchSave(dataList, user);
				}
			}
			
			return true; //只有这里返回 true
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	} 
	
	
	/**
	 * @Description: 拷贝档案到指定目录中
	 * @param	archPath：档案根目录，desPath：拷贝的目地目录，criminalidList：罪犯列表
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016年5月30日  下午1:07:28
	 * @Version 3.1
	 */
	private void copyCriminalArchive(String archPath,String desPath, List<Map<String,Object>> criminalidList){
		for(int i = 0; i < criminalidList.size(); i++){
			Map criminalidMap = criminalidList.get(i);
			String desArchPath = desPath + File.separator + criminalidMap.get("departid") + File.separator + criminalidMap.get("crimid");
			String crimidArchPath = archPath + File.separator + criminalidMap.get("departid") + File.separator + criminalidMap.get("crimid");
			try {
				FileUtil.batchCopyFile(crimidArchPath, desArchPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * @Description: 部门电子档案复制到一个临时目录中。（主要用于cwrsync工具进行跨网段传输）
	 * @param	criminalidList中每一个Map: {crimid:610555555555, departid:6105}
	 * @return void  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016年5月19日  上午10:15:26
	 * @Version 3.1
	 */
	@Override
	public void preExportArchive(List<Map<String,Object>> criminalidList){
		//
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String basePath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH) == null ? 
									"": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH);
		//
		String archPath = GetAbsolutePath.getAbsolutePathAppend(basePath, GkzxCommon.CRIMINALARCHIVES_PATH);
		String desPath = GetAbsolutePath.getAbsolutePathAppend(basePath, GkzxCommon.TEMPARCHIVES_PATH);
//		String srcPath = "D:\\criminalarchives\\CriminalArchives";
//		String desPath = "D:\\criminalarchives\\TempArchives";
		
		copyCriminalArchive(archPath,desPath,criminalidList);
	}
	
	
	private String getCurrentTimestampStr(){
		String currentDateStr = dateFormaterTimestamp.format(new Date());
		return currentDateStr;
	}
	
	/**
	 * 电子档案的导入
	 * @param file
	 */
	private void importCriminalArchive(File file){
		
		String strPath  = file.getPath();
		int intIndex = strPath.lastIndexOf(File.separator);
		String strTmpPath = strPath.substring(intIndex + 1);
//		String srcPath = GetAbsolutePath.getAbsolutePath(strPath);
		
		Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
		String desPath = jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH) == null ? "": jyconfig.getProperty(GkzxCommon.CRIMINALARCHIVES_ABSOLUTEPATH);
		desPath = GetAbsolutePath.getAbsolutePathAppend(desPath, GkzxCommon.CRIMINALARCHIVES_PATH);
		
		if(strTmpPath.equals(GkzxCommon.CRIMINALARCHIVES_PATH)) {
			try {
				FileUtil.batchCopyFile(strPath, desPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	/**
	 * 数据交换
	 * @author YangZR	2015-06-19
	 * paramap ：为从前台传的参数Map，包含操作某监狱下的flowdraftids(如省局可操作所有监狱的数据，但是在操作时，分监狱操作，一个监狱一个监狱进行操作)
	 * 				    paramap应包含数据传输对应的参数：ddcid  excid		(ddcid 传递的不合理，后期可能要改)
	 * remoteDepartid ：远程传递过来的远程单位ID
	 * crimids ：远程传递过来的要操作的罪犯编号
	 * isArch ：远程传递过来的是否要操作电子档案(目录中)的标志
	*/
	@Override
	@Transactional
	public JSONMessage dataExchange(String type,Map<String,Object> paramap, SystemUser user, String remoteDepartid, String crimids, String isArch){
		JSONMessage message = new JSONMessage();
		//数据交换
		Map<String,Object> tempMap = new HashMap<String,Object>();
		tempMap.put("departid", remoteDepartid);
		tempMap.put("dchangeflag", "1");
		DbmsDatabaseNew remoteDB = null;
		List<DbmsDatabaseNew> ddbnList= dbmsDatabaseNewMapper.getDbmsDataBaseNewByDepartid(tempMap);
		if(null != ddbnList && ddbnList.size()>0){
			remoteDB = ddbnList.get(0);
			dataExchange(type,paramap, user, remoteDB);
		}
		//是否交换电子档案
//		if(StringNumberUtil.notEmpty(isArch) && "1".equals(isArch.trim()) && StringNumberUtil.notEmpty(crimids)){
//			//电子档案的传递
//			List<String> crimidList = StringNumberUtil.formatString2List(crimids, ",");
//			String excid = paramap.get("excid")==null ?"":paramap.get("excid").toString();
//			electronicFileExchange(crimidList, excid, remoteDepartid);
//		}
		message.setSuccess();
		return message;
	}
	
	
	/**
	 * @描述:　数据交换(不包括电子档案的传递)
	 * @author YangZR	2015-06-19
	 * paramap ：包含操作某监狱下的flowdraftids(如省局可操作所有监狱的数据，但是在操作时，分监狱操作，一个监狱一个监狱进行操作)
	 * 				    paramap应包含数据传输对应的参数：excid
	 * user：模拟的用户
	 * remoteDB : 远程数据来源所对应的 数据库配制信息
	 */
	@Transactional
	public JSONMessage dataExchange(String type, Map<String,Object> paramap, SystemUser user, DbmsDatabaseNew remoteDB){
		JSONMessage message = new JSONMessage();
		Connection localConn = null;
		Connection remoteConn = null;
		try{
			localConn = JdbcConnUtil.getConn();
			remoteConn = JdbcConnUtil.getConnection(remoteDB);
			
			if(null != remoteConn && null != localConn){
				if("1".equals(type)){
					doDataExchange(paramap, user, localConn, remoteConn);
				}else if("2".equals(type)){
					doDataExchange(paramap, user, remoteConn,localConn);
				}
			}else {
				throw new RuntimeException("获取数据库链接失败！");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			JdbcConnUtil.close(remoteConn);
			JdbcConnUtil.close(localConn);
		}
		message.setSuccess();
		return message;
	}
	
	private void doDataExchange(Map<String,Object> paramap, SystemUser user, Connection saveConn, Connection checkConn){
		List<TbsysExchangeScheme> ecsList = tbsysExchangeSchemeMapper.getListByPrimaryKey(paramap);
		try{
			if(null != ecsList && ecsList.size()>0){
				for(int i=0; i<ecsList.size(); i++){
					List<?> data = new ArrayList<Object>();
					
					String solutionid = ecsList.get(i).getSolutionid();
					paramap.put("solutionid", solutionid);
					
					Map<String,Object> resultMap = commonSQLSolutionService.list(paramap, user, checkConn);
					Object list = resultMap.get("list");
					if(list instanceof List<?>){
						List<Map<String, Object>> dataList = (List<Map<String, Object>>)list;
						data = MapUtil.convertKeyList2LowerCase(dataList);
					}
					
					if(data.size() > 0 ){
						//
						for(int j=0; j<data.size(); j++){
							Object temObj = data.get(j);
							if(null != temObj && temObj instanceof Map){
								Map<String,Object> temMap = (Map<String,Object>)temObj;
								temMap.put("solutionid", solutionid);
								commonSQLSolutionService.save(temMap, user, saveConn);
							}
						}
					}//data.size()
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
	/**
	 * 描述：电子档案的传递
	 * @author YangZR	2015-06-19
	 * @return
	 */
	@Transactional
	public JSONMessage electronicFileExchange(List<String> crimidList, String ddcid, String remoteDepartid){
		JSONMessage message = new JSONMessage();
		Connection localConn = null;
		try{
			localConn = JdbcConnUtil.getConn();
			dbmsNewDataExportService.newOutPutArchives(crimidList, localConn, ddcid, remoteDepartid);
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}finally{
			JdbcConnUtil.close(localConn);
		}
		message.setSuccess();
		return message;
	}
	
	
}

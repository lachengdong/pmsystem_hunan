package com.sinog2c.ws.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.alibaba.druid.support.json.JSONUtils;
import com.gkzx.common.GkzxCommon;
import com.gkzx.util.property.GetProperty;
import com.sinog2c.dao.api.dbmsnew.TbsysServicesMapper;
import com.sinog2c.model.dbmsnew.DbmsDatabaseNew;
import com.sinog2c.model.dbmsnew.DbmsNewDataExportBean;
import com.sinog2c.model.dbmsnew.TbsysServices;
import com.sinog2c.model.system.SystemUser;
import com.sinog2c.service.api.dbmsnew.DataTransferService;
import com.sinog2c.service.api.dbmsnew.DbmsDatabaseNewService;
import com.sinog2c.service.api.dbmsnew.DbmsDatasChemeNewService;
import com.sinog2c.service.api.dbmsnew.DbmsDatasTableNewService;
import com.sinog2c.service.api.dbmsnew.DbmsNewDataExportService;
import com.sinog2c.service.api.flow.FlowBaseService;
import com.sinog2c.service.impl.base.ServiceImplBase;
import com.sinog2c.util.common.DateUtils;
import com.sinog2c.util.common.FileUtil;
import com.sinog2c.util.common.GetAbsolutePath;
import com.sinog2c.util.common.StringNumberUtil;
import com.sinog2c.ws.IWebServiceClient;

@Service
public class WebServiceClientImpl extends ServiceImplBase implements IWebServiceClient{
	
	@Autowired
	private DbmsDatasChemeNewService dbmsDatasChemeNewService;
	@Autowired
	private DbmsDatabaseNewService dbmsDatabaseNewService;
	@Autowired
	private TbsysServicesMapper tbsysServicesMapper;
	@Autowired
	private FlowBaseService flowBaseService;
	@Autowired
	private DbmsDatasTableNewService tableNewService;
	@Autowired
	private DbmsNewDataExportService dbmsNewDataExportService;
	@Autowired
	DataTransferService dataTransferService;
	
	
	@Override
	@Transactional
	public Object webServiceSender(SystemUser user, Map<String,Object> paramap, HttpServletRequest request){
		
		List<Map<String,Object>> list = flowBaseService.findFlowBaseData(paramap);
		int count = 0;
		int total = list.size();
		
		String excid = StringNumberUtil.getStrFromMap("excid", paramap);
		String remoteDepartid = StringNumberUtil.getStrFromMap("departid", paramap);//远程单位id
		
		if(StringNumberUtil.notEmpty(remoteDepartid)){
			Map<String,Object> senderData = list.get(0);
			senderData.put("excid", excid);
			dataExchangeNoWebService(remoteDepartid, user, senderData,count,total,request);
			//
		}else{
			for(Map<String,Object> senderData : list){
				senderData.put("excid", excid);
				remoteDepartid = senderData.get("departid").toString();
				dataExchangeNoWebService(remoteDepartid, user, senderData,count,total,request);
				//
				count++;
			}
		}
		
		flowBaseService.setSendedStatus(paramap);
		
		return "1";
	}
	
	private void dataExchangeNoWebService(String remoteDepartid, SystemUser user, Map<String,Object> senderData,
			int count,int total,HttpServletRequest request){
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
		String type = "2";
		
		dataTransferService.dataExchange(type,senderData, user, remoteDepartid, null, null);
		//
		count ++ ;
		double percent = count*100.0/total;
		request.getSession().setAttribute("percent", df.format(percent));
		
		
		/*String flowdraftids = StringNumberUtil.returnString2(senderData.get("flowdraftids"));
		String[] flowdraftidArr = flowdraftids.split(",");
		for(String flowdraftid : flowdraftidArr){
			senderData.put("flowdraftids", flowdraftid);
			dataTransferService.dataExchange(type,senderData, user, remoteDepartid, null, null);
			//垃圾回收
			System.gc();
			//
			count ++ ;
			double percent = count*100.0/total;
			request.getSession().setAttribute("percent", df.format(percent));
			
		}*/
		
	}
	
	
	private Object wsClientSendSignal(String departid, Map<String,Object> senderData){
		//通过departid 获取
		TbsysServices sysServices = tbsysServicesMapper.selectByPrimaryKey(departid);
		wsClientSendSignal(sysServices, senderData);
		return 1;
	}
	
	
	/**
	 * @Description: TODO
	 * @param
	 * @return Object  
	 * @throws
	 * @author 杨祖榕
	 * @date 2016年5月26日  上午10:03:50
	 * @Version 3.1
	 */
	private Object wsClientSendSignal(TbsysServices sysServices, Map<String,Object> params){
		String url = "http://" + sysServices.getAddress() +":"+ sysServices.getPort() +"/"+GkzxCommon.PROJECTNAME+"/ws/"+GkzxCommon.PROJECTNAME+"?wsdl";
		String businesstype = "1";//业务类型   1 通过查询方案交换数据（如：监狱与省局）
		
		params.put("businesstype", businesstype);
		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
		Client client = factory.createClient(url);
		
		//webService采用单个发送，以防止time out
		String flowdraftids = StringNumberUtil.returnString2(params.get("flowdraftids"));
		String[] flowdraftidArr = flowdraftids.split(",");
		for(String flowdraftid : flowdraftidArr){
			params.put("flowdraftids", flowdraftid);
			clientWebServiceSender(params,client);
		}
		
		return "1";
		
	}
	
	

	@Override
	public Object wsYwgkByProcedure(String url,Map<String,Object> callSql) {
		
		String businesstype = "3";//业务类型   3  通过调用服务端的存储过程（狱务公开）
		Object result =  clientWebServiceSender(url, businesstype, callSql);
		return result;
	}

	@Override
	public Object wsYwgkByDataScheme(String url,String ddcid,Map<String,Object> paramMap) {
		paramMap.put("ddcid", ddcid);
		
		String businesstype = "2";//业务类型   2 通过老版数据交换方案交换数据（狱务公开）
		Object result = clientWebServiceSender(url, businesstype, paramMap);
		return result;
	}
	
	
	/**
	 * @Description: WS的客户端发送请求
	 * @param	url：服务端的url		
	 * 			businesstype：业务类型	1  通过查询方案交换数据（如：监狱与省局），2 通过老版数据交换方案交换数据（狱务公开）
	 * 								3  通过调用服务端的存储过程（狱务公开）
	 * @author 杨祖榕
	 * @date 2016年5月26日  上午10:05:24
	 * @Version 3.1
	 */
	private Object clientWebServiceSender(String url, String businesstype, Map<String,Object> params){
		params.put("businesstype", businesstype);
		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
		Client client = factory.createClient(url);
		return clientWebServiceSender(params,client);
		
	}
	
	/**
	 * @Description: WS的客户端发送请求
	 * @param	url：服务端的url		
	 * 			businesstype：业务类型	1  通过查询方案交换数据（如：监狱与省局），2 通过老版数据交换方案交换数据（狱务公开）
	 * 								3  通过调用服务端的存储过程（狱务公开）
	 * @author 杨祖榕
	 * @date 2016年5月26日  上午10:05:24
	 * @Version 3.1
	 */
	private Object clientWebServiceSender(Map<String,Object> params,Client client){
		QName opName = new QName("http://ws.sinog2c.com/", "wsServiceReceive");  // 指定到接口类所在包
		try {
			String paramsStr = JSONUtils.toJSONString(params);
			Object[] result = client.invoke(opName, paramsStr); // 按照方法的参数来提供值
			return result[0];

		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
	
	@Override
	public Object executeWebXmlResolve(DbmsNewDataExportBean bean,SystemUser user) {
		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
		DbmsDatabaseNew fromDatabase = dbmsDatabaseNewService.selectFromDatabaseByDdcid(bean.getDdcid());
		//获取wsdl地址
		String wsdlUrl = StringNumberUtil.getWsdlUrl(fromDatabase.getDdip(), fromDatabase.getPort().toString(), fromDatabase.getDatabasename());
		Client client = factory.createClient(wsdlUrl);
//		QName opName = new QName("http://ws.sinog2c.com/", fromDatabase.getDatabaseuser());//getDatabaseuser是要远程调用的方法名称
		Endpoint endpoint = client.getEndpoint(); 
		String operation = fromDatabase.getDatabaseuser();//getDatabaseuser是要远程调用的方法名称
		QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), operation);  
		BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();  
		if (bindingInfo.getOperation(opName) == null) {  
		    for (BindingOperationInfo operationInfo : bindingInfo.getOperations()) {  
		        if (operation.equals(operationInfo.getName().getLocalPart())) {  
		            opName = operationInfo.getName();  
		            break;  
		        }  
		    }  
		}
		try {
			//客户端开始发送请求...
			Object[] result = client.invoke(opName);// 按照方法的参数来提供值 
			//客户端请求结束...result[0]为返回的值
			System.out.println("客户端请求结束..."+result[0]);
			 //获取xml存放路径
		    Properties jyconfig = new GetProperty().bornProp(GkzxCommon.DATABASETYPE, null);
			String XmlDirName = jyconfig.getProperty(GkzxCommon.WEB_XML_RESOLVE) == null ? "": jyconfig.getProperty(GkzxCommon.WEB_XML_RESOLVE); 
			String xmlFileDir = GetAbsolutePath.getAbsolutePath(XmlDirName);
		    String xmlFilePath = null;
		    if(FileUtil.createDirIfNotExist(xmlFileDir)){//判断是否存在xml的文件夹，不存在就创建。
		    	 //xml文件命名：方案ddcid_时间.xml
		    	 String xmlName = bean.getDdcid()+"_"+DateUtils.date2String(new Date(),"yyyyMMddHHmmss")+"_in.xml";
		    	 xmlFilePath = GetAbsolutePath.getAbsolutePath(XmlDirName+GkzxCommon.SINGLESEPALINE+xmlName);
		    	 OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(xmlFilePath),"UTF-8");
//		    	 FileWriter fw = new FileWriter(xmlFilePath);
		    	 out.write(result[0].toString());
		    	 out.flush();
		    	 out.close();
		    }
			localSaveFileData(xmlFilePath,bean,user);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 读取本地xml文件解析成数据保存到相关表中
	 */
	private boolean localSaveFileData(String localXmlFileUrl,DbmsNewDataExportBean bean,SystemUser user) {
		boolean result = false;
		File file = new File(localXmlFileUrl);// 指定File文件
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ddcid", bean.getDdcid());
		map.put("sdid", user.getDepartid());
		String results = "";//结果集对应节点值
		//根据方案编号获取xml解析配置表信息
		List<Map<String, Object>> chemelist = dbmsDatasChemeNewService.getDbmsresolvewebxmlList(map);
		if(chemelist.size()>0){
			for(int i=0;i<chemelist.size();i++){
				Map<String, Object> chememap = chemelist.get(i);
				if(chememap.get("isdataitems").equals("1") && chememap.get("iscycle").equals("1")){
					results = chememap.get("nodename").toString();
				}
			}
		}
	    // 建立DocumentBuilderFactory对象
	    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();// 建立DocumentBuilder对象
			 // 用DocumentBuilder对象的parse方法引入文件建立Document对象
		     org.w3c.dom.Document document = builder.parse(file);
		     //NodeList  allNode =  (NodeList) ((Node) document).getChildNodes();
		     
		     NodeList reultlist = ((org.w3c.dom.Document) document).getElementsByTagName(results);// 找出所有指定标签，返回NodeList
		     // 对符合条件的所有节点进行遍历
		     for (int i = 0; i < reultlist.getLength(); i++){
		    	 Node element =  reultlist.item(i);// 获得一个元素
				 NodeList nodelist = ((Node) element).getChildNodes();// 此元素有子节点，获取所有子节点，返回一个List
				 // 遍历所有子节点
				 Map nodeMap = new HashMap();
				 for (int j = 0; j < nodelist.getLength(); j++){
					 // 若子节点的名称不为#text，则输出，#text为反／标签
					 if (!nodelist.item(j).getNodeName().equals("#text")){
						 // 输出节点名称、节点值
						 System.out.println(nodelist.item(j).getNodeName() + ":"+ nodelist.item(j).getTextContent());
						 nodeMap.put(nodelist.item(j).getNodeName().toLowerCase(), nodelist.item(j).getTextContent());
					}
				}
				result = dbmsNewDataExportService.localSaveFileData(bean,nodeMap);
		     } 
		    // 文件不为空则进行删除   
		    if (result && file.isFile() && file.exists()) {   
		        file.delete();   
		        return result;
		    }
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}
	

	@Override
	public String testWebXmlResolve(DbmsNewDataExportBean bean, SystemUser user) {
		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
		DbmsDatabaseNew fromDatabase = dbmsDatabaseNewService.selectFromDatabaseByDdcid(bean.getDdcid());
		//获取wsdl地址
		String wsdlUrl = StringNumberUtil.getWsdlUrl(fromDatabase.getDdip(), fromDatabase.getPort().toString(), fromDatabase.getDatabasename());
		Client client = factory.createClient(wsdlUrl);
		QName opName = new QName("http://ws.sinog2c.com/", fromDatabase.getDatabaseuser());//getDatabaseuser是要远程调用的方法名称
		String resultstr="未获取到数据！";
		try {
			Object[] result = client.invoke(opName);// 按照方法的参数来提供值
			if(resultstr!=null && resultstr.length()>0){
				resultstr = (String)result[0];
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return resultstr;
	}
	
	
	
	
//	@Override
//	public Object wsCaseDataExchange(String ip, String port, String ddcid,String condition){
//		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
//		String url = "http://" + ip +":"+ port +"/"+GkzxCommon.PROJECTNAME +"/ws/"+GkzxCommon.PROJECTNAME+"?wsdl";
//		Client client = factory.createClient(url);
//		QName opName = new QName("http://ws.sinog2c.com/", "caseDataExchange");  // 指定到接口类所在包
//		try {
//			Object[] result = client.invoke(opName, ddcid, condition); // 按照方法的参数来提供值
//			return result[0];
//
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//		
//		return null;
//	}

//	@Override
//	public Object wsCaseDataExchange_sx(Map map) {
//		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
//		String url = "http://" + map.get("ip") +":"+ map.get("port") +"/"+GkzxCommon.PROJECTNAME+"/ws/"+GkzxCommon.PROJECTNAME+"?wsdl";
//		Client client = factory.createClient(url);
//		QName opName = new QName("http://ws.sinog2c.com/", "caseDataExchange_sx");  // 指定到接口类所在包
//		try {
//			Object[] result = client.invoke(opName, map.get("ddcid")); // 按照方法的参数来提供值
//			return result[0];
//
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//		return null;
//	}

//	@Override
//	public Object wsCaseDataExchange_sxsj(Map map) {
//		JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
//		String url = "http://" + map.get("ip") +":"+ map.get("port") +"/"+GkzxCommon.PROJECTNAME+"/ws/"+GkzxCommon.PROJECTNAME+"?wsdl";
//		Client client = factory.createClient(url);
//		QName opName = new QName("http://ws.sinog2c.com/", "caseDataExchange_sxsj");  // 指定到接口类所在包
//		try {
//			Object[] result = client.invoke(opName, map.get("ddcid")); // 按照方法的参数来提供值
//			return result[0];
//
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//		return null;
//	}
	
}

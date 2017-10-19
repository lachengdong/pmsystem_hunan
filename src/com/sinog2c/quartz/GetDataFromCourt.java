package com.sinog2c.quartz;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.xml.namespace.QName;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gkzx.util.property.GetProperty;
import com.sinog2c.util.common.FileUtil;
import com.sinog2c.ws.service.OpenToOutsideService;

/**
 * 数据交换作业任务
 */
@Service
public class GetDataFromCourt {
	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(GetDataFromCourt.class);
	
	public static final JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
	
	@Resource
	OpenToOutsideService openToOutsideService;

	Properties jyconfig = new GetProperty().bornProp("courtJointPrisonconfig", null);
	/**
	 * 业务逻辑处理
	 */
	public void doBiz(String stateid) {
		System.gc();
		String webservicepath = (jyconfig.getProperty("webservicepath")== null?"":jyconfig.getProperty("webservicepath"));
		String courtjxjszippath = jyconfig.getProperty("courtjxjszippath");
		String persionid = jyconfig.getProperty("persionid");
		FileUtil.createDirIfNotExist(courtjxjszippath);
	 	//webservice的wsdl地址
        Client client = clientFactory.createClient(webservicepath);
		//Client client = clientFactory.createClient("http://127.0.0.1:8081/pmsystem/ws/gkzx?wsdl");
        Endpoint endpoint = client.getEndpoint(); 
       //如果调不到方法，说明服务端没有指定命名空间，命名空间不一样，需要用到这个QName—————————— sayHello是你要调的方法
     //QName opName = getQName(endpoint.getService().getName().getNamespaceURI(), "sayHello");
        QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), "getCaseDataJxjs");
		BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();  
		if (bindingInfo.getOperation(opName) == null) {  
			for (BindingOperationInfo operationInfo : bindingInfo.getOperations()) {  
				if ( "getCaseDataJxjs".equals(operationInfo.getName().getLocalPart())) {  
					opName = operationInfo.getName();  
					break;  
				}  
			}  
		}
		//如果命名空间不一样，需要用到这个QName——————————
        Object[] result1;
			try { 
				Object[] result = client.invoke(opName,"",persionid,stateid);
				getList(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				client.destroy();
				System.gc();
			}
			logger.info("调用结束");
	}
	
	public void getList(Object[] result){
		String webservicepath = (jyconfig.getProperty("webservicepath")== null?"":jyconfig.getProperty("webservicepath"));
		String courtftpIP = jyconfig.getProperty("courtftpIP"); 
		String courtftpPort = jyconfig.getProperty("courtftpPort");
		String courtftpUser = jyconfig.getProperty("courtftpUser");
		String courtftpPwd = jyconfig.getProperty("courtftpPwd");
		String courtjxjszippath = jyconfig.getProperty("courtjxjszippath");
		//取出文件路径
		 Document doc;
		try {
			doc = DocumentHelper.parseText((String) result[0]);
			List fileList = new ArrayList();
			List pathlist = doc.selectNodes("result/R/path");
		    for (int i=0;i<pathlist.size();i++) {
		    	Map map = new HashMap<String, Object>();
		    	Element  root = doc.getRootElement();
				 Element stageid = root.element("stageid");
				 String stage = stageid.getTextTrim();
		    	Element e = (Element) pathlist.get(i);
		    	int last = e.getText().lastIndexOf("/");
				System.out.println("文件路径-----------------"+e.getText().substring(0,last-1));
		    	System.out.println("文件名称----------------"+e.getText().substring(last-1));
		    	map.put("ftppath", e.getText().substring(0,last));
		    	map.put("fileName", e.getText().substring(last+1));
		    	map.put("localDirPath", courtjxjszippath );
		    	map.put("stageid", stage);
		    	fileList.add(map);
		    }
			
		    List<Map<String, Object>> resultList = batchDownloadFile(courtftpIP, Integer.valueOf(courtftpPort), courtftpUser,courtftpPwd,fileList);
		    //Boolean a = FtpUtil.downloadFile(FtpUtil.getFTPClient(courtftpIP, Integer.valueOf(courtftpPort), courtftpUser,courtftpPwd),e.getText().substring(0,last),e.getText().substring(last),courtjxjszippath);
	    	/*System.out.println("------------------------------"+a.toString());*/
		    for (int i = 0; i < resultList.size(); i++) {
		    	String result1 = openToOutsideService.getCaseData(resultList.get(i).get("localDirPath").toString(),resultList.get(i).get("filenames").toString(),resultList.get(i).get("stageid").toString());
			}
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	//批量下载
	public static List<Map<String,Object>> batchDownloadFile(String url, int port, String username,String password, List<Map<String,Object>> list){
		BufferedOutputStream outStream = null;
		List resultlist = new ArrayList<Map>();
		FTPClient ftp = new FTPClient();// 创建FTPClient对象
		FTPClientConfig ftpClientConfig = new FTPClientConfig();
		try {
			ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
			ftp.setControlEncoding("GBK");
			ftp.configure(ftpClientConfig);
			ftp.connect(url, port);
			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			ftp.login(username, password);
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.setBufferSize(2048);
			for (int i = 0; i < list.size(); i++) {
				String ftppath = list.get(0).get("ftppath").toString();
				Map resultmap = new HashMap<String, Object>();
				String localDirPath = list.get(i).get("localDirPath").toString();
				String fileName = list.get(i).get("fileName").toString();
				String strFilePath = localDirPath + File.separator + fileName;
				outStream = new BufferedOutputStream(new FileOutputStream(
						strFilePath));
				ftp.changeWorkingDirectory(ftppath);
				ftp.retrieveFile(fileName, outStream);
				outStream.flush();
				String stageid = list.get(i).get("stageid").toString();
				resultmap.put("localDirPath", localDirPath);
				resultmap.put("filenames", fileName);
				resultmap.put("stageid", stageid);
				resultlist.add(resultmap);
			}
			ftp.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ftp.disconnect();
				if(outStream!=null){
					outStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultlist;
	}
	
}

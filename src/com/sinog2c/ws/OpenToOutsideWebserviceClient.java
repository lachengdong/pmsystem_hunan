package com.sinog2c.ws;

import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;

public class OpenToOutsideWebserviceClient {

	
	 public static void main(String[] args) throws Exception {  

		 JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
		 	//webservice的wsdl地址
	        Client client = clientFactory.createClient("http://127.0.0.1:8080/pmsystem/ws/gkzx?wsdl");  
	        Endpoint endpoint = client.getEndpoint(); 
	       //如果调不到方法，说明服务端没有指定命名空间，命名空间不一样，需要用到这个QName—————————— sayHello是你要调的方法
	        QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), "getCaseInfo"); 
			BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();  
			if (bindingInfo.getOperation(opName) == null) {  
				for (BindingOperationInfo operationInfo : bindingInfo.getOperations()) {  
					if ( "getCaseInfo".equals(operationInfo.getName().getLocalPart())) {  
						opName = operationInfo.getName();  
						break;  
					}  
				}  
			}
			//如果命名空间不一样，需要用到这个QName——————————
	        Object[] result = client.invoke(opName);  
	        System.out.println(result[0]);  
		 
	 }
}

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"
%><%@page import="com.sinog2c.mvc.message.JSONMessage,com.alibaba.fastjson.JSONObject,com.sinog2c.mvc.message.JSONMessage.CLIENT_ACTION"%><%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//
response.setContentType("text/html;charset=utf-8");
response.setStatus(200);
//
Object errorMessage = (String)request.getAttribute("errorMessage");
if(false == errorMessage instanceof String){
	errorMessage = "请求失败!";
}
//
JSONMessage message = new JSONMessage();
message.setInfo(String.valueOf(errorMessage));
message.setErrorcode(JSONMessage.CLIENT_ACTION.LOGIN);
message.setActionvalue(JSONMessage.CLIENT_ACTION.LOGIN);
String json = JSONObject.toJSONString(message);
%><%= json%>
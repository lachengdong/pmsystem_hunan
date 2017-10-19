<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.sinog2c.mvc.controller.base.ControllerBase"%>
<%@page import="com.sinog2c.model.system.SystemUser"%>
<%@page import="java.net.URLEncoder"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String sysName = "刑罚执行网上协同工作平台V3.0";

String from = request.getParameter("from");
if(null == from){
	from = "page";
}
Object o = ControllerBase.getLoginUser(request);
boolean hasLogin = false;
if(o instanceof SystemUser){
	// 这就是登录过的.直接跳转到
	hasLogin = true;
	if(null != from && from.startsWith("http") && !from.contains("main.action") ){
		// 重定向到该页面
		response.sendRedirect(from);
		// 返回
		return;
	} else {
		//
		// 重定向到该页面
		//response.sendRedirect(basePath + "main.action?__r="+System.currentTimeMillis());
		// 返回
		//return;
	}
}

String tokenValue = ""+ new Random().nextInt(); // 只在这里生成
String tokenName = "random";
//
request.getSession(true).setAttribute(tokenName, tokenValue);
%>

<%
	String errorMessageHideClass = "";
	String errorMessage = (String) request.getAttribute("errorMessage");
	//
	if (null == errorMessage || "".equals(errorMessage.trim())) {
		String e = request.getParameter("e");
		if(null != e){
			errorMessage = URLEncoder.encode(e, "UTF-8");
		}
	}
	//
	if (null == errorMessage || "".equals(errorMessage.trim())) {
		errorMessage = "";
	}
	// 转码
	errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
%>
<html>
  <head>
    <title></title>
 <script language="javascript">
 //
 var hasLogin = <%=hasLogin%>;
 var randomVal = '<%=tokenValue%>';
 var token = '<%=tokenName%>';
 var from = '<%=from%>';
 var e = '<%=errorMessage%>';
 var basePath = '<%=basePath%>';
 
  function opennew()
  {
        // 加随机数
        // var randomVal = new Date().getTime();
		// var action = "userLogin.action";
		//var action = "main.action" + "?__r=" + randomVal;
		var action = basePath + "loginPage.page" + "?token="+token +"&"+token+"=" + randomVal +"&from=" + from +"&e=" + e ;
		if(hasLogin){
			action = basePath + "main.action" + "?token="+token +"&"+token+"=" + randomVal ;
		}
		var facnwin=window.open(action,"","toolbar=no,status=no,resizable=no,location=no");
		facnwin.moveTo(0,0);
		facnwin.resizeTo(window.screen.availWidth, window.screen.availHeight);
		//
		window.setTimeout(function(){
			var win = window.top || window;
			win.opener=null;
			win.open('', '_self', ''); 
			win.close();
		}, 1);
		//window.top.open('', '_self', ''); 
		//window.top.close();
  };
 </script>    
  </head>
  <body scroll="yes"  onload="opennew()">
    <form id="Form1" method="post" runat="server">
    </form>
  </body>
</html>
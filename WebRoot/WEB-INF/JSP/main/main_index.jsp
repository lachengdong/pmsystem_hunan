<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.sinog2c.util.common.StringNumberUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String sysName = "刑罚执行网上协同工作平台V3.0";

String from = request.getParameter("from");
if(null != from && from.startsWith("http") && !from.contains("main.action") ){
	// 重定向到该页面
	response.sendRedirect(from);
	// 返回
	return;
}
//
String mainActionURL = basePath+ "main.action?__r="+System.currentTimeMillis();
//
String tokenName = request.getParameter("token"); // token名字
if(StringNumberUtil.notEmpty(tokenName)){
	String tokenValue = request.getParameter(tokenName);
	//Object tokenValueSession =session.getAttribute(tokenName);
	Object tokenValueSession = request.getAttribute("tokenValueSession");
	// Token 匹配, 已在弹出窗口
	if(StringNumberUtil.notEmpty(tokenValue) && tokenValue.equals(tokenValueSession)){
		// 重定向到该页面
		response.sendRedirect(mainActionURL);
		// 返回
		return;
	}
}
%>
<html>
  <head>
    <title></title>
 <script language="javascript">
  function opennew()
  {
        // 加随机数
        var randomVal = new Date().getTime();
		// var action = "userLogin.action";
		var action = "main.action" + "?__r=" + randomVal;
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

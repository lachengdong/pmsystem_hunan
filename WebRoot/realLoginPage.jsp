<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@page import="com.dj.sign.DJSSOUtil"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
 
try{
    Cookie[] cookies = request.getCookies();
    if(cookies != null){
        for(int i = 0; i < cookies.length; i++){
            if(cookies[i].getName().equals("userName")){
                String username=URLDecoder.decode(cookies[i].getValue(), "utf-8");
                request.setAttribute("userName",username);
            }
        }
    }
}catch(Exception e){
    e.printStackTrace();
}
%>
<%
	//@ page import="com.dj.sign.DJSSOUtil"
%>
<%

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String sysName = "刑罚执行网上协同工作平台V3.0";
Object userNameObj = request.getAttribute("userName");
String userName = (null == userNameObj) ? "" : String.valueOf(userNameObj);

//
String from = (String)request.getAttribute("from");
if(null == from){
	from = "";
}
//
String tokenName = request.getParameter("token"); // token名字
if(null == tokenName){
	tokenName = "noname";
}
String tokenValue = request.getParameter(tokenName);
if(null == tokenValue){
	tokenValue = "";
}
%>
<%
	String errorMessageHideClass = "";
	String errorMessage = (String) request.getAttribute("errorMessage");
	//
	if (null == errorMessage || "".equals(errorMessage.trim())) {
		String e = request.getParameter("e");
		if(null != e){
			errorMessage = URLDecoder.decode(e, "UTF-8");
		}
	}
	//
	if (null == errorMessage || "".equals(errorMessage.trim())) {
		errorMessage = "";
		errorMessageHideClass = "hide";
	}
%>

<html>
	<head>
		<title><%=sysName%></title>
		<meta http-equiv="Content-Type" content="no-cache" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="icon" href="<%=path%>/images/favicon.ico" type="image/x-icon" >
		<link rel="bookmark"  href="<%=path%>/images/favicon.ico" >
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"	type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<SCRIPT language="JavaScript" src="<%=path%>/scripts/loginsealV7.js"></SCRIPT>
		<style type="text/css">
body,tr {
	margin: 0;
	padding: 0;
}

body {
	background: url(<%=path%>/images/Login/01-bg.gif) repeat-x #fefefe;
	font-size: 13px;
	font-family: Verdana, Arial, Helvetica, sans-serif;;
}

.hide {
	display: none;
}

</style>
	</head>
	<body onLoad="myload()">
		<form name="loginform" action="<%=path %>/login.action" method="post"
			theme="simple">
			<input id="token" name="token" type="hidden" value="<%=tokenName %>">
			<input name="<%=tokenName %>" type="hidden" value="<%=tokenValue %>">
			<input id="from" name="from" type="hidden" value="<%=from %>">
			<input id="loginType" name="loginType" type="hidden" >
			<table width="453" border="0" cellspacing="0" cellpadding="0"
				valign="middle" align="center">
				<tr colspan="2" height="110"></tr>
				<tr>
					<td colspan="2" >
						<img alt="" style="display:block;margin: 0 auto;" src="<%=path%>/images/Login/002.gif"  >
					</td>
				</tr>
				<!-- sdbj22new.jpg为监狱图片 如果监狱部署请用此图片 若是法院部署请用fayuan-login.jpg
        <tr style="background:url(<%=path%>/images/Login/fayuan-login.jpg) no-repeat;" width="453" height="161">
		 -->
				<tr
					style="background:url(<%=path%>/images/Login/sdbj22new.jpg) no-repeat;"
					width="453" height="161">
					<td class="login-1" valign="top">
						<table cellspacing="0" cellpadding="0" border="0">
							<tr>
								<td width="80">
									&nbsp;
								</td>
								<td height="25" valign="middle" align="center">
									用&nbsp;&nbsp;户：
								</td>
								<td valign="middle" align="center">
									<input id="username" name="username" style="width: 132px" type="text"
										value="<%=userName %>"
										autofucus size="16" tabIndex="1">
								</td>
								<td width="80" rowspan="2" align="right" valign="middle">
									<img alt="提交" src="<%=path%>/images/Login/login_p_img11.gif"
										onclick="return validateUser();" tabIndex="3"
										style="cursor: pointer;">
								</td>
							</tr>
							<tr>
								<td width="80">
									&nbsp;
								</td>
								<td valign="middle" height="25" align="center">
									密&nbsp;&nbsp;码：
								</td>
								<td valign="middle" height="25" align="center">
									<input id="password" name="password" style="width: 132px" type="password"
										size="16" tabIndex="2">
								</td>
							</tr>
							<tr>
								<td width="90">
									&nbsp;
								</td>
								<td valign="middle" height="25" align="center" colspan="2">
									&nbsp;
									<a href="javascript:void(0)" style="text-decoration: none;"
										onclick="SealLogin();" tabIndex="4">USBKey登录</a>
								</td>
							</tr>
							<tr>
								<td width="80">
									&nbsp;
								</td>
								<td align="center" colspan="2">
									<div style="color: red;">
										<ul id="errorMessageWrap" class="<%=errorMessageHideClass%> errorMessageWrap" >
											<li>
												<span id="errorMessage" class="errorMessage"><%=errorMessage%></span>
											</li>
										</ul>
									</div>
									<input type="hidden" id="certno" name="certno">
									<input type="hidden" id="signdata" name="signdata">
								</td>
							</tr>
							<tr>
								<td>
									<input type="hidden" name="keyid" value="">
									<div id="longinSeal" style="display: none;"></div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">
		function SealLogin() {
			//
			if (false && !window["isIE"]) {
				//
				var errorMessage= document.querySelector(".errorMessage");
				errorMessage.innerHTML = ("请使用IE浏览器");
				var errorMessageWrap= document.querySelector(".errorMessageWrap");
				errorMessageWrap.classList.remove("hide");
				return;
			}
			//
			makeSeal();
			var obj = document.getElementById("DWebSignSeal");
			var login = obj.CardLogin("");//登录key，确认用户身份
			if(login==0){//返回值为0表示正确登录		
				document.getElementById("certno").value=obj.GetCertInfo(1,0,"");//获取证书序列号		
				//document.getElementById("certdata").value=obj.GetCertInfo(3,0,"");//获取公钥证书
				<%
					String temp_value = DJSSOUtil.setInitValue(session);
				%>
				document.getElementById("signdata").value=obj.CertSignData("<%=temp_value%>","",0);
				
				//document.loginform.action="login.action?loginType=usbkey";
				var loginTypeinput = document.getElementById("loginType");
				loginTypeinput.value = "usbkey";
				//
				document.loginform.submit();
			}else{
				alert("证书认证失败,请插入正确的UKey或联系管理员");
			}
		};
		function getKey(){
			var obj = document.getElementById("MakeSealV6");
			//var keyid = obj.GetKeyID("");//返回值keyid
			var keyid=obj.SerialNumber;
			return keyid;
		};
		function myload(){
			document.loginform.username.focus()
			if(document.getElementById("username") && document.getElementById("username").value){
				document.loginform.password.focus()
			}
		}
	</script>
	</body>
</html>
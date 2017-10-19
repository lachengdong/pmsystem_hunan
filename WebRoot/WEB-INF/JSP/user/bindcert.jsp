<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%@page import="com.sinog2c.model.system.SystemUser"%>
<%@page import="com.sinog2c.model.user.UserCertificate"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
	
	//
	Object _user = request.getAttribute("user");
	Object _cert = request.getAttribute("cert");

	//
	SystemUser user = null;
	if(_user instanceof SystemUser){
		user = (SystemUser)_user;
	}
	//
	UserCertificate cert = null;
	if(_cert instanceof UserCertificate){
		cert = (UserCertificate)_cert;
	}
	//
	String userid = user.getUserid();
	if(null == userid){
		userid = "";
	}
%>
<html>
	<head>
		<base href="<%=basePath%>"/>
		<title>证书绑定</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"	type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/loginsealV7.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />

		<style type="text/css">
		body {
			margin: 0;
			padding: 0;
			border: 0;
			width: 100%;
			height: 100%;
			overflow: hidden;
		}
		</style>
	</head>
	<body>
<div id="form1">	
<form name="loginform" action="saveBoundUsbkey.action" >
	<div style="width:100%;">
	    <div class="mini-toolbar" style="border-bottom:0px;padding:0px;">
	        <table style="width:100%;">
	            <tr>
	                <td style="width:100%;">
	                <a class="mini-button" onclick="SealLogin()" plain="true" iconCls="icon-add" >读取证书</a>
	                <a class="mini-button" onclick="boundKey()" plain="true" iconCls="icon-save" >绑定证书</a>
	                <a class="mini-button" onclick="jiechukey()" plain="true"  iconCls="icon-remove">解除绑定</a>
	                </td>
	            </tr>
	        </table>           
	    </div>
	</div>
	<table cellspacing="0" cellpadding="2" width="100%" border="0" style="padding: 2px;">
		<tr>
	        <td  style="width: 70px;">当前帐号 </td>
	     	<td  ><input id="userid" name="userid" class="mini-textbox" value="<%=userid%>" readonly="readonly" style="width: 450px;"/></td>
	     </tr>
	 	<tr>
	        <td  style="width: 70px;">证书主题 </td>
	     	<td  ><input id="subject" name="subject" class="mini-textbox" readonly="readonly" style="width: 450px;"/></td>
	     </tr>
	    <tr>
	        <td  style="width: 70px;">证书序列号 </td>
	     	<td  ><input id="certno" name="certno"  class="mini-textbox" readonly="readonly" style="width: 450px;"/></td>
	     </tr>
	     <tr>
	     	<td  style="width: 70px;">证 书 DN </td>
	     	<td  ><textarea rows="1" cols="20" id="certdn" name="certdn"  class="mini-textarea" readonly="readonly" style="height: 26px;width: 450px;"></textarea></td>
	     </tr>
	     <tr>
	    	<td  style="width: 70px;">公钥证书</td>
	     	<td  ><textarea rows="8" id="certdata" name="certdata"  class="mini-textarea" readonly="readonly" style="width: 450px;height: 150px"></textarea></td>
	    </tr>
	</table>
	<input type="hidden" name="keyid" value=""/>	
</form>
</div>
<div id="longinSeal" style="display:none;"></div>

<script type="text/javascript">
	mini.parse();
	function SealLogin(){
		makeSeal();
		var obj=document.getElementById("DWebSignSeal");
		
		var login=obj.CardLogin("");//登录key，确认用户身份
		var subjectObj = obj.GetCertInfo(0,0,"");//获取证书主题	
		var certnoObj = obj.GetCertInfo(1,0,"");//获取证书序列号
		var certdnObj = obj.GetCertInfo(2,0,"");//获取证书DN		
		var certdataObj = obj.GetCertInfo(3,0,"");//获取公钥证书
	
		if(login==0){//返回值为0表示正确登录
		var subject = mini.get("subject");	
		var certno = mini.get("certno");
		var certdn = mini.get("certdn");
		var certdata = mini.get("certdata");
		
		subject.setValue(subjectObj);
		certno.setValue(certnoObj);
		certdn.setValue(certdnObj);
		certdata.setValue(certdataObj);
		
		}else{
			alert("证书读取失败,请插入正确的UKey或联系管理员");
		}
	};
	function boundKey(){
		var certno = mini.get("certno").getValue();
		if(''==certno){
			alert("请先读取证书再绑定！");
		}else{
			var form = new mini.Form("#form1");
			  form.validate();
			  if (form.isValid() == false) {
				  return;
			  }
			 var data = form.getData();
			//
			var  url = "<%=path%>/user/ajax/bindkey.json";
			var successCallback = function (message) {
					var info = message["info"];
					// 判断是否成功
					alert(info);
					if(1 === message["status"]){
					} 
					return false;
			    };
			//
			var errotCallback = function (jqXHR, textStatus, errorThrown) {
			         alert("绑定失败！");
			    };
			//
			requestAjax(url,data,successCallback,errotCallback);
		}
	};
	
		// 请求AJAX,工具方法
		function requestAjax(url, data, successCallback, errotCallback){
			// 执行AJAX请求
			$.ajax({
			    url: url,
			    data: data,
		        type: "post",
			    success: function (message) {
			    	/*
			    	if(window["JSON"]){
			    		message = mini.decode(message) || {};
			    	} else { // IE6, IE7
	        	   		message = eval("("+ message + ")") || {};
			    	}
			    	*/
			    	message = mini.decode(message) || {};
			   		if(successCallback){
			    	   successCallback.call(window, message);
			   		}
	            	return false;
			    },
			    error: function (jqXHR, textStatus, errorThrown) {
			    	// 把错误吃了
			       if(errotCallback){
			    	   errotCallback.apply(window, arguments);
			       } else {
			    	   alert("操作失败!");
			       };
			    }
			});
		};
		function jiechukey(){
			var certno = mini.get("certno").getValue();
			var url = "<%=path%>/user/ajax/jiechukey.action";
			$.ajax({
				url:url,
				data:{certno:certno},
				dataType:"json",
				type: "POST",
				success: function (message) {
					alert(message.info);
				},
				error: function (jqXHR, textStatus, errorThrown) {
			    	   alert("操作失败!");
			    }
			});
		}
</script>
</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.sinog2c.model.system.SystemUser"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
    Object aa = request.getAttribute("criminalinfo");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
		<title>筛查条件</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script> 
	    <style type="text/css">
	    html, body
	    {
	        font-size:12px;
	        padding:0;
	        margin:0;
	        border:0;
	        height:100%;
	        overflow:auto;
	    }
	     .errorText
	    {
	        color:red;
	    }
	    </style>
    </head>
	<body>
		<div class="mini-toolbar" style="border-bottom:0;padding:0px;"> 
		   <table style="width:100%;">
		       <tr>
		       	<td style="width:100%;">
		            <a class="mini-button" onclick="onCancel" style="width:60px;" plain="true">关闭</a> 
				 </td>
		       </tr>
		   </table>
		</div>
	 <div id="form1">
		 <div style="width:100%;height:100%px; margin: auto;padding-top: 0px;">
<%-- 			<input id="content" name="content" class="mini-textarea" value="${criminalinfo }" style="width:100%; height:210px;"/> --%>
           <%=aa%>
		 </div>
		</div>
		<script type="text/javascript">
			mini.parse();
			   function onCancel(e) {
			       CloseWindow("cancel");
			   }
			   function CloseWindow(action) {
			      if(window.CloseOwnerWindow){
			      	 return window.CloseOwnerWindow(action);
			      }else{
			       window.close();
			      }        
			  }
		</script>
	</body>
</html>

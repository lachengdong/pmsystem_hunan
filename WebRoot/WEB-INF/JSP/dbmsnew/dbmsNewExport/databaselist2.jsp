<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>

<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
		<title>数据库管理databaselist.jsp</title>
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
	    </style>
	    <script type="text/javascript">	    
			
			function queryList(){
				document.queryTableList.action='queryTableInfoList.action';
				document.queryTableList.submit();
			}
		</script>
	</head>
	<body onload="queryList()">
		<s:form id="queryTableList" name="queryTableList"	theme="simple">
			
		</s:form>
	</body>
</html>

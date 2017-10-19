<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
		<title>会议记录预览(导出)</title>
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<style type="text/css">
	    body{
    	    	margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
   			}    
    </style> 
	</head>
<body>
<div style="width:100%;height:100%;">
	<script LANGUAGE="javascript" src="<%=path%>/scripts/LoadRMViewer2.js"></script>
	<textarea id="RMVIEWER_DATA" style="display:none">${mydata}</textarea>
	<script language="JavaScript">
	  document.all("MyViewer").Init(window, document, 600);
	  document.all("MyViewer").ShowProgress = true;
	  document.all("MyViewer").GetReportData(document.all("RMVIEWER_DATA").value);
	  document.all("MyViewer").ShowReport();
	</script>
</div>
</body>
</html>
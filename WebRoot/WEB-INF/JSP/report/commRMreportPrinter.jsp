<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8" import="java.util.*" %>
<%@taglib uri="/WEB-INF/commRMreportPrinterTag.tld" prefix="commRMreportPrinterTag"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
		<title>报表统计</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
  		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
	</head>
<body  style="padding-right: 0px; padding-left: 0px; padding-bottom: 0px; margin: 0px; width: 100%; padding-top: 0px; height: 100%;">
<commRMreportPrinterTag:commRMreportPrinterTag  />
</body>
</html>
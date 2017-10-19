<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
	rel="stylesheet" type="text/css" />
<title>案件整理tab页</title>
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
<body onload="onload()"> 
	<div id="tabs" class="mini-tabs" name="minitabs" activeIndex="0"   style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
		<div id="700314" name="700314" title="案件整理"  url="<%=path%>/commutationParole/tocaseSettlePage.action?1=1" ></div>
		<!-- <div id="700314" name="700314" title="重要案件整理"  url="<%=path%>/commutationParole/tozycaseSettlePage.action?1=1" ></div> -->
    </div> 

	
	<script type="text/javascript">
		    mini.parse();
			
		</script>
</body>
</html>
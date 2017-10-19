<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>数据管理showDataInfo.jsp</title>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<style type="text/css">
	    body{
	        margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
	    }    
	    </style>
	    <script type="text/javascript">
	   		function onbeforeactivechanged(e){
	        }
	        function aa() {
	        }
		</script>
	</head>
	
	<body >
		<div id="tabs" class="mini-tabs" name="datamanage" activeIndex="0" onbeforeactivechanged="onbeforeactivechanged"  style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
			<div id="tab1" name="1"  title="数据导出" url="toDataExportPage.page" refreshOnClick="true" >
			</div>
			<div  id="tab2"  name="2"  title="数据导入" url="toDataImportPage.page" refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab3"  name="3"   title="数据交换" url="toDataSwapPage.page"  refreshOnClick="true"  visible="true">
			</div>
		</div>
	</body>
</html>

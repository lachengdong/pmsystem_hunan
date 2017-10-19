<%@ page language="java" pageEncoding="UTF-8"  %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<title>数据方案dbmstabpage.jsp</title>
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
		<div id="tabs" class="mini-tabs" name="datafangan" activeIndex="0" onbeforeactivechanged="onbeforeactivechanged"  style="width:100%;height:100%;" bodyStyle="padding:0;border:0;">
			<div id="tab1" name="1"  title="数据库管理" url="getConnectionMessageList.page" refreshOnClick="true" >
			</div>
			<div  id="tab2"  name="2"  title="方案管理" url="showSchemeList.page" refreshOnClick="true"  visible="true">
			</div>
			<div  id="tab3"  name="3"   title="代码管理" url="getCodeManagesList.page"  refreshOnClick="true"  visible="true">
			</div>
		</div>
	</body>
</html>
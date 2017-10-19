<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8" %>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
		<title>数据库管理dataImportSuccess.jsp</title>
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
		    $(function(){
				function show(){
					if(window.parent.document.getElementById("tabs1")){
						//window.location="admissionImport.action?1=1";
	               		window.location="dataimportmanage.page?1=1";
					}else{
	               		window.parent.location="dataimportmanage.page?1=1";
	               	}
				}
				setInterval(show,2000);
			});
		</script>
	</head>
	<body >
		<table id="Table1" width="100%" class="mini-toolbar">
			<tr>
				<td class="TableBody" colspan="2" height="40" align="center">
					<b>数据导入成功！！</b>
				</td>
			</tr>
		</table>
	</body>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>收监说明</title>
	<base   target="_self">
	<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
   	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
   	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script> 
    <link href="<%=path%>/scripts/miniui/themes/blue/skin.css" rel="stylesheet" type="text/css" />
	
 	<style type="text/css">
	    html, body
	    {
	        font-size:12px;
	        padding:0;
	        margin:0;
	        border:0;
	        height:100%;
	        overflow:hidden; 
	        background-color:#DAE6F2;
	    }
    </style>
	<script LANGUAGE="javascript" FOR="HWPostil1" EVENT="NotifyCtrlReady">
		
		download();
		document.all.HWPostil1.JSEnv = 1;
	</script>
    <script type="text/javascript">
		function download()
		{
			var obj = document.getElementById("HWPostil1");
			obj.LoadFileBase64("<s:property value='#request.smoperatediscrobe'/>");
			obj.SetPageMode(1,100);
			obj.SilentMode = true;
			obj.ShowToolBar = 0;
			obj.ShowDefMenu = 0;
		}
 	</script>
</head>
<body  >
<table width="100%" height="100%" style="margin:0 0 0px 0";>
		<tr width="100%">
			<td height="100%">
			<script LANGUAGE="javascript" src="<%=path%>/scripts/form/loadaip.js"></script>
			</td>
		</tr>
</table>
</body>
</html>
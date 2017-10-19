<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<title>流程表单查看</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/css/flowInstance.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>

<style type="text/css">
</style>
</head>
<body>	
	<div class="mini-fit" style="height: 0px;width: 0px;" id="dvflowform">
		<script src="<%=path%>/scripts/form/loadaip.js"></script>
		<script src="<%=path%>/scripts/form/OAflowdeliver.js"></script>
		<script LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
			var aipObj = document.all.HWPostil1;
			topOAflowdeliver = new OAflowdeliver(aipObj);			
			topOAflowdeliver.runModelReady('${formcontent}', '0');		
		</script>
		<SCRIPT LANGUAGE="javascript" FOR="HWPostil1"
			EVENT="JSNotifyBeforeAction(lActionType,lType,strName,strValue)">
			topOAflowdeliver.JSNotifyBeforeAction(lActionType, lType, strName,
					strValue);
		</SCRIPT>
		<SCRIPT LANGUAGE="javascript" FOR="HWPostil1"
			EVENT="NotifyDocOpened()">
			mini.get("dvflowform").set({
				width : '100%'
			});
			
		</SCRIPT>
		<script LANGUAGE="javascript" FOR="HWPostil1"
			EVENT="NotifyLineAction(lPage,lStartPos,lEndPos)">
			
		</script>
	</div>
	<script type="text/javascript">
		var topOAflowdeliver = null;
		mini.parse();
		
		
		
	</script>
</body>
</html>

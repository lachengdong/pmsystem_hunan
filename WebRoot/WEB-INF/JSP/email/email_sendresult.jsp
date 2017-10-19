<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>mail send result</title>

<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script type="text/javascript" src="<%=path%>/scripts/boot.js"></script>

<script type="text/javascript">		
	$(function() {		
		parent.callback('${result.info}');		
	});

</script>

</head>

<body>

</body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.net.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
	<title>附件上传</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
		type="text/css" />
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
</head>
<body>

	<div>
		<form name="form3" enctype="multipart/form-data" method="post"
			target="hidden_frame">
			<div style="float:left;margin-top:20px;margin-left:5px;">
				注意：上传文件的大小不超过100M。${prompt} <span id="uploadResult"></span> <input
					type="button" value="上传" onclick="uploadfile();" id="btnupload"
					style="display: none" />
			</div>
			<div id="ui-upload-holder" title="上传附件">
				<div id="ui-upload-txt"></div>
				<input type="file" id="ui-upload-input" name="file"
					onchange="javascript:onfileChange();" />选择文件
			</div>			
			<iframe name='hidden_frame' id="hidden_frame" style="display: none">
			</iframe>
		</form>
	</div>


	<script type="text/javascript">
		mini.parse();
		function onfileChange() {
			document.getElementById("btnupload").click();

		}
		//上传文件 
		function uploadfile() {
			document.form3.method = "post";
			document.form3.action = "${path}/fileUploadDown/fileUpload.action?1=1";
			document.form3.submit();
			document.cookie = "";
		}

		function onCancel(e) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}

		function callback(msg) {		
			window.returnValue = msg;
			window.close();
		}
	</script>

</body>
</html>

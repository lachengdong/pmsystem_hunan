<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<title>发文类型选择</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
</head>

<body>
	<div class="mini-toolbar"
		style="padding:2px;border-top:0;border-left:0;border-right:0;text-align:right;">
		<a class="mini-button" iconCls="icon-ok" plain="true" onclick="doOK()">确定</a>
	</div>
	<div class="mini-fit">
		<table class="form-table" border="0" cellpadding="1" cellspacing="2"
			style="width:100%;">
			<tr>
				<td style="width:60px;">公文类型：</td>
				<td><input name="ddlType1" class="mini-combobox"
					style="width:140px;" id="ddlType1" textField="text" valueField="id"
					data="Genders" emptyText="请选择..." required="true" allowInput="true"
					showNullItem="true" nullItemText="请选择..." /></td>
			</tr>
		</table>
	</div>

	<script type="text/javascript">
		// TODO 需要在页面打开时到数据库中加载.			
		var Genders = [ {
			id : "决议",
			text : "决议"
		}, {
			id : "通告",
			text : "通告"
		}, {
			id : "通报",
			text : "通报"
		} ];

		var oMyObject = window.dialogArguments;		
		mini.parse();		
		mini.get("ddlType1").setValue(oMyObject);		
		function doOK() {
			var docType = mini.get("ddlType1").getValue();
			window.returnValue = docType;
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}
	</script>


</body>
</html>

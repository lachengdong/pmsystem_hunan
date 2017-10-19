<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<title>设置绑定字段</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
	rel="stylesheet" type="text/css" />
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
<body>
	<form id="form1" method="post">
		<div class="mini-toolbar mini-grid-headerCell"
			style="padding: 1px; border-bottom: 0px;">
			<table>
				<tr>
					<td style="width: 100%;white-space: nowrap;"><a
						class="mini-button mini-button-iconLeft" plain="true"
						iconCls="icon-ok" onclick="dosubmit();">确定</a>&nbsp;&nbsp;<a
						class="mini-button mini-button-iconLeft" plain="true"
						iconCls="icon-cancel" onclick="onCancel();">取消</a></td>
					<td></td>

				</tr>
			</table>
		</div>
		<fieldset
			style="width:100%;border:solid 0px #aaa;margin-top:8px;position:relative;">
			<div style="padding:5px;">
				<input class="mini-hidden" name="id" />
				<table style="width:100%;">
					<tr>
						<td style="width:60px;">字段ID：</td>
						<td style="width:100px;"><input id="colid" name="colid"
							class="mini-textbox" required="true" /></td>
					</tr>
					<tr>
						<td>角色绑定：</td>
						<td><input name="ddlRole" id="ddlRole" class="mini-combobox"
							style="width:120px;" valueField="roleid" textField="name"
							emptyText="请选择角色" allowInput="true"
							url="${path}/role/ajax/getAllRoleByUser.json?1=1"
							required="false" showNullItem="true" nullItemText="请选择角色" /></td>

					</tr>

				</table>
			</div>
		</fieldset>
	</form>
	<script type="text/javascript">
		mini.parse();
		//$("#colid").focus ( );
		mini.get("colid").setValue('text1');
		mini.get("colid").focus();
	
		//alert($("#colid"));
		function dosubmit() {			
			var field_name = mini.get("colid").getValue();		
			var field_role =  mini.get("ddlRole").getValue();		
			window.returnValue = {"field_name":field_name,"field_role":field_role};
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();			
		}

		function onCancel(e) {			
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}
	</script>
</body>
</html>
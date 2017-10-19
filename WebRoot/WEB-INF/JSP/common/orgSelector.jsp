<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>部门选择器</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>

<style type="text/css">
html,body {
	padding: 0;
	margin: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
</head>
<body>
	<div id="div_one" style="width:100%;height:100%;">
		<input id="id" class="mini-hidden" />
		<div class="mini-toolbar" style="padding:0px;border:0;text-align:left">
			<table style="width:100%;">
				<tr>
					<td><a class="mini-button" onclick="onOk" plain="true"
						style="width:60px">确定</a> <a class="mini-button"
						onclick="onCancel" plain="true" style="width:60px;">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

					</td>
				</tr>
			</table>
		</div>
		<div class="mini-fit">
			<ul id="tree1" class="mini-tree" style="width:100%;height:100%;"
				showTreeIcon="true" textField="name" idField="orgid"
				parentField="porgid" resultAsTree="false" showRadioButton="true"
				expandOnLoad="false" checkRecursive="true">
			</ul>
		</div>
	</div>
	<script type="text/javascript">
		var oMyObject = window.dialogArguments;

		$(function() {
			var tree = mini.get("tree1");
			tree.loadList(mini.decode('${orgInfos}'), 'orgid', 'porgid');
			if (oMyObject) {
				tree.selectNode('' + oMyObject.orgId);
				tree.expandPath('' + oMyObject.orgId);
				tree.selectNode(oMyObject.orgId);
			}
		});

		function SetData(data) {
			var tree = mini.get("tree1");
			tree.selectNode('' + data);
			tree.expandPath('' + data);
			tree.selectNode(data);
		};

		function onOk() {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			if (window.CloseOwnerWindow) {
				parent.setOrgNo(node.orgid, node.name);
			} else {
				window.returnValue = node;
				window.close();
			}

		};
		function onCancel() {
			if (window.CloseOwnerWindow) {
				parent.setOrgNo();
			} else {
				window.close();
			}
		};
	</script>
</body>
</html>
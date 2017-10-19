<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<title>流程任务办理人选择</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>

<style type="text/css">
.actionIcons span {
	width: 16px;
	height: 16px;
	display: inline-block;
	background-position: 50% 50%;
	cursor: pointer;
}

tr {
	background-color: beige;
}

td {
	background-color: white;
}
</style>

</head>

<body>
	<div class="mini-toolbar"
		style="padding:2px;border-top:0;border-left:0;border-right:0;text-align:right;align:right;">

		<a class="mini-button" iconCls="icon-ok" plain="true" onclick="doOK()">确定</a>
	</div>
	<div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" idField="id" showFooter="false"
			allowSortColumn="false" showPager="false" allowAlternating="true"
			multiSelect="true" allowCellEdit="true" allowCellSelect="true"
			editNextOnEnterKey="true" editNextRowCell="true">
			<div property="columns">
				<div type="checkcolumn" width="40px"></div>
				<div type="indexcolumn" width="40px" headerAlign="center"
					align="center">序号</div>
				<div field="userid" width="15%" headerAlign="center"
					allowSort="true">用户编号</div>
				<div field="name" width="80%" allowSort="true" align="left"
					headerAlign="center">姓名</div>

			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var oMyObject = window.dialogArguments;
		var grid = mini.get("datagrid1");

		$(document).ready(function() {
			var assigners = mini.decode('${assigners}');
			grid.addRows(assigners);
			grid.selectAll();
		});
		function doOK() {
			var rows = grid.getSelecteds();
			if (rows.length<1) {
				alert("请选择任务执行者！");
				return;
			}
			window.returnValue = mini.encode(rows);
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}
	</script>

</body>
</html>

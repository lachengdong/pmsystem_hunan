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
<title>公文正文套红模板选择</title>
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
			multiSelect="false" allowCellEdit="true" allowCellSelect="true"
			editNextOnEnterKey="true" editNextRowCell="true">
			<div property="columns">
				<div type="checkcolumn" width="40px"></div>
				<div field="tempname" width="20%" headerAlign="center" align="left"
					allowSort="true">模板名称</div>
				<div field="modulename" width="20%" headerAlign="center"
					allowSort="true" align="left" renderer="onmoduleRenderer">模板类型</div>
				<div field="remark" headerAlign="center" align="left"
					allowSort="true" width="60%">备注</div>				
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");

		$(document).ready(function() {
			var docTemplates = mini.decode('${docTemplates}');
			grid.addRows(docTemplates);
			if (docTemplates.length > 0) {
				grid.setSelected(grid.getRow(0));
			}
		});

		function doOK() {
			var row = grid.getSelected();
			if (!row) {
				alert("请套红模板！");
				return;
			}
			window.returnValue = row.doctempid;
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}

		function onmoduleRenderer(e) {
			var states = {
				"docsend" : "公文正文模板",
				"ringred" : "套红模板"
			};
			return states[e.value];
		}
	</script>

</body>
</html>

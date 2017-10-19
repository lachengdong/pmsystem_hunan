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
<title>表单栏位权限设定</title>
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
	background-color:beige;
}

td
{
	background-color:white;
}
</style>

</head>

<body>
	<div class="mini-toolbar"
		style="padding:2px;border-top:0;border-left:0;border-right:0;text-align:right;align:right;">
		<!--  <a class="mini-button" iconCls="icon-edit" plain="true"
			onclick="setEdit()">编辑</a> <span class="separator"></span>-->
			<a class="mini-button" iconCls="icon-ok" plain="true"
			onclick="saveData()">确定</a>
	</div>
	<div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;" idField="id" showFooter="false"
			allowSortColumn="false" showPager="false" allowAlternating="true"
			allowCellEdit="true" allowCellSelect="true" editNextOnEnterKey="true" editNextRowCell="true">
			<div property="columns">
				<div type="indexcolumn" width="40" headerAlign="center"
					align="center">序号</div>
				<div field="id" width="120" headerAlign="center" allowSort="true">字段名称</div>

				<div field="roleid" width="100" allowSort="true"
					renderer="onGenderRenderer" align="left" headerAlign="center">
					可被操作的角色 <input property="editor" class="mini-combobox"
						style="width:100%;" data="Genders" emptyText="请选择角色" allowInput="true" showNullItem="true" nullItemText="请选择角色" />
				</div>

			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var oMyObject = window.dialogArguments;
		var grid = mini.get("datagrid1");

		Genders = [];
		//alert(oMyObject);
		$(document).ready(function() {
			Genders = mini.decode('${roles}');
			for (var i = 0; i < oMyObject.clos.length; i++) {
				grid.addRow(oMyObject.clos[i]);
				//grid.beginEditRow(oMyObject.clos[i]);
				//lsBox.select(fields[i].field);
			}
			//grid.addRows(fields);
		});
		function onGenderRenderer(e) {
			for (var i = 0, l = Genders.length; i < l; i++) {
				var g = Genders[i];
				if (g.id == e.value)
					return g.text;
			}
			return "";
		}
		function saveData() {
			grid.commitEdit();
			var rows = grid.getEditData(true);	
			//alert(rows);
			window.returnValue=rows;
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}
	</script>

</body>
</html>

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

	<title>表单查询grid head设置</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
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

.actionIcons span {
	width: 16px;
	height: 16px;
	display: inline-block;
	background-position: 50% 50%;
	cursor: pointer;
}

td {
	background-color: white;
}
</style>
</head>

<body>
	<div class="mini-splitter" style="width:100%;height:100%;">
		<div size="240" showCollapseButton="false" minSize="150" maxSize="240">
			<div class="mini-toolbar"
				style="padding:2px;border-top:0;border-left:0;border-right:0;">
				<span style="padding-left:5px;">当前表单字段列表：</span>
			</div>
			<div class="mini-fit">
				<div id="lisCol" class="mini-listbox" value="id" valueField="id"
					textField="id" style="width: 100%; height: 100%;"
					onvaluechanged="onListBoxValueChanged" showCheckBox="true"
					multiSelect="true">
					<div property="columns">
						<div header="字段名" field="id"></div>
					</div>
				</div>
			</div>
		</div>
		<div showCollapseButton="false">
			<div class="mini-toolbar"
				style="padding:2px;border-top:0;border-left:0;border-right:0;">
				<!--  <a class="mini-button" iconCls="icon-edit" plain="true"
					onclick="setEdit()">编辑</a> <span class="separator"></span>-->
				<a class="mini-button" iconCls="icon-save" plain="true"
					onclick="saveData()">保存</a>
			</div>
			<div class="mini-fit">
				<div id="datagrid1" class="mini-datagrid"
					style="width: 100%; height: 100%;" idField="id" showFooter="false"
					allowSortColumn="false" showPager="false" allowAlternating="true"
					allowCellEdit="true" allowCellSelect="true"
					editNextOnEnterKey="true" editNextRowCell="true">
					<div property="columns">
						<div type="indexcolumn" width="40" headerAlign="center"
							align="center">序号</div>
						<div cellCls="actionIcons" name="action" width="40"
							headerAlign="center" align="center" renderer="onActionRenderer"
							cellStyle="padding:0;">#</div>
						<div field="field" width="120" headerAlign="center"
							allowSort="true">字段名称</div>
						<div field="description" width="100" allowSort="true"
							align="center" headerAlign="center">
							标题 <input property="editor" class="mini-textbox"
								style="width:100%;" />
						</div>
						<div field="width" width="100" allowSort="true">
							宽度 <input property="editor" class="mini-spinner" minValue="0"
								maxValue="200" value="25" style="width:100%;" />
						</div>
						<div field="headerAlign" width="100" allowSort="true"
							renderer="onGenderRenderer" align="center" headerAlign="center">
							标题布局 <input property="editor" class="mini-combobox"
								style="width:100%;" data="Genders" />
						</div>
						<div field="align" width="100" allowSort="true"
							renderer="onGenderRenderer" align="center" headerAlign="center">
							内容布局 <input property="editor" class="mini-combobox"
								style="width:100%;" data="Genders" />
						</div>
						<div field="allowSort" width="100" allowSort="true"
							renderer="onGenderRenderer1" align="center" headerAlign="center">
							允许排序？<input property="editor" class="mini-combobox"
								style="width:100%;" data="Genders1" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		Genders = [ {
			id : 'center',
			text : '居中'
		}, {
			id : 'left',
			text : '靠左'
		}, {
			id : 'right',
			text : '靠右'
		} ];

		Genders1 = [ {
			id : 'true',
			text : '允许'
		}, {
			id : 'false',
			text : '不允许 '
		} ];

		mini.parse();
		var grid = mini.get("datagrid1");
		var lsBox = mini.get("lisCol");
		var oMyObject = window.dialogArguments;

		lsBox.loadData(oMyObject.clos);

		$(document).ready(function() {
			var fields = mini.decode('${fields}');
			for (var i = 0; i < fields.length; i++) {
				grid.addRow(fields[i]);
				//grid.beginEditRow(fields[i]);
				lsBox.select(fields[i].field);
			}
		});

		function onListBoxValueChanged(e) {
			var listbox = e.sender;
			var value = listbox.getValue();
			var fields = value.split(',');
			var rowcount = grid.data.length;
			var hased = false;
			for (var i = 0; i < fields.length; i++) {
				hased = false;
				for (var j = 0; j < rowcount; j++) {
					var row = grid.getRow(j);
					if (row.field == fields[i]) {
						hased = true;
						break;
					}
				}
				if (!hased) {
					if (fields[i] == "")
						continue;
					var newRow = {};
					newRow.field = fields[i];
					newRow.description = "";
					newRow.width = "20";
					newRow.headerAlign = "center";
					newRow.align = "center";
					newRow.allowSort = "true";
					grid.addRow(newRow);
					grid.beginEditRow(newRow);
				}
			}
		}

		function newRow() {
			var row = {};
			grid.addRow(row);
			grid.beginEditRow(row);
		}

		function delRow(row_uid) {
			var row = grid.getRowByUID(row_uid);
			if (row) {
				grid.removeRow(row);
				lsBox.deselect(row.field);
			}
		}
		function setEdit() {
			var rowcount = grid.data.length;
			for (var j = 0; j < rowcount; j++) {
				var row = grid.getRow(j);
				grid.beginEditRow(row);
			}
		}

		function saveData() {
			grid.commitEdit();
			var rows = grid.getEditData(false);
			var rows2 = [];
			var keylist = [];
			var keyItem = {};
			var rowtemp = {};
			var keys = "";

			if (rows.length > 25) {
				mini.alert("表头字段个数不能超过25个,请重新设置再保存！");
				return;
			}

			var index = 1;
			for (var i = 0; i < rows.length; i++) {
				keyItem = {};
				keyItem.id = rows[i].field;
				keyItem.clo = "text" + index;
				keyItem.des = rows[i].description;
				keylist.push(keyItem);
				index++;
				rowtemp = mini.clone(rows[i]);
				rowtemp.field = keyItem.clo;
				keys += "tbflow_execution." + rowtemp.field + ",";
				rows2.push(rowtemp);
			}

			var json = mini.encode(rows);
			var json2 = mini.encode(rows2);
			var keyfields = mini.encode(keylist);

			keys = keys.substring(0, keys.length - 1);

			var postData = {
				tempid : oMyObject.tempid,
				departid : oMyObject.departid,
				keyfields : keyfields,
				gridheaders1 : json,
				gridheaders2 : json2,
				keyfieldsTemp : keys,
				mainsqltemp : "select " + keys
			};
			var data = {
				data : mini.encode(postData)
			};
			// 界面
			grid.loading("保存中，请稍候......");

			var url = "${path}/templet/updateTemplate.action";
			var successCallback = function(message) {
				grid.unmask();
				mini.alert("保存成功！", "提示", function() {
					if (window.CloseOwnerWindow)
						return window.CloseOwnerWindow(action);
					else
						window.close();
				});
				return false;
			};
			//
			var errotCallback = function(jqXHR, textStatus, errorThrown) {
				mini.alert("保存失败");
				grid.unmask();
			};
			//
			requestAjax(url, data, successCallback, errotCallback);
		}

		function onGenderRenderer(e) {
			for (var i = 0, l = Genders.length; i < l; i++) {
				var g = Genders[i];
				if (g.id == e.value)
					return g.text;
			}
			return "";
		}

		function onGenderRenderer1(e) {
			for (var i = 0, l = Genders1.length; i < l; i++) {
				var g = Genders1[i];
				if (g.id == e.value)
					return g.text;
			}
			return "";
		}

		function onActionRenderer(e) {
			var record = e.record;
			var uid = record._uid;
			var s = '<span class="icon-remove" title="删除记录" onclick="delRow('
					+ uid + ')"></span>';
			return s;
		}
	</script>
</body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript">
		</script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
			rel="stylesheet" type="text/css" />
		<title>退案展示页</title>
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
	<body onload="init('${menuid}');">
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
			<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
			<input id="flowdefid" name="flowdefid" class="mini-hidden"
				value="${flowdefid}" />
			<input id="tempid" name="tempid" class="mini-hidden"
				value="${tempid}" />
			<input id="menuid" name="menuid" type="hidden" value="${menuid}" />
			<input id="ischeckseal" name="ischeckseal" class="mini-hidden"
				value="${ischeckseal}" />
			<table>
				<tr>
					<td style="width: 100%;">
						<a class="mini-button" style="display: none;" id="900203_1"
							iconCls="icon-edit" plain="true" onclick="chooseAll('900203_1');">批量修改</a>
					</td>
					<td style="white-space: nowrap;">
						<input class="mini-textbox" id="key" class="mini-textbox"
							emptyText="编号,姓名" onenter="onKeyEnter" />
						<a class="mini-button" plain="true" iconCls="icon-search"
							plain="true" onclick="search()">查询</a>
						<a class="mini-button" plain="true" iconCls="icon-help"
							onclick="chakanshuoming('${menuid}')"></a>
					</td>
				</tr>
			</table>
		</div>
		<div class="mini-fit">
			<div id="datagrid" allowMoveColumn="false"
				style="width: 100%; height: 100%;" class="mini-datagrid"
				allowResize="false" allowSortColumn="true" allowCellSelect="true"
				url="getTuiAnList.action" idField="" multiSelect="true"
				allowAlternating="true" sizeList="[10,20,50,100]" pageSize="20"
				showLoading="true">
				<div property="columns">
					<div type="checkcolumn" width="15"></div>
					<div field="anjianhao" width="67" headerAlign="center"
						allowSort="true" align="center">
						案件号
					</div>
					<div field="crimid" width="30" headerAlign="center"
						allowSort="true" align="center">
						罪犯编号
					</div>
					<div field="name" width="25" headerAlign="center" allowSort="true"
						align="center">
						罪犯姓名
					</div>
					<div field="age" width="15" headerAlign="center" align="center"
						allowSort="true">
						年龄
					</div>
					<div field=orgname1 width="30" headerAlign="center" align="center"
						allowSort="true">
						所在监区
					</div>
					<div id="action" width="25" headerAlign="center" align="center"
						allowSort="false" renderer="onActionRenderer">
						操作
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
mini.parse();
var editWindow = mini.get("editWindow");
var grid = mini.get("datagrid");
grid.sortBy("anjianhao", "asc");
grid.load();
var confirmMessage = "请选中一条记录！";
var confirmMessages = "请至少选中一条记录！";
//快速查询
function search() {
	var key = mini.get("key").getValue();
	grid.sortBy("anjianhao", "asc");
	grid.load( {key : key});
}

function onActionRenderer(e) {
	var s = '';
	s = document.getElementById('middlebtns').value;
	return s;
}

// 渲染日期
function onDateRenderer(e) {
	if (e && e.value) {
		return mini.formatDate(new Date(e.value), "yyyy-MM-dd");
	}
	return "";
}

//关闭 按钮
function cancelEditWindow(){
             editWindow.hide();
}
function onKeyEnter(e) {
	search();
}

function onPageChanged() {
	myloading();
}
function myloading() {
	var myform = new mini.Form("datagrid");
	myform.loading();
}
function unmask() {
	myunmask();
}
function myunmask() {
	var myform = new mini.Form("datagrid");
	myform.unmask();
}

function chooseAll(menuid) {
	var rows = grid.getSelecteds();
	var fatherMenuid = document.getElementById("menuid").value;
	var closetype = 1;
	if (rows.length > 0) {
		if (confirm("确定操作 选中记录？")) {
			var ids = [];
			for ( var i = 0, l = rows.length; i < l; i++) {
				var row = rows[i];
				var flowid = row.flowid;
				if (flowid == undefined) {
					flowid = "flowidnull";
				}
				ids.push(row.crimid + "@" + row.areaid + "@" + row.flowdraftid
						+ "@" + flowid + "@" + row.lastnodeid);
			}
			var flowdefid = mini.get("flowdefid").getValue();
			var tempid = mini.get("tempid").getValue();
			var ischeckseal = mini.get("ischeckseal").getValue();
			var id = ids.join(',');
			var url = "toTuiAnTabs.action?1=1&tempid=" + tempid
					+ "&id=" + id + "&menuid=" + menuid + "&fathermenuid="
					+ fatherMenuid + "&closetype=" + closetype + "&flowdefid="
					+ flowdefid + "&ischeckseal=" + ischeckseal;
			url = encodeURI(encodeURI(url));
			self.location.href = url;
		}
	} else {
		alert("请至少选中一条记录！");
	}
}

function chooseOne(menuid) {
	var row = grid.getSelected();
	var crimid = row.crimid;
	var orgid = row.areaid;
	var flowdraftid = row.flowdraftid;
	var flowid = row.flowid;
	var lastnodeid = row.lastnodeid;
	var fatherMenuid = document.getElementById("menuid").value;
	var tempid = mini.get("tempid").getValue();
	var flowdefid = mini.get("flowdefid").getValue();
	var ischeckseal = mini.get("ischeckseal").getValue();
	if (flowid == undefined) {
		flowid = "flowidnull";
	}
	$.ajax( {
		url : "<%=path%>/toEditTuiAnPage.json?1=1",
		type : "post",
		data : {
			flowdraftid : flowdraftid
		},
		async : false,
		success : function(text) {
			var result = eval(text);
			if (result != '-1') {
				alert("此案件正由" + result + "审批。");
				grid.reload();
			} else {
				var closetype = 1;
				var id = crimid + "@" + orgid + "@" + flowdraftid + "@"
						+ flowid + "@" + lastnodeid;
				var url = "toTuiAnTabs.action?1=1&tempid=" + tempid
						+ "&menuid=" + menuid + "&fathermenuid=" + fatherMenuid
						+ "&id=" + id + "&closetype=" + closetype
						+ "&flowdefid=" + flowdefid + "&ischeckseal="
						+ ischeckseal;
				self.location.href = url;
			}
		},
		error : function() {
			alert("操作失败!");
		}
	});
}
</script>
	</body>
</html>


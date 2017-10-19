<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>个人邮箱</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<meta content="text/html;charset=utf-8" http-equiv="content-type" />

<meta http-equiv="cache-control" content="no-cache" />

<meta http-equiv="expires" content="0" />

<script type="text/javascript" src="<%=path%>/scripts/boot.js"></script>

<script type="text/javascript"
	src="<%=path%>/bootstrap/js/bootstrap.min.js"></script>

<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />

<link href="<%=path%>/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />

<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />

<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.mini-pager-num {
	top: 2px;
	line-height: 15px;
}
</style>
</head>
<body style="padding:0px 2px">
	<div style="padding-bottom:3px;border:0px;background-color:white;">
		<button type="button" class="btn btn-danger btn-sm" id="addsets"
			style="text-shadow: black 5px 3px 3px; padding:2px 5px;margin-top:3px;"
			onclick="addmailsets();">
			<span class="glyphicon glyphicon-plus"></span> 添加邮箱
		</button>
	</div>
	<div class="mini-fit">
		<input name="showid" id="showid" type="hidden" />
		<div id="grmailsets" class="mini-datagrid"
			style="width: 100%; height: 100%;"
			url="${path}/email/getWebMailSetsList.action" allowAlternating="true"
			idField="id" sizeList="[10,20,50,100]" pageSize="20"
			multiSelect="true" borderStyle="border:0px;">
			<div property="columns">
				<div type="indexcolumn" width="45px" headerAlign="center"
					align="center">序号</div>
				<div field="email" width="80%" headerAlign="center" align="left"
					allowSort="true">电子邮件地址</div>
				<div field="" width="10%" headerAlign="center" align="center"
					allowSort="false" renderer="renderoperation">操作</div>
			</div>
		</div>
	</div>
	<div id="editWindow" class="mini-window" title="个人web邮箱"
		style="width:800px;height:500px;border:0px;background-color:red;"
		showMaxButton="true" showCollapseButton="false" showShadow="false"
		showToolbar="false" showFooter="false" showModal="true"
		allowResize="true" showHeader="false" borderStyle="border:0"
		allowDrag="true"></div>
	<script type="text/javascript">
		$(function() {
			search();
		});
		function addmailsets() {
			var url = "${path}/email/toWebMailSets.page?actionType=New";
			var win = mini.get("editWindow");
			var ifram = win.getIFrameEl();
			if (!ifram) {
				win.load(url);
			}
			win.max();
		}

		function editmailsets(id) {
			var url = "${path}/email/toWebMailSets.page?actionType=edit&id="
					+ id;
			var win = mini.get("editWindow");
			win.load(url);
			win.max();
		}

		function search() {
			var grid = mini.get("grmailsets");
			var param = window["param"] || {};
			window["param"] = param;
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		}

		function renderoperation(e) {
			var mailbox = e.record;
			var s = '<a class="Edit_Button" href="javascript:editmailsets(\''
					+ mailbox.id + '\');">修改</a>'
					+ ' <a class="Edit_Button" href="javascript:remove(\''
					+ mailbox.id + '\');" >删除</a>';
			return s;
		}

		function remove(id) {
			var grid = mini.get("grmailsets");
			var url = "${path}/email/removeWebMailSetsInfo.action";
			var data = {
				id : id
			};
			doRemove(data, url, grid);
		}

		function hideEditWidow(reflush) {
			var win = mini.get("editWindow");
			win.hide();
			if (reflush) {
				var grid = mini.get("grmailsets");
				grid.reload();
			}
		}
	</script>
</body>
</html>

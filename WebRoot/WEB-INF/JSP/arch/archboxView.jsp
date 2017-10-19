<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>卷盒文件查看</title>
<meta content="text/html;charset=utf-8" http-equiv="content-type">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />

<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=path%>/bootstrap/js/bootstrap.min.js"></script>
<link href="<%=path%>/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />
<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
<link href="<%=path%>/css/main.css" type="text/css" rel="stylesheet" />


<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
}

.mini-pager-num {
	top: 0px;
	line-height: 15px;
}

label {
	float: left;
	font-weight: normal;
	width: 85px;
	text-align: right;
	margin-right: 10px;
}

.controls {
	float: left;
}

.control-group {
	margin-top: 2px;
	clear: left;
	height: 25px;
}
</style>

</head>
<body style="padding:0px;">
	<div id="yw1" class="td-nav">
		<table class="td-nav-table">
			<tr>
				<td><a style="padding-left:0;padding-right:0;font-size:13px;"
					class="btn btn-link" id="yw1" href="javascript:goback2();">卷盒管理</a><span
					style="display:inline-block;vertical-align: middle;color:#000;">&nbsp;/&nbsp;</span><a
					style="padding-left:0;padding-right:0; text-decoration:none;color:#000;font-size:13px;"
					class="btn btn-link" id="htitle">查看卷盒</a>
					<div class="pull-right">
						<button class="btn btn-default btn-sm" style="padding:2px 10px;"
							onclick="goback2();">返回</button>
					</div></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="grdArchfile" class="mini-datagrid"
			style="width: 100%; height: 100%;border:0px;"
			url="${path}/arch/getArchFileInfos.json" allowAlternating="true"
			idField="fmfileid" sizeList="[10,20,50,100]" pageSize="20"
			multiSelect="true" borderStyle="border:0px;">
			<div property="columns">
				<div field=fileNo width="5%" headerAlign="center"
					sortField="file_no" allowSort="true" align="right">件号</div>
				<div field="fileName" width="20%" headerAlign="center"
					sortField="file_name" align="left" allowSort="true">文件标题</div>
				<div field="fileCode" width="10%" headerAlign="center"
					sortField="file_code" align="left" allowSort="true">文件编号</div>
				<div field="sendUnit" width="10%" headerAlign="center"
					sortField="send_unit" allowSort="true" align="center">发文单位</div>
				<div field="saveDate" width="10%" headerAlign="center"
					sortField="save_date" allowSort="true" align="center"
					renderer="renderSavedata">保管期限</div>
				<div field="secret" width="10%" headerAlign="center"
					sortField="secret" allowSort="true" align="center">秘密等级</div>
				<div field="urgency" width="10%" headerAlign="center"
					sortField="urgency" allowSort="true" align="center">紧急程度</div>
				<div field="date1" width="10%" headerAlign="center"
					dateFormat="yyyy-MM-dd HH:mm:ss" sortField="date1" allowSort="true"
					align="left">时间</div>
				<div field="" width="15%" headerAlign="center" align="center"
					name="colOperation" allowSort="false" renderer="renderOperation1">操作</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function search2(boxId) {
			var grid = mini.get("grdArchfile");
			var param = window["param"] || {};
			window["param"] = param;
			param["sortField"] = "date1";
			param["sortOrder"] = "desc";
			param["boxId"] = boxId;
			param["total"] = -1;
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		}

		function goback2() {
			$("#divarchBoxView").hide();
			$("#divarchBoxList").show();
			var param = window["param"] || {};
			window["param"] = param;
			param["sortField"] = "year";
		}

		function renderOperation1(e) {
			var recode = e.record;
			var s = '<a data-original-title="查看" class="icon-eye td-link-icon" title="查看" href="javascript:viewArchFile(\''
					+ recode.id
					+ '\');">查看</a>&nbsp;&nbsp;<a data-original-title="删除" class="icon-remove-2 td-link-icon" title="删除" action-type="deleteFile" rel="tooltip" href="javascript:moveoutArchFile('
					+ recode.id + ');">移至文件管理</a>';
			return s;
		}
		function moveoutArchFile(id) {
			var grid = mini.get("grdArchfile");
			grid.loading("保存中，请稍候......");
			var url = "${path}/arch/moveoutArchFile.json";
			var successCallback = function(message) {
				grid.unmask();
				grid.reload();
				return false;
			};
			//
			var errotCallback = function(jqXHR, textStatus, errorThrown) {
				// 把错误吃了
				alert("保存失败");
				grid.unmask();
			};
			requestAjax(url, {
				id : id
			}, successCallback, errotCallback);
		}
		function viewArchFile(id) {
			var url = "${path}/arch/gotoArvhFileViewPage.page?id=" + id;
			var win = mini.open({
				url : url,
				showMaxButton : true,
				allowResize : false,
				showHeader : false,
				title : '档案文件查看',
				height : 800,
				width : 1200,
				onload : function() {
				},
				ondestroy : function(action) {

				}
			});
			win.max();
		};
	</script>

</body>
</html>

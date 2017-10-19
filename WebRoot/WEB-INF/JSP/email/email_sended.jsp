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
<title>个人邮箱</title>
<meta content="text/html;charset=utf-8" http-equiv="content-type">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />

<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=path%>/bootstrap/js/bootstrap.min.js"></script>
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />

<link href="<%=path%>/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />

<style type="text/css">
.sidebar_logo {
	height: 100px;
	line-height: 100px;
	font-size: 72px;
	text-align: center;
	color: #dd4b39;
	align: left;
	padding-top: 10px;
}
.mini-pager-num {
	top: 2px;
	line-height: 15px;
}
label {
	font-weight: normal;
}
</style>

</head>
<body>
	<div style="width:100%;height:100%;padding-left:5px;padding-top:10px;">
		<div>
			<c:if test="${box == '1'}">
				<div class="btn-group" style="float:left;">
					<button id="batch_clear" class="btn btn-info btn-sm"
						style="padding:3px 10px; margin:0px;width:50px;" type="button"
						onclick="javascript:deleteSdmail('2');">删除</button>


				</div>
			</c:if>
			<c:if test="${box == '2'}">
				<div class="btn-group" style="float:left;">
					<button id="batch_del" class="btn btn-info btn-sm" type="button"
						style="padding:4px 10px;margin:0px;"
						onclick="javascript:realdeleteDraft();">彻底删除</button>
				</div>
			</c:if>
			<div style="float:right;">
				<input id="subject" class="mini-textbox" emptyText="根据主题搜索"
					onenter="onKeyEnter" style="margin-right:1px;font-size:12px;" /> <a
					class="mini-button" plain="true" iconCls="icon-search" plain="true"
					onclick="search()">查询</a>
			</div>
			<div style="clear:both;"></div>
		</div>

		<div class="mini-fit" style="margin-top:2px;border:0px;">
			<input name="showid" id="showid" type="hidden" /> <input name="box"
				id="box" class="mini-hidden" value="${box}" />
			<div id="datagrid1" class="mini-datagrid"
				style="width: 100%; height: 100%;border:0px;"
				url="${path}/email/getSdMailListByuser.action"
				allowAlternating="false" idField="fmfileid"
				sizeList="[10,20,50,100]" pageSize="20" multiSelect="true"
				borderStyle="border:0px;" ondrawcell="myDrawcell"
				oncellclick="mycellclick" showVGridLines="false">
				<div property="columns">
					<div type="checkcolumn" width="30px"></div>
					<div name="iconcl" type="" width="50px" headerAlign="center"
						headerStyle="border-right:0px" align="left"></div>
					<div field="touname" width="20%" headerAlign="center"
						sortField="touname" headerStyle="border-right:0px" align="left"
						allowSort="false">收件人</div>
					<div field="subject" width="40%" headerAlign="center"
						cellStyle="cursor: pointer;" headerStyle="border-right:0px"
						align="left" allowSort="true">主题</div>
					<div field="createTime" width="15%" headerAlign="center"
						sortField="create_Time" allowSort="true"
						dateFormat="yyyy-MM-dd HH:mm:ss" headerStyle="border-right:0px"
						align="center">时间</div>
					<div field="" width="15%" headerAlign="center" align="center"
						name="colOperation" allowSort="false">操作</div>
				</div>
			</div>
		</div>
		<div id="readwindow" class="mini-window" title="阅读个人邮件"
			style="width:800px;height:500px;border:0px;" showMaxButton="true"
			showCollapseButton="false" showShadow="false" showToolbar="false"
			showFooter="false" showModal="true" allowResize="true"
			showHeader="false" borderStyle="border:0" allowDrag="true"></div>
	</div>
	<script type="text/javascript">
		function mycellclick(e) {
			var record = e.record, field = e.field;
			if (field == 'subject') {
				var box = mini.get("box").getValue();
				if (box == "1") {
					var url = "${path}/email/toMailReadPage.page?box=${box}&contentId="
							+ record.id;
					var win = mini.get("readwindow");
					win.load(url, function() {
						ifram0 = this.getIFrameEl();
						ifram0.contentWindow.SetData(record.fromUname);
					}, function(action) {

					});
					win.max();
				} else if (box == "2") {
					doWork('forward');
				}
			}
		}
		function deleteSdmail(deleteflag) {
			var grid = mini.get("datagrid1");
			var rows = grid.getSelecteds();
			if (rows.length < 1) {
				mini.alert("请选择要删除的行！");
				return;
			}
			mini
					.confirm(
							"如果删除内部收信人未读邮件，内部收件人不会接收该邮件！",
							"确认要删除所选邮件吗？",
							function(action) {
								if (action != "ok")
									return;
								var data = {
									data : mini.encode(rows)
								};
								var url = "${path}/email/deleteSdEmail.action?deleteflag="
										+ deleteflag;
								grid.loading("删除中，请稍候......");
								$.ajax({
									url : url,
									data : data,
									type : "post",
									cache : false,
									async : false,
									success : function(text) {
										grid.unmask();
										grid.reload();
									},
									error : function(jqXHR, textStatus,
											errorThrown) {
										grid.unmask();
										mini.alert("删除失败！");
									}
								});
							});
		}

		function realdeleteDraft() {
			var grid = mini.get("datagrid1");
			var rows = grid.getSelecteds();
			if (rows.length < 1) {
				mini.alert("请选择要删除的行！");
				return;
			}
			mini.confirm("确定彻底删除所选的邮件吗?", "彻底删除？", function(action) {
				if (action != "ok")
					return;
				var data = {
					data : mini.encode(rows)
				};
				var url = "${path}/email/realdeleteDraft.action";
				grid.loading("删除中，请稍候......");
				$.ajax({
					url : url,
					data : data,
					type : "post",
					cache : false,
					async : false,
					success : function(text) {
						grid.unmask();
						grid.reload();
					},
					error : function(jqXHR, textStatus, errorThrown) {
						grid.unmask();
						mini.alert("删除失败！");
					}
				});
			});
		}

		function doWork(action) {
			var grid = mini.get("datagrid1");
			var record = grid.getSelected();
			var url = "${path}/email/toWebMailWritePage.page?box=${box}&contentId="
					+ record.id + "&actionType=" + action;
			var win = mini.get("readwindow");
			win.load(url, function() {
				ifram0 = this.getIFrameEl();
				ifram0.contentWindow.SetData(record.fromUname);
			}, function(action) {

			});
			win.max();
		}

		function hideEditWidow(flag) {
			var win = mini.get("readwindow");
			win.hide();
			if (flag) {
				var grid = mini.get("datagrid1");
				grid.reload();
			}
		}

		function myDrawcell(e) {
			var record = e.record, column = e.column;
			var box = mini.get("box").getValue();

			var icol = '';
			if (record.important != '0') {
				icol = icol
						+ '<i title="重要邮件" rel="tooltip" class="ico-important"></i>';
			}
			if (record.hasattach == '1') {
				icol = icol
						+ '<i title="有附件" rel="tooltip" class="icon-attachment"></i>';
			}
			if (column.name == "iconcl") {
				e.cellHtml = icol;
			}

			var operations = "";
			if (box == "1") {
				operations = '<a data-original-title="再次发送" class="td-link-icon" title="再次发送" rel="tooltip" href="javascript:doWork(\'sendagain\');">再次发送</a>&nbsp;<a data-original-title="转发" class="td-link-icon" title="" rel="tooltip" href="javascript:doWork(\'forward\');">转发</a>&nbsp;<a class="td-link-icon" data-original-title="删除" title="删除" rel="tooltip" href="javascript:deleteSdmail(\'2\');">删除</a>';
			}
			if (box == "2") {
				operations = '<a data-original-title="转发" class="td-link-icon" title="" rel="tooltip" href="javascript:doWork(\'forward\');">转发</a>&nbsp;<a class="td-link-icon" data-original-title="删除" title="删除" rel="tooltip" href="javascript:realdeleteDraft();">删除</a>';
			}
			if (column.name == "colOperation") {

				e.cellHtml = operations;
			}
		}

		$(function() {
			search();
		});

		function onKeyEnter() {
			search();
		}

		function search() {
			var grid = mini.get("datagrid1");
			var param = window["param"] || {};
			window["param"] = param;
			var box = mini.get("box").getValue();

			if (box == "1") {

				param["emailStatus"] = '1';
			} else {
				param["emailStatus"] = '0';
			}
			param["sortField"] = "create_Time";
			param["sortOrder"] = "desc";
			var key = mini.get("subject").getValue();
			param["subject"] = key;
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		}
	</script>

</body>
</html>

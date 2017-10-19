<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<title>工作流发起页面</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
	if ('${myjs1}' != '')
		$.getScript('${path}/scripts/${myjs1}');
</script>
<style type="text/css">
html,body {
	padding: 0;
	margin: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

label {
	font-weight: normal;
	font-size: 13px;
}
</style>
</head>
<body>
	<div class="mini-toolbar" style="border-bottom: 0px;">
		<table>
			<tr>
				<td style="width: 100%;white-space: nowrap;">${topTools}</td>
				<td style="white-space: nowrap;"><input class="mini-textbox"
					id="key" class="mini-textbox" emptyText="工作事项等关键字"
					onenter="onKeyEnter" /> <a class="mini-button" plain="true"
					iconCls="icon-search" plain="true" onclick="search()">快速查询</a> <!-- 操作说明 -->
					<a class="mini-button" plain="true" iconCls="icon-help"
					onclick="chakanshuoming('8738');"></a></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<div id="dgrflow" allowMoveColumn="false"
			style="width: 100%; height: 100%;" class="mini-datagrid"
			allowResize="false" showLoading="true" multiSelect="true"
			allowAlternating="true" sizeList="[20,50,100]" pageSize="20"
			onbeforeselect="ondoselect">
			<div property="columns">
				<div type="checkcolumn" width="5"></div>
				<div type="indexcolumn" width="8" headerAlign="center"
					align="center">序号</div>
				<c:forEach var="clo" items="${titleList}">
					<div field="${clo.field}" headerAlign="${clo.headerAlign}"
						align="${clo.align}" allowSort="${clo.allowSort}"
						width="${clo.width}">${clo.description}</div>
				</c:forEach>
				<div field="cnodestate" width="15" headerAlign="center"
					sortField="response" allowSort="true" align="center"
					renderer="oncNStateRenderer">状态</div>
				<div field="applydatetime" width="15" headerAlign="center"
					allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss" align="center">日期</div>
				<div width="10%" headerAlign="center" align="center" allowSort="false"
					renderer="onActionRenderer">操作</div>
			</div>
		</div>
	</div>
	<div id="editWindow" class="mini-window" title=""
		style="width:800px;height:500px" showMaxButton="true"
		showCollapseButton="false" showShadow="false" showToolbar="false"
		showFooter="false" showModal="true" allowResize="true"
		allowDrag="true"></div>
	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("dgrflow");
		var gridurl = "${path}/deliver/listFlowInstanceBydefineID.action?1=1";
		//
		grid.setUrl(gridurl);
		search();

		//公用新建流程处理函数	
		function add() {
			var url = "${path}/deliver/toflowDeliver_main.page?action=new&menuid=${menuid}&flowdefid=${flowdefid}&templetid=${templetid}&type=${type}&attachjs=${myjs2}&solutionid=${solutionid}";
			var win = mini.open({
				url : url,
				showMaxButton : true,
				allowResize : false,
				width : 1000,
				height : 700,
				showHeader : false,
				ondestroy : function(action) {
					if (action == "OK") {
						grid.reload();
					}
				}
			});
			win.max();
		}

		//删除流程草稿
		function doRemoveflowDraft() {
			var rows = grid.getSelecteds();
			if (rows.length < 1) {
				mini.alert("请选择要删除的行！");
				return;
			}
			var data = {
				data : mini.encode(rows)
			};
			var url = "${path}/deliver/doRemoveflowDraft.action?1=1";
			doRemove(data, url, grid);
		}

		//刷新grid数据
		function refresh() {
			var win = mini.get("editWindow");
			win.hide();
			grid.reload();
		}

		function ondoselect(obj) {
			if (obj.record.cnodestate != '5') {
				obj.cancel = true;
			}
		}
		//模糊查询---查询出所有任务
		function search() {
			var key = mini.get("key").getValue();
			var param = window["param"] || {};
			param["key"] = key;
			param["templetid"] = '${templetid}';
			param["flowdefid"] = '${flowdefid}';
			param["applyid"]='${currentUserid}';
			param["sortField"] = "optime";
			param["sortOrder"] = "desc";
			param["flowtype"] = '${type}';
			param["pageIndex"] = 0;		
			param["total"] = -1;
			param["state"] = '';
			window["param"] = param;
			// 加载数据
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		}

		function oncNStateRenderer(e) {
			var record = e.record;
			var response = record.response;
			var nstates = {
				"5" : "拟办",
				"1" : "办理中",
				"0" : "办理中"
			};
			if (response == '1') {
				return "已办结";
			} else {
				return nstates[e.value];
			}
		}

		function getUnclose() {
			var key = mini.get("key").getValue();
			var param = window["param"] || {};
			param["key"] = key;
			param["templetid"] = '${templetid}';
			param["flowdefid"] = '${flowdefid}';
			param["applyid"]='${currentUserid}';
			param["sortField"] = "optime";
			param["sortOrder"] = "desc";
			param["flowtype"] = '${type}';
			param["pageIndex"] = 0;
			param["response"] = '0';
			param["total"] = -1;
			param["state"] = '01';
			window["param"] = param;
			// 加载数据
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		}

		function getClose() {
			var key = mini.get("key").getValue();
			var param = window["param"] || {};
			param["key"] = key;
			param["templetid"] = '${templetid}';
			param["flowdefid"] = '${flowdefid}';
			param["applyid"]='${currentUserid}';
			param["sortField"] = "optime";
			param["sortOrder"] = "desc";
			param["flowtype"] = '${type}';
			param["pageIndex"] = 0;
			param["state"] = '';
			param["total"] = -1;
			param["response"] = '1';
			window["param"] = param;
			// 加载数据
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		}

		function getReject() {
			var key = mini.get("key").getValue();
			var param = window["param"] || {};
			param["key"] = key;
			param["templetid"] = '${templetid}';
			param["flowdefid"] = '${flowdefid}';
			param["sortField"] = "optime";
			param["sortOrder"] = "desc";
			param["flowtype"] = '${type}';
			param["pageIndex"] = 0;
			param["total"] = -1;
			param["state"] = '';
			param["response"] = '-1';
			window["param"] = param;
			// 加载数据
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		}

		function onActionRenderer(e) {
			var record = e.record;
			var cnodestate = record.cnodestate;
			var response = record.response;
			var flowid = record.flowid;
			var taskid = record.taskid;
			var s = "";
			if (cnodestate == '5') {
				var button1 = ' <a class="icon-pencil-5" href="javascript:doWork(\'edit\',\''
						+ flowid + '\',\'' + taskid + '\');" >&nbsp;发布</a>';
				s = button1
						+ ' &nbsp; <a class="icon-eye" href="javascript:doWork(\'view\',\''
						+ flowid + '\',\'' + taskid + '\');" >&nbsp;查看</a>';

			} else if (response == '0') {				
				s = ' <a class="icon-pencil-5" href="javascript:dourge(\''
						+ flowid
						+ '\',\''
						+ taskid
						+ '\');" >&nbsp;催办</a>'
						//+ ' &nbsp; <a class="icon-pencil-5" href="javascript:doclose(\''
						//+ flowid
						//+ '\',\''
						//+ taskid
						//+ '\');" >&nbsp;办结</a>'
						+ ' &nbsp; <a class="icon-eye" href="javascript:doWork(\'view\',\''
						+ flowid + '\',\'' + taskid + '\');" >&nbsp;查看</a>';

			} else {
				s = ' <a class="icon-eye" href="javascript:doWork(\'view\',\''
						+ flowid + '\',\'' + taskid + '\');" >&nbsp;查看</a>';
			}
			return s;
		}

		function dourge(flowid, taskid) {
			var url = "${path}/urge/tourge.page?flowid=" + flowid;
			var win = mini.open({
				url : url,
				showMaxButton : true,
				allowResize : false,
				showHeader : true,
				title : '任务催办',
				height : 600,
				width : 500,
				onload : function() {
				},
				ondestroy : function(action) {					
				}
			});
			win.show();
		};
		
		var actionNames = {
			"view" : "查看",
			"new" : "新建",
			"edit" : "编辑"
		};
		function doWork(cmd, flowid, taskid) {
			var url = "${path}/deliver/toflowDeliver_main.page?action=" + cmd
					+ "&menuid=${menuid}&flowdefid=${flowdefid}"
					+ "&templetid=${templetid}&type=${type}&flowid=" + flowid
					+ "&taskid=" + taskid + "&attachjs=${myjs2}";
			var win = mini.open({
				url : url,
				showMaxButton : true,
				allowResize : false,
				showHeader : false,
				title : '处理',
				height : 800,
				width : 1200,
				onload : function() {
				},
				ondestroy : function(action) {
					if (action == "OK") {
						grid.reload();
					}
				}
			});
			win.max();
		};
		function onKeyEnter() {
			search();
		}
	</script>
</body>
</html>

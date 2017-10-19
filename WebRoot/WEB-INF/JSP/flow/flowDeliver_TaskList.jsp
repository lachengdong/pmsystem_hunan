<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<title>我的公文-待办页面</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script src="<%=path%>/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>

<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
	if ('${myjs1}' != '')
		$.getScript('${path}/scripts/${myjs1}');
</script>
</head>
<body>
	<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
		<table>
			<tr>
				<td style="width: 100%;white-space: nowrap;">${topTools}</td>
				<td style="white-space: nowrap;"><input class="mini-textbox"
					id="key" class="mini-textbox" emptyText="主题词等关键字"
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
			allowAlternating="true" sizeList="[20,50,100]" pageSize="20">
			<div property="columns">
				<div type="checkcolumn" width="8"></div>
				<div type="indexcolumn" width="8" headerAlign="center"
					align="center">序号</div>
				<c:if test="${flowtype !='3'}">
					<div field="flowname" width="15%" headerAlign="center"
						allowSort="true" align="center">公文类型</div>
				</c:if>
				<c:forEach var="clo" items="${titleList}">
					<div field="${clo.field}" headerAlign="${clo.headerAlign}"
						align="${clo.align}" allowSort="${clo.allowSort}"
						width="${clo.width}">${clo.description}</div>
				</c:forEach>
				<div field="cnodename" width="10%" headerAlign="center" 
					allowSort="true" align="center" align="left">当前步骤</div>
				<div field="cnodestate" width="15" headerAlign="center"
					allowSort="true" align="center" renderer="oncStateRenderer">当前状态</div>
				<div field="flowstate" width="15" headerAlign="center"
					allowSort="true" align="center" renderer="onStateRenderer">总状态</div>
				<div field="applydatetime" width="20" headerAlign="center"
					allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss" align="center">日期</div>
				<div width="10%" headerAlign="center" align="center"
					allowSort="false" renderer="onActionRenderer">操作</div>
			</div>
		</div>
	</div>




	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("dgrflow");
		var gridurl = "${path}/deliver/listFlowInstanceBydefineID.action?1=1";
		//
		grid.setUrl(gridurl);
		search();

		function search() {
			var key = mini.get("key").getValue();
			var param = window["param"] || {};
			param["key"] = key;
			param["templetid"] = '${templetid}';
			param["flowdefid"] = '${flowdefid}';
			param["sortField"] = "applydatetime";
			param["sortOrder"] = "desc";
			param["pageIndex"] = 0;
			param["state"] = '${state}';
			param["flowtype"] = '${flowtype}';
			window["param"] = param;
			// 加载数据
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		}

		function onActionRenderer(e) {
			var record = e.record;
			var cnodestate = record.cnodestate;
			var flowid = record.flowid;
			var taskid = record.taskid;
			var s = "";
			if (cnodestate == '0' || cnodestate == '5'|| cnodestate == '2') {
				var button1 = ' <a class="icon-pencil-5" href="javascript:doWork(\'edit\',\''
						+ flowid + '\',\'' + taskid + '\');" >&nbsp;处理</a>';
				s = button1
						+ ' &nbsp; <a class="icon-eye" href="javascript:doWork(\'view\',\''
						+ flowid + '\',\'' + taskid + '\');" >&nbsp;查看</a>';

			} else {
				s = ' <a class="icon-eye" href="javascript:doWork(\'view\',\''
						+ flowid + '\',\'' + taskid + '\');" >&nbsp;查看</a>';
			}
			return s;
		}

		function doWork(cmd, flowid, taskid) {
			var url = "${path}/deliver/toflowDeliver_main.page?action=" + cmd
					+ "&menuid=${menuid}&flowdefid=${flowdefid}"
					+ "&templetid=${templetid}&type=${flowtype}&flowid="
					+ flowid + "&taskid=" + taskid + "&attachjs=${myjs2}";
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
					grid.reload();
				}
			});
			win.max();
		};
		function onKeyEnter() {
			search();
		};
		function onStateRenderer(e) {
			var states = {
				"0" : "办理中",
				"1" : "完成"
			};
			return states[e.value];
		};
		function oncStateRenderer(e) {
			var states = {
				"0" : "办理中",
				"1" : "完成",
				"2" : "被退回",
				"5" : "草稿"
			};
			return states[e.value];

		}
	</script>
</body>
</html>

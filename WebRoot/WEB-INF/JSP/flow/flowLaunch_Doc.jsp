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
<title>工作流起草页面</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script src="<%=path%>/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>
<link href="<%=path%>/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />
<link href="<%=path%>/css/icomoon.css" type="text/css" rel="stylesheet" />
<style type="text/css">
label {
	font-weight: normal;
	font-size: 13px;
}
</style>
<script type="text/javascript">
	if ('${myjs1}' != '')
		$.getScript('${path}/scripts/${myjs1}');
</script>
</head>
<body>
	<div class="mini-toolbar" style="border-bottom: 0px;">
		<table>
			<tr>
				<td
					style="width: 100%;white-space: nowrap;padding-left:2px;font-size:13px;font-weight:bold;">${typeName}</td>
				<td style="white-space: nowrap;"><input class="mini-textbox"
					id="key" class="mini-textbox" emptyText="流程名称"
					onenter="onKeyEnter" /> <a class="mini-button" plain="true"
					iconCls="icon-search" plain="true" onclick="search()">快速查询</a> <!-- 操作说明 -->
					<a class="mini-button" plain="true" iconCls="icon-help"
					onclick="chakanshuoming('8738');"></a></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<input name="htype" class="mini-hidden" id="htype" type="hidden"
			value="${type}" />
		<div id="dgrflow" allowMoveColumn="false"
			style="width: 100%; height: 100%;" class="mini-datagrid"
			url="${path}/deliver/getFlowByTypeAndDept.action" allowResize="false"
			showLoading="true" multiSelect="true" allowAlternating="true"
			sizeList="[20,50,100]" pageSize="20" showPager="false">
			<div property="columns">
				<div type="indexcolumn" width="40px" headerAlign="center"
					align="center">序号</div>
				<div field="remark" width="70%" headerAlign="center"
					allowSort="true" align="left">流程名称</div>
				<div width="15%" headerAlign="center" align="center"
					allowSort="false" renderer="onActionRenderer"></div>
				<div width="15%" headerAlign="center" align="center"
					allowSort="false" renderer="onActionRenderer2">操作</div>
			</div>
		</div>
	</div>
	<div id="editWindow" class="mini-window" title=""
		style="width:800px;height:500px" showMaxButton="true"
		showCollapseButton="false" showShadow="false" showToolbar="false"
		showFooter="false" showModal="true" allowResize="true"
		showHeader="false" allowDrag="true"></div>
	<script type="text/javascript">
		mini.parse();
		this.search();
		function search() {
			var grid = mini.get("dgrflow");
			var param = window["param"] || {};
			window["param"] = param;
			var key = mini.get("key").getValue();
			var type = mini.get("htype").getValue();
			param["key"] = key;
			param["type"] = type;
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		}

		function onActionRenderer(e) {			
			var record = e.record;
			var flowdefid = record.flowdefid;
			var tempid = record.tempid;
			var s = ' <a class="icon-network" href="javascript:viewflow(\''
					+ flowdefid
					+ '\');" >&nbsp;查看流程</a>'
					+ ' &nbsp; <a class="icon-table" href="javascript:viewTemplate(\''
					+ tempid + '\');" >&nbsp;查看表单</a>';
			return s;
		}
		function onActionRenderer2(e) {
			var btnText={"1":"新建发文","2":"新建收文"};
			var s = ' <a class="btn btn-success btn-sm" href="javascript:doflowInstanceDraft('
					+ e.rowIndex + ');" >'+btnText['${type}']+'</a>';
			return s;
		}
		//公用新建流程处理函数		
		function doflowInstanceDraft(rowIndex) {
			var grid = mini.get("dgrflow");
			var data = grid.getRow(rowIndex);

			var url = "${path}/deliver/toflowDeliver_main.page?action=new&menuid=${menuid}&flowdefid="
					+ data.flowdefid
					+ "&templetid="
					+ data.tempid
					+ "&type="
					+ data.type + "&attachjs=${myjs2}&solutionid=${solutionid}";

			var win = mini.open({
				url : url,
				showMaxButton : true,
				allowResize : false,
				width : 1000,
				height : 700,
				showHeader : false,
				ondestroy : function(action) {
				}
			});
			win.max();
		}

		//查看流程定义信息
		function viewflow(flowdefid) {

			var url = "${path}/deliver/toViewFlowDefineInfo.page?flowdefid="
					+ flowdefid;
			var win = mini.open({
				url : url,
				showMaxButton : true,
				allowResize : false,
				title : "流程查看",
				width : 1000,
				height : 700,
				showHeader : true,
				ondestroy : function(action) {

				}
			});
			win.show();
		}

		//查看流程表单信息
		function viewTemplate(templetid) {

			var url = "${path}/deliver/toViewFlowFormInfo.page?templetid="
					+ templetid;
			var win = mini.open({
				url : url,
				showMaxButton : true,
				allowResize : false,
				title : "流程表单查看",
				width : 1000,
				height : 700,
				showHeader : true,
				ondestroy : function(action) {

				}
			});
			win.show();
		}
	</script>
</body>
</html>
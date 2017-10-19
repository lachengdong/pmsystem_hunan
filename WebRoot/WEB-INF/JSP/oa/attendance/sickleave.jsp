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
<title>还款业务</title>
<meta content="text/html;charset=utf-8" http-equiv="content-type">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script src="<%=path%>/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>
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
	top: 2px;
	line-height: 15px;
}

label {
	font-weight: normal;
}
</style>

</head>
<body>
	<div class="mini-toolbar" style="border-bottom: 0px;">
		<table>
			<tr>
				<td
					style="width: 100%;white-space: nowrap;padding-left:2px;font-size:13px;font-weight:bold;">
					<div id="chkpersonal" class="mini-CheckBox" value="1" trueValue="1"
						falseValue='0' text="&nbsp;我的请假单"></div>
				</td>
				<td style="white-space: nowrap;"><input class="mini-textbox"
					id="key" class="mini-textbox" emptyText="根据请假单号、请假人姓名查询"
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
			url="${path}/leave/getLeaveListInfo.action" allowResize="false"
			showLoading="true" multiSelect="true" allowAlternating="true"
			sizeList="[20,50,100]" pageSize="20" showPager="false">
			<div property="columns">
				<div type="indexcolumn" width="5%" headerAlign="center"
					align="center">序号</div>
				<div field="text2" width="15%" headerAlign="center"
					allowSort="true" align="center" sortField="text2">请假人</div>
				<div field=text3 width="30%" headerAlign="center"
					allowSort="true" align="center" sortField="dept_Name">部门</div>
				<div field="leavedate1" width="25%" headerAlign="center"
					dateFormat="yyyy-MM-dd" allowSort="true" align="center"
					sortField="borrow_date">结束日</div>
				<div field="leavedate2" width="15%" headerAlign="center"
					dateFormat="yyyy-MM-dd" allowSort="true" align="center"
					sortField="borrow_date">结束日</div>
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
			var isSelf = mini.get("chkpersonal").getValue();
			if (isSelf == '1') {
				param["name"] = '${onlineUser}';
			} else {
				param["name"] ='';
			}		
			param["key"] = key;
			param["ispayback"] = 0;
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		}

		function onActionRenderer2(e) {		
			var s = ' <a class="btn btn-success btn-sm" href="javascript:doflowInstanceDraft('
					+ e.rowIndex + ');" >销假</a>';
			return s;
		}
		//公用新建流程处理函数		
		function doflowInstanceDraft(rowIndex) {
			var grid = mini.get("dgrflow");
			var data = grid.getRow(rowIndex);
			var url = "${path}/sickleave/toflowDeliver_main.page?action=new&menuid=${menuid}&flowdefid=${flowdefid}&templetid=${templetid}&type=${type}&attachjs=${myjs2}&solutionid=${solutionid}&uuid="+data.uuid;
			var win = mini.open({
				url : url,
				showMaxButton : true,
				allowResize : false,
				width : 1000,
				height : 700,
				showHeader : false,
				ondestroy : function(action) {
					var grid = mini.get("dgrflow");
					grid.reload();
				}
			});
			win.max();
		}

		//查看借款单信息
		function viewloanInfo(flowid) {	
			var url = "${path}/loan/toloanInfo.page?flowid=" + flowid;			
			vRet = window
					.showModalDialog(
							url,
							{},
							"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:800px;dialogWidth:900px");

		
		}		
	</script>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<title>流程管理页面</title>
<!--[if lt IE 9]>
<?import namespace="v" implementation="#default#VML" ?>
<![endif]-->
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="mini-toolbar" style="padding: 2px; border-bottom: 0px;">
		<table>
			<tr>
				<td style="width:100%"><a class="mini-button" plain="true"
					iconCls="icon-add" id="addflow" onclick="addflowpage();">新增流程</a></td>
				<td style="white-space:nowrap;"><input id="mykey"
					class="mini-textbox" emptyText="流程名称，ID" onenter="onKeyEnter" /> <a
					class="mini-button" plain="true" iconCls="icon-search" plain="true"
					onclick="search()">查询</a><a class="mini-button" plain="true"
					iconCls="icon-help" onclick="chakanshuoming('8738');"></a> <input
					id="fullopen" name="fullopen" type="hidden" value="" /></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit">
		<input name="showid" id="showid" type="hidden" />
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;"
			url="${path}/OAflow/getFlowByDepartid.action" allowAlternating="true"
			idField="fmfileid" sizeList="[10,20,50,100]" pageSize="20"
			multiSelect="true">
			<div property="columns">
				<div type="checkcolumn" width="30px"></div>
				<div type="indexcolumn" width="45px" headerAlign="center"
					align="center">序号</div>
				<div field="flowdefid" width="20%" headerAlign="center" align="left"
					allowSort="true">流程id</div>
				<div field="remark" width="40%" headerAlign="center" align="left"
					allowSort="true">流程名称</div>
				<div field="optime" width="15%" headerAlign="center"
					allowSort="true" dateFormat="yyyy-MM-dd HH:mm:ss" align="center">修改时间</div>
				<div field="" width="10%" headerAlign="center" align="center"
					allowSort="false" renderer="renderOperation">操作</div>
			</div>
		</div>
	</div>

	<div id="win1" class="mini-window" title=""
		style="width:950px;height:550px;padding:0px;" showMaxButton="true"
		bodyStyle="padding:0px;border:0px;" showCollapseButton="false"
		showShadow="true" showToolbar="false" showFooter="false"
		showModal="true" borderStyle="border:0px;" allowResize="true"
		allowDrag="true" onbuttonclick="fbuttonclick">加载中...</div>

	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");
		this.search();

		function search() {
			var param = window["param"] || {};
			window["param"] = param;
			var key = mini.get("mykey").getValue();
			param["key"] = key;
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		}
		function renderOperation(e) {
			var flow = e.record;
			var s = '<a class="Edit_Button" href="javascript:detail(\''
					+ flow.flowdefid + '\');">明细</a>'
					+ ' <a class="Edit_Button" href="javascript:remove(\''
					+ flow.flowdefid + '\');" >删除</a>';
			return s;
		}

		//删除流程
		function remove(flowdefid) {
			var url = "${path}/OAflow/removeflowbyid.action?1=1";
			var data = {
				flowdefid : flowdefid
			};
			doRemove(data, url, grid);
		}

		//查看编辑流程
		function detail(e) {
			//var url = "${path}/flow/toFlowDesign.page?1=1&flowdefid=" + e
			//		+ "&actionType=Edit";
			//grid.loading();
			//window.location = url;
			//return;
			//grid.loading();
			var wintop = mini.get("win1");
			var randomVal = new Date().getTime();
			var url = "${path}/OAflow/toFlowDesign.page?1=1&flowdefid=" + e
					+ "&actionType=Edit&__r=" + randomVal;
			
			
			//window
		//.showModalDialog(
		//			url,
		//			{
					
		//			},
		//			"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:800px;dialogWidth:1000px");

			
			
			
			wintop.load(url);
			wintop.hide();
			var atEl = document.getElementById("addflow");
			wintop.showAtEl(atEl, {
				xAlign : "outleft",
				yAlign : "above"
			});
			wintop.max();
		}

		//新增流程
		function addflowpage() {
			//grid.loading();
			var url = "${path}/OAflow/toFlowDesign.page?1=1&flowdefid=&actionType=New";
			//url = "topage.action?viewName=test/index2";
			var win = mini.get("win1");
			win.load(url);
			var atEl = document.getElementById("addflow");
			win.showAtEl(atEl, {
				xAlign : "outleft",
				yAlign : "above"
			});
			win.max();
		}

		//显示设计流程窗口
		//function showDesignwin() {
		//	var wintop = mini.get("win1");
		//	var atEl = document.getElementById("addflow");
		///	wintop.showAtEl(atEl, {
		//		xAlign : "outleft",
		//		yAlign : "above"
		//	});
		//	wintop.max();
		//	grid.unmask();
		//}

		//function reflesh() {
		//	var wintop = mini.get("win1");
		//	wintop.restore();
		//	wintop.max();
		//	grid.unmask();

		//}

		function reloadData() {
			var wintop = mini.get("win1");
			wintop.hide();
			grid.reload();
		}

		function onKeyEnter() {
			search();
		}

		function fbuttonclick(e) {
			//if(e.name=="close")
			//	{

			//	}
			//alert(e.name);

		}
	</script>
</body>
</html>
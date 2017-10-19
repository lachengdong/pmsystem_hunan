<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流程实例图</title>
<!--[if lt IE 9]>
<?import namespace="v" implementation="#default#VML" ?>
<![endif]-->
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/workFlowDesign/ExtJs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css"
	href="<%=path%>/workFlowDesign/designUI.css" />
<script src="<%=path%>/workFlowDesign/ExtJs/ext-all-debug.js"
	type="text/javascript"></script>
<script src="<%=path%>/workFlowDesign/workFlowDesign.js"
	type="text/javascript"></script>
<style type="text/css">
html,body {
	font-size: 12px;
	padding: 0;
	margin: 0;
	border: 0;
	height: 100%;
	overflow: hidden;
}
</style>

</head>

<body>
	<div
		style="border-bottom: 0px;height:35px;vAlign:center;padding-top:5px;">
		<span class="td-nav-title" style="display: block;">当前步骤---${cNodeName}</span>
	</div>
	<div class="mini-fit">
		<div class="mini-splitter" style="width:100%;height:100%;">
			<div size="65%" showCollapseButton="false">
				<div id="divWorkFlowBg" style='width:100%; height:100%'></div>
			</div>
			<div showCollapseButton="true" style="border:0px">
				<div
					style="border-bottom: 0px;height:30px;vAlign:center;padding-top:5px;">
					<span class="td-nav-title" style="display: block;">流程完成情况列表：</span>
				</div>
				<div class="mini-fit">
					<div id="dgrflow" allowMoveColumn="false"
						style="width: 100%; height: 100%;" class="mini-datagrid"
						allowResize="false" showLoading="true" multiSelect="true"
						allowAlternating="true" sizeList="[20,50,100]" showPager="false">
						<div property="columns">
							<div type="indexcolumn" width="40px" headerAlign="center"
								align="center">序号</div>
							<div field="cnodename" width="15%" headerAlign="center"
								allowSort="false" align="left">当前步骤</div>
							<div field="lassigneername" width="20%" headerAlign="center"
								allowSort="false" align="left">上一步办理人</div>
							<div field=assigneername width="20%" headerAlign="center"
								allowSort="false" align="left">办理人</div>
							<div field="startdate" width="20%" headerAlign="center"
								allowSort="false" renderer="onDateRenderer" align="center"
								dateFormat="yyyy-MM-dd HH:mm:ss">开始时间</div>
							<div field="enddate" width="25%" headerAlign="center"
								allowSort="false" renderer="onDateRenderer" align="center"
								dateFormat="yyyy-MM-dd HH:mm:ss">结束时间</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		Ext.onReady(function() {
			//页面根路径
			workFlowDesign.$basepath = '${path}' + '/';
			var flowdefid = '${flowdefine.flowdefid}';
			var flowdescription = '${flowdefine.remark}';
			var type = '${flowdefine.type}';
			var nodedata = Ext.JSON.decode('${flowdefine.nodedata}');
			var linedata = Ext.JSON.decode('${flowdefine.linedata}');
			//数据加载完毕，显示设计窗口			
			workFlowDesign.readLoad(flowdefid, flowdescription, type, nodedata,
					linedata, 'divWorkFlowBg');
			workFlowDesign.selectNode('${cNode}');
		});

		jQuery(function($) {
			var grid = mini.get("dgrflow");
			var tasktemp = '${tasks}';
			if (tasktemp != '') {
				var tasks = mini.decode(tasktemp);
				grid.addRows(tasks);
				grid.accept();
			}
		});

		// 渲染日期
		function onDateRenderer(e) {
			if (e && e.value) {
				return mini
						.formatDate(new Date(e.value), "yyyy-MM-dd HH:mm:ss");
			}
			return "";
		};
	</script>
</body>
</html>

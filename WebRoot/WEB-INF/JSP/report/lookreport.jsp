<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isErrorPage="true"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
	Map<String,String> topmap = (Map<String,String>)request.getAttribute("topmap");
	Map<String,String> middlemap = (Map<String,String>)request.getAttribute("middlemap");
%>
<html>
	<head>
		<base href="<%=basePath%>"/>
		<title>报表数据列表</title>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"			rel="stylesheet" type="text/css" />
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
	<body>
		<div class="crud-grid mini-fit">
			<div id="datagrid1" class="mini-datagrid"
				url="report/reprotList.json?1=1" idField="id"
				allowResize="false" pageSize="20" allowCellEdit="true"
				allowCellSelect="true" multiSelect="true"
				allowMoveColumn="false" style="width:100%;height:100%;"
				sortField="optime" sortOrder="desc"
				>
				 <div property="columns">
				 	<div type="indexcolumn" width="10"></div>
					<div field="name" width="40" headerAlign="center"
					  align="center" allowSort="true">
						所在单位
					</div>
					<div field="title" width="35" headerAlign="center"
						allowSort="true" align="center" >
						文件标题
					</div>
					<div field="optime" width="25" renderer="onStatusRenderer"
						align="center" headerAlign="center">
						生成时间
					</div>
					<div field="opid" width="35" headerAlign="center" align="center"
						allowSort="true">
						操作人员
					</div>
					<div field="" width="35" headerAlign="center" renderer="onActionRenderer"
						allowSort="true" align="center">
						操作
					</div>
				</div>
			</div>
		</div>
   	<script type="text/javascript">
        mini.parse();
        var grid = mini.get("datagrid1");
        grid.load();
        
        function onActionRenderer(e){
        	var record=e.record;
        	var docid = record.docid; 
        	var s='<a class="Edit_Button" href="javascript:chakan(\'' + docid+ '\',0)">查看</a>';
        	return s;
        }
        function chakan(docid){
        	var win=mini.open({
        		url:"report/chakanReportPage.page?1=1&docid="+docid,
        		showMaxButton: true,
	            contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
	            allowResize: true, 
	            title: "预览", width: 1200, height: 700
        	});
        }
    </script>
	</body>
</html>
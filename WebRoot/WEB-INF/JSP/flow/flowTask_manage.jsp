<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>流程任务</title>
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
<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />

<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

.mini-menuitem-text {
	text-align: left;
	cursor: pointer;
}
.mini-tabs-scrollCt
{
	border:0px;
	display:none;
	height:0px;
}
</style>

</head>

<body>
	<div id="layout1" class="mini-layout"
		style="width:100%;height:100%;margin-top:1px;margin-left:1px;">
		<div showHeader="false" region="east" width="180" maxWidth="250"
			minWidth="100" showSplit="false" showSplitIcon="true"
			bodyStyle="overflow: hidden;BACKGROUND: url(${path}/images/email/bgg.png) #fff repeat-y;text-align:center;">
			<!-- <div class="sidebar_logo T_icon" style="width:195px;margin-bottom:20px;">
				<span class="icon-pencil-5"></span>
			</div> -->
			<div id="leftTree" class="mini-outlookmenu"
				onitemselect="onItemSelect" idField="resid" parentField="presid"
				textField="name" borderStyle="border:0" iconField="showico"></div>

		</div>
		<div title="center" region="center"
			bodyStyle="border:0px;overflow:hidden;"
			style="width:100%;height:100%;border: 0px;overflow:hidden">
			<div class="mini-tabs" activeIndex="0" id="tasktabs"
				maskOnLoad="true" borderStyle="border:0px;"  onactivechanged="tabactivechanged"
				style="border:0px;width:100%;height:100%;margin-right:2px;overflow: hidden;margin-bottom:2px;"
				bodyStyle="border:0px;padding:0px;overflow: hidden;padding-bottom:1px;"
				headerStyle="border:0px;CURSOR: pointer;"></div>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			var tree = mini.get("leftTree");
			var menus = mini.decode('${treeMenus}');
			tree.loadList(menus, 'resid', 'presid');			
			var nodes=tree.findNodes(function(node){return true;});	
			if (nodes.length > 0) {			
				tree.selectNode(nodes[0]);
			}
		});
		function onItemSelect(e) {
			var node = e.item;
			if (!node || !node.srurl) {
				return;
			}
			var tabs = mini.get("tasktabs");
			var id = "tab$" + node.resid;
			var tab = tabs.getTab(id);
			if (!tab) {
				tab = {};
				tab.name = id;
				tab.title = node.name;				
				var srurl = node.srurl;
				var ismenu = node.ismenu;
				var formid = node.formid;
				var querysql = node.querysql;
				if (srurl) {
					srurl = '${path}/' + srurl.trim();
					var theURL = srurl;
					var indexQuestionMark = srurl.indexOf("?");
					var indexEqualSign = srurl.indexOf("=");
					if (indexQuestionMark < 0) {
						theURL += "?1=1";
					} else if (indexEqualSign < 0) {
						theURL += "1=1";
					}
					if (0 === ismenu || ismenu) {
						theURL += '&ismenu=' + ismenu;
					}
					if (formid) {
						theURL += '&tempid=' + formid;
					}
					if (querysql) {
						theURL += '&solutionid=' + querysql;
					}
					theURL += '&menuid=' + node.resid;
					tab.url = theURL;
					tabs.addTab(tab);
				}
			}
			tabs.activeTab(tab);		
		}
		
		function tabactivechanged(e)
		{			
			mini.get("layout1").doLayout();		
		}
	</script>
</body>
</html>

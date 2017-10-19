<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>卷库选择器</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>

<style type="text/css">
html,body {
	padding: 0;
	margin: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

.filefoldpic {
	background: url(${path}/images/folder.gif) no-repeat;
}
</style>
</head>
<body>
	<div id="div_one" style="width:100%;height:100%;">
		<input id="id" class="mini-hidden" />
		<div class="mini-toolbar" style="padding:0px;border:0;text-align:left">
			<table style="width:100%;">
				<tr>
					<td><a class="mini-button" onclick="onOk" plain="true"
						style="width:50px">确定</a> <a class="mini-button"
						onclick="onCancel" plain="true" style="width:50px;">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

					</td>
				</tr>
			</table>
		</div>
		<div class="mini-fit">
			<ul id="tree1" class="mini-tree" style="width:100%;height:100%;"
				showTreeIcon="true" textField="name" idField="id"
				parentField="parentId" resultAsTree="false" showRadioButton="false"
				onnodeselect="onTreeNodeSelect" ondrawnode="onDrawNode"
				expandOnLoad="false">
			</ul>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			var tree = mini.get("tree1");
			tree.loadList(mini.decode('${folders}'), 'id', 'parentId');
		});

	
		function onOk() {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			
			mini.confirm("确定放入"+node.name+"中?","确认归档？",function (action){
				if(action!='ok')return;
				parent.doArchive(node.id);				
			});		
		};
		function onCancel() {
			parent.doArchive();
		};
		//目录树画面初始化渲染操作--处理目录icon
		function onDrawNode(e) {
			var tree = e.sender;
			var node = e.node;
			var isLeaf = tree.isLeaf(node);
			if (isLeaf) {
				e.iconCls = "filefoldpic";

			}
		};
		
		function onTreeNodeSelect(e) {
			onOk();
		};
	
	</script>
</body>
</html>
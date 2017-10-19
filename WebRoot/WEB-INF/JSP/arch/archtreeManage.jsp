<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>卷库维护</title>
<meta content="text/html;charset=utf-8" http-equiv="content-type" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible" />
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



.filefoldpic {
	background: url(${path}/images/folder.gif) no-repeat;
}

label {
	float: left;
	font-weight: normal;
	width: 60px;
}

.controls {
	float: left;
}

.control-group {
	margin-top: 5px;
	clear: left;
	height: 25px;
}
</style>

</head>
<body>
	<div class="mini-layout" style="width:100%;height:100%;padding:0px;">
		<div width="240px" height="100%" region="east" showSplit="false"
			showHeader="false" showSplitIcon="true" showCollapseButton="true"
			bodyStyle="border:0px;overflow: hidden;" class="sidebar">
			<div id="tabs1" class="mini-tabs" activeIndex="0"
				headerStyle="border:0px;CURSOR: pointer;"
				style="width:100%;height:100%;" plain="true" buttons="#tabsButtons"
				bodyStyle="border:0px;" onbeforeactivechanged="myactivechanged">
				<div title="卷库目录" name="tabprivate">
					<div class="mini-fit">
						<!--Tree-->
						<ul id="fileTree" class="mini-tree"
							style="width:100%;height:100%;" url="" resultAsTree="false"
							textField="name" idField="id" parentField="parentId"
							onnodeselect="onTreeNodeSelect" ondrawnode="onDrawNode">
						</ul>
					</div>
				</div>
			</div>
			<div id="tabsButtons">
				<button type="button" class="btn btn-danger btn-sm" id="addsets"
					style="text-shadow: black 5px 3px 3px; padding:0px 5px;margin-top:3px;margin-right:10px;"
					onclick="addarchfolder();" title="新建卷库">
					<span class="glyphicon glyphicon-plus"></span>
				</button>
			</div>
		</div>
		<div region="center" style="border-top:0px;padding:0px;margin:0px;">
			<div id="yw1" class="td-nav">
				<table class="td-nav-table">
					<tbody>
						<tr>
							<td>
								<div>
									<span class="ellipsis" style="display:inline-block;width:150px;font-size:14px;"
										id="spnTitle"> 卷库管理 </span>
									<div class="pull-right">
										<div id="yw2" class="pull-right">
											<button id="save" class="btn btn-primary btn-sm"
												style="padding:2px 10px" onclick="doSave();">保存</button>
											&nbsp;
											<button id="delete" class="btn btn-danger btn-sm"
												style="padding:2px 10px" type="button" onclick="dodelete();">删除</button>
										</div>
									</div>
								</div>
							</td>

						</tr>
					</tbody>
				</table>
			</div>

			<div class="mini-fit">
				<div class="form" style="margin-left:15px;margin-top:15px;"
					id="editform">
					<input name="id" id="id" class="mini-hidden" /> <input
						name="action" id="action" class="mini-hidden" />
					<div class="control-group">
						<label class="control-label required" for="ArchTree_no"
							style="float:left;">编号 <span class="required">*</span>
						</label>
						<div class="controls">
							<input size="50" maxlength="10" name="no" class="mini-textbox"
								id="ArchTree_no" type="text" vtype="maxLength:10"
								required="true" requiredErrorText="编号不能为空！" style="width:200px;" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label required" for="ArchTree_name">名称
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input size="50" maxlength="50" name="name" class="mini-textbox"
								id="ArchTree_name" type="text" vtype="maxLength:50"
								required="true" requiredErrorText="名称不能为空！" style="width:200px;" />
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="ArchTree_parent_id">所属卷库</label>
						<div class="controls">
							<input class="mini-treeselect" name="parentId" valueField="id"
								valueFromSelect="true" textField="name" style="width:200px"
								id="trparentId" />


						</div>
					</div>

					<div class="control-group ">
						<label class="control-label" for="ArchTree_remark">备注</label>
						<div class="controls">
							<input size="60" maxlength="200" name="remark"
								class="mini-textarea" style="width:500px;margin-bottom:10px;"
								id="ArchTree_remark" type="text" />
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		var currentFolderID = 0;
		var currentNode = {};

		//页面初始化
		$(function() {
			var tree1 = mini.get('fileTree');
			var folders = mini.decode('${folders}');
			tree1.loadList(folders, 'id', 'parentId');
			addarchfolder();
			//tree1.expandLevel(0);
			//tree1.selectNode(tree1.getChildNodes()[0]);
		});
		//目录树选择
		function onTreeNodeSelect(e) {
			var node = e.node;
			var tree1 = mini.get('fileTree');
			var tree2 = mini.get('trparentId');
			var form = new mini.Form("#editform");
			var data = tree1.getList();
			data = mini.clone(data);
			$.each(data, function(i, n) {
				if (n.id == node.id) {
					data = arrayDel(data, i);
					return false;
				}
			});

			tree2.loadList(data, 'id', 'parentId');
			$("#delete").show();
			$("#spnTitle").text("卷库管理 / 卷库编辑");
			node.action = "edit";
			form.setData(node, true);
			//search();
		}

		//目录树画面初始化渲染操作--处理目录icon
		function onDrawNode(e) {
			var tree = e.sender;
			var node = e.node;
			var isLeaf = tree.isLeaf(node);
			if (isLeaf) {
				e.iconCls = "filefoldpic";

			}
		}

		function addarchfolder() {
			var tree1 = mini.get('fileTree');
			var tree2 = mini.get('trparentId');
			var form = new mini.Form("#editform");
			var data = tree1.getList();
			data = mini.clone(data);
			tree2.loadList(data, 'id', 'parentId');

			var node = tree1.getSelectedNode();
			var node2 = {};
			if (node) {
				node2.parentId = node.id;
			}
			node2.action = "new";
			form.setData(node2, true);
			$("#spnTitle").text("卷库管理 / 新建卷库");
			$("#delete").hide();
			mini.get("ArchTree_no").focus();
		}

		function doSave() {
			var form = new mini.Form("#editform");
			// 校验
			form.validate();
			if (form.isValid() == false) {
				return;
			}
			var data = form.getData();
			// 界面
			form.loading("保存中，请稍候......");
			var url = "${path}/arch/ajax/editArchfolderInfo.json";
			var successCallback = function(message) {
				form.unmask();
				var info = message["info"];
				if (1 === message["status"]) {
					// 判断是否成功
					mini.alert(info, '提示', function() {
						var tree1 = mini.get('fileTree');
						tree1.loadList(message.data, 'id', 'parentId');
						tree1.selectNode('' + message.key + '');
						tree1.expandPath('' + message.key + '');
					});
				} else {
					alert(info);
				}
				return false;
			};
			//
			var errotCallback = function(jqXHR, textStatus, errorThrown) {
				// 把错误吃了
				alert("保存失败");
				form.unmask();
			};
			requestAjax(url, {
				data : mini.encode(data)
			}, successCallback, errotCallback);
		}

		function dodelete() {

			mini.confirm(
				"确定删除案卷？",
				"确认!",
				function(action) {
					if (action == "ok") {
						var form = new mini.Form("#editform");
						// 界面
						form.loading("删除中，请稍候......");
						var url = "${path}/arch/ajax/deleteArchfolderInfo.json";
						var successCallback = function(message) {
							form.unmask();
							var info = message["info"];
							if (1 === message["status"]) {
								// 判断是否成功
								mini.alert(info, '提示', function() {
									var tree1 = mini
											.get('fileTree');
									tree1.loadList(message.data,
											'id', 'parentId');								
								});
							} else {
								alert(info);
							}
							return false;
						};
						//
						var errotCallback = function(jqXHR,
								textStatus, errorThrown) {
							// 把错误吃了
							alert("保存失败");
							form.unmask();
						};
						requestAjax(url, {
							id : mini.get("id").getValue()
						}, successCallback, errotCallback);

					}
				});
		}
	</script>
</body>
</html>

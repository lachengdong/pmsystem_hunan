<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>" />
<title>用户选择器</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=path%>/bootstrap/js/bootstrap.min.js"></script>

<link href="<%=path%>/bootstrap/css/bootstrap.min.css" type="text/css"
	rel="stylesheet" />
<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.mini-pager-num {
	top: 2px;
	line-height: 15px;
}
</style>
</head>
<body style="padding:0px;overflow:hidden;">
	<div class="mini-layout"
		style="width:100%;height:100%;padding:0px;overflow:hidden;position:absolute;">
		<div size="300" maxSize="1024" minSize="150" showHeader="false"
			region="west"
			style="width:300px;height:100%;margin-bottom:10px;border:0px;margin-left:3px"
			width="200px">
			<div class="mini-toolbar"
				style="padding:2px;border-top:0;border-left:0;border-right:0;">
				<table>
					<tr>
						<td style="white-space:nowrap;"><span
							style="padding-left:5px;">名称：</span></td>
						<td><input class="mini-textbox" vtype="maxLength:30;"
							id="keyorg" style="width:80px;" onenter="onKeyEnter" /></td>
						<td style="white-space:nowrap;"><a class="mini-button"
							iconCls="icon-search" plain="true" onclick="searchOrg()">查找</a></td>

					</tr>
				</table>
			</div>
			<!--Tree-->
			<div class="mini-fit">
				<ul id="tree1" class="mini-tree" style="width:100%;height:100%;"
					url="<%=path%>/org/ajax/list.action?1=1" showTreeIcon="true"
					expandOnLoad="0" resultAsTree="false" textField="name"
					idField="orgid" parentField="porgid"
					onnodeselect="onTreeNodeSelect" onload="selectPrevNode">
				</ul>
			</div>
		</div>
		<!-- 右边的显示区域 -->
		<div showCollapseButton="false"
			style="overflow:hide;padding:0px;border:0px;"
			borderStyle="border:0px;overflow:hidden;" region="center">
			<div class="mini-toolbar" style="padding:2px;border:0;">
				<span>姓名/帐号：</span> <input class="mini-textbox" id="key"
					onenter="onKeyEnter2" /> <a class="mini-button"
					iconCls="icon-search" onclick="search()" plain="true">查找</a> <input
					name="selectedUser" id="selectedUser" class="mini-hidden" /> <a
					class="btn btn-primary"
					style="padding:3px 10px; width:60px;margin-left:50px;margin-top:1px;"
					onclick="onOk();">确定</a>

			</div>
			<div class="mini-fit">
				<div id="datagrid1" class="mini-datagrid" idField="id"
					sortField="userid" sortOrder="asc" allowResize="false"
					onload="mygridload" pageSize="19" allowCellEdit="true"
					allowCellSelect="true" multiSelect="true" allowMoveColumn="false"
					onselect="myselect" ondeselect="mydeselect"
					style="width:100%;height:100%;overflow: hidden;">
					<div property="columns">
						<div type="checkcolumn" width="10"></div>
						<div field="userid" width="40" headerAlign="center"
							allowSort="true" align="center">帐号</div>
						<div field="name" width="30" headerAlign="center"
							vtype="maxLength:10" allowSort="true" align="left">姓名</div>
						<div field="orgname" width="30" headerAlign="center"
							allowSort="true" align="left" sortField="orgid">单位</div>
						<div field="duty" width="30" headerAlign="center" allowSort="true"
							align="left">职务</div>
					</div>
				</div>

			</div>

		</div>

	</div>

	<script type="text/javascript">
		mini.parse();
		var tree = mini.get("tree1");
		// 获取grig对象		
		var grid = mini.get("datagrid1");
		selectedUsers = {};
		allowAppend = true;

		grid.setUrl("${path}/user/getUserinfos.action");

		function SetData(data) {
			data = mini.clone(data);
			mini.get("selectedUser").setValue(data.data.userids);
			selectedUsers = data.data;
			//alert('ddd'+selectedUsers.userids);

			allowAppend = false;
			grid.deselectAll(false);
			var rowcount = grid.data.length;
			var userids = selectedUsers.userids.split(';');
			for (var i = 0; i < userids.length; i++) {
				for (var j = 0; j < rowcount; j++) {
					var row = grid.getRow(j);
					if (row.userid == userids[i]) {
						grid.setSelected(row, false);

						break;
					}
				}
			}
			allowAppend = true;

		}

		function mygridload(e) {
			allowAppend = false;
			var rowcount = grid.data.length;
			var userids = selectedUsers.userids.split(';');
			for (var i = 0; i < userids.length; i++) {
				for (var j = 0; j < rowcount; j++) {
					var row = grid.getRow(j);
					if (row.userid == userids[i]) {
						grid.setSelected(row, false);
						break;
					}
				}
			}
			allowAppend = true;
		}

		function myselect(obj) {
			if (allowAppend) {
				selectedUsers.userids = selectedUsers.userids
						+ obj.record.userid + ";";
				selectedUsers.userNames = selectedUsers.userNames
						+ obj.record.name + ";";
			}
		}

		function mydeselect(obj) {
			if (allowAppend) {
				selectedUsers.userids = selectedUsers.userids.replace(
						obj.record.userid + ";", "");
				selectedUsers.userNames = selectedUsers.userNames.replace(
						obj.record.name + ";", "");
			}
		}

		//关闭窗口
		function CloseWindow(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}
		function onCancel() {
			CloseWindow();
		}

		function onOk() {
			//CloseWindow(selectedUsers);
			parent.setSendUser(selectedUsers);
		}

		function selectPrevNode() {
			var doSelect = function() {
				var tree = mini.get("tree1");
				node = tree.getRootNode() || {};
				node = node["children"] || [];
				node = node[0];
				if (!node) {
					return;
				}
				// 展开
				expand(tree, node);
				tree.selectNode(node);
			};
			window.setTimeout(doSelect, 100);
		};

		// 展开树
		function expand(tree, node) {
			if (!node || !tree || !node.orgid) {
				return;
			}
			var porgid = node.porgid;
			if (porgid && porgid != "0" && porgid != "-1") {
				var pnode = tree.getNode(node.porgid);
				expand(tree, pnode);
			}
			tree.expandNode(node);
		};

		// 选择树节点时,默认是查看信息
		function onTreeNodeSelect(e) {
			var node = e.node;
			if (null == node) {
				e.cancel = true;
				return false;
			} else {
				//
				window["userm_org"] = node;
			}
			var isLeaf = e.isLeaf;
			if (isLeaf) {
			}
			var orgid = node.orgid;
			var islocalnew = node.islocalnew;
			if (orgid && !islocalnew) {
				// 保存本地 cookie
				document.cookie = "userm_orgid=" + escape(orgid);
			}
			showDataToGrid(node);
			e.cancel = true;
			return false;
		};
		// 将数据显示(要显示的节点,父节点)
		function showDataToGrid(node, pnode) {
			if (!node) {
				return;
			}

			var param = window["param"] || {};
			param["orgid"] = node.orgid;
			param["orgname"] = node.name;
			param["pageIndex"] = 0;
			param["total"] = -1;
			param["sortField"] = "userid";
			param["sortOrder"] = "desc";
			window["param"] = param;
			// 加载数据
			grid.load(param, function() {
				param["total"] = grid.totalCount;
				//alert(grid.totalCount);
			});
			//	
		};

		//过滤树
		function searchOrg() {
			var key = mini.get("keyorg").getValue();
			if (key == "") {
				tree.clearFilter();
			} else {
				key = key.toLowerCase();
				tree.filter(function(node) {
					var text = node.name ? node.name.toLowerCase() : "";
					if (text.indexOf(key) != -1) {
						return true;
					}
				});
			}
		};
		function onKeyEnter(e) {
			searchOrg();
		};

		function onKeyEnter2(e) {
			search();
		};

		// 渲染性别
		function onGenderRenderer(e) {
			var ISMENU_TEXT = [ {
				id : 1,
				text : '男'
			}, {
				id : 2,
				text : '女'
			} ];
			for (var i = 0, l = ISMENU_TEXT.length; i < l; i++) {
				var g = ISMENU_TEXT[i];
				if (g.id == e.value)
					return g.text;
			}
			return "男";
		};

		// 渲染日期
		function onDateRenderer(e) {
			if (e && e.value) {
				return mini.formatDate(new Date(e.value), "yyyy-MM-dd");
			}
			return "";
		};

		function search() {
			var key = mini.get("key").getValue();
			//console.dir(grid);
			var param = window["param"] || {};
			param["key"] = key;
			param["pageIndex"] = 0;
			window["param"] = param;
			param["total"] = -1;
			// 加载数据
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		};
	</script>
</body>
</html>

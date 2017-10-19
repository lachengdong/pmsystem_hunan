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
<title>共享目录设置</title>
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
label {
	font-weight: normal;
	margin-right: 2px;
	margin-left: 5px;
}

span.required {
	color: red;
}

.mini-pager-num {
	top: 2px;
	line-height: 15px;
}

</style>
</head>
<body style="padding:0px;overflow:hidden;">
	<div class="mini-layout"
		style="width:100%;height:524px;padding:0px;overflow:hidden;margin:0px;">
		<div height="130" region="north" style="border:0px"
			showCollapseButton="false" showHeader="false">
			<div id="frmsharedfolder">
				<table style="width:100%;">
					<tr>
						<td colspan="6" style="align:right;">
							<div class="mini-toolbar"
								style="padding:2px;border:0;height:33px;">
								<a class="btn btn-primary btn-sm"
									style="padding:3px 10px; width:60px;margin-right:5px;margin-top:1px;float:right;tabIndex:-1;"
									href="javascript:doSave();">保存</a> <a
									class="btn btn-default btn-sm"
									style="padding:3px 10px; width:60px;margin-right:5px;margin-top:1px;float:right"
									href="javascript:doCancle();">取消</a> <a
									class="btn btn-danger btn-sm"
									style="padding:3px 10px; width:60px;margin-right:5px;margin-top:1px;float:right"
									href="javascript:doDelete();">删除</a>

							</div>
						</td>
					</tr>
					<tr style="height:30px;">
						<td style="white-space:nowrap;"><label
							class="control-label required" for="folderName">共享目录 <span
								class="required">*</span> <input name="folderId"
								id="hidfolderId" class="mini-hidden" />
						</label></td>
						<td><input id="txtfolderName" type="text" readonly="true"
							style="width:100px;" /></td>
						<td style="white-space:nowrap;"><label
							class="control-label required" for="dtstartTime">开始时间 <span
								class="required">*</span>
						</label></td>
						<td><input id="dtstartTime" class="mini-datepicker"
							name="startTime" style="width:200px;" nullValue="null"
							format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss"
							inputStyle="background-color:white;" showTime="true"
							showClearButton="false" required="true" /></td>
						<td style="white-space:nowrap;"><label
							class="control-label required" for="dtendTime">结束时间 <span
								class="required">*</span></label></td>
						<td><input id="dtendTime" class="mini-datepicker"
							name="endTime" style="width:200px;background-color:white;"
							nullValue="null" format="yyyy-MM-dd HH:mm:ss"
							timeFormat="HH:mm:ss" inputStyle="background-color:white;"
							showTime="true" showClearButton="false" required="true" /></td>
					</tr>
					<tr>
						<td><label class="control-label required"
							for="txtsharedpersons">共享范围<span class="required">*</span></label></td>
						<td colspan="5"><textarea class="mini-textarea"
								id="txtsharedpersons" name="sharednames" readOnly="readOnly"
								style="margin-bottom:0;font-size:12px;width:580px;height:40px"
								inputStyle="background-color:white;" emptyText="请选择共享人"
								required="true" requiredErrorText="请选择共享人"></textarea> <a
							style="margin-left:4px;vertical-align:bottom;padding:2px 5px;"
							href="javascript:clearsets();" class="btn btn-default btn-sm">清空</a></td>
					</tr>
				</table>
			</div>
			<div style="margin-top:10px;margin-left:5px;">
				<span>共享范围选择：</span>
			</div>
		</div>

		<!-- 右边的显示区域 -->
		<div showCollapseButton="false" style="overflow:hide;border:0px;margin-left:1px;"
			borderStyle="border:0px;overflow:hidden;" region="center" >
			<div class="mini-splitter mini-fit"
				style="width:100%;height:100%;padding:0px;background-color:control;">
				<div size="200" style="padding:0px">
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
						<ul id="tree1" class="mini-tree" style="width:100%;height:100%"
							url="<%=path%>/org/ajax/list.action?1=1" showTreeIcon="true"
							expandOnLoad="0" resultAsTree="false" textField="name"
							idField="orgid" parentField="porgid"
							onnodeselect="onTreeNodeSelect" onload="selectPrevNode">
						</ul>
					</div>
				</div>
				<div style="padding:0px;width:100%">
					<div class="mini-toolbar" style="padding:2px;border:0;">
						<span>姓名/帐号：</span> <input class="mini-textbox" id="key"
							onenter="onKeyEnter2" /> <a class="mini-button"
							iconCls="icon-search" onclick="search()" plain="true">查找</a>
					</div>
					<div class="mini-fit" style="border:0px;">
						<div id="datagrid1" class="mini-datagrid" idField="id"
							sortField="userid" sortOrder="asc" allowResize="false"
							onload="mygridload" pageSize="13" allowCellEdit="true"
							allowCellSelect="false" multiSelect="true"
							onlyCheckSelection="true" allowMoveColumn="false"
							onselect="myselect" ondeselect="mydeselect"
							borderStyle="border:0px;"
							style="width:100%; height:100%;overflow: hidden;border:0px">
							<div property="columns">
								<div type="checkcolumn" width="20px" cellStyle="cursor:pointer;"></div>
								<div field="int2" width="40px" headerAlign="center"
									align="center" name="colOperation" allowSort="false"
									renderer="renderOperation">可否编辑</div>
								<div field="userid" width="10%" headerAlign="center"
									allowSort="true" align="center">帐号</div>
								<div field="name" width="15%" headerAlign="center"
									vtype="maxLength:10" allowSort="true" align="left">姓名</div>
								<div field="organization.name" width="40%" headerAlign="center"
									allowSort="true" align="left">单位</div>
								<div field="duty" width="15%" headerAlign="center"
									allowSort="true" align="left">职务</div>

							</div>
						</div>
					</div>
				</div>


			</div>

		</div>
	</div>

	<script type="text/javascript">
		mini.parse();
		selectedUsers = [];
		currentFolder = 0;
		allowAppend = true;
		usernames = "";

		//“可否编辑”栏位渲染
		function renderOperation(e) {
			//var grid = mini.get("datagrid1");
			var recode = e.record;
			//var rowIndex = grid.indexOf(recode);
			var s = '<div data-original-title="" title="点击设置编辑权限" action-type="" style="cursor:pointer;width:100%;position:relative;height:100%;" rel="tooltip" onclick="javascript:setEditPriv(\''
					+ recode.userid
					+ '\');" id="div'
					+ recode.userid
					+ '">&nbsp;</div>';

			var sharefolder = getsharedfolder(recode.userid);

			if (sharefolder && sharefolder.isEditable == 1) {
				s = '<div data-original-title="" title="点击设置编辑权限" action-type="" style="cursor:pointer;width:100%;position:relative;height:100%;" rel="tooltip" onclick="javascript:setEditPriv(\''
						+ recode.userid
						+ '\');" id="div'
						+ recode.userid
						+ '">√</div>';
			}
			//alert(sharefolder);
			return s;
		}
		//设置“可否编辑”栏位值
		function setEditPriv(uid) {
			var sharefolder = getsharedfolder(uid);
			if (sharefolder) {
				var txt = $("#div" + uid).text();
				if (txt == '√') {
					$("#div" + uid).html("&nbsp;");
					sharefolder.isEditable = 0;
				} else {
					$("#div" + uid).text('√');
					sharefolder.isEditable = 1;
				}
			}
		}

		function getsharedfolder(uid) {
			var result = null;
			$.each(selectedUsers, function(i, n) {
				if (n.iuid == uid) {
					result = n;
					return result;
				}
			});
			return result;
		}

		//清空共享范围
		function clearsets() {
			var grid = mini.get("datagrid1");
			grid.deselectAll(false);
		}

		//保存共享目录信息
		function doSave() {
			var form = new mini.Form("#frmsharedfolder");
			var dts = mini.get("dtstartTime").getValue();
			var dte = mini.get("dtendTime").getValue();
			if (dts > dte) {
				mini.alert("开始日期不能大于结束日期！");
				return;
			}
			// 校验
			form.validate();
			if (form.isValid() == false) {
				return;
			}
			var data = form.getData();
			data.items = selectedUsers;
			// 界面
			form.loading("保存中，请稍候......");
			var url = "${path}/file/ajax/setsharedfolderInfo.json?action=edit";
			var successCallback = function(message) {
				form.unmask();
				var folderId = mini.get("hidfolderId").getValue();
				//alert(folderId);
				parent.refreshfolderTree2(true, folderId);
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

		function doCancle() {
			parent.refreshfolderTree2(false, null);
		}
		//删除共享目录
		function doDelete() {
			var form = new mini.Form("#frmsharedfolder");
			var folderId = mini.get("hidfolderId").getValue();
			//		
			form.loading("删除中，请稍候......");
			var url = "${path}/file/ajax/setsharedfolderInfo.json?action=remove";
			var successCallback = function(message) {
				form.unmask();
				parent.refreshfolderTree2(true, folderId);
				return false;
			};
			//
			var errotCallback = function(jqXHR, textStatus, errorThrown) {
				// 把错误吃了
				alert("保存失败");
				form.unmask();
			};
			requestAjax(url, {
				folderId : folderId
			}, successCallback, errotCallback);

		}

		//页面初始化
		function SetData(data) {
			data = mini.clone(data);
			currentFolder = data.folderId;
			mini.get("hidfolderId").setValue(currentFolder);
			$("#txtfolderName").val(data.folderName);

			var grid = mini.get("datagrid1");
			// 界面
			grid.loading("数据加载...");
			var url = "${path}/file/getSharedfolderInfos.action";
			var successCallback = function(message) {
				grid.unmask();
				if (message == '') {
					grid.deselectAll(false);
					mini.get("dtstartTime").setValue(new Date());
					mini.get("dtendTime").setValue(null);
					mini.get("txtsharedpersons").setValue('');
					selectedUsers = [];
					//return;
				} else {
					selectedUsers = mini.decode(message);
				}
				allowAppend = false;
				grid.deselectAll(false);

				var users = selectedUsers;
				if (!users) {
					return;
				}
				if (users.length > 0) {
					mini.get("dtstartTime").setValue(users[0].startTime);
					mini.get("dtendTime").setValue(users[0].endTime);
				}
				setValueOfshares();
				var rows = grid.findRows(function() {
					return true;
				});
				//强制刷新grid，不再上服务器端获取数据
				if (rows) {
					grid.clearRows();
					grid.addRows(rows);
				}
				var rowcount = grid.data.length;
				for (var i = 0; i < users.length; i++) {
					for (var j = 0; j < rowcount; j++) {
						var row = grid.getRow(j);
						if (row.userid == users[i].iuid) {
							grid.setSelected(row, false);
							break;
						}
					}
				}
				allowAppend = true;
				return true;
			};
			//
			var errotCallback = function(jqXHR, textStatus, errorThrown) { // 把错误吃了
				grid.unmask();
				mini.alert("共享信息加载失败");
			};
			requestAjax(url, {
				id : currentFolder
			}, successCallback, errotCallback);
		}

		function mygridload(e) {
			var grid = mini.get("datagrid1");
			allowAppend = false;
			var rowcount = grid.data.length;
			var users = selectedUsers;
			for (var i = 0; i < users.length; i++) {
				for (var j = 0; j < rowcount; j++) {
					var row = grid.getRow(j);
					if (row.userid == users[i].iuid) {
						grid.setSelected(row, true);
						break;
					}
				}
			}
			allowAppend = true;
		}

		function myselect(obj) {
			var userid = obj.record.userid;
			if (allowAppend) {
				for (var i = 0; i < selectedUsers.length; i++) {
					if (selectedUsers[i].iuid == userid) {
						return;
					}
				}
				var shareitem = {};
				shareitem.iuid = userid;
				shareitem.folderId = currentFolder;
				shareitem.username = obj.record.name;
				selectedUsers.push(shareitem);
				setValueOfshares();
			}
		}
		function mydeselect(obj) {
			var userid = obj.record.userid;
			if (allowAppend) {
				$.each(selectedUsers, function(i, n) {
					if (n.iuid == userid) {
						$("#div" + userid).html("&nbsp;");
						selectedUsers = arrayDel(selectedUsers, i);
						return false;
					}
				});
				setValueOfshares();
			}
		}

		function setValueOfshares() {
			var names = "";
			$.each(selectedUsers, function(i, n) {
				names = names + n.username + ";";
			});
			mini.get("txtsharedpersons").setValue(names);
		}

		function arrayDel(a, n) {
			if (n < 0)
				return a;
			else
				return a.slice(0, n).concat(a.slice(n + 1, a.length));
		}

		function onOk() {
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
			var grid = mini.get("datagrid1");
			grid.setUrl("${path}/user/getUserinfos.action");
			if (!node) {
				return;
			}
			var param = window["param"] || {};
			param["orgid"] = node.orgid;
			param["orgname"] = node.name;
			param["pageIndex"] = 0;
			window["param"] = param;
			// 加载数据
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		};

		//过滤树
		function searchOrg() {
			var tree = mini.get("tree1");
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

		function search() {
			var grid = mini.get("datagrid1");
			var key = mini.get("key").getValue();
			//console.dir(grid);
			var param = window["param"] || {};
			param["key"] = key;
			param["pageIndex"] = 0;
			window["param"] = param;
			param["total"] = 0;
			// 加载数据
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		};
	</script>
</body>
</html>

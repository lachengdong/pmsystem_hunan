<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<title>流程按钮绑定</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
	rel="stylesheet" type="text/css" />
<style type="text/css">
.actionIcons span {
	width: 16px;
	height: 16px;
	display: inline-block;
	background-position: 50% 50%;
	cursor: pointer;
}
</style>
</head>
<body>
	<!--Layout-->
	<div class="mini-layout" style="width:100%;height:100%;">
		<div title="center" region="center">
			<!--Splitter-->
			<div class="mini-splitter" style="width:100%;height:100%;"
				borderStyle="border:0;">
				<!-- 左边的资源树 -->
				<div size="200" maxSize="1024" minSize="150"
					showCollapseButton="true">
					<div class="mini-toolbar"
						style="padding:2px;border-top:0;border-left:0;border-right:0;">
						<span style="padding-left:5px;">名称：</span> <input
							class="mini-textbox" vtype="maxLength:30;" id="key"
							style="width:80px;" onenter="onKeyEnter" /> <a
							class="mini-button" iconCls="icon-search" plain="true"
							onclick="search()">查找</a>
					</div>
					<div class="mini-fit">
						<!--Tree-->
						<ul id="tree1" class="mini-tree" style="width:100%;height:100%;"
							url="<%=path%>/resource/ajax/listAllMenuWithFlowBtun.json?1=1"
							showTreeIcon="true" resultAsTree="false" textField="resname"
							idField="resid" parentField="presid" contextMenu="#treeMenu"
							onnodeselect="onTreeNodeSelect">
						</ul>
					</div>
				</div>
				<!-- 右边的显示区域 -->
				<div showCollapseButton="false" style="overflow: auto"
					borderStyle="border:solid 1px #aaa;">
					<div region="north" height="40" showSplit="false"
						showHeader="false" showCollapseButton="false"></div>
					<div class="mini-toolbar"
						style="padding:2px;border:0;border-bottom:1px solid #99bce8;">
						<table style="width:100%;">
							<tr>
								<td style="width:100%;" align="right"><a
									class="mini-button" iconCls="icon-add" onclick="onAddNode()"
									plain="true">新增资源子节点</a> <a class="mini-button"
									iconCls="icon-reload" onclick="refreshPage()" plain="true">刷新</a>

									<a class="mini-button" style="width:60px;" onclick="onCancel()" plain="true">取消
									</a> 
									<a class="mini-button"
									style="width:60px;" onclick="saveData()" plain="true"
									iconCls="icon-save">确定
									</a>
									</td>
								<td style="white-space:nowrap;"></td>
							</tr>
						</table>
					</div>
					<div class="mini-fit" showCollapseButton="false">
						<div id="editform" class="form" style="margin-left: 10px;">
							<table class="form-table" border="0" cellpadding="1"
								cellspacing="2" style="width:100%;">
								<tr>
									<td style="width:90px;text-align:left">动作参数：</td>
									<td colspan="5" style="padding-right:25px"><input
										id="txtParam" class="mini-textbox" style="width:200px;"
										onvaluechanged="inputParam" required="true" /></td>
								</tr>
								<tr>
									<td style="width:90px;">对&nbsp;应&nbsp;动&nbsp;作：</td>
									<td colspan="5"><input name="srurl" class="mini-textbox"
										style="width:95%;" required="true" allowInput="true"
										id="srurl" /></td>
								</tr>
								<tr>
									<td style="width:90px;">资源编号：</td>
									<td><input name="resid" class="mini-textbox"
										style="width:120px;" vtype="maxLength:12" required="true" />
										<input name="presid" class="mini-hidden" /> <input
										id="islocalnew" name="islocalnew" class="mini-hidden" /></td>
									<td style="width:90px;">显示列数：</td>
									<td><input name="rct" class="mini-textbox"
										style="width:120px;" vtype="int;maxLength:5" /></td>
									<td style="width:90px;">打开图标：</td>
									<td><input name="openico" class="mini-textbox"
										style="width:120px;" /></td>
								</tr>
								<tr>
									<td style="width:90px;">资源名称：</td>
									<td><input name="name" class="mini-textbox" id="rsname"
										selectOnFocus="true" style="width:120px;" vtype="maxLength:32"
										required="true" onvaluechanged="setTreeNodeText" /></td>
									<td style="width:90px;">显示位置：</td>
									<td><input name="showsite" class="mini-textbox"
										style="width:120px;" /></td>
									<td style="width:90px;">关闭图标：</td>
									<td><input name="closeico" class="mini-textbox"
										style="width:120px;" /></td>
								</tr>
								<tr>
									<td style="width:90px;">资源类型：</td>
									<td><input name="restypeid" class="mini-combobox"
										style="width:120px;" textField="name" valueField="restypeid"
										data="resTypes" /></td>
									<td style="width:90px;">显示图片：</td>
									<td>
										<div name="showico" id="showico" class="mini-combobox"
											style="width:120px;" popupWidth="200" textField="id"
											valueField="id" data="iconList" multiSelect=false
											showClose="false" oncloseclick="onCloseClick">
											<div property="columns">
												<div cellCls="actionIcons" name="action" width="20px"
													headerAlign="center" align="center"
													renderer="onActionRenderer" cellStyle="padding:10;"></div>
												<div header="ID" field="id"></div>
											</div>
										</div>
									</td>
									<td style="width:90px;">创建者ID：</td>
									<td><input name="opid" class="mini-textbox"
										style="width:120px;" /></td>
								</tr>
								<tr>
									<td style="width:90px;">资源排序：</td>
									<td><input name="sn" class="mini-textbox"
										style="width:120px;" vtype="int;maxLength:5" required="true" />
									</td>
									<td style="width:90px;">附加信息：</td>
									<td colspan="3"><input name="text1" class="mini-textbox"
										style="width:120px;" /></td>
									<!--  
									<td style="width:90px;">创建时间：</td>
									<td><input name="optime" 
										style="width:120px;" valuechanged="valuechanged" /></td>-->
								</tr>
								<tr>
									<td style="width:90px;">是否可视：</td>
									<td><input name="isvisible" class="mini-textbox"
										style="width:120px;" /></td>
									<td style="width:90px;">提示信息：</td>
									<td><input name="title" class="mini-textbox"
										style="width:120px;" /></td>
									<td style="width:90px;">右下角X坐标：</td>
									<td><input name="x2" class="mini-textbox"
										style="width:120px;" vtype="int;maxLength:5" /></td>
								</tr>
								<tr>
									<td style="width:90px;">弹出框高度：</td>
									<td><input name="windowhight" class="mini-textbox"
										style="width:120px;" vtype="int;maxLength:5" /></td>
									<td style="width:90px;">左上角X坐标：</td>
									<td><input name="x1" class="mini-textbox"
										style="width:120px;" vtype="int;maxLength:5" /></td>
									<td style="width:90px;">左上角Y坐标：</td>
									<td><input name="y1" class="mini-textbox"
										style="width:120px;" vtype="int;maxLength:5" /></td>
								</tr>
								<tr>
									<td style="width:90px;">弹出框宽度：</td>
									<td><input name="windowwidth" class="mini-textbox"
										style="width:120px;" vtype="int;maxLength:5" /></td>
									<td style="width:90px;">右下角Y坐标：</td>
									<td><input name="y2" class="mini-textbox"
										style="width:120px;" vtype="int;maxLength:5" /></td>
									<td style="width:90px;">查询提示信息：</td>
									<td><input name="prompt" class="mini-textbox"
										style="width:120px;" /></td>
								</tr>
								<tr>
									<td style="width:90px;">标签页类型：</td>
									<td><input name="ismenu" class="mini-combobox"
										style="width:120px;" textField="text" valueField="id"
										data="ismenuarray" /></td>
									<td style="width:90px;">表&nbsp;单&nbsp;&nbsp;ID：</td>
									<td><input name="formid" class="mini-textbox"
										style="width:120px;" vtype="maxLength:20" /></td>
									<td style="width:90px;">查询方案ID：</td>
									<td><input name="querysql" class="mini-textbox"
										style="width:120px;" vtype="maxLength:50" /></td>
								</tr>

								<tr>
									<td style="width:90px;">备&nbsp;注&nbsp;信&nbsp;息：</td>
									<td colspan="5"><textarea name="remark"
											class="mini-textarea" style="width:85%;height:80px"></textarea>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="hide">
		<!-- 资源树,右键菜单 -->
		<ul id="treeMenu" class="mini-contextmenu"
			onbeforeopen="onBeforeOpenTreeMenu">
			<li name="add" iconCls="icon-add" onclick="onAddNode">新增资源子节点</li>
			<li class="separator"></li>
			<li name="view" iconCls="icon-node" onclick="onViewNode">查看资源信息</li>
			<li class="separator"></li>
			<li name="edit" iconCls="icon-edit" onclick="onEditNode">修改资源信息</li>
			<li class="separator"></li>
			<li class="separator"></li>
			<li name="remove" iconCls="icon-remove" onclick="onRemoveNode">删除节点</li>
		</ul>
	</div>
	<script type="text/javascript">
		// TODO 需要在页面打开时到数据库中加载.			
		var ismenuarray = [ {
			id : "0",
			text : "未定义"
		}, {
			id : "1",
			text : "普通页面"
		}, {
			id : "2",
			text : "子标签页"
		}, {
			id : "3",
			text : "多标签页"
		} ];

		var iconList = [ {
			id : "icon-add"
		}, {
			id : "icon-addnew"
		}, {
			id : "icon-edit"
		}, {
			id : "icon-remove"
		}, {
			id : "icon-save"
		}, {
			id : "icon-close"
		}, {
			id : "icon-cut"
		}, {
			id : "icon-ok"
		}, {
			id : "icon-no"
		}, {
			id : "icon-cancel"
		}, {
			id : "icon-reload"
		}, {
			id : "icon-search"
		}, {
			id : "icon-print"
		}, {
			id : "icon-help"
		}, {
			id : "icon-undo"
		}, {
			id : "icon-redo"
		}, {
			id : "icon-tip"
		}, {
			id : "icon-zoomin"
		}, {
			id : "icon-zoomout"
		}, {
			id : "icon-goto"
		}, {
			id : "icon-date"
		}, {
			id : "icon-filter"
		}, {
			id : "icon-find"
		}, {
			id : "icon-folder"
		}, {
			id : "icon-folderopen"
		}, {
			id : "icon-lock"
		}, {
			id : "icon-unlock"
		}, {
			id : "icon-new"
		}, {
			id : "icon-node"
		}, {
			id : "icon-nowait"
		}, {
			id : "icon-sort"
		}, {
			id : "icon-wait"
		}, {
			id : "icon-upgrade"
		}, {
			id : "icon-downgrade"
		}, {
			id : "icon-sets"
		}, {
			id : "icon-download"
		}, {
			id : "icon-upload"
		}, {
			id : "icon-user"
		}, {
			id : "icon-split"
		}, {
			id : "icon-addfolder"
		}, {
			id : "icon-expand"
		}, {
			id : "icon-collapse"
		}, {
			id : "icon-gk_bjsj"
		}, {
			id : "icon-gk_forward"
		}, {
			id : "icon-gk_fullscream"
		}, {
			id : "icon-gk_gz"
		}, {
			id : "icon-gk_next"
		}, {
			id : "icon-gk_open"
		}, {
			id : "icon-gk_page"
		}, {
			id : "icon-gk_plgz"
		}, {
			id : "icon-gk_print"
		}, {
			id : "icon-gk_qfz"
		}, {
			id : "icon-gk_save"
		}, {
			id : "icon-gk_sxqm"
		}, {
			id : "icon-gk_user"
		}, {
			id : "icon-gk_zw"
		}, {
			id : "icon-gk_xpc"
		}, {
			id : "icon-gk_cx"
		}, {
			id : "icon-gk_help"
		}, {
			id : "icon-gk_continue"
		}, {
			id : "icon-gk_search"
		} ];
		var resTypes = [ {
			restypeid : "8",
			name : "流程按钮"
		} ];

		mini.parse();

		// 当前选中的节点
		var curNode = null;
		myparameter = "";
		lineType = "";
		function inputParam(e) {
			setResUrl();
		}

		function setTreeNodeText(e) {
			var text = mini.get("rsname").getValue();
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			if (!node)
				return;
			tree.setNodeText(node, text);
			node.name = text;
		}
		// 如果在 onload 之前需要调用,则执行此方法
		function SetData(data) {
			data = mini.clone(data);
			var tree = mini.get("tree1");

			tree.selectNode(data.resid);
			tree.expandPath(data.resid);

			//selectPrevNode(data.resid);			
			myparameter = data.parameter;
			lineType = data.type;
			var parm = mini.get("txtParam");
			parm.setValue(data.parameter);
			setResUrl();
			parm.focus();
		}

		function setResUrl() {
			var srurl = mini.get("srurl");
			var parmeter = mini.get("txtParam").getValue().replace("'", "\'");
			//parm.blur();
			var url = srurl.getValue();
			if (lineType != "start") {
				if (url != '') {
					re = /Execute\((\'[^\']*\')(,\'.*\')?\)/ig;
					url.match(re);
					url = url.replace(RegExp.$1, '\'' + parmeter + '\'');
					srurl.setValue(url);
				} else {
					srurl.setValue("Execute('" + parmeter + "')");
				}

			} else {//发起流程
				srurl.setValue("DoSaveDraft('" + parmeter + "')");
			}
			//parm.focus();
		}

		// 获取选中节点
		function GetData() {
			var tree = mini.get("tree1");
			var parm = mini.get("txtParam");
			var node = tree.getSelectedNode();
			var data = {
				resid : node.resid,
				residName : node.resname,
				resParam : parm.getValue().replace("'", "\'")
			};
			return data;
		};

		//////////////////////////////////
		function CloseWindow(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		};

		function onCancel() {	
			parent.onbuttonclick();
		};

		// 保存数据
		function saveData() {
			var form = new mini.Form("#editform");
			// 校验
			form.validate();
			if (form.isValid() == false) {
				return;
			}
			//
			var data = form.getData();
			// 界面
			form.loading("保存中，请稍候......");
			//
			var url = "${path}/resource/ajax/update.action";

			var successCallback = function(message) {
				var status = message["status"];
				var info = message["info"];
				if (1 === status) {

					//CloseWindow("ok");
					var tree = mini.get("tree1");
					var parm = mini.get("txtParam");
					var rsname = mini.get("rsname");
					var node = tree.getSelectedNode();
					node.islocalnew = 0;
					parent.resetFlowBtn(node.resid, rsname.getValue(), parm
							.getValue().replace("'", "\'"));

					mini.get("islocalnew").setValue('0');

				} else {
					debug(info, true);
				}
				form.unmask();
				return false;
			};
			var errorCallback = function() {
				alert("保存失败");
				form.unmask();
				return false;
			};
			//
			requestAjax(url, data, successCallback, errorCallback);
		};

		// 选择树节点时,默认是查看信息
		function onTreeNodeSelect(e) {
			var node = e.node;
			// var isLeaf = e.isLeaf;
			if (!node) {
				return;
			}
			showResourceToForm(node);
			e.cancel = true;
			return false;
		};
		// 打开树的右键菜单前
		function onBeforeOpenTreeMenu(e) {
			var menu = e.sender;
			var tree = mini.get("tree1");
			// 取得点击的节点
			var node = tree.getSelectedNode();
			if (!node) {
				e.cancel = true;
				return;
			}
			// 某些特殊节点,屏蔽事件;
			if (node && node.text == "Base") {
				e.cancel = true;
				//阻止浏览器默认右键菜单
				e.htmlEvent.preventDefault();
				return;
			}
			////////////////////////////////
			// 对某些特殊的菜单执行隐藏,显示操作
			var editItem = mini.getbyName("edit", menu);
			var removeItem = mini.getbyName("remove", menu);
			// 设置为初始值
			editItem.show();
			removeItem.show();
			// 某些节点,不显示编辑菜单
			if (node.id == "forms") {
				editItem.hide();
			}
			// 某些节点,不显示删除菜单
			var isLeaf = e.isLeaf;
			var restypeid = node.restypeid;
			var typeScheme = 14;

			// 资源这里不允许编辑打印方案
			if (typeScheme == restypeid) {
				e.cancel = true;
				//阻止浏览器默认右键菜单
				e.htmlEvent.preventDefault();
				return;
			}

			if ("0" === node.presid || node.children || isLeaf) {
				//removeItem.enable();
				//removeItem.disable();
				removeItem.hide();
			}
		};
		// 查看资源节点
		function onViewNode(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			// 将节点数据带到右边的form中
			showResourceToForm(node);
			e.cancel = true;
			return false;
		};

		// 新增资源
		function onAddNode(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			if (!node) {
				alert("请先选中资源树中的节点");
				return false;
			}
			// 查询下一个资源ID
			// 执行AJAX请求			
			$
					.ajax({
						url : "${path}/resource/ajax/getnextid.action",
						data : {},
						type : "post",
						success : function(message) {
							if (window["JSON"]) {
								//message = JSON.parse(message);
							} else {
								// IE6, IE7
								message = eval("(" + message + ")");
							}

							var parm = mini.get("txtParam");
							//
							var info = message["info"];
							if (1 === message["status"] || true) {
								//
								var meta = message["meta"] || {};
								var nextid = meta["nextid"] || "";
								// 判断是否成功
								var newNode = {
									resid : '' + nextid,
									presid : node.resid,
									sn : "9999",
									srurl : "Execute('"
											+ parm.getValue()
													.replace("'", "\'") + "')",
									optime : new Date(),
									ismenu : 0,
									ismenuleaf : 1,
									isresourcesleaf : 0,
									restypeid : 8,
									name : "新增资源_" + nextid,
									islocalnew : 1
								};
								tree.addNode(newNode, "add", node);
								tree.selectNode(newNode);
								showResourceToForm(newNode, node);
								mini.get("rsname").focus();
							} else {
								debug(info, true);
							}
							return false;
						},
						error : function(jqXHR, textStatus, errorThrown) {
							// 把错误吃了
						}
					});
		};
		// 编辑资源
		function onEditNode(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			// 将节点数据带到右边的form中
			showResourceToForm(node);
		};
		// 删除
		function onRemoveNode(e) {
			var tree = mini.get("tree1");
			var node = tree.getSelectedNode();
			if (!node) {
				return;
			}
			//
			var pnode = tree.getParentNode(node);
			if (node.islocalnew) {
				tree.removeNode(node);
				if (pnode) {
					tree.selectNode(pnode);
				}
				return false;
			}
			if (confirm("确定删除选中资源?")) {
				var url = "${path}/resource/ajax/delete.action";

				var data = {
					resid : node.resid
				};
				var successCallback = function(message) {
					var status = message["status"];
					//var meta = message["meta"] || {};
					var info = message["info"];
					if (1 === status) {
						// 删除子节点
						tree.removeNode(node);
						// 选中父节点
						if (pnode) {
							tree.selectNode(pnode);
						}
					} else {
						alert(info);
					}
				};
				var errorCallback = function(message) {
					alert("删除失败");
				};
				// 执行AJAX
				requestAjax(url, data, successCallback, errorCallback);
			}
		};

		// 
		function debug(obj, force) {
			// chrome
			if (window["debugMode"] || force) {
				//
				alert(obj);
			} else if (window["console"]) {
				console.dir(obj);
			} else {
				// 不执行任何操作
			}
		};
		// 渲染日期
		function valuechanged(target, e) {
			debug(target);
			debug(e);
			if (e && e.value) {
				return mini
						.formatDate(new Date(e.value), "yyyy-MM-dd HH:mm:ss");
			}
			return "";
		};
		//
		// 将数据显示(要显示的节点,父节点)
		function showResourceToForm(node, pnode) {
			if (!node) {
				return;
			}
			var islocalnew = node.islocalnew;
			// 存储当前节点
			curNode = pnode || node;
			// 怎么带出来?
			var form = new mini.Form("#editform");
			//form.loading();
			// 设置数据
			form.setData(node, true);
			//
			var residInput = mini.getbyName("resid");
			var nameInput = mini.getbyName("name");
			var optime = mini.getbyName("optime");
			//
			var restypeidInput = mini.getbyName("restypeid");
			restypeidInput.setValue(node.restypeid);
			//console.dir(node);

			// 修改时不允许改变ID
			if (residInput) {
				if (!islocalnew) {
					residInput.allowInput = false;
				} else {
					residInput.allowInput = true;
					// 延迟
					window.setTimeout(function() {
						nameInput && nameInput.focus();
					}, 20);
				}
			}
			//
			if (optime) {
				var value = node.optime || "";
				if (/^\d+$/.test) {
					value = mini.formatDate(new Date(value),
							"yyyy-MM-dd HH:mm:ss");
				} else if (value) {
					value = mini.formatDate(value, "yyyy-MM-dd HH:mm:ss");
				}
				optime.setValue(value);
				optime.allowInput = false;
				//

			}

			form.setIsValid(true);
			form.setChanged(false);
			setResUrl();
			// 允许编辑
			form.unmask();
		};

		// 刷新本页面
		function refreshPage() {
			location.reload();
		};
		//

		//过滤树
		function search() {
			var tree = mini.get("tree1");
			var key = mini.get("key").getValue();
			if (key == "") {
				tree.clearFilter();
			} else {
				key = key.toLowerCase();
				tree.filter(function(node) {
					//var text = node.name?node.name.toLowerCase() : "";
					var name = node.resname || "";
					var id = node.resid || "";
					if (name.indexOf(key) > -1) {
						return true;
					} else if (id.indexOf(key) > -1) {
						return true;
					}
				});
			}
		}
		function onKeyEnter(e) {
			search();
		}
		function onActionRenderer(e) {
			var record = e.record;
			var s = '<span class="'+record.id+'"></span>';
			return s;
		}
	</script>
</body>
</html>
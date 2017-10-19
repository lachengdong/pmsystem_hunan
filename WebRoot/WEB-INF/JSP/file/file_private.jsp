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
<title>个人文件柜</title>
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

.filefoldpic {
	background: url(${path}/images/folder.gif) no-repeat;
}

.filefoldShared {
	background: url(${path}/images/folder_share.gif) no-repeat;
}

label {
	font-weight: normal;
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
				<div title="个人文件柜" name="tabprivate">
					<div class="mini-fit">
						<!--Tree-->
						<ul id="fileTree" class="mini-tree"
							style="width:100%;height:100%;" url="" showTreeIcon="false"
							resultAsTree="false" textField="folderName" idField="id"
							parentField="parentId" contextMenu="#treeMenu"
							onnodeselect="onTreeNodeSelect" ondrawnode="onDrawNode">
						</ul>
					</div>
				</div>
				<div title="共享文件柜" name="tabshared">
					<div class="mini-fit">
						<!--Tree-->
						<ul id="filesharedTree" class="mini-tree"
							style="width:100%;height:100%;" url="" showTreeIcon="false"
							resultAsTree="false" textField="folderName" idField="id"
							ondrawnode="onDrawNode2" parentField="parentId"
							onnodeselect="onTreeNodeSelect">
						</ul>
					</div>

				</div>
			</div>
			<div id="tabsButtons">
				<button type="button" class="btn btn-danger btn-sm" id="addsets"
					style="text-shadow: black 5px 3px 3px; padding:0px 5px;margin-top:3px;margin-right:10px;"
					onclick="addfilefolder();" title="添加文件夹">
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
									<span class="ellipsis" style="display:inline-block;width:18px;">
										<img src="${path}/images/folder_open.gif">
									</span> <span class="ellipsis folder_name" id="spnfolderPath"
										style="display:inline-block;width:auto;max-width: 600px;">

									</span>
									<div class="pull-right">
										<div id="yw2" class="pull-right" style="margin-top:4px">
											<input id="txtFileName" class="mini-textbox"
												emptyText="根据文件名搜索" onenter="onKeyEnter"
												style="margin-right:2px;font-size:12px;" /> <a
												class="mini-button" plain="true" iconCls="icon-search"
												plain="true" onclick="search()">查询</a>
										</div>
									</div>
								</div>
							</td>
							<td>
								<form class="form-horizontal mini-form" id="frmbatchdown"
									action="${path}/attachment/batchdownload.action" method="post">
									<input name="batchfiles" id="batchfiles" type="hidden"
										class="mini-hidden" />
								</form>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="toolbar" style="padding:3px 0px;padding-left:2px;">
				<BUTTON class="btn btn-primary btn-sm" style="margin-right:3px"
					id="btnUpload" onClick="showUpload()" id="yw3">
					<i class="icon-upload-6"></i> 上传文件
				</BUTTON>
				<!-- <BUTTON id="selectAll" style="margin-right:3px"
				class="btn btn-default btn-sm">
				<i class="icon-checkbox-unchecked"></i> 全选
			</BUTTON> -->
				<BUTTON class="btn btn-default btn-sm" style="margin-right:3px;"
					id="batchShare" onClick="setSharedFolder();">
					<i class="icon-share-2"></i> 分享
				</BUTTON>
				<BUTTON class="btn btn-default btn-sm" id="batchDownload"
					style="margin-right:3px;" onClick="batchDownload();">
					<i class="icon-download-2"></i> 批量下载
				</BUTTON>
				<BUTTON class="btn btn-default btn-sm" id="batchDel"
					style="margin-right:3px;" onClick="batchremovefile();">
					<i class="icon-remove-5"></i> 批量删除
				</BUTTON>
				<div class="btn-group" id="divmore">
					<BUTTON data-toggle="dropdown" class="btn btn-default btn-sm"
						id="yw4">
						更多 <span class="glyphicon glyphicon-chevron-down"></span>
					</BUTTON>
					<ul id="yw5" class="dropdown-menu">
						<li><a id="rename" class="disabled" tabindex="-1"
							href="javascript:onRename();">重命名</a></li>
						<li><a id="batch_move" tabindex="-1"
							href="javascript:onMove();">移动到</a></li>
						<!--  <li><a id="batch_copy" tabindex="-1" href="javascript:;">复制到</a></li>-->
					</ul>
				</div>
				<!-- 
				<div style="margin-left:0;" class="btn-group">
					<BUTTON data-toggle="dropdown"
						class="btn btn-default btn-sm dropdown-toggle" id="yw6" href="#"
						style="padding:4px 5px;">
						<i class="icon-menu-3"></i> 列表视图 <span
							class="glyphicon glyphicon-chevron-down"></span>
					</BUTTON>
					<ul id="yw7" class="dropdown-menu">
						<li><a tabindex="-1" href="#"><i class="icon-grid-5"></i>
								看板视图</a></li>
					</ul>
				</div>
				 -->
			</div>

			<div class="mini-fit">
				<div id="datagrid1" class="mini-datagrid" contextMenu="#gridMenu"
					style="width: 100%; height: 100%;border:0px"
					url="${path}/file/getPrivateFiles.action" allowAlternating="true"
					idField="fmfileid" sizeList="[10,20,50,100]" pageSize="14"
					multiSelect="true" borderStyle="border:0px;" showVGridLines="false">
					<div property="columns">
						<div type="checkcolumn" width="30px"></div>
						<div name="iconcl" type="" width="40px" headerAlign="center"
							headerStyle="border-right:0px" align="left" renderer="rendericon"></div>
						<div field="fileName" width="55%" headerAlign="center"
							sortField="FILE_NAME" headerStyle="border-right:0px" align="left"
							cellStyle="cursor: pointer;" allowSort="true">文件名</div>
						<div field="strSize" width="10%" headerAlign="center"
							sortField="FILE_SIZE" headerStyle="border-right:0px"
							align="right" allowSort="true">大小</div>
						<div field="updateTime" width="15%" headerAlign="center"
							sortField="UPDATE_TIME" allowSort="true"
							dateFormat="yyyy-MM-dd HH:mm:ss" headerStyle="border-right:0px"
							align="center">修改时间</div>
						<div field="" width="15%" headerAlign="center" align="center"
							name="colOperation" allowSort="false" renderer="renderOperation">操作</div>
					</div>
				</div>
				<div id="datagrid2" class="mini-datagrid" contextMenu="#gridMenu"
					style="width: 100%; height: 100%;border:0px;display:none;"
					allowAlternating="true" idField="fmfileid"
					url="${path}/file/getPrivateFiles.action" sizeList="[10,20,50,100]"
					pageSize="14" multiSelect="true" borderStyle="border:0px;"
					showVGridLines="false">
					<div property="columns">
						<div type="checkcolumn" width="30px"></div>
						<div name="iconcl" type="" width="40px" headerAlign="center"
							headerStyle="border-right:0px" align="left" renderer="rendericon"></div>
						<div field="fileName" width="55%" headerAlign="center"
							sortField="FILE_NAME" headerStyle="border-right:0px" align="left"
							cellStyle="cursor: pointer;" allowSort="true">文件名</div>
						<div field="strSize" width="10%" headerAlign="center"
							sortField="FILE_SIZE" headerStyle="border-right:0px"
							align="right" allowSort="true">大小</div>
						<div field="updateTime" width="15%" headerAlign="center"
							sortField="UPDATE_TIME" allowSort="true"
							dateFormat="yyyy-MM-dd HH:mm:ss" headerStyle="border-right:0px"
							align="center">修改时间</div>
						<div field="" width="15%" headerAlign="center" align="center"
							name="colOperation" allowSort="false" renderer="renderOperation2">操作</div>
					</div>
				</div>
			</div>

		</div>
	</div>

	<ul id="gridMenu" class="mini-contextmenu" onbeforeopen="onBeforeOpen"
		allowSelectItem="true">
		<li name="mdownload" iconCls="icon-download-2" onclick="ondownload"
			style="cursor: pointer;">下载</li>
		<li name="mread" iconCls="icon-reading" onclick="onRead"
			style="cursor: pointer;width:100%;">阅读</li>
		<li name="medit" iconCls="icon-pencil-5" onclick="onEdit"
			style="cursor: pointer;">编辑</li>
		<li class="separator" name="mseparator" id="mseparator"></li>
		<li name="mrename" iconCls="" onclick="onRename"
			style="cursor: pointer;">重命名</li>
		<li name="mmove" iconCls="icon-folder-remove" onclick="onMove"
			style="cursor: pointer;">移动到</li>
		<li name="mremove" iconCls="icon-remove-2" onclick="onRemove"
			style="cursor: pointer;">删除</li>
	</ul>

	<div id="addfolderwin" class="mini-window" title="新建子文件夹"
		style="width:600px;height:290px;border:0px;" showMaxButton="true"
		showCollapseButton="false" showShadow="false" showToolbar="false"
		showFooter="false" showModal="true" allowResize="false"
		showHeader="false" allowDrag="true" bodyStyle="padding:0px;"></div>
	<div id="uploadwin" class="mini-window" title="文件上传"
		style="width:720px;height:420px;border:0px;padding:0px;"
		showMaxButton="true" showCollapseButton="false" showShadow="false"
		showToolbar="false" showFooter="false" showModal="true"
		allowResize="false" showHeader="false" allowDrag="true"></div>
	<div class="hide">
		<!-- 资源树,右键菜单 -->
		<ul style="display: block;" class="mini-contextmenu" id="treeMenu"
			onbeforeopen="onBeforeOpenTreeMenu">
			<li onclick="javascript:addfilefolder();"><a id="cm_private_add"><i
					class="icon-plus"></i> &nbsp;添加</a></li>
			<li onclick="javascript:editfolder();"><a id="cm_private_edit"><i
					class="icon-pencil-5"></i> &nbsp;编辑</a></li>
			<li onclick="javascript:removefolder();"><a
				id="cm_private_remove"><i class="icon-remove"></i> &nbsp;删除</a></li>
		</ul>
	</div>

	<div id="renameWin" class="mini-window" title="重命名"
		style="width:500px;height:150px;border:0px;" showMaxButton="false"
		showCollapseButton="false" showShadow="false" showToolbar="false"
		showFooter="false" showModal="true" allowResize="false"
		showHeader="true" allowDrag="true">
		<div class="form-horizontal mini-form" id="frmRenameFile"
			method="post">
			<input name="id" id="AttachmentItem_id" type="hidden"
				class="mini-hidden"> <input name="fileName" id="filename"
				type="hidden" class="mini-hidden">
			<div class="control-group " style="margin-top:20px;margin-left:10px;">
				<label class="control-label" for="AttachmentItem_file_name"
					style="float:left;margin-left:10px;">文件名 <span
					class="required">*</span>
				</label>
				<div class="input-group input-group-sm"
					style="margin-top:2px;margin-left:20px;padding:0px;margin-right:20px">
					<input type="text" class="form-control input-sm"
						placeholder="请输入文件名" id="AttachmentItem_file_name"
						onkeyup="setfileName(this);"
						style="padding:0px;font-size:13px;height:25px;"> <span
						class="input-group-addon" style="padding:0px 5px;height:25px;"
						id="spnExtenname"></span>
				</div>
			</div>
			<div style="float:right;margin-right:30px;margin-top:20px;">
				<button onclick="doRenamefile();" class="btn btn-danger btn-sm"
					id="yw31" style="padding:3px 10px">确定</button>
				&nbsp;
				<button data-dismiss="modal" class="btn btn-default btn-sm"
					id="yw32" name="yt6" type="button" style="padding:3px 10px"
					onclick="dorefleshfileList(false);">取消</button>
			</div>
		</div>
	</div>

	<div id="movetowin" class="mini-window" title="移动到"
		style="width:600px;height:400px;border:0px;" showMaxButton="false"
		showCollapseButton="false" showShadow="false" showToolbar="false"
		showFooter="false" showModal="true" allowResize="false"
		showHeader="true" allowDrag="true">
		<div class="form-horizontal mini-form" id="frMoveFile" method="post">
			<input name="id" id="Item_id" type="hidden" class="mini-hidden">
			<input name="attachmentId" id="attachment_id" type="hidden"
				class="mini-hidden">
			<div class="mini-fix"
				style="margin-top:5px;margin-left:10px;height:300px">
				<ul id="fileTree2" class="mini-tree" style="width:100%;height:100%;"
					url="" showTreeIcon="false" resultAsTree="false"
					expandOnNodeClick="true" textField="folderName" idField="id"
					parentField="parentId" onnodeselect="onTreeNodeSelect2"
					ondrawnode="onDrawNode">
				</ul>
			</div>
		</div>
		<div style="float:right;margin-right:30px;margin-top:20px;">
			<button onclick="doMovefile();" class="btn btn-danger btn-sm"
				id="yw31" style="padding:3px 10px">确定</button>
			&nbsp;
			<button data-dismiss="modal" class="btn btn-default btn-sm" id="yw32"
				name="yt6" type="button" style="padding:3px 10px"
				onclick="dorefleshfileList2(false);">取消</button>
		</div>
	</div>

	<div id="setsharedwin" class="mini-window" title="共享目录设置"
		style="width:700px;height:550px;border:0px;padding:0px;"
		showMaxButton="false" showCollapseButton="false" showShadow="false"
		showToolbar="false" showFooter="false" showModal="true"
		allowResize="false" showHeader="true" allowDrag="true"
		bodyStyle="padding:0px;"></div>


	<script type="text/javascript">
		mini.parse();
		var currentFolderID = 0;
		var currentGrid = "datagrid1";
		var currentNode = {};
		var curDirPath = '';

		//个人-共享文件柜页签切换
		function myactivechanged(e) {
			var tabName = e.tab.name;
			var tempPath = $("#spnfolderPath").html();
			if (curDirPath != '') {
				$("#spnfolderPath").html(curDirPath);
			}
			curDirPath = tempPath;
			if (tabName == "tabprivate") {

				$("#btnUpload").show();
				$("#batchShare").show();
				$("#batchDel").show();
				$("#divmore").show();
				currentGrid = "datagrid1";
				var tree1 = mini.get('fileTree');
				var node = tree1.getSelectedNode();
				currentNode = node;
				$("#datagrid2").hide();
				$("#datagrid1").show();
				$("#tabsButtons").show();
			} else {

				$("#btnUpload").hide();
				$("#batchShare").hide();
				$("#batchDel").hide();
				$("#divmore").hide();
				currentGrid = "datagrid2";
				$("#datagrid1").hide();
				$("#datagrid2").show();

				$("#tabsButtons").hide();

				var tree1 = mini.get('filesharedTree');
				var node = tree1.getSelectedNode();
				if (!node) {
					if (tree1.getChildNodes().length > 0) {
						tree1.selectNode(tree1.getChildNodes()[0]);
						currentNode = tree1.getChildNodes()[0];
						//tree1.setUrl("${path}/file/getFileFolders.json");						
						///TODO:加载子目录
					} else {
						var grid = mini.get("datagrid2");
						grid.addRow({});
						grid.clearRows();
					}
				} else {
					currentNode = node;
				}
			}
			var param = window["param"] || {};
			window["param"] = param;
			if (currentNode.id) {
				param["id"] = currentNode.id;
				param["isEditable"] = currentNode.isEditable;
			}
		}

		//文件批量下载
		function batchDownload() {
			var grid = mini.get(currentGrid);
			var rows = grid.getSelecteds();
			if (rows.length < 1) {
				mini.alert("请选择要下载的文件！");
				return;
			}
			mini.get("batchfiles").setValue(mini.encode(rows));
			$("#frmbatchdown").submit();
		}

		//共享目录设置
		function setSharedFolder() {
			var tree1 = mini.get('fileTree');
			var node = tree1.getSelectedNode();
			if (!node) {
				mini.alert("请先选择文件目录!");
				return;
			}
			var url = "${path}/public/topage.action?viewName=file/foldersharedSet";
			var win = mini.get("setsharedwin");
			var ifram0 = win.getIFrameEl();
			var data = {};
			data.folderId = node.id;
			data.folderName = node.folderName;
			if (!ifram0) {
				win.load(url, function() {
					ifram0 = this.getIFrameEl();
					ifram0.contentWindow.SetData(data);
				}, function(action) {
				});
			} else {
				ifram0.contentWindow.SetData(data);
			}
			win.show();
		}
		//共享目录设置后，刷新目录树
		function refreshfolderTree2(allow, id) {
			var win = mini.get("setsharedwin");
			win.hide();
			if (allow) {
				var tree1 = mini.get('fileTree');
				tree1.load("${path}/file/getFileFolders.json");
				tree1.selectNode('' + id + '');
				tree1.expandPath('' + id + '');
				var data = tree1.getList();
				data = mini.clone(data);
				var tree2 = mini.get('fileTree2');
				tree2.loadList(data, 'id', 'parentId');
			}
		}
		//文件列表grid右键菜单处理
		function onBeforeOpen(e) {
			var grid = mini.get(currentGrid);
			var menu = e.sender;
			var row = grid.getSelected();
			//var isEditable=row.isEditable;

			//var rowIndex = grid.indexOf(row);			

			if (!row) {
				e.cancel = true;
				//阻止浏览器默认右键菜单
				e.htmlEvent.preventDefault();
				return;
			}

			var mread = mini.getbyName("mread", menu);
			var medit = mini.getbyName("medit", menu);
			var mseparator = $("#mseparator");
			var mrename = mini.getbyName("mrename", menu);
			var mmove = mini.getbyName("mmove", menu);
			var mremove = mini.getbyName("mremove", menu);

			mread.hide();
			medit.hide();
			mrename.hide();
			mmove.hide();
			mremove.hide();
			mseparator.hide();
			if (currentGrid == "datagrid1") {
				mseparator.show();
				mrename.show();
				mmove.show();
				mremove.show();
			}
			if (row.canReade > -1) {
				mread.show();
			}

			if (row.canEdit > 0) {
				medit.show();
			}
		}
		//右键菜单--下载处理
		function ondownload(e) {
			var grid = mini.get(currentGrid);
			var row = grid.getSelected();
			window.location.href = "${path}/" + row.downhref;
		}
		//grid右键菜单--重命名
		function onRename(e) {
			var grid = mini.get(currentGrid);
			var row = grid.getSelected();
			if (!row) {
				mini.alert("请先选择文件！");
				return;
			}
			editfileName(row.id, row.fileName);
		}
		//grid右键菜单-删除
		function onRemove(e) {
			batchremovefile();
		}
		//grid右键菜单-阅读
		function onRead(e) {
			var grid = mini.get(currentGrid);
			var row = grid.getSelected();
			readwrite('view', row.code, row.fileExtName);
		}
		//grid右键菜单-编辑
		function onEdit(e) {
			var grid = mini.get(currentGrid);
			var row = grid.getSelected();
			readwrite('edit', row.code, row.fileExtName);
		}
		//grid右键菜单-移动到
		function onMove(e) {
			var grid = mini.get(currentGrid);
			var row = grid.getSelected();
			if (!row) {
				mini.alert("请先选择文件！");
				return;
			}
			var foldid = row.pk;
			var tree1 = mini.get('fileTree2');
			var win = mini.get("movetowin");
			mini.get("Item_id").setValue(row.id);
			win.show();
			tree1.selectNode('' + foldid + '');
			tree1.expandPath('' + foldid + '');
		}
		//文件移动后刷新文件列表
		function dorefleshfileList2(fresh) {
			var win = mini.get("movetowin");
			win.hide();
			if (fresh) {
				var grid = mini.get(currentGrid);
				grid.reload();
			}
		}
		function onTreeNodeSelect2(e) {
			var node = e.node;
			mini.get("attachment_id").setValue(node.attchmentId);
		}
		//移动文件--提交保存
		function doMovefile() {
			var form = new mini.Form("#frMoveFile");
			var data = form.getData();
			// 界面
			form.loading("保存中，请稍候......");
			var url = "${path}/attachment/ajax/updateAttachmentItem.json";
			var successCallback = function(message) {
				form.unmask();
				var info = message["info"];
				if (1 === message["status"]) {
					// 判断是否成功				
					mini.alert(info, '提示', function() {
						dorefleshfileList2(true);
					});
				} else {
					mini.alert(info);
				}
				return false;
			};
			//
			var errotCallback = function(jqXHR, textStatus, errorThrown) { // 把错误吃了
				form.unmask();
				mini.alert("保存失败");

			};
			requestAjax(url, {
				data : mini.encode(data)
			}, successCallback, errotCallback);

		}
		////////////////////////////////////////////////////
		//阅读、编辑文件
		function readwrite(action, code, extName) {
			var url = "${path}/file/code/" + code
					+ "/toFileReadWritePage.page?action=" + action
					+ "&extname=" + extName;
			var win = mini.open({
				url : url,
				showMaxButton : false,
				allowResize : false,
				showHeader : false,
				title : '阅读',
				height : 800,
				width : 1200,
				onload : function() {
				},
				ondestroy : function(action) {

				}
			});
			win.max();
		}
		//文件上传
		function showUpload() {
			var tree1 = mini.get('fileTree');
			var node = tree1.getSelectedNode();
			if (!node) {
				mini.alert("请先选择文件目录!");
				return;
			}
			var url = "${path}/public/topage.action?viewName=file/fileUpload";
			var win = mini.get("uploadwin");
			var ifram0 = win.getIFrameEl();
			var data = {};
			data.id = node.id;
			data.attachtype = "privateFolder";
			if (!ifram0) {
				win.load(url, function() {
					ifram0 = this.getIFrameEl();
					ifram0.contentWindow.SetData(data);
				}, function(action) {
				});
			} else {
				ifram0.contentWindow.SetData(data);
			}
			win.show();
		}
		//文件上传后，刷新文件列表
		function refreshfolderlist(allow, id) {
			var win = mini.get("uploadwin");
			win.hide();
			if (allow) {
				var grid = mini.get(currentGrid);
				grid.reload();
			}
		}

		//文件列表画面渲染处理--显示文件缩略图
		function rendericon(e) {
			var attachment1 = e.record;
			var s = '<img src="${path}/'+attachment1.thumbnail+'" />';
			return s;
		}
		//文件列表画面渲染处理--显示修改、下载、删除按钮
		function renderOperation(e) {
			var grid = mini.get(currentGrid);
			var recode = e.record;
			var rowIndex = grid.indexOf(recode);
			var s = '<a data-original-title="修改" class="icon-pencil td-link-icon" title="修改" href="javascript:editfileName(\''
					+ recode.id
					+ '\',\''
					+ recode.fileName
					+ '\');"></a>&nbsp;<a data-original-title="下载" class="download td-link-icon icon-download-2" title="下载" rel="tooltip" href="${path}/'+recode.downhref+'"></a>&nbsp;<a data-original-title="删除" class="icon-remove-2 td-link-icon" title="删除" action-type="deleteFile" rel="tooltip" href="javascript:removefile('
					+ rowIndex + ');"></a>';
			return s;
		}

		//文件列表画面渲染处理--显示修改、下载、删除按钮
		function renderOperation2(e) {
			var recode = e.record;
			var s = '';
			if (recode.canEdit > 0) {
				s = '<a data-original-title="修改" class="icon-pencil td-link-icon" title="修改" href="javascript:editfileName(\''
						+ recode.id
						+ '\',\''
						+ recode.fileName
						+ '\');"></a>&nbsp;<a data-original-title="下载" class="download td-link-icon icon-download-2" title="下载" rel="tooltip" href="${path}/'+recode.downhref+'"></a>';
			} else {
				s = '<a data-original-title="下载" class="download td-link-icon icon-download-2" title="下载" rel="tooltip" href="${path}/'+recode.downhref+'"></a>';
			}
			return s;
		}

		//删除指定文件
		function removefile(rowIndex) {
			var grid = mini.get(currentGrid);
			var row = grid.getRow(rowIndex);
			if (!grid.isSelected(row)) {
				grid.setSelected(row);
			}
			batchremovefile();
		}
		//批量删除文件
		function batchremovefile() {
			var grid = mini.get(currentGrid);
			var rows = grid.getSelecteds();
			if (rows.length < 1) {
				mini.alert("请选择要删除的文件！");
				return;
			}
			mini.confirm("确定彻底删除所选的文件吗?", "彻底删除？", function(action) {
				if (action != "ok")
					return;
				var data = {
					data : mini.encode(rows)
				};
				var url = "${path}/attachment/removeattachments.action";
				grid.loading("删除中，请稍候......");
				$.ajax({
					url : url,
					data : data,
					type : "post",
					cache : false,
					async : false,
					success : function(text) {
						grid.unmask();
						grid.reload();
					},
					error : function(jqXHR, textStatus, errorThrown) {
						grid.unmask();
						mini.alert("删除失败！");
					}
				});
			});

		}

		//////////////////重命名文件名
		function editfileName(id, name) {
			var win = mini.get("renameWin");
			var temp = name.split('.');
			$("#AttachmentItem_file_name").val(temp[0]);
			if (temp.length > 1) {
				$("#spnExtenname").text('.' + temp[temp.length - 1]);
				$("#AttachmentItem_file_name").val(
						name.replace($("#spnExtenname").text(), ''));
			}
			mini.get("AttachmentItem_id").setValue(id);
			mini.get("filename").setValue(name);
			win.show();
			$("#AttachmentItem_file_name").focus();
			$("#AttachmentItem_file_name").select();
		}
		function setfileName(e) {
			mini.get("filename").setValue(
					$(e).val() + $("#spnExtenname").text());
		}
		function dorefleshfileList(fresh) {
			var win = mini.get("renameWin");
			win.hide();
			if (fresh) {
				var grid = mini.get(currentGrid);
				grid.reload();
			}
		}
		function doRenamefile() {
			var form = new mini.Form("#frmRenameFile");
			// 校验		
			if (mini.get("filename").getValue() == "") {
				mini.alert("请输入文件名！");
				return;
			}
			var data = form.getData();
			// 界面
			form.loading("保存中，请稍候......");
			var url = "${path}/attachment/ajax/updateAttachmentItem.json";
			var successCallback = function(message) {
				form.unmask();
				var info = message["info"];
				if (1 === message["status"]) {
					// 判断是否成功				
					mini.alert(info, '提示', function() {
						dorefleshfileList(true);
					});
				} else {
					mini.alert(info);
				}
				return false;
			};
			//
			var errotCallback = function(jqXHR, textStatus, errorThrown) { // 把错误吃了
				form.unmask();
				mini.alert("保存失败");

			};
			requestAjax(url, {
				data : mini.encode(data)
			}, successCallback, errotCallback);
		}
		///////////////////////////////			
		//修改指定目录
		function editfolder() {
			doEditFolder('edit');
		}
		//删除指定目录
		function removefolder() {
			var tree1 = mini.get('fileTree');
			var node = tree1.getSelectedNode();
			var grid = mini.get(currentGrid);
			if (tree1.isLeaf(node)) {
				mini.confirm("确定删除选中的目录?", "删除？", function(action) {
					if (action != "ok")
						return;
					var data = {
						id : node.id						
					};
					var url = "${path}/file/deleteFileFolder.action";
					grid.loading("删除中，请稍候......");
					$.ajax({
						url : url,
						data : data,
						type : "post",
						cache : false,
						async : false,
						success : function(text) {
							grid.unmask();							
							var tree1 = mini.get('fileTree');
							tree1.load("${path}/file/getFileFolders.json");
							tree1.expandLevel(0);
							tree1.selectNode(tree1.getChildNodes()[0]);
							var data = tree1.getList();
							data = mini.clone(data);
							var tree2 = mini.get('fileTree2');
							tree2.loadList(data, 'id', 'parentId');
						},
						error : function(jqXHR, textStatus, errorThrown) {
							grid.unmask();
							mini.alert("删除失败！");
						}
					});
				});
			} else {
				mini.alert("请先删除子目录！");
			}
		}
		//新增目录
		function addfilefolder() {
			doEditFolder('new');
		}
		//修改或新增目录
		function doEditFolder(action) {
			var tree1 = mini.get('fileTree');
			var node = tree1.getSelectedNode();
			if (!node) {
				mini.alert("请先选择文件目录!");
				return;
			}
			var url = "${path}/file/toEditeFileFolderView.page?action="
					+ action;
			var win = mini.get("addfolderwin");
			var ifram0 = win.getIFrameEl();
			var data = {};
			data.id = node.id;
			data.data = tree1.getList();
			data.action = action;
			data.currentfolder = node;
			if (!ifram0) {
				win.load(url, function() {
					ifram0 = this.getIFrameEl();
					ifram0.contentWindow.SetData(data);
				}, function(action) {
				});
			} else {
				ifram0.contentWindow.SetData(data);
			}
			win.show();
		}
		//目录新增或修改后，刷新目录树
		function refreshfolderTree(allow, id) {
			var win = mini.get("addfolderwin");
			win.hide();
			if (allow) {
				var tree1 = mini.get('fileTree');
				tree1.load("${path}/file/getFileFolders.json");
				tree1.selectNode('' + id + '');
				tree1.expandPath('' + id + '');

				var data = tree1.getList();
				data = mini.clone(data);
				var tree2 = mini.get('fileTree2');
				tree2.loadList(data, 'id', 'parentId');
				//tree2.load("${path}/file/getFileFolders.json");
			}
		}
		//页面初始化
		$(function() {
			var tree1 = mini.get('fileTree');
			var tree2 = mini.get('fileTree2');
			var treeshared = mini.get('filesharedTree');
			var folders = mini.decode('${folders}');
			var folders2 = mini.decode('${folders}');

			var sharedfolders = mini.decode('${sharedfolders}');
			tree2.loadList(folders2, 'id', 'parentId');
			tree1.loadList(folders, 'id', 'parentId');
			tree1.expandLevel(0);
			tree1.selectNode(tree1.getChildNodes()[0]);

			//alert('${sharedfolders}');
			treeshared.loadList(sharedfolders, 'id', 'parentId');
		});
		//目录树选择
		function onTreeNodeSelect(e) {
			var node = e.node;
			var tree1 = mini.get('fileTree');
			var normalT = '<a id="" class="btn btn-link" op="back" style="padding-left:0;padding-right:0; text-decoration:none;color:#000;cursor:pointer;font-size:12px;" ></a>';
			var normalC = '<a style="padding-left:0;padding-right:0; text-decoration:none;color:#a0a0a0;cursor:default;font-size:12px;" class="btn btn-link" id="9" op="topReload"></a>';
			var normalS = '<span style="display:inline-block;vertical-align: middle;color:#000;font-size:13px;">&nbsp;/&nbsp;</span>';

			var folders = $("#spnfolderPath");
			currentFolderID = node.id;
			currentNode = node;

			folders.empty();
			tree1.bubbleParent(node, function(e1) {
				var l, s;
				s = $(normalS);
				if (node.id == e1.id) {
					l = $(normalC);
					l.attr("id", e1.id);
				} else {
					l = $(normalT);
				}
				l.text(e1.folderName);
				folders.prepend(l);
				if (e1.parentId != '0') {
					folders.prepend(s);
				}
				if (node.id != e1.id) {
					$(l).on('click', function() {
						goback(e1.id);
					});
				}
			}, this);

			search();
		}
		//文件目录重定位
		function goback(id) {
			var tree1 = mini.get('fileTree');
			tree1.selectNode('' + id + '');
			tree1.expandPath('' + id + '');
		}
		//目录下文件查找
		function search() {
			var grid = mini.get(currentGrid);
			var param = window["param"] || {};
			window["param"] = param;
			param["sortField"] = "UPDATE_TIME";
			param["sortOrder"] = "desc";
			var key = mini.get("txtFileName").getValue();
			param["fileName"] = key;
			param["id"] = currentNode.id;
			param["total"] = -1;
			param["isEditable"] = currentNode.isEditable;
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		}
		function onKeyEnter() {
			search();
		}
		//目录树画面初始化渲染操作--处理目录icon
		function onDrawNode(e) {
			var tree = e.sender;
			var node = e.node;
			var isLeaf = tree.isLeaf(node);
			if (node.isShared > 0) {
				e.nodeHtml = '<SPAN class="filefoldShared" title="" style="text-indent: 10px;float:left;width:25px;" id="'+node.orgid+'">&nbsp;</SPAN><SPAN>'
						+ node.folderName + '</SPAN>';
			} else {
				e.nodeHtml = '<SPAN class="filefoldpic" title="" style="text-indent: 10px;float:left;width:25px;" id="'+node.orgid+'">&nbsp;</SPAN><SPAN>'
						+ node.folderName + '</SPAN>';
			}
		}
		function onDrawNode2(e) {
			var tree = e.sender;
			var node = e.node;
			var isLeaf = tree.isLeaf(node);
			e.nodeHtml = '<SPAN class="filefoldShared" title="" style="text-indent: 10px;float:left;width:25px;" id="'+node.orgid+'">&nbsp;</SPAN><SPAN>'
					+ node.folderName + '</SPAN>';
		}
		//处理目录树的右键菜单
		function onBeforeOpenTreeMenu(e) {
			//var menu = e.sender;
			var tree = mini.get("fileTree");
			// 取得点击的节点
			var node = tree.getSelectedNode();
			// 某些特殊节点,屏蔽事件;
			if (node && node.parentId == '0') {
				//e.cancel = true;
				//阻止浏览器默认右键菜单
				//e.htmlEvent.preventDefault();
				$("#cm_private_edit").hide();
				$("#cm_private_remove").hide();
				return;
			}
			$("#cm_private_remove").show();
			$("#cm_private_edit").show();

		};
	</script>
</body>
</html>

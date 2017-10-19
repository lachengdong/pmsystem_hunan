<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>新建文件夹</title>
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
LABEL {
	font-size: 12px;
	font-weight: normal;
}

h4 {
	margin: 0px;
	font-weight: bold;
}

.filefoldpic {
	background: url(${path}/images/folder.gif) no-repeat;
}
</style>
</head>
<body style="overflow: hidden;padding:0px;">
	<div style="display: block;overflow: hidden;" tabindex="-1" id="folder">
		<div class="modal-header">
			<a class="close" data-dismiss="modal" href="javascript:doCancle();">×</a>
			<h4 id="htitle">新建子文件夹</h4>
		</div>
		<div class="modal-body" style="text-align: center;overflow: hidden; ">
			<form class="form-horizontal mini-form" role="form"
				id="folder-private-form" method="POST">
				<div class="form-group">
					<label class="col-sm-2 control-label"
						style="font-size:12px;float:left;min-width:100px;text-align:right;margin-top:8px;"
						for="FileFolderPrivate_folder_order">排序号 <span
						class="required">*</span></label>
					<div class="col-sm-10" style="float:left;">
						<input name="folderOrder" class="form-control mini-textbox" selectOnFocus="true"
							id="folder_order" type="text" emptyText="请输入排序号" required="true"
							vtype="int" requiredErrorText="排序号不能为空！">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"
						for="FileFolderPrivate_folder_name"
						style="font-size:12px;float:left;min-width:100px;text-align:right;margin-top:8px;">文件夹名称
						<span class="required">*</span>
					</label>
					<div class="col-sm-10" style="float:left;margin-bottom:5px;">
						<input name="folderName" id="folder_name"
							class="form-control mini-textbox" maxlength="200" type="text"
							emptyText="请输入文件夹名" required="true" requiredErrorText="文件夹名不能为空！">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"
						style="font-size:12px;float:left;min-width:110px;text-align:right;margin-top:8px;"
						for="FileFolderPrivate_parent_id">上级文件夹名称</label>
					<div class="col-sm-10" style="float:left;">
						<input id="fileTree" class="mini-treeselect" multiSelect="false"
							name="parentId" valueFromSelect="false" textField="folderName"
							idField="id" parentField="parentId" allowInput="true"
							height="30px" style="width:145px;" showRadioButton="flase"
							popupHeight="150" showFolderCheckBox="false" />
					</div>
				</div>
				<input name="id" id="primary_id" type="hidden" class="mini-hidden" />
				<input name="action" id="action" type="hidden" />
			</form>
		</div>
		<div class="modal-footer"
			style="text-align:center;background-color:#f8f8f8">
			<button onclick="doSave();" class="btn btn-danger btn-sm" id="yw13"
				name="yt0" type="button" style="padding:3px 15px">保存</button>
			<button data-dismiss="modal" class="btn btn-default btn-sm" id="yw14"
				name="yt1" type="button" onclick="doCancle();"
				style="padding:3px 15px">取消</button>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			var tree1 = mini.get('fileTree');
			var folders = mini.decode('${folders}');
			tree1.loadList(folders, 'id', 'parentId');
		});

		function onDrawNode(e) {
			var tree = e.sender;
			var node = e.node;
			var isLeaf = tree.isLeaf(node);
			//e.nodeHtml = '<SPAN class="filefoldpic" title="" style="text-indent: 10px;float:left;width:25px;" id="'+node.orgid+'">&nbsp;</SPAN><SPAN>'
			//		+ node.folderName + '</SPAN>';
		}

		function doCancle() {
			parent.refreshfolderTree(false);
		}

		function doSave() {
			var form = new mini.Form("#folder-private-form");
			// 校验
			form.validate();
			if (form.isValid() == false) {
				return;
			}
			var data = form.getData();
			// 界面
			form.loading("保存中，请稍候......");
			var url = "${path}/file/ajax/editFilefolderInfo.json?action="
					+ $("#action").val();
			var successCallback = function(message) {
				form.unmask();
				var info = message["info"];
				if (1 === message["status"]) {
					// 判断是否成功
					mini.alert(info, '提示', function() {
						parent.refreshfolderTree(true, message.data[0].id);
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
		function SetData(data) {
			var tree1 = mini.get('fileTree');
			data = mini.clone(data);

			$("#action").val(data.action);
			if (data.action == 'edit') {
				var form = new mini.Form("#folder-private-form");
				form.setData(data.currentfolder);
				$("#htitle").text("编辑子文件夹");
				$.each(data.data, function(i, n) {
					if (n.id == data.currentfolder.id) {
						data.data = arrayDel(data.data, i);
						return false;
					}
				});
				tree1.loadList(data.data, 'id', 'parentId');
				tree1.setValue(data.currentfolder.parentId);
				mini.get("folder_order").focus();
			} else {
				$("#htitle").text("新建子文件夹");
				mini.get("folder_name").setValue('');
				mini.get("folder_order").setValue('9999');
				mini.get("primary_id").setValue(data.currentfolder.id);
				mini.get("folder_order").focus();
				tree1.loadList(data.data, 'id', 'parentId');
				tree1.setValue(data.id);
			}

		}

		function arrayDel(a, n) {
			if (n < 0)
				return a;
			else
				return a.slice(0, n).concat(a.slice(n + 1, a.length));
		}

		//////////////////////////////////
		function CloseWindow(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		};
	</script>
</body>
</html>

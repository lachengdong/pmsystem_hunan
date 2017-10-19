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
<title>卷盒管理</title>
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
	top: 0px;
	line-height: 15px;
}

label {
	float: left;
	font-weight: normal;
	width: 85px;
	text-align: right;
	margin-right: 10px;
}

.controls {
	float: left;
}

.control-group {
	margin-top: 2px;
	clear: left;
	height: 25px;
}
</style>

</head>
<body style="padding:0px;">
	<div id="divarchBoxList"
		style="width:100%;height:100%;padding-left:2px;padding-top:1px;">
		<div class="td-nav">
			<div class="btn-group" style="float:left;">
				<button id="ArchBox"
					style="margin-left:5px;padding:3px 10px;margin-top:5px;"
					class="btn btn-danger" onclick="openArchFolderSelector();">
					<i class="icon-exit-3"></i> 档案归档
				</button>
				<button id="create"
					style="margin-left:5px;padding:3px 10px;margin-top:5px;"
					class="btn btn-primary" onclick="doarchboxEdit('new');">
					<i class="icon-archive"></i> 新建卷盒
				</button>
			</div>

			<div style="float:right;">
				<input id="key" class="mini-textbox" emptyText="根据盒号、年度搜索"
					onenter="onKeyEnter" style="margin-right:1px;font-size:12px;" /> <a
					class="mini-button" plain="true" iconCls="icon-search" plain="true"
					onclick="search()">查询</a>
			</div>
			<div style="clear:both;"></div>
		</div>

		<div class="mini-fit">
			<div id="datagrid1" class="mini-datagrid"
				style="width: 100%; height: 100%;border:0px;"
				url="${path}/arch/getArchboxInfos.json" allowAlternating="true"
				idField="fmfileid" sizeList="[10,20,50,100]" pageSize="20" onbeforeselect="ondoselect"
				multiSelect="true" borderStyle="border:0px;">
				<div property="columns">
					<div type="checkcolumn" width="30px"></div>
					<div field="boxNo" width="10%" headerAlign="center"
						sortField="box_no" align="right" allowSort="true">盒号</div>
					<div field="createUserName" width="10%" headerAlign="center"
						align="left" allowSort="false">创建人</div>
					<div field="year" width="10%" headerAlign="center" sortField="year"
						allowSort="true" align="center">年度</div>
					<div field="saveDate" width="10%" headerAlign="center"
						sortField="save_date" allowSort="true" align="center"
						renderer="renderSavedata">保管期限</div>
					<div field="secret" width="15%" headerAlign="center"
						sortField="secret" allowSort="true" align="center">秘密等级</div>
					<div field="docsum" width="10%" headerAlign="center"
						allowSort="false" align="center">文件数</div>
					<div field="docNum" width="15%" headerAlign="center"
						sortField="doc_num" allowSort="true" align="left">文件编号</div>
					<div field="isArch" width="5%" headerAlign="center"
						sortField="is_arch" allowSort="true" align="center"
						renderer="renderIsArch">已归档？</div>
					<div field="" width="15%" headerAlign="center" align="center"
						name="colOperation" allowSort="false" renderer="renderOperation">操作</div>
				</div>
			</div>
		</div>
		<div id="winarchboxedit" class="mini-window" title="卷盒编辑"
			style="width:800px;height:500px;border:0px;display:none" showMaxButton="true"
			showCollapseButton="false" showShadow="false" showToolbar="false"
			showFooter="false" showModal="true" allowResize="true" 
			showHeader="false" borderStyle="border:0" allowDrag="true"
			bodyStyle="padding:0px;">
			<div class="mini-fit" id="divarchFileEdit">
				<div id="yw1" class="td-nav">
					<table class="td-nav-table">
						<tr>
							<td><a
								style="padding-left:0;padding-right:0;font-size:13px;"
								class="btn btn-link" id="yw1" href="javascript:goback();">卷盒管理</a><span
								style="display:inline-block;vertical-align: middle;color:#000;">&nbsp;/&nbsp;</span><a
								style="padding-left:0;padding-right:0; text-decoration:none;color:#000;font-size:13px;"
								class="btn btn-link" id="htitle">新建卷盒</a>
								<div class="pull-right">
									<button onclick="doSave();" class="btn btn-danger btn-sm"
										style="padding:2px 10px;">保存</button>
									&nbsp;
									<button class="btn btn-default btn-sm"
										style="padding:2px 10px;" onclick="goback();">返回</button>
								</div></td>
						</tr>
					</table>
				</div>
				<div class="form" style="margin-top:15px;margin-left:10px;"
					id="frmarchboxedit">
					<div class="control-group ">
						<input name="id" id="id" class="mini-hidden" /> <input
							name="action" id="action" class="mini-hidden" /> <label
							class="control-label required" for="Archive_boxNo">盒号 <span
							class="required">*</span>
						</label>
						<div class="controls">
							<input class="mini-textbox" name="boxNo" id="Archive_boxNo"
								required="true" requiredErrorText="盒号不能为空！" errorMode="border" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label required" for="Archive_year">年度
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input class="mini-textbox" name="year" id="Archive_year"
								required="true" vtype="int" requiredErrorText="年度不能为空！"
								errorMode="border" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label required" for="Archive_save_date">保管期限
							<span class="required">*</span>
						</label>
						<div class="controls">
							<input name="saveDate" class="mini-combobox" id="saveDate"
								style="width:125px;" textField="text" valueField="id" value="1"
								data="[{id:'10',text:'10年'},{id:'30',text:'30年'},{id:'C',text:'永久'}]" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label" for="Archive_secret">秘密等级</label>
						<div class="controls">
							<input id="Archive_secret" name="secret" class="mini-combobox"
								textField="text" valueField="id" emptyText="-请选择-"
								inputStyle="text-align:left;"
								data="[{id:'普通',text:'普通'},{id:'秘密',text:'秘密'},{id:'机密',text:'机密'},{id:'绝密',text:'绝密'}]"
								allowInput="true" showNullItem="true" nullItemText="-请选择-" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label" for="Archive_docsum">文件编号</label>
						<div class="controls">
							<input size="50" maxlength="50" name="docNum" id="Archive_docsum"
								class="mini-textbox" />
						</div>
					</div>
					<div class="control-group ">
						<label class="control-label" for="Archive_remark">备注</label>
						<div class="controls">
							<textarea class="mini-textarea" id="Archive_remark" name="remark"
								style="margin-bottom:0;font-size:12px;width:600px;height:50px;margin-bottom:6px;"
								inputStyle="background-color:white;"></textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="divarchBoxView"
		style="width:100%;height:100%;padding-left:2px;padding-top:1px;">
		<jsp:include page="/WEB-INF/JSP/arch/archboxView.jsp"></jsp:include>
	</div>
	<div id="winArchFolderinfo" class="mini-window" title="选择卷库归档"
		style="width:300px;height:500px;border:0px;" showMaxButton="true"
		showCollapseButton="false" showShadow="false" showToolbar="false"
		showFooter="false" showModal="true" allowResize="true"
		showHeader="true" allowDrag="true" bodyStyle="padding:0px;"></div>

	<script type="text/javascript">
		$(function() {
			search();
			$("#divarchBoxView").hide();
		});
		function onKeyEnter() {
			search();
		};

		//档案归档
		function openArchFolderSelector() {
			var grid = mini.get("datagrid1");
			var row = grid.getSelected();
			if (!row) {
				mini.alert("请选择需要归档的卷盒！");
				return;
			}
			var url = "${path}/arch/toArchFolderSelectorPage.page";
			var win = mini.get("winArchFolderinfo");
			var ifram0 = win.getIFrameEl();		
			if (!ifram0) {
				win.load(url, function() {
					//ifram0 = this.getIFrameEl();				
				}, function(action) {
				});
			} 
			win.show();
		};
		function doArchive(folderID)
		{
			var win = mini.get("winArchFolderinfo");
			win.hide();
			if(!folderID)return;
			var grid = mini.get("datagrid1");
			var row = grid.getSelected();
			var data={};
			data.id=row.id;
			data.placeId=folderID;
			data.isArch=1;
			grid.loading("保存中，请稍候......");
			var url = "${path}/arch/ajax/doArchiveArchbox.json";
			var successCallback = function(message) {
				grid.unmask();
				grid.reload();
				return false;
			};
			//
			var errotCallback = function(jqXHR, textStatus, errorThrown) {
				// 把错误吃了
				alert("保存失败");
				grid.unmask();
			};
			requestAjax(url, {
				data : mini.encode(data)
			}, successCallback, errotCallback);
			
		};

		//卷盒查询
		function search() {
			var grid = mini.get("datagrid1");
			var param = window["param"] || {};
			window["param"] = param;
			param["sortField"] = "year";
			param["sortOrder"] = "desc";
			var key = mini.get("key").getValue();
			param["boxNo"] = key;
			param["total"] = -1;
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		};
		//隐藏卷盒信息编辑页签
		function goback() {
			var win = mini.get("winarchboxedit");
			win.hide();
		};

		//卷盒新增
		function doarchboxEdit(action) {
			var win = mini.get("winarchboxedit");
			var archbox = {};
			archbox.year = (new Date()).getFullYear();
			archbox.saveDate = '10';
			archbox.secret = '普通';
			archbox.action = action;
			var form = new mini.Form("#frmarchboxedit");
			form.setData(archbox);
			win.max();
			$("#htitle").text("新建卷盒");
			mini.get("Archive_boxNo").focus();
		};

		//卷盒信息修改
		function eidtArchBox(rowIndex) {
			var win = mini.get("winarchboxedit");
			var grid = mini.get("datagrid1");
			var data = grid.getRow(rowIndex);
			var form = new mini.Form("#frmarchboxedit");
			data.action = 'edit';
			form.setData(data);
			win.max();
			$("#htitle").text("编辑卷盒");
			mini.get("Archive_boxNo").focus();
		};

		//卷盒内档案文件查看
		function viewArchBox(box_id) {
			$("#divarchBoxList").hide();
			$("#divarchBoxView").show();
			search2(box_id);
		};
		//卷盒信息保存
		function doSave() {
			var form = new mini.Form("#frmarchboxedit");
			// 校验
			form.validate();
			if (form.isValid() == false) {
				return;
			}
			var data = form.getData();
			// 界面
			form.loading("保存中，请稍候......");
			var url = "${path}/arch/ajax/editArchboxInfo.json";
			var successCallback = function(message) {
				form.unmask();
				var info = message["info"];
				if (1 === message["status"]) {
					// 判断是否成功
					mini.alert(info, '提示', function() {
						goback();
						var grid = mini.get("datagrid1");
						grid.reload();
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
		};

		function renderOperation(e) {
			var recode = e.record;
			var s = '';
			if (recode.isArch == '0') {
				s = '<a data-original-title="查看" class="icon-eye td-link-icon" title="查看" href="javascript:viewArchBox(\''
						+ recode.id
						+ '\');">查看</a>&nbsp;&nbsp;<a data-original-title="修改" class="icon-pencil td-link-icon" title="修改" rel="tooltip" href="javascript:eidtArchBox('
						+ e.rowIndex
						+ ');">修改</a>&nbsp;&nbsp;<a data-original-title="删除" class="icon-remove-2 td-link-icon" title="删除" action-type="deleteFile" rel="tooltip" href="javascript:removeArchBox('
						+ recode.id + ');">删除</a>';
			} else {
				s = '<a data-original-title="查看" class="icon-eye td-link-icon" title="查看" href="javascript:viewArchBox(\''
						+ recode.id + '\');">查看</a>';
			}
			return s;
		};

		function renderSavedata(e) {
			var states = {
				"10" : "10年",
				"30" : "30年",
				"C" : "永久"
			};
			return states[e.value];

		};

		function renderIsArch(e) {
			var states = {
				"0" : "未归档",
				"1" : "已归档"
			};
			return states[e.value];
		};
		
		function ondoselect(obj) {		
			if (obj.record.docsum == 0) {
				obj.cancel = true;
			}
		}

		//删除卷盒
		function removeArchBox(id) {
			var grid = mini.get("datagrid1");
			var url = "${path}/arch/deleteArchBox.action?1=1";
			var data = {
				id : id
			};
			doRemove(data, url, grid);
		};
	</script>

</body>
</html>

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
<title>预归档管理</title>
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

label {
	font-weight: normal;
}
</style>

</head>
<body>
	<div class="mini-layout"
		style="overflow: hidden;width:100%;height:100%;margin-left:1px;margin-top:1px">
		<div showHeader="false" region="west" width="196" maxWidth="250"
			minWidth="100" showSplit="false" showSplitIcon="true"
			bodyStyle="overflow: hidden;BACKGROUND: url(${path}/images/email/bgg.png) #fff repeat-y;text-align:center;">
			<div class="sidebar_logo T_icon" style="width:195px;">
				<span class="icon-books"></span>
			</div>
			<div class="sidebar_content T_bg" style="width:195px;">
				<button style="width:120px;margin:5px auto;"
					onclick="doopendivFileEdit();" class="btn btn-success btn-sm "
					id="yw0">

					<i class="icon-pencil"></i> 新建档案文件
				</button>
				<ul id="arch_menu" class="nav nav-list" style="width:195px">
					<li id="lifile" class="active"><a
						href="javascript:gotodivArchfileList();"><i
							class="icon-numbered-list"></i> &nbsp;文件管理<!--<span
							class="badge badge-important pull-right">0</span>--></a></li>
					<li id="libox"><a href="javascript:doopenarchbox();"><i
							class="icon-file"></i>&nbsp;卷盒管理 <!--  <span
							class="badge badge-important pull-right">0</span>--> </a></li>
				</ul>
			</div>

		</div>
		<div region="center" style="padding:0px;margin:0px;">
			<div class="mini-fit" id="divarchFileEdit" style="display:none;">
				<jsp:include page="/WEB-INF/JSP/arch/archFileEdit.jsp"></jsp:include>
			</div>
			<div class="mini-fit" id="divArchFileList">
				<div class="td-nav" style="padding:0px;">
					<div class="btn-group" style="float:left;margin-left:0px;">
						<a
							style="margin-top:5px;text-decoration:none;color:#000;font-size:13px;magin-left:0px;font-weight:bold;"
							class="btn btn-link">档案文件管理</a>
					</div>
					<div style="float:right;">
						<input id="key" class="mini-textbox" emptyText="根据标题、文件编号搜索"
							onenter="onKeyEnter" style="margin-right:1px;font-size:12px;" />
						<a class="mini-button" plain="true" iconCls="icon-search"
							plain="true" onclick="search()">查询</a>
					</div>
					<div style="clear:both;"></div>
				</div>
				<div class="mini-fit">
					<div id="grdArchfile" class="mini-datagrid"
						style="width: 100%; height: 100%;border:0px;"
						url="${path}/arch/getArchFileInfos.json" allowAlternating="true"
						idField="fmfileid" sizeList="[10,20,50,100]" pageSize="20"
						multiSelect="true" borderStyle="border:0px;">
						<div property="columns">
							<div field="fileName" width="20%" headerAlign="center"
								sortField="file_name" align="left" allowSort="true">文件标题</div>
							<div field="fileCode" width="10%" headerAlign="center"
								sortField="file_code" align="left" allowSort="true">文件编号</div>
							<div field="sendUnit" width="10%" headerAlign="center"
								sortField="send_unit" allowSort="true" align="center">发文单位</div>
							<div field="saveDate" width="10%" headerAlign="center"
								sortField="save_date" allowSort="true" align="center"
								renderer="renderSavedata">保管期限</div>
							<div field="secret" width="10%" headerAlign="center"
								sortField="secret" allowSort="true" align="center">秘密等级</div>
							<div field="urgency" width="10%" headerAlign="center"
								sortField="urgency" allowSort="true" align="center">紧急程度</div>
							<div field="boxId" width="5%" headerAlign="center"
								sortField="box_id" allowSort="true" align="center"
								renderer="renderIsArch">已立卷？</div>
							<div field="date1" width="10%" headerAlign="center"
								dateFormat="yyyy-MM-dd HH:mm:ss" sortField="date1"
								allowSort="true" align="left">时间</div>
							<div field="" width="15%" headerAlign="center" align="center"
								name="colOperation" allowSort="false" renderer="renderOperation">操作</div>
						</div>
					</div>
				</div>
			</div>
			<div class="mini-panel" borderStyle="border:0" showFooter="false"
				showHeader="false" style="display:none;width:100%;height:100%"
				id="divarchbox" bodyStyle="padding:0px;"></div>

		</div>
	</div>

	<script type="text/javascript">
		//档案文件查询
		$(function() {
			search();
			$('#arch-file')
					.MultiFile(
							{
								'list' : '#yw20-container',
								'STRING' : {
									'remove' : '<i class=\"icon-remove-2\" rel=\"tooltip\" title=\"去掉该文件\"><\/i>',
									'selected' : '文件：$file',
									'denied' : '禁止上传扩展名为$ext的文件',
									'duplicate' : '您已经选择了这个文件，请勿重复选择：$file'
								}
							});

		});
		function onKeyEnter() {
			search();
		}
		function search() {
			var grid = mini.get("grdArchfile");
			var param = window["param"] || {};
			window["param"] = param;
			param["sortField"] = "date1";
			param["sortOrder"] = "desc";
			var key = mini.get("key").getValue();
			param["fileName"] = key;
			param["total"] = -1;
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		}

		//进入档案文件列表页签
		function gotodivArchfileList() {
			var param = window["param"] || {};
			param["boxId"] = 0;
			$("#arch_menu>li").removeClass("active");
			mini.get("divarchbox").hide();
			mini.get("divarchFileEdit").hide();
			mini.get("divArchFileList").show();
			$("#lifile").addClass("active");
		}
		//打开档案文件信息修改页签--新增档案文件
		function doopendivFileEdit() {
			var file = {};
			$("#arch_menu>li").removeClass("active");
			mini.get("divarchbox").hide();
			mini.get("divArchFileList").hide();

			file.unitCode = "000000000";
			file.fondsNo = "00000";
			file.year = (new Date()).getFullYear();
			file.saveDate = '10';
			file.action = "new";
			file.orgNo = '${orgId}';
			file.org_name = '${orgName}';
			var form = new mini.Form("#frmArchFile");
			form.setData(file);
			mini.get("divarchFileEdit").show();
			$("#harchfile").text("新建档案");
			$("#yw20-container").find('a.MultiFile-remove').click();
		}
		//档案文件信息修改
		function eidtArchFile(rowIndex) {
			var grid = mini.get("grdArchfile");
			var row = grid.getRow(rowIndex);
			$("#arch_menu>li").removeClass("active");
			mini.get("divarchbox").hide();
			mini.get("divArchFileList").hide();
			row.action = 'edit';
			var form = new mini.Form("#frmArchFile");
			form.setData(row);
			mini.get("divarchFileEdit").show();
			$("#harchfile").text("修改档案");

		}

		//删除档案文件
		function removeArchFile(id) {
			var grid = mini.get("grdArchfile");
			var url = "${path}/arch/deleteArchFile.action?1=1";
			var data = {
				id : id
			};
			doRemove(data, url, grid);
		}
		//打开卷盒信息管理页签
		function doopenarchbox() {
			var url = "${path}/public/topage.action?viewName=arch/archboxManage";
			var win = mini.get("divarchbox");
			var ifram0 = win.getIFrameEl();

			if (!ifram0) {
				win.load(url, function() {
					ifram0 = this.getIFrameEl();
					//ifram0.contentWindow.SetData(data);
				}, function(action) {
				});
			} else {
				//	ifram0.contentWindow.SetData(data);
			}
			$("#arch_menu>li").removeClass("active");
			$("#divarchFileEdit").hide();
			$("#divArchFileList").hide();
			$("#libox").addClass("active");
			mini.get("divArchFileList").hide();
			win.show();
		}
		function renderSavedata(e) {
			var states = {
				"10" : "10年",
				"30" : "30年",
				"C" : "永久"
			};
			return states[e.value];

		}
		function renderIsArch(e) {
			if (e.value == 0)
				return "未立卷";
			else
				return "已立卷";

		}
		function renderOperation(e) {
			var recode = e.record;
			var s = '';
			if (recode.boxId == '0') {
				s = '<a data-original-title="查看" class="icon-eye td-link-icon" title="查看" href="javascript:viewArchFile(\''
						+ recode.id
						+ '\');">查看</a>&nbsp;&nbsp;<a data-original-title="修改" class="icon-pencil td-link-icon" title="修改" rel="tooltip" href="javascript:eidtArchFile('
						+ e.rowIndex
						+ ');">修改</a>&nbsp;&nbsp;<a data-original-title="删除" class="icon-remove-2 td-link-icon" title="删除" action-type="deleteFile" rel="tooltip" href="javascript:removeArchFile('
						+ recode.id + ');">删除</a>';
			} else {
				s = '<a data-original-title="查看" class="icon-eye td-link-icon" title="查看" href="javascript:viewArchFile(\''
						+ recode.id + '\');">查看</a>';
			}
			return s;
		}
		function viewArchFile(id) {
			var url = "${path}/arch/gotoArvhFileViewPage.page?id=" + id;
			var win = mini.open({
				url : url,
				showMaxButton : true,
				allowResize : false,
				showHeader : false,
				title : '档案文件查看',
				height : 800,
				width : 1200,
				onload : function() {
				},
				ondestroy : function(action) {

				}
			});
			win.max();
		};
	</script>
</body>
</html>

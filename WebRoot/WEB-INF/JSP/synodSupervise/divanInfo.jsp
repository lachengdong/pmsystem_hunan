﻿<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>会议室信息维护</title>
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

label {
	float: left;
	font-weight: normal;
	width: 85px;
	text-align: right;
	margin-right: 10px;
}
</style>

</head>
<body>
        <input id="schemeid" name="schemeid" class="mini-hidden" value="" /> 
	<div class="mini-layout" style="width:100%;height:100%;padding:0px;">
		<div region="center" style="padding:0px;margin:0px;">
			<div class="mini-fit" id="divDivanEdit" style="display:none;">
				<jsp:include page="/WEB-INF/JSP/synodSupervise/divanAdd.jsp"></jsp:include>
			</div>
			<div class="mini-fit" id="divDivanList">
				<div class="td-nav" style="padding:0px;">
				<a class="mini-button" iconCls="icon-add" onclick="doopendivDivanEdit()"
				plain="true">新增</a>
					<div style="float:right;">
						<input id="key" class="mini-textbox" emptyText="根据会议室名称搜索"
							onenter="onKeyEnter" style="margin-right:1px;font-size:12px;" />
						<a class="mini-button" plain="true" iconCls="icon-search"
							plain="true" onclick="search()">查询</a>
					</div>
					<div style="clear:both;"></div>
				</div>
				<!-- 显示表格 -->
				<div class="mini-fit">
					<div id="grdDivan" class="mini-datagrid"
						style="width: 100%; height: 100%;border:0px;"
						url="${path}/MRoomInfo/getDivanInfos.json" allowAlternating="false"
						idField="fmfileid" sizeList="[10,20,50,100]" pageSize="20"
						multiSelect="true" borderStyle="border:0px;">
						<div property="columns">
							<div type="indexcolumn" width="15px" headerAlign="center"
								align="center">序号</div>
							<div field="name" width="30" headerAlign="center" allowSort="true"
					align="center">名 称</div>
				<div field="depict" width="45" headerAlign="center" allowSort="true"
					align="center">会议室描述</div>
				
				<div field="adminis" width="30" headerAlign="center" allowSort="true"
					align="center">会议管理员</div>
				<div field="capacity" width="20" headerAlign="center" allowSort="true"
					align="center">可容纳人数</div>

				<div field="equipment" width="45" headerAlign="center" allowSort="true"
					align="center">设备情况</div>
				<div field="locus" width="30" allowSort="true" headerAlign="center"
					allowSort="true" align="center">所在地点</div>
         <div field="state" width="25" headerAlign="center" allowSort="true"
					renderer="onvDivanRenderer" align="center">状态</div>
				<div name="operation" width="25" headerAlign="center" align="center"
					renderer="onActionRenderer" cellStyle="padding:0;">操 作</div>
						</div>
					</div>
				</div>
			</div>

			<div class="mini-panel" borderStyle="border:0" showFooter="false"
				showHeader="false" style="display:none;width:100%;height:100%"
				id="divDivanbox" bodyStyle="padding:0px;"></div>
		</div>
	</div>

	<script type="text/javascript">
		// 加载数据
		mini.parse();
		var grid = mini.get("grdDivan");
		grid.load();
	
		// 渲染操作菜单
		function onActionRenderer(e) {
			var recode = e.record;
		var s = '&nbsp;<a data-original-title="修改" class="icon-pencil td-link-icon" title="修改" rel="tooltip" href="javascript:eidDivanAmend('
					+ e.rowIndex
					+ ');">修改</a>&nbsp;&nbsp;&nbsp;<a  class="icon-remove-2 td-link-icon"  href="javascript:deleteDivan(\''
					+ recode.id + '\');">删除</a>';
			return s;
			return s;
		};
		function onvDivanRenderer(e) {
			var tests = {
				"0" : "不可用",
				"1" : "可用",
				"2" : "使用中"
			};
			return tests[e.value];
		};
		//会议室查询
		$(function() {
			search();
		});
		function onKeyEnter() {
			search();
		}
		function search() {
			var grid = mini.get("grdDivan");
			var param = window["param"] || {};
			window["param"] = param;
			param["sortField"] = "name";
			param["sortOrder"] = "desc";
			var key = mini.get("key").getValue();
			param["name"] = key;
			param["total"] = -1;
			grid.load(param, function() {
				param["total"] = grid.totalCount;
			});
		}
		
		//打开会议室信息修改页--新增会议室
		function doopendivDivanEdit() {
			var file = {};
			$("#Divan_menu>li").removeClass("active");
			mini.get("divDivanbox").hide();
			mini.get("divDivanList").hide();
			file.tempmid= "new";
			file.orgname = '${orgId}';
			file.org_namex = '${orgNamex}';
			var form = new mini.Form("#frmDivan");
			form.setData(file);
			mini.get("divDivanEdit").show();
			$("#DivanTitle").text("新建会议室");
		}
		//修改会议室信息
		function eidDivanAmend(rowIndex) {
			var grid = mini.get("grdDivan");
			var row = grid.getRow(rowIndex);
			$("#Divan_menu>li").removeClass("active");
			mini.get("divDivanbox").hide();
			mini.get("divDivanList").hide();
			row.tempmid = 'edit';
			var form = new mini.Form("#frmDivan");
			form.setData(row);
			mini.get("divDivanEdit").show();
			$("#DivanTitle").text("会议室信息修改");
		}
		//会议室信息查看
		function eidtDivan(rowIndex) {
			var grid = mini.get("grdDivan");
			var row = grid.getRow(rowIndex);
			mini.get("divDivanbox").hide();
			mini.get("divDivanList").hide();
			var form = new mini.Form("#passDivan");
			form.setData(row);
			mini.get("divDivanEdit").show();
		}
		//返回会议室设定主页
		function gotDivanList() {
			$("#arch_menu>li").removeClass("active");
			mini.get("divDivanbox").hide();
			mini.get("divDivanEdit").hide();
			mini.get("divDivanList").show();
			$("#lifile").addClass("active");
		}
		//删除会议室信息
		function deleteDivan(id) {
			var grid = mini.get("grdDivan");
			var url = "${path}/MRoomInfo/deleteDivanInfo.action?1=1";
			var data = {
				id : id
			};
			doRemove(data, url, grid);
		}
	</script>
</body>
</html>
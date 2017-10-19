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
<title>会议申请</title>
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
			<div class="mini-fit" id="divSynodEdit" style="display:none;">
				<jsp:include page="/WEB-INF/JSP/synodSupervise/synodAdd.jsp"></jsp:include>
			</div>
			<div class="mini-fit" id="divSynodList">
				<div class="td-nav" style="padding:0px;">
				<a class="mini-button" iconCls="icon-add" onclick="doopendivSynodEdit()"
				plain="true">新增</a>
					<div style="float:right;">
						<input id="key" class="mini-textbox" emptyText="根据会议名称搜索"
							onenter="onKeyEnter" style="margin-right:1px;font-size:12px;" />
						<a class="mini-button" plain="true" iconCls="icon-search"
							plain="true" onclick="search()">查询</a>
					</div>
					<div style="clear:both;"></div>
				</div>
				<!-- 显示表格 -->
				<div class="mini-fit">
					<div id="grdSynod" class="mini-datagrid"
						style="width: 100%; height: 100%;border:0px;"
						url="${path}/MRoomInfo/getSynodInfos.json" allowAlternating="false"
						idField="fmfileid" sizeList="[10,20,50,100]" pageSize="20"
						multiSelect="true" borderStyle="border:0px;">
						<div property="columns">
							<div type="indexcolumn" width="15px" headerAlign="center"
								align="center">序号</div>
							<div field="name" width="30" headerAlign="center" allowSort="true"
					align="center">名 称</div>
				<div field="topic" width="45" headerAlign="center" allowSort="true"
					align="center">主 题</div>
				
				<div field="depict" width="30" headerAlign="center" allowSort="true"
					align="center">会议描述</div>
				<div field="divanname" width="30" headerAlign="center" allowSort="true"
					align="center">会议室</div>

				<div field="begin" width="30" headerAlign="center" allowSort="true"
				renderer="onDateRenderer"	align="center">会议开始时间</div>
				<div field="finish" width="30" allowSort="true" headerAlign="center"
				renderer="onDateRenderer"	allowSort="true" align="center">结束时间</div>
         <
				<div name="operation" width="25" headerAlign="center" align="center"
					renderer="onActionRenderer" cellStyle="padding:0;">操 作</div>
						</div>
					</div>
				</div>
			</div>

			<div class="mini-panel" borderStyle="border:0" showFooter="false"
				showHeader="false" style="display:none;width:100%;height:100%"
				id="divSynodbox" bodyStyle="padding:0px;"></div>
		</div>
	</div>

	<script type="text/javascript">
		// 加载数据
		mini.parse();
		var grid = mini.get("grdSynod");
		grid.load();
		// 渲染日期
	function onDateRenderer(e) {
			if (e && e.value) {
				return mini.formatDate(new Date(e.value), "yyyy-MM-dd ");
			}
			return "";
		}
		// 渲染操作菜单
		function onActionRenderer(e) {
			var recode = e.record;
		var s = '&nbsp;<a data-original-title="修改" class="icon-pencil td-link-icon" title="修改" rel="tooltip" href="javascript:eidSynodAmend('
					+ e.rowIndex
					+ ');">修改</a>&nbsp;&nbsp;&nbsp;<a  class="icon-remove-2 td-link-icon"  href="javascript:deleteSynod(\''
					+ recode.mid + '\');">删除</a>';
			return s;
			return s;
		};
		
		//会议申请查询
		$(function() {
			search();
		});
		function onKeyEnter() {
			search();
		}
		function search() {
			var grid = mini.get("grdSynod");
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
		
		//打开会议申请信息修改页--新增会议申请
		function doopendivSynodEdit() {
			var file = {};
			$("#Divan_menu>li").removeClass("active");
			mini.get("divSynodbox").hide();
			mini.get("divSynodList").hide();
			file.actionType= "new";
			file.orgname = '${orgId}';
			file.org_namex = '${orgNamex}';
			var form = new mini.Form("#frmSynod");
			form.setData(file);
			mini.get("divSynodEdit").show();
			$("#SynodTitle").text("新建会议申请");
		}
		//修改会议申请信息
		function eidSynodAmend(rowIndex) {
			var grid = mini.get("grdSynod");
			var row = grid.getRow(rowIndex);
			$("#Divan_menu>li").removeClass("active");
			mini.get("divSynodbox").hide();
			mini.get("divSynodList").hide();
			row.actionType = 'edit';
			var form = new mini.Form("#frmSynod");
			form.setData(row);
			mini.get("divSynodEdit").show();
			$("#SynodTitle").text("会议申请修改");
		}
	
		//返回会议申请主页
		function gotSynodList() {
			$("#arch_menu>li").removeClass("active");
			mini.get("divSynodbox").hide();
			mini.get("divSynodEdit").hide();
			mini.get("divSynodList").show();
			$("#lifile").addClass("active");
		}
		//删除会议申请
		function deleteSynod(mid) {	
			var grid = mini.get("grdSynod");
			var url = "${path}/MRoomInfo/deleteSynodInfo.action?1=1";
			var data = {
				mid : mid
			};			
			doRemove(data, url, grid);
		}
	</script>
	</body>
</html>
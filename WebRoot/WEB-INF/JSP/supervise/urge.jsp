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
<title>任务催办</title>
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

body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

.mini-pager-num {
	top: 2px;
	line-height: 15px;
}
</style>
</head>
<body style="padding:0px;overflow:hidden;">
	<div id="frmurg" style="height:100%">
		<div height="130" style="border:0px" showCollapseButton="false"
			showHeader="false">
			<div id="frmurg">
				<table style="width:100%;">
					<tr>
						<td colspan="2" style="align:right;">
							<div class="mini-toolbar"
								style="padding:2px;border:0;height:33px;">
								<a class="btn btn-primary btn-sm"
									style="padding:3px 10px; width:60px;margin-right:5px;margin-top:1px;float:right;tabIndex:-1;"
									href="javascript:doSave();">保存</a> <a
									class="btn btn-default btn-sm"
									style="padding:3px 10px; width:60px;margin-right:5px;margin-top:1px;float:right"
									href="javascript:doCancle();">取消</a>
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align:top;padding-top:5px;padding-right:5px">&nbsp;催办内容<span class="required">*</span></td>
						<td><textarea class="mini-textarea" id="txtContent"
								name="content"
								style="margin-bottom:0;font-size:12px;width:420px;height:80px;margin-top:5px;"
								inputStyle="background-color:white;" emptyText="请输入催办内容"
								required="true" requiredErrorText="请输入催办内容"></textarea></td>
					</tr>
				</table>
			</div>
			<div style="margin-top:10px;margin-left:5px;">
				<span>提醒人员选择：</span>
			</div>
		</div>
		<div class="mini-fit" style="border:0px;">
			<div id="dgrflow" allowMoveColumn="false"
				style="width: 100%; height: 100%;" class="mini-datagrid"
				allowResize="false" showLoading="true" multiSelect="true"
				borderStyle="border-left:0;border-bottom:0;border-right:0;"
				allowAlternating="true" sizeList="[20,50,100]" showPager="false">
				<div property="columns">
					<div type="checkcolumn" width="15px"></div>
					<div type="indexcolumn" width="15px" headerAlign="center"
						align="center">序号</div>
					<div field="cnodename" width="15%" headerAlign="center"
						allowSort="false" align="left">当前步骤</div>
					<div field=assigneername width="12%" headerAlign="center"
						allowSort="false" align="left">办理人</div>
					<div field="startdate" width="25%" headerAlign="center"
						allowSort="false" width="26%" renderer="onDateRenderer"
						align="center" dateFormat="yyyy-MM-dd HH:mm:ss">开始时间</div>
					<div field="enddate" width="26%" headerAlign="center"
						allowSort="false" renderer="onDateRenderer" align="center"
						dateFormat="yyyy-MM-dd HH:mm:ss">结束时间</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//mini.parse();
		$(document).ready(function() {
			var grid = mini.get("dgrflow");
			var urgeuser = '${urgeuser}';
			if (urgeuser != '') {
				grid.addRows(mini.decode(urgeuser));
				grid.accept();
			}
		});

		//保存共享目录信息
		function doSave() {
			var form = new mini.Form("#frmurg");

			// 校验
			form.validate();
			if (form.isValid() == false) {
				return;
			}
			var data = form.getData();
			var grid = mini.get("dgrflow");
			var rows = grid.getSelecteds();
			if (rows.length < 1) {
				mini.alert("请选择提醒人员！");
				return;
			}
			data.items = rows;
			data.flowid = '${flowid}';
			// 界面
			form.loading("保存中，请稍候......");
			var url = "${path}/urge/doUrge.action";

			$.ajax({
				url : url,
				data : {
					data : mini.encode(data)
				},
				type : "POST",
				dataType : "json",
				async : false,
				success : function(text) {
					form.unmask();				
					CloseWindow('OK');
				},
				error : function(jqXHR, textStatus, errorThrown) {
					form.unmask();
					alert("操作失败");
				}
			});
		}

		function doCancle() {
			CloseWindow('close');
		}

		// 关闭窗口
		function CloseWindow(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}

		// 渲染日期
		function onDateRenderer(e) {
			if (e && e.value) {
				return mini
						.formatDate(new Date(e.value), "yyyy-MM-dd HH:mm:ss");
			}
			return "";
		};
	</script>
</body>
</html>

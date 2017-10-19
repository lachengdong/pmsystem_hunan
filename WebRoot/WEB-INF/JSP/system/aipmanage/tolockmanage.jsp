<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>区域锁定</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/scripts/boot.js" type="text/javascript">
		</script>
		<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
			rel="stylesheet" type="text/css" />
		<style type="text/css">
html,body {
	font-size: 12px;
	padding: 0;
	margin: 0;
	border: 0;
	height: 100%;
	overflow: hidden;
}
</style>
	</head>
	<body>
		<form id="form1" method="post">
			<div class="mini-toolbar mini-grid-headerCell" style="border-bottom: 0; padding: 0px;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 100%;">
							<a class="mini-button" onclick="onOk" plain="true"
								style="width: 60px">保存</a>
							<a class="mini-button" onclick="onCancel" plain="true"
								style="width: 60px;">取消</a>
						</td>
						<td style="white-space: nowrap;">
						</td>
					</tr>
				</table>
			</div>
			<div style="padding-left: 11px; padding-bottom: 5px;">
				<table style="table-layout: fixed;">
					<tr>
						<td style="width: 20%;">
							表单ID：
						</td>
						<td style="width:80%;">
							<input id="tempid" name="tempid" class="mini-textbox" required="true" readonly="true"/>
						</td>
						</tr>
						<tr>
						<td style="width:20%;">
							单位ID：
						</td>
						<td style="width:80%;">
							<input id="departid" name="departid" class="mini-textbox" required="true" readonly="true"/>
						</td>
					</tr>
					
					<tr>
						<td style="width: 20%;">
							不可编辑字段：
						</td>
						<td style="width:80%;height: 120px;">
							<input id="uneditedfields" name="uneditedfields" style="width: 100%;height: 100%;"
								class="mini-textarea"/>
						</td>
					</tr>
					
					<tr>
						<td style="width: 20%;">
							可编辑字段：
						</td>
						<td style="width:80%;height: 120px;">
							<input id="editedfields" name="editedfields" style="width: 100%;height: 100%;"
								class="mini-textarea"/>
						</td>
					</tr>
				</table>
			</div>
		</form>
		<script type="text/javascript">
		mini.parse();
		var form = new mini.Form("form1");
		function SaveData() {
			var data = form.getData();
			form.validate();
			if (form.isValid() == false){
					return;
			}
			 var json = mini.encode([data]);
			//
			$.ajax( {
				url : '<%=path%>/form/updatelockareamanage.action?1=1',
				data : {data:json},
				cache : false,
				type : "post",
				success : function(text) {
					CloseWindow("save");
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert("操作失败!");
					CloseWindow();
				}
			});
		};
		////////////////////
		//标准方法接口定义
		function SetData(data) {
			//
			if(data){
				//跨页面传递的数据对象，克隆后才可以安全使用
				data = mini.clone(data);
				mini.get("tempid").setValue(data.tempid);
				mini.get("departid").setValue(data.departid);
				mini.get("uneditedfields").setValue(data.uneditedfields);
				mini.get("editedfields").setValue(data.editedfields);
			}
		};
		
		function CloseWindow(action) {
			if (action == "close" && form.isChanged()) {
				if (confirm("数据被修改了，是否先保存？")) {
					return false;
				}
			}
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		};
		function onOk(e) {
			SaveData();
		};
		function onCancel(e) {
			CloseWindow("cancel");
		};
		function onCloseClick(e) {
			var obj = e.sender;
			obj.setText("");
			obj.setValue("");
		};
		</script>
	</body>
</html>

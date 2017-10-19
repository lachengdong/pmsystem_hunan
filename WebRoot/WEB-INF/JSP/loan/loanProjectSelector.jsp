<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>借款项目/金额填写</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
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
		<div class="mini-toolbar"
			style="border-bottom:0;padding:1px;height:30px;">
			<a class="mini-button" onclick="onOk2" plain="true"
				style="width:60px">确定</a> <a class="mini-button" onclick="onCancel"
				plain="true" style="width:60px;">取消</a>
		</div>
		<div
			style="padding-left:11px;padding-bottom:5px;padding-right:11px;margin-top:10px;">
			<table style="table-layout:fixed;">
				<tr>
					<td style="white-space: nowrap;">项目：</td>
					<td><input name="project_id" id="cmbProject"
						class="mini-combobox" emptyText="-请选择-" nullItemText="-请选择-"
						errorMode="border" required="true" requiredErrorText="请选择项目!"
						onvaluechanged="pvaluechanged" style="width:125px;"
						textField="name" valueField="id" /></td>					
				</tr>
				<tr>					
					<td style="white-space: nowrap;">预算余额：</td>
					<td><input class="mini-textbox" name="balance" id="txtBalance" /></td>
				</tr>
				<tr>
					<td style="white-space: nowrap;">借款金额：</td>
					<td ><input name="money" class="mini-textbox"
						size="40" id="money" errorMode="border" vtype="float"
						required="true" requiredErrorText="请输入借款金额!" /></td>
				</tr>
			</table>
		</div>
	</form>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form("form1");
		var pvalues = window.dialogArguments;
		var projects;
		$(document).ready(function() {
			projects = mini.decode('${projects}');
			var cmbProject = mini.get('cmbProject');
			cmbProject.setData(projects);
			var form = new mini.Form("#form1");
			form.setData(pvalues);
		});

		function CloseWindow(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}

		function onOk2(e) {
			var form = new mini.Form("#form1");
			// 校验
			form.validate();
			if (form.isValid() == false) {
				return;
			}

			var data = form.getData();
			var cmbProject = mini.get('cmbProject');
			var balance = new Number(data.balance);
			var money = new Number(data.money);

			if (money > balance) {
				alert('借款金额超出预算余额，不能借贷！');
				return;
			}
			data.projectName = cmbProject.getText();
			window.returnValue = data;
			CloseWindow("OK");

		}
		function onCancel(e) {
			CloseWindow("cancel");
		}

		function pvaluechanged(e) {
			var cmbProject = mini.get('cmbProject');
			var id = cmbProject.getValue();
			for (var i = 0; i < projects.length; i++) {
				if (id == projects[i].id) {
					var txtBalance = mini.get("txtBalance");
					txtBalance
							.setValue(projects[i].budmoney - projects[i].cost);
					break;
				}
			}
		}
		function SetData(data) {

		}
	</script>
</body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>公文字号设置</title>
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
		<input id="name" name="name" class="mini-hidden"
			value="${config.name}" /> <input id="value" name="value"
			class="mini-hidden" value="${config.value}" /> <input id="id"
			name="id" class="mini-hidden" value="${config.id}" /> <input
			id="catagory" name="catagory" class="mini-hidden"
			value="${config.catagory}" />
		<div class="mini-toolbar"
			style="border-bottom:0;padding:1px;height:30px;">
			<a class="mini-button" onclick="onOk2" plain="true"
				style="width:60px">确定</a> <a class="mini-button" onclick="onCancel"
				plain="true" style="width:60px;">取消</a>
		</div>
		<div style="padding-left:11px;padding-bottom:5px;padding-right:11px;margin-top:10px;">
			<table style="table-layout:fixed;">
				<tr>
					<td style="width: 100%;white-space: nowrap;">公文字号：<input
						id="txtnum0" class="mini-textbox" required="true" value="${num0}"
						requiredErrorText="字号不能为空！" /> 【<input id="txtnum1"
						value="${num1}" class="mini-textbox" style="width:50px;"
						required="true" requiredErrorText="年号不能为空！" />】 <input
						id="txtnum2" value="${num2}" class="mini-textbox" required="true"
						requiredErrorText="不能为空！" vtype="int" style="width:50px"/>号
					</td>
				</tr>
			</table>
		</div>
	</form>
	<script type="text/javascript">
		mini.parse();
		var s1 = mini.get("txtnum0");
		var s2 = mini.get("txtnum1");
		var s3 = mini.get("txtnum2");
		var form = new mini.Form("form1");
		var doc_nums = window.dialogArguments;
		$(document).ready(function() {
			if (doc_nums && doc_nums != '') {
				var temp = doc_nums.split(';');
				s1.setValue(temp[0]);
				s2.setValue(temp[1]);
				s3.setValue(temp[2]);

			}
		});

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
		}

		function onOk2(e) {
			var form = new mini.Form("#form1");
			// 校验
			form.validate();
			if (form.isValid() == false) {
				return;
			}
			
			var vobj=mini.get("value");
			vobj.setValue( s1.getValue() + ";" + s2.getValue()+ ";" + s3.getValue());
			var data = form.getData();
			// 界面
			form.loading("保存中，请稍候......");
			var url = "${path}/docdeliver/ajax/saveDocNum.json";
			var successCallback = function(message) {
				form.unmask();
				window.returnValue = vobj.getValue();
				CloseWindow("OK");
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
		function onCancel(e) {
			CloseWindow("cancel");
		}
		function SetData(data) {

		}
	</script>
</body>
</html>

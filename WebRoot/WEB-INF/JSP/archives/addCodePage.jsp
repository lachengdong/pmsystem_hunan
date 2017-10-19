<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>电子档案类型添加</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/scripts/validate.js" type="text/javascript">
		</script>
		<script src="<%=path%>/scripts/boot.js" type="text/javascript">
		</script>


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
		<div class="mini-toolbar" style="border-bottom: 0; padding: 0px;">
			<table style="width: 100%;">
				<tr>
					<td style="width: 100%;">
						<a class="mini-button" onclick="SaveData();" plain="true"
							style="width: 60px">确定</a>
						<a class="mini-button" onclick="onCancel" plain="true"
							style="width: 60px;">取消</a>
					</td>
					<td style="white-space: nowrap;">
					</td>
				</tr>
			</table>
		</div>
		<form id="form1" method="post" enctype="multipart/form-data">
			<div style="margin-left: 20px;">
				<table>
					<tr>
						<td align="center" style="width: 100px;">
							编码ID
						</td>
						<td style="width: 150px;">
							<input name="codeid" id="codeid" class="mini-textbox"
								required="true" requiredErrorText="ID不能为空" value="${codeid}"/>
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							档案名称
						</td>
						<td style="width: 150px;">
							<input name="codename" id="codename" class="mini-textbox"
								required="true" requiredErrorText="名称不能为空" value="${codename}" />
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							上级编码
						</td>
						<td style="width: 150px;">
							<input id="pcodeid" name="pcodeid" class="mini-textbox" required="true" showNullItem="true" value="${pcodeid}"/>
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							正（副）卷
						</td>
						<td style="width: 150px;">
							<input name="ispositive" id="ispositive" class="mini-combobox" data="ispositivedata"
								required="true" requiredErrorText="正副卷不能为空" value="${ispositive}"/>
						</td>
					</tr>
					
					<tr>
						<td align="center" style="width: 100px;">
							对应模板ID
						</td>
						<td style="width: 100px;">
							<input name="tempid" id="tempid" class="mini-textbox" emptyText="需要归档时填写此处" value="${tempid}"/>
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							排序
						</td>
						
						<td style="width: 100px;">
						    <input name="sn" id="sn" class="mini-spinner" allowInput="true" maxValue="99" minValue="1" value="${sn} style="width:40px;"/>
						</td>
					</tr>
					<tr>
						<td align="center" stype="width:100px;">
							备注
						</td>
						<td style="width: 0px;">
							<input name="remark" id="remark" class="mini-textbox" value="${remark}"/>
						</td>
					</tr>
				</table>
			</div>
		</form>
		<script type="text/javascript">
		var ispositivedata=[{ id: 0, text: '副卷' },{ id: 1, text: '正卷'}];
		
    		mini.parse();
    	  function SaveData() {
    	  	var form = new mini.Form("#form1");

			form.validate();
			if (form.isValid() == false){
				return;
			}
            var data = form.getData(true, false);
            var datas = mini.encode([data]);
				$.ajax( {
					url : "saveCode.action?1=1&addorupdate=${addorupdate}&departidtemp=${departidtemp}",
					type : "post",
					data : {
						data:datas
					},
					success : function(text) {
						var value=eval(text);
						if(value>0){
							alert("操作成功！");
							onCancel();
						}else if(value==-1){
							alert("CODEID重复，请重新输入！")
						}
					},
					error: function (jqXHR, textStatus, errorThrown){
							alert("操作失败！");
					}
				} );
	}

	//标准方法接口定义
	function SetData(data) {
		if (data.action == "new") {
			data = mini.clone(data);
			mini.get("remark").setValue(data.remark);
			mini.get("flowdefid").setValue(data.flowdefid);
		} else if (data.action == "edit") {
			data = mini.clone(data);
			$("#pmykey").val(data.id);
			$.ajax( {
				url : "selectFolderByFolderId.action?id=" + data.id,
				type : "post",
				cache : false,
				success : function(text) {
					var o = mini.decode(text);
					grid.setData(o);
					grid.setChanged(false);
				}
			});

		}
	}
	function onCancel(action) {
		if (window.CloseOwnerWindow)
			return window.CloseOwnerWindow(action);
		else
			window.close();
	}
  </script>
	</body>
</html>
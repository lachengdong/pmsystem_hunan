<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加流程配置</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
		<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
		<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
		<style type="text/css">
			html,body {
				font-size: 12px;padding: 0;margin: 0;border: 0;height: 100%;overflow: hidden;
			}
		</style>
	</head>
	<body>
			<div class="mini-toolbar" style="border-bottom: 0; padding: 0px;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 100%;">
							<a class="mini-button" onclick="SaveData();" plain="true" style="width: 60px">确定</a>
							<a class="mini-button" onclick="onCancel" plain="true" style="width: 60px;">取消</a>
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
						<td align="center" style="width: 100px;" >
							流程定义ID
						</td>
						<td style="width: 150px;">
							<input name="flowdefid" id="flowdefid" class="mini-textbox"  required="true" value="${flowdefid}" />
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							用户选择
						</td>
						<td style="width: 180px;">
							<input name="userid" id="userid" class="mini-treeselect"  url="<%=path%>/grant/ajax/getOrgAndUserTree.json?1=1"
								   multiSelect="true"  valueFromSelect="false" textField="name" valueField="id" parentField="pid" s 
							       allowInput="false" showRadioButton="false" showFolderCheckBox="false"/>
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							节点ID
						</td>
						<td style="width: 100px;">
							<input name="nodeid" id="nodeid" class="mini-textbox"  required="true" value="${nodeid}" />
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							备用1
						</td>
						<td style="width: 100px;">
							<input name="text1" id="text1" class="mini-textbox" value="${text1}"/>
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							备用2
						</td>
						<td style="width: 100px;">
							<input name="text2" id="text2" class="mini-textbox" value="${text2}"/>
						</td>
					</tr>
					<tr>
						<td align="center" stype="width:100px;">
							备注
						</td>
						<td style="width: 0px;">
							<input name="remark" id="remark" class="mini-textbox" required="true" value="${remark}" />
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							排序
						</td>
						<td style="width: 100px;">
						    <input name="sn" id="sn" class="mini-spinner" allowInput="true" maxValue="99" minValue="1" value="${sn}" />
						</td>
					</tr>
				</table>
			</div>
		</form>
		<script type="text/javascript">
		mini.parse();
		if('${nodeid}')
        	mini.get("nodeid").setEnabled(false);
		if('${flowdefid}')
        	mini.get("flowdefid").setEnabled(false);
        
        
    	 	
    	function SaveData() {
    	  var form = new mini.Form("#form1");
    	  
			// 校验
			form.validate();
			if (form.isValid() == false){
				return;
			}
       		
            var data = form.getData(true, false);
            var datas = mini.encode([data]);
				$.ajax( {
					url : "saveFlowSetUpDate.action?1=1&nodeid=${nodeid}&groupid=${groupid}",
					type : "post",
					data : { data:datas },
					success : function(text) {
						text = eval(text);
						if(text=='2'){
							alert("本组的节点已配置，请重新输入！");
						}else{
							alert("保存成功！");
							onCancel();
						}
					},
					error: function (jqXHR, textStatus, errorThrown){
						mini.alert("保存失败！");
					}
				} );
		}
		function onCancel(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else
				window.close();
		}
		function beforenodeselect(e) {alert(e);
            //禁止选中父节点
            if (e.isLeaf == false) e.cancel = true;
        }
        
	</script>
	</body>
</html>
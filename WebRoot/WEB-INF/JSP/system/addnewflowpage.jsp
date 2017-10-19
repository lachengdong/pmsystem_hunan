<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加流程明细</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
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
						<td align="center" style="width: 100px;" >
							流程定义ID
						</td>
						<td style="width: 150px;">
							<input name="flowid" id="flowid" class="mini-textbox"  required="true" value="${flowid}" requiredErrorText="流程ID不能为空"/>
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							流程相关按钮
						</td>
						<td style="width: 150px;">
						<input name="resid" id="resid"  class="mini-treeselect" parentField="presid" 
								url="<%=path %>/resource/ajax/list.json?1=1" multiSelect="false" emptyText="选择对应按钮"
								textField="resname" valueField="resid" showFolderCheckBox="false" expandOnLoad="false"
								showClose="false" popupWidth="150px"   required="true"  requiredErrorText="相关按钮不能为空"
						    />
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							流程简称
						</td>
						<td style="width: 150px;">
							<input name="jiancheng" id="jiancheng" class="mini-textbox" emptyText="拼音首字母大写" required="true" value="${jiancheng}" requiredErrorText="流程简称不能为空"/>
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							源节点ID
						</td>
						<td style="width: 100px;">
							<input name="snodeid" id="snodeid" class="mini-textbox"  required="true"  requiredErrorText="源节点ID不能为空" />
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							目标节点ID
						</td>
						<td style="width: 100px;">
							<input name="dnodeid" id="dnodeid" class="mini-textbox"  required="true"  requiredErrorText="目标节点ID不能为空"/>
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							流转说明
						</td>
						<td style="width: 100px;">
							<input name="explain" id="explain" class="mini-textbox"  required="true"  requiredErrorText="流程说明不能为空" />
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							流转状态
						</td>
						<td style="width: 100px;">
							<input name="state" id="state" class="mini-textbox"  required="true"  requiredErrorText="流程状态不能为空" />
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							备用1
						</td>
						<td style="width: 100px;">
							<input name="text1" id="text1" class="mini-textbox" />
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							备用2
						</td>
						<td style="width: 100px;">
							<input name="text2" id="text2" class="mini-textbox" />
						</td>
					</tr>
					<tr>
						<td align="center" style="width: 100px;">
							备用3
						</td>
						<td style="width: 100px;">
							<input name="text3" id="text3" class="mini-textbox" />
						</td>
					</tr>
					<tr>
						<td align="center" stype="width:100px;">
							备用
						</td>
						<td style="width: 0px;">
							<input name="int1" id="int1" class="mini-combobox" data="intdata" value="${int1}" />
						</td>
					</tr>
					
					<tr>
						<td align="center" stype="width:100px;">
							备注
						</td>
						<td style="width: 0px;">
							<input name="remark" id="remark" class="mini-textbox" required="true" value="${remark}" emptyText="流程名称" requiredErrorText="备注不能为空" />
						</td>
					</tr>
				</table>
			</div>
		</form>
		<script type="text/javascript"><!--
		var intdata=[{id:0, text:'申请人'},{id:1, text:'罪犯'},{id:2,text:'用户'},{id:3,text:'其他'}];
		mini.parse();
		if('${flowid}'){
        	mini.get("remark").setEnabled(false);
    	 	mini.get("flowid").setEnabled(false);	
    	 	mini.get("int1").setEnabled(false);
    	 	mini.get("jiancheng").setEnabled(false);
        }
        
    	 	
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
					url : "addFlowPage.action?1=1",
					type : "post",
					data : {
						data:datas
					},
					success : function(text) {
						mini.alert("保存成功！");
							onCancel();
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
--></script>
	</body>
</html>
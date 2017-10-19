<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<base href="<%=basePath%>">
	<title>流程设计</title> <!--[if lt IE 9]>
<?import namespace="v" implementation="#default#VML" ?>
<![endif]-->
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
		type="text/css" />
	<script src="<%=path%>/scripts/validate.js" type="text/javascript"></script>
	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>

	<link rel="stylesheet" type="text/css"
		href="<%=path%>/workFlowDesign/ExtJs/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css"
		href="<%=path%>/workFlowDesign/designUI.css" />
	<script src="<%=path%>/workFlowDesign/ExtJs/ext-all.js"
		type="text/javascript"></script>
	<script src="<%=path%>/workFlowDesign/workFlowDesign.js"
		type="text/javascript"></script>


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
	<div id='divResID' style='display: none;'>
		<table style='margin-top:10px;width:100%'>
			<colgroup>
				<col with='55px' />
				<col />
			</colgroup>
			<tr>
				<td><LABEL style="width:40px;float:left;margin-left:2px;"
					unselectable="on">按钮:</LABEL></td>
				<td style="align:left"><input name="dresid" id="dresid"
					class="mini-treeselect" parentField="presid"
					onvaluechanged='onresidSelectchanged'
					style="height:25px;position:relative;width:133px;float:left"
					url="<%=path%>/resource/ajax/list.json?1=1" multiSelect="false"
					emptyText="选择对应按钮" textField="resname" valueField="resid" 
					showFolderCheckBox="false" expandOnLoad="false" showClose="false"
					popupWidth="100%" required="true" requiredErrorText="相关按钮不能为空" /></td>
			</tr>
		</table>

	</div>

	<div id="divWorkFlowBg" style='width:100%; height:100%'></div>

	<script type="text/javascript">
		var configs = {
			tools : 'NewFlow,-,showGrid,-,undo,-,redo,-,deleteb,showXml,->,save',
			selectTreeContainID : 'divResID',
			selectTreeID : 'dresid'
		};
		Ext.onReady(function() {
			workFlowDesign.doUpdateTreeUI = updateResidUI;
			workFlowDesign.doSave = SaveData;
			workFlowDesign.init(divWorkFlowBg, configs);	
		});

		function onresidSelectchanged(e) {
			workFlowDesign.setresbtnID(e.sender.getValue(), e.sender.text);
		}

		function updateResidUI(id) {
			if (id == undefined) {
				mini.get('dresid').setValue('');
			} else {
				mini.get('dresid').setValue(id);
			}
		}
		function SaveData() {
			var datas = workFlowDesign.getJOSNData();
			if (datas.error != '') {
				mini.alert(datas.error);
				return;
			}
			var msgid = mini.loading('保存中，请稍后......', '');
			$.ajax({
				url : "addFlowPage.action?1=1",
				type : "post",
				data : {
					data : datas.data
				},
				success : function(text) {
					mini.hideMessageBox(msgid);
					mini.alert("保存成功！");
				},
				error : function(jqXHR, textStatus, errorThrown) {
					mini.hideMessageBox(msgid);
					mini.alert("保存失败！");
				}
			});
		}
	</script>
</body>
</html>

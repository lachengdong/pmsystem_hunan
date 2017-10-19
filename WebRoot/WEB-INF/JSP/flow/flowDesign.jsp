<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流程图设计</title>
<!--[if lt IE 9]>
<?import namespace="v" implementation="#default#VML" ?>
<![endif]-->

<link href="<%=path%>/css/pmisystem.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
	rel="stylesheet" type="text/css" />
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

.actionIcons span {
	width: 16px;
	height: 16px;
	display: inline-block;
	background-position: 50% 50%;
	cursor: pointer;
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
					unselectable="on">按钮：</LABEL></td>
				<td style="align:left"><input id="dresid" name="dresid"
					allowInput="false" class="mini-buttonedit"
					onbuttonclick="onflowBtnEdit" /> <input id="hparameter"
					name="hparameter" class="mini-hidden" /></td>
			</tr>
		</table>
	</div>

	<div class="mini-panel" title="流程转向按钮绑定" iconCls=""
		style="width:900px;height:550px;z-index:40000;left:200px;top:50px;display:none;"
		buttons="close" allowResize="true" collapseOnTitleClick="true" bodyStyle="padding:0px;"
		onbuttonclick="onbuttonclick" id="pnlbtnSet" showCollapseButton="true"></div>

	<div class="mini-panel" title="请选择任务执行者 ：" iconCls=""
		style="width:650px;height:580px;z-index:40000;left:200px;top:10px;display:none;padding:0px;"
		bodyStyle="padding:0px;" buttons="close" allowResize="true"
		collapseOnTitleClick="true" onbuttonclick="onbuttonclick1"
		id="pnlUserSet"></div>


	<input name="actiontype" id="actiontype" class="mini-hidden"
		value="${actionType}" />
	<div id="divWorkFlowBg" style='width:100%; height:100%'></div>

	<script type="text/javascript">
		var configs = {
			//tools : 'NewFlow,-,showGrid,-,undo,-,redo,-,deleteb,->,doback,save',
			tools : 'showGrid,-,undo,-,redo,-,deleteb,->,savedraft,-,save',
			selectTreeContainID : 'divResID',
			selectTreeID : 'dresid'
		};
		Ext.onReady(function() {				
			configs.templates = Ext.JSON.decode('${templates}');			
			configs.flowTypes=Ext.JSON.decode('${flowTypes}');
			if (acType != 'Edit') {
				configs.tempid = '${flowdefine.tempid}';
			} else {
				configs.tempid = '';
			}			
			
			//设置流程流向关联按钮
			workFlowDesign.doUpdateTreeUI = updateResidUI;
			//设置保存处理
			workFlowDesign.doSave = SaveData;
			workFlowDesign.doSaveDraft = SaveDataDraft;
			//页面根路径
			workFlowDesign.$basepath = '${path}' + '/';
			//初始化
			workFlowDesign.init('divWorkFlowBg', configs);
			//设置新建流程的ID --补丁 
			workFlowDesign.loadInfo('${flowdefid}', '新建流程 ', '1', {}, {});
			//设置任务分配者
			workFlowDesign.doSetAssign = showAssigneer;
			workFlowDesign.doShowBtnSet = onflowBtnEdit;
			var acType = '${actionType}';
			if (acType != 'Edit') {
				return;
			}
			//如果是修改，加载以前的flow
			var flowdefid = '${flowdefine.flowdefid}';
			var flowdescription = '${flowdefine.remark}';
			var type = '${flowdefine.type}';
			var nodedata = Ext.JSON.decode('${flowdefine.nodedata}');
			var linedata = Ext.JSON.decode('${flowdefine.linedata}');
			//数据加载完毕，显示设计窗口
			workFlowDesign.loadInfo(flowdefid, flowdescription, type, nodedata,
					linedata);
		});

		function drawData() {
			parent.reflesh();
			var flowdefid = '${flowdefine.flowdefid}';
			var flowdescription = '${flowdefine.remark}';
			var nodedata = Ext.JSON.decode('${flowdefine.nodedata}');
			var linedata = Ext.JSON.decode('${flowdefine.linedata}');
			workFlowDesign.loadInfo(flowdefid, flowdescription, nodedata,
					linedata);
		}
		var maskwin;
		// 设定流转按钮
		function onflowBtnEdit() {
			maskwin = mini.loading();
			var pnl = mini.get("pnlbtnSet");
			var iframe = pnl.getIFrameEl();
			var dresid = mini.get("dresid");
			var hparameter = mini.get("hparameter");
			var url = "${path}/public/topage.action?viewName=flow/flowDeliverBtnSet";
			var id = workFlowDesign.$focusElement.id;
			var data = {
				resid : dresid.getValue(),
				parameter : hparameter.getValue(),
				type : workFlowDesign.$nodeData[workFlowDesign.$lineData[id].from].type
			};
			if (!iframe) {
				pnl.load(url, function() {
					iframe = this.getIFrameEl();
					iframe.contentWindow.SetData(data);
				}, function(action) {
				});
			} else {
				iframe.contentWindow.SetData(data);
			}
			pnl.show();
			setPosition("pnlbtnSet");
		}

		function resetFlowBtn(resid, residName, resParam) {
			updateResidUI(resid, residName, resParam);
			workFlowDesign.setresbtnID(resid, residName, resParam);
			onbuttonclick();
		}

		function onbuttonclick() {
			var pnl = mini.get("pnlbtnSet");
			pnl.hide();

			if (maskwin) {
				mini.hideMessageBox(maskwin);
			}
		}

		function onbuttonclick1() {
			var pnl = mini.get("pnlUserSet");
			pnl.hide();

			if (maskwin) {
				mini.hideMessageBox(maskwin);
			}
		}

		//更新流程按钮绑定显示
		function updateResidUI(resid, residName, resParam) {
			var dresid = mini.get("dresid");
			var hparameter = mini.get("hparameter");
			dresid.setValue(resid);
			dresid.setText(residName);
			hparameter.setValue(resParam);

		}

		//显示任务执行者窗口 
		function showAssigneer(input) {
			maskwin = mini.loading();
			var url = "${path}/public/topage.action?viewName=user/userSelector";
			var pnl = mini.get("pnlUserSet");
			var iframe = pnl.getIFrameEl();
			var data = {
				action : "new",
				data : input
			};

			if (!iframe) {
				pnl.load(url, function() {
					iframe = this.getIFrameEl();
					iframe.contentWindow.SetData(data);
				}, function(action) {
				});
			} else {
				iframe.contentWindow.SetData(data);
			}
			pnl.show();
			
			setPosition("pnlUserSet");

		}
		
		
		function setPosition(id)
		{
			var dialog = $("#"+id);
			height = dialog.height();
			width = dialog.width();
			
			var wWidth = (window.innerWidth || (window.document.documentElement.clientWidth || window.document.body.clientWidth));
			var hHeight = (window.innerHeight || (window.document.documentElement.clientHeight || window.document.body.clientHeight));
			var top = left = 0;
			var bst = document.body.scrollTop || document.documentElement.scrollTop;
			top = Math.round((hHeight - height) / 2 + bst) + "px";
			mleft = "-" + Math.round(width / 2) + "px";
			top = top < 0 ? top = 0 : top;
			dialog.css({
						"top" : top,
						"left" : "50%",
						"margin-left" : mleft
					});			
		}

		function setSendUser(users) {
			workFlowDesign.setAssign(users);
			onbuttonclick1();
		}

		//保存数据
		function SaveData() {
			var datas = workFlowDesign.getJSONData();
			if (datas.error != '') {
				mini.alert(datas.error);
				return;
			}
			var msgid = mini.loading('保存中，请稍后......', '');
			$.ajax({
				url : "${path}/OAflow/addOrUpdateFlow.action?1=1",
				type : "post",
				data : datas.data,
				success : function(text) {
					mini.hideMessageBox(msgid);
					mini.alert("保存成功！", "提示", function() {
						parent.reloadData();
					});
				},
				error : function(jqXHR, textStatus, errorThrown) {
					mini.hideMessageBox(msgid);
					//mini.alert(jqXHR);
					//mini.alert(textStatus);
					//mini.alert(errorThrown);
					//var text=mini.encode(errorThrown);			
					///TODO:需要了解errorThrown信息
					mini.alert("保存失败！");
				}
			});
		}

		//保存数据
		function SaveDataDraft() {
			var datas = workFlowDesign.getDataWithoutCheck();
			if (datas.error != '') {
				mini.alert(datas.error);
				return;
			}
			var msgid = mini.loading('保存中，请稍后......', '');
			$.ajax({
				url : "${path}/OAflow/addOrUpdateFlow.action?1=1",
				type : "post",
				data : datas.data,
				success : function(text) {
					mini.hideMessageBox(msgid);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					mini.hideMessageBox(msgid);
					mini.alert("保存失败！");
				}
			});
		}

		function showAssigneer1(input) {
			var url = "${path}/public/topage.action?viewName=user/userSelector";
			mini.open({
				url : url,
				title : "请选择任务执行者 ：",
				width : 1000,
				height : 550,
				showMaxButton : true,
				allowResize : false,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {
						action : "new",
						data : input
					};
					iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					if (action == 'close' || action == undefined
							|| action == null)
						return;
					if ("undefined" != typeof action) {
						workFlowDesign.setAssign(action);
					}
				}
			});
		}

		function onflowBtnEdit1(e) {
			var dresid = mini.get("dresid");
			var hparameter = mini.get("hparameter");
			var url = "${path}/public/topage.action?viewName=flow/flowDeliverBtnSet";
			var id = workFlowDesign.$focusElement.id;
			mini
					.open({
						url : url,
						showMaxButton : true,
						title : "流程转向按钮绑定",
						width : 950,
						height : 550,
						onload : function() {
							var iframe = this.getIFrameEl();
							var data = {
								resid : dresid.getValue(),
								parameter : hparameter.getValue(),
								type : workFlowDesign.$nodeData[workFlowDesign.$lineData[id].from].type
							};
							iframe.contentWindow.SetData(data);
						},
						ondestroy : function(action) {
							if (action == "ok") {
								var iframe = this.getIFrameEl();
								var data = iframe.contentWindow.GetData();
								data = mini.clone(data);
								if (data) {
									updateResidUI(data.resid, data.residName,
											data.resParam);
									workFlowDesign.setresbtnID(data.resid,
											data.residName, data.resParam);
								}
							}
						}
					});
		};
	</script>
</body>
</html>

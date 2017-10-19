<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.gkzx.common.GkzxCommon"%>
<%
	String path = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>罪犯减刑（假释）立案审批表</title>
 	<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
 	<script src="<%=path%>/scripts/loginsealV7.js" type="text/javascript"></script>
		<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
</head>
<body onload="init('${menuid }')">
	<div id="form1" class="mini-splitter" vertical="true" style="width: 100%; height: 100%; border: 0px;">
		<div size="38px;" showCollapseButton="false">
			<div class="mini-toolbar" style="height: 30px;">
				<table>
					<tr>
					<td style="width: 100%;">
					<a class="mini-button" style="display: none;" id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="doMenueButton(4);"></a>&nbsp;
					<a class="mini-button" style="display: none;" id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="doMenueButton(3);"></a>&nbsp;
					<span class="separator"></span>
						${topstr }
					<a class="mini-button" plain="true" iconCls="icon-close" onclick="onCancel()">关闭</a>
					<span class="separator"></span>
					<a class="mini-button" style="display: none;" id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="doMenueButton(5);"></a>&nbsp;
					<a class="mini-button" style="display: none;" id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="doMenueButton(13);"></a>&nbsp;
					<a class="mini-button" style="display: none;" id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="doMenueButton(10);"></a>&nbsp;
					<span class="separator"></span>
					<a class="mini-button" style="display: none;" id="12465" iconCls="icon-gk_bjsj" tooltip="笔迹设置" plain="true" onclick="doMenueButton(12)"></a>&nbsp;
					<a class="mini-button" style="display: none;" id="12461" iconCls="icon-gk_sxqm" tooltip="手写签批" plain="true" onclick="doMenueButton(6);"></a>&nbsp;
					<a class="mini-button" style="display: none;" id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="doMenueButton(7);"></a>&nbsp;
					<span class="separator"></span>
					<a class="mini-button" style="display: none;" id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="doMenueButton(8);"></a>&nbsp;
					<span class="separator"></span>
					<a class="mini-button" style="display: none;" id="12464" iconCls="icon-gk_print" tooltip="文档打印" plain="true" onclick="doMenueButton(9);"></a>&nbsp;
					<a class="mini-button" style="display: none;" id="12466" iconCls="icon-gk_open" tooltip="加载文档" plain="true" onclick="doMenueButton(0);"></a>&nbsp;
					<a class="mini-button" style="display: none;" id="12467" iconCls="icon-gk_save" tooltip="另存为..." plain="true" onclick="doMenueButton(1);"></a>&nbsp;
					</td>
					<td style="white-space: nowrap;">
					<a class="mini-button" style="display: none;" id="1111" iconCls="icon-gk_help" tooltip="帮助" plain="true" onclick=""></a>&nbsp;
					</td>
					</tr>
				</table>
			</div>
		</div>
		<div showCollapseButton="false">
			<jsp:include page="/WEB-INF/JSP/form/buttoncontrol.jsp"></jsp:include>
			<jsp:include page="/WEB-INF/JSP/form/include_form.jsp"></jsp:include>
		</div>
	</div>
	<div id="longinSeal" style="display:none;"></div>
<script type="text/javascript">
	mini.parse();
	if('${closetype}'=='0'){
		$("#900047").hide();
	}
	
	var form = new mini.Form("form1");
	var grid = mini.get("datagrid1");
	var flowdefid = mini.get("flowdefid").getValue();

	function saveNext() {
		var menuid = mini.get("menuid").getValue();
		var id = mini.get("id").getValue();
		var index = id.indexOf(",");
		id = id.substring(index + 1, id.length);
		var url = "theCommutationParoleApplicationTabs.action?1=1&id=" + id
				+ "&menuid=" + menuid + "&tempid=SH_ZFJXJSLASPB"+"&flowdefid="+flowdefid;
		if (index > 0) {
			parent.location.href = url;
		} else {
			//alert("处理已完成！");
			onCancel();
		}
	}

	//跳转到选择罪犯页面
	function onCancel(closetype) {
		if('${closetype}'=='0'){ //关闭在待办事项办理案件时的弹出窗口
	          var parentWindow = window.parent;
	          parentWindow.CloseWindow("cancel");
	     }else {
	     	var url = "criminalParoleFileApplication.action?1=1&menuid=900041"+"&flowdefid="+flowdefid;
			parent.location.href = url;
	     }
	}
	//刑罚科同意报省局
	function penaltyOfConsent(opionval) {
		var applyid = mini.get("applyid").getValue();
		var flowdraftid = mini.get("flowdraftid").getValue();
		var url = "<%=path%>/commutationParole/loadDataGrid.action?1=1&crimid=" + applyid;
		$.ajax({
              url: url,
              data: {type:1},
              type: "post",
              success: function (text) {  
              	   var text = eval(text);
              	   if(text) text = text.length;
              	   if(text>0){  
              		   operationOpprove('900248','yes',opionval);//处室审批
              	   }else{
              		   operationOpprove('900054','end',opionval);
              	   }
              },			
              error: function (jqXHR, textStatus, errorThrown) {
              
              }
		});
	}
	//标准方法接口定义
	function SetData(data) {
		data = mini.clone(data);
		mini.get("operator").setValue(data.action);
	}
</script>
</body>
</html>

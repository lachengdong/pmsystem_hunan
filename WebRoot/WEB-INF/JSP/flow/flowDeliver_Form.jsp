<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<title>公文起草表单页面</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/css/flowInstance.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script type="text/javascript">
if ('${attachjs}' != '')
	$.getScript('${path}/scripts/${attachjs}');
</script>
<style type="text/css">
</style>
</head>
<body>
	<div style="BORDER-BOTTOM-COLOR: #428bca;border-bottom: 1px solid;">
		<table cellPadding="0" cellSpacing="0">
			<tr>
				<td style="width: 100%;white-space: nowrap;">
			<a class="mini-button"  style="" id="12456" iconCls="icon-gk_fullscream" tooltip="全屏模式" plain="true" onclick="topOAflowdeliver.doMenueButton(4);"></a>&nbsp;
			<a class="mini-button"  style="" id="12455" iconCls="icon-gk_page" tooltip="阅读模式" plain="true" onclick="topOAflowdeliver.doMenueButton(3);"></a>&nbsp;
			
	    	<span class="separator"></span>
	    	<a class="mini-button"  style="" id="12458" iconCls="icon-gk_gz" tooltip="签名（章）" plain="true" onclick="topOAflowdeliver.doMenueButton(5);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12459" iconCls="icon-gk_qfz" tooltip="骑缝章" plain="true" onclick="topOAflowdeliver.doMenueButton(13);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12460" iconCls="icon-gk_cx" tooltip="撤销签名" plain="true" onclick="topOAflowdeliver.doMenueButton(10);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="" id="12465" iconCls="icon-gk_bjsj"  tooltip="笔迹设置" plain="true" onclick="topOAflowdeliver.doMenueButton(12)"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12461" iconCls="icon-gk_sxqm"  tooltip="手写签批" plain="true" onclick="topOAflowdeliver.doMenueButton(6);"></a>&nbsp;
	    	<a class="mini-button"  style="" id="12462" iconCls="icon-gk_xpc" plain="true" tooltip="擦除手写签批" onclick="topOAflowdeliver.doMenueButton(7);"></a>&nbsp;
	    	<span class="separator"></span> 
	    	<a class="mini-button"  style="" id="12463" iconCls="icon-gk_zw" tooltip="按指纹" plain="true" onclick="topOAflowdeliver.doMenueButton(8);"></a>&nbsp;
				
				
				${topTools}</td>
				<td style="white-space: nowrap;padding-bottom:1px"><input
					class="mini-hidden" id="userroles" value="${userRoles}" /> <input
					class="mini-hidden" id="keysreflect" value="${keysreflect}" /> <a
					a class="mini-button" id="10066" iconCls="icon-save" id="btnSave"
					plain="true" onclick="DoSaveDocForm();">保存</a> <a
					class="mini-button" id="btnPrint" iconCls="icon-print" plain="true"
					onclick="topOAflowdeliver.doMenueButton(9);">打印</a></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit" style="height: 0px;width: 0px;" id="dvflowform">
		<script src="<%=path%>/scripts/form/loadaip.js"></script>
		<script src="<%=path%>/scripts/form/OAflowdeliver.js"></script>
		<script LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
			var aipObj = document.all.HWPostil1;
			topOAflowdeliver = new OAflowdeliver(aipObj);
			topOAflowdeliver.$keyfieldsreflector = mini
					.decode('${keysreflect}');
			topOAflowdeliver.$flowdefid = '${flowdefid}';
			topOAflowdeliver.$templetid = '${templetid}';
			topOAflowdeliver.$taskid = '${taskid}';
			topOAflowdeliver.$flowid = '${flowid}';
			topOAflowdeliver.$basePath = '${path}';
			topOAflowdeliver.$action = '${action}';
			topOAflowdeliver.$modiflag = '${modiflag}';
			topOAflowdeliver.$flowdocid = '${flowdocid}';
			topOAflowdeliver.$userName = '${userName}';
			topOAflowdeliver.$userid='${userid}';
			topOAflowdeliver.$orgName = '${orgName}';
			topOAflowdeliver.$orgid= '${orgid}'
			topOAflowdeliver.$flowtype = '${type}';
			topOAflowdeliver.$assigners = "";
			topOAflowdeliver.$menuid='${resid}';
			topOAflowdeliver.$attachjs='${attachjs}';			
			topOAflowdeliver.runModelReady('${formcontent}', '0');			
			topOAflowdeliver.api_display('${formDatajson}', '${selectDatajson}');			
			topOAflowdeliver.setNodeEditable(aipObj, document
					.getElementById("userroles").value);
			AddCustAction(topOAflowdeliver, aipObj, '${path}');			
		</script>
		<SCRIPT LANGUAGE="javascript" FOR="HWPostil1"
			EVENT="JSNotifyBeforeAction(lActionType,lType,strName,strValue)">
			topOAflowdeliver.JSNotifyBeforeAction(lActionType, lType, strName,
					strValue);
		</SCRIPT>
		<SCRIPT LANGUAGE="javascript" FOR="HWPostil1"
			EVENT="NotifyDocOpened()">
			mini.get("dvflowform").set({
				width : '100%'
			});
		</SCRIPT>
		<script LANGUAGE="javascript" FOR="HWPostil1"
			EVENT="NotifyLineAction(lPage,lStartPos,lEndPos)">
			
		</script>
	</div>
	<script type="text/javascript">
		var topOAflowdeliver = null;
		mini.parse();
		//流程流转操作
		function Execute(explain) {
			var type = '${type}';			
			if (type != '3' && topOAflowdeliver.$assigners == "") {
				var url = "${path}/deliver/toGetCurrentNodeAssigner.page?taskid=${taskid}&flowdefid=${flowdefid}&explain="
						+ explain;
				url = encodeURI(url);
				vRet = window
						.showModalDialog(
								url,
								"",
								"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:300px;dialogWidth:550px");
				if (vRet) {
					topOAflowdeliver.$assigners = vRet;
				} else {
					alert("请选择任务执行者！");
					return;
				}
			}
			return topOAflowdeliver.Execute(explain);
		}
		function doExecuteCustormMethod(cmd)
		{
			eval(cmd+'();');
		}
		//流程发起--草稿 
		function DoSaveDocForm() {
			return topOAflowdeliver.doSaveDraft('');
		}
		//设置公文流程实例公文正文文件名称
		function setFlowInstanceDocCntName(name) {
			topOAflowdeliver.$doccontenfilename = name;
		}
		//设置公文流程实例公文版式正文文件名称
		function setFlowInstanceDocAipName(name) {
			topOAflowdeliver.$docaipcontentfilename = name;
		}
		function AddCustAction() {
		};
	</script>


</body>
</html>

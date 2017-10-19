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
<title>附件阅读编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/flowInstance.css" rel="stylesheet"
	type="text/css" />
<link href="<%=path%>/css/pmisystem.css" rel="stylesheet"
	type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<script src="<%=path%>/scripts/form/aip.js" type="text/javascript"></script>
<style type="text/css">
</style>
</head>
<body>
	<div class="mini-toolbar" style="border: 0px;margin:2px">
		<table cellPadding="0" cellSpacing="0">
			<tr>
				<td style="width: 100%;white-space: nowrap;"><a
					class="mini-button" iconCls="icon-node" id="allsave" plain="true"
					onclick="aip_0.FullScreen();">全屏</a> &nbsp; <c:if
						test="${action !='view'}">
						<input name="ddltrReversion" id="ddltrReversion"
							class="mini-combobox"
							data="[{id:'1',text:'保留痕迹'},{id:'0',text:'不留痕迹'}]" value="1"
							onvaluechanged="aip_0.setTrackRevision(e);" width="80px" />
					&nbsp;&nbsp; <input name="ddlshowRevison" id="ddlshowRevison"
							class="mini-combobox"
							data="[{id:'1',text:'显示痕迹'},{id:'0',text:'不显痕迹'}]" value="0"
							onvaluechanged="aip_0.setShowRevision(e);" width="80px" />
					</c:if></td>
				<td style="white-space: nowrap;"><c:if
						test="${action !='view'}">
						<a a class="mini-button" id="10066" iconCls="icon-save"
							id="btnSave" plain="true" onclick="aip_0.Save('${flowid}');">保存</a>
					</c:if> <a class="mini-button" id="btnPrint" iconCls="icon-print"
					plain="true" onclick="aip_0.obj.PrintDoc(1,1);">打印</a> <a
					class="btn btn-primary btn-sm" plain="true"
					onclick="CloseWindow('close');">关闭</a></td>
			</tr>
		</table>
	</div>
	<div class="mini-fit" id="docc" style="height: 0px;width: 0px;"></div>
	<SCRIPT language=javascript for=AIP_0 event=NotifyCtrlReady>
		aip_0.obj.LoadOriginalFile(
				"${basePath}attachment/code/${code}/fileDownLoad.action",
				"${extname}");
		aip_0.OnCtrlReady();
		mini.get("docc").set({
			width : '100%'
		});
		var action = '${action}';
		if (action == "view") {
			aip_0.obj.ProtectDoc(3, '', 1);
		} else {
			aip_0.obj.TrackRevisions = 1;
			aip_0.obj.ShowRevisions = 0;
		}
	</SCRIPT>
	<SCRIPT language=javascript for=AIP_0 event=NotifyDocOpened>
		aip_0.OnDocOpened();
	</SCRIPT>
	<script type="text/javascript">
		aip_0 = new AIP(
				'docc',
				{
					"loginUser" : "${userName}",
					"fileName" : "Attachment[doc]",
					"aipUploadUrl" : "${basePath}attachment/code/${code}/uploadDoc.action?filetype=${extname}"
				});

		//关闭窗口
		function CloseWindow(action) {
			if (window.CloseOwnerWindow)
				return window.CloseOwnerWindow(action);
			else {
				window.returnValue = action;
				window.close();
			}
		}
	</script>


</body>
</html>

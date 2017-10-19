<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">

<title>流程流转</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link href="<%=path%>/css/gkzxcss.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/scripts/boot.js" type="text/javascript"></script>
<link href="<%=path%>/scripts/miniui/themes/blue/skin.css"
	rel="stylesheet" type="text/css" />
<!-- 必须指定,否则高度为0 -->
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
}

.hide {
	display: none;
}
</style>

</head>
<body>
	<div class="crud-search">
		<div class="mini-toolbar"
			style="padding-top: 5px; padding-bottom: 5px;text-align:left;border-bottom:0;">
			${topTools}</div>
	</div>
	<div class="mini-fit">
		<jsp:include page="/WEB-INF/JSP/form/includjs.jsp"></jsp:include>
		<script src="<%=path%>/scripts/form/loadaip.js"></script>
		<script src="<%=path%>/scripts/form/control/control_seal.js"
			type="text/javascript"></script>
		<script src="<%=path%>/scripts/form/control/control_approve.js"
			type="text/javascript"></script>
		<script src="<%=path%>/scripts/form/control/control_other.js"
			type="text/javascript"></script>
		<script src="<%=path%>/scripts/form/main.js" type="text/javascript"></script>

		<script LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyCtrlReady>
			var aipObj = document.all.HWPostil1;
			document.getElementById("HWPostil1").JSEnv = 1;
			HWPostil1_modelReady('${formcontent}', '0');
			//HWPostil1_display('${formDatajson},'${selectDatajson}');
			//isEditAttribute();
		</script>
		<SCRIPT LANGUAGE=javascript FOR=HWPostil1 EVENT=NotifyDocOpened()>
			
		</SCRIPT>
		<script LANGUAGE="javascript" FOR="HWPostil1"
			EVENT="NotifyLineAction(lPage,lStartPos,lEndPos)">
			// 控件"HWPostil1" 的 NotifyLineAction
			NotifyLineAction(lPage, lStartPos, lEndPos);
		</script>
		<SCRIPT LANGUAGE=javascript FOR=HWPostil1
			EVENT=JSNotifyBeforeAction(lActionType,lType,strName,strValue)>
			HWPostil1_JSNotifyBeforeAction(lActionType, lType, strName,
					strValue);
		</SCRIPT>

	</div>

	<script type="text/javascript">
		mini.parse();
		var resid = '${resid}';
		var flowdefid = '${flowdefid}';
		var tempid = '${tempid}';
		function dosubmitNew(operateType) {
			doSubmitFlow(resid, operateType, flowdefid, tempid);
		}
		function doUploadFile() {
			var aipObj = getmyvalue("HWPostil1");
			var url = "${path}/flowNew/toFileUpload.page";

			vRet = window
					.showModalDialog(
							url,
							"",
							"edge: Raised; center: Yes; help: Yes; resizable: Yes; status: No;dialogHeight:150px;dialogWidth:250px");
			if (vRet) {

				aipObj.SetValue("attchfile", vRet);
			}

			//不进入编辑框
			aipObj.jsValue = 0;
		}
	</script>
</body>
</html>
